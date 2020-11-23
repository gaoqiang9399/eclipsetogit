package app.component.app.makepolicymeeting.feign;

import app.component.app.makepolicymeeting.entity.MfMakingMeetingSummaryBus;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface MfMakingMeetingSummaryBusFeign {
	@RequestMapping("/mfMakingMeetingSummaryBus/insert")
	public void insert(@RequestBody MfMakingMeetingSummaryBus mfMakingMeetingSummaryBus) throws Exception;

	@RequestMapping("/mfMakingMeetingSummaryBus/delete")
	public void delete(@RequestBody MfMakingMeetingSummaryBus mfMakingMeetingSummaryBus) throws Exception;

	@RequestMapping("/mfMakingMeetingSummaryBus/update")
	public void update(@RequestBody MfMakingMeetingSummaryBus mfMakingMeetingSummaryBus) throws Exception;

	@RequestMapping("/mfMakingMeetingSummaryBus/getById")
	public MfMakingMeetingSummaryBus getById(@RequestBody MfMakingMeetingSummaryBus mfMakingMeetingSummaryBus) throws Exception;

	@RequestMapping("/mfMakingMeetingSummaryBus/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfMakingMeetingSummaryBus/getListByMeetingId")
    List<MfMakingMeetingSummaryBus> getListByMeetingId(@RequestBody MfMakingMeetingSummaryBus mfMakingMeetingSummaryBus) throws Exception;
}
