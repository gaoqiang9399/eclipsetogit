package app.component.report.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.base.User;
import app.component.report.entity.MfReportRecall;
import app.component.report.feign.MfReportRecallFeign;
import cn.mftcc.util.PropertiesUtil;

@Controller
@RequestMapping("/mfReportRecall")
public class MfReportRecallController extends BaseFormBean{
	//recall
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReportRecallFeign mfReportRecallFeign;
	

	@RequestMapping(value = "/getRecallList")
	public String getRecallList(Model model, String ajaxData, String reportId, String sqlMap) throws Exception{
		
		ActionContext.initialize(request,
				response);

		String opNo = User.getRegNo(request);
		MfReportRecall mfReportRecall = new MfReportRecall();
		try{
			//报表查询
			request.setAttribute("reportUrl", PropertiesUtil.getWebServiceProperty("report.url"));
			List<MfReportRecall> mfReportRecallList = mfReportRecallFeign.getRecall(mfReportRecall,opNo,reportId,sqlMap);
			request.setAttribute("list", mfReportRecallList);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}	
		return "/component/report/reportList/recallInfo";
	}

}
