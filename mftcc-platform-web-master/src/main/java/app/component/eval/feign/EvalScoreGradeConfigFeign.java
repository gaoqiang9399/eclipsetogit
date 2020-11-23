package  app.component.eval.feign;
import java.util.List;

import com.core.domain.screen.OptionsList;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalScoreGradeConfig;
import app.util.toolkit.Ipage;

/**
* Title: EvalScoreGradeConfigBo.java
* Description:
* @author:@dhcc.com.cn
* @Wed Apr 27 06:10:07 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalScoreGradeConfigFeign {
	
	@RequestMapping(value = "/evalScoreGradeConfig/insert")
	public void insert(@RequestBody EvalScoreGradeConfig evalScoreGradeConfig) throws ServiceException;
	
	@RequestMapping(value = "/evalScoreGradeConfig/delete")
	public void delete(@RequestBody EvalScoreGradeConfig evalScoreGradeConfig) throws ServiceException;
	
	@RequestMapping(value = "/evalScoreGradeConfig/update")
	public void update(@RequestBody EvalScoreGradeConfig evalScoreGradeConfig) throws ServiceException;
	
	@RequestMapping(value = "/evalScoreGradeConfig/getById")
	public EvalScoreGradeConfig getById(@RequestBody EvalScoreGradeConfig evalScoreGradeConfig) throws ServiceException;
	
	@RequestMapping(value = "/evalScoreGradeConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/evalScoreGradeConfig/getAll")
	public List<EvalScoreGradeConfig> getAll(@RequestBody EvalScoreGradeConfig evalScoreGradeConfig) throws ServiceException;
	
	@RequestMapping(value = "/evalScoreGradeConfig/getForScenceNo")
	public List<EvalScoreGradeConfig> getForScenceNo(@RequestBody String scenceNo) throws ServiceException;
	@RequestMapping(value = "/evalScoreGradeConfig/getGradeLevelList")
	List<OptionsList> getGradeLevelList(@RequestBody EvalScoreGradeConfig evalScoreGradeConfig)throws ServiceException;
}
