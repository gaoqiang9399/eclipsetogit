package app.component.sys.feign;


import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.sys.entity.SysRoleUrl;

@FeignClient("mftcc-platform-factor")
public interface SysRoleUrlFeign {
	@RequestMapping("/sysRoleUrl/insert")
	public void insert(@RequestBody SysRoleUrl sysRoleUrl) throws ServiceException;
	@RequestMapping("/sysRoleUrl/getById")
	public List<SysRoleUrl> getById(@RequestBody SysRoleUrl SysRoleUrl) throws ServiceException;
	@RequestMapping("/sysRoleUrl/delete")
	public void delete(@RequestBody SysRoleUrl sysRoleUrl) throws ServiceException;
	@RequestMapping("/sysRoleUrl/getByUserNo")
	public List<SysRoleUrl> getByUserNo(@RequestBody SysRoleUrl sysRoleUrl) throws ServiceException;
}
