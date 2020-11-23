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
import app.component.report.entity.MfAppEfficiencyRepBean;
import app.component.report.feign.MfAppEfficiencyFeign;
import cn.mftcc.util.PropertiesUtil;

@Controller
@RequestMapping("/mfAppEfficiency")
public class MfAppEfficiencyController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfAppEfficiencyFeign mfAppEfficiencyFeign;
	
	@RequestMapping(value = "/appEfficiency")
	public String appEfficiency(Model model, String sqlMap, String reportId) throws Exception{
		ActionContext.initialize(request,
				response);
		String opNo = User.getRegNo(request);
		List<MfAppEfficiencyRepBean> resultList = null;
		try {
			Map<String, String>pmMap=new HashMap<String, String>();
			pmMap.put("opNo", opNo);
			pmMap.put("reportId", reportId);
			pmMap.put("sqlMap", sqlMap);
			resultList = mfAppEfficiencyFeign.getAppList(pmMap);
			model.addAttribute("reportUrl", PropertiesUtil.getWebServiceProperty("report.url"));
			request.setAttribute("list", resultList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/report/reportList/appEfficiency";
		
	}

}
