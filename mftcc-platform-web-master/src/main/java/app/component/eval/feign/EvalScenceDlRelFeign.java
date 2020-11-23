package  app.component.eval.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalScenceDlRel;
import app.util.toolkit.Ipage;

/**
* Title: EvalScenceDlRelBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Mar 22 01:39:54 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalScenceDlRelFeign {
	
	@RequestMapping(value = "/evalScenceDlRel/insert")
	public void insert(@RequestBody EvalScenceDlRel evalScenceDlRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDlRel/delete")
	public void delete(@RequestBody EvalScenceDlRel evalScenceDlRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDlRel/update")
	public void update(@RequestBody EvalScenceDlRel evalScenceDlRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDlRel/getById")
	public EvalScenceDlRel getById(@RequestBody EvalScenceDlRel evalScenceDlRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDlRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalScenceDlRel") EvalScenceDlRel evalScenceDlRel) throws ServiceException;

	@RequestMapping(value = "/evalScenceDlRel/getAll")
	public List<EvalScenceDlRel> getAll(@RequestBody EvalScenceDlRel evalScenceDlRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDlRel/getForScenceNo")
	public List<EvalScenceDlRel> getForScenceNo(@RequestBody String scenceNo) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceDlRel/getByJavaItem")
	public List<EvalScenceDlRel> getByJavaItem(@RequestBody String scenceNo,@RequestParam("javaItem") String javaItem) throws ServiceException;
}
