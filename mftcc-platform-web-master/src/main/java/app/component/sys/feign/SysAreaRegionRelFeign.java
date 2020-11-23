package  app.component.sys.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.sys.entity.SysAreaRegionRel;
import app.util.toolkit.Ipage;

/**
* Title: SysAreaRegionRelBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue May 10 03:42:07 GMT 2016
**/
@FeignClient("mftcc-platform-factor")
public interface SysAreaRegionRelFeign {
	@RequestMapping("/sysAreaRegionRel/insert")
	public void insert(@RequestBody SysAreaRegionRel sysAreaRegionRel) throws ServiceException;
	@RequestMapping("/sysAreaRegionRel/delete")
	public void delete(@RequestBody SysAreaRegionRel sysAreaRegionRel) throws ServiceException;
	@RequestMapping("/sysAreaRegionRel/update")
	public void update(@RequestBody SysAreaRegionRel sysAreaRegionRel) throws ServiceException;
	@RequestMapping("/sysAreaRegionRel/getById")
	public SysAreaRegionRel getById(@RequestBody SysAreaRegionRel sysAreaRegionRel) throws ServiceException;
	@RequestMapping("/sysAreaRegionRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping("/sysAreaRegionRel/getCountByProvNo")
	public int getCountByProvNo(@RequestBody SysAreaRegionRel sysAreaRegionRel)throws ServiceException;
}
