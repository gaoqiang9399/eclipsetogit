package app.component.sys.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sys.entity.SysMenu;

@FeignClient("mftcc-platform-factor")
public interface SysMenuFeign {
	@RequestMapping("/sysMenu/getAllMenuByRole_no")
	public List<SysMenu> getAllMenuByRole_no(@RequestParam("roleNo") String[] roleNo) throws Exception;
	@RequestMapping("/sysMenu/findMenuLev1ByRole")
	public List<SysMenu> findMenuLev1ByRole(@RequestParam("roleNo") String[] roleNo) throws Exception ;
	@RequestMapping("/sysMenu/findAll")
	public List<SysMenu> findAll(@RequestParam("stats") String stats) throws ServiceException;
	@RequestMapping("/sysMenu/getAllMenuByRole")
	public List<SysMenu> getAllMenuByRole(@RequestParam("role_no") String role_no) throws ServiceException;
	@RequestMapping("/sysMenu/getAllMenuByRoles")
	public List<SysMenu> getAllMenuByRoles(@RequestParam("role_no") String role_no) throws ServiceException;
	@RequestMapping("/sysMenu/getAllJsonMenu2")
	public String getAllJsonMenu2() throws ServiceException;
	@RequestMapping("/sysMenu/saveOrUpdate")
	public void saveOrUpdate(@RequestBody SysMenu sm) throws ServiceException;
	@RequestMapping("/sysMenu/insert")
	public void insert(@RequestBody SysMenu sm) throws ServiceException;
	@RequestMapping("/sysMenu/update")
	public void update(@RequestBody SysMenu sm) throws ServiceException;
	@RequestMapping("/sysMenu/delete")
	public void delete(@RequestParam("menuNo") String menuNo) throws ServiceException;
	@RequestMapping("/sysMenu/deleteByParent")
	public void deleteByParent(@RequestParam("menuNo") String menuNo) throws ServiceException;
	@RequestMapping("/sysMenu/getParentMenuNameByMenuNo")
	public String[] getParentMenuNameByMenuNo(@RequestParam("menuNo") String menuNo)throws ServiceException;
	@RequestMapping("/sysMenu/getAllMenuByParent")
	public List<SysMenu> getAllMenuByParent(@RequestParam("menuNo") String menuNo) throws ServiceException;
	@RequestMapping("/sysMenu/getById")
	public SysMenu getById(@RequestParam("menuNo") String menuNo) throws ServiceException;

}
