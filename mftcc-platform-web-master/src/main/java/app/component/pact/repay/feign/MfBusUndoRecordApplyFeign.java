package app.component.pact.repay.feign;

import app.component.calc.core.entity.MfBusUndoApply;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 还款撤销相关
 * @author WD
 *MfBusUndoRecordApplyFeign.java
 */
@FeignClient("mftcc-platform-factor")
public interface MfBusUndoRecordApplyFeign {
    /**
     * 
     * 方法描述：获取还款撤销相关信息 通过repayId 和 fincId
     * @param mfBusUndoApply
     * @return
     * @throws Exception 
     * Map<String,Object>
     * @author WD
     * @date 2018-3-15 下午3:50:10
     */
	@RequestMapping(value = "/calcUndoRepaymentInterface/getMfBusUndoApplybyFincInfo")
	public Map<String,Object> getMfBusUndoApplybyFincInfo(@RequestBody MfBusUndoApply mfBusUndoApply)throws Exception;
	/**
	 * 
	 * 方法描述：处理还款撤销相关业务
	 * @param mfBusUndoApply
	 * @return
	 * @throws Exception 
	 * Map<String,Object>
	 * @author WD
	 * @date 2018-3-15 下午3:50:58
	 */
	@RequestMapping(value = "/calcUndoRepaymentInterface/doDealMfBusUndoApplyInfo")
	public Map<String, Object> doDealMfBusUndoApplyInfo(@RequestBody MfBusUndoApply mfBusUndoApply)throws Exception;

}
