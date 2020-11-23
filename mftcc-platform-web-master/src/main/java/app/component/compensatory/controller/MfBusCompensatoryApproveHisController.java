package  app.component.compensatory.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.pact.entity.MfBusFincApp;
import app.component.pact.feign.MfBusFincAppFeign;

/**
 * Title: MfBusCompensatoryApproveHisAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 09 19:01:16 CST 2018
 **/
@Controller
@RequestMapping("/mfBusCompensatoryApproveHis")
public class MfBusCompensatoryApproveHisController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	public String getListPage() throws Exception {
		ActionContext.initialize(request,response);
		return "MfBusCompensatoryApproveHis_List";
	}
	
}
