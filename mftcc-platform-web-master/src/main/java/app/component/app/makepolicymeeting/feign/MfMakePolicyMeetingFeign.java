package app.component.app.makepolicymeeting.feign;

import app.component.app.makepolicymeeting.entity.MfMakePolicyMeeting;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import feign.Param;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfMakePolicyMeetingFeign {
	@RequestMapping("/mfMakePolicyMeeting/insert")
	public void insert(@RequestBody MfMakePolicyMeeting mfMakePolicyMeeting) throws Exception;

	@RequestMapping("/mfMakePolicyMeeting/delete")
	public void delete(@RequestBody MfMakePolicyMeeting mfMakePolicyMeeting) throws Exception;

	@RequestMapping("/mfMakePolicyMeeting/update")
	public void update(@RequestBody MfMakePolicyMeeting mfMakePolicyMeeting) throws Exception;

	@RequestMapping("/mfMakePolicyMeeting/getById")
	public MfMakePolicyMeeting getById(@RequestBody MfMakePolicyMeeting mfMakePolicyMeeting) throws Exception;

	@RequestMapping("/mfMakePolicyMeeting/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfMakePolicyMeeting/getLastedByMakePolicyMeetingAppId")
	MfMakePolicyMeeting getLastedByMakePolicyMeetingAppId(@RequestBody MfMakePolicyMeeting mfMakePolicyMeeting)throws Exception ;

	@RequestMapping("/mfMakePolicyMeeting/doCommit")
	Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("nextUser") String nextUser, @RequestBody Map<String,Object> formDataMap)throws Exception ;

	@RequestMapping("/mfMakePolicyMeeting/getListByAppId")
    List<MfMakePolicyMeeting> getListByAppId(@RequestBody  MfMakePolicyMeeting mfMakePolicyMeeting)throws Exception ;
}
