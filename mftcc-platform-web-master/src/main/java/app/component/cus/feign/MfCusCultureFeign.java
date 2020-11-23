package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusCulture;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCultureBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon May 30 09:03:56 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusCultureFeign {
	
	@RequestMapping("/mfCusCulture/insert")
	public void insert(@RequestBody MfCusCulture mfCusCulture) throws Exception;
	
//	@RequestMapping("/mfCusCulture/insertForApp")
//	public MfCusCulture insertForApp(@RequestBody MfCusCulture mfCusCulture) throws Exception;
	
	@RequestMapping("/mfCusCulture/delete")
	public void delete(@RequestBody MfCusCulture mfCusCulture) throws Exception;

	@RequestMapping("/mfCusCulture/update")
	public void update(@RequestBody MfCusCulture mfCusCulture) throws Exception;
	
	@RequestMapping("/mfCusCulture/getById")
	public MfCusCulture getById(@RequestBody MfCusCulture mfCusCulture) throws Exception;
	
	@RequestMapping("/mfCusCulture/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
