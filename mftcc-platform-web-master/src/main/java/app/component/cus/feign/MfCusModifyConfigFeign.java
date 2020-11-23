package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusModifyConfig;
import app.util.toolkit.Ipage;

/**
* Title: MfCusModifyConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Nov 09 15:09:29 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusModifyConfigFeign {
	
	@RequestMapping("/mfCusModifyConfig/insert")
	public void insert(@RequestBody MfCusModifyConfig mfCusModifyConfig) throws Exception;
	
	@RequestMapping("/mfCusModifyConfig/delete")
	public void delete(@RequestBody MfCusModifyConfig mfCusModifyConfig) throws Exception;
	
	@RequestMapping("/mfCusModifyConfig/update")
	public void update(@RequestBody MfCusModifyConfig mfCusModifyConfig) throws Exception;
	
	@RequestMapping("/mfCusModifyConfig/getById")
	public MfCusModifyConfig getById(@RequestBody MfCusModifyConfig mfCusModifyConfig) throws Exception;
	
	@RequestMapping("/mfCusModifyConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
