package app.component.wkf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
@FeignClient("mftcc-platform-factor") 
public interface ProcessFeign {
	@RequestMapping(value = "/process/deleteProcessInstance")
	public void deleteProcessInstance(@RequestBody String processInstanceId) throws ServiceException;
	@RequestMapping(value = "/process/doCancel")
	public void doCancel(@RequestBody String processInstanceId) throws ServiceException;
}
