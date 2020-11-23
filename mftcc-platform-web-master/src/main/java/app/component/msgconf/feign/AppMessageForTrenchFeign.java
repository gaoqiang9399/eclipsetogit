package  app.component.msgconf.feign;

import app.util.toolkit.Ipage;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.msgconf.entity.AppMessageForTrench;

/**
* Title: AppMessageForTrenchBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Aug 01 10:56:11 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface AppMessageForTrenchFeign {
	
	@RequestMapping(value = "/appMessageForTrench/insert")
	public void insert(@RequestBody AppMessageForTrench appMessageForTrench) throws Exception;
	
	@RequestMapping(value = "/appMessageForTrench/delete")
	public void delete(@RequestBody AppMessageForTrench appMessageForTrench) throws Exception;
	
	@RequestMapping(value = "/appMessageForTrench/update")
	public void update(@RequestBody AppMessageForTrench appMessageForTrench) throws Exception;
	
	@RequestMapping(value = "/appMessageForTrench/getById")
	public AppMessageForTrench getById(@RequestBody AppMessageForTrench appMessageForTrench) throws Exception;
	
	@RequestMapping(value = "/appMessageForTrench/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
