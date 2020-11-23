package app.component.wkf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface ProcessInstanceFeign {
	/// wFProcessDefinition

	@RequestMapping(value = "/wFProcessInstance/resume")
	public void resume(@RequestParam("processInstanceId") String processInstanceId) throws Exception;

	@RequestMapping(value = "/wFProcessInstance/suspend")
	public void suspend(@RequestParam("processInstanceId") String processInstanceId) throws Exception;

	@RequestMapping(value = "/wFProcessInstance/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFProcessInstance/findByPageAjax")
	public Ipage findByPageAjax(@RequestBody Ipage ipage) throws Exception;

}
