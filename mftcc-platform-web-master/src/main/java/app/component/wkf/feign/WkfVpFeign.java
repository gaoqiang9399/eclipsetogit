package  app.component.wkf.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.wkf.entity.WkfVp;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: WkfVpBo.java
* Description:
* @author:@dhcc.com.cn
* @Wed Jan 27 03:07:15 GMT 2016
**/
@FeignClient("mftcc-platform-factor")
public interface WkfVpFeign {
	
	@RequestMapping(value = "/wkfVp/insert")
	public void insert(@RequestBody WkfVp wkfVp) throws ServiceException;
	
	@RequestMapping(value = "/wkfVp/delete")
	public void delete(@RequestBody WkfVp wkfVp) throws ServiceException;
	
	@RequestMapping(value = "/wkfVp/update")
	public void update(@RequestBody WkfVp wkfVp) throws ServiceException;
	
	@RequestMapping(value = "/wkfVp/getById")
	public WkfVp getById(@RequestBody WkfVp wkfVp) throws ServiceException;
	
	@RequestMapping(value = "/wkfVp/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("wkfVp") WkfVp wkfVp) throws ServiceException;
	
	@RequestMapping(value = "/wkfVp/findByRole")
	public List<WkfVp> findByRole(@RequestBody WkfVp wkfVp) throws ServiceException;
	
	@RequestMapping(value = "/wkfVp/getListByMode")
	public List<WkfVp> getListByMode(@RequestBody String wkfModeNo,@RequestParam("wkfVpNo") String wkfVpNo) throws ServiceException;
	
	@RequestMapping(value = "/wkfVp/getByVpNo")
	public JSONArray getByVpNo(@RequestBody String wkfVpNo) throws ServiceException;
	
}
