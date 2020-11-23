package app.component.oa.leave.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.oa.leave.entity.MfOaLeave;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfOaLeaveFeign {
	@RequestMapping("/MfOaLeave/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfOaLeave/insert")
	public void insert(@RequestBody MfOaLeave mfOaLeave) throws Exception;

	@RequestMapping("/MfOaLeave/getById")
	public MfOaLeave getById(@RequestBody MfOaLeave mfOaLeave) throws Exception;

	@RequestMapping("/MfOaLeave/delete")
	public void delete(@RequestBody MfOaLeave mfOaLeave) throws Exception;

	@RequestMapping("/MfOaLeave/update")
	public void update(@RequestBody MfOaLeave mfOaLeave) throws Exception;

	@RequestMapping("/MfOaLeave/insertForApp")
	public MfOaLeave insertForApp(@RequestBody MfOaLeave mfOaLeave) throws Exception;

	@RequestMapping("/MfOaLeave/findAllList")
	public List<MfOaLeave> findAllList(@RequestBody MfOaLeave mfOaLeave) throws Exception;

	@RequestMapping("/MfOaLeave/insertForSubmit")
	public MfOaLeave insertForSubmit(@RequestBody MfOaLeave mfOaLeave) throws Exception;

	@RequestMapping("/MfOaLeave/insertForSubmitApp")
	public MfOaLeave insertForSubmitApp(@RequestBody MfOaLeave mfOaLeave) throws Exception;

	@RequestMapping("/MfOaLeave/deletForSubmit")
	public MfOaLeave deletForSubmit(@RequestBody MfOaLeave mfOaLeave) throws Exception;

	@RequestMapping("/MfOaLeave/updateForSubmit1")
	public Result updateForSubmit1(@RequestParam("taskId") String taskId,@RequestParam("leaveNo") String leaveNo,@RequestParam("opinionType") String opinionType,@RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("transition") String transition,@RequestParam("regNo") String regNo,@RequestParam("nextUser") String nextUser,@RequestBody Map<String, Object> dataMap)
			throws Exception;

	@RequestMapping("/MfOaLeave/getTotalById")
	public Map<String, Object> getTotalById(String opNo) throws Exception;

	@RequestMapping("/MfOaLeave/urgedDo")
	public void urgedDo(@RequestBody MfOaLeave mfOaLeave) throws Exception;

	@RequestMapping("/MfOaLeave/getOaLeaveCount")
	public int getOaLeaveCount(@RequestBody MfOaLeave mfOaLeave) throws Exception;
}
