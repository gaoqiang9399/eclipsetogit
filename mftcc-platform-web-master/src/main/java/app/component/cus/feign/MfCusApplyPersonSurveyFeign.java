package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusApplyPersonSurvey;
import app.util.toolkit.Ipage;

/**
* Title: MfCusApplyPersonSurveyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Nov 28 09:32:23 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusApplyPersonSurveyFeign {
	
	@RequestMapping(value = "/mfCusApplyPersonSurvey/insert")
	public void insert(@RequestBody MfCusApplyPersonSurvey mfCusApplyPersonSurvey) throws Exception;
	
	@RequestMapping(value = "/mfCusApplyPersonSurvey/insertForApp")
	public void insertForApp(@RequestBody MfCusApplyPersonSurvey mfCusApplyPersonSurvey) throws Exception;
	
	@RequestMapping(value = "/mfCusApplyPersonSurvey/delete")
	public void delete(@RequestBody MfCusApplyPersonSurvey mfCusApplyPersonSurvey) throws Exception;
	
	@RequestMapping(value = "/mfCusApplyPersonSurvey/update")
	public void update(@RequestBody MfCusApplyPersonSurvey mfCusApplyPersonSurvey) throws Exception;
	
	@RequestMapping(value = "/mfCusApplyPersonSurvey/getById")
	public MfCusApplyPersonSurvey getById(@RequestBody MfCusApplyPersonSurvey mfCusApplyPersonSurvey) throws Exception;
	
	@RequestMapping(value = "/mfCusApplyPersonSurvey/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfCusApplyPersonSurvey/findByEntity")
	public List<MfCusApplyPersonSurvey> findByEntity(@RequestBody MfCusApplyPersonSurvey mfCusApplyPersonSurvey) throws Exception;
	
}
