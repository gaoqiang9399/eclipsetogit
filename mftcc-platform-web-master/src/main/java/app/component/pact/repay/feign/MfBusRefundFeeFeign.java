package app.component.pact.repay.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import app.component.pact.repay.entity.MfRefundFeeApply;

@FeignClient("mftcc-platform-factor")
public interface MfBusRefundFeeFeign {
	 /**
     * 
     * 方法描述：获取退费相关信息（包含退息 退费 退违约金）
     * @param mfRefundFeeApply
     * @return
     * @throws Exception 
     * MfRefundFeeApply
     * @author WD
     * @date 2018-1-29 上午11:48:14
     */
	@RequestMapping(value = "/mfBusRefundFee/getMfRefundFeeInfobyFincInfo")
	public Map<String,Object> getMfRefundFeeInfobyFincInfo(
			@RequestBody 	MfRefundFeeApply mfRefundFeeApply) throws Exception;
    /**
     * 
     * 方法描述：保存退费相关信息 处理退费相关业务
     * @param mfRefundFeeApply
     * @return
     * @throws Exception 
     * Map<String,Object>
     * @author WD
     * @date 2018-1-27 下午2:21:22
     */
	@RequestMapping(value = "/mfBusRefundFee/insert")
	public Map<String, Object> insert(@RequestBody MfRefundFeeApply mfRefundFeeApply) throws Exception;
}
