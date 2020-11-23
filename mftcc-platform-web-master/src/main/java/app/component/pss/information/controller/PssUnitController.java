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

import app.component.pss.information.entity.PssUnit;
import app.component.pss.information.feign.PssUnitFeign;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssUnitAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Dec 04 10:18:34 CST 2017
 **/
@Controller
@RequestMapping("/pssUnit")
public class PssUnitController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssUnitFeign pssUnitFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssUnit pssUnit) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssUnitFeign.getAll(pssUnit), null, true);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tableData", tableHtml);
	}

	private void getTableData(List<PssUnit> list, String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", list, null, true);
		Map<String, Object> dataMap = new HashMap<String, Object>();
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpssunit = formService.getFormData("pssunit0002");
		PssUnit pssUnit = new PssUnit();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("pssUnit", pssUnit));
		List<PssUnit> pssUnitList = (List<PssUnit>) pssUnitFeign.findByPage(ipage).getResult();
		model.addAttribute("formpssunit", formpssunit);
		model.addAttribute("pssUnitList", pssUnitList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssUnit_List";
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
		FormData formpssunit = formService.getFormData("pssunit0002");
		PssUnit pssUnit = new PssUnit();
		List<PssUnit> pssUnitList = pssUnitFeign.getAll(pssUnit);
		model.addAttribute("formpssunit", formpssunit);
		model.addAttribute("pssUnitList", pssUnitList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssUnit_List";
	}

	/**
	 * 详细页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailSinPage")
	public String getDetailSinPage(Model model, String unitId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpssunit = formService.getFormData("pssunitsingle01");
		PssUnit pssUnit = new PssUnit();
		pssUnit.setUnitId(unitId);
		pssUnit = pssUnitFeign.getById(pssUnit);
		getObjValue(formpssunit, pssUnit);
		model.addAttribute("formpssunit", formpssunit);
		model.addAttribute("query", "");
		return "/component/pss/information/PssUnit_SingleDetail";
	}

	/**
	 * 详细页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailMutiPage")
	public String getDetailMutiPage(Model model, String relId, String formpssunit) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<PssUnit> unitList = new ArrayList<PssUnit>();
		PssUnit pssUnit = new PssUnit();
		pssUnit.setRelId(relId);
		unitList = pssUnitFeign.getMutiById(pssUnit);
		JSONArray unitJson = JSONArray.fromObject(unitList);
		dataMap.put("unitList", unitJson.toString());
		model.addAttribute("formpssunit", formpssunit);
		model.addAttribute("query", "");
		return "/component/pss/information/PssUnit_MutiDetail";
	}

	/**
	 * 详细页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMutiByRelIdAjax")
	@ResponseBody
	public Map<String, Object> getMutiByRelIdAjax(String relId, Integer pageSize, Integer pageNo, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssUnit pssUnit = new PssUnit();
		pssUnit.setRelId(relId);
		Ipage ipage = this.getIpage();
		ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		ipage.setParams(this.setIpageParams("pssUnit", pssUnit));
		ipage = pssUnitFeign.findByPage(ipage);
		// 返回相应的HTML方法
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
		// 处理需要隐藏的列
		ipage.setResult(tableHtml);
		dataMap.put("ipage", ipage);
		return dataMap;
	}

	/**
	 * 详细页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByRelIdGoodsIdAjax")
	@ResponseBody
	public Map<String, Object> getByRelIdGoodsIdAjax(String relId, String goodsId, Integer pageSize, Integer pageNo,
			String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Ipage ipage = this.getIpage();
		ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		ipage = pssUnitFeign.getByRelIdGoodsId(ipage, relId, goodsId);
		// 返回相应的HTML方法
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
		// 处理需要隐藏的列
		ipage.setResult(tableHtml);
		dataMap.put("ipage", ipage);
		return dataMap;
	}

	@RequestMapping(value = "/getInputPage")
	public String getInputPage(Model model, String unitType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		String result = "";
		switch (unitType) {
		case "1":
			FormData formpssunit = formService.getFormData("pssunitsingle01");
			PssUnit pssUnit = new PssUnit();
			pssUnit.setUnitType("1");// 独立单位
			getObjValue(formpssunit, pssUnit);
			model.addAttribute("formpssunit", formpssunit);
			model.addAttribute("query", "");
			result = "/component/pss/information/PssUnit_SingleInput";
			break;
		case "2":
			result = "/component/pss/information/PssUnit_MutiInput";
			break;
			default:
		}

		return result;
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssUnit pssUnit = new PssUnit();
		try {
			pssUnit.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pssUnit.setCustomSorts(ajaxData);// 自定义排序参数赋值
			pssUnit.setCriteriaList(pssUnit, ajaxData);// 我的筛选

			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("pssUnit", pssUnit));
			// 自定义查询Bo方法
			ipage = pssUnitFeign.findByPage(ipage);
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

	@RequestMapping(value = "/findByPageSingleAjax")
	@ResponseBody
	public Map<String, Object> findByPageSingleAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssUnit pssUnit = new PssUnit();
		try {
			pssUnit.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pssUnit.setCustomSorts(ajaxData);// 自定义排序参数赋值
			pssUnit.setCriteriaList(pssUnit, ajaxData);// 我的筛选

			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("pssUnit", pssUnit));
			// 自定义查询Bo方法
			ipage = pssUnitFeign.findByPageSingle(ipage);
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

	@RequestMapping(value = "/findByPageMutiAjax")
	@ResponseBody
	public Map<String, Object> findByPageMutiAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssUnit pssUnit = new PssUnit();
		try {
			pssUnit.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pssUnit.setCustomSorts(ajaxData);// 自定义排序参数赋值
			pssUnit.setCriteriaList(pssUnit, ajaxData);// 我的筛选

			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("pssUnit", pssUnit));
			// 自定义查询Bo方法
			ipage = pssUnitFeign.findByPageMuti(ipage);
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
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssunit = formService.getFormData("pssunit0002");
			getFormValue(formpssunit, getMapByJson(ajaxData));
			if (this.validateFormData(formpssunit)) {
				PssUnit pssUnit = new PssUnit();
				setObjValue(formpssunit, pssUnit);
				pssUnitFeign.insert(pssUnit);
				getTableData(tableId, pssUnit);// 获取列表
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
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertMutiAjax")
	@ResponseBody
	public Map<String, Object> insertMutiAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<PssUnit> unitList = PssPublicUtil.getMapByJsonObj(new PssUnit(), ajaxData);
			if (unitList.size() > 0) {
				Map<String, String> result = pssUnitFeign.insert(unitList);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
				dataMap.put("unitName", result.get("groupName"));
				dataMap.put("unitId", result.get("relId"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", "没有数据");
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
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertSingleAjax")
	@ResponseBody
	public Map<String, Object> insertSingleAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssunit = formService.getFormData("pssunitsingle01");
			getFormValue(formpssunit, getMapByJson(ajaxData));
			if (this.validateFormData(formpssunit)) {
				PssUnit pssUnit = new PssUnit();
				setObjValue(formpssunit, pssUnit);
				if (pssUnitFeign.getSameName(pssUnit) > 0) {
					dataMap.put("flag", "error");
					dataMap.put("msg", "单位名称已经存在");
					return dataMap;
				}
				String waterId = WaterIdUtil.getWaterId();
				pssUnit.setUnitId(waterId);
				pssUnit.setRelId(waterId);
				pssUnit.setInvalidFlag(PssEnumBean.YES_OR_NO.YES.getNum());
				pssUnit.setIsBase("1");
				pssUnit.setRelNum("1");
				pssUnitFeign.insert(pssUnit);
				getTableData(tableId, pssUnit);// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
				dataMap.put("unitId", pssUnit.getUnitId());
				dataMap.put("unitName", pssUnit.getUnitName());
			} else {
				dataMap.put("flag", "");
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
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssUnit pssUnit = new PssUnit();
		try {
			FormData formpssunit = formService.getFormData("pssunitsingle01");
			getFormValue(formpssunit, getMapByJson(ajaxData));
			if (this.validateFormData(formpssunit)) {
				pssUnit = new PssUnit();
				setObjValue(formpssunit, pssUnit);
				pssUnitFeign.update(pssUnit);
				getTableData(tableId, pssUnit);// 获取列表
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
	@RequestMapping(value = "/updateMutiAjax")
	@ResponseBody
	public Map<String, Object> updateMutiAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssUnit pssUnit = new PssUnit();
		// 获取数据
		List<PssUnit> unitList = PssPublicUtil.getMapByJsonObj(new PssUnit(), ajaxData);
		// 拼接多单位祖名
		String tmpName = "";
		String tmpRate = "";
		for (int i = 0; i < unitList.size(); i++) {
			if (i == 0) {
				tmpName += unitList.get(0).getUnitName() + ",";
				tmpRate += "1:";
			} else {
				tmpName += unitList.get(i).getUnitName();
				tmpRate += unitList.get(i).getRelNum();
				if (i < unitList.size() - 1) {
					tmpName += ",";
					tmpRate += ":";
				}
			}
		}
		// 更新数据
		String relId = unitList.get(0).getRelId();
		pssUnit.setRelId(relId);
		List<PssUnit> oldList = pssUnitFeign.getMutiById(pssUnit);
		for (int i = 0; i < unitList.size(); i++) {
			pssUnit = new PssUnit();
			if (StringUtil.isEmpty(unitList.get(i).getUnitId())) {
				// 如果unitId为空，判断为新增副单位
				pssUnit.setUnitId(WaterIdUtil.getWaterId());
				pssUnit.setRelId(relId);
				pssUnit.setUnitType("2");
				pssUnit.setIsBase("0");
				pssUnit.setUnitName(unitList.get(i).getUnitName());
				pssUnit.setRelNum(unitList.get(i).getRelNum());
				pssUnitFeign.insert(pssUnit);
			} else {
				// 如果unitId 存在，与旧值id匹配，并把更新的值放入原有数据中
				for (int j = 0; j < oldList.size(); j++) {
					if (unitList.get(i).getUnitId().equals(oldList.get(j).getUnitId())) {
						pssUnit = oldList.get(j);
						pssUnit.setUnitName(unitList.get(i).getUnitName());
						if (pssUnit.getIsBase() == "0") {
							pssUnit.setRelNum(unitList.get(i).getRelNum());
						} else {
							pssUnit.setGroupName(tmpName + "(" + tmpRate + ")");
						}
						pssUnitFeign.update(pssUnit);
					}
				}
			}
		}
		pssUnit = new PssUnit();
		pssUnit.setRelId(relId);
		List<PssUnit> fList = pssUnitFeign.getMutiById(pssUnit);
		JSONArray unitJson = JSONArray.fromObject(fList);
		dataMap.put("flag", "success");
		dataMap.put("unitList", unitJson.toString());
		dataMap.put("relId", relId);
		return dataMap;
	}

	/**
	 * ` 更新标志
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSingleFlagAjax")
	@ResponseBody
	public Map<String, Object> updateSingleFlagAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formpssunit = formService.getFormData("pssunitsingle01");
			getFormValue(formpssunit, jobj);
			PssUnit pssUnit = new PssUnit();
			setObjValue(formpssunit, pssUnit);
			int count = pssUnitFeign.updateFlag(pssUnit);
			if (count > 0) {
				pssUnit = pssUnitFeign.getById(pssUnit);
				ArrayList<PssUnit> pssUnitList = new ArrayList<PssUnit>();
				pssUnitList.add(pssUnit);
				getTableData(pssUnitList, tableId);
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
	 * 更新标志
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateMutiFlagAjax")
	@ResponseBody
	public Map<String, Object> updateMutiFlagAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			String unitId = (String) jobj.get("unitId");
			String invalidFlag = (String) jobj.get("invalidFlag");
			PssUnit pssUnit = new PssUnit();
			pssUnit.setUnitId(unitId);
			pssUnit = pssUnitFeign.getById(pssUnit);
			pssUnit.setInvalidFlag(invalidFlag);
			if (pssUnitFeign.updateFlag(pssUnit) > 0) {
				pssUnit = pssUnitFeign.getById(pssUnit);
				ArrayList<PssUnit> pssUnitList = new ArrayList<PssUnit>();
				pssUnitList.add(pssUnit);
				getTableData(pssUnitList, tableId);
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
	@RequestMapping(value = "/getByIdSinAjax")
	@ResponseBody
	public Map<String, Object> getByIdSinAjax(String unitId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssunit = formService.getFormData("pssunitsingle01");
		PssUnit pssUnit = new PssUnit();
		pssUnit.setUnitId(unitId);
		pssUnit = pssUnitFeign.getById(pssUnit);
		getObjValue(formpssunit, pssUnit, formData);
		if (pssUnit != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String unitId, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssUnit pssUnit = new PssUnit();
		pssUnit.setUnitId(unitId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssUnit = (PssUnit) JSONObject.toBean(jb, PssUnit.class);
			pssUnitFeign.delete(pssUnit);
			getTableData(tableId, pssUnit);// 获取列表
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
