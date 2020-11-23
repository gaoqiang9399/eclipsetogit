package app.component.oa.adjustment.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.oa.adjustment.entity.MfOaAdjustment;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaAdjustmentFeign {
	@RequestMapping("/MfOaAdjustment/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfOaAdjustment/insert")
	public void insert(@RequestBody MfOaAdjustment mfOaAdjustment) throws Exception;

	@RequestMapping("/MfOaAdjustment/submitProcess")
	public MfOaAdjustment submitProcess(@RequestBody MfOaAdjustment mfOaAdjustment) throws Exception;

	@RequestMapping("/MfOaAdjustment/getById")
	public MfOaAdjustment getById(@RequestBody MfOaAdjustment mfOaAdjustment) throws Exception;

	@RequestMapping("/MfOaAdjustment/delete")
	public void delete(@RequestBody MfOaAdjustment mfOaAdjustment) throws Exception;

	@RequestMapping("/MfOaAdjustment/update")
	public void update(@RequestBody MfOaAdjustment mfOaAdjustment);

	@RequestMapping("/MfOaAdjustment/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/MfOaAdjustment/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("nextUser") String nextUser,
			 @RequestBody Map<String, Object> formDataMap) throws Exception;

}
