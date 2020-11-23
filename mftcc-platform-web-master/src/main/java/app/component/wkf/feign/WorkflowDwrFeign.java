package app.component.wkf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface WorkflowDwrFeign {

	@RequestMapping(value = "/workflowDwr/getTaskInfo")
	public String[] getTaskInfo(@RequestBody Map<String, Object> map) throws Exception;

	@RequestMapping(value = "/workflowDwr/findNextTransition")
	public String findNextTransition(@RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/workflowDwr/findBackTransition")
	public String findBackTransition(@RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/workflowDwr/findExecutorByTransition")
	public String[] findExecutorByTransition(@RequestParam("taskId") String taskId, @RequestParam("transitionName") String transitionName,
			@RequestParam("designateType") String designateType, @RequestParam("branchId") String branchId, @RequestParam("appNo") String appNo)
			throws Exception;

	@RequestMapping(value = "/workflowDwr/findWkfApprovalRole")
	public String findWkfApprovalRole(@RequestParam("wkfRoleNo") String wkfRoleNo) throws Exception;

	@RequestMapping(value = "/workflowDwr/findWkfApprovalUser")
	public String findWkfApprovalUser(@RequestParam("wkfRoleNo") String wkfRoleNo) throws Exception;
	@RequestMapping(value = "/workflowDwr/getTask")
	public String getTask(@RequestParam("taskId")String taskId) throws Exception;
}
