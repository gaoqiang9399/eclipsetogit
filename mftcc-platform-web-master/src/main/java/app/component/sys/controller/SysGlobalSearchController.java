package app.component.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.base.SpringUtil;
import app.base.User;
import app.component.nmd.entity.ParmDic;
import app.component.sys.entity.SysGlobalSearch;
import app.component.sys.feign.SysGlobalSearchFeign;
import app.util.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/sysGlobalSearch")
public class SysGlobalSearchController extends BaseFormBean{
	private static final long serialVersionUID = 3036492860675624542L;
	@Autowired
	private SysGlobalSearchFeign sysGlobalSearchFeign;
	private JSONArray jsonArray;
	//全局变量
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	//表单变量
	private FormData formsys9901;
	private FormData formsys9999;
	private FormService formService = new FormService();
	private List<Object> result;//返回数据
	private String query; // 查询的的参数
	
	public SysGlobalSearchController(){
		query = "";
	}
	
	/***
	 * 判断使用h5-storage 还是 redis
	 * 如果是redis，则加载parmDics，tableParmList，sysGlobalTypes到前台界面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkCache")
	@ResponseBody
	public Map<String,Object> checkCache() throws Exception{
		Map<String,Object> sysGlobalTypes = new HashMap<String,Object>();
		CodeUtils CodeUtils = new CodeUtils();
//		CodeUtils CodeUtils = SpringUtil.getBean("codeUtil",CodeUtils.class);
		Map<String,Object> parmDics = new HashMap<String,Object>();

		List<String> tableParmList = new ArrayList<String>();
		Map<String,Object> internalParmDics = new HashMap<String,Object>();
		ActionContext.initialize(request, response);
		//处理数据字典项
		for(SysGlobalSearch sysGlobalSearch:getTable()){
			List<String> sysGlobalTypeList = new ArrayList<String>();
			String url = sysGlobalSearch.getUrl();
			String belong = sysGlobalSearch.getBelong();//归属
			String tableColState = sysGlobalSearch.getTableColState();
			String tableColumns = sysGlobalSearch.getTableColumns();
			sysGlobalTypes.put(belong.split("-")[0], sysGlobalTypeList);
			//获得数据字典项字段
			String params = sysGlobalSearch.getParams();
			
			List<Object> parmDiclist = null;
			if(params!=null&&!"".equals(params)){
				for(String param:params.split(";")){
					if(param.indexOf("-")!=-1&&!tableParmList.contains(param)){
						tableParmList.add(belong.split("-")[0]+":"+param);
						String colName = param.split("-")[0];
						String keyName = param.split("-")[1];
						parmDiclist = (List<Object>)CodeUtils.getCacheByKeyName(keyName);
						parmDics.put(keyName, parmDiclist);
						internalParmDics.put(colName, parmDiclist);
					}
				}
			}
			//处理前台显示列
			for(int i=0;i<tableColumns.split(",").length;i++){
				String key = tableColumns.split(",")[i];
				String value = tableColState.split(",")[i];
				sysGlobalTypeList.add(key+"-"+value);
			}
		}
		Map<String,Object> dataMap = new HashMap<String,Object>();
		String value = "storage";
		dataMap.put("result", value);
		dataMap.put("parmDics", parmDics);
		dataMap.put("tableParmList",tableParmList);
		dataMap.put("sysGlobalType", sysGlobalTypes);
		dataMap.put("flag", "success");
		return dataMap;
	} 
	@RequestMapping(value = "/globalSearch")
	@ResponseBody
	//h5 storage
	public Map<String,Object> globalSearch() throws Exception{
		//Long time1 = System.currentTimeMillis();
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Object> ajaxList = new ArrayList<Object>();
		List<String> tableParmList = new ArrayList<String>();
		Map<String,Object> sysGlobalTypes = new HashMap<String,Object>();
		
		CodeUtils CodeUtils = new CodeUtils();
		Map<String,Object> parmDics = new HashMap<String,Object>();
		Map<String,Object> internalParmDics = new HashMap<String,Object>();
		for(SysGlobalSearch sysGlobalSearch:getTable()){
			List<String> sysGlobalTypeList = new ArrayList<String>();
			String url = sysGlobalSearch.getUrl();
			String belong = sysGlobalSearch.getBelong();//归属
			String tableColState = sysGlobalSearch.getTableColState();
			String tableColumns = sysGlobalSearch.getTableColumns();
			sysGlobalTypes.put(belong.split("-")[0], sysGlobalTypeList);
			//获得数据字典项字段
			String params = sysGlobalSearch.getParams();
			
			List<Object> parmDiclist = null;
			if(params!=null&&!"".equals(params)){
				for(String param:params.split(";")){
					if(param.indexOf("-")!=-1&&!tableParmList.contains(param)){
						tableParmList.add(belong.split("-")[0]+":"+param);
						String colName = param.split("-")[0];
						String keyName = param.split("-")[1];
						parmDiclist = (List<Object>)CodeUtils.getCacheByKeyName(keyName);
						parmDics.put(keyName, parmDiclist);
						internalParmDics.put(colName, parmDiclist);
					}
				}
			}
			//处理前台显示列
			for(int i=0;i<tableColumns.split(",").length;i++){
				String key = tableColumns.split(",")[i];
				String value = tableColState.split(",")[i];
				sysGlobalTypeList.add(key+"-"+value);
			}
			sysGlobalSearch.setVals("");
			List<Object> list =  sysGlobalSearchFeign.globalSearch(sysGlobalSearch);
			//System.out.println(System.currentTimeMillis()-time1);
			if(list!=null){
				for(Object obj:list){
					Map<String,Object> map = new HashMap<String,Object>();
					JSONObject jb = new JSONObject();
					try{
						JSONObject jbs = JSONObject.fromObject(obj);
						for(Object key:jbs.keySet()){
							if(!"".equals(jbs.get(key).toString())&&!"null".equals(jbs.get(key).toString())){
								jb.put(String.valueOf(key).toLowerCase(), jbs.get(key));
							}
						}
					}catch(Exception e){
						System.out.println("查询字段为空的不可转换");
						e.printStackTrace();
					}
					int i = 0;
					StringBuffer values = new StringBuffer();
					//拼接全局 全部显示的内容
					for(String col:sysGlobalSearch.getTableColumns().split(",")){
						Object value = jb.get(col);
						List<Object> parmList = (List)internalParmDics.get(col);
						if(parmList!=null&&parmList.size()>0){
							value = getKeyNameForValue(parmList, value);
						}
						if(i==0){
							values.append(value+"("+tableColState.split(",")[i]+")");
						}else{
							values.append(","+value+"("+tableColState.split(",")[i]+")");
						}
						i++;
					}
					map.put("label", values.toString());
					map.put("url", getUrlParameter(url,jsonObjectForMap(jb)));
					map.put("type", belong.split("-")[0]);
					map.put("hex", belong.split("-")[1]);
					map.put("entity", jb);
					ajaxList.add(map);
				}
			}
		}
		//String []keyNames = {"LEASE_TYPE","ID_TYPE","LEASE_MOLD","CUS_CORP_FORMAL","CUS_PERS_FORMAL","APP_STS"};
		
		dataMap.put("list", ajaxList);
		dataMap.put("parmDics", parmDics);
		dataMap.put("tableParmList",tableParmList);
		dataMap.put("sysGlobalType",sysGlobalTypes);
		dataMap.put("flag", "success");
		//System.out.println(System.currentTimeMillis()-time1);
		return dataMap;
	}
	
//	/***
//	 * 全局搜索redis版action
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/globalSearchByRedis")
//	@ResponseBody
//	public Map<String ,Object> globalSearchByRedis() throws Exception{
//		Map<String,Object> dataMap = new HashMap<String ,Object>();
//		ActionContext.initialize(request, response);
//		jsonArray = new JSONArray();
//		List<Object> lists = new ArrayList<>();
//		//query = new String(term.getBytes(UploadUtil.getEncoding(term)),"utf-8");
//		if(query.contains("-") || query.contains("+") || query.contains("*") || query.contains("/")) {
//			query = query.replaceAll("-", "%-");
//			query = query.replaceAll("\\+", "%+");
//			query = query.replaceAll("\\*", "%*");
//			query = query.replaceAll("\\/", "%/");
//		}
//		List<String> args = new ArrayList<>();
//		List<String> arg = Arrays.asList(query.split(" "));
//		boolean flag = true;
//		for(String a : arg) {
//			if(a.contains("(") && a.contains(")")) {
//				flag = false;
//			}
//		}
//		if(flag == false) {
//			for(String str : arg) {
//				//其他部门信息(部门)
//				if( str.indexOf("(") != -1 && str.indexOf(")") != -1) {
//					String value = str.substring(0,str.indexOf("("));
//					args.add(value);
//					continue;
//				}
//				//其他部门信息(部
//				else if(str.indexOf("(") != -1) {
//					String value = str.substring(0,str.indexOf("("));
//					args.add(value);
//					continue;
//				}
//				//其他部
//				else{
//					args.add(str);
//				}
//			}
//			arg = null;
//		}
//		Jedis jedis = RedisInit.newInstance();
//		List<String> Allkeys = java.util.Arrays.asList(jedis.get("Allkeys").split(","));
//		long startTime = new Date().getTime();
//		if(arg == null) {
//			result = (List<Object>) jedis.eval(getLuaScript(args), Allkeys,args);
//		}else {
//			result = (List<Object>) jedis.eval(getLuaScript(arg), Allkeys,arg);
//		}
//		for(int i=0;i<result.size();i++) {
//			List l = (List) result.get(i);
//			Map<String,Object> list = new HashMap<String,Object>();
//			for (int j = 0; j < l.size(); j=j+2) {
//				String key = (String) l.get(j);
//				if(key.equals("search")) {
//					continue;
//				}
//				Object value = (String) l.get(j+1);
//				if(key.equals("entity")) {
//					JSONObject entity = JSONObject.fromObject(value);
//					JSONObject jb = new JSONObject();
//					for (Object key1 : entity.keySet()) {
//						if (!"".equals(entity.get(key1).toString()) && !"null".equals(entity.get(key1).toString())) {
//							jb.put(String.valueOf(key1).toLowerCase(), entity.get(key1));
//						}
//					}
//				}
//				list.put(key,value);
//			}
//			lists.add(list);
//		}
//		long endTime = new Date().getTime();
//		System.out.println(endTime - startTime);
//		jsonArray = JSONArray.fromObject(lists);
//		dataMap.put("jsonArray", jsonArray);
//		dataMap.put("result", result);
//		return dataMap;
//	}
//	@RequestMapping(value = "/reloadRedis")
//	@ResponseBody
//	public Map<String,Object> reloadRedis() throws Exception{
//		ActionContext.initialize(request, response);
//		Map<String,Object> dataMap = new HashMap<String,Object>();
//		RedisInit ri = new RedisInit();
//		if (RedisInit.isInitRedis()) {
//			ri.initSearch();
//			dataMap.put("flag", "success");
//		}else {
//			dataMap.put("flag", "success");
//		}
//		return dataMap;
//	}
	
	
	/********************************************工具类**********************************************/
	
	// 多条件模糊查询的lua函数
	private static String getLuaScript(List<String> args) {
		String ARG = "string.find(temp2,'"+ args.get(0) +"',1) ~= nil";
		for (int i = 1; i < args.size(); i++) {
			ARG += " and string.find(temp2,'"+ args.get(i) +"',1) ~= nil";
		}
		System.out.println(ARG);
		String ss =  "local resultArr = {};"
				+ "local count = 0;"
				+ "for k,v in pairs(KEYS) do "
				+ "local temp = redis.call('hgetall',v);"
				+ "local temp2 = redis.call('hget',v,'search');"
				+ "if " + ARG + " "
				+ "then "
				+ "if count < 30 "
				+ "then "
				+ "count = count + 1;"
				+ "table.insert(resultArr,temp);"
				+ "else "
				+ "break;"
				+ "end; "
				+ "end; "
				+ "end;"
				+ "return resultArr;";
		return ss;
	}
	
	/**
	 * 获取数据字典项值
	 * @param list
	 * @param value
	 * @return
	 */
	public Object getKeyNameForValue(List<Object> list,Object value){
		if(value!=null&&list!=null&&list.size()>0){
			for (Object obj : list) {
				ParmDic parmDic = (ParmDic)obj;
				String optCode = parmDic.getOptCode();
				String optName = parmDic.getOptName();
				if(value.equals(optCode)){
					value = optName;
					break;
				}
			}
		}
		return value;
	}
	
	public  List<SysGlobalSearch> getTable() throws Exception{
		String manageNo = User.getRegNo(request);
		List<SysGlobalSearch> sysGlobalSearchList = new ArrayList<SysGlobalSearch>();
		sysGlobalSearchList = sysGlobalSearchFeign.getIsUseList();
		for (SysGlobalSearch sysGlobalSearch : sysGlobalSearchList) {
			String fixedQuery =  sysGlobalSearch.getFixedQuery();
			if(fixedQuery!=null&&fixedQuery.indexOf("#")!=-1){
				if(fixedQuery.indexOf("manageNo")!=-1){
					fixedQuery = fixedQuery.replaceAll("\\#\\{manageNo\\}", manageNo);
				}else{
					String regex = "\\#\\{\\w*\\}";
					Pattern pat = Pattern.compile(regex);  
				    Matcher matcher = pat.matcher(fixedQuery);     
				    while (matcher.find()) { 
				      String temp = fixedQuery.substring(matcher.start(),matcher.end());
				      fixedQuery = fixedQuery.replaceAll(temp,"");
				    }
				}
				sysGlobalSearch.setFixedQuery(fixedQuery);
			}
		}
		/*SysGlobalSearch sysGlobalSearch = null;
		//申请 
		String appProject = "appProject-申请";//申请
		sysGlobalSearch = new SysGlobalSearch();
		sysGlobalSearch.setTableName("app_project");//查询表
		String tableColumns = "cus_name,app_no,id_no";//前台显示字段
		String selectColumns =  "cus_name,app_no,id_no,lease_type,prod_name,total_amt,actual_amt,execute_rate,term_mon,app_sts,cus_Type,con_No";//查询字段
		String tableColState = "承租人,申请号,证件号";//显示字段对照
		sysGlobalSearch.setTableColumns(tableColumns);
		sysGlobalSearch.setTableColState(tableColState);
		sysGlobalSearch.setSelectColumns(selectColumns);
		//String values[] = {"张三","1"};
		//sysGlobalSearch.setVals(values);
		String url = "AppProjectAction_getAppViewPoint.action;appNo-appNo;cusType-cusType;conNo-conNo;vpNo-2";//conNo-conNo;
		//href="AppProjectAction_getAppViewPoint.action?appNo=20160603000613&conNo=20160603000613-1&cusType=2&vpNo=2"
		sysGlobalSearch.setUrl(url);
		sysGlobalSearch.setBelong(appProject);
		sysGlobalSearch.setFunNoSql(getRoleConditions(BizPubParm.PMS_DATA_RANG_NO));
		sysGlobalSearchList.add(sysGlobalSearch);
		
		//合同
		String appProjectCont = "appProjectCont-合同";//合同
		sysGlobalSearch = new SysGlobalSearch();
		sysGlobalSearch.setTableName("app_project_cont");
		String tableColumns1 = "cus_name,app_no,con_no";
		selectColumns = "cus_name,app_no,con_no";
		String tableColState1 = "承租人,申请号,合同号";
		sysGlobalSearch.setTableColumns(tableColumns1);
		sysGlobalSearch.setTableColState(tableColState1);
		sysGlobalSearch.setSelectColumns(selectColumns);
		url = "AppProjectContAction_getListPage.action;conNo-conNo;cusName-cusName";
		sysGlobalSearch.setUrl(url);
		sysGlobalSearch.setBelong(appProjectCont);
		sysGlobalSearch.setFunNoSql(getRoleConditions(BizPubParm.PMS_DATA_RANG_NO));
		sysGlobalSearchList.add(sysGlobalSearch);
		
		String cusManageRelPers = "cusManageRelPers-承租人";//个人客户
		sysGlobalSearch = new SysGlobalSearch();
		sysGlobalSearch.setTableName("cus_Manage_Rel");
		String tableColumns2 = "cus_name,cus_no,id_no";
		String tableColState2 = "客户名称,客户号,证件号";
		sysGlobalSearch.setTableColumns(tableColumns2);
		sysGlobalSearch.setTableColState(tableColState2);
		selectColumns = "cus_no,cus_name,id_no,cus_type,manage_no,id_type";
		sysGlobalSearch.setSelectColumns(selectColumns);
		url = "CusManageRelAction_getRenterViewPoint.action;cusNo-cusNo;cusName-cusName;cusType-cusType;manageNo-manageNo";
		sysGlobalSearch.setUrl(url);
		sysGlobalSearch.setBelong(cusManageRelPers);
		sysGlobalSearch.setFixedQuery("formal-like:P01,C01;manage_no-=:"+manageNo);//过滤条件列-条件类型：值
		//sysGlobalSearch.setFunNoSql(getRoleConditions(BizPubParm.PMS_DATA_RANG_NO));
		sysGlobalSearchList.add(sysGlobalSearch);
		
		String cusManageRelJxs = "cusManageRelJxs-经销商";//对公客户
		sysGlobalSearch = new SysGlobalSearch();
		sysGlobalSearch.setTableName("cus_Manage_Rel");
		String tableColumns3 = "cus_name,cus_no,id_type";
		String tableColState3 = "客户名称,客户号,证件类型";
		sysGlobalSearch.setTableColumns(tableColumns3);
		sysGlobalSearch.setTableColState(tableColState3);
		selectColumns = "cus_no,cus_name,id_no,cus_type,manage_no,id_type";
		sysGlobalSearch.setSelectColumns(selectColumns);
		url = "CusManageRelAction_getProxyViewPoint.action;cusNo-cusNo;cusName-cusName;cusType-cusType;manageNo-manageNo;vpNo-11";
		sysGlobalSearch.setUrl(url);
		sysGlobalSearch.setBelong(cusManageRelJxs);
		sysGlobalSearch.setFixedQuery("formal-like:C04;manage_no-=:"+manageNo);//过滤条件列-条件类型：值
		sysGlobalSearchList.add(sysGlobalSearch);
		
		String cusManageRelGhx = "cusManageRelGhx-供货商";//对公客户
		sysGlobalSearch = new SysGlobalSearch();
		sysGlobalSearch.setTableName("cus_Manage_Rel");
		String tableColumns13 = "cus_name,cus_no,id_type";
		String tableColState13 = "客户名称,客户号,证件类型";
		sysGlobalSearch.setTableColumns(tableColumns13);
		sysGlobalSearch.setTableColState(tableColState13);
		selectColumns = "cus_no,cus_name,id_no,cus_type,manage_no,id_type";
		sysGlobalSearch.setSelectColumns(selectColumns);
		url = "CusManageRelAction_getSupplierViewPoint.action;cusNo-cusNo;cusName-cusName;cusType-cusType;manageNo-manageNo;vpNo-12";
		sysGlobalSearch.setUrl(url);
		sysGlobalSearch.setBelong(cusManageRelGhx);
		sysGlobalSearch.setFixedQuery("formal-like:C03;manage_no-=:"+manageNo);//过滤条件列-条件类型：值
		//sysGlobalSearch.setFunNoSql(getRoleConditions(BizPubParm.PMS_DATA_RANG_NO));
		sysGlobalSearchList.add(sysGlobalSearch);
		
		String leaseItemInfo = "leaseItemInfo-租赁物";//租赁物
		sysGlobalSearch = new SysGlobalSearch();
		sysGlobalSearch.setTableName("lease_item_info");
		String tableColumns4 = "lease_name,lease_mold,model_no,manufacturers,price_amt";
		String tableColState4 = "租赁物名称,租赁物类型,型号,制造商,单价";
		sysGlobalSearch.setTableColumns(tableColumns4);
		sysGlobalSearch.setTableColState(tableColState4);
		selectColumns = "lease_no,lease_mold,lease_name,model_no,manufacturers,price_amt";
		sysGlobalSearch.setSelectColumns(selectColumns);
		url = "LeaseItemInfoAction_getLeaseViewPoint.action;leaseNo-leaseNo;vpNo-6";
		sysGlobalSearch.setUrl(url);
		sysGlobalSearch.setBelong(leaseItemInfo);
		sysGlobalSearch.setFunNoSql(getRoleConditions(BizPubParm.PMS_DATA_RANG_NO));
		sysGlobalSearchList.add(sysGlobalSearch);*/
		
		return sysGlobalSearchList;
	}
	
	public static String getUrlParameter(String url,Map<String,Object> map){
		StringBuffer newUrl = new StringBuffer();
		String strs[] = url.split(";");
		if(strs!=null&&strs.length>0){
			String action = strs[0];
			newUrl.append(action);
			boolean falg = true;
			for(int i=1;i<strs.length;i++){//搜索查询条件 以空格区分
				String str = strs[i];
				String paras[] = str.split("-");
				String val = "";
				if(paras!=null&&paras.length==2){
					if(falg){
						falg = false;
						newUrl.append("?ajaxData=");
					}else{
						newUrl.append(" ");
					}
					val = map.get(paras[0])!=null&&!"null".equals(map.get(paras[0]).toString())?map.get(paras[0]).toString():"";
					newUrl.append(val);
				}
			}
			for(int i=1;i<strs.length;i++){
				String str = strs[i];
				String paras[] = str.split("-");
				String val = "";
				if(paras!=null&&paras.length==2){
					if(falg){
						falg = false;
						newUrl.append("?");
					}else{
						newUrl.append("&");
					}
					val = map.get(paras[1])!=null?map.get(paras[1]).toString():paras[1];
					newUrl.append(paras[0]+"="+val);
				}
			}
			newUrl.append("&searchFlag=true");
		}
		return newUrl.toString();
	}
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,response);
		return "/component/sys/SysGlobalSearch_List";
	}
	/***
	 * 列表数据查询的异步方法
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysGlobalSearch sysGlobalSearch = new SysGlobalSearch();
		try {
			sysGlobalSearch.setCustomQuery(ajaxData);//自定义查询参数赋值
			sysGlobalSearch.setCriteriaList(sysGlobalSearch, ajaxData);//我的筛选
			//this.getRoleConditions(demo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("sysGlobalSearch", sysGlobalSearch));
			ipage = sysGlobalSearchFeign.findByPage(ipage);
			//返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,SysGlobalSearch sysGlobalSearch,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", sysGlobalSearchFeign.getAll(sysGlobalSearch), null,true);
		dataMap.put("tableData",tableHtml);
	}
	private void getTableData(List<SysGlobalSearch> list,String tableId,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"thirdTableTag", list, null,true);
		dataMap.put("tableData",tableHtml);
	}
	@RequestMapping(value = "/inputAjax")
	@ResponseBody
	public Map<String, Object> inputAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysGlobalSearch sysGlobalSearch = new SysGlobalSearch();	
		formsys9999 = formService.getFormData("sys9999");
		getObjValue(formsys9999,sysGlobalSearch);
		JsonFormUtil jfu = new JsonFormUtil();
		String  formHtml = jfu.getJsonStr(formsys9999,"bigFormTag",query);
		dataMap.put("formHtml",formHtml);
		return dataMap;
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			formsys9999 = formService.getFormData("sys9999");
			getFormValue(formsys9999, getMapByJson(ajaxData));
			if(this.validateFormData(formsys9999)){
				SysGlobalSearch sysGlobalSearch = new SysGlobalSearch();
				setObjValue(formsys9999, sysGlobalSearch);
				sysGlobalSearchFeign.insert(sysGlobalSearch);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysGlobalSearch sysGlobalSearch = new SysGlobalSearch();
		try{
			formsys9999 = formService.getFormData("sys9999");
			getFormValue(formsys9999, getMapByJson(ajaxData));
			if(this.validateFormData(formsys9999)){
				sysGlobalSearch = new SysGlobalSearch();
				setObjValue(formsys9999, sysGlobalSearch);
				sysGlobalSearchFeign.update(sysGlobalSearch);
				List<SysGlobalSearch> list = new ArrayList<SysGlobalSearch>();
				list.add(sysGlobalSearch);
				getTableData(list,tableId,dataMap);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStsAjax")
	@ResponseBody
	public Map<String,Object> updateStsAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			formsys9999 = formService.getFormData("sys9999");
			getFormValue(formsys9999, jobj);
			SysGlobalSearch sysGlobalSearch = new SysGlobalSearch();
			setObjValue(formsys9999, sysGlobalSearch);
			int count = sysGlobalSearchFeign.updateSts(sysGlobalSearch);
			if(count>0){
				sysGlobalSearch = sysGlobalSearchFeign.getById(sysGlobalSearch);
				List<SysGlobalSearch> list = new ArrayList<SysGlobalSearch>();
				list.add(sysGlobalSearch);
				getTableData(list,tableId,dataMap);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String,Object> getByIdAjax(String configNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		formsys9999 = formService.getFormData("sys9999");
		SysGlobalSearch sysGlobalSearch = new SysGlobalSearch();
		sysGlobalSearch.setConfigNo(configNo);
		sysGlobalSearch = sysGlobalSearchFeign.getById(sysGlobalSearch);
		getObjValue(formsys9999, sysGlobalSearch);
		if(sysGlobalSearch!=null){
			JsonFormUtil jfu = new JsonFormUtil();
			String  formHtml = jfu.getJsonStr(formsys9999,"bigFormTag",query);
			dataMap.put("formHtml",formHtml);
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String configNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysGlobalSearch sysGlobalSearch = new SysGlobalSearch();
		sysGlobalSearch.setConfigNo(configNo);
		try {
			sysGlobalSearchFeign.delete(sysGlobalSearch);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * jsonobject 转驼峰键值map
	 * @param jb
	 * @return
	 */
	public static Map<String,Object> jsonObjectForMap(JSONObject jb){
		Map<String,Object> map = new HashMap<String,Object>();
		if(jb!=null){
			for(Object key:jb.keySet()){
				map.put(columnToProperty(key.toString()), jb.get(key));
			}
		}
		return map;
	}
	/**
	 * 数据库字段转驼峰格式
	 * @param column
	 * @return
	 */
	public static String columnToProperty(String column) {
        StringBuilder result = new StringBuilder();
        if (column == null || column.isEmpty()) {
            return "";
        } else if (!column.contains("_")) {
            return column.substring(0, 1).toLowerCase() + column.substring(1);
        } else {
            String[] columns = column.split("_");
            for (String columnSplit : columns) {
                if (columnSplit.isEmpty()) {
                    continue;
                }
                if (result.length() == 0) {
                    result.append(columnSplit.toLowerCase());
                } else {
                    result.append(columnSplit.substring(0, 1).toUpperCase()).append(columnSplit.substring(1).toLowerCase());
                }
            }
            return result.toString();
        }
    }

	public FormData getFormsys9901() {
		return formsys9901;
	}

	public void setFormsys9901(FormData formsys9901) {
		this.formsys9901 = formsys9901;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}


	public FormData getFormsys9999() {
		return formsys9999;
	}

	public void setFormsys9999(FormData formsys9999) {
		this.formsys9999 = formsys9999;
	}

	public FormService getFormService() {
		return formService;
	}

	public void setFormService(FormService formService) {
		this.formService = formService;
	}
	


	public List<Object> getResult() {
		return result;
	}

	public void setResult(List<Object> result) {
		this.result = result;
	}

	@Override
	public JSONArray getJsonArray() {
		return jsonArray;
	}

	@Override
	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

}
