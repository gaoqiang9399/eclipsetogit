package app.component.eval.controller;

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

import app.component.eval.entity.EvalScenceFinVal;
import app.component.eval.feign.EvalScenceFinValFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: EvalScenceFinValAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Thu Mar 31 06:35:36 GMT 2016
 **/
@Controller
@RequestMapping("/evalScenceFinVal")
public class EvalScenceFinValController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入EvalScenceFinValBo
	@Autowired
	private EvalScenceFinValFeign evalScenceFinValFeign;
	// 全局变量
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
		return "/component/eval/EvalScenceFinVal_List";
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceFinVal evalScenceFinVal = new EvalScenceFinVal();
		try {
			evalScenceFinVal.setCustomQuery(ajaxData);// 自定义查询参数赋值
			evalScenceFinVal.setCriteriaList(evalScenceFinVal, ajaxData);// 我的筛选
			// this.getRoleConditions(evalScenceFinVal,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = evalScenceFinValFeign.findByPage(ipage, evalScenceFinVal);
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
			FormData formeval2002 = formService.getFormData("eval2002");
			getFormValue(formeval2002, getMapByJson(ajaxData));
			if (this.validateFormData(formeval2002)) {
				EvalScenceFinVal evalScenceFinVal = new EvalScenceFinVal();
				setObjValue(formeval2002, evalScenceFinVal);
				evalScenceFinValFeign.insert(evalScenceFinVal);
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
			FormData formeval2002 = formService.getFormData("eval2002");
			getFormValue(formeval2002, getMapByJson(ajaxData));
			if (this.validateFormData(formeval2002)) {
				EvalScenceFinVal evalScenceFinVal = new EvalScenceFinVal();
				setObjValue(formeval2002, evalScenceFinVal);
				evalScenceFinValFeign.update(evalScenceFinVal);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String evalScenceNo, String evalAppNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval2002 = formService.getFormData("eval2002");
		EvalScenceFinVal evalScenceFinVal = new EvalScenceFinVal();
		evalScenceFinVal.setEvalScenceNo(evalScenceNo);
		evalScenceFinVal.setEvalAppNo(evalAppNo);
		evalScenceFinVal = evalScenceFinValFeign.getById(evalScenceFinVal);
		getObjValue(formeval2002, evalScenceFinVal, formData);
		if (evalScenceFinVal != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String evalScenceNo, String evalAppNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceFinVal evalScenceFinVal = new EvalScenceFinVal();
		evalScenceFinVal.setEvalScenceNo(evalScenceNo);
		evalScenceFinVal.setEvalAppNo(evalAppNo);
		try {
			evalScenceFinValFeign.delete(evalScenceFinVal);
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
		FormData formeval2002 = formService.getFormData("eval2002");
		model.addAttribute("formeval2002", formeval2002);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceFinVal_Insert";
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
		FormData formeval2002 = formService.getFormData("eval2002");
		getFormValue(formeval2002);
		EvalScenceFinVal evalScenceFinVal = new EvalScenceFinVal();
		setObjValue(formeval2002, evalScenceFinVal);
		evalScenceFinValFeign.insert(evalScenceFinVal);
		getObjValue(formeval2002, evalScenceFinVal);
		this.addActionMessage(model, "保存成功");
		@SuppressWarnings("unchecked")
		List<EvalScenceFinVal> evalScenceFinValList = (List<EvalScenceFinVal>) evalScenceFinValFeign
				.findByPage(this.getIpage(), evalScenceFinVal).getResult();
		model.addAttribute("evalScenceFinValList", evalScenceFinValList);
		model.addAttribute("formeval2002", formeval2002);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceFinVal_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String evalScenceNo, String evalAppNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formeval2002 = formService.getFormData("eval2002");
		getFormValue(formeval2002);
		EvalScenceFinVal evalScenceFinVal = new EvalScenceFinVal();
		evalScenceFinVal.setEvalScenceNo(evalScenceNo);
		evalScenceFinVal.setEvalAppNo(evalAppNo);
		evalScenceFinVal = evalScenceFinValFeign.getById(evalScenceFinVal);
		getObjValue(formeval2002, evalScenceFinVal);
		model.addAttribute("formeval2002", formeval2002);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceFinVal_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String evalScenceNo, String evalAppNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		EvalScenceFinVal evalScenceFinVal = new EvalScenceFinVal();
		evalScenceFinVal.setEvalScenceNo(evalScenceNo);
		evalScenceFinVal.setEvalAppNo(evalAppNo);
		evalScenceFinValFeign.delete(evalScenceFinVal);
		return getListPage(model);
	}
}
