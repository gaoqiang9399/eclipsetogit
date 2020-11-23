package app.component.sys.feign;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.sys.entity.SysLoginLog;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface SysLoginLogFeign {
	@RequestMapping("/sysLoginLog/insert")
	public void insert(@RequestBody SysLoginLog sysLoginLog) throws ServiceException;
	@RequestMapping("/sysLoginLog/delete")
	public void delete(@RequestBody SysLoginLog sysLoginLog) throws ServiceException;
	@RequestMapping("/sysLoginLog/update")
	public void update(@RequestBody SysLoginLog sysLoginLog) throws ServiceException;
	@RequestMapping("/sysLoginLog/getById")
	public SysLoginLog getById(@RequestBody SysLoginLog sysLoginLog) throws ServiceException;
	@RequestMapping("/sysLoginLog/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping("/sysLoginLog/findByPageOnLine")
	public Ipage findByPageOnLine(@RequestBody Ipage ipage) throws ServiceException;
}
