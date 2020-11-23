package app.component.sys.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.common.PasConstant;
import app.component.nmd.entity.ParmDic;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.feign.SysTaskInfoFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/sysLogin")
public class BwmTaskInfoController  extends BaseFormBean{
	@Autowired
	private SysTaskInfoFeign sysTaskInfoFeign;
	//全局变量
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	private String query;

	//表单变量
	private FormData formsys0001;
	private FormData formsys0002;
	private FormData form;
	private FormService formService = new FormService();
	
	public BwmTaskInfoController(){
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
		return "getListPage";
	}
	/**
	 * B面任务
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageToBAjax")
	@ResponseBody
	public Map<String, Object> getListPageToBAjax(int pageNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		CodeUtils cu =new CodeUtils();
		List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("PAS_SUB_TYPE");
		JSONObject json = new JSONObject();
		JSONArray dicArray = JSONArray.fromObject(pdList);
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		List<SysTaskInfo> stiList = sysTaskInfoFeign.getPasMinTypeCount(sysTaskInfo);
		SysTaskInfo maxCount = new SysTaskInfo();
		maxCount.setPasMaxNo(PasConstant.PAS_BIG_TYPE_MESSAGE);
		maxCount = sysTaskInfoFeign.getPasMaxCount(maxCount);
		maxCount.setPasAware(PasConstant.PAS_AWARE_YES);
		String pasAwareCount = sysTaskInfoFeign.getPasAwareCount(maxCount);
		for(int i=0;i<dicArray.size();i++){
			dicArray.getJSONObject(i).put("id", dicArray.getJSONObject(i).getString("optCode"));
			dicArray.getJSONObject(i).put("name", dicArray.getJSONObject(i).getString("optName"));
			dicArray.getJSONObject(i).put("pId", "0");
			dicArray.getJSONObject(i).put("open",true);
			dicArray.getJSONObject(i).put("count","0");
			for (SysTaskInfo sti : stiList) {
				if(sti.getPasMinNo().equals(dicArray.getJSONObject(i).getString("optCode"))){
					dicArray.getJSONObject(i).put("count",sti.getPasContent());
				}
			}
		}
		Ipage ipage = this.getIpage();
		ipage.setPageNo(pageNo);//异步传页面翻页参数
		//自定义查询Bo方法
		ipage.setParams(this.setIpageParams("sysTaskInfo", sysTaskInfo));
		ipage = sysTaskInfoFeign.findByPage(ipage);
		json.put("sysTaskInfoArray", JSONObject.fromObject(ipage));
		json.put("pasSubType", dicArray);
		json.put("maxCount", maxCount);
		json.put("pasAwareCount", pasAwareCount);
		json.put("SysDate", User.getSysDate(request));
		dataMap.put("ajaxData", json);
		return dataMap;
	}
	@RequestMapping(value = "/getFormHtmlAjax")
	@ResponseBody
	public Map<String, Object> getFormHtmlAjax(String formId,String query)throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		//formId = "vou0007";
	//	form = formService.getFormData(formId);
		JsonFormUtil jfu = new JsonFormUtil();
		String  formHtml = jfu.getJsonStr(formId,"bigFormTag",query);
		dataMap.put("formHtml",formHtml);
		dataMap.put("flag", "success");
		return dataMap;
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		try {
//			sysTaskInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
//			sysTaskInfo.setCriteriaList(sysTaskInfo, ajaxData);//我的筛选
			//this.getRoleConditions(sysTaskInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysTaskInfo", sysTaskInfo));
			ipage = sysTaskInfoFeign.findByPage(ipage);
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
	@RequestMapping(value = "/findByPageToBAjax")
	@ResponseBody
	public Map<String, Object> findByPageToBAjax(String jsonData,int pageNo,String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		try {
			List<SysTaskInfo> stiList = sysTaskInfoFeign.getPasMinTypeCount(sysTaskInfo);
			SysTaskInfo maxCount = new SysTaskInfo();
			maxCount.setPasMaxNo(PasConstant.PAS_BIG_TYPE_MESSAGE);
			maxCount = sysTaskInfoFeign.getPasMaxCount(maxCount);
			maxCount.setPasAware(PasConstant.PAS_AWARE_YES);
			String pasAwareCount = sysTaskInfoFeign.getPasAwareCount(maxCount);
			if(jsonData!=null){
				JSONObject js = JSONObject.fromObject(jsonData);// 根据字符串转换对象
				pageNo = js.getInt("pageNo");
				sysTaskInfo = (SysTaskInfo) JSONObject.toBean(js, sysTaskInfo.getClass()); // 把值绑定成相应的值对象
			}
			if(ajaxData!=null){
				sysTaskInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			}
			//sysTaskInfo.setCriteriaList(sysTaskInfo, ajaxData);//我的筛选
			//this.getRoleConditions(sysTaskInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysTaskInfo", sysTaskInfo));
			ipage = sysTaskInfoFeign.findByPage(ipage);
			dataMap.put("ipage",ipage);
			dataMap.put("list",stiList);
			dataMap.put("maxCount",maxCount);
			dataMap.put("pasAwareCount",pasAwareCount);
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
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response); 
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			formsys0002 = formService.getFormData("sys0002");
			getFormValue(formsys0002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys0002)){
				SysTaskInfo sysTaskInfo = new SysTaskInfo();
				setObjValue(formsys0002, sysTaskInfo);
				sysTaskInfoFeign.insert(sysTaskInfo);
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formsys0002 = formService.getFormData("sys0002");
		getFormValue(formsys0002, getMapByJson(ajaxData));
		SysTaskInfo sysTaskInfoJsp = new SysTaskInfo();
		setObjValue(formsys0002, sysTaskInfoJsp);
		SysTaskInfo sysTaskInfo = sysTaskInfoFeign.getById(sysTaskInfoJsp);
		if(sysTaskInfo!=null){
			try{
				sysTaskInfo = (SysTaskInfo)EntityUtil.reflectionSetVal(sysTaskInfo, sysTaskInfoJsp, getMapByJson(ajaxData));
				sysTaskInfoFeign.update(sysTaskInfo);
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
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysTaskInfo	sysTaskInfo = new SysTaskInfo();
		try{
			formsys0002 = formService.getFormData("sys0002");
			getFormValue(formsys0002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys0002)){
				sysTaskInfo = new SysTaskInfo();
				setObjValue(formsys0002, sysTaskInfo);
				sysTaskInfoFeign.update(sysTaskInfo);
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
	public Map<String, Object> getByIdAjax(String pasNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		List<SysTaskInfo> stiList = sysTaskInfoFeign.getPasMinTypeCount(sysTaskInfo);
		SysTaskInfo maxCount = new SysTaskInfo();
		maxCount.setPasMaxNo(PasConstant.PAS_BIG_TYPE_MESSAGE);
		maxCount = sysTaskInfoFeign.getPasMaxCount(maxCount);
		maxCount.setPasAware(PasConstant.PAS_AWARE_YES);
		String pasAwareCount = sysTaskInfoFeign.getPasAwareCount(maxCount);
		sysTaskInfo.setPasNo(pasNo);
		sysTaskInfo = sysTaskInfoFeign.getById(sysTaskInfo);
//		getObjValue(formsys0002, sysTaskInfo,formData);
		if(sysTaskInfo!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("info", sysTaskInfo);
		dataMap.put("list", stiList);
		dataMap.put("maxCount", maxCount);
		dataMap.put("pasAwareCount", pasAwareCount);
		return dataMap;
	}
	@RequestMapping(value = "/updataMsgAjax")
	@ResponseBody
	public Map<String, Object> updataMsgAjax(String pasNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		List<SysTaskInfo> stiList = sysTaskInfoFeign.getPasMinTypeCount(sysTaskInfo);
		SysTaskInfo maxCount = new SysTaskInfo();
		maxCount.setPasMaxNo(PasConstant.PAS_BIG_TYPE_MESSAGE);
		maxCount = sysTaskInfoFeign.getPasMaxCount(maxCount);
		updateCompleteMessage(pasNo);
		sysTaskInfo = sysTaskInfoFeign.getById(sysTaskInfo);
		if(sysTaskInfo!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("info", sysTaskInfo);
		dataMap.put("list", stiList);
		dataMap.put("maxCount", maxCount);
		return dataMap;
	}
	@RequestMapping(value = "/updataPasAwareStsAjax")
	@ResponseBody
	public Map<String, Object> updataPasAwareStsAjax(String pasNo) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasNo(pasNo);
		sysTaskInfo = sysTaskInfoFeign.getById(sysTaskInfo);
		if(PasConstant.PAS_AWARE_YES.equals(sysTaskInfo.getPasAware())){
			sysTaskInfo.setPasAware(PasConstant.PAS_AWARE_NO);
		}else{
			sysTaskInfo.setPasAware(PasConstant.PAS_AWARE_YES);
		}
		sysTaskInfoFeign.update(sysTaskInfo);
		SysTaskInfo maxCount = new SysTaskInfo();
		maxCount.setPasAware(PasConstant.PAS_AWARE_YES);
		String pasAwareCount = sysTaskInfoFeign.getPasAwareCount(maxCount);
		if(sysTaskInfo!=null){
			dataMap.put("flag", "success");
			dataMap.put("sysTaskInfo", sysTaskInfo);
			dataMap.put("pasAwareCount", pasAwareCount);
		}else{
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String pasNo) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasNo(pasNo);
		try {
			sysTaskInfoFeign.delete(sysTaskInfo);
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
		formsys0002 = formService.getFormData("sys0002");
		model.addAttribute("formsys0002", formsys0002);
		return "input";
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
		 SysTaskInfo sysTaskInfo = new SysTaskInfo();
		 setObjValue(formsys0002, sysTaskInfo);
		 sysTaskInfoFeign.insert(sysTaskInfo);
		 getObjValue(formsys0002, sysTaskInfo);
		 this.addActionMessage(model,"保存成功");
		 Ipage ipage =this.getIpage();
		 ipage.setParams(this.setIpageParams("sysTaskInfo", sysTaskInfo));
		 List<SysTaskInfo> sysTaskInfoList = (List<SysTaskInfo>)sysTaskInfoFeign.findByPage(ipage).getResult();
		model.addAttribute("sysTaskInfoList", sysTaskInfoList);
		 return "insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String pasNo) throws Exception{
		ActionContext.initialize(request,response);
		 formsys0001 = formService.getFormData("sys0001");
		 getFormValue(formsys0001);
		 SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasNo(pasNo);
		 sysTaskInfo = sysTaskInfoFeign.getById(sysTaskInfo);
		 getObjValue(formsys0001, sysTaskInfo);
		 model.addAttribute("formsys0001", formsys0001);
		return "query";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String pasNo) throws Exception {
		ActionContext.initialize(request,response);
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasNo(pasNo);
		sysTaskInfoFeign.delete(sysTaskInfo);
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
	/**
	 * 查看消息修改状态
	 * @return
	 * @throws Exception
	 */
	public void updateCompleteMessage(String pasNo) throws Exception{
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setPasNo(pasNo);
		sysTaskInfo = sysTaskInfoFeign.getById(sysTaskInfo);
		sysTaskInfo.setPasSts(PasConstant.PAS_TASK_STS_1);
		sysTaskInfoFeign.update(sysTaskInfo);
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
	
	public FormData getForm() {
		return form;
	}

	public void setForm(FormData form) {
		this.form = form;
	}

	
	
}
