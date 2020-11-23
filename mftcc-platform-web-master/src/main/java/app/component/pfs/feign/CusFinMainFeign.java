package app.component.pfs.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pfs.entity.CusFinMain;
import app.util.toolkit.Ipage;

/**
 * 财务主表业务控制操作 Title: CusFinMainBoImplImpl.java Description:
 * 
 * @author:LJW
 * @Mon May 09 05:23:55 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinMainFeign {

	@RequestMapping(value = "/cusFinMain/insert")
	public void insert(@RequestBody CusFinMain cusFinMain) throws Exception;

	@RequestMapping(value = "/cusFinMain/delete")
	public void delete(@RequestBody CusFinMain cusFinMain) throws Exception;

	@RequestMapping(value = "/cusFinMain/update")
	public void update(@RequestBody CusFinMain cusFinMain) throws Exception;

	@RequestMapping(value = "/cusFinMain/getById")
	public CusFinMain getById(@RequestBody CusFinMain cusFinMain) throws Exception;

	@RequestMapping(value = "/cusFinMain/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("cusFinMain") CusFinMain cusFinMain) throws Exception;

	@RequestMapping(value = "/cusFinMain/getAll")
	public List<CusFinMain> getAll(@RequestBody CusFinMain cusFinMain) throws Exception;

	@RequestMapping(value = "/cusFinMain/insertMoreExcel")
	public void insertMoreExcel(@RequestBody CusFinMain cusFinMain, @RequestParam("allPath") String allPath,
			@RequestParam("teplateType") String teplateType) throws Exception;
	@RequestMapping(value = "/cusFinMain/insertMoreExcelNew")
	public void insertMoreExcelNew(@RequestBody Map<String,Object> beans,@RequestParam("teplateType") String teplateType) throws Exception;

	@RequestMapping(value = "/cusFinMain/doExportExcelModel")
	public Map<String,Object> doExportExcelModel( @RequestParam("downType") String downType, @RequestParam("cusNo") String cusNo) throws Exception;

	@RequestMapping(value = "/cusFinMain/doPersonExportExcelModel")
	public Map<String,Object> doPersonExportExcelModel( @RequestParam("downType") String downType, @RequestParam("cusNo") String cusNo) throws Exception;

	@RequestMapping(value = "/cusFinMain/updateReportConfirm")
	public Map<String,Object> updateReportConfirm(@RequestBody CusFinMain cusFinMain) throws Exception;

	@RequestMapping(value = "/cusFinMain/getForFinIndicators")
	public List<CusFinMain> getForFinIndicators(@RequestBody CusFinMain cusFinMain) throws Exception;

	@RequestMapping(value = "/cusFinMain/getFinIndicatorsForProjectEval")
	public List<CusFinMain> getFinIndicatorsForProjectEval( @RequestParam("cusNo") String cusNo, @RequestParam("finRptYear") String finRptYear,
			@RequestParam("accRule") String accRule) throws Exception;

	@RequestMapping(value = "/cusFinMain/getListByAccRule")
	public List<CusFinMain> getListByAccRule(@RequestBody CusFinMain cusFinMain) throws Exception;

	@RequestMapping(value = "/cusFinMain/doCheckFinData")
	public boolean doCheckFinData(@RequestBody CusFinMain cusFinMain, @RequestParam("reportType") String reportType) throws Exception;
	/**
	 * 
	 * @param codeColumn
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/cusFinMain/doCheckFinParam",produces="text/html;charset=UTF-8")
	public String doCheckFinParam(@RequestParam("codeColumn")String codeColumn ,@RequestBody Map<String, Object> map) throws ServiceException;

	@RequestMapping(value = "/cusFinMain/getCusFinMainList")
	public List<CusFinMain> getCusFinMainList(@RequestBody CusFinMain cusFinMain) throws Exception;

	@RequestMapping(value = "/cusFinMain/maxMinFinRptDate")
	public Map<String, String> maxMinFinRptDate(@RequestBody CusFinMain cusFinMain) throws Exception;


	/**

	 *@描述 获得财务报表多期对比数据

	 *@参数

	 *@返回值

	 *@创建人  shenhaobing

	 *@创建时间  2019/1/16

	 *@修改人和其它信息

	 */
	@RequestMapping(value = "/cusFinMain/multiPeriodComparisonView")
	public Map<String, Object> multiPeriodComparisonView(@RequestBody CusFinMain cusFinMain) throws Exception;
}
