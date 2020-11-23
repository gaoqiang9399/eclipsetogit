package app.component.sys.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sys.entity.SysOrg;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
@FeignClient("mftcc-platform-factor")
public interface SysOrgFeign {

	@RequestMapping("/sysOrg/getByBrNo/{brNo}")
	public SysOrg getByBrNo(@PathVariable("brNo") String brNo) throws Exception;
	@RequestMapping("/sysOrg/getById")
	public SysOrg getById(@RequestBody SysOrg sysOrg) throws ServiceException;
	@RequestMapping("/sysOrg/del")
	public void del(@RequestBody SysOrg sysOrg) throws ServiceException;
	@RequestMapping("/sysOrg/insert")
	public SysOrg insert(@RequestBody SysOrg sysOrg) throws ServiceException;
	@RequestMapping("/sysOrg/update")
	public void update(@RequestBody SysOrg sysOrg) throws ServiceException;
	@RequestMapping("/sysOrg/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg) throws ServiceException;
	@RequestMapping("/sysOrg/getTreeTop")
	public SysOrg getTreeTop()throws ServiceException;
	@RequestMapping("/sysOrg/getByBrNo")
	public Ipage findByPageForNotice(@RequestBody Ipage ipg) throws ServiceException;
	@RequestMapping("/sysOrg/getChildren")
	public List<SysOrg> getChildren(@RequestBody SysOrg sysOrg) throws ServiceException;
	@RequestMapping("/sysOrg/getAllOrg")
	public List<SysOrg> getAllOrg(@RequestBody SysOrg sysOrg) throws ServiceException;
	@RequestMapping("/sysOrg/getAllByOrgName")
	public Ipage getAllByOrgName(@RequestBody Ipage ipg) throws ServiceException;
	@RequestMapping("/sysOrg/getAllOrgJson")
	public String getAllOrgJson(@RequestBody SysOrg sysOrg) throws ServiceException;
	@RequestMapping("/sysOrg/getAllOrgForSelect")
	public JSONArray getAllOrgForSelect(@RequestBody SysOrg sysOrg) throws ServiceException;
	@RequestMapping("/sysOrg/prodAutoMenu")
	public Ipage prodAutoMenu(@RequestBody Ipage ipg) throws ServiceException;
	@RequestMapping("/sysOrg/getByUpOneBizType")
	public List<SysOrg> getByUpOneBizType(@RequestParam("upOne") String upOne,@RequestParam("bizType")String bizType) throws ServiceException;
	@RequestMapping("/sysOrg/getByUpOne")
	public List<SysOrg> getByUpOne(@RequestParam("upOne") String upOne) throws ServiceException;
	@RequestMapping("/sysOrg/getAllChildren")
	public String getAllChildren(@RequestParam("brNo")String brNo) throws ServiceException;
	@RequestMapping("/sysOrg/getSameOrg")
	public List<SysOrg> getSameOrg(@RequestBody SysOrg sysOrg) throws ServiceException;
	@RequestMapping("/sysOrg/getSelfOrUpOne")
	public List<SysOrg> getSelfOrUpOne(@RequestBody SysOrg sysOrg) throws ServiceException;
	
	/**
	 * 查询本区域的机构
	 * @param sysOrg
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sysOrg/getSelfRegion")
	public List<SysOrg> getSelfRegion(@RequestBody SysOrg sysOrg) throws ServiceException;
	
	/**
	 * 获取合同前缀
	 * @param sysOrg
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sysOrg/getUserPreByBrNo")
	public SysOrg getUserPreByBrNo(@RequestBody SysOrg sysOrg)  throws ServiceException;
	/**
	 * 获取业务的部门
	 * @param ipg
	 * @param sysOrg
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sysOrg/appOrgAutoMenu")
	public Ipage appOrgAutoMenu(@RequestBody Ipage ipg) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得初始化机构新增表单实体
	 * @return
	 * @throws ServiceException
	 * SysOrg
	 * @author 沈浩兵
	 * @date 2017-8-10 上午10:18:38
	 */
	@RequestMapping("/sysOrg/initSysOrg")
	public SysOrg initSysOrg()throws Exception;
	/**
	 * 
	 * 方法描述：  根据选择的上级部门异步带出机构展示编号
	 * @param upOneNo
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-8-10 上午11:05:28
	 */
	@RequestMapping("/sysOrg/getBrAliasNoByUpNo")
	public String getBrAliasNoByUpNo(@RequestParam("upOneNo") String upOneNo)throws Exception;
	/**
	 * @Description:获取部门分页list 
	 * @param sysOrg
	 * @return
	 * @throws ServiceException
	 * @author: 李伟
	 * @date: 2017-8-27 上午10:29:31
	 */
	@RequestMapping("/sysOrg/findOrgList")
	public List<SysOrg> findOrgList(@RequestBody SysOrg sysOrg) throws ServiceException;
	/**
	 * @Description:获取部门信息列表(包含上级部门名称) 
	 * @param sysOrg
	 * @return
	 * @throws DAOException
	 * @author: 李伟
	 * @date: 2017-9-15 上午9:50:01
	 */
	@RequestMapping("/sysOrg/findByPageIncludeUpOne")
	public Ipage findByPageIncludeUpOne(@RequestBody Ipage ipg ) throws ServiceException;
	
	/**
	 * 获取所有一级和二级机构
	 */
	@RequestMapping("/sysOrg/getAllByOrgNameAndLevel")
	public List<SysOrg> getAllByOrgNameAndLevel(@RequestBody SysOrg sysOrg ) throws ServiceException;
	
	@RequestMapping("/sysOrg/getAOrgListByBrNos")
	public List<SysOrg> getAOrgListByBrNos(@RequestParam("brNo") String brNo)throws Exception;
	
	/**
	 * 查询所有子公司
	 * @param sysOrg
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sysOrg/getAllChildCompany") 
	public List<SysOrg> getAllChildCompany(@RequestBody SysOrg sysOrg) throws ServiceException;
	/**
	 * 
	 * <p>Title: getSigningSubject</p>  
	 * <p>Description:查询出组织架构的第二级作为签约主体 </p>  
	 * @param ipg
	 * @return
	 * @throws ServiceException  
	 * @author zkq  
	 * @date 2018年8月7日 上午9:54:11
	 */
	@RequestMapping("/sysOrg/getSigningSubject")
	public Ipage getSigningSubject(@RequestBody Ipage ipg) throws ServiceException;
	/**
	 * 根据部门更新其下的所有操作员信息的部门名称。
	 * @param sysUser
	 * @author LiuYF
	 */
	@RequestMapping("/sysOrg/updateOrgAndUserBrName")
	public void updateOrgAndUserBrName(SysOrg sysOrg);



	/**
	 *
	 * 方法描述： 角色管理列表
	 * @return
	 * @throws Exception
	 * String
	 * @author cd
	 * @date 2019-4-13 下午14:02:04
	 */
	@RequestMapping("/sysOrg/findByPageList")
    public Ipage findByPageList(@RequestBody Ipage ipage) throws Exception;
}
