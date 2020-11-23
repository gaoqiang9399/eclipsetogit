package app.component.importexcel.feign;

import app.component.importexcel.entity.MfImportRepayPlan;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* Title: MfImportRepayPlanBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 27 17:08:34 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfImportRepayPlanFeign {
	
	@RequestMapping(value = "/mfImportRepayPlan/insert")
	public MfImportRepayPlan insert(@RequestBody MfImportRepayPlan mfImportRepayPlan) throws Exception;
	
	@RequestMapping(value = "/mfImportRepayPlan/delete")
	public void delete(@RequestBody MfImportRepayPlan mfImportRepayPlan) throws Exception;
	
	@RequestMapping(value = "/mfImportRepayPlan/update")
	public void update(@RequestBody MfImportRepayPlan mfImportRepayPlan) throws Exception;
	
	@RequestMapping(value = "/mfImportRepayPlan/getById")
	public MfImportRepayPlan getById(@RequestBody MfImportRepayPlan mfImportRepayPlan) throws Exception;
	
	@RequestMapping(value = "/mfImportRepayPlan/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfImportRepayPlan") MfImportRepayPlan mfImportRepayPlan) throws Exception;
}
