package app.component.app.makepolicymeeting.feign;

import app.component.app.makepolicymeeting.entity.MfMakingMeetingSummary;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface MfMakingMeetingSummaryFeign {
	@RequestMapping("/mfMakingMeetingSummary/insert")
	public void insert(@RequestBody MfMakingMeetingSummary mfMakingMeetingSummary) throws Exception;

	@RequestMapping("/mfMakingMeetingSummary/delete")
	public void delete(@RequestBody MfMakingMeetingSummary mfMakingMeetingSummary) throws Exception;

	@RequestMapping("/mfMakingMeetingSummary/update")
	public void update(@RequestBody MfMakingMeetingSummary mfMakingMeetingSummary) throws Exception;

	@RequestMapping("/mfMakingMeetingSummary/getById")
	public MfMakingMeetingSummary getById(@RequestBody MfMakingMeetingSummary mfMakingMeetingSummary) throws Exception;

	@RequestMapping("/mfMakingMeetingSummary/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfMakingMeetingSummary/getMeetingTimeList")
    List<String> getMeetingTimeList()throws Exception;
}
