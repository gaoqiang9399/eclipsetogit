package app.component.sysInterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.entity.SysLegalHolidayRecord;
import app.component.param.entity.BusCtlParmMang;
import app.component.sys.entity.MfBusModel;
import app.component.sys.entity.MfQueryAccountInfo;
import app.component.sys.entity.MfSysCompanyMst;
import app.component.sys.entity.MfTimeLineQueryConfig;
import app.component.sys.entity.MfUserPermission;
import app.component.sys.entity.SysAreaConf;
import app.component.sys.entity.SysLoginLog;
import app.component.sys.entity.SysOrg;
import app.component.sys.entity.SysRole;
import app.component.sys.entity.SysRoleUrl;
import app.component.sys.entity.SysUser;
import app.util.toolkit.Ipage;
@FeignClient("mftcc-platform-factor")
public interface SysInterfaceFeign {
	@RequestMapping(value = "/sysInterface/getSysAreaConfByProvNo")
	public SysAreaConf getSysAreaConfByProvNo(@RequestBody String provNo)throws Exception;
	
	/**	
	 * 微信绑定承租人账号
	 * @param wechatOpenId
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/bindSysUser")
	public int bindSysUser(@RequestBody String openId,@RequestParam("opNo") String opNo) throws Exception;
	
	/**
	 * 微信解绑承租人账号
	 * @param wechatOpenId
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/unbindSysUser")
	public int unbindSysUser(@RequestBody String opNo) throws Exception;
	
	/**
	 * 通过微信端的唯一标识查用户
	 * @param wechatOpenId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/getSysUserInfoByWechatOpenId")
	public SysUser getSysUserInfoByWechatOpenId(@RequestBody String wechatOpenId) throws Exception;
	
	@RequestMapping(value = "/sysInterface/updateSysUserMobile")
	public int updateSysUserMobile(@RequestBody String opNo,@RequestParam("mobile") String mobile) throws Exception;
	
	@RequestMapping(value = "/sysInterface/insertSysOrg")
	public void insertSysOrg(@RequestBody SysOrg sysOrg) throws Exception;
	
	@RequestMapping(value = "/sysInterface/getSysGlobalDate")
	public String getSysGlobalDate()throws Exception;
	
	@RequestMapping(value = "/sysInterface/getSysUserCount")
	public String getSysUserCount()throws Exception;
	/**
	 * 
	 * 方法描述： 获得最大角色编号
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-2-23 下午7:27:46
	 */
	@RequestMapping(value = "/sysInterface/getSysRole")
	public SysRole getSysRole(@RequestParam("roleNo") String roleNo) throws Exception;
	
	@RequestMapping(value = "/sysInterface/getSysRoleByRoleNo")
	public SysRole getSysRoleByRoleNo(@RequestBody String RoleNo) throws Exception;
	
	@RequestMapping(value = "/sysInterface/getSysOrgByOrgNo")
	public SysOrg getSysOrgByOrgNo(@RequestParam("orgNo") String orgNo) throws Exception;
	
	@RequestMapping(value = "/sysInterface/getByUpOneBizType")
	public List<SysOrg> getByUpOneBizType(@RequestBody String upOne,@RequestParam("bizType") String bizType) throws Exception;
	
	@RequestMapping(value = "/sysInterface/getChildren")
	public List<SysOrg> getChildren(@RequestBody SysOrg sysOrg) throws Exception;
	
	@RequestMapping(value = "/sysInterface/getAllChildren")
	public List<SysOrg> getAllChildren(@RequestBody SysOrg sysOrg) throws Exception;
	
	@RequestMapping(value = "/sysInterface/getSameOrg")
	public List<SysOrg> getSameOrg(@RequestBody SysOrg sysOrg) throws Exception;
	
	@RequestMapping(value = "/sysInterface/getSelfOrUpOne")
	public List<SysOrg> getSelfOrUpOne(@RequestBody SysOrg sysOrg) throws Exception;
	
	/**
	 * 查询本区域的机构
	 * @param sysOrg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/getSelfRegion")
	public List<SysOrg> getSelfRegion(@RequestBody SysOrg sysOrg) throws Exception;
	
	/**
	 * 获取业务控制参数
	 * @param busCtlParmMang
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/sysInterface/getBusCtlParmMangById")
	public BusCtlParmMang getBusCtlParmMangById(@RequestBody BusCtlParmMang busCtlParmMang) throws ServiceException;
	
	/**
	 * 通过用户编号获取用户名称。
	 * <strong>如用于当前登录操作员，请通过User的静态方法从session中获取。</strong>
	 * @param opNo
	 * @return String
	 * @throws Exception
	 * @author LiuYF
	 */
	@RequestMapping(value = "/sysInterface/getSysUserName", produces="text/html;charset=utf-8")
	public String getSysUserName(@RequestParam("opNo") String opNo) throws Exception;
	
	/**
	 * 通过部门编号字符串获取用户编号字符串
	 * @param orgNos 部门编号字符串,部门号之间用"|"进行分隔(例: "101101|101500")
	 * @return 用户编号字符串，编号之间用"|"进行分隔(例: "RX0057|RX0056")
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/sysInterface/getUsersByOrgNos")
	public String getUsersByOrgNos(@RequestBody String orgNos) throws Exception;
	
	/**
	 * 通过角色编号字符串获取用户编号字符串
	 * @param roleNos 角色编号字符串，编号之间用"|"进行分隔(例: "BL0023|BL0024")
	 * @return 用户编号字符串，编号之间用"|"进行分隔(例: "RX0057|RX0056")
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/sysInterface/getUsersByRoleNos")
	public String getUsersByRoleNos(@RequestBody String roleNos) throws Exception;
	
	/**
	 * 获取某员工所在分公司下的某部门类型、角色类型的用户
	 * @param userNo 员工编号
	 * @param bizType 部门类型
	 * @param roleNo 角色类型
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/getUserByUserBizRole")
	public List<SysUser> getUserByUserBizRole(@RequestParam("userNo") String userNo,@RequestParam("bizType") String bizType,@RequestParam("roleNo") String roleNo) throws Exception;
	/**
	 * 根据用户号获取用户id
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/getUserNoBySysUser")
	public SysUser getUserNoBySysUser(@RequestParam("userNo")  String userNo) throws Exception;
	/**
	 * 查询所有用户
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/getAllUser")
	public List<SysUser> getAllUser(@RequestBody SysUser sysUser) throws Exception;
	/**
	 * 查询所有机构
	 * @param sysOrg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/getAllOrg")
	public List<SysOrg> getAllOrg() throws Exception;
	
	/**
	 * @Description: 获取所有部门的json格式
	 * @return
	 * @throws ServiceException
	 * @author: 李伟
	 * @date: 2017-7-6 下午3:55:12
	 */
	@RequestMapping(value = "/sysInterface/getAllOrgJson")
	public String getAllOrgJson(@RequestBody SysOrg sysOrg) throws ServiceException;
	/**
	 * 查询所有角色
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/getAllSysRole")
	public List<SysRole> getAllSysRole(@RequestBody SysRole role) throws Exception;
	/**
	 * 查询消息模板消息内容
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/getSysMessage")
	public String getSysMessage(@RequestBody String msgNo) throws Exception;
	
	/**
	 * 根据用户编号和按钮编号获取按钮URL列表
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/getSysBtnMenu")
	public List<SysRoleUrl> getSysBtnMenu(@RequestBody String userNo,@RequestParam("btnNo") String btnNo) throws Exception;
	/**
	 * 
	 * 方法描述： 获得最大用户编号
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-4-25 下午4:00:16
	 */
	@RequestMapping(value = "/sysInterface/getMaxUserNo")
	public String getMaxUserNo() throws Exception;
	
	@RequestMapping(value = "/sysInterface/update")
	public void  update(@RequestBody MfSysCompanyMst  mfSysCompanyMst) throws Exception;
	
	@RequestMapping(value = "/sysInterface/getCompanyInfo")
	public MfSysCompanyMst getCompanyInfo() throws Exception;
	/**
	 * 
	 * 方法描述： 根据手机号获得用户
	 * @param sysUser
	 * @return
	 * @throws Exception
	 * SysUser
	 * @author 沈浩兵
	 * @date 2017-6-13 上午11:31:45
	 */
	@RequestMapping(value = "/sysInterface/getByMobile")
	public SysUser getByMobile(@RequestBody SysUser sysUser) throws Exception;
	
	@RequestMapping(value = "/sysInterface/getByMobileOrUser")
	public List<SysUser> getByMobileOrUser(@RequestBody SysUser sysUser) throws Exception;

	@RequestMapping(value = "/sysInterface/viewMode")
	String viewMode(@RequestBody SysUser sysUser) throws Exception;

	/**
	 * 方法描述： 获取操作员分页数据
	 * @param sysUser
	 * @return
	 * @throws Exception
	 * Page<SysUser>
	 * @author YuShuai
	 * @date 2017-8-22 下午12:00:39
	 */
	@RequestMapping(value = "/sysInterface/getAllUserByPage")
	public List<SysUser> getAllUserByPage(@RequestBody SysUser sysUser)throws Exception;
	/**
	 * @Description:根据数据权限查询所有部门和操作员 
	 * @param sysUser
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-8-24 下午3:50:06
	 */
	@RequestMapping(value = "/sysInterface/getBrNoOptNoByRoleType")
	public Map<String,String> getBrNoOptNoByRoleType(@RequestBody SysUser sysUser) throws Exception;
	/**
	 * @Description:根据数据权限获取部门 
	 * @param sysUser
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-8-26 下午4:59:06
	 */
	@RequestMapping(value = "/sysInterface/getBrListByRoleType")
	public Ipage getBrListByRoleType(@RequestBody Ipage ipg,@RequestParam("sysUser") SysUser sysUser,@RequestParam("formMap") Map<String, String> formMap) throws Exception; 
	
	/**
	 * @Description:根据部门获取用户 
	 * @param brNo
	 * @return
	 * @author: 李伟
	 * @date: 2017-8-26 下午4:58:46
	 */
	@RequestMapping(value = "/sysInterface/getUserListByBrNo")
	public Ipage getUserListByBrNo(@RequestBody Ipage ipg,@RequestParam("sysUser") SysUser sysUser,@RequestParam("formMap") Map<String, String> formMap) throws Exception; 
	
	@RequestMapping(value = "/sysInterface/getAllUserByBrNo")
	public  List<SysUser> getAllUserByBrNo(@RequestBody SysUser sysUser) throws Exception; 
	
	/**
	 * @Description:获取部门list 
	 * @param sysOrg
	 * @return
	 * @author: 李伟
	 * @date: 2017-9-7 上午9:04:32
	 */
	@RequestMapping(value = "/sysInterface/findOrgList")
	public List<SysOrg> findOrgList(@RequestBody SysOrg sysOrg);
	/**
	 * 
	 * 方法描述： 根据id获得用户信息
	 * @param sysUser
	 * @return
	 * @throws Exception
	 * SysUser
	 * @author 沈浩兵
	 * @date 2017-10-10 上午10:56:12
	 */
	@RequestMapping(value = "/sysInterface/getSysUserById")
	public SysUser getSysUserById(@RequestBody SysUser sysUser)throws Exception;
	/**
	 * 
	 * 方法描述： 根据业务模式获得业务模式基础数据
	 * @param busModelNo
	 * @return
	 * @throws Exception
	 * MfBusModel
	 * @author 沈浩兵
	 * @date 2017-10-25 上午11:47:00
	 */
	@RequestMapping(value = "/sysInterface/getByModelNo")
	public MfBusModel getByModelNo(@RequestBody String busModelNo) throws Exception;
	/**
	 * 分配操作员（按照名下客户数量）
	 * @param resultMap
	 * @param sysUserList
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/allotRegNo")
	public Map<String , Object>  allotRegNo () throws Exception;
	
	/**
	 * 
	 * 方法描述： 获得时间查询配置信息{'1小时内-h1','2小时内-h2'.....}
	 * @param mfTimeLineQueryConfig
	 * @return
	 * @throws Exception
	 * List<String>
	 * @author 沈浩兵
	 * @date 2017-11-24 下午6:27:14
	 */
	@RequestMapping(value = "/sysInterface/getTimeLineQueryList")
	public List<String> getTimeLineQueryList(@RequestBody MfTimeLineQueryConfig mfTimeLineQueryConfig) throws Exception;
	/**
	 * 档案信息页面获取操作员列表
	 * @param ipage
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/getSysUserPage")
	public Ipage getSysUserPage(@RequestBody Ipage ipage,@RequestParam("sysUser") SysUser sysUser) throws Exception;
	/**
	 * 
	 * 方法描述： pad端获取操作员列表
	 * @param ipage
	 * @param sysUser
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author YaoWenHao
	 * @date 2017-12-15 下午2:10:59
	 */
	@RequestMapping(value = "/sysInterface/getSysUserPageForPad")
	public Ipage getSysUserPageForPad(@RequestBody Ipage ipage,@RequestParam("sysUser") SysUser sysUser) throws Exception;
	/**
	 * 
	 * 方法描述： 获取客户数据源
	 * @param ipage
	 * @param mfUserPermission
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author YaoWenHao
	 * @date 2017-12-20 下午1:40:33
	 */
	@RequestMapping(value = "/sysInterface/getPerDataSource")
	public Ipage getPerDataSource(@RequestBody Ipage ipage,@RequestParam("mfUserPermission") MfUserPermission mfUserPermission,@RequestParam("ajaxData") String ajaxData)throws Exception;
	/**
	 * 
	 * 方法描述： 根据月份查询节假日信息
	 * @param sysLegalHolidayRecord
	 * @return
	 * @throws Exception
	 * List<SysLegalHolidayRecord>
	 * @author YaoWenHao
	 * @date 2018-1-18 上午11:29:18
	 */
	@RequestMapping(value = "/sysInterface/getSysLegalHolidayRecordByMonth")
	public List<SysLegalHolidayRecord> getSysLegalHolidayRecordByMonth(@RequestBody SysLegalHolidayRecord sysLegalHolidayRecord) throws Exception;
	
	/**
	 * 
	 * 方法描述： 获得累计查询次数最少的查询员信息
	 * @param mfQueryAccountInfo
	 * @return
	 * @throws Exception
	 * MfQueryAccountInfo
	 * @author 沈浩兵
	 * @date 2018-1-23 下午1:47:56
	 */
	@RequestMapping(value = "/sysInterface/getQueryNoByMinQueryNum")
	public MfQueryAccountInfo getQueryNoByMinQueryNum(@RequestBody MfQueryAccountInfo mfQueryAccountInfo) throws Exception;
	/**
	 * 
	 * 方法描述：  更新的查询员信息
	 * @param mfQueryAccountInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2018-1-23 下午2:11:55
	 */
	@RequestMapping(value = "/sysInterface/updateQueryAccountInfo")
	public void updateQueryAccountInfo(@RequestBody MfQueryAccountInfo mfQueryAccountInfo) throws Exception;

	/**
	 * 方法描述： 记录登录系统信息
	 * 
	 * @param sysLoginLog
	 * @throws Exception void
	 * @author 沈浩兵
	 * @date 2018-3-14 下午5:08:33
	 */
	@RequestMapping(value = "/sysInterface/insertSysLoginLog")
	public void insertSysLoginLog(@RequestBody SysLoginLog sysLoginLog) throws Exception;

	/**
	 * 方法描述： 根据部门编号查询所有上级部门或者分公司，组合成字符串
	 * 
	 * @param brNo
	 * @return
	 * @throws Exception String
	 * @author 仇招
	 * @date 2018-3-17 下午4:24:31
	 */
	@RequestMapping(value = "/sysInterface/getUpOrg")
	public String getUpOrg(@RequestBody String brNo) throws Exception;
	/**
	 * 方法描述： 根据操作员编号获取表单是否可编辑
	 * @param opNo
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018年9月11日 下午7:26:59
	 */
	@RequestMapping(value = "/sysInterface/getQueryResult",produces="text/html;charset=utf-8")
	public String getQueryResult(@RequestParam("opNo") String opNo) throws Exception;
	/**
	 * 方法描述： 根据部门编号获取二级部门
	 * @param sysOrg
	 * @return
	 * @author 仇招
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysInterface/getTwoLevelBrNoByBrNo",produces="text/html;charset=utf-8")
	public String getTwoLevelBrNoByBrNo(@RequestBody SysOrg sysOrg) throws Exception;
}
