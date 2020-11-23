package app.component.pact.fiveclass.feign;


import app.component.pact.fiveclass.entity.MfFiveclassSummaryApply;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Title: MfFiveclassModelBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Mar 10 12:26:58 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfFiveclassSummaryApplyFeign {

	@RequestMapping(value = "/mfFiveclassSummaryApply/insert")
	public MfFiveclassSummaryApply insert(@RequestBody MfFiveclassSummaryApply mfFiveclassSummaryApply) throws Exception;
	@RequestMapping(value = "/mfFiveclassSummaryApply/getById",produces = "application/json; charset=utf-8")
	public MfFiveclassSummaryApply getById(@RequestBody MfFiveclassSummaryApply mfFiveclassSummaryApply) throws Exception;
	@RequestMapping(value = "/mfFiveclassSummaryApply/doCommit")
	public Result doCommit(@RequestBody Map<String, Object> dataMap)throws Exception;
	@RequestMapping(value = "/mfFiveclassSummaryApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage)
			throws Exception;
	@RequestMapping(value = "/mfFiveclassSummaryApply/insertForStage")
	public MfFiveclassSummaryApply insertForStage(@RequestBody MfFiveclassSummaryApply mfFiveclassSummaryApply) throws Exception;
	@RequestMapping(value = "/mfFiveclassSummaryApply/doCommitForStage")
	public Result doCommitForStage(@RequestBody Map<String, Object> dataMap)throws Exception;
}
