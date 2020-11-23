package app.component.tools.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.tools.entity.MfLoanCalculator;
import app.component.tools.feign.MfLoanCalculatorFeign;
import cn.mftcc.util.DateUtil;
/**
 * 贷款计算器
 * @author Tangxj
 */
@Controller
@RequestMapping(value = "/mfLoanCalculator")	
public class MfLoanCalculatorController extends BaseFormBean {
	
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private MfLoanCalculatorFeign mfLoanCalculatorFeign;
	/*public MfLoanCalculatorController(){
		query = "";
	
	
	MfLoanCalculatorBoImp mfLoanCalculatorBoImp = new MfLoanCalculatorBoImp();}
	*/
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formloancalculator00001 = formService.getFormData("loancalculator00001");
		MfLoanCalculator mfLoanCalculator = new MfLoanCalculator();
		mfLoanCalculator.setRepayTerm(1);
		mfLoanCalculator.setStartDate(DateUtil.getDate());
		getObjValue(formloancalculator00001, mfLoanCalculator);
		model.addAttribute("formloancalculator00001", formloancalculator00001);
		model.addAttribute("query", "");
		return "/component/tools/MfLoanCalculator_Input";
	}
	@ResponseBody
	@RequestMapping(value = "/calculateAjax")
	public Map<String, Object> calculateAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		dataMap= getMapByJson(ajaxData);
		try {
			dataMap = mfLoanCalculatorFeign.calculate(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}


    /**
     *
     * 功能描述: 贷款计算器 通过开始日期  和期限 计算结束日期
     * @param: [ajaxData]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @auther: wd
     * @date: 2018/9/1 11:11
     *
     */
	@RequestMapping(value = "/getEndDateInfoMapAjax")
	@ResponseBody
	public Map<String, Object> getEndDateInfoMapAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			//{"beginDate":beginDate,"term":term,"termType":termType,"calcIntstFlag":calcIntstFlag,"pactEndDateShowFlag":pactEndDateShowFlag,"pactEndDateShowFlag":appId,"pactId":pactId},			String multipleLoanPlanMerge = request.getParameter("multipleLoanPlanMerge");
			Map<String,String> parmMap= new HashMap<String,String>();
			String beginDate = request.getParameter("beginDate");//合同开始日期
			String term = request.getParameter("term");//合同期限
			parmMap.put("beginDate", beginDate);
			parmMap.put("term", term);
			dataMap=mfLoanCalculatorFeign.getEndDateInfoMap(parmMap);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	
}
