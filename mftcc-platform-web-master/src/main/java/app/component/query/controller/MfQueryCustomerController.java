package app.component.query.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.nmd.entity.ParmDic;
import app.tech.oscache.CodeUtils;

@Controller
@RequestMapping("/mfQueryCustomer")
public class MfQueryCustomerController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping(value = "/getCustomer")
	public String getCustomer(Model model) throws Exception {
		ActionContext.initialize(request, response);
		CodeUtils cu = new CodeUtils();
		List<ParmDic> kindNoList = (List<ParmDic>) cu.getCacheByKeyName("KIND_NO");
		model.addAttribute("firstKindNo", kindNoList.get(0).getOptCode());
		model.addAttribute("query", "");
		return "/component/query/MfQueryEntrance";
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
		return "/component/query/MfQueryCustomer_List";
	}

}
