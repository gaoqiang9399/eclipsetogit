package app.component.finance.voucher.controller;

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
import com.google.gson.Gson;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.finance.voucher.entity.CwVoucherRemarks;
import app.component.finance.voucher.feign.CwVoucherRemarksFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: CwVoucherRemarksAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jan 07 09:04:42 CST 2017
 **/
@Controller
@RequestMapping("/cwVoucherRemarks")
public class CwVoucherRemarksController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CwVoucherRemarksFeign
	@Autowired
	private CwVoucherRemarksFeign cwVoucherRemarksFeign;
	// 全局变量
	private String query;
	// 异步参数
	// 表单变量
	private FormService formService = new FormService();

	public CwVoucherRemarksController() {
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
		return "/component/finance/voucher/CwVoucherRemarks_List";
	}

	/***
	 * 凭证摘要列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVoucherRemarks cwVoucherRemarks = new CwVoucherRemarks();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwVoucherRemarks.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwVoucherRemarks.setCriteriaList(cwVoucherRemarks, ajaxData);// 我的筛选
			// this.getRoleConditions(cwVoucherRemarks,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwVoucherRemarks", cwVoucherRemarks);
			ipage.setParams(params);
			ipage = cwVoucherRemarksFeign.findByPage(ipage,finBooks);
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

	@RequestMapping(value = "/ajaxSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ajaxSave(String ajaxData) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			Map<String, Object> map = getMapByJson(ajaxData);// 提交的form表单的参数
			map.put("regNo", User.getRegNo(request));
			map.put("regName", User.getRegName(request));
			String[] result = cwVoucherRemarksFeign.saveVoucherRemarks(map,finBooks);
			dataMap.put("flag", result[0]);
			dataMap.put("msg", result[1]);
		} catch (Exception e) {
//			log.error("ajaxSave方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "系统异常，请在日志中查看详细错误信息！");
		}
		return dataMap;
	}

	//////////////////
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
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formvoucherremarks0002 = formService.getFormData("voucherremarks0002");
			getFormValue(formvoucherremarks0002, getMapByJson(ajaxData));
			if (this.validateFormData(formvoucherremarks0002)) {
				CwVoucherRemarks cwVoucherRemarks = new CwVoucherRemarks();
				setObjValue(formvoucherremarks0002, cwVoucherRemarks);
				cwVoucherRemarksFeign.insert(cwVoucherRemarks,finBooks);
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
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formvoucherremarks0002 = formService.getFormData("voucherremarks0002");
		getFormValue(formvoucherremarks0002, getMapByJson(ajaxData));
		CwVoucherRemarks cwVoucherRemarksJsp = new CwVoucherRemarks();
		setObjValue(formvoucherremarks0002, cwVoucherRemarksJsp);
		CwVoucherRemarks cwVoucherRemarks = cwVoucherRemarksFeign.getById(cwVoucherRemarksJsp,finBooks);
		if (cwVoucherRemarks != null) {
			try {
				cwVoucherRemarks = (CwVoucherRemarks) EntityUtil.reflectionSetVal(cwVoucherRemarks, cwVoucherRemarksJsp,
						getMapByJson(ajaxData));
				cwVoucherRemarksFeign.update(cwVoucherRemarks,finBooks);
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
			FormData formvoucherremarks0002 = formService.getFormData("voucherremarks0002");
			getFormValue(formvoucherremarks0002, getMapByJson(ajaxData));
			if (this.validateFormData(formvoucherremarks0002)) {
				CwVoucherRemarks cwVoucherRemarks = new CwVoucherRemarks();
				setObjValue(formvoucherremarks0002, cwVoucherRemarks);
				cwVoucherRemarksFeign.update(cwVoucherRemarks,finBooks);
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
	public Map<String, Object> getByIdAjax(String uid) throws Exception {
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formvoucherremarks0002 = formService.getFormData("voucherremarks0002");
		CwVoucherRemarks cwVoucherRemarks = new CwVoucherRemarks();
		cwVoucherRemarks.setUid(uid);
		cwVoucherRemarks = cwVoucherRemarksFeign.getById(cwVoucherRemarks,finBooks);
		getObjValue(formvoucherremarks0002, cwVoucherRemarks, formData);
		if (cwVoucherRemarks != null) {
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
	public Map<String, Object> deleteAjax(String uid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVoucherRemarks cwVoucherRemarks = new CwVoucherRemarks();
		cwVoucherRemarks.setUid(uid);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwVoucherRemarksFeign.delete(cwVoucherRemarks,finBooks);
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
		ActionContext.initialize(request, response);
		FormData formvoucherremarks0002 = formService.getFormData("voucherremarks0002");
		model.addAttribute("formvoucherremarks0002", formvoucherremarks0002);
		model.addAttribute("query", query);
		return "/component/finance/voucher/CwVoucherRemarks_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		ActionContext.initialize(request, response);
		FormData formvoucherremarks0002 = formService.getFormData("voucherremarks0002");
		getFormValue(formvoucherremarks0002);
		CwVoucherRemarks cwVoucherRemarks = new CwVoucherRemarks();
		setObjValue(formvoucherremarks0002, cwVoucherRemarks);
		cwVoucherRemarksFeign.insert(cwVoucherRemarks,finBooks);
		getObjValue(formvoucherremarks0002, cwVoucherRemarks);
//		this.addActionMessage("保存成功");
		Ipage ipage = this.getIpage();
		Map<String, Object> params = new HashMap<>();
		params.put("cwVoucherRemarks", cwVoucherRemarks);
		ipage.setParams(params);
		List<CwVoucherRemarks> cwVoucherRemarksList = (List<CwVoucherRemarks>) cwVoucherRemarksFeign
				.findByPage(ipage,finBooks).getResult();
		model.addAttribute("cwVoucherRemarks", cwVoucherRemarks);
		model.addAttribute("cwVoucherRemarksList", cwVoucherRemarksList);
		model.addAttribute("formvoucherremarks0002", formvoucherremarks0002);
		model.addAttribute("query", query);
		return "/component/finance/voucher/CwVoucherRemarks_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String uid) throws Exception {
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		ActionContext.initialize(request, response);
		FormData formvoucherremarks0001 = formService.getFormData("voucherremarks0001");
		getFormValue(formvoucherremarks0001);
		CwVoucherRemarks cwVoucherRemarks = new CwVoucherRemarks();
		cwVoucherRemarks.setUid(uid);
		cwVoucherRemarks = cwVoucherRemarksFeign.getById(cwVoucherRemarks,finBooks);
		getObjValue(formvoucherremarks0001, cwVoucherRemarks);
		model.addAttribute("cwVoucherRemarks", cwVoucherRemarks);
		model.addAttribute("formvoucherremarks0002", formvoucherremarks0001);
		model.addAttribute("query", query);
		return "/component/finance/voucher/CwVoucherRemarks_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String uid) throws Exception {
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		ActionContext.initialize(request, response);
		CwVoucherRemarks cwVoucherRemarks = new CwVoucherRemarks();
		cwVoucherRemarks.setUid(uid);
		cwVoucherRemarksFeign.delete(cwVoucherRemarks,finBooks);
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
		FormData formvoucherremarks0002 = formService.getFormData("voucherremarks0002");
		getFormValue(formvoucherremarks0002);
		boolean validateFlag = this.validateFormData(formvoucherremarks0002);
		model.addAttribute("formvoucherremarks0002", formvoucherremarks0002);
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
		FormData formvoucherremarks0002 = formService.getFormData("voucherremarks0002");
		getFormValue(formvoucherremarks0002);
		boolean validateFlag = this.validateFormData(formvoucherremarks0002);
		model.addAttribute("formvoucherremarks0002", formvoucherremarks0002);
		model.addAttribute("query", query);
	}

}
