package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusShed;
import app.util.toolkit.Ipage;

/**
* Title: MfCusShedBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon May 30 09:03:56 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusShedFeign {
	
	@RequestMapping("/mfCusShed/insert")
	public void insert(@RequestBody MfCusShed mfCusShed) throws Exception;
	
//	@RequestMapping("/mfCusShed/insertForApp")
//	public MfCusShed insertForApp(@RequestBody MfCusShed mfCusShed) throws Exception;
	
	@RequestMapping("/mfCusShed/delete")
	public void delete(@RequestBody MfCusShed mfCusShed) throws Exception;
	
	@RequestMapping("/mfCusShed/update")
	public void update(@RequestBody MfCusShed mfCusShed) throws Exception;
	
	@RequestMapping("/mfCusShed/getById")
	public MfCusShed getById(@RequestBody MfCusShed mfCusShed) throws Exception;
	
	@RequestMapping("/mfCusShed/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
