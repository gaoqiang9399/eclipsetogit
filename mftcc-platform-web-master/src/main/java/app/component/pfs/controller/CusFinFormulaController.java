package app.component.pfs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.cus.report.entity.MfCusReportCalc;
import app.component.cus.report.feign.MfCusReportCalcFeign;
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
import app.component.pfs.entity.CusFinFormula;
import app.component.pfs.feign.CusFinFormulaFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: CusFinFormulaAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Nov 15 14:49:36 CST 2016
 **/
@Controller
@RequestMapping("/cusFinFormula")
public class CusFinFormulaController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CusFinFormulaBo
	@Autowired
	private CusFinFormulaFeign cusFinFormulaFeign;
	@Autowired
	private MfCusReportCalcFeign mfCusReportCalcFeign;
	// 全局变量
	// 异步参数
	// 表单变量


	/**
	 * @author czk
	 * @param formulaColumn  peng-财务改
	 * @Description: 得到某报表元素所在的公式 date 2016-11-22
	 */
	@RequestMapping(value = "/checkFormulaAjaxNew")
	@ResponseBody
	public Map<String, Object> checkFormulaAjaxNew(String ajaxData, String formulaColumn) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<MfCusReportCalc> allList=new ArrayList<>();
			MfCusReportCalc mfCusReportCalc=new MfCusReportCalc();
			mfCusReportCalc.setCalcItem(formulaColumn);
			allList=mfCusReportCalcFeign.getList(mfCusReportCalc);

			dataMap.put("flag", "success");
			dataMap.put("cusFinFormulaList", allList);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
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
		model.addAttribute("query", "");
		return "/component/pfs/CusFinFormula_List";
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
		CusFinFormula cusFinFormula = new CusFinFormula();
		try {
			cusFinFormula.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cusFinFormula.setCriteriaList(cusFinFormula, ajaxData);// 我的筛选
			// this.getRoleConditions(cusFinFormula,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = cusFinFormulaFeign.findByPage(ipage, cusFinFormula);
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
	 * @author czk
	 * @param formulaColumn 
	 * @Description: 得到某报表元素所在的公式 date 2016-11-22
	 */
	@RequestMapping(value = "/checkFormulaAjax")
	@ResponseBody
	public Map<String, Object> checkFormulaAjax(String ajaxData, String formulaColumn) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			CusFinFormula cusFinFormula = new CusFinFormula();
			cusFinFormula.setFormulaColumn(formulaColumn);
			List<CusFinFormula> cusFinFormulaList = cusFinFormulaFeign.getByFormulaColumn(cusFinFormula);
			dataMap.put("flag", "success");
			dataMap.put("cusFinFormulaList", cusFinFormulaList);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
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
			FormData formfinformula0002 = formService.getFormData("finformula0002");
			getFormValue(formfinformula0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfinformula0002)) {
				CusFinFormula cusFinFormula = new CusFinFormula();
				setObjValue(formfinformula0002, cusFinFormula);
				cusFinFormulaFeign.insert(cusFinFormula);
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
		FormData formfinformula0002 = formService.getFormData("finformula0002");
		getFormValue(formfinformula0002, getMapByJson(ajaxData));
		CusFinFormula cusFinFormulaJsp = new CusFinFormula();
		setObjValue(formfinformula0002, cusFinFormulaJsp);
		CusFinFormula cusFinFormula = cusFinFormulaFeign.getById(cusFinFormulaJsp);
		if (cusFinFormula != null) {
			try {
				cusFinFormula = (CusFinFormula) EntityUtil.reflectionSetVal(cusFinFormula, cusFinFormulaJsp,
						getMapByJson(ajaxData));
				cusFinFormulaFeign.update(cusFinFormula);
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
			FormData formfinformula0002 = formService.getFormData("finformula0002");
			getFormValue(formfinformula0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfinformula0002)) {
				CusFinFormula cusFinFormula = new CusFinFormula();
				setObjValue(formfinformula0002, cusFinFormula);
				cusFinFormulaFeign.update(cusFinFormula);
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
	 * @param codeColumn 
	 * @param formulaColumn 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String codeColumn, String formulaColumn) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfinformula0002 = formService.getFormData("finformula0002");
		CusFinFormula cusFinFormula = new CusFinFormula();
		cusFinFormula.setCodeColumn(codeColumn);
		cusFinFormula.setFormulaColumn(formulaColumn);
		cusFinFormula = cusFinFormulaFeign.getById(cusFinFormula);
		getObjValue(formfinformula0002, cusFinFormula, formData);
		if (cusFinFormula != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * @param codeColumn 
	 * @param formulaColumn 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String codeColumn, String formulaColumn) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinFormula cusFinFormula = new CusFinFormula();
		cusFinFormula.setCodeColumn(codeColumn);
		cusFinFormula.setFormulaColumn(formulaColumn);
		try {
			cusFinFormulaFeign.delete(cusFinFormula);
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
		FormData formfinformula0002 = formService.getFormData("finformula0002");
		model.addAttribute("formfinformula0002", formfinformula0002);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinFormula_Insert";
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
		FormData formfinformula0002 = formService.getFormData("finformula0002");
		getFormValue(formfinformula0002);
		CusFinFormula cusFinFormula = new CusFinFormula();
		setObjValue(formfinformula0002, cusFinFormula);
		cusFinFormulaFeign.insert(cusFinFormula);
		getObjValue(formfinformula0002, cusFinFormula);
		this.addActionMessage(model, "保存成功");
		List<CusFinFormula> cusFinFormulaList = (List<CusFinFormula>) cusFinFormulaFeign.findByPage(this.getIpage(), cusFinFormula)
				.getResult();
		model.addAttribute("formfinformula0002", formfinformula0002);
		model.addAttribute("cusFinFormulaList", cusFinFormulaList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinFormula_Insert";
	}

	/**
	 * 查询
	 * @param codeColumn 
	 * @param formulaColumn 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String codeColumn, String formulaColumn) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfinformula0001 = formService.getFormData("finformula0001");
		getFormValue(formfinformula0001);
		CusFinFormula cusFinFormula = new CusFinFormula();
		cusFinFormula.setCodeColumn(codeColumn);
		cusFinFormula.setFormulaColumn(formulaColumn);
		cusFinFormula = cusFinFormulaFeign.getById(cusFinFormula);
		getObjValue(formfinformula0001, cusFinFormula);
		model.addAttribute("formfinformula0001", formfinformula0001);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinFormula_Detail";
	}

	/**
	 * 删除
	 * @param codeColumn 
	 * @param formulaColumn 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String codeColumn, String formulaColumn) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		CusFinFormula cusFinFormula = new CusFinFormula();
		cusFinFormula.setCodeColumn(codeColumn);
		cusFinFormula.setFormulaColumn(formulaColumn);
		cusFinFormulaFeign.delete(cusFinFormula);
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
		FormData formfinformula0002 = formService.getFormData("finformula0002");
		getFormValue(formfinformula0002);
		boolean validateFlag = this.validateFormData(formfinformula0002);
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
		FormData formfinformula0002 = formService.getFormData("finformula0002");
		getFormValue(formfinformula0002);
		boolean validateFlag = this.validateFormData(formfinformula0002);
	}

}
