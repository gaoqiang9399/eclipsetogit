package app.component.finance.paramset.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

/**
 * Title: CwCycleHstAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jan 07 15:57:56 CST 2017
 **/
@Controller
@RequestMapping("/cwParamset")
public class CwParamsetController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	
	@RequestMapping(value = "/cwParamEntrance")
	public String cwParamEntrance(Model model) throws Exception {
		ActionContext.initialize(request, response);
		
		return "/component/finance/paramset/CwParamEntrance";
	}

	
}
