package  app.component.eval.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalScenceRestrictRel;
import app.util.toolkit.Ipage;

/**
* Title: EvalScenceRestrictRelBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Mar 22 01:41:22 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalScenceRestrictRelFeign {
	
	@RequestMapping(value = "/evalScenceRestrictRel/insert")
	public void insert(@RequestBody EvalScenceRestrictRel evalScenceRestrictRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceRestrictRel/delete")
	public void delete(@RequestBody EvalScenceRestrictRel evalScenceRestrictRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceRestrictRel/update")
	public void update(@RequestBody EvalScenceRestrictRel evalScenceRestrictRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceRestrictRel/getById")
	public EvalScenceRestrictRel getById(@RequestBody EvalScenceRestrictRel evalScenceRestrictRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceRestrictRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalScenceRestrictRel") EvalScenceRestrictRel evalScenceRestrictRel) throws ServiceException;

	@RequestMapping(value = "/evalScenceRestrictRel/getAll")
	public List<EvalScenceRestrictRel> getAll(@RequestBody EvalScenceRestrictRel evalScenceRestrictRel) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceRestrictRel/getForScenceNo")
	public List<EvalScenceRestrictRel> getForScenceNo(@RequestBody String scenceNo) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceRestrictRel/getForJavaItem")
	public List<EvalScenceRestrictRel> getForJavaItem(@RequestBody String scenceNo,@RequestParam("javaItem") String javaItem) throws ServiceException;

}
