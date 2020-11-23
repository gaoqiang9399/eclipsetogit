package app.component.pss.stock.controller;

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

import app.base.User;
import app.component.finance.util.CwPublicUtil;
import app.component.pss.conf.entity.PssConfig;
import app.component.pss.conf.feign.PssConfigFeign;
import app.component.pss.information.entity.PssGoods;
import app.component.pss.information.entity.PssStorehouse;
import app.component.pss.information.feign.PssBillnoConfFeign;
import app.component.pss.information.feign.PssGoodsFeign;
import app.component.pss.information.feign.PssStorehouseFeign;
import app.component.pss.stock.entity.PssAlloTransBill;
import app.component.pss.stock.entity.PssAlloTransDetailBill;
import app.component.pss.stock.feign.PssAlloTransBillFeign;
import app.component.pss.stockinterface.ExcelInterfaceFeign;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssAlloTransBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 10 10:29:03 CST 2017
 **/
@Controller
@RequestMapping("/pssAlloTransBill")
public class PssAlloTransBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssAlloTransBillFeign pssAlloTransBillFeign;
	@Autowired
	private PssGoodsFeign pssGoodsFeign;
	@Autowired
	private PssStorehouseFeign pssStorehouseFeign;
	@Autowired
	private PssBillnoConfFeign pssBillnoConfFeign;
	@Autowired
	private PssConfigFeign pssConfigFeign;
	@Autowired
	private ExcelInterfaceFeign excelInterfaceFeign;
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
		 * Map<String, Object> dataMap = new HashMap<String, Object>(); String
		 * date = DateUtil.getDate("yyyy-MM-dd"); dataMap.put("pssStartDate",
		 * DateUtil.getMinMonthDate(date, "yyyy-MM-dd"));
		 * dataMap.put("pssEndDate", date);
		 */
		JSONArray pssAuditStsedJsonArray = new CodeUtils().getJSONArrayByKeyName("PSS_AUDIT_STSED");
		this.getHttpRequest().setAttribute("pssAuditStsedJsonArray", pssAuditStsedJsonArray);

		model.addAttribute("query", "");
		return "/component/pss/stock/PssAlloTransBill_List";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssAlloTransBill pssAlloTransBill = new PssAlloTransBill();
		try {
			pssAlloTransBill.setCustomQuery(ajaxData);
			pssAlloTransBill.setCustomSorts(ajaxData);
			pssAlloTransBill.setCriteriaList(pssAlloTransBill, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssAlloTransBillFeign.findByPage(ipage, pssAlloTransBill);
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
			// Log.error("查询调拨列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 查询调拨列表数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-10 上午10:56:06
	 */

	/*
	 * public Map<String, Object> getAlloTransBillListAjax(String ajaxData)
	 * throws Exception { FormService formService = new FormService();
	 * ActionContext.initialize(request, response); Map<String, Object> dataMap
	 * = new HashMap<String, Object>(); try { Map<String, String> formMap =
	 * CwPublicUtil.getMapByJson(ajaxData); Ipage ipage = this.getIpage();
	 * ipage.setPageNo(pageNo);//异步传页面翻页参数 ipage =
	 * pssAlloTransBillFeign.getAlloTransBillList(ipage, formMap); //返回相应的HTML方法
	 * JsonTableUtil jtu = new JsonTableUtil();
	 * 
	 * @SuppressWarnings("rawtypes") String tableHtml =
	 * jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
	 * //处理需要隐藏的列 Map<String, String> map = new HashMap<String, String>();
	 * map.put("printTimes", "printTimes"); map.put("memo", "memo");
	 * map.put("goodsUnit", "goodsUnit"); tableHtml =
	 * DealTableUtil.dealTabStr(tableHtml, map); ipage.setResult(tableHtml);
	 * dataMap.put("ipage",ipage); } catch (Exception e) { e.printStackTrace();
	 * Log.error("查询调拨列表数据出错", e); dataMap.put("flag", "error");
	 * dataMap.put("msg", e.getMessage()); throw e; } return dataMap; }
	 */
	/**
	 * 方法描述： 批量审核调拨单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-15 下午4:56:06
	 */
	@RequestMapping(value = "/checkATBBatchAjax")
	@ResponseBody
	public Map<String, Object> checkATBBatchAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
            formMap.put("regNo", User.getRegNo(request));
            formMap.put("regName", User.getRegName(request));
            formMap.put("orgNo", User.getOrgNo(request));
            formMap.put("orgName", User.getOrgName(request));
			Map<String, String> reMap = pssAlloTransBillFeign.updateChkBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("调拨单审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 批量反审核调拨单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-17 上午10:56:06
	 */
	@RequestMapping(value = "/reCheckATBBatchAjax")
	@ResponseBody
	public Map<String, Object> reCheckATBBatchAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Map<String, String> reMap = pssAlloTransBillFeign.updateReChkBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("调拨单审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 批量删除调拨单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-22 上午8:56:06
	 */
	@RequestMapping(value = "/deleteATBBatchAjax")
	@ResponseBody
	public Map<String, Object> deleteATBBatchAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Map<String, String> reMap = pssAlloTransBillFeign.deleteATBBatch(formMap);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("调拨单删除出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 新增调拨单
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
		// dataMap.put("alloTransNo", "AT"+WaterIdUtil.getWaterId());
		dataMap.put("alloTransNo", pssBillnoConfFeign.getBillNo(PssEnumBean.PSS_BILL_TYPE.DBD.getValue(), "view"));
		FormData formpssallotransbill0002 = formService.getFormData("pssallotransbill0002");

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

		model.addAttribute("formpssallotransbill0002", formpssallotransbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssAlloTransBill_Input";
	}

	/**
	 * 方法描述： 保存调拨单(insert)
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
			FormData formpssallotransbill0002 = formService.getFormData("pssallotransbill0002");
			getFormValue(formpssallotransbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssallotransbill0002)) {
				PssAlloTransBill pssAlloTransBill = new PssAlloTransBill();
				setObjValue(formpssallotransbill0002, pssAlloTransBill);
				// 校验单据编号不能重复
				/*
				 * PssAlloTransBill ATBTem =
				 * pssAlloTransBillFeign.getByATBNo(pssAlloTransBill); if(null
				 * != ATBTem){ dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; }
				 */
				List<PssAlloTransDetailBill> tableList = PssPublicUtil.getMapByJsonObj(new PssAlloTransDetailBill(),
						jsonArr);
				tableList = PssPublicUtil.filterSepList(tableList);
				if (null == tableList || tableList.size() == 0) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				// pssAlloTransBillFeign.insertATB(pssAlloTransBill,
				// tableListMap);
				Map<String, String> reMap = pssAlloTransBillFeign.insertATBBet(pssAlloTransBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("alloTransNo", pssAlloTransBill.getAlloTransNo());
				dataMap.put("alloTransId", pssAlloTransBill.getAlloTransId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			// Log.error("调拨单保存出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 保存调拨单(update)
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-22 下午4:56:06
	 */
	@RequestMapping(value = "/updateOrderAjax")
	@ResponseBody
	public Map<String, Object> updateOrderAjax(String ajaxData, String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssallotransbill0002 = formService.getFormData("pssallotransbill0002");
			getFormValue(formpssallotransbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssallotransbill0002)) {
				PssAlloTransBill pssAlloTransBill = new PssAlloTransBill();
				setObjValue(formpssallotransbill0002, pssAlloTransBill);
				List<PssAlloTransDetailBill> tableList = PssPublicUtil.getMapByJsonObj(new PssAlloTransDetailBill(),
						jsonArr);
				tableList = PssPublicUtil.filterSepList(tableList);
				if (null == tableList || tableList.size() == 0) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				Map<String, String> reMap = pssAlloTransBillFeign.updateATBBet(pssAlloTransBill, tableList);
				dataMap.put("flag", "success");
				dataMap.put("data", reMap);
				dataMap.put("alloTransId", pssAlloTransBill.getAlloTransId());
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			// Log.error("调拨单保存出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 审核调拨单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-05 上午09:56:06
	 */
	@RequestMapping(value = "/auditOrderAjax")
	@ResponseBody
	public Map<String, Object> auditOrderAjax(String ajaxData, String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssallotransbill0002 = formService.getFormData("pssallotransbill0002");
			getFormValue(formpssallotransbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssallotransbill0002)) {
				PssAlloTransBill pssAlloTransBill = new PssAlloTransBill();
				setObjValue(formpssallotransbill0002, pssAlloTransBill);

				// 校验单据编号不能重复
				/*
				 * if(null != pssAlloTransBill.getAlloTransId() &&
				 * !"".equals(pssAlloTransBill.getAlloTransId())){
				 * PssAlloTransBill patb =
				 * pssAlloTransBillFeign.getById(pssAlloTransBill); if(null ==
				 * patb){ PssAlloTransBill ATBTem =
				 * pssAlloTransBillFeign.getByATBNo(pssAlloTransBill); if(null
				 * != ATBTem){ dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; } } }else{ PssAlloTransBill ATBTem =
				 * pssAlloTransBillFeign.getByATBNo(pssAlloTransBill); if(null
				 * != ATBTem){ dataMap.put("flag", "error");
				 * dataMap.put("msg",MessageEnum.ERROR_REPEAT.getMessage("单据编号")
				 * ); return dataMap; } }
				 */

				List<PssAlloTransDetailBill> tableList = PssPublicUtil.getMapByJsonObj(new PssAlloTransDetailBill(),
						jsonArr);
				tableList = PssPublicUtil.filterSepList(tableList);
				if (null == tableList || tableList.size() == 0) {
					// 分录信息为空
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
					return dataMap;
				}
				Map<String, String> reMap = pssAlloTransBillFeign.updateChk(pssAlloTransBill, tableList);
				dataMap.put("data", reMap);
				dataMap.put("alloTransNo", pssAlloTransBill.getAlloTransNo());
				dataMap.put("alloTransId", pssAlloTransBill.getAlloTransId());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("调拨单审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 反审核调拨单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-05 下午01:56:06
	 */
	@RequestMapping(value = "/reAuditOrderAjax")
	@ResponseBody
	public Map<String, Object> reAuditOrderAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssallotransbill0002 = formService.getFormData("pssallotransbill0002");
			getFormValue(formpssallotransbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssallotransbill0002)) {
				PssAlloTransBill pssAlloTransBill = new PssAlloTransBill();
				setObjValue(formpssallotransbill0002, pssAlloTransBill);
				Map<String, String> reMap = pssAlloTransBillFeign.updateReChk(pssAlloTransBill);
				dataMap.put("data", reMap);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("调拨单反审核出错", e);
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
			FormData formpssallotransbill0002 = formService.getFormData("pssallotransbill0002");
			getFormValue(formpssallotransbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssallotransbill0002)) {
				PssAlloTransBill pssAlloTransBill = new PssAlloTransBill();
				setObjValue(formpssallotransbill0002, pssAlloTransBill);
				pssAlloTransBillFeign.insert(pssAlloTransBill);
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
		PssAlloTransBill pssAlloTransBill = new PssAlloTransBill();
		try {
			FormData formpssallotransbill0002 = formService.getFormData("pssallotransbill0002");
			getFormValue(formpssallotransbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssallotransbill0002)) {
				pssAlloTransBill = new PssAlloTransBill();
				setObjValue(formpssallotransbill0002, pssAlloTransBill);
				pssAlloTransBillFeign.update(pssAlloTransBill);
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
	 * 方法描述： 保存调拨单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-21 上午11:56:06
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String alloTransId, String alloTransNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssallotransbill0002 = formService.getFormData("pssallotransbill0002");
		PssAlloTransBill pssAlloTransBill = new PssAlloTransBill();
		pssAlloTransBill.setAlloTransId(alloTransId);
		pssAlloTransBill.setAlloTransNo(alloTransNo);
		if (null != alloTransId && !"".equals(alloTransId)) {
			pssAlloTransBill = pssAlloTransBillFeign.getById(pssAlloTransBill);
		} else if (null != alloTransNo && !"".equals(alloTransNo)) {
			pssAlloTransBill = pssAlloTransBillFeign.getByATBNo(pssAlloTransBill);
		}else {
		}
		getObjValue(formpssallotransbill0002, pssAlloTransBill);

		// 审核状态
		dataMap.put("auditSts", pssAlloTransBill.getAuditSts());

		JSONObject json = new JSONObject();
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

		model.addAttribute("formpssallotransbill0002", formpssallotransbill0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssAlloTransBill_Detail";
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String alloTransId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssallotransbill0002 = formService.getFormData("pssallotransbill0002");
		PssAlloTransBill pssAlloTransBill = new PssAlloTransBill();
		pssAlloTransBill.setAlloTransId(alloTransId);
		pssAlloTransBill = pssAlloTransBillFeign.getById(pssAlloTransBill);
		getObjValue(formpssallotransbill0002, pssAlloTransBill, formData);
		if (pssAlloTransBill != null) {
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
	public Map<String, Object> deleteAjax(String alloTransId, String alloTransNo) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssAlloTransBill pssAlloTransBill = new PssAlloTransBill();
		pssAlloTransBill.setAlloTransId(alloTransId);
		pssAlloTransBill.setAlloTransNo(alloTransNo);
		try {
			pssAlloTransBill = pssAlloTransBillFeign.getById(pssAlloTransBill);
			if (null == pssAlloTransBill) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage() + "调拨单信息不存在");
				return dataMap;
			}
			pssAlloTransBillFeign.delete(pssAlloTransBill);
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
	 * 导入
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/importExcel")
	public String importExcel(Model model, String ajaxData) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("downloadTmplate", "PssAlloTransBillAction_downloadTemplate.action");
		dataMap.put("serverUrl", "PssAlloTransBillActionAjax_uploadExcelAjax.action");
		dataMap.put("downloadErrorUrl", "PssAlloTransBillAction_downloadErrorExcel.action");

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
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		try {
			// HttpServletResponse response = response;

			String excelName = "调拨单导入模板";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";

			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			/*
			 * byte[]b=new byte[1024]; OutputStream
			 * oStream=response.getOutputStream(); InputStream is =null;
			 * is=pssAlloTransBillFeign.getDownFileStream(is); int
			 * size=is.read(b); while(size>0){ oStream.write(b,0,size);
			 * size=is.read(b); } is.close(); oStream.close();
			 */

			// 调拨单
			String type = "PssAlloTransBill_import";
			HSSFWorkbook pssWorkBook = excelInterfaceFeign.getDownFileStream(type);
			// 输出流
			ServletOutputStream out = response.getOutputStream();
			pssWorkBook.write(out);
			response.flushBuffer();
			out.flush();
			out.close();

		} catch (Exception e) {
			// Log.error("PssAlloTransBillAction_downloadTemplate方法出错，执行action层失败，抛出异常，"
			// + e.getMessage(), e);
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
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String type = "PssAlloTransBill_import";
		try {
			/*
			 * pssAlloTransBillFeign.uploadExcel(vch); dataMap.put("flag",
			 * "success"); dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
			 */
			String result = pssAlloTransBillFeign.uploadExcel(vch, type);
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
			dataMap.put("msg", "调拨单导入失败");
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
	public void downloadErrorExcel(Model model, String errorFileUrl) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		// HttpServletResponse response = response;
		FileInputStream in = null;
		ServletOutputStream out = null;
		try{
			String excelName = "调拨单导入校验文件";
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
	public void downloadToExcel(Model model, String alloTransIds) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		try {
			// HttpServletRequest request = request;
			// HttpServletResponse response = response;

			request.setCharacterEncoding("utf-8");

			String excelName = "调拨单导出";
			String filename = excelName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("alloTransIds", alloTransIds);
			paramMap.put("type", "PssAlloTransBill_export");
			HSSFWorkbook pssWorkBook = pssAlloTransBillFeign.downloadToExcel(paramMap);
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
