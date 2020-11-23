package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusApplySpouseSurvey;
import app.util.toolkit.Ipage;

/**
* Title: MfCusApplySpouseSurveyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Nov 28 09:34:32 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusApplySpouseSurveyFeign {
	@RequestMapping(value = "/mfCusApplySpouseSurvey/insert")
	public void insert(@RequestBody MfCusApplySpouseSurvey mfCusApplySpouseSurvey) throws Exception;
	
	@RequestMapping(value = "/mfCusApplySpouseSurvey/delete")
	public void delete(@RequestBody MfCusApplySpouseSurvey mfCusApplySpouseSurvey) throws Exception;
	
	@RequestMapping(value = "/mfCusApplySpouseSurvey/update")
	public void update(@RequestBody MfCusApplySpouseSurvey mfCusApplySpouseSurvey) throws Exception;
	
	@RequestMapping(value = "/mfCusApplySpouseSurvey/getById")
	public MfCusApplySpouseSurvey getById(@RequestBody MfCusApplySpouseSurvey mfCusApplySpouseSurvey) throws Exception;
	
	@RequestMapping(value = "/mfCusApplySpouseSurvey/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
