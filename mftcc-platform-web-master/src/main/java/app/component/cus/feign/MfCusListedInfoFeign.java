package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusListedInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusListedInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Mar 09 16:41:38 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusListedInfoFeign {
	
	@RequestMapping("/mfCusListedInfo/insert")
	public void insert(@RequestBody MfCusListedInfo mfCusListedInfo) throws Exception;
	
	@RequestMapping("/mfCusListedInfo/delete")
	public void delete(@RequestBody MfCusListedInfo mfCusListedInfo) throws Exception;
	
	@RequestMapping("/mfCusListedInfo/update")
	public void update(@RequestBody MfCusListedInfo mfCusListedInfo) throws Exception;
	
	@RequestMapping("/mfCusListedInfo/getById")
	public MfCusListedInfo getById(@RequestBody MfCusListedInfo mfCusListedInfo) throws Exception;
	
	@RequestMapping("/mfCusListedInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
