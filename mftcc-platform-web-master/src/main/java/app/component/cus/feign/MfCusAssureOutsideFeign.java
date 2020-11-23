package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusAssureOutside;
import app.util.toolkit.Ipage;

/**
* Title: MfCusAssureOutsideBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Nov 28 11:34:05 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusAssureOutsideFeign {
	@RequestMapping("/mfCusAssureOutside/insert")
	public void insert(@RequestBody MfCusAssureOutside mfCusAssureOutside) throws Exception;
	
	@RequestMapping("/mfCusAssureOutside/delete")
	public void delete(@RequestBody MfCusAssureOutside mfCusAssureOutside) throws Exception;
	
	@RequestMapping("/mfCusAssureOutside/update")
	public void update(@RequestBody MfCusAssureOutside mfCusAssureOutside) throws Exception;
	
	@RequestMapping("/mfCusAssureOutside/getById")
	public MfCusAssureOutside getById(@RequestBody MfCusAssureOutside mfCusAssureOutside) throws Exception;
	
	@RequestMapping("/mfCusAssureOutside/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfCusAssureOutside/getMfAssureList")
	public Ipage getMfAssureList(@RequestBody Ipage ipage)throws Exception;
	
}
