package  app.component.eval.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalScenceDxRel;
import app.util.toolkit.Ipage;

/**
* Title: EvalScenceDxRelBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 17 06:45:25 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalScenceDxRelFeign {
	
	@RequestMapping(value = "/evalScenceDxRel/insert")
	public List<EvalScenceDxRel> insert(@RequestBody EvalScenceDxRel evalScenceDxRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDxRel/delete")
	public void delete(@RequestBody EvalScenceDxRel evalScenceDxRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDxRel/update")
	public void update(@RequestBody EvalScenceDxRel evalScenceDxRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDxRel/getById")
	public EvalScenceDxRel getById(@RequestBody EvalScenceDxRel evalScenceDxRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDxRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalScenceDxRel") EvalScenceDxRel evalScenceDxRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDxRel/getForScenceNo")
	public List<EvalScenceDxRel> getForScenceNo(@RequestBody String scenceNo) throws ServiceException;
	
}
