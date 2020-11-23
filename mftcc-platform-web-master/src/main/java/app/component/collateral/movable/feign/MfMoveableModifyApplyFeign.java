package  app.component.collateral.movable.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.PledgeBaseInfoBill;
import app.component.collateral.movable.entity.MfMoveableModifyApply;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableModifyApplyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Jun 12 16:12:17 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableModifyApplyFeign {
	
	@RequestMapping(value = "/mfMoveableModifyApply/insert")
	public MfMoveableModifyApply insert(@RequestBody MfMoveableModifyApply mfMoveableModifyApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableModifyApply/delete")
	public void delete(@RequestBody MfMoveableModifyApply mfMoveableModifyApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableModifyApply/update")
	public void update(@RequestBody MfMoveableModifyApply mfMoveableModifyApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableModifyApply/getById")
	public MfMoveableModifyApply getById(@RequestBody MfMoveableModifyApply mfMoveableModifyApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableModifyApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableModifyApply") MfMoveableModifyApply mfMoveableModifyApply) throws Exception;
	/**
	 * 
	 * 方法描述： 初始化调价申请新增实体属性
	 * @param busPleId
	 * @param appId
	 * @return
	 * @throws Exception
	 * MfMoveableModifyApply
	 * @author 姚文豪
	 * @date 2017-6-12 下午5:11:02
	 */
	@RequestMapping(value = "/mfMoveableModifyApply/initTransferApply")
	public MfMoveableModifyApply initTransferApply(@RequestBody String busPleId,@RequestParam("appId") String appId) throws Exception;
	/**
	 * 
	 * 方法描述：调价审批提交
	 * @param taskId
	 * @param appId
	 * @param transferId
	 * @param opinionType
	 * @param approvalOpinion
	 * @param transition
	 * @param opNo
	 * @param nextUser
	 * @param MfMoveableModifyApply
	 * @param mMfMoveableModifyApproHis
	 * @return
	 * @throws Exception
	 * Result
	 * @author ywh
	 * @date 2017-6-9 下午7:02:35
	 */
	@RequestMapping(value = "/mfMoveableModifyApply/doCommit")
	public Result doCommit(@RequestBody Map<String,Object> dataMap) throws Exception;
	/**
	 * 
	 * 方法描述： 根据押品编号获得关联的调价货物明细
	 * @param pledgeBaseInfoBill
	 * @return
	 * @throws ServiceException
	 * List<PledgeBaseInfoBill>
	 * @author ywh
	 * @date 2017-6-14 下午2:31:40
	 */
	@RequestMapping(value = "/mfMoveableModifyApply/getModifyBillListByPledgeNo")
	public List<PledgeBaseInfoBill> getModifyBillListByPledgeNo(@RequestBody MfMoveableModifyApply mfMoveableModifyApply) throws ServiceException;
}
