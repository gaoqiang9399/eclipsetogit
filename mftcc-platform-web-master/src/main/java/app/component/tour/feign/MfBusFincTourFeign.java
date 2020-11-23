package  app.component.tour.feign;

import app.component.tour.entity.MfBusFincTour;
import app.util.toolkit.Ipage;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


/**
* Title: MfBusFincAppChildBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 26 13:09:22 CST 2018
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusFincTourFeign {
	
	@RequestMapping("/mfbusFincTour/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfbusFincTour/insert")
	public void insert(@RequestBody MfBusFincTour mfBusFincTour) throws Exception;


	@RequestMapping("/mfbusFincTour/getById")
	public MfBusFincTour getById(@RequestBody MfBusFincTour mfBusFincTour) throws Exception;

	@RequestMapping("/mfbusFincTour/delete")
	public void delete(@RequestBody MfBusFincTour mfBusFincTour) throws Exception;

	@RequestMapping("/mfbusFincTour/update")
	public void update(@RequestBody MfBusFincTour mfBusFincTour) throws Exception;
	
	
}
