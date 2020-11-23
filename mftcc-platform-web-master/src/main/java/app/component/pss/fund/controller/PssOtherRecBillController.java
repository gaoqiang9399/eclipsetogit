package app.component.pss.fund.controller;

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

import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.pss.conf.entity.PssConfig;
import app.component.pss.conf.feign.PssConfigFeign;
import app.component.pss.fund.entity.PssOtherRecBill;
import app.component.pss.fund.entity.PssOtherRecDetailBill;
import app.component.pss.fund.feign.PssOtherRecBillFeign;
import app.component.pss.information.entity.PssAccounts;
import app.component.pss.information.entity.PssCustomerInfo;
import app.component.pss.information.feign.PssAccountsFeign;
import app.component.pss.information.feign.PssBillnoConfFeign;
import app.component.pss.information.feign.PssCustomerInfoFeign;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssOtherRecBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Nov 15 15:12:57 CST 2017
 **/
@Controller
@RequestMapping("/pssOtherRecBill")
public class PssOtherRecBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssOtherRecBillFeign pssOtherRecBillFeign;
	@Autowired
	private PssBillnoConfFeign pssBillnoConfFeign;
	// private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private CwToolsFeign cwToolsFeign;
	@Autowired
	private PssConfigFeign pssConfigFeign;
	// 全局变量
	// 表单变量
	@Autowired
	private PssCustomerInfoFeign pssCustomerInfoFeign;
	@Autowired
	private PssAccountsFeign pssAccountsFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,PssOtherRecBill pssOtherRecBill) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssOtherRecBillFeign.getAll(pssOtherRecBill), null,true);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tableData", tableHtml);
	}

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
		return "/component/pss/fund/PssOtherRecBill_List";
	}

	@RequestMapping(value = "/saveOtherRecBillAjax")
	@ResponseBody
	public Map<String, Object> saveOtherRecBillAjax(String ajaxData,String pssOtherRecDetailsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherrecbill0002 = formService.getFormData("pssotherrecbill0002");
			getFormValue(formpssotherrecbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherrecbill0002)) {
				PssOtherRecBill pssOtherRecBill = new PssOtherRecBill();
				setObjValue(formpssotherrecbill0002, pssOtherRecBill);

				List<PssOtherRecDetailBill> pssOtherRecDetailBillList = PssPublicUtil
						.getMapByJsonObj(new PssOtherRecDetailBill(), pssOtherRecDetailsJson);

				boolean success = pssOtherRecBillFeign.doSaveOtherRecBill(pssOtherRecBill, pssOtherRecDetailBillList);
				String otherRecId = pssOtherRecBill.getOtherRecId();
				dataMap.put("otherRecId", otherRecId);
				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
				}
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", ex.getMessage());
			throw ex;
		}
		return dataMap;
	}

	@RequestMapping(value = "/auditOtherRecBillAjax")
	@ResponseBody
	public Map<String, Object> auditOtherRecBillAjax(String ajaxData,String pssOtherRecDetailsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherrecbill0002 = formService.getFormData("pssotherrecbill0002");
			getFormValue(formpssotherrecbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherrecbill0002)) {
				PssOtherRecBill pssOtherRecBill = new PssOtherRecBill();
				setObjValue(formpssotherrecbill0002, pssOtherRecBill);

				List<PssOtherRecDetailBill> pssOtherRecDetailBillList = PssPublicUtil
						.getMapByJsonObj(new PssOtherRecDetailBill(), pssOtherRecDetailsJson);

				boolean success = pssOtherRecBillFeign.doAuditOtherRecBill(pssOtherRecBill, pssOtherRecDetailBillList);
				String otherRecId = pssOtherRecBill.getOtherRecId();
				dataMap.put("otherRecId", otherRecId);
				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("其他收入单审核"));
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("其他收入单审核"));
				}
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", ex.getMessage());
			throw ex;
		}
		return dataMap;
	}

	@RequestMapping(value = "/reverseAuditOtherRecBillAjax")
	@ResponseBody
	public Map<String, Object> reverseAuditOtherRecBillAjax(String otherRecId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (otherRecId == null || "".equals(otherRecId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssOtherRecBill pssOtherRecBill = new PssOtherRecBill();
		pssOtherRecBill.setOtherRecId(otherRecId);
		try {
			boolean success = pssOtherRecBillFeign.doReverseAuditOtherRecBill(pssOtherRecBill);
			otherRecId = pssOtherRecBill.getOtherRecId();
			dataMap.put("otherRecId", otherRecId);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("其他收入单反审核"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("其他收入单反审核"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	@RequestMapping(value = "/batchAuditOtherRecBillAjax")
	@ResponseBody
	public Map<String, Object> batchAuditOtherRecBillAjax(String pssOtherRecBillsJson) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssOtherRecBill> pssOtherRecBillList = PssPublicUtil.getMapByJsonObj(new PssOtherRecBill(),
					pssOtherRecBillsJson);
			if (pssOtherRecBillList != null && pssOtherRecBillList.size() > 0) {
				String msg = "";
				for (PssOtherRecBill pssOtherRecBill : pssOtherRecBillList) {
					try {
						boolean success = pssOtherRecBillFeign.doAuditOtherRecBill(pssOtherRecBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("其他收入单[" + pssOtherRecBill.getOtherRecNo() + "]审核") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("其他收入单[" + pssOtherRecBill.getOtherRecNo() + "]审核") + "<br>";
						}
					} catch (Exception e) {
						msg += e.getMessage() + "<br>";
					}
				}

				dataMap.put("success", true);
				dataMap.put("msg", msg);
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/batchReverseAuditOtherRecBillAjax")
	@ResponseBody
	public Map<String, Object> batchReverseAuditOtherRecBillAjax(String pssOtherRecBillsJson) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssOtherRecBill> pssOtherRecBillList = PssPublicUtil.getMapByJsonObj(new PssOtherRecBill(),
					pssOtherRecBillsJson);
			if (pssOtherRecBillList != null && pssOtherRecBillList.size() > 0) {
				String msg = "";
				for (PssOtherRecBill pssOtherRecBill : pssOtherRecBillList) {
					try {
						boolean success = pssOtherRecBillFeign.doReverseAuditOtherRecBill(pssOtherRecBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("其他收入单[" + pssOtherRecBill.getOtherRecNo() + "]审核") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("其他收入单[" + pssOtherRecBill.getOtherRecNo() + "]审核") + "<br>";
						}
					} catch (Exception e) {
						msg += e.getMessage() + "<br>";
					}
				}

				dataMap.put("success", true);
				dataMap.put("msg", msg);
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/deleteOtherRecBillAjax")
	@ResponseBody
	public Map<String, Object> deleteOtherRecBillAjax(String otherRecId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		if (otherRecId == null || "".equals(otherRecId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssOtherRecBill pssOtherRecBill = new PssOtherRecBill();
		pssOtherRecBill.setOtherRecId(otherRecId);

		try {
			boolean success = pssOtherRecBillFeign.deleteOtherRecBill(pssOtherRecBill);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("其他收入单删除"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("其他收入单删除"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/batchDeleteOtherRecBillAjax")
	@ResponseBody
	public Map<String, Object> batchDeleteOtherRecBillAjax(String pssOtherRecBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssOtherRecBill> pssOtherRecBillList = PssPublicUtil.getMapByJsonObj(new PssOtherRecBill(),
					pssOtherRecBillsJson);
			if (pssOtherRecBillList != null && pssOtherRecBillList.size() > 0) {
				String msg = "";
				for (PssOtherRecBill pssOtherRecBill : pssOtherRecBillList) {
					try {
						boolean success = pssOtherRecBillFeign.deleteOtherRecBill(pssOtherRecBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("其他收入单[" + pssOtherRecBill.getOtherRecNo() + "]删除") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("其他收入单[" + pssOtherRecBill.getOtherRecNo() + "]删除") + "<br>";
						}
					} catch (Exception e) {
						msg += e.getMessage() + "<br>";
					}
				}
				dataMap.put("success", true);
				dataMap.put("msg", msg);
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getDetailPage")
	public String getDetailPage(Model model,String otherRecId,String otherRecNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherRecBill pssOtherRecBill = new PssOtherRecBill();
		if (StringUtil.isNotEmpty(otherRecId)) {
			pssOtherRecBill.setOtherRecId(otherRecId);
			pssOtherRecBill = pssOtherRecBillFeign.getById(pssOtherRecBill);
		} else if (StringUtil.isNotEmpty(otherRecNo)) {
			pssOtherRecBill.setOtherRecNo(otherRecNo);
			pssOtherRecBill = pssOtherRecBillFeign.getByOtherRecNo(pssOtherRecBill);
		} else {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键或其他收入单单号"));
		}

		if (pssOtherRecBill == null) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		} else {
			otherRecId = pssOtherRecBill.getOtherRecId();
			dataMap.put("cusNo", pssOtherRecBill.getCusNo());
			dataMap.put("billDate", DateUtil.getShowDateTime(pssOtherRecBill.getBillDate()));
			dataMap.put("auditStsed", pssOtherRecBill.getAuditSts());
			dataMap.put("otherRecNo", pssOtherRecBill.getOtherRecNo());
			FormData formpssotherrecbill0002 = formService.getFormData("pssotherrecbill0002");
			getObjValue(formpssotherrecbill0002, pssOtherRecBill);

			// 客户选择组件
			JSONObject json = new JSONObject();
			/**
			 * List<MfCusCustomer> cusList = mfCusCustomerFeign.getAllCus("");
			 * JSONArray cusArray = new JSONArray(); JSONObject cusObj = null;
			 * for (MfCusCustomer mcc:cusList) { JSONObject cusObj = new
			 * JSONObject(); cusObj.put("id", mcc.getCusNo());
			 * cusObj.put("name", mcc.getCusName()); cusArray.add(cusObj); }
			 **/

			PssCustomerInfo queryPssCustomerInfo = new PssCustomerInfo();
			queryPssCustomerInfo.setEnabledStatus(PssEnumBean.ENABLED_STATUS.ENABLED.getValue());
			List<PssCustomerInfo> pssCustomerInfoList = pssCustomerInfoFeign.getAll(queryPssCustomerInfo);
			JSONArray cusArray = new JSONArray();
			for (PssCustomerInfo pssCustomerInfo : pssCustomerInfoList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", pssCustomerInfo.getCusNo());
				jsonObject.put("name", pssCustomerInfo.getCusName());
				cusArray.add(jsonObject);
			}

			queryPssCustomerInfo.setEnabledStatus(PssEnumBean.ENABLED_STATUS.CLOSED.getValue());
			pssCustomerInfoList = pssCustomerInfoFeign.getAll(queryPssCustomerInfo);
			for (PssCustomerInfo pssCustomerInfo : pssCustomerInfoList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", pssCustomerInfo.getCusNo());
				jsonObject.put("name", pssCustomerInfo.getCusName() + "(已禁用)");
				cusArray.add(jsonObject);
			}
			json.put("cus", cusArray);

			// 结算账户组件
			// List<CwCashierAccount> cwCashierAccountList =
			// cwToolsFeign.getCwCashierAccountList(new CwCashierAccount());
			JSONArray settlementAccountListArray = new JSONArray();
			JSONObject settlementAccountJSONObject = new JSONObject();
			/*
			 * settlementAccountJSONObject.put("id", "-1");
			 * settlementAccountJSONObject.put("name", "(空)");
			 * settlementAccountListArray.add(settlementAccountJSONObject);
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

			// 结算方式
			JSONArray buyTypeListArray = new CodeUtils().getJSONArrayByKeyName("PSS_OTHER_REC_TYPE");
			JSONArray newBuyTypeListArray = new JSONArray();
			JSONObject clearAccTypeObject = null;
			for (int i = 0; i < buyTypeListArray.size(); i++) {
				clearAccTypeObject = new JSONObject();
				clearAccTypeObject.put("id", buyTypeListArray.getJSONObject(i).getString("optCode"));
				clearAccTypeObject.put("name", buyTypeListArray.getJSONObject(i).getString("optName"));
				newBuyTypeListArray.add(clearAccTypeObject);
			}
			json.put("buyType", newBuyTypeListArray);

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

			model.addAttribute("formpssotherrecbill0001", formpssotherrecbill0002);
			model.addAttribute("ajaxData", ajaxData);
			model.addAttribute("query", "");
		}
		return "/component/pss/fund/PssOtherRecBill_Input";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherRecBill pssOtherRecBill = new PssOtherRecBill();
		try {

			pssOtherRecBill.setCustomQuery(ajaxData);
			pssOtherRecBill.setCustomSorts(ajaxData);
			pssOtherRecBill.setCriteriaList(pssOtherRecBill, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssOtherRecBillFeign.findByPage(ipage, pssOtherRecBill);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);

		} catch (Exception ex) {
			ex.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", ex.getMessage());
			throw ex;
		}
		return dataMap;
	}

	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		String date = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("billDate", date);

		dataMap.put("otherRecNo", pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.QTSR.getValue(), "view"));
		FormData formpssotherrecbill0002 = formService.getFormData("pssotherrecbill0002");

		// 客户选择组件
		JSONObject json = new JSONObject();
		/**
		 * List<MfCusCustomer> cusList = mfCusCustomerFeign.getAllCus("");
		 * JSONArray cusArray = new JSONArray(); JSONObject cusObj = null; for
		 * (MfCusCustomer mcc:cusList) { JSONObject cusObj = new JSONObject();
		 * cusObj.put("id", mcc.getCusNo()); cusObj.put("name",
		 * mcc.getCusName()); cusArray.add(cusObj); }
		 **/

		PssCustomerInfo queryPssCustomerInfo = new PssCustomerInfo();
		queryPssCustomerInfo.setEnabledStatus(PssEnumBean.ENABLED_STATUS.ENABLED.getValue());
		List<PssCustomerInfo> pssCustomerInfoList = pssCustomerInfoFeign.getAll(queryPssCustomerInfo);
		JSONArray cusArray = new JSONArray();
		for (PssCustomerInfo pssCustomerInfo : pssCustomerInfoList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", pssCustomerInfo.getCusNo());
			jsonObject.put("name", pssCustomerInfo.getCusName());
			cusArray.add(jsonObject);
		}
		json.put("cus", cusArray);

		// 结算账户组件
		// List<CwCashierAccount> cwCashierAccountList =
		// cwToolsFeign.getCwCashierAccountList(new CwCashierAccount());
		JSONArray settlementAccountListArray = new JSONArray();
		JSONObject settlementAccountJSONObject = new JSONObject();
		/*
		 * settlementAccountJSONObject.put("id", "-1");
		 * settlementAccountJSONObject.put("name", "(空)");
		 * settlementAccountListArray.add(settlementAccountJSONObject);
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

		// 结算方式
		JSONArray buyTypeListArray = new CodeUtils().getJSONArrayByKeyName("PSS_OTHER_REC_TYPE");
		JSONArray newBuyTypeListArray = new JSONArray();
		JSONObject clearAccTypeObject = null;
		for (int i = 0; i < buyTypeListArray.size(); i++) {
			clearAccTypeObject = new JSONObject();
			clearAccTypeObject.put("id", buyTypeListArray.getJSONObject(i).getString("optCode"));
			clearAccTypeObject.put("name", buyTypeListArray.getJSONObject(i).getString("optName"));
			newBuyTypeListArray.add(clearAccTypeObject);
		}
		json.put("buyType", newBuyTypeListArray);

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

		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssOtherRecBill_Input";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpssotherrecbill0002 = formService.getFormData("pssotherrecbill0002");
		PssOtherRecBill pssOtherRecBill = new PssOtherRecBill();
		List<PssOtherRecBill> pssOtherRecBillList = pssOtherRecBillFeign.getAll(pssOtherRecBill);
		model.addAttribute("formpssotherrecbill0002", formpssotherrecbill0002);
		model.addAttribute("pssOtherRecBillList", pssOtherRecBillList);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssOtherRecBill_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherrecbill0002 = formService.getFormData("pssotherrecbill0002");
			getFormValue(formpssotherrecbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherrecbill0002)) {
				PssOtherRecBill pssOtherRecBill = new PssOtherRecBill();
				setObjValue(formpssotherrecbill0002, pssOtherRecBill);
				pssOtherRecBillFeign.insert(pssOtherRecBill);
				getTableData(tableId,pssOtherRecBill);// 获取列表
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherRecBill pssOtherRecBill = new PssOtherRecBill();
		try {
			FormData formpssotherrecbill0002 = formService.getFormData("pssotherrecbill0002");
			getFormValue(formpssotherrecbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherrecbill0002)) {
				pssOtherRecBill = new PssOtherRecBill();
				setObjValue(formpssotherrecbill0002, pssOtherRecBill);
				pssOtherRecBillFeign.update(pssOtherRecBill);
				getTableData(tableId,pssOtherRecBill);// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
	public Map<String, Object> getByIdAjax(String otherRecId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssotherrecbill0002 = formService.getFormData("pssotherrecbill0002");
		PssOtherRecBill pssOtherRecBill = new PssOtherRecBill();
		pssOtherRecBill.setOtherRecId(otherRecId);
		pssOtherRecBill = pssOtherRecBillFeign.getById(pssOtherRecBill);
		getObjValue(formpssotherrecbill0002, pssOtherRecBill, formData);
		if (pssOtherRecBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String otherRecId,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherRecBill pssOtherRecBill = new PssOtherRecBill();
		pssOtherRecBill.setOtherRecId(otherRecId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssOtherRecBill = (PssOtherRecBill) JSONObject.toBean(jb, PssOtherRecBill.class);
			pssOtherRecBillFeign.delete(pssOtherRecBill);
			getTableData(tableId,pssOtherRecBill);// 获取列表
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

}
