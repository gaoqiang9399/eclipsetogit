package app.component.app.makepolicymeeting.feign;

import app.component.app.makepolicymeeting.entity.MfReviewMember;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface MfReviewMemberFeign {
	@RequestMapping("/mfReviewMember/insert")
	public void insert(@RequestBody MfReviewMember mfReviewMember) throws Exception;

	@RequestMapping("/mfReviewMember/delete")
	public void delete(@RequestBody MfReviewMember mfReviewMember) throws Exception;

	@RequestMapping("/mfReviewMember/update")
	public void update(@RequestBody MfReviewMember mfReviewMember) throws Exception;

	@RequestMapping("/mfReviewMember/getById")
	public MfReviewMember getById(@RequestBody MfReviewMember mfReviewMember) throws Exception;

	@RequestMapping("/mfReviewMember/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfReviewMember/getAllByMakePolicyMeetingId")
	List<MfReviewMember> getAllByMakePolicyMeetingId(@RequestBody MfReviewMember mfReviewMember) throws Exception;
}
