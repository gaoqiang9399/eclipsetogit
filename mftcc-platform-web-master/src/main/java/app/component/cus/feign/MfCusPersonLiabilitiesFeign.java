package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusPersonLiabilities;
import app.util.toolkit.Ipage;

/**
* Title: MfCusPersonLiabilitiesBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Aug 05 16:03:23 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusPersonLiabilitiesFeign {
	
	@RequestMapping("/mfCusPersonLiabilities/insert")
	public void insert(@RequestBody MfCusPersonLiabilities mfCusPersonLiabilities) throws Exception;
	
	@RequestMapping("/mfCusPersonLiabilities/delete")
	public void delete(@RequestBody MfCusPersonLiabilities mfCusPersonLiabilities) throws Exception;
	
	@RequestMapping("/mfCusPersonLiabilities/update")
	public void update(@RequestBody MfCusPersonLiabilities mfCusPersonLiabilities) throws Exception;
	
	@RequestMapping("/mfCusPersonLiabilities/getById")
	public MfCusPersonLiabilities getById(@RequestBody MfCusPersonLiabilities mfCusPersonLiabilities) throws Exception;
	@RequestMapping("/mfCusPersonLiabilities/autoCalc")
	public MfCusPersonLiabilities autoCalc(@RequestBody MfCusPersonLiabilities mfCusPersonLiabilities) throws Exception;

	@RequestMapping("/mfCusPersonLiabilities/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusPersonLiabilities/getByAppId")
	public List<MfCusPersonLiabilities> getByAppId(@RequestBody MfCusPersonLiabilities mfCusPersonLiabilities) throws Exception;
	
}
