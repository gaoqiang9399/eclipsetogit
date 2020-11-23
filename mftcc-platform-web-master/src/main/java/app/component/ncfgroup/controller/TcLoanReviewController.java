package app.component.ncfgroup.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.ncfgroup.feign.TcLoanReviewFeign;

@Controller
@RequestMapping("/tcLoanReview")
public class TcLoanReviewController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private TcLoanReviewFeign tcLoanReviewFeign;

	// 调用资方,开户注册
	@ResponseBody
	@RequestMapping(value = "/getLoanReviewAjax")
	public Map<String, Object> getLoanReviewAjax(String cusNo) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<>();
		Map<String, String> map = new HashMap<>();
		map.put("fincId", "17090415395545411");
		map.put("loanAmt", "10000");
		map.put("guaranteeRateY", "3.0");
		map.put("consultationRateY", "3.0");
		map.put("unionConsultationRateY", "3.0");
		map.put("platformRateY", "3.0");
		try {
			dataMap = tcLoanReviewFeign.loanReview(map, cusNo);
			dataMap.put("errorCode", "000000");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("errorcode", "000001");
			dataMap.put("errorMsg", e.getMessage());
		}
		return dataMap;
	}

}
