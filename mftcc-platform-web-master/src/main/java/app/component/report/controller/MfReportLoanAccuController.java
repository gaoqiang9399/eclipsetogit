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
import app.component.report.entity.MfLoanAccountBean;
import app.component.report.feign.MfReportLoanAccuFeign;
import cn.mftcc.util.PropertiesUtil;

/**
 * 贷款累放累收统计月报表
 * @author LiWQ
 *
 */
@Controller
@RequestMapping("/mfReportLoanAccu")
public class MfReportLoanAccuController extends BaseFormBean{

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReportLoanAccuFeign mfReportLoanAccuFeign;
	/**
	 * 
	 * 方法描述： 贷款累放累收统计月报表数据列表
	 * @return
	 * String
	 * @author lwq
	 * @param reportId 
	 * @param sqlMap 
	 * @date 2017-7-17 下午5:18:50
	 */
	@RequestMapping(value = "/getLoanAccuReportList")
	public String getLoanAccuReportList(Model model, String ajaxData, String reportId, String sqlMap)throws Exception{
		ActionContext.initialize(request,
				response);
		String opNo = User.getRegNo(request);
		List<MfLoanAccountBean> reportList = null;
		try {
			Map<String,String> parmMap = new HashMap<String,String>();
			parmMap.put("opNo", opNo);
			parmMap.put("reportId", reportId);
			parmMap.put("sqlMap", sqlMap);
			reportList = mfReportLoanAccuFeign.getLoanAccuReportList(parmMap);
			request.setAttribute("reportUrl", PropertiesUtil.getWebServiceProperty("report.url"));
			request.setAttribute("list", reportList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/report/reportList/loanAccuReport";
	}
	
}
