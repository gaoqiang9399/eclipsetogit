package app.component.pss.sales.controller;

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
import app.component.pss.information.entity.PssCustomerInfo;
import app.component.pss.information.entity.PssGoods;
import app.component.pss.information.entity.PssStorehouse;
import app.component.pss.information.feign.PssAccountsFeign;
import app.component.pss.information.feign.PssBillnoConfFeign;
import app.component.pss.information.feign.PssCustomerInfoFeign;
import app.component.pss.information.feign.PssGoodsFeign;
import app.component.pss.information.feign.PssStorehouseFeign;
import app.component.pss.sales.entity.PssSaleBill;
import app.component.pss.sales.entity.PssSaleOrder;
import app.component.pss.sales.entity.PssSaleReturnBill;
import app.component.pss.sales.entity.PssSaleReturnBillDetail;
import app.component.pss.sales.feign.PssSaleBillFeign;
import app.component.pss.sales.feign.PssSaleOrderFeign;
import app.component.pss.sales.feign.PssSaleReturnBillFeign;
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
 * Title: PssSaleReturnBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Sep 14 15:30:38 CST 2017
 **/
@Controller
@RequestMapping("/pssSaleReturnBill")
public class PssSaleReturnBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssSaleReturnBillFeign pssSaleReturnBillFeign;
	@Autowired
	private PssGoodsFeign pssGoodsFeign;
	@Autowired
	private PssStorehouseFeign pssStorehouseFeign;
	@Autowired
	private PssSaleOrderFeign pssSaleOrderFeign;
	@Autowired
	private PssSaleBillFeign pssSaleBillFeign;
	@Autowired
	private PssAccountsFeign pssAccountsFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
	@Autowired
	private PssBillnoConfFeign pssBillnoConfFeign;
	@Autowired
	private PssConfigFeign pssConfigFeign;
	@Autowired
	private PssCustomerInfoFeign pssCustomerInfoFeign;
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
	private void getTableData(String tableId, PssSaleReturnBill pssSaleReturnBill) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssSaleReturnBillFeign.getAll(pssSaleReturnBill), null,
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
		FormData formdl_psssalereturnbill02 = formService.getFormData("dl_psssalereturnbill02");
		PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
		List<PssSaleReturnBill> pssSaleReturnBillList = pssSaleReturnBillFeign.getAll(pssSaleReturnBill);
		model.addAttribute("formdl_psssalereturnbill02", formdl_psssalereturnbill02);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleReturnBill_List";
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
			FormData formdl_psssalereturnbill02 = formService.getFormData("dl_psssalereturnbill02");
			getFormValue(formdl_psssalereturnbill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalereturnbill02)) {
				PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
				setObjValue(formdl_psssalereturnbill02, pssSaleReturnBill);
				pssSaleReturnBillFeign.insert(pssSaleReturnBill);
				getTableData(tableId,pssSaleReturnBill);// 获取列表
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
		PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
		try {
			FormData formdl_psssalereturnbill02 = formService.getFormData("dl_psssalereturnbill02");
			getFormValue(formdl_psssalereturnbill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalereturnbill02)) {
				pssSaleReturnBill = new PssSaleReturnBill();
				setObjValue(formdl_psssalereturnbill02, pssSaleReturnBill);
				pssSaleReturnBillFeign.update(pssSaleReturnBill);
				getTableData(tableId,pssSaleReturnBill);// 获取列表
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
	public Map<String, Object> getByIdAjax(String saleReturnBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_psssalereturnbill02 = formService.getFormData("dl_psssalereturnbill02");
		PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
		pssSaleReturnBill.setSaleReturnBillId(saleReturnBillId);
		pssSaleReturnBill = pssSaleReturnBillFeign.getById(pssSaleReturnBill);
		getObjValue(formdl_psssalereturnbill02, pssSaleReturnBill, formData);
		if (pssSaleReturnBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String saleReturnBillId,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
		pssSaleReturnBill.setSaleReturnBillId(saleReturnBillId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssSaleReturnBill = (PssSaleReturnBill) JSONObject.toBean(jb, PssSaleReturnBill.class);
			pssSaleReturnBillFeign.delete(pssSaleReturnBill);
			getTableData(tableId,pssSaleReturnBill);// 获取列表
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
		//FormService formService = new FormService();
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
		this.getHttpRequest().setAttribute("salerNoJsonArray", opNoJsonArray);
		this.getHttpRequest().setAttribute("regOpNoJsonArray", opNoJsonArray);
		this.getHttpRequest().setAttribute("auditOpNoJsonArray", opNoJsonArray);
		this.getHttpRequest().setAttribute("lstModOpNoJsonArray", opNoJsonArray);

		
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleReturnBill_List";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
		try {
			pssSaleReturnBill.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pssSaleReturnBill.setCustomSorts(ajaxData);// 自定义排序参数赋值
			pssSaleReturnBill.setCriteriaList(pssSaleReturnBill, ajaxData);// 我的筛选

			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = pssSaleReturnBillFeign.findByPage(ipage, pssSaleReturnBill);
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
	public String getDetailPage(Model model,String saleReturnBillId,String saleReturnBillNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
		if (StringUtil.isNotEmpty(saleReturnBillId)) {
			pssSaleReturnBill.setSaleReturnBillId(saleReturnBillId);
			pssSaleReturnBill = pssSaleReturnBillFeign.getById(pssSaleReturnBill);
		} else if (StringUtil.isNotEmpty(saleReturnBillNo)) {
			pssSaleReturnBill.setSaleReturnBillNo(saleReturnBillNo);
			pssSaleReturnBill = pssSaleReturnBillFeign.getBySaleReturnBillNo(pssSaleReturnBill);
		} else {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键或销货退货单号"));
		}

		if (pssSaleReturnBill == null) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		} else {
			saleReturnBillId = pssSaleReturnBill.getSaleReturnBillId();
			dataMap.put("cusNo", pssSaleReturnBill.getCusNo());
			dataMap.put("salerNo", pssSaleReturnBill.getSalerNo());
			dataMap.put("billDate", DateUtil.getShowDateTime(pssSaleReturnBill.getBillDate()));
			dataMap.put("saleReturnBillNo", pssSaleReturnBill.getSaleReturnBillNo());
			dataMap.put("auditStsed", pssSaleReturnBill.getAuditStsed());
			dataMap.put("docBizNo", pssSaleReturnBill.getSaleReturnBillId());
			FormData formdl_psssalereturnbill02 = formService.getFormData("dl_psssalereturnbill02");
			getObjValue(formdl_psssalereturnbill02, pssSaleReturnBill);

			JSONObject json = new JSONObject();
			CodeUtils codeUtils = new CodeUtils();
			// 客户选择组件
			Map<String, PssCustomerInfo> customerMap = new HashMap<String, PssCustomerInfo>();
			PssCustomerInfo queryPssCustomerInfo = new PssCustomerInfo();
			queryPssCustomerInfo.setEnabledStatus(PssEnumBean.ENABLED_STATUS.ENABLED.getValue());
			List<PssCustomerInfo> pssCustomerInfoList = pssCustomerInfoFeign.getAll(queryPssCustomerInfo);
			JSONArray cusArray = new JSONArray();
			for (PssCustomerInfo pssCustomerInfo : pssCustomerInfoList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", pssCustomerInfo.getCusNo());
				jsonObject.put("name", pssCustomerInfo.getCusName());
				jsonObject.put("cusGrade", pssCustomerInfo.getCusGrade());
				jsonObject.put("salerNo", pssCustomerInfo.getSalerNo());
				jsonObject.put("salerName", pssCustomerInfo.getSalerName());
				jsonObject.put("accountsReceivedBalance", pssCustomerInfo.getAccountsReceivedBalance());
				jsonObject.put("enabledStatus", pssCustomerInfo.getEnabledStatus());
				cusArray.add(jsonObject);
				customerMap.put(pssCustomerInfo.getCusNo(), pssCustomerInfo);
			}
			json.put("cus", cusArray);

			Map<String, PssCustomerInfo> disableCustomerMap = new HashMap<String, PssCustomerInfo>();
			queryPssCustomerInfo.setEnabledStatus(PssEnumBean.ENABLED_STATUS.CLOSED.getValue());
			List<PssCustomerInfo> disableCustomerList = pssCustomerInfoFeign.getAll(queryPssCustomerInfo);
			for (PssCustomerInfo pssCustomerInfo : disableCustomerList) {
				disableCustomerMap.put(pssCustomerInfo.getCusNo(), pssCustomerInfo);
				customerMap.put(pssCustomerInfo.getCusNo(), pssCustomerInfo);
			}
			dataMap.put("disableCustomerMap", JSONObject.fromObject(disableCustomerMap));
			dataMap.put("customerMap", JSONObject.fromObject(customerMap));

			// 销售人员选择组件
			List<SysUser> sysUserList = sysInterfaceFeign.getAllUser(new SysUser());
			JSONArray salerArray = new JSONArray();
			JSONObject salerJSONObject = new JSONObject();
			salerJSONObject.put("id", "-1");
			salerJSONObject.put("name", "(空)");
			salerArray.add(salerJSONObject);
			for (SysUser sysUser : sysUserList) {
				salerJSONObject = new JSONObject();
				salerJSONObject.put("id", sysUser.getOpNo());
				salerJSONObject.put("name", sysUser.getOpName());
				salerArray.add(salerJSONObject);
			}
			json.put("saler", salerArray);

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
				jsonObject.put("retailPrice", pssGoods.getRetailPrice());
				jsonObject.put("wholesalePrice", pssGoods.getWholesalePrice());
				jsonObject.put("vipPrice", pssGoods.getVipPrice());
				jsonObject.put("discountRate1", pssGoods.getDiscountRate1());
				jsonObject.put("discountRate2", pssGoods.getDiscountRate2());
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
			model.addAttribute("formdl_psssalereturnbill02", formdl_psssalereturnbill02);
			model.addAttribute("ajaxData", ajaxData);
		}
		
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleReturnBill_Detail";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAddPage")
	public String getAddPage(Model model, String saleOrderId,String saleBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_psssalereturnbill02 = formService.getFormData("dl_psssalereturnbill02");
		String saleReturnBillNo = pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.XHT.getValue(), "view");
		dataMap.put("saleReturnBillNo", saleReturnBillNo);
		String currentDate = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("billDate", currentDate);
		dataMap.put("docBizNo", WaterIdUtil.getWaterId("SRB"));

		if (saleOrderId != null && !"".equals(saleOrderId)) {
			// 由销货订单生成
			PssSaleOrder pssSaleOrder = new PssSaleOrder();
			pssSaleOrder.setSaleOrderId(saleOrderId);
			pssSaleOrder = pssSaleOrderFeign.getById(pssSaleOrder);
			if (pssSaleOrder != null) {
				PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
				pssSaleReturnBill.setCusNo(pssSaleOrder.getCusNo());
				pssSaleReturnBill.setSalerNo(pssSaleOrder.getSalerNo());
				pssSaleReturnBill.setSaleOrderNo(pssSaleOrder.getSaleOrderNo());
				pssSaleReturnBill.setMemo(pssSaleOrder.getMemo());
				pssSaleReturnBill.setDiscountRate(pssSaleOrder.getDiscountRate());
				pssSaleReturnBill.setDiscountAmount(pssSaleOrder.getDiscountAmount());
				pssSaleReturnBill.setDiscountAfterAmount(pssSaleOrder.getDiscountAfterAmount());
				pssSaleReturnBill.setThisRefund(0.0);
				pssSaleReturnBill.setThisDebt(pssSaleOrder.getDiscountAfterAmount());
				pssSaleReturnBill.setSaleRefundExpense(0.0);
				dataMap.put("cusNo", pssSaleReturnBill.getCusNo());
				dataMap.put("salerNo", pssSaleReturnBill.getSalerNo());
				formdl_psssalereturnbill02 = formService.getFormData("dl_psssalereturnbill02");
				getObjValue(formdl_psssalereturnbill02, pssSaleReturnBill);
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		} else if (saleBillId != null && !"".equals(saleBillId)) {
			// 由销货单生成
			PssSaleBill pssSaleBill = new PssSaleBill();
			pssSaleBill.setSaleBillId(saleBillId);
			pssSaleBill = pssSaleBillFeign.getById(pssSaleBill);
			if (pssSaleBill != null) {
				PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
				pssSaleReturnBill.setCusNo(pssSaleBill.getCusNo());
				pssSaleReturnBill.setSalerNo(pssSaleBill.getSalerNo());
				pssSaleReturnBill.setSaleBillNo(pssSaleBill.getSaleBillNo());
				pssSaleReturnBill.setMemo(pssSaleBill.getMemo());
				pssSaleReturnBill.setDiscountRate(pssSaleBill.getDiscountRate());
				pssSaleReturnBill.setDiscountAmount(pssSaleBill.getDiscountAmount());
				pssSaleReturnBill.setDiscountAfterAmount(pssSaleBill.getDiscountAfterAmount());
				pssSaleReturnBill.setThisRefund(0.0);
				pssSaleReturnBill.setThisDebt(pssSaleBill.getDiscountAfterAmount());
				pssSaleReturnBill.setSaleRefundExpense(0.0);
				dataMap.put("cusNo", pssSaleReturnBill.getCusNo());
				dataMap.put("salerNo", pssSaleReturnBill.getSalerNo());
				formdl_psssalereturnbill02 = formService.getFormData("dl_psssalereturnbill02");
				getObjValue(formdl_psssalereturnbill02, pssSaleReturnBill);
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		}else {
		}

		JSONObject json = new JSONObject();
		CodeUtils codeUtils = new CodeUtils();
		// 客户选择组件
		Map<String, PssCustomerInfo> customerMap = new HashMap<String, PssCustomerInfo>();
		PssCustomerInfo queryPssCustomerInfo = new PssCustomerInfo();
		queryPssCustomerInfo.setEnabledStatus(PssEnumBean.ENABLED_STATUS.ENABLED.getValue());
		List<PssCustomerInfo> pssCustomerInfoList = pssCustomerInfoFeign.getAll(queryPssCustomerInfo);
		JSONArray cusArray = new JSONArray();
		for (PssCustomerInfo pssCustomerInfo : pssCustomerInfoList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", pssCustomerInfo.getCusNo());
			jsonObject.put("name", pssCustomerInfo.getCusName());
			jsonObject.put("cusGrade", pssCustomerInfo.getCusGrade());
			jsonObject.put("salerNo", pssCustomerInfo.getSalerNo());
			jsonObject.put("salerName", pssCustomerInfo.getSalerName());
			jsonObject.put("accountsReceivedBalance", pssCustomerInfo.getAccountsReceivedBalance());
			jsonObject.put("enabledStatus", pssCustomerInfo.getEnabledStatus());
			cusArray.add(jsonObject);
			customerMap.put(pssCustomerInfo.getCusNo(), pssCustomerInfo);
		}
		json.put("cus", cusArray);
		dataMap.put("customerMap", JSONObject.fromObject(customerMap));

		// 销售人员选择组件
		List<SysUser> sysUserList = sysInterfaceFeign.getAllUser(new SysUser());
		JSONArray salerArray = new JSONArray();
		JSONObject salerJSONObject = new JSONObject();
		salerJSONObject.put("id", "-1");
		salerJSONObject.put("name", "(空)");
		salerArray.add(salerJSONObject);
		for (SysUser sysUser : sysUserList) {
			salerJSONObject = new JSONObject();
			salerJSONObject.put("id", sysUser.getOpNo());
			salerJSONObject.put("name", sysUser.getOpName());
			salerArray.add(salerJSONObject);
		}
		json.put("saler", salerArray);

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
			jsonObject.put("retailPrice", pssGoods.getRetailPrice());
			jsonObject.put("wholesalePrice", pssGoods.getWholesalePrice());
			jsonObject.put("vipPrice", pssGoods.getVipPrice());
			jsonObject.put("discountRate1", pssGoods.getDiscountRate1());
			jsonObject.put("discountRate2", pssGoods.getDiscountRate2());
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

		model.addAttribute("formdl_psssalereturnbill02", formdl_psssalereturnbill02);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleReturnBill_Detail";
	}

	@RequestMapping(value = "/getNewSaleReturnBillNoAjax")
	@ResponseBody
	public Map<String, Object> getNewSaleReturnBillNoAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String saleReturnBillNo = pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.XHT.getValue(), "view");
			dataMap.put("saleReturnBillNo", saleReturnBillNo);
			dataMap.put("docBizNo", WaterIdUtil.getWaterId("SB"));
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
	 * 保存销货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSaleReturnBillAjax")
	@ResponseBody
	public Map<String, Object> saveSaleReturnBillAjax(String ajaxData,String pssSaleReturnBillDetailsJson,String pssBuySaleExpBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdl_psssalereturnbill02 = formService.getFormData("dl_psssalereturnbill02");
			getFormValue(formdl_psssalereturnbill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalereturnbill02)) {
				PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
				setObjValue(formdl_psssalereturnbill02, pssSaleReturnBill);

				List<PssSaleReturnBillDetail> pssSaleReturnBillDetailList = PssPublicUtil
						.getMapByJsonObj(new PssSaleReturnBillDetail(), pssSaleReturnBillDetailsJson);
				List<PssBuySaleExpBill> pssBuySaleExpBillList = PssPublicUtil.getMapByJsonObj(new PssBuySaleExpBill(),
						pssBuySaleExpBillsJson);

				boolean success = pssSaleReturnBillFeign.doSaveSaleReturnBill(pssSaleReturnBill,
						pssSaleReturnBillDetailList, pssBuySaleExpBillList);
				String saleReturnBillId = pssSaleReturnBill.getSaleReturnBillId();
				dataMap.put("saleReturnBillId", saleReturnBillId);
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
	 * 审核销货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditSaleReturnBillAjax")
	@ResponseBody
	public Map<String, Object> auditSaleReturnBillAjax(String ajaxData,String pssSaleReturnBillDetailsJson,String pssBuySaleExpBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdl_psssalereturnbill02 = formService.getFormData("dl_psssalereturnbill02");
			getFormValue(formdl_psssalereturnbill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalereturnbill02)) {
				PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
				setObjValue(formdl_psssalereturnbill02, pssSaleReturnBill);

				List<PssSaleReturnBillDetail> pssSaleReturnBillDetailList = PssPublicUtil
						.getMapByJsonObj(new PssSaleReturnBillDetail(), pssSaleReturnBillDetailsJson);
				List<PssBuySaleExpBill> pssBuySaleExpBillList = PssPublicUtil.getMapByJsonObj(new PssBuySaleExpBill(),
						pssBuySaleExpBillsJson);

				boolean success = pssSaleReturnBillFeign.doAuditSaleReturnBill1(pssSaleReturnBill,
						pssSaleReturnBillDetailList, pssBuySaleExpBillList);
				String saleReturnBillId = pssSaleReturnBill.getSaleReturnBillId();
				dataMap.put("saleReturnBillId", saleReturnBillId);
				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("销货退货单审核"));
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("销货退货单审核"));
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
	 * 批量审核销货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchAuditSaleReturnBillAjax")
	@ResponseBody
	public Map<String, Object> batchAuditSaleReturnBillAjax(String pssSaleReturnBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSaleReturnBill> pssSaleReturnBillList = PssPublicUtil.getMapByJsonObj(new PssSaleReturnBill(),
					pssSaleReturnBillsJson);
			if (pssSaleReturnBillList != null && pssSaleReturnBillList.size() > 0) {
				String msg = "";
				for (PssSaleReturnBill pssSaleReturnBill : pssSaleReturnBillList) {
					try {
						boolean success = pssSaleReturnBillFeign.doAuditSaleReturnBill(pssSaleReturnBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("销货退货单[" + pssSaleReturnBill.getSaleReturnBillNo() + "]审核") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("销货退货单[" + pssSaleReturnBill.getSaleReturnBillNo() + "]审核") + "<br>";
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
	 * 反审核销货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reverseAuditSaleReturnBillAjax")
	@ResponseBody
	public Map<String, Object> reverseAuditSaleReturnBillAjax(String saleReturnBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (StringUtil.isEmpty(saleReturnBillId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
		pssSaleReturnBill.setSaleReturnBillId(saleReturnBillId);
		try {
			boolean success = pssSaleReturnBillFeign.doReverseAuditSaleReturnBill(pssSaleReturnBill);
			saleReturnBillId = pssSaleReturnBill.getSaleReturnBillId();
			dataMap.put("saleReturnBillId", saleReturnBillId);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("销货退货单反审核"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("销货退货单反审核"));
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
	 * 批量反审核销货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchReverseAuditSaleReturnBillAjax")
	@ResponseBody
	public Map<String, Object> batchReverseAuditSaleReturnBillAjax(String pssSaleReturnBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSaleReturnBill> pssSaleReturnBillList = PssPublicUtil.getMapByJsonObj(new PssSaleReturnBill(),
					pssSaleReturnBillsJson);
			if (pssSaleReturnBillList != null && pssSaleReturnBillList.size() > 0) {
				String msg = "";
				for (PssSaleReturnBill pssSaleReturnBill : pssSaleReturnBillList) {
					try {
						boolean success = pssSaleReturnBillFeign.doReverseAuditSaleReturnBill(pssSaleReturnBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("销货退货单[" + pssSaleReturnBill.getSaleReturnBillNo() + "]反审核") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("销货退货单[" + pssSaleReturnBill.getSaleReturnBillNo() + "]反审核") + "<br>";
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
	 * 删除销货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSaleReturnBillAjax")
	@ResponseBody
	public Map<String, Object> deleteSaleReturnBillAjax(String saleReturnBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (saleReturnBillId == null || "".equals(saleReturnBillId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssSaleReturnBill pssSaleReturnBill = new PssSaleReturnBill();
		pssSaleReturnBill.setSaleReturnBillId(saleReturnBillId);
		try {
			boolean success = pssSaleReturnBillFeign.deleteSaleReturnBill(pssSaleReturnBill);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("销货退货单删除"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("销货退货单删除"));
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
	 * 批量删除销货退货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchDeleteSaleReturnBillAjax")
	@ResponseBody
	public Map<String, Object> batchDeleteSaleReturnBillAjax(String pssSaleReturnBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSaleReturnBill> pssSaleReturnBillList = PssPublicUtil.getMapByJsonObj(new PssSaleReturnBill(),
					pssSaleReturnBillsJson);
			if (pssSaleReturnBillList != null && pssSaleReturnBillList.size() > 0) {
				String msg = "";
				for (PssSaleReturnBill pssSaleReturnBill : pssSaleReturnBillList) {
					try {
						boolean success = pssSaleReturnBillFeign.deleteSaleReturnBill(pssSaleReturnBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("销货退货单[" + pssSaleReturnBill.getSaleReturnBillNo() + "]删除") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("销货退货单[" + pssSaleReturnBill.getSaleReturnBillNo() + "]删除") + "<br>";
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
		dataMap.put("downloadTmplate", "PssSaleReturnBillAction_downloadTemplate.action");
		dataMap.put("serverUrl", "PssSaleReturnBillActionAjax_uploadExcelAjax.action");
		dataMap.put("downloadErrorUrl", "PssSaleReturnBillAction_downloadErrorExcel.action");

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

			String excelName = "销货退货单导入模板";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			// 销货退货单
			String type = "PssSaleReturnBill_import";
			HSSFWorkbook pssWorkBook = excelInterfaceFeign.getDownFileStream(type);
			// 输出流
			ServletOutputStream out = response.getOutputStream();
			pssWorkBook.write(out);
			response.flushBuffer();
			out.flush();
			out.close();

		} catch (Exception e) {
			//Log.error("PssSaleReturnBillAction_downloadTemplate方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
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
		String type = "PssSaleReturnBill_import";
		try {
			String result = pssSaleReturnBillFeign.uploadExcel(vch, type);
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
			dataMap.put("msg", "销货退货单导入失败");
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
		FileInputStream in = null;
		ServletOutputStream out = null;
		try{
			String excelName = "销货退货单导入校验文件";
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
	public void downloadToExcel(Model model,String saleReturnBillIds) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		try {
			// HttpServletRequest request = request;
			//HttpServletResponse response = response;

			String excelName = "销货退货单导出";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("saleReturnBillIds", saleReturnBillIds);
			paramMap.put("type", "PssSaleReturnBill_export");
			HSSFWorkbook pssWorkBook = pssSaleReturnBillFeign.downloadToExcel(paramMap);
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
