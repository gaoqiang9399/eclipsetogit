package app.component.pfs.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinModel;
import app.component.pfs.entity.CusFinValid;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface CusFinValidFeign {

	@RequestMapping(value = "/cusFinValid/insert")
	public void insert(@RequestBody CusFinValid cusFinValid, @RequestParam("leftAndRightExp") String leftAndRightExp) throws Exception;

	@RequestMapping(value = "/cusFinValid/getCount")
	public int getCount(@RequestBody CusFinValid cusFinValid) throws Exception;

	@RequestMapping(value = "/cusFinValid/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg, @RequestParam("cusFinValid") CusFinValid cusFinValid) throws Exception;

	@RequestMapping(value = "/cusFinValid/update")
	public void update(@RequestBody CusFinValid cusFinValid, @RequestParam("leftAndRightExp") String leftAndRightExp) throws Exception;

	@RequestMapping(value = "/cusFinValid/delete")
	public void delete(@RequestBody String validNo) throws Exception;

	@RequestMapping(value = "/cusFinValid/getById")
	public CusFinValid getById(@RequestBody String validNo) throws Exception;

	@RequestMapping(value = "/cusFinValid/getCusFinModel")
	public List<CusFinModel> getCusFinModel(@RequestParam("reportType") String reportType, @RequestParam("accRule") String accRule)
			throws Exception;

	@RequestMapping(value = "/cusFinValid/getForBalanceCheck")
	public List<CusFinValid> getForBalanceCheck(@RequestParam("report") String report, @RequestParam("accRule") String accRule)
			throws Exception;

	@RequestMapping(value = "/cusFinValid/getCusFinModel1")
	public void getCusFinModel(@RequestParam("reportType") String reportType, @RequestParam("accRule") String accRule,
			@RequestBody List<CusFinModel> capList, @RequestParam("cashList") List<CusFinModel> cashList,
			@RequestParam("payList") List<CusFinModel> payList) throws Exception;

}
