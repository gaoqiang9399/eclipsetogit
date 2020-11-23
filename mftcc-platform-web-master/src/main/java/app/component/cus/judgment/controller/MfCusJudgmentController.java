package app.component.cus.judgment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
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
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.judgment.entity.MfCusJudgment;
import app.component.cus.judgment.feign.MfCusJudgmentFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

@Controller
@RequestMapping("/mfCusJudgment")
public class MfCusJudgmentController extends BaseFormBean{
	@Autowired
	private MfCusJudgmentFeign mfCusJudgmentFeign;
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/cus/judgment/MfCusJudgment_List";
	}
	
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusJudgment mfCusJudgment = new MfCusJudgment();
		try {
			mfCusJudgment.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusJudgment.setCriteriaList(mfCusJudgment, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusJudgment", mfCusJudgment));
			//自定义查询Bo方法
			ipage = mfCusJudgmentFeign.findByPage(ipage);
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
	 * 新增页面
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model, String cusNo, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusJudgment mfCusJudgment = new MfCusJudgment();
		mfCusJudgment.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusJudgmentAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
		} else {
			FormData cusJudgmentBase = formService.getFormData(formId);
			if (cusJudgmentBase.getFormId() == null) {
				/*
				 * logger.error("客户类型为" + mfCusCustomer.getCusType() +
				 * "的MfCusGuaranteeOuterAction表单form" + formId + ".xml文件不存在");
				 */
			} else { 
				getFormValue(cusJudgmentBase);
				mfCusJudgment.setCusName(mfCusCustomer.getCusName());
				getObjValue(cusJudgmentBase, mfCusJudgment);
			}
			model.addAttribute("cusJudgmentBase", cusJudgmentBase);
		}
//		model.addAttribute("cusName", mfCusCustomer.getCusName());
//		model.addAttribute("cusNo", mfCusCustomer.getCusNo());
		model.addAttribute("query", "");
		return "/component/cus/judgment/MfCusJudgment_Insert";
	}
	
	
	/**
	 * AJAX新增
	 * @param startFlowFlag 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusJudgmentAction").getAddModel();
			}
			FormData cusJudgmentBase = formService.getFormData(formId);
			
//			FormData cusJudgmentBase = formService.getFormData("cusJudgmentBase");
//			FormData cusJudgmentDetail = formService.getFormData("cusJudgmentDetail");
			getFormValue(cusJudgmentBase, getMapByJson(ajaxData));
			if (this.validateFormData(cusJudgmentBase)) {
				MfCusJudgment mfCusJudgment = new MfCusJudgment();
				mfCusJudgment.setCurrentSessionOrgNo(User.getOrgNo(request));
				setObjValue(cusJudgmentBase, mfCusJudgment);
				dataMap = mfCusJudgmentFeign.insert(mfCusJudgment);
				String cusNo = mfCusJudgment.getCusNo();
				String relNo = mfCusJudgment.getCusNo();
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusJudgment.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusJudgmentAction");				
				if(mfCusFormConfig == null){
					
				}else{
					formId = mfCusFormConfig.getListModelDef();
				}
				
				mfCusJudgment.setCusNo(cusNo);
				mfCusJudgment.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusJudgment", mfCusJudgment));
				String tableHtml = jtu.getJsonStr("table"+formId, "tableTag",
						(List<MfCusJudgment>) mfCusJudgmentFeign.findByPage(ipage).getResult(), null, true);
				
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo,relNo);// 更新资料完整度
				dataMap.put("mfCusJudgment", mfCusJudgment);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("DataFullFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("htmlStrFlag", "1");
				
				
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 查询
	 * @param model
	 * @param cusNo
	 * @param judgmentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String cusNo,String judgmentId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusJudgment mfCusJudgment =new MfCusJudgment();
		FormData formcusJudgmentBase= null;
		mfCusJudgment.setCusNo(cusNo);
		mfCusJudgment.setJudgmentId(judgmentId);
		mfCusJudgment = mfCusJudgmentFeign.getById(mfCusJudgment);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusJudgmentAction");
		String formId="";
		if(mfCusFormConfig == null){

		}else{
			if("1".equals(mfCusFormConfig.getShowType())){
				formId = mfCusFormConfig.getShowModelDef();
			}else{
				formId = mfCusFormConfig.getAddModelDef();
			}
		}
		if(StringUtil.isEmpty(formId)){
			//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusStaffAction表单信息没有查询到");
		}else{
			formcusJudgmentBase = formService.getFormData(formId);
			if(formcusJudgmentBase.getFormId() == null){
				//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusStaffAction表单form"+formId+".xml文件不存在");
			}else{
				getFormValue(formcusJudgmentBase);
				getObjValue(formcusJudgmentBase, mfCusJudgment);
			}
		}
		model.addAttribute("formcusJudgmentBase", formcusJudgmentBase);
		model.addAttribute("query", "");
		return "/component/cus/judgment/MfCusJudgment_Detail";
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
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try{
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusExecNoticeAction").getAddModel();
			}
			FormData formcusJudgmentBase = formService.getFormData(formId);

			getFormValue(formcusJudgmentBase,map);
			if(this.validateFormData(formcusJudgmentBase)){
				MfCusJudgment mfCusJudgment= new MfCusJudgment();
				setObjValue(formcusJudgmentBase, mfCusJudgment);
                mfCusJudgmentFeign.update(mfCusJudgment);
				String  cusNo = mfCusJudgment.getCusNo();
				mfCusJudgment= new MfCusJudgment();
				mfCusJudgment.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusJudgment", mfCusJudgment));
				JsonTableUtil jtu = new JsonTableUtil();
				String  tableHtml = jtu.getJsonStr("tablecusJudgmentListBase","tableTag", (List<MfCusJudgment>)mfCusJudgmentFeign.findByPage(ipage).getResult(), null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");

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
		String formId = (String) getMapByJson(ajaxData).get("formId");
		FormData formcusJudgment = formService.getFormData(formId);
		getFormValue(formcusJudgment, getMapByJson(ajaxData));
		MfCusJudgment mfCusJudgmentJsp = new MfCusJudgment();
		setObjValue(formcusJudgment, mfCusJudgmentJsp);
		MfCusJudgment mfCusJudgment = mfCusJudgmentFeign.getById(mfCusJudgmentJsp);
		if(mfCusJudgment!=null){
			try{
				mfCusJudgment = (MfCusJudgment) EntityUtil.reflectionSetVal(mfCusJudgment, mfCusJudgmentJsp, getMapByJson(ajaxData));
				mfCusJudgmentFeign.update(mfCusJudgment);
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
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String judgmentId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusJudgment mfCusJudgment = new MfCusJudgment();
		mfCusJudgment.setJudgmentId(judgmentId);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// mfCusEquityInfo = (MfCusEquityInfo)JSONObject.toBean(jb,
			// MfCusEquityInfo.class);
			mfCusJudgmentFeign.delete(mfCusJudgment);
			// getTableData();
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String judgmentId) throws Exception {
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusJudgmentAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			dataMap = new HashMap<String, Object>();
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcusJudgment0003 = formService.getFormData(formId);
			MfCusJudgment mfCusJudgment = new MfCusJudgment();
			mfCusJudgment.setJudgmentId(judgmentId);
			mfCusJudgment = mfCusJudgmentFeign.getById(mfCusJudgment);
			getObjValue(formcusJudgment0003, mfCusJudgment, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusJudgment0003, "propertySeeTag", "");
			if (mfCusJudgment != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusJudgment);
		}
		return dataMap;
	}
	
}
