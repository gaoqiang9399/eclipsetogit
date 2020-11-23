package  app.component.thirdRecord.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.channel.fund.entity.MfFundChannelRepayPlan;
import app.util.toolkit.Ipage;


/**
 * Title: MfFundChannelRepayPlanFeign.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 16 19:39:17 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfFundChannelRepayPlanFeign{

	@RequestMapping(value = "/mfFundChannelRepayPlan/insert")
	public MfFundChannelRepayPlan insert(@RequestBody MfFundChannelRepayPlan mfFundChannelRepayPlan);
	
	@RequestMapping(value = "/mfFundChannelRepayPlan/insertList")
	public MfFundChannelRepayPlan insertList(@RequestParam("ajaxData") String ajaxData,@RequestBody Map<String,Object> pactMap);

	@RequestMapping(value = "/mfFundChannelRepayPlan/getById")
	public MfFundChannelRepayPlan getById(@RequestBody MfFundChannelRepayPlan mfFundChannelRepayPlan);
	
	@RequestMapping(value = "/mfFundChannelRepayPlan/getByIdForRepay")
	public MfFundChannelRepayPlan getByIdForRepay(@RequestBody MfFundChannelRepayPlan mfFundChannelRepayPlan);

	@RequestMapping(value = "/mfFundChannelRepayPlan/getRecourseList")
	public List<MfFundChannelRepayPlan> getRecourseList(@RequestBody MfFundChannelRepayPlan mfFundChannelRepayPlan);

	@RequestMapping(value = "/mfFundChannelRepayPlan/doExportExcelModel")
	public String doExportExcelModel(@RequestParam("pactId") String pactId);
	
	@RequestMapping(value = "/mfFundChannelRepayPlan/submitBussProcess")
	public void submitBussProcess(@RequestParam("appId") String appId,@RequestParam("opNo") String opNo) throws ServiceException;
	
	@RequestMapping(value = "/mfFundChannelRepayPlan/getMfFundChannelUnRepayPlanByPactId")
	public List<MfFundChannelRepayPlan> getMfFundChannelUnRepayPlanByPactId(@RequestBody MfFundChannelRepayPlan mfFundChannelRepayPlan) throws Exception;
	
	@RequestMapping(value = "/mfFundChannelRepayPlan/saveMfFundChannelRepayplan")
	public Map<String,String> saveMfFundChannelRepayplan(@RequestBody Map<String,Object> formMap) throws Exception;
	
	@RequestMapping("/mfFundChannelRepayPlan/findByPageForMfFundChannelRepayPlan")
	public Ipage findByPageForMfFundChannelRepayPlan(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfFundChannelRepayPlan/insertNewPlanList")
	public MfFundChannelRepayPlan insertNewPlanList(@RequestParam("ajaxData") String ajaxData,@RequestBody Map<String,Object> pactMap);
	
	@RequestMapping(value = "/mfFundChannelRepayPlan/getMfFundChannelRepayPlanList")
	public List<MfFundChannelRepayPlan> getMfFundChannelRepayPlanList(@RequestBody MfFundChannelRepayPlan mfFundChannelRepayPlan);
}
