package  app.component.sec.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sec.entity.SecUserMarkInfo;
import app.util.toolkit.Ipage;

/**
* Title: SecUserMarkInfoBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Feb 23 02:25:30 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface SecUserMarkInfoFeign {
	
	@RequestMapping(value = "/secUserMarkInfo/insert")
	public void insert(@RequestBody SecUserMarkInfo secUserMarkInfo) throws ServiceException;
	
	@RequestMapping(value = "/secUserMarkInfo/delete")
	public void delete(@RequestBody SecUserMarkInfo secUserMarkInfo) throws ServiceException;
	
	@RequestMapping(value = "/secUserMarkInfo/update")
	public void update(@RequestBody SecUserMarkInfo secUserMarkInfo) throws ServiceException;
	
	@RequestMapping(value = "/secUserMarkInfo/getById")
	public SecUserMarkInfo getById(@RequestBody SecUserMarkInfo secUserMarkInfo) throws ServiceException;
	
	@RequestMapping(value = "/secUserMarkInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("secUserMarkInfo") SecUserMarkInfo secUserMarkInfo) throws ServiceException;
	
	@RequestMapping(value = "/secUserMarkInfo/getAllLogin")
	public Map<String,Long> getAllLogin(@RequestBody Map<String,String> map) throws ServiceException;
	
	@RequestMapping(value = "/secUserMarkInfo/insertOrUpdate")
	public void insertOrUpdate(@RequestBody SecUserMarkInfo secUserMarkInfo) throws ServiceException;

	@RequestMapping(value = "/secUserMarkInfo/unlockAccount")
	public void unlockAccount(@RequestBody SecUserMarkInfo secUserMarkInfo) throws ServiceException;
	
}
