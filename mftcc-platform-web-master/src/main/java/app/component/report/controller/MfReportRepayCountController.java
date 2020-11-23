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
import app.component.report.entity.MfBusMonth;
import app.component.report.entity.MfRepayDetail;
import app.component.report.feign.MfReportRepayCountFeign;
import cn.mftcc.util.PropertiesUtil;

@Controller
@RequestMapping("/mfReportRepayCount")
public class MfReportRepayCountController extends BaseFormBean{

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReportRepayCountFeign mfReportRepayCountFeign;
	
	/**
	 * 
	 * 方法描述： 还款统计明细报表
	 * @return
	 * String
	 * @author lwq
	 * @date 2017-7-22 上午11:15:22
	 */
	@RequestMapping(value = "/getRepayRepayDetailCount")
	public String getRepayRepayDetailCount(Model model, String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		List<MfRepayDetail> resultList = null;
		try {
			resultList = mfReportRepayCountFeign.getRepayDetailList();
			request.setAttribute("list", resultList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/report/reportList/repayDetailCount";
	}
	
	/**
	 * 
	 * 方法描述： 获取业务经营统计月报表数据
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @param reportId 
	 * @param sqlMap 
	 * @date 2017-7-23 上午11:54:52
	 */
	@RequestMapping(value = "/getBussinessMonth")
	public String getBussinessMonth(Model model, String reportId, String sqlMap)throws Exception{
		ActionContext.initialize(request,
				response);
		String opNo = User.getRegNo(request);
		List<MfBusMonth> resultList = null;
		try {
			resultList = mfReportRepayCountFeign.getBussinessMonthList(opNo,reportId,sqlMap);
			request.setAttribute("reportUrl", PropertiesUtil.getWebServiceProperty("report.url"));
			request.setAttribute("list", resultList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/report/reportList/bussinessMonthCount";
	}
	
}
