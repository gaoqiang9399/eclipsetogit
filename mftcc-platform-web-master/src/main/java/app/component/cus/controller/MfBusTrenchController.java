package app.component.cus.controller;

import app.base.SpringUtil;
import app.base.User;
import app.component.app.entity.MfBusAssureDetail;
import app.component.app.feign.MfBusAssureDetailFeign;
import app.component.assureamt.entity.MfBusAssureAmt;
import app.component.assureamt.feign.MfBusAssureAmtFeign;
import app.component.auth.entity.MfCusCreditContract;
import app.component.auth.feign.MfCusCreditContractFeign;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.cooperating.entity.MfCusCooperativeAgency;
import app.component.cus.cooperating.feign.MfCusCooperativeAgencyFeign;
import app.component.cus.courtinfo.entity.MfCusCourtInfo;
import app.component.cus.courtinfo.feign.MfCusCourtInfoFeign;
import app.component.cus.cusgroup.entity.MfCusGroup;
import app.component.cus.cusgroup.feign.MfCusGroupFeign;
import app.component.cus.dishonestinfo.entity.MfCusDishonestInfo;
import app.component.cus.dishonestinfo.feign.MfCusDishonestInfoFeign;
import app.component.cus.entity.MfBusAgencies;
import app.component.cus.entity.MfBusTrench;
import app.component.cus.entity.MfCusAssets;
import app.component.cus.entity.MfCusAssureCompany;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusBankAcceptanceBill;
import app.component.cus.entity.MfCusCoreCompany;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCorpMajorChange;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusEquityInfo;
import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusFarmerEconoInfo;
import app.component.cus.entity.MfCusFarmerIncExpe;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusGoods;
import app.component.cus.entity.MfCusGuaranteeOuter;
import app.component.cus.entity.MfCusHighInfo;
import app.component.cus.entity.MfCusLegalEmployInfo;
import app.component.cus.entity.MfCusLegalEquityInfo;
import app.component.cus.entity.MfCusLegalMember;
import app.component.cus.entity.MfCusListedInfo;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.entity.MfCusPersonAssetsInfo;
import app.component.cus.entity.MfCusPersonCorp;
import app.component.cus.entity.MfCusPersonCreditInfo;
import app.component.cus.entity.MfCusPersonDebtInfo;
import app.component.cus.entity.MfCusPersonIncExpe;
import app.component.cus.entity.MfCusPersonJob;
import app.component.cus.entity.MfCusPersonLiabilities;
import app.component.cus.entity.MfCusReputationInfo;
import app.component.cus.entity.MfCusSellInfo;
import app.component.cus.entity.MfCusShareholder;
import app.component.cus.entity.MfCusStaff;
import app.component.cus.entity.MfCusTable;
import app.component.cus.entity.MfCusType;
import app.component.cus.entity.MfCusWarehouse;
import app.component.cus.feign.MfBusAgenciesFeign;
import app.component.cus.feign.MfBusTrenchFeign;
import app.component.cus.feign.MfCusAssetsFeign;
import app.component.cus.feign.MfCusAssureCompanyFeign;
import app.component.cus.feign.MfCusBankAccManageFeign;
import app.component.cus.feign.MfCusBankAcceptanceBillFeign;
import app.component.cus.feign.MfCusCoreCompanyFeign;
import app.component.cus.feign.MfCusCorpBaseInfoFeign;
import app.component.cus.feign.MfCusCorpMajorChangeFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusEquityInfoFeign;
import app.component.cus.feign.MfCusFamilyInfoFeign;
import app.component.cus.feign.MfCusFarmerEconoInfoFeign;
import app.component.cus.feign.MfCusFarmerIncExpeFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusGoodsFeign;
import app.component.cus.feign.MfCusGuaranteeOuterFeign;
import app.component.cus.feign.MfCusHighInfoFeign;
import app.component.cus.feign.MfCusLegalEmployInfoFeign;
import app.component.cus.feign.MfCusLegalEquityInfoFeign;
import app.component.cus.feign.MfCusLegalMemberFeign;
import app.component.cus.feign.MfCusListedInfoFeign;
import app.component.cus.feign.MfCusPersBaseInfoFeign;
import app.component.cus.feign.MfCusPersonAssetsInfoFeign;
import app.component.cus.feign.MfCusPersonCorpFeign;
import app.component.cus.feign.MfCusPersonCreditInfoFeign;
import app.component.cus.feign.MfCusPersonDebtInfoFeign;
import app.component.cus.feign.MfCusPersonIncExpeFeign;
import app.component.cus.feign.MfCusPersonJobFeign;
import app.component.cus.feign.MfCusPersonLiabilitiesFeign;
import app.component.cus.feign.MfCusReputationInfoFeign;
import app.component.cus.feign.MfCusSellInfoFeign;
import app.component.cus.feign.MfCusShareholderFeign;
import app.component.cus.feign.MfCusStaffFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.component.cus.feign.MfCusTypeFeign;
import app.component.cus.feign.MfCusWarehouseFeign;
import app.component.cus.payment.entity.MfCusAssurePayment;
import app.component.cus.payment.feign.MfCusAssurePaymentFeign;
import app.component.cus.relation.entity.Child;
import app.component.cus.trenchsubsidiary.entity.MfShareProfitConfig;
import app.component.cus.trenchsubsidiary.entity.MfTrenchShareProfitRate;
import app.component.cus.trenchsubsidiary.feign.MfShareProfitConfigFeign;
import app.component.cus.trenchsubsidiary.feign.MfTrenchShareProfitRateFeign;
import app.component.cus.trenchsubsidiary.feign.MfTrenchUserFeign;
import app.component.cus.warehouse.entity.MfCusWarehouseOrg;
import app.component.cus.warehouse.feign.MfCusWarehouseOrgFeign;
import app.component.interfaces.mobileinterface.entity.WebCusLineReg;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.query.entity.MfQueryItem;
import app.component.query.feign.MfQueryItemFeign;
import app.component.sysInterface.SysInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfBusTrenchAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jul 29 17:36:57 CST 2017
 **/
@Controller
@RequestMapping("/mfBusTrench")
public class MfBusTrenchController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusTrenchFeign mfBusTrenchFeign;
	@Autowired
	private MfCusCreditContractFeign mfCusCreditContractFeign;
	@Autowired
	private MfCusTypeFeign mfCusTypeFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private BusViewInterfaceFeign busViewInterfaceFeign;
	@Autowired
	private MfCusShareholderFeign mfCusShareholderFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfCusHighInfoFeign mfCusHighInfoFeign;
	@Autowired
	private MfCusBankAccManageFeign mfCusBankAccManageFeign;
	@Autowired
	private MfBusAgenciesFeign mfBusAgenciesFeign;
	@Autowired
	private MfCusListedInfoFeign mfCusListedInfoFeign;
	@Autowired
	private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
	@Autowired
	private MfCusPersonLiabilitiesFeign mfCusPersonLiabilitiesFeign;
	@Autowired
	private MfCusFarmerEconoInfoFeign mfCusFarmerEconoInfoFeign;//
	@Autowired
	private MfCusFarmerIncExpeFeign mfCusFarmerIncExpeFeign;//
	@Autowired
	private MfCusReputationInfoFeign mfCusReputationInfoFeign;//
	@Autowired
	private MfCusAssetsFeign mfCusAssetsFeign;
	@Autowired
	private MfCusEquityInfoFeign mfCusEquityInfoFeign;
	@Autowired
	private MfCusLegalMemberFeign mfCusLegalMemberFeign;
	@Autowired
	private MfCusStaffFeign mfCusStaffFeign;
	@Autowired
	private MfCusPersonJobFeign mfCusPersonJobFeign;
	@Autowired
	private MfCusFamilyInfoFeign mfCusFamilyInfoFeign;
	@Autowired
	private MfCusBankAcceptanceBillFeign mfCusBankAcceptanceBillFeign;
	@Autowired
	private MfCusGuaranteeOuterFeign mfCusGuaranteeOuterFeign;
	@Autowired
	private MfCusGoodsFeign mfCusGoodsFeign;
	@Autowired
	private MfCusLegalEmployInfoFeign mfCusLegalEmployInfoFeign;
	@Autowired
	private MfCusLegalEquityInfoFeign mfCusLegalEquityInfoFeign;
	@Autowired
	private MfCusCorpMajorChangeFeign mfCusCorpMajorChangeFeign;
	@Autowired
	private MfCusPersonCorpFeign mfCusPersonCorpFeign;
	@Autowired
	private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;
	@Autowired
	private MfCusPersonIncExpeFeign mfCusPersonIncExpeFeign;
	@Autowired
	private MfCusPersonAssetsInfoFeign mfCusPersonAssetsInfoFeign;
	@Autowired
	private MfCusPersonDebtInfoFeign mfCusPersonDebtInfoFeign;
	@Autowired
	private MfCusPersonCreditInfoFeign mfCusPersonCreditInfoFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusWarehouseFeign mfCusWarehouseFeign;
	@Autowired
	private MfCusSellInfoFeign mfCusSellInfoFeign;
	@Autowired
	private MfCusAssureCompanyFeign mfCusAssureCompanyFeign;
	@Autowired
	private MfCusGroupFeign mfCusGroupFeign;
	@Autowired
	private MfCusCourtInfoFeign mfCusCourtInfoFeign;
	@Autowired
	private MfCusDishonestInfoFeign mfCusDishonestInfoFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfTrenchUserFeign mfTrenchUserFeign;
	@Autowired
	private MfTrenchShareProfitRateFeign mfTrenchShareProfitRateFeign;
	@Autowired
	private MfCusCoreCompanyFeign mfCusCoreCompanyFeign;
	@Autowired
	private MfBusAssureAmtFeign mfBusAssureAmtFeign;
	@Autowired
	private MfCusCooperativeAgencyFeign mfCusCooperativeAgencyFeign;
	@Autowired
	private MfQueryItemFeign mfQueryItemFeign;
	@Autowired
	private MfShareProfitConfigFeign mfShareProfitConfigFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
	@Autowired
	private MfCusAssurePaymentFeign mfCusAssurePaymentFeign;

	@Autowired
	private MfBusAssureDetailFeign mfBusAssureDetailFeign;
	@Autowired
	private MfCusWarehouseOrgFeign mfCusWarehouseOrgFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		// 获取tableId
		String tableId = "tabletrenchlist";
		MfCusFormConfig mc = mfCusFormConfigFeign.getByCusType("base", "MfBusTrenchAction");
		if (mc != null) {
			tableId = mc.getListModelDef();
		}
		model.addAttribute("tableId", tableId);
		// 获取展示名称
		MfQueryItem mfQueryItem = new MfQueryItem();
		mfQueryItem.setItemId("trench");
		mfQueryItem.setIsBase(BizPubParm.YES_NO_Y);
		mfQueryItem = mfQueryItemFeign.getById(mfQueryItem);
		model.addAttribute("itemName", mfQueryItem.getItemName());
		model.addAttribute("showName", mfQueryItem.getShowName());
		return "/component/cus/MfBusTrench_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, String tableId, String tableType)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusTrench mfBusTrench = new MfBusTrench();
		try {
			mfBusTrench.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusTrench.setCriteriaList(mfBusTrench, ajaxData);// 我的筛选
			mfBusTrench.setCustomSorts(ajaxData);// 自定义排序参数赋值
			// this.getRoleConditions(mfBusTrench,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfBusTrench", mfBusTrench));
			ipage = mfBusTrenchFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData) == null ? new HashMap() : getMapByJson(ajaxData);
			String formId = String.valueOf(map.get("formId"));
			FormData formtrench0002 = formService.getFormData(formId);
			getFormValue(formtrench0002, map);
			if (this.validateFormData(formtrench0002)) {
				MfBusTrench mfBusTrench = new MfBusTrench();
				setObjValue(formtrench0002, mfBusTrench);
				mfBusTrenchFeign.insert(mfBusTrench);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			// logger.error("渠道新增失败",e);
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap  = getMapByJson(ajaxData);
		MfBusTrench mfBusTrenchJsp = new MfBusTrench();
		FormService formService = new FormService();
		FormData formapply0006 = formService.getFormData(dataMap.get("formId").toString());
		getFormValue(formapply0006, getMapByJson(ajaxData));
		setObjValue(formapply0006, mfBusTrenchJsp);
		MfBusTrench mfBusTrench = mfBusTrenchFeign.getByUId(mfBusTrenchJsp);
		if (mfBusTrench != null) {
			try {
				mfBusTrench = (MfBusTrench) EntityUtil.reflectionSetVal(mfBusTrench, mfBusTrenchJsp,
						getMapByJson(ajaxData));
				new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusTrench);
				mfBusTrenchFeign.update(mfBusTrench);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusTrench mfBusTrench = new MfBusTrench();
		String formId = "";
		String query = "";
		request.setAttribute("ifBizManger", "3");
		try {
			Map<String, Object> paramMap = getMapByJson(ajaxData);
			Map<String, Object> map = getMapByJson(ajaxData) == null ? new HashMap() : getMapByJson(ajaxData);
			formId = String.valueOf(map.get("formId"));
			FormData formtrench0002 = formService.getFormData(formId);
			getFormValue(formtrench0002, map);
			if (this.validateFormData(formtrench0002)) {
				setObjValue(formtrench0002, mfBusTrench);
				mfBusTrenchFeign.update(mfBusTrench);
				Map<String, String> cusInfoMap = this.transViewBean(mfBusTrench.getTrenchUid());
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfBusTrench.getCusType(),
						"MfBusTrenchAction");
				if (mfCusFormConfig == null) {

				} else {
					formId = mfCusFormConfig.getShowModelDef();
				}
				FormData formToAjaxByOne = formService.getFormData(formId);
				if (formToAjaxByOne.getFormId() == null) {
					// logger.error("渠道商客户类型为" + mfBusTrench.getCusType() +
					// "的MfBusTrenchAction表单form" + formId + ".xml文件不存在");
				}
				getFormValue(formToAjaxByOne);
				getObjValue(formToAjaxByOne, mfBusTrench);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formToAjaxByOne, "propertySeeTag", query);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("cusInfo", cusInfoMap);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获得渠道基本信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2018-3-6 下午6:28:05
	 */
	@RequestMapping("/getTrenchBaseHtmlAjax")
	@ResponseBody
	public Map<String, Object> getTrenchBaseHtmlAjax(String trenchId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusTrench mfBusTrench = new MfBusTrench();
		request.setAttribute("ifBizManger", "3");
		String formId = "";
		try {
			mfBusTrench.setTrenchUid(trenchId);
			mfBusTrench = mfBusTrenchFeign.getByUId(mfBusTrench);
			MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfBusTrench.getCusType(),
					"MfBusTrenchAction");
			if (mfCusFormConfig == null) {

			} else {
				formId = mfCusFormConfig.getShowModelDef();
			}
			FormData formToAjaxByOne = formService.getFormData(formId);
			if (formToAjaxByOne.getFormId() == null) {
				// logger.error("渠道商客户类型为" + mfBusTrench.getCusType() +
				// "的MfBusTrenchAction表单form" + formId + ".xml文件不存在");
			}
			getFormValue(formToAjaxByOne);
			getObjValue(formToAjaxByOne, mfBusTrench);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(formToAjaxByOne, "propertySeeTag", "");
			dataMap.put("htmlStr", htmlStr);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String trenchId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtrench0002 = formService.getFormData("trench0002");
		MfBusTrench mfBusTrench = new MfBusTrench();
		mfBusTrench.setTrenchId(trenchId);
		mfBusTrench = mfBusTrenchFeign.getById(mfBusTrench);
		getObjValue(formtrench0002, mfBusTrench, formData);
		if (mfBusTrench != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
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
	public Map<String, Object> deleteAjax(String trenchId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusTrench mfBusTrench = new MfBusTrench();
		mfBusTrench.setTrenchId(trenchId);
		try {
			mfBusTrenchFeign.delete(mfBusTrench);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String typeNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String formId = "";
		if (StringUtil.isNotEmpty(typeNo)) {
			MfCusFormConfig mc = mfCusFormConfigFeign.getByCusType(typeNo, "MfBusTrenchAction");
			if (mc != null) {
				formId = mc.getAddModelDef();
			}
		} else {
			MfCusType mfCusType = new MfCusType();
			mfCusType.setBaseType(BizPubParm.CUS_BASE_TYPE_QUDAO);
			mfCusType.setUseFlag("1");
			List<MfCusType> list = mfCusTypeFeign.getAllList(mfCusType);

			if (list != null && list.size() > 0) {
				String cusType = list.get(0).getTypeNo();// 第一个会是表单的默认项
				MfCusFormConfig mc = mfCusFormConfigFeign.getByCusType(cusType, "MfBusTrenchAction");
				if (mc != null) {
					formId = mc.getAddModelDef();
				}
			}
		}
		if (StringUtil.isEmpty(formId)) {
			formId = "trench0002";
		}
		FormData formtrench0002 = formService.getFormData(formId);
		getFormValue(formtrench0002);
		MfBusTrench mfBusTrench = new MfBusTrench();
		mfBusTrench.setCusType(typeNo);
		getObjValue(formtrench0002, mfBusTrench);
		model.addAttribute("formtrench0002", formtrench0002);
		model.addAttribute("query", "");
		return "/component/cus/MfBusTrench_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtrench0002 = formService.getFormData("trench0002");
		getFormValue(formtrench0002);
		MfBusTrench mfBusTrench = new MfBusTrench();
		setObjValue(formtrench0002, mfBusTrench);
		mfBusTrenchFeign.insert(mfBusTrench);
		getObjValue(formtrench0002, mfBusTrench);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = new Ipage();
		ipage.setParams(this.setIpageParams("mfBusTrench", mfBusTrench));
		List<MfBusTrench> mfBusTrenchList = (List<MfBusTrench>) mfBusTrenchFeign.findByPage(this.getIpage())
				.getResult();
		model.addAttribute("formtrench0002", formtrench0002);
		model.addAttribute("mfBusTrenchList", mfBusTrenchList);
		model.addAttribute("query", "");
		return "/component/cus/MfBusTrench_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String trenchId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtrench0001 = formService.getFormData("trench0001");
		getFormValue(formtrench0001);
		MfBusTrench mfBusTrench = new MfBusTrench();
		mfBusTrench.setTrenchId(trenchId);
		mfBusTrench = mfBusTrenchFeign.getById(mfBusTrench);
		getObjValue(formtrench0001, mfBusTrench);
		model.addAttribute("formtrench0001", formtrench0001);
		model.addAttribute("query", "");
		return "/component/cus/MfBusTrench_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtrench0002 = formService.getFormData("trench0002");
		getFormValue(formtrench0002);
		boolean validateFlag = this.validateFormData(formtrench0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtrench0002 = formService.getFormData("trench0002");
		getFormValue(formtrench0002);
		boolean validateFlag = this.validateFormData(formtrench0002);
	}

	/**
	 * 方法描述： 获取渠道来源分页列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-7-29 下午5:43:01
	 */
	@RequestMapping(value = "/getChannelAjax")
	@ResponseBody
	public Map<String, Object> getChannelAjax(String ajaxData, int pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Ipage ipage = this.getIpage();
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		MfBusTrench mfBusTrench = new MfBusTrench();
		if (ajaxData != null) {
			mfBusTrench.setCustomQuery(ajaxData);// 自定义查询参数赋值
		}
		mfBusTrench.setTrenchOpNo(User.getRegNo(request));
		ipage.setParams(this.setIpageParams("mfBusTrench", mfBusTrench));
		ipage = mfBusTrenchFeign.getChannelListPage(ipage);
		dataMap.put("ipage", ipage);
		return dataMap;
	}

	/**
	 * 获取视角所需对象信息（头部最基础的信息） 渠道商、资金机构共用
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getByIdForViewAjax")
	@ResponseBody
	public Map<String, Object> getByIdForViewAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> cusInfoMap = this.transViewBean(cusNo);
		dataMap.put("cusInfo", cusInfoMap);
		return dataMap;
	}

	/**
	 * 调视角
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTrenchView")
	public String getTrenchView(Model model, String trenchId, String cusNo, String busEntrance) throws Exception {
		ActionContext.initialize(request, response);
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			String baseType = BizPubParm.CUS_BASE_TYPE_QUDAO;
			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("trenchId", trenchId);
			MfBusTrench mfBusTrench = new MfBusTrench();
			mfBusTrench.setTrenchId(trenchId);
			mfBusTrench.setTrenchUid(cusNo);
			mfBusTrench = mfBusTrenchFeign.getById(mfBusTrench);
			parmMap.put("cusType", mfBusTrench.getCusType());
			parmMap.put("trenchUid", mfBusTrench.getTrenchUid());// 参与其他关联都用的trenchUid
			parmMap.put("operable", "operable");// 底部显示待完善信息块
			baseType = mfBusTrench.getCusType().substring(0, 1);
			parmMap.put("baseType", baseType);
			String generalClass = "cus";
			parmMap.put("docParm", "cusNo=" + mfBusTrench.getTrenchUid() + "&relNo=" + mfBusTrench.getTrenchUid()
					+ "&scNo=" + BizPubParm.SCENCE_TYPE_DOC_CUS);
			Map<String, Object> cusViewMap = busViewInterfaceFeign.getCommonViewMap(generalClass, busEntrance, parmMap);
			dataMap.put("cusNo", mfBusTrench.getTrenchUid());
			dataMap.put("baseType", baseType);
			dataMap.putAll(cusViewMap);
			YmlConfig ymlConfig = (YmlConfig)SpringUtil.getBean(YmlConfig.class);
			String projectName = ymlConfig.getSysParams().get("sys.project.name");
			CodeUtils cu = new CodeUtils();
			Map<String,String> baseTypeMap = cu.getMapByKeyName("CUS_BASE_TYPE");
			model.addAttribute("baseTypeName", baseTypeMap.get(baseType));
			model.addAttribute("dataMap", dataMap);
			model.addAttribute("query", "");
			model.addAttribute("opNoType", BizPubParm.OP_NO_TYPE2);
			model.addAttribute("projectName", projectName);
			model.addAttribute("trenchName",mfBusTrench.getTrenchName());//二维码标题显示使用
		} catch (Exception e) {
			// logger.error("获取渠道商详情视角失败",e);
			e.printStackTrace();
			throw e;
		}
		return "/component/cus/commonview/MfCusCustomer_ComView";
	}

	/**
	 * 将实体对象转换为主视图页面需要的参数对象
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/transViewBean")
	@ResponseBody
	public Map<String, String> transViewBean(String cusNo) throws Exception {
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		Map<String, String> cusInfoMap = new HashMap<String, String>();
		if (mfCusCustomer != null) {
			// 名称
			cusInfoMap.put("cusName", mfCusCustomer.getCusName());
			// 基本类型
			cusInfoMap.put("cusBaseType", mfCusCustomer.getCusType().substring(0, 1));
			cusInfoMap.put("cusType", mfCusCustomer.getCusType());
			cusInfoMap.put("uId", mfCusCustomer.getCusNo());// 业务编号
			// 获取客户类型汉字
			MfCusType mfCusType = new MfCusType();
			mfCusType.setTypeNo(mfCusCustomer.getCusType());
			mfCusType = mfCusTypeFeign.getById(mfCusType);
			cusInfoMap.put("cusNameRate", mfCusType != null ? mfCusType.getTypeName() : "未知");
			// 对接人联系方式
			cusInfoMap.put("contactsTel", mfCusCustomer.getContactsTel());
			// 对接人姓名
			cusInfoMap.put("contactsName", mfCusCustomer.getContactsName());
			// 资料完整度
			cusInfoMap.put("infIntegrity", mfCusCustomer.getInfIntegrity());
			// 是否上传头像图片
			cusInfoMap.put("ifUploadHead", mfCusCustomer.getIfUploadHead());
			// 头像图片路径
			cusInfoMap.put("headImg", mfCusCustomer.getHeadImg());

			MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
			mfCusCorpBaseInfo.setCusNo(cusNo);
			mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
			if (mfCusCorpBaseInfo != null) {
				String cusAccountStatus = mfCusCorpBaseInfo.getAccountSts();
				cusInfoMap.put("cusAccountStatus", cusAccountStatus);
				if (cusAccountStatus != null && !"".equals(cusAccountStatus)) {
					Map<String, String> dicMap = new CodeUtils().getMapByKeyName("HM_ACCOUNT_STS");
					String cusAccountStatusName = dicMap.get(cusAccountStatus);
					cusInfoMap.put("cusAccountStatusName", cusAccountStatusName);
				}
			}
		}
		return cusInfoMap;
	}

	/**
	 * 取不同小类的客户类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCusTypeToShowAjax")
	@ResponseBody
	public Map<String, Object> getCusTypeToShowAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<>();
		String baseType = request.getParameter("baseType");
		MfCusType mfCusType = new MfCusType();
		mfCusType.setBaseType(baseType);
		mfCusType.setUseFlag("1");
		List<MfCusType> list = mfCusTypeFeign.getAllList(mfCusType);
		dataMap.put("cusTypeList", list);
		return dataMap;
	}

	/**
	 * 取不属于某个客户小类 不 对应的类型
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getCusTypeNotShowAjax")
	@ResponseBody
	public Map<String, Object> getCusTypeNotShowAjax(String baseTypes) throws Exception {
		ActionContext.initialize(request, response);
		MfCusType mfCusType = new MfCusType();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<String> resultList = Arrays.asList(baseTypes.split(","));// 将分隔的字符串解析成List集合
			mfCusType.setBaseType(resultList.get(0));
			List<MfCusType> list = mfCusTypeFeign.getAllListNotThisBaseType(mfCusType);
			if (resultList.size() > 0 && resultList != null) {
				for (String baseType : resultList) {
					for (int i = 0; i < list.size(); i++) {
						if ("0".equals(list.get(i).getUseFlag()) || baseType.equals(list.get(i).getBaseType())) {// 状态为0和传入的客户类型相同的移除集合
							list.remove(i);
						}
					}
				}
				dataMap.put("cusTypeList", list);

			} else {
				throw new Exception("获取客户类型时发生错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataMap;
	}

	/**
	 * 取主视图的展示表单 cusNo
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getCusTableListAjax")
	@ResponseBody
	@SuppressWarnings({ "unchecked", "unused" })
	public Map<String, Object> getCusTableListAjax() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		String query = "";
		String cusNo = request.getParameter("cusNo");// 渠道商传来的是trenchUid
		String cusType = request.getParameter("cusType");
		MfCusType mct = new MfCusType();
		mct.setTypeNo(cusType);
		mct = mfCusTypeFeign.getById(mct);
		String baseType = mct.getBaseType();
		request.setAttribute("ifBizManger", "3");
		// 查询已经录入信息的表单
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(cusNo);
		mfCusTable.setCusType(cusType);
		List<MfCusTable> cusTableList = mfCusTableFeign.getList(mfCusTable);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		JsonTableUtil jtu = new JsonTableUtil();
		for (int i = 0; i < cusTableList.size(); i++) {
			if ("0".equals(cusTableList.get(i).getDataFullFlag())) {
				continue;
			}
			String action = cusTableList.get(i).getAction();
			String htmlStr = "";
			if ("MfBusTrenchAction".equals(action)) {// 渠道商基本信息
				MfBusTrench mfBusTrench = new MfBusTrench();
				mfBusTrench.setTrenchUid(cusNo);
				mfBusTrench = mfBusTrenchFeign.getById(mfBusTrench);
				mfBusTrench.setCusNo(mfBusTrench.getTrenchUid());
				query = sysInterfaceFeign.getQueryResult(User.getRegNo(request));
				if(query == null){
					query = "";
				}
				FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfBusTrench);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfBusAgenciesAction".equals(action)) {// 资金机构基本信息
				MfBusAgencies mfBusAgencies = new MfBusAgencies();
				mfBusAgencies.setAgenciesUid(cusNo);
				mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);
				query = sysInterfaceFeign.getQueryResult(User.getRegNo(request));
				if(query == null){
					query = "";
				}
				FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfBusAgencies);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusCoreCompanyAction".equals(action)) {// 核心企业基本信息
				MfCusCoreCompany mfCusCoreCompany = new MfCusCoreCompany();
				mfCusCoreCompany.setCoreCompanyUid(cusNo);
				mfCusCoreCompany = mfCusCoreCompanyFeign.getById(mfCusCoreCompany);
				MfCusCreditContract mfCusCreditContract = mfCusCreditContractFeign.getNewestCusCreditContrac(cusNo);
				if (mfCusCreditContract != null) {
					mfCusCoreCompany.setCreditSum(mfCusCreditContract.getCreditSum());
					mfCusCoreCompany.setAuthBal(mfCusCreditContract.getAuthBal());
					mfCusCoreCompany.setIsCeilingLoop(mfCusCreditContract.getIsCeilingLoop());
				}
				FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCoreCompany);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusCorpBaseInfoAction".equals(action)) {
				MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				mfCusCorpBaseInfo.setCusNo(cusNo);
				mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
				Child child = mfCusCorpBaseInfoFeign.getLoanUseById(mfCusCorpBaseInfo);
				if (child != null) {
					mfCusCorpBaseInfo.setWayClassName(child.getName());
				}
				FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCorpBaseInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			} else if ("MfCusPersBaseInfoAction".equals(action)) {
				MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
				mfCusPersBaseInfo.setCusNo(cusNo);
				mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
				FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusPersBaseInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			}  else if ("MfShareProfitConfigAction".equals(action)) {// 分润配置信息
				MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
				mfShareProfitConfig.setCusNo(cusNo);
				mfShareProfitConfig = mfShareProfitConfigFeign.getById(mfShareProfitConfig);
				query = sysInterfaceFeign.getQueryResult(User.getRegNo(request));
				if(query == null){
					query = "";
				}
				FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfShareProfitConfig);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			} else if ("MfCusShareholderAction".equals(action)) {

				MfCusShareholder mfCusShareholder = new MfCusShareholder();
				mfCusShareholder.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusShareholder", mfCusShareholder));
				String tableFormId = "tableCusShareHolderList";
				if (StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())) {
					tableFormId = "table" + cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag",
						(List<MfCusShareholder>) mfCusShareholderFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusHighInfoAction".equals(action)) {

				MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
				mfCusHighInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusHighInfo", mfCusHighInfo));
				htmlStr = jtu.getJsonStr("tablecushigh00001", "tableTag",
						(List<MfCusHighInfo>) mfCusHighInfoFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusAssetsAction".equals(action)) {

				MfCusAssets mfCusAssets = new MfCusAssets();
				mfCusAssets.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusAssets", mfCusAssets));
				htmlStr = jtu.getJsonStr("tablecusassets00001", "tableTag",
						(List<MfCusAssets>) mfCusAssetsFeign.findByPage(ipage).getResult(), null, true);

			}  else if ("MfCusEquityInfoAction".equals(action)) {
				MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
				mfCusEquityInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusEquityInfo", mfCusEquityInfo));
				htmlStr = jtu.getJsonStr("tablecusequ00001", "tableTag",
						(List<MfCusEquityInfo>) mfCusEquityInfoFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusStaffAction".equals(action)) {
				MfCusStaff mfCusStaff = new MfCusStaff();
				mfCusStaff.setCusNo(cusNo);
				mfCusStaff = mfCusStaffFeign.getById(mfCusStaff);
				FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusStaff);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusLegalMemberAction".equals(action)) {
				MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
				mfCusLegalMember.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusLegalMember", mfCusLegalMember));
				htmlStr = jtu.getJsonStr("tablecuslegm00001", "tableTag",
						(List<MfCusLegalMember>) mfCusLegalMemberFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusBankAccManageAction".equals(action)) {
				MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
				mfCusBankAccManage.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusBankAccManage", mfCusBankAccManage));
				String tableFormId = "tablecusAccountListBase";
				if (StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())) {
					tableFormId = "table" + cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag",
						(List<MfCusBankAccManage>) mfCusBankAccManageFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusPersonCorpAction".equals(action)) {// 个人名下企业
				MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
				mfCusPersonCorp.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusPersonCorp", mfCusPersonCorp));
				htmlStr = jtu.getJsonStr("tablecusPersonCorpBase", "tableTag",
						(List<MfCusPersonCorp>) mfCusPersonCorpFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusPersonLiabilitiesAction".equals(action)) {// 个人资产负债表
				MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
				mfCusPersonLiabilities.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusPersonLiabilities", mfCusPersonLiabilities));
				htmlStr = jtu.getJsonStr("tablecusliabilitiesBase", "tableTag",
						(List<MfCusPersonLiabilities>) mfCusPersonLiabilitiesFeign.findByPage(ipage).getResult(), null,
						true);
			} else if ("MfCusWarehouseAction".equals(action)) {// 仓库信息
				MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
				mfCusWarehouse.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusWarehouse", mfCusWarehouse));
                String tableFormId = "tableCusWarehouseList";
                if (StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())) {
                    tableFormId = "table" + cusTableList.get(i).getListModelDef();
                }
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag",
						(List<MfCusWarehouse>) mfCusWarehouseFeign.findByPage(ipage).getResult(), null, true);
			}  else if ("MfCusWarehouseOrgAction".equals(action)) {// 仓储机构
                MfCusWarehouseOrg mfCusWarehouseOrg = new MfCusWarehouseOrg();
                mfCusWarehouseOrg.setCusNo(cusNo);
                mfCusWarehouseOrg = mfCusWarehouseOrgFeign.getById(mfCusWarehouseOrg);
                query = sysInterfaceFeign.getQueryResult(User.getRegNo(request));
                if(query == null){
                    query = "";
                }
                FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
                getFormValue(formcommon);
                getObjValue(formcommon, mfCusWarehouseOrg);
                htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
            }else if ("MfCusBankAcceptanceBillAction".equals(action)) {
				MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
				mfCusBankAcceptanceBill.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusBankAcceptanceBill", mfCusBankAcceptanceBill));
				htmlStr = jtu.getJsonStr("tablecusbankbill0001", "tableTag",
						(List<MfCusBankAcceptanceBill>) mfCusBankAcceptanceBillFeign.findByPage(ipage).getResult(),
						null, true);

			} else if ("MfCusGuaranteeOuterAction".equals(action)) {
				MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
				mfCusGuaranteeOuter.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusGuaranteeOuter", mfCusGuaranteeOuter));
				htmlStr = jtu.getJsonStr("tablecusguaranteeouter0001", "tableTag",
						(List<MfCusGuaranteeOuter>) mfCusGuaranteeOuterFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusGoodsAction".equals(action)) {
				MfCusGoods mfCusGoods = new MfCusGoods();
				mfCusGoods.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusGoods", mfCusGoods));
				htmlStr = jtu.getJsonStr("tablecusgoods0001", "tableTag",
						(List<MfCusGoods>) mfCusGoodsFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusListedInfoAction".equals(action)) {
				MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
				mfCusListedInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusListedInfo", mfCusListedInfo));
				htmlStr = jtu.getJsonStr("tablecuslistinfo0001", "tableTag",
						(List<MfCusListedInfo>) mfCusListedInfoFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusSellInfoAction".equals(action)) {
				MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
				mfCusSellInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusSellInfo", mfCusSellInfo));
				htmlStr = jtu.getJsonStr("tablecussell0001", "tableTag",
						(List<MfCusSellInfo>) mfCusSellInfoFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusPersonJobAction".equals(action)) {// 职业信息
				MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
				mfCusPersonJob.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusPersonJob", mfCusPersonJob));
				htmlStr = jtu.getJsonStr("tablecusjob00001", "tableTag",
						(List<MfCusGoods>) mfCusPersonJobFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusFamilyInfoAction".equals(action)) {// 个人客户社会关系
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusFamilyInfo", mfCusFamilyInfo));
				htmlStr = jtu.getJsonStr("tablecusfam00001", "tableTag",
						(List<MfCusGoods>) mfCusFamilyInfoFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusPersonAssetsInfoAction".equals(action)) {
				MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
				mfCusPersonAssetsInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusPersonAssetsInfo", mfCusPersonAssetsInfo));
				htmlStr = jtu.getJsonStr("tablecuspersassets0001", "tableTag",
						(List<MfCusPersonAssetsInfo>) mfCusPersonAssetsInfoFeign.findByPage(ipage).getResult(), null,
						true);
			} else if ("MfCusPersonDebtInfoAction".equals(action)) {
				MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
				mfCusPersonDebtInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusPersonDebtInfo", mfCusPersonDebtInfo));
				htmlStr = jtu.getJsonStr("tablecuspersdebt0001", "tableTag",
						(List<MfCusPersonDebtInfo>) mfCusPersonDebtInfoFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusPersonCreditInfoAction".equals(action)) {// 个人客户信用情况
				MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
				mfCusPersonCreditInfo.setCusNo(cusNo);
				mfCusPersonCreditInfo = mfCusPersonCreditInfoFeign.getById(mfCusPersonCreditInfo);
				// formcommon =
				// formService.getFormData(cusTableList.get(i).getShowModelDef());
				FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusPersonCreditInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusPersonIncExpeAction".equals(action)) {// 收支情况
				MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
				mfCusPersonIncExpe.setCusNo(cusNo);
				mfCusPersonIncExpe = mfCusPersonIncExpeFeign.getById(mfCusPersonIncExpe);
				// formcommon =
				// formService.getFormData(cusTableList.get(i).getShowModelDef());
				FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusPersonIncExpe);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusLegalEquityInfoAction".equals(action)) {// 法人对外投资
				MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
				mfCusLegalEquityInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusLegalEquityInfo", mfCusLegalEquityInfo));
				htmlStr = jtu.getJsonStr("tablecuslegalequ00001", "tableTag",
						(List<MfCusLegalEquityInfo>) mfCusLegalEquityInfoFeign.findByPage(ipage).getResult(), null,
						true);

			} else if ("MfCusLegalEmployInfoAction".equals(action)) {// 法人对外任职情况
				MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
				mfCusLegalEmployInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusLegalEmployInfo", mfCusLegalEmployInfo));
				htmlStr = jtu.getJsonStr("tablecusemploy00001", "tableTag",
						(List<MfCusLegalEmployInfo>) mfCusLegalEmployInfoFeign.findByPage(ipage).getResult(), null,
						true);

			} else if ("MfCusCorpMajorChangeAction".equals(action)) {// 企业重大变更
				MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
				mfCusCorpMajorChange.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusCorpMajorChange", mfCusCorpMajorChange));
				htmlStr = jtu.getJsonStr("tablecuschange0001", "tableTag",
						(List<MfCusCorpMajorChange>) mfCusCorpMajorChangeFeign.findByPage(ipage).getResult(), null,
						true);

			} else if ("MfCusFarmerEconoInfoAction".equals(action)) {// 农户经济情况
				MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
				mfCusFarmerEconoInfo.setCusNo(cusNo);
				mfCusFarmerEconoInfo = mfCusFarmerEconoInfoFeign.getById(mfCusFarmerEconoInfo);
				FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusFarmerEconoInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusFarmerIncExpeAction".equals(action)) {// 农户收支情况
				MfCusFarmerIncExpe mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
				mfCusFarmerIncExpe.setCusNo(cusNo);
				mfCusFarmerIncExpe = mfCusFarmerIncExpeFeign.getById(mfCusFarmerIncExpe);
				FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusFarmerIncExpe);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusReputationInfoAction".equals(action)) {// 农户信誉情况
				MfCusReputationInfo mfCusReputationInfo = new MfCusReputationInfo();
				mfCusReputationInfo.setCusNo(cusNo);
				mfCusReputationInfo = mfCusReputationInfoFeign.getById(mfCusReputationInfo);
				FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusReputationInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusAssureCompanyAction".equals(action)) {// 担保公司
				MfCusAssureCompany mfCusAssureCompany = new MfCusAssureCompany();
				mfCusAssureCompany.setCusNo(cusNo);
				mfCusAssureCompany = mfCusAssureCompanyFeign.getById(mfCusAssureCompany);

				FormData formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusAssureCompany);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfBusAssureDetailAction".equals(action)) {// 担保公司变更历史表
				MfBusAssureDetail mfBusAssureDetail = new MfBusAssureDetail();
				mfBusAssureDetail.setAssureCompanyId(cusNo);

				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfBusAssureDetail", mfBusAssureDetail));

				String tableFormId = "tablemfBusAssureDetailList";
				if (StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())) {
					tableFormId = "table" + cusTableList.get(i).getListModelDef();
				}

				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfBusAssureDetail>) mfBusAssureDetailFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfTrenchUserAction".equals(action)) {// 渠道操作员
				WebCusLineReg webCusLineReg = new WebCusLineReg();
				webCusLineReg.setChannelSourceNo(cusNo);
				String tableFormId = "tableTrenchUserList";
				if (StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())) {
					tableFormId = "table" + cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", mfTrenchUserFeign.getCusLineRegList(webCusLineReg),
						null, true);
			} else if ("MfCusGroupAction".equals(action)) {
				MfCusGroup mfCusGroup = new MfCusGroup();
				mfCusGroup.setGroupNo(cusNo);
				mfCusGroup = mfCusGroupFeign.getById(mfCusGroup);
				FormData formcusgroup = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcusgroup);
				getObjValue(formcusgroup, mfCusGroup);
				htmlStr = jsonFormUtil.getJsonStr(formcusgroup, "propertySeeTag", query);
			}else if ("MfCusCourtInfoAction".equals(action)) {// 法院信息

				MfCusCourtInfo mfCusCourtInfo = new MfCusCourtInfo();
				mfCusCourtInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusCourtInfo", mfCusCourtInfo));
				String tableFormId = "tablecusCourtInfoListBase";
				if (StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())) {
					tableFormId = "table" + cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag",
						(List<MfCusCourtInfo>) mfCusCourtInfoFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusDishonestInfoAction".equals(action)) {// 失信公告

				MfCusDishonestInfo mfCusDishonestInfo = new MfCusDishonestInfo();
				mfCusDishonestInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusDishonestInfo", mfCusDishonestInfo));
				String tableFormId = "tablecusDishonestInfoListBase";
				if (StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())) {
					tableFormId = "table" + cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag",
						(List<MfCusDishonestInfo>) mfCusDishonestInfoFeign.findByPage(ipage).getResult(), null, true);

			}else if ("MfTrenchShareProfitRateAction".equals(action)) {// 分润比例
				MfTrenchShareProfitRate mfTrenchShareProfitRate = new MfTrenchShareProfitRate();
				mfTrenchShareProfitRate.setTrenchUid(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfTrenchShareProfitRate", mfTrenchShareProfitRate));
				String tableFormId = "TrenchProfitRateList";//放款表单
				MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
				mfShareProfitConfig.setCusNo(cusNo);
				mfShareProfitConfig = mfShareProfitConfigFeign.getById(mfShareProfitConfig);
				if(mfShareProfitConfig != null){
					String calcBase = mfShareProfitConfig.getCalcBase();
					String calcCoefficient = mfShareProfitConfig.getCalcCoefficient();
					if(BizPubParm.MATCH_TYPE_1.equals(calcBase) || BizPubParm.MATCH_TYPE_3.equals(calcBase)){
						tableFormId = "TrenchProfitRateList_Count";//放款次数，客户数
						if(BizPubParm.CALC_COEFFICIENT_1.equals(calcCoefficient)){
							tableFormId = "TrenchProfitRateList_Count_Fixed";//放款次数，客户数
						}
					}else{
						if(BizPubParm.CALC_COEFFICIENT_1.equals(calcCoefficient)){
							tableFormId = "TrenchProfitRateList_Fixed";//放款次数，客户数
						}
					}
							
				}
				htmlStr = jtu.getJsonStr("table" + tableFormId, "tableTag",
						(List<MfTrenchShareProfitRate>) mfTrenchShareProfitRateFeign.findByPage(ipage).getResult(),
						null, true);

			} else if ("MfBusAssureAmtAction".equals(action)) {// 保证金管理
				MfBusAssureAmt mfBusAssureAmt = new MfBusAssureAmt();
				mfBusAssureAmt.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfBusAssureAmt", mfBusAssureAmt));
				String tableFormId = "tableassureamtlist";
				if (StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())) {
					tableFormId = "table" + cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag",
						(List<MfBusAssureAmt>) mfBusAssureAmtFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusCooperativeAgencyAction".equals(action)) {// 通用合作机构
				MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
				mfCusCooperativeAgency.setOrgaNo(cusNo);
				mfCusCooperativeAgency = mfCusCooperativeAgencyFeign.getById(mfCusCooperativeAgency);
				FormData formcuscoop = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcuscoop);
				getObjValue(formcuscoop, mfCusCooperativeAgency);
				htmlStr = jsonFormUtil.getJsonStr(formcuscoop, "propertySeeTag", query);

			}else if ("MfCusAssurePaymentAction".equals(action)) {//担保代偿功能
				MfCusAssurePayment mfCusAssurePayment = new MfCusAssurePayment();
				mfCusAssurePayment.setAssureNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusAssurePayment", mfCusAssurePayment));
				String tableFormId = "tableassurepaymentlist";
				if (StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())) {
					tableFormId = "table" + cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag",
						(List<MfCusAssurePayment>) mfCusAssurePaymentFeign.findPageByAssureNo(ipage).getResult(), null, true);

			}else {
			}
			cusTableList.get(i).setHtmlStr(htmlStr);
		}
		dataMap = new HashMap<String, Object>();
		dataMap.put("cusTableList", cusTableList);
		dataMap.put("flag", "success");
		return dataMap;
	}

	/**
	 * 渠道商的历史业务统计数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTrenchBusHisAjax")
	@ResponseBody
	public Map<String, Object> getTrenchBusHisAjax() {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String trenchUid = request.getParameter("trenchUid");
			Map<String, String> resulMap = mfBusTrenchFeign.getTrenchBusHisAjax(trenchUid);
			dataMap.put("flag", "success");
			dataMap.putAll(resulMap);
		} catch (Exception e) {
			// logger.error("获取渠道商历史数据失败，",e);
		}
		return dataMap;
	}

	/**
	 * 列表打开渠道额度预警页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTrenchWarnListPage")
	public String getTrenchWarnListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfBusTrench_TrenchWarnList";
	}

	/***
	 * 渠道额度预警列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findTrenchWarnByPageAjax")
	@ResponseBody
	public Map<String, Object> findTrenchWarnByPageAjax(String ajaxData, String tableId, String tableType,
			Integer pageNo, Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusTrench mfBusTrench = new MfBusTrench();
		try {
			mfBusTrench.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusTrench.setCriteriaList(mfBusTrench, ajaxData);// 我的筛选
			mfBusTrench.setCustomSorts(ajaxData);// 自定义排序参数赋值
			// this.getRoleConditions(mfBusTrench,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusTrench", mfBusTrench));
			ipage = mfBusTrenchFeign.findTrenchWarnByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/getTrenchData")
	@ResponseBody
	public Map<String, Object> getTrenchData() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusTrench mfBusTrench = new MfBusTrench();
		List<MfBusTrench> mfBusTrenchList = mfBusTrenchFeign.getMfBusTrenchList();

		JSONArray array = new JSONArray();
		if (mfBusTrenchList != null && mfBusTrenchList.size() > 0) {
			for (int i = 0; i < mfBusTrenchList.size(); i++) {
				JSONObject obj = new JSONObject();
				obj.put("id", mfBusTrenchList.get(i).getTrenchUid());
				obj.put("name", mfBusTrenchList.get(i).getTrenchName());
				array.add(obj);
			}
		}
		dataMap.put("items", array.toString());
		return dataMap;
	}

	/**
	 * 方法描述： 获得子渠道列表htmlStr
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2018-3-18 下午3:54:21
	 */
	@RequestMapping(value = "/getTrenchBusListHtmlStrAjax")
	@ResponseBody
	public Map<String, Object> getTrenchBusListHtmlStrAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		MfBusTrench mfBusTrench = new MfBusTrench();
		mfBusTrench.setTrenchUid((String) request.getSession().getAttribute("channelSourceNo"));
		mfBusTrench = mfBusTrenchFeign.getByUId(mfBusTrench);
		MfBusTrench busTrench = new MfBusTrench();
		busTrench.setTrenchHierarchyNo(mfBusTrench.getTrenchHierarchyNo());
		List<MfBusTrench> mfBusTrenchList = mfBusTrenchFeign.getChildTrenchList(busTrench);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tablechildTrenchList", "tableTag", mfBusTrenchList, null, true);
		dataMap.put("flag", "success");
		dataMap.put("tableHtml", tableHtml);

		return dataMap;
	}

	@RequestMapping(value = "/checkTrenchButtPhoneAjax")
	@ResponseBody
	public Map<String, Object> checkTrenchButtPhoneAjax(String trenchButtPhone) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusTrench mfBusTrench = new MfBusTrench();
		mfBusTrench.setTrenchButtPhone(trenchButtPhone);
		List<MfBusTrench> list = mfBusTrenchFeign.getBusTrenchList(mfBusTrench);
		if (list != null && list.size() > 0) {
			dataMap.put("flag", "success");
			dataMap.put("msg", "联系人电话");
		} else {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
	/**
	 * 方法描述： 打开渠道分润页面
	 * @param model
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018年9月4日 上午11:07:22
	 */
	@RequestMapping(value = "/trenchShareProfit")
	public String trenchShareProfit(Model model,String cusNo,String calcBase) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("calcBase", calcBase);
		model.addAttribute("cusNo", cusNo);
		return "/component/cus/TrenchShareProfit";
	}
	/**
	 * 方法描述： 渠道分润按照客户数
	 * @param model
	 * @param cusNo
	 * @param calcBase
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018年9月7日 上午10:28:55
	 */
	@RequestMapping(value = "/trenchShareProfitByCus")
	public String trenchShareProfitByCus(Model model,String cusNo,String calcBase) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("calcBase", calcBase);
		model.addAttribute("cusNo", cusNo);
		return "/component/cus/TrenchShareProfit";
	}

}
