package  app.component.tcph.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.tcph.entity.MfFundPlan;
import app.util.toolkit.Ipage;

/**
* Title: MfFundPlanBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Nov 29 18:13:15 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfFundPlanFeign {
	@RequestMapping(value = "/mfFundPlan/insert")
	public void insert(@RequestBody MfFundPlan mfFundPlan) throws Exception;
	@RequestMapping(value = "/mfFundPlan/delete")
	public void delete(@RequestBody MfFundPlan mfFundPlan) throws Exception;
	@RequestMapping(value = "/mfFundPlan/update")
	public void update(@RequestBody MfFundPlan mfFundPlan) throws Exception;
	@RequestMapping(value = "/mfFundPlan/getById")
	public MfFundPlan getById(@RequestBody MfFundPlan mfFundPlan) throws Exception;
	@RequestMapping(value = "/mfFundPlan/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	@RequestMapping(value = "/mfFundPlan/getMfFundPlanTable")
	public Map<String , Object> getMfFundPlanTable(@RequestBody Map<String, Object> dataMap)throws Exception;
	@RequestMapping(value = "/mfFundPlan/getDays")
	public int getDays(@RequestBody Map<String, Object> dataMap)throws Exception;
	@RequestMapping(value = "/mfFundPlan/exportExcel")
	public String exportExcel(@RequestBody Map<String, Object> mfFundPlanTableMap)throws Exception;

	
}
