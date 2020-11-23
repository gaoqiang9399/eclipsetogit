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

import app.component.finance.util.CwPublicUtil;
import app.component.pss.conf.entity.PssConfig;
import app.component.pss.conf.feign.PssConfigFeign;
import app.component.pss.information.entity.PssGoods;
import app.component.pss.information.entity.PssStorehouse;
import app.component.pss.information.feign.PssBillnoConfFeign;
import app.component.pss.information.feign.PssGoodsFeign;
import app.component.pss.information.feign.PssStorehouseFeign;
import app.component.pss.stock.entity.PssCostAdjustBill;
import app.component.pss.stock.entity.PssCostAdjustDetailBill;
import app.component.pss.stock.feign.PssCostAdjustBillFeign;
import app.component.pss.utils.DealTableUtil;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssCostAdjustBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 17 16:47:36 CST 2017
 **/
@Controller
@RequestMapping("/pssCostAdjustBill")
public class PssCostAdjustBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssCostAdjustBillFeign pssCostAdjustBillFeign;
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
		/*
		 * PssStorehouse pssStorehouse = new PssStorehouse();
		 * pssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum()); JSONArray
		 * pssStorehouseJsonArray =
		 * pssStorehouseFeign.getPssStorehouseJSONArray(pssStorehouse);
		 * this.getHttpRequest().setAttribute("pssStorehouseJsonArray",
		 * pssStorehouseJsonArray);
		 */
		model.addAttribute("query", "");
		return "/component/pss/stock/PssCostAdjustBill_List";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCostAdjustBill pssCostAdjustBill = new PssCostAdjustBill();
		try {
			pssCostAdjustBill.setCustomQuery(ajaxData);
			pssCostAdjustBill.setCustomSorts(ajaxData);
			pssCostAdjustBill.setCriteriaList(pssCostAdjustBill, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssCostAdjustBillFeign.findByPage(ipage, pssCostAdjustBill);
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
			// Log.error("查询成本调整单表数据出错", e);
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
	@RequestMapping(value = "/getCostAdjustBillListAjax")
	@ResponseBody
	public Map<String, Object> getCostAdjustBillListAjax(String ajaxData, Integer pageNo, String tableId,
			String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssCostAdjustBillFeign.getCostAdjustBillList(ipage, formMap);
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			// 处理需要隐藏的列
			Map<String, String> map = new HashMap<String, String>();
			// map.put("printTimes", "printTimes");
			// map.put("memo", "memo");
			// map.put("goodsUnit", "goodsUnit");
			tableHtml = DealTableUtil.dealTabStr(tableHtml, map);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.error("查询成本调整单表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 新增成本调整单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-31 下午02:56:06
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String date = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("currDate", date);
		// dataMap.put("costAdjustNo", "CA"+WaterIdUtil.getWaterId());
		dataMap.put("costAdjustNo", pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.CBT.getValue(), "view"));
		FormData formpsscostadjustbill0002 = formService.getFormData("psscostadjustbill0002");

		// 商品选择组件
		JSONObject json = new JSONObject();
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

		model.addAttribute("formpsscostadjustbill0002", formpsscostadjustbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssCostAdjustBill_Input";
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
			FormData formpsscostadjustbill0002 = formService.getFormData("psscostadjustbill0002");
			getFormValue(formpsscostadjustbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscostadjustbill0002)) {
				PssCostAdjustBill pssCostAdjustBill = new PssCostAdjustBill();
				setObjValue(formpsscostadjustbill0002, pssCostAdjustBill);
				pssCostAdjustBillFeign.insert(pssCostAdjustBill);
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
		PssCostAdjustBill pssCostAdjustBill = new PssCostAdjustBill();
		try {
			FormData formpsscostadjustbill0002 = formService.getFormData("psscostadjustbill0002");
			getFormValue(formpsscostadjustbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscostadjustbill0002)) {
				pssCostAdjustBill = new PssCostAdjustBill();
				setObjValue(formpsscostadjustbill0002, pssCostAdjustBill);
				pssCostAdjustBillFeign.update(pssCostAdjustBill);
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
	 * 方法描述： 保存成本调整单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-31 上午11:56:06
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String costAdjustId, String costAdjustNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsscostadjustbill0002 = formService.getFormData("psscostadjustbill0002");
		PssCostAdjustBill pssCostAdjustBill = new PssCostAdjustBill();
		pssCostAdjustBill.setCostAdjustId(costAdjustId);
		pssCostAdjustBill.setCostAdjustNo(costAdjustNo);
		if (null != costAdjustId && !"".equals(costAdjustId)) {
			pssCostAdjustBill = pssCostAdjustBillFeign.getById(pssCostAdjustBill);
		} else if (null != costAdjustNo && !"".equals(costAdjustNo)) {
			pssCostAdjustBill = pssCostAdjustBillFeign.getByCABNo(pssCostAdjustBill);
		}else {
		}
		getObjValue(formpsscostadjustbill0002, pssCostAdjustBill);

		// 商品选择组件
		JSONObject json = new JSONObject();
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

		model.addAttribute("formpsscostadjustbill0002", formpsscostadjustbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssCostAdjustBill_Detail";
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String costAdjustId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsscostadjustbill0002 = formService.getFormData("psscostadjustbill0002");
		PssCostAdjustBill pssCostAdjustBill = new PssCostAdjustBill();
		pssCostAdjustBill.setCostAdjustId(costAdjustId);
		pssCostAdjustBill = pssCostAdjustBillFeign.getById(pssCostAdjustBill);
		getObjValue(formpsscostadjustbill0002, pssCostAdjustBill, formData);
		if (pssCostAdjustBill != null) {
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
	public Map<String, Object> deleteAjax(String costAdjustId) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCostAdjustBill pssCostAdjustBill = new PssCostAdjustBill();
		pssCostAdjustBill.setCostAdjustId(costAdjustId);
		try {
			pssCostAdjustBill = pssCostAdjustBillFeign.getById(pssCostAdjustBill);
			if (null == pssCostAdjustBill) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.NO_FILE.getMessage("该成本调整信息"));
			} else {
				pssCostAdjustBillFeign.delete(pssCostAdjustBill);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 保存成本调整单(insert)
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-31 上午11:56:06
	 */
	@RequestMapping(value = "/saveOrderAjax")
	@ResponseBody
	public Map<String, Object> saveOrderAjax(String ajaxData, String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpsscostadjustbill0002 = formService.getFormData("psscostadjustbill0002");
			getFormValue(formpsscostadjustbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscostadjustbill0002)) {
				PssCostAdjustBill pssCostAdjustBill = new PssCostAdjustBill();
				setObjValue(formpsscostadjustbill0002, pssCostAdjustBill);
				// 校验单据编号不能重复
				/*
				 * PssCostAdjustBill ATBTem =
				 * pssCostAdjustBillFeign.getByCABNo(pssCostAdjustBill); if(null
				 * != ATBTem){ dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; }
				 */
				List<PssCostAdjustDetailBill> tableList = PssPublicUtil.getMapByJsonObj(new PssCostAdjustDetailBill(),
						jsonArr);
				tableList = PssPublicUtil.filterSepList(tableList);
				if (null == tableList || tableList.size() == 0) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				Map<String, String> reMap = pssCostAdjustBillFeign.insertCABBet(pssCostAdjustBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("costAdjustNo", pssCostAdjustBill.getCostAdjustNo());
				dataMap.put("costAdjustId", pssCostAdjustBill.getCostAdjustId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			// Log.error("成本调整单保存出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 保存成本调整单(update)
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-31 下午4:56:06
	 */
	@RequestMapping(value = "/updateOrderAjax")
	@ResponseBody
	public Map<String, Object> updateOrderAjax(String ajaxData, String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpsscostadjustbill0002 = formService.getFormData("psscostadjustbill0002");
			getFormValue(formpsscostadjustbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscostadjustbill0002)) {
				PssCostAdjustBill pssCostAdjustBill = new PssCostAdjustBill();
				setObjValue(formpsscostadjustbill0002, pssCostAdjustBill);
				List<PssCostAdjustDetailBill> tableList = PssPublicUtil.getMapByJsonObj(new PssCostAdjustDetailBill(),
						jsonArr);
				tableList = PssPublicUtil.filterSepList(tableList);
				if (null == tableList || tableList.size() == 0) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				Map<String, String> reMap = pssCostAdjustBillFeign.updateCABBet(pssCostAdjustBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("flag", "success");
				dataMap.put("costAdjustId", pssCostAdjustBill.getCostAdjustId());
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			// Log.error("成本调整单保存出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 批量删除成本调整单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-18 下午01:56:06
	 */
	@RequestMapping(value = "/deleteCABBatchAjax")
	@ResponseBody
	public Map<String, Object> deleteCABBatchAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Map<String, String> reMap = pssCostAdjustBillFeign.deleteCABBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("成本调整单删除出错", e);
			throw e;
		}
		return dataMap;
	}
}
