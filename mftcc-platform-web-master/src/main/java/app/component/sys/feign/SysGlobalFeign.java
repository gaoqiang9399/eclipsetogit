package app.component.sys.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.sys.entity.SysGlobal;

@FeignClient("mftcc-platform-factor")
public interface SysGlobalFeign {
	@RequestMapping("/sysGlobal/getSysGlobal")
	public SysGlobal getSysGlobal() throws ServiceException;
	@RequestMapping("/sysGlobal/updateSts")
	public void updateSts(@RequestBody SysGlobal sysGlobal) throws ServiceException;
	@RequestMapping("/sysGlobal/updateDocSize")
	public void updateDocSize(@RequestBody SysGlobal sysGlobal) throws ServiceException;
}
