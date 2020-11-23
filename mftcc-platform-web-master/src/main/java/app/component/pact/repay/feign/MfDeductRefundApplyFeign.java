package app.component.pact.repay.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.repay.entity.MfDeductRefundApply;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfDeductRefundApplyBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Aug 22 11:24:01 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfDeductRefundApplyFeign {

	@RequestMapping(value = "/mfDeductRefundApply/insert")
	public Map<String, String> insert(@RequestBody MfDeductRefundApply mfDeductRefundApply, @RequestParam("flowFlag") String flowFlag)
			throws Exception;

	@RequestMapping(value = "/mfDeductRefundApply/delete")
	public void delete(@RequestBody MfDeductRefundApply mfDeductRefundApply) throws Exception;

	@RequestMapping(value = "/mfDeductRefundApply/update")
	public void update(@RequestBody MfDeductRefundApply mfDeductRefundApply, @RequestParam("flowFlag") String flowFlag)
			throws Exception;

	@RequestMapping(value = "/mfDeductRefundApply/getById")
	public MfDeductRefundApply getById(@RequestBody MfDeductRefundApply mfDeductRefundApply) throws Exception;

	@RequestMapping(value = "/mfDeductRefundApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage)
			throws Exception;

	@RequestMapping(value = "/mfDeductRefundApply/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("id") String id, @RequestParam("opinionType") String opinionType,
			@RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition, @RequestParam("opNo") String opNo,
			@RequestParam("nextUser") String nextUser, @RequestBody MfDeductRefundApply mfDeductRefundApply,
			@RequestParam("dataMap") Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfDeductRefundApply/getRefundFincInfo")
	public Map<String, Object> getRefundFincInfo(@RequestBody String fincId) throws Exception;

}
