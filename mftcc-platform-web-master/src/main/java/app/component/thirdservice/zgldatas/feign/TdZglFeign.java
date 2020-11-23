package app.component.thirdservice.zgldatas.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;

@FeignClient("mftcc-platform-factor")
public interface TdZglFeign {

	/**
	 * 精确查找询价接口
	 * @param jsonParam
	 * @return
	 * @throws ServiceException
	 */
	Map<String, Object> getHousingPricesExact(String jsonParam)
			throws ServiceException;

	/**
	 * 行政区划接口
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdZgl/getAreaCode")
	Map<String, Object> getAreaCode() throws ServiceException;

	/**
	 *  物业信息接口
	 * @param areaCode
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/tdZgl/getPropertyType")
	Map<String, Object> getPropertyType(@RequestBody String areaCode) throws ServiceException;

}
