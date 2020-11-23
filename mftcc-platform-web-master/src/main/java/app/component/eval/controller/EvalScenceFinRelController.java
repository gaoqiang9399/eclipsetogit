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

import app.component.common.EntityUtil;
import app.component.eval.entity.EvalScenceFinRel;
import app.component.eval.feign.EvalScenceFinRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: EvalScenceFinRelAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Thu Mar 17 06:44:16 GMT 2016
 **/
@Controller
@RequestMapping("/evalScenceFinRel")
public class EvalScenceFinRelController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入EvalScenceFinRelBo
	@Autowired
	private EvalScenceFinRelFeign evalScenceFinRelFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/eval/EvalScenceFinRel_List";
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
		EvalScenceFinRel evalScenceFinRel = new EvalScenceFinRel();
		try {
			evalScenceFinRel.setCustomQuery(ajaxData);// 自定义查询参数赋值
			evalScenceFinRel.setCriteriaList(evalScenceFinRel, ajaxData);// 我的筛选
			// this.getRoleConditions(evalScenceFinRel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = evalScenceFinRelFeign.findByPage(ipage, evalScenceFinRel);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
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
			FormData formeval0002 = formService.getFormData("eval0002");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formeval0002, jb);
			if (this.validateFormData(formeval0002)) {
				EvalScenceFinRel evalScenceFinRel = new EvalScenceFinRel();
				setObjValue(formeval0002, evalScenceFinRel);
				String finCode = evalScenceFinRel.getFinCode();
				String scenceNo = evalScenceFinRel.getScenceNo();
				if (finCode != null && !"".endsWith(finCode)
						&& evalScenceFinRelFeign.getByFinCode(scenceNo, finCode).size() > 0) {
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage("该财务指标"));
				} else {
					evalScenceFinRel = evalScenceFinRelFeign.insert(evalScenceFinRel);
					dataMap.put("entityData", evalScenceFinRel);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}
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
		FormData formeval0002 = formService.getFormData("eval0002");
		getFormValue(formeval0002, getMapByJson(ajaxData));
		EvalScenceFinRel evalScenceFinRelJsp = new EvalScenceFinRel();
		setObjValue(formeval0002, evalScenceFinRelJsp);
		EvalScenceFinRel evalScenceFinRel = evalScenceFinRelFeign.getById(evalScenceFinRelJsp);
		if (evalScenceFinRel != null) {
			try {
				evalScenceFinRel = (EvalScenceFinRel) EntityUtil.reflectionSetVal(evalScenceFinRel, evalScenceFinRelJsp,
						getMapByJson(ajaxData));
				evalScenceFinRelFeign.update(evalScenceFinRel);
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
			FormData formeval0002 = formService.getFormData("eval0002");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formeval0002, jb);
			if (this.validateFormData(formeval0002)) {
				EvalScenceFinRel evalScenceFinRel = new EvalScenceFinRel();
				setObjValue(formeval0002, evalScenceFinRel);
				String finCode = evalScenceFinRel.getFinCode();
				String scenceNo = evalScenceFinRel.getScenceNo();
				boolean existsFlag = true;
				if (finCode != null && !"".equals(finCode)) {
					List<EvalScenceFinRel> list = evalScenceFinRelFeign.getByFinCode(scenceNo, finCode);
					if (list.size() > 0 && !evalScenceFinRel.getIndexNo().equals(list.get(0).getIndexNo())) {
						dataMap.put("flag", "error");
						dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage("该财务指标"));
						existsFlag = false;
					}
				}
				if (existsFlag) {
					evalScenceFinRelFeign.update(evalScenceFinRel);
					dataMap.put("entityData", evalScenceFinRel);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				}
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
	public Map<String, Object> getByIdAjax(String ajaxData, String indexNo, String scenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval0002 = formService.getFormData("eval0002");
		EvalScenceFinRel evalScenceFinRel = new EvalScenceFinRel();
		evalScenceFinRel.setIndexNo(indexNo);
		evalScenceFinRel.setScenceNo(scenceNo);
		evalScenceFinRel = evalScenceFinRelFeign.getById(evalScenceFinRel);
		getObjValue(formeval0002, evalScenceFinRel, formData);
		if (evalScenceFinRel != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String indexNo, String scenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceFinRel evalScenceFinRel = new EvalScenceFinRel();
		evalScenceFinRel.setIndexNo(indexNo);
		evalScenceFinRel.setScenceNo(scenceNo);
		try {
			evalScenceFinRelFeign.delete(evalScenceFinRel);
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
		FormData formeval0002 = formService.getFormData("eval0002");
		model.addAttribute("formeval0002", formeval0002);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceFinRel_Insert";
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
		FormData formeval0002 = formService.getFormData("eval0002");
		getFormValue(formeval0002);
		EvalScenceFinRel evalScenceFinRel = new EvalScenceFinRel();
		setObjValue(formeval0002, evalScenceFinRel);
		evalScenceFinRelFeign.insert(evalScenceFinRel);
		getObjValue(formeval0002, evalScenceFinRel);
		this.addActionMessage(model, "保存成功");
		List<EvalScenceFinRel> evalScenceFinRelList = (List<EvalScenceFinRel>) evalScenceFinRelFeign
				.findByPage(this.getIpage(), evalScenceFinRel).getResult();
		model.addAttribute("evalScenceFinRelList", evalScenceFinRelList);
		model.addAttribute("formeval0002", formeval0002);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceFinRel_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String indexNo, String scenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formeval0002 = formService.getFormData("eval0002");
		getFormValue(formeval0002);
		EvalScenceFinRel evalScenceFinRel = new EvalScenceFinRel();
		evalScenceFinRel.setIndexNo(indexNo);
		evalScenceFinRel.setScenceNo(scenceNo);
		evalScenceFinRel = evalScenceFinRelFeign.getById(evalScenceFinRel);
		getObjValue(formeval0002, evalScenceFinRel);
		model.addAttribute("formeval0002", formeval0002);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceFinRel_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String indexNo, String scenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		EvalScenceFinRel evalScenceFinRel = new EvalScenceFinRel();
		evalScenceFinRel.setIndexNo(indexNo);
		evalScenceFinRel.setScenceNo(scenceNo);
		evalScenceFinRelFeign.delete(evalScenceFinRel);
		return getListPage(model);
	}
}
