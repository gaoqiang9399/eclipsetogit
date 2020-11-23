package app.component.oa.human.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.oa.human.entity.MfOaHumanResources;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaHumanResourcesFeign {
	@RequestMapping("/mfOaHumanResources/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfOaHumanResources/insert")
	public void insert(@RequestBody MfOaHumanResources mfOaHumanResources) throws Exception;

	@RequestMapping("/mfOaHumanResources/submitProcess")
	public MfOaHumanResources submitProcess(@RequestBody MfOaHumanResources mfOaHumanResources) throws Exception;

	@RequestMapping("/mfOaHumanResources/getById")
	public MfOaHumanResources getById(@RequestBody MfOaHumanResources mfOaHumanResources) throws Exception;

	@RequestMapping("/mfOaHumanResources/delete")
	public void delete(@RequestBody MfOaHumanResources mfOaHumanResources) throws Exception;

	@RequestMapping("/mfOaHumanResources/update")
	public void update(@RequestBody MfOaHumanResources mfOaHumanResources) throws Exception;

	@RequestMapping("/mfOaHumanResources/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/mfOaHumanResources/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition,
			@RequestParam("nextUser") String nextUser,
			@RequestBody Map<String, Object> formDataMap) throws Exception;
}
