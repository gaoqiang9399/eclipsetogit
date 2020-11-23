/**
 * Copyright (C) DXHM 版权所有
 * 文件名： CarModelInterface.java
 * 包名： app.component.carmodelinterface
 * 说明： 
 * @author 仇招
 * @date 2018年7月6日 下午7:49:24
 * @version V1.0
 */ 
package app.component.carmodelinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.carmodel.entity.MfCarBrand;
import app.component.carmodel.entity.MfCarModel;
import app.component.carmodel.entity.MfCarSeries;

/**
 * 类名： CarModelInterface
 * 描述：车型库接口
 * @author 仇招
 * @date 2018年7月6日 下午7:49:24
 */
@FeignClient("mftcc-platform-factor")
public interface CarModelInterfaceFeign {
	
	/**
	 * 方法描述： 获取所有车辆品牌
	 * @return
	 * @throws Exception
	 * List<MfCarBrand>
	 * @author 仇招
	 * @date 2018年7月6日 下午7:53:29
	 */
	@RequestMapping("/carModelInterface/getMfCarBrandAllList")
	public List<MfCarBrand> getMfCarBrandAllList() throws Exception;
	
	/**
	 * 方法描述： 根据品牌ID获取品牌下所有车系
	 * @return
	 * @throws Exception
	 * List<MfCarSeries>
	 * @author 仇招
	 * @date 2018年7月6日 下午7:55:36
	 */
	@RequestMapping("/carModelInterface/getMfCarSeriesByBrandId")
	public List<MfCarSeries> getMfCarSeriesByBrandId(@RequestBody MfCarSeries mfCarSeries) throws Exception;
	
	/**
	 * 方法描述： 根据车系获取该车系下所有车型
	 * @param mfCarSeries
	 * @return
	 * @throws Exception
	 * List<MfCarModel>
	 * @author 仇招
	 * @date 2018年7月6日 下午7:57:38
	 */
	@RequestMapping("/carModelInterface/getMfCarModelBySeriesId")
	public List<MfCarModel> getMfCarModelBySeriesId(@RequestBody MfCarModel mfCarModel) throws Exception;
	
	/**
	 * 方法描述： 更新车型库
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年7月9日 上午10:45:22
	 */
	@RequestMapping("/carModelInterface/updateCarModelLibrary")
	public Map<String,Object> updateCarModelLibrary() throws Exception;
	
}
