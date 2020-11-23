package app.component.pact.repayplan.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.core.entity.MfRepayPlan;

@FeignClient("mftcc-platform-factor")
public interface MfRementpayPlanFeign {

	@RequestMapping(value = "/mfRementpayPlan/doExportExcelModel")
	public String doExportExcelModel(@RequestParam("cusNo") String cusNo) throws Exception;

	@RequestMapping(value = "/mfRementpayPlan/insert")
	public void insert(@RequestBody List<MfRepayPlan> mfRepayPlans,@RequestParam("fincId") String fincId) throws Exception;


}
