package  app.component.cus.controller;
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

import app.base.User;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusBranchOrganization;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusBranchOrganizationFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

/**
 * Title: MfCusBranchOrganizationAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Apr 23 16:01:54 CST 2018
 **/
@Controller
@RequestMapping("/mfCusBranchOrganization")
public class MfCusBranchOrganizationController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private MfCusBranchOrganizationFeign mfCusBranchOrganizationFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,response);
		return "MfCusBranchOrganization_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusBranchOrganization mfCusBranchOrganization = new MfCusBranchOrganization();
		try {
			mfCusBranchOrganization.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusBranchOrganization.setCriteriaList(mfCusBranchOrganization, ajaxData);//我的筛选
			//mfCusBranchOrganization.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusBranchOrganization,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusBranchOrganization", mfCusBranchOrganization));
			ipage = mfCusBranchOrganizationFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
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
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			/*String formId = map.get("formId").toString();
			FormData formmfcusbranchorganization0002 = formService.getFormData("formmfcusbranchorganization0002");*/
			String formId = map.get("formId").toString();
			FormData formmfcusbranchorganization0001 = formService.getFormData(formId);
			getFormValue(formmfcusbranchorganization0001, getMapByJson(ajaxData));
			if(this.validateFormData(formmfcusbranchorganization0001)){
				MfCusBranchOrganization mfCusBranchOrganization = new MfCusBranchOrganization();
				String cusNo = (String)map.get("cusNo");
				setObjValue(formmfcusbranchorganization0001, mfCusBranchOrganization);
				String relNo = mfCusBranchOrganization.getRelNo();
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				
				mfCusBranchOrganizationFeign.insert(mfCusBranchOrganization);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusBranchOrganization.getCusNo(),
						mfCusBranchOrganization.getRelNo());// 更新资料完整度
				JsonTableUtil jtu = new JsonTableUtil();
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusBranchOrganization", mfCusBranchOrganization));
				String  tableHtml = jtu.getJsonStr("tablemfcusbranchorganization0001","tableTag", (List<MfCusBranchOrganization>)mfCusBranchOrganizationFeign.findByPage(ipage).getResult(), null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");
				dataMap.put("infIntegrity",infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
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
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> map = getMapByJson(ajaxData);
		String formId = map.get("formId").toString();
		FormData formmfcusbranchorganization0002 = formService.getFormData(formId);
		getFormValue(formmfcusbranchorganization0002, getMapByJson(ajaxData));
		MfCusBranchOrganization mfCusBranchOrganizationJsp = new MfCusBranchOrganization();
		setObjValue(formmfcusbranchorganization0002, mfCusBranchOrganizationJsp);
		MfCusBranchOrganization mfCusBranchOrganization = new MfCusBranchOrganization();
		mfCusBranchOrganization = mfCusBranchOrganizationFeign.getById(mfCusBranchOrganizationJsp);
		if(mfCusBranchOrganization!=null){
			try{
				mfCusBranchOrganization = (MfCusBranchOrganization)EntityUtil.reflectionSetVal(mfCusBranchOrganization, mfCusBranchOrganizationJsp, getMapByJson(ajaxData));
				mfCusBranchOrganizationFeign.update(mfCusBranchOrganization);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
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
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		MfCusBranchOrganization mfCusBranchOrganization = new MfCusBranchOrganization();
		Map<String, Object> map = getMapByJson(ajaxData);
		String formId = map.get("formId").toString();
		try{
			FormData formmfcusbranchorganization0002 = formService.getFormData(formId);
			getFormValue(formmfcusbranchorganization0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmfcusbranchorganization0002)){
				mfCusBranchOrganization = new MfCusBranchOrganization();
				setObjValue(formmfcusbranchorganization0002, mfCusBranchOrganization);
				mfCusBranchOrganizationFeign.update(mfCusBranchOrganization);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
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
	public Map<String,Object> getByIdAjax(String surveyId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formmfcusbranchorganization0002 = formService.getFormData("mfcusbranchorganization0002");
		MfCusBranchOrganization mfCusBranchOrganization = new MfCusBranchOrganization();
		mfCusBranchOrganization.setSurveyId(surveyId);
		mfCusBranchOrganization = mfCusBranchOrganizationFeign.getById(mfCusBranchOrganization);
		getObjValue(formmfcusbranchorganization0002, mfCusBranchOrganization,formData);
		if(mfCusBranchOrganization!=null){
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
	public Map<String,Object> deleteAjax(String surveyId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusBranchOrganization mfCusBranchOrganization = new MfCusBranchOrganization();
		mfCusBranchOrganization.setSurveyId(surveyId);
		try {
			mfCusBranchOrganizationFeign.delete(mfCusBranchOrganization);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
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
	@RequestMapping("/input")
	public String input(Model model,String cusNo,String relNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfcusbranchorganization0001 = formService.getFormData("mfcusbranchorganization0001");
		MfCusBranchOrganization mfCusBranchOrganization = new MfCusBranchOrganization();
		mfCusBranchOrganization.setCusNo(cusNo);
		String formId="";
		//根据获取的客户号查询该客户数据
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		//表单基本信息
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusAssureOutsideAction");
		if(mfCusFormConfig == null){
			
		}else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		
		if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureOutsideAction表单信息没有查询到");
		} else {
			FormData formcusAssOutsideBase = formService.getFormData(formId);
			if (formcusAssOutsideBase.getFormId() == null) {
//				logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureOutsideAction表单form" + formId
//						+ ".xml文件不存在");
			} else {
				getFormValue(formcusAssOutsideBase);
				mfCusBranchOrganization.setCusName(mfCusCustomer.getCusName());
				mfCusBranchOrganization.setCusNo(cusNo);
				mfCusBranchOrganization.setRelNo(relNo);
				mfCusBranchOrganization.setOpName(User.getRegName(request));
				mfCusBranchOrganization.setOpNo(User.getRegNo(request));
				getObjValue(formcusAssOutsideBase, mfCusBranchOrganization);
				getObjValue(formmfcusbranchorganization0001, mfCusBranchOrganization);
			};
			model.addAttribute("formmfcusbranchorganization0001", formmfcusbranchorganization0001);
			model.addAttribute("query", "");
		}	
		return "/component/cus/MfCusBranchOrganization_Insert";
	}
	
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String surveyId, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String cusType = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusBranchOrganizationAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + cusType +
			// "的MfCusHighInfoAction表单信息没有查询到");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger","3");
			dataMap = new HashMap<String, Object>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formmfcusbranchorganization0004 = formService.getFormData(formId);
			MfCusBranchOrganization mfCusBranchOrganization = new MfCusBranchOrganization();
			mfCusBranchOrganization.setSurveyId(surveyId);
			mfCusBranchOrganization = mfCusBranchOrganizationFeign.getById(mfCusBranchOrganization);
			getObjValue(formmfcusbranchorganization0004, mfCusBranchOrganization, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formmfcusbranchorganization0004, "propertySeeTag", "");
			if (mfCusBranchOrganization != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusBranchOrganization);
		}
		return dataMap;
	}
	
	/**inputBiz
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model,String cusNo,String relNo,String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfCusBranchOrganization mfCusBranchOrganization = new MfCusBranchOrganization();
		String formId="";
		mfCusBranchOrganization.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusBranchOrganizationAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			logger.error("产品类型为" + kindNo + "的MfCusAssureOutside表单信息没有查询到");
		} else {
			FormData formcusBranchOrganizationBase = formService.getFormData(formId);
			if (formcusBranchOrganizationBase.getFormId() == null) {
//				logger.error("产品类型为" + kindNo + "的MfCusAssureOutside表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusBranchOrganizationBase);
				mfCusBranchOrganization.setCusName(mfCusCustomer.getCusName());
				mfCusBranchOrganization.setCusNo(cusNo);
				mfCusBranchOrganization.setOpName(User.getRegName(request));
				mfCusBranchOrganization.setOpNo(User.getRegNo(request));
				getObjValue(formcusBranchOrganizationBase, mfCusBranchOrganization);
			}
			model.addAttribute("formcusBranchOrganizationBase", formcusBranchOrganizationBase);
			model.addAttribute("query", "");
		}
		return "/component/cus/MfCusBranchOrganization_Insert";
	}
	
	/**getById
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(String surveyId,Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfcusbranchorganization0002 = formService.getFormData("mfcusbranchorganization0001");
		getFormValue(formmfcusbranchorganization0002);
		MfCusBranchOrganization mfCusBranchOrganization = new MfCusBranchOrganization();
		mfCusBranchOrganization.setSurveyId(surveyId);
		mfCusBranchOrganization = mfCusBranchOrganizationFeign.getById(mfCusBranchOrganization);
		getObjValue(formmfcusbranchorganization0002, mfCusBranchOrganization);
		model.addAttribute("formmfcusbranchorganization0002", formmfcusbranchorganization0002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusBranchOrganization_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		 FormData formmfcusbranchorganization0002 = formService.getFormData("mfcusbranchorganization0002");
		 getFormValue(formmfcusbranchorganization0002);
		 boolean validateFlag = this.validateFormData(formmfcusbranchorganization0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		 FormData formmfcusbranchorganization0002 = formService.getFormData("mfcusbranchorganization0002");
		 getFormValue(formmfcusbranchorganization0002);
		 boolean validateFlag = this.validateFormData(formmfcusbranchorganization0002);
	}
}
