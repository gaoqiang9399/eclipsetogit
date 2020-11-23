package  app.component.auth.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.model.entity.MfSysTemplate;
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

import app.component.auth.entity.MfCusCreditModel;
import app.component.auth.entity.MfDynamicForm;
import app.component.auth.feign.MfCusCreditModelFeign;
import app.component.auth.feign.MfDynamicFormFeign;
import app.component.common.EntityUtil;
import app.component.model.feign.MfSysTemplateFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfCusCreditModelAction.java
 * Description:授信模型配置
 * @author:LJW
 * @Thu Feb 23 16:13:12 CST 2017
 **/
@Controller
@RequestMapping("/mfCusCreditModel")
public class MfCusCreditModelController extends BaseFormBean{
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusCreditModelBo
	@Autowired
	private MfCusCreditModelFeign mfCusCreditModelFeign;
	@Autowired
	private MfDynamicFormFeign mfDynamicFormFeign;
	//模板业务操作类
	@Autowired
	private MfSysTemplateFeign mfSysTemplateFeign;
	//全局变量
	//异步参数
	//表单变量
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model,String modelId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		try {
			MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
			mfCusCreditModel.setModelId(modelId);
			mfCusCreditModel = mfCusCreditModelFeign.getById(mfCusCreditModel);
			FormData 	formcreditmodel0002 = formService.getFormData("creditmodel0002");
			getObjValue(formcreditmodel0002, mfCusCreditModel);
			model.addAttribute("formcreditmodel0002", formcreditmodel0002);
			model.addAttribute("query", "");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/auth/MfCusCreditModel_Insert";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
		try {
			mfCusCreditModel.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCreditModel.setCriteriaList(mfCusCreditModel, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusCreditModel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfCusCreditModelFeign.findByPage(ipage, mfCusCreditModel);
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
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formcreditmodel0002 = formService.getFormData("creditmodel0002");
			getFormValue(formcreditmodel0002, getMapByJson(ajaxData));
			if(this.validateFormData(formcreditmodel0002)){
		MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
				setObjValue(formcreditmodel0002, mfCusCreditModel);
				int cusTypeModelSum = mfCusCreditModelFeign.getByCusTypeNoSum(mfCusCreditModel);
				if (cusTypeModelSum >= 1) {
					dataMap.put("flag", "error");
					dataMap.put("msg",MessageEnum.ERROR_CUSTYPE.getMessage());
				}else {
					mfCusCreditModelFeign.insert(mfCusCreditModel);
					//dataMap.put("creditModel", mfCusCreditModel);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}
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
	public Map<String, Object> updateAjaxByOne(String ajaxData,String applyTime) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcreditmodel0002 = formService.getFormData("creditmodel0002");
		getFormValue(formcreditmodel0002, getMapByJson(ajaxData));
		MfCusCreditModel mfCusCreditModelJsp = new MfCusCreditModel();
		setObjValue(formcreditmodel0002, mfCusCreditModelJsp);
		MfCusCreditModel mfCusCreditModel = mfCusCreditModelFeign.getById(mfCusCreditModelJsp);
		if(mfCusCreditModel!=null){
			try{
				mfCusCreditModel = (MfCusCreditModel)EntityUtil.reflectionSetVal(mfCusCreditModel, mfCusCreditModelJsp, getMapByJson(ajaxData));
				mfCusCreditModel.setApplyTime(applyTime);
				mfCusCreditModelFeign.update(mfCusCreditModel);
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
	public Map<String, Object> updateAjax(String ajaxData,String modelId,String sts) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
			mfCusCreditModel.setModelId(modelId);
			mfCusCreditModel.setSts(sts);
			mfCusCreditModelFeign.update(mfCusCreditModel);
			//dataMap.put("creditModel", mfCusCreditModel);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
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
	 * @param modelId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String modelId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcreditmodel0002 = formService.getFormData("creditmodel0002");
		MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
		mfCusCreditModel.setModelId(modelId);
		mfCusCreditModel = mfCusCreditModelFeign.getById(mfCusCreditModel);
		getObjValue(formcreditmodel0002, mfCusCreditModel,formData);
		if(mfCusCreditModel!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param modelId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String modelId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
		mfCusCreditModel.setModelId(modelId);
		try {
			mfCusCreditModelFeign.delete(mfCusCreditModel);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_DELETE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_DELETE.getMessage());
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
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formcreditmodel0002 = formService.getFormData("creditmodel0002");
		model.addAttribute("formcreditmodel0002", formcreditmodel0002);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditModel_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formcreditmodel0002 = formService.getFormData("creditmodel0002");
		getFormValue(formcreditmodel0002);
		MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
		setObjValue(formcreditmodel0002, mfCusCreditModel);
		mfCusCreditModelFeign.insert(mfCusCreditModel);
		getObjValue(formcreditmodel0002, mfCusCreditModel);
		this.addActionMessage(model, "保存成功");
		List<MfCusCreditModel> mfCusCreditModelList = (List<MfCusCreditModel>) mfCusCreditModelFeign.findByPage(this.getIpage(), mfCusCreditModel).getResult();
		model.addAttribute("formcreditmodel0002", formcreditmodel0002);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditModel_Insert";
	}
	/**
	 * 查询操作
	 * @param modelId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String modelId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formcreditmodel0002 = formService.getFormData("creditmodel0002");
		getFormValue(formcreditmodel0002);
		MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
		mfCusCreditModel.setModelId(modelId);
		mfCusCreditModel = mfCusCreditModelFeign.getById(mfCusCreditModel);
		getObjValue(formcreditmodel0002, mfCusCreditModel);
		model.addAttribute("formcreditmodel0002", formcreditmodel0002);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditModel_Detail";
	}
	
	/** 
	 * 获取授信申请动态表单列表
	 * @author LJW
	 * date 2017-2-24
	 */
	@RequestMapping(value = "/getCreditDynFormAjax")
	@ResponseBody
	public Map<String, Object> getCreditDynFormAjax(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		List<MfDynamicForm> mfDynamicForms = new ArrayList<MfDynamicForm>();
		MfDynamicForm mfDynamicForm = new MfDynamicForm();
		try{
			mfDynamicForms = mfDynamicFormFeign.getDynamicForms(mfDynamicForm);
			if (mfDynamicForms != null && mfDynamicForms.size() > 0) {
				dataMap.put("flag", "success");
				dataMap.put("creditDynFormList", mfDynamicForms);
			}else {
				dataMap.put("flag", "success");
				dataMap.put("creditDynFormList", null);
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 获取模板列表
	 * @author LJW
	 * date 2017-2-24
	 */
	@RequestMapping(value = "/getModelListAjax")
	@ResponseBody
	public Map<String, Object> getModelListAjax(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		List<MfSysTemplate> mfSysTemplates = new ArrayList<MfSysTemplate>();
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		try{
			mfSysTemplates = mfSysTemplateFeign.getAll(mfSysTemplate);
			if (mfSysTemplates != null && mfSysTemplates.size() > 0) {
				dataMap.put("flag", "success");
				dataMap.put("modelList", mfSysTemplates);
			}else {
				dataMap.put("flag", "success");
				dataMap.put("modelList", null);
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formcreditmodel0002 = formService.getFormData("creditmodel0002");
		 getFormValue(formcreditmodel0002);
		 boolean validateFlag = this.validateFormData(formcreditmodel0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formcreditmodel0002 = formService.getFormData("creditmodel0002");
		 getFormValue(formcreditmodel0002);
		 boolean validateFlag = this.validateFormData(formcreditmodel0002);
	}
	
}
