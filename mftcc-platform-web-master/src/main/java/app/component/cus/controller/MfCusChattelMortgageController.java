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
import app.component.cus.entity.MfCusChattelMortgage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusChattelMortgageFeign;
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
 * Title: MfCusChattelMortgageAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Apr 23 15:57:59 CST 2018
 **/
@Controller
@RequestMapping("/mfCusChattelMortgage")
public class MfCusChattelMortgageController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	@Autowired
	private MfCusChattelMortgageFeign mfCusChattelMortgageFeign;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
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
		return "MfCusChattelMortgage_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,String tableId,String tableType,int pageNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusChattelMortgage mfCusChattelMortgage = new MfCusChattelMortgage();
		try {
			mfCusChattelMortgage.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusChattelMortgage.setCriteriaList(mfCusChattelMortgage, ajaxData);//我的筛选
			//mfCusChattelMortgage.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusChattelMortgage,"100000Base");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusChattelMortgage", mfCusChattelMortgage));
			ipage = mfCusChattelMortgageFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formcusChattelMortgageDetail = formService.getFormData(formId);
			getFormValue(formcusChattelMortgageDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusChattelMortgageDetail)){
				String relNo = (String)map.get("relNo");

				MfCusChattelMortgage mfCusChattelMortgage = new MfCusChattelMortgage();
				setObjValue(formcusChattelMortgageDetail, mfCusChattelMortgage);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusChattelMortgage.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				mfCusChattelMortgageFeign.insert(mfCusChattelMortgage);
				
				
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusChattelMortgage.getCusNo(),
						mfCusChattelMortgage.getRelNo());// 更新资料完整度

				String tableId = "tablecusChattelMortgageList";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusChattelMortgageAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}
				JsonTableUtil jtu = new JsonTableUtil();
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusChattelMortgage", mfCusChattelMortgage));
				String  tableHtml = jtu.getJsonStr(tableId,"tableTag", (List<MfCusChattelMortgage>)mfCusChattelMortgageFeign.findByPage(ipage).getResult(), null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");
				dataMap.put("infIntegrity",infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusChattelMortgage mfCusChattelMortgage = new MfCusChattelMortgage();
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");
		FormData formcusChattelMortgageDetail = formService.getFormData(formId);
		getFormValue(formcusChattelMortgageDetail, getMapByJson(ajaxData));
		MfCusChattelMortgage mfCusChattelMortgageJsp = new MfCusChattelMortgage();
		setObjValue(formcusChattelMortgageDetail, mfCusChattelMortgageJsp);
		mfCusChattelMortgage = mfCusChattelMortgageFeign.getById(mfCusChattelMortgageJsp);
		if(mfCusChattelMortgage!=null){
			try{
				mfCusChattelMortgage = (MfCusChattelMortgage)EntityUtil.reflectionSetVal(mfCusChattelMortgage, mfCusChattelMortgageJsp, getMapByJson(ajaxData));
				mfCusChattelMortgageFeign.update(mfCusChattelMortgage);
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusChattelMortgage mfCusChattelMortgage = new MfCusChattelMortgage();
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formcusChattelMortgageDetail = formService.getFormData(formId);
			getFormValue(formcusChattelMortgageDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusChattelMortgageDetail)){
				mfCusChattelMortgage = new MfCusChattelMortgage();
				setObjValue(formcusChattelMortgageDetail, mfCusChattelMortgage);
				mfCusChattelMortgageFeign.update(mfCusChattelMortgage);

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusChattelMortgage.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String tableId = "tablecusChattelMortgageBase";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfCusChattelMortgageAction");
				if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
					tableId = "table" + mfCusFormConfig.getListModelDef();
				}
				String cusNo = mfCusChattelMortgage.getCusNo();
				mfCusChattelMortgage.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusChattelMortgage", mfCusChattelMortgage));
				String tableHtml = jtu.getJsonStr(tableId, "tableTag",
						(List<MfCusChattelMortgage>) mfCusChattelMortgageFeign.findByPage(ipage).getResult(), null, true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");

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
	public Map<String, Object> getByIdAjax(String surveyId,String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Map<String, Object> map = getMapByJson(ajaxData);
		String formId = map.get("formId").toString();
		FormData formcusChattelMortgageDetail = formService.getFormData(formId);
		MfCusChattelMortgage mfCusChattelMortgage = new MfCusChattelMortgage();
		mfCusChattelMortgage.setSurveyId(surveyId);
		mfCusChattelMortgage = mfCusChattelMortgageFeign.getById(mfCusChattelMortgage);
		getObjValue(formcusChattelMortgageDetail, mfCusChattelMortgage,formData);
		if(mfCusChattelMortgage!=null){
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
	public Map<String, Object> deleteAjax(String surveyId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusChattelMortgage mfCusChattelMortgage = new MfCusChattelMortgage();
		mfCusChattelMortgage.setSurveyId(surveyId);
		try {
			mfCusChattelMortgageFeign.delete(mfCusChattelMortgage);
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
	@RequestMapping(value = "/input")
	public String input(Model model,String cusNo,String relNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcusChattelMortgageBase = formService.getFormData("cusChattelMortgageBase");
		MfCusChattelMortgage mfCusChattelMortgage = new MfCusChattelMortgage();
		mfCusChattelMortgage.setCusNo(cusNo);
		String formId = "";
		//根据获取的客户号查询该客户数据
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		//表单基本信息
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"mfCusChattelMortgageAction");
		if(mfCusFormConfig == null){
			
		}else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		
		if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureOutsideAction表单信息没有查询到");
		} else {
			FormData formcusAssOutsideBase = formService.getFormData(formId);
		if (formcusAssOutsideBase.getFormId() == null) {
//			logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusAssureOutsideAction表单form" + formId
//			+ ".xml文件不存在");
		} else {
			getFormValue(formcusAssOutsideBase);
			mfCusChattelMortgage.setCusName(mfCusCustomer.getCusName());
			mfCusChattelMortgage.setCusNo(cusNo);
			mfCusChattelMortgage.setRelNo(relNo);
			mfCusChattelMortgage.setOpName(User.getRegName(request));
			mfCusChattelMortgage.setOpNo(User.getRegNo(request));
			getObjValue(formcusAssOutsideBase, mfCusChattelMortgage);
			getObjValue(formcusChattelMortgageBase, mfCusChattelMortgage);
		};
			model.addAttribute("formcusChattelMortgageBase", formcusChattelMortgageBase);
			model.addAttribute("query", "");
		}
		return "/component/cus/MfCusChattelMortgage_Insert";
	}
	
	/**listShowDetailAjax
	 * 详情页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String surveyId, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		request.setAttribute("ifBizManger", "3");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String cusType = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "mfCusChattelMortgageAction");
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
			this.setIfBizManger("3");
			dataMap = new HashMap<String, Object>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formmfCusChattelMortgage0004 = formService.getFormData(formId);
			MfCusChattelMortgage mfCusChattelMortgage = new MfCusChattelMortgage();
			mfCusChattelMortgage.setSurveyId(surveyId);
			mfCusChattelMortgage = mfCusChattelMortgageFeign.getById(mfCusChattelMortgage);
			getObjValue(formmfCusChattelMortgage0004, mfCusChattelMortgage, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formmfCusChattelMortgage0004, "propertySeeTag", "");
			if (mfCusChattelMortgage != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusChattelMortgage);
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
		MfCusChattelMortgage mfCusChattelMortgage = new MfCusChattelMortgage();
		String formId="";
		mfCusChattelMortgage.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "mfCusChattelMortgageAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
//			logger.error("产品类型为" + kindNo + "的MfCusAssureOutside表单信息没有查询到");
		} else {
			FormData formcusChattelMortgageBase = formService.getFormData(formId);
			if (formcusChattelMortgageBase.getFormId() == null) {
//				logger.error("产品类型为" + kindNo + "的MfCusAssureOutside表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusChattelMortgageBase);
				mfCusChattelMortgage.setCusName(mfCusCustomer.getCusName());
				mfCusChattelMortgage.setCusNo(cusNo);
				mfCusChattelMortgage.setOpName(User.getRegName(request));
				mfCusChattelMortgage.setOpNo(User.getRegNo(request));
				getObjValue(formcusChattelMortgageBase, mfCusChattelMortgage);
			}
			model.addAttribute("formcusChattelMortgageBase", formcusChattelMortgageBase);
			model.addAttribute("query", "");
		}
		return "/component/cus/mfCusChattelMortgage_Insert";
	}
	
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(String surveyId,Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		MfCusChattelMortgage mfCusChattelMortgage = new MfCusChattelMortgage();
		mfCusChattelMortgage.setSurveyId(surveyId);
		mfCusChattelMortgage = mfCusChattelMortgageFeign.getById(mfCusChattelMortgage);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusChattelMortgage.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusChattelMortgageAction");
		String formId = "";
		if (mfCusFormConfig == null) {
		} else {
			if ("1".equals(mfCusFormConfig.getShowType())) {
				formId = mfCusFormConfig.getShowModelDef();
			} else {
				formId = mfCusFormConfig.getAddModelDef();
			}
		}
		if (StringUtil.isEmpty(formId)) {

		} else {
			FormData formcusChattelMortgageBase = formService.getFormData(formId);
			if (formcusChattelMortgageBase.getFormId() == null) {

			} else {
				getFormValue(formcusChattelMortgageBase);
				getObjValue(formcusChattelMortgageBase, mfCusChattelMortgage);
			}
			model.addAttribute("formcusChattelMortgageBase", formcusChattelMortgageBase);
		}
		model.addAttribute("query","");
		return "/component/cus/MfCusChattelMortgage_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> map = getMapByJson(ajaxData);
		String formId = map.get("formId").toString();
		FormData formcusChattelMortgageDetail = formService.getFormData(formId);
		getFormValue(formcusChattelMortgageDetail);
		boolean validateFlag = this.validateFormData(formcusChattelMortgageDetail);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> map = getMapByJson(ajaxData);
		String formId = map.get("formId").toString();
		FormData formcusChattelMortgageDetail = formService.getFormData(formId);
		getFormValue(formcusChattelMortgageDetail);
		boolean validateFlag = this.validateFormData(formcusChattelMortgageDetail);
	}
}
