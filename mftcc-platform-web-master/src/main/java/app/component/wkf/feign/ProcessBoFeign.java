package app.component.wkf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mftcc-platform-factor")
public interface ProcessBoFeign {

	@RequestMapping(value = "/process/deleteProcessInstance")
	public void deleteProcessInstance(@RequestParam("processInstanceId") String processInstanceId) throws Exception;

	@RequestMapping(value = "/process/doCancel")
	public void doCancel(@RequestParam("processInstanceId") String processInstanceId) throws Exception;
}
