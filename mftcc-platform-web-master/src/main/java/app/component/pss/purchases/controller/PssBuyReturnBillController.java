package app.component.pss.purchases.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import app.component.pss.conf.entity.PssConfig;
import app.component.pss.conf.feign.PssConfigFeign;
import app.component.pss.fund.entity.PssBuySaleExpBill;
import app.component.pss.information.entity.PssAccounts;
import app.component.pss.information.entity.PssGoods;
import app.component.pss.information.entity.PssStorehouse;
import app.component.pss.information.entity.PssSupplierInfo;
import app.component.pss.information.feign.PssAccountsFeign;
import app.component.pss.information.feign.PssBillnoConfFeign;
import app.component.pss.information.feign.PssGoodsFeign;
import app.component.pss.information.feign.PssStorehouseFeign;
import app.component.pss.information.feign.PssSupplierInfoFeign;
import app.component.pss.purchases.entity.PssBuyBill;
import app.component.pss.purchases.entity.PssBuyOrder;
import app.component.pss.purchases.entity.PssBuyReturnBill;
import app.component.pss.purchases.entity.PssBuyReturnBillDetail;
import app.component.pss.purchases.feign.PssBuyBillFeign;
import app.component.pss.purchases.feign.PssBuyOrderFeign;
import app.component.pss.purchases.feign.PssBuyReturnBillFeign;
import app.component.pss.stockinterface.ExcelInterfaceFeign;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.component.sys.entity.SysUser;
import app.component.sysInterface.SysInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssBuyReturnBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Sep 14 14:19:26 CST 2017
 **/
@Controller
@RequestMapping("/pssBuyReturnBill")
public class PssBuyReturnBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssBuyReturnBillFeign pssBuyReturnBillFeign;
	@Autowired
	private PssGoodsFeign pssGoodsFeign;
	@Autowired
	private PssStorehouseFeign pssStorehouseFeign;
	@Autowired
	private PssBuyOrderFeign pssBuyOrderFeign;
	@Autowired
	private PssBuyBillFeign pssBuyBillFeign;
	@Autowired
	private PssAccountsFeign pssAccountsFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
	@Autowired
	private PssBillnoConfFeign pssBillnoConfFeign;
	@Autowired
	private PssConfigFeign pssConfigFeign;
	@Autowired
	private PssSupplierInfoFeign pssSupplierInfoFeign;
	@Autowired
	private ExcelInterfaceFeign excelInterfaceFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssBuyReturnBill pssBuyReturnBill) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssBuyReturnBillFeign.getAll(pssBuyReturnBill), null,
				true);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tableData", tableHtml);
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
		FormData formdl_pssbuyreturnbill02 = formService.getFormData("dl_pssbuyreturnbill02");
		PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
		List<PssBuyReturnBill> pssBuyReturnBillList = pssBuyReturnBillFeign.getAll(pssBuyReturnBill);
		model.addAttribute("formdl_pssbuyreturnbill02", formdl_pssbuyreturnbill02);
		model.addAttribute("pssBuyReturnBillList", pssBuyReturnBillList);
		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyReturnBill_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdl_pssbuyreturnbill02 = formService.getFormData("dl_pssbuyreturnbill02");
			getFormValue(formdl_pssbuyreturnbill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuyreturnbill02)) {
				PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
				setObjValue(formdl_pssbuyreturnbill02, pssBuyReturnBill);
				pssBuyReturnBillFeign.insert(pssBuyReturnBill);
				getTableData(tableId, pssBuyReturnBill);// 获取列表
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
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
		try {
			FormData formdl_pssbuyreturnbill02 = formService.getFormData("dl_pssbuyreturnbill02");
			getFormValue(formdl_pssbuyreturnbill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuyreturnbill02)) {
				pssBuyReturnBill = new PssBuyReturnBill();
				setObjValue(formdl_pssbuyreturnbill02, pssBuyReturnBill);
				pssBuyReturnBillFeign.update(pssBuyReturnBill);
				getTableData(tableId, pssBuyReturnBill);// 获取列表
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
	public Map<String, Object> getByIdAjax(String buyReturnBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_pssbuyreturnbill02 = formService.getFormData("dl_pssbuyreturnbill02");
		PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
		pssBuyReturnBill.setBuyReturnBillId(buyReturnBillId);
		pssBuyReturnBill = pssBuyReturnBillFeign.getById(pssBuyReturnBill);
		getObjValue(formdl_pssbuyreturnbill02, pssBuyReturnBill, formData);
		if (pssBuyReturnBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String buyReturnBillId, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
		pssBuyReturnBill.setBuyReturnBillId(buyReturnBillId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssBuyReturnBill = (PssBuyReturnBill) JSONObject.toBean(jb, PssBuyReturnBill.class);
			pssBuyReturnBillFeign.delete(pssBuyReturnBill);
			getTableData(tableId, pssBuyReturnBill);// 获取列表
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
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);

		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		CodeUtils codeUtils = new CodeUtils();
		JSONArray auditStsedJsonArray = codeUtils.getJSONArrayByKeyName("PSS_AUDIT_STSED");
		this.getHttpRequest().setAttribute("auditStsedJsonArray", auditStsedJsonArray);

		JSONArray refundStateJsonArray = codeUtils.getJSONArrayByKeyName("PSS_REFUND_STATE");
		this.getHttpRequest().setAttribute("refundStateJsonArray", refundStateJsonArray);

		List<SysUser> sysUserList = sysInterfaceFeign.getAllUser(new SysUser());
		JSONArray opNoJsonArray = new JSONArray();
		for (SysUser sysUser : sysUserList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("optCode", sysUser.getOpNo());
			jsonObject.put("optName", sysUser.getOpName());
			opNoJsonArray.add(jsonObject);
		}
		this.getHttpRequest().setAttribute("regOpNoJsonArray", opNoJsonArray);
		this.getHttpRequest().setAttribute("auditOpNoJsonArray", opNoJsonArray);
		this.getHttpRequest().setAttribute("lstModOpNoJsonArray", opNoJsonArray);

		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyReturnBill_List";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
		try {
			pssBuyReturnBill.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pssBuyReturnBill.setCustomSorts(ajaxData);// 自定义排序参数赋值
			pssBuyReturnBill.setCriteriaList(pssBuyReturnBill, ajaxData);// 我的筛选

			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = pssBuyReturnBillFeign.findByPage(ipage, pssBuyReturnBill);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 跳转到详情页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailPage")
	public String getDetailPage(Model model, String buyReturnBillId, String buyReturnBillNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
		if (StringUtil.isNotEmpty(buyReturnBillId)) {
			pssBuyReturnBill.setBuyReturnBillId(buyReturnBillId);
			pssBuyReturnBill = pssBuyReturnBillFeign.getById(pssBuyReturnBill);
		} else if (StringUtil.isNotEmpty(buyReturnBillNo)) {
			pssBuyReturnBill.setBuyReturnBillNo(buyReturnBillNo);
			pssBuyReturnBill = pssBuyReturnBillFeign.getByBuyReturnBillNo(pssBuyReturnBill);
		} else {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键或购货退货单号"));
		}

		if (pssBuyReturnBill == null) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		} else {
			buyReturnBillId = pssBuyReturnBill.getBuyReturnBillId();
			dataMap.put("supplierId", pssBuyReturnBill.getSupplierId());
			dataMap.put("billDate", DateUtil.getShowDateTime(pssBuyReturnBill.getBillDate()));
			dataMap.put("buyReturnBillNo", pssBuyReturnBill.getBuyReturnBillNo());
			dataMap.put("auditStsed", pssBuyReturnBill.getAuditStsed());
			FormData formdl_pssbuyreturnbill02 = formService.getFormData("dl_pssbuyreturnbill02");
			getObjValue(formdl_pssbuyreturnbill02, pssBuyReturnBill);

			JSONObject json = new JSONObject();
			CodeUtils codeUtils = new CodeUtils();
			// 供应商选择组件
			Map<String, PssSupplierInfo> supplierMap = new HashMap<String, PssSupplierInfo>();
			PssSupplierInfo queryPssSupplierInfo = new PssSupplierInfo();
			queryPssSupplierInfo.setEnabledStatus(PssEnumBean.ENABLED_STATUS.ENABLED.getValue());
			List<PssSupplierInfo> pssSupplierInfoList = pssSupplierInfoFeign.getAll(queryPssSupplierInfo);
			JSONArray supplierArray = new JSONArray();
			for (PssSupplierInfo pssSupplierInfo : pssSupplierInfoList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", pssSupplierInfo.getSupplierId());
				jsonObject.put("name", pssSupplierInfo.getSupplierName());
				jsonObject.put("taxRate", pssSupplierInfo.getTaxRate());
				jsonObject.put("enabledStatus", pssSupplierInfo.getEnabledStatus());
				supplierArray.add(jsonObject);
				supplierMap.put(pssSupplierInfo.getSupplierId(), pssSupplierInfo);
			}
			json.put("supplier", supplierArray);

			Map<String, PssSupplierInfo> disableSupplierMap = new HashMap<String, PssSupplierInfo>();
			queryPssSupplierInfo.setEnabledStatus(PssEnumBean.ENABLED_STATUS.CLOSED.getValue());
			List<PssSupplierInfo> disableSupplierList = pssSupplierInfoFeign.getAll(queryPssSupplierInfo);
			for (PssSupplierInfo pssSupplierInfo : disableSupplierList) {
				disableSupplierMap.put(pssSupplierInfo.getSupplierId(), pssSupplierInfo);
				supplierMap.put(pssSupplierInfo.getSupplierId(), pssSupplierInfo);
			}
			dataMap.put("disableSupplierMap", JSONObject.fromObject(disableSupplierMap));
			dataMap.put("supplierMap", JSONObject.fromObject(supplierMap));

			// 商品选择组件
			PssGoods queryPssGoods = new PssGoods();
			queryPssGoods.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
			List<PssGoods> goodsList = pssGoodsFeign.getAll(queryPssGoods);
			JSONArray goodsArray = new JSONArray();
			for (PssGoods pssGoods : goodsList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", pssGoods.getGoodsId());
				jsonObject.put("name", pssGoods.getGoodsName());
				jsonObject.put("goodsModel", pssGoods.getGoodsModel());
				jsonObject.put("goodsUnit", pssGoods.getGoodsUnit());
				jsonObject.put("unitId", pssGoods.getUnitId());
				jsonObject.put("storehouseId", pssGoods.getStorehouseId());
				jsonObject.put("estimatedPurchasePrice", pssGoods.getEstimatedPurchasePrice());
				jsonObject.put("flag", pssGoods.getFlag());
				goodsArray.add(jsonObject);
			}
			json.put("goods", goodsArray);

			Map<String, PssGoods> disableGoodsMap = new HashMap<String, PssGoods>();
			queryPssGoods.setFlag(PssEnumBean.YES_OR_NO.NO.getNum());
			List<PssGoods> disableGoodsList = pssGoodsFeign.getAll(queryPssGoods);
			for (PssGoods pssGoods : disableGoodsList) {
				disableGoodsMap.put(pssGoods.getGoodsId(), pssGoods);
			}
			dataMap.put("disableGoodsMap", JSONObject.fromObject(disableGoodsMap));

			// 单位选择组件
			/*
			 * JSONArray unitArray =
			 * codeUtils.getJSONArrayByKeyName("PSS_GOODS_UNIT"); for (int i =
			 * 0; i < unitArray.size(); i++) {
			 * unitArray.getJSONObject(i).put("id",
			 * unitArray.getJSONObject(i).getString("optCode"));
			 * unitArray.getJSONObject(i).put("name",
			 * unitArray.getJSONObject(i).getString("optName")); }
			 * json.put("unit", unitArray);
			 */

			// 仓库选择组件
			PssStorehouse queryPssStorehouse = new PssStorehouse();
			queryPssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
			List<PssStorehouse> storehouseList = pssStorehouseFeign.getAll(queryPssStorehouse);
			JSONArray storehouseArray = new JSONArray();
			for (PssStorehouse pssStorehouse : storehouseList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", pssStorehouse.getStorehouseId());
				jsonObject.put("name", pssStorehouse.getStorehouseName());
				jsonObject.put("flag", pssStorehouse.getFlag());
				storehouseArray.add(jsonObject);
			}
			json.put("storehouse", storehouseArray);

			Map<String, PssStorehouse> disableStorehouseMap = new HashMap<String, PssStorehouse>();
			queryPssStorehouse.setFlag(PssEnumBean.YES_OR_NO.NO.getNum());
			List<PssStorehouse> disableStorehouseList = pssStorehouseFeign.getAll(queryPssStorehouse);
			for (PssStorehouse pssStorehouse : disableStorehouseList) {
				disableStorehouseMap.put(pssStorehouse.getStorehouseId(), pssStorehouse);
			}
			dataMap.put("disableStorehouseMap", JSONObject.fromObject(disableStorehouseMap));

			// 结算账户组件
			/*
			 * List<CwCashierAccount> cwCashierAccountList =
			 * cwToolsFeign.getCwCashierAccountList(new CwCashierAccount());
			 * JSONArray settlementAccountListArray = new JSONArray();
			 * JSONObject settlementAccountJSONObject = new JSONObject();
			 * settlementAccountJSONObject.put("id", "-1");
			 * settlementAccountJSONObject.put("name", "(空)");
			 * settlementAccountListArray.add(settlementAccountJSONObject);
			 * for(CwCashierAccount cwCashierAccount : cwCashierAccountList) {
			 * JSONObject settlementAccountJSONObject = new JSONObject();
			 * settlementAccountJSONObject.put("id", cwCashierAccount.getUid());
			 * settlementAccountJSONObject.put("name",
			 * cwCashierAccount.getAccountName());
			 * settlementAccountListArray.add(settlementAccountJSONObject); }
			 * json.put("settlementAccount", settlementAccountListArray);
			 */

			PssAccounts queryPssAccounts = new PssAccounts();
			List<PssAccounts> pssAccountsList = pssAccountsFeign.getAll(queryPssAccounts);
			JSONArray settlementAccountListArray = new JSONArray();
			JSONObject settlementAccountJSONObject = new JSONObject();
			settlementAccountJSONObject.put("id", "-1");
			settlementAccountJSONObject.put("name", "(空)");
			for (PssAccounts pssAccounts : pssAccountsList) {
				settlementAccountJSONObject = new JSONObject();
				settlementAccountJSONObject.put("id", pssAccounts.getAccountId());
				settlementAccountJSONObject.put("name", pssAccounts.getAccountName());
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
			model.addAttribute("formdl_pssbuyreturnbill02", formdl_pssbuyreturnbill02);
			model.addAttribute("ajaxData", ajaxData);
		}

		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyReturnBill_Detail";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAddPage")
	public String getAddPage(Model model, String buyOrderId, String buyBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_pssbuyreturnbill02 = formService.getFormData("dl_pssbuyreturnbill02");
		String buyReturnBillNo = pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.GHT.getValue(), "view");
		dataMap.put("buyReturnBillNo", buyReturnBillNo);
		String currentDate = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("billDate", currentDate);

		if (buyOrderId != null && !"".equals(buyOrderId)) {
			// 由购货订单生成
			PssBuyOrder pssBuyOrder = new PssBuyOrder();
			pssBuyOrder.setBuyOrderId(buyOrderId);
			pssBuyOrder = pssBuyOrderFeign.getById(pssBuyOrder);
			if (pssBuyOrder != null) {
				PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
				pssBuyReturnBill.setSupplierId(pssBuyOrder.getSupplierId());
				pssBuyReturnBill.setBuyOrderNo(pssBuyOrder.getBuyOrderNo());
				pssBuyReturnBill.setMemo(pssBuyOrder.getMemo());
				pssBuyReturnBill.setDiscountRate(pssBuyOrder.getDiscountRate());
				pssBuyReturnBill.setDiscountAmount(pssBuyOrder.getDiscountAmount());
				pssBuyReturnBill.setDiscountAfterAmount(pssBuyOrder.getDiscountAfterAmount());
				pssBuyReturnBill.setThisRefund(0.0);
				pssBuyReturnBill.setDebt(pssBuyOrder.getDiscountAfterAmount());
				pssBuyReturnBill.setBuyRefundExpense(0.0);
				dataMap.put("supplierId", pssBuyReturnBill.getSupplierId());
				formdl_pssbuyreturnbill02 = formService.getFormData("dl_pssbuyreturnbill02");
				getObjValue(formdl_pssbuyreturnbill02, pssBuyReturnBill);
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		} else if (buyBillId != null && !"".equals(buyBillId)) {
			// 由购货单生成
			PssBuyBill pssBuyBill = new PssBuyBill();
			pssBuyBill.setBuyBillId(buyBillId);
			pssBuyBill = pssBuyBillFeign.getById(pssBuyBill);
			if (pssBuyBill != null) {
				PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
				pssBuyReturnBill.setSupplierId(pssBuyBill.getSupplierId());
				pssBuyReturnBill.setBuyBillNo(pssBuyBill.getBuyBillNo());
				pssBuyReturnBill.setMemo(pssBuyBill.getMemo());
				pssBuyReturnBill.setDiscountRate(pssBuyBill.getDiscountRate());
				pssBuyReturnBill.setDiscountAmount(pssBuyBill.getDiscountAmount());
				pssBuyReturnBill.setDiscountAfterAmount(pssBuyBill.getDiscountAfterAmount());
				pssBuyReturnBill.setThisRefund(0.0);
				pssBuyReturnBill.setDebt(pssBuyBill.getDiscountAfterAmount());
				pssBuyReturnBill.setBuyRefundExpense(0.0);
				dataMap.put("supplierId", pssBuyReturnBill.getSupplierId());
				formdl_pssbuyreturnbill02 = formService.getFormData("dl_pssbuyreturnbill02");
				getObjValue(formdl_pssbuyreturnbill02, pssBuyReturnBill);
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		}else {
		}

		JSONObject json = new JSONObject();
		CodeUtils codeUtils = new CodeUtils();
		// 供应商选择组件
		Map<String, PssSupplierInfo> supplierMap = new HashMap<String, PssSupplierInfo>();
		PssSupplierInfo queryPssSupplierInfo = new PssSupplierInfo();
		queryPssSupplierInfo.setEnabledStatus(PssEnumBean.ENABLED_STATUS.ENABLED.getValue());
		List<PssSupplierInfo> pssSupplierInfoList = pssSupplierInfoFeign.getAll(queryPssSupplierInfo);
		JSONArray supplierArray = new JSONArray();
		for (PssSupplierInfo pssSupplierInfo : pssSupplierInfoList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", pssSupplierInfo.getSupplierId());
			jsonObject.put("name", pssSupplierInfo.getSupplierName());
			jsonObject.put("taxRate", pssSupplierInfo.getTaxRate());
			jsonObject.put("enabledStatus", pssSupplierInfo.getEnabledStatus());
			supplierArray.add(jsonObject);
			supplierMap.put(pssSupplierInfo.getSupplierId(), pssSupplierInfo);
		}
		json.put("supplier", supplierArray);
		dataMap.put("supplierMap", JSONObject.fromObject(supplierMap));

		// 商品选择组件
		PssGoods queryPssGoods = new PssGoods();
		queryPssGoods.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssGoods> goodsList = pssGoodsFeign.getAll(queryPssGoods);
		JSONArray goodsArray = new JSONArray();
		for (PssGoods pssGoods : goodsList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", pssGoods.getGoodsId());
			jsonObject.put("name", pssGoods.getGoodsName());
			jsonObject.put("goodsModel", pssGoods.getGoodsModel());
			jsonObject.put("goodsUnit", pssGoods.getGoodsUnit());
			jsonObject.put("unitId", pssGoods.getUnitId());
			jsonObject.put("storehouseId", pssGoods.getStorehouseId());
			jsonObject.put("estimatedPurchasePrice", pssGoods.getEstimatedPurchasePrice());
			jsonObject.put("flag", pssGoods.getFlag());
			goodsArray.add(jsonObject);
		}
		json.put("goods", goodsArray);

		// 单位选择组件
		/*
		 * JSONArray unitArray =
		 * codeUtils.getJSONArrayByKeyName("PSS_GOODS_UNIT"); for (int i = 0; i
		 * < unitArray.size(); i++) { unitArray.getJSONObject(i).put("id",
		 * unitArray.getJSONObject(i).getString("optCode"));
		 * unitArray.getJSONObject(i).put("name",
		 * unitArray.getJSONObject(i).getString("optName")); } json.put("unit",
		 * unitArray);
		 */

		// 仓库选择组件
		PssStorehouse queryPssStorehouse = new PssStorehouse();
		queryPssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssStorehouse> storehouseList = pssStorehouseFeign.getAll(queryPssStorehouse);
		JSONArray storehouseArray = new JSONArray();
		for (PssStorehouse pssStorehouse : storehouseList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", pssStorehouse.getStorehouseId());
			jsonObject.put("name", pssStorehouse.getStorehouseName());
			jsonObject.put("flag", pssStorehouse.getFlag());
			storehouseArray.add(jsonObject);
		}
		json.put("storehouse", storehouseArray);

		// 结算账户组件
		/*
		 * List<CwCashierAccount> cwCashierAccountList =
		 * cwToolsFeign.getCwCashierAccountList(new CwCashierAccount());
		 * JSONArray settlementAccountListArray = new JSONArray(); JSONObject
		 * settlementAccountJSONObject = new JSONObject();
		 * settlementAccountJSONObject.put("id", "-1");
		 * settlementAccountJSONObject.put("name", "(空)");
		 * settlementAccountListArray.add(settlementAccountJSONObject);
		 * for(CwCashierAccount cwCashierAccount : cwCashierAccountList) {
		 * JSONObject settlementAccountJSONObject = new JSONObject();
		 * settlementAccountJSONObject.put("id", cwCashierAccount.getUid());
		 * settlementAccountJSONObject.put("name",
		 * cwCashierAccount.getAccountName());
		 * settlementAccountListArray.add(settlementAccountJSONObject); }
		 * json.put("settlementAccount", settlementAccountListArray);
		 */

		PssAccounts queryPssAccounts = new PssAccounts();
		List<PssAccounts> pssAccountsList = pssAccountsFeign.getAll(queryPssAccounts);
		JSONArray settlementAccountListArray = new JSONArray();
		JSONObject settlementAccountJSONObject = new JSONObject();
		settlementAccountJSONObject.put("id", "-1");
		settlementAccountJSONObject.put("name", "(空)");
		for (PssAccounts pssAccounts : pssAccountsList) {
			settlementAccountJSONObject = new JSONObject();
			settlementAccountJSONObject.put("id", pssAccounts.getAccountId());
			settlementAccountJSONObject.put("name", pssAccounts.getAccountName());
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

		model.addAttribute("formdl_pssbuyreturnbill01", formdl_pssbuyreturnbill02);
		model.addAttribute("query", "");
		return "PssBuyReturnBill_Detail";
	}

	@RequestMapping(value = "/getNewBuyReturnBillNoAjax")
	@ResponseBody
	public Map<String, Object> getNewBuyReturnBillNoAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String buyReturnBillNo = pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.GHT.getValue(), "view");
			dataMap.put("buyReturnBillNo", buyReturnBillNo);
			dataMap.put("success", true);
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("生成新的单据！复制数据"));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	/**
	 * 保存购货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveBuyReturnBillAjax")
	@ResponseBody
	public Map<String, Object> saveBuyReturnBillAjax(String ajaxData, String pssBuyReturnBillDetailsJson,
			String pssBuySaleExpBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdl_pssbuyreturnbill02 = formService.getFormData("dl_pssbuyreturnbill02");
			getFormValue(formdl_pssbuyreturnbill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuyreturnbill02)) {
				PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
				setObjValue(formdl_pssbuyreturnbill02, pssBuyReturnBill);

				List<PssBuyReturnBillDetail> pssBuyReturnBillDetailList = PssPublicUtil
						.getMapByJsonObj(new PssBuyReturnBillDetail(), pssBuyReturnBillDetailsJson);
				List<PssBuySaleExpBill> pssBuySaleExpBillList = PssPublicUtil.getMapByJsonObj(new PssBuySaleExpBill(),
						pssBuySaleExpBillsJson);

				boolean success = pssBuyReturnBillFeign.doSaveBuyReturnBill(pssBuyReturnBill,
						pssBuyReturnBillDetailList, pssBuySaleExpBillList);
				String buyReturnBillId = pssBuyReturnBill.getBuyReturnBillId();
				dataMap.put("buyReturnBillId", buyReturnBillId);
				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				}
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	/**
	 * 审核购货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditBuyReturnBillAjax")
	@ResponseBody
	public Map<String, Object> auditBuyReturnBillAjax(String ajaxData, String pssBuyReturnBillDetailsJson,
			String pssBuySaleExpBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdl_pssbuyreturnbill02 = formService.getFormData("dl_pssbuyreturnbill02");
			getFormValue(formdl_pssbuyreturnbill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuyreturnbill02)) {
				PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
				setObjValue(formdl_pssbuyreturnbill02, pssBuyReturnBill);

				List<PssBuyReturnBillDetail> pssBuyReturnBillDetailList = PssPublicUtil
						.getMapByJsonObj(new PssBuyReturnBillDetail(), pssBuyReturnBillDetailsJson);
				List<PssBuySaleExpBill> pssBuySaleExpBillList = PssPublicUtil.getMapByJsonObj(new PssBuySaleExpBill(),
						pssBuySaleExpBillsJson);

				boolean success = pssBuyReturnBillFeign.doAuditBuyReturnBill(pssBuyReturnBill);
				String buyReturnBillId = pssBuyReturnBill.getBuyReturnBillId();
				dataMap.put("buyReturnBillId", buyReturnBillId);
				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("购货退货单审核"));
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("购货退货单审核"));
				}
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	/**
	 * 批量审核购货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchAuditBuyReturnBillAjax")
	@ResponseBody
	public Map<String, Object> batchAuditBuyReturnBillAjax(String pssBuyReturnBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssBuyReturnBill> pssBuyReturnBillList = PssPublicUtil.getMapByJsonObj(new PssBuyReturnBill(),
					pssBuyReturnBillsJson);
			if (pssBuyReturnBillList != null && pssBuyReturnBillList.size() > 0) {
				String msg = "";
				for (PssBuyReturnBill pssBuyReturnBill : pssBuyReturnBillList) {
					try {
						boolean success = pssBuyReturnBillFeign.doAuditBuyReturnBill(pssBuyReturnBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("购货退货单[" + pssBuyReturnBill.getBuyReturnBillNo() + "]审核") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("购货退货单[" + pssBuyReturnBill.getBuyReturnBillNo() + "]审核") + "<br>";
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

	/**
	 * 反审核购货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reverseAuditBuyReturnBillAjax")
	@ResponseBody
	public Map<String, Object> reverseAuditBuyReturnBillAjax(String buyReturnBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (StringUtil.isEmpty(buyReturnBillId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
		pssBuyReturnBill.setBuyReturnBillId(buyReturnBillId);
		try {
			boolean success = pssBuyReturnBillFeign.doReverseAuditBuyReturnBill(pssBuyReturnBill);
			buyReturnBillId = pssBuyReturnBill.getBuyReturnBillId();
			dataMap.put("buyReturnBillId", buyReturnBillId);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("购货退货单反审核"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("购货退货单反审核"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	/**
	 * 批量反审核购货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchReverseAuditBuyReturnBillAjax")
	@ResponseBody
	public Map<String, Object> batchReverseAuditBuyReturnBillAjax(String pssBuyReturnBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssBuyReturnBill> pssBuyReturnBillList = PssPublicUtil.getMapByJsonObj(new PssBuyReturnBill(),
					pssBuyReturnBillsJson);
			if (pssBuyReturnBillList != null && pssBuyReturnBillList.size() > 0) {
				String msg = "";
				for (PssBuyReturnBill pssBuyReturnBill : pssBuyReturnBillList) {
					try {
						boolean success = pssBuyReturnBillFeign.doReverseAuditBuyReturnBill(pssBuyReturnBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("购货退货单[" + pssBuyReturnBill.getBuyReturnBillNo() + "]反审核") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("购货退货单[" + pssBuyReturnBill.getBuyReturnBillNo() + "]反审核") + "<br>";
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

	/**
	 * 删除购货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBuyReturnBillAjax")
	@ResponseBody
	public Map<String, Object> deleteBuyReturnBillAjax(String buyReturnBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (buyReturnBillId == null || "".equals(buyReturnBillId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssBuyReturnBill pssBuyReturnBill = new PssBuyReturnBill();
		pssBuyReturnBill.setBuyReturnBillId(buyReturnBillId);
		try {
			boolean success = pssBuyReturnBillFeign.deleteBuyReturnBill(pssBuyReturnBill);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("购货退货单删除"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("购货退货单删除"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	/**
	 * 批量删除购货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchDeleteBuyReturnBillAjax")
	@ResponseBody
	public Map<String, Object> batchDeleteBuyReturnBillAjax(String pssBuyReturnBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssBuyReturnBill> pssBuyReturnBillList = PssPublicUtil.getMapByJsonObj(new PssBuyReturnBill(),
					pssBuyReturnBillsJson);
			if (pssBuyReturnBillList != null && pssBuyReturnBillList.size() > 0) {
				String msg = "";
				for (PssBuyReturnBill pssBuyReturnBill : pssBuyReturnBillList) {
					try {
						boolean success = pssBuyReturnBillFeign.deleteBuyReturnBill(pssBuyReturnBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("购货退货单[" + pssBuyReturnBill.getBuyReturnBillNo() + "]删除") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("购货退货单[" + pssBuyReturnBill.getBuyReturnBillNo() + "]删除") + "<br>";
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

	/**
	 * 导入
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/importExcel")
	public String importExcel(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("downloadTmplate", "PssBuyReturnBillAction_downloadTemplate.action");
		dataMap.put("serverUrl", "PssBuyReturnBillActionAjax_uploadExcelAjax.action");
		dataMap.put("downloadErrorUrl", "PssBuyReturnBillAction_downloadErrorExcel.action");

		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/pss/import/PssBills_ImportExcel";
	}

	/**
	 * 下载导入模板EXCEL
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadTemplate")
	public void downloadTemplate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		try {
			// HttpServletResponse response = response;

			String excelName = "购货退货单导入模板";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			String type = "PssBuyReturnBill_import";
			HSSFWorkbook pssWorkBook = excelInterfaceFeign.getDownFileStream(type);
			// 输出流
			ServletOutputStream out = response.getOutputStream();
			pssWorkBook.write(out);
			response.flushBuffer();
			out.flush();
			out.close();

		} catch (Exception e) {
			// Log.error("PssBuyReturnBillAction_downloadTemplate方法出错，执行action层失败，抛出异常，"
			// + e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 上传EXCEL
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadExcelAjax")
	@ResponseBody
	public Map<String, Object> uploadExcelAjax(File vch) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String type = "PssBuyReturnBill_import";
		try {
			String result = pssBuyReturnBillFeign.uploadExcel(vch, type);
			if ("0000".equals(result)) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR.getMessage());
				dataMap.put("result", URLEncoder.encode(result, "utf-8"));
			}

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "购货退货单导入失败");
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 下载EXCEL（校验）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadErrorExcel")
	public void downloadErrorExcel(Model model, String errorFileUrl) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		// HttpServletResponse response = response;
		FileInputStream in = null;
		ServletOutputStream out = null;
		try{
			String excelName = "购货退货单导入校验文件";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			// 输出流
			out = response.getOutputStream();
			File file = new File(URLDecoder.decode(errorFileUrl, "UTF-8"));
			in = new FileInputStream(file);
			byte[] buffer = new byte[in.available()];
			in.read(buffer);
			out.write(buffer);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 导出EXCEL
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadToExcel")
	public void downloadToExcel(Model model, String buyReturnBillIds) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		try {
			// HttpServletRequest request = request;
			// HttpServletResponse response = response;

			String excelName = "购货退货单导出";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("buyReturnBillIds", buyReturnBillIds);
			paramMap.put("type", "PssBuyReturnBill_export");
			HSSFWorkbook pssWorkBook = pssBuyReturnBillFeign.downloadToExcel(paramMap);
			// 输出流
			ServletOutputStream out = response.getOutputStream();
			pssWorkBook.write(out);
			response.flushBuffer();
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
