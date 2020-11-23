package app.component.oa.mattersreported.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.oa.mattersreported.entity.MfOaMattersReported;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaMattersReportedFeign {
	@RequestMapping("/MfOaMattersReported/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfOaMattersReported/insert")
	public void insert(@RequestBody MfOaMattersReported mfOaMattersReported) throws Exception;

	@RequestMapping("/MfOaMattersReported/submitProcess")
	public MfOaMattersReported submitProcess(@RequestBody MfOaMattersReported mfOaMattersReported) throws Exception;

	@RequestMapping("/MfOaMattersReported/getById")
	public MfOaMattersReported getById(@RequestBody MfOaMattersReported mfOaMattersReported) throws Exception;

	@RequestMapping("/MfOaMattersReported/delete")
	public void delete(@RequestBody MfOaMattersReported mfOaMattersReported) throws Exception;

	@RequestMapping("/MfOaMattersReported/update")
	public void update(@RequestBody MfOaMattersReported mfOaMattersReported) throws Exception;

	@RequestMapping("/MfOaMattersReported/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/MfOaMattersReported/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("nextUser") String nextUser,
			@RequestBody Map<String, Object> formDataMap) throws Exception;
}
