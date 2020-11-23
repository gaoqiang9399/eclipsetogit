package  app.component.auth.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.auth.entity.MfCusCreditAdjustApply;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditApproveInfo;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCreditApproveInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Jun 26 09:56:51 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusCreditApproveInfoFeign {
	
	@RequestMapping(value = "/mfCusCreditApproveInfo/insert")
	public void insert(@RequestBody MfCusCreditApproveInfo mfCusCreditApproveInfo) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditApproveInfo/delete")
	public void delete(@RequestBody MfCusCreditApproveInfo mfCusCreditApproveInfo) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditApproveInfo/update")
	public void update(@RequestBody MfCusCreditApproveInfo mfCusCreditApproveInfo) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditApproveInfo/getById")
	public MfCusCreditApproveInfo getById(@RequestBody MfCusCreditApproveInfo mfCusCreditApproveInfo) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditApproveInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusCreditApproveInfo") MfCusCreditApproveInfo mfCusCreditApproveInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 获得审批数据
	 * @param mfCusCreditApproveInfo
	 * @return
	 * @throws Exception
	 * List<MfCusCreditApproveInfo>
	 * @author 沈浩兵
	 * @date 2017-7-5 下午9:01:30
	 */
	@RequestMapping(value = "/mfCusCreditApproveInfo/getMfCusCreditApproveInfoList")
	public List<MfCusCreditApproveInfo> getMfCusCreditApproveInfoList(@RequestBody MfCusCreditApproveInfo mfCusCreditApproveInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 插入授信审批信息
	 * @param creditType
	 * @param creditAppId
	 * @param adjustAppId
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-6-26 上午10:20:26
	 */
	@RequestMapping(value = "/mfCusCreditApproveInfo/insertCreditApproveInfo")
	public MfCusCreditApproveInfo insertCreditApproveInfo(@RequestBody String creditType,@RequestParam("mfCusCreditApply") MfCusCreditApply mfCusCreditApply,@RequestParam("mfCusCreditAdjustApply") MfCusCreditAdjustApply mfCusCreditAdjustApply) throws Exception;
	/**
	 * 
	 * 方法描述： 授信审批保存提交保存审批意见
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2017-6-26 下午4:03:25
	 */
	@RequestMapping(value = "/mfCusCreditApproveInfo/doCommit")
	public Result doCommit(@RequestBody Map<String,Object> dataMap) throws Exception;
	/**
	 *
	 * 方法描述： 授信立项审批保存提交保存审批意见
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2017-6-26 下午4:03:25
	 */
	@RequestMapping(value = "/mfCusCreditApproveInfo/doCommitForPrimary")
	public Result doCommitForPrimary(@RequestBody Map<String,Object> dataMap) throws Exception;

	/**
	 * 
	 * 方法描述：单独提交业务流程 
	 * @param mfCusCreditApproveInfo
	 * @return
	 * @throws Exception
	 * Result
	 * @author zhs
	 * @date 2017-10-25 上午10:58:09
	 */
	@RequestMapping(value = "/mfCusCreditApproveInfo/doCommitNextStep")
	public Result doCommitNextStep(@RequestBody MfCusCreditApproveInfo mfCusCreditApproveInfo) throws Exception;
}
