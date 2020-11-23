package  app.component.collateral.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.entity.MfReceivablesTransferInfo;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfReceivablesTransferInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu May 11 11:22:45 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfReceivablesTransferInfoFeign {
	
	@RequestMapping(value = "/mfReceivablesTransferInfo/insert")
	public void insert(@RequestBody MfReceivablesTransferInfo mfReceivablesTransferInfo) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesTransferInfo/delete")
	public void delete(@RequestBody MfReceivablesTransferInfo mfReceivablesTransferInfo) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesTransferInfo/update")
	public void update(@RequestBody MfReceivablesTransferInfo mfReceivablesTransferInfo) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesTransferInfo/getById")
	public MfReceivablesTransferInfo getById(@RequestBody MfReceivablesTransferInfo mfReceivablesTransferInfo) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesTransferInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfReceivablesTransferInfo") MfReceivablesTransferInfo mfReceivablesTransferInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 封装初始数据
	 * @param appId
	 * @return
	 * @throws Exception
	 * MfReceivablesTransferInfo
	 * @author 沈浩兵
	 * @date 2017-5-11 下午5:03:16
	 */
	@RequestMapping(value = "/mfReceivablesTransferInfo/setReceTransferInfo")
	public MfReceivablesTransferInfo setReceTransferInfo(@RequestBody String appId) throws Exception;
	/**
	 * 
	 * 方法描述： 新增保存应收账款转让申请并提交到下一步保理业务流程
	 * @param mfReceivablesTransferInfo
	 * @param appId
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-5-11 下午5:07:03
	 */
	@RequestMapping(value = "/mfReceivablesTransferInfo/insertAndSubmitBussProcess")
	public void insertAndSubmitBussProcess(@RequestBody MfReceivablesTransferInfo mfReceivablesTransferInfo,@RequestParam("appId") String appId) throws Exception;
	/**
	 * 
	 * 方法描述： 保理业务流程中业务提交应收账款转让审批
	 * @param appId
	 * @return
	 * @throws Exception
	 * MfReceivablesTransferInfo
	 * @author 沈浩兵
	 * @date 2017-5-11 下午5:08:22
	 */
	@RequestMapping(value = "/mfReceivablesTransferInfo/submitProcess")
	public MfReceivablesTransferInfo submitProcess(@RequestBody String appId,@RequestParam("regNo") String regNo) throws Exception;
	/**
	 * 
	 * 方法描述： 应收账款转让审批提交
	 * @param taskId
	 * @param appId
	 * @param receTranAppId
	 * @param opinionType
	 * @param approvalOpinion
	 * @param transition
	 * @param opNo
	 * @param nextUser
	 * @param receivablesTransferInfo
	 * @param dataMap
	 * mfCommitRepoProvHis
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2017-5-11 下午5:21:38
	 */
	@RequestMapping(value = "/mfReceivablesTransferInfo/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("appId")   String appId,@RequestParam("receTranAppId") String receTranAppId,@RequestParam("opinionType")  String opinionType,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition")  String transition,@RequestParam("opNo")  String opNo,@RequestParam("nextUser") String nextUser,@RequestBody  Map<String, Object> dataMap) throws Exception;
	
}
