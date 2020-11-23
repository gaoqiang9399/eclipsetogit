package app.component.sec.controller;

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

import app.component.sec.entity.SecAuditInfoLog;
import app.component.sec.feign.SecAuditInfoLogFeign;
import app.util.toolkit.Ipage;

@Controller
@RequestMapping("/secAuditInfoLog")
public class SecAuditInfoLogController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	// 注入SecAuditInfoLogFeign
	private SecAuditInfoLogFeign secAuditInfoLogFeign;
	// 全局变量
	private SecAuditInfoLog secAuditInfoLog;
	private List<SecAuditInfoLog> secAuditInfoLogList;
	// private Integer logId;
	// private String tableType;
	// private String tableId;
	// private int pageNo;
	private String query;
	// 异步参数
	// private String ajaxData;
	private Map<String, Object> dataMap;
	// 表单变量
	private FormData formsec0031;
	private FormData formsec0032;
	private FormService formService = new FormService();

	public SecAuditInfoLogController() {
		query = "";
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
		model.addAttribute("query", query);
		return "/component/sec/SecAuditInfoLog_List";
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
		formsec0032 = formService.getFormData("sec0032");
		model.addAttribute("formsec0032", formsec0032);
		model.addAttribute("query", query);
		return "/component/sec/SecAuditInfoLog_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formsec0032 = formService.getFormData("sec0032");
		getFormValue(formsec0032);
		secAuditInfoLog = new SecAuditInfoLog();
		setObjValue(formsec0032, secAuditInfoLog);
		secAuditInfoLogFeign.insert(secAuditInfoLog);
		getObjValue(formsec0032, secAuditInfoLog);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("secAuditInfoLog", secAuditInfoLog));
		secAuditInfoLogList = (List<SecAuditInfoLog>) secAuditInfoLogFeign.findByPage(ipage, secAuditInfoLog).getResult();
		model.addAttribute("formsec0032", formsec0032);
		model.addAttribute("secAuditInfoLog", secAuditInfoLog);
		model.addAttribute("secAuditInfoLogList", secAuditInfoLogList);
		model.addAttribute("query", query);
		return "/component/sec/SecAuditInfoLog_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, Integer logId) throws Exception {
		ActionContext.initialize(request, response);
		formsec0031 = formService.getFormData("sec0031");
		getFormValue(formsec0031);
		secAuditInfoLog = new SecAuditInfoLog();
		secAuditInfoLog.setLogId(logId);
		secAuditInfoLog = secAuditInfoLogFeign.getById(secAuditInfoLog);
		getObjValue(formsec0031, secAuditInfoLog);
		model.addAttribute("formsec0031", formsec0031);
		model.addAttribute("secAuditInfoLog", secAuditInfoLog);
		model.addAttribute("query", query);
		return "/component/sec/SecAuditInfoLog_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(Model model, Integer logId) throws Exception {
		ActionContext.initialize(request, response);
		secAuditInfoLog = new SecAuditInfoLog();
		secAuditInfoLog.setLogId(logId);
		secAuditInfoLogFeign.delete(secAuditInfoLog);
		model.addAttribute("secAuditInfoLog", secAuditInfoLog);
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
		ActionContext.initialize(request, response);
		formsec0032 = formService.getFormData("sec0032");
		getFormValue(formsec0032);
		model.addAttribute("formsec0032", formsec0032);
		boolean validateFlag = this.validateFormData(formsec0032);
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
		formsec0032 = formService.getFormData("sec0032");
		getFormValue(formsec0032);
		model.addAttribute("formsec0032", formsec0032);
		boolean validateFlag = this.validateFormData(formsec0032);
		model.addAttribute("query", query);
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String ajaxData, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		secAuditInfoLog = new SecAuditInfoLog();
		try {
			secAuditInfoLog.setCustomQuery(ajaxData);// 自定义查询参数赋值
			secAuditInfoLog.setCriteriaList(secAuditInfoLog, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("secAuditInfoLog", secAuditInfoLog));
			ipage = secAuditInfoLogFeign.findByPage(ipage, secAuditInfoLog);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		try {
			formsec0032 = formService.getFormData("sec0032");
			getFormValue(formsec0032, getMapByJson(ajaxData));
			if (this.validateFormData(formsec0032)) {
				secAuditInfoLog = new SecAuditInfoLog();
				setObjValue(formsec0032, secAuditInfoLog);
				secAuditInfoLogFeign.insert(secAuditInfoLog);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		secAuditInfoLog = new SecAuditInfoLog();
		try {
			formsec0032 = formService.getFormData("sec0032");
			getFormValue(formsec0032, getMapByJson(ajaxData));
			if (this.validateFormData(formsec0032)) {
				secAuditInfoLog = new SecAuditInfoLog();
				setObjValue(formsec0032, secAuditInfoLog);
				secAuditInfoLogFeign.update(secAuditInfoLog);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(Integer logId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		dataMap = new HashMap<String, Object>();
		formsec0032 = formService.getFormData("sec0032");
		secAuditInfoLog = new SecAuditInfoLog();
		secAuditInfoLog.setLogId(logId);
		secAuditInfoLog = secAuditInfoLogFeign.getById(secAuditInfoLog);
		getObjValue(formsec0032, secAuditInfoLog, formData);
		if (secAuditInfoLog != null) {
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
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(Integer logId) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		secAuditInfoLog = new SecAuditInfoLog();
		secAuditInfoLog.setLogId(logId);
		try {
			secAuditInfoLogFeign.delete(secAuditInfoLog);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
}
