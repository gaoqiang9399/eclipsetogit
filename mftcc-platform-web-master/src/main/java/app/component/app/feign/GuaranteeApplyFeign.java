package app.component.app.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.entity.MfBusFincApp;
import app.component.wkf.entity.Result;

/**
 * 担保业务bo接口<br>
 * 
 * @author WangChao
 */
@FeignClient("mftcc-platform-factor")
public interface GuaranteeApplyFeign {
	/**
	 * 收费提交
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-5-24 下午3:38:42
	 */
	@RequestMapping(value = "/guaranteeApply/doFeeSubmit", method = RequestMethod.POST)
	public Result doFeeSubmit(@RequestBody Map<String, Object> map) throws Exception;

	/**
	 * 放款确认、发卡，提交
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-5-24 下午3:51:45
	 */
	@RequestMapping(value = "/guaranteeApply/doLoanConfirmSubmit", method = RequestMethod.POST)
	public Result doLoanConfirmSubmit(@RequestParam("appId") String appId,@RequestBody  Map<String, Object> map) throws Exception;

	/**
	 * 合并放款申请放款复核功能，提交后进入贷后，提交
	 * 
	 * @param appId
	 * @param map
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @param mfBusFincApp 
	 * @date 2017-7-24 下午3:10:52
	 */
	@RequestMapping(value = "/guaranteeApply/doMergeLoanSubmit", method = RequestMethod.POST)
	public Result doMergeLoanSubmit(@RequestBody MfBusFincApp mfBusFincApp,@RequestParam("appId") String appId,@RequestParam("map")  Map<String, Object> map) throws Exception;
	/**
	 * 
	 * 方法描述： 费用收取提交，收费主体和费用项关联完成收取后业务流程提交
	 * @param map
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2018年4月12日 下午3:24:54
	 */
	@RequestMapping(value = "/guaranteeApply/doFeeCollectSubmit", method = RequestMethod.POST)
	public Result doFeeCollectSubmit(@RequestBody Map<String, Object> map) throws Exception;
	/**
	 * 
	 * 方法描述： 费用确认提交
	 * @param map
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2018年4月12日 下午7:41:12
	 */
	@RequestMapping(value = "/guaranteeApply/doFeeCollectConfirmSubmit", method = RequestMethod.POST)
	public Result doFeeCollectConfirmSubmit(@RequestBody Map<String, Object> map) throws Exception;

	/**
	 * 方法描述：应收账款融资费用收取提交
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/guaranteeApply/doReceFincFeeSubmit", method = RequestMethod.POST)
	public Result doReceFincFeeSubmit(@RequestBody Map<String, Object> map) throws Exception;

}
