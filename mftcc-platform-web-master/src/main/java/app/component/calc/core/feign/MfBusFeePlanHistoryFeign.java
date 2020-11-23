package  app.component.calc.core.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.core.entity.MfBusFeePlanHistory;
import app.component.pact.entity.MfBusPact;
import app.util.toolkit.Ipage;

/**
* Title: MfBusFeePlanHistoryBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 20 15:46:51 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusFeePlanHistoryFeign {
	
	@RequestMapping(value = "/mfBusFeePlanHistory/insert")
	public void insert(@RequestBody MfBusFeePlanHistory mfBusFeePlanHistory) throws Exception;
	
	@RequestMapping(value = "/mfBusFeePlanHistory/delete")
	public void delete(@RequestBody MfBusFeePlanHistory mfBusFeePlanHistory) throws Exception;
	
	@RequestMapping(value = "/mfBusFeePlanHistory/update")
	public void update(@RequestBody MfBusFeePlanHistory mfBusFeePlanHistory) throws Exception;
	
	@RequestMapping(value = "/mfBusFeePlanHistory/getById")
	public MfBusFeePlanHistory getById(@RequestBody MfBusFeePlanHistory mfBusFeePlanHistory) throws Exception;
	
	@RequestMapping(value = "/mfBusFeePlanHistory/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusFeePlanHistory") MfBusFeePlanHistory mfBusFeePlanHistory) throws Exception;

	@RequestMapping(value = "/mfBusFeePlanHistory/getFeeInfo")
	public Map<String, Object> getFeeInfo(@RequestBody MfBusPact mfBusPact)throws Exception;
	
}
