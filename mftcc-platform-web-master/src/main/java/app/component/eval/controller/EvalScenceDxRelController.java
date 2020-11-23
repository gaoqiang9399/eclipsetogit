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
import app.component.eval.entity.EvalScenceDxRel;
import app.component.eval.feign.EvalScenceDxRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: EvalScenceDxRelAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Thu Mar 17 06:45:25 GMT 2016
 **/
@Controller
@RequestMapping("/evalScenceDxRel")
public class EvalScenceDxRelController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入EvalScenceDxRelBo
	@Autowired
	private EvalScenceDxRelFeign evalScenceDxRelFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		return "/component/eval/EvalScenceDxRel_List";
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
		EvalScenceDxRel evalScenceDxRel = new EvalScenceDxRel();
		try {
			evalScenceDxRel.setCustomQuery(ajaxData);// 自定义查询参数赋值
			evalScenceDxRel.setCriteriaList(evalScenceDxRel, ajaxData);// 我的筛选
			// this.getRoleConditions(evalScenceDxRel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = evalScenceDxRelFeign.findByPage(ipage, evalScenceDxRel);
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
			FormData formeval0003 = formService.getFormData("eval0003");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formeval0003, jb);
			if (this.validateFormData(formeval0003)) {
				EvalScenceDxRel evalScenceDxRel = new EvalScenceDxRel();
				setObjValue(formeval0003, evalScenceDxRel);
				List<EvalScenceDxRel> list = evalScenceDxRelFeign.insert(evalScenceDxRel);
				if (list != null && list.size() > 0) {
					dataMap.put("dxRelList", list);
				}
				dataMap.put("entityData", evalScenceDxRel);
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
		FormData formeval0003 = formService.getFormData("eval0003");
		getFormValue(formeval0003, getMapByJson(ajaxData));
		EvalScenceDxRel evalScenceDxRelJsp = new EvalScenceDxRel();
		setObjValue(formeval0003, evalScenceDxRelJsp);
		EvalScenceDxRel evalScenceDxRel = evalScenceDxRelFeign.getById(evalScenceDxRelJsp);
		if (evalScenceDxRel != null) {
			try {
				evalScenceDxRel = (EvalScenceDxRel) EntityUtil.reflectionSetVal(evalScenceDxRel, evalScenceDxRelJsp,
						getMapByJson(ajaxData));
				evalScenceDxRelFeign.update(evalScenceDxRel);
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
			FormData formeval0003 = formService.getFormData("eval0003");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formeval0003, jb);
			if (this.validateFormData(formeval0003)) {
				EvalScenceDxRel evalScenceDxRel = new EvalScenceDxRel();
				setObjValue(formeval0003, evalScenceDxRel);
				evalScenceDxRelFeign.update(evalScenceDxRel);
				dataMap.put("entityData", evalScenceDxRel);
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
	public Map<String, Object> getByIdAjax(String ajaxData, String indexNo, String scenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval0003 = formService.getFormData("eval0003");
		EvalScenceDxRel evalScenceDxRel = new EvalScenceDxRel();
		evalScenceDxRel.setIndexNo(indexNo);
		evalScenceDxRel.setScenceNo(scenceNo);
		evalScenceDxRel = evalScenceDxRelFeign.getById(evalScenceDxRel);
		getObjValue(formeval0003, evalScenceDxRel, formData);
		if (evalScenceDxRel != null) {
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalScenceDxRel evalScenceDxRel = new EvalScenceDxRel();
		evalScenceDxRel.setIndexNo(indexNo);
		evalScenceDxRel.setScenceNo(scenceNo);
		try {
			if (evalScenceDxRelFeign.getById(evalScenceDxRel) != null) {
				evalScenceDxRelFeign.delete(evalScenceDxRel);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
			}
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
		FormData formeval0003 = formService.getFormData("eval0003");
		model.addAttribute("formeval0003", formeval0003);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceDxRel_Insert";
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
		FormData formeval0003 = formService.getFormData("eval0003");
		getFormValue(formeval0003);
		EvalScenceDxRel evalScenceDxRel = new EvalScenceDxRel();
		setObjValue(formeval0003, evalScenceDxRel);
		evalScenceDxRelFeign.insert(evalScenceDxRel);
		getObjValue(formeval0003, evalScenceDxRel);
		this.addActionMessage(model, "保存成功");
		List<EvalScenceDxRel> evalScenceDxRelList = (List<EvalScenceDxRel>) evalScenceDxRelFeign
				.findByPage(this.getIpage(), evalScenceDxRel).getResult();
		model.addAttribute("evalScenceDxRelList", evalScenceDxRelList);
		model.addAttribute("formeval0003", formeval0003);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceDxRel_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String indexNo, String scenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formeval0003 = formService.getFormData("eval0003");
		getFormValue(formeval0003);
		EvalScenceDxRel evalScenceDxRel = new EvalScenceDxRel();
		evalScenceDxRel.setIndexNo(indexNo);
		evalScenceDxRel.setScenceNo(scenceNo);
		evalScenceDxRel = evalScenceDxRelFeign.getById(evalScenceDxRel);
		getObjValue(formeval0003, evalScenceDxRel);
		model.addAttribute("formeval0003", formeval0003);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceDxRel_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String indexNo, String scenceNo) throws Exception {
		ActionContext.initialize(request, response);
		EvalScenceDxRel evalScenceDxRel = new EvalScenceDxRel();
		evalScenceDxRel.setIndexNo(indexNo);
		evalScenceDxRel.setScenceNo(scenceNo);
		evalScenceDxRelFeign.delete(evalScenceDxRel);
		return getListPage();
	}


}
