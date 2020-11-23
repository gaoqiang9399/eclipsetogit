package  app.component.auth.feign;

import app.base.ServiceException;
import app.component.auth.entity.MfCusCreditAdjustApply;
import app.component.auth.entity.MfCusCreditApplyHis;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
* Title: MfCusCreditAdjustApplyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jun 22 17:41:42 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusCreditAdjustApplyFeign {
	
	@RequestMapping(value = "/mfCusCreditAdjustApply/insert")
	public void insert(@RequestBody MfCusCreditAdjustApply mfCusCreditAdjustApply) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditAdjustApply/delete")
	public void delete(@RequestBody MfCusCreditAdjustApply mfCusCreditAdjustApply) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditAdjustApply/update")
	public void update(@RequestBody MfCusCreditAdjustApply mfCusCreditAdjustApply) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditAdjustApply/getById")
	public MfCusCreditAdjustApply getById(@RequestBody MfCusCreditAdjustApply mfCusCreditAdjustApply) throws Exception;

	@RequestMapping(value = "/mfCusCreditAdjustApply/getByCusNo")
	public MfCusCreditAdjustApply getByCusNo(@RequestBody MfCusCreditAdjustApply mfCusCreditAdjustApply) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditAdjustApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusCreditAdjustApply") MfCusCreditAdjustApply mfCusCreditAdjustApply) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditAdjustApply/insertStartProcess")
	public MfCusCreditAdjustApply insertStartProcess(@RequestBody Map<String,Object> dataMap) throws Exception;
	/**
	 * 
	 * 方法描述： 获得授信调整历史
	 * @param mfCusCreditAdjustApply
	 * @return
	 * @throws Exception
	 * List<MfCusCreditAdjustApply>
	 * @author 沈浩兵
	 * @date 2017-6-25 上午10:31:17
	 */
	@RequestMapping(value = "/mfCusCreditAdjustApply/getCreditAdjustApplyList")
	public List<MfCusCreditAdjustApply> getCreditAdjustApplyList(@RequestBody MfCusCreditAdjustApply mfCusCreditAdjustApply) throws Exception;
	/**
	 * 
	 * 方法描述： 授信调整审批保存提交
	 * @param taskId
	 * @param adjustAppId
	 * @param opinionType
	 * @param approvalOpinion
	 * @param transition
	 * @param opNo
	 * @param nextUser
	 * @param mfCusCreditApplyHis
	 * @return
	 * @throws ServiceException
	 * Result
	 * @author 沈浩兵
	 * @date 2017-6-26 上午8:58:02
	 */
	@RequestMapping(value = "/mfCusCreditAdjustApply/doCommitApprove")
	public Result doCommitApprove(@RequestBody String taskId,@RequestParam("adjustAppId")  String adjustAppId,@RequestParam("opinionType")  String opinionType,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition")  String transition,@RequestParam("opNo")  String opNo,@RequestParam("nextUser") String nextUser,@RequestParam("cusCreditAdjustApply") MfCusCreditAdjustApply cusCreditAdjustApply,@RequestParam("mfCusCreditApplyHis") MfCusCreditApplyHis mfCusCreditApplyHis) throws ServiceException;

	@RequestMapping(value = "/mfCusCreditAdjustApply/submitInput")
	public MfCusCreditAdjustApply submitInput(@RequestBody Map<String, Object> dataMap) throws Exception;

    @RequestMapping(value = "/mfCusCreditAdjustApply/getMfCusCreditAdjustApplyListByCusNo")
    public List<MfCusCreditAdjustApply> getMfCusCreditAdjustApplyListByCusNo(@RequestBody MfCusCreditAdjustApply mfCusCreditAdjustApply) throws Exception;
}
