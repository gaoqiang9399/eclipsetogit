package app.component.wkf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import app.common.BaseController;
@Controller
@RequestMapping(value = "/mfUniqueConfig")

public class WFDesignerController extends BaseController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@RequestMapping(value = "/openDesigner")
	  public String openDesigner() throws Exception {
	    return "/component/wkf/openDesigner";
	  }
}
