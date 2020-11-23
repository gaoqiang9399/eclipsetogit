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

import app.base.User;
import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.finance.util.CwPublicUtil;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.pss.conf.entity.PssConfig;
import app.component.pss.conf.feign.PssConfigFeign;
import app.component.pss.fund.entity.PssReceiptBill;
import app.component.pss.fund.entity.PssReceiptDetailBill;
import app.component.pss.fund.entity.PssSourceDetailBill;
import app.component.pss.fund.feign.PssReceiptBillFeign;
import app.component.pss.information.entity.PssAccounts;
import app.component.pss.information.entity.PssCustomerInfo;
import app.component.pss.information.feign.PssAccountsFeign;
import app.component.pss.information.feign.PssBillnoConfFeign;
import app.component.pss.information.feign.PssCustomerInfoFeign;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.component.sys.entity.SysUser;
import app.component.sysInterface.SysInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssReceiptBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 20 13:28:45 CST 2017
 **/
@Controller
@RequestMapping("/pssReceiptBill")
public class PssReceiptBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssReceiptBillFeign pssReceiptBillFeign;
	@Autowired
	private CwToolsFeign cwToolsFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;

	// private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private PssCustomerInfoFeign pssCustomerInfoFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
	@Autowired
	private PssBillnoConfFeign pssBillnoConfFeign;
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
		return "/component/pss/fund/PssReceiptBill_List";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssReceiptBill pssReceiptBill = new PssReceiptBill();
		try {
			pssReceiptBill.setCustomQuery(ajaxData);
			pssReceiptBill.setCustomSorts(ajaxData);
			pssReceiptBill.setCriteriaList(pssReceiptBill, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssReceiptBillFeign.findByPage(ipage, pssReceiptBill);
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
	 * 方法描述： 新增收款单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-20 下午02:56:06
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String date = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("currDate", date);
		// dataMap.put("receiptNo", "R"+WaterIdUtil.getWaterId());
		dataMap.put("receiptNo", pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.SKD.getValue(), "view"));
		FormData formpssreceiptbill0002 = formService.getFormData("pssreceiptbill0002");

		JSONObject json = new JSONObject();
		// 客户选择组件
		/*
		 * List<MfCusCustomer> cusList = mfCusCustomerFeign.getAllCus("");
		 * JSONArray cusArray = new JSONArray(); JSONObject cusObj = null; for
		 * (MfCusCustomer mcc:cusList) { JSONObject cusObj = new JSONObject();
		 * cusObj.put("id", mcc.getCusNo()); cusObj.put("name",
		 * mcc.getCusName()); cusArray.add(cusObj); } json.put("cus", cusArray);
		 */
		PssCustomerInfo pssCustomerInfo = new PssCustomerInfo();
		pssCustomerInfo.setEnabledStatus(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssCustomerInfo> pssCustList = pssCustomerInfoFeign.getAll(pssCustomerInfo);
		JSONArray pssCustArray = new JSONArray();
		JSONObject pssCustObj = null;
		for (PssCustomerInfo pci : pssCustList) {
			pssCustObj = new JSONObject();
			pssCustObj.put("id", pci.getCusNo());
			pssCustObj.put("name", pci.getCusName());
			pssCustArray.add(pssCustObj);
		}
		json.put("cus", pssCustArray);

		// 收款人选择组件-操作员
		List<SysUser> sysUserList = sysInterfaceFeign.getAllUser(new SysUser());
		JSONArray sysUserArray = new JSONArray();
		for (SysUser sysUser : sysUserList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", sysUser.getOpNo());
			jsonObject.put("name", sysUser.getOpName());
			sysUserArray.add(jsonObject);
		}
		json.put("sysUser", sysUserArray);

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

		// 结算方式
		JSONArray clearAccTypeListArray = new CodeUtils().getJSONArrayByKeyName("PSS_CLEAR_ACC_TYPE");
		JSONArray newClearAccTypeListArray = new JSONArray();
		JSONObject clearAccTypeObject = null;
		for (int i = 0; i < clearAccTypeListArray.size(); i++) {
			clearAccTypeObject = new JSONObject();
			clearAccTypeObject.put("id", clearAccTypeListArray.getJSONObject(i).getString("optCode"));
			clearAccTypeObject.put("name", clearAccTypeListArray.getJSONObject(i).getString("optName"));
			newClearAccTypeListArray.add(clearAccTypeObject);
		}
		json.put("clearAccType", newClearAccTypeListArray);

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

		model.addAttribute("formpssreceiptbill0002", formpssreceiptbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssReceiptBill_Input";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String jsonArrBD,String jsonArrRD) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssreceiptbill0002 = formService.getFormData("pssreceiptbill0002");
			getFormValue(formpssreceiptbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssreceiptbill0002)) {
				PssReceiptBill pssReceiptBill = new PssReceiptBill();
				setObjValue(formpssreceiptbill0002, pssReceiptBill);
				// 校验单据编号不能重复
				/*
				 * PssReceiptBill RBTem =
				 * pssReceiptBillFeign.getByRBNo(pssReceiptBill); if(null !=
				 * RBTem){ dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; }
				 */
				// 收款单明细
				List<PssReceiptDetailBill> bdTableList = PssPublicUtil.getMapByJsonObj(new PssReceiptDetailBill(),
						jsonArrBD);
				bdTableList = PssPublicUtil.filterSepList(bdTableList, "getClearanceAccountId");
				if (null == bdTableList || bdTableList.size() == 0) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("收款单分录信息"));
					return dataMap;
				}
				// 源单明细
				List<PssSourceDetailBill> rdTableList = PssPublicUtil.getMapByJsonObj(new PssSourceDetailBill(),
						jsonArrRD);
				rdTableList = PssPublicUtil.filterSepList(rdTableList, "getSourceBillNo");

				Map<String, String> reMap = pssReceiptBillFeign.insert(pssReceiptBill, bdTableList, rdTableList);
				dataMap.put("data", reMap);
				dataMap.put("receiptNo", pssReceiptBill.getReceiptNo());
				dataMap.put("receiptId", pssReceiptBill.getReceiptId());
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
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 方法描述： 收款单详情
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-25 下午04:56:06
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String receiptId,String receiptNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);

		Map<String, Object> dataMap = new HashMap<String, Object>();

		PssReceiptBill pssReceiptBill = new PssReceiptBill();
		pssReceiptBill.setReceiptId(receiptId);
		pssReceiptBill.setReceiptNo(receiptNo);
		pssReceiptBill = pssReceiptBillFeign.getByIdOrNo(pssReceiptBill);

		FormData formpssreceiptbill0002 = formService.getFormData("pssreceiptbill0002");
		getObjValue(formpssreceiptbill0002, pssReceiptBill);
		dataMap.put("auditSts", pssReceiptBill.getAuditSts());

		JSONObject json = new JSONObject();
		// 客户选择组件
		/*
		 * List<MfCusCustomer> cusList = mfCusCustomerFeign.getAllCus("");
		 * JSONArray cusArray = new JSONArray(); JSONObject cusObj = null; for
		 * (MfCusCustomer mcc:cusList) { JSONObject cusObj = new JSONObject();
		 * cusObj.put("id", mcc.getCusNo()); cusObj.put("name",
		 * mcc.getCusName()); cusArray.add(cusObj); } json.put("cus", cusArray);
		 */
		PssCustomerInfo pssCustomerInfo = new PssCustomerInfo();
		// pssCustomerInfo.setEnabledStatus(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssCustomerInfo> pssCustList = pssCustomerInfoFeign.getAll(pssCustomerInfo);
		JSONArray pssCustArray = new JSONArray();
		JSONObject pssCustObj = null;
		for (PssCustomerInfo pci : pssCustList) {
			pssCustObj = new JSONObject();
			pssCustObj.put("id", pci.getCusNo());
			pssCustObj.put("name", pci.getCusName());
			pssCustArray.add(pssCustObj);
		}
		json.put("cus", pssCustArray);

		// 收款人选择组件-操作员
		List<SysUser> sysUserList = sysInterfaceFeign.getAllUser(new SysUser());
		JSONArray sysUserArray = new JSONArray();
		for (SysUser sysUser : sysUserList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", sysUser.getOpNo());
			jsonObject.put("name", sysUser.getOpName());
			sysUserArray.add(jsonObject);
		}
		json.put("sysUser", sysUserArray);

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

		// 结算方式
		JSONArray clearAccTypeListArray = new CodeUtils().getJSONArrayByKeyName("PSS_CLEAR_ACC_TYPE");
		JSONArray newClearAccTypeListArray = new JSONArray();
		JSONObject clearAccTypeObject = null;
		for (int i = 0; i < clearAccTypeListArray.size(); i++) {
			clearAccTypeObject = new JSONObject();
			clearAccTypeObject.put("id", clearAccTypeListArray.getJSONObject(i).getString("optCode"));
			clearAccTypeObject.put("name", clearAccTypeListArray.getJSONObject(i).getString("optName"));
			newClearAccTypeListArray.add(clearAccTypeObject);
		}
		json.put("clearAccType", newClearAccTypeListArray);

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

		model.addAttribute("formpssreceiptbill0002", formpssreceiptbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssReceiptBill_Detail";
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String jsonArrBD,String jsonArrRD) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssReceiptBill pssReceiptBill = new PssReceiptBill();
		try {
			FormData formpssreceiptbill0002 = formService.getFormData("pssreceiptbill0002");
			getFormValue(formpssreceiptbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssreceiptbill0002)) {
				pssReceiptBill = new PssReceiptBill();
				setObjValue(formpssreceiptbill0002, pssReceiptBill);

				// 收款单明细
				List<PssReceiptDetailBill> bdTableList = PssPublicUtil.getMapByJsonObj(new PssReceiptDetailBill(),
						jsonArrBD);
				bdTableList = PssPublicUtil.filterSepList(bdTableList, "getClearanceAccountId");
				if (null == bdTableList || bdTableList.size() == 0) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("收款单分录信息"));
					return dataMap;
				}
				// 源单明细
				List<PssSourceDetailBill> rdTableList = PssPublicUtil.getMapByJsonObj(new PssSourceDetailBill(),
						jsonArrRD);
				rdTableList = PssPublicUtil.filterSepList(rdTableList, "getSourceBillNo");

				Map<String, String> reMap = pssReceiptBillFeign.update(pssReceiptBill, bdTableList, rdTableList);
				dataMap.put("data", reMap);
				dataMap.put("receiptNo", pssReceiptBill.getReceiptNo());
				dataMap.put("receiptId", pssReceiptBill.getReceiptId());
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
	public Map<String, Object> getByIdAjax(String receiptId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssreceiptbill0002 = formService.getFormData("pssreceiptbill0002");
		PssReceiptBill pssReceiptBill = new PssReceiptBill();
		pssReceiptBill.setReceiptId(receiptId);
		pssReceiptBill = pssReceiptBillFeign.getById(pssReceiptBill);
		getObjValue(formpssreceiptbill0002, pssReceiptBill, formData);
		if (pssReceiptBill != null) {
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
	public Map<String, Object> deleteAjax(String receiptId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssReceiptBill pssReceiptBill = new PssReceiptBill();
		pssReceiptBill.setReceiptId(receiptId);
		try {
			pssReceiptBill = pssReceiptBillFeign.getById(pssReceiptBill);
			if (null == pssReceiptBill) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage() + "收款单信息不存在");
				return dataMap;
			}
			pssReceiptBillFeign.delete(pssReceiptBill);
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

	/**
	 * 方法描述： 批量删除收款单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-26 上午09:56:06
	 */
	@RequestMapping(value = "/deleteRBBatchAjax")
	@ResponseBody
	public Map<String, Object> deleteRBBatchAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Map<String, String> reMap = pssReceiptBillFeign.deleteRBBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			//Log.error("收款单删除出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 批量审核收款单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-10-12 下午03:56:06
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
			Map<String, String> reMap = pssReceiptBillFeign.auditBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			//Log.error("收款单批量审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 批量反审核收款单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-10-12 下午03:56:06
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
			Map<String, String> reMap = pssReceiptBillFeign.reAuditBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			//Log.error("收款单批量反审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 审核收款单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-10-12 下午01:56:06
	 */
	@RequestMapping(value = "/auditOrderAjax")
	@ResponseBody
	public Map<String, Object> auditOrderAjax(String ajaxData,String jsonArrBD,String jsonArrRD) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssreceiptbill0002 = formService.getFormData("pssreceiptbill0002");
			getFormValue(formpssreceiptbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssreceiptbill0002)) {
				PssReceiptBill pssReceiptBill = new PssReceiptBill();
				setObjValue(formpssreceiptbill0002, pssReceiptBill);

				// 校验单据编号不能重复
				/*
				 * if(null != pssReceiptBill.getReceiptId() &&
				 * !"".equals(pssReceiptBill.getReceiptId())){ PssReceiptBill
				 * rpb = pssReceiptBillFeign.getById(pssReceiptBill); if(null ==
				 * rpb){ PssReceiptBill rpbTem =
				 * pssReceiptBillFeign.getByIdOrNo(pssReceiptBill); if(null !=
				 * rpbTem){ dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; } } }else{ PssReceiptBill rpbTem =
				 * pssReceiptBillFeign.getByIdOrNo(pssReceiptBill); if(null !=
				 * rpbTem){ dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; } }
				 */

				// 收款单明细
				List<PssReceiptDetailBill> bdTableList = PssPublicUtil.getMapByJsonObj(new PssReceiptDetailBill(),
						jsonArrBD);
				bdTableList = PssPublicUtil.filterSepList(bdTableList, "getClearanceAccountId");
				if (null == bdTableList || bdTableList.size() == 0) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("收款单分录信息"));
					return dataMap;
				}
				// 源单明细
				List<PssSourceDetailBill> rdTableList = PssPublicUtil.getMapByJsonObj(new PssSourceDetailBill(),
						jsonArrRD);
				rdTableList = PssPublicUtil.filterSepList(rdTableList, "getSourceBillNo");

				Map<String, String> reMap = pssReceiptBillFeign.updateChk(pssReceiptBill, bdTableList, rdTableList);
				dataMap.put("data", reMap);
				dataMap.put("receiptNo", pssReceiptBill.getReceiptNo());
				dataMap.put("receiptId", pssReceiptBill.getReceiptId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			//Log.error("收款单审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 反审核收款单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-10-12 下午03:56:06
	 */
	@RequestMapping(value = "/reAuditOrderAjax")
	@ResponseBody
	public Map<String, Object> reAuditOrderAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssreceiptbill0002 = formService.getFormData("pssreceiptbill0002");
			getFormValue(formpssreceiptbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssreceiptbill0002)) {
				PssReceiptBill pssReceiptBill = new PssReceiptBill();
				setObjValue(formpssreceiptbill0002, pssReceiptBill);

				Map<String, String> reMap = pssReceiptBillFeign.updateChk(pssReceiptBill);
				dataMap.put("data", reMap);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			//Log.error("收款单反审核出错", e);
			throw e;
		}
		return dataMap;
	}

}
