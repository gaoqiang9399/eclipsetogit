package app.component.pact.repayplan.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.core.entity.MfRepayPlanParameter;

/**
 * 展期还款计划 相关处理
 * 
 * @author WD
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MfBusExtensionRepayPlanFeign {

	@RequestMapping(value = "/mfBusExtensionRepayPlan/doExtensionApprovalProcess", method = RequestMethod.POST)
	public Map<String, Object> doExtensionApprovalProcess(@RequestParam("extensionApplyId") String extensionApplyId,
			@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate,@RequestBody Map<String, String> formMap,
			@RequestParam("planListSize") String planListSize, @RequestParam("regNo") String regNo) throws Exception ;

	@RequestMapping(value = "/mfBusExtensionRepayPlan/getDateAndMoneyFormat")
	public List<MfRepayPlan> getDateAndMoneyFormat(@RequestBody List<MfRepayPlan> repayPlanList,
			@RequestParam("digits") String digits) throws Exception;

	@RequestMapping(value = "/mfBusExtensionRepayPlan/getDateAndMoneyFormatStr")
	public List<MfRepayPlan> getDateAndMoneyFormatStr(@RequestBody List<MfRepayPlan> repayPlanList,
			@RequestParam("digits") String digits) throws Exception;

	@RequestMapping(value = "/mfBusExtensionRepayPlan/getExtensionRepayPlanList")
	public Map<String, Object> getExtensionRepayPlanList(@RequestParam("extensionApplyId") String extensionApplyId,
			@RequestBody Map<String, String> mapParm) throws Exception;

	@RequestMapping(value = "/mfBusExtensionRepayPlan/getfixEndDate")
	public String getfixEndDate(@RequestBody String repayDateSet, @RequestParam("repayDateDef") String repayDateDef,
			@RequestParam("intstEndDate") String intstEndDate);

	@RequestMapping(value = "/mfBusExtensionRepayPlan/getExtensionPlanListByEndDate")
	public Map<String, Object> getExtensionPlanListByEndDate(@RequestBody MfRepayPlanParameter mfRepayPlanParameter,
			@RequestParam("mapParm") Map<String, String> mapParm) throws Exception;

}
