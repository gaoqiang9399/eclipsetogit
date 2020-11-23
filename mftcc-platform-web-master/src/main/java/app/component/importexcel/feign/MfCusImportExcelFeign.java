package app.component.importexcel.feign;

import java.util.List;
import java.util.Map;

import app.component.importexcel.entity.MfCusImportExcelCensus;
import app.component.importexcel.entity.MfCusImportExcelHis;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient("mftcc-platform-factor")
public interface MfCusImportExcelFeign {
	
	@RequestMapping(value = "/mfCusImportExcel/excelToObj")	
	public Map<String,Object> excelToObj(@RequestBody MfCusImportExcelHis mfCusImportExcelHis) throws Exception;
	
	@RequestMapping(value = "/mfCusImportExcel/excelToObjBus")	
	public Map<String,Object> excelToObjBus(@RequestBody String destFileName) throws Exception;

	@RequestMapping(value = "/mfCusImportExcel/updateHis")
	public void updateHis(@RequestBody MfCusImportExcelHis mfCusImportExcelHis)  throws Exception;

	@RequestMapping(value = "/mfCusImportExcel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage)  throws Exception;

	@RequestMapping(value = "/mfCusImportExcel/getById")
	public MfCusImportExcelHis getById(@RequestBody MfCusImportExcelHis mfCusImportExcelHis)  throws Exception;

	@RequestMapping(value = "/mfCusImportExcel/saveByTemporary")
	public Map<String,Object> saveByTemporary(@RequestBody MfCusImportExcelHis mfCusImportExcelHis) throws Exception;

	@RequestMapping(value = "/mfCusImportExcel/checkByTemporary")
	public Map<String,Object> checkByTemporary(@RequestBody MfCusImportExcelHis mfCusImportExcelHis) throws Exception;

	@RequestMapping(value = "/mfCusImportExcel/getDownData")
	public List<MfCusImportExcelCensus> getDownData(@RequestBody MfCusImportExcelHis mfCusImportExcelHis) throws Exception;

	@RequestMapping(value = "/mfCusImportExcel/getImportExcelCensusList")
	public List<MfCusImportExcelCensus> getImportExcelCensusList(@RequestBody MfCusImportExcelCensus mfCusImportExcelCensus)  throws Exception;

}
