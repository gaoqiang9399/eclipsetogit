/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfThirdPayVerifyBo.java
 * 包名： app.component.thirdpay.bo
 * 说明：
 * @author wzw
 * @date 2017-11-11 上午10:19:46
 * @version V1.0
 */ 
package app.component.thirdpay.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类名： MfThirdPayAdapterBo
 * 描述：第三方适配
 * @author wzw
 * @date 2017-11-11 上午10:19:46
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MfThirdPayAdapterFeign {

	/**
	 * 
	 * 方法描述： 第三方适配
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * String
	 * @author wzw
	 * @date 2017-11-11 上午10:32:10
	 */
	@RequestMapping(value = "/mfThirdPayAdapter/insert")
	public String doAdapter(@RequestBody Map<String,Object> paramMap) throws Exception;
	
}
