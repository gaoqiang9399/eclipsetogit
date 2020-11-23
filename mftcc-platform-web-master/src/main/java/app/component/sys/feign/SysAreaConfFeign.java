package  app.component.sys.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sys.entity.SysAreaConf;
import app.util.toolkit.Ipage;

/**
* Title: SysAreaConfBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue May 10 03:41:01 GMT 2016
**/
@FeignClient("mftcc-platform-factor")
public interface SysAreaConfFeign {
	@RequestMapping("/sysAreaConf/insert")
	public void insert(@RequestBody SysAreaConf sysAreaConf) throws ServiceException;
	@RequestMapping("/sysAreaConf/delete")
	public void delete(@RequestBody SysAreaConf sysAreaConf) throws ServiceException;
	@RequestMapping("/sysAreaConf/insert")
	public void update(@RequestBody SysAreaConf sysAreaConf) throws ServiceException;
	@RequestMapping("/sysAreaConf/insert")
	public SysAreaConf getById(@RequestBody SysAreaConf sysAreaConf) throws ServiceException;
	@RequestMapping("/sysAreaConf/insert")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping("/sysAreaConf/insert")
	public SysAreaConf getSysAreaConfByProvNo(@RequestParam("provNo") String provNo)throws ServiceException;
	
}
