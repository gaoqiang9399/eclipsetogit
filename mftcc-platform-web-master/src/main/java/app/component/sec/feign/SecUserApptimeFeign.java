package  app.component.sec.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sec.entity.SecUserApptime;
import app.util.toolkit.Ipage;

/**
* Title: SecUserApptimeBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Feb 23 02:28:25 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface SecUserApptimeFeign {
	
	@RequestMapping(value = "/secUserApptime/insert")
	public void insert(@RequestBody SecUserApptime secUserApptime) throws ServiceException;
	
	@RequestMapping(value = "/secUserApptime/delete")
	public void delete(@RequestBody SecUserApptime secUserApptime) throws ServiceException;
	
	@RequestMapping(value = "/secUserApptime/update")
	public void update(@RequestBody SecUserApptime secUserApptime) throws ServiceException;
	
	@RequestMapping(value = "/secUserApptime/getById")
	public SecUserApptime getById(@RequestBody SecUserApptime secUserApptime) throws ServiceException;
	
	@RequestMapping(value = "/secUserApptime/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("secUserApptime") SecUserApptime secUserApptime) throws ServiceException;
	
}
