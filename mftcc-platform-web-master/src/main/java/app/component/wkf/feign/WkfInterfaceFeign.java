package app.component.wkf.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dhcc.workflow.api.history.HistoryTask;
import com.dhcc.workflow.api.model.Transition;
import com.dhcc.workflow.api.task.Task;
import com.dhcc.workflow.pvm.internal.history.model.HistoryTaskImpl;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.ServiceException;
import app.component.wkf.entity.Result;
import app.component.wkf.entity.WfCopyer;
import app.component.wkf.entity.WkfApprovalOpinion;
import app.component.wkf.entity.WkfApprovalUser;
import app.component.wkf.entity.WkfBusinessConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@FeignClient("mftcc-platform-factor")
public interface WkfInterfaceFeign {

	/**
	 * @param processKey
	 *            流程Id
	 * @param obj
	 *            业务对象
	 * @param appNo
	 *            业务主键值
	 * @param primaryKeyName
	 *            业务主键名称
	 * @param opNo
	 *            操作员
	 * @throws Exception
	 *             TODO
	 */
	@RequestMapping(value = "/wkfInterface/startProcess")
	public void startProcess(@RequestParam("processKey") String processKey, @RequestBody Object obj, @RequestParam("appNo") String appNo,
			@RequestParam("primaryKeyName") String primaryKeyName, @RequestParam("opNo") String opNo, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("optType") String optType, @RequestParam("pasMaxNo") String pasMaxNo,
			@RequestParam("pasMinNo") String pasMinNo, @RequestParam("userRegName") String userRegName,
			@RequestParam("userGetBizType") String userGetBizType, @RequestParam("userOrgNo") String userOrgNo) throws Exception;

	/**
	 * @param processKey
	 *            流程Id
	 * @param obj
	 *            业务对象
	 * @param appNo
	 *            业务主键值
	 * @param primaryKeyName
	 *            业务主键名称
	 * @param opNo
	 *            操作员
	 * @throws Exception
	 */
	@RequestMapping(value = "/wkfInterface/startProcess2")
	public void startProcess(@RequestParam("processKey") String processKey, @RequestBody Object obj, @RequestParam("appNo") String appNo,
			@RequestParam("primaryKeyName") String primaryKeyName, @RequestParam("opNo") String opNo, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("optType") String optType, @RequestParam("pasMaxNo") String pasMaxNo,
			@RequestParam("pasMinNo") String pasMinNo, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("userRegName") String userRegName, @RequestParam("userGetBizType") String userGetBizType,
			@RequestParam("userOrgNo") String userOrgNo) throws Exception;

	/**
	 * 在流程审批URl中拼接&nextUser=#{***} 在流程提交时根据nextUser是否为空指定下一审批人
	 * 
	 * @param processKey
	 *            流程Id
	 * @param obj
	 *            业务对象
	 * @param appNo
	 *            业务主键值
	 * @param primaryKeyName
	 *            业务主键名称
	 * @param opNo
	 *            操作员
	 * @return String 流程TaskId
	 * @throws Exception
	 *             TODO
	 */
	@RequestMapping(value = "/wkfInterface/startProcessNextUser")
	public String startProcessNextUser(@RequestParam("processKey") String processKey, @RequestBody Object obj, @RequestParam("appNo") String appNo,
			@RequestParam("primaryKeyName") String primaryKeyName, @RequestParam("opNo") String opNo, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("optType") String optType, @RequestParam("pasMaxNo") String pasMaxNo,
			@RequestParam("pasMinNo") String pasMinNo, @RequestParam("currentUser") String currentUser,
			@RequestParam("userRegName") String userRegName, @RequestParam("userGetBizType") String userGetBizType,
			@RequestParam("userOrgNo") String userOrgNo) throws Exception;

	/**
	 * 在流程审批URl中拼接&nextUser=#{***} 在流程提交时根据nextUser是否为空指定下一审批人
	 * 
	 * @param processKey
	 *            流程Id
	 * @param obj
	 *            业务对象
	 * @param appNo
	 *            业务主键值
	 * @param primaryKeyName
	 *            业务主键名称
	 * @param opNo
	 *            操作员
	 * @return String 流程TaskId
	 * @throws Exception
	 */
	@RequestMapping(value = "/wkfInterface/startProcessNextUser2")
	public String startProcessNextUser(@RequestParam("processKey") String processKey, @RequestBody Object obj, @RequestParam("appNo") String appNo,
			@RequestParam("primaryKeyName") String primaryKeyName, @RequestParam("opNo") String opNo, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("optType") String optType, @RequestParam("pasMaxNo") String pasMaxNo,
			@RequestParam("pasMinNo") String pasMinNo, @RequestParam("nextUser") String nextUser, @RequestParam("currentUser") String currentUser,
			@RequestParam("userRegName") String userRegName, @RequestParam("userGetBizType") String userGetBizType,
			@RequestParam("userOrgNo") String userOrgNo) throws Exception;

	/**
	 * @param processKey
	 *            流程Id
	 * 
	 * @param obj
	 *            业务对象
	 * 
	 * @param appNo
	 *            业务主键值
	 * 
	 * @param primaryKeyName
	 *            业务主键名称
	 * 
	 * @param opNo
	 *            操作员
	 * @throws Exception
	 *             TODO
	 */
	@RequestMapping(value = "/wkfInterface/startOnlyProcess")
	public void startOnlyProcess(@RequestParam("processKey") String processKey, @RequestBody Object obj, @RequestParam("appNo") String appNo,
			@RequestParam("primaryKeyName") String primaryKeyName, @RequestParam("opNo") String opNo) throws Exception;

	/**
	 * @param processKey
	 *            流程Id
	 * @param obj
	 *            业务对象
	 * @param appNo
	 *            业务主键值
	 * @param primaryKeyName
	 *            业务主键名称
	 * @param opNo
	 *            操作员
	 * @throws Exception
	 */
	@RequestMapping(value = "/wkfInterface/startProcessPointUsers")
	public void startProcessPointUsers(@RequestParam("processKey") String processKey, @RequestBody Object obj, @RequestParam("appNo") String appNo,
			@RequestParam("primaryKeyName") String primaryKeyName, @RequestParam("opNo") String opNo, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("optType") String optType, @RequestParam("pasMaxNo") String pasMaxNo,
			@RequestParam("pasMinNo") String pasMinNo, @RequestParam("pointNextUser") String pointNextUser,
			@RequestParam("userRegName") String userRegName, @RequestParam("userGetBizType") String userGetBizType,
			@RequestParam("userOrgNo") String userOrgNo) throws Exception;

	/**
	 * @param taskId
	 *            当前任务Id
	 * 
	 * @param appNo
	 *            业务主键
	 * 
	 * @return Task
	 */
	@RequestMapping(value = "/wkfInterface/getTask")
	public TaskImpl getTask(@RequestParam("appNo") String appNo, @RequestParam("taskId") String taskId) throws Exception;

	/**
	 * @param taskId
	 *            当前任务Id
	 * 
	 * @param approvalOpinion
	 *            审批意见
	 * 
	 * @param transition
	 *            跳转路径
	 * 
	 * @param nextUser
	 *            下一环节审批人
	 * 
	 * @return Result
	 */
	@RequestMapping(value = "/wkfInterface/agree")
	public Result agree(@RequestParam("taskId") String taskId, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser, @RequestParam("opNo") String opNo)
			throws Exception;

	/**
	 * @param taskId
	 *            当前任务Id
	 * 
	 * @param approvalOpinion
	 *            审批意见
	 * 
	 * @param opNo
	 *            操作员
	 * 
	 * @return Result
	 */
	@RequestMapping(value = "/wkfInterface/refuse")
	public Result refuse(@RequestParam("taskId") String taskId, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("opNo") String opNo) throws Exception;

	/**
	 * @param taskId
	 *            当前任务Id
	 * 
	 * @param approvalOpinion
	 *            审批意见
	 * 
	 * @param transition
	 *            跳转路径
	 * 
	 * @return Result
	 */
	@RequestMapping(value = "/wkfInterface/rollBack")
	public Result rollBack(@RequestParam("taskId") String taskId, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("transition") String transition) throws Exception;

	/**
	 * @param taskId
	 *            当前任务Id
	 * @param approvalOpinion
	 *            审批意见
	 * @param transition
	 *            跳转路径
	 * @param opNo
	 *            当前操作员
	 * @return Result
	 */
	@RequestMapping(value = "/wkfInterface/rollBack")
	public Result rollBack(@RequestParam("taskId") String taskId, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("transition") String transition, @RequestParam("opNo") String opNo) throws Exception;

	/**
	 * @param taskId
	 *            当前任务Id
	 */
	@RequestMapping(value = "/wkfInterface/rollbackToDefaultNode")
	public Result rollbackToDefaultNode(@RequestParam("taskId") String taskId, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("opNo") String opNo) throws Exception;

	/**
	 * @see 会签任务
	 */
	@RequestMapping(value = "/wkfInterface/getSignTask")
	public String getSignTask() throws Exception;

	/**
	 * @see 结束状态
	 */
	@RequestMapping(value = "/wkfInterface/getEndSts")
	public String getEndSts() throws Exception;

	/**
	 * @param taskId
	 *            当前任务Id
	 * @param opinionType
	 *            意见类型
	 * @param approvalOpinion
	 *            审批意见
	 * @param transition
	 *            跳转路径
	 * @param opNo
	 *            操作员
	 * @param nextUser
	 *            下一环节审批人
	 * @return Result 成功返回 Result
	 */
	@RequestMapping(value = "/wkfInterface/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("opinionType") String opinionType,
			@RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition, @RequestParam("opNo") String opNo,
			@RequestParam("nextUser") String nextUser) throws Exception;

	/**
	 * @param 申请号
	 */
	@RequestMapping(value = "/wkfInterface/doReCall")
	public void doReCall(@RequestParam("appNo") String appNo) throws Exception;

	/**
	 * @param 任务ID
	 */
	@RequestMapping(value = "/wkfInterface/findNextTransition")
	public List<Transition> findNextTransition(@RequestParam("taskId") String taskId) throws Exception;

	/**
	 * @ 获取所有审批用户
	 */
	@RequestMapping(value = "/wkfInterface/getAllList")
	public List<WkfApprovalUser> getAllList(@RequestBody WkfApprovalUser wkfApprovalUser) throws Exception;

	/**
	 * 保存审批意见
	 */
	@RequestMapping(value = "/wkfInterface/doSave")
	public void doSave(@RequestParam("taskId") String taskId, @RequestParam("opinionType") String opinionType,
			@RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("opNo") String opNo) throws Exception;

	/**
	 * @param appNo
	 *            当前业务号
	 * 
	 * @param taskId
	 *            当前任务Id
	 * 
	 * @param opNo
	 *            操作员
	 */
	@RequestMapping(value = "/wkfInterface/getAllApprovalOpinion")
	public List<HistoryTask> getAllApprovalOpinion(@RequestParam("executionId") String executionId) throws Exception;

	@RequestMapping(value = "/wkfInterface/getAllApprovalOpinion")
	public List<WkfApprovalOpinion> getAllApprovalOpinion(@RequestBody WkfApprovalOpinion wkfApprovalOpinion) throws Exception;

	/**
	 * @param taskId
	 *            当前任务Id
	 */
	@RequestMapping(value = "/wkfInterface/getApprovalRoleNo")
	public String getApprovalRoleNo(@RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/wkfInterface/updateAppValue")
	public void updateAppValue(@RequestBody Object obj, @RequestParam("appNo") String appNo) throws Exception;

	@RequestMapping(value = "/wkfInterface/getRefuseApprovalOpinion")
	public HistoryTaskImpl getRefuseApprovalOpinion(@RequestParam("appNo") String appNo) throws Exception;

	@RequestMapping(value = "/wkfInterface/getLastOp")
	public String getLastOp(@RequestParam("appNo") String appNo, @RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/wkfInterface/getWkfInstanceId")
	public String getWkfInstanceId(@RequestParam("appNo") String appNo) throws Exception;

	@RequestMapping(value = "/wkfInterface/getUpOneOrg")
	public String getUpOneOrg(@RequestParam("brNo") String brNo) throws Exception;

	@RequestMapping(value = "/wkfInterface/getUpTwoOrg")
	public String getUpTwoOrg(@RequestParam("brNo") String brNo) throws Exception;

	@RequestMapping(value = "/wkfInterface/doCancelByAppNo")
	public void doCancelByAppNo(@RequestParam("appNo") String appNo) throws Exception;

	@RequestMapping(value = "/wkfInterface/deleteProcessInstance")
	public void deleteProcessInstance(@RequestParam("appNo") String appNo) throws Exception;

	@RequestMapping(value = "/wkfInterface/suspend")
	public void suspend(@RequestParam("appNo") String appNo) throws Exception;

	@RequestMapping(value = "/wkfInterface/deleteProcessCascade")
	public void deleteProcessCascade(@RequestParam("appNo") String appNo) throws Exception;

	@RequestMapping(value = "/wkfInterface/getLoanApprovalOpinionList")
	public List<WkfApprovalOpinion> getLoanApprovalOpinionList(@RequestBody WkfApprovalOpinion wkfApprovalOpinion) throws Exception;

	@RequestMapping(value = "/wkfInterface/getApplyApprovalOpinionList")
	public List<WkfApprovalOpinion> getApplyApprovalOpinionList(@RequestBody WkfApprovalOpinion wkfApprovalOpinion) throws Exception;

	/**
	 * @see 根据业务主键获取审批意见历史
	 * @param appNo
	 *            业务主键
	 */
	@RequestMapping(value = "/wkfInterface/getWkfTaskHisList")
	public List<WkfApprovalOpinion> getWkfTaskHisList(@RequestParam("appNo") String appNo) throws Exception;

	@RequestMapping(value = "/wkfInterface/getDefaultOpinion")
	public String getDefaultOpinion() throws Exception;

	@RequestMapping(value = "/wkfInterface/setDefaultOpinion")
	public void setDefaultOpinion(@RequestParam("defaultOpinion") String defaultOpinion) throws Exception;

	/**
	 * @see 查询上一个审批任务
	 * @param taskId
	 *            当前任务Id
	 */
	@RequestMapping(value = "/wkfInterface/getLastTask")
	public HistoryTask getLastTask(@RequestParam("taskId") String taskId) throws Exception;

	/**
	 * @see 查询上一个审批任务
	 * @param taskId
	 *            当前任务Id
	 */
	@RequestMapping(value = "/wkfInterface/getHistTask")
	public HistoryTask getHistTask(@RequestParam("taskId") String taskId) throws Exception;

	@RequestMapping(value = "/wkfInterface/findByObjName")
	public List<WkfBusinessConfig> findByObjName(@RequestBody WkfBusinessConfig wkfBusinessConfig) throws Exception;

	/**
	 * 通过用户编号获取审批角色号
	 * 
	 * @param userNo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getWkfRoleNoForUserNo")
	public String getWkfRoleNoForUserNo(@RequestParam("userNo") String userNo) throws Exception;

	/**
	 * 通过用户编号获取审批角色号数组集合
	 * 
	 * @param userNo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getWkfRoleNoForUserNoList")
	public List<String> getWkfRoleNoForUserNoList(@RequestParam("userNo") String userNo) throws Exception;

	@RequestMapping(value = "/wkfInterface/getTaskUser")
	public String[] getTaskUser(@RequestBody Task task) throws Exception;

	@RequestMapping(value = "/wkfInterface/getNextUsers")
	public String getNextUsers(@RequestBody Task task, @RequestParam("orgNo") String orgNo) throws Exception;

	/**
	 * 通过角色编号获取审批用户号和用户名的数组集合
	 * 
	 * @param roleArr
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getWkfUserForRoleArr")
	public String[] getWkfUserForRoleArr(@RequestBody String[] roleArr) throws Exception;

	/**
	 * 根据taskId获取流程抄送人
	 * 
	 * @param taskId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getCopyerForTaskId")
	public WfCopyer getCopyerForTaskId(@RequestParam("taskId") String taskId) throws Exception;

	/**
	 * 根据dbid获取流程抄送人
	 * 
	 * @param dbid
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getWfCopyerById")
	public WfCopyer getWfCopyerById(@RequestParam("dbid") String dbid) throws Exception;

	/**
	 * 根据审批角色号获取审批用户
	 * 
	 * @param wkfRoleNo
	 * @param brNo
	 *            模糊查询 前三位为本区域
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getUserBrByRoleForList")
	public List<Map<String, String>> getUserBrByRoleForList(@RequestParam("wkfRoleNo") String wkfRoleNo, @RequestParam("brNo") String brNo)
			throws Exception;

	/**
	 * 根据任务编号获取下一个节点的任务信息
	 * 
	 * @param taskId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getNextTaskById")
	public JSONObject getNextTaskById(@RequestParam("taskId") String taskId) throws Exception;

	/**
	 * 根据用户编号获取该用户存在的流程定义信息
	 * 
	 * @param userNo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getWkfDefinitionByUser")
	public JSONArray getWkfDefinitionByUser(@RequestParam("userNo") String userNo) throws Exception;

	/**
	 * 
	 * 方法描述： 获得当前岗位前面审批过得岗位
	 * 
	 * @param appNo
	 * @param opNo
	 * @return
	 * @throws Exception
	 *             JSONArray
	 * @author 沈浩兵
	 * @date 2017-7-11 上午11:54:05
	 */
	@RequestMapping(value = "/wkfInterface/getBefNodes")
	public JSONArray getBefNodes(@RequestParam("appNo") String appNo, @RequestParam("opNo") String opNo) throws Exception;

	/**
	 * 
	 * 方法描述： 获得提交到当前审批岗位的岗位信息， 用于在发回到的岗位再次提交
	 * 
	 * @param taskId
	 * @param appNo
	 * @return
	 * @throws Exception
	 *             JSONObject
	 * @author 沈浩兵
	 * @date 2017-7-11 上午11:54:20
	 */
	@RequestMapping(value = "/wkfInterface/getLstResult")
	public JSONObject getLstResult(@RequestParam("taskId") String taskId, @RequestParam("appNo") String appNo) throws Exception;

	/**
	 * 
	 * 方法描述： 发回重审提交到指定岗位
	 * 
	 * @param taskId
	 * @param approvalOpinion
	 * @param approveType
	 * @param rollbackName
	 * @return
	 * @throws Exception
	 *             Result
	 * @author 沈浩兵
	 * @date 2017-7-11 上午11:58:24
	 */
	@RequestMapping(value = "/wkfInterface/rollBackToDesignName")
	public Result rollBackToDesignName(@RequestParam("taskId") String taskId, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("approveType") String approveType, @RequestParam("rollbackName") String rollbackName) throws Exception;

	/**
	 * 
	 * @param oldProcessKey
	 *            要复制的流程定义标识
	 * @param newProcessKey
	 *            复制后的流程定义标识(key)
	 * @param newProcessName
	 *            复制后的流程名称
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wkfInterface/copyWkfDefinitionById")
	public boolean copyWkfDefinitionById(@RequestParam("oldProcessKey") String oldProcessKey, @RequestParam("newProcessKey") String newProcessKey,
			@RequestParam("newProcessName") String newProcessName) throws Exception;

	/**
	 * 删除指定的流程
	 * 
	 * @param processKey
	 *            要删除的流程定义标识
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = "/wkfInterface/deleteWkfDefinitionById")
	public boolean deleteWkfDefinitionById(@RequestParam("processKey") String processKey) throws Exception;

	/**
	 * 根据流程定义获取第一个审批节点的审批用户
	 * 
	 * @param processId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getUserForFristTask")
	public List<Map<String, Object>> getUserForFristTask(@RequestParam("processId") String processId) throws Exception;

}
