package app.component.sys.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sys.entity.SysRole;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;
@FeignClient("mftcc-platform-factor")
public interface SysRoleFeign {

	
	/**
	 * 分页查询
	 * @param ipg
	 * @param role
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sysRole/findPage")
	public Ipage findPage(@RequestBody Ipage ipg) throws ServiceException;
	@RequestMapping("/sysRole/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg) throws ServiceException;
	/**
	 * 角色号查询
	 * @param role_no
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sysRole/getByRoleNo")
	public SysRole getByRoleNo(@RequestParam("role_no") String role_no) throws ServiceException;
	/**
	 * 新增和更新
	 * @param role
	 * @throws ServiceException
	 */
	@RequestMapping("/sysRole/saveOrUpdate")
	public void saveOrUpdate(@RequestBody SysRole role) throws ServiceException;
	@RequestMapping("/sysRole/update")
	public void update(@RequestBody SysRole role) throws ServiceException;
	@RequestMapping("/sysRole/insert")
	public void insert(@RequestBody SysRole role) throws ServiceException;
	/**
	 * 角色号删除
	 * @param role_no
	 * @throws ServiceException
	 */
	@RequestMapping("/sysRole/deleteByRoleNo")
	public void deleteByRoleNo(@RequestParam("role_no") String role_no) throws ServiceException;
	@RequestMapping("/sysRole/getAllList")
	public List<SysRole> getAllList(@RequestBody SysRole role) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得最大角色编号
	 * @return
	 * @throws ServiceException
	 * String
	 * @author 沈浩兵
	 * @date 2017-2-23 下午7:26:22
	 */
	@RequestMapping("/sysRole/getMaxRoleNo")
	public String getMaxRoleNo() throws ServiceException;
	/**
	 * 
	 * 方法描述： 保存角色权限配置
	 * 包括角色、入口权限、功能权限
	 * @param roleDataMap
	 * @param entrNoStr
	 * @param pmsBizArr
	 * @return
	 * @throws ServiceException
	 * String
	 * @author 沈浩兵
	 * @date 2017-7-19 下午6:12:57
	 */
	@RequestMapping("/sysRole/saveRolePmsConfig")
	public JSONObject saveRolePmsConfig(@RequestBody Map<String,Object> roleDataMap) throws ServiceException;
	/**
	 * 角色名称查询
	 * @param role_name
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sysRole/getByRoleName")
	public SysRole getByRoleName(@RequestParam("role_name") String role_name) throws ServiceException;

}
