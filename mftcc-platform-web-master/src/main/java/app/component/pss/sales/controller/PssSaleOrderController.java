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
import app.component.pss.information.entity.PssCustomerInfo;
import app.component.pss.information.entity.PssGoods;
import app.component.pss.information.entity.PssStorehouse;
import app.component.pss.information.feign.PssBillnoConfFeign;
import app.component.pss.information.feign.PssCustomerInfoFeign;
import app.component.pss.information.feign.PssGoodsFeign;
import app.component.pss.information.feign.PssStorehouseFeign;
import app.component.pss.sales.entity.PssSaleOrder;
import app.component.pss.sales.entity.PssSaleOrderDetail;
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
 * Title: PssSaleOrderAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 31 16:53:40 CST 2017
 **/
@Controller
@RequestMapping("/pssSaleOrder")
public class PssSaleOrderController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssSaleOrderFeign pssSaleOrderFeign;
	@Autowired
	private PssGoodsFeign pssGoodsFeign;
	@Autowired
	private PssStorehouseFeign pssStorehouseFeign;
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
	private void getTableData(String tableId, PssSaleOrder pssSaleOrder) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssSaleOrderFeign.getAll(pssSaleOrder), null, true);
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
		FormData formdl_psssaleorder02 = formService.getFormData("dl_psssaleorder02");
		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		List<PssSaleOrder> pssSaleOrderList = pssSaleOrderFeign.getAll(pssSaleOrder);
		model.addAttribute("formdl_psssaleorder02", formdl_psssaleorder02);
		model.addAttribute("pssSaleOrderList", pssSaleOrderList);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleOrder_List";
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
			FormData formdl_psssaleorder02 = formService.getFormData("dl_psssaleorder02");
			getFormValue(formdl_psssaleorder02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssaleorder02)) {
				PssSaleOrder pssSaleOrder = new PssSaleOrder();
				setObjValue(formdl_psssaleorder02, pssSaleOrder);
				pssSaleOrderFeign.insert(pssSaleOrder);
				getTableData(tableId,pssSaleOrder);// 获取列表
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
		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		try {
			FormData formdl_psssaleorder02 = formService.getFormData("dl_psssaleorder02");
			getFormValue(formdl_psssaleorder02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssaleorder02)) {
				pssSaleOrder = new PssSaleOrder();
				setObjValue(formdl_psssaleorder02, pssSaleOrder);
				pssSaleOrderFeign.update(pssSaleOrder);
				getTableData(tableId,pssSaleOrder);// 获取列表
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
	public Map<String, Object> getByIdAjax(String saleOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_psssaleorder02 = formService.getFormData("dl_psssaleorder02");
		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		pssSaleOrder.setSaleOrderId(saleOrderId);
		pssSaleOrder = pssSaleOrderFeign.getById(pssSaleOrder);
		getObjValue(formdl_psssaleorder02, pssSaleOrder, formData);
		if (pssSaleOrder != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String saleOrderId,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		pssSaleOrder.setSaleOrderId(saleOrderId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssSaleOrder = (PssSaleOrder) JSONObject.toBean(jb, PssSaleOrder.class);
			pssSaleOrderFeign.delete(pssSaleOrder);
			getTableData(tableId,pssSaleOrder);// 获取列表
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
		JSONArray businessTypeJsonArray = codeUtils.getJSONArrayByKeyName("PSS_SALE_BUSINESS_TYPE");
		this.getHttpRequest().setAttribute("businessTypeJsonArray", businessTypeJsonArray);

		JSONArray auditStsedJsonArray = codeUtils.getJSONArrayByKeyName("PSS_AUDIT_STSED");
		this.getHttpRequest().setAttribute("auditStsedJsonArray", auditStsedJsonArray);

		JSONArray orderStateJsonArray = codeUtils.getJSONArrayByKeyName("PSS_SALE_ORDER_STATE");
		this.getHttpRequest().setAttribute("orderStateJsonArray", orderStateJsonArray);

		JSONArray enabledStatusJsonArray = codeUtils.getJSONArrayByKeyName("PSS_ENABLED_STATUS");
		this.getHttpRequest().setAttribute("enabledStatusJsonArray", enabledStatusJsonArray);

		JSONArray lockStatusJsonArray = codeUtils.getJSONArrayByKeyName("PSS_LOCK_STATUS");
		this.getHttpRequest().setAttribute("lockStatusJsonArray", lockStatusJsonArray);

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
		return "/component/pss/sales/PssSaleOrder_List";
	}

	/**
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String orderState,String auditStsed,String enabledStatus,String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		try {
			pssSaleOrder.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pssSaleOrder.setCustomSorts(ajaxData);// 自定义排序参数赋值
			pssSaleOrder.setCriteriaList(pssSaleOrder, ajaxData);// 我的筛选

			if (orderState != null && !"".equals(orderState)) {
				pssSaleOrder.setOrderState(orderState);
			}
			if (auditStsed != null && !"".equals(auditStsed)) {
				pssSaleOrder.setAuditStsed(auditStsed);
			}
			if (enabledStatus != null && !"".equals(enabledStatus)) {
				pssSaleOrder.setEnabledStatus(enabledStatus);
			}

			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = pssSaleOrderFeign.findByPage(ipage, pssSaleOrder);
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
	public String getDetailPage(Model model,String saleOrderNo,String saleOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		
		if (StringUtil.isNotEmpty(saleOrderId)) {
			pssSaleOrder.setSaleOrderId(saleOrderId);
			pssSaleOrder = pssSaleOrderFeign.getById(pssSaleOrder);
		} else if (StringUtil.isNotEmpty(saleOrderNo)) {
			pssSaleOrder.setSaleOrderNo(saleOrderNo);
			pssSaleOrder = pssSaleOrderFeign.getBySaleOrderNo(pssSaleOrder);
		} else {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键或销货订单号"));
		}

		if (pssSaleOrder == null) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		} else {
			saleOrderId = pssSaleOrder.getSaleOrderId();
			dataMap.put("cusNo", pssSaleOrder.getCusNo());
			dataMap.put("salerNo", pssSaleOrder.getSalerNo());
			dataMap.put("orderDate", DateUtil.getShowDateTime(pssSaleOrder.getOrderDate()));
			dataMap.put("deliveryDate", DateUtil.getShowDateTime(pssSaleOrder.getDeliveryDate()));
			dataMap.put("saleOrderNo", pssSaleOrder.getSaleOrderNo());
			dataMap.put("auditStsed", pssSaleOrder.getAuditStsed());
			dataMap.put("enabledStatus", pssSaleOrder.getEnabledStatus());
			dataMap.put("lockStatus", pssSaleOrder.getLockStatus());
			FormData formdl_psssaleorder02 = formService.getFormData("dl_psssaleorder02");
			getObjValue(formdl_psssaleorder02, pssSaleOrder);

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
			model.addAttribute("formdl_psssaleorder02", formdl_psssaleorder02);
			model.addAttribute("ajaxData", ajaxData);
		}
		
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleOrder_Detail";
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAddPage")
	public String getAddPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_psssaleorder02 = formService.getFormData("dl_psssaleorder02");
		String saleOrderNo = pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.XHDD.getValue(), "view");
		dataMap.put("saleOrderNo", saleOrderNo);
		String currentDate = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("orderDate", currentDate);
		dataMap.put("deliveryDate", currentDate);

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

		model.addAttribute("formdl_psssaleorder01", formdl_psssaleorder02);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleOrder_Detail";
	}

	/**
	 * 获取新的单据编号
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNewSaleOrderNoAjax")
	@ResponseBody
	public Map<String, Object> getNewSaleOrderNoAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String saleOrderNo = pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.XHDD.getValue(), "view");
			dataMap.put("saleOrderNo", saleOrderNo);
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
	 * 保存销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> saveSaleOrderAjax(String ajaxData,String pssSaleOrderDetailsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdl_psssaleorder02 = formService.getFormData("dl_psssaleorder02");
			getFormValue(formdl_psssaleorder02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssaleorder02)) {
				PssSaleOrder pssSaleOrder = new PssSaleOrder();
				setObjValue(formdl_psssaleorder02, pssSaleOrder);

				List<PssSaleOrderDetail> pssSaleOrderDetailList = PssPublicUtil
						.getMapByJsonObj(new PssSaleOrderDetail(), pssSaleOrderDetailsJson);

				boolean success = pssSaleOrderFeign.doSaveSaleOrder(pssSaleOrder, pssSaleOrderDetailList);
				String saleOrderId = pssSaleOrder.getSaleOrderId();
				dataMap.put("saleOrderId", saleOrderId);
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

	@RequestMapping(value = "/beforeGenerateBillAjax")
	@ResponseBody
	public Map<String, Object> beforeGenerateBillAjax(String saleOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (saleOrderId == null || "".equals(saleOrderId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		pssSaleOrder.setSaleOrderId(saleOrderId);
		try {
			pssSaleOrderFeign.beforeGenerateBill(pssSaleOrder);
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
	 * 审核销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> auditSaleOrderAjax(String ajaxData,String pssSaleOrderDetailsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdl_psssaleorder02 = formService.getFormData("dl_psssaleorder02");
			getFormValue(formdl_psssaleorder02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssaleorder02)) {
				PssSaleOrder pssSaleOrder = new PssSaleOrder();
				setObjValue(formdl_psssaleorder02, pssSaleOrder);

				List<PssSaleOrderDetail> pssSaleOrderDetailList = PssPublicUtil
						.getMapByJsonObj(new PssSaleOrderDetail(), pssSaleOrderDetailsJson);

				boolean success = pssSaleOrderFeign.doAuditSaleOrder(pssSaleOrder, pssSaleOrderDetailList);
				String saleOrderId = pssSaleOrder.getSaleOrderId();
				dataMap.put("saleOrderId", saleOrderId);
				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("销货订单审核"));
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("销货订单审核"));
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
	 * 批量审核销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchAuditSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> batchAuditSaleOrderAjax(String pssSaleOrdersJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSaleOrder> pssSaleOrderList = PssPublicUtil.getMapByJsonObj(new PssSaleOrder(), pssSaleOrdersJson);
			if (pssSaleOrderList != null && pssSaleOrderList.size() > 0) {
				String msg = "";
				for (PssSaleOrder pssSaleOrder : pssSaleOrderList) {
					try {
						boolean success = pssSaleOrderFeign.doAuditSaleOrder(pssSaleOrder);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]审核") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]审核") + "<br>";
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
	 * 反审核销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reverseAuditSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> reverseAuditSaleOrderAjax(String saleOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (saleOrderId == null || "".equals(saleOrderId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		pssSaleOrder.setSaleOrderId(saleOrderId);
		try {
			boolean success = pssSaleOrderFeign.doReverseAuditSaleOrder(pssSaleOrder);
			saleOrderId = pssSaleOrder.getSaleOrderId();
			dataMap.put("saleOrderId", saleOrderId);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("销货订单反审核"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("销货订单反审核"));
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
	 * 批量反审核销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchReverseAuditSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> batchReverseAuditSaleOrderAjax(String pssSaleOrdersJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSaleOrder> pssSaleOrderList = PssPublicUtil.getMapByJsonObj(new PssSaleOrder(), pssSaleOrdersJson);
			if (pssSaleOrderList != null && pssSaleOrderList.size() > 0) {
				String msg = "";
				for (PssSaleOrder pssSaleOrder : pssSaleOrderList) {
					try {
						boolean success = pssSaleOrderFeign.doReverseAuditSaleOrder(pssSaleOrder);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]反审核") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]反审核") + "<br>";
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
	 * 更新启用状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateEnabledStatusAjax")
	@ResponseBody
	public Map<String, Object> updateEnabledStatusAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		try {
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			String saleOrderId = (String) jsonObject.get("saleOrderId");
			if (StringUtil.isEmpty(saleOrderId)) {
				throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
			}
			pssSaleOrder.setSaleOrderId(saleOrderId);
			pssSaleOrder = pssSaleOrderFeign.getById(pssSaleOrder);
			String enabledStatus = (String) jsonObject.get("enabledStatus");
			if (StringUtil.isNotEmpty(enabledStatus)) {
				if (PssEnumBean.ENABLED_STATUS.CLOSED.getValue().equals(enabledStatus)) {
					// 关闭
					pssSaleOrderFeign.doCloseSaleOrder(pssSaleOrder);
				} else if (PssEnumBean.ENABLED_STATUS.ENABLED.getValue().equals(enabledStatus)) {
					// 启用
					pssSaleOrderFeign.doEnableSaleOrder(pssSaleOrder);
				} else {
					throw new Exception(MessageEnum.FAILED_SAVE_CONTENT.getMessage("启用状态不正确"));
				}
			} else {
				throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("启用状态"));
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
	 * 关闭销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/closeSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> closeSaleOrderAjax(String saleOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (saleOrderId == null || "".equals(saleOrderId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		pssSaleOrder.setSaleOrderId(saleOrderId);
		try {
			boolean success = pssSaleOrderFeign.doCloseSaleOrder(pssSaleOrder);
			saleOrderId = pssSaleOrder.getSaleOrderId();
			dataMap.put("saleOrderId", saleOrderId);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("销货订单关闭"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("销货订单关闭"));
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
	 * 批量关闭销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchCloseSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> batchCloseSaleOrderAjax(String pssSaleOrdersJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSaleOrder> pssSaleOrderList = PssPublicUtil.getMapByJsonObj(new PssSaleOrder(), pssSaleOrdersJson);
			if (pssSaleOrderList != null && pssSaleOrderList.size() > 0) {
				String msg = "";
				for (PssSaleOrder pssSaleOrder : pssSaleOrderList) {
					try {
						boolean success = pssSaleOrderFeign.doCloseSaleOrder(pssSaleOrder);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]关闭") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]关闭") + "<br>";
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
	 * 开启销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enableSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> enableSaleOrderAjax(String saleOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (saleOrderId == null || "".equals(saleOrderId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		pssSaleOrder.setSaleOrderId(saleOrderId);
		try {
			boolean success = pssSaleOrderFeign.doEnableSaleOrder(pssSaleOrder);
			saleOrderId = pssSaleOrder.getSaleOrderId();
			dataMap.put("saleOrderId", saleOrderId);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("销货订单开启"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("销货订单开启"));
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
	 * 批量启用销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchEnableSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> batchEnableSaleOrderAjax(String pssSaleOrdersJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSaleOrder> pssSaleOrderList = PssPublicUtil.getMapByJsonObj(new PssSaleOrder(), pssSaleOrdersJson);
			if (pssSaleOrderList != null && pssSaleOrderList.size() > 0) {
				String msg = "";
				for (PssSaleOrder pssSaleOrder : pssSaleOrderList) {
					try {
						boolean success = pssSaleOrderFeign.doEnableSaleOrder(pssSaleOrder);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]启用") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]启用") + "<br>";
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
	 * 锁定销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/lockSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> lockSaleOrderAjax(String saleOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (saleOrderId == null || "".equals(saleOrderId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		pssSaleOrder.setSaleOrderId(saleOrderId);
		try {
			boolean success = pssSaleOrderFeign.doLockSaleOrder(pssSaleOrder);
			saleOrderId = pssSaleOrder.getSaleOrderId();
			dataMap.put("saleOrderId", saleOrderId);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("销货订单锁定"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("销货订单锁定"));
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
	 * 批量关闭销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchLockSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> batchLockSaleOrderAjax(String pssSaleOrdersJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSaleOrder> pssSaleOrderList = PssPublicUtil.getMapByJsonObj(new PssSaleOrder(), pssSaleOrdersJson);
			if (pssSaleOrderList != null && pssSaleOrderList.size() > 0) {
				String msg = "";
				for (PssSaleOrder pssSaleOrder : pssSaleOrderList) {
					try {
						boolean success = pssSaleOrderFeign.doLockSaleOrder(pssSaleOrder);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]锁定") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]锁定") + "<br>";
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
	 * 解锁销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unlockSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> unlockSaleOrderAjax(String saleOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (saleOrderId == null || "".equals(saleOrderId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		pssSaleOrder.setSaleOrderId(saleOrderId);
		try {
			boolean success = pssSaleOrderFeign.doUnlockSaleOrder(pssSaleOrder);
			saleOrderId = pssSaleOrder.getSaleOrderId();
			dataMap.put("saleOrderId", saleOrderId);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("销货订单解锁"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("销货订单解锁"));
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
	 * 批量解锁销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchUnlockSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> batchUnlockSaleOrderAjax(String pssSaleOrdersJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSaleOrder> pssSaleOrderList = PssPublicUtil.getMapByJsonObj(new PssSaleOrder(), pssSaleOrdersJson);
			if (pssSaleOrderList != null && pssSaleOrderList.size() > 0) {
				String msg = "";
				for (PssSaleOrder pssSaleOrder : pssSaleOrderList) {
					try {
						boolean success = pssSaleOrderFeign.doUnlockSaleOrder(pssSaleOrder);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]解锁") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]解锁") + "<br>";
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
	 * 删除销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> deleteSaleOrderAjax(String saleOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (saleOrderId == null || "".equals(saleOrderId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssSaleOrder pssSaleOrder = new PssSaleOrder();
		pssSaleOrder.setSaleOrderId(saleOrderId);
		try {
			boolean success = pssSaleOrderFeign.deleteSaleOrder(pssSaleOrder);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("销货订单删除"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("销货订单删除"));
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
	 * 批量删除销货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchDeleteSaleOrderAjax")
	@ResponseBody
	public Map<String, Object> batchDeleteSaleOrderAjax(String pssSaleOrdersJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssSaleOrder> pssSaleOrderList = PssPublicUtil.getMapByJsonObj(new PssSaleOrder(), pssSaleOrdersJson);
			if (pssSaleOrderList != null && pssSaleOrderList.size() > 0) {
				String msg = "";
				for (PssSaleOrder pssSaleOrder : pssSaleOrderList) {
					try {
						boolean success = pssSaleOrderFeign.deleteSaleOrder(pssSaleOrder);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]删除") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("销货订单[" + pssSaleOrder.getSaleOrderNo() + "]删除") + "<br>";
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
		dataMap.put("downloadTmplate", "PssSaleOrderAction_downloadTemplate.action");
		dataMap.put("serverUrl", "PssSaleOrderActionAjax_uploadExcelAjax.action");
		dataMap.put("downloadErrorUrl", "PssSaleOrderAction_downloadErrorExcel.action");

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
		
		ActionContext.initialize(request, response);
		try {
			//HttpServletResponse response = response;

			String excelName = "销货订单导入模板";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			// 销货订单
			String type = "PssSaleOrder_import";
			HSSFWorkbook pssWorkBook = excelInterfaceFeign.getDownFileStream(type);
			// 输出流
			ServletOutputStream out = response.getOutputStream();
			pssWorkBook.write(out);
			response.flushBuffer();
			out.flush();
			out.close();

		} catch (Exception e) {
			//Log.error("PssSaleOrderAction_downloadTemplate方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
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
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String type = "PssSaleOrder_import";
		try {
			String result = pssSaleOrderFeign.uploadExcel(vch, type);
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
			dataMap.put("msg", "销货订单导入失败");
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
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		//HttpServletResponse response = response;
		FileInputStream in = null;
		ServletOutputStream out = null;
		try {
			String excelName = "销货订单导入校验文件";
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
	public void downloadToExcel(Model model,String saleOrderIds) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		try {
			// HttpServletRequest request = request;
			//HttpServletResponse response = response;

			String excelName = "销货订单导出";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("saleOrderIds", saleOrderIds);
			paramMap.put("type", "PssSaleOrder_export");
			HSSFWorkbook pssWorkBook = pssSaleOrderFeign.downloadToExcel(paramMap);
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
