package app.component.report.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.report.entity.MfCreditQueryRecordHis;
import app.component.report.entity.MfReportBus;
import app.component.report.entity.MfReportCustomer;
import app.component.report.entity.MfReportFinc;
import app.component.report.entity.MfReportFiveClass;
import app.component.report.entity.MfReportPledge;
import app.component.report.entity.MfReportRepay;
import app.component.report.feign.MfReportBusFeign;
import app.component.report.feign.MfReportCustomerFeign;
import app.component.report.feign.MfReportFincFeign;
import app.component.report.feign.MfReportFiveClassFeign;
import app.component.report.feign.MfReportPledgeFeign;
import app.component.report.feign.MfReportRepayFeign;

@Controller
@RequestMapping("/mfReportCustomer")
public class MfReportCustomerController extends BaseFormBean {
	/* cus */
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReportCustomerFeign mfReportCustomerFeign;
	/* bus */
	@Autowired
	private MfReportBusFeign mfReportBusFeign;
	// FIVECLASS
	@Autowired
	private MfReportFiveClassFeign mfReportFiveClassFeign;
	/* finc */
	@Autowired
	private MfReportFincFeign mfReportFincFeign;
	/* pledge */
	@Autowired
	private MfReportPledgeFeign mfReportPledgeFeign;
	// repay
	@Autowired
	private MfReportRepayFeign mfReportRepayFeign;

	@RequestMapping(value = "/getCusList")
	public String getCusList(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);

		MfReportCustomer mfReportCustomer = new MfReportCustomer();
		try {
			if (ajaxData != "" && ajaxData != null) {
				mfReportCustomer.setCustomQuery(ajaxData);
				mfReportCustomer.setCriteriaList(mfReportCustomer, ajaxData);
			}
			List<MfReportCustomer> mfReportCustomerList = mfReportCustomerFeign.getCusList(mfReportCustomer);
			request.setAttribute("list", mfReportCustomerList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/report/reportList/cusBaseInfo";
	}

	@RequestMapping(value = "/getBusList")
	public String getBusList(Model model, String ajaxData) throws Exception {

		ActionContext.initialize(request, response);

		MfReportBus mfReportBus = new MfReportBus();
		try {
			if (ajaxData != "" && ajaxData != null) {
				mfReportBus.setCustomQuery(ajaxData);
				mfReportBus.setCriteriaList(mfReportBus, ajaxData);
			}
			List<MfReportBus> mfReportBusList = mfReportBusFeign.getBusList(mfReportBus);
			request.setAttribute("list", mfReportBusList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return "/component/report/reportList/loanAll";
	}

	@RequestMapping(value = "/getFiveClassList")
	public String getFiveClassList(Model model, String ajaxData) throws Exception {

		ActionContext.initialize(request, response);

		MfReportFiveClass mfReportFiveClass = new MfReportFiveClass();
		try {
			List<MfReportFiveClass> mfReportFiveClassList = mfReportFiveClassFeign.getFiveClass(mfReportFiveClass);
			request.setAttribute("list", mfReportFiveClassList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return "/component/report/reportList/fiveClass";
	}

	@RequestMapping(value = "/getRepayList")
	public String getRepayList(Model model, String ajaxData) throws Exception {

		ActionContext.initialize(request, response);

		MfReportRepay mfReportRepay = new MfReportRepay();
		try {
			List<MfReportRepay> mfReportRepayList = mfReportRepayFeign.getRepay(mfReportRepay);
			request.setAttribute("list", mfReportRepayList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return "/component/report/reportList/repayInfo";
	}

	@RequestMapping(value = "/getFincList")
	public String getFincList(Model model, String ajaxData) throws Exception {

		ActionContext.initialize(request, response);

		MfReportFinc mfReportFinc = new MfReportFinc();
		try {
			if (ajaxData != "" && ajaxData != null) {
				mfReportFinc.setCustomQuery(ajaxData);
				mfReportFinc.setCriteriaList(mfReportFinc, ajaxData);
			}
			List<MfReportFinc> mfReportFincList = mfReportFincFeign.getFincList(mfReportFinc);
			request.setAttribute("list", mfReportFincList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/report/reportList/fangDetail";
	}

	@RequestMapping(value = "/getPledgeList")
	public String getPledgeList(Model model, String ajaxData) throws Exception {

		ActionContext.initialize(request, response);

		MfReportPledge mfReportPledge = new MfReportPledge();
		try {
			if (ajaxData != "" && ajaxData != null) {
				mfReportPledge.setCustomQuery(ajaxData);
				mfReportPledge.setCriteriaList(mfReportPledge, ajaxData);
			}
			List<MfReportPledge> mfReportPledgeList = mfReportPledgeFeign.getPledgeList(mfReportPledge);
			request.setAttribute("list", mfReportPledgeList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/report/reportList/pledgeInfo";
	}

	/**
	 * 
	 * 方法描述： 征信查询台账
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2018-1-17 下午4:13:58
	 */
	@RequestMapping(value = "/getCreditQueryList")
	public String getCreditQueryList(Model model, String ajaxData,String uuid,String  reportUrl) throws Exception {

		ActionContext.initialize(request, response);

		MfCreditQueryRecordHis mfCreditQueryRecordHis = new MfCreditQueryRecordHis();
		Gson gson=new Gson();
		try {
			List<MfCreditQueryRecordHis> creditQueryRecordHisList = mfReportCustomerFeign
					.getCreditQueryList(mfCreditQueryRecordHis);
			String json=gson.toJson(creditQueryRecordHisList);
			request.setAttribute("list", creditQueryRecordHisList);
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
