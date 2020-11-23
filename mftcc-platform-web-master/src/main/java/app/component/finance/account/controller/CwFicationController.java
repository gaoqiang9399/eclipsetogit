package app.component.finance.account.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.finance.account.entity.CwFication;
import app.component.finance.account.feign.CwFicationFeign;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: CwFicationAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jan 19 15:24:19 CST 2017
 **/
@Controller
@RequestMapping("/cwFication")
public class CwFicationController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CwFicationFeign
	@Autowired
	private CwFicationFeign cwFicationFeign;
	// 全局变量
	private String query;
	// 表单变量
	private FormData formfication0001;
	private FormData formfication0002;
	private FormService formService = new FormService();

	public CwFicationController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/finance/account/CwFication_List";
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
		CwFication cwFication = new CwFication();
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			cwFication.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwFication.setCriteriaList(cwFication, ajaxData);// 我的筛选
			// this.getRoleConditions(cwFication,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("cwFication",cwFication));
			ipage = cwFicationFeign.findByPage(ipage,finBooks);
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
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			formfication0002 = formService.getFormData("fication0002");
			getFormValue(formfication0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfication0002)) {
				CwFication cwFication = new CwFication();
				setObjValue(formfication0002, cwFication);
				String finBooks = (String) request.getSession().getAttribute("finBooks");
				cwFicationFeign.insert(cwFication,finBooks);
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
	@RequestMapping(value = "/updateAjaxByOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formfication0002 = formService.getFormData("fication0002");
		getFormValue(formfication0002, getMapByJson(ajaxData));
		CwFication cwFicationJsp = new CwFication();
		setObjValue(formfication0002, cwFicationJsp);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		CwFication cwFication = cwFicationFeign.getById(cwFicationJsp,finBooks);
		if (cwFication != null) {
			try {
				cwFication = (CwFication) EntityUtil.reflectionSetVal(cwFication, cwFicationJsp,
						getMapByJson(ajaxData));
				cwFicationFeign.update(cwFication,finBooks);
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
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			formfication0001 = formService.getFormData("fication0001");
			getFormValue(formfication0001, getMapByJson(ajaxData));
			if (this.validateFormData(formfication0001)) {
				CwFication cwFication = new CwFication();
				setObjValue(formfication0001, cwFication);
				cwFicationFeign.update(cwFication,finBooks);
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
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String txType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formfication0002 = formService.getFormData("fication0002");
		CwFication cwFication = new CwFication();
		cwFication.setTxType(txType);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwFication = cwFicationFeign.getById(cwFication,finBooks);
		getObjValue(formfication0002, cwFication, formData);
		if (cwFication != null) {
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
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAjax(String txType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwFication cwFication = new CwFication();
		cwFication.setTxType(txType);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwFicationFeign.delete(cwFication,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			e.printStackTrace();
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
		ActionContext.initialize(request, response);
		formfication0002 = formService.getFormData("fication0002");
		model.addAttribute("formfication0002", formfication0002);
		model.addAttribute("query", query);
		return "/component/finance/account/CwFication_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formfication0002 = formService.getFormData("fication0002");
		getFormValue(formfication0002);
		CwFication cwFication = new CwFication();
		setObjValue(formfication0002, cwFication);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwFicationFeign.insert(cwFication,finBooks);
		getObjValue(formfication0002, cwFication);
		Ipage ipage=this.getIpage();
		Map<String, Object> params = new HashMap<>();
		params.put("cwFication", cwFication);
		ipage.setParams(params);
		List<CwFication> cwFicationList = (List<CwFication>) cwFicationFeign.findByPage(ipage,finBooks)
				.getResult();
		model.addAttribute("cwFication", cwFication);
		model.addAttribute("formfication0002", formfication0002);
		model.addAttribute("cwFicationList", cwFicationList);
		model.addAttribute("query", query);
		return "/component/finance/account/CwFication_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String txType) throws Exception {
		ActionContext.initialize(request, response);
		formfication0001 = formService.getFormData("fication0001");
		getFormValue(formfication0001);
		CwFication cwFication = new CwFication();
		cwFication.setTxType(txType);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwFication = cwFicationFeign.getById(cwFication,finBooks);
		getObjValue(formfication0001, cwFication);
		model.addAttribute("cwFication", cwFication);
		model.addAttribute("formfication0001", formfication0001);
		model.addAttribute("query", query);
		return "/component/finance/account/CwFication_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String txType) throws Exception {
		ActionContext.initialize(request, response);
		CwFication cwFication = new CwFication();
		cwFication.setTxType(txType);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwFicationFeign.delete(cwFication,finBooks);
		return getListPage();
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formfication0002 = formService.getFormData("fication0002");
		getFormValue(formfication0002);
		boolean validateFlag = this.validateFormData(formfication0002);
		model.addAttribute("formfication0002", formfication0002);
		model.addAttribute("query", query);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formfication0002 = formService.getFormData("fication0002");
		getFormValue(formfication0002);
		boolean validateFlag = this.validateFormData(formfication0002);
		model.addAttribute("formfication0002", formfication0002);
		model.addAttribute("query", query);
	}

	/**
	 * 
	 * 方法描述： 获取辅助核算表数据
	 * 
	 * @throws Exception
	 *             void
	 * @author 刘争帅
	 * @date 2017-2-3 上午10:11:20
	 */
	@RequestMapping(value = "/getFicationDataAjax")
	@ResponseBody
	public Map<String, Object> getFicationDataAjax() throws Exception {
		// ActionContext.initialize(ServletActionContext.getRequest(),ServletActionContext.getResponse());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			List<Map<String, String>> listMap = cwFicationFeign.getFicationData(finBooks);
			dataMap.put("listmap", listMap);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增一级科目失败");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			e.printStackTrace();
			throw e;
		}

		return dataMap;
	}

}
