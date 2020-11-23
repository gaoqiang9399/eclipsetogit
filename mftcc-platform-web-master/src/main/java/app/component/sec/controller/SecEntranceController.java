package app.component.sec.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

@Controller
@RequestMapping(value ="/secEntrance")
public class SecEntranceController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	private String query;

	public SecEntranceController() {
		query = "";
	}

	/**
	 * 安全什么配置页
	 */
	@RequestMapping("/showConfig")
	public String showConfig(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", query);
		return "/component/sec/SecEntrance_Main";
	}

	@RequestMapping("/secAuditConfig")
	public String secAuditConfig(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", query);
		return "/component/sec/SecAuditConfig_Page";
	}
}
