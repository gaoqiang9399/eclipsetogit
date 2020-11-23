package  app.component.sys.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.sys.entity.MfBusModel;
import app.util.toolkit.Ipage;

/**
* Title: MfBusModelBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Oct 25 11:45:14 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfBusModelFeign {
	@RequestMapping("/mfBusModel/insert")
	public void insert(@RequestBody MfBusModel mfBusModel) throws Exception;
	@RequestMapping("/mfBusModel/delete")
	public void delete(@RequestBody MfBusModel mfBusModel) throws Exception;
	@RequestMapping("/mfBusModel/update")
	public void update(@RequestBody MfBusModel mfBusModel) throws Exception;
	@RequestMapping("/mfBusModel/getById")
	public MfBusModel getById(@RequestBody MfBusModel mfBusModel) throws Exception;
	@RequestMapping("/mfBusModel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
