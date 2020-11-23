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
import app.component.pss.information.entity.PssStorehouse;
import app.component.pss.information.feign.PssStorehouseFeign;
import app.component.pss.stock.entity.PssCheckStockBill;
import app.component.pss.stock.entity.PssCheckStockDetailBill;
import app.component.pss.stock.feign.PssCheckStockBillFeign;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssCheckStockBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 17:11:31 CST 2017
 **/
@Controller
@RequestMapping("/pssCheckStockBill")
public class PssCheckStockBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssCheckStockBillFeign pssCheckStockBillFeign;
	@Autowired
	private PssStorehouseFeign pssStorehouseFeign;
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
		// 仓库
		/*
		 * PssStorehouse pssStorehouse = new PssStorehouse();
		 * pssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		 * List<PssStorehouse> storeList =
		 * pssStorehouseFeign.getAll(pssStorehouse); PssStorehouse
		 * pssStorehouseAll = new PssStorehouse();
		 * pssStorehouseAll.setStorehouseId("");
		 * pssStorehouseAll.setStorehouseName("所有仓库"); storeList.add(0,
		 * pssStorehouseAll); dataMap.put("storeIds", new
		 * Gson().toJson(storeList));
		 */

		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		JSONArray pssStorehouseJsonArray = pssStorehouseFeign.getPssStorehouseJSONArray(pssStorehouse);
		/*
		 * JSONObject json = new JSONObject(); json.put("optName", "所有仓库");
		 * json.put("optCode", ""); pssStorehouseJsonArray.add(json);
		 */
		this.getHttpRequest().setAttribute("pssStorehouseJsonArray", pssStorehouseJsonArray);

		model.addAttribute("query", "");
		return "/component/pss/stock/PssCheckStockBill_List";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCheckStockBill pssCheckStockBill = new PssCheckStockBill();
		try {
			pssCheckStockBill.setCustomQuery(ajaxData);
			pssCheckStockBill.setCustomSorts(ajaxData);
			pssCheckStockBill.setCriteriaList(pssCheckStockBill, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssCheckStockBillFeign.findByPage(ipage, pssCheckStockBill);
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
			// Log.error("查询盘点列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 查询盘点列表数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-14 上午9:56:06
	 */
	@RequestMapping(value = "/getCheckStockBillListAjax")
	@ResponseBody
	public Map<String, Object> getCheckStockBillListAjax(String ajaxData, Integer pageNo, String tableId,
			String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssCheckStockBillFeign.getCheckStockBillList(ipage, formMap);
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
			// Log.error("查询盘点列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 新增盘点
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-29 下午4:56:06
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String date = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("currDate", date);
		// dataMap.put("otherStockInNo", "OSI"+WaterIdUtil.getWaterId());
		FormData formpsscheckstockbill0002 = formService.getFormData("psscheckstockbill0002");

		JSONObject json = new JSONObject();
		// 仓库
		/*
		 * PssStorehouse pssStorehouse = new PssStorehouse();
		 * pssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		 * List<PssStorehouse> storeList =
		 * pssStorehouseFeign.getAll(pssStorehouse); PssStorehouse
		 * pssStorehouseAll = new PssStorehouse();
		 * pssStorehouseAll.setStorehouseId("");
		 * pssStorehouseAll.setStorehouseName("所有仓库"); storeList.add(0,
		 * pssStorehouseAll); dataMap.put("storeIds", new
		 * Gson().toJson(storeList));
		 */
		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssStorehouse> storehouseList = pssStorehouseFeign.getAll(pssStorehouse);
		JSONArray storehouseArray = new JSONArray();
		JSONObject storehouseJSONObj = null;
		/*
		 * storehouseJSONObj = new JSONObject(); storehouseJSONObj.put("id",
		 * ""); storehouseJSONObj.put("name", "所有仓库");
		 * storehouseArray.add(storehouseJSONObj);
		 */
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

		model.addAttribute("formpsscheckstockbill0002", formpsscheckstockbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssCheckStockBill_Input";
	}

	/**
	 * 方法描述： 查看盘点
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-31 上午9:56:06
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String checkStockId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String date = DateUtil.getDate("yyyy-MM-dd");
		dataMap.put("currDate", date);

		PssCheckStockBill pssCheckStockBill = new PssCheckStockBill();
		pssCheckStockBill.setCheckStockId(checkStockId);
		pssCheckStockBill = pssCheckStockBillFeign.getById(pssCheckStockBill);

		FormData formpsscheckstockbill0002 = formService.getFormData("psscheckstockbill0002");
		getObjValue(formpsscheckstockbill0002, pssCheckStockBill);

		JSONObject json = new JSONObject();
		// 仓库
		/*
		 * PssStorehouse pssStorehouse = new PssStorehouse();
		 * pssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		 * List<PssStorehouse> storeList =
		 * pssStorehouseFeign.getAll(pssStorehouse); PssStorehouse
		 * pssStorehouseAll = new PssStorehouse();
		 * pssStorehouseAll.setStorehouseId("");
		 * pssStorehouseAll.setStorehouseName("所有仓库"); storeList.add(0,
		 * pssStorehouseAll); dataMap.put("storeIds", new
		 * Gson().toJson(storeList));
		 */
		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssStorehouse> storehouseList = pssStorehouseFeign.getAll(pssStorehouse);
		JSONArray storehouseArray = new JSONArray();
		JSONObject storehouseJSONObj = null;
		/*
		 * storehouseJSONObj = new JSONObject(); storehouseJSONObj.put("id",
		 * ""); storehouseJSONObj.put("name", "所有仓库");
		 * storehouseArray.add(storehouseJSONObj);
		 */
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

		model.addAttribute("formpsscheckstockbill0002", formpsscheckstockbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssCheckStockBill_Detail";
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
			FormData formpsscheckstockbill0002 = formService.getFormData("psscheckstockbill0002");
			getFormValue(formpsscheckstockbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscheckstockbill0002)) {
				PssCheckStockBill pssCheckStockBill = new PssCheckStockBill();
				setObjValue(formpsscheckstockbill0002, pssCheckStockBill);
				pssCheckStockBillFeign.insert(pssCheckStockBill);
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
		PssCheckStockBill pssCheckStockBill = new PssCheckStockBill();
		try {
			FormData formpsscheckstockbill0002 = formService.getFormData("psscheckstockbill0002");
			getFormValue(formpsscheckstockbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscheckstockbill0002)) {
				pssCheckStockBill = new PssCheckStockBill();
				setObjValue(formpsscheckstockbill0002, pssCheckStockBill);
				pssCheckStockBillFeign.update(pssCheckStockBill);
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
	public Map<String, Object> getByIdAjax(String checkStockId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsscheckstockbill0002 = formService.getFormData("psscheckstockbill0002");
		PssCheckStockBill pssCheckStockBill = new PssCheckStockBill();
		pssCheckStockBill.setCheckStockId(checkStockId);
		pssCheckStockBill = pssCheckStockBillFeign.getById(pssCheckStockBill);
		getObjValue(formpsscheckstockbill0002, pssCheckStockBill, formData);
		if (pssCheckStockBill != null) {
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
	public Map<String, Object> deleteAjax(String checkStockId) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCheckStockBill pssCheckStockBill = new PssCheckStockBill();
		pssCheckStockBill.setCheckStockId(checkStockId);
		try {
			pssCheckStockBill = pssCheckStockBillFeign.getById(pssCheckStockBill);
			if (null == pssCheckStockBill) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage() + "盘点信息不存在");
				return dataMap;
			} else {
				String inNo = pssCheckStockBill.getOtherStockInNo();
				String outNo = pssCheckStockBill.getOtherStockOutNo();
				if ((null != inNo && !"".equals(inNo)) || null != outNo && !"".equals(outNo)) {
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage() + "已生成盘点单据的盘点记录不能再被删除");
					return dataMap;
				}
			}
			pssCheckStockBillFeign.delete(pssCheckStockBill);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
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
	 * 方法描述： 保存\修改盘点(insert)
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-30 下午1:56:06
	 */
	@RequestMapping(value = "/saveOrderAjax")
	@ResponseBody
	public Map<String, Object> saveOrderAjax(String ajaxData, String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpsscheckstockbill0002 = formService.getFormData("psscheckstockbill0002");
			getFormValue(formpsscheckstockbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscheckstockbill0002)) {
				PssCheckStockBill pssCheckStockBill = new PssCheckStockBill();
				setObjValue(formpsscheckstockbill0002, pssCheckStockBill);
				List<PssCheckStockDetailBill> tableList = PssPublicUtil.getMapByJsonObj(new PssCheckStockDetailBill(),
						jsonArr);
				// 分录信息校验
				tableList = PssPublicUtil.filterSepList(tableList);
				if (null == tableList || tableList.isEmpty()) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				// 已经生成盘点单据不能再保存
				if (null != pssCheckStockBill.getCheckStockNo() && !"".equals(pssCheckStockBill.getCheckStockNo())) {
					PssCheckStockBill csb = pssCheckStockBillFeign.getByCSBNo(pssCheckStockBill);
					if (null != csb) {
						String inNo = csb.getOtherStockInNo();
						String outNo = csb.getOtherStockOutNo();
						if ((null != inNo && !"".equals(inNo)) || null != outNo && !"".equals(outNo)) {
							dataMap.put("flag", "error");
							dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage() + "已生成盘点单据的盘点记录不能再被保存");
							return dataMap;
						}
					}
				}

				Map<String, String> reMap = pssCheckStockBillFeign.insertCSBBet(pssCheckStockBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("checkStockNo", pssCheckStockBill.getCheckStockNo());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			// Log.error("盘点保存出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 生成盘点单据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-31 上午09:56:06
	 */
	@RequestMapping(value = "/generateChkBillAjax")
	@ResponseBody
	public Map<String, Object> generateChkBillAjax(String ajaxData, String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpsscheckstockbill0002 = formService.getFormData("psscheckstockbill0002");
			getFormValue(formpsscheckstockbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscheckstockbill0002)) {
				PssCheckStockBill pssCheckStockBill = new PssCheckStockBill();
				setObjValue(formpsscheckstockbill0002, pssCheckStockBill);
				List<PssCheckStockDetailBill> tableList = PssPublicUtil.getMapByJsonObj(new PssCheckStockDetailBill(),
						jsonArr);
				// 分录信息校验
				tableList = PssPublicUtil.filterSepList(tableList);
				if (null == tableList || tableList.isEmpty()) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				// 已生成的盘点单据不能再次生成，且不能修改盘点信息
				if ((null != pssCheckStockBill.getOtherStockInNo() && !"".equals(pssCheckStockBill.getOtherStockInNo()))
						|| (null != pssCheckStockBill.getOtherStockOutNo()
								&& !"".equals(pssCheckStockBill.getOtherStockOutNo()))) {
					dataMap.put("flag", "error");
					dataMap.put("msg", "已生成盘点单据的盘点记录不能再生成单据");
					return dataMap;
				}

				Map<String, String> reMap = pssCheckStockBillFeign.insertChkBill(pssCheckStockBill, tableList);
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
			// Log.error("盘点保存出错", e);
			throw e;
		}
		return dataMap;
	}

}
