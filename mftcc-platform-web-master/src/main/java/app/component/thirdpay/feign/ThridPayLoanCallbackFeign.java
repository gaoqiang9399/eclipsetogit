/**
 * Copyright (C) DXHM 版权所有
 * 文件名： ThridPayLoanCallbackBo.java
 * 包名： app.component.thirdpay.api
 * 说明：
 * @author 沈浩兵
 * @date 2017-11-13 下午2:00:09
 * @version V1.0
 */ 
package app.component.thirdpay.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类名： ThridPayLoanCallbackBo
 * 描述：
 * @author 沈浩兵
 * @date 2017-11-13 下午2:00:09
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface ThridPayLoanCallbackFeign {
	/**
	 * 
	 * 方法描述： 请求第三方回调处理业务。生成还款计划
	 * @param Map<String, Object>
	 * Map里面包含
	 * 请求流水号 thirdPartyRequestId 必传
	 * 放款状态 fincState 2成功、3失败 必传
	 * 原因  remark 必传
	 * 第三方响应报文 returnData 必传
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-11-13 下午2:02:42
	 */
	@RequestMapping(value = "/thridPayLoanCallback/doAutomaticRepayPlan")
	public Map<String,Object> doAutomaticRepayPlan(@RequestBody Map<String, Object> returnMap) throws Exception;
}
