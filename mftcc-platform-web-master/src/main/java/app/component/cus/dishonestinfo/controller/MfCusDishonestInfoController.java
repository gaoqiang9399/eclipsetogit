package app.component.cus.dishonestinfo.controller;

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
import app.component.cus.dishonestinfo.entity.MfCusDishonestInfo;
import app.component.cus.dishonestinfo.feign.MfCusDishonestInfoFeign;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.judgment.entity.MfCusJudgment;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

@Controller
@RequestMapping("/mfCusDishonestInfo")
public class MfCusDishonestInfoController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusDishonestInfoFeign mfCusDishonestInfoFeign;
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
		return "/component/cus/dishonestinfo/MfCusDishonestInfo_List";
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
		MfCusDishonestInfo mfCusDishonestInfo = new MfCusDishonestInfo();
		try {
			mfCusDishonestInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusDishonestInfo.setCriteriaList(mfCusDishonestInfo, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusDishonestInfo", mfCusDishonestInfo));
			//自定义查询Bo方法
			ipage = mfCusDishonestInfoFeign.findByPage(ipage);
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
		MfCusDishonestInfo MfCusDishonestInfo = new MfCusDishonestInfo();
		MfCusDishonestInfo.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),"MfCusDishonestInfoAction");
		String formId = "";
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusGuaranteeOuterAction表单信息没有查询到");
		} else {
			FormData formmfcusDishonestInfoBase = formService.getFormData(formId);
			if (formmfcusDishonestInfoBase.getFormId() == null) {
				/*
				 * logger.error("客户类型为" + mfCusCustomer.getCusType() +
				 * "的MfCusGuaranteeOuterAction表单form" + formId + ".xml文件不存在");
				 */
			} else { 
				getFormValue(formmfcusDishonestInfoBase);
				MfCusDishonestInfo.setCusName(mfCusCustomer.getCusName());
				getObjValue(formmfcusDishonestInfoBase, MfCusDishonestInfo);
			}
			model.addAttribute("formmfcusDishonestInfoBase", formmfcusDishonestInfoBase);
		}
//		model.addAttribute("cusName", mfCusCustomer.getCusName());
//		model.addAttribute("cusNo", mfCusCustomer.getCusNo());
		model.addAttribute("query", "");
		return "/component/cus/dishonestinfo/MfCusDishonestInfo_Insert";
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
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusDishonestInfoAction").getAddModel();
			}
			FormData cusDishonestInfoBase = formService.getFormData(formId);
			
//			FormData cusDishonestInfoBase = formService.getFormData("cusDishonestInfoBase");
//			FormData cusDishonestInfoDetail = formService.getFormData("cusDishonestInfoDetail");
			getFormValue(cusDishonestInfoBase, getMapByJson(ajaxData));
			if (this.validateFormData(cusDishonestInfoBase)) {
				MfCusDishonestInfo mfCusDishonestInfo = new MfCusDishonestInfo();
				mfCusDishonestInfo.setCurrentSessionOrgNo(User.getOrgNo(request));
				setObjValue(cusDishonestInfoBase, mfCusDishonestInfo);
				dataMap = mfCusDishonestInfoFeign.insert(mfCusDishonestInfo);
				String cusNo = mfCusDishonestInfo.getCusNo();
				String relNo = mfCusDishonestInfo.getCusNo();
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusDishonestInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String tableId = "cusDishonestInfoListBase";
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusDishonestInfoAction");				
				if(mfCusFormConfig != null){
					if(StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())){
						tableId = mfCusFormConfig.getListModelDef();
					}
				}
				
				mfCusDishonestInfo = new MfCusDishonestInfo();
				mfCusDishonestInfo.setCusNo(cusNo);
//				mfCusDishonestInfo.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusDishonestInfo", mfCusDishonestInfo));
				String tableHtml = jtu.getJsonStr("table"+tableId, "tableTag",
						(List<MfCusDishonestInfo>) mfCusDishonestInfoFeign.findByPage(ipage).getResult(), null, true);
				
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(cusNo,relNo);// 更新资料完整度
				dataMap.put("mfCusDishonestInfo", mfCusDishonestInfo);
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
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String dishonestId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusDishonestInfo mfCusDishonestInfo = new MfCusDishonestInfo();
		mfCusDishonestInfo.setDishonestId(dishonestId);
		FormData formcusDishonestInfoBase = formService.getFormData("cusDishonestInfoBase");
		String flag = "update";
		getFormValue(formcusDishonestInfoBase);
		mfCusDishonestInfo = mfCusDishonestInfoFeign.getById(mfCusDishonestInfo);
		getObjValue(formcusDishonestInfoBase, mfCusDishonestInfo);
		model.addAttribute("formcusDishonestInfoBase", formcusDishonestInfoBase);
		model.addAttribute("query", "");
		return "/component/cus/dishonestinfo/MfCusDishonestInfo_Detail";
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
		MfCusDishonestInfo mfCusDishonestInfo = new MfCusDishonestInfo();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusDishonestInfoAction").getAddModel();
			}
			FormData formcusCourtInfoBase = formService.getFormData(formId);
			getFormValue(formcusCourtInfoBase, map);
			if (this.validateFormData(formcusCourtInfoBase)) {

				setObjValue(formcusCourtInfoBase, mfCusDishonestInfo);
				mfCusDishonestInfoFeign.update(mfCusDishonestInfo);

				String cusNo = mfCusDishonestInfo.getCusNo();
				mfCusDishonestInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusDishonestInfo", mfCusDishonestInfo));
				String tableHtml = jtu.getJsonStr("tablecusDishonestInfoListBase", "tableTag",
						(List<MfCusDishonestInfo>) mfCusDishonestInfoFeign.findByPage(ipage).getResult(), null,
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
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String formId  = (String) getMapByJson(ajaxData).get("formId");
		FormData formcusper00002 = formService.getFormData(formId);
		getFormValue(formcusper00002, getMapByJson(ajaxData));
		MfCusDishonestInfo mfCusDishonestInfoJsp = new MfCusDishonestInfo();
		setObjValue(formcusper00002, mfCusDishonestInfoJsp);
		MfCusDishonestInfo mfCusDishonestInfo = mfCusDishonestInfoFeign.getById(mfCusDishonestInfoJsp);
		if(mfCusDishonestInfo!=null){
			try{
				mfCusDishonestInfo = (MfCusDishonestInfo) EntityUtil.reflectionSetVal(mfCusDishonestInfo, mfCusDishonestInfoJsp, getMapByJson(ajaxData));
				mfCusDishonestInfoFeign.update(mfCusDishonestInfo);
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
	public Map<String, Object> deleteAjax(String dishonestId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusDishonestInfo mfCusDishonestInfo = new MfCusDishonestInfo();
		mfCusDishonestInfo.setDishonestId(dishonestId);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// mfCusEquityInfo = (MfCusEquityInfo)JSONObject.toBean(jb,
			// MfCusEquityInfo.class);
			mfCusDishonestInfoFeign.delete(mfCusDishonestInfo);
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
	public Map<String, Object> listShowDetailAjax(String cusNo, String dishonestId) throws Exception {
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusDishonestInfoAction");
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
			FormData formcusDishonestInfo0003 = formService.getFormData(formId);
			MfCusDishonestInfo mfCusDishonestInfo = new MfCusDishonestInfo();
			mfCusDishonestInfo.setDishonestId(dishonestId);
			mfCusDishonestInfo = mfCusDishonestInfoFeign.getById(mfCusDishonestInfo);
			getObjValue(formcusDishonestInfo0003, mfCusDishonestInfo, formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcusDishonestInfo0003, "propertySeeTag", "");
			if (mfCusDishonestInfo != null) {
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", mfCusDishonestInfo);
		}
		return dataMap;
	}
	
}
