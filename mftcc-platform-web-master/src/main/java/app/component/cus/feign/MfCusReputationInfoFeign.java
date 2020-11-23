package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusReputationInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusReputationInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Aug 18 10:27:55 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusReputationInfoFeign {
	
	@RequestMapping("/mfCusReputationInfo/insert")
	public void insert(@RequestBody MfCusReputationInfo mfCusReputationInfo) throws Exception;
	
	@RequestMapping("/mfCusReputationInfo/delete")
	public void delete(@RequestBody MfCusReputationInfo mfCusReputationInfo) throws Exception;
	
	@RequestMapping("/mfCusReputationInfo/update")
	public void update(@RequestBody MfCusReputationInfo mfCusReputationInfo) throws Exception;
	
	@RequestMapping("/mfCusReputationInfo/getById")
	public MfCusReputationInfo getById(@RequestBody MfCusReputationInfo mfCusReputationInfo) throws Exception;
	
	@RequestMapping("/mfCusReputationInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
