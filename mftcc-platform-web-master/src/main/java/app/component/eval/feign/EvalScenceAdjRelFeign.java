package  app.component.eval.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalScenceAdjRel;
import app.util.toolkit.Ipage;

/**
* Title: EvalScenceAdjRelBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 17 06:46:26 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalScenceAdjRelFeign {
	
	@RequestMapping(value = "/evalScenceAdjRel/insert")
	public List<EvalScenceAdjRel> insert(@RequestBody EvalScenceAdjRel evalScenceAdjRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceAdjRel/delete")
	public void delete(@RequestBody EvalScenceAdjRel evalScenceAdjRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceAdjRel/update")
	public void update(@RequestBody EvalScenceAdjRel evalScenceAdjRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceAdjRel/getById")
	public EvalScenceAdjRel getById(@RequestBody EvalScenceAdjRel evalScenceAdjRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceAdjRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalScenceAdjRel") EvalScenceAdjRel evalScenceAdjRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceAdjRel/getForScenceNo")
	public List<EvalScenceAdjRel> getForScenceNo(@RequestBody String scenceNo) throws ServiceException;
	
}
