package app.component.pact.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfReceivablePrincipalInterestFeign {
	
	@RequestMapping(value = "/mfReceivablePrincipalInterest/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

}
