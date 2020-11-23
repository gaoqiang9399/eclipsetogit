package  app.component.app.controller;

import app.component.rulesinterface.RulesInterfaceFeign;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.eclipse.jdt.internal.compiler.ast.ForeachStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Title: MfBusApplyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat May 21 10:40:47 CST 2016
 **/
@Controller
@RequestMapping("/fincRateRules")
public class MfFincRateRulesController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@Autowired
	private RulesInterfaceFeign rulesInterfaceFeign;

}
