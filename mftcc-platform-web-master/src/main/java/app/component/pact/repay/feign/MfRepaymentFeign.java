package app.component.pact.repay.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfRepaymentFeign {

	@RequestMapping(value = "/mfRepayment/doRepaymentJsp")
	public Map<String, Object> doRepaymentJsp(@RequestBody String fincId) throws Exception;

	@RequestMapping(value = "/mfRepayment/doRepaymentOperate")
	public Map<String, String> doRepaymentOperate(@RequestBody Map<String, Object> parmMap) throws Exception;
	@RequestMapping(value = "/mfRepayment/doRepaymentRegistrationOperate")
	public Map<String, String> doRepaymentRegistrationOperate(@RequestBody Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/mfRepayment/doRepaymentOperateForBuy")
	public Map<String, String> doRepaymentOperateForBuy(@RequestBody Map<String, Object> parmMap) throws Exception;
	@RequestMapping(value = "/mfRepayment/doRepaymentOperateForAccount")
	public Map<String, String> doRepaymentOperateForAccount(@RequestBody Map<String, Object> parmMap) throws Exception;
	@RequestMapping(value = "/mfRepayment/doTailOperate")
	public Map<String, String> doTailOperate(@RequestBody Map<String, Object> parmMap) throws Exception;
	@RequestMapping(value = "/mfRepayment/doTailOperateForFincMain")
	public Map<String, String> doTailOperateForFincMain(@RequestBody Map<String, Object> parmMap) throws Exception;
	/**
	 * 融资页面下的买方还款
	 * @param parmMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfRepayment/doRepaymentOperateForBuyFincMain")
	public Map<String, String> doRepaymentOperateForBuyFincMain(@RequestBody Map<String, Object> parmMap) throws Exception;
	/**
	 * 融资页面下的卖方还款
	 * @param parmMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfRepayment/doRepaymentOperateForBuyFincMainMai")
	public Map<String, String> doRepaymentOperateForBuyFincMainMai(@RequestBody Map<String, Object> parmMap) throws Exception;
    /**
     *
     * 方法描述：三方还款申请
     * @param paramMap
     * @return
     * @throws Exception 
     * Map<String,Object>
     * @author wd
     * @date 2018年7月6日 下午6:42:14
     */
	@RequestMapping(value = "/thirdPayGateWayInterfaceConT/doRepayment")
	public Map<String, String> doRepayment(@RequestBody Map<String, Object> paramMap)throws Exception;
	
	@RequestMapping(value = "/mfRepayment/doOverBatch")
	public Map<String, Object> doOverBatch() throws Exception;

	@RequestMapping(value = "/mfRepayment/doRapayDateChange")
	public Map<String, Object> doRapayDateChange(@RequestBody Map<String, String> parmMap) throws Exception;

	@RequestMapping(value = "/mfRepayment/doRapayDateChangeTrial")
	public Map<String, Object> doRapayDateChangeTrial(@RequestBody Map<String, String> parmMap) throws Exception;

	@RequestMapping(value = "/mfRepayment/doPrepaymentJsp")
	public Map<String, Object> doPrepaymentJsp(@RequestBody String fincId) throws Exception;

	@RequestMapping(value = "/mfRepayment/doCalcLiXiTiQianHuanKuan")
	public Map<String, Object> doCalcLiXiTiQianHuanKuan(@RequestBody Map<String, String> parmMap) throws Exception;

	@RequestMapping(value = "/mfRepayment/doPrepaymentOperate")
	public Map<String, String> doPrepaymentOperate(@RequestBody Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/mfRepayment/doCheckTiQianHuanKuan")
	public Map<String, Object> doCheckTiQianHuanKuan(@RequestBody Map<String, String> parmMap) throws Exception;
	/**
     * 
     * 方法描述：利随本清正常还款相关处理   判断 利随本清计算方式 为 按还款本金计算利息 
     * @param parmMap
     * @return
     * @throws Exception 
     * Map<String,Object>
     * @author WD
     * @date 2018-4-4 下午2:52:05
     */
	@RequestMapping(value = "/mfRepayment/doCheckLsbqHuanKuan")
	public Map<String, Object> doCheckLsbqHuanKuan(@RequestBody Map<String, String> parmMap)throws Exception;

    /**
     * 
     * 方法描述：计算利随本清还款 利息 利息收取按照还款本金计算
     * @param parmMap
     * @return
     * @throws Exception 
     * Map<String,Object>
     * @author WD
     * @date 2018-4-4 下午3:36:28
     */
	@RequestMapping(value = "/mfRepayment/doCalcLiXiLsbqHuanKuan")
	public Map<String, Object> doCalcLiXiLsbqHuanKuan(@RequestBody Map<String, String> parmMap)throws Exception;

    /**
     * 
     * 方法描述：利随本清还款 还款业务处理
     * @param parmMap
     * @return
     * @throws Exception 
     * Map<String,String>
     * @author WD
     * @date 2018-4-4 下午3:46:01
     */
	@RequestMapping(value = "/mfRepayment/doLsbqRaymentOperate")
	public Map<String, String> doLsbqRaymentOperate(@RequestBody Map<String, Object> parmMap)throws Exception;
	
	/**
	 * 
	 * 方法描述：利随本清还款  还款页面数据获取
	 * @param fincId
	 * @return
	 * @throws Exception 
	 * Map<String,Object>
	 * @author WD
	 * @date 2018-4-10 上午10:30:47
	 */
	@RequestMapping(value = "/mfRepayment/doLsbqRapaymentJsp")
	public Map<String, Object> doLsbqRapaymentJsp(@RequestBody String fincId)throws Exception;
    /**
     *
     * 功能描述:展期前利息还款检查，判断展期前是否收取所有的利息
     * @param: [parmMap]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @auther: wd
     * @date: 2018/7/26 16:07
     *
     */
	@RequestMapping(value = "/mfLiXiRepayment/doCheckLiXiHuanKuan")
	public Map<String,Object> doCheckLiXiHuanKuan(Map<String,String> parmMap)throws Exception;
    /**
     *
     * 功能描述:利息还款（展期前收息）页面数据获取
     * @param: [fincId]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @auther: wd
     * @date: 2018/7/26 17:53
     *
     */
	@RequestMapping(value = "/mfLiXiRepayment/doLiXiRepaymentJsp")
	public Map<String, Object> doLiXiRepaymentJsp(@RequestBody String fincId) throws Exception;
    /**
     *
     * 功能描述:利息还款（展期前收息） 还款业务处理
     * @param: [parmMap]
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @auther: wd
     * @date: 2018/7/26 17:56
     *
     */
	@RequestMapping(value = "/mfLiXiRepayment/doLiXiRepaymentOperate")
	public Map<String, String> doLiXiRepaymentOperate(@RequestBody Map<String, Object> parmMap) throws Exception;
}
