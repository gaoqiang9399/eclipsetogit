package  app.component.sys.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.sys.entity.SysDescTemp;
import app.util.toolkit.Ipage;

/**
* Title: ParmKeyBo.java
* Description:
* @author:jiangyunxin@dhcc.com.cn
* @Thu Apr 10 09:10:06 GMT 2014
**/
@FeignClient("mftcc-platform-factor")
public interface SysDescTempFeign {
	@RequestMapping("/sysDescTemp/getById")
	public SysDescTemp getById(@RequestBody SysDescTemp sysDescTemp) throws ServiceException;
	@RequestMapping("/sysDescTemp/del")
	public void del(@RequestBody SysDescTemp sysDescTemp) throws ServiceException;
	@RequestMapping("/sysDescTemp/insert")
	public void insert(@RequestBody SysDescTemp sysDescTemp) throws ServiceException;
	@RequestMapping("/sysDescTemp/update")
	public void update(@RequestBody SysDescTemp sysDescTemp) throws ServiceException;
	@RequestMapping("/sysDescTemp/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipgp) throws ServiceException;
	@RequestMapping("/sysDescTemp/findByPageList")
	public List<SysDescTemp> findByPage(@RequestBody  SysDescTemp sysDescTemp)throws ServiceException;
	@RequestMapping("/sysDescTemp/getAll")
	public List<SysDescTemp> getAll()throws ServiceException;
}