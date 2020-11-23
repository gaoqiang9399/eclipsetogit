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
import app.component.pss.sales.entity.PssSaleBillDetail;
import app.component.pss.sales.entity.PssSaleOrder;
import app.component.pss.sales.feign.PssSaleBillFeign;
import app.component.pss.sales.feign.PssSaleOrderFeign;
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
 * Title: PssSaleBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Sep 05 15:01:53 CST 2017
 **/
@Controller
@RequestMapping("/pssSaleBill")
public class PssSaleBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssSaleBillFeign pssSaleBillFeign;
	@Autowired
	private PssGoodsFeign pssGoodsFeign;
	@Autowired
	private PssStorehouseFeign pssStorehouseFeign;
	@Autowired
	private PssSaleOrderFeign pssSaleOrderFeign;
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
	private void getTableData(String tableId, PssSaleBill pssSaleBill) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssSaleBillFeign.getAll(pssSaleBill), null, true);
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
		FormData formdl_psssalebill02 = formService.getFormData("dl_psssalebill02");
		PssSaleBill pssSaleBill = new PssSaleBill();
		List<PssSaleBill> pssSaleBillList = pssSaleBillFeign.getAll(pssSaleBill);
		model.addAttribute("formdl_psssalebill02", formdl_psssalebill02);
		model.addAttribute("pssSaleBillList", pssSaleBillList);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleBill_List";
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
			FormData formdl_psssalebill02 = formService.getFormData("dl_psssalebill02");
			getFormValue(formdl_psssalebill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalebill02)) {
				PssSaleBill pssSaleBill = new PssSaleBill();
				setObjValue(formdl_psssalebill02, pssSaleBill);
				pssSaleBillFeign.insert(pssSaleBill);
				getTableData(tableId,pssSaleBill);// 获取列表
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
		PssSaleBill pssSaleBill = new PssSaleBill();
		try {
			FormData formdl_psssalebill02 = formService.getFormData("dl_psssalebill02");
			getFormValue(formdl_psssalebill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalebill02)) {
				pssSaleBill = new PssSaleBill();
				setObjValue(formdl_psssalebill02, pssSaleBill);
				pssSaleBillFeign.update(pssSaleBill);
				getTableData(tableId,pssSaleBill);// 获取列表
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
	public Map<String, Object> getByIdAjax(String saleBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_psssalebill02 = formService.getFormData("dl_psssalebill02");
		PssSaleBill pssSaleBill = new PssSaleBill();
		pssSaleBill.setSaleBillId(saleBillId);
		pssSaleBill = pssSaleBillFeign.getById(pssSaleBill);
		getObjValue(formdl_psssalebill02, pssSaleBill, formData);
		if (pssSaleBill != null) {
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
	public Map<String, Object> deleteAjax(String saleBillId,String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleBill pssSaleBill = new PssSaleBill();
		pssSaleBill.setSaleBillId(saleBillId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssSaleBill = (PssSaleBill) JSONObject.toBean(jb, PssSaleBill.class);
			pssSaleBillFeign.delete(pssSaleBill);
			getTableData(tableId,pssSaleBill);// 获取列表
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

		JSONArray receivedStateJsonArray = codeUtils.getJSONArrayByKeyName("PSS_RECEIVED_STATE");
		this.getHttpRequest().setAttribute("receivedStateJsonArray", receivedStateJsonArray);

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
		return "/component/pss/sales/PssSaleBill_List";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleBill pssSaleBill = new PssSaleBill();
		try {
			pssSaleBill.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pssSaleBill.setCustomSorts(ajaxData);// 自定义排序参数赋值
			pssSaleBill.setCriteriaList(pssSaleBill, ajaxData);// 我的筛选

			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = pssSaleBillFeign.findByPage(ipage, pssSaleBill);
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
	public String getDetailPage(Model model, String saleBillId, String saleBillNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleBill pssSaleBill = new PssSaleBill();
		if (StringUtil.isNotEmpty(saleBillId)) {
			pssSaleBill.setSaleBillId(saleBillId);
			pssSaleBill = pssSaleBillFeign.getById(pssSaleBill);
		} else if (StringUtil.isNotEmpty(saleBillNo)) {
			pssSaleBill.setSaleBillNo(saleBillNo);
			pssSaleBill = pssSaleBillFeign.getBySaleBillNo(pssSaleBill);
		} else {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键或销货单号"));
		}

		if (pssSaleBill == null) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		} else {
			saleBillId = pssSaleBill.getSaleBillId();
			dataMap.put("cusNo", pssSaleBill.getCusNo());
			dataMap.put("salerNo", pssSaleBill.getSalerNo());
			dataMap.put("billDate", DateUtil.getShowDateTime(pssSaleBill.getBillDate()));
			dataMap.put("saleBillNo", pssSaleBill.getSaleBillNo());
			dataMap.put("auditStsed", pssSaleBill.getAuditStsed());
			dataMap.put("docBizNo", pssSaleBill.getSaleBillId());
			FormData formdl_psssalebill02 = formService.getFormData("dl_psssalebill02");
			getObjValue(formdl_psssalebill02, pssSaleBill);

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
			model.addAttribute("formdl_psssalebill02", formdl_psssalebill02);
			model.addAttribute("ajaxData", ajaxData);
		}
		
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleBill_Detail";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAddPage")
	public String getAddPage(Model model, String saleOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_psssalebill02 = formService.getFormData("dl_psssalebill02");
		String saleBillNo = pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.XHD.getValue(), "view");
		dataMap.put("saleBillNo", saleBillNo);
		String currentDate = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("billDate", currentDate);
		dataMap.put("docBizNo", WaterIdUtil.getWaterId("SB"));

		if (saleOrderId != null && !"".equals(saleOrderId)) {
			// 由销货订单生成
			PssSaleOrder pssSaleOrder = new PssSaleOrder();
			pssSaleOrder.setSaleOrderId(saleOrderId);
			pssSaleOrder = pssSaleOrderFeign.getById(pssSaleOrder);
			if (pssSaleOrder != null) {
				PssSaleBill pssSaleBill = new PssSaleBill();
				pssSaleBill.setCusNo(pssSaleOrder.getCusNo());
				pssSaleBill.setSalerNo(pssSaleOrder.getSalerNo());
				pssSaleBill.setSaleOrderNo(pssSaleOrder.getSaleOrderNo());
				pssSaleBill.setMemo(pssSaleOrder.getMemo());
				pssSaleBill.setDiscountRate(pssSaleOrder.getDiscountRate());
				pssSaleBill.setDiscountAmount(pssSaleOrder.getDiscountAmount());
				pssSaleBill.setDiscountAfterAmount(pssSaleOrder.getDiscountAfterAmount());
				pssSaleBill.setThisReceipt(0.0);
				pssSaleBill.setCustomerCost(0.0);
				pssSaleBill.setThisDebt(pssSaleOrder.getDiscountAfterAmount());
				pssSaleBill.setSaleExpense(0.0);
				dataMap.put("cusNo", pssSaleBill.getCusNo());
				dataMap.put("salerNo", pssSaleBill.getSalerNo());
				formdl_psssalebill02 = formService.getFormData("dl_psssalebill02");
				getObjValue(formdl_psssalebill02, pssSaleBill);
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
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

		model.addAttribute("formdl_psssalebill02", formdl_psssalebill02);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleBill_Detail";
	}

	@RequestMapping(value = "/getNewSaleBillNoAjax")
	@ResponseBody
	public Map<String, Object> getNewSaleBillNoAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String saleBillNo = pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.XHD.getValue(), "view");
			dataMap.put("saleBillNo", saleBillNo);
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
	 * 保存销货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSaleBillAjax")
	@ResponseBody
	public Map<String, Object> saveSaleBillAjax(String ajaxData,String pssSaleBillDetailsJson,String pssBuySaleExpBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdl_psssalebill02 = formService.getFormData("dl_psssalebill02");
			getFormValue(formdl_psssalebill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalebill02)) {
				PssSaleBill pssSaleBill = new PssSaleBill();
				setObjValue(formdl_psssalebill02, pssSaleBill);

				List<PssSaleBillDetail> pssSaleBillDetailList = PssPublicUtil.getMapByJsonObj(new PssSaleBillDetail(),
						pssSaleBillDetailsJson);
				List<PssBuySaleExpBill> pssBuySaleExpBillList = PssPublicUtil.getMapByJsonObj(new PssBuySaleExpBill(),
						pssBuySaleExpBillsJson);

				boolean success = pssSaleBillFeign.doSaveSaleBill(pssSaleBill, pssSaleBillDetailList,
						pssBuySaleExpBillList);
				String saleBillId = pssSaleBill.getSaleBillId();
				dataMap.put("saleBillId", saleBillId);
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

	@RequestMapping(value = "/beforeGenerateSaleReturnBillAjax")
	@ResponseBody
	public Map<String, Object> beforeGenerateSaleReturnBillAjax(String saleBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (saleBillId == null || "".equals(saleBillId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssSaleBill pssSaleBill = new PssSaleBill();
		pssSaleBill.setSaleBillId(saleBillId);
		try {
			pssSaleBillFeign.beforeGenerateSaleReturnBill(pssSaleBill);
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
	 * 审核销货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditSaleBillAjax")
	@ResponseBody
	public Map<String, Object> auditSaleBillAjax(String ajaxData,String pssSaleBillDetailsJson,String pssBuySaleExpBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdl_psssalebill02 = formService.getFormData("dl_psssalebill02");
			getFormValue(formdl_psssalebill02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalebill02)) {
				PssSaleBill pssSaleBill = new PssSaleBill();
				setObjValue(formdl_psssalebill02, pssSaleBill);

				List<PssSaleBillDetail> pssSaleBillDetailList = PssPublicUtil.getMapByJsonObj(new PssSaleBillDetail(),
						pssSaleBillDetailsJson);
				List<PssBuySaleExpBill> pssBuySaleExpBillList = PssPublicUtil.getMapByJsonObj(new PssBuySaleExpBill(),
						pssBuySaleExpBillsJson);

				boolean success = pssSaleBillFeign.doAuditSaleBill1(pssSaleBill, pssSaleBillDetailList,
						pssBuySaleExpBillList);
				String saleBillId = pssSaleBill.getSaleBillId();
				dataMap.put("saleBillId", saleBillId);
				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("销货单审核"));
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("销货单审核"));
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
	 * 批量审核销货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchAuditSaleBillAjax")
	@ResponseBody
	public Map<String, Object> batchAuditSaleBillAjax(String pssSaleBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSaleBill> pssSaleBillList = PssPublicUtil.getMapByJsonObj(new PssSaleBill(), pssSaleBillsJson);
			if (pssSaleBillList != null && pssSaleBillList.size() > 0) {
				String msg = "";
				for (PssSaleBill pssSaleBill : pssSaleBillList) {
					try {
						boolean success = pssSaleBillFeign.doAuditSaleBill(pssSaleBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("销货单[" + pssSaleBill.getSaleBillNo() + "]审核") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION.getMessage("销货单[" + pssSaleBill.getSaleBillNo() + "]审核")
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
	 * 反审核销货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reverseAuditSaleBillAjax")
	@ResponseBody
	public Map<String, Object> reverseAuditSaleBillAjax(String saleBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (saleBillId == null || "".equals(saleBillId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssSaleBill pssSaleBill = new PssSaleBill();
		pssSaleBill.setSaleBillId(saleBillId);
		try {
			boolean success = pssSaleBillFeign.doReverseAuditSaleBill(pssSaleBill);
			saleBillId = pssSaleBill.getSaleBillId();
			dataMap.put("saleBillId", saleBillId);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("销货单反审核"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("销货单反审核"));
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
	 * 批量反审核销货单
	 * @param pssSaleBillsJson 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchReverseAuditSaleBillAjax")
	@ResponseBody
	public Map<String, Object> batchReverseAuditSaleBillAjax(String ajaxData, String pssSaleBillsJson) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSaleBill> pssSaleBillList = PssPublicUtil.getMapByJsonObj(new PssSaleBill(), pssSaleBillsJson);
			if (pssSaleBillList != null && pssSaleBillList.size() > 0) {
				String msg = "";
				for (PssSaleBill pssSaleBill : pssSaleBillList) {
					try {
						boolean success = pssSaleBillFeign.doReverseAuditSaleBill(pssSaleBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("销货单[" + pssSaleBill.getSaleBillNo() + "]反审核") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("销货单[" + pssSaleBill.getSaleBillNo() + "]反审核") + "<br>";
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
	 * 删除销货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSaleBillAjax")
	@ResponseBody
	public Map<String, Object> deleteSaleBillAjax(String saleBillId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (saleBillId == null || "".equals(saleBillId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssSaleBill pssSaleBill = new PssSaleBill();
		pssSaleBill.setSaleBillId(saleBillId);
		try {
			boolean success = pssSaleBillFeign.deleteSaleBill(pssSaleBill);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("销货单删除"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("销货单删除"));
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
	 * 批量删除销货单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchDeleteSaleBillAjax")
	@ResponseBody
	public Map<String, Object> batchDeleteSaleBillAjax(String pssSaleBillsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSaleBill> pssSaleBillList = PssPublicUtil.getMapByJsonObj(new PssSaleBill(), pssSaleBillsJson);
			if (pssSaleBillList != null && pssSaleBillList.size() > 0) {
				String msg = "";
				for (PssSaleBill pssSaleBill : pssSaleBillList) {
					try {
						boolean success = pssSaleBillFeign.deleteSaleBill(pssSaleBill);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("销货单[" + pssSaleBill.getSaleBillNo() + "]删除") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION.getMessage("销货单[" + pssSaleBill.getSaleBillNo() + "]删除")
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("downloadTmplate", "PssSaleBillAction_downloadTemplate.action");
		dataMap.put("serverUrl", "PssSaleBillActionAjax_uploadExcelAjax.action");
		dataMap.put("downloadErrorUrl", "PssSaleBillAction_downloadErrorExcel.action");

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

			String excelName = "销货单导入模板";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			// 销货单
			String type = "PssSaleBill_import";
			HSSFWorkbook pssWorkBook = excelInterfaceFeign.getDownFileStream(type);
			// 输出流
			ServletOutputStream out = response.getOutputStream();
			pssWorkBook.write(out);
			response.flushBuffer();
			out.flush();
			out.close();

		} catch (Exception e) {
			//Log.error("PssSaleBillAction_downloadTemplate方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
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
		String type = "PssSaleBill_import";
		try {
			String result = pssSaleBillFeign.uploadExcel(vch, type);
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
			dataMap.put("msg", "销货单导入失败");
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
		ActionContext.initialize(request, response);
		//HttpServletResponse response = response;
		FileInputStream in = null;
		ServletOutputStream out = null;
		try{
			String excelName = "销货单导入校验文件";
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
	public void downloadToExcel(Model model,String saleBillIds) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		try {
			// HttpServletRequest request = request;
			//HttpServletResponse response = response;

			String excelName = "销货单导出";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("saleBillIds", saleBillIds);
			paramMap.put("type", "PssSaleBill_export");
			HSSFWorkbook pssWorkBook = pssSaleBillFeign.downloadToExcel(paramMap);
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
