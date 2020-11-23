/**
 * Copyright (C) DXHM 版权所有
 * 文件名： RiskDataBo.java
 * 包名： app.component.rules.bo
 * 说明：
 * @author 沈浩兵
 * @date 2017-1-17 下午5:05:57
 * @version V1.0
 */ 
package app.component.rules.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.rules.entity.AccesBigBean;
import app.component.rules.entity.RiskBigBean;

/**
 * 类名： RiskDataBo
 * 描述：根据客户或业务相关参数获得客户或业务数据封装风险拦截大实体
 * @author 沈浩兵
 * @date 2017-1-17 下午5:05:57
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface RiskDataFeign {
	/**
	 * 
	 * 方法描述： 根据客户和业务参数调用规则引擎获得风险拦截大实体
	 * @return
	 * @throws ServiceException
	 * RiskBigBean
	 * @author 沈浩兵
	 * @date 2017-1-18 上午9:53:11
	 */
	@RequestMapping(value = "/riskData/getRiskBigBeanByRules")
	public RiskBigBean getRiskBigBeanByRules(@RequestBody Map<String, Object> dataMap) throws ServiceException;

	/**
	 * 方法描述： 获取规则引擎返回的结果
	 * @param dataMap
	 * @return
	 * AccesBigBean
	 * @author YuShuai
	 * @date 2017-5-10 上午9:26:23
	 */
	@RequestMapping(value = "/riskData/getAccesBigBeanByRules")
	public AccesBigBean getAccesBigBeanByRules(@RequestBody Map<String, Object> dataMap) throws Exception;
}
