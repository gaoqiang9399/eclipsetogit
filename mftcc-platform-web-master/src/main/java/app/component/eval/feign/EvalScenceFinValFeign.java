package  app.component.eval.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalScenceFinVal;
import app.util.toolkit.Ipage;

/**
* Title: EvalScenceFinValBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 31 06:35:36 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalScenceFinValFeign {
	
	@RequestMapping(value = "/evalScenceFinVal/insert")
	public void insert(@RequestBody EvalScenceFinVal evalScenceFinVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceFinVal/insertOrUpdate")
	public void insertOrUpdate(@RequestBody EvalScenceFinVal evalScenceFinVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceFinVal/delete")
	public void delete(@RequestBody EvalScenceFinVal evalScenceFinVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceFinVal/update")
	public void update(@RequestBody EvalScenceFinVal evalScenceFinVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceFinVal/getById")
	public EvalScenceFinVal getById(@RequestBody EvalScenceFinVal evalScenceFinVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceFinVal/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalScenceFinVal") EvalScenceFinVal evalScenceFinVal) throws ServiceException;
	
}
