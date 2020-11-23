package app.component.app.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.app.entity.MfBusAppKind;

@FeignClient("mftcc-platform-fileService")
public interface AppFeign {
	
	@RequestMapping("/appInterface/getMfBusAppKind")
	MfBusAppKind getMfBusAppKind(@RequestBody MfBusAppKind mfBusAppKind);

}
