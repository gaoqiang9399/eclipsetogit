package app.component.pfs.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinFormula;
import app.component.pfs.entity.CusFinModel;
import app.component.pfs.entity.CusFinParm;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface CusFinParmFeign {

	@RequestMapping(value = "/cusFinParm/getFinParmList")
	public List<CusFinParm> getFinParmList(@RequestParam("reportType") String reportType, @RequestParam("accRule")String accRule) throws Exception;

	@RequestMapping(value = "/cusFinParm/getFinModelList")
	public List<CusFinModel> getFinModelList(@RequestParam("reportType") String reportRype, @RequestParam("accRule")String accRule) throws Exception;

	@RequestMapping(value = "/cusFinParm/getById")
	public CusFinParm getById(@RequestBody CusFinParm cusFinParm) throws Exception;

	@RequestMapping(value = "/cusFinParm/del")
	public void del(@RequestBody CusFinParm cusFinParm) throws Exception;

	@RequestMapping(value = "/cusFinParm/insert")
	public CusFinParm insert(@RequestBody Map<String, Object> paramMap)
			throws Exception;

	@RequestMapping(value = "/cusFinParm/update")
	public void update(@RequestBody CusFinParm cusFinParm) throws Exception;

	@RequestMapping(value = "/cusFinParm/update1")
	public CusFinParm update1(@RequestBody Map<String, Object> paramMap)
			throws Exception;

	@RequestMapping(value = "/cusFinParm/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg) throws Exception;

	@RequestMapping(value = "/cusFinParm/getList")
	public List<CusFinParm> getList(@RequestBody CusFinParm cusFinParm) throws Exception;

	@RequestMapping(value = "/cusFinParm/insertParmByExcel")
	public void insertParmByExcel(@RequestBody String allPath) throws Exception;

}