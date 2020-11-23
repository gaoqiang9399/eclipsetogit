package  app.component.collateral.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.entity.MfPleRepoApproveHis;
import app.util.toolkit.Ipage;

/**
* Title: MfPleRepoApproveHisBo.java
* Description:审批业务控制
* @author:LJW
* @Fri May 05 15:09:54 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfPleRepoApproveHisFeign {
	
	/**
	 * 插入
	 * @author LJW
	 * date 2017-5-5
	 */
	@RequestMapping(value = "/mfPleRepoApproveHis/insert")
	public void insert(@RequestBody MfPleRepoApproveHis mfPleRepoApproveHis) throws Exception;
	
	/**
	 * 删除
	 * @author LJW
	 * date 2017-5-5
	 */
	@RequestMapping(value = "/mfPleRepoApproveHis/delete")
	public void delete(@RequestBody MfPleRepoApproveHis mfPleRepoApproveHis) throws Exception;
	
	/**
	 * 更新
	 * @author LJW
	 * date 2017-5-5
	 */
	@RequestMapping(value = "/mfPleRepoApproveHis/update")
	public void update(@RequestBody MfPleRepoApproveHis mfPleRepoApproveHis) throws Exception;
	
	/**
	 * 查询单条记录
	 * @author LJW
	 * date 2017-5-5
	 */
	@RequestMapping(value = "/mfPleRepoApproveHis/getById")
	public MfPleRepoApproveHis getById(@RequestBody MfPleRepoApproveHis mfPleRepoApproveHis) throws Exception;
	
	/**
	 * 分页查询
	 * @author LJW
	 * date 2017-5-5
	 */
	@RequestMapping(value = "/mfPleRepoApproveHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfPleRepoApproveHis") MfPleRepoApproveHis mfPleRepoApproveHis) throws Exception;
	
}
