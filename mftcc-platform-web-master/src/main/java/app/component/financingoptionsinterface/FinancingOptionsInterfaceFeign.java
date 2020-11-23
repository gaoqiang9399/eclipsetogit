/**
 * Copyright (C) DXHM 版权所有
 * 文件名： FinancingOptionsInterface.java
 * 包名： app.component.financingoptionsinterface
 * 说明： 
 * @author 仇招
 * @date 2018年7月11日 上午11:34:09
 * @version V1.0
 */ 
package app.component.financingoptionsinterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.financingoptions.entity.MfFinancingOptions;

/**
 * 类名： FinancingOptionsInterface
 * 描述：融资方案接口
 * @author 仇招
 * @date 2018年7月11日 上午11:34:09
 */
@FeignClient("mftcc-platform-factor")
public interface FinancingOptionsInterfaceFeign {

	@RequestMapping("/financingOptionsInterface/getBySelectCondition")
	List<MfFinancingOptions> getBySelectCondition(@RequestBody MfFinancingOptions mfFinancingOptions)throws Exception;

}
