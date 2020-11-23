package  app.component.device.record.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.device.record.entity.MfAppDevice;
import app.util.toolkit.Ipage;

/**
* Title: MfAppDeviceBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jan 16 14:12:25 CST 2018
**/

@FeignClient("mftcc-platform-factor")
public interface MfAppDeviceFeign {
	
	@RequestMapping(value = "/mfAppDevice/insert")
	public void insert(@RequestBody MfAppDevice mfAppDevice) throws Exception;
	
	@RequestMapping(value = "/mfAppDevice/delete")
	public void delete(@RequestBody MfAppDevice mfAppDevice) throws Exception;
	
	@RequestMapping(value = "/mfAppDevice/update")
	public void update(@RequestBody MfAppDevice mfAppDevice) throws Exception;
	
	@RequestMapping(value = "/mfAppDevice/getById")
	public MfAppDevice getById(@RequestBody MfAppDevice mfAppDevice) throws Exception;
	
	@RequestMapping(value = "/mfAppDevice/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfAppDevice/getList")
	public List<MfAppDevice> getList(@RequestBody MfAppDevice mfAppDevice) throws Exception;
	
}
