package  app.component.auth.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.auth.entity.MfCusCreditContract;
import app.component.wkf.entity.Result;


/**
* Title: MfCusCreditContractBo.java
* Description:授信协议业务控制
* @author:LJW
* @Tue Mar 07 15:39:22 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusCreditPactWkfFeign {

	/**
	 * 方法描述： 提交审批流程
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * Result
	 * @author YuShuai
	 * @date 2018年6月27日 下午4:02:17
	 */
	@RequestMapping("/mfCusCreditPactWkf/doCommit")
	public Result doCommit(@RequestBody Map<String, Object> dataMap)throws Exception;
	/**
	 * 方法描述： 提交审批流程
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * Result
	 * @author zhs
	 * @date 2018年7月23日 下午5:02:17
	 */
	@RequestMapping("/mfCusCreditPactWkf/doCommitForPrimary")
	public Result doCommitForPrimary(@RequestBody Map<String, Object> dataMap)throws Exception;

	/**
	 * 方法描述： 提交流程到下一步
	 * @param mfCusCreditContract
	 * @return
	 * @throws Exception
	 * Result
	 * @author YuShuai
	 * @date 2018年6月27日 下午4:02:50
	 */
	@RequestMapping("/mfCusCreditPactWkf/doCommitNextStep")
	public Result doCommitNextStep(@RequestBody MfCusCreditContract mfCusCreditContract)throws Exception;


}
