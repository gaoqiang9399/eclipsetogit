/**
 * Copyright (C) DXHM 版权所有
 * 文件名： EvalDataBo.java
 * 包名： app.component.rules.bo
 * 说明：
 * @author 沈浩兵
 * @date 2017-1-17 下午5:06:19
 * @version V1.0
 */ 
package app.component.rules.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.rules.entity.EvalBigBean;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 类名： EvalDataBo
 * 描述：根据客户或业务相关参数获得客户或业务数据封装客户评级大实体
 * @author 沈浩兵
 * @date 2017-1-17 下午5:06:19
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface EvalDataFeign {
	/**
	 * 
	 * 方法描述： 根据客户和业务参数调用规则引擎获得客户评级大实体
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * EvalBigBean
	 * @author 沈浩兵
	 * @date 2017-1-18 下午4:30:00
	 */
	@RequestMapping(value = "/evalData/getEvalDataByRules")
	public EvalBigBean getEvalDataByRules(@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/evalDataBo/getFinancialRatio")
	Map<String, Object> getFinancialRatio(@RequestParam("evalAppNo") String evalAppNo)throws Exception;
}
