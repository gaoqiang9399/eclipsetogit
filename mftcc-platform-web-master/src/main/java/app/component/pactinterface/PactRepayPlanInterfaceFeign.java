package app.component.pactinterface;

import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.core.entity.MfRepayPlanParameter;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusFincAppChild;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Title: cusInterface.java Description:
 * 
 * @author:LiuYF@dhcc.com.cn
 * @Mon May 16 20:45:38 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface PactRepayPlanInterfaceFeign {

	@RequestMapping(value = "/pactRepayPlanInterface/getMfRepayPlanListByAppId")
	public Map<String, Object> getMfRepayPlanListByAppId(@RequestBody  Map<String, Object> mapParm) throws Exception;

	@RequestMapping(value = "/pactRepayPlanInterface/getReturnPlanList")
	public List<MfRepayPlan> getReturnPlanList(@RequestBody MfRepayPlanParameter mfRepayPlanParameter) throws Exception;

	@RequestMapping(value = "/pactRepayPlanInterface/getReturnPlanListNorm")
	public List<MfRepayPlan> getReturnPlanListNorm(@RequestBody MfRepayPlanParameter mfRepayPlanParameter)
			throws Exception;

	@RequestMapping(value = "/pactRepayPlanInterface/doApprovalProcess")
	public Map<String, Object> doApprovalProcess(@RequestBody MfBusFincApp mfBusFincApp,  @RequestParam("beginDate") String beginDate,
			@RequestParam("endDate") String endDate, @RequestParam("planListData")  String planListData,  @RequestParam("planListSize") String planListSize,
			@RequestParam("repayFeeSum") String repayFeeSum, @RequestParam("repayIntstSum")  String repayIntstSum,
			 @RequestParam("multipleLoanPlanMerge") String multipleLoanPlanMerge, @RequestParam("interestCollectType")  String interestCollectType);

	@RequestMapping(value = "/pactRepayPlanInterface/getRepayPlanList")
	public Map<String, Object> getRepayPlanList(@RequestParam("appId") String appId, @RequestParam("mapParm") Map<String, String> mapParm)
			throws Exception;

	@RequestMapping(value = "/pactRepayPlanInterface/getRealTimePay")
	public Map<String, Object> getRealTimePay(@RequestBody MfBusFincAppChild mfBusFincAppChild,
			@RequestParam("isAutoValidateVerification") String isAutoValidateVerification) throws Exception;

	@RequestMapping(value = "/pactRepayPlanInterface/doApprovalProcessAllParm")
	public Map<String, Object> doApprovalProcessAllParm(@RequestBody MfBusFincApp mfBusFincApp,
			@RequestParam("beginDate")String beginDate,  @RequestParam("endDate")String endDate,  @RequestParam("planListData")String planListData,
			@RequestParam("planListSize")String planListSize, @RequestParam("repayFeeSum") String repayFeeSum,  @RequestParam("appId")String repayIntstSum,
			@RequestParam("multipleLoanPlanMerge") String multipleLoanPlanMerge, @RequestParam("interestCollectType") String interestCollectType,
			@RequestParam("putOutChargeIntstFlag") String putOutChargeIntstFlag,  @RequestParam("putOutChargeIntst")String putOutChargeIntst,
			@RequestParam("parmMap") Map<String, String> parmMap);
}
