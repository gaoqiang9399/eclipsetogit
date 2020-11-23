package app.component.pact.advanceloan.feign;

import app.component.pact.advanceloan.entity.MfBusAdvanceLoan;
import app.component.pact.assetmanage.entity.MfAssetManage;
import app.component.wkf.entity.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 提前还款
 */
@FeignClient("mftcc-platform-factor")
public interface MfBusAdvanceLoanFeign {

	/**
	 * 新增
	 * @param mfBusAdvanceLoan
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusAdvanceLoan/insert")
	Map<String,Object> insert(@RequestBody MfBusAdvanceLoan mfBusAdvanceLoan)throws  Exception;


	@RequestMapping(value = "/mfBusAdvanceLoan/getById")
	MfBusAdvanceLoan getById(@RequestParam("advanceLoanId")  String advanceLoanId) throws Exception;

	@RequestMapping(value = "/mfBusAdvanceLoan/getByPactId")
	MfBusAdvanceLoan getByPactId(@RequestParam("pactId")  String pactId) throws Exception;

	@RequestMapping(value = "/mfBusAdvanceLoan/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("id") String id,
						   @RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
						   @RequestParam("transition") String transition, @RequestParam("opNo") String opNo,
						   @RequestParam("nextUser") String nextUser,
						   @RequestBody Map<String, Object> dataMap) throws Exception;

}
