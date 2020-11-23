package app.component.rules.feign;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.rules.entity.RepayPlanBean;

/**
 * <p>RepayPlanDataBo</p>
 * <p>Description:还款计划-规则引擎返回结果处理</p>
 * <p>Company:</p>
 * @author WD
 * @date 2017-05-17 
 */
@FeignClient("mftcc-platform-factor")
public interface RepayPlanDataFeign {

    /**
     * 获取等额本息还款计划规则引擎返回的结果数据
     * @author WD
     * date 2017-05-17
     */
	@RequestMapping(value = "/repayPlanData/getAvgPrcpPlusIntst")
    public RepayPlanBean getAvgPrcpPlusIntst(@RequestBody Map<String, String> dataMap) throws Exception;
    /**
     * 获取到期偿还本金按月结息还款计划规则引擎
     * @param dataMap
     * @return
     * @throws Exception
     * @author	贾帅龙
     * @date 2017-05-17 下午15:22:18
     */
	@RequestMapping(value = "/repayPlanData/getMonthlyIntst")
    public RepayPlanBean getMonthlyIntst(@RequestBody Map<String, String> dataMap) throws Exception;
    /**
     * 获取到期偿还本金按季结息还款计划规则引擎
     * @param dataMap
     * @return
     * @throws Exception
     * @author	贾帅龙
     * @date 2017-05-17 下午17:40:18
     */
	@RequestMapping(value = "/repayPlanData/getQuarterlyIntst")
    public RepayPlanBean getQuarterlyIntst(@RequestBody Map<String, String> dataMap) throws Exception;
    /**
     * 获取按计划还款的还款计划规则引擎
     * @param dataMap
     * @return
     * @throws Exception
     * @author	贾帅龙
     * @date 2017-05-17 下午17:40:18
     */
	@RequestMapping(value = "/repayPlanData/getOnSchedule")
    public RepayPlanBean getOnSchedule(@RequestBody Map<String, String> dataMap) throws Exception;
    /**
     * 获取利随本清的还款计划规则引擎
     * @param dataMap
     * @return
     * @throws Exception 
     * @author	贾帅龙
     * @date 2017-05-18 上午11:35:18
     */
	@RequestMapping(value = "/repayPlanData/getPrcpAndIntstOnce")
    public RepayPlanBean getPrcpAndIntstOnce(@RequestBody Map<String, String> dataMap) throws Exception; 
    /**
     * 获取等本等息还款计划规则引擎返回的结果数据
     * @author WD
     * date 2017-05-17
     */
	@RequestMapping(value = "/repayPlanData/getAvgPrcpAndAvgIntst")
    public RepayPlanBean getAvgPrcpAndAvgIntst(@RequestBody Map<String, String> dataMap) throws Exception;
    
    /**
     * 获取等本等息还款计划规则引擎返回的结果数据
     * @author WD
     * date 2017-05-17
     */
	@RequestMapping(value = "/repayPlanData/getSunFixedPrincipalInterest")
    public RepayPlanBean getSunFixedPrincipalInterest(@RequestBody Map<String, String> dataMap) throws Exception;

    
    /**
     * 获取等额本金还款计划规则引擎返回的结果数据
     * @author WD
     * date 2017-05-17
     */
	@RequestMapping(value = "/repayPlanData/getAvgPrcp")
    public RepayPlanBean getAvgPrcp(@RequestBody Map<String, String> dataMap) throws Exception;
    
    
    /**
     * 获取灵活还款(按比例)还款计划规则引擎返回的结果数据
     * @author WD
     * date 2017-05-18
     */
	@RequestMapping(value = "/repayPlanData/getInsertFlexiblePaymentByPlnRate")
    public RepayPlanBean getInsertFlexiblePaymentByPlnRate(@RequestBody Map<String, Object> dataMap) throws Exception;
    
    /**
     * 获取灵活还款（每期比例不同）还款计划规则引擎返回的结果数据
     * @author WD
     * date 2017-05-18
     */
	@RequestMapping(value = "/repayPlanData/getInsertFlexiblePaymentByPlnDiff")
    public RepayPlanBean getInsertFlexiblePaymentByPlnDiff(@RequestBody Map<String, Object> dataMap) throws Exception;
    /**
	 * 方法描述：获取按周还款的还款计划规则引擎
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-19 上午10:45:18
	 */
	@RequestMapping(value = "/repayPlanData/getWeeklyIntst")
	public RepayPlanBean getWeeklyIntst(@RequestBody Map<String, Object> dataMap) throws Exception;
	/**
	 * 方法描述：获取灵活还款（合并第一期）的还款计划规则引擎
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-19 下午14:31:18
	 */
	@RequestMapping(value = "/repayPlanData/getFlexiblePaymentByPln")
	public RepayPlanBean getFlexiblePaymentByPln(@RequestBody Map<String, Object> dataMap) throws Exception; 
	/**
	 * 方法描述：获取灵活还款（按金额）的还款计划规则引擎
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-19 下午14:31:18
	 */
	@RequestMapping(value = "/repayPlanData/getFlexiblePaymentByPlnAmt")
	public RepayPlanBean getFlexiblePaymentByPlnAmt(@RequestBody Map<String, Object> dataMap) throws Exception;
	/**
	 * 方法描述：获取灵活还款（独立一期且延期）的还款计划规则引擎
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-19 下午15:47:18
	 */
	@RequestMapping(value = "/repayPlanData/getFlexiblePaymentByPlnGrace")
	public RepayPlanBean getFlexiblePaymentByPlnGrace(@RequestBody Map<String, Object> dataMap) throws Exception;
	/**
	 * 方法描述：获取灵活还款（整期计息合并第一期）的还款计划规则引擎
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-19 下午16:15:18
	 */
	@RequestMapping(value = "/repayPlanData/getFlexiblePaymentByPlnHb")
	public RepayPlanBean getFlexiblePaymentByPlnHb(@RequestBody Map<String, Object> dataMap) throws Exception;
	/**
	 * 方法描述：获取灵活还款（独立一期按日计息）的还款计划规则引擎
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-19 下午15:10:18
	 */
	@RequestMapping(value = "/repayPlanData/getFlexiblePaymentByPlnDl")
	public RepayPlanBean getFlexiblePaymentByPlnDl(@RequestBody Map<String, Object> dataMap) throws Exception;
	/**
	 * 方法描述：获取自定义费用的规则引擎
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-07-9 下午15:02:18
	 */
	@RequestMapping(value = "/repayPlanData/getDefinitionFee")
	public Map<String, Object> getDefinitionFee(@RequestBody Map<String, String> dataMap) throws Exception;
	/**
	 * 方法描述：获取担保费用的规则引擎
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-07-9 下午15:02:18
	 */
	@RequestMapping(value = "/repayPlanData/getGuaranteeFee")
	public Map<String, Object> getGuaranteeFee(@RequestBody Map<String, String> dataMap) throws Exception;
	/**
	 * 方法描述：获取按照还款计划期数收取费用
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-08-7 下午15:02:18
	 */
	@RequestMapping(value = "/repayPlanData/getRepayPeriodsCost")
	public Map<String, Object> getRepayPeriodsCost(@RequestBody Map<String, String> dataMap) throws Exception;
	/**
     * 获取按自然季结息，到期还本还款计划规则引擎
     * @param dataMap
     * @return
     * @throws Exception
     * @author	贾帅龙
     * @date 2017-05-17 下午17:40:18
     */
	@RequestMapping(value = "/repayPlanData/getSeasonInterestDuePrincipal")
    public RepayPlanBean getSeasonInterestDuePrincipal(@RequestBody Map<String, String> dataMap) throws Exception;
    /**
     * 获取按周期天数还款还款计划规则引擎
     * @param dataMap
     * @return
     * @throws Exception
     * @author	贾帅龙
     * @date 2017-05-17 下午17:40:18
     */
	@RequestMapping(value = "/repayPlanData/getCycleDaysRepayment")
    public RepayPlanBean getCycleDaysRepayment(@RequestBody Map<String, String> dataMap) throws Exception;
    /**
	 * 是否可以提前还款 1-可以 0-不可以
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/repayPlanData/ifCanPreRepay")
	public String  ifCanPreRepay(@RequestBody Map<String, String> dataMap)throws Exception;
	
	/**
	 * 
	 * 方法描述： 等额本息还款计划规则引擎 只支持固定还款日到次月
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * RepayPlanBean
	 * @author 栾好威
	 * @date 2017-8-31 下午4:44:30
	 */
	@RequestMapping(value = "/repayPlanData/getFixedPaymentMortgage")
    public RepayPlanBean getFixedPaymentMortgage(@RequestBody Map<String, String> dataMap) throws Exception;
    
    /**
     * 
     * 方法描述： 等额本金还款计划规则引擎 只支持固定还款日到次月
     * @param dataMap
     * @return
     * @throws Exception
     * RepayPlanBean
     * @author 栾好威
     * @date 2017-8-31 下午4:46:23
     */
	@RequestMapping(value = "/repayPlanData/getFixedBasisMortgage")
    public RepayPlanBean getFixedBasisMortgage(@RequestBody Map<String, String> dataMap) throws Exception;
    
    /**
     * 
     * 方法描述： 还款及规则调用通用参数
     * @param dataMap
     * @return
     * @throws Exception
     * RepayPlanBean
     * @author 栾好威
     * @date 2017-11-14 上午12:51:18
     */
	@RequestMapping(value = "/repayPlanData/getRulesPlanNorm")
    public RepayPlanBean getRulesPlanNorm(@RequestBody Map<String, String> dataMap) throws Exception;
    /**
     * 
     * 方法描述：调用规则获取违约金计算比例（违约金按阶梯收取时使用）
     * @param dataMap
     * @return 
     * Map<String,Object>
     * @author WD
     * @date 2018-1-17 下午5:34:51
     */
	@RequestMapping(value = "/repayPlanData/getPenaltyReceiveValue")
	public Map<String, Object> getPenaltyReceiveValue(@RequestBody Map<String, Object> dataMap)throws Exception;
}
