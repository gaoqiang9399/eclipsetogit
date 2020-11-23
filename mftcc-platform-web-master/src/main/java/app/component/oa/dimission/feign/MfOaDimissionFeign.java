package app.component.oa.dimission.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.oa.dimission.entity.MfOaDimission;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaDimissionFeign {
	@RequestMapping("/MfOaDimission/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfOaDimission/insert")
	public void insert(@RequestBody MfOaDimission mfOaDimission) throws Exception;

	@RequestMapping("/MfOaDimission/submitProcess")
	public MfOaDimission submitProcess(@RequestBody MfOaDimission mfOaDimission) throws Exception;

	@RequestMapping("/MfOaDimission/getById")
	public MfOaDimission getById(@RequestBody MfOaDimission mfOaDimission) throws Exception;

	@RequestMapping("/MfOaDimission/delete")
	public void delete(@RequestBody MfOaDimission mfOaDimission) throws Exception;

	@RequestMapping("/MfOaDimission/update")
	public void update(@RequestBody MfOaDimission mfOaDimission) throws Exception;

	@RequestMapping("/MfOaDimission/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/MfOaDimission/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("transition") String nextUser,
			@RequestBody Map<String, Object> formDataMap) throws Exception;
}
