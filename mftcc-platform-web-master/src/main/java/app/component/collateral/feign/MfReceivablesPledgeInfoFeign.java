package  app.component.collateral.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.entity.MfReceivablesPledgeInfo;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfReceivablesPledgeInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri May 05 11:00:47 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfReceivablesPledgeInfoFeign {
	
	@RequestMapping(value = "/mfReceivablesPledgeInfo/insert")
	public void insert(@RequestBody MfReceivablesPledgeInfo mfReceivablesPledgeInfo) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesPledgeInfo/delete")
	public void delete(@RequestBody MfReceivablesPledgeInfo mfReceivablesPledgeInfo) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesPledgeInfo/update")
	public void update(@RequestBody MfReceivablesPledgeInfo mfReceivablesPledgeInfo) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesPledgeInfo/getById")
	public MfReceivablesPledgeInfo getById(@RequestBody MfReceivablesPledgeInfo mfReceivablesPledgeInfo) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesPledgeInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfReceivablesPledgeInfo") MfReceivablesPledgeInfo mfReceivablesPledgeInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 融资业务流程中业务提交应收账款质押或转让审批
	 * @param mfReceivablesPledgeInfo
	 * @return
	 * @throws Exception
	 * MfReceivablesPledgeInfo
	 * @author 沈浩兵
	 * @date 2017-5-5 下午3:15:05
	 */
	@RequestMapping(value = "/mfReceivablesPledgeInfo/submitProcess")
	public MfReceivablesPledgeInfo submitProcess(@RequestBody String appId,@RequestParam("regNo") String regNo, @RequestParam("regName") String regName, @RequestParam("orgNo") String orgNo) throws Exception;
	/**
	 * 
	 * 方法描述： 新增保存应收账款质押或转让信息并提交到下一步融资业务流程
	 * @param mfReceivablesPledgeInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-5-5 下午3:23:15
	 */
	@RequestMapping(value = "/mfReceivablesPledgeInfo/insertAndSubmitBussProcess")
	public void insertAndSubmitBussProcess(@RequestBody MfReceivablesPledgeInfo mfReceivablesPledgeInfo,@RequestParam("appId") String appId) throws Exception;
	/**
	 * 
	 * 方法描述： 应收账款质押或转让审批提交
	 * @param taskId
	 * @param appId
	 * @param receivablesPledgeId
	 * @param opinionType
	 * @param approvalOpinion
	 * @param transition
	 * @param opNo
	 * @param nextUser
	 * @param mfReceivablesPledgeInfo
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2017-5-5 下午8:42:50
	 */
	@RequestMapping(value = "/mfReceivablesPledgeInfo/doCommit")
	public Result doCommit(@RequestBody String taskId,@RequestParam("appId")   String appId,@RequestParam("receivablesPledgeId") String receivablesPledgeId,@RequestParam("opinionType")  String opinionType,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition")  String transition,@RequestParam("opNo")  String opNo,@RequestParam("nextUser") String nextUser,@RequestParam("mfReceivablesPledgeInfo")  MfReceivablesPledgeInfo mfReceivablesPledgeInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 获得审批视角所需要的参数并封装到map中
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-5-6 上午10:14:41
	 */
	@RequestMapping(value = "/mfReceivablesPledgeInfo/getViewDataMap")
	public Map<String,Object> getViewDataMap(@RequestParam("busCollateralId") String busCollateralId) throws Exception;
}
