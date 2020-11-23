package app.component.wkfBusInterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.common.BizPubParm.WKF_NODE;
import app.component.prdct.entity.MfKindFlow;
import app.component.wkf.entity.Result;
import app.component.wkfBus.bean.WkfBusNode;
@FeignClient("mftcc-platform-factor")
public interface WkfBusInterfaceFeign {
	@RequestMapping(value = "/wkfBusInterface/doCommitNextStepWithUser")
	public Result doCommitNextStepWithUser(@RequestParam("appId") String appId, @RequestParam("wkfAppId") String wkfAppId, @RequestParam("opNo") String opNo) throws Exception;

	/**
	 * 用于业务流程节点提交
	 * 
	 * @param appId 申请号
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-6-16 下午5:06:10
	 */
	@RequestMapping(value = "/wkfBusInterface/doBusinessCommit")
	public Result doBusinessCommit(@RequestBody String appId, @RequestParam("regNo") String regNo) throws Exception;

	/**
	 * 用于业务流程节点提交
	 * 
	 * @param appId 申请号
	 * @param nextUser 下一环节审批人
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-7-20 下午5:30:09
	 */
	@RequestMapping(value = "/wkfBusInterface/doBusinessCommit1")
	public Result doBusinessCommit1(@RequestBody String appId,@RequestParam("nextUser") String nextUser) throws Exception;

	/**
	 * 提交至审批(业务审批、合同审批、放款审批), 进入审批流程<br>
	 * 由流程结束事件调用{@link WkfEndEventListener}，不要自己在业务中调用此接口
	 * 
	 * @param appId 申请号
	 * @param pactId 合同号
	 * @param fincId 借据号
	 * @param nodeNo 节点编号
	 * @throws Exception
	 * @author WangChao
	 * @param firstApprovalUser 
	 * @date 2017-6-24 下午2:20:43
	 */
	@RequestMapping(value = "/wkfBusInterface/doAuditFlowInit")
	public void doAuditFlowInit(@RequestBody String appId,@RequestParam("pactId") String pactId,@RequestParam("fincId") String fincId,@RequestParam("nodeNo") String nodeNo,@RequestParam("firstApprovalUser") String firstApprovalUser,@RequestParam("orgNo") String orgNo) throws Exception;
	/**
	 * 提交至审批(业务审批、合同审批、放款审批), 进入审批流程<br>
	 * 由流程结束事件调用{@link WkfEndEventListener}，不要自己在业务中调用此接口
	 * 
	 * @param appId 申请号
	 * @param pactId 合同号
	 * @param fincId 借据号
	 * @param nodeNo 节点编号
	 * @param opNo 节点编号
	 * @param opName 节点编号
	 * @param brNo 节点编号
	 * @throws Exception
	 * @author ywh
	 * @param firstApprovalUser 
	 * @date 2017-10-31 下午3:20:43
	 */
	@RequestMapping(value = "/wkfBusInterface/doAuditFlowInitWithUser")
	public void doAuditFlowInitWithUser(@RequestBody String appId,@RequestParam("pactId") String pactId,@RequestParam("fincId") String fincId,@RequestParam("nodeNo") String nodeNo,@RequestParam("firstApprovalUser") String firstApprovalUser,@RequestParam("opNo")String opNo,@RequestParam("opName")String opName,@RequestParam("brNo")String brNo) throws Exception;
	
	
	/**
	 * 获取{@link MfKindFlow}的所有流程节点，并将其排序
	 * @param mfKindFlow
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-7-29 下午4:47:29
	 */
	@RequestMapping(value = "/wkfBusInterface/getKindFlow")
	public List<WkfBusNode> getKindFlow(@RequestBody MfKindFlow mfKindFlow) throws Exception;
	
	/**
	 * 方法描述： 获取当前节点的审批人员或者角色
	 * @param appId   申请号 appId
	 * @param nodeNo  当前节点编号
	 * @param wkfNode 用于区分申请审批，合同审批，放款审批     如果是业务流程 传apply
	 * @return
	 * String[]  返回 user 和 group
	 * @author YuShuai
	 * @date 2017-8-15 下午3:31:50
	 */
	@RequestMapping(value = "/wkfBusInterface/getCurrentUser")
	public String [] getCurrentUser(@RequestParam("appId") String appId,@RequestParam("nodeNo") String nodeNo,@RequestBody WKF_NODE wkfNode)throws Exception;

	/**
	 * 
	 * 方法描述： 初始化客户模块的审批流程初始化
	 * @param wkfAppId
	 * @param nodeName
	 * @throws Exception
	 * void
	 * @author zhs
	 * @date 2017-8-24 下午4:18:58
	 */
	@RequestMapping(value = "/wkfBusInterface/doCusAuditFlowInit")
	public void doCusAuditFlowInit(@RequestParam("wkfAppId") String wkfAppId,@RequestParam("nodeName") String nodeName,@RequestParam("opNo") String opNo,@RequestParam("firstApprovalUser") String firstApprovalUser) throws Exception;
	/**
	 * 
	 * 方法描述： 初始化展期业务审批流程
	 * @param wkfAppId
	 * @param nodeName
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-9-7 下午2:38:28
	 */
	@RequestMapping(value = "/wkfBusInterface/doExtensionFlowInit")
	public void doExtensionFlowInit(@RequestBody String wkfAppId,@RequestParam("nodeName") String nodeName) throws Exception;
	
	/**
	 * 
	 * 方法描述： 
	 * @param wkfAppId
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2017-9-7 下午2:15:11
	 */
	@RequestMapping(value = "/wkfBusInterface/doCommitBussFlow")
	public Result doCommitBussFlow(@RequestBody String wkfAppId, @RequestParam("regNo") String regNo) throws Exception;
	/**
	 * 用于app端业务流程节点提交
	 * 
	 * @param appId 申请号
	 * @param opNo 操作员编号
	 * @return
	 * @throws Exception
	 * @author ywh
	 * @date 2017-10-30下午5:06:10
	 */
	@RequestMapping(value = "/wkfBusInterface/doBusinessCommitForApp")
	public Result doBusinessCommitForApp(@RequestBody String appId,@RequestParam("opNo") String opNo) throws Exception;
	
	/**
	 * 
	 * 方法描述：  提交下一步业务流程，并更新业务办理节点(目前仅有审批子流程通过后，提交主业务流程时在使用，其他环节如有需要，也可以使用该方法)
	 * @param appId
	 * @param wkfAppId
	 * @param opNo
	 * @return
	 * @throws Exception
	 * Result
	 * @author zhs
	 * @date 2017-10-24 上午9:47:58
	 */
	@RequestMapping(value = "/wkfBusInterface/doCommitNextStepWithOpNo")
	public Result doCommitNextStepWithOpNo(@RequestBody String appId,@RequestParam("wkfAppId") String wkfAppId,@RequestParam("opNo") String opNo) throws Exception;
	
	@RequestMapping(value = "/wkfBusInterface/doCommitNextStepByOpinionType")
	public Result doCommitNextStepByOpinionType(@RequestBody String appId,@RequestParam("wkfAppId") String wkfAppId,@RequestParam("opinionType") String opinionType,@RequestParam("opNo") String opNo) throws Exception;
}
