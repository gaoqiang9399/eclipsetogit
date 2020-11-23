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
import app.component.report.entity.MfProSaleYearlyBean;
import app.component.report.feign.MfProSaleYearlyFeign;
import cn.mftcc.util.PropertiesUtil;

@Controller
@RequestMapping("/mfProSaleYearly")
public class MfProSaleYearlyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfProSaleYearlyFeign mfProSaleYearlyFeign;

	@RequestMapping(value = "/proSaleStatYealy")
	public String proSaleStatYealy(Model model, String sqlMap, String reportId) throws Exception{
		ActionContext.initialize(request,response);
		String opNo = User.getRegNo(request);
		List<MfProSaleYearlyBean> resultList = null;
		try {
			Map<String, String> pmMap=new HashMap<String, String>();
			pmMap.put("opNo", opNo);
			pmMap.put("sqlMap", sqlMap);
			pmMap.put("reportId", reportId);
			resultList = mfProSaleYearlyFeign.getList(pmMap);
			request.setAttribute("reportUrl", PropertiesUtil.getWebServiceProperty("report.url"));
			request.setAttribute("list", resultList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/report/reportList/proSaleStatYealy";
		
	}

}
