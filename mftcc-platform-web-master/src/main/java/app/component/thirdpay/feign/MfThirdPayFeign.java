/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfThirdPayBo.java
 * 包名： app.component.thirdpay.bo
 * 说明：
 * @author wzw
 * @date 2017-11-10 下午1:54:07
 * @version V1.0
 */ 
package app.component.thirdpay.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类名： MfThirdPayBo
 * 描述：第三方支付
 * @author wzw
 * @date 2017-11-10 下午1:54:07
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MfThirdPayFeign {

	/**
	 * 
	 * 方法描述： 第三方支付 放款
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author wzw
	 * @date 2017-11-10 下午1:55:18
	 */
	@RequestMapping(value = "/ mfThirdPay/insert")
	public Map<String,String> doLoan(@RequestBody Map<String,Object> paramMap) throws Exception;
	
}
