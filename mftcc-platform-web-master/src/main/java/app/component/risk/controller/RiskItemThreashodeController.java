package app.component.risk.controller;

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

import app.component.common.EntityUtil;
import app.component.nmd.entity.ParmDic;
import app.component.nmd.feign.ParmDicFeign;
import app.component.nmd.feign.ParmKeyFeign;
import app.component.risk.entity.RiskItemThreashode;
import app.component.risk.feign.RiskItemThreashodeFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: RiskItemThreashodeAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Feb 23 06:49:07 GMT 2016
 **/
@Controller
@RequestMapping("/riskItemThreashode")
public class RiskItemThreashodeController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入RiskItemThreashodeBo
	@Autowired
	private RiskItemThreashodeFeign riskItemThreashodeFeign;

	@Autowired
	private ParmKeyFeign parmKeyFeign;
	@Autowired
	private ParmDicFeign parmDicFeign;

	private Map<String, Object> getTableData(String itemNo, String tableId) throws Exception {
		FormService formService = new FormService();
		JsonTableUtil jtu = new JsonTableUtil();
		FormData formrisk0004 = formService.getFormData("risk0004");
		// parmKeys = parmKeyFeign.findByPage(null);
		RiskItemThreashode itemThreashode = new RiskItemThreashode();
		itemThreashode.setItemNo(itemNo);
		Ipage ipage = getIpage();
		List<RiskItemThreashode> riskItemThreashodeList = (List<RiskItemThreashode>) riskItemThreashodeFeign
				.getListPage(ipage, itemThreashode).getResult();
		for (RiskItemThreashode riskItemThreashode : riskItemThreashodeList) {
			if ("2".equals(riskItemThreashode.getThreashodeType())) {
				String dicKeyName = riskItemThreashode.getDicKeyName();
				if (dicKeyName.contains("-")) {
					dicKeyName = dicKeyName.substring(0, dicKeyName.indexOf("-"));
				}
				ParmDic parmDic = new ParmDic();
				parmDic.setKeyName(dicKeyName);
				parmDic.setOptCode(riskItemThreashode.getDicKeyValue());
				String dickeyValue = parmDicFeign.getById(parmDic).getOptName();
				riskItemThreashode.setThreashodeValue(dickeyValue);
			}
		}
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", riskItemThreashodeList, null, true);
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("tableData", tableHtml);
		dataMap.put("riskItemThreashodeList", riskItemThreashodeList);
		dataMap.put("formrisk0004", formrisk0004);
		return dataMap;
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/risk/RiskItemThreashode_List";
	}

	@RequestMapping(value = "/getListPageByItemNo")
	public String getListPageByItemNo(Model model, String itemNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrisk0004 = formService.getFormData("risk0004");
//		List<ParmKey> parmKeys = parmKeyFeign.findByPage(null);

		RiskItemThreashode itemThreashode = new RiskItemThreashode();
		itemThreashode.setItemNo(itemNo);
		Ipage ipage = getIpage();
		List<RiskItemThreashode> riskItemThreashodeList = (List<RiskItemThreashode>) riskItemThreashodeFeign
				.getListPage(ipage, itemThreashode).getResult();
		for (RiskItemThreashode riskItemThreashode : riskItemThreashodeList) {
			if ("2".equals(riskItemThreashode.getThreashodeType())) {
				String dicKeyName = riskItemThreashode.getDicKeyName();
				if (dicKeyName.contains("-")) {
					dicKeyName = dicKeyName.substring(0, dicKeyName.indexOf("-"));
				}
				ParmDic parmDic = new ParmDic();
				parmDic.setKeyName(dicKeyName);
				parmDic.setOptCode(riskItemThreashode.getDicKeyValue());
				parmDic = parmDicFeign.getById(parmDic);
				String dickeyValue = "";
				if (parmDic != null) {
					dickeyValue = parmDic.getOptName();

				}
				riskItemThreashode.setThreashodeValue(dickeyValue);
			}
		}
		model.addAttribute("formrisk0004", formrisk0004);
		model.addAttribute("query", "");
		return "/component/risk/RiskItemThreashode_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RiskItemThreashode riskItemThreashode = new RiskItemThreashode();
		try {
			riskItemThreashode.setCustomQuery(ajaxData);// 自定义查询参数赋值
			riskItemThreashode.setCriteriaList(riskItemThreashode, ajaxData);// 我的筛选
			// getRoleConditions(riskItemThreashode,"1000000001");//记录级权限控制方法
			Ipage ipage = getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = riskItemThreashodeFeign.findByPage(ipage, riskItemThreashode);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
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
			FormData formrisk0004 = formService.getFormData("risk0004");
			getFormValue(formrisk0004, getMapByJson(ajaxData));
			if (validateFormData(formrisk0004)) {
				RiskItemThreashode riskItemThreashode = new RiskItemThreashode();
				setObjValue(formrisk0004, riskItemThreashode);
				riskItemThreashodeFeign.insert(riskItemThreashode);
				String itemNo = riskItemThreashode.getItemNo();
				dataMap.putAll(getTableData(itemNo, tableId));// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrisk0004 = formService.getFormData("risk0004");
		getFormValue(formrisk0004, getMapByJson(ajaxData));
		RiskItemThreashode riskItemThreashodeJsp = new RiskItemThreashode();
		setObjValue(formrisk0004, riskItemThreashodeJsp);
		RiskItemThreashode riskItemThreashode = riskItemThreashodeFeign.getById(riskItemThreashodeJsp);
		if (riskItemThreashode != null) {
			try {
				riskItemThreashode = (RiskItemThreashode) EntityUtil.reflectionSetVal(riskItemThreashode,
						riskItemThreashodeJsp, getMapByJson(ajaxData));
				riskItemThreashodeFeign.update(riskItemThreashode);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
	public Map<String, Object> updateAjax(String ajaxData, String itemNo, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrisk0004 = formService.getFormData("risk0004");
			getFormValue(formrisk0004, getMapByJson(ajaxData));
			if (validateFormData(formrisk0004)) {
				RiskItemThreashode riskItemThreashode = new RiskItemThreashode();
				setObjValue(formrisk0004, riskItemThreashode);
				riskItemThreashodeFeign.update(riskItemThreashode);
				itemNo = riskItemThreashode.getItemNo();
				dataMap.putAll(getTableData(itemNo, tableId));// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getParmDicAjax")
	@ResponseBody
	public Map<String, Object> getParmDicAjax(String keyName) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			ParmDic parmDic = new ParmDic();
			if (keyName.contains("-")) {
				keyName = keyName.substring(0, keyName.indexOf("-"));

			}
			parmDic.setKeyName(keyName);
			List<ParmDic> parmDics = parmDicFeign.findByPage(parmDic);
			dataMap.put("parmDics", parmDics);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("查询字典项"));
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
	public Map<String, Object> getByIdAjax(String threashodeName, String itemNo, String threashodeIndex)
			throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();

		RiskItemThreashode riskItemThreashode = new RiskItemThreashode();
		riskItemThreashode.setItemNo(itemNo);
		riskItemThreashode.setThreashodeName(threashodeName);
		riskItemThreashode.setThreashodeIndex(threashodeIndex);
		riskItemThreashode = riskItemThreashodeFeign.getById(riskItemThreashode);

		FormData formrisk0004 = formService.getFormData("risk0004");
		String threashodeType = riskItemThreashode.getThreashodeType();
		if ("2".equals(threashodeType)) {
			String dicKeyNameString = riskItemThreashode.getDicKeyName();
			String keyName = "";
			if (dicKeyNameString.contains("-")) {
				keyName = dicKeyNameString.substring(0, dicKeyNameString.indexOf("-"));

			}
			ParmDic parmDic = new ParmDic();
			parmDic.setKeyName(keyName);
			List<ParmDic> parmDics = parmDicFeign.findByPage(parmDic);
			dataMap.put("parmDics", parmDics);
			dataMap.put("selectOption", riskItemThreashode.getDicKeyValue());
		}
		getObjValue(formrisk0004, riskItemThreashode, formData);

		if (riskItemThreashode != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	@RequestMapping(value = "/getByIdAjax1")
	@ResponseBody
	public Map<String, Object> getByIdAjax1(String itemNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();

		RiskItemThreashode risk = new RiskItemThreashode();
		risk.setItemNo(itemNo);
		List<RiskItemThreashode> riskItemThreashodeList = riskItemThreashodeFeign.getListByItemNo(risk);
		dataMap.put("size", riskItemThreashodeList.size() + 1);
		RiskItemThreashode riskItemThreashode = new RiskItemThreashode();

		if (riskItemThreashodeList.size() > 0) {

			riskItemThreashode = riskItemThreashodeList.get(0);
		}

		FormData formrisk0004 = formService.getFormData("risk0004");
		String threashodeType = riskItemThreashode.getThreashodeType();
		if ("2".equals(threashodeType)) {
			String dicKeyNameString = riskItemThreashode.getDicKeyName();
			String keyName = "";
			if (dicKeyNameString.contains("-")) {
				keyName = dicKeyNameString.substring(0, dicKeyNameString.indexOf("-"));

			}
			ParmDic parmDic = new ParmDic();
			parmDic.setKeyName(keyName);
			List<ParmDic> parmDics = parmDicFeign.findByPage(parmDic);
			dataMap.put("parmDics", parmDics);
			dataMap.put("selectOption", riskItemThreashode.getDicKeyValue());
		}
		getObjValue(formrisk0004, riskItemThreashode, formData);

		if (riskItemThreashode != null) {
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
	public Map<String, Object> deleteAjax(String threashodeName, String itemNo, String threashodeIndex, String tableId)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RiskItemThreashode riskItemThreashode = new RiskItemThreashode();
		riskItemThreashode.setThreashodeName(threashodeName);
		riskItemThreashode.setItemNo(itemNo);
		riskItemThreashode.setThreashodeIndex(threashodeIndex);
		try {
			riskItemThreashodeFeign.delete(riskItemThreashode);
			dataMap.putAll(getTableData(itemNo, tableId));// 获取列表
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrisk0004 = formService.getFormData("risk0004");
		model.addAttribute("formrisk0004", formrisk0004);
		model.addAttribute("query", "");
		return "/component/risk/RiskItemThreashode_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrisk0004 = formService.getFormData("risk0004");
		getFormValue(formrisk0004);
		RiskItemThreashode riskItemThreashode = new RiskItemThreashode();
		setObjValue(formrisk0004, riskItemThreashode);
		riskItemThreashodeFeign.insert(riskItemThreashode);
		getObjValue(formrisk0004, riskItemThreashode);
		addActionMessage(model, "保存成功");
		List<RiskItemThreashode> riskItemThreashodeList = (List<RiskItemThreashode>) riskItemThreashodeFeign
				.findByPage(getIpage(), riskItemThreashode).getResult();
		model.addAttribute("riskItemThreashodeList", riskItemThreashodeList);
		model.addAttribute("formrisk0004", formrisk0004);
		model.addAttribute("query", "");
		return "/component/risk/RiskItemThreashode_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String threashodeName, String itemNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrisk0004 = formService.getFormData("risk0004");
		getFormValue(formrisk0004);
		RiskItemThreashode riskItemThreashode = new RiskItemThreashode();
		riskItemThreashode.setThreashodeName(threashodeName);
		riskItemThreashode.setItemNo(itemNo);
		riskItemThreashode = riskItemThreashodeFeign.getById(riskItemThreashode);
		getObjValue(formrisk0004, riskItemThreashode);
		model.addAttribute("riskItemThreashode", riskItemThreashode);
		model.addAttribute("formrisk0004", formrisk0004);
		model.addAttribute("query", "");
		return "/component/risk/RiskItemThreashode_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String threashodeName, String itemNo, String threashodeIndex) throws Exception {
		ActionContext.initialize(request, response);
		RiskItemThreashode riskItemThreashode = new RiskItemThreashode();
		riskItemThreashode.setThreashodeName(threashodeName);
		riskItemThreashode.setItemNo(itemNo);
		riskItemThreashode.setThreashodeIndex(threashodeIndex);
		riskItemThreashodeFeign.delete(riskItemThreashode);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrisk0004 = formService.getFormData("risk0004");
		getFormValue(formrisk0004);
		boolean validateFlag = validateFormData(formrisk0004);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrisk0004 = formService.getFormData("risk0004");
		getFormValue(formrisk0004);
		boolean validateFlag = validateFormData(formrisk0004);
	}

}
