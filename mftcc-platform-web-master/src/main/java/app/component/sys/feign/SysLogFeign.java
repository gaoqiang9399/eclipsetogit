package  app.component.sys.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.sys.entity.SysLog;
import app.util.toolkit.Ipage;

/**
* Title: SysLogBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Mar 01 06:53:50 GMT 2016
**/
@FeignClient("mftcc-platform-factor")
public interface SysLogFeign {
	@RequestMapping("/sysLog/insert")
	public void insert(@RequestBody SysLog sysLog) throws ServiceException;
	@RequestMapping("/sysLog/delete")
	public void delete(@RequestBody SysLog sysLog) throws ServiceException;
	@RequestMapping("/sysLog/update")
	public void update(@RequestBody SysLog sysLog) throws ServiceException;
	@RequestMapping("/sysLog/getById")
	public SysLog getById(@RequestBody SysLog sysLog) throws ServiceException;
	@RequestMapping("/sysLog/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping("/sysLog/insertOrUpdate")
	public void insertOrUpdate(@RequestBody SysLog syslog) throws ServiceException;
	
}
