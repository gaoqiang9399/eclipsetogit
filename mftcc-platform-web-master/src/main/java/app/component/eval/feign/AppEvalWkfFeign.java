package app.component.eval.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.eval.entity.AppEval;
import app.component.wkf.entity.Result;

@FeignClient("mftcc-platform-factor")
public interface AppEvalWkfFeign {
	/***
	 * 业务提交
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/appEvalWkf/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("appNo") String appNo,@RequestParam("opinionType") String opinionType,@RequestParam("approvalOpinion")  String approvalOpinion,@RequestParam("transition") String transition,@RequestParam("opNo")  String opNo,@RequestParam("nextUser")  String nextUser,@RequestBody Map<String,Object> dataMap) throws Exception;
}
