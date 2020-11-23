package app.component.pfsinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinCapData;
import app.component.pfs.entity.CusFinCashData;
import app.component.pfs.entity.CusFinForm;
import app.component.pfs.entity.CusFinMain;
import app.component.pfs.entity.CusFinProData;
import app.component.pfs.entity.CusFinRatioData;

@FeignClient("mftcc-platform-factor")
public interface PfsInterfaceFeign {

	@RequestMapping(value = "/pfsInterface/insertCusFicRatioData")
	public void insertCusFicRatioData(@RequestBody CusFinRatioData cusFinRatioData) throws Exception;

	@RequestMapping(value = "/pfsInterface/updateBeginForCusFinCapData")
	public void updateBeginForCusFinCapData(@RequestBody CusFinCapData cusFinCapData) throws Exception;

	@RequestMapping(value = "/pfsInterface/updateBeginForCusFinCashData")
	public void updateBeginForCusFinCashData(@RequestBody CusFinCashData cusFinCashData) throws Exception;

	@RequestMapping(value = "/pfsInterface/updateBeginForCusFinProData")
	public void updateBeginForCusFinProData(@RequestBody CusFinProData cusFinProData) throws Exception;

	@RequestMapping(value = "/pfsInterface/getFinIndicators")
	public Map<String, Double> getFinIndicators(@RequestBody CusFinCapData cusFinCapData) throws Exception;

	@RequestMapping(value = "/pfsInterface/getLstFinIndicators")
	public Map<String, Double> getLstFinIndicators(@RequestBody String cusNo) throws Exception;

	@RequestMapping(value = "/pfsInterface/getFinIndicatorsForProjectEval")
	public Map<String, Double> getFinIndicatorsForProjectEval(@RequestBody String cusNo) throws Exception;

	@RequestMapping(value = "/pfsInterface/getAccRule", method = RequestMethod.POST)
	public String getAccRule() throws Exception;

	@RequestMapping(value = "/pfsInterface/getcusFinForm")
	public CusFinForm getcusFinForm(@RequestBody String formNo) throws Exception;

	@RequestMapping(value = "/pfsInterface/getCusFinCapDataList")
	public List<CusFinCapData> getCusFinCapDataList(@RequestBody CusFinCapData cusFinCapData) throws Exception;

	@RequestMapping(value = "/pfsInterface/getCusFinCashDataList")
	public List<CusFinCashData> getCusFinCashDataList(@RequestBody CusFinCashData cusFinCashData) throws Exception;

	@RequestMapping(value = "/pfsInterface/getCusFinProDataList")
	public List<CusFinProData> getCusFinProDataList(@RequestBody CusFinProData cusFinProData) throws Exception;

	@RequestMapping(value = "/pfsInterface/getCusFinMain")
	public CusFinMain getCusFinMain(@RequestBody CusFinMain cusFinMain) throws Exception;

	@RequestMapping(value = "/pfsInterface/getCusFinFormList")
	public List<CusFinForm> getCusFinFormList(@RequestBody CusFinForm cusFinForm) throws Exception;

	@RequestMapping(value = "/pfsInterface/doCheckFinData")
	public boolean doCheckFinData(@RequestBody CusFinMain cusFinMain, @RequestParam("reportType") String reportType) throws Exception;

	@RequestMapping(value = "/pfsInterface/getAll")
	public List<CusFinMain> getAll(@RequestBody CusFinMain cusFinMain) throws Exception;

	@RequestMapping(value = "/pfsInterface/finCapDataMaps")
	public List<List<String>> finCapDataMaps(@RequestBody Map<String, String> parmMap) throws Exception;

	@RequestMapping(value = "/pfsInterface/finProftDataMaps")
	public List<List<String>> finProftDataMaps(@RequestBody Map<String, String> parmMap) throws Exception;

}
