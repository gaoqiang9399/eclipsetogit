package app.component.wkf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.wkf.entity.WkfAppProcess;
@FeignClient("mftcc-platform-factor")
public interface WkfAppProcessFeign {
	
	@RequestMapping(value = "/wkfAppProcess/insert")
	public void insert(@RequestBody WkfAppProcess wkfAppProcess)throws ServiceException;

	@RequestMapping(value = "/wkfAppProcess/getById")
	public WkfAppProcess getById(@RequestBody WkfAppProcess wkfAppProcess)throws ServiceException;
	
	@RequestMapping(value = "/wkfAppProcess/delete")
	public void delete(@RequestBody WkfAppProcess wkfAppProcess)throws ServiceException;
	
	@RequestMapping(value = "/wkfAppProcess/deleteByProcessInstanceId")
	public void deleteByProcessInstanceId(@RequestBody WkfAppProcess wkfAppProcess)throws ServiceException;
}
