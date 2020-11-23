package app.component.cus.execnotice.controller;

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
import app.component.cus.courtinfo.entity.MfCusCourtInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.execnotice.entity.MfCusExecNotice;
import app.component.cus.execnotice.feign.MfCusExecNoticeFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

@Controller
@RequestMapping("/mfCusExecNotice")
public class MfCusExecNoticeController extends BaseFormBean{
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusExecNoticeFeign mfCusExecNoticeFeign;
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
		return "/component/cus/execnotice/MfCusExecNotice_List";
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
		MfCusExecNotice mfCusExecNotice = new MfCusExecNotice();
		try {
			mfCusExecNotice.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusExecNotice.setCriteriaList(mfCusExecNotice, ajaxData);//我的筛选
			//mfCusGuaLoanOuterSum.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusGuaLoanOuterSum,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusExecNotice", mfCusExecNotice));
			//自定义查询Bo方法
			ipage = mfCusExecNoticeFeign.findByPage(ipage);
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
		MfCusExecNotice mfCusExecNotice = new MfCusExecNotice();
		mfCusExecNotice.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusExecNoticeAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
		} else {
			FormData formcusExecNoticeBase = formService.getFormData(formId);
			if (formcusExecNoticeBase.getFormId() == null) {
				/*
				 * logger.error("客户类型为" + mfCusCustomer.getCusType() +
				 * "的MfCusGuaranteeOuterAction表单form" + formId + ".xml文件不存在");
				 */
			} else { 
				getFormValue(formcusExecNoticeBase);
				mfCusExecNotice.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusExecNoticeBase, mfCusExecNotice);
			}
			model.addAttribute("formcusExecNoticeBase", formcusExecNoticeBase);
		}
//		model.addAttribute("cusName", mfCusCustomer.getCusName());
//		model.addAttribute("cusNo", mfCusCustomer.getCusNo());
		model.addAttribute("query", "");
		return "/component/cus/execnotice/MfCusExecNotice_Insert";
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
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusExecNoticeAction").getAddModel();
			}
			FormData cusExecNoticeBase = formService.getFormData(formId);
			
//			FormData cusExecNoticeBase = formService.getFormData("cusExecNoticeBase");
//			FormData cusExecNoticeDetail = formService.getFormData("cusExecNoticeDetail");
			getFormValue(cusExecNoticeBase, getMapByJson(ajaxData));
			if (this.validateFormData(cusExecNoticeBase)) {
				MfCusExecNotice mfCusExecNotice = new MfCusExecNotice();
				mfCusExecNotice.setCurrentSessionOrgNo(User.getOrgNo(request));
				setObjValue(cusExecNoticeBase, mfCusExecNotice);
				dataMap = mfCusExecNoticeFeign.insert(mfCusExecNotice);
				String cusNo = mfCusExecNotice.getCusNo();
				String relNo = mfCusExecNotice.getCusNo();
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusExecNotice.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusExecNoticeAction");
				if(mfCusFormConfig == null){
					
				}else{
					formId = mfCusFormConfig.getListModelDef();
				}
				
				
				mfCusExecNotice.setCusNo(cusNo);
				mfCusExecNotice.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusExecNotice", mfCusExecNotice));
				String tableHtml = jtu.getJsonStr("table"+formId, "tableTag",
						(List<MfCusExecNotice>) mfCusExecNoticeFeign.findByPage(ipage).getResult(), null, true);
				
				
				
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo,relNo);// 更新资料完整度
				dataMap.put("mfCusExecNotice", mfCusExecNotice);
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
	 * @param execNoticeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String cusNo,String execNoticeId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusExecNotice mfCusExecNotice = new MfCusExecNotice();
		FormData formcusExecNoticeBase= null;
		mfCusExecNotice.setCusNo(cusNo);
        mfCusExecNotice.setExecNoticeId(execNoticeId);
		mfCusExecNotice = mfCusExecNoticeFeign.getById(mfCusExecNotice);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusExecNoticeAction");
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
			formcusExecNoticeBase = formService.getFormData(formId);
			if(formcusExecNoticeBase.getFormId() == null){
				//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusStaffAction表单form"+formId+".xml文件不存在");
			}else{
				getFormValue(formcusExecNoticeBase);
				getObjValue(formcusExecNoticeBase, mfCusExecNotice);
			}
		}
		model.addAttribute("formcusExecNoticeBase", formcusExecNoticeBase);
		model.addAttribute("query", "");
		return "/component/cus/execnotice/MfCusExecNotice_Detail";
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
			FormData formcusExecNoticeBase = formService.getFormData(formId);

			getFormValue(formcusExecNoticeBase,map);
			if(this.validateFormData(formcusExecNoticeBase)){
				MfCusExecNotice mfCusExecNotice =   new MfCusExecNotice();
				setObjValue(formcusExecNoticeBase, mfCusExecNotice);
				mfCusExecNoticeFeign.update(mfCusExecNotice);
				/*MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
				setObjValue(formcusbank00003, mfCusBankAccManage);
				mfCusBankAccManageFeign.update(mfCusBankAccManage);*/

				//getTableData();
				String cusNo = mfCusExecNotice.getCusNo();
				 mfCusExecNotice =   new MfCusExecNotice();
				mfCusExecNotice.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusExecNotice", mfCusExecNotice));
				JsonTableUtil jtu = new JsonTableUtil();
				String  tableHtml = jtu.getJsonStr("tablecusExecNoticeListBase","tableTag", (List<MfCusExecNotice>)mfCusExecNoticeFeign.findByPage(ipage).getResult(), null,true);
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
		FormData formcusper00002 = formService.getFormData(formId);
		getFormValue(formcusper00002, getMapByJson(ajaxData));
		MfCusExecNotice mfCusExecNoticeJsp = new MfCusExecNotice();
		setObjValue(formcusper00002, mfCusExecNoticeJsp);
		MfCusExecNotice mfCusExecNotice = mfCusExecNoticeFeign.getById(mfCusExecNoticeJsp);
		if(mfCusExecNotice!=null){
			try{
				mfCusExecNotice = (MfCusExecNotice) EntityUtil.reflectionSetVal(mfCusExecNotice, mfCusExecNoticeJsp, getMapByJson(ajaxData));
				mfCusExecNoticeFeign.update(mfCusExecNotice);
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
	public Map<String, Object> deleteAjax(String execNoticeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusExecNotice mfCusExecNotice = new MfCusExecNotice();
		mfCusExecNotice.setExecNoticeId(execNoticeId);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// mfCusEquityInfo = (MfCusEquityInfo)JSONObject.toBean(jb,
			// MfCusEquityInfo.class);
			mfCusExecNoticeFeign.delete(mfCusExecNotice);
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
	public Map<String, Object> listShowDetailAjax(String cusNo, String execNoticeId) throws Exception {
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusExecNoticeAction");
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
			FormData formcusExecNotice0003 = formService.getFormData(formId);
			MfCusExecNotice mfCusExecNotice = new MfCusExecNotice();
			mfCusExecNotice.setExecNoticeId(execNoticeId);
			mfCusExecNotice = mfCusExecNoticeFeign.getById(mfCusExecNotice);
			getObjValue(formcusExecNotice0003, mfCusExecNotice);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusExecNotice0003, "propertySeeTag", "");
			if (mfCusExecNotice != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusExecNotice);
		}
		return dataMap;
	}
}
