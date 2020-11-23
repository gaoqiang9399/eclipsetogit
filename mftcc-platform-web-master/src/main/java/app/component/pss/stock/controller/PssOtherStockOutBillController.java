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
import app.component.pss.information.entity.PssCustomerInfo;
import app.component.pss.information.entity.PssGoods;
import app.component.pss.information.entity.PssStorehouse;
import app.component.pss.information.feign.PssBillnoConfFeign;
import app.component.pss.information.feign.PssCustomerInfoFeign;
import app.component.pss.information.feign.PssGoodsFeign;
import app.component.pss.information.feign.PssStorehouseFeign;
import app.component.pss.stock.entity.PssOtherStockOutBill;
import app.component.pss.stock.entity.PssOtherStockOutDetailBill;
import app.component.pss.stock.feign.PssOtherStockOutBillFeign;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssOtherStockOutBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Aug 14 11:47:07 CST 2017
 **/
@Controller
@RequestMapping("/pssOtherStockOutBill")
public class PssOtherStockOutBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssOtherStockOutBillFeign pssOtherStockOutBillFeign;

	// private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private PssCustomerInfoFeign pssCustomerInfoFeign;
	@Autowired
	private PssGoodsFeign pssGoodsFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
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

		ActionContext.initialize(request, response);
		JSONArray pssAuditStsedJsonArray = new CodeUtils().getJSONArrayByKeyName("PSS_AUDIT_STSED");
		this.getHttpRequest().setAttribute("pssAuditStsedJsonArray", pssAuditStsedJsonArray);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssOtherStockOutBill_List";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherStockOutBill pssOtherStockOutBill = new PssOtherStockOutBill();
		try {
			pssOtherStockOutBill.setCustomQuery(ajaxData);
			pssOtherStockOutBill.setCustomSorts(ajaxData);
			pssOtherStockOutBill.setCriteriaList(pssOtherStockOutBill, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssOtherStockOutBillFeign.findByPage(ipage, pssOtherStockOutBill);
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
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
			// Log.error("查询其他出库单表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 查询其他出库单列表数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-14 上午11:56:06
	 */
	@RequestMapping(value = "/getOtherStockOutBillListAjax")
	@ResponseBody
	public Map<String, Object> getOtherStockOutBillListAjax(String ajaxData, Integer pageNo, String tableId,
			String tableType) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssOtherStockOutBillFeign.getOtherStockOutBillList(ipage, formMap);
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
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
			// Log.error("查询其他出库单表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 新增其他出库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-28 上午09:56:06
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String date = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("currDate", date);
		// dataMap.put("otherStockOutNo", "OSO"+WaterIdUtil.getWaterId());
		dataMap.put("otherStockOutNo", pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.QTCK.getValue(), "view"));
		FormData formpssotherstockoutbill0002 = formService.getFormData("pssotherstockoutbill0002");

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

		// 业务类别
		/*
		 * ParmDic parmDic = new ParmDic();
		 * parmDic.setKeyName("PSS_OTHER_OUT_TYPE");
		 * 
		 * @SuppressWarnings("unchecked") List<ParmDic> parmDicList =
		 * nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		 * dataMap.put("otherOutTypes", new Gson().toJson(parmDicList));
		 */
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("PSS_OTHER_OUT_TYPE");
		@SuppressWarnings("unchecked")
		List<ParmDic> parmDicList = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		JSONArray pooTypeArray = new JSONArray();
		JSONObject pooTypeObj = null;
		for (ParmDic pd : parmDicList) {
			if (PssEnumBean.YES_OR_NO.NO.getNum().equals(pd.getSts())) {
				continue;
			}
			pooTypeObj = new JSONObject();
			pooTypeObj.put("id", pd.getOptCode());
			pooTypeObj.put("name", pd.getOptName());
			pooTypeArray.add(pooTypeObj);
		}
		json.put("pooTypeArray", pooTypeArray);

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

		model.addAttribute("formpssotherstockoutbill0002", formpssotherstockoutbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssOtherStockOutBill_Input";
	}

	/**
	 * 方法描述： 保存其他出库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-21 上午11:56:06
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String otherStockOutId, String otherStockOutNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssotherstockoutbill0002 = formService.getFormData("pssotherstockoutbill0002");
		PssOtherStockOutBill pssOtherStockOutBill = new PssOtherStockOutBill();
		pssOtherStockOutBill.setOtherStockOutId(otherStockOutId);
		pssOtherStockOutBill.setOtherStockOutNo(otherStockOutNo);
		if (null != otherStockOutId && !"".equals(otherStockOutId)) {
			pssOtherStockOutBill = pssOtherStockOutBillFeign.getById(pssOtherStockOutBill);
		} else if (null != otherStockOutNo && !"".equals(otherStockOutNo)) {
			pssOtherStockOutBill = pssOtherStockOutBillFeign.getByOSOBNo(pssOtherStockOutBill);
		}else {
		}
		getObjValue(formpssotherstockoutbill0002, pssOtherStockOutBill);

		// 审核状态
		dataMap.put("auditSts", pssOtherStockOutBill.getAuditSts());

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
		// 禁用处理
		String cusNo = pssOtherStockOutBill.getCusNo() != null ? pssOtherStockOutBill.getCusNo() : "";
		boolean cusExist = true;
		if ("".equals(cusNo)) {
			cusExist = false;
		}
		for (PssCustomerInfo pci : pssCustList) {
			pssCustObj = new JSONObject();
			// 判断客户是否禁用
			if (cusExist && cusNo.equals(pci.getCusNo())) {
				cusExist = false;
			}
			pssCustObj.put("id", pci.getCusNo());
			pssCustObj.put("name", pci.getCusName());
			pssCustArray.add(pssCustObj);
		}
		if (cusExist) {
			pssCustomerInfo = new PssCustomerInfo();
			pssCustomerInfo.setCusNo(cusNo);
			pssCustomerInfo = pssCustomerInfoFeign.getById(pssCustomerInfo);
			if (pssCustomerInfo != null) {
				pssCustObj = new JSONObject();
				pssCustObj.put("id", pssCustomerInfo.getCusNo());
				pssCustObj.put("name", pssCustomerInfo.getCusName());
				pssCustArray.add(pssCustObj);
			}
		}
		json.put("cus", pssCustArray);

		// 业务类别
		/*
		 * ParmDic parmDic = new ParmDic();
		 * parmDic.setKeyName("PSS_OTHER_OUT_TYPE");
		 * 
		 * @SuppressWarnings("unchecked") List<ParmDic> parmDicList =
		 * nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		 * dataMap.put("otherOutTypes", new Gson().toJson(parmDicList));
		 */
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("PSS_OTHER_OUT_TYPE");
		@SuppressWarnings("unchecked")
		List<ParmDic> parmDicList = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		JSONArray pooTypeArray = new JSONArray();
		JSONObject pooTypeObj = null;
		for (ParmDic pd : parmDicList) {
			/*
			 * if(PssEnumBean.YES_OR_NO.NO.getNum().equals(pd.getSts())){
			 * continue; }
			 */
			pooTypeObj = new JSONObject();
			pooTypeObj.put("id", pd.getOptCode());
			pooTypeObj.put("name", pd.getOptName());
			pooTypeArray.add(pooTypeObj);
		}
		json.put("pooTypeArray", pooTypeArray);

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

		model.addAttribute("formpssotherstockoutbill0002", formpssotherstockoutbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssOtherStockOutBill_Detail";
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
			FormData formpssotherstockoutbill0002 = formService.getFormData("pssotherstockoutbill0002");
			getFormValue(formpssotherstockoutbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockoutbill0002)) {
				PssOtherStockOutBill pssOtherStockOutBill = new PssOtherStockOutBill();
				setObjValue(formpssotherstockoutbill0002, pssOtherStockOutBill);
				pssOtherStockOutBillFeign.insert(pssOtherStockOutBill);
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
		PssOtherStockOutBill pssOtherStockOutBill = new PssOtherStockOutBill();
		try {
			FormData formpssotherstockoutbill0002 = formService.getFormData("pssotherstockoutbill0002");
			getFormValue(formpssotherstockoutbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockoutbill0002)) {
				pssOtherStockOutBill = new PssOtherStockOutBill();
				setObjValue(formpssotherstockoutbill0002, pssOtherStockOutBill);
				pssOtherStockOutBillFeign.update(pssOtherStockOutBill);
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
	public Map<String, Object> getByIdAjax(String otherStockOutId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssotherstockoutbill0002 = formService.getFormData("pssotherstockoutbill0002");
		PssOtherStockOutBill pssOtherStockOutBill = new PssOtherStockOutBill();
		pssOtherStockOutBill.setOtherStockOutId(otherStockOutId);
		pssOtherStockOutBill = pssOtherStockOutBillFeign.getById(pssOtherStockOutBill);
		getObjValue(formpssotherstockoutbill0002, pssOtherStockOutBill, formData);
		if (pssOtherStockOutBill != null) {
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
	public Map<String, Object> deleteAjax(String otherStockOutId) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherStockOutBill pssOtherStockOutBill = new PssOtherStockOutBill();
		pssOtherStockOutBill.setOtherStockOutId(otherStockOutId);
		try {
			pssOtherStockOutBill = pssOtherStockOutBillFeign.getById(pssOtherStockOutBill);
			if (null == pssOtherStockOutBill) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage() + "其他出库单信息不存在");
				return dataMap;
			}
			pssOtherStockOutBillFeign.delete(pssOtherStockOutBill);
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
	 * 方法描述： 保存其他出库单(insert)
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-28 上午11:26:06
	 */
	@RequestMapping(value = "/saveOrderAjax")
	@ResponseBody
	public Map<String, Object> saveOrderAjax(String ajaxData, String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherstockoutbill0002 = formService.getFormData("pssotherstockoutbill0002");
			getFormValue(formpssotherstockoutbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockoutbill0002)) {
				PssOtherStockOutBill pssOtherStockOutBill = new PssOtherStockOutBill();
				setObjValue(formpssotherstockoutbill0002, pssOtherStockOutBill);
				// 校验单据编号不能重复
				/*
				 * PssOtherStockOutBill OSOBTem =
				 * pssOtherStockOutBillFeign.getByOSOBNo(pssOtherStockOutBill);
				 * if(null != OSOBTem){ dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; }
				 */
				List<PssOtherStockOutDetailBill> tableList = PssPublicUtil
						.getMapByJsonObj(new PssOtherStockOutDetailBill(), jsonArr);
				// 分录信息校验
				tableList = PssPublicUtil.filterSepList(tableList);
				if (null == tableList || tableList.isEmpty()) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				Map<String, String> reMap = pssOtherStockOutBillFeign.insertOSOBBet(pssOtherStockOutBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("otherStockOutNo", pssOtherStockOutBill.getOtherStockOutNo());
				dataMap.put("otherStockOutId", pssOtherStockOutBill.getOtherStockOutId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			// Log.error("其他出库单保存出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 批量删除其他出库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-15 上午10:56:06
	 */
	@RequestMapping(value = "/deleteOSOBBatchAjax")
	@ResponseBody
	public Map<String, Object> deleteOSOBBatchAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Map<String, String> reMap = pssOtherStockOutBillFeign.deleteOSOBBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("其他出库单删除出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 批量审核其他出库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-15 上午10:56:06
	 */
	@RequestMapping(value = "/checkOSOBBatchAjax")
	@ResponseBody
	public Map<String, Object> checkOSOBBatchAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
            formMap.put("regNo", User.getRegNo(request));
            formMap.put("regName", User.getRegName(request));
            formMap.put("orgNo", User.getOrgNo(request));
            formMap.put("orgName", User.getOrgName(request));
			Map<String, String> reMap = pssOtherStockOutBillFeign.updateChkBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("其他出库单审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 批量反审核其他出库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-15 上午10:56:06
	 */
	@RequestMapping(value = "/reCheckOSOBBatchAjax")
	@ResponseBody
	public Map<String, Object> reCheckOSOBBatchAjax(String ajaxData) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Map<String, String> reMap = pssOtherStockOutBillFeign.updateReChkBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("其他出库单反审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 审核其他出库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-07 下午04:26:06
	 */
	@RequestMapping(value = "/auditOrderAjax")
	@ResponseBody
	public Map<String, Object> auditOrderAjax(String ajaxData, String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherstockoutbill0002 = formService.getFormData("pssotherstockoutbill0002");
			getFormValue(formpssotherstockoutbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockoutbill0002)) {
				PssOtherStockOutBill pssOtherStockOutBill = new PssOtherStockOutBill();
				setObjValue(formpssotherstockoutbill0002, pssOtherStockOutBill);
				// 校验单据编号不能重复
				/*
				 * if(null != pssOtherStockOutBill.getOtherStockOutId() &&
				 * !"".equals(pssOtherStockOutBill.getOtherStockOutId())){
				 * PssOtherStockOutBill posib =
				 * pssOtherStockOutBillFeign.getById(pssOtherStockOutBill);
				 * if(null == posib){ PssOtherStockOutBill OSOBTem =
				 * pssOtherStockOutBillFeign.getByOSOBNo(pssOtherStockOutBill);
				 * if(null != OSOBTem){ dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; } } }else{ PssOtherStockOutBill OSOBTem =
				 * pssOtherStockOutBillFeign.getByOSOBNo(pssOtherStockOutBill);
				 * if(null != OSOBTem){ dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; } }
				 */

				List<PssOtherStockOutDetailBill> tableList = PssPublicUtil
						.getMapByJsonObj(new PssOtherStockOutDetailBill(), jsonArr);
				// 分录信息校验
				tableList = PssPublicUtil.filterSepList(tableList);
				if (null == tableList || tableList.isEmpty()) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				Map<String, String> reMap = pssOtherStockOutBillFeign.updateChk(pssOtherStockOutBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("otherStockOutNo", pssOtherStockOutBill.getOtherStockOutNo());
				dataMap.put("otherStockOutId", pssOtherStockOutBill.getOtherStockOutId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			// Log.error("其他出库单审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 反审核其他出库单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-07 下午05:26:06
	 */
	@RequestMapping(value = "/reAuditOrderAjax")
	@ResponseBody
	public Map<String, Object> reAuditOrderAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherstockoutbill0002 = formService.getFormData("pssotherstockoutbill0002");
			getFormValue(formpssotherstockoutbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockoutbill0002)) {
				PssOtherStockOutBill pssOtherStockOutBill = new PssOtherStockOutBill();
				setObjValue(formpssotherstockoutbill0002, pssOtherStockOutBill);

				Map<String, String> reMap = pssOtherStockOutBillFeign.updateChk(pssOtherStockOutBill);
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
			// Log.error("其他出库单反审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 保存其他出库单(update)
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-29 下午13:56:06
	 */
	@RequestMapping(value = "/updateOrderAjax")
	@ResponseBody
	public Map<String, Object> updateOrderAjax(String ajaxData, String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherstockoutbill0002 = formService.getFormData("pssotherstockoutbill0002");
			getFormValue(formpssotherstockoutbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockoutbill0002)) {
				PssOtherStockOutBill pssOtherStockOutBill = new PssOtherStockOutBill();
				setObjValue(formpssotherstockoutbill0002, pssOtherStockOutBill);
				List<PssOtherStockOutDetailBill> tableList = PssPublicUtil
						.getMapByJsonObj(new PssOtherStockOutDetailBill(), jsonArr);
				// 分录信息校验
				tableList = PssPublicUtil.filterSepList(tableList);
				if (null == tableList || tableList.isEmpty()) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				Map<String, String> reMap = pssOtherStockOutBillFeign.updateOSOBBet(pssOtherStockOutBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("flag", "success");
				dataMap.put("otherStockOutId", pssOtherStockOutBill.getOtherStockOutId());
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			// Log.error("其他出库单保存出错", e);
			throw e;
		}
		return dataMap;
	}

}
