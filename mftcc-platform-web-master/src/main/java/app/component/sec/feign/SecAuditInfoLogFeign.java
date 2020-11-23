package  app.component.sec.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sec.entity.SecAuditInfoLog;
import app.util.toolkit.Ipage;

/**
* Title: SecAuditInfoLogBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Feb 23 02:46:27 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface SecAuditInfoLogFeign {
	
	@RequestMapping(value = "/secAuditInfoLog/insert")
	public void insert(@RequestBody SecAuditInfoLog secAuditInfoLog) throws ServiceException;
	
	@RequestMapping(value = "/secAuditInfoLog/delete")
	public void delete(@RequestBody SecAuditInfoLog secAuditInfoLog) throws ServiceException;
	
	@RequestMapping(value = "/secAuditInfoLog/update")
	public void update(@RequestBody SecAuditInfoLog secAuditInfoLog) throws ServiceException;
	
	@RequestMapping(value = "/secAuditInfoLog/getById")
	public SecAuditInfoLog getById(@RequestBody SecAuditInfoLog secAuditInfoLog) throws ServiceException;
	
	@RequestMapping(value = "/secAuditInfoLog/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("secAuditInfoLog") SecAuditInfoLog secAuditInfoLog) throws ServiceException;
	
}
