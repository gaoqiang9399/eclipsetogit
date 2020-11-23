package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusPersonIncomeSurvey;
import app.util.toolkit.Ipage;

/**
* Title: MfCusPersonIncomeSurveyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Nov 28 09:27:50 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusPersonIncomeSurveyFeign {
	
	@RequestMapping("/mfCusPersonIncomeSurvey/insert")
	public void insert(@RequestBody MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey) throws Exception;
	
	@RequestMapping("/mfCusPersonIncomeSurvey/insertForApp")
	public void insertForApp(@RequestBody MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey) throws Exception;
	
	@RequestMapping("/mfCusPersonIncomeSurvey/delete")
	public void delete(@RequestBody MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey) throws Exception;
	
	@RequestMapping("/mfCusPersonIncomeSurvey/update")
	public void update(@RequestBody MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey) throws Exception;
	
	@RequestMapping("/mfCusPersonIncomeSurvey/getById")
	public MfCusPersonIncomeSurvey getById(@RequestBody MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey) throws Exception;
	
	@RequestMapping("/mfCusPersonIncomeSurvey/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusPersonIncomeSurvey/findByEntity")
	public List<MfCusPersonIncomeSurvey> findByEntity(@RequestBody MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey) throws Exception;
	
}
