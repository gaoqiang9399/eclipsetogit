package app.component.pss.purchases.controller;

import java.io.File;
import java.io.FileInputStream;
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
import app.component.pss.purchases.entity.PssBuyBillDetail;
import app.component.pss.purchases.entity.PssBuyOrder;
import app.component.pss.purchases.feign.PssBuyBillFeign;
import app.component.pss.purchases.feign.PssBuyOrderFeign;
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
 * Title: PssBuyBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 24 22:06:03 CST 2017
 **/
@Controller
@RequestMapping("/pssBuyBill")
public class PssBuyBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssBuyBillFeign pssBuyBillFeign;
	@Autowired
	private PssGoodsFeign pssGoodsFeign;
	@Autowired
	private PssStorehouseFeign pssStorehouseFeign;
	@Autowired
	private PssBuyOrderFeign pssBuyOrderFeign;
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
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssBuyBill pssBuyBill) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssBuyBillFeign.getAll(pssBuyBill), null, true);
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
		FormData formdl_pssbuybill02 = formService.getFormData("dl_pssbuybill02");
		PssBuyBill pssBuyBill = new PssBuyBill();
		List<PssBuyBill> pssBuyBillList = pssBuyBillFeign.getAll(pssBuyBill);
		model.addAttribute("formdl_pssbuybill02", formdl_pssbuybill02);
		model.addAttribute("pssBuyBillList", pssBuyBillList);
		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyBill_List";
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
			FormData formdl_pssbuybill02 = formService.getFormData("dl_pssbuybill02");
			getFormValue(formdl_pssbuybill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuybill02)) {
				PssBuyBill pssBuyBill = new PssBuyBill();
				setObjValue(formdl_pssbuybill02, pssBuyBill);
				pssBuyBillFeign.insert(pssBuyBill);
				getTableData(tableId,pssBuyBill);// 获取列表
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
		PssBuyBill pssBuyBill = new PssBuyBill();
		try {
			FormData formdl_pssbuybill02 = formService.getFormData("dl_pssbuybill02");
			getFormValue(formdl_pssbuybill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuybill02)) {
				pssBuyBill = new PssBuyBill();
				setObjValue(formdl_pssbuybill02, pssBuyBill);
				pssBuyBillFeign.update(pssBuyBill);
				getTableData(tableId,pssBuyBill);// 获取列表
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
	public Map<String, Object> getByIdAjax(String buyBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_pssbuybill02 = formService.getFormData("dl_pssbuybill02");
		PssBuyBill pssBuyBill = new PssBuyBill();
		pssBuyBill.setBuyBillId(buyBillId);
		pssBuyBill = pssBuyBillFeign.getById(pssBuyBill);
		getObjValue(formdl_pssbuybill02, pssBuyBill, formData);
		if (pssBuyBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String buyBillId,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyBill pssBuyBill = new PssBuyBill();
		pssBuyBill.setBuyBillId(buyBillId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssBuyBill = (PssBuyBill) JSONObject.toBean(jb, PssBuyBill.class);
			pssBuyBillFeign.delete(pssBuyBill);
			getTableData(tableId,pssBuyBill);// 获取列表
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
		ActionContext.initialize(request, response);

		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		CodeUtils codeUtils = new CodeUtils();
		JSONArray auditStsedJsonArray = codeUtils.getJSONArrayByKeyName("PSS_AUDIT_STSED");
		this.getHttpRequest().setAttribute("auditStsedJsonArray", auditStsedJsonArray);

		JSONArray paidStateJsonArray = codeUtils.getJSONArrayByKeyName("PSS_PAID_STATE");
		this.getHttpRequest().setAttribute("paidStateJsonArray", paidStateJsonArray);

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
		return "/component/pss/purchases/PssBuyBill_List";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData,String auditStsed) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyBill pssBuyBill = new PssBuyBill();
		try {
			pssBuyBill.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pssBuyBill.setCustomSorts(ajaxData);// 自定义排序参数赋值
			pssBuyBill.setCriteriaList(pssBuyBill, ajaxData);// 我的筛选

			if (auditStsed != null && !"".equals(auditStsed)) {
				pssBuyBill.setAuditStsed(auditStsed);
			}

			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = pssBuyBillFeign.findByPage(ipage, pssBuyBill);
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
	public String getDetailPage(Model model, String buyBillId,String buyBillNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyBill pssBuyBill = new PssBuyBill();
		if (StringUtil.isNotEmpty(buyBillId)) {
			pssBuyBill.setBuyBillId(buyBillId);
			pssBuyBill = pssBuyBillFeign.getById(pssBuyBill);
		} else if (StringUtil.isNotEmpty(buyBillNo)) {
			pssBuyBill.setBuyBillNo(buyBillNo);
			pssBuyBill = pssBuyBillFeign.getByBuyBillNo(pssBuyBill);
		} else {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键或购货单号"));
		}

		if (pssBuyBill == null) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		} else {
			buyBillId = pssBuyBill.getBuyBillId();
			dataMap.put("supplierId", pssBuyBill.getSupplierId());
			dataMap.put("billDate", DateUtil.getShowDateTime(pssBuyBill.getBillDate()));
			dataMap.put("buyBillNo", pssBuyBill.getBuyBillNo());
			dataMap.put("auditStsed", pssBuyBill.getAuditStsed());
			FormData formdl_pssbuybill02 = formService.getFormData("dl_pssbuybill02");
			getObjValue(formdl_pssbuybill02, pssBuyBill);

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
			model.addAttribute("formdl_pssbuybill02", formdl_pssbuybill02);
			model.addAttribute("ajaxData", ajaxData);
		}
		
		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyBill_Detail";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAddPage")
	public String getAddPage(Model model, String buyOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_pssbuybill02 = formService.getFormData("dl_pssbuybill02");
		String buyBillNo = pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.GHD.getValue(), "view");
		dataMap.put("buyBillNo", buyBillNo);
		String currentDate = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("billDate", currentDate);

		if (buyOrderId != null && !"".equals(buyOrderId)) {
			// 由购货订单生成
			PssBuyOrder pssBuyOrder = new PssBuyOrder();
			pssBuyOrder.setBuyOrderId(buyOrderId);
			pssBuyOrder = pssBuyOrderFeign.getById(pssBuyOrder);
			if (pssBuyOrder != null) {
				PssBuyBill pssBuyBill = new PssBuyBill();
				pssBuyBill.setSupplierId(pssBuyOrder.getSupplierId());
				pssBuyBill.setBuyOrderNo(pssBuyOrder.getBuyOrderNo());
				pssBuyBill.setMemo(pssBuyOrder.getMemo());
				pssBuyBill.setDiscountRate(pssBuyOrder.getDiscountRate());
				pssBuyBill.setDiscountAmount(pssBuyOrder.getDiscountAmount());
				pssBuyBill.setDiscountAfterAmount(pssBuyOrder.getDiscountAfterAmount());
				pssBuyBill.setThisPayment(0.0);
				pssBuyBill.setDebt(pssBuyOrder.getDiscountAfterAmount());
				pssBuyBill.setBuyExpense(0.0);
				dataMap.put("supplierId", pssBuyBill.getSupplierId());
				formdl_pssbuybill02 = formService.getFormData("dl_pssbuybill02");
				getObjValue(formdl_pssbuybill02, pssBuyBill);
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
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

		model.addAttribute("formdl_pssbuybill01", formdl_pssbuybill02);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyBill_Detail";
	}

	@RequestMapping(value = "/getNewBuyBillNoAjax")
	@ResponseBody
	public Map<String, Object> getNewBuyBillNoAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String buyBillNo = pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.GHD.getValue(), "view");
			dataMap.put("buyBillNo", buyBillNo);
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
	 * 保存购货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveBuyBillAjax")
	@ResponseBody
	public Map<String, Object> saveBuyBillAjax(String ajaxData,String pssBuyBillDetailsJson,String pssBuySaleExpBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdl_pssbuybill02 = formService.getFormData("dl_pssbuybill02");
			getFormValue(formdl_pssbuybill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuybill02)) {
				PssBuyBill pssBuyBill = new PssBuyBill();
				setObjValue(formdl_pssbuybill02, pssBuyBill);

				List<PssBuyBillDetail> pssBuyBillDetailList = PssPublicUtil.getMapByJsonObj(new PssBuyBillDetail(),
						pssBuyBillDetailsJson);
				List<PssBuySaleExpBill> pssBuySaleExpBillList = PssPublicUtil.getMapByJsonObj(new PssBuySaleExpBill(),
						pssBuySaleExpBillsJson);

				boolean success = pssBuyBillFeign.doSaveBuyBill(pssBuyBill, pssBuyBillDetailList,
						pssBuySaleExpBillList);
				String buyBillId = pssBuyBill.getBuyBillId();
				dataMap.put("buyBillId", buyBillId);
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
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	@RequestMapping(value = "/beforeGenerateBuyReturnBillAjax")
	@ResponseBody
	public Map<String, Object> beforeGenerateBuyReturnBillAjax(String buyBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (buyBillId == null || "".equals(buyBillId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssBuyBill pssBuyBill = new PssBuyBill();
		pssBuyBill.setBuyBillId(buyBillId);
		try {
			pssBuyBillFeign.beforeGenerateBuyReturnBill(pssBuyBill);
			dataMap.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	/**
	 * 审核购货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditBuyBillAjax")
	@ResponseBody
	public Map<String, Object> auditBuyBillAjax(String ajaxData,String pssBuyBillDetailsJson,String pssBuySaleExpBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdl_pssbuybill02 = formService.getFormData("dl_pssbuybill02");
			getFormValue(formdl_pssbuybill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuybill02)) {
				PssBuyBill pssBuyBill = new PssBuyBill();
				setObjValue(formdl_pssbuybill02, pssBuyBill);

				List<PssBuyBillDetail> pssBuyBillDetailList = PssPublicUtil.getMapByJsonObj(new PssBuyBillDetail(),
						pssBuyBillDetailsJson);
				List<PssBuySaleExpBill> pssBuySaleExpBillList = PssPublicUtil.getMapByJsonObj(new PssBuySaleExpBill(),
						pssBuySaleExpBillsJson);

				boolean success = pssBuyBillFeign.doAuditBuyBill(pssBuyBill, pssBuyBillDetailList,
						pssBuySaleExpBillList);
				String buyBillId = pssBuyBill.getBuyBillId();
				dataMap.put("buyBillId", buyBillId);
				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("购货单审核"));
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("购货单审核"));
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
	 * 批量审核购货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchAuditBuyBillAjax")
	@ResponseBody
	public Map<String, Object> batchAuditBuyBillAjax(String pssBuyBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssBuyBill> pssBuyBillList = PssPublicUtil.getMapByJsonObj(new PssBuyBill(), pssBuyBillsJson);
			if (pssBuyBillList != null && pssBuyBillList.size() > 0) {
				String msg = "";
				for (PssBuyBill pssBuyBill : pssBuyBillList) {
					try {
						boolean success = pssBuyBillFeign.doAuditBuyBill(pssBuyBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION.getMessage("购货单[" + pssBuyBill.getBuyBillNo() + "]审核")
									+ "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION.getMessage("购货单[" + pssBuyBill.getBuyBillNo() + "]审核")
									+ "<br>";
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
	 * 反审核购货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reverseAuditBuyBillAjax")
	@ResponseBody
	public Map<String, Object> reverseAuditBuyBillAjax(String buyBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (StringUtil.isEmpty(buyBillId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssBuyBill pssBuyBill = new PssBuyBill();
		pssBuyBill.setBuyBillId(buyBillId);
		try {
			boolean success = pssBuyBillFeign.doReverseAuditBuyBill(pssBuyBill);
			buyBillId = pssBuyBill.getBuyBillId();
			dataMap.put("buyBillId", buyBillId);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("购货单反审核"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("购货单反审核"));
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
	 * 批量反审核购货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchReverseAuditBuyBillAjax")
	@ResponseBody
	public Map<String, Object> batchReverseAuditBuyBillAjax(String pssBuyBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssBuyBill> pssBuyBillList = PssPublicUtil.getMapByJsonObj(new PssBuyBill(), pssBuyBillsJson);
			if (pssBuyBillList != null && pssBuyBillList.size() > 0) {
				String msg = "";
				for (PssBuyBill pssBuyBill : pssBuyBillList) {
					try {
						boolean success = pssBuyBillFeign.doReverseAuditBuyBill(pssBuyBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION.getMessage("购货单[" + pssBuyBill.getBuyBillNo() + "]反审核")
									+ "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION.getMessage("购货单[" + pssBuyBill.getBuyBillNo() + "]反审核")
									+ "<br>";
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
	 * 删除购货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBuyBillAjax")
	@ResponseBody
	public Map<String, Object> deleteBuyBillAjax(String buyBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (buyBillId == null || "".equals(buyBillId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssBuyBill pssBuyBill = new PssBuyBill();
		pssBuyBill.setBuyBillId(buyBillId);
		try {
			boolean success = pssBuyBillFeign.deleteBuyBill(pssBuyBill);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("购货单删除"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("购货单删除"));
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
	 * 批量删除购货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchDeleteBuyBillAjax")
	@ResponseBody
	public Map<String, Object> batchDeleteBuyBillAjax(String pssBuyBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssBuyBill> pssBuyBillList = PssPublicUtil.getMapByJsonObj(new PssBuyBill(), pssBuyBillsJson);
			if (pssBuyBillList != null && pssBuyBillList.size() > 0) {
				String msg = "";
				for (PssBuyBill pssBuyBill : pssBuyBillList) {
					try {
						boolean success = pssBuyBillFeign.deleteBuyBill(pssBuyBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION.getMessage("购货单[" + pssBuyBill.getBuyBillNo() + "]删除")
									+ "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION.getMessage("购货单[" + pssBuyBill.getBuyBillNo() + "]删除")
									+ "<br>";
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
		dataMap.put("downloadTmplate", "PssBuyBillAction_downloadTemplate.action");
		dataMap.put("serverUrl", "PssBuyBillActionAjax_uploadExcelAjax.action");
		dataMap.put("downloadErrorUrl", "PssBuyBillAction_downloadErrorExcel.action");

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
			//HttpServletResponse response = response;

			String excelName = "购货单导入模板";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			// 购货单
			String type = "PssBuyBill_import";
			HSSFWorkbook pssWorkBook = pssBuyBillFeign.getDownFileStream(type);
			// 输出流
			ServletOutputStream out = response.getOutputStream();
			pssWorkBook.write(out);
			response.flushBuffer();
			out.flush();
			out.close();

		} catch (Exception e) {
			//Log.error("PssBuyBillAction_downloadTemplate方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 上传EXCEL
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadExcelAjax")
	@ResponseBody
	public Map<String, Object> uploadExcelAjax(File vch) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String type = "PssBuyBill_import";
		try {
			String result = pssBuyBillFeign.uploadExcel(vch, type);
			if("0000".equals(result)){
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR.getMessage());
				dataMap.put("result", URLEncoder.encode(result, "utf-8"));
			}
		 	/*pledgeBaseInfoBill = new PledgeBaseInfoBill();
			pledgeBaseInfoBill.setPledgeNo(pledgeNo);
			Ipage ipage = this.getIpage();
			JsonTableUtil jtu = new JsonTableUtil();
			tableId = "dlpledgebaseinfobill0004";
			String  tableHtml = jtu.getJsonStr("table"+tableId,"tableTag", (List<PledgeBaseInfoBill>)pledgeBaseInfoBillFeign.findByPage(ipage, pledgeBaseInfoBill).getResult(), null,true);
			dataMap.put("htmlStr", tableHtml);*/
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "购货单导入失败");
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
	public void downloadErrorExcel(Model model,String errorFileUrl) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		//HttpServletResponse response = response;
		FileInputStream in =null;
		try{
			String excelName = "购货单导入校验文件";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			// 输出流
			ServletOutputStream out = response.getOutputStream();
			File file = new File(URLDecoder.decode(errorFileUrl, "UTF-8"));
			in = new FileInputStream(file);
			byte[] buffer = new byte[in.available()];
			in.read(buffer);
			in.close();
			out.write(buffer);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * 导出EXCEL
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadToExcel")
	public void downloadToExcel(Model model,String buyBillIds) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		try {
			// HttpServletRequest request = request;
			//HttpServletResponse response = response;

			String excelName = "购货单导出";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("buyBillIds", buyBillIds);
			paramMap.put("type", "PssBuyBill_export");
			HSSFWorkbook pssWorkBook = pssBuyBillFeign.downloadToExcel(paramMap);
			// 输出流
			ServletOutputStream out = response.getOutputStream();
			pssWorkBook.write(out);
			response.flushBuffer();
			out.flush();
			out.close();
		} catch (Exception e) {
			// logger.error("downloadExcel方法出错，执行action层失败，抛出异常，"+e.getMessage(),e);
			e.printStackTrace();
			throw e;
		}
	}
}
