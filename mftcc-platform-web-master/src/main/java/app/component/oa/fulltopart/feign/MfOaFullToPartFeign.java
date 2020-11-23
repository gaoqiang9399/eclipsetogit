package app.component.oa.fulltopart.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.oa.fulltopart.entity.MfOaFullToPart;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaFullToPartFeign {
	@RequestMapping("/MfOaFullToPart/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfOaFullToPart/insert")
	public void insert(@RequestBody MfOaFullToPart mfOaFullToPart) throws Exception;

	@RequestMapping("/MfOaFullToPart/submitProcess")
	public MfOaFullToPart submitProcess(@RequestBody MfOaFullToPart mfOaFullToPart) throws Exception;

	@RequestMapping("/MfOaFullToPart/getById")
	public MfOaFullToPart getById(@RequestBody MfOaFullToPart mfOaFullToPart) throws Exception;

	@RequestMapping("/MfOaFullToPart/delete")
	public void delete(@RequestBody MfOaFullToPart mfOaFullToPart) throws Exception;

	@RequestMapping("/MfOaFullToPart/update")
	public void update(@RequestBody MfOaFullToPart mfOaFullToPart) throws Exception;

	@RequestMapping("/MfOaFullToPart/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/MfOaFullToPart/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("nextUser") String nextUser,
			@RequestBody Map<String, Object> formDataMap) throws Exception;
}
