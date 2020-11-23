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
import app.component.pss.information.entity.PssGoods;
import app.component.pss.information.entity.PssStorehouse;
import app.component.pss.information.entity.PssSupplierInfo;
import app.component.pss.information.feign.PssBillnoConfFeign;
import app.component.pss.information.feign.PssGoodsFeign;
import app.component.pss.information.feign.PssStorehouseFeign;
import app.component.pss.information.feign.PssSupplierInfoFeign;
import app.component.pss.purchases.entity.PssBuyOrder;
import app.component.pss.purchases.entity.PssBuyOrderDetail;
import app.component.pss.purchases.feign.PssBuyOrderFeign;
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
 * Title: PssBuyOrderAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 10 13:26:18 CST 2017
 **/
@Controller
@RequestMapping("/pssBuyOrder")
public class PssBuyOrderController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssBuyOrderFeign pssBuyOrderFeign;
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
	private PssSupplierInfoFeign pssSupplierInfoFeign;
	@Autowired
	private ExcelInterfaceFeign excelInterfaceFeign;
	// 全局变量
	private String saleOrderDetailDatas; // 以销定购中销货订单明细数据集合(格式:"销货订单主键,商品ID,本次采购数量,本次采购价格;...")
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssBuyOrder pssBuyOrder) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssBuyOrderFeign.getAll(pssBuyOrder), null, true);
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
		FormData formdl_pssbuyorder02 = formService.getFormData("dl_pssbuyorder02");
		PssBuyOrder pssBuyOrder = new PssBuyOrder();
		List<PssBuyOrder> pssBuyOrderList = pssBuyOrderFeign.getAll(pssBuyOrder);
		model.addAttribute("formdl_pssbuyorder02", formdl_pssbuyorder02);
		model.addAttribute("pssBuyOrderList", pssBuyOrderList);
		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyOrder_List";
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
			FormData formdl_pssbuyorder02 = formService.getFormData("dl_pssbuyorder02");
			getFormValue(formdl_pssbuyorder02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuyorder02)) {
				PssBuyOrder pssBuyOrder = new PssBuyOrder();
				setObjValue(formdl_pssbuyorder02, pssBuyOrder);
				pssBuyOrderFeign.insert(pssBuyOrder);
				getTableData(tableId,pssBuyOrder);// 获取列表
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
		PssBuyOrder pssBuyOrder = new PssBuyOrder();
		try {
			FormData formdl_pssbuyorder02 = formService.getFormData("dl_pssbuyorder02");
			getFormValue(formdl_pssbuyorder02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuyorder02)) {
				pssBuyOrder = new PssBuyOrder();
				setObjValue(formdl_pssbuyorder02, pssBuyOrder);
				pssBuyOrderFeign.update(pssBuyOrder);
				getTableData(tableId,pssBuyOrder);// 获取列表
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
	public Map<String, Object> getByIdAjax(String buyOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_pssbuyorder02 = formService.getFormData("dl_pssbuyorder02");
		PssBuyOrder pssBuyOrder = new PssBuyOrder();
		pssBuyOrder.setBuyOrderId(buyOrderId);
		pssBuyOrder = pssBuyOrderFeign.getById(pssBuyOrder);
		getObjValue(formdl_pssbuyorder02, pssBuyOrder, formData);
		if (pssBuyOrder != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String buyOrderId,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyOrder pssBuyOrder = new PssBuyOrder();
		pssBuyOrder.setBuyOrderId(buyOrderId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssBuyOrder = (PssBuyOrder) JSONObject.toBean(jb, PssBuyOrder.class);
			pssBuyOrderFeign.delete(pssBuyOrder);
			getTableData(tableId,pssBuyOrder);// 获取列表
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
		JSONArray businessTypeJsonArray = codeUtils.getJSONArrayByKeyName("PSS_BUY_BUSINESS_TYPE");
		this.getHttpRequest().setAttribute("businessTypeJsonArray", businessTypeJsonArray);

		JSONArray auditStsedJsonArray = codeUtils.getJSONArrayByKeyName("PSS_AUDIT_STSED");
		this.getHttpRequest().setAttribute("auditStsedJsonArray", auditStsedJsonArray);

		JSONArray orderStateJsonArray = codeUtils.getJSONArrayByKeyName("PSS_BUY_ORDER_STATE");
		this.getHttpRequest().setAttribute("orderStateJsonArray", orderStateJsonArray);

		JSONArray enabledStatusJsonArray = codeUtils.getJSONArrayByKeyName("PSS_ENABLED_STATUS");
		this.getHttpRequest().setAttribute("enabledStatusJsonArray", enabledStatusJsonArray);

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
		return "/component/pss/purchases/PssBuyOrder_List";
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
			String ajaxData,String auditStsed) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyOrder pssBuyOrder = new PssBuyOrder();
		try {
			pssBuyOrder.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pssBuyOrder.setCustomSorts(ajaxData);// 自定义排序参数赋值
			pssBuyOrder.setCriteriaList(pssBuyOrder, ajaxData);// 我的筛选

			if (auditStsed != null && !"".equals(auditStsed)) {
				pssBuyOrder.setAuditStsed(auditStsed);
			}

			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = pssBuyOrderFeign.findByPage(ipage, pssBuyOrder);
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
	public String getDetailPage(Model model, String buyOrderId,String buyOrderNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyOrder pssBuyOrder = new PssBuyOrder();
		if (StringUtil.isNotEmpty(buyOrderId)) {
			pssBuyOrder.setBuyOrderId(buyOrderId);
			pssBuyOrder = pssBuyOrderFeign.getById(pssBuyOrder);
		} else if (StringUtil.isNotEmpty(buyOrderNo)) {
			pssBuyOrder.setBuyOrderNo(buyOrderNo);
			pssBuyOrder = pssBuyOrderFeign.getByBuyOrderNo(pssBuyOrder);
		} else {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键或购货订单号"));
		}

		if (pssBuyOrder == null) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		} else {
			buyOrderId = pssBuyOrder.getBuyOrderId();
			dataMap.put("supplierId", pssBuyOrder.getSupplierId());
			dataMap.put("orderDate", DateUtil.getShowDateTime(pssBuyOrder.getOrderDate()));
			dataMap.put("deliveryDate", DateUtil.getShowDateTime(pssBuyOrder.getDeliveryDate()));
			dataMap.put("buyOrderNo", pssBuyOrder.getBuyOrderNo());
			dataMap.put("auditStsed", pssBuyOrder.getAuditStsed());
			dataMap.put("enabledStatus", pssBuyOrder.getEnabledStatus());
			FormData formdl_pssbuyorder02 = formService.getFormData("dl_pssbuyorder02");
			getObjValue(formdl_pssbuyorder02, pssBuyOrder);

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
			model.addAttribute("formdl_pssbuyorder02", formdl_pssbuyorder02);
			model.addAttribute("ajaxData", ajaxData);
		}
		
		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyOrder_Detail";
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
		FormData formdl_pssbuyorder02 = formService.getFormData("dl_pssbuyorder02");
		String buyOrderNo = pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.GHDD.getValue(), "view");
		dataMap.put("buyOrderNo", buyOrderNo);
		String currentDate = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("orderDate", currentDate);
		dataMap.put("deliveryDate", currentDate);

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

		if (saleOrderDetailDatas != null && !"".equals(saleOrderDetailDatas)) {
			Map<String, PssGoods> disableGoodsMap = new HashMap<String, PssGoods>();
			queryPssGoods.setFlag(PssEnumBean.YES_OR_NO.NO.getNum());
			List<PssGoods> disableGoodsList = pssGoodsFeign.getAll(queryPssGoods);
			for (PssGoods pssGoods : disableGoodsList) {
				disableGoodsMap.put(pssGoods.getGoodsId(), pssGoods);
			}
			dataMap.put("disableGoodsMap", JSONObject.fromObject(disableGoodsMap));

			Map<String, PssStorehouse> disableStorehouseMap = new HashMap<String, PssStorehouse>();
			queryPssStorehouse.setFlag(PssEnumBean.YES_OR_NO.NO.getNum());
			List<PssStorehouse> disableStorehouseList = pssStorehouseFeign.getAll(queryPssStorehouse);
			for (PssStorehouse pssStorehouse : disableStorehouseList) {
				disableStorehouseMap.put(pssStorehouse.getStorehouseId(), pssStorehouse);
			}
			dataMap.put("disableStorehouseMap", JSONObject.fromObject(disableStorehouseMap));
		}

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

		json.put("saleOrderDetailDatas", saleOrderDetailDatas);
		String ajaxData = json.toString();

		model.addAttribute("formdl_pssbuyorder02", formdl_pssbuyorder02);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyOrder_Detail";
	}

	/**
	 * 获取新的单据编号
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNewBuyOrderNoAjax")
	@ResponseBody
	public Map<String, Object> getNewBuyOrderNoAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String buyOrderNo = pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.GHDD.getValue(), "view");
			dataMap.put("buyOrderNo", buyOrderNo);
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
	 * 保存购货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveBuyOrderAjax")
	@ResponseBody
	public Map<String, Object> saveBuyOrderAjax(String ajaxData,String pssBuyOrderDetailsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdl_pssbuyorder02 = formService.getFormData("dl_pssbuyorder02");
			getFormValue(formdl_pssbuyorder02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuyorder02)) {
				PssBuyOrder pssBuyOrder = new PssBuyOrder();
				setObjValue(formdl_pssbuyorder02, pssBuyOrder);

				List<PssBuyOrderDetail> pssBuyOrderDetailList = PssPublicUtil.getMapByJsonObj(new PssBuyOrderDetail(),
						pssBuyOrderDetailsJson);

				boolean success = pssBuyOrderFeign.doSaveBuyOrder(pssBuyOrder, pssBuyOrderDetailList);
				String buyOrderId = pssBuyOrder.getBuyOrderId();
				dataMap.put("buyOrderId", buyOrderId);
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
	public Map<String, Object> beforeGenerateBillAjax(String buyOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (buyOrderId == null || "".equals(buyOrderId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssBuyOrder pssBuyOrder = new PssBuyOrder();
		pssBuyOrder.setBuyOrderId(buyOrderId);
		try {
			pssBuyOrderFeign.beforeGenerateBill(pssBuyOrder);
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
	 * 审核购货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditBuyOrderAjax")
	@ResponseBody
	public Map<String, Object> auditBuyOrderAjax(String ajaxData,String pssBuyOrderDetailsJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdl_pssbuyorder02 = formService.getFormData("dl_pssbuyorder02");
			getFormValue(formdl_pssbuyorder02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuyorder02)) {
				PssBuyOrder pssBuyOrder = new PssBuyOrder();
				setObjValue(formdl_pssbuyorder02, pssBuyOrder);

				List<PssBuyOrderDetail> pssBuyOrderDetailList = PssPublicUtil.getMapByJsonObj(new PssBuyOrderDetail(),
						pssBuyOrderDetailsJson);

				boolean success = pssBuyOrderFeign.doAuditBuyOrder(pssBuyOrder, pssBuyOrderDetailList);
				String buyOrderId = pssBuyOrder.getBuyOrderId();
				dataMap.put("buyOrderId", buyOrderId);
				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("购货订单审核"));
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("购货订单审核"));
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
	 * 批量审核购货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchAuditBuyOrderAjax")
	@ResponseBody
	public Map<String, Object> batchAuditBuyOrderAjax(String pssBuyOrdersJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssBuyOrder> pssBuyOrderList = PssPublicUtil.getMapByJsonObj(new PssBuyOrder(), pssBuyOrdersJson);
			if (pssBuyOrderList != null && pssBuyOrderList.size() > 0) {
				String msg = "";
				for (PssBuyOrder pssBuyOrder : pssBuyOrderList) {
					try {
						boolean success = pssBuyOrderFeign.doAuditBuyOrder(pssBuyOrder);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("购货订单[" + pssBuyOrder.getBuyOrderNo() + "]审核") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("购货订单[" + pssBuyOrder.getBuyOrderNo() + "]审核") + "<br>";
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
	 * 反审核购货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reverseAuditBuyOrderAjax")
	@ResponseBody
	public Map<String, Object> reverseAuditBuyOrderAjax(String buyOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (buyOrderId == null || "".equals(buyOrderId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssBuyOrder pssBuyOrder = new PssBuyOrder();
		pssBuyOrder.setBuyOrderId(buyOrderId);
		try {
			boolean success = pssBuyOrderFeign.doReverseAuditBuyOrder(pssBuyOrder);
			buyOrderId = pssBuyOrder.getBuyOrderId();
			dataMap.put("buyOrderId", buyOrderId);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("购货订单反审核"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("购货订单反审核"));
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
	 * 批量反审核购货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchReverseAuditBuyOrderAjax")
	@ResponseBody
	public Map<String, Object> batchReverseAuditBuyOrderAjax(String pssBuyOrdersJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssBuyOrder> pssBuyOrderList = PssPublicUtil.getMapByJsonObj(new PssBuyOrder(), pssBuyOrdersJson);
			if (pssBuyOrderList != null && pssBuyOrderList.size() > 0) {
				String msg = "";
				for (PssBuyOrder pssBuyOrder : pssBuyOrderList) {
					try {
						boolean success = pssBuyOrderFeign.doReverseAuditBuyOrder(pssBuyOrder);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("购货订单[" + pssBuyOrder.getBuyOrderNo() + "]反审核") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("购货订单[" + pssBuyOrder.getBuyOrderNo() + "]反审核") + "<br>";
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
		PssBuyOrder pssBuyOrder = new PssBuyOrder();
		try {
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			String buyOrderId = (String) jsonObject.get("buyOrderId");
			if (StringUtil.isEmpty(buyOrderId)) {
				throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
			}
			pssBuyOrder.setBuyOrderId(buyOrderId);
			pssBuyOrder = pssBuyOrderFeign.getById(pssBuyOrder);
			String enabledStatus = (String) jsonObject.get("enabledStatus");
			if (StringUtil.isNotEmpty(enabledStatus)) {
				if (PssEnumBean.ENABLED_STATUS.CLOSED.getValue().equals(enabledStatus)) {
					// 关闭
					pssBuyOrderFeign.doCloseBuyOrder(pssBuyOrder);
				} else if (PssEnumBean.ENABLED_STATUS.ENABLED.getValue().equals(enabledStatus)) {
					// 启用
					pssBuyOrderFeign.doEnableBuyOrder(pssBuyOrder);
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
	 * 关闭购货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/closeBuyOrderAjax")
	@ResponseBody
	public Map<String, Object> closeBuyOrderAjax(String buyOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (buyOrderId == null || "".equals(buyOrderId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssBuyOrder pssBuyOrder = new PssBuyOrder();
		pssBuyOrder.setBuyOrderId(buyOrderId);
		try {
			boolean success = pssBuyOrderFeign.doCloseBuyOrder(pssBuyOrder);
			buyOrderId = pssBuyOrder.getBuyOrderId();
			dataMap.put("buyOrderId", buyOrderId);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("购货订单关闭"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("购货订单关闭"));
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
	 * 批量关闭购货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchCloseBuyOrderAjax")
	@ResponseBody
	public Map<String, Object> batchCloseBuyOrderAjax(String pssBuyOrdersJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssBuyOrder> pssBuyOrderList = PssPublicUtil.getMapByJsonObj(new PssBuyOrder(), pssBuyOrdersJson);
			if (pssBuyOrderList != null && pssBuyOrderList.size() > 0) {
				String msg = "";
				for (PssBuyOrder pssBuyOrder : pssBuyOrderList) {
					try {
						boolean success = pssBuyOrderFeign.doCloseBuyOrder(pssBuyOrder);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("购货订单[" + pssBuyOrder.getBuyOrderNo() + "]关闭") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("购货订单[" + pssBuyOrder.getBuyOrderNo() + "]关闭") + "<br>";
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
	 * 开启购货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enableBuyOrderAjax")
	@ResponseBody
	public Map<String, Object> enableBuyOrderAjax(String buyOrderId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (buyOrderId == null || "".equals(buyOrderId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssBuyOrder pssBuyOrder = new PssBuyOrder();
		pssBuyOrder.setBuyOrderId(buyOrderId);
		try {
			boolean success = pssBuyOrderFeign.doEnableBuyOrder(pssBuyOrder);
			buyOrderId = pssBuyOrder.getBuyOrderId();
			dataMap.put("buyOrderId", buyOrderId);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("购货订单开启"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("购货订单开启"));
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
	 * 批量启用购货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchEnableBuyOrderAjax")
	@ResponseBody
	public Map<String, Object> batchEnableBuyOrderAjax(String pssBuyOrdersJson) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssBuyOrder> pssBuyOrderList = PssPublicUtil.getMapByJsonObj(new PssBuyOrder(), pssBuyOrdersJson);
			if (pssBuyOrderList != null && pssBuyOrderList.size() > 0) {
				String msg = "";
				for (PssBuyOrder pssBuyOrder : pssBuyOrderList) {
					try {
						boolean success = pssBuyOrderFeign.doEnableBuyOrder(pssBuyOrder);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("购货订单[" + pssBuyOrder.getBuyOrderNo() + "]启用") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("购货订单[" + pssBuyOrder.getBuyOrderNo() + "]启用") + "<br>";
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
	 * 删除购货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBuyOrderAjax")
	@ResponseBody
	public Map<String, Object> deleteBuyOrderAjax(String buyOrderId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (buyOrderId == null || "".equals(buyOrderId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
		}

		PssBuyOrder pssBuyOrder = new PssBuyOrder();
		pssBuyOrder.setBuyOrderId(buyOrderId);
		try {
			boolean success = pssBuyOrderFeign.deleteBuyOrder(pssBuyOrder);
			if (success) {
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("购货订单删除"));
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("购货订单删除"));
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
	 * 批量删除购货订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchDeleteBuyOrderAjax")
	@ResponseBody
	public Map<String, Object> batchDeleteBuyOrderAjax(String pssBuyOrdersJson) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssBuyOrder> pssBuyOrderList = PssPublicUtil.getMapByJsonObj(new PssBuyOrder(), pssBuyOrdersJson);
			if (pssBuyOrderList != null && pssBuyOrderList.size() > 0) {
				String msg = "";
				for (PssBuyOrder pssBuyOrder : pssBuyOrderList) {
					try {
						boolean success = pssBuyOrderFeign.deleteBuyOrder(pssBuyOrder);
						if (success) {
							msg += MessageEnum.SUCCEED_OPERATION
									.getMessage("购货订单[" + pssBuyOrder.getBuyOrderNo() + "]删除") + "<br>";
						} else {
							msg += MessageEnum.FAILED_OPERATION
									.getMessage("购货订单[" + pssBuyOrder.getBuyOrderNo() + "]删除") + "<br>";
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
		dataMap.put("downloadTmplate", "PssBuyOrderAction_downloadTemplate.action");
		dataMap.put("serverUrl", "PssBuyOrderActionAjax_uploadExcelAjax.action");
		dataMap.put("downloadErrorUrl", "PssBuyOrderAction_downloadErrorExcel.action");

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

			String excelName = "购货订单导入模板";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			// 购货订单
			String type = "PssBuyOrder_import";
			HSSFWorkbook pssWorkBook = excelInterfaceFeign.getDownFileStream(type);
			// 输出流
			ServletOutputStream out = response.getOutputStream();
			pssWorkBook.write(out);
			response.flushBuffer();
			out.flush();
			out.close();

		} catch (Exception e) {
			//Log.error("PssBuyOrderAction_downloadTemplate方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
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
		String type = "PssBuyOrder_import";
		try {
			String result = pssBuyOrderFeign.uploadExcel(vch, type);
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
			dataMap.put("msg", "购货订单导入失败");
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
			String excelName = "购货订单导入校验文件";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			// 输出流
			out = response.getOutputStream();
			File file = new File(URLDecoder.decode(errorFileUrl, "UTF-8"));
			in = new FileInputStream(file);
			byte[] buffer = new byte[in.available()];
			in.read(buffer);
			in.close();
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
	public void downloadToExcel(Model model,String buyOrderIds) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		try {
			// HttpServletRequest request = request;
			//HttpServletResponse response = response;

			String excelName = "购货订单导出";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("buyOrderIds", buyOrderIds);
			paramMap.put("type", "PssBuyOrder_export");
			HSSFWorkbook pssWorkBook = pssBuyOrderFeign.downloadToExcel(paramMap);
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
