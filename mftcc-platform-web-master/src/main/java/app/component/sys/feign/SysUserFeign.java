package app.component.sys.feign;


import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sys.entity.SysUser;
import app.util.toolkit.Ipage;
@FeignClient("mftcc-platform-factor")
public interface SysUserFeign {

	// 服务端借口部分
	@RequestMapping("/sysUser/getById")
	public SysUser getById(@RequestBody SysUser sysUser) throws Exception;

	@RequestMapping(value = "/sysUser/changePassWord", method = RequestMethod.POST)
	public String changePassWord(String pwInfo, @RequestParam("regNo") String regNo) throws Exception;
	
	@RequestMapping(value = "/sysUser/getByMobile", method = RequestMethod.POST)
	public SysUser getByMobile(@RequestBody SysUser sysUser) throws Exception;

	@RequestMapping(value = "/sysUser/insert", method = RequestMethod.POST)
	public void insert(@RequestBody SysUser sysUser) throws ServiceException;
	
	@RequestMapping(value = "/sysUser/delete", method = RequestMethod.POST)
	public void delete(@RequestBody SysUser sysUser) throws Exception;

	@RequestMapping(value = "/sysUser/update", method = RequestMethod.POST)
	public void update(@RequestBody SysUser sysUser) throws ServiceException;

	@RequestMapping(value = "/sysUser/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping(value = "/sysUser/getAllUserList", method = RequestMethod.POST)
	public List<SysUser> getAllUserList(@RequestBody SysUser sysUser) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根据部门编号获得部门下的员工
	 * @param sysUser
	 * @return
	 * @throws ServiceException
	 * List<SysUser>
	 * @author 沈浩兵
	 * @date 2017-2-21 下午4:48:38
	 */
	@RequestMapping(value = "/sysUser/getAllUserByBrNoByPage", method = RequestMethod.POST)
	public Ipage getAllUserByBrNoByPage(@RequestBody Ipage ipg) throws ServiceException;
	@RequestMapping(value = "/sysUser/getAllUserByBrNo", method = RequestMethod.POST)

	public List<SysUser> getAllUserByBrNo(@RequestBody SysUser sysUser) throws Exception;
	@RequestMapping(value = "/sysUser/updateOpSts", method = RequestMethod.POST)

	public void updateOpSts(@RequestBody SysUser sysUser) throws ServiceException;
	@RequestMapping(value = "/sysUser/getSysUserCount", method = RequestMethod.POST)
	public String getSysUserCount() throws ServiceException;
	/**
	 * 获取最大的用户号序号
	 */
	@RequestMapping(value = "/sysUser/getMaxOpNo", method = RequestMethod.POST)
	public String getMaxOpNo(@RequestParam("userPre") String userPre) throws ServiceException;
	/**
	 * 更新用户微信端的唯一标识
	 * @param SysUser
	 * @return 更新数据的行数
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/sysUser/getMaxOpNo", method = RequestMethod.POST)
	public int updateSysUserWechatOpenId(@RequestBody SysUser sysUser) throws ServiceException;
	
	/**
	 * 根据微信端唯一标识查用户信息
	 * @param wechatOpenId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/sysUser/getByWechatOpenId", method = RequestMethod.POST)
	public SysUser getByWechatOpenId(@RequestParam("wechatOpenId")String wechatOpenId) throws ServiceException;
	/**
	 * 更新手机号
	 * @param sysUser
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/sysUser/updateForMobile", method = RequestMethod.POST)
	public int updateForMobile(@RequestBody SysUser sysUser) throws ServiceException;
	
	/**
	 * 通过部门编号字符串获取用户编号字符串
	 * @param orgNos 部门编号字符串,部门号之间用"|"进行分隔(例: "101101|101500")
	 * @return 用户编号字符串，编号之间用"|"进行分隔(例: "RX0057|RX0056")
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/sysUser/getUsersByOrgNos", method = RequestMethod.POST)
	public String getUsersByOrgNos(@RequestParam("orgNos")String orgNos) throws ServiceException;
	
	/**
	 * 通过角色编号字符串获取用户编号字符串
	 * @param roleNos 角色编号字符串，编号之间用"|"进行分隔(例: "BL0023|BL0024")
	 * @return 用户编号字符串，编号之间用"|"进行分隔(例: "RX0057|RX0056")
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/sysUser/getUsersByRoleNos", method = RequestMethod.POST)

	public String getUsersByRoleNos(@RequestParam("roleNos")String roleNos) throws ServiceException;
	
	/**
	 * 业务申请协助经理
	 * @param ipage
	 * @param sysUser
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/sysUser/findByPageForOcManage", method = RequestMethod.POST)
	public Ipage findByPageForOcManage(@RequestBody Ipage ipage) throws ServiceException;
	
	/**
	 * 获取某员工所在分公司下的某部门类型、角色类型的用户
	 * @param userNo 员工编号
	 * @param bizType 部门类型
	 * @param roleNo 角色类型
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysUser/getUserByUserBizRole", method = RequestMethod.POST)

	public List<SysUser> getUserByUserBizRole(@RequestParam("userNo")String userNo, @RequestParam("bizType")String bizType, @RequestParam("roleNo")String roleNo) throws Exception;
	
	/**
	 * 获取业务的所有客户经理
	 * @param ipage
	 * @param sysUser
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/sysUser/appUserAutoMenu", method = RequestMethod.POST)
	public Ipage appUserAutoMenu(@RequestBody Ipage ipage) throws ServiceException;
	
	/**
	 * 
	 * 方法描述： 获得最大用户编号
	 * @return
	 * @throws ServiceException
	 * String
	 * @author 沈浩兵
	 * @date 2017-4-25 下午4:02:18
	 */
	@RequestMapping(value = "/sysUser/getMaxUserNo", method = RequestMethod.POST)
	public String getMaxUserNo() throws ServiceException;
	/**
	 * 根据手机号，登陆号，登录名查询用户
	 * @param sysUser
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/sysUser/getByMobileOrUser", method = RequestMethod.POST)
	public List<SysUser> getByMobileOrUser(@RequestBody SysUser sysUser) throws ServiceException;

	/**
	 * 
	 * 方法描述： 获取本部门及其以下的同角色的操作人员
	 * @param ipage
	 * @param sysUser
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author zhs
	 * @date 2017-8-3 下午6:04:18
	 */
	@RequestMapping(value = "/sysUser/findSameDownBrAndRoleByPage", method = RequestMethod.POST)
	public Ipage findSameDownBrAndRoleByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 初始化新增员工所需要的数据
	 * @param brNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-8-10 上午11:53:10
	 */
	@RequestMapping(value = "/sysUser/initSysUser", method = RequestMethod.POST)
	public Map<String,Object> initSysUser(@RequestParam("brNo") String brNo,@RequestParam("opNoType") String opNoType) throws Exception;
	/**
	 * 
	 * 方法描述： 验证员工登录账号和员工展示号唯一性
	 * @param sysUser
	 * @param type add 新增验证 edit 编辑验证
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-8-10 下午3:07:05
	 */
	@RequestMapping(value = "/sysUser/checkOnlyNo", method = RequestMethod.POST)
	public Map<String,Object> checkOnlyNo(@RequestBody SysUser sysUser,@RequestParam("type")String type) throws Exception;
	/**
	 * 方法描述： 获取操作员分页数据
	 * @param sysUser
	 * @return
	 * @throws Exception
	 * Page<SysUser>
	 * @author YuShuai
	 * @date 2017-8-22 下午12:00:39
	 */
	@RequestMapping(value = "/sysUser/getAllUserByPage", method = RequestMethod.POST)
	public Ipage getAllUserByPage(@RequestBody SysUser sysUser)throws Exception;
	/*
	 * 重置密码
	 */
	@RequestMapping(value = "/sysUser/updatePwdDefault", method = RequestMethod.POST)
	public void updatePwdDefault(@RequestParam("opNo") String opNo) throws Exception;
	
	/**
	 * @Description:获取操作员组合数据 
	 * @param sysUser
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-9-14 下午3:45:04
	 */
	@RequestMapping(value = "/sysUser/findByPageUnionSelf", method = RequestMethod.POST)
	public Ipage findByPageUnionSelf(@RequestBody Ipage ipg) throws ServiceException;
	@RequestMapping(value = "/sysUser/getCountByOpNo", method = RequestMethod.POST)
	public int getCountByOpNo(@RequestBody String opNo) throws Exception;
	@RequestMapping(value = "/sysUser/getSysUserPage", method = RequestMethod.POST)

	public Ipage getSysUserPage(@RequestBody Ipage ipage) throws Exception;
	
	/**
	 * 
	 * 方法描述： 验证系统当前登录操作员对当前数据的可操作权限
	 * @param currOpNo
	 * @param dataOpNo
	 * @throws ServiceException
	 * Map<String, Object>
	 * @author 沈浩兵
	 * @date 2017-12-14 下午2:40:21
	 */
	@RequestMapping(value = "/sysUser/checkOperable", method = RequestMethod.POST)

	public Map<String, Object> checkOperable(@RequestParam("currOpNo")String currOpNo,@RequestParam("dataOpNo")String dataOpNo, @RequestParam("regNo") String regNo) throws Exception;

	/**
	 * 
	 * 方法描述： 筛选增加潜在客户能看到的操作员
	 * @param opNo
	 * @param 
	 * @throws ServiceException
	 * Map<String, Object>
	 * @author 刘东迎
	 * @date 2018年1月11日10:34:25
	 */
	@RequestMapping(value = "/sysUser/checkOperable", method = RequestMethod.POST)
	public String checkOperable(@RequestBody String opNo) throws Exception;

	@RequestMapping(value = "/sysUser/updateStsNew", method = RequestMethod.POST)
	public void updateStsNew(@RequestBody SysUser sysUser) throws ServiceException;

	@RequestMapping("/sysUser/getAllUserByBrNos")
	public List<SysUser> getAllUserByBrNos(@RequestParam("brNo") String brNo)throws Exception;
	

	@RequestMapping(value = "/sysUser/getCusMug", method = RequestMethod.POST)
	public Ipage getCusMug(@RequestBody Ipage ipage) throws Exception;


	@RequestMapping("/sysUser/getCusInformation")
	public Map<String,Object> getCusInformation(@RequestParam("opNo") String opNo)throws Exception;





	/**
	 * 方法描述： 强制修改密码处理
	 *
	 * @param
	 * @return
	 * @throws Exception String
	 * @author cd
	 * @date 2019-3-25 下午8:58:18
	 */
	@RequestMapping(value = "/sysUser/forceUpdate",method = RequestMethod.POST)
	public void forceUpdate(@RequestBody SysUser sysUser) throws ServiceException;
}
