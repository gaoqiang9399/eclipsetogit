package  app.component.model.feign;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.model.entity.PageContent;

/**
* Title: MfSysModelBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Sep 05 18:48:35 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfToPageOfficeFeign {
	
	/**
	 * 
	 * 方法描述： 调用新版标签取值公共方法
	 * @param map
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2016-9-8 上午9:45:01
	 */
	@RequestMapping(value = "/mfToPageOffice/getDataMapComm")
	public Map<String,Object> getDataMapComm(@RequestBody String basePath,@RequestParam("bookParmMap")  Map<String, String> bookParmMap) throws ServiceException;
	
	/**
	 * 
	 * 方法描述： 调用新版新增打开文档方法
	 * @return
	 * @throws ServiceException
	 * String
	 * @author lwq
	 * @date 2017-8-21 下午5:57:37
	 */
	@RequestMapping(value = "/mfToPageOffice/getPageContent",produces="text/html;charset=UTF-8")
	public String getPageContent(@RequestParam("basePath") String basePath,@RequestBody  Map<String, String> bookParmMap)throws ServiceException;

	@RequestMapping(value = "/mfToPageOffice/showHtmlTemplatePage",produces="text/html;charset=UTF-8")
	public String showHtmlTemplatePage(@RequestBody Map<String ,String> parmMap) throws Exception ;
	
}
