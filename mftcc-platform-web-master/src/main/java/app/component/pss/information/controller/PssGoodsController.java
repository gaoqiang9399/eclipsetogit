package app.component.pss.information.controller;

import java.util.ArrayList;
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
import app.component.docinterface.DocInterfaceFeign;
import app.component.finance.util.CwPublicUtil;
import app.component.nmd.entity.ParmDic;
import app.component.nmd.feign.ParmDicFeign;
import app.component.pss.conf.entity.PssConfig;
import app.component.pss.conf.feign.PssConfigFeign;
import app.component.pss.information.entity.PssGoods;
import app.component.pss.information.entity.PssStorehouse;
import app.component.pss.information.entity.PssUnit;
import app.component.pss.information.entity.PssUnitGoodsRel;
import app.component.pss.information.feign.PssGoodsFeign;
import app.component.pss.information.feign.PssStorehouseFeign;
import app.component.pss.information.feign.PssUnitFeign;
import app.component.pss.information.feign.PssUnitGoodsRelFeign;
import app.component.pss.utils.DealTableUtil;
import app.component.pss.utils.PssPublicUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssGoodsAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Aug 15 17:06:56 CST 2017
 **/
@Controller
@RequestMapping("/pssGoods")
public class PssGoodsController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssGoodsFeign pssGoodsFeign;
	@Autowired
	private PssConfigFeign pssConfigFeign;
	@Autowired
	private PssStorehouseFeign pssStorehouseFeign;
	@Autowired
	private ParmDicFeign parmDicFeign;
	@Autowired
	private PssUnitFeign pssUnitFeign;
	@Autowired
	private PssUnitGoodsRelFeign pssUnitGoodsRelFeign;
	@Autowired
	private DocInterfaceFeign docInterfaceFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,PssGoods pssGoods) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssGoodsFeign.getAll(pssGoods), null, true);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("tableData", tableHtml);
	}

	private void getTableData(List<PssGoods> list,String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", list, null, true);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("tableData", tableHtml);
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
		model.addAttribute("query", "");
		return "/component/pss/information/PssGoods_List";
	}

	@RequestMapping(value = "/getInputPage")
	public String getInputPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		PssGoods pssGoods = new PssGoods();
		pssGoods.setBatchWarningFlag("0");
		pssGoods.setStockWarningFlag("0");
		pssGoods.setSnFlag("0");
		pssGoods.setFlag("0");
		pssGoods.setGoodsId(WaterIdUtil.getWaterId());
		FormData formpssgoods0002 = formService.getFormData("pssgoods0002");
		getObjValue(formpssgoods0002, pssGoods);
		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setFlag("1");
		JSONObject json = new JSONObject();
		// 仓库数据
		List<PssStorehouse> houseList = new ArrayList<PssStorehouse>();
		houseList = pssStorehouseFeign.getAll(pssStorehouse);
		JSONArray houseJsonArray = new JSONArray();
		for (int i = 0; i < houseList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", houseList.get(i).getStorehouseId());
			jsonObject.put("name", houseList.get(i).getStorehouseName());
			houseJsonArray.add(jsonObject);
		}
		// 商品种类数据
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("PSS_GOODS_TYPE");
		List<ParmDic> goodsTypeDicList = parmDicFeign.findlist(parmDic);
		JSONArray goodsTypeArray = new JSONArray();
		for (int i = 0; i < goodsTypeDicList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", goodsTypeDicList.get(i).getOptCode());
			jsonObject.put("name", goodsTypeDicList.get(i).getOptName());
			goodsTypeArray.add(jsonObject);
		}
		// 商品单位
		PssUnit pssUnit = new PssUnit();
		pssUnit.setUnitType("1");
		List<PssUnit> unitList = pssUnitFeign.getAllOnByUnitType(pssUnit);
		JSONArray goodsUnitArray = new JSONArray();
		for (int i = 0; i < unitList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", unitList.get(i).getUnitId());
			jsonObject.put("name", unitList.get(i).getUnitName());
			goodsUnitArray.add(jsonObject);
		}
		// 商品单位
		pssUnit.setUnitType("2");
		pssUnit.setIsBase("1");
		List<PssUnit> mutiList = pssUnitFeign.getAllOnByUnitType(pssUnit);
		JSONArray goodsMutiUnitArray = new JSONArray();
		for (int i = 0; i < mutiList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", mutiList.get(i).getUnitId());
			jsonObject.put("name", mutiList.get(i).getGroupName());
			goodsMutiUnitArray.add(jsonObject);
		}
		json.put("houseJsonArray", houseJsonArray);
		json.put("goodsTypeArray", goodsTypeArray);
		json.put("goodsUnitArray", goodsUnitArray);
		json.put("goodsId", pssGoods.getGoodsId());
		json.put("goodsMutiUnitArray", goodsMutiUnitArray);

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
		model.addAttribute("formpssgoods0002", formpssgoods0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/information/PssGoods_Input";
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBatchAjax")
	@ResponseBody
	public Map<String, Object> deleteBatchAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String goodsIds = formMap.get("goodsIds");
			if (StringUtil.isNotEmpty(goodsIds)) {
				String[] ids = goodsIds.split(",");
				for (String s : ids) {
					PssGoods pssGoods = new PssGoods();
					pssGoods.setGoodsId(s);
					pssGoods.setDisplayFlag("0");
					pssGoodsFeign.updateDisplayFlag(pssGoods);
				}
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			//Log.error("商品删除出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageAjax")
	@ResponseBody
	public Map<String, Object> getListPageAjax(String ajaxData,Integer pageSize,Integer pageNo,String tableId,String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssGoods pssGoods = new PssGoods();
		pssGoods.setCustomQuery(ajaxData);// 自定义查询参数赋值
		pssGoods.setCustomSorts(ajaxData);// 自定义排序参数赋值
		pssGoods.setCriteriaList(pssGoods, ajaxData);// 我的筛选
		Ipage ipage = this.getIpage();
		ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		ipage.setParams(this.setIpageParams("pssGoods",pssGoods));
		ipage = pssGoodsFeign.queryList(ipage);
		// 返回相应的HTML方法
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
		// 处理需要隐藏的列
		Map<String, String> map = new HashMap<String, String>();
		tableHtml = DealTableUtil.dealTabStr(tableHtml, map);
		ipage.setResult(tableHtml);
		dataMap.put("ipage", ipage);
		return dataMap;
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssGoods pssGoods = new PssGoods();
		pssGoods.setCustomQuery(ajaxData);// 自定义查询参数赋值
		pssGoods.setCustomSorts(ajaxData);// 自定义排序参数赋值
		pssGoods.setCriteriaList(pssGoods, ajaxData);// 我的筛选
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("pssGoods",pssGoods));
		ipage = pssGoodsFeign.queryList(ipage);
		// 返回相应的HTML方法
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
		// 处理需要隐藏的列
		Map<String, String> map = new HashMap<String, String>();
		tableHtml = DealTableUtil.dealTabStr(tableHtml, map);
		ipage.setResult(tableHtml);
		dataMap.put("ipage", ipage);
		return dataMap;
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
		FormData formpssgoods0002 = formService.getFormData("pssgoods0002");
		PssGoods pssGoods = new PssGoods();
		List<PssGoods> pssGoodsList = pssGoodsFeign.getAll(pssGoods);
		model.addAttribute("formpssgoods0002", formpssgoods0002);
		model.addAttribute("pssGoodsList", pssGoodsList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssGoods_List";
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailPage")
	public String getDetailPage(Model model, String goodsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		FormData formpssgoods0002 = formService.getFormData("pssgoods0002");
		PssGoods pssGoods = new PssGoods();
		pssGoods.setGoodsId(goodsId);
		pssGoods = pssGoodsFeign.getById(pssGoods);
		getObjValue(formpssgoods0002, pssGoods);
		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setFlag("1");
		JSONObject json = new JSONObject();
		List<PssStorehouse> houseList = new ArrayList<PssStorehouse>();
		houseList = pssStorehouseFeign.getAll(pssStorehouse);
		JSONArray houseJsonArray = new JSONArray();
		for (int i = 0; i < houseList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", houseList.get(i).getStorehouseId());
			jsonObject.put("name", houseList.get(i).getStorehouseName());
			houseJsonArray.add(jsonObject);
		}
		// 商品种类数据
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("PSS_GOODS_TYPE");
		List<ParmDic> goodsTypeDicList = parmDicFeign.findlist(parmDic);
		JSONArray goodsTypeArray = new JSONArray();
		for (int i = 0; i < goodsTypeDicList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", goodsTypeDicList.get(i).getOptCode());
			jsonObject.put("name", goodsTypeDicList.get(i).getOptName());
			goodsTypeArray.add(jsonObject);
		}
		// 商品单位

		// 商品单位
		PssUnit pssUnit = new PssUnit();
		pssUnit.setUnitType("1");
		List<PssUnit> unitList = pssUnitFeign.getAllOnByUnitType(pssUnit);
		JSONArray goodsUnitArray = new JSONArray();
		for (int i = 0; i < unitList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", unitList.get(i).getUnitId());
			jsonObject.put("name", unitList.get(i).getUnitName());
			goodsUnitArray.add(jsonObject);
		}
		// 商品单位
		pssUnit.setUnitType("2");
		pssUnit.setIsBase("1");
		List<PssUnit> mutiList = pssUnitFeign.getAllOnByUnitType(pssUnit);
		JSONArray goodsMutiUnitArray = new JSONArray();
		for (int i = 0; i < mutiList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", mutiList.get(i).getUnitId());
			jsonObject.put("name", mutiList.get(i).getGroupName());
			goodsMutiUnitArray.add(jsonObject);
		}

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
		json.put("houseJsonArray", houseJsonArray);
		json.put("goodsTypeArray", goodsTypeArray);
		json.put("goodsUnitArray", goodsUnitArray);
		json.put("goodsId", pssGoods.getGoodsId());
		json.put("mutiUnitFlag", pssGoods.getMutiUnitFlag());
		json.put("snFlag", pssGoods.getSnFlag());
		json.put("batchWarningFlag", pssGoods.getBatchWarningFlag());
		json.put("goodsMutiUnitArray", goodsMutiUnitArray);
		String ajaxData = json.toString();
		model.addAttribute("formpssgoods0002", formpssgoods0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/information/PssGoods_Detail";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String unitData,String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		//HttpServletRequest request = request;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<PssUnitGoodsRel> unitList = PssPublicUtil.getMapByJsonObj(new PssUnitGoodsRel(), unitData);
		try {
			FormData formpssgoods0002 = formService.getFormData("pssgoods0002");
			getFormValue(formpssgoods0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssgoods0002)) {
				PssGoods pssGoods = new PssGoods();
				setObjValue(formpssgoods0002, pssGoods);
				pssGoods.setRegOpName(User.getRegName(request));
				pssGoods.setRegOpNo(User.getRegNo(request));
				pssGoods.setRegBrName(User.getOrgName(request));
				pssGoods.setRegBrNo(User.getOrgNo(request));
				pssGoods.setRegTime(DateUtil.getDateTime());
				pssGoods.setFlag("1");
				pssGoods.setDisplayFlag("1");
				pssGoodsFeign.insert(pssGoods);
				getTableData(tableId,pssGoods);// 获取列表
				PssUnitGoodsRel ugrBean = new PssUnitGoodsRel();
				if (pssGoods.getMutiUnitFlag() == "") {
					ugrBean.setUgrId(WaterIdUtil.getWaterId());
					ugrBean.setGoodsId(pssGoods.getGoodsId());
					ugrBean.setUnitId(pssGoods.getUnitId());
					ugrBean.setRelNum("1");
					ugrBean.setInvalidFlag("1");
					ugrBean.setDiscountRate1(pssGoods.getDiscountRate1());
					ugrBean.setDiscountRate2(pssGoods.getDiscountRate2());
					ugrBean.setEstimatedPurchasePrice(pssGoods.getEstimatedPurchasePrice());
					ugrBean.setRetailPrice(pssGoods.getRetailPrice());
					ugrBean.setVipPrice(pssGoods.getVipPrice());
					ugrBean.setWholesalePrice(pssGoods.getWholesalePrice());
					pssUnitGoodsRelFeign.insert(ugrBean);
				} else {
					ugrBean.setGoodsId(pssGoods.getGoodsId());
					ugrBean.setInvalidFlag("1");
					List<PssUnitGoodsRel> oldList = pssUnitGoodsRelFeign.getAllByGoodsId(ugrBean);
					if (oldList == null || oldList.size() == 0) {
						addUgr(unitList,pssGoods);
					} else {
						disAbleUgr(oldList);
						addUgr(unitList,pssGoods);
					}
				}
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

	@RequestMapping(value = "/addUgr")
	public void addUgr(List<PssUnitGoodsRel> unitList,PssGoods pssGoods) {
		for (PssUnitGoodsRel ugr : unitList) {
			ugr.setUgrId(WaterIdUtil.getWaterId());
			ugr.setGoodsId(pssGoods.getGoodsId());
			pssUnitGoodsRelFeign.insert(ugr);
		}
	}

	@RequestMapping(value = "/disAbleUgr")
	public void disAbleUgr(List<PssUnitGoodsRel> oldList) {
		for (PssUnitGoodsRel ugr : oldList) {
			pssUnitGoodsRelFeign.deleteByUgrId(ugr);
		}
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String unitData,String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssGoods pssGoods = new PssGoods();
		try {
			FormData formpssgoods0002 = formService.getFormData("pssgoods0002");
			getFormValue(formpssgoods0002, getMapByJson(ajaxData));
			List<PssUnitGoodsRel> unitList = PssPublicUtil.getMapByJsonObj(new PssUnitGoodsRel(), unitData);
			if (this.validateFormData(formpssgoods0002)) {
				pssGoods = new PssGoods();
				setObjValue(formpssgoods0002, pssGoods);
				pssGoodsFeign.update(pssGoods);
				PssUnitGoodsRel ugrBean = new PssUnitGoodsRel();
				getTableData(tableId,pssGoods);// 获取列表
				if (pssGoods.getMutiUnitFlag() == "") {
					ugrBean.setGoodsId(pssGoods.getGoodsId());
					pssUnitGoodsRelFeign.deleteByGoodsId(ugrBean);
					// ugrBean.setUgrId(WaterIdUtil.getWaterId());
					// ugrBean.setUnitId(pssGoods.getUnitId());
					// ugrBean.setDiscountRate1(pssGoods.getDiscountRate1());
					// ugrBean.setDiscountRate2(pssGoods.getDiscountRate2());
					// ugrBean.setEstimatedPurchasePrice(pssGoods.getEstimatedPurchasePrice());
					// ugrBean.setRetailPrice(pssGoods.getRetailPrice());
					// ugrBean.setVipPrice(pssGoods.getVipPrice());
					// ugrBean.setWholesalePrice(pssGoods.getWholesalePrice());
					ugrBean.setUgrId(WaterIdUtil.getWaterId());
					ugrBean.setGoodsId(pssGoods.getGoodsId());
					ugrBean.setUnitId(pssGoods.getUnitId());
					ugrBean.setRelNum("1");
					ugrBean.setInvalidFlag("1");
					ugrBean.setDiscountRate1(pssGoods.getDiscountRate1());
					ugrBean.setDiscountRate2(pssGoods.getDiscountRate2());
					ugrBean.setEstimatedPurchasePrice(pssGoods.getEstimatedPurchasePrice());
					ugrBean.setRetailPrice(pssGoods.getRetailPrice());
					ugrBean.setVipPrice(pssGoods.getVipPrice());
					ugrBean.setWholesalePrice(pssGoods.getWholesalePrice());
					pssUnitGoodsRelFeign.insert(ugrBean);
				} else {
					ugrBean.setGoodsId(pssGoods.getGoodsId());
					List<PssUnitGoodsRel> oldList = pssUnitGoodsRelFeign.getAllByGoodsId(ugrBean);
					if (oldList == null || oldList.size() == 0) {
						addUgr(unitList,pssGoods);
					} else {
						disAbleUgr(oldList);
						addUgr(unitList,pssGoods);
					}
				}
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateFlagAjax")
	@ResponseBody
	public Map<String, Object> updateFlagAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formpssgoods0002 = formService.getFormData("pssgoods0002");
			getFormValue(formpssgoods0002, jobj);
			PssGoods pssGoods = new PssGoods();
			setObjValue(formpssgoods0002, pssGoods);
			int count = pssGoodsFeign.updateFlag(pssGoods);
			if (count > 0) {
				pssGoods = pssGoodsFeign.getById(pssGoods);
				ArrayList<PssGoods> pssGoodsList = new ArrayList<PssGoods>();
				pssGoodsList.add(pssGoods);
				getTableData(pssGoodsList,tableId);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
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
	public Map<String, Object> getByIdAjax(String goodsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssgoods0002 = formService.getFormData("pssgoods0002");
		PssGoods pssGoods = new PssGoods();
		pssGoods.setGoodsId(goodsId);
		pssGoods = pssGoodsFeign.getById(pssGoods);
		getObjValue(formpssgoods0002, pssGoods, formData);
		if (pssGoods != null) {
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
	public Map<String, Object> deleteAjax(String goodsId,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssGoods pssGoods = new PssGoods();
		pssGoods.setGoodsId(goodsId);
		pssGoods.setDisplayFlag("0");
		try {
			pssGoodsFeign.updateDisplayFlag(pssGoods);
			getTableData(tableId,pssGoods);// 获取列表
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

}
