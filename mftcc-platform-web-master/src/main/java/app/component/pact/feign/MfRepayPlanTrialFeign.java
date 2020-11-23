package app.component.pact.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.entity.MfRepayPlanTrial;
import app.util.toolkit.Ipage;

/**
 * Title: MfRepayPlanTrialBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Dec 12 10:35:23 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfRepayPlanTrialFeign {

	@RequestMapping(value = "/mfRepayPlanTrial/insert")
	public void insert(@RequestBody MfRepayPlanTrial mfRepayPlanTrial) throws Exception;

	@RequestMapping(value = "/mfRepayPlanTrial/delete")
	public void delete(@RequestBody MfRepayPlanTrial mfRepayPlanTrial) throws Exception;

	@RequestMapping(value = "/mfRepayPlanTrial/update")
	public void update(@RequestBody MfRepayPlanTrial mfRepayPlanTrial) throws Exception;

	@RequestMapping(value = "/mfRepayPlanTrial/getById")
	public MfRepayPlanTrial getById(@RequestBody MfRepayPlanTrial mfRepayPlanTrial) throws Exception;

	@RequestMapping(value = "/mfRepayPlanTrial/getByPactId")
	public List<MfRepayPlanTrial> getByPactId(@RequestBody MfRepayPlanTrial mfRepayPlanTrial) throws Exception;

	@RequestMapping(value = "/mfRepayPlanTrial/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfRepayPlanTrial") MfRepayPlanTrial mfRepayPlanTrial) throws Exception;

	@RequestMapping(value = "/mfRepayPlanTrial/calculate")
	public Map<String, Object> calculate(@RequestBody Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/mfRepayPlanTrial/deleteByPactId")
	public void deleteByPactId(@RequestBody MfRepayPlanTrial mfRepayPlanTrial) throws Exception;
	
	@RequestMapping(value = "/mfRepayPlanTrial/doRapayTrialDateChange")
	public Map<String,Object> doRapayTrialDateChange(@RequestBody Map<String, String> parmMap) throws Exception;
}
