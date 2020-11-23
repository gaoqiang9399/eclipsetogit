package  app.component.coreservice.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.coreservice.entity.ApiSendtoHxRecord;
import app.util.toolkit.Ipage;

/**
* Title: ApiSendtoHxRecordBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Jan 15 11:53:13 CST 2018
**/

@FeignClient("mftcc-platform-dhcc")
public interface ApiSendtoHxRecordFeign {
	
	@RequestMapping(value = "/apiSendtoHxRecord/insert")
	public void insert(@RequestBody ApiSendtoHxRecord apiSendtoHxRecord) throws Exception;
	
	@RequestMapping(value = "/apiSendtoHxRecord/delete")
	public void delete(@RequestBody ApiSendtoHxRecord apiSendtoHxRecord) throws Exception;
	
	@RequestMapping(value = "/apiSendtoHxRecord/update")
	public void update(@RequestBody ApiSendtoHxRecord apiSendtoHxRecord) throws Exception;
	
	@RequestMapping(value = "/apiSendtoHxRecord/getById")
	public ApiSendtoHxRecord getById(@RequestBody ApiSendtoHxRecord apiSendtoHxRecord) throws Exception;
	
	@RequestMapping(value = "/apiSendtoHxRecord/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("apiSendtoHxRecord") ApiSendtoHxRecord apiSendtoHxRecord) throws Exception;
	
}
