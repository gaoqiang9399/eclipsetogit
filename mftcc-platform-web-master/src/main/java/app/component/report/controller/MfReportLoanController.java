package app.component.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.base.User;
import app.component.report.entity.MfCountByCus;
import app.component.report.entity.MfCountByVouType;
import app.component.report.entity.MfLoanBean;
import app.component.report.feign.MfReportLoanFeign;
import cn.mftcc.util.PropertiesUtil;

/**
 * 放款统计表明细报表
 * 
 * @author LiWQ
 *
 */
@Controller
@RequestMapping("/mfReportLoan")
public class MfReportLoanController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReportLoanFeign mfReportLoanFeign;

	/**
	 * 
	 * 方法描述：放款统计明细表（按产品）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lwq
	 * @param reportId
	 * @param sqlMap
	 * @date 2017-7-19 下午5:50:40
	 */
	@RequestMapping(value = "/getPutList")
	public String getPutList(Model model, String reportId, String sqlMap,String uuid,String  reportUrl) throws Exception {

		ActionContext.initialize(request, response);
		String opNo = User.getRegNo(request);
		List<MfLoanBean> resultList = null;
		try {
			Map<String,String> map = new HashMap();
			map.put("opNo",opNo);
			map.put("reportId",reportId);
			map.put("sqlMap",sqlMap);
			resultList = mfReportLoanFeign.getResultList(map);
			String json = new Gson().toJson(resultList);
			model.addAttribute("json",json);
			model.addAttribute("uuid",uuid);
			model.addAttribute("reportUrl",reportUrl);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return "/component/report/reportList/creditQueryListRDP";
	}

	/**
	 * 
	 * 方法描述： 放款统计表按客户类型统计
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lwq
	 * @date 2017-7-20 下午3:53:03
	 */
	@RequestMapping(value = "/getPutListByCus")
	public String getPutListByCus(Model model, String reportId, String sqlMap) throws Exception {

		ActionContext.initialize(request, response);
		String opNo = User.getRegNo(request);
		List<MfCountByCus> countByCusList = null;
		try {
			countByCusList = mfReportLoanFeign.getPutListByCus(opNo, reportId, sqlMap);
			request.setAttribute("reportUrl", PropertiesUtil.getWebServiceProperty("report.url"));
			request.setAttribute("list", countByCusList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return "/component/report/reportList/putListByCus";
	}

	/**
	 * 
	 * 方法描述： 放款统计表按担保类型统计
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lwq
	 * @date 2017-7-20 下午5:58:14
	 */
	@RequestMapping(value = "/getPutListByVouType")
	public String getPutListByVouType(Model model, String reportId, String sqlMap,String uuid,String  reportUrl) throws Exception {

		ActionContext.initialize(request, response);
		String opNo = User.getRegNo(request);
		List<MfCountByVouType> countByVouTypeList = null;
		try {
			Map<String,String> map = new HashMap();
			map.put("opNo",opNo);
			map.put("reportId",reportId);
			map.put("sqlMap",sqlMap);
			countByVouTypeList = mfReportLoanFeign.getPutListByVouType(map);
			String json = new Gson().toJson(countByVouTypeList);
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
