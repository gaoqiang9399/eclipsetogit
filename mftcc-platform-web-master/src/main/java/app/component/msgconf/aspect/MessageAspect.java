//package app.component.msgconf.aspect;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.Reader;
//
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.io.IOUtils;
//import org.aspectj.lang.JoinPoint;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//import app.component.cus.dao.MfCusCustomerDao;
//import app.component.cus.entity.MfCusCustomer;
//import app.component.forewarning.bo.MfSysMsgInfoBo;
//import app.component.forewarning.entity.MfSysMsgInfo;
//import app.component.msgconf.dao.MessageAspectDao;
//import app.component.msgconf.dao.MfMsgListenerDao;
//import app.component.msgconf.dao.MfMsgVarDao;
//import app.component.msgconf.entity.MfMsgListener;
//import app.component.msgconf.entity.MfMsgUtil;
//import app.component.msgconf.entity.MfMsgVar;
//import app.component.rec.dao.RecallBaseDao;
//import app.component.sys.dao.SysUserDao;
//import app.component.sys.entity.SysUser;
//
///** 
// * @author
// * @date
// * @version 1.0 
// */
//public class MessageAspect {
//	
//	//private MessageAspectBo messageAspectBo;
//	private static final String SEPERATE_STR = "\\|";
//	private static final String TRIGGER_SEPERATE_STR = ";";
//	private static final String TRIGGER_EXPRESSION_STR = "=";
//	private static final String COL_SEPERATE = "_";
//	private static final String REMOTE_SERVICE_URL = "http://127.0.0.1:8080/FactorService/forewarning/add.json";
//	
//	public MfMsgListenerDao mfMsgListenerDao;
//	public MfMsgVarDao mfMsgVarDao;
//	public MessageAspectDao messageAspectDao;
//	public RecallBaseDao recallBaseDao;
//	public MfSysMsgInfoBo mfSysMsgInfoBo;
//	public SysUserDao sysUserDao ;
//	public MfCusCustomerDao mfCusCustomerDao;
//	
//	public Logger log = LoggerFactory.getLogger(MessageAspect.class);
//	/**
//	public void after(JoinPoint joinPoint) {
//		
//	}
//	
//	public void before(JoinPoint joinPoint) {
//		
//	}
//	 * @throws Exception 
//	**/
//	
//	public void afterReturning(JoinPoint joinPoint, Object returnVal) throws Exception {
//		
//		if(returnVal == null){
//			return ;
//		}else{
//		
//			String methodName = joinPoint.getSignature().getName();	//函数名
//			String className = joinPoint.getSignature().getDeclaringTypeName(); //类名(带完整包路径名)
//			String returnValClassName = returnVal.getClass().getName(); //函数返回值的对象名(带完整包路径名)
//			
//			//System.out.println("methodName:" + methodName + ",className:" + className + ",returnValClassName:" + returnValClassName);
//			//System.out.println(joinPoint.getSignature().getClass());
//			//return ;
//			
//			MfMsgListener mfMsgListener =  new MfMsgListener();
//			mfMsgListener.setListenerClass(className);
//			mfMsgListener.setListenerFunc(methodName);
//			
//			List<MfMsgListener> mfMsgListenerList = getMfMsgListenerAll(mfMsgListener);
//			if(mfMsgListenerList.size() > 0){//拦截后,如果拦截className与methodName在mf_msg_listener存在,则说明是要拦截的数据.
//				String mainTableName = "";
//				String mainTablePKStr = "";
//				String mainTablePKValue = "";
//				String reciverType = mfMsgListenerList.get(0).getReciverType();//发送对象
//				String sendType = mfMsgListenerList.get(0).getSendType();//发送类型
//				String listenerBusi = mfMsgListenerList.get(0).getListenerBusi();//业务阶段
//				String listenerTrigger = mfMsgListenerList.get(0).getListenerTrigger();//触发条件
//				String listenerArgs = mfMsgListenerList.get(0).getListenerArgs();//模板元素参数
//				String modelContent = mfMsgListenerList.get(0).getModelContent();
//				boolean flag = compareTriggerValeToAttributeValue(listenerTrigger,returnVal);
//				if(flag == false){
//					return ;
//				}
//				Map<String,Object> map = getMainTableNameTablePk(listenerArgs);//根据listen表中所有关联的元素,查找元素表,并找出主表以及主表的主键名称
//				mainTableName = (String)map.get("mainTableName");
//				mainTablePKStr = (String)map.get("mainTablePKStr");
//				//根据拦截函数返回的对象,设置主表主键的值(其中包括:判断"拦截函数返回的对象"中是否存在该主键属性)
//				map = setAttributeValue(map,returnValClassName,mainTablePKStr,returnVal);
//				mainTablePKValue = (String)map.get("mainTablePKValue");
//				//将模板所配置的所有元素(每一个元素对应一个MfMsgUtil对象),封装在一个List中
//				List<MfMsgUtil> mfMsgUtilList = getMfMsgUtil(listenerArgs, mainTablePKValue, mainTablePKStr);
//				for(MfMsgUtil mfMsgUtil : mfMsgUtilList){
//					String str = modelContent.replace(mfMsgUtil.getVariableCnName(), mfMsgUtil.getVariableValue());
//					modelContent = str; 
//				}
//				
//				String sql = ""; 
//				List<Map<String,Object>> sqlMapList ;
//				
//				sql = "select CUS_NO from " + mainTableName + " where " + mainTablePKStr + " = '" + mainTablePKValue + "'"; 
//				sqlMapList = messageAspectDao.getResultByDynamicSql(sql);
//				String cusNo = (String)sqlMapList.get(0).get("CUS_NO");
//				
//				sql = "select CUS_MNG_NO FROM MF_CUS_CUSTOMER where CUS_NO = '" + cusNo + "'";
//				sqlMapList = messageAspectDao.getResultByDynamicSql(sql);
//				String cusMngNo = (String)sqlMapList.get(0).get("CUS_MNG_NO");
//				
//				if(reciverType.indexOf("|") == -1){//接收人类型只有一个
//					if(reciverType.equals("01")){//客户经理
//						saveMfSysForewarningInfo("系统拦截消息",modelContent,sendType,
//								"01",cusMngNo,cusNo,listenerBusi);
//					}else if(reciverType.equals("03")){//客户
//						saveMfSysForewarningInfo("系统拦截消息",modelContent,sendType,
//								"02",cusMngNo,cusNo,listenerBusi);
//					}else if(reciverType.equals("04")){//风控主管
//						saveMfSysForewarningInfo("系统拦截消息",modelContent,sendType,
//								"04",cusMngNo,cusNo,listenerBusi);
//					}
//				}else{//存在多个接收人类型
//					String str[] = reciverType.split(SEPERATE_STR);
//					for(int i = 0; i < str.length ; i++){
//						if(str[i].equals("01")){//客户经理
//							saveMfSysForewarningInfo("系统拦截消息",modelContent,sendType,
//									"01",cusMngNo,cusNo,listenerBusi);
//						}else if(str[i].equals("03")){//客户
//							saveMfSysForewarningInfo("系统拦截消息",modelContent,sendType,
//									"02",cusMngNo,cusNo,listenerBusi);
//						}else if(str[i].equals("04")){//风控主管
//							saveMfSysForewarningInfo("系统拦截消息",modelContent,sendType,
//									"04",cusMngNo,cusNo,listenerBusi);
//						}
//					}
//				}
//				
//				return ;
//			}else{
//				return ;
//			}
//			
//		}
//		
//	}
//	
//	public void saveMfSysForewarningInfo(String title,String content,String sendType,
//			String recCusType,String cusMngNo,String cusNo,String listenerBusi) throws Exception {
//		
//		MfSysMsgInfo mfSysMsgInfo = new MfSysMsgInfo();
//		mfSysMsgInfo.setTitle(title);
//		mfSysMsgInfo.setContent(content);
//		mfSysMsgInfo.setSendType(sendType.replace("|", "@"));
//		mfSysMsgInfo.setMsgType(listenerBusi);
//		
//		if(recCusType.equals("01")){//客户经理
//			mfSysMsgInfo.setReceiverType("1");
//			mfSysMsgInfo.setReceiverId(cusMngNo);
//			
//			//List<SysUser> list = recallBaseDao.getSysUserByOpNo(cusMngNo);
//			SysUser sysUser = new SysUser();
//			sysUser.setOpNo(cusMngNo);
//			sysUser = sysUserDao.getById(sysUser);
//			
//			if(sendType.indexOf("01") != -1){//发送方式存在短信
//				mfSysMsgInfo.setReceiverNumber(sysUser.getMobile());
//				mfSysMsgInfo.setPhoneNumber(sysUser.getMobile());
//			}else if(sendType.indexOf("03") != -1){//发送方式存在邮件
//				mfSysMsgInfo.setReceiverNumber(sysUser.getEmail());
//				mfSysMsgInfo.setEmailAddress(sysUser.getEmail());
//			}
//			
//			mfSysMsgInfoBo.insert(mfSysMsgInfo);	
//		}else if(recCusType.equals("03")){//客户
//			mfSysMsgInfo.setReceiverType("2");
//			mfSysMsgInfo.setReceiverId(cusNo);
//			
//			//List<MfCusCustomer> list = afterLoanDao.getMfCustomerByCusNo(cusNo);
//			MfCusCustomer mfCusCustomer = new MfCusCustomer();
//			mfCusCustomer.setCusNo(cusNo);
//			mfCusCustomer = mfCusCustomerDao.getById(mfCusCustomer);
//			
//			if(sendType.indexOf("01") != -1){//发送方式存在短信
//				mfSysMsgInfo.setReceiverNumber(mfCusCustomer.getCusTel());
//				mfSysMsgInfo.setPhoneNumber(mfCusCustomer.getCusTel());
//			}else if(sendType.indexOf("03") != -1){//发送方式存在邮件
//				mfSysMsgInfo.setReceiverNumber(mfCusCustomer.getOtherContactWay());
//				mfSysMsgInfo.setEmailAddress(mfCusCustomer.getOtherContactWay());
//			}
//			
//			mfSysMsgInfoBo.insert(mfSysMsgInfo);
//			
//		}else if(recCusType.equals("04")){//风控主管
//			mfSysMsgInfo.setReceiverType("1");
//			/**
//			SysUser sysUser1 = new SysUser();
//			sysUser1.setRoleNo("BL0004");
//			List<SysUser> list = recallBaseDao.getSysUserByRoleNo1(sysUser1);
//			**/
//			String sql = ""; 
//			List<Map<String,Object>> sqlMapList ;
//			sql = "select * from sys_user where role_no like '%BL0004%'";
//			sqlMapList = messageAspectDao.getResultByDynamicSql(sql);
//			
//			for(Map<String,Object> sqlMap : sqlMapList){
//				if(sendType.indexOf("01") != -1){//发送方式存在短信
//					mfSysMsgInfo.setReceiverNumber((String)sqlMap.get("MOBILE"));
//					mfSysMsgInfo.setPhoneNumber((String)sqlMap.get("MOBILE"));
//					mfSysMsgInfo.setReceiverId((String)sqlMap.get("OP_NO"));
//				}else if(sendType.indexOf("03") != -1){//发送方式存在邮件
//					mfSysMsgInfo.setReceiverNumber((String)sqlMap.get("EMAIL"));
//					mfSysMsgInfo.setEmailAddress((String)sqlMap.get("EMAIL"));
//					mfSysMsgInfo.setReceiverId((String)sqlMap.get("OP_NO"));
//				}
//				
//				mfSysMsgInfoBo.insert(mfSysMsgInfo);
//			}
//		}
//		
//	}
//	
//	public String sendPost(MfSysMsgInfo mfSysMsgInfo) throws Exception{
//		String result = "";
//		HttpClient client = new HttpClient();
//		client.getParams().setContentCharset("utf-8");
//		PostMethod postUrl = new PostMethod(REMOTE_SERVICE_URL);
//		postUrl.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//		postUrl.setRequestHeader("pageEncoding","utf-8");
//		
//		postUrl.addParameter("title", mfSysMsgInfo.getTitle());
//		postUrl.addParameter("content", mfSysMsgInfo.getContent());
//		postUrl.addParameter("sendType", mfSysMsgInfo.getSendType());
//		//postUrl.addParameter("warningType", mfSysMsgInfo.getWarningType());
//		//postUrl.addParameter("warningSub", mfSysMsgInfo.getWarningSub());
//		//postUrl.addParameter("bizPkType", mfSysMsgInfo.getBizPkType());
//		//postUrl.addParameter("bizPkNo", mfSysMsgInfo.getBizPkNo());
//		postUrl.addParameter("receiverId", mfSysMsgInfo.getReceiverId());
//		postUrl.addParameter("receiverNumber", mfSysMsgInfo.getReceiverNumber());
//		
//		try {
//			client.executeMethod(postUrl);
//			Reader r = new InputStreamReader(postUrl.getResponseBodyAsStream(),"utf-8");
//			result = IOUtils.toString(r);
//		} catch (HttpException e) {
//			log.error("MessageAspect sendPost:请求异常", e);
//			e.printStackTrace();
//		} catch (IOException e) {
//			log.error("MessageAspect sendPost:请求异常", e);
//			e.printStackTrace();
//		}
//		return result;
//	}
//	
//	public MfCusCustomerDao getMfCusCustomerDao() {
//		return mfCusCustomerDao;
//	}
//
//	public void setMfCusCustomerDao(MfCusCustomerDao mfCusCustomerDao) {
//		this.mfCusCustomerDao = mfCusCustomerDao;
//	}
//
//	public SysUserDao getSysUserDao() {
//		return sysUserDao;
//	}
//
//	public void setSysUserDao(SysUserDao sysUserDao) {
//		this.sysUserDao = sysUserDao;
//	}
//
//	public MfSysMsgInfoBo getMfSysMsgInfoBo() {
//		return mfSysMsgInfoBo;
//	}
//
//	public void setMfSysMsgInfoBo(MfSysMsgInfoBo mfSysMsgInfoBo) {
//		this.mfSysMsgInfoBo = mfSysMsgInfoBo;
//	}
//
//	public RecallBaseDao getRecallBaseDao() {
//		return recallBaseDao;
//	}
//
//	public void setRecallBaseDao(RecallBaseDao recallBaseDao) {
//		this.recallBaseDao = recallBaseDao;
//	}
//
//	public MfMsgListenerDao getMfMsgListenerDao() {
//		return mfMsgListenerDao;
//	}
//	public void setMfMsgListenerDao(MfMsgListenerDao mfMsgListenerDao) {
//		this.mfMsgListenerDao = mfMsgListenerDao;
//	}
//	public MfMsgVarDao getMfMsgVarDao() {
//		return mfMsgVarDao;
//	}
//	public void setMfMsgVarDao(MfMsgVarDao mfMsgVarDao) {
//		this.mfMsgVarDao = mfMsgVarDao;
//	}
//	public MessageAspectDao getMessageAspectDao() {
//		return messageAspectDao;
//	}
//	public void setMessageAspectDao(MessageAspectDao messageAspectDao) {
//		this.messageAspectDao = messageAspectDao;
//	}
//	
//	/**
//	public Object around(ProceedingJoinPoint pjp) throws Throwable {
//		System.out.println("around start..");
//		Object returnObj = null;
//		try {
//			returnObj =pjp.proceed();
//		} catch (Throwable ex) {
//			System.out.println("error in around");
//			throw ex;
//		}
//		System.out.println("around end");
//		return returnObj;
//	}
//	**/
//	/**
//	public void afterThrowing(JoinPoint jp, Throwable error) {
//		System.out.println("error:" + error);
//	}
//	**/
//	
//	//将原始字符串按指定的分隔符重新更换分隔符为","
//	public String formateStr(String originalStr,String sep){
//		String str[] = originalStr.split(sep);
//		String returnStr = "";
//		for(int i = 0; i < str.length ; i++){	
//			if(i == str.length-1){
//				returnStr = returnStr + "'"+str[i]+"'";
//				break;
//			}else{
//				returnStr = returnStr + "'"+str[i]+"'" + ","; 
//			}
//		}
//		return returnStr;
//	}
//		
//	//检查某一个类中是否存在某个属性
//	//className:包名+类名 ,attributeName:属性名
//	public boolean checkAttribute(String className, String attributeName){
//		Class<?> class1 ;
//		boolean flag = false;
//		try{
//			class1 = Class.forName(className);
//			String fieldname = attributeName;
//			Field[] fields = class1.getDeclaredFields();
//			
//			for (int i = 0; i < fields.length; i++) {
//			    if(fields[i].getName().equals(fieldname)){
//			        flag = true;
//			        break;
//			    }
//			}
//
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return flag;
//	}
//		
//	//根据属性名,获取类中属性的值
//	public String getAttributeValue(Object returnVal, String fieldName) throws Exception {
//		String firstLetter = fieldName.substring(0, 1).toUpperCase();    
//        String getter = "get" + firstLetter + fieldName.substring(1);    
//        Method method = returnVal.getClass().getMethod(getter, new Class[] {});    
//        Object value = method.invoke(returnVal, new Object[] {});
//        return (String)value;
//	}
//		
//	//将数据库字段名称按照java 驼峰命名规则转化为属性名称
//	public String transColumnNameToAttributeName(String columnName){
//		String str[] = columnName.split(COL_SEPERATE);
//		String returnStr = "";
//		for(int i = 0; i < str.length ; i++){
//			if(i == 0){
//				returnStr = returnStr + str[i].toLowerCase();
//			}else{
//				returnStr = returnStr + str[i].substring(0,1).toUpperCase() + str[i].substring(1).toLowerCase();
//			}
//		}
//		return returnStr;
//	}
//		
//	public List<MfMsgUtil> getMfMsgUtil(String listenerArgs,String pkValue,String pkName) throws Exception {
//		log.info("getMfMsgUtil  start");
//		
//		//按照模板表中模板的元素集合listenerArgs组装tableName-tablePk-column<List>的对应关系
//		List<Map<String,Object>> relateList = encapTableRelation(listenerArgs);
//		log.info("relateList size():" + relateList.size());
//		int i = 0;//执行一次拼接sql并执行,则i+1
//		boolean nextTbPkFlag = false;
//		List<MfMsgUtil> returnMfMsgUtilList = new ArrayList<MfMsgUtil>();
//		List<MfMsgUtil> nextTbPkList = new ArrayList<MfMsgUtil>(); ////下一张表所需要的主键List
//		MfMsgUtil mfMsgUtilNextPk = new MfMsgUtil();//下一张表所需要的主键对象
//		
//		do{
//			
//			for(Map<String,Object> relateMap : relateList){//遍历relateList
//				MfMsgUtil returnMfMsgUtil ;
//				
//				if(nextTbPkFlag == false){//首次循环
//					if(((String)relateMap.get("tablePk")).equals(pkName)){//根据外来数据主键查找,查询数据主表
//						
//						nextTbPkList = new ArrayList<MfMsgUtil>(); ////下一张表所需要的主键List
//						mfMsgUtilNextPk = new MfMsgUtil();//下一张表所需要的主键对象
//						
//						
//						String tableName = (String)relateMap.get("tableName");
//						String tablkePk = (String)relateMap.get("tablePk");
//						List<?> columnList = (List<?>)relateMap.get("columnList");
//						String columnStr = "";
//						for(int j = 0 ; j < columnList.size() ; j++ ){//遍历每一个列对象
//							MfMsgUtil columnMfMsgUtil = (MfMsgUtil)columnList.get(j);
//							if(columnMfMsgUtil.getVarType().equals("01")){//变量类型-01 查询结果
//								returnMfMsgUtil = new MfMsgUtil();
//								returnMfMsgUtil.setVariableCnName(columnMfMsgUtil.getVariableCnName());
//								returnMfMsgUtil.setVariableEnName(columnMfMsgUtil.getVariableEnName());
//								returnMfMsgUtil.setVarType(columnMfMsgUtil.getVarType());
//
//								returnMfMsgUtilList.add(returnMfMsgUtil);
//								
//							}else if(columnMfMsgUtil.getVarType().equals("02")){//变量类型-02 关联ID
//								mfMsgUtilNextPk.setVariableCnName(columnMfMsgUtil.getVariableCnName());
//								mfMsgUtilNextPk.setVariableEnName(columnMfMsgUtil.getVariableEnName());
//								mfMsgUtilNextPk.setVarType(columnMfMsgUtil.getVarType());
//
//								nextTbPkList.add(mfMsgUtilNextPk);
//							}
//							
//							if(j == columnList.size() -1){
//								columnStr = columnStr + columnMfMsgUtil.getVariableEnName() ;
//							}else{
//								columnStr = columnStr + columnMfMsgUtil.getVariableEnName() + ",";
//							}
//						}
//						
//						String sql = "select " + columnStr + " from " + tableName + " where " + tablkePk +" = '"+pkValue+"'";
//						List<Map<String,Object>> rsList = messageAspectDao.getResultByDynamicSql(sql);
//						i = i + 1;
//						
//						for(int j = 0; j < returnMfMsgUtilList.size() ; j++){
//							MfMsgUtil mfMsgUtil = (MfMsgUtil)returnMfMsgUtilList.get(j);
//							for(int k = 0 ; k < rsList.size() ; k++){
//								java.util.Map<String,Object> map = (java.util.HashMap<String,Object>)rsList.get(k);
//								mfMsgUtil.setVariableValue((String)map.get(mfMsgUtil.getVariableEnName()));
//							}
//						}
//						
//						for(int j = 0; j < nextTbPkList.size() ; j++){
//							MfMsgUtil mfMsgUtil = (MfMsgUtil)nextTbPkList.get(j);
//							for(int k = 0 ; k < rsList.size() ; k++){
//								java.util.Map<String,Object> map = (java.util.HashMap<String,Object>)rsList.get(k);
//								mfMsgUtil.setVariableValue((String)map.get(mfMsgUtil.getVariableEnName()));
//							}
//						}
//						
//						nextTbPkFlag = true;
//					}
//					
//				}else{//非首次循环,则判断主键是,主键来源于mfMsgUtilNextPk对象(前一个已经执行过查询的表)
//					List<MfMsgUtil> nextTbPkListNew = new ArrayList<MfMsgUtil>(); ////下一张表所需要的主键List
//					for(MfMsgUtil  nextPk : nextTbPkList){
//						if(((String)relateMap.get("tablePk")).equals(nextPk.getVariableEnName())){//根据前一张表中的主键判断
//							
//							//nextTbPkList = new ArrayList<MfMsgUtil>(); ////下一张表所需要的主键List
//							mfMsgUtilNextPk = new MfMsgUtil();//下一张表所需要的主键对象
//							
//							String tableName = (String)relateMap.get("tableName");
//							String tablkePk = (String)relateMap.get("tablePk");
//							List<?> columnList = (List<?>)relateMap.get("columnList");
//							String columnStr = "";
//							for(int j = 0 ; j < columnList.size() ; j++ ){//遍历每一个列对象
//								MfMsgUtil columnMfMsgUtil = (MfMsgUtil)columnList.get(j);
//								if(columnMfMsgUtil.getVarType().equals("01")){//变量类型-01 查询结果
//									returnMfMsgUtil = new MfMsgUtil();
//									returnMfMsgUtil.setVariableCnName(columnMfMsgUtil.getVariableCnName());
//									returnMfMsgUtil.setVariableEnName(columnMfMsgUtil.getVariableEnName());
//									returnMfMsgUtil.setVarType(columnMfMsgUtil.getVarType());
//
//									returnMfMsgUtilList.add(returnMfMsgUtil);
//									
//								}else if(columnMfMsgUtil.getVarType().equals("02")){//变量类型-02 关联ID
//									mfMsgUtilNextPk.setVariableCnName(columnMfMsgUtil.getVariableCnName());
//									mfMsgUtilNextPk.setVariableEnName(columnMfMsgUtil.getVariableEnName());
//									mfMsgUtilNextPk.setVarType(columnMfMsgUtil.getVarType());
//
//									nextTbPkListNew.add(mfMsgUtilNextPk);
//								}
//								
//								if(j == columnList.size() -1){
//									columnStr = columnStr + columnMfMsgUtil.getVariableEnName() ;
//								}else{
//									columnStr = columnStr + columnMfMsgUtil.getVariableEnName() + ",";
//								}
//							}
//							
//							String sql = "select " + columnStr + " from " + tableName + " where " + tablkePk +" = '"+nextPk.getVariableValue()+"'";
//							List<Map<String,Object>> rsList = messageAspectDao.getResultByDynamicSql(sql);
//							i = i + 1;
//							
//							for(int j = 0; j < returnMfMsgUtilList.size() ; j++){
//								MfMsgUtil mfMsgUtil = (MfMsgUtil)returnMfMsgUtilList.get(j);
//								//String variableValue = mfMsgUtil.getVariableValue();
//								for(int k = 0 ; k < rsList.size() ; k++){
//									java.util.Map<String,Object> map = (java.util.HashMap<String,Object>)rsList.get(k);
//									if(mfMsgUtil.getVariableValue() != null && !mfMsgUtil.getVariableValue().equals("")){
//										mfMsgUtil.setVariableValue(mfMsgUtil.getVariableValue());
//									}else{
//										mfMsgUtil.setVariableValue((String)map.get(mfMsgUtil.getVariableEnName()));
//									}
//								}
//							}
//							
//							for(int j = 0; j < nextTbPkListNew.size() ; j++){
//								MfMsgUtil mfMsgUtil = (MfMsgUtil)nextTbPkListNew.get(j);
//								for(int k = 0 ; k < rsList.size() ; k++){
//									java.util.Map<String,Object> map = (java.util.HashMap<String,Object>)rsList.get(k);
//									mfMsgUtil.setVariableValue((String)map.get(mfMsgUtil.getVariableEnName()));
//								}
//							}
//							
//							nextTbPkFlag = true;
//							break;//前一张表提供的对外主键可能为多个,只要匹配上一个,代码执行后,则跳出本次循环
//						}
//					}
//					
//					nextTbPkList = nextTbPkListNew;
//					
//				}
//				
//				
//			}
//			
//		}while(i != relateList.size());
//		
//		log.info("getMfMsgUtil  end");
//		
//		return returnMfMsgUtilList;
//	}
//		
//	public List<Map<String,Object>> encapTableRelation(String listenerArgs) throws Exception{
//		log.info("encapTableRelation  start");
//		List<Map<String,Object>> relateList = new ArrayList<Map<String,Object>>();
//		Map<String,Object> map ;
//		
//		MfMsgVar mfMsgVarQuery = new MfMsgVar();
//		mfMsgVarQuery.setVarId(formateStr(listenerArgs,SEPERATE_STR));
//		//根据模板表中的参数集合在元素表中取表与该表的主键
//		List<MfMsgVar> mfMsgVarTbList = mfMsgVarDao.getTbPkByVarId(mfMsgVarQuery);
//		List<MfMsgUtil> mfMfMsgUtilList = new ArrayList<MfMsgUtil>();
//		for(MfMsgVar mfMsgVarTbResult : mfMsgVarTbList){
//			
//			//relateList = new ArrayList<Map<String,Object>>();
//			map = new HashMap<String,Object>();
//			
//			String tableName = mfMsgVarTbResult.getVarTb();
//			String tablePk = mfMsgVarTbResult.getTbPk();
//			
//			mfMsgVarQuery = new MfMsgVar();
//			mfMsgVarQuery.setVarTb(tableName);
//			mfMsgVarQuery.setVarId(formateStr(listenerArgs,SEPERATE_STR));
//			//按表名、元素ID取所有的关联的元素
//			List<MfMsgVar> mfMsgVarList = mfMsgVarDao.getByTableName(mfMsgVarQuery);
//			
//			MfMsgUtil mfMsgUtil ;
//			//根据元素组装  mfMsgUtil
//			mfMfMsgUtilList = new ArrayList<MfMsgUtil>();
//			for(MfMsgVar mfMsgVarResult : mfMsgVarList){
//				mfMsgUtil = new MfMsgUtil();
//				mfMsgUtil.setVariableEnName(mfMsgVarResult.getVarCol());
//				mfMsgUtil.setVariableCnName("{"+mfMsgVarResult.getVarName()+"}");
//				mfMsgUtil.setVarType(mfMsgVarResult.getVarType());
//				mfMfMsgUtilList.add(mfMsgUtil);
//			}
//			
//			map.put("tableName", tableName);
//			map.put("tablePk", tablePk);
//			map.put("columnList", mfMfMsgUtilList);
//			relateList.add(map);
//			log.info("encapTableRelation  test");
//		}
//		
//		log.info("encapTableRelation  end");
//		return relateList;
//	}
//	
//	public Map<String,Object> getMainTableNameTablePk(String listenerArgs) throws Exception {
//		Map<String,Object> returnMap = new HashMap<String,Object>();
//		MfMsgVar query = new MfMsgVar();
//		
//		query.setVarId(this.formateStr(listenerArgs, SEPERATE_STR));
//		List<MfMsgVar> tbPkList = mfMsgVarDao.getTbPkByVarId(query);
//		
//		query = new MfMsgVar();
//		query.setVarId(this.formateStr(listenerArgs, SEPERATE_STR));
//		query.setVarType("02");
//		List<MfMsgVar> foreignPkList = mfMsgVarDao.getForeignPk(query);
//		
//		for(MfMsgVar mfMsgVar : tbPkList){
//			boolean flag = checkPkIsInForeignPkList(mfMsgVar.getTbPk(),foreignPkList);
//			if(flag == true){
//				returnMap.put("mainTableName", mfMsgVar.getVarTb());
//				returnMap.put("mainTablePKStr",mfMsgVar.getTbPk());
//				break;
//			}
//		}
//		return returnMap;
//	}
//		
//	public boolean checkPkIsInForeignPkList(String pkName,List<MfMsgVar> foreignPkList){
//		boolean flag = true;
//		if(foreignPkList.size() == 0){//模板表中只涉及到一张表,所以元素表中只配置了一张表,这张表不需要提供对外主键.
//			
//		}else{
//			for(MfMsgVar mfMsgVar : foreignPkList){
//				if(pkName.equals(mfMsgVar.getVarCol())){
//					flag = false;
//					break;
//				}
//			}
//		}
//		return flag;
//	}
//		
//	public Map<String,Object> setAttributeValue(Map<String,Object> map,String className,String attributeName,Object returnVal) throws Exception{
//		
//		if(checkAttribute(className, transColumnNameToAttributeName(attributeName)) == true){
//			String mainTablePKValue =  getAttributeValue(returnVal, transColumnNameToAttributeName(attributeName));
//			map.put("mainTablePKValue", mainTablePKValue);
//		}else{
//			map.put("mainTablePKValue", "");
//		}
//		return map;
//	}
//	
//	public List<MfMsgListener> getMfMsgListenerAll(MfMsgListener mfMsgListener) throws Exception {
//		return mfMsgListenerDao.getAll(mfMsgListener);
//	}
//	
//	public boolean compareTriggerValeToAttributeValue(String listenerTrigger,Object returnVal) throws Exception{
//		boolean flag = true;
//		String[] triggerArray = listenerTrigger.split(TRIGGER_SEPERATE_STR);
//		for(String str : triggerArray){
//			String attributeName = str.substring(0, str.indexOf(TRIGGER_EXPRESSION_STR));
//			String attributeConfigValue = str.substring(str.indexOf(TRIGGER_EXPRESSION_STR)+1);
//			String attributeDomainValue =  getAttributeValue(returnVal,attributeName);
//			if(attributeConfigValue.equals(attributeDomainValue)){//listener表中配置的trigger条件中,只要存在属性值与拦截的实体类的中属性值相等的,则结束循环
//				flag = true;
//				break;
//			}else{
//				flag = false;
//				//break;
//			}
//		}
//		return flag;
//	}
//}
