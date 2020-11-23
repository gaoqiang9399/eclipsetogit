package  app.component.wkf.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.wkf.entity.WkfApprovalUser;
import app.util.toolkit.Ipage;

/**
* Title: WkfApprovalUserBo.java
* Description:
* @author:zhanglei@dhcc.com.cn
* @Fri Feb 22 10:03:00 CST 2013
**/
@FeignClient("mftcc-platform-factor")
public interface WkfApprovalUserFeign {

	@RequestMapping(value = "/wkfApprovalUser/getById")
	public WkfApprovalUser getById(@RequestBody WkfApprovalUser wkfApprovalUser) throws ServiceException;

	@RequestMapping(value = "/wkfApprovalUser/del")
	public void del(@RequestBody WkfApprovalUser wkfApprovalUser) throws ServiceException;

	@RequestMapping(value = "/wkfApprovalUser/insert")
	public void insert(@RequestBody WkfApprovalUser wkfApprovalUser) throws ServiceException;

	@RequestMapping(value = "/wkfApprovalUser/update")
	public void update(@RequestBody WkfApprovalUser wkfApprovalUser) throws ServiceException;

	@RequestMapping(value = "/wkfApprovalUser/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg) throws ServiceException;
	
	@RequestMapping(value = "/wkfApprovalUser/findByPageForWkf")
	public Ipage findByPageForWkf(@RequestBody Ipage ipg,@RequestParam("wkfApprovalUser") WkfApprovalUser wkfApprovalUser) throws ServiceException;
	
	@RequestMapping(value = "/wkfApprovalUser/findAllForWkf")
	public List<WkfApprovalUser>  findAllForWkf(@RequestBody WkfApprovalUser wkfApprovalUser) throws ServiceException;

	@RequestMapping(value = "/wkfApprovalUser/batchInsert")
	public void batchInsert(@RequestBody String wkfRoleNoStr,@RequestParam("roleNoStr") String roleNoStr,@RequestParam("brNoStr") String brNoStr) throws ServiceException;
	
	@RequestMapping(value = "/wkfApprovalUser/getByUser")
	public WkfApprovalUser getByUser(@RequestBody WkfApprovalUser wkfApprovalUser) throws ServiceException;
	/**
	 * 通过用户编号获取审批角色号数组集合
	 * @param wkfApprovalUser
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/wkfApprovalUser/getByUserForList")
	public List<String> getByUserForList(@RequestBody WkfApprovalUser wkfApprovalUser) throws ServiceException;
	
	@RequestMapping(value = "/wkfApprovalUser/getUserBrByRoleForList")
	public List<Map<String,String>> getUserBrByRoleForList(@RequestBody WkfApprovalUser wkfApprovalUser) throws ServiceException;
	
	@RequestMapping(value = "/wkfApprovalUser/getWkfApprovalUserList")
	public List<String> getWkfApprovalUserList(@RequestBody WkfApprovalUser wkfApprovalUser)throws ServiceException;

	@RequestMapping(value = "/wkfApprovalUser/findByPageMapPop")
	public Ipage findByPageMapPop(@RequestBody Ipage ipage)throws ServiceException;
	
	@RequestMapping(value = "/wkfApprovalUser/getAllList")
	public List<WkfApprovalUser> getAllList(@RequestBody WkfApprovalUser wkfApprovalUser)throws ServiceException;

	@RequestMapping(value = "/wkfApprovalUser/delByWkfRoleNo")
	public void delByWkfRoleNo(@RequestBody WkfApprovalUser wkfApprovalUser)throws ServiceException;

	@RequestMapping(value = "/wkfApprovalUser/findApprovalUserByPage")
	public Ipage findApprovalUserByPage(@RequestBody Ipage ipage)throws ServiceException;
	
	@RequestMapping(value = "/wkfApprovalUser/findApprovalUser")
	public List<WkfApprovalUser> findApprovalUser(@RequestBody WkfApprovalUser wkfApprovalUser)throws Exception;
	
	@RequestMapping(value = "/wkfApprovalUser/getByWkfRoleNoArr")
	public List<WkfApprovalUser> getByWkfRoleNoArr(@RequestBody WkfApprovalUser wkfApprovalUser)throws ServiceException;
	/**
	 * 方法描述：根据操作员编号查询对应审批角色
	 * @param opNo
	 * @throws Exception
	 */
	@RequestMapping(value = "/wkfApprovalUser/getCountByOpNo")
	public int getCountByOpNo(@RequestBody String opNo) throws Exception;
	/**
	 * 方法描述：根据操作员编号删除对应审批角色
	 * @param opNo
	 * @throws Exception
	 */
	@RequestMapping(value = "/wkfApprovalUser/deleteApprovalRoleByOpNo")
	public void deleteApprovalRoleByOpNo(@RequestBody String opNo) throws Exception;

	@RequestMapping(value = "/wkfApprovalUser/getUserForTaskPage")
	public Ipage getUserForTaskPage(@RequestBody Ipage ipage) throws Exception;

}