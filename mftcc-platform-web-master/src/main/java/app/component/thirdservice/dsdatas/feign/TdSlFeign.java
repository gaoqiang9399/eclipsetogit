/**
 * Copyright (C) DXHM 版权所有
 * 文件名： TdSlBo.java
 * 包名： app.component.thirdservice.dsdatas.bo
 * 说明：
 * @author wzw
 * @date 2017-11-17 下午3:53:56
 * @version V1.0
 */ 
package app.component.thirdservice.dsdatas.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;

/**
 * 类名： TdSlBo
 * 描述：索伦第三方数据服务
 * @author wzw
 * @date 2017-11-17 下午3:53:56
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface TdSlFeign {

	
	/**
	 * 索伦-反欺诈
	 * @param jsonParam
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdSl/getSauron")
	Map<String, Object> getSauron(@RequestBody String jsonParam) throws ServiceException;
	
}
