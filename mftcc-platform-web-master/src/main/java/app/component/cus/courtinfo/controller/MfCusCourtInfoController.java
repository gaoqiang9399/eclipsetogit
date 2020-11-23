package app.component.cus.courtinfo.controller;

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
import app.component.cus.courtinfo.feign.MfCusCourtInfoFeign;
import app.component.cus.dishonestinfo.entity.MfCusDishonestInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

@Controller
@RequestMapping("/mfCusCourtInfo")
public class MfCusCourtInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusCourtInfoFeign mfCusCourtInfoFeign;
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
		return "/component/cus/courtinfo/MfCusCourtInfo_List";
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
		MfCusCourtInfo mfCusCourtInfo = new MfCusCourtInfo();
		try {
			mfCusCourtInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCourtInfo.setCriteriaList(mfCusCourtInfo, ajaxData);//我的筛选
			//mfCusGuaLoanOuterSum.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusGuaLoanOuterSum,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCourtInfo", mfCusCourtInfo));
			//自定义查询Bo方法
			ipage = mfCusCourtInfoFeign.findByPage(ipage);
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
		MfCusCourtInfo mfCusCourtInfo = new MfCusCourtInfo();
		mfCusCourtInfo.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusCourtInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
		} else {
			FormData formcusCourtInfoBase = formService.getFormData(formId);
			if (formcusCourtInfoBase.getFormId() == null) {
				/*
				 * logger.error("客户类型为" + mfCusCustomer.getCusType() +
				 * "的MfCusGuaranteeOuterAction表单form" + formId + ".xml文件不存在");
				 */
			} else { 
				getFormValue(formcusCourtInfoBase);
				mfCusCourtInfo.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusCourtInfoBase, mfCusCourtInfo);
			}
			model.addAttribute("formcusCourtInfoBase", formcusCourtInfoBase);
		}
//		model.addAttribute("cusName", mfCusCustomer.getCusName());
//		model.addAttribute("cusNo", mfCusCustomer.getCusNo());
		model.addAttribute("query", "");
		return "/component/cus/courtinfo/MfCusCourtInfo_Insert";
	}

	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String courtId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusCourtInfo mfCusCourtInfo = new MfCusCourtInfo();
		mfCusCourtInfo.setCourtId(courtId);
		FormData formcusCourtInfoBase = formService.getFormData("cusCourtInfoBase");
		String flag = "update";
		getFormValue(formcusCourtInfoBase);
		mfCusCourtInfo = mfCusCourtInfoFeign.getById(mfCusCourtInfo);
		getObjValue(formcusCourtInfoBase, mfCusCourtInfo);
		model.addAttribute("formcusCourtInfoBase", formcusCourtInfoBase);
		model.addAttribute("query", "");
		return "/component/cus/courtinfo/MfCusCourtInfo_Detail";
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
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusCourtInfoAction").getAddModel();
			}
			FormData cusCourtInfoBase = formService.getFormData(formId);
			
//			FormData cusCourtInfoBase = formService.getFormData("cusCourtInfoBase");
//			FormData cusCourtInfoDetail = formService.getFormData("cusCourtInfoDetail");
			getFormValue(cusCourtInfoBase, getMapByJson(ajaxData));
			if (this.validateFormData(cusCourtInfoBase)) {
				MfCusCourtInfo mfCusCourtInfo = new MfCusCourtInfo();
				mfCusCourtInfo.setCurrentSessionOrgNo(User.getOrgNo(request));
				setObjValue(cusCourtInfoBase, mfCusCourtInfo);
				dataMap = mfCusCourtInfoFeign.insert(mfCusCourtInfo);
				String cusNo = mfCusCourtInfo.getCusNo();
				String relNo = mfCusCourtInfo.getCusNo();
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusCourtInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String tableId = "cusCourtInfoListBase";
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCourtInfoAction");				
				if(mfCusFormConfig != null){
					if(StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())){
						tableId = mfCusFormConfig.getListModelDef();
					}
				}
				
				mfCusCourtInfo = new MfCusCourtInfo();
				mfCusCourtInfo.setCusNo(cusNo);
//				mfCusCourtInfo.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusCourtInfo", mfCusCourtInfo));
				String tableHtml = jtu.getJsonStr("table"+tableId, "tableTag",
						(List<MfCusCourtInfo>) mfCusCourtInfoFeign.findByPage(ipage).getResult(), null, true);
				
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo,relNo);// 更新资料完整度
				dataMap.put("mfCusCourtInfo", mfCusCourtInfo);
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
		MfCusCourtInfo mfCusCourtInfoJsp = new MfCusCourtInfo();
		setObjValue(formcusper00002, mfCusCourtInfoJsp);
		MfCusCourtInfo mfCusCourtInfo = mfCusCourtInfoFeign.getById(mfCusCourtInfoJsp);
		if(mfCusCourtInfo!=null){
			try{
				mfCusCourtInfo = (MfCusCourtInfo) EntityUtil.reflectionSetVal(mfCusCourtInfo, mfCusCourtInfoJsp, getMapByJson(ajaxData));
				mfCusCourtInfoFeign.update(mfCusCourtInfo);
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
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCourtInfo mfCusCourtInfo = new MfCusCourtInfo();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusCourtInfoAction").getAddModel();
			}
			FormData formcusCourtInfoBase = formService.getFormData(formId);
			getFormValue(formcusCourtInfoBase, map);
			if (this.validateFormData(formcusCourtInfoBase)) {

				setObjValue(formcusCourtInfoBase, mfCusCourtInfo);
				mfCusCourtInfoFeign.update(mfCusCourtInfo);

				String cusNo = mfCusCourtInfo.getCusNo();
				mfCusCourtInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusCourtInfo", mfCusCourtInfo));
				String tableHtml = jtu.getJsonStr("tablecusCourtInfoListBase", "tableTag",
						(List<MfCusCourtInfo>) mfCusCourtInfoFeign.findByPage(ipage).getResult(), null,
						true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
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
	public Map<String, Object> deleteAjax(String courtId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCourtInfo mfCusCourtInfo = new MfCusCourtInfo();
		mfCusCourtInfo.setCourtId(courtId);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// mfCusEquityInfo = (MfCusEquityInfo)JSONObject.toBean(jb,
			// MfCusEquityInfo.class);
			mfCusCourtInfoFeign.delete(mfCusCourtInfo);
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
	public Map<String, Object> listShowDetailAjax(String cusNo, String courtId) throws Exception {
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusCourtInfoAction");
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
			FormData formcusCourtInfo0003 = formService.getFormData(formId);
			MfCusCourtInfo mfCusCourtInfo = new MfCusCourtInfo();
			mfCusCourtInfo.setCourtId(courtId);
			mfCusCourtInfo = mfCusCourtInfoFeign.getById(mfCusCourtInfo);
			getObjValue(formcusCourtInfo0003, mfCusCourtInfo, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusCourtInfo0003, "propertySeeTag", "");
			if (mfCusCourtInfo != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusCourtInfo);
		}
		return dataMap;
	}
}
