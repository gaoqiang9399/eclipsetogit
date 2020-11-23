package app.component.pact.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.entity.MfBusPact;

@FeignClient("mftcc-platform-fileService")
public interface PactFeign {
	@RequestMapping("/mfBusPact/getByAppId")
	MfBusPact getByAppIdMfBusPact(@RequestParam("appId")String appId);

}
