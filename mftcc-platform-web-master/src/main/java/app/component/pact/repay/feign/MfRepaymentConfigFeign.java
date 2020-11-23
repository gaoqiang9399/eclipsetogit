/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfRepaymentConfigBoImpl.java
 * 包名： app.component.pact.repay.bo.impl
 * 说明：
 * @author 沈浩兵
 * @date 2018-1-23 下午4:02:06
 * @version V1.0
 */
package app.component.pact.repay.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.doc.entity.DocBizManageParam;
import app.component.doc.entity.DocBizSceConfig;

/**
 * 类名： MfRepaymentConfigBoImpl 描述：
 * 
 * @author 沈浩兵
 * @date 2018-1-23 下午4:02:06
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MfRepaymentConfigFeign  {

	@RequestMapping(value = "/mfRepaymentConfig/getRepaymentDocConfig")
	public Map<String, Object> getRepaymentDocConfig(@RequestBody DocBizSceConfig docBizSceConfig) throws Exception;

	@RequestMapping(value = "/mfRepaymentConfig/initRepaymentDoc")
	public void initRepaymentDoc(@RequestBody DocBizManageParam docBizManageParam) throws Exception;

}
