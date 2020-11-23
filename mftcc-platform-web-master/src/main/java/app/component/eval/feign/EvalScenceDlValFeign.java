package  app.component.eval.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalScenceDlVal;
import app.util.toolkit.Ipage;

/**
* Title: EvalScenceDlValBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 31 06:37:10 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalScenceDlValFeign {
	
	@RequestMapping(value = "/evalScenceDlVal/insert")
	public void insert(@RequestBody EvalScenceDlVal evalScenceDlVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDlVal/insertOrUpdate")
	public void insertOrUpdate(@RequestBody EvalScenceDlVal evalScenceDlVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDlVal/delete")
	public void delete(@RequestBody EvalScenceDlVal evalScenceDlVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDlVal/update")
	public void update(@RequestBody EvalScenceDlVal evalScenceDlVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDlVal/getById")
	public EvalScenceDlVal getById(@RequestBody EvalScenceDlVal evalScenceDlVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDlVal/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalScenceDlVal") EvalScenceDlVal evalScenceDlVal) throws ServiceException;

	@RequestMapping(value = "/evalScenceDlVal/getAll")
	public List<EvalScenceDlVal> getAll(@RequestBody EvalScenceDlVal evalScenceDlVal) throws ServiceException;
}
