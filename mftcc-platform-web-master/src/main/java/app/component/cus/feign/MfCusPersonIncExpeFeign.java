package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusPersonIncExpe;
import app.util.toolkit.Ipage;

/**
* Title: MfCusPersonIncExpeBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon May 30 09:03:56 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusPersonIncExpeFeign {
	
	@RequestMapping("/mfCusPersonIncExpe/insert")
	public void insert(@RequestBody MfCusPersonIncExpe mfCusPersonIncExpe) throws Exception;
	
	@RequestMapping("/mfCusPersonIncExpe/insertForApp")
	public MfCusPersonIncExpe insertForApp(@RequestBody MfCusPersonIncExpe mfCusPersonIncExpe) throws Exception;
	
	@RequestMapping("/mfCusPersonIncExpe/delete")
	public void delete(@RequestBody MfCusPersonIncExpe mfCusPersonIncExpe) throws Exception;
	
	@RequestMapping("/mfCusPersonIncExpe/update")
	public void update(@RequestBody MfCusPersonIncExpe mfCusPersonIncExpe) throws Exception;
	
	@RequestMapping("/mfCusPersonIncExpe/getById")
	public MfCusPersonIncExpe getById(@RequestBody MfCusPersonIncExpe mfCusPersonIncExpe) throws Exception;
	
	@RequestMapping("/mfCusPersonIncExpe/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
