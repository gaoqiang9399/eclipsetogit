package  app.component.eval.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.AppProjectEval;
import app.util.toolkit.Ipage;

/**
* Title: AppProjectEvalBo.java
* Description:
* @author:jizhonghui@dhcc.com.cn
* @Thu Jul 14 01:17:56 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface AppProjectEvalFeign {
	
	@RequestMapping(value = "/appProjectEval/insert")
	public void insert(@RequestBody AppProjectEval appProjectEval) throws ServiceException;
	
	@RequestMapping(value = "/appProjectEval/insertOrUpdate")
	public void insertOrUpdate(@RequestBody AppProjectEval appProjectEval) throws ServiceException;
	
	@RequestMapping(value = "/appProjectEval/delete")
	public void delete(@RequestBody AppProjectEval appProjectEval) throws ServiceException;
	
	@RequestMapping(value = "/appProjectEval/update")
	public void update(@RequestBody AppProjectEval appProjectEval) throws ServiceException;
	
	@RequestMapping(value = "/appProjectEval/getById")
	public AppProjectEval getById(@RequestBody AppProjectEval appProjectEval) throws ServiceException;
	
	@RequestMapping(value = "/appProjectEval/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("appProjectEval") AppProjectEval appProjectEval) throws ServiceException;
	
	@RequestMapping(value = "/appProjectEval/getProEvalForAppNo")
	public AppProjectEval getProEvalForAppNo(@RequestBody String appNo) throws ServiceException;
	
}
