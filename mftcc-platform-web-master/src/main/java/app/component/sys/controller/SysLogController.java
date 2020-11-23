package  app.component.sys.controller;
import java.util.HashMap;
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
import app.component.sys.entity.SysLog;
import app.component.sys.feign.SysLogFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: SysLogAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Tue Mar 01 06:53:50 GMT 2016
 **/
@Controller
@RequestMapping(value ="/sysLog")
public class SysLogController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入sysLogFeign
	@Autowired  
	private SysLogFeign sysLogFeign;
	//全局变量
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	private String query;
	//异步参数
	//表单变量
	private FormData formsys9001;
	private FormData formsys9002;
	private FormService formService = new FormService();
	
	public SysLogController(){
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
		return "/component/sys/SysLog_List";
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
		SysLog sysLog = new SysLog();
		try {
			sysLog.setCustomQuery(ajaxData);//自定义查询参数赋值
			sysLog.setCriteriaList(sysLog, ajaxData);//我的筛选
			//this.getRoleConditions(sysLog,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysLog", sysLog));
			ipage = sysLogFeign.findByPage(ipage);
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
			formsys9002 = formService.getFormData("sys9002");
			getFormValue(formsys9002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys9002)){
				SysLog sysLog = new SysLog();
				setObjValue(formsys9002, sysLog);
				sysLogFeign.insert(sysLog);
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
		formsys9002 = formService.getFormData("sys9002");
		getFormValue(formsys9002, getMapByJson(ajaxData));
		SysLog sysLogJsp = new SysLog();
		setObjValue(formsys9002, sysLogJsp);
		SysLog sysLog = sysLogFeign.getById(sysLogJsp);
		if(sysLog!=null){
			try{
				sysLog = (SysLog)EntityUtil.reflectionSetVal(sysLog, sysLogJsp, getMapByJson(ajaxData));
				sysLogFeign.update(sysLog);
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
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysLog sysLog = new SysLog();
		try{
			formsys9002 = formService.getFormData("sys9002");
			getFormValue(formsys9002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys9002)){
				sysLog = new SysLog();
				setObjValue(formsys9002, sysLog);
				sysLogFeign.update(sysLog);
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
	public Map<String, Object> getByIdAjax(String logId,String opSeqn) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		formsys9002 = formService.getFormData("sys9002");
		SysLog sysLog = new SysLog();
		sysLog.setLogId(logId);
		sysLog.setOpSeqn(opSeqn);
		sysLog = sysLogFeign.getById(sysLog);
		getObjValue(formsys9002, sysLog,formData);
		if(sysLog!=null){
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
	public Map<String, Object> deleteAjax(String logId,String opSeqn) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		SysLog sysLog = new SysLog();
		sysLog.setLogId(logId);
		sysLog.setOpSeqn(opSeqn);
		try {
			sysLogFeign.delete(sysLog);
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
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		formsys9002 = formService.getFormData("sys9002");
		model.addAttribute("formsys9002", formsys9002);
		return "/component/sys/SysLog_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		 formsys9002 = formService.getFormData("sys9002");
		 getFormValue(formsys9002);
		 SysLog sysLog = new SysLog();
		 setObjValue(formsys9002, sysLog);
		 sysLogFeign.insert(sysLog);
		 getObjValue(formsys9002, sysLog);
		 this.addActionMessage(model,"保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("sysLog", sysLog));
		 List<SysLog> sysLogList = (List<SysLog>)sysLogFeign.findByPage(ipage).getResult();
		 model.addAttribute("formsys9002", formsys9002);
		 model.addAttribute("sysLogList", sysLogList);
		 return "/component/sys/SysLog_Detail";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String logId,String opSeqn) throws Exception{
		ActionContext.initialize(request,response);
		 formsys9001 = formService.getFormData("sys9001");
		 getFormValue(formsys9001);
		 SysLog sysLog = new SysLog();
		sysLog.setLogId(logId);
		sysLog.setOpSeqn(opSeqn);
		 sysLog = sysLogFeign.getById(sysLog);
		 getObjValue(formsys9001, sysLog);
		 model.addAttribute("formsys9001", formsys9001);
		return "/component/sys/SysLog_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String logId,String opSeqn) throws Exception {
		ActionContext.initialize(request,response);
		SysLog sysLog = new SysLog();
		sysLog.setLogId(logId);
		sysLog.setOpSeqn(opSeqn);
		sysLogFeign.delete(sysLog);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys9002 = formService.getFormData("sys9002");
		 getFormValue(formsys9002);
		 boolean validateFlag = this.validateFormData(formsys9002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys9002 = formService.getFormData("sys9002");
		 getFormValue(formsys9002);
		 boolean validateFlag = this.validateFormData(formsys9002);
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	public FormData getFormsys9002() {
		return formsys9002;
	}
	public void setFormsys9002(FormData formsys9002) {
		this.formsys9002 = formsys9002;
	}
	public FormData getFormsys9001() {
		return formsys9001;
	}
	public void setFormsys9001(FormData formsys9001) {
		this.formsys9001 = formsys9001;
	}
	
}
