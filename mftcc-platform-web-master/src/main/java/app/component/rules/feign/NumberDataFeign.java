/**
 * Copyright (C) DXHM 版权所有
 * 文件名： NumberDataBo.java
 * 包名： app.component.rules.bo
 * 说明：
 * @author 沈浩兵
 * @date 2017-4-24 下午3:46:27
 * @version V1.0
 */ 
package app.component.rules.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.rules.entity.NumberBigBean;

/**
 * 类名： NumberDataBo
 * 描述：
 * @author 沈浩兵
 * @date 2017-4-24 下午3:46:27
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface NumberDataFeign {
	/**
	 * 
	 * 方法描述： 调用规则生成编号
	 * @param numberBigBean
	 * @return
	 * @throws ServiceException
	 * NumberBigBean
	 * @author 沈浩兵
	 * @date 2017-4-24 下午3:49:19
	 */
	@RequestMapping(value = "/numberData/getNumberDataByRules")
	public NumberBigBean getNumberDataByRules(@RequestBody NumberBigBean numberBigBean) throws ServiceException;;
}
