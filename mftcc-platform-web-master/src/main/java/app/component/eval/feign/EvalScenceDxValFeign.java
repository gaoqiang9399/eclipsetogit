package  app.component.eval.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.util.toolkit.Ipage;
import app.component.eval.entity.EvalScenceDxVal;

/**
* Title: EvalScenceDxValBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 31 06:38:05 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalScenceDxValFeign {
	
	@RequestMapping(value = "/evalScenceDxVal/insert")
	public void insert(@RequestBody EvalScenceDxVal evalScenceDxVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDxVal/insertOrUpdate")
	public void insertOrUpdate(@RequestBody EvalScenceDxVal evalScenceDxVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDxVal/delete")
	public void delete(@RequestBody EvalScenceDxVal evalScenceDxVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDxVal/update")
	public void update(@RequestBody EvalScenceDxVal evalScenceDxVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDxVal/insertOrUpdateProjectEval")
	public void insertOrUpdateProjectEval(@RequestBody EvalScenceDxVal evalScenceDxVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDxVal/getById")
	public EvalScenceDxVal getById(@RequestBody EvalScenceDxVal evalScenceDxVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDxVal/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalScenceDxVal") EvalScenceDxVal evalScenceDxVal) throws ServiceException;

	@RequestMapping(value = "/evalScenceDxVal/getAll")
	public List<EvalScenceDxVal> getAll(@RequestBody EvalScenceDxVal evalScenceDxVal) throws ServiceException;
}
