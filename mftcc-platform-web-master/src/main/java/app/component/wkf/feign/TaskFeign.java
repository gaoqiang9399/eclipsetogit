package app.component.wkf.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dhcc.workflow.api.RepositoryService;
import com.dhcc.workflow.api.TaskQuery;
import com.dhcc.workflow.api.model.Transition;
import com.dhcc.workflow.api.task.Task;
import com.dhcc.workflow.pvm.internal.model.ExecutionImpl;
import com.dhcc.workflow.pvm.internal.task.TaskDefinitionImpl;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface TaskFeign {

	@RequestMapping(value = "/wFTask/findByPageAjax")
	public Ipage findByPageAjax(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFTask/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFTask/findByPageForApp")
	public Ipage findByPageForApp(@RequestBody Ipage ipage) throws Exception;

	/*@RequestMapping(value = "/wFTask/rollback")
	public void rollback(@RequestParam("taskId") String taskId, @RequestParam("rollbackTo") String rollbackTo) throws Exception;
	
	@RequestMapping(value = "/wFTask/rollback2")
	public void rollback2(@RequestParam("taskId") String taskId,@RequestParam("message") String message, @RequestParam("rollbackTo") String rollbackTo) throws Exception;*/
	
	@RequestMapping(value = "/wFTask/rollback")
	public void rollback(@RequestParam("taskId") String taskId,@RequestParam("rollback") String rollback, @RequestParam("rollbackTo") String rollbackTo) throws Exception;

	@RequestMapping(value = "/wFTask/recall")
	public void recall(@RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/wFTask/candidate")
	public Ipage candidate(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/wFTask/candidate2")
	public TaskQuery candidate2(@RequestParam("regNo") String regNo,@RequestParam("orgNo") String orgNo) throws Exception;
	
	@RequestMapping(value = "/wFTask/candidateUserId")
	public TaskQuery candidateUserId(@RequestParam("userId") String userId) throws Exception;
	
	@RequestMapping(value = "/wFTask/candidateTaskQuery")
	public TaskQuery candidateTaskQuery(@RequestParam("userId") String userId,@RequestParam("branchId") String branchId) throws Exception;
	
	@RequestMapping(value = "/wFTask/candidateUAB")
	public TaskQuery candidateUAB(@RequestParam("userId") String userId,@RequestParam("branchId") String branchId) throws Exception;

	@RequestMapping(value = "/wFTask/assign")
	public void assign(@RequestParam("taskId") String taskId, @RequestParam("userId") String userId) throws Exception;

	@RequestMapping(value = "/wFTask/getTask")
	public TaskImpl getTask(@RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/wFTask/findExecutionByTaskId")
	public ExecutionImpl findExecutionByTaskId(@RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/wFTask/getTaskDefinition")
	public TaskDefinitionImpl getTaskDefinition(@RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/wFTask/getTransitions")
	public List<Transition> getTransitions(@RequestParam("taskId") String taskId) throws Exception;
	
	@RequestMapping(value = "/wFTask/getTransitionsStr")
	public String getTransitionsStr(@RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/wFTask/getBackTransitions")
	public List<Transition> getBackTransitions(@RequestParam("taskId") String taskId) throws Exception;
	@RequestMapping(value = "/wFTask/refuse")
	public void refuseString(@RequestParam("taskId") String taskId,@RequestParam("message") String message, @RequestParam("userId") String userId) throws Exception;
	
	@RequestMapping(value = "/wFTask/sendedUser")
	public TaskQuery sendedUser(@RequestParam("userId") String userId) throws Exception;


	@RequestMapping(value = "/wFTask/mySendedTask")
	public Ipage mySendedTask(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFTask/myCommitedTask")
	public Ipage myCommitedTask(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFTask/myCommitedTaskForApp")
	public Ipage myCommitedTaskForApp(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFTask/myCommitedTaskForAuth")
	public Ipage myCommitedTaskForAuth(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFTask/myCommitedTaskForEval")
	public Ipage myCommitedTaskForEval(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFTask/myCommitedTaskForExtend")
	public Ipage myCommitedTaskForExtend(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFTask/getTaskURL")
	public String getTaskURL(@RequestParam("taskId") String taskId, @RequestParam("formURL") String formURL) throws Exception;
	
	@RequestMapping(value = "/wFTask/getTaskURL2")
	public String getTaskURL2(@RequestParam("taskId") String taskId, @RequestParam("approveUrl") String approveUrl) throws Exception;
	
	@RequestMapping(value = "/wFTask/getTaskURLAppUrl")
	public String getTaskURLAppUrl(@RequestParam("taskId") String taskId, @RequestParam("appUrl") String appUrl) throws Exception;
	@RequestMapping(value = "/wFTask/openApproveUrl")
	public String openApproveUrl(@RequestParam("taskId") String taskId) throws Exception;
	@RequestMapping(value = "/wFTask/openApproveUrl")
	public String openApproveUrl(@RequestParam("taskId") String taskId, @RequestParam("approved") String approved) throws Exception;

	@RequestMapping(value = "/wFTask/resumeTask")
	public void resumeTask(@RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/wFTask/suspend")
	public void suspend(@RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/wFTask/complete")
	public String complete(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition, @RequestParam("userId") String userId)
			throws Exception;	
	@RequestMapping(value = "/wFTask/completeString")
	public String completeString(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition, @RequestParam("msg") String msg, @RequestParam("userId") String userId)
			throws Exception;	
	@RequestMapping(value = "/wFTask/completeZT")
	public void completeZT(@RequestParam("taskId") String taskId, @RequestParam("taskConstants") String taskConstants, @RequestParam("message") String message,
			@RequestParam("transition") String transition,@RequestParam("userId") String userId)
			throws Exception;
	@RequestMapping(value = "/wFTask/completeByNextUser")
	public void completeByNextUser(@RequestParam("taskId") String taskId, @RequestParam("taskConstants") String taskConstants, @RequestParam("message") String message,
			@RequestParam("transition") String transition,@RequestParam("nextAssignee") String nextAssignee)
					throws Exception;
	@RequestMapping(value = "/wFTask/take")
	public void take(@RequestParam("taskId") String taskId, @RequestParam("userId") String userId) throws Exception;

	@RequestMapping(value = "/wFTask/untake")
	public void untake(@RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/wFTask/findPersonalTasks")
	public List<TaskImpl> findPersonalTasks(@RequestParam("userId") String userId) throws Exception;
	
	@RequestMapping(value = "/wFTask/findPersonalTasksList")
	public List<Task> findPersonalTasksList(@RequestParam("userId") String userId) throws Exception;
	
	@RequestMapping(value = "/wFTask/findGroupTasks")
	public List<TaskImpl> findGroupTasks(@RequestParam("userId") String userId, @RequestParam("branchId") String branchId) throws Exception;

	@RequestMapping(value = "/wFTask/findGroupTasksList")
	public List<Task> findGroupTasksList(@RequestParam("userId") String userId, @RequestParam("branchId") String branchId) throws Exception;

	@RequestMapping(value = "/wFTask/listMyTaskByUser")
	public Ipage listMyTaskByUser(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFTask/listMyTaskByGroup")
	public Ipage listMyTaskByGroup(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFTask/delete")
	public void delete(@RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/wFTask/approvePath")
	public Ipage approvePath(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFTask/approveIdea")
	public Ipage approveIdea(@RequestBody Ipage ipage) throws Exception;
	@RequestMapping(value = "/wFTask/getAssignee")
	public String getAssignee(@RequestParam("taskId") String taskId) throws Exception;
	@RequestMapping(value = "/wFTask/getNextTaskExecutor")
	public String[] getNextTaskExecutor(@RequestParam("taskId") String taskId) throws Exception;
	@RequestMapping(value = "/wFTask/createQuery")
	public TaskQuery createQuery() throws Exception;
	@RequestMapping(value = "/wFTask/resume")
	public void resume(@RequestParam("taskId") String taskId) throws Exception;
	@RequestMapping(value = "/wFTask/setVariables")
	public void setVariables(@RequestParam("taskId") String taskId,@RequestBody Map<String,Object> thisMap) throws Exception;
	@RequestMapping(value = "/wFTask/assignee")
	public TaskQuery assignee(@RequestParam("userId") String userId)throws Exception;
	@RequestMapping(value = "/wFTask/getNext")
	public String[] getNext(@RequestParam("activityType") String activityType, @RequestParam("appId") String appId,
			@RequestParam("name") String name, @RequestParam("id") String id)throws Exception;
	@RequestMapping(value = "/wFTask/getRepositoryService")
	public RepositoryService getRepositoryService() throws Exception;
	
}
