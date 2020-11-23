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
import app.component.eval.entity.EvalScenceAdjRel;
import app.component.eval.feign.EvalScenceAdjRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: EvalScenceAdjRelController.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Thu Mar 17 06:46:26 GMT 2016
 **/
@Controller
@RequestMapping("/evalScenceAdjRel")
public class EvalScenceAdjRelController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EvalScenceAdjRelFeign evalScenceAdjRelFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/eval/EvalScenceAdjRel_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceAdjRel evalScenceAdjRel = new EvalScenceAdjRel();
		try {
			evalScenceAdjRel.setCustomQuery(ajaxData);// 自定义查询参数赋值
			evalScenceAdjRel.setCriteriaList(evalScenceAdjRel, ajaxData);// 我的筛选
			// this.getRoleConditions(evalScenceAdjRel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = evalScenceAdjRelFeign.findByPage(ipage, evalScenceAdjRel);
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
	@RequestMapping("/insertAjax")
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formeval0004 = formService.getFormData("eval0004");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formeval0004, jb);
			if (this.validateFormData(formeval0004)) {
				EvalScenceAdjRel evalScenceAdjRel = new EvalScenceAdjRel();
				setObjValue(formeval0004, evalScenceAdjRel);
				List<EvalScenceAdjRel> list = evalScenceAdjRelFeign.insert(evalScenceAdjRel);
				if (list != null && list.size() > 0) {
					dataMap.put("adjRelList", list);
				}
				dataMap.put("entityData", evalScenceAdjRel);
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval0004 = formService.getFormData("eval0004");
		getFormValue(formeval0004, getMapByJson(ajaxData));
		EvalScenceAdjRel evalScenceAdjRelJsp = new EvalScenceAdjRel();
		setObjValue(formeval0004, evalScenceAdjRelJsp);
		EvalScenceAdjRel evalScenceAdjRel = evalScenceAdjRelFeign.getById(evalScenceAdjRelJsp);
		if (evalScenceAdjRel != null) {
			try {
				evalScenceAdjRel = (EvalScenceAdjRel) EntityUtil.reflectionSetVal(evalScenceAdjRel, evalScenceAdjRelJsp,
						getMapByJson(ajaxData));
				evalScenceAdjRelFeign.update(evalScenceAdjRel);
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
	@RequestMapping("/updateAjax")
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formeval0004 = formService.getFormData("eval0004");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formeval0004, jb);
			if (this.validateFormData(formeval0004)) {
				EvalScenceAdjRel evalScenceAdjRel = new EvalScenceAdjRel();
				setObjValue(formeval0004, evalScenceAdjRel);
				evalScenceAdjRelFeign.update(evalScenceAdjRel);
				dataMap.put("entityData", evalScenceAdjRel);
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
	@RequestMapping("/getByIdAjax")
	@ResponseBody public Map<String, Object> getByIdAjax(String indexNo, String scenceNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval0004 = formService.getFormData("eval0004");
		EvalScenceAdjRel evalScenceAdjRel = new EvalScenceAdjRel();
		evalScenceAdjRel.setIndexNo(indexNo);
		evalScenceAdjRel.setScenceNo(scenceNo);
		evalScenceAdjRel = evalScenceAdjRelFeign.getById(evalScenceAdjRel);
		getObjValue(formeval0004, evalScenceAdjRel, formData);
		if (evalScenceAdjRel != null) {
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String indexNo, String scenceNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceAdjRel evalScenceAdjRel = new EvalScenceAdjRel();
		evalScenceAdjRel.setIndexNo(indexNo);
		evalScenceAdjRel.setScenceNo(scenceNo);
		try {
			evalScenceAdjRelFeign.delete(evalScenceAdjRel);
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
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formeval0004 = formService.getFormData("eval0004");
		model.addAttribute("formeval0004", formeval0004);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceAdjRel_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formeval0004 = formService.getFormData("eval0004");
		getFormValue(formeval0004);
		EvalScenceAdjRel evalScenceAdjRel = new EvalScenceAdjRel();
		setObjValue(formeval0004, evalScenceAdjRel);
		evalScenceAdjRelFeign.insert(evalScenceAdjRel);
		getObjValue(formeval0004, evalScenceAdjRel);
		this.addActionMessage(model, "保存成功");
		List<EvalScenceAdjRel> evalScenceAdjRelList = (List<EvalScenceAdjRel>) evalScenceAdjRelFeign
				.findByPage(this.getIpage(), evalScenceAdjRel).getResult();
		model.addAttribute("evalScenceAdjRelList", evalScenceAdjRelList);
		model.addAttribute("formeval0004", formeval0004);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceAdjRel_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String indexNo, String scenceNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formeval0004 = formService.getFormData("eval0004");
		getFormValue(formeval0004);
		EvalScenceAdjRel evalScenceAdjRel = new EvalScenceAdjRel();
		evalScenceAdjRel.setIndexNo(indexNo);
		evalScenceAdjRel.setScenceNo(scenceNo);
		evalScenceAdjRel = evalScenceAdjRelFeign.getById(evalScenceAdjRel);
		getObjValue(formeval0004, evalScenceAdjRel);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceAdjRel_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String indexNo, String scenceNo) throws Exception {
		ActionContext.initialize(request, response);
		EvalScenceAdjRel evalScenceAdjRel = new EvalScenceAdjRel();
		evalScenceAdjRel.setIndexNo(indexNo);
		evalScenceAdjRel.setScenceNo(scenceNo);
		evalScenceAdjRelFeign.delete(evalScenceAdjRel);
		return getListPage();
	}

}
