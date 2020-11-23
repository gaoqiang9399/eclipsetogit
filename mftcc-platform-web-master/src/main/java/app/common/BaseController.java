package app.common;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.base.BaseDomain;
import app.util.CodeUtils;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject; 
/**
 * 用于向页面返回信息
 * @author Administrator
 *
 */
public class BaseController{
	public void addActionError(Model model,String message){
		JSONArray errorMsgs = new JSONArray();
		model.asMap();
		for (Object key:model.asMap().keySet()) {
			if("errorMsgs".equals(key)) {
				errorMsgs = (JSONArray)model.asMap().get(key);
			}
		}
		errorMsgs.add(message);
		model.addAttribute("errorMsgs", errorMsgs);
	}
	
	public void addActionMessage(Model model,String message){
		JSONArray cueMsgs = new JSONArray();
		model.asMap();
		for (Object key:model.asMap().keySet()) {
			if("cueMsgs".equals(key)) {
				cueMsgs = (JSONArray)model.asMap().get(key);
			}
		}
		cueMsgs.add(message);
		model.addAttribute("cueMsgs", cueMsgs);
	}
	
	public void addRedictMessage(RedirectAttributes model,String message){
		JSONArray errorMsgs = new JSONArray();
		model.asMap();
		for (Object key:model.asMap().keySet()) {
			if("errorMsgs".equals(key)) {
				errorMsgs = (JSONArray)model.asMap().get(key);
			}
		}
		errorMsgs.add(message);
		model.addAttribute("errorMsgs", errorMsgs);
	}
	
	
	//protected Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 331256994500361617L;
	
	private String ifBizManger;
	private JSONArray jsonArray; 
	private String message;
	private Ipage ipage;
	//当前页码
	private String eadisPage;
	//页码跳转
	private String flag;
	@Autowired  
	private HttpServletRequest httpRequest;
	@Autowired  
	private HttpServletResponse httpResponse;
	//private Map<String,Object> session;
	//跳转的url
	private String url;
	//工作项ID
	private String workitemId;
	//流程实例ID
	private String processId;
	//业务流程任务ID
	private String segId;
	//业务流程环节ID
	private String parmId;
	
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public HttpServletResponse getHttpResponse() {
		return httpResponse;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		this.httpResponse=arg0;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.httpRequest=arg0;
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Ipage getIpage() {
		if(ipage==null){
			ipage=new Ipage();
		}
		if(pageSize==0){
			pageSize = 15;
		}
		if(pageSize<0){
			pageSize = 15;
		}
		ipage.setPageSize(pageSize);
		if(StringUtils.isBlank(this.getEadisPage())){
			//设置当前页码为1
			ipage.setPageNo(1);
		}else if("1".equals(this.getFlag())){
			//设置当前页码为1
			ipage.setPageNo(1);
		}else{
			//设置当前页码为页面选定值
			ipage.setPageNo(Integer.valueOf(this.getEadisPage()));
        }
		return ipage;
	}
	
	protected void toJson(){
		int ps=ipage.getPageSum();
		int pc=ipage.getPageCounts();
		int cp=ipage.getPageNo();
		int psi=ipage.getPageSize();
		List ll=(List)ipage.getResult();
		if(null==ll){
			ll=new ArrayList();
		}
		jsonArray = JSONArray.fromObject(ll);
	}
	/**
	 * 通过Json串转成Map
	 * @Title: getMapByJson
	 * @param @param ajaxData
	 * @return Map    返回类型
	 * @throws
	 */
	public Map<String, Object> getMapByJson(String ajaxData){
		Map<String, Object> map = new HashMap<String,Object>();
		JSONArray ja = JSONArray.fromObject(ajaxData);
		//int i =0 ;
		for(Object obj:ja){
			JSONObject jb = JSONObject.fromObject(obj);
			String key = jb.getString("name");
			String value = jb.getString("value");
			if(!"null".equals(map.get(key))&&map.get(key)!=null){
				String []array = {};
				if(map.get(key) instanceof String[]){
					array = (String [])ArrayUtils.add((String[])map.get(key),value);
				}else{
					array = (String [])ArrayUtils.add(array,map.get(key));
					array = (String [])ArrayUtils.add(array,value);
				}
				map.put(key, array);	
			}else{
				map.put(key, value);
			}
		}
		return map;
	}
	
	//多角色in查询
	public String[] seachForIn(String s){
		String [] arrs = s.split("\\|");
		return arrs;
	}

	public void setIpage(Ipage ipage) {
		this.ipage = ipage;
	}

	public String getEadisPage() {
		return eadisPage;
	}

	public void setEadisPage(String eadisPage) {
		 try{
	        	this.eadisPage =  Integer.parseInt(eadisPage)+"";
	        } catch (Exception e) {
	        	this.eadisPage =  "1";
	        }
		
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public String getWorkitemId() {
		return workitemId;
	}

	public void setWorkitemId(String workitemId) {
		this.workitemId = workitemId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getSegId() {
		return segId;
	}

	public void setSegId(String segId) {
		this.segId = segId;
	}

	public String getParmId() {
		return parmId;
	}

	public void setParmId(String parmId) {
		this.parmId = parmId;
	}

	private int pageSize;


	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSizeStr) {
        try{
        	this.pageSize =  Integer.parseInt(pageSizeStr);
        } catch (Exception e) {
        	this.pageSize =  15;
        }
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	
	public void getRoleConditions(BaseDomain baseDoMain,String funNo) throws Exception{
		HttpSession session = httpRequest.getSession();
		HashMap<String,Map<String,String>> funNoTypes = (HashMap<String,Map<String,String>>)session.getAttribute("funNoType");
		try {
			if(funNoTypes!=null){
				Map<String,String> rangMap = funNoTypes.get(funNo);
				String funNoType =rangMap.get("funRoleType");
				if(funNoType!=null){
					/*if(null==baseDoMain.getCriteriaLists()){
						List<List<Criteria>> cls = new ArrayList<List<Criteria>>();
						baseDoMain.setCriteriaLists(cls);
					}
					 String opNo = User.getRegNo(this.httpRequest);
					 String orgNo = User.getOrgNo(this.httpRequest);
					 List<Criteria> criteriaList = new ArrayList<Criteria>();
					 Criteria criteria = new Criteria();
					 criteria.setRoleValue(true);
					 if("1".equals(funNoType)){//登记人
						 if(checkMapString(rangMap,"pmsSts")&&rangMap.get("pmsSts").equals("1")&&checkMapString(rangMap,"pmsField")){
							 criteria.setCondition(rangMap.get("pmsField")+" = ");
						 }else{
							 criteria.setCondition("reg_no = ");
						 }
						 criteria.setValue(opNo);
					 }else if("2".equals(funNoType)){//本机构 为什么用IN，还查询了用户表
						 if(checkMapString(rangMap,"pmsSts")&&rangMap.get("pmsSts").equals("1")&&checkMapString(rangMap,"pmsField")){
							 criteria.setCondition(rangMap.get("pmsField")+" = ");
						 }else{
							 criteria.setCondition("org_no = ");
						 }
						 criteria.setValue(orgNo);
					 }else if("3".equals(funNoType)){//本机及其向下
						 if(checkMapString(rangMap,"pmsSts")&&rangMap.get("pmsSts").equals("1")&&checkMapString(rangMap,"pmsField")){
							 criteria.setCondition("FIND_IN_SET("+rangMap.get("pmsField"));
						 }else{
							 criteria.setCondition("FIND_IN_SET(org_no");
						 }
						 //这个地方可以改成查询机构表
						 criteria.setValue(",getChildList((select br_no from sys_user where op_no = '"+opNo+"')))");
					 }else if("4".equals(funNoType)){//客户经理
						 if(checkMapString(rangMap,"pmsSts")&&rangMap.get("pmsSts").equals("1")&&checkMapString(rangMap,"pmsField")){
							 criteria.setCondition(rangMap.get("pmsField")+" = ");
						 }else{
							 criteria.setCondition("manage_no = ");
						 }
						 criteria.setValue(opNo);
					 }else if("5".equals(funNoType)){//上级机构及其向下
						 if(checkMapString(rangMap,"pmsSts")&&rangMap.get("pmsSts").equals("1")&&checkMapString(rangMap,"pmsField")){
							 criteria.setCondition("FIND_IN_SET("+rangMap.get("pmsField"));
						 }else{
							 criteria.setCondition("FIND_IN_SET(org_no");
						 }
						 criteria.setValue(",getChildList((select up_one from sys_org where br_no = '"+orgNo+"')))");
					 }else if("6".equals(funNoType)){//客户经理与协办人
						 criteria.setCondition("(manage_no = '"+opNo+"' or co_manage_no='"+opNo+"')");
						 criteria.setValue("");
					 }else if("7".equals(funNoType)){//客户下的本机构
						 criteria.setCondition("(manage_no = '"+opNo+"' or co_manage_no='"+opNo+"')");
						 criteria.setValue("");
					 }else if("8".equals(funNoType)){//本机构（客户）
						 criteria.setCondition("(manage_org_no = '"+orgNo+"')");
						 criteria.setValue("");
					 }else if("10".equals(funNoType)){//本区域(业务)
						 String localDomain = orgNo.substring(0, 3);
						 criteria.setCondition("(manage_org_no like '"+localDomain+"%' or co_manage_org_no like '"+localDomain+"%')");
						 criteria.setValue("");
					 }else if("11".equals(funNoType)){//本区域(客户)
						 String localDomain = orgNo.substring(0, 3);
						 criteria.setCondition("(manage_org_no like '"+localDomain+"%')");
						 criteria.setValue("");
					 }else if("12".equals(funNoType)){//本机构（业务）
						 criteria.setCondition("(manage_org_no = '"+orgNo+"' or co_manage_org_no='"+orgNo+"')");
						 criteria.setValue("");
					 }else if("13".equals(funNoType)){//本区域
						 String localDomain = orgNo.substring(0, 3);
						 criteria.setCondition("(org_no like '"+localDomain+"%')");
						 criteria.setValue("");
					 }
					 else{
						 //可以查询全部 不设置过滤条件
						 //criteria.setRoleValue(false);
						 criteria.setCondition("1 = ");
						 criteria.setValue("1");
					 }
					criteriaList.add(criteria);
					baseDoMain.getCriteriaLists().add(criteriaList);*/
				}else{
					throw new Exception("请检查该用户是否配置了数据权限！");
				}
			}else{
				throw new Exception("请检查该用户是否配置了数据权限！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public static boolean checkMapString(Map<String,String> map,String key){
		if(map.containsKey(key)&&map.get(key)!=null&&!"".equals(map.get(key))){
			return true;
		}
		return false;
	}
	
	public String message(String key) throws Exception{
		if(key!=null&&!"".equals(key)){
			return CodeUtils.getMsgConfigByKey(key);
		}else{
			return "";
		}
	}
	
	public String message(String key, Map<String,String> map) throws Exception{
		String msg = "";
		if(key!=null&&!"".equals(key)){
			try {
				msg = CodeUtils.getMsgConfigByKey(key);
				String tmpMsg = msg;
				String regexs[] = {"\\$\\{\\w*\\}","\\#\\{\\w*\\}","\\#\\w*\\#"};
				for (String regex : regexs) {
					Pattern pat = Pattern.compile(regex);  
					Matcher matcher = pat.matcher(tmpMsg);     
					while (matcher.find()) { 
						String tempKey = tmpMsg.substring(matcher.start(),matcher.end());
						tempKey = tempKey.substring(2,tempKey.length()-1);
						String tempMsg = map.get(tempKey);
						if(tempMsg==null){
							tempMsg = "";
						}
						msg = msg.replaceAll(tempKey,tempMsg);
					}
				}
			} catch (Exception e) {
				msg = "未找到（编号："+key+"）相关的配置提示";
			}
		}
		return msg;
	}
	public String messageForBean(String key, Object obj) throws Exception{
		String msg = "";
		Map map = beanToMap(obj);
		if(key!=null&&!"".equals(key)){
			try {
				msg = CodeUtils.getMsgConfigByKey(key);
				String regexs[] = {"\\$\\{\\w*\\}","\\#\\{\\w*\\}","\\#\\w*\\#"};
				for (String regex : regexs) {
					Pattern pat = Pattern.compile(regex);  
					Matcher matcher = pat.matcher(msg);     
					while (matcher.find()) { 
						String tempKey = msg.substring(matcher.start(),matcher.end());
						tempKey = tempKey.substring(2,tempKey.length()-1);
						String tempMsg = (String) map.get(tempKey);
						if(tempMsg==null){
							tempMsg = "";
						}
						msg = msg.replaceAll(tempKey,tempMsg);
					}
				}
			} catch (Exception e) {
				msg = "未找到（编号："+key+"）相关的配置提示";
			}
		}
		return msg;
	}
	
	//将javabean实体类转为map类型，然后返回一个map类型的值
    public static Map<String, Object> beanToMap(Object obj) { 
            Map<String, Object> params = new HashMap<String, Object>(0); 
            try { 
                PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
                PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
                for (int i = 0; i < descriptors.length; i++) { 
                    String name = descriptors[i].getName(); 
                    if (!"class".equals(name)) {
                        params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                    } 
                } 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
            return params; 
    }
	
	public String formatDouble(double s){
	     DecimalFormat fmt = new DecimalFormat("#,##0.00");
	     fmt.setMaximumFractionDigits(2);
	     return fmt.format(s);
	}
	
	protected void outPrintForWX(String message){
		try {
			System.out.println(message);
			this.getHttpResponse().setCharacterEncoding("UTF-8");  
			this.getHttpResponse().getWriter().print(message);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public String getIfBizManger() {
		return ifBizManger;
	}

	public void setIfBizManger(String ifBizManger) {
		this.ifBizManger = ifBizManger;
	}
	/**
	 * 设置 Ipage参数
	 * @param objs
	 * param1 key
	 * param2 Object
	 * param3 paramsMap
	 * @return
	 */
	public Map<String,Object> setIpageParams(Object ...objs) {
		Map<String,Object> paramsMap =  new HashMap<String,Object>();
		if(objs.length==2) {
			String key = (String)objs[0];
			Object obj = objs[1];
			paramsMap.put(key, obj);
		}else {
			String key = (String)objs[0];
			Object obj = objs[1];
			paramsMap = (Map<String,Object>)objs[2];
			paramsMap.put(key, obj);
		}
		return paramsMap;
	}
}
