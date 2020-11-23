package  app.component.app.feign;


import java.util.List;
import java.util.Map;

import app.component.app.entity.MfBusApplyHis;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.app.entity.MfBusApply;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.collateral.entity.EvalInfo;
import app.component.wkf.entity.Result;

/**
* Title: MfBusApplyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 21 10:40:47 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusApplyWkfFeign {
	/**
	 * 
	 * 方法描述： 审批提交（审批意见保存）
	 * @param taskId
	 * @param appId
	 * @param opinionType
	 * @param approvalOpinion
	 * @param transition
	 * @param regNo
	 * @param nextUser
	 * @param mfBusApply
	 * @param dataMap 给工作流传递的参数
	 * @return
	 * Result
	 * @author zhs
	 * @date 2016-5-26 上午11:03:23
	 */
	@RequestMapping(value = "/mfBusApplyWkf/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("appId")  String appId,@RequestParam("opinionType")  String opinionType,@RequestParam("transition")  String transition,@RequestParam("opNo")  String opNo,@RequestParam("nextUser") String nextUser,@RequestBody  Map<String, Object> dataMap) throws Exception;

	/**
	 * 
	 * 方法描述： 申请提交至审批流程
	 * @param appId
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author zhs
	 * @param firstApprovalUser 
	 * @date 2016-5-26 下午3:11:19
	 */
	@RequestMapping(value = "/mfBusApplyWkf/submitProcess")
	public MfBusApply submitProcess(@RequestBody String appId,@RequestParam("firstApprovalUser")  String firstApprovalUser) throws Exception;
	/**
	 * 
	 * 方法描述： 申请提交至审批流程
	 * @param appId
	 * @param opNo操作员编号操作员名称
	 * @param opNo
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author zhs
	 * @param firstApprovalUser 
	 * @date 2016-5-26 下午3:11:19
	 */
	@RequestMapping(value = "/mfBusApplyWkf/submitProcessWithUser")
	public MfBusApply submitProcessWithUser(@RequestBody String appId,@RequestParam("opNo") String opNo,@RequestParam("opName") String opName,@RequestParam("brNo") String brNo,@RequestParam("firstApprovalUser")  String firstApprovalUser) throws Exception;
	
	/**
	 * 方法描述： 插入审批历史
	 * @param mfBusApply
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * MfBusApplyHis
	 * @author YuShuai
	 * @date 2017-8-10 下午2:35:57
	 */
	@RequestMapping(value = "/mfBusApplyWkf/insertMfBusApplyHis")
	public MfBusApplyHis insertMfBusApplyHis(@RequestBody MfBusApply mfBusApply, @RequestParam("Object>dataMap") Map<String, Object>dataMap) throws Exception;
	
	/**
	 * 方法描述： 
	 * @param dataMap
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-8-10 下午3:09:13
	 */
	@RequestMapping(value = "/mfBusApplyWkf/getApplyVariables")
	public Map<String, Object> getApplyVariables (@RequestBody Map<String, Object> dataMap,@RequestParam("appId") String appId) throws Exception;
	
	/**
	 * 方法描述： 
	 * @param userArray
	 * @param pasMinNo
	 * @return
	 * @throws Exception
	 * String[]
	 * @author YuShuai
	 * @date 2017-8-10 下午3:21:51
	 */
	@RequestMapping(value = "/mfBusApplyWkf/updateUserArr")
	public String[] updateUserArr(@RequestBody String[] userArray,@RequestParam("pasMinNo")  String pasMinNo) throws Exception;
	
	/**
	 * 
	 * 方法描述： 移动端审批意见保存
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2017-10-23 下午3:32:57
	 */
	@RequestMapping(value = "/mfBusApplyWkf/doCommitForApp")
	public Result doCommitForApp(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 方法描述： 网信项目提交评估审批
	 * @param taskId
	 * @param appId
	 * @param opinionType
	 * @param approvalOpinion
	 * @param transition
	 * @param regNo
	 * @param nextUser
	 * @param mfBusApply
	 * @param mfBusAppFeeList
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * Result
	 * @author YuShuai
	 * @param evalInfo 押品评估信息
	 * @date 2017-10-26 上午11:28:40
	 */
	@RequestMapping(value = "/mfBusApplyWkf/submitEvalApprove")
	public Result submitEvalApprove(@RequestBody String taskId,@RequestParam("appId") String appId,
@RequestParam("opinionType")String opinionType,@RequestParam("approvalOpinion")  String approvalOpinion,@RequestParam("transition")  String transition,
@RequestParam("regNo")String regNo,@RequestParam("nextUser")  String nextUser,@RequestParam("mfBusApply")  MfBusApply mfBusApply,
@RequestParam("mfBusAppFeeList")	List<MfBusAppFee> mfBusAppFeeList,@RequestParam("dataMap")  Map<String, Object> dataMap,@RequestParam("evalInfo")  EvalInfo evalInfo) throws Exception;

	/**
	 * 方法描述： 业务初选审批意见保存
	 * @param taskId
	 * @param appId
	 * @param opinionType
	 * @param approvalOpinion
	 * @param transition
	 * @param opNo
	 * @param nextUser
	 * @param dataMap 给工作流传递的参数
	 * @return
	 * @throws Exception
	 * Result
	 * @author zhs
	 * @date 2018年5月15日 上午10:34:24
	 */
	@RequestMapping(value = "/mfBusApplyWkf/doCommitForPrimary")
	public Result doCommitForPrimary(@RequestParam("taskId") String taskId,@RequestParam("appId")  String appId,@RequestParam("primaryAppId")  String primaryAppId,@RequestParam("opinionType")  String opinionType,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition")  String transition,@RequestParam("opNo")  String opNo,@RequestParam("nextUser") String nextUser,@RequestBody  Map<String, Object> dataMap) throws Exception;


}
