package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusChattelMortgage;
import app.util.toolkit.Ipage;
/**
* Title: MfCusChattelMortgageBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Apr 23 15:57:59 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusChattelMortgageFeign {
	@RequestMapping(value = "/mfCusChattelMortgage/insert")
	public void insert(@RequestBody MfCusChattelMortgage mfCusChattelMortgage) throws Exception;
	@RequestMapping(value = "/mfCusChattelMortgage/delete")
	public void delete(@RequestBody MfCusChattelMortgage mfCusChattelMortgage) throws Exception;
	@RequestMapping(value = "/mfCusChattelMortgage/update")
	public void update(@RequestBody MfCusChattelMortgage mfCusChattelMortgage) throws Exception;
	@RequestMapping(value = "/mfCusChattelMortgage/getById")
	public MfCusChattelMortgage getById(@RequestBody MfCusChattelMortgage mfCusChattelMortgage) throws Exception;
	@RequestMapping(value = "/mfCusChattelMortgage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
