package app.component.pact.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.entity.MfBusPact;
import app.component.wkf.entity.Result;

/**
 * Title: MfBusApplyBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat May 21 10:40:47 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusPactWkfFeign {

	@RequestMapping(value = "/mfBusPactWkf/doCommitForPrimary")
	public Result doCommitForPrimary(@RequestParam("taskId") String taskId, @RequestParam("pactId") String pactId,@RequestParam("primaryPactId") String primaryPactId,
			@RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("transition") String transition, @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser,
			@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfBusPactWkf/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("pactId") String pactId,
			@RequestParam("opinionType") String opinionType,
			@RequestParam("transition") String transition, @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser,
			@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfBusPactWkf/submitProcess")
	public MfBusPact submitProcess(@RequestBody String pactId, @RequestParam("firstApprovalUser") String firstApprovalUser,@RequestParam("regName") String regName,@RequestParam("orgNo") String orgNo)
			throws Exception;

	@RequestMapping(value = "/mfBusPactWkf/submitProcessWithUser")
	public MfBusPact submitProcessWithUser(@RequestBody String pactId, @RequestParam("firstApprovalUser") String firstApprovalUser,
			@RequestParam("opNo") String opNo, @RequestParam("opName") String opName, @RequestParam("brNo") String brNo)
			throws Exception;

	@RequestMapping(value = "/mfBusPactWkf/doCommitForApp")
	public Result doCommitForApp(@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfBusPactWkf/getPactVariables")
	public Map<String, Object> getPactVariables(@RequestBody Map<String, Object> dataMap,
			@RequestParam("pactId") String pactId) throws Exception;

}
