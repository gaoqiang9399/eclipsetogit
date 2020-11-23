package  app.component.collateral.movable.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.movable.entity.MfMoveableBuybackApply;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfMoveableBuybackApplyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Jun 19 11:37:03 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMoveableBuybackApplyFeign {
	
	@RequestMapping(value = "/mfMoveableBuybackApply/insert")
	public void insert(@RequestBody MfMoveableBuybackApply mfMoveableBuybackApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableBuybackApply/delete")
	public void delete(@RequestBody MfMoveableBuybackApply mfMoveableBuybackApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableBuybackApply/update")
	public void update(@RequestBody MfMoveableBuybackApply mfMoveableBuybackApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableBuybackApply/getById")
	public MfMoveableBuybackApply getById(@RequestBody MfMoveableBuybackApply mfMoveableBuybackApply) throws Exception;
	
	@RequestMapping(value = "/mfMoveableBuybackApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfMoveableBuybackApply") MfMoveableBuybackApply mfMoveableBuybackApply) throws Exception;
	/**
	 * 
	 * 方法描述： 获得审批视角所需要的参数并封装到map中
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-5-6 上午10:14:41
	 */
	@RequestMapping(value = "/mfMoveableBuybackApply/getViewDataMap")
	public Map<String,Object> getViewDataMap(@RequestBody String busCollateralId) throws Exception;
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
	 * @param mfMoveableModifyApproHis
	 * @param mfMoveableBuybackApply
	 * @return
	 * @throws Exception
	 * Result
	 * @author ywh
	 * @date 2017-6-9 下午7:02:35
	 */
	@RequestMapping(value = "/mfMoveableBuybackApply/doCommit")
	public Result doCommit(@RequestBody Map<String,Object> dataMap) throws Exception;
	/**
	 * 
	 * 方法描述：判断当前业务是否存在回调申请
	 * @param mfMoveableBuybackApply
	 * @return
	 * @throws Exception
	 * Result
	 * @author ywh
	 * @date 2017-6-20下午2:12:35
	 */
	@RequestMapping(value = "/mfMoveableBuybackApply/getBuybackFlag")
	public  Map<String,Object> getBuybackFlag (@RequestBody MfMoveableBuybackApply mfMoveableBuybackApply) throws Exception;
}
