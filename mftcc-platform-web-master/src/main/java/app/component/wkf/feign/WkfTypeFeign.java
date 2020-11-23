package  app.component.wkf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.wkf.entity.WkfType;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: WkfTypeBo.java
* Description:
* @author:@dhcc.com.cn
* @Fri Jul 01 06:17:10 GMT 2016
**/
@FeignClient("mftcc-platform-factor")
public interface WkfTypeFeign {
	
	@RequestMapping(value = "/wkfType/insert")
	public void insert(@RequestBody WkfType wkfType) throws ServiceException;
	
	@RequestMapping(value = "/wkfType/delete")
	public void delete(@RequestBody WkfType wkfType) throws ServiceException;
	
	@RequestMapping(value = "/wkfType/update")
	public void update(@RequestBody WkfType wkfType) throws ServiceException;
	
	@RequestMapping(value = "/wkfType/getById")
	public WkfType getById(@RequestBody WkfType wkfType) throws ServiceException;
	
	@RequestMapping(value = "/wkfType/getAll")
	public JSONArray getAll() throws ServiceException;
	
	@RequestMapping(value = "/wkfType/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("wkfType") WkfType wkfType) throws ServiceException;
	
}
