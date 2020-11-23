package  app.component.eval.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalScenceRestrictVal;
import app.util.toolkit.Ipage;

/**
* Title: EvalScenceRestrictValBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 31 06:39:47 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalScenceRestrictValFeign {
	
	@RequestMapping(value = "/evalScenceRestrictVal/insert")
	public void insert(@RequestBody EvalScenceRestrictVal evalScenceRestrictVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceRestrictVal/insertOrUpdate")
	public void insertOrUpdate(@RequestBody EvalScenceRestrictVal evalScenceRestrictVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceRestrictVal/delete")
	public void delete(@RequestBody EvalScenceRestrictVal evalScenceRestrictVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceRestrictVal/update")
	public void update(@RequestBody EvalScenceRestrictVal evalScenceRestrictVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceRestrictVal/getById")
	public EvalScenceRestrictVal getById(@RequestBody EvalScenceRestrictVal evalScenceRestrictVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceRestrictVal/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalScenceRestrictVal") EvalScenceRestrictVal evalScenceRestrictVal) throws ServiceException;

	@RequestMapping(value = "/evalScenceRestrictVal/getAll")
	public List<EvalScenceRestrictVal> getAll(@RequestBody EvalScenceRestrictVal evalScenceRestrictVal) throws ServiceException;
}
