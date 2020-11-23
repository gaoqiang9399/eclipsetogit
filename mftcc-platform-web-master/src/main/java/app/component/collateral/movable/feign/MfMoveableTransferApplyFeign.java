package  app.component.collateral.movable.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.entity.PledgeBaseInfoBill;
import app.component.collateral.movable.entity.MfMoveableTransferApply;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableTransferApplyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Jun 09 16:32:52 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableTransferApplyFeign {
	
	@RequestMapping(value = "/mfMoveableTransferApply/insert")
	public MfMoveableTransferApply insert(@RequestBody MfMoveableTransferApply mfMoveableTransferApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableTransferApply/delete")
	public void delete(@RequestBody MfMoveableTransferApply mfMoveableTransferApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableTransferApply/update")
	public void update(@RequestBody MfMoveableTransferApply mfMoveableTransferApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableTransferApply/getById")
	public MfMoveableTransferApply getById(@RequestBody MfMoveableTransferApply mfMoveableTransferApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableTransferApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableTransferApply") MfMoveableTransferApply mfMoveableTransferApply) throws Exception;
	/**
	 * 
	 * 方法描述： 初始化移库申请新增实体属性
	 * @param busPleId
	 * @param appId
	 * @return
	 * @throws Exception
	 * MfMoveableTransferApply
	 * @author 沈浩兵
	 * @date 2017-6-9 下午5:11:02
	 */
	@RequestMapping(value = "/mfMoveableTransferApply/initTransferApply")
	public MfMoveableTransferApply initTransferApply(@RequestParam("busPleId") String busPleId,@RequestParam("appId") String appId) throws Exception;
	/**
	 * 
	 * 方法描述： 移库审批提交
	 * @param taskId
	 * @param appId
	 * @param transferId
	 * @param opinionType
	 * @param approvalOpinion
	 * @param transition
	 * @param opNo
	 * @param nextUser
	 * @param mfMoveableTransferApply
	 * @param mfMoveableTransferApproHis
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2017-6-9 下午7:02:35
	 */
	@RequestMapping(value = "/mfMoveableTransferApply/doCommit")
	public Result doCommit(@RequestBody Map<String,Object> dataMap) throws Exception;
	/**
	 * 
	 * 方法描述： 获得移库申请选择的货物明细
	 * @return
	 * @throws Exception
	 * List<PledgeBaseInfoBill>
	 * @author 沈浩兵
	 * @date 2017-6-15 上午8:39:00
	 */
	@RequestMapping(value = "/mfMoveableTransferApply/getBillListByselected")
	public List<PledgeBaseInfoBill> getBillListByselected(@RequestParam("pledgeDetail") String pledgeDetail) throws Exception;
}
