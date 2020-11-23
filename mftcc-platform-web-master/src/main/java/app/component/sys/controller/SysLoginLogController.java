package  app.component.sys.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.nmd.entity.ParmDic;
import app.component.sys.entity.SysLoginLog;
import app.component.sys.feign.SysLoginLogFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.CacheUtil;
import cn.mftcc.util.CacheUtil.CACHE_KEY;

/**
 * Title: SysLoginLogAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Thu Jul 28 12:41:21 GMT 2016
 **/
@Controller
@RequestMapping(value ="/sysLoginLog")
public class SysLoginLogController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入sysLoginLogFeign
	@Autowired
	private SysLoginLogFeign sysLoginLogFeign;
	//全局变量
	private String query;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	//表单变量
	private FormData formsys0001;
	private FormData formsys0002;
	private FormService formService = new FormService();
	
	public SysLoginLogController(){
		query = "";
	}
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,response);
		return "/component/sys/SysLoginLog_List";
	}
	/**
	 * 列表打开页面请求---查询在线人数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageOnLine")
	public String getListPageOnLine() throws Exception {
		ActionContext.initialize(request,response);
		return "/component/sys/SysLoginLog_OnLine";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysLoginLog sysLoginLog = new SysLoginLog();
		try {
			sysLoginLog.setCustomQuery(ajaxData);//自定义查询参数赋值
			sysLoginLog.setCriteriaList(sysLoginLog, ajaxData);//我的筛选
			//this.getRoleConditions(sysLoginLog,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysLoginLog", sysLoginLog));
			ipage = sysLoginLogFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/***
	 * 列表数据查询---在线人数查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageOnLineAjax")
	@ResponseBody
	public Map<String, Object> findByPageOnLineAjax(String ajaxData,int pageNo,String tableId,String tableType,String brNo) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysLoginLog sysLoginLog = new SysLoginLog();
		try {
			sysLoginLog.setCustomQuery(ajaxData);//自定义查询参数赋值
			sysLoginLog.setCriteriaList(sysLoginLog, ajaxData);//我的筛选
			//this.getRoleConditions(sysLoginLog,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
//			Map<String, String> onlineSet = LoginSessionListener.hUserName;
			Map<String, String> onlineSet = CacheUtil.getMapByKey(CACHE_KEY.hUserName);
			List<String> usernameList = new ArrayList<String>();
			for(String key:onlineSet.keySet()){
				usernameList.add(key);
			}
			Map<String,Object> valueMap = new HashMap<String,Object>();
			valueMap.put("brNo", brNo);
			valueMap.put("usernameList", usernameList);
			valueMap.put("customQuery", sysLoginLog.getCustomQuery());
			ipage.setParams(this.setIpageParams("valueMap", valueMap));
			ipage = sysLoginLogFeign.findByPageOnLine(ipage);
			List<SysLoginLog> sysLogList = (List<SysLoginLog>)ipage.getResult();
			CodeUtils cu = new CodeUtils();
			List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("ROLE_NO");
			for (Iterator iterator = sysLogList.iterator(); iterator.hasNext();) {
				SysLoginLog su = (SysLoginLog) iterator.next();
//				if(!"".equals(su.getWechatId())&&su.getWechatId()!=null){
//					su.setWxSts("1");
//				}else{
//					su.setWxSts("2");
//				}
				String role = su.getRoleNo();
				String[] roleArr = new String[0];
				if(role!=null){
					if(role.indexOf("|")>-1){
						roleArr = role.split("\\|");
					}else{
						roleArr=new String[]{role};
					}
				}
				for (int i = 0; i < roleArr.length; i++) {
					for (ParmDic pd : pdList) {
						if(roleArr[i].equals(pd.getOptCode())){
							roleArr[i] = pd.getOptName();
						}
					}
				}
				role="";
				List<String> list = new ArrayList<String>();
				for (int i=0; i<roleArr.length; i++) { 
					if (!roleArr[i].isEmpty()) {
						list.add(roleArr[i]);  
					}
		        }  
				String[] newRoleArr = list.toArray(new String[1]);
				for (int i = 0; i < newRoleArr.length; i++) {
					if(i==0){
						role+=newRoleArr[i];
					}else{
						role+="|"+newRoleArr[i];
					}
				}
				su.setRoleNo(role);
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX----T下线操作员
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/TOpNoAjax")
	@ResponseBody
	public Map<String, Object> TOpNoAjax(String opNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try {
			/*if(LoginSessionListener.isOnline(opNo)){
				LoginSessionListener.doRemove(opNo);//把第一个用户T下线
			}*/
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
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
			formsys0002 = formService.getFormData("sys0002");
			getFormValue(formsys0002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys0002)){
				SysLoginLog sysLoginLog = new SysLoginLog();
				setObjValue(formsys0002, sysLoginLog);
				sysLoginLogFeign.insert(sysLoginLog);
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
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		formsys0002 = formService.getFormData("sys0002");
		getFormValue(formsys0002, getMapByJson(ajaxData));
		SysLoginLog sysLoginLogJsp = new SysLoginLog();
		setObjValue(formsys0002, sysLoginLogJsp);
		SysLoginLog sysLoginLog = sysLoginLogFeign.getById(sysLoginLogJsp);
		if(sysLoginLog!=null){
			try{
				sysLoginLog = (SysLoginLog)EntityUtil.reflectionSetVal(sysLoginLog, sysLoginLogJsp, getMapByJson(ajaxData));
				sysLoginLogFeign.update(sysLoginLog);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysLoginLog sysLoginLog = new SysLoginLog();
		try{
			formsys0002 = formService.getFormData("sys0002");
			getFormValue(formsys0002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys0002)){
				sysLoginLog = new SysLoginLog();
				setObjValue(formsys0002, sysLoginLog);
				sysLoginLogFeign.update(sysLoginLog);
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
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String sessionId,String loginDate,String loginTime) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		formsys0002 = formService.getFormData("sys0002");
		SysLoginLog sysLoginLog = new SysLoginLog();
		sysLoginLog.setSessionId(sessionId);
		 sysLoginLog.setLoginDate(loginDate);
		 sysLoginLog.setLoginTime(loginTime);
		sysLoginLog = sysLoginLogFeign.getById(sysLoginLog);
		getObjValue(formsys0002, sysLoginLog,formData);
		if(sysLoginLog!=null){
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
	public Map<String, Object> deleteAjax(String sessionId,String loginDate,String loginTime) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysLoginLog sysLoginLog = new SysLoginLog();
		sysLoginLog.setSessionId(sessionId);
		 sysLoginLog.setLoginDate(loginDate);
		 sysLoginLog.setLoginTime(loginTime);
		try {
			sysLoginLogFeign.delete(sysLoginLog);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		formsys0002 = formService.getFormData("sys0002");
		model.addAttribute("formsys0002", formsys0002);
		return "/component/sys/SysLoginLog_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		 formsys0002 = formService.getFormData("sys0002");
		 getFormValue(formsys0002);
		 SysLoginLog sysLoginLog = new SysLoginLog();
		 setObjValue(formsys0002, sysLoginLog);
		 sysLoginLogFeign.insert(sysLoginLog);
		 getObjValue(formsys0002, sysLoginLog);
		 this.addActionMessage(model,"保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("sysLoginLog", sysLoginLog));
		 List<SysLoginLog> sysLoginLogList = (List<SysLoginLog>)sysLoginLogFeign.findByPage(ipage).getResult();
		model.addAttribute("formsys0002", formsys0002);
		model.addAttribute("sysLoginLogList", sysLoginLogList);
		 return "/component/sys/SysLoginLog_Detail";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(String sessionId,String loginDate,String loginTime,Model model) throws Exception{
		ActionContext.initialize(request,response);
		 formsys0001 = formService.getFormData("sys0001");
		 getFormValue(formsys0001);
		 SysLoginLog sysLoginLog = new SysLoginLog();
		 sysLoginLog.setSessionId(sessionId);
		 sysLoginLog.setLoginDate(loginDate);
		 sysLoginLog.setLoginTime(loginTime);
		 sysLoginLog = sysLoginLogFeign.getById(sysLoginLog);
		 getObjValue(formsys0001, sysLoginLog);
		 model.addAttribute("formsys0001", formsys0001);
		return "/component/sys/SysLoginLog_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String sessionId,String loginDate,String loginTime) throws Exception {
		ActionContext.initialize(request,response);
		SysLoginLog sysLoginLog = new SysLoginLog();
		sysLoginLog.setSessionId(sessionId);
		 sysLoginLog.setLoginDate(loginDate);
		 sysLoginLog.setLoginTime(loginTime);
		sysLoginLogFeign.delete(sysLoginLog);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys0002 = formService.getFormData("sys0002");
		 getFormValue(formsys0002);
		 boolean validateFlag = this.validateFormData(formsys0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys0002 = formService.getFormData("sys0002");
		 getFormValue(formsys0002);
		 boolean validateFlag = this.validateFormData(formsys0002);
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	public FormData getFormsys0002() {
		return formsys0002;
	}
	public void setFormsys0002(FormData formsys0002) {
		this.formsys0002 = formsys0002;
	}
	public FormData getFormsys0001() {
		return formsys0001;
	}
	public void setFormsys0001(FormData formsys0001) {
		this.formsys0001 = formsys0001;
	}
}
