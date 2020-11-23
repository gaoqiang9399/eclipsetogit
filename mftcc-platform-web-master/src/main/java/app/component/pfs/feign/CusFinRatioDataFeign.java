package app.component.pfs.feign;

import java.util.List;
import java.util.Map;

import app.component.cus.report.entity.MfCusReportAcount;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinMain;
import app.component.pfs.entity.CusFinRatioData;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinRatioDataBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Wed Jun 15 03:05:33 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinRatioDataFeign {

	@RequestMapping(value = "/cusFinRatioData/insert")
	public void insert(@RequestBody CusFinRatioData cusFinRatioData) throws Exception ;

	@RequestMapping(value = "/cusFinRatioData/delete")
	public void delete(@RequestBody CusFinRatioData cusFinRatioData) throws Exception ;

	@RequestMapping(value = "/cusFinRatioData/update")
	public void update(@RequestBody CusFinRatioData cusFinRatioData) throws Exception ;

	@RequestMapping(value = "/cusFinRatioData/updateFormVal")
	public void updateFormVal(@RequestBody Map<String,Object> parmMap) throws Exception ;

	@RequestMapping(value = "/cusFinRatioData/getById")
	public CusFinRatioData getById(@RequestBody CusFinRatioData cusFinRatioData) throws Exception ;

	@RequestMapping(value = "/cusFinRatioData/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("cusFinRatioData") CusFinRatioData cusFinRatioData) throws Exception ;

	@RequestMapping(value = "/cusFinRatioData/getAll")
	public List<CusFinRatioData> getAll(@RequestBody CusFinRatioData cusFinRatioData) throws Exception ;

	@RequestMapping(value = "/cusFinRatioData/getForFinIndicators")
	public List<CusFinRatioData> getForFinIndicators(@RequestBody CusFinMain cusFinMain) throws Exception ;

	@RequestMapping(value = "/cusFinRatioData/getForLstFinIndicators")
	public List<CusFinRatioData> getForLstFinIndicators(@RequestBody String cusNo) throws Exception ;

	@RequestMapping(value = "/cusFinRatioData/getCusFinRatioListByClassNo")
	public Map<String,Object> getCusFinRatioListByClassNo(@RequestBody CusFinMain cusFinMain) throws Exception;

	@RequestMapping(value = "/cusFinRatioData/getCusFinRatioListByClassNoNew")
	public Map<String,Object> getCusFinRatioListByClassNoNew(@RequestBody MfCusReportAcount mfCusReportAcount) throws Exception;


	@RequestMapping(value = "/cusFinRatioData/updateRatioAnalysis")
	public void updateRatioAnalysis(@RequestBody CusFinRatioData cusFinRatioData) throws Exception ;
}
