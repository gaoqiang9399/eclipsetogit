/**
 * Copyright (C) DXHM 版权所有
 * 文件名： ThridPayMftApi.java
 * 包名： app.component.thirdpay.api
 * 说明：
 * @author wzw
 * @date 2017-11-10 下午2:06:44
 * @version V1.0
 */ 
package app.component.thirdpay.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类名： ThridPayMftApi
 * 描述：供应链第三方支付
 * @author wzw
 * @date 2017-11-10 下午2:06:44
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface ThridPayLoanApiFeign {
	@RequestMapping(value = "/thridPayLoanApi/doLoan")
	public Map<String,Object> doLoan(@RequestBody Map<String,Object> paramMap) throws Exception;
	
	@RequestMapping(value = "/thridPayLoanApi/getStatusAgain")
	Map<String, Object> getStatusAgain(@RequestBody Map<String, Object> paramMap) throws Exception;
}
