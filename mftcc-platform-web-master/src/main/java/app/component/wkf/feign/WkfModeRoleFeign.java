package  app.component.wkf.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.wkf.entity.WkfModeRole;
import app.util.toolkit.Ipage;

/**
* Title: WkfModeRoleBo.java
* Description:
* @author:@dhcc.com.cn
* @Wed Jan 27 03:09:54 GMT 2016
**/
@FeignClient("mftcc-platform-factor")
public interface WkfModeRoleFeign {
	
	@RequestMapping(value = "/wkfModeRole/insert")
	public void insert(@RequestBody WkfModeRole wkfModeRole) throws ServiceException;
	
	@RequestMapping(value = "/wkfModeRole/insertRole")
	public void insertRole(@RequestBody WkfModeRole wkfModeRole) throws ServiceException;
	
	@RequestMapping(value = "/wkfModeRole/delete")
	public void delete(@RequestBody WkfModeRole wkfModeRole) throws ServiceException;
	
	@RequestMapping(value = "/wkfModeRole/ideleteByModeNonsert")
	public void deleteByModeNo(@RequestBody WkfModeRole wkfModeRole) throws ServiceException;
	
	@RequestMapping(value = "/wkfModeRole/update")
	public void update(@RequestBody WkfModeRole wkfModeRole) throws ServiceException;
	
	@RequestMapping(value = "/wkfModeRole/getById")
	public WkfModeRole getById(@RequestBody WkfModeRole wkfModeRole) throws ServiceException;
	
	@RequestMapping(value = "/wkfModeRole/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("wkfModeRole") WkfModeRole wkfModeRole) throws ServiceException;
	
	@RequestMapping(value = "/wkfModeRole/findByVpNoGroupByModeNo")
	public List<WkfModeRole> findByVpNoGroupByModeNo(@RequestBody WkfModeRole wkfModeRole) throws ServiceException;
	
	@RequestMapping(value = "/wkfModeRole/findByVpNoAndModeNo")
	public List<WkfModeRole> findByVpNoAndModeNo(@RequestBody WkfModeRole wkfModeRole) throws ServiceException;
	
	@RequestMapping(value = "/wkfModeRole/updateForChangeId")
	public void updateForChangeId(@RequestBody WkfModeRole oldWkfModeRole,@RequestParam("newWkfModeRole") WkfModeRole newWkfModeRole) throws ServiceException;
}
