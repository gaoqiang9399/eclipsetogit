package app.component.oa.opencount.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.oa.opencount.entity.MfOaOpening;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaOpeningBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 17 15:31:41 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfOaOpeningFeign {
	@RequestMapping(value = "/mfOaOpening/insert")
	public MfOaOpening insert(@RequestBody MfOaOpening mfOaOpening) throws Exception;

	@RequestMapping(value = "/mfOaOpening/delete")
	public void delete(@RequestBody MfOaOpening mfOaOpening) throws Exception;

	@RequestMapping(value = "/mfOaOpening/update")
	public void update(@RequestBody MfOaOpening mfOaOpening) throws Exception;

	@RequestMapping(value = "/mfOaOpening/getById")
	public MfOaOpening getById(@RequestBody MfOaOpening mfOaOpening) throws Exception;

	@RequestMapping(value = "/mfOaOpening/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	// TODO
	@RequestMapping(value = "/mfOaOpening/updateForSubmit")
	public Result updateForSubmit(@RequestParam("taskId") String taskId, @RequestParam("badgeno") String badgeno,
			@RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("transition") String transition, @RequestParam("regNo") String regNo,
			@RequestParam("nextUser") String nextUser, @RequestBody MfOaOpening mfOaBadge,@RequestParam("badgeNodeType") String badgeNodeType);

	@RequestMapping(value = "/mfOaOpening/updateOpen")
	public void updateOpen(@RequestBody MfOaOpening mfOaOpening) throws Exception;

	@RequestMapping(value = "/mfOaOpening/getCount")
	public int getCount() throws Exception;
}
