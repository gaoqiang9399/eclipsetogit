package app.component.sysTaskInfoInterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sys.entity.SysTaskInfo;
import app.util.toolkit.Ipage;
@FeignClient("mftcc-platform-factor")
public interface SysTaskInfoInterfaceFeign {
	/**
	 * 创建流程任务
	 * @param bizPk 业务主键
	 * @param pasMAxNo 业务大类(例如审批任务、催收任务等)
	 * @param pasMinNo 业务小类(例如业务审批、评级审批等)
	 * @param pasUrl 审批URL 
	 * @param pasTitle 业务主题
	 * @param pasContent 业务内容
	 * @param nextUser 任务执行人
	 * @param taskId 流程审批节点
	 * @param optType 操作类型(当前页面审批、页面弹出)
	 * @param importLev 重要程度
	 * @param regName 消息发起人
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/insertWkfTask")
	public void insertWkfTask(@RequestParam("bizPk") String bizPk,@RequestParam("pasMaxNo") String pasMaxNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("pasUrl") String pasUrl,@RequestParam("pasTitle") String pasTitle,@RequestParam("pasContent") String pasContent,@RequestParam("nextUser") String[] nextUser,@RequestParam("taskId") String taskId,@RequestParam("optType") String optType,@RequestParam("importLev") String importLev,@RequestParam("regName") String regName) throws Exception;
	/**
	 * 创建流程任务
	 * @param bizPk 业务主键
	 * @param pasMAxNo 业务大类(例如审批任务、催收任务等)
	 * @param pasMinNo 业务小类(例如业务审批、评级审批等)
	 * @param pasUrl 审批URL 
	 * @param pasTitle 业务主题
	 * @param pasContent 业务内容
	 * @param nextUser 任务执行人
	 * @param taskId 流程审批节点
	 * @param optType 操作类型(当前页面审批、页面弹出)
	 * @param importLev 重要程度
	 * @param regName 消息发起人
	 * @param brNo 当前操作员部门
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/insertWkfTaskWithUser")
	public String[] insertWkfTaskWithUser(@RequestParam("bizPk") String bizPk,@RequestParam("pasMaxNo") String pasMaxNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("pasUrl") String pasUrl,@RequestParam("pasTitle") String pasTitle,@RequestParam("pasContent") String pasContent,@RequestParam("nextUser") String[] nextUser,@RequestParam("pasMaxNo") String taskId,@RequestParam("optType") String optType,@RequestParam("importLev") String importLev,@RequestParam("regName") String regName,@RequestParam("brNo") String brNo) throws Exception;

	/**
	 * 完成流程任务
	 * @param bizPk 业务主键
	 * @param taskId 流程审批节点
	 * @return
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/updateCompleteWkfTask")
	public SysTaskInfo updateCompleteWkfTask(@RequestParam("bizPk") String bizPk,@RequestParam("taskId") String taskId,@RequestParam("pasResult") String pasResult);
	/**
	 * 创建业务任务
	 * @param bizPk 业务主键
	 * @param pasMAxNo 业务大类
	 * @param pasMinNo 业务小类
	 * @param pasUrl 审批URL
	 * @param pasTitle 业务主题
	 * @param pasContent 业务内容
	 * @param nextUser 任务执行人
	 * @param isMust 是否必须
	 * @param optType 操作类型
	 * @param importLev 重要等级
	 * @param formId 展开的表单编号
	 * @param regName 消息发起人
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/insertBizTask")
	public void insertBizTask(@RequestParam("bizPk") String bizPk,@RequestParam("pasMaxNo") String pasMaxNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("pasUrl") String pasUrl,@RequestParam("pasTitle") String pasTitle,@RequestParam("pasContent") String pasContent,@RequestParam("nextUser") String nextUser,@RequestParam("isMust") String isMust,@RequestParam("optType") String optType,@RequestParam("importLev") String importLev,@RequestParam("formId") String formId,@RequestParam("regName") String regName);
	/**
	 * 更改业务任务
	 * @param bizPk 业务主键
	 * @param pasMinNo 业务小类
	 * @param pasUrl 审批URL
	 * @param pasTitle 业务主题
	 * @param pasContent 业务内容
	 * @param nextUser 任务执行人
	 * @param isMust 是否必须
	 * @param optType 操作类型
	 * @param importLev 重要等级
	 * @param formId 展开的表单编号
	 * @return
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/updateBizTask")
	public SysTaskInfo updateBizTask(@RequestParam("bizPk") String bizPk,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("pasUrl") String pasUrl,@RequestParam("pasTitle") String pasTitle,@RequestParam("pasContent") String pasContent,@RequestParam("nextUser") String nextUser,@RequestParam("isMust") String isMust,@RequestParam("optType") String optType,@RequestParam("importLev") String importLev,@RequestParam("formId") String formId);
	/**
	 * 完成业务任务
	 * @param bizPk 业务主键
	 * @param pasMinNo 业务小类
	 * @param user 任务执行人
	 * @param pasResult 完成返回值
	 * @return
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/updateCompleteBizTask")
	public SysTaskInfo updateCompleteBizTask(@RequestParam("bizPk") String bizPk,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("user") String user,@RequestParam("pasResult") String pasResult);
	/**
	 * 完成业务任务(只更新状态)
	 * @param bizPk 业务主键
	 * @param user 任务执行人
	 * @param pasMinNo 业务小类
	 * @param pasResult 完成返回值
	 * @return
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/completeSysTask")
	public SysTaskInfo completeSysTask(@RequestParam("bizPk") String bizPk,@RequestParam("user") String user,@RequestParam("pasMinNo") String pasMinNo);
	/**
	 * 创建消息
	 * @param pasMinNo 消息小类
	 * @param pasTitle 消息标题
	 * @param pasContent 消息内容
	 * @param nextUser 执行人
	 * @param importLev 重要程度
	 * @param isMustReply 是否必须回复
	 * @param regName 消息发起人
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/insertMessage")
	public void insertMessage(@RequestParam("pasMinNo") String pasMinNo,@RequestParam("pasTitle") String pasTitle,@RequestParam("pasContent") String pasContent,@RequestParam("nextUser") String nextUser,@RequestParam("importLev") String importLev,@RequestParam("isMustReply") String isMustReply,@RequestParam("regName") String regName);
	/**
	 * 创建消息
	 * @param pasMaxNo 消息大类
	 * @param pasMinNo 消息小类
	 * @param pasTitle 消息标题
	 * @param pasContent 消息内容
	 * @param nextUser 执行人
	 * @param importLev 重要程度
	 * @param isMustReply 是否必须回复
	 * @param regName 消息发起人
	 * @param pasStick 置顶状态
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/insertMessage1")
	public void insertMessage1(@RequestParam("pasMaxNo") String pasMaxNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("pasTitle") String pasTitle,@RequestParam("pasContent") String pasContent,@RequestParam("nextUser") String nextUser,@RequestParam("importLev") String importLev,@RequestParam("isMustReply") String isMustReply,@RequestParam("regName") String regName,@RequestParam("pasStick") String pasStick);
	/**
	 * 修改催收任务经办人
	 * @param bizPk 业务主键
	 * @param pasMinNo 业务小类
	 * @param oldNextUser 被修改的任务执行人
	 * @param newNextUser 新任务执行人
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/updateRecUser")
	public void updateRecUser(@RequestParam("bizPk") String bizPk,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("oldNextUser") String oldNextUser,@RequestParam("newNextUser") String newNextUser);
	/**
	 * 修改催收任务经办人任务内容及状态
	 * @param bizPk 业务主键
	 * @param pasMinNo 业务小类
	 * @param pasContent 业务内容
	 * @param pasSts 业务状态
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/updatePasContent")
	public void updatePasContent(@RequestParam("bizPk") String bizPk,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("pasContent") String pasContent,@RequestParam("pasSts") String pasSts);
	/**
	 * 获取全部业务任务（微信接口）
	 * @param pasMaxNo 业务大类
	 * @param pasMinNo 业务小类
	 * @param userNo 用户编号
	 * @param pasSts 业务状态
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/getAllForWechat")
	public List<SysTaskInfo> getAllForWechat(@RequestParam("pasMaxNo") String pasMaxNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("userNo") String userNo,@RequestParam("pasSts") String pasSts);
	
	/**
	 * 会签中一票否决下，当某一会签人选择不同意时，删除其他未操作的会签人的审批消息
	 * @param bizPk 业务主键
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/deleteCountersignTask")
	public void deleteCountersignTask(@RequestParam("bizPk") String bizPk);
	/**
	 * 根据流程任务主键删除
	 * @param wkfTaskNo 业务主键
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/deleteTaskByWkfTaskNo")
	public void deleteTaskByWkfTaskNo(@RequestParam("wkfTaskNo") String wkfTaskNo);
	
	/**
	 * 通过bizPkNo获取流程的taskId
	 * @param bizPkNo
	 * @return
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/getTaskIdByAppNo")
	public SysTaskInfo getTaskIdByAppNo(@RequestParam("bizPkNo") String bizPkNo);
	
	/***
	 * 通过当前节点的 抄送人发送指定信息
	 * @param wkfTaskId
	 * @param appNo
	 * @param title
	 * @param sendMsg
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/sendRoleMsg")
	public void sendRoleMsg(@RequestParam("taskId") String taskId,@RequestParam("appNo") String appNo,@RequestParam("title") String title,@RequestParam("sendMsg") String sendMsg) throws ServiceException;
	
	@RequestMapping(value = "/sysTaskInfoInterface/updateTaskChangeUser")
	public void updateTaskChangeUser(@RequestParam("bizPk") String bizPk,@RequestParam("taskId") String taskId,@RequestParam("newNextUser") String newNextUser) throws ServiceException;
	/**
	 * 根据过滤条件，去修改任务信息状态
	 * @param SysTaskInfo filter
	 * @param SysTaskInfo updateData
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/updateTaskByFilter")
	public void updateTaskByFilter(@RequestBody SysTaskInfo filter,@RequestParam("updateData") SysTaskInfo updateData) throws ServiceException;
	//根据流程审批节点获得任务信息
	@RequestMapping(value = "/sysTaskInfoInterface/getByAppNoTaskId")
	public List<SysTaskInfo> getByAppNoTaskId(@RequestBody SysTaskInfo sysTaskInfo);
	//会签任务一票通过时更新状态
	@RequestMapping(value = "/sysTaskInfoInterface/updateStsForSignTask")
	public void updateStsForSignTask(@RequestParam("bizPkNo") String bizPkNo,@RequestParam("bizPk") String[] taskIds);
	//会签任务一票否决时更新状态
	@RequestMapping(value = "/sysTaskInfoInterface/updateAllSts")
	public void updateAllSts(@RequestParam("bizPkNo") String bizPkNo);
	
	
	/**
	 * 方法描述： 开启审批流程后插入任务表数据
	 * @param appId  业务关联编号
	 * @param title  
	 * @param content
	 * @param optType  
	 * @param pasMaxNo  业务大类
	 * @param pasMinNo  业务小类
	 * @param importLev  重要等级
	 * void
	 * @author YuShuai
	 * @date 2017-7-27 下午2:49:26
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/insertWkfTaskForProcess")
	public void insertWkfTaskForProcess(@RequestParam("appId") String appId,@RequestParam("title") String title,@RequestParam("content") String content,@RequestParam("optType") String optType,@RequestParam("pasMaxNo") String pasMaxNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("importLev") String importLev) throws Exception;

	@RequestMapping(value = "/sysTaskInfoInterface/insertWkfTaskForProcess")
	public void insertWkfTaskForProcess(@RequestParam("appId") String appId,@RequestParam("title") String title,@RequestParam("string") String string,@RequestParam("next") String[] next,@RequestParam("pAS_OPT_TYPE_POP") String pAS_OPT_TYPE_POP,@RequestParam("pAS_BIG_TYPE_APPROVAL") String pAS_BIG_TYPE_APPROVAL,@RequestParam("pAS_SUB_TYPE_APP_APPLY") String pAS_SUB_TYPE_APP_APPLY,@RequestParam("pAS_IMPORT_LEV_1") String pAS_IMPORT_LEV_1) throws Exception;
	@RequestMapping(value = "/sysTaskInfoInterface/insertWkfTaskForProcessWithUser")
	public void insertWkfTaskForProcessWithUser(@RequestParam("appId") String appId,@RequestParam("title") String title,@RequestParam("string") String string,@RequestParam("next") String[] next,@RequestParam("pAS_OPT_TYPE_POP") String pAS_OPT_TYPE_POP,@RequestParam("pAS_BIG_TYPE_APPROVAL") String pAS_BIG_TYPE_APPROVAL,@RequestParam("pAS_SUB_TYPE_APP_APPLY") String pAS_SUB_TYPE_APP_APPLY,@RequestParam("pAS_IMPORT_LEV_1") String pAS_IMPORT_LEV_1,@RequestParam("opName") String opName,@RequestParam("brNo") String brNo) throws Exception;
	/**
	 * 获取审批信息列表分页
	 * 
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/findByPageSysTask")
	public Ipage findByPageSysTask(@RequestBody Ipage ipage,@RequestParam("sysTaskInfo") SysTaskInfo sysTaskInfo)throws Exception;
	
	/**
	 * 获取审批信息列表条数
	 * 
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/findByPageSysTaskCut")
	public int findByPageSysTaskCut(@RequestBody SysTaskInfo sysTaskInfo)throws Exception;
	/**
	 * 获取累计审批信息列表条数
	 *
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/findByPageSysTaskCount")
	public int findByPageSysTaskCount(@RequestBody SysTaskInfo sysTaskInfo)throws Exception;
	/**
	 * 
	 * @param tlrNo 登陆账号
	 * @param messageType 消息类型 
	 * @param pageNo 页码
	 * @param pagesize 分页大小
	 * @param search 搜索内容
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/getSysTaskInfoList")
	public Ipage getSysTaskInfoList(@RequestBody Ipage ipage,@RequestParam("sysTaskInfo") SysTaskInfo sysTaskInfo)throws Exception;
	/**
	 * 获取消息详情
	 * @param sysTaskInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/getById")
	public SysTaskInfo getById(@RequestBody SysTaskInfo sysTaskInfo)throws Exception;
	
	@RequestMapping(value = "/sysTaskInfoInterface/deleteSysTaskInfo")
	public void deleteSysTaskInfo(@RequestBody SysTaskInfo sysTaskInfo)throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据操作员获得所有待审批的审批任务
	 * @param sysTaskInfo
	 * @return
	 * @throws Exception
	 * List<SysTaskInfo>
	 * @author 沈浩兵
	 * @date 2017-10-23 上午11:10:21
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/getApproveTaskByUser")
	public List<SysTaskInfo> getApproveTaskByUser(@RequestBody SysTaskInfo sysTaskInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据业务编号、业务小类、操作员获得待审批的审批任务
	 * @param bizPkNo
	 * @param pasMinNo
	 * @param userNo
	 * @return
	 * @throws ServiceException
	 * SysTaskInfo
	 * @author 沈浩兵
	 * @date 2017-10-23 下午2:34:23
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/getByAppNoUser")
	public SysTaskInfo getByAppNoUser(@RequestParam("bizPkNo") String bizPkNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("userNo") String userNo) throws ServiceException;
	/**
	 * 方法描述： 根据关联编号和任务编号查询任务的审批人员
	 * @param taskId
	 * @param  bizPkNo
	 * @return
	 * @throws Exception
	 * String[]
	 * @author YuShuai
	 * @date 2017-11-11 下午3:55:22
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/getnextTaskUser")
	public String[] getnextTaskUser(@RequestParam("taskId") String taskId,@RequestParam("bizPkNo") String bizPkNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据操作员获得所有否决的审批任务
	 * @param sysTaskInfo
	 * @return
	 * @throws Exception
	 * List<SysTaskInfo>
	 * @author ywh
	 * @date 2017-11-29 上午11:10:21
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/getVetoApproveTaskByUser")
	public List<SysTaskInfo> getVetoApproveTaskByUser(@RequestBody SysTaskInfo sysTaskInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据操作员获得所有通过的审批任务
	 * @param sysTaskInfo
	 * @return
	 * @throws Exception
	 * List<SysTaskInfo>
	 * @author ywh
	 * @date 2017-11-29 上午11:10:21
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/getPassApproveTaskByUser")
	public List<SysTaskInfo> getPassApproveTaskByUser(@RequestBody SysTaskInfo sysTaskInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 完成流程任务，操作员传入
	 * @param bizPk
	 * @param taskId
	 * @param pasResult
	 * @param opNo
	 * @return
	 * SysTaskInfo
	 * @author 沈浩兵
	 * @date 2017-12-27 上午11:21:19
	 */
	@RequestMapping(value = "/sysTaskInfoInterface/updateCompleteWkfTaskWithOpNo")
	public SysTaskInfo updateCompleteWkfTaskWithOpNo(@RequestParam("bizPk") String bizPk,@RequestParam("taskId") String taskId,@RequestParam("pasResult") String pasResult,@RequestParam("opNo") String opNo);
}
