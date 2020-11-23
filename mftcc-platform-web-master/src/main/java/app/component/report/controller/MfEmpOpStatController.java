package app.component.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.base.User;
import app.component.report.entity.MfEmpOpStatRepBean;
import app.component.report.feign.MfEmpOpStatFeign;
import cn.mftcc.util.PropertiesUtil;

@Controller
@RequestMapping("/mfEmpOpStat")
public class MfEmpOpStatController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfEmpOpStatFeign mfEmpOpStatFeign;
	@RequestMapping(value = "/empOpStat")
	public String empOpStat(Model model, String ajaxData, String reportId, String sqlMap) throws Exception{
		ActionContext.initialize(request,
				response);
		String opNo = User.getRegNo(request);
		List<MfEmpOpStatRepBean> resultList = null;
		try {
			Map<String, String>pmMap=new HashMap<String, String>();
			pmMap.put("opNo", opNo);
			pmMap.put("reportId", reportId);
			pmMap.put("sqlMap", sqlMap);
			request.setAttribute("reportUrl", PropertiesUtil.getWebServiceProperty("report.url"));
			resultList = mfEmpOpStatFeign.getDateList(pmMap);
			request.setAttribute("list", resultList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/report/reportList/empOpStat";
		
	}

}
