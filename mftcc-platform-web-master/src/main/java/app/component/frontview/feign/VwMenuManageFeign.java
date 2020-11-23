package app.component.frontview.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.frontview.entity.VwContManage;
import app.component.frontview.entity.VwMenuManage;
import app.util.toolkit.Ipage;

/**
 * Title: VwMenuManageBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Apr 28 11:18:58 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface VwMenuManageFeign {
	/**
	 * 新增菜单
	 * 
	 * @param vwMenuManage
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwMenuManage/insert")
	public void insert(@RequestBody VwMenuManage vwMenuManage) throws ServiceException, Exception;

	/**
	 * 删除菜单
	 * 
	 * @param vwMenuManage
	 * @throws ServiceException
	 */
	@RequestMapping("/vwMenuManage/delete")
	public void delete(@RequestBody VwMenuManage vwMenuManage) throws ServiceException, Exception;

	/**
	 * 更新菜单
	 * 
	 * @param vwMenuManage
	 * @throws ServiceException
	 */
	@RequestMapping("/vwMenuManage/update")
	public void update(@RequestBody VwMenuManage vwMenuManage) throws ServiceException, Exception;

	/**
	 * 查询详情
	 * 
	 * @param vwMenuManage
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwMenuManage/getById")
	public VwMenuManage getById(@RequestBody VwMenuManage vwMenuManage) throws ServiceException, Exception;

	/**
	 * 获取列表
	 * 
	 * @param ipage
	 * @param vwMenuManage
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwMenuManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("vwMenuManage") VwMenuManage vwMenuManage) throws ServiceException, Exception;

	/**
	 * 获取列表
	 * 
	 * @param ipage
	 * @param vwMenuManage
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwMenuManage/getVwMenuManageList")
	public List<VwMenuManage> getVwMenuManageList(@RequestBody VwMenuManage vwMenuManage) throws ServiceException, Exception;

	/**
	 * 获取最大序号
	 * 
	 * @param pId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwMenuManage/getMaxSort")
	public String getMaxSort(@RequestBody String pId) throws ServiceException, Exception;

	/**
	 * 获取所有的菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vwMenuManage/getAllMenu")
	public List<VwMenuManage> getAllMenu() throws ServiceException, Exception;

	/**
	 * 获取所有的菜单 带内容集合
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vwMenuManage/getAllMenuCont")
	public List<VwMenuManage> getAllMenuCont() throws ServiceException, Exception;

	/**
	 * 根据菜单id获取其对应的内容集合
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vwMenuManage/getContByBlock")
	public List<VwContManage> getContByBlock(@RequestBody Map<String, String> paramMap) throws ServiceException, Exception;

	/**
	 * 根据菜单id获取其对应的内容集合
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vwMenuManage/findContByBlockByPage")
	public Ipage findContByBlockByPage(@RequestBody Ipage ipage, @RequestParam("paramMap") Map<String, String> paramMap) throws ServiceException, Exception;

}
