package app.component.cus.report.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import app.component.cus.report.entity.MfCusReportData;
import app.util.toolkit.Ipage;

import java.util.List;
import java.util.Map;


/**
* Title: MfCusReportDataBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu May 09 10:45:59 CST 2019
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusReportDataFeign {
	
	@RequestMapping(value = "/mfCusReportData/insert")
	public void insert(@RequestBody MfCusReportData mfCusReportData) throws Exception;
	
	@RequestMapping(value = "/mfCusReportData/delete")
	public void delete(@RequestBody MfCusReportData mfCusReportData) throws Exception;
	
	@RequestMapping(value = "/mfCusReportData/update")
	public void update(@RequestBody MfCusReportData mfCusReportData) throws Exception;
	
	@RequestMapping(value = "/mfCusReportData/getById")
	public MfCusReportData getById(@RequestBody MfCusReportData mfCusReportData) throws Exception;
	
	@RequestMapping(value = "/mfCusReportData/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfCusReportData") MfCusReportData mfCusReportData) throws Exception;

	@RequestMapping(value = "/mfCusReportData/getList")
	public List<MfCusReportData> getList(@RequestBody MfCusReportData mfCusReportData) throws Exception;

	@RequestMapping(value = "/mfCusReportData/getByDataIdForList")
	public Map<String,Object> getByDataIdForList(MfCusReportData mfCusReportData)throws Exception;

	/**
	 * 在线填报新增或修改
	 * @param mfCusReportDataList
	 * @param mfCusReportAcount
	 * @param reportTypeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusReportData/insertOrUpdateOnline")
	public void insertOrUpdateOnline(@RequestBody Map<String,Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfCusReportData/analysisFinanceJson")
    Map<String, Object> analysisFinanceJson(@RequestParam("json") String json,@RequestParam("rptdate") String rptdate,@RequestParam("companyname") String companyname,@RequestParam("state") String state)throws Exception;

}
