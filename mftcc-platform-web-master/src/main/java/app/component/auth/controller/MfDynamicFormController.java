package  app.component.auth.controller;
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

import app.component.auth.entity.MfCusCreditModel;
import app.component.auth.entity.MfDynamicForm;
import app.component.auth.feign.MfCusCreditModelFeign;
import app.component.auth.feign.MfDynamicFormFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfDynamicFormAction.java
 * Description: 客户授信申请控制操作
 * @author:LJW
 * @Wed Feb 22 18:08:09 CST 2017
 **/
@Controller
@RequestMapping("/mfDynamicForm")
public class MfDynamicFormController extends BaseFormBean{
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfDynamicFormBo
	@Autowired
	private MfDynamicFormFeign mfDynamicFormFeign;
	@Autowired
	private MfCusCreditModelFeign mfCusCreditModelFeign;  //授信业务申请模型控制
	//全局变量
	//异步参数
	//表单变量
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/auth/MfDynamicForm_List";
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfDynamicForm mfDynamicForm = new MfDynamicForm();
		try {
			mfDynamicForm.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfDynamicForm.setCriteriaList(mfDynamicForm, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfDynamicForm", mfDynamicForm));
			//自定义查询Bo方法
			ipage = mfDynamicFormFeign.findByPage(ipage);
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
	 * AJAX新增动态表单操作
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
		FormData 	formcredit0002 = formService.getFormData("credit0002");
			getFormValue(formcredit0002, getMapByJson(ajaxData));
			if(this.validateFormData(formcredit0002)){
		MfDynamicForm mfDynamicForm = new MfDynamicForm();
				mfDynamicForm.setFormNo(BizPubParm.CREDIT_FORM_ID);
				mfDynamicForm.setMotherNewFormNo(BizPubParm.CREDIT_FORM_ID);
				mfDynamicForm.setMotherDetailFormNo(BizPubParm.CREDIT_FORM_ID);
				mfDynamicForm.setFromType("CREDIT_APPLY");
				setObjValue(formcredit0002, mfDynamicForm);
				
				
				mfDynamicFormFeign.insert(mfDynamicForm);
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
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcredit0002 = formService.getFormData("credit0002");
		getFormValue(formcredit0002, getMapByJson(ajaxData));
		MfDynamicForm mfDynamicFormJsp = new MfDynamicForm();
		setObjValue(formcredit0002, mfDynamicFormJsp);
		MfDynamicForm mfDynamicForm = mfDynamicFormFeign.getById(mfDynamicFormJsp);
		if(mfDynamicForm!=null){
			try{
				mfDynamicForm = (MfDynamicForm)EntityUtil.reflectionSetVal(mfDynamicForm, mfDynamicFormJsp, getMapByJson(ajaxData));
				mfDynamicFormFeign.update(mfDynamicForm);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formcredit0002 = formService.getFormData("credit0002");
			getFormValue(formcredit0002, getMapByJson(ajaxData));
			if(this.validateFormData(formcredit0002)){
		MfDynamicForm mfDynamicForm = new MfDynamicForm();
				setObjValue(formcredit0002, mfDynamicForm);
				mfDynamicFormFeign.update(mfDynamicForm);
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
	 * 更新状态
	 * @author LJW
	 * date 2017-2-23
	 */
	@RequestMapping(value = "/updateStatusAjax")
	@ResponseBody
	public Map<String, Object> updateStatusAjax(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
		FormData 	formcredit0002 = formService.getFormData("credit0002");
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			getFormValue(formcredit0002, jsonObject);
		MfDynamicForm 	mfDynamicForm = new MfDynamicForm();
			setObjValue(formcredit0002, mfDynamicForm);
			mfDynamicFormFeign.update(mfDynamicForm);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 打开表单检查
	 * @author LJW
	 * date 2017-2-27
	 * @param id 
	 */
	@RequestMapping(value = "/checkForm")
	public Map<String, Object> checkForm(Model model, String ajaxData, Integer id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfDynamicForm mfDynamicForm = new MfDynamicForm();
		try {
			mfDynamicForm.setId(id);
			mfDynamicForm = mfDynamicFormFeign.getById(mfDynamicForm);
			String formType = request.getParameter("formtype");
			String addFormNo = mfDynamicForm.getNewFormNo();
			if ("add".equals(formType)) {
				if ("".equals(addFormNo) || addFormNo == null) {
					dataMap.put("flag", "0");
					dataMap.put("formNo", mfDynamicForm.getMotherNewFormNo());
				}else {
					dataMap.put("flag", "1");
					dataMap.put("formNo", addFormNo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_FORM_CHECK.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 复制模板表单为一个新的表单，供操作使用
	 * @author LJW
	 * date 2017-2-27
	 * @param id 
	 */
	@RequestMapping(value = "/doCopyForm")
	public Map<String, Object> doCopyForm(Model model, String ajaxData, Integer id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfDynamicForm mfDynamicForm = new MfDynamicForm();
		try{
			String formType = request.getParameter("formtype");
			String formid_old = request.getParameter("formid_old");
			//复制母版表单
		 	FormData form=formService.getFormData(formid_old);
			String formid_new = formid_old + "_" + formType + id;
			form.setFormId(formid_new);
		 	formService.saveForm(form,"insert");
			//更新数据
			mfDynamicForm.setId(id);
			if("add".equals(formType)){
				mfDynamicForm.setNewFormNo(formid_new);
			}else{
				mfDynamicForm.setDetailFormNo(formid_new);
			}
			mfDynamicFormFeign.update(mfDynamicForm);
			dataMap.put("flag", "success");
			dataMap.put("formid_new", formid_new);
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	
	/**
	 * 表单重置操作
	 * @author LJW
	 * date 2017-2-23
	 * @param id 
	 */
	@RequestMapping(value = "/doRestoreForm")
	public Map<String, Object> doRestoreForm(Model model, String ajaxData, Integer id)throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfDynamicForm mfDynamicForm = new MfDynamicForm();
		try {
			FormData formData = new FormData();
			mfDynamicForm.setId(id);
			mfDynamicForm = mfDynamicFormFeign.getById(mfDynamicForm);
			if (mfDynamicForm == null) {
				dataMap.put("flag","error");
				dataMap.put("msg",MessageEnum.ERROR_NULL_FORM.getMessage());
			}else {
				//重置时先删除，在复制
				String formNo = mfDynamicForm.getFormNo();
				if(formNo != null && !"".equals(formNo)){
		 	formData = formService.getFormData(formNo);
					formData.setFormId(formNo);
		 	formService.saveForm(formData, "delete");
					
		 	formData=formService.getFormData(mfDynamicForm.getMotherNewFormNo());
					formData.setFormId(formNo);
		 	formService.saveForm(formData,"save");
				}
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_OPERATION.getMessage("重置表单"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage("重置"));
			throw e;
		}
		return dataMap;
	}
	
	
	/**
	 * AJAX获取查看
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, Integer id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcredit0002 = formService.getFormData("credit0002");
		MfDynamicForm mfDynamicForm = new MfDynamicForm();
		mfDynamicForm.setId(id);
		mfDynamicForm = mfDynamicFormFeign.getById(mfDynamicForm);
		getObjValue(formcredit0002, mfDynamicForm,formData);
		if(mfDynamicForm!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, Integer id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfDynamicForm mfDynamicForm = new MfDynamicForm();
		mfDynamicForm.setId(id);
		try {
			mfDynamicFormFeign.delete(mfDynamicForm);
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
	 * 打开新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formcredit0002 = formService.getFormData("credit0002");
		model.addAttribute("formcredit0002", formcredit0002);
		model.addAttribute("query", "");
		return "/component/auth/MfDynamicForm_Insert";
	}
	/***
	 * 新增操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcredit0002 = formService.getFormData("credit0002");
		 getFormValue(formcredit0002);
		MfDynamicForm  mfDynamicForm = new MfDynamicForm();
		 setObjValue(formcredit0002, mfDynamicForm);
		 mfDynamicFormFeign.insert(mfDynamicForm);
		 getObjValue(formcredit0002, mfDynamicForm);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfDynamicForm", mfDynamicForm));
		 List<MfDynamicForm> mfDynamicFormList = (List<MfDynamicForm>)mfDynamicFormFeign.findByPage(ipage).getResult();
		model.addAttribute("formcredit0002", formcredit0002);
		model.addAttribute("query", "");
		return "/component/auth/MfDynamicForm_Insert";
	}
	/**
	 * 查询操作
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, Integer id) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request,response);
		FormData  formcredit0002 = formService.getFormData("credit0002");
		 getFormValue(formcredit0002);
		MfDynamicForm  mfDynamicForm = new MfDynamicForm();
		 mfDynamicForm.setId(id);
		 mfDynamicForm = mfDynamicFormFeign.getById(mfDynamicForm);
		 getObjValue(formcredit0002, mfDynamicForm);
		model.addAttribute("formcredit0002", formcredit0002);
		model.addAttribute("query", "");
		return "/component/auth/MfDynamicForm_Detail";
	}
	/**
	 * 删除操作
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, Integer id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfDynamicForm mfDynamicForm = new MfDynamicForm();
		mfDynamicForm.setId(id);
		mfDynamicFormFeign.delete(mfDynamicForm);
		return getListPage(model);
	}
	
	/**
	 * 检查授信申请表单是否正在使用
	 * @author LJW
	 * date 2017-2-23
	 * @param id 
	 */
	@RequestMapping(value = "/checkDelCredit")
	@ResponseBody
	public Map<String, Object> checkDelCredit(Model model, String ajaxData, Integer id)throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfDynamicForm mfDynamicForm = new MfDynamicForm();
		mfDynamicForm.setId(id);
		mfDynamicForm = mfDynamicFormFeign.getById(mfDynamicForm);
		if (mfDynamicForm != null) {
			String creditFormId = mfDynamicForm.getFormNo();
			//在配置模型表中根据 creditFormId 查询模型中是否在用该表单
			MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
			mfCusCreditModel.setCreditFormId(creditFormId);
			List<MfCusCreditModel> mfCusCreditModels = mfCusCreditModelFeign.getByCreditFormIds(mfCusCreditModel);
			if(mfCusCreditModels != null && mfCusCreditModels.size() > 0){
				dataMap.put("flag", "1");
			}else {
				dataMap.put("flag", "0");
			}
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
		FormData  formcredit0002 = formService.getFormData("credit0002");
		 getFormValue(formcredit0002);
		 boolean validateFlag = this.validateFormData(formcredit0002);
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
		FormData  formcredit0002 = formService.getFormData("credit0002");
		 getFormValue(formcredit0002);
		 boolean validateFlag = this.validateFormData(formcredit0002);
	}
	
}
