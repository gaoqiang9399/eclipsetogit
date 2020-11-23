package  app.component.calc.penalty.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.calc.penalty.entity.MfBusAppPenaltyMain;

/**
* Title: MfBusAppPenaltyMainBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jul 01 17:31:56 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusAppPenaltyMainFeign {
	
	@RequestMapping(value = "/mfBusAppPenaltyMain/insert")
	public void insert(@RequestBody MfBusAppPenaltyMain mfBusAppPenaltyMain) throws Exception;
	
	@RequestMapping(value = "/mfBusAppPenaltyMain/delete")
	public void delete(@RequestBody MfBusAppPenaltyMain mfBusAppPenaltyMain) throws Exception;
	
	@RequestMapping(value = "/mfBusAppPenaltyMain/update")
	public void update(@RequestBody MfBusAppPenaltyMain mfBusAppPenaltyMain) throws Exception;
	
	@RequestMapping(value = "/mfBusAppPenaltyMain/getById")
	public MfBusAppPenaltyMain getById(@RequestBody MfBusAppPenaltyMain mfBusAppPenaltyMain) throws Exception;
	
}
