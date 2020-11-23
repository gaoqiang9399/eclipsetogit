package  app.component.app.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.app.entity.MfBusRepayRules;

/**
* Title: MfBusRepayRulesBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Aug 28 16:38:07 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusRepayRulesFeign {
	
	@RequestMapping(value = "/mfBusRepayRules/insert")
	public void insert(@RequestBody MfBusRepayRules mfBusRepayRules) throws Exception;
	
	@RequestMapping(value = "/mfBusRepayRules/delete")
	public void delete(@RequestBody MfBusRepayRules mfBusRepayRules) throws Exception;
	
	@RequestMapping(value = "/mfBusRepayRules/update")
	public void update(@RequestBody MfBusRepayRules mfBusRepayRules) throws Exception;
	
	@RequestMapping(value = "/mfBusRepayRules/getById")
	public MfBusRepayRules getById(@RequestBody MfBusRepayRules mfBusRepayRules) throws Exception;
	
	@RequestMapping(value = "/mfBusRepayRules/findList")
	public List<MfBusRepayRules> findList(@RequestBody MfBusRepayRules mfBusRepayRules) throws Exception;	
}
