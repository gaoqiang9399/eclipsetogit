package app.component.pledgeinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import app.component.pledge.entity.MfPledgeDynamicForm;

/**
 * Title: pledgeInterface.java Description:
 * 
 * @author:LiuYF@dhcc.com.cn
 * @Mon May 16 20:45:38 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface PledgeInterfaceFeign {



	/**
	 * 根据申请编号获得该业务对应的押品动态表单信息
	 * 
	 * @author LJW date 2017-3-21
	 */
	@RequestMapping(value = "/pledgeInterface/getByBusAppId")
	public MfPledgeDynamicForm getByBusAppId(@RequestBody String appId, @RequestParam("formType") String formType) throws Exception;


	/**
	 * 
	 * 方法描述： 获得可用的押品表单信息
	 * 
	 * @param mfPledgeDynamicForm
	 * @return
	 * @throws Exception
	 *             List<MfPledgeDynamicForm>
	 * @author 沈浩兵
	 * @date 2016-11-17 下午9:10:56
	 */
	@RequestMapping(value = "/pledgeInterface/getUseableList")
	public List<MfPledgeDynamicForm> getUseableList(@RequestBody MfPledgeDynamicForm mfPledgeDynamicForm)
			throws Exception;

	/**
	 * 
	 * 方法描述： 获得押品动态表单信息
	 * 
	 * @return
	 * @throws Exception
	 *             MfPledgeDynamicForm
	 * @author 沈浩兵
	 * @date 2016-11-18 上午10:15:55
	 */
	@RequestMapping(value = "/pledgeInterface/getPleDyInfo")
	public MfPledgeDynamicForm getPleDyInfo(@RequestBody MfPledgeDynamicForm mfPledgeDynamicForm) throws Exception;

	/**
	 * 
	 * 方法描述：根据押品编号获得押品清单，并检查应收账款到期日不能大于当前日期加上融资期限的日期
	 * 
	 * @return
	 * @throws Exception
	 *             List<MfBusPledgeDetail>
	 * @author 沈浩兵
	 * @date 2017-1-5 下午3:07:26
	 */
	@RequestMapping(value = "/pledgeInterface/checkPledgeExpireByPleNo")
	public String checkPledgeExpireByPleNo(@RequestBody String pledgeNo) throws Exception;

	/**
	 * 方法描述： 根据申请编号获取客户所录入的评估信息
	 * 
	 * @param appId
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-5-11 上午9:41:41
	 */
	@RequestMapping(value = "/pledgeInterface/getPleEvalCnt")
	public String getPleEvalCnt(@RequestBody String appId) throws Exception;

	/**
	 * 方法描述： 获取押品的公证和权证信息
	 * 
	 * @param parMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-5-18 上午11:58:44
	 */
	@RequestMapping(value = "/pledgeInterface/getEvalCnt")
	public String getEvalCnt(@RequestBody Map<String, String> parMap) throws Exception;
}
