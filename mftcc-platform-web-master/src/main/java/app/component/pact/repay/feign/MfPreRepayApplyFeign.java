package app.component.pact.repay.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.repay.entity.MfPreRepayApply;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfPreRepayApplyBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Aug 14 10:46:31 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfPreRepayApplyFeign {

	@RequestMapping(value = "/mfPreRepayApply/insert")
	public Map<String,Object> insert(@RequestBody MfPreRepayApply mfPreRepayApply, @RequestParam("startFlowFlag") String startFlowFlag)
			throws Exception;

	@RequestMapping(value = "/mfPreRepayApply/delete")
	public void delete(@RequestBody MfPreRepayApply mfPreRepayApply) throws Exception;

	@RequestMapping(value = "/mfPreRepayApply/update")
	public void update(@RequestBody MfPreRepayApply mfPreRepayApply, @RequestParam("startFlowFlag") String startFlowFlag)
			throws Exception;

	@RequestMapping(value = "/mfPreRepayApply/getById")
	public MfPreRepayApply getById(@RequestBody MfPreRepayApply mfPreRepayApply) throws Exception;

	@RequestMapping(value = "/mfPreRepayApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfPreRepayApply/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("preRepayAppId") String preRepayAppId,
			@RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition,
			@RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser, @RequestBody MfPreRepayApply mfPreRepayApply,
			@RequestParam("dataMap") Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfPreRepayApply/checkPreRepayApply")
	public MfPreRepayApply checkPreRepayApply(@RequestBody MfPreRepayApply mfPreRepayApply) throws Exception;

	@RequestMapping(value = "/mfPreRepayApply/updateAppStsAjax")
	public void updateAppStsAjax(@RequestBody MfPreRepayApply mfPreRepayApply) throws Exception;

	@RequestMapping(value = "/mfPreRepayApply/ifCanPreRepay")
	public String ifCanPreRepay(@RequestBody MfPreRepayApply mfPreRepayApply) throws Exception;

}
