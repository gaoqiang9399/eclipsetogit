package  app.component.cus.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusSelfPutoutConfigApply;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfCusSelfPutoutConfigApplyBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Dec 27 11:46:54 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusSelfPutoutConfigApplyFeign {
	@RequestMapping("/mfCusSelfPutoutConfigApply/insert")
	public Map<String, String> insert(@RequestBody MfCusSelfPutoutConfigApply mfCusSelfPutoutConfigApply) throws Exception;
	
	@RequestMapping("/mfCusSelfPutoutConfigApply/delete")
	public void delete(@RequestBody MfCusSelfPutoutConfigApply mfCusSelfPutoutConfigApply) throws Exception;
	
	@RequestMapping("/mfCusSelfPutoutConfigApply/update")
	public void update(@RequestBody MfCusSelfPutoutConfigApply mfCusSelfPutoutConfigApply) throws Exception;
	
	@RequestMapping("/mfCusSelfPutoutConfigApply/getById")
	public MfCusSelfPutoutConfigApply getById(@RequestBody MfCusSelfPutoutConfigApply mfCusSelfPutoutConfigApply) throws Exception;
	
	@RequestMapping("/mfCusSelfPutoutConfigApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述：审批提交 
	 * @param taskId
	 * @param id
	 * @param opinionType
	 * @param approvalOpinion
	 * @param transition
	 * @param opNo
	 * @param nextUser
	 * @param mfCusSelfPutoutConfigApply
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * Result
	 * @author zhs
	 * @date 2017-12-27 下午4:06:42
	 */
	@RequestMapping("/mfCusSelfPutoutConfigApply/doCommit")
	public Result doCommit(@RequestBody Map<String, Object> parmMap) throws Exception;
	
}
