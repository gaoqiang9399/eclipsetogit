package  app.component.compensatory.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.BaseFormBean;

/**
 * Title: MfBusCompensatoryApplyDetailAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 09 18:58:56 CST 2018
 **/
@Controller
@RequestMapping("/mfBusCompensatoryApplyDetail")
public class MfBusCompensatoryApplyDetailController extends BaseFormBean{
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

}
