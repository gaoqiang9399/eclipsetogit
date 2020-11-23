package  app.component.hzey.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.util.toolkit.Ipage;



/**
* Title: MfBusIcloudManageBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Jul 19 15:07:00 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusProspectiveFeign {
	@RequestMapping(value = "/mfCusProspective/getCusAndApply")
	public Ipage getCusAndApply(@RequestBody Ipage ipage) throws Exception;
}
