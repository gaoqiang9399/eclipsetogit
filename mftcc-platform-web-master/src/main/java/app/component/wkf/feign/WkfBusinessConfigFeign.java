package  app.component.wkf.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.wkf.entity.WkfBusinessConfig;
import app.util.toolkit.Ipage;

/**
* Title: WkfBusinessConfigBo.java
* Description:
* @author:renyongxian@dhcc.com.cn
* @Thu Feb 28 12:59:54 GMT 2013
**/
@FeignClient("mftcc-platform-factor")
public interface WkfBusinessConfigFeign {

	@RequestMapping(value = "/wkfBusinessConfig/getById")
	public WkfBusinessConfig getById(@RequestBody WkfBusinessConfig wkfBusinessConfig) throws ServiceException;

	@RequestMapping(value = "/wkfBusinessConfig/del")
	public void del(@RequestBody WkfBusinessConfig wkfBusinessConfig) throws ServiceException;

	@RequestMapping(value = "/wkfBusinessConfig/insert")
	public void insert(@RequestBody WkfBusinessConfig wkfBusinessConfig) throws ServiceException;

	@RequestMapping(value = "/wkfBusinessConfig/update")
	public void update(@RequestBody WkfBusinessConfig wkfBusinessConfig) throws ServiceException;

	@RequestMapping(value = "/wkfBusinessConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg) throws ServiceException;

	@RequestMapping(value = "/wkfBusinessConfig/findByObjName")
	public List<WkfBusinessConfig> findByObjName(@RequestBody WkfBusinessConfig wkfBusinessConfig)throws ServiceException;
	/**
	 * 更新启用状态
	 */
	@RequestMapping(value = "/wkfBusinessConfig/updateSts")
	public void updateSts(@RequestBody WkfBusinessConfig wkfBusinessConfig)throws ServiceException;

}