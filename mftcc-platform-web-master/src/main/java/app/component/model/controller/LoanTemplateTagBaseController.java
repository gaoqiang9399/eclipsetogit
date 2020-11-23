package app.component.model.controller;

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
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.model.entity.LoanTemplateTagBase;
import app.component.model.feign.LoanTemplateTagBaseFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: LoanTemplateTagBaseAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Sep 05 18:26:08 CST 2016
 **/
@Controller
@RequestMapping("/loanTemplateTagBase")
public class LoanTemplateTagBaseController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入LoanTemplateTagBaseBo
	@Autowired
	private LoanTemplateTagBaseFeign loanTemplateTagBaseFeign;
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
		return "/component/model/LoanTemplateTagBase_List";
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
		LoanTemplateTagBase loanTemplateTagBase = new LoanTemplateTagBase();
		try {
			loanTemplateTagBase.setCustomQuery(ajaxData);// 自定义查询参数赋值
			loanTemplateTagBase.setCriteriaList(loanTemplateTagBase, ajaxData);// 我的筛选
			// this.getRoleConditions(loanTemplateTagBase,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = loanTemplateTagBaseFeign.findByPage(ipage, loanTemplateTagBase);
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
	@RequestMapping(value = "/getTagBaseList")
	@ResponseBody
	public List<LoanTemplateTagBase> getTagBaseList() throws Exception {
		ArrayList<LoanTemplateTagBase> loanTemplateTagBaseList = new ArrayList<LoanTemplateTagBase>();
		LoanTemplateTagBase loanTemplateTagBase = new LoanTemplateTagBase();
		try {
			loanTemplateTagBaseList = (ArrayList<LoanTemplateTagBase>) loanTemplateTagBaseFeign.getTagBaseList(loanTemplateTagBase);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> dataMap;
//			dataMap.put("flag", "error");
//			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return loanTemplateTagBaseList;
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
			FormData formtagbase0002 = formService.getFormData("tagbase0002");
			getFormValue(formtagbase0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtagbase0002)) {
				LoanTemplateTagBase loanTemplateTagBase = new LoanTemplateTagBase();
				setObjValue(formtagbase0002, loanTemplateTagBase);
				loanTemplateTagBaseFeign.insert(loanTemplateTagBase);
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
		FormData formtagbase0002 = formService.getFormData("tagbase0002");
		getFormValue(formtagbase0002, getMapByJson(ajaxData));
		LoanTemplateTagBase loanTemplateTagBaseJsp = new LoanTemplateTagBase();
		setObjValue(formtagbase0002, loanTemplateTagBaseJsp);
		LoanTemplateTagBase loanTemplateTagBase = loanTemplateTagBaseFeign.getById(loanTemplateTagBaseJsp);
		if (loanTemplateTagBase != null) {
			try {
				loanTemplateTagBase = (LoanTemplateTagBase) EntityUtil.reflectionSetVal(loanTemplateTagBase,
						loanTemplateTagBaseJsp, getMapByJson(ajaxData));
				loanTemplateTagBaseFeign.update(loanTemplateTagBase);
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
			FormData formtagbase0002 = formService.getFormData("tagbase0002");
			getFormValue(formtagbase0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtagbase0002)) {
				LoanTemplateTagBase loanTemplateTagBase = new LoanTemplateTagBase();
				setObjValue(formtagbase0002, loanTemplateTagBase);
				loanTemplateTagBaseFeign.update(loanTemplateTagBase);
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
	public Map<String, Object> getByIdAjax(String serialNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtagbase0002 = formService.getFormData("tagbase0002");
		LoanTemplateTagBase loanTemplateTagBase = new LoanTemplateTagBase();
		loanTemplateTagBase.setSerialNo(serialNo);
		loanTemplateTagBase = loanTemplateTagBaseFeign.getById(loanTemplateTagBase);
		getObjValue(formtagbase0002, loanTemplateTagBase, formData);
		if (loanTemplateTagBase != null) {
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
	public Map<String, Object> deleteAjax(String serialNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		LoanTemplateTagBase loanTemplateTagBase = new LoanTemplateTagBase();
		loanTemplateTagBase.setSerialNo(serialNo);
		try {
			loanTemplateTagBaseFeign.delete(loanTemplateTagBase);
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
		FormData formtagbase0002 = formService.getFormData("tagbase0002");
		model.addAttribute("formtagbase0002", formtagbase0002);
		model.addAttribute("query", "");
		return "/component/model/LoanTemplateTagBase_Insert";
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
		FormData formtagbase0002 = formService.getFormData("tagbase0002");
		getFormValue(formtagbase0002);
		LoanTemplateTagBase loanTemplateTagBase = new LoanTemplateTagBase();
		setObjValue(formtagbase0002, loanTemplateTagBase);
		loanTemplateTagBaseFeign.insert(loanTemplateTagBase);
		getObjValue(formtagbase0002, loanTemplateTagBase);
		this.addActionMessage(model, "保存成功");
		List<LoanTemplateTagBase> loanTemplateTagBaseList = (List<LoanTemplateTagBase>) loanTemplateTagBaseFeign
				.findByPage(this.getIpage(), loanTemplateTagBase).getResult();
		model.addAttribute("formtagbase0002", formtagbase0002);
		model.addAttribute("loanTemplateTagBaseList", loanTemplateTagBaseList);
		model.addAttribute("query", "");
		return "/component/model/LoanTemplateTagBase_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String serialNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formtagbase0002 = formService.getFormData("tagbase0001");
		getFormValue(formtagbase0002);
		LoanTemplateTagBase loanTemplateTagBase = new LoanTemplateTagBase();
		loanTemplateTagBase.setSerialNo(serialNo);
		loanTemplateTagBase = loanTemplateTagBaseFeign.getById(loanTemplateTagBase);
		getObjValue(formtagbase0002, loanTemplateTagBase);
		model.addAttribute("formtagbase0002", formtagbase0002);
		model.addAttribute("query", "");
		return "/component/model/LoanTemplateTagBase_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String serialNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		LoanTemplateTagBase loanTemplateTagBase = new LoanTemplateTagBase();
		loanTemplateTagBase.setSerialNo(serialNo);
		loanTemplateTagBaseFeign.delete(loanTemplateTagBase);
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
		FormData formtagbase0002 = formService.getFormData("tagbase0002");
		getFormValue(formtagbase0002);
		boolean validateFlag = this.validateFormData(formtagbase0002);
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
		FormData formtagbase0002 = formService.getFormData("tagbase0002");
		getFormValue(formtagbase0002);
		boolean validateFlag = this.validateFormData(formtagbase0002);
	}

}
