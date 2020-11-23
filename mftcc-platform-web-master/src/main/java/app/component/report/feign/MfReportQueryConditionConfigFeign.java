package  app.component.report.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.report.entity.MfReportQueryConditionConfig;
import app.util.toolkit.Ipage;

/**
* Title: MfReportQueryConditionConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 24 17:03:14 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfReportQueryConditionConfigFeign {
	
	@RequestMapping(value = "/mfReportQueryConditionConfig/insert")
	public void insert(@RequestBody MfReportQueryConditionConfig mfReportQueryConditionConfig) throws Exception;
	
	@RequestMapping(value = "/mfReportQueryConditionConfig/delete")
	public void delete(@RequestBody MfReportQueryConditionConfig mfReportQueryConditionConfig) throws Exception;
	
	@RequestMapping(value = "/mfReportQueryConditionConfig/update")
	public void update(@RequestBody MfReportQueryConditionConfig mfReportQueryConditionConfig) throws Exception;
	
	@RequestMapping(value = "/mfReportQueryConditionConfig/getById")
	public MfReportQueryConditionConfig getById(@RequestBody MfReportQueryConditionConfig mfReportQueryConditionConfig) throws Exception;
	
	@RequestMapping(value = "/mfReportQueryConditionConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfReportQueryConditionConfig") MfReportQueryConditionConfig mfReportQueryConditionConfig) throws Exception;
	
}
