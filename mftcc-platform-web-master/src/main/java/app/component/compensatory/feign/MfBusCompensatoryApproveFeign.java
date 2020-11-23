package  app.component.compensatory.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.compensatory.entity.MfBusCompensatoryApprove;
import app.component.wkf.entity.Result;

/**
 * Title: MfBusCompensatoryApproveFeign.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 09 19:00:11 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusCompensatoryApproveFeign{
	
	@RequestMapping("/mfCompensatoryApprove/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("transition") String nextUser,
			@RequestBody Map<String, Object> formDataMap) throws Exception;

	@RequestMapping("/mfCompensatoryApprove/getById")
	public MfBusCompensatoryApprove getById(@RequestBody MfBusCompensatoryApprove mfBusCompensatoryApprove);
}