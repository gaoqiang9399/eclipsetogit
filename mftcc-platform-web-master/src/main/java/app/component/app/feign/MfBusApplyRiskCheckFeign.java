package app.component.app.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mftcc-platform-factor")
public interface MfBusApplyRiskCheckFeign {
	
	/**
	 * @author czk
	 * @Description: 融资金额不得超过阀值
	 * date 2016-9-6
	 * @param appAmt
	 * @param fazhi
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusApplyRiskCheck/doCheckAppAmt")
	public String doCheckAppAmt(@RequestBody String appAmt,@RequestParam("fazhi") String fazhi) throws Exception;
	
	/**
	 * @author czk
	 * @Description: 融资利率不得超过阀值
	 * date 2016-9-6
	 * @param fincRate
	 * @param fazhi
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusApplyRiskCheck/doCheckFincRate")
	public String doCheckFincRate(@RequestBody String fincRate,@RequestParam("fazhi") String fazhi) throws Exception;
	
	/**
	 * @author czk
	 * @Description: 验证客户类型，如果是黑名单则验证不通过返回0
	 * date 2016-9-6
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusApplyRiskCheck/doCheckCusClassify")
	public String doCheckCusClassify(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 客户年龄检查
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-8-8 下午8:07:57
	 */
	@RequestMapping(value = "/mfBusApplyRiskCheck/checkCusAge")
	public Map<String,Object> checkCusAge(@RequestBody String cusNo) throws Exception;
	
	/**
	 * 方法描述： 杭州微溪二次贷款准入
	 * @param cusNo
	 * @return  返回  1：是  0：否
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-9-22 下午4:50:37
	 */
	@RequestMapping(value = "/mfBusApplyRiskCheck/checkCusInfo")
	public Map<String,Object> checkCusInfo(@RequestBody String cusNo)throws Exception;
	/**
	 * 方法描述： 获取保证人信息
	 * @param cusNo
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-9-22 下午6:54:07
	 */
	@RequestMapping(value = "/mfBusApplyRiskCheck/checkAssureInfo")
	public Map<String,Object> checkAssureInfo(@RequestBody String appId,@RequestParam("assureNo") String assureNo)throws Exception;
	/**
	 * 方法描述： 贷款金额是否小于净资产的30%
	 * @param cusNo
	 * @return  返回  1：是  0：否
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-10-10 下午4:44:34
	 * */
	@RequestMapping(value = "/mfBusApplyRiskCheck/checkCusMatchAssInfo")
	public Map<String,Object> checkCusMatchAssInfo(@RequestBody String appId,@RequestParam("cusNo") String cusNo)throws Exception;
	/**
	 * 方法描述：贷款金额小于销售收入的10%
	 * @param cusNo
	 * @return  返回  1：是  0：否
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-10-10 下午4:44:34
	 * */
	@RequestMapping(value = "/mfBusApplyRiskCheck/checkCusMatchSaleInfo")
	public Map<String,Object> checkCusMatchSaleInfo(@RequestBody String appId,@RequestParam("cusNo") String cusNo)throws Exception;
	/**
	 * 方法描述：贷款金额小于净利润的70%
	 * @param cusNo
	 * @return  返回  1：是  0：否
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-10-10 下午4:44:34
	 * */
	@RequestMapping(value = "/mfBusApplyRiskCheck/checkCusMatchProfitInfo")
	public Map<String,Object> checkCusMatchProfitInfo(@RequestBody String appId,@RequestParam("cusNo") String cusNo)throws Exception;
	/**
	 * 方法描述： 贷款金额小于流水日均值的3倍，且不超过流水高端值
	 * @param appId
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-10-10 下午4:44:34
	 */
	@RequestMapping(value = "/mfBusApplyRiskCheck/checkCusMatchWaterInfo")
	public Map<String,Object> checkCusMatchWaterInfo(@RequestBody String appId,@RequestParam("cusNo") String cusNo)throws Exception;
	
	/**
	 * 方法描述： 获取客户贷款笔数
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-10-13 下午4:50:47
	 */
	@RequestMapping(value = "/mfBusApplyRiskCheck/getCusApplyCnt")
	public Map<String,Object> getCusApplyCnt(@RequestBody String cusNo)throws Exception;

}
