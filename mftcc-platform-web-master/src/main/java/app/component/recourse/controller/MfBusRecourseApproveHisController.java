package  app.component.recourse.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.BaseFormBean;

/**
 * Title: MfBusRecourseApproveHisAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 16 19:42:41 CST 2018
 **/
@Controller
@RequestMapping("/mfBusRecourseApproveHis")
public class MfBusRecourseApproveHisController extends BaseFormBean{

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
}
