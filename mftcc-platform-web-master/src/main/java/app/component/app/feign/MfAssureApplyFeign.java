/**
 * Copyright (C) DXHM 版权所有

 * 文件名： MfAssureApplyBo.java
 * 包名： app.component.app.bo
 * 说明：
 * @author Javelin
 * @date 2017-6-5 上午10:18:41
 * @version V1.0
 */
package app.component.app.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.app.entity.MfBusApply;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.wkf.entity.Result;
import net.sf.json.JSONArray;

/**
 * 类名： MfAssureApplyBo 描述：
 * 
 * @author Javelin
 * @date 2017-6-5 上午10:18:41
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MfAssureApplyFeign {

	/**
	 * 方法描述： 太原-准入
	 * 
	 * @param mfCusCustomer
	 * @param mfCusPersBaseInfo
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-6-5 上午11:58:50
	 */

	@RequestMapping(value = "/mfAssureApply/insertAssureApply")
	public String insertAssureApply(@RequestBody MfCusCustomer mfCusCustomer,
			@RequestParam("mfCusPersBaseInfo") MfCusPersBaseInfo mfCusPersBaseInfo,
			@RequestParam("mfBusApply") MfBusApply mfBusApply) throws Exception;

	/**
	 * 方法描述： 审批提交（审批意见保存）
	 * 
	 * @param taskId
	 * @param appId
	 * @param transition
	 * @param nextUser
	 * @param dataMap
	 * @param mfBusApply
	 * @param JSONArray
	 * @return
	 * @throws Exception
	 *             Result
	 * @author Javelin
	 * @date 2017-6-5 下午6:16:29
	 */

	@RequestMapping(value = "/mfAssureApply/submitUpdate")
	public Result submitUpdate(@RequestBody String taskId, @RequestParam("appId") String appId,
			@RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser,
			@RequestParam("dataMap") Map<String, Object> dataMap, @RequestParam("mfBusApply") MfBusApply mfBusApply,
			@RequestParam("jsonArray") JSONArray jsonArray) throws Exception;

	/**
	 * 方法描述： 完成尽职调查结果
	 * 
	 * @param taskId
	 * @param appId
	 * @param dataMap
	 * @param mfBusApply
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 *             Result
	 * @author Javelin
	 * @date 2017-6-6 上午11:57:04
	 */

	@RequestMapping(value = "/mfAssureApply/insertSurvey")
	public Result insertSurvey(@RequestBody String taskId, @RequestParam("appId") String appId,
			@RequestParam("dataMap") Map<String, Object> dataMap, @RequestParam("mfBusApply") MfBusApply mfBusApply,
			@RequestParam("mfCusCustomer") MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * 方法描述： 审批提交方法
	 * 
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
	 * @return Result
	 * @author YuShuai
	 * @date 2017-8-10 下午3:14:35
	 */
	@RequestMapping(value = "/mfAssureApply/submitUpdate")
	public Result submitUpdate(@RequestBody String taskId,@RequestParam("appId") String appId,@RequestParam("opinionType") String opinionType,
			@RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition,
			@RequestParam("regNo") String regNo, @RequestParam("nextUser") String nextUser,
			@RequestParam("mfBusApply") MfBusApply mfBusApply,
			@RequestParam("mfBusAppFeeList") List<MfBusAppFee> mfBusAppFeeList,
			@RequestParam("dataMap") Map<String, Object> dataMap) throws Exception;

}
