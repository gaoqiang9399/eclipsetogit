package app.component.wkfinterface;

import java.lang.reflect.InvocationTargetException;
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
public interface WkfInterfaceFeign 
{
	/**
	 * @param processKey 流程Id
	 * 
	 * @param obj 业务对象
	 * 
	 * @param appNo 业务主键值
	 * 
	 * @param primaryKeyName 业务主键名称
	 * 
	 * @param opNo 操作员
	 * @throws Exception TODO
	 */
	@RequestMapping(value = "/wkfInterface/startProcess")
	public void startProcess(@RequestParam("processKey") String processKey,@RequestBody Object obj,@RequestParam("appNo") String appNo,@RequestParam("primaryKeyName") String primaryKeyName,@RequestParam("opNo") String opNo,@RequestParam("title") String title,@RequestParam("content") String content,@RequestParam("optType") String optType,@RequestParam("pasMaxNo") String pasMaxNo,@RequestParam("pasMinNo") String pasMinNo) throws Exception;
	
	/**
	 * 
	 * 方法描述：传操作员参数的流程启动
	 * @param processKey
	 * @param obj
	 * @param appNo
	 * @param primaryKeyName
	 * @param opNo
	 * @param title
	 * @param content
	 * @param optType
	 * @param pasMaxNo
	 * @param pasMinNo
	 * @param opName
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 * void
	 * @author zhs
	 * @date 2017-10-31 上午11:26:23
	 */
	@RequestMapping(value = "/wkfInterface/startProcessWithUser")
	public void startProcessWithUser(@RequestParam("processKey") String processKey,@RequestBody Object obj,@RequestParam("appNo") String appNo,@RequestParam("primaryKeyName") String primaryKeyName,@RequestParam("opNo") String opNo,@RequestParam("title") String title,@RequestParam("content") String content,@RequestParam("optType") String optType,@RequestParam("pasMaxNo") String pasMaxNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("opName") String opName,@RequestParam("brNo") String brNo) throws Exception;
	/**
	 * 在流程审批URl中拼接&nextUser=#{***} 在流程提交时根据nextUser是否为空指定下一审批人
	 * 
	 * @param processKey 流程Id
	 * 
	 * @param obj 业务对象
	 * 
	 * @param appNo 业务主键值
	 * 
	 * @param primaryKeyName 业务主键名称
	 * 
	 * @param opNo 操作员
	 * 
	 * @return String 流程TaskId
	 * @throws Exception TODO
	 */
	@RequestMapping(value = "/wkfInterface/startProcessNextUser")
	public String startProcessNextUser(@RequestParam("processKey") String processKey,@RequestBody Object obj,@RequestParam("appNo") String appNo,@RequestParam("primaryKeyName") String primaryKeyName,@RequestParam("opNo") String opNo,@RequestParam("title") String title,@RequestParam("content") String content,@RequestParam("optType") String optType,@RequestParam("pasMaxNo") String pasMaxNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("regName") String regName,@RequestParam("orgNo") String orgNo) throws Exception;
	/**
	 * 在流程审批URl中拼接&nextUser=#{***} 在流程提交时根据nextUser是否为空指定下一审批人
	 * 
	 * @param processKey 流程Id
	 * 
	 * @param obj 业务对象
	 * 
	 * @param appNo 业务主键值
	 * 
	 * @param primaryKeyName 业务主键名称
	 * 
	 * @param opNo 操作员
	 * 
	 * @return String 流程TaskId
	 * @throws Exception TODO
	 */
	@RequestMapping(value = "/wkfInterface/startProcessNextUser1")
	public String startProcessNextUser1(@RequestParam("processKey") String processKey,@RequestBody Object obj,@RequestParam("appNo") String appNo,@RequestParam("primaryKeyName") String primaryKeyName,@RequestParam("opNo") String opNo,@RequestParam("title") String title,@RequestParam("content") String content,@RequestParam("optType") String optType,@RequestParam("pasMaxNo") String pasMaxNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("nextUser") String nextUser,@RequestParam("regName") String regName,@RequestParam("orgNo") String orgNo) throws Exception;
	
	/**
	 * @param processKey 流程Id
	 * 
	 * @param obj 业务对象
	 * 
	 * @param appNo 业务主键值
	 * 
	 * @param primaryKeyName 业务主键名称
	 * 
	 * @param opNo 操作员
	 * @throws Exception TODO
	 */
	@RequestMapping(value = "/wkfInterface/startOnlyProcess")
	public abstract void startOnlyProcess(@RequestParam("processKey") String processKey,@RequestBody Object obj,@RequestParam("appNo") String appNo,@RequestParam("primaryKeyName") String primaryKeyName,@RequestParam("opNo") String opNo) throws Exception;
	
	/**
	 * @param processKey 流程Id
	 * 
	 * @param obj 业务对象
	 * 
	 * @param appNo 业务主键值
	 * 
	 * @param primaryKeyName 业务主键名称
	 * 
	 * @param opNo 操作员
	 * @throws Exception TODO
	 */
	@RequestMapping(value = "/wkfInterface/startProcessPointUsers")
	public void startProcessPointUsers(@RequestParam("processKey") String processKey,@RequestBody Object obj,@RequestParam("appNo") String appNo,@RequestParam("primaryKeyName") String primaryKeyName,@RequestParam("opNo") String opNo,@RequestParam("title") String title,@RequestParam("content") String content,@RequestParam("optType") String optType,@RequestParam("pasMaxNo") String pasMaxNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("pointNextUser") String pointNextUser,@RequestParam("regName") String regName, @RequestParam("orgNo") String orgNo) throws Exception;
	
	/**
	 * @param taskId 当前任务Id
	 * 
	 * @param appNo 业务主键
	 * 
	 * @return Task
	 */
	@RequestMapping(value = "/wkfInterface/getTask")
	public TaskImpl getTask(@RequestParam("appNo") String appNo,@RequestParam(value="taskId",required=false) String taskId)throws ServiceException; 
	
	@RequestMapping(value = "/wkfInterface/getTaskWithUser")
	public TaskImpl getTaskWithUser(@RequestParam("appNo") String appNo,@RequestParam(value="taskId",required=false) String taskId,@RequestParam("appOpNo") String appOpNo)throws ServiceException; 
	/**
	 * @param taskId 当前任务Id
	 * 
	 * @param approvalOpinion 审批意见
	 * 
	 * @param transition 跳转路径
	 * 
	 * @param nextUser 下一环节审批人
	 * 
	 * @return Result
	 */
	@RequestMapping(value = "/wkfInterface/agree")
	public Result agree(@RequestBody String taskId,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition") String transition,@RequestParam("nextUser") String nextUser,@RequestParam("opNo") String opNo)throws ServiceException;
	/**
	 * @param taskId 当前任务Id
	 * 
	 * @param approvalOpinion 审批意见
	 * 
	 * @param opNo 操作员
	 * 
	 * @return Result
	 */
	@RequestMapping(value = "/wkfInterface/refuse")
	public Result refuse(@RequestBody String taskId,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("opNo") String opNo)throws ServiceException ;
	/**
	 * @param taskId 当前任务Id
	 * 
	 * @param approvalOpinion 审批意见
	 * 
	 * @param transition 跳转路径
	 * 
	 * @return Result
	 */
	@RequestMapping(value = "/wkfInterface/rollBack")
	public Result rollBack(@RequestBody String taskId,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition") String transition)throws ServiceException ;
	/**
	 * @param taskId 当前任务Id
	 * 
	 * @param approvalOpinion 审批意见
	 * 
	 * @param transition 跳转路径
	 * 
	 * @param opNo 当前操作员
	 * 
	 * @return Result
	 */
	@RequestMapping(value = "/wkfInterface/rollBack1")
	public Result rollBack1(@RequestBody String taskId,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition") String transition,@RequestParam("opNo") String opNo)throws ServiceException ;
	/**
	 * @param taskId 当前任务Id
	 */
	@RequestMapping(value = "/wkfInterface/rollbackToDefaultNode")
	public Result rollbackToDefaultNode(@RequestBody String taskId,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("opNo") String opNo)throws ServiceException ;
	/**
	 * @see 会签任务
	 */
	@RequestMapping(value = "/wkfInterface/getSignTask")
	public String getSignTask()throws ServiceException ;
	/**
	 * @see 结束状态
	 */
	@RequestMapping(value = "/wkfInterface/getEndSts")
	public String getEndSts()throws ServiceException ;
	/**
	 * 
	 * @param taskId 当前任务Id
	 * 
	 * @param opinionType 意见类型
	 * 
	 * @param approvalOpinion 审批意见
	 * 
	 * @param transition 跳转路径
	 * 
	 * @param opNo 操作员
	 * 
	 * @param nextUser 下一环节审批人
	 * 
	 * @return Result 成功返回 Result
	 */
	@RequestMapping(value = "/wkfInterface/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("opinionType") String opinionType,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition") String transition,@RequestParam("opNo") String opNo,@RequestParam("nextUser") String nextUser)throws ServiceException;
	/**
	 * @param 申请号
	 */
	@RequestMapping(value = "/wkfInterface/doReCall")
	public void doReCall(@RequestBody String appNo,@RequestParam("regNo") String regNo)throws ServiceException;
	/**
	 * @param 任务ID
	 */
	@RequestMapping(value = "/wkfInterface/findNextTransition")
	public List<Transition> findNextTransition(@RequestBody String taskId)throws ServiceException;
	/**
	 * @ 获取所有审批用户
	 */
	@RequestMapping(value = "/wkfInterface/getAllList")
	public List<WkfApprovalUser> getAllList(@RequestBody WkfApprovalUser wkfApprovalUser)throws ServiceException;
	/**
	 * 保存审批意见
	 */
	@RequestMapping(value = "/wkfInterface/doSave")
	public void doSave(@RequestParam("taskId") String taskId,@RequestParam("opinionType") String opinionType,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("opNo") String opNo)throws ServiceException;
	/**
	 * @param appNo 当前业务号
	 * 
	 * @param taskId 当前任务Id
	 * 
	 * @param opNo 操作员
	 */
	@RequestMapping(value = "/wkfInterface/getAllApprovalOpinion")
	public List<HistoryTask> getAllApprovalOpinion(@RequestBody String executionId)throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/getAllApprovalOpinion")
	public List<WkfApprovalOpinion> getAllApprovalOpinion(@RequestBody WkfApprovalOpinion wkfApprovalOpinion)throws ServiceException;
	/**
	 * @param taskId 当前任务Id
	 */
	@RequestMapping(value = "/wkfInterface/getApprovalRoleNo")
	public String getApprovalRoleNo(@RequestBody String taskId)throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/updateAppValue")
	public void updateAppValue(@RequestBody Object obj,@RequestParam("appNo") String appNo) throws Exception;
	
	@RequestMapping(value = "/wkfInterface/getRefuseApprovalOpinion")
	public HistoryTaskImpl getRefuseApprovalOpinion(@RequestBody String appNo)throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/getLastOp")
	public String getLastOp(@RequestBody String appNo,@RequestParam("taskId") String taskId) throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/getWkfInstanceId")
	public String getWkfInstanceId(@RequestBody String appNo) throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/getUpOneOrg")
	public String getUpOneOrg(@RequestBody String brNo)throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/getUpTwoOrg")
	public String getUpTwoOrg(@RequestBody String brNo)throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/doCancelByAppNo")
	public void doCancelByAppNo(@RequestBody String appNo) throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/deleteProcessInstance")
	public void deleteProcessInstance(@RequestBody String appNo) throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/suspend")
	public void suspend(@RequestBody String appNo) throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/deleteProcessCascade")
	public void deleteProcessCascade(@RequestBody String appNo) throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/getLoanApprovalOpinionList")
	public List<WkfApprovalOpinion> getLoanApprovalOpinionList(@RequestBody WkfApprovalOpinion wkfApprovalOpinion)throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/getApplyApprovalOpinionList")
	public List<WkfApprovalOpinion> getApplyApprovalOpinionList(@RequestBody WkfApprovalOpinion wkfApprovalOpinion)throws ServiceException;
	
	/**
	 * @see 根据业务主键获取审批意见历史
	 * @param appNo 业务主键
	 */
	@RequestMapping(value = "/wkfInterface/getWkfTaskHisList")
	public List<WkfApprovalOpinion> getWkfTaskHisList(@RequestBody String appNo)throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/getDefaultOpinion")
	public String getDefaultOpinion();
	
	@RequestMapping(value = "/wkfInterface/setDefaultOpinion")
	public void setDefaultOpinion(@RequestBody String defaultOpinion);
	
	/**
	 * @see 查询上一个审批任务
	 * @param taskId 当前任务Id
	 */
	@RequestMapping(value = "/wkfInterface/getLastTask")
	public HistoryTask getLastTask(@RequestBody String taskId);
	/**
	 * @see 查询上一个审批任务
	 * @param taskId 当前任务Id
	 */
	@RequestMapping(value = "/wkfInterface/getHistTask")
	public HistoryTask getHistTask(@RequestBody String taskId);
	
	@RequestMapping(value = "/wkfInterface/findByObjName")
	public List<WkfBusinessConfig> findByObjName(@RequestBody WkfBusinessConfig wkfBusinessConfig) throws ServiceException ;
	/**
	 * 通过用户编号获取审批角色号
	 * @param userNo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getWkfRoleNoForUserNo")
	public String getWkfRoleNoForUserNo(@RequestBody String userNo) throws ServiceException ;
	
	/**
	 * 通过用户编号获取审批角色号数组集合
	 * @param userNo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getWkfRoleNoForUserNoList")
	public List<String> getWkfRoleNoForUserNoList(@RequestBody String userNo) throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/getTaskUser")
	public String[] getTaskUser(@RequestBody Task task)throws ServiceException;
	
	@RequestMapping(value = "/wkfInterface/getNextUsers")
	public String getNextUsers(@RequestBody Task task,@RequestParam("orgNo") String orgNo,@RequestParam("regNo") String regNo)throws Exception;
	/**
	 * 通过角色编号获取审批用户号和用户名的数组集合
	 * @param roleArr
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getWkfUserForRoleArr")
	public String[] getWkfUserForRoleArr(@RequestBody String[] roleArr)throws ServiceException;
	/**
	 * @param processKey 流程Id
	 * 
	 * @param obj 业务对象
	 * 
	 * @param appNo 业务主键值
	 * 
	 * @param primaryKeyName 业务主键名称
	 * 
	 * @param opNo 操作员
	 * @throws Exception TODO
	 */
	@RequestMapping(value = "/wkfInterface/startProcess")
	public void startProcess(@RequestParam("processKey") String processKey,@RequestBody Object obj,@RequestParam("appNo") String appNo,@RequestParam("primaryKeyName") String primaryKeyName,@RequestParam("opNo") String opNo,@RequestParam("title") String title,@RequestParam("content") String content,@RequestParam("optType") String optType,@RequestParam("pasMaxNo") String pasMaxNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("regName") String regName, @RequestParam("orgNo") String orgNo) throws Exception;
	
	/**
	 * 根据taskId获取流程抄送人
	 * @param taskId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getCopyerForTaskId")
	public WfCopyer getCopyerForTaskId(@RequestBody String taskId) throws ServiceException;
	
	/**
	 * 根据dbid获取流程抄送人
	 * @param dbid
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getWkfUserForRoleArr")
	public WfCopyer getWfCopyerById(@RequestBody String dbid) throws ServiceException;
	/**
	 * 根据审批角色号获取审批用户
	 * @param wkfRoleNo
	 * @param brNo 模糊查询 前三位为本区域
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getUserBrByRoleForList")
	public List<Map<String,String>> getUserBrByRoleForList(@RequestBody String wkfRoleNo,@RequestParam("brNo") String brNo) throws ServiceException;
	
	/**
	 * 根据任务编号获取下一个节点的任务信息
	 * @param taskId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getNextTaskById")
	public JSONObject getNextTaskById(@RequestBody String taskId) throws ServiceException;
	
	/**
	 * 根据用户编号获取该用户存在的流程定义信息
	 * @param userNo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getWkfDefinitionByUser")
	public JSONArray getWkfDefinitionByUser(@RequestBody String userNo) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得当前岗位前面审批过得岗位
	 * @param appNo
	 * @param opNo
	 * @return
	 * @throws Exception
	 * JSONArray
	 * @author 沈浩兵
	 * @date 2017-7-11 上午11:54:05
	 */
	@RequestMapping(value = "/wkfInterface/getBefNodes")
	public JSONArray getBefNodes(@RequestParam("appNo") String appNo,@RequestParam(value="opNo", required=false) String opNo) throws Exception ;
	/**
	 * 
	 * 方法描述： 获得当前岗位前面审批过得岗位
	 * @param taskId
	 * @return
	 * @throws Exception
	 * JSONArray
	 * @author 郭涵晨
	 * @date 2017-10-12 上午16:13:44
	 */
	@RequestMapping(value = "/wkfInterface/getBefNodes1")
	public JSONArray getBefNodes1(@RequestParam("taskId") String taskId,@RequestParam("regNo") String regNo) throws Exception ;
	/**
	 * 
	 * 方法描述： 获得提交到当前审批岗位的岗位信息，
	 * 用于在发回到的岗位再次提交
	 * @param taskId
	 * @param appNo
	 * @return
	 * @throws Exception
	 * JSONObject
	 * @author 沈浩兵
	 * @date 2017-7-11 上午11:54:20
	 */
	@RequestMapping(value = "/wkfInterface/getLstResult")
	public JSONObject getLstResult(@RequestParam("taskId") String taskId,@RequestParam("appNo") String appNo,@RequestParam("regNo") String regNo) throws Exception;
	/**
	 * 
	 * 方法描述： 发回重审提交到指定岗位
	 * @param taskId
	 * @param approvalOpinion
	 * @param approveType
	 * @param rollbackName
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2017-7-11 上午11:58:24
	 */
	@RequestMapping(value = "/wkfInterface/rollBackToDesignName")
	public Result rollBackToDesignName(@RequestBody String taskId,@RequestParam("appNo") String approvalOpinion,@RequestParam("appNo") String approveType,@RequestParam("appNo") String rollbackName) throws Exception;
	@RequestMapping(value = "/wkfInterface/rollBackToDesignNameWithUser")
	public Result rollBackToDesignNameWithUser(@RequestBody String taskId,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("approveType") String approveType,@RequestParam("rollbackName") String rollbackName,@RequestParam("opNo") String opNo) throws Exception;
	
	/**
	 * 
	 * @param oldProcessKey 要复制的流程定义标识
	 * @param newProcessKey 复制后的流程定义标识(key)
	 * @param newProcessName 复制后的流程名称
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/wkfInterface/copyWkfDefinitionById")
	public boolean copyWkfDefinitionById(@RequestBody String oldProcessKey,@RequestParam("newProcessKey") String newProcessKey,@RequestParam("newProcessName") String newProcessName) throws Exception;

	/**
	 * 方法描述：  删除指定的流程
	 * @param processKey 要删除的流程定义标识
	 * @throws Exception
	 * void
	 */
	@RequestMapping(value = "/wkfInterface/deleteWkfDefinitionById")
	public void deleteWkfDefinitionById(@RequestBody String processKey) throws Exception;
	
	/**
	 * 根据流程定义获取第一个审批节点的审批用户及所有工作任务
	 * @param processId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getUserForFristTask")
	public List<WkfApprovalUser> getUserForFristTask(@RequestBody String processId) throws Exception;
	/**
	 * 根据流程定义获取第一个审批节点的审批用户及所有工作任务
	 * @param processId
	 * @param opNo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getUserForFristTaskWithUser")
	public List<WkfApprovalUser> getUserForFristTaskWithUser(@RequestBody String processId,@RequestParam("opNo") String opNo) throws Exception;
	/**
	 * 根据流程定义获取第一个审批节点的审批用户及今日的工作任务
	 * @param processId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getUserForFristTaskForToday")
	public List<WkfApprovalUser> getUserForFristTaskForToday(@RequestBody String processId) throws Exception;
	/**
	 * 根据流程定义获取第一个审批节点的审批用户及今日的工作任务
	 * @param processId
	 * @param opNo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfInterface/getUserForFristTaskForTodayWithUser")
	public List<WkfApprovalUser> getUserForFristTaskForTodayWithUser(@RequestBody String processId,@RequestParam("opNo") String opNo) throws Exception;

	/**
	 * 
	 * 方法描述： 根据流程定义、节点名称获取审批节点的审批用户及所有工作任务
	 * @param processId 流程定义标识
	 * @param nodeName 节点名称 例如第一个审批流程节点 start
	 * @param opNo 非必填，如果不传从session中获得
	 * @return
	 * @throws Exception
	 * List<WkfApprovalUser>
	 * @author 沈浩兵
	 * @date 2017-12-1 下午3:28:11
	 */
	@RequestMapping(value = "/wkfInterface/getUserForTask")
	public List<WkfApprovalUser> getUserForTask(@RequestBody String processId,@RequestParam("nodeName") String nodeName,@RequestParam("opNo") String opNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据流程定义、节点名称获取审批节点的审批用户和今日的工作任务
	 * @param processId 流程定义标识
	 * @param nodeName 节点名称 例如第一个审批流程节点 start
	 * @param opNo 非必填，如果不传从session中获得
	 * @return
	 * @throws Exception
	 * List<WkfApprovalUser>
	 * @author 沈浩兵
	 * @date 2017-12-1 下午3:28:40
	 */
	@RequestMapping(value = "/wkfInterface/getUserForTaskForToday")
	public List<WkfApprovalUser> getUserForTaskForToday(@RequestBody String processId,@RequestParam("nodeName") String nodeName,@RequestParam("opNo") String opNo) throws Exception;
	/**
	 * 根据taskId获取下一个流程节点的类型
	* @Title getNextNodeTypeByTaskId  
	* @Description TODO  
	* @param taskId
	* @return
	* @throws ServiceException
	* @return JSONObject
	* @author 郭涵晨  
	* @date 2017-11-8 上午2:00:03
	 */
	@RequestMapping(value = "/wkfInterface/getNextNodeTypeByTaskId")
	public String getNextNodeTypeByTaskId(@RequestBody String taskId) throws ServiceException;
	
	/**
	 * 方法描述： 对于发回重审    获取当前节点最近一次的审批人员
	 * @return
	 * @throws Exception
	 * Task
	 * @author YuShuai
	 * @date 2017-11-8 上午11:42:29
	 */
	@RequestMapping(value = "/wkfInterface/getNextUserRollBack")
	public String getNextUserRollBack(@RequestBody Task task) throws Exception;

	/**
	 * 方法描述： 获取分支节点前那个节点最后审批人员
	 * @param task
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2017-11-9 下午3:04:59
	 */
	@RequestMapping(value = "/wkfInterface/getDecisionBeforeNodeOrg")
	public String getDecisionBeforeNodeOrg(@RequestBody Task task)throws Exception;

	/**
	 * 方法描述： 判断taskList中的task任务是否和task在同一个分支组里
	 * @param task
	 * @param taskList
	 * @return
	 * boolean
	 * @author YuShuai
	 * @throws Exception 
	 * @date 2017-11-9 下午7:35:45
	 */
	@RequestMapping(value = "/wkfInterface/getIfGroupDcision")
	public boolean getIfGroupDcision(@RequestBody Task task,@RequestParam("taskList") List<Task> taskList) throws Exception;
	/**
	 * 方法描述：根据操作员编号查询对应审批角色
	 */
	@RequestMapping(value = "/wkfInterface/getCountByOpNo")
	public int getCountByOpNo(@RequestBody String opNo) throws Exception;
	
	/**
	 * 方法描述：根据操作员编号删除对应审批角色
	 * @param opNo
	 * @throws Exception
	 */
	@RequestMapping(value = "/wkfInterface/deleteApprovalRoleByOpNo")
	public void deleteApprovalRoleByOpNo(@RequestBody String opNo) throws Exception;

	/**
	 * 方法描述： 分配下一个岗位的审批人员  
	 * @param taskId
	 * @param nextUsers 已经获取的下一岗位审批人员
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2018-1-2 下午5:34:13
	 */
	@RequestMapping(value = "/wkfInterface/getAssignedUsers")
	public String getAssignedUsers(@RequestBody String taskId,@RequestParam("nextUsers") String nextUsers,@RequestParam("regNo") String regNo)throws Exception;
	
	/**
	 * 方法描述： 获取url中参数的值
	 * @param url
	 * @param parm
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2018-1-3 上午10:30:59
	 */
	@RequestMapping(value = "/wkfInterface/getUrlParm")
	public String getUrlParm(@RequestBody String url,@RequestParam("parm") String parm)throws Exception;

	/**
	 * 
	 * 方法描述： 根据流程编号以及节点的名称获取节点上配置的审批人员或者角色编号
	 * @param processId
	 * @param nodeName
	 * @return
	 * Map<String,Object>
	 * @author zhs
	 * @date 2018-1-25 上午10:52:16
	 */
	@RequestMapping(value = "/wkfInterface/getWkfApprovalUserByProcessId")
	public Map<String, Object> getWkfApprovalUserByProcessId(@RequestBody String processId,@RequestParam("nodeName") String nodeName) throws Exception;
	
}
