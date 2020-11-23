package  app.component.cus.feign;

import app.util.toolkit.Ipage;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusAccountDetail;

/**
* Title: MfCusAccountDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jul 17 12:17:45 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusAccountDetailFeign {
	
	@RequestMapping(value = "/mfCusAccountDetail/insert")
	public void insert(@RequestBody MfCusAccountDetail mfCusAccountDetail) throws Exception;
	
	@RequestMapping(value = "/mfCusAccountDetail/delete")
	public void delete(@RequestBody MfCusAccountDetail mfCusAccountDetail) throws Exception;
	
	@RequestMapping(value = "/mfCusAccountDetail/update")
	public void update(@RequestBody MfCusAccountDetail mfCusAccountDetail) throws Exception;
	
	@RequestMapping(value = "/mfCusAccountDetail/getById")
	public MfCusAccountDetail getById(@RequestBody MfCusAccountDetail mfCusAccountDetail) throws Exception;
	
	@RequestMapping(value = "/mfCusAccountDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
