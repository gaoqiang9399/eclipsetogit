package  app.component.sys.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.sys.entity.SysMsgConfig;
import app.util.toolkit.Ipage;

/**
* Title: SysMsgConfigBo.java
* Description:
* @author:@dhcc.com.cn
* @Sat May 07 02:29:40 GMT 2016
**/
@FeignClient("mftcc-platform-factor")
public interface SysMsgConfigFeign {
	@RequestMapping("/sysMsgConfig/insert")
	public void insert(@RequestBody SysMsgConfig sysMsgConfig) throws ServiceException;
	@RequestMapping("/sysMsgConfig/delete")
	public void delete(@RequestBody SysMsgConfig sysMsgConfig) throws ServiceException;
	@RequestMapping("/sysMsgConfig/update")
	public void update(@RequestBody SysMsgConfig sysMsgConfig) throws ServiceException;
	@RequestMapping("/sysMsgConfig/getById")
	public SysMsgConfig getById(@RequestBody SysMsgConfig sysMsgConfig) throws ServiceException;
	@RequestMapping("/sysMsgConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping("/sysMsgConfig/getAll")
	public List<SysMsgConfig> getAll() throws ServiceException;
	
}
