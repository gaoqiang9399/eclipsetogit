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

import app.component.common.EntityUtil;
import app.component.sec.entity.SecUserApptime;
import app.component.sec.feign.SecUserApptimeFeign;
import app.util.toolkit.Ipage;

@Controller
@RequestMapping("/secUserApptime")
public class SecUserApptimeController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	// 注入SecUserApptimeFeign
	private SecUserApptimeFeign secUserApptimeFeign;
	// 全局变量
	private SecUserApptime secUserApptime;
	private List<SecUserApptime> secUserApptimeList;
	// private String id;
	// private String tableType;
	// private String tableId;
	// private int pageNo;
	private String query;
	// // 异步参数
	// private String ajaxData;
	private Map<String, Object> dataMap;
	// 表单变量
	private FormData formsec0021;
	private FormData formsec0022;
	private FormService formService = new FormService();

	public SecUserApptimeController() {
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
		return "/component/sec/SecUserApptime_List";
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
		formsec0022 = formService.getFormData("sec0022");
		model.addAttribute("formsec0022", formsec0022);
		model.addAttribute("query", query);
		return "/component/sec/SecUserApptime_Insert";
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
		formsec0022 = formService.getFormData("sec0022");
		getFormValue(formsec0022);
		secUserApptime = new SecUserApptime();
		setObjValue(formsec0022, secUserApptime);
		secUserApptimeFeign.insert(secUserApptime);
		getObjValue(formsec0022, secUserApptime);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("secUserApptime", secUserApptime));
		secUserApptimeList = (List<SecUserApptime>) secUserApptimeFeign.findByPage(ipage, secUserApptime).getResult();
		model.addAttribute("formsec0022", formsec0022);
		model.addAttribute("secUserApptime", secUserApptime);
		model.addAttribute("secUserApptimeList", secUserApptimeList);
		model.addAttribute("query", query);
		return "/component/sec/SecUserApptime_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		ActionContext.initialize(request, response);
		formsec0021 = formService.getFormData("sec0021");
		getFormValue(formsec0021);
		secUserApptime = new SecUserApptime();
		secUserApptime.setId(id);
		secUserApptime = secUserApptimeFeign.getById(secUserApptime);
		getObjValue(formsec0021, secUserApptime);
		model.addAttribute("formsec0021", formsec0021);
		model.addAttribute("secUserApptime", secUserApptime);
		model.addAttribute("query", query);
		return "/component/sec/SecUserApptime_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(Model model, String id) throws Exception {
		ActionContext.initialize(request, response);
		secUserApptime = new SecUserApptime();
		secUserApptime.setId(id);
		secUserApptimeFeign.delete(secUserApptime);
		model.addAttribute("secUserApptime", secUserApptime);
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
		formsec0022 = formService.getFormData("sec0022");
		getFormValue(formsec0022);
		model.addAttribute("formsec0022", formsec0022);
		boolean validateFlag = this.validateFormData(formsec0022);
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
		formsec0022 = formService.getFormData("sec0022");
		getFormValue(formsec0022);
		model.addAttribute("formsec0022", formsec0022);
		boolean validateFlag = this.validateFormData(formsec0022);
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
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		secUserApptime = new SecUserApptime();
		try {
			secUserApptime.setCustomQuery(ajaxData);// 自定义查询参数赋值
			// secUserApptime.setCriteriaList(secUserApptime, ajaxData);// 我的筛选
			// this.getRoleConditions(secUserApptime,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("secUserApptime", secUserApptime));
			ipage = secUserApptimeFeign.findByPage(ipage, secUserApptime);
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
			formsec0022 = formService.getFormData("sec0022");
			getFormValue(formsec0022, getMapByJson(ajaxData));
			if (this.validateFormData(formsec0022)) {
				secUserApptime = new SecUserApptime();
				setObjValue(formsec0022, secUserApptime);
				secUserApptimeFeign.insert(secUserApptime);
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
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		formsec0022 = formService.getFormData("sec0022");
		getFormValue(formsec0022, getMapByJson(ajaxData));
		SecUserApptime secUserApptimeJsp = new SecUserApptime();
		setObjValue(formsec0022, secUserApptimeJsp);
		secUserApptime = secUserApptimeFeign.getById(secUserApptimeJsp);
		if (secUserApptime != null) {
			try {
				secUserApptime = (SecUserApptime) EntityUtil.reflectionSetVal(secUserApptime, secUserApptimeJsp, getMapByJson(ajaxData));
				secUserApptimeFeign.update(secUserApptime);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
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
		secUserApptime = new SecUserApptime();
		try {
			formsec0022 = formService.getFormData("sec0022");
			getFormValue(formsec0022, getMapByJson(ajaxData));
			if (this.validateFormData(formsec0022)) {
				secUserApptime = new SecUserApptime();
				setObjValue(formsec0022, secUserApptime);
				secUserApptimeFeign.update(secUserApptime);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		dataMap = new HashMap<String, Object>();
		formsec0022 = formService.getFormData("sec0022");
		secUserApptime = new SecUserApptime();
		secUserApptime.setId(id);
		secUserApptime = secUserApptimeFeign.getById(secUserApptime);
		getObjValue(formsec0022, secUserApptime, formData);
		if (secUserApptime != null) {
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		secUserApptime = new SecUserApptime();
		secUserApptime.setId(id);
		try {
			secUserApptimeFeign.delete(secUserApptime);
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
