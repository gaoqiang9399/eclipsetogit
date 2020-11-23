package  app.component.eval.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalScenceFinRel;
import app.util.toolkit.Ipage;

/**
* Title: EvalScenceFinRelBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 17 06:44:16 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalScenceFinRelFeign {
	
	@RequestMapping(value = "/evalScenceFinRel/insert")
	public EvalScenceFinRel insert(@RequestBody EvalScenceFinRel evalScenceFinRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceFinRel/delete")
	public void delete(@RequestBody EvalScenceFinRel evalScenceFinRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceFinRel/update")
	public void update(@RequestBody EvalScenceFinRel evalScenceFinRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceFinRel/getById")
	public EvalScenceFinRel getById(@RequestBody EvalScenceFinRel evalScenceFinRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceFinRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalScenceFinRel") EvalScenceFinRel evalScenceFinRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceFinRel/getForScenceNo")
	public List<EvalScenceFinRel> getForScenceNo(@RequestBody EvalScenceFinRel evalScenceFinRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceFinRel/getByFinCode")
	public List<EvalScenceFinRel> getByFinCode(@RequestParam("scenceNo") String scenceNo,@RequestParam("finCode") String finCode) throws Exception;
		
}
