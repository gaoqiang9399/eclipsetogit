package  app.component.wkf.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.wkf.entity.WkfApprovalRole;
import app.util.toolkit.Ipage;

/**
* Title: WkfApprovalRoleBo.java
* Description:
* @author:zhanglei@dhcc.com.cn
* @Thu Feb 21 14:01:33 CST 2013
**/
@FeignClient("mftcc-platform-factor")
public interface WkfApprovalRoleFeign {
	
	@RequestMapping(value = "/wkfApprovalRole/getById")
	public WkfApprovalRole getById(@RequestBody WkfApprovalRole wkfApprovalRole) throws ServiceException;

	@RequestMapping(value = "/wkfApprovalRole/del")
	public void del(@RequestBody WkfApprovalRole wkfApprovalRole) throws ServiceException;

	@RequestMapping(value = "/wkfApprovalRole/insert")
	public void insert(@RequestBody WkfApprovalRole wkfApprovalRole) throws ServiceException;

	@RequestMapping(value = "/wkfApprovalRole/update")
	public void update(@RequestBody WkfApprovalRole wkfApprovalRole) throws ServiceException;

	@RequestMapping(value = "/wkfApprovalRole/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg) throws ServiceException;
	
	@RequestMapping(value = "/wkfApprovalRole/getAllList")
	public List<WkfApprovalRole> getAllList(@RequestBody WkfApprovalRole wkfApprovalRole) throws ServiceException;

	@RequestMapping(value = "/wkfApprovalRole/insertOrUpdate")
	public void insertOrUpdate(@RequestBody WkfApprovalRole wkfApprovalRole,@RequestParam("members") String members,@RequestParam("saveFlag") String saveFlag)throws ServiceException;

	@RequestMapping(value = "/wkfApprovalRole/delGroup")
	public void delGroup(@RequestBody WkfApprovalRole wkfApprovalRole)throws ServiceException;

	@RequestMapping(value = "/wkfApprovalRole/getByAppId")
	public String getByAppId(@RequestBody Map<String,String> map)throws ServiceException;
	
}