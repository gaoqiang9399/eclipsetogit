package app.component.cus.judgment.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.cus.entity.MfCusEquityInfo;
import app.component.cus.judgment.entity.MfCusJudgment;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfCusJudgmentFeign {
	@RequestMapping(value = "/mfCusJudgment/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusJudgment/insert")
	public Map<String, Object> insert(@RequestBody MfCusJudgment mfCusJudgment) throws ServiceException;
	
	@RequestMapping(value = "/mfCusJudgment/getById")
	public MfCusJudgment getById(@RequestBody MfCusJudgment mfCusJudgment) throws ServiceException;
	
	@RequestMapping(value = "/mfCusJudgment/update")
	public void update(@RequestBody MfCusJudgment mfCusJudgment) throws ServiceException;
	
	@RequestMapping(value = "/mfCusJudgment/delete")
	public void delete(@RequestBody MfCusJudgment mfCusJudgment) throws Exception;
	
	@RequestMapping(value = "/mfCusJudgment/getAllList")
	public List<MfCusJudgment> getAllList(@RequestBody MfCusJudgment mfCusJudgment) throws ServiceException;
}
