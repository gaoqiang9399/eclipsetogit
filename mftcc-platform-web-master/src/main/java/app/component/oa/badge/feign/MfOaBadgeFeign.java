package app.component.oa.badge.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.oa.badge.entity.MfOaBadge;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaBadgeBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jun 01 09:00:01 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfOaBadgeFeign {
	@RequestMapping(value = "/mfOaBadge/insert",produces = "text/html;charset=UTF-8")
	public String insert(@RequestBody MfOaBadge mfOaBadge) throws Exception;

	@RequestMapping(value = "/mfOaBadge/delete")
	public void delete(@RequestBody MfOaBadge mfOaBadge) throws Exception;

	@RequestMapping(value = "/mfOaBadge/update")
	public void update(@RequestBody MfOaBadge mfOaBadge) throws Exception;

	@RequestMapping(value = "/mfOaBadge/getById")
	public MfOaBadge getById(@RequestBody MfOaBadge mfOaBadge) throws Exception;

	@RequestMapping(value = "/mfOaBadge/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	// 管理界面
	@RequestMapping(value = "/mfOaBadge/findByPageManage")
	public Ipage findByPageManage(@RequestBody Ipage ipage) throws Exception;

	// 审批提交处理
	//参数 taskId,  badgeno,  opinionType,  approvalOpinion, transition,  regNo,  nextUser, MfOaBadge ,  badgeNodeType
	@RequestMapping(value = "/mfOaBadge/updateForSubmit")
	public Result updateForSubmit(@RequestParam("taskId") String taskId,@RequestParam("badgeno") String badgeno,
			@RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition") String transition,
			@RequestParam("regNo")String regNo,@RequestParam("nextUser") String nextUser,@RequestBody MfOaBadge mfOaBadge,@RequestParam("badgeNodeType")String badgeNodeType) throws Exception;

	@RequestMapping(value = "/mfOaBadge/submitProcess")
	public String submitProcess(@RequestBody MfOaBadge mfOaBadge) throws Exception;

	/**
	 * 
	 * @param mfOaBadge
	 * @return 成功返回"1234" 不成功返回原因
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/mfOaBadge/confirm")
	public String confirm(@RequestBody MfOaBadge mfOaBadge) throws Exception;

	/**
	 * 返回数量
	 * 
	 */
	@RequestMapping(value = "/mfOaBadge/getBadgeCount")
	public Integer getBadgeCount() throws Exception;

	/**
	 * 处理出章操作
	 */
	@RequestMapping(value = "/mfOaBadge/discussOutBadge")
	public void discussOutBadge(@RequestBody MfOaBadge mfOaBadge) throws Exception;

	@RequestMapping(value = "/mfOaBadge/discussRevertBadge")
	public void discussRevertBadge(@RequestBody MfOaBadge mfOaBadge) throws Exception;

	/**
	 * 查询用章管理相关数量
	 */
	@RequestMapping(value = "/mfOaBadge/getBadgeCountManage")
	public int getBadgeCountManage() throws Exception;
}