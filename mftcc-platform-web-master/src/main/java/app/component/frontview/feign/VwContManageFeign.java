package app.component.frontview.feign;

import app.base.ServiceException;
import app.component.frontview.entity.VwContManage;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Title: VwContManageBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed May 03 21:51:39 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface VwContManageFeign {
	/**
	 * 新增内容
	 * 
	 * @param vwContManage
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwContManage/insert")
	public void insert(@RequestBody VwContManage vwContManage) throws ServiceException, Exception;

	/**
	 * 删除内容
	 * 
	 * @param vwContManage
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwContManage/delete")
	public void delete(@RequestBody VwContManage vwContManage) throws ServiceException, Exception;

	/**
	 * 更新内容
	 * 
	 * @param vwContManage
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwContManage/update")
	public void update(@RequestBody VwContManage vwContManage) throws ServiceException, Exception;

	/**
	 * 获取内容详情
	 * 
	 * @param vwContManage
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwContManage/getById")
	public VwContManage getById(@RequestBody VwContManage vwContManage) throws ServiceException, Exception;

	/**
	 * 获取列表数据
	 * 
	 * @param ipage
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwContManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException, Exception;

	/**
	 * 获取图片base64字符串
	 * 
	 * @param id
	 * @param type
	 *            in：内容图片，out:外部链接图片
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwContManage/getBase64")
	public String getBase64(@RequestParam("id") String id, @RequestParam("type") String type) throws ServiceException, Exception;

	/**
	 * 获取内容的html
	 * 
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwContManage/getContentById")
	public VwContManage getContentById(@RequestBody Map<String, String> paramMap) throws ServiceException, Exception;

	/**
	 * 获取此菜单内容对应的菜单类型
	 * 
	 * @param block
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwContManage/getMenuType")
	public String getMenuType(@RequestParam("block") String block) throws ServiceException, Exception;

	/**
	 * 检查此菜单下内容的条数
	 * 
	 * @param block
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vwContManage/getCountByBlock")
	public Integer getCountByBlock(@RequestParam("block") String block) throws Exception;

}
