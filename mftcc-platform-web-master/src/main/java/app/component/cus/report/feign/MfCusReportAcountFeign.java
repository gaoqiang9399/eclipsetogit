package app.component.cus.report.feign;

import app.base.ServiceException;
import app.component.cus.report.entity.MfCusReportData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import app.util.toolkit.Ipage;

import app.component.cus.report.entity.MfCusReportAcount;

import java.util.List;
import java.util.Map;

/**
* Title: MfCusReportAcountBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu May 09 09:53:12 CST 2019
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusReportAcountFeign {
	
	@RequestMapping(value = "/mfCusReportAcount/insert")
	public void insert(@RequestBody MfCusReportAcount mfCusReportAcount) throws Exception;
	
	@RequestMapping(value = "/mfCusReportAcount/delete")
	public void delete(@RequestBody MfCusReportAcount mfCusReportAcount) throws Exception;
	
	@RequestMapping(value = "/mfCusReportAcount/update")
	public void update(@RequestBody MfCusReportAcount mfCusReportAcount) throws Exception;
	
	@RequestMapping(value = "/mfCusReportAcount/getById")
	public MfCusReportAcount getById(@RequestBody MfCusReportAcount mfCusReportAcount) throws Exception;
	
	@RequestMapping(value = "/mfCusReportAcount/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfCusReportAcount") MfCusReportAcount mfCusReportAcount) throws Exception;

	@RequestMapping(value = "/mfCusReportAcount/insertMoreExcelNew")
	public MfCusReportAcount insertMoreExcelNew(@RequestBody Map<String, Object> beans, @RequestParam("teplateType") String teplateType, @RequestParam("cusType") String cusType) throws Exception;

	/*****
	 * 20190514-peng
	 * @param mfCusReportAcount
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusReportAcount/getList")
	public List<MfCusReportAcount> getList(@RequestBody MfCusReportAcount mfCusReportAcount) throws Exception;


	@RequestMapping(value = "/mfCusReportAcount/insertOrUpdate")
	public void insertOrUpdate(@RequestBody List<MfCusReportData> mfCusReportDataList,@RequestParam("dataId") String dataId) throws Exception;


	@RequestMapping(value = "/mfCusReportAcount/updateReportConfirm")
	public Map<String,Object> updateReportConfirm(@RequestBody MfCusReportAcount mfCusReportAcount) throws Exception;

	@RequestMapping(value = "/mfCusReportAcount/getListForEval")

	List<MfCusReportAcount> getListForEval(@RequestBody MfCusReportAcount mfCusReportAcount);
	@RequestMapping(value = "/mfCusReportAcount/getSubJectsMap")
    Map<String, Object> getSubJectsMap(@RequestParam("accountIdArray")String accountIdArray)throws Exception;

	@RequestMapping(value = "/mfCusReportAcount/getFincialIndexMap")
	Map<String, Object> getFincialIndexMap(@RequestParam("accountIdArray")String accountIdArray)throws Exception;

	@RequestMapping(value = "/mfCusReportAcount/getContinuityReport")
	List<MfCusReportAcount> getContinuityReport(@RequestParam("cusNo")String cusNo, @RequestParam("rptDate")String rptDate, @RequestParam("submitMonth")String submitMonth, @RequestParam("accountId")String accountId)throws ServiceException;

	/**
	 * 查询最近一期财务报表
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusReportAcount/getReportOrdeyByWeek")
	public MfCusReportAcount getReportOrdeyByWeek(@RequestParam("cusNo")String cusNo) throws Exception;
}
