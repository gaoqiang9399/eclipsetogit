package app.component.rulesinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.rules.entity.AccesBigBean;
import app.component.rules.entity.CreditBigBean;
import app.component.rules.entity.EvalBigBean;
import app.component.rules.entity.ExamineBigBean;
import app.component.rules.entity.FiveclassBean;
import app.component.rules.entity.MfRulesProRelation;
import app.component.rules.entity.MfRulesSerialNum;
import app.component.rules.entity.NumberBigBean;
import app.component.rules.entity.RepayPlanBean;
import app.component.rules.entity.RiskBigBean;

/**
 * Title: RulesInterface.java Description:
 * 
 * @author:LiuYF@dhcc.com.cn
 * @Thu Jan 12 11:54:35 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface RulesInterfaceFeign {
	/**
	 * 
	 * 方法描述： 调用规则引擎获得风险拦截数据
	 * 
	 * @param dataMap
	 *            dataMap中包含客户号、业务编号、规则名（编号）等参数
	 * @return
	 * @throws Exception
	 *             RiskBigBean
	 * @author 沈浩兵
	 * @date 2017-1-17 下午5:11:22
	 */
	@RequestMapping(value = "/rulesInterface/getRiskDataByRules")
	public RiskBigBean getRiskDataByRules(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 方法描述： 根据节点设置调用规则引擎获取风险拦截数据（业务提交拦截）
	 * 
	 * @param dataMap
	 *            relNo 业务编号（必传） nodeNo 节点编号（必传） cusNo 客户号 appId 申请号 pledgeNo
	 *            押品号 pactId 合同号 fincId 借据号 rulesNo 规则引擎编号
	 * @return
	 * @throws Exception
	 *             Map<String, Object> 返回结果：{list=[客户评级等级高于B评级,@RequestParam
	 *             ....],@RequestParam exsitRefused=false} exsitRefused ：
	 *             是否存在业务拒绝 Boolean list ：业务拒绝具体描述
	 * @author Javelin
	 * @date 2017-7-1 下午4:09:35
	 */

	@RequestMapping(value = "/rulesInterface/getNodeRiskDataForSubmit")
	public Map<String, Object> getNodeRiskDataForSubmit(@RequestBody Map<String, String> dataMap,
			@RequestParam("rulesNo") String rulesNo) throws Exception;

	/**
	 * 方法描述： 根据节点设置调用规则引擎获取风险拦截数据（准入拦截）
	 * 
	 * @param dataMap
	 *            relNo 业务编号（必传） nodeNo 节点编号（必传） cusNo 客户号 appId 申请号 pledgeNo
	 *            押品号 pactId 合同号 fincId 借据号
	 * @return
	 * @throws Exception
	 *             Map<String,Object> 返回结果：{list=[客户评级等级高于B评级,@RequestParam
	 *             ....],@RequestParam exsitRefused=false} exsitRefused ：
	 *             是否存在业务拒绝 Boolean list ：业务拒绝具体描述
	 * @author Javelin
	 * @date 2017-7-4 下午2:33:35
	 */

	@RequestMapping(value = "/rulesInterface/getNodeRiskDataForBegin")
	public Map<String, Object> getNodeRiskDataForBegin(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 
	 * 方法描述： 调用规则引擎获得客户评级数据
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             EvalBigBean
	 * @author 沈浩兵
	 * @date 2017-1-17 下午5:17:51
	 */
	@RequestMapping(value = "/rulesInterface/getEvalDataByRules")
	public EvalBigBean getEvalDataByRules(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 
	 * 方法描述： 调用规则引擎获得检查大实体
	 * 
	 * @return
	 * @throws Exception
	 *             ExamineBigBean
	 * @author 沈浩兵
	 * @date 2017-2-17 下午12:00:18
	 */
	@RequestMapping(value = "/rulesInterface/getExamineDataByRules")
	public ExamineBigBean getExamineDataByRules(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 获取规则引擎返回的结果数据
	 * 
	 * @author LJW date 2017-3-13
	 */
	@RequestMapping(value = "/rulesInterface/getCreditDataByRules")
	public CreditBigBean getCreditDataByRules(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 调用规则引擎获得五级分类
	 * 
	 * @author GuoXY date 2017-3-18
	 */
	@RequestMapping(value = "/rulesInterface/getFiveclassDataByRules")
	public FiveclassBean getFiveclassDataByRules(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 
	 * 方法描述： 调用规则生成编号
	 * 
	 * @param numberBigBean
	 * @return
	 * @throws Exception
	 *             NumberBigBean
	 * @author 沈浩兵
	 * @date 2017-4-24 下午3:40:40
	 */
	@RequestMapping(value = "/rulesInterface/getNumberBigBean")
	public NumberBigBean getNumberBigBean(@RequestBody NumberBigBean numberBigBean) throws Exception;

	/**
	 * 方法描述：
	 * 
	 * @param dataMapAll
	 * @return AccesBigBean
	 * @author YuShuai
	 * @date 2017-5-10 上午9:48:42
	 */
	@RequestMapping(value = "/rulesInterface/getAccesDataByRules")
	public AccesBigBean getAccesDataByRules(@RequestBody Map<String, Object> dataMapAll) throws Exception;

	/**
	 * 方法描述： 通过规则引擎获取等额本息还款计划数据
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author WD
	 */
	@RequestMapping(value = "/rulesInterface/getAvgPrcpPlusIntst")
	public RepayPlanBean getAvgPrcpPlusIntst(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 方法描述：获取到期偿还本金按月结息还款计划规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-17 下午15:22:18
	 */
	@RequestMapping(value = "/rulesInterface/getMonthlyIntst")
	public RepayPlanBean getMonthlyIntst(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 方法描述：获取到期偿还本金按月季结息还款计划规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-17 下午15:22:18
	 */
	@RequestMapping(value = "/rulesInterface/getQuarterlyIntst")
	public RepayPlanBean getQuarterlyIntst(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 方法描述：获取按计划还款的还款计划规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-18 上午10:15:18
	 */
	@RequestMapping(value = "/rulesInterface/getOnSchedule")
	public RepayPlanBean getOnSchedule(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 方法描述：获取利随本清还款计划规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-18 上午11:31:18
	 */
	@RequestMapping(value = "/rulesInterface/getPrcpAndIntstOnce")
	public RepayPlanBean getPrcpAndIntstOnce(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 方法描述： 通过规则引擎获取等本等息还款计划数据
	 * 
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @author WD
	 */
	@RequestMapping(value = "/rulesInterface/getAvgPrcpAndAvgIntst")
	public RepayPlanBean getAvgPrcpAndAvgIntst(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 方法描述： 通过规则引擎获取等本等息还款计划数据
	 * 
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @author WD
	 */
	@RequestMapping(value = "/rulesInterface/getSunFixedPrincipalInterest")
	public RepayPlanBean getSunFixedPrincipalInterest(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 方法描述： 通过规则引擎获取等额本金还款计划数据
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author WD
	 */
	@RequestMapping(value = "/rulesInterface/getAvgPrcp")
	public RepayPlanBean getAvgPrcp(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 方法描述： 通过规则引擎获取灵活还款(@RequestBody 按比例)还款计划数据
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author WD
	 */
	@RequestMapping(value = "/rulesInterface/getInsertFlexiblePaymentByPlnRate")
	public RepayPlanBean getInsertFlexiblePaymentByPlnRate(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 方法描述： 通过规则引擎获取灵活还款（每期比例不同）还款计划数据
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author WD
	 */
	@RequestMapping(value = "/rulesInterface/getInsertFlexiblePaymentByPlnDiff")
	public RepayPlanBean getInsertFlexiblePaymentByPlnDiff(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 方法描述：获取按周还款的还款计划规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-19 上午10:45:18
	 */
	@RequestMapping(value = "/rulesInterface/getWeeklyIntst")
	public RepayPlanBean getWeeklyIntst(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 方法描述：获取灵活还款（合并第一期）的还款计划规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-19 下午14:24:18
	 */
	@RequestMapping(value = "/rulesInterface/getFlexiblePaymentByPln")
	public RepayPlanBean getFlexiblePaymentByPln(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 方法描述：获取灵活还款（按金额）的还款计划规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-19 下午15:10:18
	 */
	@RequestMapping(value = "/rulesInterface/getFlexiblePaymentByPlnAmt")
	public RepayPlanBean getFlexiblePaymentByPlnAmt(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 方法描述：获取灵活还款（独立一期且延期）的还款计划规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-19 下午15:43:18
	 */
	@RequestMapping(value = "/rulesInterface/getFlexiblePaymentByPlnGrace")
	public RepayPlanBean getFlexiblePaymentByPlnGrace(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 方法描述：获取灵活还款（整期计息合并第一期）的还款计划规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-19 下午16:10:18
	 */
	@RequestMapping(value = "/rulesInterface/getFlexiblePaymentByPlnHb")
	public RepayPlanBean getFlexiblePaymentByPlnHb(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 方法描述：获取灵活还款（整期计息合并第一期）的还款计划规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-19 下午15:02:18
	 */
	@RequestMapping(value = "/rulesInterface/getFlexiblePaymentByPlnDl")
	public RepayPlanBean getFlexiblePaymentByPlnDl(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 方法描述：获取自定义费用的规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-07-9 下午15:02:18
	 */
	@RequestMapping(value = "/rulesInterface/getDefinitionFee")
	public Map<String, Object> getDefinitionFee(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 方法描述：获取担保费用的规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-07-10 下午15:02:18
	 */
	@RequestMapping(value = "/rulesInterface/getGuaranteeFee")
	public Map<String, Object> getGuaranteeFee(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 方法描述：获取按照还款计划期数收取费用的规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-08-07 下午20:20:18
	 */
	@RequestMapping(value = "/rulesInterface/getRepayPeriodsCost")
	public Map<String, Object> getRepayPeriodsCost(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 方法描述：获取按自然季结息，到期还本还款计划规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-17 下午15:22:18
	 */
	@RequestMapping(value = "/rulesInterface/getSeasonInterestDuePrincipal")
	public RepayPlanBean getSeasonInterestDuePrincipal(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 方法描述：获取按周期天数还款还款计划规则引擎
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-17 下午15:22:18
	 */
	@RequestMapping(value = "/rulesInterface/getCycleDaysRepayment")
	public RepayPlanBean getCycleDaysRepayment(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 是否可以提前还款 1-可以 0-不可以
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rulesInterface/ifCanPreRepay")
	public String ifCanPreRepay(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 
	 * 方法描述： 等额本息还款计划规则引擎 只支持固定还款日到次月
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             RepayPlanBean
	 * @author 栾好威
	 * @date 2017-8-31 下午4:44:30
	 */
	@RequestMapping(value = "/rulesInterface/getFixedPaymentMortgage")
	public RepayPlanBean getFixedPaymentMortgage(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 
	 * 方法描述： 等额本金还款计划规则引擎 只支持固定还款日到次月
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             RepayPlanBean
	 * @author 栾好威
	 * @date 2017-8-31 下午4:46:23
	 */
	@RequestMapping(value = "/rulesInterface/getFixedBasisMortgage")
	public RepayPlanBean getFixedBasisMortgage(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 根据流水号I获取流水号信息
	 * 
	 * @param rulesSerialNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rulesInterface/getRulesSerialNumById")
	public MfRulesSerialNum getRulesSerialNumById(@RequestBody MfRulesSerialNum rulesSerialNum) throws Exception;

	/**
	 * 新增流水号信息
	 * 
	 * @param rulesSerialNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rulesInterface/insertRulesSerialNum")
	public void insertRulesSerialNum(@RequestBody MfRulesSerialNum rulesSerialNum) throws Exception;

	/**
	 * 更新流水号信息
	 * 
	 * @param rulesSerialNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rulesInterface/updateRulesSerialNum")
	public void updateRulesSerialNum(@RequestBody MfRulesSerialNum rulesSerialNum) throws Exception;

	/**
	 * @Description:获取业务场景与规则配置
	 * @param mfRulesProRelation
	 * @return
	 * @throws Exception
	 * @author: 仇招
	 * @date: 2017-10-17 上午08:39:57
	 */
	@RequestMapping(value = "/rulesInterface/getMfRulesProRelationList")
	public List<MfRulesProRelation> getMfRulesProRelationList(@RequestBody MfRulesProRelation mfRulesProRelation)
			throws Exception;

	/**
	 * @Description:获取业务场景与规则配置
	 * @param mfRulesProRelation
	 * @return
	 * @throws Exception
	 * @author: 仇招
	 * @date: 2017-10-17 上午08:39:57
	 */
	@RequestMapping(value = "/rulesInterface/getMfRulesProRelation")
	public MfRulesProRelation getMfRulesProRelation(@RequestBody MfRulesProRelation mfRulesProRelation)
			throws Exception;

	@RequestMapping(value = "/rulesInterface/getRulesPlanNorm")
	public RepayPlanBean getRulesPlanNorm(@RequestBody Map<String, String> dataMap) throws Exception;

	/**
	 * 
	 * 方法描述：调用规则获取违约金计算比例（违约金按阶梯收取时使用）
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author WD
	 * @date 2018-1-17 下午5:23:44
	 */
	@RequestMapping(value = "/rulesInterface/getPenaltyReceiveValue")
	public Map<String, Object> getPenaltyReceiveValue(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	*@desc调用规则
	*@author lwq
	*@parm
	*@return
	**/
	@RequestMapping(value = "/rulesInterface/getFincRateRulesData")
	public  Map<String, Object> getFincRateRulesData(@RequestBody Map<String, Object> dataMap) throws Exception;
	
	/**
	 * 根据商品类别、押品清单最小单位单价、最小单位数量、单位单价、单位数量计算计算押品货值
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rulesInterface/getPleValue")
	public  Map<String, Object> getPleValue(@RequestBody Map<String, Object> dataMap) throws Exception;
}
