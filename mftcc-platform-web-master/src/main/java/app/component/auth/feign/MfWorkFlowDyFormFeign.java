package app.component.auth.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfWorkFlowDyFormFeign {
	@RequestMapping("/mfWorkFlowDyForm/findByPageForCallCenter")
	public Ipage findByPageForCallCenter(@RequestBody Ipage ipage) throws Exception;
}
