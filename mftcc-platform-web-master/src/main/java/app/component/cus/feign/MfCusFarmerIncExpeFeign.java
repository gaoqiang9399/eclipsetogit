package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusFarmerIncExpe;
import app.util.toolkit.Ipage;

/**
* Title: MfCusFarmerIncExpeBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 17 14:26:26 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusFarmerIncExpeFeign {
	@RequestMapping("/mfCusFarmerIncExpe/insert")
	public void insert(@RequestBody MfCusFarmerIncExpe mfCusFarmerIncExpe) throws Exception;
	
	@RequestMapping("/mfCusFarmerIncExpe/insertForApp")
	public MfCusFarmerIncExpe insertForApp(@RequestBody MfCusFarmerIncExpe mfCusFarmerIncExpe) throws Exception;
	
	@RequestMapping("/mfCusFarmerIncExpe/delete")
	public void delete(@RequestBody MfCusFarmerIncExpe mfCusFarmerIncExpe) throws Exception;
	
	@RequestMapping("/mfCusFarmerIncExpe/update")
	public void update(@RequestBody MfCusFarmerIncExpe mfCusFarmerIncExpe) throws Exception;
	
	@RequestMapping("/mfCusFarmerIncExpe/getById")
	public MfCusFarmerIncExpe getById(@RequestBody MfCusFarmerIncExpe mfCusFarmerIncExpe) throws Exception;
	
	@RequestMapping("/mfCusFarmerIncExpe/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
