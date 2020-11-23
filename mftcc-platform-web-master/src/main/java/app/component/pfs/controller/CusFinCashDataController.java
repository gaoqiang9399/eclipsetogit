package app.component.pfs.controller;

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
import app.component.cusinterface.CusInterfaceFeign;
import app.component.pfs.entity.CusFinCashData;
import app.component.pfs.feign.CusFinCashDataFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * Title: CusFinCashDataAction.java Description:现金流量视图控制
 * 
 * @author:LJW
 * @Mon May 09 05:30:24 GMT 2016
 **/
@Controller
@RequestMapping("/cusFinCashData")
public class CusFinCashDataController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CusFinCashDataBo
	@Autowired
	private CusFinCashDataFeign cusFinCashDataFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	// 全局变量

	// 名下企业
	// 异步参数
	// 表单变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinCashData_List";
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
		CusFinCashData cusFinCashData = new CusFinCashData();
		try {
			cusFinCashData.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cusFinCashData.setCriteriaList(cusFinCashData, ajaxData);// 我的筛选
			// this.getRoleConditions(cusFinCashData,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = cusFinCashDataFeign.findByPage(ipage, cusFinCashData);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpfs0022 = formService.getFormData("pfs0022");
			getFormValue(formpfs0022, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs0022)) {
				CusFinCashData cusFinCashData = new CusFinCashData();
				setObjValue(formpfs0022, cusFinCashData);
				cusFinCashDataFeign.insert(cusFinCashData);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 保存/更新
	 * 
	 * @author LJW date 2017-4-18
	 * @param cusNo
	 * @param finRptDate
	 * @param finRptType
	 * @param accRule
	 * @param relationCorpName
	 * @param relationCorpNo
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insertOrUpdateAjax")
	@ResponseBody
	public Map<String, Object> insertOrUpdateAjax(String ajaxData, String cusNo, String finRptDate, String finRptType,
			String accRule, String relationCorpName, String relationCorpNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONArray jsonArray = JSONArray.fromObject(ajaxData);
			List<CusFinCashData> cusFinCashDataList = (List<CusFinCashData>) JSONArray.toList(jsonArray,
					new CusFinCashData(), new JsonConfig());
			CusFinCashData cusFinCashData = new CusFinCashData();
			Object obj = cusInterfaceFeign.getCusByCusNo(cusNo);
			JSONObject jb = JSONObject.fromObject(obj);
			String cusName = jb.getString("cusName");
			cusFinCashData.setCusNo(cusNo);
			cusFinCashData.setCusName(cusName);
			String finRptYear = finRptDate.substring(0, 4);
			cusFinCashData.setFinRptYear(finRptYear);
			cusFinCashData.setFinRptDate(finRptDate);
			cusFinCashData.setFinRptType(finRptType);
			cusFinCashData.setAccRule(accRule);
			cusFinCashData.setRelationCorpName(relationCorpName);
			cusFinCashData.setRelationCorpNo(relationCorpNo);
			Map<String, Object> parmMap = new HashMap<String, Object>();
			parmMap.put("cusFinCashDataList",cusFinCashDataList);
			parmMap.put("cusFinCashData",cusFinCashData);
			cusFinCashDataFeign.insertOrUpdate1(parmMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			dataMap.put("flag", "error");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
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
		FormData formpfs0022 = formService.getFormData("pfs0022");
		getFormValue(formpfs0022, getMapByJson(ajaxData));
		CusFinCashData cusFinCashDataJsp = new CusFinCashData();
		setObjValue(formpfs0022, cusFinCashDataJsp);
		CusFinCashData cusFinCashData = cusFinCashDataFeign.getById(cusFinCashDataJsp);
		if (cusFinCashData != null) {
			try {
				cusFinCashData = (CusFinCashData) EntityUtil.reflectionSetVal(cusFinCashData, cusFinCashDataJsp,
						getMapByJson(ajaxData));
				cusFinCashDataFeign.update(cusFinCashData);
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpfs0022 = formService.getFormData("pfs0022");
			getFormValue(formpfs0022, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs0022)) {
				CusFinCashData cusFinCashData = new CusFinCashData();
				setObjValue(formpfs0022, cusFinCashData);
				cusFinCashDataFeign.update(cusFinCashData);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * @param cusNo 
	 * @param finRptYear 
	 * @param codeColumn 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String cusNo, String finRptYear, String codeColumn) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpfs0022 = formService.getFormData("pfs0022");
		CusFinCashData cusFinCashData = new CusFinCashData();
		cusFinCashData.setCusNo(cusNo);
		cusFinCashData.setFinRptYear(finRptYear);
		cusFinCashData.setCodeColumn(codeColumn);
		cusFinCashData = cusFinCashDataFeign.getById(cusFinCashData);
		getObjValue(formpfs0022, cusFinCashData, formData);
		if (cusFinCashData != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * @param cusNo 
	 * @param finRptYear 
	 * @param codeColumn 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String cusNo, String finRptYear, String codeColumn) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinCashData cusFinCashData = new CusFinCashData();
		cusFinCashData.setCusNo(cusNo);
		cusFinCashData.setFinRptYear(finRptYear);
		cusFinCashData.setCodeColumn(codeColumn);
		try {
			cusFinCashDataFeign.delete(cusFinCashData);
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
		FormData formpfs0022 = formService.getFormData("pfs0022");
		model.addAttribute("formpfs0022", formpfs0022);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinCashData_Insert";
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
		FormData formpfs0022 = formService.getFormData("pfs0022");
		getFormValue(formpfs0022);
		CusFinCashData cusFinCashData = new CusFinCashData();
		setObjValue(formpfs0022, cusFinCashData);
		cusFinCashDataFeign.insert(cusFinCashData);
		getObjValue(formpfs0022, cusFinCashData);
		this.addActionMessage(model, "保存成功");
		List<CusFinCashData> cusFinCashDataList = (List<CusFinCashData>) cusFinCashDataFeign.findByPage(this.getIpage(), cusFinCashData)
				.getResult();
		model.addAttribute("formpfs0022", formpfs0022);
		model.addAttribute("cusFinCashDataList", cusFinCashDataList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinCashData_Detail";
	}

	/**
	 * 查询
	 * @param cusNo 
	 * @param finRptYear 
	 * @param codeColumn 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String cusNo, String finRptYear, String codeColumn) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs0001 = formService.getFormData("pfs0001");
		getFormValue(formpfs0001);
		CusFinCashData cusFinCashData = new CusFinCashData();
		cusFinCashData.setCusNo(cusNo);
		cusFinCashData.setFinRptYear(finRptYear);
		cusFinCashData.setCodeColumn(codeColumn);
		cusFinCashData = cusFinCashDataFeign.getById(cusFinCashData);
		getObjValue(formpfs0001, cusFinCashData);
		model.addAttribute("formpfs0001", formpfs0001);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinCashData_Detail";
	}

	/**
	 * 删除
	 * @param cusNo 
	 * @param finRptYear 
	 * @param codeColumn 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String cusNo, String finRptYear, String codeColumn) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		CusFinCashData cusFinCashData = new CusFinCashData();
		cusFinCashData.setCusNo(cusNo);
		cusFinCashData.setFinRptYear(finRptYear);
		cusFinCashData.setCodeColumn(codeColumn);
		cusFinCashDataFeign.delete(cusFinCashData);
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
		FormData formpfs0022 = formService.getFormData("pfs0022");
		getFormValue(formpfs0022);
		boolean validateFlag = this.validateFormData(formpfs0022);
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
		FormData formpfs0022 = formService.getFormData("pfs0022");
		getFormValue(formpfs0022);
		boolean validateFlag = this.validateFormData(formpfs0022);
	}

	
}
