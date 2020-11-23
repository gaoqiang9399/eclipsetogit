package app.component.cus.report.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import app.component.cus.report.entity.MfCusReportCalc;
import app.util.toolkit.Ipage;

import java.util.List;

/**
* Title: MfCusReportCalcBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu May 09 10:45:22 CST 2019
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusReportCalcFeign {
	
	@RequestMapping(value = "/mfCusReportCalc/insert")
	public void insert(@RequestBody MfCusReportCalc mfCusReportCalc) throws Exception;
	
	@RequestMapping(value = "/mfCusReportCalc/delete")
	public void delete(@RequestBody MfCusReportCalc mfCusReportCalc) throws Exception;
	
	@RequestMapping(value = "/mfCusReportCalc/update")
	public void update(@RequestBody MfCusReportCalc mfCusReportCalc) throws Exception;
	
	@RequestMapping(value = "/mfCusReportCalc/getById")
	public MfCusReportCalc getById(@RequestBody MfCusReportCalc mfCusReportCalc) throws Exception;
	
	@RequestMapping(value = "/mfCusReportCalc/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfCusReportCalc") MfCusReportCalc mfCusReportCalc) throws Exception;

	@RequestMapping(value = "/mfCusReportCalc/getList")
	public List<MfCusReportCalc> getList(@RequestBody MfCusReportCalc mfCusReportCalc) throws Exception;
	
}
