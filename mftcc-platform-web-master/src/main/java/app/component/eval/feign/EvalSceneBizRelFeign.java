package  app.component.eval.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalSceneBizRel;
import app.util.toolkit.Ipage;

/**
* Title: EvalSceneBizRelBo.java
* Description:
* @author:@dhcc.com.cn
* @Fri Sep 02 05:37:40 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalSceneBizRelFeign {
	
	@RequestMapping(value = "/evalSceneBizRel/insert")
	public void insert(@RequestBody EvalSceneBizRel evalSceneBizRel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceneBizRel/delete")
	public void delete(@RequestBody EvalSceneBizRel evalSceneBizRel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceneBizRel/update")
	public void update(@RequestBody EvalSceneBizRel evalSceneBizRel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceneBizRel/getEvalSceneNo")
	public EvalSceneBizRel getEvalSceneNo(@RequestBody EvalSceneBizRel evalSceneBizRel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceneBizRel/getById")
	public EvalSceneBizRel getById(@RequestBody EvalSceneBizRel evalSceneBizRel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceneBizRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalSceneBizRel") EvalSceneBizRel evalSceneBizRel) throws ServiceException;
	
}
