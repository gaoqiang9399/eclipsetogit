package app.component.pss.fund.controller;

import java.net.URLDecoder;
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
import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.finance.util.CwPublicUtil;
import app.component.pss.conf.entity.PssConfig;
import app.component.pss.conf.feign.PssConfigFeign;
import app.component.pss.fund.entity.PssOtherPayBill;
import app.component.pss.fund.entity.PssOtherPayDetailBill;
import app.component.pss.fund.feign.PssOtherPayBillFeign;
import app.component.pss.information.entity.PssAccounts;
import app.component.pss.information.entity.PssSupplierInfo;
import app.component.pss.information.feign.PssAccountsFeign;
import app.component.pss.information.feign.PssBillnoConfFeign;
import app.component.pss.information.feign.PssSupplierInfoFeign;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssOtherPayBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Nov 15 14:57:11 CST 2017
 **/
@Controller
@RequestMapping("/pssOtherPayBill")
public class PssOtherPayBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssOtherPayBillFeign pssOtherPayBillFeign;
	@Autowired
	private PssBillnoConfFeign pssBillnoConfFeign;
	@Autowired
	private PssSupplierInfoFeign pssSupplierInfoFeign;
	@Autowired
	private CwToolsFeign cwToolsFeign;
	@Autowired
	private PssConfigFeign pssConfigFeign;
	@Autowired
	private PssAccountsFeign pssAccountsFeign;

	// 全局变量
	// 表单变量
	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray pssAuditStsedJsonArray = new CodeUtils().getJSONArrayByKeyName("PSS_AUDIT_STSED");
		this.getHttpRequest().setAttribute("pssAuditStsedJsonArray", pssAuditStsedJsonArray);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssOtherPayBill_List";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherPayBill pssOtherPayBill = new PssOtherPayBill();
		try {
			pssOtherPayBill.setCustomQuery(ajaxData);
			pssOtherPayBill.setCustomSorts(ajaxData);
			pssOtherPayBill.setCriteriaList(pssOtherPayBill, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssOtherPayBillFeign.findByPage(ipage, pssOtherPayBill);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 新增其他支出单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-11-15 下午04:56:06
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String buySaleExpIds,String supplierId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String date = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("currDate", date);
		dataMap.put("otherPayNo", pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.QTZC.getValue(), "view"));
		dataMap.put("buySaleExpIds", buySaleExpIds);
		dataMap.put("supplierId", supplierId);
		FormData formpssotherpaybill0002 = formService.getFormData("pssotherpaybill0002");

		JSONObject json = new JSONObject();
		// 供应商选择组件
		/*
		 * List<MfCusCustomer> mfCusCustomerList =
		 * mfCusCustomerFeign.getAllCus(""); JSONArray supplierArray = new
		 * JSONArray(); JSONObject jsonObject = null; for (MfCusCustomer
		 * mfCusCustomer : mfCusCustomerList) { JSONObject jsonObject = new
		 * JSONObject(); jsonObject.put("id", mfCusCustomer.getCusNo());
		 * jsonObject.put("name", mfCusCustomer.getCusName());
		 * supplierArray.add(jsonObject); } json.put("supp", supplierArray);
		 */
		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
		pssSupplierInfo.setEnabledStatus(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssSupplierInfo> pssSuppList = pssSupplierInfoFeign.getAll(pssSupplierInfo);
		JSONArray pssSuppArray = new JSONArray();
		JSONObject pssSuppObj = null;
		boolean suppExist = false;
		if (supplierId != null && !"".equals(supplierId)) {
			suppExist = true;
		}
		for (PssSupplierInfo psi : pssSuppList) {
			pssSuppObj = new JSONObject();
			if (suppExist && supplierId.equals(psi.getSupplierId())) {
				suppExist = false;
			}
			pssSuppObj.put("id", psi.getSupplierId());
			pssSuppObj.put("name", psi.getSupplierName());
			pssSuppArray.add(pssSuppObj);
		}
		if (suppExist) {
			pssSupplierInfo = new PssSupplierInfo();
			pssSupplierInfo.setSupplierId(supplierId);
			pssSupplierInfo = pssSupplierInfoFeign.getById(pssSupplierInfo);
			if (pssSupplierInfo != null) {
				pssSuppObj = new JSONObject();
				pssSuppObj.put("id", pssSupplierInfo.getSupplierId());
				pssSuppObj.put("name", pssSupplierInfo.getSupplierName());
				pssSuppArray.add(pssSuppObj);
			}
		}
		json.put("supp", pssSuppArray);

		// 支出类别
		JSONArray saleTypeListArray = new CodeUtils().getJSONArrayByKeyName("PSS_SALE_TYPE");
		JSONArray newClearAccTypeListArray = new JSONArray();
		JSONObject saleTypeObject = null;
		for (int i = 0; i < saleTypeListArray.size(); i++) {
			saleTypeObject = new JSONObject();
			saleTypeObject.put("id", saleTypeListArray.getJSONObject(i).getString("optCode"));
			saleTypeObject.put("name", saleTypeListArray.getJSONObject(i).getString("optName"));
			newClearAccTypeListArray.add(saleTypeObject);
		}
		json.put("saleType", newClearAccTypeListArray);

		// 结算账户组件
		// List<CwCashierAccount> cwCashierAccountList =
		// cwToolsFeign.getCwCashierAccountList(new CwCashierAccount());
		JSONArray settlementAccountListArray = new JSONArray();
		JSONObject settlementAccountJSONObject = new JSONObject();
		// settlementAccountJSONObject.put("id", "-1");
		// settlementAccountJSONObject.put("name", "(空)");
		// settlementAccountListArray.add(settlementAccountJSONObject);
		/*
		 * for(CwCashierAccount cwCashierAccount : cwCashierAccountList) {
		 * JSONObject settlementAccountJSONObject = new JSONObject();
		 * settlementAccountJSONObject.put("id", cwCashierAccount.getUid());
		 * settlementAccountJSONObject.put("name",
		 * cwCashierAccount.getAccountName());
		 * settlementAccountListArray.add(settlementAccountJSONObject); }
		 */
		PssAccounts pssAccounts = new PssAccounts();
		List<PssAccounts> pssAccountsList = pssAccountsFeign.getAll(pssAccounts);
		for (PssAccounts pa : pssAccountsList) {
			settlementAccountJSONObject = new JSONObject();
			settlementAccountJSONObject.put("id", pa.getAccountId());
			settlementAccountJSONObject.put("name", pa.getAccountName());
			settlementAccountJSONObject.put("isDefault", pa.getIsDefaultAccount());
			settlementAccountListArray.add(settlementAccountJSONObject);
		}
		json.put("settlementAccount", settlementAccountListArray);

		// 系统配置信息
		PssConfig pssConfig = new PssConfig();
		List<PssConfig> pssConfigList = pssConfigFeign.getAll(pssConfig);
		if (pssConfigList != null && pssConfigList.size() > 0) {
			pssConfig = pssConfigList.get(0);
			if (pssConfig.getNumDecimalDigit() == null || "".equals(pssConfig.getNumDecimalDigit())) {
				pssConfig.setNumDecimalDigit("0");
			}
			if (pssConfig.getAmtDecimalDigit() == null || "".equals(pssConfig.getAmtDecimalDigit())) {
				pssConfig.setAmtDecimalDigit("2");
			}
			if (pssConfig.getDutyRate() == null) {
				pssConfig.setDutyRate(0.0);
			}
		} else {
			pssConfig.setNumDecimalDigit("0");
			pssConfig.setAmtDecimalDigit("2");
			pssConfig.setDutyRate(0.0);
		}
		json.put("pssConfig", pssConfig);

		String ajaxData = json.toString();

		model.addAttribute("formpssotherpaybill0002", formpssotherpaybill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssOtherPayBill_Input";
	}

	/**
	 * 方法描述：其他支出单详情
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-11-17 上午09:56:06
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String otherPayId,String otherPayNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);

		Map<String, Object> dataMap = new HashMap<String, Object>();

		PssOtherPayBill pssOtherPayBill = new PssOtherPayBill();
		pssOtherPayBill.setOtherPayId(otherPayId);
		if (null != otherPayNo && !"".equals(otherPayNo)) {
			pssOtherPayBill.setOtherPayNo(URLDecoder.decode(otherPayNo, "UTF-8"));
		}
		pssOtherPayBill = pssOtherPayBillFeign.getByIdOrNo(pssOtherPayBill);

		FormData formpssotherpaybill0002 = formService.getFormData("pssotherpaybill0002");
		getObjValue(formpssotherpaybill0002, pssOtherPayBill);
		dataMap.put("auditSts", pssOtherPayBill.getAuditSts());

		JSONObject json = new JSONObject();
		// 供应商选择组件
		/*
		 * List<MfCusCustomer> mfCusCustomerList =
		 * mfCusCustomerFeign.getAllCus(""); JSONArray supplierArray = new
		 * JSONArray(); JSONObject jsonObject = null; for (MfCusCustomer
		 * mfCusCustomer : mfCusCustomerList) { JSONObject jsonObject = new
		 * JSONObject(); jsonObject.put("id", mfCusCustomer.getCusNo());
		 * jsonObject.put("name", mfCusCustomer.getCusName());
		 * supplierArray.add(jsonObject); } json.put("supp", supplierArray);
		 */
		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
		pssSupplierInfo.setEnabledStatus(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssSupplierInfo> pssSuppList = pssSupplierInfoFeign.getAll(pssSupplierInfo);
		JSONArray pssSuppArray = new JSONArray();
		JSONObject pssSuppObj = null;
		boolean suppExist = false;
		if (pssOtherPayBill.getSupplierId() != null && !"".equals(pssOtherPayBill.getSupplierId())) {
			suppExist = true;
		}
		for (PssSupplierInfo psi : pssSuppList) {
			pssSuppObj = new JSONObject();
			if (suppExist && pssOtherPayBill.getSupplierId().equals(psi.getSupplierId())) {
				suppExist = false;
			}
			pssSuppObj.put("id", psi.getSupplierId());
			pssSuppObj.put("name", psi.getSupplierName());
			pssSuppArray.add(pssSuppObj);
		}
		if (suppExist) {
			pssSupplierInfo = new PssSupplierInfo();
			pssSupplierInfo.setSupplierId(pssOtherPayBill.getSupplierId());
			pssSupplierInfo = pssSupplierInfoFeign.getById(pssSupplierInfo);
			if (pssSupplierInfo != null) {
				pssSuppObj = new JSONObject();
				pssSuppObj.put("id", pssSupplierInfo.getSupplierId());
				pssSuppObj.put("name", pssSupplierInfo.getSupplierName());
				pssSuppArray.add(pssSuppObj);
			}
		}
		json.put("supp", pssSuppArray);

		// 支出类别
		JSONArray saleTypeListArray = new CodeUtils().getJSONArrayByKeyName("PSS_SALE_TYPE");
		JSONArray newClearAccTypeListArray = new JSONArray();
		JSONObject saleTypeObject = null;
		for (int i = 0; i < saleTypeListArray.size(); i++) {
			saleTypeObject = new JSONObject();
			saleTypeObject.put("id", saleTypeListArray.getJSONObject(i).getString("optCode"));
			saleTypeObject.put("name", saleTypeListArray.getJSONObject(i).getString("optName"));
			newClearAccTypeListArray.add(saleTypeObject);
		}
		json.put("saleType", newClearAccTypeListArray);

		// 结算账户组件
		// List<CwCashierAccount> cwCashierAccountList =
		// cwToolsFeign.getCwCashierAccountList(new CwCashierAccount());
		JSONArray settlementAccountListArray = new JSONArray();
		JSONObject settlementAccountJSONObject = new JSONObject();
		// settlementAccountJSONObject.put("id", "-1");
		// settlementAccountJSONObject.put("name", "(空)");
		// settlementAccountListArray.add(settlementAccountJSONObject);
		/*
		 * for(CwCashierAccount cwCashierAccount : cwCashierAccountList) {
		 * JSONObject settlementAccountJSONObject = new JSONObject();
		 * settlementAccountJSONObject.put("id", cwCashierAccount.getUid());
		 * settlementAccountJSONObject.put("name",
		 * cwCashierAccount.getAccountName());
		 * settlementAccountListArray.add(settlementAccountJSONObject); }
		 */
		PssAccounts pssAccounts = new PssAccounts();
		List<PssAccounts> pssAccountsList = pssAccountsFeign.getAll(pssAccounts);
		for (PssAccounts pa : pssAccountsList) {
			settlementAccountJSONObject = new JSONObject();
			settlementAccountJSONObject.put("id", pa.getAccountId());
			settlementAccountJSONObject.put("name", pa.getAccountName());
			settlementAccountListArray.add(settlementAccountJSONObject);
		}
		json.put("settlementAccount", settlementAccountListArray);

		// 系统配置信息
		PssConfig pssConfig = new PssConfig();
		List<PssConfig> pssConfigList = pssConfigFeign.getAll(pssConfig);
		if (pssConfigList != null && pssConfigList.size() > 0) {
			pssConfig = pssConfigList.get(0);
			if (pssConfig.getNumDecimalDigit() == null || "".equals(pssConfig.getNumDecimalDigit())) {
				pssConfig.setNumDecimalDigit("0");
			}
			if (pssConfig.getAmtDecimalDigit() == null || "".equals(pssConfig.getAmtDecimalDigit())) {
				pssConfig.setAmtDecimalDigit("2");
			}
			if (pssConfig.getDutyRate() == null) {
				pssConfig.setDutyRate(0.0);
			}
		} else {
			pssConfig.setNumDecimalDigit("0");
			pssConfig.setAmtDecimalDigit("2");
			pssConfig.setDutyRate(0.0);
		}
		json.put("pssConfig", pssConfig);

		String ajaxData = json.toString();

		model.addAttribute("formpssotherpaybill0002", formpssotherpaybill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssOtherPayBill_Detail";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherpaybill0002 = formService.getFormData("pssotherpaybill0002");
			getFormValue(formpssotherpaybill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherpaybill0002)) {
				PssOtherPayBill pssOtherPayBill = new PssOtherPayBill();
				setObjValue(formpssotherpaybill0002, pssOtherPayBill);
				List<PssOtherPayDetailBill> tableList = PssPublicUtil.getMapByJsonObj(new PssOtherPayDetailBill(),
						jsonArr);
				tableList = PssPublicUtil.filterSepList(tableList, "getSaleType");
				if (null == tableList || tableList.size() == 0) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				Map<String, String> reMap = pssOtherPayBillFeign.insert(pssOtherPayBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("otherPayNo", pssOtherPayBill.getOtherPayNo());
				dataMap.put("otherPayId", pssOtherPayBill.getOtherPayId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			//Log.error("其他支出单新增出错", e);
			throw e;
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
	public Map<String, Object> updateAjax(String ajaxData,String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherpaybill0002 = formService.getFormData("pssotherpaybill0002");
			getFormValue(formpssotherpaybill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherpaybill0002)) {
				PssOtherPayBill pssOtherPayBill = new PssOtherPayBill();
				setObjValue(formpssotherpaybill0002, pssOtherPayBill);
				List<PssOtherPayDetailBill> tableList = PssPublicUtil.getMapByJsonObj(new PssOtherPayDetailBill(),
						jsonArr);
				tableList = PssPublicUtil.filterSepList(tableList, "getSaleType");
				if (null == tableList || tableList.size() == 0) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				Map<String, String> reMap = pssOtherPayBillFeign.update(pssOtherPayBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("otherPayNo", pssOtherPayBill.getOtherPayNo());
				dataMap.put("otherPayId", pssOtherPayBill.getOtherPayId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			//Log.error("其他支出单保存出错", e);
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 方法描述： 审核其他支出单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-11-17 下午02:56:06
	 */
	@RequestMapping(value = "/auditOrderAjax")
	@ResponseBody
	public Map<String, Object> auditOrderAjax(String ajaxData,String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherpaybill0002 = formService.getFormData("pssotherpaybill0002");
			getFormValue(formpssotherpaybill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherpaybill0002)) {
				PssOtherPayBill pssOtherPayBill = new PssOtherPayBill();
				setObjValue(formpssotherpaybill0002, pssOtherPayBill);
				List<PssOtherPayDetailBill> tableList = PssPublicUtil.getMapByJsonObj(new PssOtherPayDetailBill(),
						jsonArr);
				tableList = PssPublicUtil.filterSepList(tableList, "getSaleType");
				if (null == tableList || tableList.size() == 0) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				Map<String, String> reMap = pssOtherPayBillFeign.updateChk(pssOtherPayBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("otherPayNo", pssOtherPayBill.getOtherPayNo());
				dataMap.put("otherPayId", pssOtherPayBill.getOtherPayId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			//Log.error("其他支出单审核出错", e);
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 方法描述： 反审核其他支出单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-11-17 下午03:56:06
	 */
	@RequestMapping(value = "/reAuditOrderAjax")
	@ResponseBody
	public Map<String, Object> reAuditOrderAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherpaybill0002 = formService.getFormData("pssotherpaybill0002");
			getFormValue(formpssotherpaybill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherpaybill0002)) {
				PssOtherPayBill pssOtherPayBill = new PssOtherPayBill();
				setObjValue(formpssotherpaybill0002, pssOtherPayBill);
				Map<String, String> reMap = pssOtherPayBillFeign.updateChk(pssOtherPayBill);
				dataMap.put("data", reMap);
				dataMap.put("otherPayNo", pssOtherPayBill.getOtherPayNo());
				dataMap.put("otherPayId", pssOtherPayBill.getOtherPayId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			//Log.error("其他支出单反审核出错", e);
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 方法描述： 批量删除其他支出单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-11-17 下午04:56:06
	 */
	@RequestMapping(value = "/deleteOPBBatchAjax")
	@ResponseBody
	public Map<String, Object> deleteOPBBatchAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Map<String, String> reMap = pssOtherPayBillFeign.deleteOPBBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			//Log.error("其他支出单删除出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 批量审核其他支出单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-11-17 下午04:56:06
	 */
	@RequestMapping(value = "/auditBatchAjax")
	@ResponseBody
	public Map<String, Object> auditBatchAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			formMap.put("regNo", User.getRegNo(request));
			formMap.put("regName", User.getRegName(request));
			formMap.put("orgNo", User.getOrgNo(request));
			formMap.put("orgName", User.getOrgName(request));
			Map<String, String> reMap = pssOtherPayBillFeign.auditBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			//Log.error("其他支出单批量审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 批量反审核其他支出单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-11-17 下午04:56:06
	 */
	@RequestMapping(value = "/reAuditBatchAjax")
	@ResponseBody
	public Map<String, Object> reAuditBatchAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
            formMap.put("regNo", User.getRegNo(request));
            formMap.put("regName", User.getRegName(request));
            formMap.put("orgNo", User.getOrgNo(request));
            formMap.put("orgName", User.getOrgName(request));
			Map<String, String> reMap = pssOtherPayBillFeign.reAuditBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			//Log.error("其他支出单批量反审核出错", e);
			throw e;
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
	public Map<String, Object> getByIdAjax(String otherPayId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssotherpaybill0002 = formService.getFormData("pssotherpaybill0002");
		PssOtherPayBill pssOtherPayBill = new PssOtherPayBill();
		pssOtherPayBill.setOtherPayId(otherPayId);
		pssOtherPayBill = pssOtherPayBillFeign.getById(pssOtherPayBill);
		getObjValue(formpssotherpaybill0002, pssOtherPayBill, formData);
		if (pssOtherPayBill != null) {
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
	public Map<String, Object> deleteAjax(String otherPayId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherPayBill pssOtherPayBill = new PssOtherPayBill();
		pssOtherPayBill.setOtherPayId(otherPayId);
		try {
			pssOtherPayBill = pssOtherPayBillFeign.getById(pssOtherPayBill);
			if (null == pssOtherPayBill) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage() + "其他支出单信息不存在");
				return dataMap;
			}
			pssOtherPayBillFeign.delete(pssOtherPayBill);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

}
