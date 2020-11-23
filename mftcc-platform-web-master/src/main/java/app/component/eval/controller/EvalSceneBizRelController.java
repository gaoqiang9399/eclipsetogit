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

import app.base.User;
import app.component.common.EntityUtil;
import app.component.eval.entity.EvalSceneBizRel;
import app.component.eval.feign.EvalSceneBizRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: EvalSceneBizRelAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Fri Sep 02 05:37:40 GMT 2016
 **/
@Controller
@RequestMapping("/evalSceneBizRel")
public class EvalSceneBizRelController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入EvalSceneBizRelBo
	@Autowired
	private EvalSceneBizRelFeign evalSceneBizRelFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/eval/EvalSceneBizRel_List";
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
		EvalSceneBizRel evalSceneBizRel = new EvalSceneBizRel();
		try {
			evalSceneBizRel.setCustomQuery(ajaxData);// 自定义查询参数赋值
			evalSceneBizRel.setCriteriaList(evalSceneBizRel, ajaxData);// 我的筛选
			// this.getRoleConditions(evalSceneBizRel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = evalSceneBizRelFeign.findByPage(ipage, evalSceneBizRel);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formeval4002 = formService.getFormData("eval4002");
			getFormValue(formeval4002, getMapByJson(ajaxData));
			if (this.validateFormData(formeval4002)) {
				EvalSceneBizRel evalSceneBizRel = new EvalSceneBizRel();
				setObjValue(formeval4002, evalSceneBizRel);
				evalSceneBizRelFeign.insert(evalSceneBizRel);
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
		FormData formeval4002 = formService.getFormData("eval4002");
		getFormValue(formeval4002, getMapByJson(ajaxData));
		EvalSceneBizRel evalSceneBizRelJsp = new EvalSceneBizRel();
		setObjValue(formeval4002, evalSceneBizRelJsp);
		EvalSceneBizRel evalSceneBizRel = evalSceneBizRelFeign.getById(evalSceneBizRelJsp);
		if (evalSceneBizRel != null) {
			try {
				evalSceneBizRel = (EvalSceneBizRel) EntityUtil.reflectionSetVal(evalSceneBizRel, evalSceneBizRelJsp,
						getMapByJson(ajaxData));
				evalSceneBizRelFeign.update(evalSceneBizRel);
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
			FormData formeval4002 = formService.getFormData("eval4002");
			getFormValue(formeval4002, getMapByJson(ajaxData));
			if (this.validateFormData(formeval4002)) {
				EvalSceneBizRel evalSceneBizRel = new EvalSceneBizRel();
				setObjValue(formeval4002, evalSceneBizRel);
				evalSceneBizRelFeign.update(evalSceneBizRel);
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
	public Map<String, Object> getByIdAjax(String ajaxData,String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formeval4002 = formService.getFormData("eval4002");
		EvalSceneBizRel evalSceneBizRel = new EvalSceneBizRel();
		evalSceneBizRel.setRelNo(relNo);
		evalSceneBizRel = evalSceneBizRelFeign.getById(evalSceneBizRel);
		getObjValue(formeval4002, evalSceneBizRel, formData);
		if (evalSceneBizRel != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String relNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		EvalSceneBizRel evalSceneBizRel = new EvalSceneBizRel();
		evalSceneBizRel.setRelNo(relNo);
		try {
			evalSceneBizRelFeign.delete(evalSceneBizRel);
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
		FormData formeval4002 = formService.getFormData("eval4002");
		EvalSceneBizRel evalSceneBizRel = new EvalSceneBizRel();
		evalSceneBizRel.setOrgName(User.getOrgName(request));
		evalSceneBizRel.setRegName(User.getRegName(request));
		evalSceneBizRel.setRegDate(User.getSysDate(this.getHttpRequest()));
		evalSceneBizRel.setLstRegName(User.getRegName(request));
		evalSceneBizRel.setLstDate(User.getSysDate(this.getHttpRequest()));
		getObjValue(formeval4002, evalSceneBizRel);
		model.addAttribute("formeval4002", formeval4002);
		model.addAttribute("query", "");
		return "/component/eval/EvalSceneBizRel_Update";
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
		FormData formeval4002 = formService.getFormData("eval4002");
		getFormValue(formeval4002);
		EvalSceneBizRel evalSceneBizRel = new EvalSceneBizRel();
		setObjValue(formeval4002, evalSceneBizRel);
		evalSceneBizRelFeign.insert(evalSceneBizRel);
		getObjValue(formeval4002, evalSceneBizRel);
		this.addActionMessage(model, "保存成功");
		List<EvalSceneBizRel> evalSceneBizRelList = (List<EvalSceneBizRel>) evalSceneBizRelFeign.findByPage(this.getIpage(), evalSceneBizRel)
				.getResult();
		model.addAttribute("formeval4002", formeval4002);
		model.addAttribute("evalSceneBizRelList", evalSceneBizRelList);
		model.addAttribute("query", "");
		return "/component/eval/EvalSceneBizRel_Update";
	}

	/**
	 * 获取更新页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePage")
	public String updatePage(Model model, String ajaxData,String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formeval4002 = formService.getFormData("eval4002");
		getFormValue(formeval4002);
		EvalSceneBizRel evalSceneBizRel = new EvalSceneBizRel();
		evalSceneBizRel.setRelNo(relNo);
		evalSceneBizRel = evalSceneBizRelFeign.getById(evalSceneBizRel);
		getObjValue(formeval4002, evalSceneBizRel);
		model.addAttribute("formeval0001", formeval4002);
		model.addAttribute("query", "");
		return "/component/eval/EvalSceneBizRel_Update";
	}

	/**
	 * 获取更新页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public String update(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formeval4002 = formService.getFormData("eval4002");
		getFormValue(formeval4002);
		EvalSceneBizRel evalSceneBizRel = new EvalSceneBizRel();
		setObjValue(formeval4002, evalSceneBizRel);
		evalSceneBizRelFeign.update(evalSceneBizRel);
		getObjValue(formeval4002, evalSceneBizRel);
		this.addActionMessage(model, "保存成功");
		model.addAttribute("formeval4002", formeval4002);
		model.addAttribute("query", "");
		return "/component/eval/EvalSceneBizRel_Update";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData,String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formeval4002 = formService.getFormData("eval4002");
		getFormValue(formeval4002);
		EvalSceneBizRel evalSceneBizRel = new EvalSceneBizRel();
		evalSceneBizRel.setRelNo(relNo);
		evalSceneBizRel = evalSceneBizRelFeign.getById(evalSceneBizRel);
		getObjValue(formeval4002, evalSceneBizRel);
		model.addAttribute("formeval4002", formeval4002);
		model.addAttribute("query", "");
		return "/component/eval/EvalSceneBizRel_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData,String relNo) throws Exception {
		ActionContext.initialize(request, response);
		EvalSceneBizRel evalSceneBizRel = new EvalSceneBizRel();
		evalSceneBizRel.setRelNo(relNo);
		evalSceneBizRelFeign.delete(evalSceneBizRel);
		return getListPage(model);
	}


}
