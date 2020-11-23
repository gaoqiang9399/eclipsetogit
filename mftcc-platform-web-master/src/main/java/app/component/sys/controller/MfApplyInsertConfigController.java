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
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.sys.entity.MfApplyInsertConfig;
import app.component.sys.feign.MfApplyInsertConfigFeign;
import app.util.toolkit.Ipage;
@Controller
@RequestMapping("/mfApplyInsertConfig")
public class MfApplyInsertConfigController extends BaseFormBean{

	//注入mfApplyInsertConfigFeign
	@Autowired
	private MfApplyInsertConfigFeign mfApplyInsertConfigFeign;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;

	private String query;
	//表单变量
	private FormData formapplyinsertconfig0001;
	private FormData formapplyinsertconfig0002;
	private FormService formService = new FormService();
	
	public MfApplyInsertConfigController(){
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
		return "/component/sys/MfApplyInsertConfig_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(int pageNo,String tableId, String tableType,String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfApplyInsertConfig mfApplyInsertConfig = new MfApplyInsertConfig();
		try {
			mfApplyInsertConfig.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfApplyInsertConfig.setCriteriaList(mfApplyInsertConfig, ajaxData);//我的筛选
			//mfApplyInsertConfig.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfApplyInsertConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfApplyInsertConfig", mfApplyInsertConfig));
			ipage = mfApplyInsertConfigFeign.findByPage(ipage);
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
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			formapplyinsertconfig0002 = formService.getFormData("applyinsertconfig0002");
			getFormValue(formapplyinsertconfig0002, getMapByJson(ajaxData));
			if(this.validateFormData(formapplyinsertconfig0002)){
				MfApplyInsertConfig mfApplyInsertConfig = new MfApplyInsertConfig();
				setObjValue(formapplyinsertconfig0002, mfApplyInsertConfig);
				mfApplyInsertConfigFeign.insert(mfApplyInsertConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
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
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formapplyinsertconfig0002 = formService.getFormData("applyinsertconfig0002");
		getFormValue(formapplyinsertconfig0002, getMapByJson(ajaxData));
		MfApplyInsertConfig mfApplyInsertConfigJsp = new MfApplyInsertConfig();
		setObjValue(formapplyinsertconfig0002, mfApplyInsertConfigJsp);
		MfApplyInsertConfig mfApplyInsertConfig = mfApplyInsertConfigFeign.getById(mfApplyInsertConfigJsp);
		if(mfApplyInsertConfig!=null){
			try{
				mfApplyInsertConfig = (MfApplyInsertConfig)EntityUtil.reflectionSetVal(mfApplyInsertConfig, mfApplyInsertConfigJsp, getMapByJson(ajaxData));
				mfApplyInsertConfigFeign.update(mfApplyInsertConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
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
		MfApplyInsertConfig mfApplyInsertConfig = new MfApplyInsertConfig();
		try{
			formapplyinsertconfig0002 = formService.getFormData("applyinsertconfig0002");
			getFormValue(formapplyinsertconfig0002, getMapByJson(ajaxData));
			if(this.validateFormData(formapplyinsertconfig0002)){
				mfApplyInsertConfig = new MfApplyInsertConfig();
				setObjValue(formapplyinsertconfig0002, mfApplyInsertConfig);
				mfApplyInsertConfigFeign.update(mfApplyInsertConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
	public Map<String, Object> getByIdAjax(String configId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		formapplyinsertconfig0002 = formService.getFormData("applyinsertconfig0002");
		MfApplyInsertConfig mfApplyInsertConfig = new MfApplyInsertConfig();
		mfApplyInsertConfig.setConfigId(configId);
		mfApplyInsertConfig = mfApplyInsertConfigFeign.getById(mfApplyInsertConfig);
		getObjValue(formapplyinsertconfig0002, mfApplyInsertConfig,formData);
		if(mfApplyInsertConfig!=null){
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
	public Map<String, Object> deleteAjax(String configId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfApplyInsertConfig mfApplyInsertConfig = new MfApplyInsertConfig();
		mfApplyInsertConfig.setConfigId(configId);
		try {
			mfApplyInsertConfigFeign.delete(mfApplyInsertConfig);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
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
		formapplyinsertconfig0002 = formService.getFormData("applyinsertconfig0002");
		model.addAttribute("formapplyinsertconfig0002", formapplyinsertconfig0002);
		return "/component/sys/MfApplyInsertConfig_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		 formapplyinsertconfig0002 = formService.getFormData("applyinsertconfig0002");
		 getFormValue(formapplyinsertconfig0002);
		 MfApplyInsertConfig mfApplyInsertConfig = new MfApplyInsertConfig();
		 setObjValue(formapplyinsertconfig0002, mfApplyInsertConfig);
		 mfApplyInsertConfigFeign.insert(mfApplyInsertConfig);
		 getObjValue(formapplyinsertconfig0002, mfApplyInsertConfig);
		 this.addActionMessage(model,"保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfApplyInsertConfig", mfApplyInsertConfig));
		 List<MfApplyInsertConfig> mfApplyInsertConfigList = (List<MfApplyInsertConfig>)mfApplyInsertConfigFeign.findByPage(ipage).getResult();
		 model.addAttribute("formapplyinsertconfig0002", formapplyinsertconfig0002);
		 model.addAttribute("mfApplyInsertConfigList", mfApplyInsertConfigList);
		return "/component/sys/MfApplyInsertConfig_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String configId) throws Exception{
		ActionContext.initialize(request,response);
		 formapplyinsertconfig0001 = formService.getFormData("applyinsertconfig0001");
		 getFormValue(formapplyinsertconfig0001);
		 MfApplyInsertConfig mfApplyInsertConfig = new MfApplyInsertConfig();
		 mfApplyInsertConfig.setConfigId(configId);
		 mfApplyInsertConfig = mfApplyInsertConfigFeign.getById(mfApplyInsertConfig);
		 getObjValue(formapplyinsertconfig0001, mfApplyInsertConfig);
		 model.addAttribute("formapplyinsertconfig0001", formapplyinsertconfig0001);
		return "/component/sys/MfApplyInsertConfig_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String configId) throws Exception {
		ActionContext.initialize(request,response);
		MfApplyInsertConfig mfApplyInsertConfig = new MfApplyInsertConfig();
		mfApplyInsertConfig.setConfigId(configId);
		mfApplyInsertConfigFeign.delete(mfApplyInsertConfig);
		return getListPage();
	}
	/**
	 * AJAX获取请求地址
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getReqUrlByUserAjax")
	@ResponseBody
	public Map<String, Object>  getReqUrlByUserAjax() throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			Map<String, Object> resultMap  = mfApplyInsertConfigFeign.getReqUrlByUser(User.getRegNo(request));
			if (resultMap!=null) {
				dataMap.put("flag", "success");
				dataMap.put("reqUrl", resultMap.get("reqUrl"));
				dataMap.put("reqType", resultMap.get("reqType"));
				dataMap.put("kindCnt", resultMap.get("kindCnt"));
				dataMap.put("kindNo", resultMap.get("kindNo"));
			}else {
				dataMap.put("flag", "error");
				dataMap.put("reqUrl", "跳转页面暂未配置");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取进件跳转页面失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 formapplyinsertconfig0002 = formService.getFormData("applyinsertconfig0002");
		 getFormValue(formapplyinsertconfig0002);
		 boolean validateFlag = this.validateFormData(formapplyinsertconfig0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 formapplyinsertconfig0002 = formService.getFormData("applyinsertconfig0002");
		 getFormValue(formapplyinsertconfig0002);
		 boolean validateFlag = this.validateFormData(formapplyinsertconfig0002);
	}
	
	
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public FormData getFormapplyinsertconfig0002() {
		return formapplyinsertconfig0002;
	}
	public void setFormapplyinsertconfig0002(FormData formapplyinsertconfig0002) {
		this.formapplyinsertconfig0002 = formapplyinsertconfig0002;
	}
	public FormData getFormapplyinsertconfig0001() {
		return formapplyinsertconfig0001;
	}
	public void setFormapplyinsertconfig0001(FormData formapplyinsertconfig0001) {
		this.formapplyinsertconfig0001 = formapplyinsertconfig0001;
	}

}
