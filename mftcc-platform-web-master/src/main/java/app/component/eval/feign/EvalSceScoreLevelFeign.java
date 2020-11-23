package  app.component.eval.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalSceScoreLevel;
import app.util.toolkit.Ipage;

/**
* Title: EvalSceScoreLevelBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 31 12:01:52 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalSceScoreLevelFeign {
	
	@RequestMapping(value = "/evalSceScoreLevel/insert")
	public void insert(@RequestBody EvalSceScoreLevel evalSceScoreLevel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceScoreLevel/delete")
	public void delete(@RequestBody EvalSceScoreLevel evalSceScoreLevel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceScoreLevel/update")
	public void update(@RequestBody EvalSceScoreLevel evalSceScoreLevel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceScoreLevel/getById")
	public EvalSceScoreLevel getById(@RequestBody EvalSceScoreLevel evalSceScoreLevel) throws ServiceException;
	
	@RequestMapping(value = "/evalSceScoreLevel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalSceScoreLevel") EvalSceScoreLevel evalSceScoreLevel) throws ServiceException;

	@RequestMapping(value = "/evalSceScoreLevel/getAll")
	public List<EvalSceScoreLevel> getAll(@RequestBody EvalSceScoreLevel evalSceScoreLevel) throws ServiceException;
}
