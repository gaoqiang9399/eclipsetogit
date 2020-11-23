package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.cus.entity.MfCusAdministrativePenalty;
import app.util.toolkit.Ipage;



/**
* Title: MfCusAdministrativePenaltyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Apr 23 16:01:02 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusAdministrativePenaltyFeign {
	@RequestMapping(value = "/mfCusAdministrativePenalty/insert")
	public void insert(@RequestBody MfCusAdministrativePenalty mfCusAdministrativePenalty) throws Exception;
	@RequestMapping(value = "/mfCusAdministrativePenalty/delete")
	public void delete(@RequestBody MfCusAdministrativePenalty mfCusAdministrativePenalty) throws Exception;
	@RequestMapping(value = "/mfCusAdministrativePenalty/update")
	public void update(@RequestBody MfCusAdministrativePenalty mfCusAdministrativePenalty) throws Exception;
	@RequestMapping(value = "/mfCusAdministrativePenalty/getById")
	public MfCusAdministrativePenalty getById(@RequestBody MfCusAdministrativePenalty mfCusAdministrativePenalty) throws Exception;
	@RequestMapping(value = "/mfCusAdministrativePenalty/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusAdministrativePenalty") MfCusAdministrativePenalty mfCusAdministrativePenalty) throws Exception;
	
}
