package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import app.component.cus.entity.MfCusLogisticsInformation;
import app.util.toolkit.Ipage;

/**
* Title: MfCusLogisticsInformationBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Apr 23 15:57:22 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusLogisticsInformationFeign {
	@RequestMapping(value = "/mfCusLogisticsInformation/insert")
	public void insert(@RequestBody MfCusLogisticsInformation mfCusLogisticsInformation) throws Exception;
	@RequestMapping(value = "/mfCusLogisticsInformation/delete")
	public void delete(@RequestBody MfCusLogisticsInformation mfCusLogisticsInformation) throws Exception;
	@RequestMapping(value = "/mfCusLogisticsInformation/update")
	public void update(@RequestBody MfCusLogisticsInformation mfCusLogisticsInformation) throws Exception;
	@RequestMapping(value = "/mfCusLogisticsInformation/getById")
	public MfCusLogisticsInformation getById(@RequestBody MfCusLogisticsInformation mfCusLogisticsInformation) throws Exception;
	@RequestMapping(value = "/mfCusLogisticsInformation/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
