package app.component.oa.trainingneeds.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.oa.trainingneeds.entity.MfOaTrainingNeeds;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaTrainingNeedsFeign {
	@RequestMapping("/MfOaTrainingNeeds/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfOaTrainingNeeds/insert")
	public void insert(@RequestBody MfOaTrainingNeeds mfOaTrainingNeeds) throws Exception;

	@RequestMapping("/MfOaTrainingNeeds/submitProcess")
	public MfOaTrainingNeeds submitProcess(@RequestBody MfOaTrainingNeeds mfOaTrainingNeeds) throws Exception;

	@RequestMapping("/MfOaTrainingNeeds/getById")
	public MfOaTrainingNeeds getById(@RequestBody MfOaTrainingNeeds mfOaTrainingNeeds) throws Exception;

	@RequestMapping("/MfOaTrainingNeeds/delete")
	public void delete(@RequestBody MfOaTrainingNeeds mfOaTrainingNeeds) throws Exception;

	@RequestMapping("/MfOaTrainingNeeds/update")
	public void update(@RequestBody MfOaTrainingNeeds mfOaTrainingNeeds) throws Exception;

	@RequestMapping("/MfOaTrainingNeeds/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/MfOaTrainingNeeds/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("nextUser") String nextUser,@RequestBody Map<String, Object> formDataMap) throws Exception;
}
