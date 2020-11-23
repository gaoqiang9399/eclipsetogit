package app.component.importexcel.feign;

import app.component.importexcel.entity.MfImpotCheckConfig;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
* Title: MfImpotCheckConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 27 17:08:34 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfImpotCheckConfigFeign {
	
	@RequestMapping(value = "/mfImpotCheckConfig/insert")
	public MfImpotCheckConfig insert(@RequestBody MfImpotCheckConfig mfImpotCheckConfig) throws Exception;
	
	@RequestMapping(value = "/mfImpotCheckConfig/delete")
	public void delete(@RequestBody MfImpotCheckConfig mfImpotCheckConfig) throws Exception;
	
	@RequestMapping(value = "/mfImpotCheckConfig/update")
	public void update(@RequestBody MfImpotCheckConfig mfImpotCheckConfig) throws Exception;
	
	@RequestMapping(value = "/mfImpotCheckConfig/getById")
	public MfImpotCheckConfig getById(@RequestBody MfImpotCheckConfig mfImpotCheckConfig) throws Exception;
	
	@RequestMapping(value = "/mfImpotCheckConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfImpotCheckConfig") MfImpotCheckConfig mfImpotCheckConfig) throws Exception;
}
