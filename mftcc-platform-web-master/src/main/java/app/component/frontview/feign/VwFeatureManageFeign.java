package app.component.frontview.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.frontview.entity.VwFeatureManage;
import app.util.toolkit.Ipage;

/**
 * Title: VwFeatureManageBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed May 10 19:16:00 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface VwFeatureManageFeign {
	/**
	 * 新增平台特点
	 * 
	 * @param vwFeatureManage
	 * @throws ServiceException
	 */
	@RequestMapping("/vwFeatureManage/insert")
	public void insert(@RequestBody VwFeatureManage vwFeatureManage) throws ServiceException;

	/**
	 * 删除平台特点
	 * 
	 * @param vwFeatureManage
	 * @throws ServiceException
	 */
	@RequestMapping("/vwFeatureManage/delete")
	public void delete(@RequestBody VwFeatureManage vwFeatureManage) throws ServiceException;

	/**
	 * 修改平台特点
	 * 
	 * @param vwFeatureManage
	 * @throws ServiceException
	 */
	@RequestMapping("/vwFeatureManage/update")
	public void update(@RequestBody VwFeatureManage vwFeatureManage) throws ServiceException;

	/**
	 * 详情
	 * 
	 * @param vwFeatureManage
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwFeatureManage/getById")
	public VwFeatureManage getById(@RequestBody VwFeatureManage vwFeatureManage) throws ServiceException;

	/**
	 * 获取列表
	 * 
	 * @param ipage
	 * @param vwFeatureManage
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/vwFeatureManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("vwFeatureManage") VwFeatureManage vwFeatureManage) throws ServiceException;

	/**
	 * 获取所有启用的平台特点
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwFeatureManage/getAllFeature")
	public List<VwFeatureManage> getAllFeature() throws ServiceException, Exception;

}
