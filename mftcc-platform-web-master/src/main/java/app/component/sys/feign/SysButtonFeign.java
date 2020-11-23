package  app.component.sys.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.sys.entity.SysButton;
import app.util.toolkit.Ipage;

/**
* Title: SysButtonBo.java
* Description:
* @author:lifeng@dhcc.com.cn
* @Thu Mar 14 12:47:13 GMT 2013
**/
@FeignClient("mftcc-platform-factor")
public interface SysButtonFeign {
	@RequestMapping("/sysButton/getById")
	public SysButton getById(@RequestBody SysButton sysButton) throws ServiceException;
	@RequestMapping("/sysButton/del")
	public void del(@RequestBody SysButton sysButton) throws ServiceException;
	@RequestMapping("/sysButton/insert")
	public void insert(@RequestBody SysButton sysButton) throws ServiceException;
	@RequestMapping("/sysButton/update")
	public void update(@RequestBody SysButton sysButton) throws ServiceException;
	@RequestMapping("/sysButton/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg) throws ServiceException;
	@RequestMapping("/sysButton/findAllByMenu")
	public List<SysButton> findAllByMenu(@RequestBody SysButton sysButton) throws ServiceException;

}