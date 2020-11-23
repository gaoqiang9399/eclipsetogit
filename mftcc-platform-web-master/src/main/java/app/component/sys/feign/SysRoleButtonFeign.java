package  app.component.sys.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sys.entity.SysRoleButton;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: SysRoleButtonBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Aug 23 08:28:20 GMT 2016
**/
@FeignClient("mftcc-platform-factor")
public interface SysRoleButtonFeign {
	@RequestMapping("/sysOrg/insert")
	public void insert(@RequestBody SysRoleButton sysRoleButton) throws ServiceException;
	@RequestMapping("/sysOrg/delete")
	public void delete(@RequestBody SysRoleButton sysRoleButton) throws ServiceException;
	@RequestMapping("/sysOrg/update")
	public void update(@RequestBody SysRoleButton sysRoleButton) throws ServiceException;
	@RequestMapping("/sysOrg/getById")
	public SysRoleButton getById(@RequestBody SysRoleButton sysRoleButton) throws ServiceException;
	@RequestMapping("/sysOrg/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping("/sysOrg/insertByJSONArray")
	public void insertByJSONArray(@RequestBody JSONArray jsonArray,@RequestParam("funNo") String funNo,@RequestParam("roleNo") String roleNo) throws ServiceException;
	@RequestMapping("/sysOrg/findList")
	public List<SysRoleButton> findList() throws ServiceException;
	
}
