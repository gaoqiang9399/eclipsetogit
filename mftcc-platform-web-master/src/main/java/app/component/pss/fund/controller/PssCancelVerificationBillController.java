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

import app.component.finance.util.CwPublicUtil;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.pss.conf.entity.PssConfig;
import app.component.pss.conf.feign.PssConfigFeign;
import app.component.pss.fund.entity.PssCancelVerificationBill;
import app.component.pss.fund.entity.PssSourceDetailBill;
import app.component.pss.fund.feign.PssCancelVerificationBillFeign;
import app.component.pss.information.entity.PssCustomerInfo;
import app.component.pss.information.entity.PssSupplierInfo;
import app.component.pss.information.feign.PssBillnoConfFeign;
import app.component.pss.information.feign.PssCustomerInfoFeign;
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
 * Title: PssCancelVerificationBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 27 14:24:51 CST 2017
 **/
@Controller
@RequestMapping("/pssCancelVerificationBill")
public class PssCancelVerificationBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssCancelVerificationBillFeign pssCancelVerificationBillFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
	// private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private PssCustomerInfoFeign pssCustomerInfoFeign;
	@Autowired
	private PssSupplierInfoFeign pssSupplierInfoFeign;
	@Autowired
	private PssBillnoConfFeign pssBillnoConfFeign;
	@Autowired
	private PssConfigFeign pssConfigFeign;
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
		JSONArray pssCancelTypedJsonArray = new CodeUtils().getJSONArrayByKeyName("PSS_CANCEL_TYPE");
		this.getHttpRequest().setAttribute("pssCancelTypedJsonArray", pssCancelTypedJsonArray);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssCancelVerificationBill_List";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCancelVerificationBill pssCancelVerificationBill = new PssCancelVerificationBill();
		try {
			pssCancelVerificationBill.setCustomQuery(ajaxData);
			pssCancelVerificationBill.setCustomSorts(ajaxData);
			pssCancelVerificationBill.setCriteriaList(pssCancelVerificationBill, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssCancelVerificationBillFeign.findByPage(ipage, pssCancelVerificationBill);
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
	 * 方法描述： 新增核销单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-20 下午02:56:06
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String cancelType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String date = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("currDate", date);
		// dataMap.put("cancelNo", "CV"+WaterIdUtil.getWaterId());
		dataMap.put("cancelNo", pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.HXD.getValue(), "view"));
		FormData formpsscancelverificationbill0002 = formService.getFormData("psscancelverificationbill0002");

		JSONObject json = new JSONObject();
		// 客户、供应商选择组件
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

		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
		pssSupplierInfo.setEnabledStatus(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssSupplierInfo> pssSuppList = pssSupplierInfoFeign.getAll(pssSupplierInfo);
		JSONArray pssSuppArray = new JSONArray();
		JSONObject pssSuppObj = null;
		for (PssSupplierInfo psi : pssSuppList) {
			pssSuppObj = new JSONObject();
			pssSuppObj.put("id", psi.getSupplierId());
			pssSuppObj.put("name", psi.getSupplierName());
			pssSuppArray.add(pssSuppObj);
		}
		json.put("supp", pssSuppArray);

		// 业务类别
		JSONArray cancelTypeListArray = new CodeUtils().getJSONArrayByKeyName("PSS_CANCEL_TYPE");
		JSONArray newCancelTypeListArray = new JSONArray();
		JSONObject cancelTypeObject = null;
		for (int i = 0; i < cancelTypeListArray.size(); i++) {
			cancelTypeObject = new JSONObject();
			cancelTypeObject.put("id", cancelTypeListArray.getJSONObject(i).getString("optCode"));
			cancelTypeObject.put("name", cancelTypeListArray.getJSONObject(i).getString("optName"));
			newCancelTypeListArray.add(cancelTypeObject);
		}
		json.put("cancelTypeArr", newCancelTypeListArray);

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

		if (null == cancelType || "".equals(cancelType)) {
			dataMap.put("cancelType", PssEnumBean.CANCEL_TYPE.TYPE1.getValue());
		} else {
			dataMap.put("cancelType", cancelType);
		}

		model.addAttribute("formpsscancelverificationbill0002", formpsscancelverificationbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssCancelVerificationBill_Input";
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
			FormData formpsscancelverificationbill0002 = formService.getFormData("psscancelverificationbill0002");
			getFormValue(formpsscancelverificationbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscancelverificationbill0002)) {
				PssCancelVerificationBill pssCancelVerificationBill = new PssCancelVerificationBill();
				setObjValue(formpsscancelverificationbill0002, pssCancelVerificationBill);

				/*
				 * PssCancelVerificationBill CVBTem =
				 * pssCancelVerificationBillFeign.getByIdOrNo(
				 * pssCancelVerificationBill); if(null != CVBTem){
				 * dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; }
				 */

				String cancelType = pssCancelVerificationBill.getCancelType();
				String msg = "";
				// 预收单据-预付单据-应收单据
				List<PssSourceDetailBill> bdTableList = PssPublicUtil.getMapByJsonObj(new PssSourceDetailBill(),
						jsonArrBD);
				bdTableList = PssPublicUtil.filterSepList(bdTableList, "getSourceBillNo");
				if (null == bdTableList || bdTableList.size() == 0) {
					// 预收单据-预付单据信息为空
					dataMap.put("flag", "error");
					if (PssEnumBean.CANCEL_TYPE.TYPE1.getValue().equals(cancelType)) {
						msg = "预收单据信息";
					} else if (PssEnumBean.CANCEL_TYPE.TYPE2.getValue().equals(cancelType)) {
						msg = "预付单据信息";
					} else if (PssEnumBean.CANCEL_TYPE.TYPE3.getValue().equals(cancelType)) {
						msg = "应收单据信息";
					}else {
					}
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage(msg));
					return dataMap;
				}
				// 应收单据-应付单据
				List<PssSourceDetailBill> rdTableList = PssPublicUtil.getMapByJsonObj(new PssSourceDetailBill(),
						jsonArrRD);
				rdTableList = PssPublicUtil.filterSepList(rdTableList, "getSourceBillNo");
				if (null == rdTableList || rdTableList.size() == 0) {
					// 应收单据-应付单据为空
					dataMap.put("flag", "error");
					if (PssEnumBean.CANCEL_TYPE.TYPE1.getValue().equals(cancelType)) {
						msg = "应收单据信息";
					} else if (PssEnumBean.CANCEL_TYPE.TYPE2.getValue().equals(cancelType)
							|| PssEnumBean.CANCEL_TYPE.TYPE3.getValue().equals(cancelType)) {
						msg = "应付单据信息";
					}else {
					}
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage(msg));
					return dataMap;
				}

				Map<String, String> reMap = pssCancelVerificationBillFeign.insert(pssCancelVerificationBill,
						bdTableList, rdTableList);
				dataMap.put("data", reMap);
				dataMap.put("cancelNo", pssCancelVerificationBill.getCancelNo());
				dataMap.put("cancelId", pssCancelVerificationBill.getCancelId());
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
	 * 方法描述： 核销单详情
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-30 下午02:56:06
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String cancelId,String cancelNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		PssCancelVerificationBill pssCancelVerificationBill = new PssCancelVerificationBill();
		pssCancelVerificationBill.setCancelId(cancelId);
		pssCancelVerificationBill.setCancelNo(cancelNo);
		pssCancelVerificationBill = pssCancelVerificationBillFeign.getByIdOrNo(pssCancelVerificationBill);

		FormData formpsscancelverificationbill0002 = formService.getFormData("psscancelverificationbill0002");
		getObjValue(formpsscancelverificationbill0002, pssCancelVerificationBill);
		dataMap.put("cancelType", pssCancelVerificationBill.getCancelType());

		JSONObject json = new JSONObject();
		// 客户、供应商选择组件
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

		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
		// pssSupplierInfo.setEnabledStatus(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssSupplierInfo> pssSuppList = pssSupplierInfoFeign.getAll(pssSupplierInfo);
		JSONArray pssSuppArray = new JSONArray();
		JSONObject pssSuppObj = null;
		for (PssSupplierInfo psi : pssSuppList) {
			pssSuppObj = new JSONObject();
			pssSuppObj.put("id", psi.getSupplierId());
			pssSuppObj.put("name", psi.getSupplierName());
			pssSuppArray.add(pssSuppObj);
		}
		json.put("supp", pssSuppArray);

		// 业务类别
		JSONArray cancelTypeListArray = new CodeUtils().getJSONArrayByKeyName("PSS_CANCEL_TYPE");
		JSONArray newCancelTypeListArray = new JSONArray();
		JSONObject cancelTypeObject = null;
		for (int i = 0; i < cancelTypeListArray.size(); i++) {
			cancelTypeObject = new JSONObject();
			cancelTypeObject.put("id", cancelTypeListArray.getJSONObject(i).getString("optCode"));
			cancelTypeObject.put("name", cancelTypeListArray.getJSONObject(i).getString("optName"));
			newCancelTypeListArray.add(cancelTypeObject);
		}
		json.put("cancelTypeArr", newCancelTypeListArray);

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

		model.addAttribute("formpsscancelverificationbill0002", formpsscancelverificationbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssCancelVerificationBill_Detail";
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
		PssCancelVerificationBill pssCancelVerificationBill = new PssCancelVerificationBill();
		try {
			FormData formpsscancelverificationbill0002 = formService.getFormData("psscancelverificationbill0002");
			getFormValue(formpsscancelverificationbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscancelverificationbill0002)) {
				pssCancelVerificationBill = new PssCancelVerificationBill();
				setObjValue(formpsscancelverificationbill0002, pssCancelVerificationBill);

				String cancelType = pssCancelVerificationBill.getCancelType();
				String msg = "";
				// 预收单据-预付单据-应收单据
				List<PssSourceDetailBill> bdTableList = PssPublicUtil.getMapByJsonObj(new PssSourceDetailBill(),
						jsonArrBD);
				bdTableList = PssPublicUtil.filterSepList(bdTableList, "getSourceBillNo");
				if (null == bdTableList || bdTableList.size() == 0) {
					// 预收单据-预付单据信息为空
					dataMap.put("flag", "error");
					if (PssEnumBean.CANCEL_TYPE.TYPE1.getValue().equals(cancelType)) {
						msg = "预收单据信息";
					} else if (PssEnumBean.CANCEL_TYPE.TYPE2.getValue().equals(cancelType)) {
						msg = "预付单据信息";
					} else if (PssEnumBean.CANCEL_TYPE.TYPE3.getValue().equals(cancelType)) {
						msg = "应收单据信息";
					}else {
					}
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage(msg));
					return dataMap;
				}
				// 应收单据-应付单据
				List<PssSourceDetailBill> rdTableList = PssPublicUtil.getMapByJsonObj(new PssSourceDetailBill(),
						jsonArrRD);
				rdTableList = PssPublicUtil.filterSepList(rdTableList, "getSourceBillNo");
				if (null == rdTableList || rdTableList.size() == 0) {
					// 应收单据-应付单据为空
					dataMap.put("flag", "error");
					if (PssEnumBean.CANCEL_TYPE.TYPE1.getValue().equals(cancelType)) {
						msg = "应收单据信息";
					} else if (PssEnumBean.CANCEL_TYPE.TYPE2.getValue().equals(cancelType)
							|| PssEnumBean.CANCEL_TYPE.TYPE3.getValue().equals(cancelType)) {
						msg = "应付单据信息";
					}else {
					}
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage(msg));
					return dataMap;
				}

				Map<String, String> reMap = pssCancelVerificationBillFeign.update(pssCancelVerificationBill,
						bdTableList, rdTableList);
				dataMap.put("data", reMap);
				dataMap.put("cancelNo", pssCancelVerificationBill.getCancelNo());
				dataMap.put("cancelId", pssCancelVerificationBill.getCancelId());
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
	public Map<String, Object> getByIdAjax(String cancelId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsscancelverificationbill0002 = formService.getFormData("psscancelverificationbill0002");
		PssCancelVerificationBill pssCancelVerificationBill = new PssCancelVerificationBill();
		pssCancelVerificationBill.setCancelId(cancelId);
		pssCancelVerificationBill = pssCancelVerificationBillFeign.getById(pssCancelVerificationBill);
		getObjValue(formpsscancelverificationbill0002, pssCancelVerificationBill, formData);
		if (pssCancelVerificationBill != null) {
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
	public Map<String, Object> deleteAjax(String cancelId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCancelVerificationBill pssCancelVerificationBill = new PssCancelVerificationBill();
		pssCancelVerificationBill.setCancelId(cancelId);
		try {
			pssCancelVerificationBill = pssCancelVerificationBillFeign.getById(pssCancelVerificationBill);
			if (null == pssCancelVerificationBill) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage() + "核销单信息不存在");
				return dataMap;
			}
			pssCancelVerificationBillFeign.delete(pssCancelVerificationBill);
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
	 * 方法描述： 批量删除核销单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-10-09 上午03:56:06
	 */
	@RequestMapping(value = "/deleteCBBatchAjax")
	@ResponseBody
	public Map<String, Object> deleteCBBatchAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Map<String, String> reMap = pssCancelVerificationBillFeign.deleteCBBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			//Log.error("核销单删除出错", e);
			throw e;
		}
		return dataMap;
	}

}
