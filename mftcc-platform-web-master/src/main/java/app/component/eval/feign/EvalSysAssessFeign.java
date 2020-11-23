package  app.component.eval.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalSysAssess;
import app.util.toolkit.Ipage;

/**
* Title: EvalSysAssessBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Jul 26 02:04:05 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalSysAssessFeign {
	
	@RequestMapping(value = "/evalSysAssess/insert")
	public void insert(@RequestBody EvalSysAssess evalSysAssess) throws ServiceException;
	
	@RequestMapping(value = "/evalSysAssess/delete")
	public void delete(@RequestBody EvalSysAssess evalSysAssess) throws ServiceException;
	
	@RequestMapping(value = "/evalSysAssess/update")
	public void update(@RequestBody EvalSysAssess evalSysAssess) throws ServiceException;
	
	@RequestMapping(value = "/evalSysAssess/getById")
	public EvalSysAssess getById(@RequestBody EvalSysAssess evalSysAssess) throws ServiceException;
	
	@RequestMapping(value = "/evalSysAssess/getByLevel")
	public String getByLevel(@RequestBody String evalLevel) throws ServiceException;
	
	@RequestMapping(value = "/evalSysAssess/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalSysAssess") EvalSysAssess evalSysAssess) throws ServiceException;
	
}
