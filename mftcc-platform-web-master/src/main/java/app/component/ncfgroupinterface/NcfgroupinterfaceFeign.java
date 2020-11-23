package app.component.ncfgroupinterface;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Title: ncfgroupinterface.java Description:
 * 
 * @author:张冬磊@dhcc.com.cn
 * @Sun Sep 17 20:23:52 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface NcfgroupinterfaceFeign {

	/**
	 * 运营商三要素认证是否通过
	 * 
	 * @param cusNo
	 * @param cusName
	 * @param phone
	 * @param idNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ncfgroupinterface/verifyMobileInfo3")
	public Map<String, Object> verifyMobileInfo3(@RequestBody String cusNo, @RequestParam("cusName") String cusName,
			@RequestParam("phone") String phone, @RequestParam("idNum") String idNum) throws Exception;

	/**
	 * 根据风云决返回结果改变申请业务信息 及业务状态
	 * 
	 * @param parmMap
	 * @param appId
	 * @return map key:passAmt = "0";//审批通过金额 <br/>
	 *         map key:pass= "0";//是否通过字段，默认不通过 (1通过)
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-18 下午5:42:26
	 */
	@RequestMapping("/ncfgroupinterface/doChangeMfBusApplyByFengyunjue")
	public Map<String, Object> doChangeMfBusApplyByFengyunjue(@RequestBody Map<String, Object> parmMap,
			@RequestParam("appId") String appId) throws Exception;

	/**
	 * 根据风云决返回结果和白名单授信额度改变申请业务信息 及业务状态(汇达贷)
	 * 
	 * @param parmMap
	 * @param appId
	 * @return map key:passAmt = "0";//审批通过金额 <br/>
	 *         map key:pass= "0";//是否通过字段，默认不通过 (1通过)
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-18 下午5:42:26
	 */
	@RequestMapping("/ncfgroupinterface/doChangeMfBusApplyByFengyunjueForHd")
	public Map<String, Object> doChangeMfBusApplyByFengyunjueForHd(@RequestBody Map<String, Object> parmMap,
			@RequestParam("appId") String appId) throws Exception;

	/**
	 * 根据appId获取客户白名单信息
	 * 
	 * @param parmMap
	 * @param appId
	 * @return
	 * @throws Exception
	 * @author zhang_dlei
	 * @date 2017-12-13 下午5:42:26
	 */
	@RequestMapping("/ncfgroupinterface/doChangeMfBusApplyByWhiteName")
	public Map<String, Object> doChangeMfBusApplyByWhiteName(@RequestBody String appId) throws Exception;

	/**
	 * 根据小风策返回结果改变申请业务信息 及业务状态
	 * 
	 * @param parmMap
	 * @param appId
	 * @return map key:passAmt = "0";//审批通过金额 <br/>
	 *         map key:pass= "0";//是否通过字段，默认不通过 (1通过)
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-18 下午5:42:26
	 */
	@RequestMapping("/ncfgroupinterface/doChangeMfBusApplyByRiskAssessmentTcredit")
	public Map<String, Object> doChangeMfBusApplyByRiskAssessmentTcredit(@RequestBody Map<String, String> parmMap,
			@RequestParam("appId") String appId) throws Exception;

}
