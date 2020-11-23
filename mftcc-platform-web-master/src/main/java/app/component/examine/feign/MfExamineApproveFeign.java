package  app.component.examine.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.examine.entity.MfExamineApprove;
import app.util.toolkit.Ipage;

/**
* Title: MfExamineApproveBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jul 27 18:21:50 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfExamineApproveFeign {
	@RequestMapping("/mfExamineApprove/insert")
	public void insert(@RequestBody MfExamineApprove mfExamineApprove) throws Exception;
	@RequestMapping("/mfExamineApprove/delete")
	public void delete(@RequestBody MfExamineApprove mfExamineApprove) throws Exception;
	@RequestMapping("/mfExamineApprove/update")
	public void update(@RequestBody MfExamineApprove mfExamineApprove) throws Exception;
	@RequestMapping("/mfExamineApprove/getById")
	public MfExamineApprove getById(@RequestBody MfExamineApprove mfExamineApprove) throws Exception;
	@RequestMapping("/mfExamineApprove/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
