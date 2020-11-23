package app.component.model.feign;

import app.base.ServiceException;
import app.component.model.entity.MfTemplateTagBase;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
* Title: MfSysModelBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Sep 05 18:48:35 CST 2016
**/

@FeignClient("mftcc-platform-templete-tag")
public interface MfNewToPageOfficeFeign {



	/**e
	 *
	 * 方法描述： 调用新版标签取值公共方法
	 * @param map
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 
	 * @date
	 */
	@RequestMapping(value = "/mfToPageOffice/getDataMapComm")
	public Map<String,Object> getDataMapComm(@RequestParam("bookParmMap") Map<String, String> bookParmMap) throws ServiceException;

	/**
	 *
	 * 方法描述： 获取该模板的所配置的标签
	 * @param map
	 * @return
	 * @throws ServiceException
	 * List<MfTemplateTagBase>
	 * @author	lcj
	 * @date	2019-03-04 16:10:30bookParmMap
	 */
	@RequestMapping(value = "/pageOfficeFactor/getTemplateTagBaseValue")
	public List<MfTemplateTagBase> getTemplateTagBaseValue(@RequestBody  Map<String, String> bookParmMap) throws ServiceException;
	
}
