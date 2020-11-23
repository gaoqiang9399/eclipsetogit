package app.component.pss.information.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.finance.util.CwPublicUtil;
import app.component.pss.fund.feign.PssPaymentBillFeign;
import app.component.pss.fund.feign.PssReceiptBillFeign;
import app.component.pss.information.entity.PssQueryItem;
import app.component.pss.information.entity.PssReportQueryConditionUser;
import app.component.pss.information.feign.PssReportFeign;
import app.component.pss.purchases.entity.PssBuyBill;
import app.component.pss.purchases.entity.PssBuyOrder;
import app.component.pss.purchases.feign.PssBuyBillFeign;
import app.component.pss.purchases.feign.PssBuyOrderFeign;
import app.component.pss.sales.entity.PssSaleOrder;
import app.component.pss.sales.feign.PssSaleOrderFeign;
import app.component.pss.stock.feign.PssStoreStockFeign;
import app.component.pss.utils.PssEnumBean;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/pssReport")
public class PssReportController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssReportFeign pssReportFeign;

	@Autowired
	private PssSaleOrderFeign pssSaleOrderFeign;
	@Autowired
	private PssBuyOrderFeign pssBuyOrderFeign;
	@Autowired
	private PssBuyBillFeign pssBuyBillFeign;
	@Autowired
	private PssStoreStockFeign pssStoreStockFeign;
	@Autowired
	private PssReceiptBillFeign pssReceiptBillFeign;
	@Autowired
	private PssPaymentBillFeign pssPaymentBillFeign;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getReportListPage")
	public String getReportListPage(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			PssQueryItem pssQueryItem = new PssQueryItem();
			pssQueryItem.setOpNo(User.getRegNo(request));
			dataMap = pssReportFeign.getReportItemAll(pssQueryItem);
			JSONObject json = new JSONObject();
			// 购货报表、销货报表、仓库报表总的列表
			JSONArray arrayTotal = JSONArray.fromObject(dataMap.get("total"));
			json.put("pssReportItemListTotal", arrayTotal);
			request.setAttribute("json", json);

			Map<String, Object> reportMap = pssReportFeign.getQueryItemList();
			List<PssQueryItem> buyOrderQueryList = (List<PssQueryItem>) reportMap.get("buyOrder");
			List<PssQueryItem> saleOrderQueryList = (List<PssQueryItem>) reportMap.get("saleOrder");
			List<PssQueryItem> storeOrderQueryList = (List<PssQueryItem>) reportMap.get("storeOrder");
			List<PssQueryItem> graphReportQueryList = (List<PssQueryItem>) reportMap.get("graphReport");

			model.addAttribute("buyOrderQueryList", buyOrderQueryList);
			model.addAttribute("buyOrderQueryList", saleOrderQueryList);
			model.addAttribute("buyOrderQueryList", storeOrderQueryList);
			model.addAttribute("buyOrderQueryList", graphReportQueryList);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("query", "");
		return "/component/pss/information/PssReportEntrance";
	}

	@RequestMapping(value = "/showFormConditionVal")
	public Map<String, Object> showFormConditionVal(Model model, String reportId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = new HashMap<String, String>();
			formMap.put("reportId", reportId);
			formMap.put("opNo", User.getRegNo(request));
			// 自定义查询Bo方法
			String result = pssReportFeign.showFormConditionVal(formMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
			dataMap.put("result", result);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		model.addAttribute("dataMap", dataMap);
		return dataMap;
	}

	@RequestMapping(value = "/saveReoprtSqlCondition")
	public Map<String, Object> saveReoprtSqlCondition(Model model, String reportId, String sqlCondition) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		PssReportQueryConditionUser pssReportQueryConditionUser = new PssReportQueryConditionUser();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			pssReportQueryConditionUser.setReportId(reportId);
			pssReportQueryConditionUser.setConditonContent(sqlCondition);
			pssReportQueryConditionUser.setOpNo(User.getRegNo(request));
			pssReportQueryConditionUser.setOpName(User.getRegName(request));
			pssReportFeign.saveReoprtSqlCondition(pssReportQueryConditionUser);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}

	@RequestMapping(value = "/reportQuery")
	public Map<String, Object> reportQuery(Model model, String reportId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		PssReportQueryConditionUser pssReportQueryConditionUser = new PssReportQueryConditionUser();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			pssReportQueryConditionUser.setReportId(reportId);
			pssReportQueryConditionUser.setOpNo(User.getRegNo(request));
			pssReportQueryConditionUser.setOpName(User.getRegName(request));
			String querySqlCondition = pssReportFeign.reportQuery(pssReportQueryConditionUser);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
			dataMap.put("querySqlCondition",querySqlCondition);
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}

	@RequestMapping(value = "/getReportSqlListDataAjax")
	@ResponseBody
	public Map<String, Object> getReportSqlListDataAjax(String ajaxData,Integer pageSize,Integer pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			formMap.put("opNo", User.getRegNo(request));
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("formMap",formMap));
			// 自定义查询Bo方法
			ipage = pssReportFeign.getReportSqlListBean(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List<?>) ipage.getResult(), ipage, true);
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

	@RequestMapping(value = "/reportQueryForGraph")
	public Map<String, Object> reportQueryForGraph(Model model, String reportId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if ("Pss-Sale-Total-Amount".equals(reportId)) {
				list = pssReportFeign.getPssSaleTotalAmount();
				if (list.size() > 0) {
					JSONArray json1 = new JSONArray();
					JSONArray json2 = new JSONArray();
					for (int i = 0; i < list.size(); i++) {
						json1.add(list.get(i).get("total"));
						json2.add(list.get(i).get("bill_date"));
					}
					dataMap.put("data2", json2);
					dataMap.put("data1", json1);
				} else {
					dataMap.put("data2", "");
					dataMap.put("data1", "");
				}
			} else if ("Pss-Buy-Bill-Amount-Rank".equals(reportId)) {
				list = pssReportFeign.getPssBuyBillAmountRank();
				if (list.size() > 0) {
					JSONArray json = new JSONArray();
					for (int i = 0; i < list.size(); i++) {
						JSONObject jo = new JSONObject();

						jo.put("value", new DecimalFormat("0.00").format(list.get(i).get("total")));
						jo.put("name", list.get(i).get("GOODS_NAME"));
						json.add(jo);
					}
					for (int i = list.size(); i < 10; i++) {
						JSONObject jo = new JSONObject();

						jo.put("value", new DecimalFormat("0.00").format(0.00));
						jo.put("name", "暂无商品信息");
						json.add(jo);
					}
					dataMap.put("pssBuyBillAmountRankList", json);
				} else {
					dataMap.put("pssBuyBillAmountRankList", "");
				}

			} else if ("Pss-Stock-Count-Distribute".equals(reportId)) {
				list = pssReportFeign.getPssStockCountDistribute();
				if (list.size() > 0) {
					JSONArray json = new JSONArray();
					for (int i = 0; i < list.size(); i++) {
						JSONObject jo = new JSONObject();

						BigDecimal d = (BigDecimal) list.get(i).get("QTY");
						BigDecimal setScale = d.setScale(2, BigDecimal.ROUND_HALF_UP);

						jo.put("value", setScale);
						jo.put("name", list.get(i).get("STOREHOUSE_NAME"));
						json.add(jo);
					}
					dataMap.put("data2", json);
					json = new JSONArray();
					for (int i = 0; i < list.size(); i++) {
						json.add(list.get(i).get("STOREHOUSE_NAME"));
					}
					dataMap.put("data1", json);
				} else {
					dataMap.put("data2", "");
					dataMap.put("data1", "");
				}
			}else {
			}

			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}

	@RequestMapping(value = "/getGeneralPageKeyDataCount")
	public Map<String, Object> getGeneralPageKeyDataCount(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleOrder pssSaleOrder;
		PssBuyOrder pssBuyOrder;
		PssBuyBill pssBuyBill;
		try {
			JSONArray json = new JSONArray();
			JSONObject jo;
			int i1 = 0;
			int i2 = 0;
			// 库存预警
			jo = new JSONObject();
			jo.put("storeWarningCount", pssStoreStockFeign.selectCountForWarn());
			json.add(jo);

			// 未发货销货订单
			jo = new JSONObject();
			pssSaleOrder = new PssSaleOrder();
			pssSaleOrder.setOrderState(PssEnumBean.SALE_ORDER_STATE.NONE.getValue());
			pssSaleOrder.setAuditStsed(PssEnumBean.YES_OR_NO.YES.getNum());
			pssSaleOrder.setEnabledStatus(PssEnumBean.ENABLED_STATUS.ENABLED.getValue());
			i1 = pssSaleOrderFeign.getAll(pssSaleOrder).size();
			pssSaleOrder.setOrderState(PssEnumBean.SALE_ORDER_STATE.PART.getValue());
			i2 = pssSaleOrderFeign.getAll(pssSaleOrder).size();
			jo.put("unSendSaleBillCount", i1 + i2);
			json.add(jo);

			// 未审核购货订单
			jo = new JSONObject();
			pssBuyOrder = new PssBuyOrder();
			pssBuyOrder.setAuditStsed(PssEnumBean.YES_OR_NO.NO.getNum());
			i1 = pssBuyOrderFeign.getAll(pssBuyOrder).size();
			jo.put("unExamBuyBillCount", i1);
			json.add(jo);

			// 未审核销货订单
			jo = new JSONObject();
			pssSaleOrder = new PssSaleOrder();
			pssSaleOrder.setAuditStsed(PssEnumBean.YES_OR_NO.NO.getNum());
			i1 = pssSaleOrderFeign.getAll(pssSaleOrder).size();
			jo.put("unExamSaleBillCount", i1);
			json.add(jo);

			// 未审核购货单
			jo = new JSONObject();
			pssBuyBill = new PssBuyBill();
			pssBuyBill.setAuditStsed(PssEnumBean.YES_OR_NO.NO.getNum());
			i1 = pssBuyBillFeign.getAll(pssBuyBill).size();
			jo.put("unExamBuyOrderCount", i1);
			json.add(jo);

			dataMap.put("generalPageKeyDataCountJson", json);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");

		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;

	}

	@RequestMapping(value = "/getGeneralPageMenuData")
	public Map<String, Object> getGeneralPageMenuData(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String endDate = formatter.format(new Date());
		String beginDate = endDate.substring(0, 6) + "01";

		try {
			JSONArray json = new JSONArray();
			JSONObject jo;
			// 库存总量
			jo = new JSONObject();
			jo.put("storeCount", (new Double(pssStoreStockFeign.selectStockTotalNum())).intValue());
			json.add(jo);
			// 库存成本
			jo = new JSONObject();
			jo.put("storeCost",
					new DecimalFormat("0.00").format(pssStoreStockFeign.selectStockTotalCost().doubleValue()));
			json.add(jo);

			// 现金
			jo = new JSONObject();
			BigDecimal bd1 = pssReportFeign.getCashOrBankDepositByAccountTpye(beginDate, endDate,
					PssEnumBean.PSS_ACCOUNT_TYPE.CASH.getValue());
			jo.put("cash", new DecimalFormat("0.00").format(pssReportFeign.getCashOrBankDepositByAccountTpye(beginDate,
					endDate, PssEnumBean.PSS_ACCOUNT_TYPE.CASH.getValue())));
			json.add(jo);
			// 银行存款
			jo = new JSONObject();
			BigDecimal bd2 = pssReportFeign.getCashOrBankDepositByAccountTpye(beginDate, endDate,
					PssEnumBean.PSS_ACCOUNT_TYPE.BANK_DEPOSIT.getValue());
			jo.put("deposit",
					new DecimalFormat("0.00").format(pssReportFeign.getCashOrBankDepositByAccountTpye(beginDate,
							endDate, PssEnumBean.PSS_ACCOUNT_TYPE.BANK_DEPOSIT.getValue())));
			json.add(jo);

			// 客户欠款
			jo = new JSONObject();
			// jo.put("cusDebt", new
			// DecimalFormat("0.00").format(pssReceiptBillFeign.selectShouldRecCusAmt().doubleValue()));
			jo.put("cusDebt",
					new DecimalFormat("0.00").format(pssReportFeign.getCusAndSupplierDebt().get(0).get("value1")));
			json.add(jo);
			// 供应商欠款
			jo = new JSONObject();
			// jo	("supplierDebt", new
			// DecimalFormat("0.00").format(pssPaymentBillFeign.selectShouldPaySuppAmt().doubleValue()));
			jo.put("supplierDebt",
					new DecimalFormat("0.00").format(pssReportFeign.getCusAndSupplierDebt().get(0).get("value2")));
			json.add(jo);

			// 销售收入(本月)
			jo = new JSONObject();
			BigDecimal currentSaleAmount = pssReportFeign.getCurrentMonthSaleAmount(beginDate, endDate);
			jo.put("saleIncome", new DecimalFormat("0.00").format(currentSaleAmount.doubleValue()));
			json.add(jo);
			// 销售毛利(本月)
			jo = new JSONObject();
			BigDecimal currentSaleProfitAmount = pssReportFeign.getCurrentMonthSaleProfitAmount(beginDate, endDate);
			jo.put("saleGross", new DecimalFormat("0.00").format(currentSaleProfitAmount.doubleValue()));
			json.add(jo);
			// 采购金额(本月)
			jo = new JSONObject();
			BigDecimal currentPurchaseAmount = pssReportFeign.getCurrentMonthPerchaseAmount(beginDate, endDate);
			jo.put("buyAmount", new DecimalFormat("0.00").format(currentPurchaseAmount.doubleValue()));
			json.add(jo);
			// 商品种类(本月)
			Integer i = pssReportFeign.getCurrentMonthGoodsPurchaseCount(beginDate, endDate);
			jo = new JSONObject();
			jo.put("goodsType", i);
			json.add(jo);

			dataMap.put("generalPageMenuDataJson", json);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");

		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}

}
