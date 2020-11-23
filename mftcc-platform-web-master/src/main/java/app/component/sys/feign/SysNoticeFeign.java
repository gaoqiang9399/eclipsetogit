package  app.component.sys.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.sys.entity.SysNotice;
import app.util.toolkit.Ipage;

/**
* Title: SysNoticeBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Jul 26 04:21:37 GMT 2016
**/
@FeignClient("mftcc-platform-factor")
public interface SysNoticeFeign {
	@RequestMapping("/sysNotice/insert")
	public void insert(@RequestBody SysNotice sysNotice) throws ServiceException;
	@RequestMapping("/sysNotice/insertAndPush")
	public void insertAndPush(@RequestBody SysNotice sysNotice) throws ServiceException;
	@RequestMapping("/sysNotice/push")
	public void push(@RequestBody SysNotice sysNotice) throws ServiceException;
	@RequestMapping("/sysNotice/delete")
	public void delete(@RequestBody SysNotice sysNotice) throws ServiceException;
	@RequestMapping("/sysNotice/update")
	public void update(@RequestBody SysNotice sysNotice) throws ServiceException;
	@RequestMapping("/sysNotice/getById")
	public SysNotice getById(@RequestBody SysNotice sysNotice) throws ServiceException;
	@RequestMapping("/sysNotice/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
}
