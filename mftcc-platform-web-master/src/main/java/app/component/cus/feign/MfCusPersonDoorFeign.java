package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusPersonDoor;
import app.util.toolkit.Ipage;

/**
* Title: MfCusPersonDoorBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon May 30 16:52:59 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusPersonDoorFeign {
	
	@RequestMapping("/mfCusPersonDoor/insert")
	public void insert(@RequestBody MfCusPersonDoor mfCusPersonDoor) throws Exception;
	
	@RequestMapping("/mfCusPersonDoor/delete")
	public void delete(@RequestBody MfCusPersonDoor mfCusPersonDoor) throws Exception;
	
	@RequestMapping("/mfCusPersonDoor/update")
	public void update(@RequestBody MfCusPersonDoor mfCusPersonDoor) throws Exception;
	
	@RequestMapping("/mfCusPersonDoor/getById")
	public MfCusPersonDoor getById(@RequestBody MfCusPersonDoor mfCusPersonDoor) throws Exception;
	
	@RequestMapping("/mfCusPersonDoor/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
