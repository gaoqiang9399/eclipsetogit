package app.component.wkf.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mftcc-platform-factor")
public interface WkfListenerHandlerFeign {

	@RequestMapping(value = "/wkfListenerHandler/eventHandler")
	public void eventHandler(@RequestBody Map<String, Object> map) throws Exception;


}
