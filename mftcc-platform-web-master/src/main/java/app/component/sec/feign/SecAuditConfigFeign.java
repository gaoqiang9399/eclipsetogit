package  app.component.sec.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sec.entity.SecAuditConfig;
import app.util.toolkit.Ipage;

/**
* Title: SecAuditConfigBo.java
* Description:
* @author:@dhcc.com.cn
* @Mon Feb 22 07:15:15 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface SecAuditConfigFeign {
	
	@RequestMapping(value = "/secAuditConfig/insert")
	public void insert(@RequestBody SecAuditConfig secAuditConfig) throws ServiceException;
	
	@RequestMapping(value = "/secAuditConfig/delete")
	public void delete(@RequestBody SecAuditConfig secAuditConfig) throws ServiceException;
	
	@RequestMapping(value = "/secAuditConfig/update")
	public void update(@RequestBody SecAuditConfig secAuditConfig) throws ServiceException;
	
	@RequestMapping(value = "/secAuditConfig/updateSts")
	public void updateSts(@RequestBody List<SecAuditConfig> list) throws ServiceException;
	
	@RequestMapping(value = "/secAuditConfig/getById")
	public SecAuditConfig getById(@RequestBody SecAuditConfig secAuditConfig) throws ServiceException;
	
	@RequestMapping(value = "/secAuditConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("secAuditConfig") SecAuditConfig secAuditConfig) throws ServiceException;
	
	@RequestMapping(value = "/secAuditConfig/getAll")
	public List<SecAuditConfig> getAll(@RequestBody SecAuditConfig secAuditConfig) throws ServiceException;
	
	@RequestMapping(value = "/secAuditConfig/getListForSec")
	public List<SecAuditConfig> getListForSec(@RequestBody SecAuditConfig secAudit) throws ServiceException;
	
	@RequestMapping(value = "/secAuditConfig/getPwResult")
	public Map<String,SecAuditConfig> getPwResult() throws ServiceException;
	
}
