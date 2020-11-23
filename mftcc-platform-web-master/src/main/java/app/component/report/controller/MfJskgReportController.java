package app.component.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.report.feign.MfJskgReportFeign;

import app.base.User;

import app.component.report.entity.MfReportPerfaStaBean;


import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 金山控股处理报表
 * @author rjq
 *
 */
@Controller
@RequestMapping("/mfJskgReport")
public class MfJskgReportController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfJskgReportFeign mfJskgReportFeign;

	/**
	 * 获取业绩统计表
	 */
	@RequestMapping("/getPerformanceStaList")
	public  String  getPerformanceStaList(Model model, String sqlMap, String reportId,String uuid,String  reportUrl,String queryStr)throws Exception{
		ActionContext.initialize(request, response);
		List<MfReportPerfaStaBean> resultList = null;
		try {
			String opNo = User.getRegNo(request);
			Map<String,String> parmMap = new HashMap<String,String>();
			parmMap.put("opNo", opNo);
			parmMap.put("reportId", reportId);
			parmMap.put("sql", sqlMap);
			parmMap.put("queryStr", queryStr);
			resultList = mfJskgReportFeign.getPerformanceStaList(parmMap);
			String json = new Gson().toJson(resultList);
			request.setAttribute("list", resultList);
			request.setAttribute("json",json);
			request.setAttribute("uuid",uuid);
			request.setAttribute("reportUrl",reportUrl);
			model.addAttribute("json",json);
			model.addAttribute("uuid",uuid);
			model.addAttribute("reportUrl",reportUrl);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/report/reportList/creditQueryListRDP";
		
	}

}
