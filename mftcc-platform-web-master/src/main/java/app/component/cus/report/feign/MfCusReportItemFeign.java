package app.component.cus.report.feign;

import app.component.cus.report.entity.MfCusReportCalc;
import app.component.finance.finreport.entity.CwSearchReportList;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import app.component.cus.report.entity.MfCusReportItem;
import app.util.toolkit.Ipage;

import java.util.List;
import java.util.Map;


/**
* Title: MfCusReportItemBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu May 09 10:46:30 CST 2019
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusReportItemFeign {
	
	@RequestMapping(value = "/mfCusReportItem/insert")
	public void insert(@RequestBody MfCusReportItem mfCusReportItem) throws Exception;
	
	@RequestMapping(value = "/mfCusReportItem/delete")
	public void delete(@RequestBody MfCusReportItem mfCusReportItem) throws Exception;
	
	@RequestMapping(value = "/mfCusReportItem/update")
	public void update(@RequestBody MfCusReportItem mfCusReportItem) throws Exception;
	
	@RequestMapping(value = "/mfCusReportItem/getById")
	public MfCusReportItem getById(@RequestBody MfCusReportItem mfCusReportItem) throws Exception;
	
	@RequestMapping(value = "/mfCusReportItem/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfCusReportItem") MfCusReportItem mfCusReportItem) throws Exception;


	@RequestMapping(value = "/mfCusReportItem/getList")
	public List<MfCusReportItem> getList(@RequestBody MfCusReportItem mfCusReportItem) throws Exception;

	@RequestMapping(value = "/mfCusReportItem/insertItemAndCalc")
	public MfCusReportItem insertItemAndCalc(@RequestBody Map<String, Object> paramMap)
			throws Exception;

    @RequestMapping(value = "/mfCusReportItem/getSetReportList")
    public List<CwSearchReportList> getSetReportList(@RequestBody Map<String, String> formMap) throws Exception;

    @RequestMapping(value = "/mfCusReportItem/getReportViewList")
    public List<CwSearchReportList> getReportViewList(@RequestBody Map<String, String> formMap) throws Exception;

    @RequestMapping(value = "/mfCusReportItem/getReportItemDetail")
    public MfCusReportItem getReportItemDetail(@RequestBody MfCusReportItem mfCusReportItem) throws Exception;

    @RequestMapping(value = "/mfCusReportItem/getItemCalcSetList")
    public List<MfCusReportCalc> getItemCalcSetList(@RequestBody Map<String, String> formMap) throws Exception;

    @RequestMapping(value = "/mfCusReportItem/updateOrder")
    public String updateOrder(@RequestBody Map<String, String> paramMap) throws Exception;

    @RequestMapping(value = "/mfCusReportItem/updateDeployData")
    public String updateDeployData(@RequestBody Map<String, Object> paramMap) throws Exception;


}
