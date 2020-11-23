package  app.component.calc.core.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.core.entity.MfBusFeePlanHistoryDetail;
import app.util.toolkit.Ipage;

/**
* Title: MfBusFeePlanHistoryDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 20 15:52:13 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusFeePlanHistoryDetailFeign {
	
	@RequestMapping(value = "/mfBusFeePlanHistoryDetail/insert")
	public void insert(@RequestBody MfBusFeePlanHistoryDetail mfBusFeePlanHistoryDetail) throws Exception;
	
	@RequestMapping(value = "/mfBusFeePlanHistoryDetail/delete")
	public void delete(@RequestBody MfBusFeePlanHistoryDetail mfBusFeePlanHistoryDetail) throws Exception;
	
	@RequestMapping(value = "/mfBusFeePlanHistoryDetail/update")
	public void update(@RequestBody MfBusFeePlanHistoryDetail mfBusFeePlanHistoryDetail) throws Exception;
	
	@RequestMapping(value = "/mfBusFeePlanHistoryDetail/getById")
	public MfBusFeePlanHistoryDetail getById(@RequestBody MfBusFeePlanHistoryDetail mfBusFeePlanHistoryDetail) throws Exception;
	
	@RequestMapping(value = "/mfBusFeePlanHistoryDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusFeePlanHistoryDetail") MfBusFeePlanHistoryDetail mfBusFeePlanHistoryDetail) throws Exception;
	
	@RequestMapping(value = "/mfBusFeePlanHistoryDetail/findList")
	public List<MfBusFeePlanHistoryDetail> findList(@RequestBody MfBusFeePlanHistoryDetail mfBusFeePlanHistoryDetail) throws Exception; 
	
}
