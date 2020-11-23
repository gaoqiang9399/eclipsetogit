package app.component.eval.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.eval.entity.EvalSysAssess;
import app.component.eval.feign.EvalSysAssessFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: EvalSysAssessAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Jul 26 02:04:05 GMT 2016
 **/
@Controller
@RequestMapping("/evalSysAssess")
public class EvalSysAssessController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入EvalSysAssessBo
	@Autowired
	private EvalSysAssessFeign evalSysAssessFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/eval/EvalSysAssess_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalSysAssess evalSysAssess = new EvalSysAssess();
		try {
			evalSysAssess.setCustomQuery(ajaxData);// 自定义查询参数赋值
			evalSysAssess.setCriteriaList(evalSysAssess, ajaxData);// 我的筛选
			// this.getRoleConditions(evalSysAssess,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = evalSysAssessFeign.findByPage(ipage, evalSysAssess);
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
			FormData formeval4001 = formService.getFormData("eval4001");
			getFormValue(formeval4001, getMapByJson(ajaxData));
			if (this.validateFormData(formeval4001)) {
				EvalSysAssess evalSysAssess = new EvalSysAssess();
				setObjValue(formeval4001, evalSysAssess);
				evalSysAssessFeign.insert(evalSysAssess);
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
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formeval4001 = formService.getFormData("eval4001");
			getFormValue(formeval4001, getMapByJson(ajaxData));
			if (this.validateFormData(formeval4001)) {
				EvalSysAssess evalSysAssess = new EvalSysAssess();
				setObjValue(formeval4001, evalSysAssess);
				evalSysAssessFeign.update(evalSysAssess);
				List<EvalSysAssess> list = new ArrayList<EvalSysAssess>();
				list.add(evalSysAssess);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", list, null, true);
				dataMap.put("tableData", tableHtml);
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
	public Map<String, Object> getByIdAjax(String ajaxData,String evalLevel) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval4001 = formService.getFormData("eval4001");
		EvalSysAssess evalSysAssess = new EvalSysAssess();
		evalSysAssess.setEvalLevel(evalLevel);
		evalSysAssess = evalSysAssessFeign.getById(evalSysAssess);
		getObjValue(formeval4001, evalSysAssess, formData);
		if (evalSysAssess != null) {
			JsonFormUtil jfu = new JsonFormUtil();
			this.changeFormProperty(formeval4001, "evalLevel", "readonly", "1");
			String formHtml = jfu.getJsonStr(formeval4001, "bigFormTag", "");
			dataMap.put("formHtml", formHtml);
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
	public Map<String, Object> deleteAjax(String ajaxData,String evalLevel) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalSysAssess evalSysAssess = new EvalSysAssess();
		evalSysAssess.setEvalLevel(evalLevel);
		try {
			evalSysAssessFeign.delete(evalSysAssess);
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

	@RequestMapping(value = "/inputAjax")
	@ResponseBody
	public Map<String, Object> inputAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalSysAssess evalSysAssess = new EvalSysAssess();
		FormData formeval4001 = formService.getFormData("eval4001");
		getObjValue(formeval4001, evalSysAssess);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formeval4001, "bigFormTag", "");
		dataMap.put("formHtml", formHtml);
		dataMap.put("flag", "success");
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
		FormData formeval4001 = formService.getFormData("eval4001");
		model.addAttribute("formeval4001", formeval4001);
		model.addAttribute("query", "");
		return "/component/eval/EvalSysAssess_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formeval4001 = formService.getFormData("eval4001");
		getFormValue(formeval4001);
		EvalSysAssess evalSysAssess = new EvalSysAssess();
		setObjValue(formeval4001, evalSysAssess);
		evalSysAssessFeign.insert(evalSysAssess);
		getObjValue(formeval4001, evalSysAssess);
		this.addActionMessage(model, "保存成功");
		List<EvalSysAssess> evalSysAssessList = (List<EvalSysAssess>) evalSysAssessFeign.findByPage(this.getIpage(), evalSysAssess)
				.getResult();
		model.addAttribute("evalSysAssessList", evalSysAssessList);
		model.addAttribute("formeval4001", formeval4001);
		model.addAttribute("query", "");
		return "/component/eval/EvalSysAssess_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData,String evalLevel) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formeval4001 = formService.getFormData("eval4001");
		getFormValue(formeval4001);
		EvalSysAssess evalSysAssess = new EvalSysAssess();
		evalSysAssess.setEvalLevel(evalLevel);
		evalSysAssess = evalSysAssessFeign.getById(evalSysAssess);
		getObjValue(formeval4001, evalSysAssess);
		model.addAttribute("formeval4001", formeval4001);
		model.addAttribute("query", "");
		return "/component/eval/EvalSysAssess_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData,String evalLevel) throws Exception {
		ActionContext.initialize(request, response);
		EvalSysAssess evalSysAssess = new EvalSysAssess();
		evalSysAssess.setEvalLevel(evalLevel);
		evalSysAssessFeign.delete(evalSysAssess);
		return getListPage(model);
	}


}
