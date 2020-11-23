package app.component.report.feign;

import app.component.report.entity.MfReportQueryConditionConfigSet;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* Title: MfReportQueryConditionConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 24 17:03:14 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfReportQueryConditionConfigSetFeign {

	@RequestMapping(value = "/mfReportQueryConditionConfigSet/insert")
	public void insert(@RequestBody MfReportQueryConditionConfigSet mfReportQueryConditionConfigSet) throws Exception;

	@RequestMapping(value = "/mfReportQueryConditionConfigSet/delete")
	public void delete(@RequestBody MfReportQueryConditionConfigSet mfReportQueryConditionConfigSet) throws Exception;

	@RequestMapping(value = "/mfReportQueryConditionConfigSet/update")
	public void update(@RequestBody MfReportQueryConditionConfigSet mfReportQueryConditionConfigSet) throws Exception;

	@RequestMapping(value = "/mfReportQueryConditionConfigSet/getById")
	public MfReportQueryConditionConfigSet getById(@RequestBody MfReportQueryConditionConfigSet mfReportQueryConditionConfigSet) throws Exception;

	@RequestMapping(value = "/mfReportQueryConditionConfigSet/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
