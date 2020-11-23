package app.component.app.feign;


import app.base.ServiceException;

import app.component.app.entity.MfBusReconsiderApply;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;




/**
* Title: MfBusApplyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 21 10:40:47 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusReconsiderApplyFeign {


	@RequestMapping(value = "/mfBusReconsiderApply/getById")
	public MfBusReconsiderApply getById(@RequestBody MfBusReconsiderApply mfBusReconsiderApply) throws ServiceException;



}
