package  app.component.recourse.feign;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.recourse.entity.MfBusRecourseApprove;
import app.component.wkf.entity.Result;

/**
 * Title: MfBusRecourseApproveAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 16 19:41:30 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusRecourseApproveFeign{
	
	@RequestMapping("/mfBusRecourseApprove/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("transition") String nextUser,
			@RequestBody Map<String, Object> formDataMap) throws Exception;

	@RequestMapping("/mfBusRecourseApprove/getById")
	public MfBusRecourseApprove getById(@RequestBody MfBusRecourseApprove mfBusRecourseApprove);
}
