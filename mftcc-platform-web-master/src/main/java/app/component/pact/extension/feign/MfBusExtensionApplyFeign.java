package app.component.pact.extension.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.extension.entity.MfBusExtensionApply;
import app.component.pact.extension.entity.MfBusExtensionApproveHis;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfBusExtensionApplyBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 06 11:05:09 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusExtensionApplyFeign {

	@RequestMapping(value = "/mfBusExtensionApply/insert")
	public MfBusExtensionApply insert(@RequestBody MfBusExtensionApply mfBusExtensionApply) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/delete")
	public void delete(@RequestBody MfBusExtensionApply mfBusExtensionApply) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/update")
	public void update(@RequestBody MfBusExtensionApply mfBusExtensionApply) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/getById")
	public MfBusExtensionApply getById(@RequestBody MfBusExtensionApply mfBusExtensionApply) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfBusExtensionApply") MfBusExtensionApply mfBusExtensionApply)
			throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/initInputData")
	public Map<String, Object> initInputData(@RequestBody Map<String,Object> paramMap) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/getExtensionInfoByFincId")
	public Map<String, Object> getExtensionInfoByFincId(@RequestBody MfBusExtensionApply mfBusExtensionApply)
			throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/getAllowExtensionCondition")
	public String getAllowExtensionCondition(@RequestBody String fincId) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/setExtensionPledgeRelation")
	public MfBusExtensionApply setExtensionPledgeRelation(@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/investigationInputData")
	public Map<String, Object> investigationInputData(@RequestBody MfBusExtensionApply mfBusExtensionApply)
			throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/doConfirmInvestigationReport")
	public Map<String, Object> doConfirmInvestigationReport(
			@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/submitProcess")
	public void submitProcess(@RequestBody MfBusExtensionApply mfBusExtensionApply) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/initApproveData")
	public Map<String, Object> initApproveData(@RequestBody MfBusExtensionApply mfBusExtensionApply) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser,
			@RequestBody  Map<String, Object> dataMap)
			throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/initContractSignData")
	public Map<String, Object> initContractSignData(@RequestBody MfBusExtensionApply mfBusExtensionApply)
			throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/saveContractSign")
	public Map<String, Object> saveContractSign(@RequestBody Map<String, Object> formDataMap) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/getExtensionDetailInfo")
	public Map<String, Object> getExtensionDetailInfo(@RequestBody MfBusExtensionApply mfBusExtensionApply)
			throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/updateMfBusExtensionApply")
	public void updateMfBusExtensionApply(@RequestBody MfBusExtensionApply mfBusExtensionApplyUpd) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/doDealRateFloat")
	public MfBusExtensionApply doDealRateFloat(@RequestBody MfBusExtensionApply mfBusExtensionApply) throws Exception;

	@RequestMapping(value = "/mfBusExtensionApply/getByAppId")
	public List<MfBusExtensionApply> getByAppId(@RequestBody MfBusExtensionApply mfBusExtensionApply) throws Exception;

    @RequestMapping(value = "/mfBusExtensionApply/getNewExtensionByFincId")
    public MfBusExtensionApply getNewExtensionByFincId(@RequestBody MfBusExtensionApply mfBusExtensionApply) throws Exception;


    @RequestMapping(value = "/mfBusExtensionApply/doDisagreeExtensionBuss")
    public Map<String, Object> doDisagreeExtensionBuss(@RequestBody MfBusExtensionApply mfBusExtensionApply) throws Exception;


    @RequestMapping(value = "/mfBusExtensionApply/getExtensionDetailInfoNew")
    public Map<String, Object> getExtensionDetailInfoNew(@RequestBody MfBusExtensionApply mfBusExtensionApply)
            throws Exception;


}
