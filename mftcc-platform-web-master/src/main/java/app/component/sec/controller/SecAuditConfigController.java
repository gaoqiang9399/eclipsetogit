package app.component.sec.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import app.component.sec.entity.SecAuditConfig;
import app.component.sec.feign.SecAuditConfigFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/secAuditConfig")
public class SecAuditConfigController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	// 注入SecAuditConfigFeign
	private SecAuditConfigFeign secAuditConfigFeign;
	// 全局变量
	private SecAuditConfig secAuditConfig;
	private List<SecAuditConfig> secAuditConfigList;
	// private String itemNo;
	// private String tableType;
	// private String tableId;
	// private String codeType;
	// private int pageNo;
	private String query;
	// 异步参数
	// private String ajaxData;
	private Map<String, Object> dataMap;
	// 表单变量
	private FormData formsec0001;
	private FormData formsec0002;
	private FormService formService = new FormService();

	public SecAuditConfigController() {
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
		return "/component/sec/SecAuditConfig_List";
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
		formsec0002 = formService.getFormData("sec0002");
		model.addAttribute("formsec0002", formsec0002);
		model.addAttribute("query", query);
		return "/component/sec/SecAuditConfig_Insert";
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
		formsec0002 = formService.getFormData("sec0002");
		getFormValue(formsec0002);
		secAuditConfig = new SecAuditConfig();
		setObjValue(formsec0002, secAuditConfig);
		secAuditConfigFeign.insert(secAuditConfig);
		getObjValue(formsec0002, secAuditConfig);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("secAuditConfig", secAuditConfig));
		secAuditConfigList = (List<SecAuditConfig>) secAuditConfigFeign.findByPage(ipage, secAuditConfig).getResult();
		model.addAttribute("formsec0002", formsec0002);
		model.addAttribute("secAuditConfig", secAuditConfig);
		model.addAttribute("secAuditConfigList", secAuditConfigList);
		model.addAttribute("query", query);
		return "/component/sec/SecAuditConfig_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String itemNo) throws Exception {
		ActionContext.initialize(request, response);
		formsec0001 = formService.getFormData("sec0001");
		getFormValue(formsec0001);
		secAuditConfig = new SecAuditConfig();
		secAuditConfig.setItemNo(itemNo);
		secAuditConfig = secAuditConfigFeign.getById(secAuditConfig);
		getObjValue(formsec0001, secAuditConfig);
		model.addAttribute("formsec0001", formsec0001);
		model.addAttribute("secAuditConfig", secAuditConfig);
		model.addAttribute("query", query);
		return "/component/sec/SecAuditConfig_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String itemNo) throws Exception {
		ActionContext.initialize(request, response);
		secAuditConfig = new SecAuditConfig();
		secAuditConfig.setItemNo(itemNo);
		secAuditConfigFeign.delete(secAuditConfig);
		model.addAttribute("secAuditConfig", secAuditConfig);
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
		formsec0002 = formService.getFormData("sec0002");
		getFormValue(formsec0002);
		boolean validateFlag = this.validateFormData(formsec0002);
		model.addAttribute("formsec0002", formsec0002);
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
		formsec0002 = formService.getFormData("sec0002");
		getFormValue(formsec0002);
		boolean validateFlag = this.validateFormData(formsec0002);
		model.addAttribute("formsec0002", formsec0002);
		model.addAttribute("query", query);
	}

	@ResponseBody
	@RequestMapping(value = "/getAllData")
	public Map<String, Object> getAllData(String tableId) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		secAuditConfig = new SecAuditConfig();
		JsonTableUtil jtu = new JsonTableUtil();
		secAuditConfig.setCodeType("PR");
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", secAuditConfigFeign.getAll(secAuditConfig), null, true);
		dataMap.put("pwSetTableData", tableHtml);
		secAuditConfig.setCodeType("SF");
		tableHtml = jtu.getJsonStr(tableId, "tableTag", secAuditConfigFeign.getAll(secAuditConfig), null, true);
		dataMap.put("pwErrorTableData", tableHtml);
		secAuditConfig.setCodeType("SL");
		tableHtml = jtu.getJsonStr(tableId, "tableTag", secAuditConfigFeign.getAll(secAuditConfig), null, true);
		dataMap.put("pwUpdateTableData", tableHtml);
		secAuditConfig.setCodeType("SR");
		tableHtml = jtu.getJsonStr(tableId, "tableTag", secAuditConfigFeign.getAll(secAuditConfig), null, true);
		dataMap.put("accessTableData", tableHtml);
		dataMap.put("flag", "success");
		return dataMap;
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		secAuditConfig = new SecAuditConfig();
		try {
			secAuditConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			// secAuditConfig.setCriteriaList(secAuditConfig, ajaxData);// 我的筛选
			// this.getRoleConditions(secAuditConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("secAuditConfig", secAuditConfig));
			ipage = secAuditConfigFeign.findByPage(ipage, secAuditConfig);
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

	/***
	 * 校验密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/validatePWAjax")
	public Map<String, Object> validatePWAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		String pwd = ajaxData;
		String msg = "";
		try {
			String regNo = User.getRegNo(getHttpRequest());
			Map<String, SecAuditConfig> map = secAuditConfigFeign.getPwResult();
			if (map.containsKey("1")) {// 密码长度最小值，值选择：0-20位；
				int len = new Integer(map.get("1").getItemValues());
				if (pwd.length() < len) {
					msg += "密码最小长度为" + map.get("1").getItemValues() + "<br>";
				}
			}
			if (map.containsKey("2")) {// 必须包含英文大写字母(A 到 Z)；
				if (!match("[A-Z]+?", pwd)) {
					msg += "必须包含英文大写字母(A 到 Z)" + "<br>";
				}
			}
			if (map.containsKey("3")) {// 必须包含英文小写字母(a 到 z)；
				if (!match("[a-z]+?", pwd)) {
					msg += "必须包含英文小写字母(a 到 z)" + "<br>";
				}
			}
			if (map.containsKey("4")) {// 必须包含10个基本数字(0 到 9)；
				if (!match("[0-9]+?", pwd)) {
					msg += "必须包含10个基本数字(0 到 9)" + "<br>";
				}
			}
			if (map.containsKey("5")) {// 必须包含特殊字符(!、@、$、*、.)；
				if (isConSpeCharacters(pwd)) {
					msg += "必须包含特殊字符(!、@、$、*、.)" + "<br>";
				}
			}
			if (map.containsKey("6")) {// 不能包含用户的帐户名；
				if (pwd.indexOf(regNo) > -1) {
					msg += "不能包含用户的帐户名" + "<br>";
				}
			}
			if ("".equals(msg)) {
				dataMap.put("flag", "success");
				dataMap.put("msg", msg);
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		// return matcher.matches();
		return matcher.find();
	}

	/**
	 * 功能：判断一个字符串是否包含特殊字符
	 * 
	 * @param string
	 *            要判断的字符串
	 * @return true 提供的参数string不包含特殊字符
	 * @return false 提供的参数string包含特殊字符
	 */
	private static boolean isConSpeCharacters(String string) {
		if (string.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0) {
			// 如果不包含特殊字符
			return true;
		}
		return false;
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
			formsec0002 = formService.getFormData("sec0002");
			getFormValue(formsec0002, getMapByJson(ajaxData));
			if (this.validateFormData(formsec0002)) {
				secAuditConfig = new SecAuditConfig();
				setObjValue(formsec0002, secAuditConfig);
				secAuditConfigFeign.insert(secAuditConfig);
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
		formsec0002 = formService.getFormData("sec0002");
		getFormValue(formsec0002, getMapByJson(ajaxData));
		SecAuditConfig secAuditConfigJsp = new SecAuditConfig();
		setObjValue(formsec0002, secAuditConfigJsp);
		secAuditConfig = secAuditConfigFeign.getById(secAuditConfigJsp);
		if (secAuditConfig != null) {
			try {
				secAuditConfig = (SecAuditConfig) EntityUtil.reflectionSetVal(secAuditConfig, secAuditConfigJsp, getMapByJson(ajaxData));
				secAuditConfigFeign.update(secAuditConfig);
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
		secAuditConfig = new SecAuditConfig();
		try {
			formsec0002 = formService.getFormData("sec0002");
			getFormValue(formsec0002, getMapByJson(ajaxData));
			if (this.validateFormData(formsec0002)) {
				secAuditConfig = new SecAuditConfig();
				setObjValue(formsec0002, secAuditConfig);
				secAuditConfigFeign.update(secAuditConfig);
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

	@ResponseBody
	@RequestMapping(value = "/updateListAjax")
	public Map<String, Object> updateListAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		secAuditConfig = new SecAuditConfig();
		try {
			JSONArray jsonArray = JSONArray.fromObject(ajaxData);
			List<SecAuditConfig> secAuditConfigAjaxList = (List<SecAuditConfig>) JSONArray.toList(jsonArray, new SecAuditConfig(), new JsonConfig());
			secAuditConfigFeign.updateSts(secAuditConfigAjaxList);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
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
	public Map<String, Object> getByIdAjax(String itemNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		dataMap = new HashMap<String, Object>();
		formsec0002 = formService.getFormData("sec0002");
		secAuditConfig = new SecAuditConfig();
		secAuditConfig.setItemNo(itemNo);
		secAuditConfig = secAuditConfigFeign.getById(secAuditConfig);
		getObjValue(formsec0002, secAuditConfig, formData);
		if (secAuditConfig != null) {
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
	public Map<String, Object> deleteAjax(String itemNo) throws Exception {
		ActionContext.initialize(request, response);
		dataMap = new HashMap<String, Object>();
		secAuditConfig = new SecAuditConfig();
		secAuditConfig.setItemNo(itemNo);
		try {
			secAuditConfigFeign.delete(secAuditConfig);
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
