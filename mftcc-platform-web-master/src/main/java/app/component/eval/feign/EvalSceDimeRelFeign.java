package  app.component.eval.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalSceDimeRel;
import app.util.toolkit.Ipage;

/**
* Title: EvalSceDimeRelBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 31 12:04:33 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalSceDimeRelFeign {
	
	@RequestMapping(value = "/evalSceDimeRel/insert")
	public void insert(@RequestBody EvalSceDimeRel evalSceDimeRel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceDimeRel/delete")
	public void delete(@RequestBody EvalSceDimeRel evalSceDimeRel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceDimeRel/update")
	public void update(@RequestBody EvalSceDimeRel evalSceDimeRel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceDimeRel/getById")
	public EvalSceDimeRel getById(@RequestBody EvalSceDimeRel evalSceDimeRel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceDimeRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalSceDimeRel") EvalSceDimeRel evalSceDimeRel) throws ServiceException;

	@RequestMapping(value = "/evalSceDimeRel/getAll")
	public List<EvalSceDimeRel> getAll(@RequestBody EvalSceDimeRel evalSceDimeRel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceDimeRel/getByOptCode")
	public EvalSceDimeRel getByOptCode(@RequestBody String optCode) throws ServiceException;
}
