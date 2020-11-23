/**
 * Copyright (C)  版权所有
 * 文件名： TdZglInterface.java
 * 包名： app.component.thirdserviceinterface
 * 说明：
 * @author YuShuai
 * @date 2017-8-8 下午5:04:34
 * @version V1.0
 */ 
package app.component.thirdserviceinterface;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;

/**
 * 类名： TdZglInterface
 * 描述：
 * @author YuShuai
 * @date 2017-8-8 下午5:04:34
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface TdZglInterfaceFeign {

	
	/**
	 * 精确查找询价接口
	 * @param jsonParam
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdZglInterface/getHousingPricesExact")
	Map<String, Object> getHousingPricesExact(@RequestBody String jsonParam)
			throws ServiceException;

	/**
	 * 行政区划接口
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdZglInterface/getAreaCode")
	Map<String, Object> getAreaCode() throws ServiceException;

	/**
	 *  物业信息接口
	 * @param areaCode
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdZglInterface/getPropertyType")
	Map<String, Object> getPropertyType(@RequestBody String areaCode) throws ServiceException;
	
}
