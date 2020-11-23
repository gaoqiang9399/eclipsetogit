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
import app.component.report.entity.MfReportStatic;
import app.component.report.entity.MfReportStatus;
import app.component.report.feign.MfReportBusStaticFeign;
import app.component.report.feign.MfReportLoanStatusFeign;
import cn.mftcc.util.PropertiesUtil;

/**
 * 贷款情况汇总表
 * @author LiWQ
 *
 */
@Controller
@RequestMapping("/mfReportLoanStatus")
public class MfReportLoanStatusController extends BaseFormBean{

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReportLoanStatusFeign mfReportLoanStatusFeign;
	@Autowired
	private MfReportBusStaticFeign mfReportBusStaticFeign;
	
	/**
	 * 
	 * 方法描述： 获取贷款汇总表的数据列表
	 * @return
	 * String
	 * @author lwq
	 * @param reportId 
	 * @param sqlMap 
	 * @date 2017-7-11 上午11:41:37
	 */
	@RequestMapping(value = "/getMfReportStatusList")
	public String getMfReportStatusList(Model model, String ajaxData, String reportId, String sqlMap) throws Exception{
		
		ActionContext.initialize(request,
				response);
		
		String opNo = User.getRegNo(request);
		List<MfReportStatus> mfReportS = null;
		try {
			mfReportS = mfReportLoanStatusFeign.getMfReportStatusList(opNo,reportId,sqlMap);
			request.setAttribute("reportUrl", PropertiesUtil.getWebServiceProperty("report.url"));
			request.setAttribute("list", mfReportS);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		return "/component/report/reportList/loanStatusCount";
	}

	/**
	 * 
	 * 方法描述： 获取业务综合汇总表
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @param reportId 
	 * @param sqlMap 
	 * @date 2017-7-14 下午9:04:35
	 */
	@RequestMapping(value = "/getMfReportBusStatistics")
	public String getMfReportBusStatistics(Model model, String ajaxData, String reportId, String sqlMap)throws Exception{
		
		ActionContext.initialize(request,
				response);
		
		String opNo = User.getRegNo(request);
		List<MfReportStatic> MfReportStaticlist = null;
		try {
			MfReportStaticlist = mfReportBusStaticFeign.getMfReportStaticList(opNo, reportId,sqlMap);
			request.setAttribute("reportUrl", PropertiesUtil.getWebServiceProperty("report.url"));
			request.setAttribute("list", MfReportStaticlist);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		return "/component/report/reportList/mfReportBusStatic";
	}
	
}
