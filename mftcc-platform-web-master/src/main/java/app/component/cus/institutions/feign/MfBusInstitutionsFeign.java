package app.component.cus.institutions.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.cus.institutions.entity.MfBusInstitutions;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfBusInstitutionsFeign {
	@RequestMapping("/mfBusInstitutions/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfBusInstitutions/insert")
	public void insert(@RequestBody MfBusInstitutions mfBusInstitutions) throws Exception;

	@RequestMapping("/mfBusInstitutions/submitProcess")
	public MfBusInstitutions submitProcess(@RequestBody MfBusInstitutions mfBusInstitutions) throws Exception;

	@RequestMapping("/mfBusInstitutions/getById")
	public MfBusInstitutions getById(@RequestBody MfBusInstitutions mfBusInstitutions) throws Exception;

	@RequestMapping("/mfBusInstitutions/delete")
	public void delete(@RequestBody MfBusInstitutions mfBusInstitutions) throws Exception;

	@RequestMapping("/mfBusInstitutions/update")
	public void update(@RequestBody MfBusInstitutions mfBusInstitutions) throws Exception;

	@RequestMapping("/mfBusInstitutions/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/mfBusInstitutions/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("transition") String nextUser,
			@RequestBody Map<String, Object> formDataMap) throws Exception;

	@RequestMapping("/mfBusInstitutions/getAgenciesDataSourse")
	public Ipage getAgenciesDataSourse(@RequestBody Ipage ipage) throws Exception;
}
