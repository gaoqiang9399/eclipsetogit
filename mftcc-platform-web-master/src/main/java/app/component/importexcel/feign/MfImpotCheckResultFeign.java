package app.component.importexcel.feign;

import app.component.importexcel.entity.MfImpotCheckResult;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* Title: MfImpotCheckResultBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 27 17:08:34 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfImpotCheckResultFeign {
	
	@RequestMapping(value = "/mfImpotCheckResult/insert")
	public MfImpotCheckResult insert(@RequestBody MfImpotCheckResult mfImpotCheckResult) throws Exception;
	
	@RequestMapping(value = "/mfImpotCheckResult/delete")
	public void delete(@RequestBody MfImpotCheckResult mfImpotCheckResult) throws Exception;
	
	@RequestMapping(value = "/mfImpotCheckResult/update")
	public void update(@RequestBody MfImpotCheckResult mfImpotCheckResult) throws Exception;
	
	@RequestMapping(value = "/mfImpotCheckResult/getById")
	public MfImpotCheckResult getById(@RequestBody MfImpotCheckResult mfImpotCheckResult) throws Exception;
	
	@RequestMapping(value = "/mfImpotCheckResult/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
