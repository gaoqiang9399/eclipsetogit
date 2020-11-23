package app.component.query.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.app.entity.MfBusApply;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfQueryDisagreeFeign {
	/**
	 * 查询否决业务
	 * 
	 * @param ipage
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-8-3 上午8:57:59
	 */
	@RequestMapping(value = "/mfQueryDisagree/findDisagreeByPage")
	public Ipage findDisagreeByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 发起复议审批
	 * 
	 * @param mfBusApply
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-8-3 下午2:29:35
	 */
	@RequestMapping(value = "/mfQueryDisagree/doDisagreeReconsider")
	public Map<String, Object> doDisagreeReconsider(@RequestBody MfBusApply mfBusApply) throws Exception;



	/**
	 * 提交否决复议流程
	 * 
	 * @param taskId
	 * @param appId
	 * @param opinionType
	 * @param approvalOpinion
	 * @param transition
	 * @param opNo
	 * @param nextUser
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-8-3 下午5:41:58
	 */
	@RequestMapping(value = "/mfQueryDisagree/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("appId")  String appId,@RequestParam("opinionType")  String opinionType,@RequestParam("approvalOpinion")  String approvalOpinion,@RequestParam("transition")  String transition,@RequestParam("opNo")  String opNo,@RequestParam("nextUser")  String nextUser,@RequestBody  Map<String, Object> dataMap) throws Exception;
	@RequestMapping(value = "/mfQueryDisagree/findByPage")
    public Ipage findByPage(Ipage ipage)throws Exception;
}
