package  app.component.collateral.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.MfPleRepoApply;
import app.component.collateral.entity.MfPleRepoApproveHis;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfPleRepoApplyBo.java
* Description:应收账款回购申请业务操作
* @author:LJW
* @Fri May 05 09:49:33 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfPleRepoApplyFeign {
	
	/**
	 * 插入
	 * @author LJW
	 * date 2017-5-5
	 */
	@RequestMapping(value = "/mfPleRepoApply/insert")
	public MfPleRepoApply insert(@RequestBody MfPleRepoApply mfPleRepoApply) throws Exception;
	
	/**
	 * 删除
	 * @author LJW
	 * date 2017-5-5
	 */
	@RequestMapping(value = "/mfPleRepoApply/delete")
	public void delete(@RequestBody MfPleRepoApply mfPleRepoApply) throws Exception;
	
	/**
	 * 更新
	 * @author LJW
	 * date 2017-5-5
	 */
	@RequestMapping(value = "/mfPleRepoApply/update")
	public void update(@RequestBody MfPleRepoApply mfPleRepoApply) throws Exception;
	/**
	 * 
	 * 方法描述： 反转让确认保存
	 * @param mfPleRepoApply
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-5-23 上午11:06:41
	 */
	@RequestMapping(value = "/mfPleRepoApply/updateRepoAffirm")
	public void updateRepoAffirm(@RequestBody MfPleRepoApply mfPleRepoApply) throws Exception;
	
	/**
	 * 单条记录查询
	 * @author LJW
	 * date 2017-5-5
	 */
	@RequestMapping(value = "/mfPleRepoApply/getById")
	public MfPleRepoApply getById(@RequestBody MfPleRepoApply mfPleRepoApply) throws Exception;
	
	/**
	 * 分页查询
	 * @author LJW
	 * date 2017-5-5
	 */
	@RequestMapping(value = "/mfPleRepoApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfPleRepoApply") MfPleRepoApply mfPleRepoApply) throws Exception;
	
	/**
	 * 提交应收账款回购审批
	 * @author LJW
	 * date 2017-5-5
	 */
	@RequestMapping(value = "/mfPleRepoApply/doCommitApprove")
	public Result doCommitApprove(@RequestParam("taskId") String taskId, @RequestParam("id") String id,
						   @RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
						   @RequestParam("transition") String transition, @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser,
						   @RequestBody Map<String, Object> dataMap) throws Exception;
	/**
	 * 
	 * 方法描述： 初始化反转让新增数据
	 * @param appId
	 * @return
	 * @throws Exception
	 * MfPleRepoApply
	 * @author 沈浩兵
	 * @date 2017-5-12 上午10:29:45
	 */
	@RequestMapping(value = "/mfPleRepoApply/getMfPleRepoApplyInit")
	public MfPleRepoApply getMfPleRepoApplyInit(@RequestParam("appId") String appId,@RequestParam("receId") String receId) throws Exception;
	
	/**
	 * 
	 * 方法描述： 获得反转让历史
	 * @param mfPleRepoApply
	 * @return
	 * @throws Exception
	 * List<MfPleRepoApply>
	 * @author 沈浩兵
	 * @date 2017-5-23 下午5:12:37
	 */
	@RequestMapping(value = "/mfPleRepoApply/getPleRepoList")
	public List<MfPleRepoApply> getPleRepoList(@RequestBody MfPleRepoApply mfPleRepoApply) throws Exception;
	
}
