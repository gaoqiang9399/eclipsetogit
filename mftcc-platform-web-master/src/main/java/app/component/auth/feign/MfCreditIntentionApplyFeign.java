package app.component.auth.feign;

import app.base.ServiceException;
import app.component.auth.entity.MfCreditFrozenThaw;
import app.component.auth.entity.MfCreditIntentionApply;
import app.component.auth.entity.MfCreditOaApproveDetails;
import app.component.auth.entity.MfCusCreditApply;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
* Title: MfCusCreditApplyBo.java
* Description:客户授信申请业务操作控制
* @author:LJW
* @Mon Feb 27 10:43:09 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCreditIntentionApplyFeign {
	/**
	 *
	 * 方法描述： 插入授信申请信息并提交流程
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * MfCreditIntentionApply
	 * @author 沈浩兵
	 * @date 2017-6-22 上午11:09:47
	 */
	@RequestMapping(value = "/mfCreditIntentionApply/insertStartProcess")
	public MfCreditIntentionApply insertStartProcess(@RequestBody Map<String,Object> dataMap) throws Exception;
	@RequestMapping("/mfCreditIntentionApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	@RequestMapping("/mfCreditIntentionApply/getById")
	public MfCreditIntentionApply getById(@RequestBody MfCreditIntentionApply mfCreditIntentionApply) throws Exception;
	@RequestMapping("/mfCreditIntentionApply/getAllByCusNo")
	public List<MfCreditIntentionApply> getAllByCusNo(@RequestBody MfCreditIntentionApply mfCreditIntentionApply) throws Exception;
	@RequestMapping("/mfCreditIntentionApply/getOaDetails")
	public List<MfCreditOaApproveDetails> getOaDetails(@RequestBody MfCreditOaApproveDetails mfCreditOaApproveDetails) throws Exception;
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
	@RequestMapping(value = "/mfCreditIntentionApply/doCommit")
	public Result doCommit(@RequestBody Map<String,Object> dataMap) throws Exception;
	@RequestMapping(value = "/mfCreditIntentionApply/doCommitForFrozen")
	public Result doCommitForFrozen(@RequestBody Map<String,Object> dataMap) throws Exception;
	/**
	 *
	 * 方法描述： 插入授信申请信息并提交流程
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * MfCreditIntentionApply
	 * @author 沈浩兵
	 * @date 2017-6-22 上午11:09:47
	 */
	@RequestMapping(value = "/mfCreditIntentionApply/insertFrozenThaw")
	public MfCreditFrozenThaw insertFrozenThaw(@RequestBody Map<String,Object> dataMap) throws Exception;
	@RequestMapping(value = "/mfCreditIntentionApply/getMfCreditFrozenThawById")
	public MfCreditFrozenThaw getMfCreditFrozenThawById(@RequestBody MfCreditFrozenThaw mfCreditFrozenThaw) throws Exception;
	@RequestMapping(value = "/mfCreditIntentionApply/getFrozenThawList")
	public List<MfCreditFrozenThaw>  getFrozenThawList(@RequestBody MfCreditFrozenThaw mfCreditFrozenThaw) throws Exception;
}