package app.component.pss.stock.controller;

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
import app.component.finance.util.CwPublicUtil;
import app.component.nmd.entity.ParmDic;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.pss.conf.entity.PssConfig;
import app.component.pss.conf.feign.PssConfigFeign;
import app.component.pss.information.entity.PssGoods;
import app.component.pss.information.entity.PssStorehouse;
import app.component.pss.information.entity.PssSupplierInfo;
import app.component.pss.information.feign.PssBillnoConfFeign;
import app.component.pss.information.feign.PssGoodsFeign;
import app.component.pss.information.feign.PssStorehouseFeign;
import app.component.pss.information.feign.PssSupplierInfoFeign;
import app.component.pss.stock.entity.PssOtherStockInBill;
import app.component.pss.stock.entity.PssOtherStockInDetailBill;
import app.component.pss.stock.feign.PssOtherStockInBillFeign;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssOtherStockInBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Aug 14 11:03:39 CST 2017
 **/
@Controller
@RequestMapping("/pssOtherStockInBill")
public class PssOtherStockInBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssOtherStockInBillFeign pssOtherStockInBillFeign;
//	@Autowired
//	 private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private PssSupplierInfoFeign pssSupplierInfoFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
	@Autowired
	private PssGoodsFeign pssGoodsFeign;
	@Autowired
	private PssStorehouseFeign pssStorehouseFeign;
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
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		JSONArray pssAuditStsedJsonArray = new CodeUtils().getJSONArrayByKeyName("PSS_AUDIT_STSED");
		this.getHttpRequest().setAttribute("pssAuditStsedJsonArray", pssAuditStsedJsonArray);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssOtherStockInBill_List";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherStockInBill pssOtherStockInBill = new PssOtherStockInBill();
		try {
			pssOtherStockInBill.setCustomQuery(ajaxData);
			pssOtherStockInBill.setCustomSorts(ajaxData);
			pssOtherStockInBill.setCriteriaList(pssOtherStockInBill, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssOtherStockInBillFeign.findByPage(ipage, pssOtherStockInBill);
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List<?>) ipage.getResult(), ipage, true);
			// 处理需要隐藏的列
			/*
			 * Map<String, String> map = new HashMap<String, String>();
			 * map.put("printTimes", "printTimes"); map.put("memo", "memo");
			 * map.put("goodsUnit", "goodsUnit"); tableHtml =
			 * DealTableUtil.dealTabStr(tableHtml, map);
			 */
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.error("查询其他入库单表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 查询其他入库单列表数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-14 上午10:56:06
	 */
	@RequestMapping(value = "/getOtherStockInBillListAjax")
	@ResponseBody
	public Map<String, Object> getOtherStockInBillListAjax(String ajaxData, Integer pageNo, String tableId,
			String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssOtherStockInBillFeign.getOtherStockInBillList(ipage, formMap);
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List<?>) ipage.getResult(), ipage, true);
			// 处理需要隐藏的列
			/*
			 * Map<String, String> map = new HashMap<String, String>();
			 * map.put("printTimes", "printTimes"); map.put("memo", "memo");
			 * map.put("goodsUnit", "goodsUnit"); tableHtml =
			 * DealTableUtil.dealTabStr(tableHtml, map);
			 */
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.error("查询其他入库单表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 批量审核其他入库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-14 下午2:56:06
	 */
	@RequestMapping(value = "/checkOSIBBatchAjax")
	@ResponseBody
	public Map<String, Object> checkOSIBBatchAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
            formMap.put("regNo", User.getRegNo(request));
            formMap.put("regName", User.getRegName(request));
            formMap.put("orgNo", User.getOrgNo(request));
            formMap.put("orgName", User.getOrgName(request));
			Map<String, String> reMap = pssOtherStockInBillFeign.updateChkBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("其他入库单审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 批量反审核其他入库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-14 下午4:56:06
	 */
	@RequestMapping(value = "/reCheckOSIBBatchAjax")
	@ResponseBody
	public Map<String, Object> reCheckOSIBBatchAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Map<String, String> reMap = pssOtherStockInBillFeign.updateReChkBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("其他入库单反审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 新增其他入库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-18 下午01:56:06
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String date = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("currDate", date);
		// dataMap.put("otherStockInNo", "OSI"+WaterIdUtil.getWaterId());
		dataMap.put("otherStockInNo", pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.QTRK.getValue(), "view"));
		FormData formpssotherstockinbill0002 = formService.getFormData("pssotherstockinbill0002");

		JSONObject json = new JSONObject();
		// 供应商选择组件
		/*
		 * List<MfCusCustomer> cusList = mfCusCustomerFeign.getAllCus("");
		 * JSONArray cusArray = new JSONArray(); JSONObject cusObj = null; for
		 * (MfCusCustomer mcc:cusList) { JSONObject cusObj = new JSONObject();
		 * cusObj.put("id", mcc.getCusNo()); cusObj.put("name",
		 * mcc.getCusName()); cusArray.add(cusObj); } json.put("cus", cusArray);
		 */
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
		json.put("cus", pssSuppArray);

		// 业务类别
		/*
		 * ParmDic parmDic = new ParmDic();
		 * parmDic.setKeyName("PSS_OTHER_IN_TYPE");
		 * 
		 * @SuppressWarnings("unchecked") List<ParmDic> parmDicList =
		 * nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		 * dataMap.put("otherInTypes", new Gson().toJson(parmDicList));
		 */
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("PSS_OTHER_IN_TYPE");
		@SuppressWarnings("unchecked")
		List<ParmDic> parmDicList = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		JSONArray poiTypeArray = new JSONArray();
		JSONObject poiTypeObj = null;
		for (ParmDic pd : parmDicList) {
			if (PssEnumBean.YES_OR_NO.NO.getNum().equals(pd.getSts())) {
				continue;
			}
			poiTypeObj = new JSONObject();
			poiTypeObj.put("id", pd.getOptCode());
			poiTypeObj.put("name", pd.getOptName());
			poiTypeArray.add(poiTypeObj);
		}
		json.put("poiTypeArray", poiTypeArray);

		// 商品选择组件
		PssGoods pssGoods = new PssGoods();
		pssGoods.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssGoods> goodsList = pssGoodsFeign.getAll(pssGoods);
		JSONArray goodsArray = new JSONArray();
		JSONObject goodsObj = null;
		for (PssGoods pg : goodsList) {
			goodsObj = new JSONObject();
			goodsObj.put("id", pg.getGoodsId());
			goodsObj.put("name", pg.getGoodsName());
			goodsObj.put("storehouseId", pg.getStorehouseId());
			goodsArray.add(goodsObj);
		}
		json.put("goods", goodsArray);

		// 仓库
		/*
		 * PssStorehouse pssStorehouse = new PssStorehouse();
		 * pssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		 * List<PssStorehouse> storeList =
		 * pssStorehouseFeign.getAll(pssStorehouse); dataMap.put("storeIds", new
		 * Gson().toJson(storeList));
		 */

		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssStorehouse> storehouseList = pssStorehouseFeign.getAll(pssStorehouse);
		JSONArray storehouseArray = new JSONArray();
		JSONObject storehouseJSONObj = null;
		for (PssStorehouse ps : storehouseList) {
			storehouseJSONObj = new JSONObject();
			storehouseJSONObj.put("id", ps.getStorehouseId());
			storehouseJSONObj.put("name", ps.getStorehouseName());
			storehouseArray.add(storehouseJSONObj);
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

		model.addAttribute("formpssotherstockinbill0002", formpssotherstockinbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssOtherStockInBill_Input";
	}

	/**
	 * 方法描述： 保存其他入库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-21 上午11:56:06
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String otherStockInId, String otherStockInNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssotherstockinbill0002 = formService.getFormData("pssotherstockinbill0002");
		PssOtherStockInBill pssOtherStockInBill = new PssOtherStockInBill();
		pssOtherStockInBill.setOtherStockInId(otherStockInId);
		pssOtherStockInBill.setOtherStockInNo(otherStockInNo);
		if (null != otherStockInId && !"".equals(otherStockInId)) {
			pssOtherStockInBill = pssOtherStockInBillFeign.getById(pssOtherStockInBill);
		} else if (null != otherStockInNo && !"".equals(otherStockInNo)) {
			pssOtherStockInBill = pssOtherStockInBillFeign.getByOSIBNo(pssOtherStockInBill);
		}else {
		}
		getObjValue(formpssotherstockinbill0002, pssOtherStockInBill);

		// 审核状态
		dataMap.put("auditSts", pssOtherStockInBill.getAuditSts());

		JSONObject json = new JSONObject();
		// 供应商选择组件
		/*
		 * List<MfCusCustomer> cusList = mfCusCustomerFeign.getAllCus("");
		 * JSONArray cusArray = new JSONArray(); JSONObject cusObj = null; for
		 * (MfCusCustomer mcc:cusList) { JSONObject cusObj = new JSONObject();
		 * cusObj.put("id", mcc.getCusNo()); cusObj.put("name",
		 * mcc.getCusName()); cusArray.add(cusObj); } json.put("cus", cusArray);
		 */
		PssSupplierInfo pssSupplierInfo = new PssSupplierInfo();
		pssSupplierInfo.setEnabledStatus(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssSupplierInfo> pssSuppList = pssSupplierInfoFeign.getAll(pssSupplierInfo);
		JSONArray pssSuppArray = new JSONArray();
		JSONObject pssSuppObj = null;
		// 禁用处理
		String supplierId = pssOtherStockInBill.getSupplierId() != null ? pssOtherStockInBill.getSupplierId() : "";
		boolean suppExist = true;
		if ("".equals(supplierId)) {
			suppExist = false;
		}
		for (PssSupplierInfo psi : pssSuppList) {
			pssSuppObj = new JSONObject();
			// 判断供应商是否禁用
			if (suppExist && supplierId.equals(psi.getSupplierId())) {
				suppExist = false;
			}
			pssSuppObj.put("id", psi.getSupplierId());
			pssSuppObj.put("name", psi.getSupplierName());
			pssSuppArray.add(pssSuppObj);
		}
		if (suppExist) {
			pssSupplierInfo = new PssSupplierInfo();
			pssSupplierInfo.setSupplierId(supplierId);
			pssSupplierInfo = pssSupplierInfoFeign.getById(pssSupplierInfo);
			if (pssSupplierInfo != null) {
				pssSuppObj = new JSONObject();
				pssSuppObj.put("id", pssSupplierInfo.getSupplierId());
				pssSuppObj.put("name", pssSupplierInfo.getSupplierName());
				pssSuppArray.add(pssSuppObj);
			}
		}
		json.put("cus", pssSuppArray);

		// 业务类别
		/*
		 * ParmDic parmDic = new ParmDic();
		 * parmDic.setKeyName("PSS_OTHER_IN_TYPE");
		 * 
		 * @SuppressWarnings("unchecked") List<ParmDic> parmDicList =
		 * nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		 * dataMap.put("otherInTypes", new Gson().toJson(parmDicList));
		 */
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("PSS_OTHER_IN_TYPE");
		@SuppressWarnings("unchecked")
		List<ParmDic> parmDicList = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		JSONArray poiTypeArray = new JSONArray();
		JSONObject poiTypeObj = null;
		for (ParmDic pd : parmDicList) {
			/*
			 * if(PssEnumBean.YES_OR_NO.NO.getNum().equals(pd.getSts())){
			 * continue; }
			 */
			poiTypeObj = new JSONObject();
			poiTypeObj.put("id", pd.getOptCode());
			poiTypeObj.put("name", pd.getOptName());
			poiTypeArray.add(poiTypeObj);
		}
		json.put("poiTypeArray", poiTypeArray);

		// 商品选择组件
		PssGoods pssGoods = new PssGoods();
		pssGoods.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssGoods> goodsList = pssGoodsFeign.getAll(pssGoods);
		JSONArray goodsArray = new JSONArray();
		JSONObject goodsObj = null;
		for (PssGoods pg : goodsList) {
			goodsObj = new JSONObject();
			goodsObj.put("id", pg.getGoodsId());
			goodsObj.put("name", pg.getGoodsName());
			goodsObj.put("storehouseId", pg.getStorehouseId());
			goodsArray.add(goodsObj);
		}
		json.put("goods", goodsArray);

		Map<String, PssGoods> disableGoodsMap = new HashMap<String, PssGoods>();
		pssGoods.setFlag(PssEnumBean.YES_OR_NO.NO.getNum());
		List<PssGoods> disableGoodsList = pssGoodsFeign.getAll(pssGoods);
		for (PssGoods pg : disableGoodsList) {
			disableGoodsMap.put(pg.getGoodsId(), pg);
		}
		dataMap.put("disableGoodsMap", JSONObject.fromObject(disableGoodsMap));

		// 仓库
		/*
		 * PssStorehouse pssStorehouse = new PssStorehouse();
		 * pssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		 * List<PssStorehouse> storeList =
		 * pssStorehouseFeign.getAll(pssStorehouse); dataMap.put("storeIds", new
		 * Gson().toJson(storeList));
		 */

		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssStorehouse> storehouseList = pssStorehouseFeign.getAll(pssStorehouse);
		JSONArray storehouseArray = new JSONArray();
		JSONObject storehouseJSONObj = null;
		for (PssStorehouse ps : storehouseList) {
			storehouseJSONObj = new JSONObject();
			storehouseJSONObj.put("id", ps.getStorehouseId());
			storehouseJSONObj.put("name", ps.getStorehouseName());
			storehouseArray.add(storehouseJSONObj);
		}
		json.put("storehouse", storehouseArray);

		Map<String, PssStorehouse> disableStorehouseMap = new HashMap<String, PssStorehouse>();
		pssStorehouse.setFlag(PssEnumBean.YES_OR_NO.NO.getNum());
		List<PssStorehouse> disableStorehouseList = pssStorehouseFeign.getAll(pssStorehouse);
		for (PssStorehouse ps : disableStorehouseList) {
			disableStorehouseMap.put(ps.getStorehouseId(), ps);
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

		model.addAttribute("formpssotherstockinbill0002", formpssotherstockinbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssOtherStockInBill_Detail";
	}

	/**
	 * 方法描述： 批量删除其他入库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-25 下午1:56:06
	 */
	@RequestMapping(value = "/deleteOSIBBatchAjax")
	@ResponseBody
	public Map<String, Object> deleteOSIBBatchAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Map<String, String> reMap = pssOtherStockInBillFeign.deleteOSIBBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("其他入库单删除出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherstockinbill0002 = formService.getFormData("pssotherstockinbill0002");
			getFormValue(formpssotherstockinbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockinbill0002)) {
				PssOtherStockInBill pssOtherStockInBill = new PssOtherStockInBill();
				setObjValue(formpssotherstockinbill0002, pssOtherStockInBill);
				pssOtherStockInBillFeign.insert(pssOtherStockInBill);
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherStockInBill pssOtherStockInBill = new PssOtherStockInBill();
		try {
			FormData formpssotherstockinbill0002 = formService.getFormData("pssotherstockinbill0002");
			getFormValue(formpssotherstockinbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockinbill0002)) {
				pssOtherStockInBill = new PssOtherStockInBill();
				setObjValue(formpssotherstockinbill0002, pssOtherStockInBill);
				pssOtherStockInBillFeign.update(pssOtherStockInBill);
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
	public Map<String, Object> getByIdAjax(String otherStockInId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssotherstockinbill0002 = formService.getFormData("pssotherstockinbill0002");
		PssOtherStockInBill pssOtherStockInBill = new PssOtherStockInBill();
		pssOtherStockInBill.setOtherStockInId(otherStockInId);
		pssOtherStockInBill = pssOtherStockInBillFeign.getById(pssOtherStockInBill);
		getObjValue(formpssotherstockinbill0002, pssOtherStockInBill, formData);
		if (pssOtherStockInBill != null) {
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
	public Map<String, Object> deleteAjax(String otherStockInId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherStockInBill pssOtherStockInBill = new PssOtherStockInBill();
		pssOtherStockInBill.setOtherStockInId(otherStockInId);
		try {
			pssOtherStockInBill = pssOtherStockInBillFeign.getById(pssOtherStockInBill);
			if (null == pssOtherStockInBill) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage() + "其他入库单信息不存在");
				return dataMap;
			}
			pssOtherStockInBillFeign.delete(pssOtherStockInBill);
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
	 * 方法描述： 保存其他入库单(insert)
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-21 上午11:56:06
	 */
	@RequestMapping(value = "/saveOrderAjax")
	@ResponseBody
	public Map<String, Object> saveOrderAjax(String ajaxData, String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherstockinbill0002 = formService.getFormData("pssotherstockinbill0002");
			getFormValue(formpssotherstockinbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockinbill0002)) {
				PssOtherStockInBill pssOtherStockInBill = new PssOtherStockInBill();
				setObjValue(formpssotherstockinbill0002, pssOtherStockInBill);
				// 校验单据编号不能重复
				/*
				 * PssOtherStockInBill OSIBTem =
				 * pssOtherStockInBillFeign.getByOSIBNo(pssOtherStockInBill);
				 * if(null != OSIBTem){ dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; }
				 */
				List<PssOtherStockInDetailBill> tableList = PssPublicUtil
						.getMapByJsonObj(new PssOtherStockInDetailBill(), jsonArr);
				// 分录信息校验
				tableList = PssPublicUtil.filterSepList(tableList);
				if (null == tableList || tableList.isEmpty()) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				// pssOtherStockInBillFeign.insertOSIBBet(pssOtherStockInBill,
				// tableList);
				Map<String, String> reMap = pssOtherStockInBillFeign.insertOSIBBet(pssOtherStockInBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("otherStockInNo", pssOtherStockInBill.getOtherStockInNo());
				dataMap.put("otherStockInId", pssOtherStockInBill.getOtherStockInId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			// Log.error("保存其他入库单出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 审核其他入库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-06 下午1:56:06
	 */
	@RequestMapping(value = "/auditOrderAjax")
	@ResponseBody
	public Map<String, Object> auditOrderAjax(String ajaxData, String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherstockinbill0002 = formService.getFormData("pssotherstockinbill0002");
			getFormValue(formpssotherstockinbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockinbill0002)) {
				PssOtherStockInBill pssOtherStockInBill = new PssOtherStockInBill();
				setObjValue(formpssotherstockinbill0002, pssOtherStockInBill);
				// 校验单据编号不能重复
				/*
				 * if(null != pssOtherStockInBill.getOtherStockInId() &&
				 * !"".equals(pssOtherStockInBill.getOtherStockInId())){
				 * PssOtherStockInBill posib =
				 * pssOtherStockInBillFeign.getById(pssOtherStockInBill);
				 * if(null == posib){ PssOtherStockInBill OSIBTem =
				 * pssOtherStockInBillFeign.getByOSIBNo(pssOtherStockInBill);
				 * if(null != OSIBTem){ dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; } } }else{ PssOtherStockInBill OSIBTem =
				 * pssOtherStockInBillFeign.getByOSIBNo(pssOtherStockInBill);
				 * if(null != OSIBTem){ dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; } }
				 */

				List<PssOtherStockInDetailBill> tableList = PssPublicUtil
						.getMapByJsonObj(new PssOtherStockInDetailBill(), jsonArr);
				// 分录信息校验
				tableList = PssPublicUtil.filterSepList(tableList);
				if (null == tableList || tableList.isEmpty()) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}

				Map<String, String> reMap = pssOtherStockInBillFeign.updateChk(pssOtherStockInBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("otherStockInNo", pssOtherStockInBill.getOtherStockInNo());
				dataMap.put("otherStockInId", pssOtherStockInBill.getOtherStockInId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("审核其他入库单出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 反审核其他入库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-07上午08:56:06
	 */
	@RequestMapping(value = "/reAuditOrderAjax")
	@ResponseBody
	public Map<String, Object> reAuditOrderAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherstockinbill0002 = formService.getFormData("pssotherstockinbill0002");
			getFormValue(formpssotherstockinbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockinbill0002)) {
				PssOtherStockInBill pssOtherStockInBill = new PssOtherStockInBill();
				setObjValue(formpssotherstockinbill0002, pssOtherStockInBill);
				Map<String, String> reMap = pssOtherStockInBillFeign.updateChk(pssOtherStockInBill);
				dataMap.put("data", reMap);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("审核其他入库单出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 保存其他入库单(update)
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-29 上午11:56:06
	 */
	@RequestMapping(value = "/updateOrderAjax")
	@ResponseBody
	public Map<String, Object> updateOrderAjax(String ajaxData, String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherstockinbill0002 = formService.getFormData("pssotherstockinbill0002");
			getFormValue(formpssotherstockinbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockinbill0002)) {
				PssOtherStockInBill pssOtherStockInBill = new PssOtherStockInBill();
				setObjValue(formpssotherstockinbill0002, pssOtherStockInBill);
				List<PssOtherStockInDetailBill> tableList = PssPublicUtil
						.getMapByJsonObj(new PssOtherStockInDetailBill(), jsonArr);
				// 分录信息校验
				tableList = PssPublicUtil.filterSepList(tableList);
				if (null == tableList || tableList.isEmpty()) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				Map<String, String> reMap = pssOtherStockInBillFeign.updateOSIBBet(pssOtherStockInBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("flag", "success");
				dataMap.put("otherStockInId", pssOtherStockInBill.getOtherStockInId());
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			// Log.error("其他入库单保存出错", e);
			throw e;
		}
		return dataMap;
	}

}
