package  app.component.cus.cuslevel.feign;

import app.util.toolkit.Ipage;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.cus.cuslevel.entity.MfCusClassify;
import app.component.cus.cuslevel.entity.MfCusClassifyAdjustHistory;
import app.component.wkf.entity.Result;

/**
* Title: MfCusClassifyAdjustHistoryBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 05 15:21:01 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusClassifyAdjustHistoryFeign {
	
	@RequestMapping(value = "/mfCusClassifyAdjustHistory/insert")
	public void insert(@RequestBody MfCusClassifyAdjustHistory mfCusClassifyAdjustHistory) throws Exception;
	
	@RequestMapping(value = "/mfCusClassifyAdjustHistory/delete")
	public void delete(@RequestBody MfCusClassifyAdjustHistory mfCusClassifyAdjustHistory) throws Exception;
	
	@RequestMapping(value = "/mfCusClassifyAdjustHistory/update")
	public void update(@RequestBody MfCusClassifyAdjustHistory mfCusClassifyAdjustHistory) throws Exception;
	
	@RequestMapping(value = "/mfCusClassifyAdjustHistory/getById")
	public MfCusClassifyAdjustHistory getById(@RequestBody MfCusClassifyAdjustHistory mfCusClassifyAdjustHistory) throws Exception;
	
	@RequestMapping(value = "/mfCusClassifyAdjustHistory/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfCusClassifyAdjustHistory/findByPage")
	Ipage doCommit(@RequestBody MfCusClassifyAdjustHistory mfCusClassifyAdjustHistory) throws Exception;
	
	@RequestMapping("/mfCusClassifyAdjustHistory/startApproveFlow")
	Map<String, Object> startApproveFlow(@RequestBody MfCusClassify mfCusClassify) throws Exception;
	
	@RequestMapping("/mfCusClassifyAdjustHistory/getByFlowWaterId")
	MfCusClassifyAdjustHistory getNewByFlowWaterId(@RequestParam("flowWaterId") String flowWaterId) throws Exception;
	
	@RequestMapping("/mfCusClassifyAdjustHistory/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("nextUser") String nextUser,
			@RequestBody MfCusClassifyAdjustHistory mfCusClassifyAdjustHistory) throws Exception;
	
}
