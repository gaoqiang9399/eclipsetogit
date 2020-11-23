package  app.component.checkoff.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.checkoff.entity.MfBusCheckoffHis;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfBusCheckoffHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Aug 18 11:26:41 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusCheckoffHisFeign {
	
	@RequestMapping(value = "/mfBusCheckoffHis/insert")
	public void insert(@RequestBody MfBusCheckoffHis mfBusCheckoffHis) throws Exception;
	
	@RequestMapping(value = "/mfBusCheckoffHis/delete")
	public void delete(@RequestBody MfBusCheckoffHis mfBusCheckoffHis) throws Exception;
	
	@RequestMapping(value = "/mfBusCheckoffHis/update")
	public void update(@RequestBody MfBusCheckoffHis mfBusCheckoffHis) throws Exception;
	
	@RequestMapping(value = "/mfBusCheckoffHis/getById")
	public MfBusCheckoffHis getById(@RequestBody MfBusCheckoffHis mfBusCheckoffHis) throws Exception;
	
	@RequestMapping(value = "/mfBusCheckoffHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusCheckoffHis") MfBusCheckoffHis mfBusCheckoffHis) throws Exception;
	
	@RequestMapping(value = "/mfBusCheckoffHis/findByCheckOffId")
	public List<MfBusCheckoffHis> findByCheckOffId(@RequestBody MfBusCheckoffHis mfBusCheckoffHis) throws Exception;
	@RequestMapping(value = "/mfBusCheckoffHis/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("pactId")  String pactId,@RequestParam("opinionType")  String opinionType,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition")  String transition,@RequestParam("opNo")  String opNo,@RequestParam("nextUser") String nextUser,@RequestBody Map<String, Object> dataMap) throws Exception;
	
}
