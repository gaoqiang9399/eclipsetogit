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
import app.component.pfs.entity.CusFinCapData;
import app.component.pfs.feign.CusFinCapDataFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * Title: CusFinCapDataAction.java Description:资产负债表视图层控制
 * 
 * @author:LJW
 * @Mon May 09 05:27:05 GMT 2016
 **/
@Controller
@RequestMapping("/cusFinCapData")
public class CusFinCapDataController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CusFinCapDataBo
	@Autowired
	private CusFinCapDataFeign cusFinCapDataFeign;
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
		return "/component/pfs/CusFinCapData_List";
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
		CusFinCapData cusFinCapData = new CusFinCapData();
		try {
			cusFinCapData.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cusFinCapData.setCriteriaList(cusFinCapData, ajaxData);// 我的筛选
			// this.getRoleConditions(cusFinCapData,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = cusFinCapDataFeign.findByPage(ipage, cusFinCapData);
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
			FormData formpfs0011 = formService.getFormData("pfs0011");
			getFormValue(formpfs0011, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs0011)) {
				CusFinCapData cusFinCapData = new CusFinCapData();
				setObjValue(formpfs0011, cusFinCapData);
				cusFinCapDataFeign.insert(cusFinCapData);
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
	 * @param finRptDate
	 * @param ajaxData
	 * @param finRptType
	 * @param relationCorpName
	 * @param relationCorpNo
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insertOrUpdateAjax")
	@ResponseBody
	public Map<String, Object> insertOrUpdateAjax(String cusNo, String finRptDate, String ajaxData, String finRptType,
			String relationCorpName, String relationCorpNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONArray jsonArray1 = JSONArray.fromObject(ajaxData);
			List<CusFinCapData> cusFinCapDataList = (List<CusFinCapData>) JSONArray.toList(jsonArray1,
					new CusFinCapData(), new JsonConfig());
			CusFinCapData cusFinCapData = new CusFinCapData();
			Object obj = cusInterfaceFeign.getCusByCusNo(cusNo);
			JSONObject jb = JSONObject.fromObject(obj);
			String cusName = jb.getString("cusName");
			String finRptYear = finRptDate.substring(0, 4);
			cusFinCapData.setCusNo(cusNo);
			cusFinCapData.setCusName(cusName);
			cusFinCapData.setFinRptYear(finRptYear);
			cusFinCapData.setFinRptType(finRptType);
			cusFinCapData.setFinRptDate(finRptDate);
			cusFinCapData.setRelationCorpName(relationCorpName);
			cusFinCapData.setRelationCorpNo(relationCorpNo);
			String accRule = "1";
			cusFinCapData.setAccRule(accRule);
			Map<String, Object> parmMap = new HashMap<String, Object>();
			parmMap.put("cusFinCapDataList",cusFinCapDataList);
			parmMap.put("cusFinCapData",cusFinCapData);
			cusFinCapDataFeign.insertOrUpdate1(parmMap);
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
		FormData formpfs0011 = formService.getFormData("pfs0011");
		getFormValue(formpfs0011, getMapByJson(ajaxData));
		CusFinCapData cusFinCapDataJsp = new CusFinCapData();
		setObjValue(formpfs0011, cusFinCapDataJsp);
		CusFinCapData cusFinCapData = cusFinCapDataFeign.getById(cusFinCapDataJsp);
		if (cusFinCapData != null) {
			try {
				cusFinCapData = (CusFinCapData) EntityUtil.reflectionSetVal(cusFinCapData, cusFinCapDataJsp,
						getMapByJson(ajaxData));
				cusFinCapDataFeign.update(cusFinCapData);
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
			FormData formpfs0011 = formService.getFormData("pfs0011");
			getFormValue(formpfs0011, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs0011)) {
				CusFinCapData cusFinCapData = new CusFinCapData();
				setObjValue(formpfs0011, cusFinCapData);
				cusFinCapDataFeign.update(cusFinCapData);
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
	 * @param codeColumn 
	 * @param finRptYear 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String cusNo, String codeColumn, String finRptYear) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpfs0011 = formService.getFormData("pfs0011");
		CusFinCapData cusFinCapData = new CusFinCapData();
		cusFinCapData.setCusNo(cusNo);
		cusFinCapData.setFinRptYear(finRptYear);
		cusFinCapData.setCodeColumn(codeColumn);
		cusFinCapData = cusFinCapDataFeign.getById(cusFinCapData);
		getObjValue(formpfs0011, cusFinCapData, formData);
		if (cusFinCapData != null) {
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
		CusFinCapData cusFinCapData = new CusFinCapData();
		cusFinCapData.setCusNo(cusNo);
		cusFinCapData.setFinRptYear(finRptYear);
		cusFinCapData.setCodeColumn(codeColumn);
		try {
			cusFinCapDataFeign.delete(cusFinCapData);
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
		FormData formpfs0011 = formService.getFormData("pfs0011");
		model.addAttribute("formpfs0011", formpfs0011);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinCapData_Insert";
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
		FormData formpfs0011 = formService.getFormData("pfs0011");
		getFormValue(formpfs0011);
		CusFinCapData cusFinCapData = new CusFinCapData();
		setObjValue(formpfs0011, cusFinCapData);
		cusFinCapDataFeign.insert(cusFinCapData);
		getObjValue(formpfs0011, cusFinCapData);
		this.addActionMessage(model, "保存成功");
		List<CusFinCapData> cusFinCapDataList = (List<CusFinCapData>) cusFinCapDataFeign.findByPage(this.getIpage(), cusFinCapData)
				.getResult();
		model.addAttribute("formpfs0011", formpfs0011);
		model.addAttribute("cusFinCapDataList", cusFinCapDataList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinCapData_Detail";
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
		FormData formpfs0000 = formService.getFormData("pfs0000");
		getFormValue(formpfs0000);
		CusFinCapData cusFinCapData = new CusFinCapData();
		cusFinCapData.setCusNo(cusNo);
		cusFinCapData.setFinRptYear(finRptYear);
		cusFinCapData.setCodeColumn(codeColumn);
		cusFinCapData = cusFinCapDataFeign.getById(cusFinCapData);
		getObjValue(formpfs0000, cusFinCapData);
		model.addAttribute("formpfs0000", formpfs0000);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinCapData_Detail";
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
		CusFinCapData cusFinCapData = new CusFinCapData();
		cusFinCapData.setCusNo(cusNo);
		cusFinCapData.setFinRptYear(finRptYear);
		cusFinCapData.setCodeColumn(codeColumn);
		cusFinCapDataFeign.delete(cusFinCapData);
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
		FormData formpfs0011 = formService.getFormData("pfs0011");
		getFormValue(formpfs0011);
		boolean validateFlag = this.validateFormData(formpfs0011);
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
		FormData formpfs0011 = formService.getFormData("pfs0011");
		getFormValue(formpfs0011);
		boolean validateFlag = this.validateFormData(formpfs0011);
	}

}
