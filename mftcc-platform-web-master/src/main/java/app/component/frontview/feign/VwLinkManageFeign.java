package app.component.frontview.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.frontview.entity.VwLinkManage;
import app.util.toolkit.Ipage;

/**
 * Title: VwLinkManageBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue May 02 14:15:45 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface VwLinkManageFeign {
	/**
	 * 新增链接
	 * 
	 * @param vwLinkManage
	 * @param upload
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwLinkManage/insert")
	public void insert(@RequestBody VwLinkManage vwLinkManage) throws ServiceException, Exception;

	/**
	 * 删除链接
	 * 
	 * @param vwLinkManage
	 * @throws ServiceException
	 */
	@RequestMapping("/vwLinkManage/delete")
	public void delete(@RequestBody VwLinkManage vwLinkManage) throws ServiceException, Exception;

	/**
	 * 修改链接
	 * 
	 * @param vwLinkManage
	 * @param upload
	 * @throws ServiceException
	 */
	@RequestMapping("/vwLinkManage/update")
	public void update(@RequestBody VwLinkManage vwLinkManage) throws ServiceException, Exception;

	/**
	 * 查询链接
	 * 
	 * @param vwLinkManage
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwLinkManage/getById")
	public VwLinkManage getById(@RequestBody VwLinkManage vwLinkManage) throws ServiceException, Exception;

	/**
	 * 获取列表
	 * 
	 * @param ipage
	 * @param vwLinkManage
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwLinkManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException, Exception;

	/**
	 * 获取图片
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwLinkManage/getImgBase64")
	public String getImgBase64(@RequestBody String id) throws ServiceException, Exception;

	/**
	 * 接口调用 获取所有的合作伙伴和友情链接
	 * 
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwLinkManage/getLinkGroup")
	public List<VwLinkManage> getLinkGroup(@RequestBody Map<String, String> paramMap) throws ServiceException, Exception;

}
