package  app.component.thirdpay.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.thirdpay.entity.MfRequestThirdPartyData;
import app.util.toolkit.Ipage;

/**
* Title: MfRequestThirdPartyDataBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Nov 09 14:41:04 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfRequestThirdPartyDataFeign {
	
	@RequestMapping(value = "/mfRequestThirdPartyData/insert")
	public void insert(@RequestBody MfRequestThirdPartyData mfRequestThirdPartyData) throws Exception;
	
	@RequestMapping(value = "/mfRequestThirdPartyData/delete")
	public void delete(@RequestBody MfRequestThirdPartyData mfRequestThirdPartyData) throws Exception;
	
	@RequestMapping(value = "/mfRequestThirdPartyData/update")
	public void update(@RequestBody MfRequestThirdPartyData mfRequestThirdPartyData) throws Exception;
	
	@RequestMapping(value = "/mfRequestThirdPartyData/getById")
	public MfRequestThirdPartyData getById(@RequestBody MfRequestThirdPartyData mfRequestThirdPartyData) throws Exception;
	
	@RequestMapping(value = "/mfRequestThirdPartyData/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfRequestThirdPartyData") MfRequestThirdPartyData mfRequestThirdPartyData) throws Exception;
	
}
