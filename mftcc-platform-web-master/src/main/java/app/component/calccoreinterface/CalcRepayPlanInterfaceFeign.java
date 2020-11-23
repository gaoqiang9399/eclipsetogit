package  app.component.calccoreinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.calc.core.entity.MfBusOverBaseRecord;
import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.core.entity.MfRepayPlanChild;
import app.component.calc.core.entity.MfRepayPlanParameter;

/**
* Title: CalcRepayPlanInterface.java
* Description: 还款计划接口
* @author:LHW@dhcc.com.cn
* @Mon May 15 15:37:16 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface CalcRepayPlanInterfaceFeign {

	/**
	 * 等额本息还款 insertAvgPrcpPlusIntst
	 * @param Map<String, String> 
	 *  开始日期    			putoutDate <br>
	 *  结束日期     		endDate <br>
	 *  还款日方式    		repayDayType   1-固定还款日|2-随放款日<br>
	 *  固定还款日还款方式      fixedRepayDayType   固定还款日还款方式       	1-合并第一期|2-独立一期|3-放款时收取<br>
	 *  固定还款日       		fixedRepayDay    1-29或月末<br>
	 *  贷款金额       		loanAmt <br>
	 *  年利率  			fincRate<br>
	 *  计息天数    			years	  360或365<br>
	 *  不足期计息类型     	intstCalcLessCycle	1-按月计息|2-按实际天数计息<br>
	 *  保留小数位数      		digits	2-两位|1-一位|0-不保留<br>
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表		 
	 * @author WD
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getAvgPrcpPlusIntst")
	public Map<String, Object> getAvgPrcpPlusIntst(@RequestBody Map<String, String> mapParm) throws Exception;
	/**
	 * 方法描述： 到期偿还本金按月结息
	 *  @param Map<String, String> 
	 *  开始日期    			putoutDate <br>
	 *  结束日期     		endDate <br>
	 *  还款日方式    		repayDayType   1-固定还款日|2-随放款日<br>
	 *  固定还款日还款方式      fixedRepayDayType   固定还款日还款方式       	1-合并第一期|2-独立一期|3-放款时收取<br>
	 *  固定还款日       		fixedRepayDay    1-29或月末<br>
	 *  贷款金额       		loanAmt <br>
	 *  年利率  			fincRate<br>
	 *  计息天数    			years	  360或365<br>
	 *  不足期计息类型     	intstCalcLessCycle	1-按月计息|2-按实际天数计息<br>
	 *  保留小数位数      		digits	2-两位|1-一位|0-不保留<br>
	 *  计息方式 			normCalcType	 1-按月计息  2-按实际天数计息	
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾帅龙
	 * @date 2017-05-17 上午11:11:18
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getMonthlyIntst")
	public Map<String, Object> getMonthlyIntst(@RequestBody Map<String, String> mapParm) throws Exception;
	/**
	 * 方法描述： 到期偿还本金按季结息
	 *  @param Map<String, String> 
	 *  开始日期    			putoutDate <br>
	 *  结束日期     		endDate <br>
	 *  还款日方式    		repayDayType   1-固定还款日|2-随放款日<br>
	 *  固定还款日还款方式      fixedRepayDayType   固定还款日还款方式       	1-合并第一期|2-独立一期|3-放款时收取<br>
	 *  固定还款日       		fixedRepayDay    1-29或月末<br>
	 *  贷款金额       		loanAmt <br>
	 *  年利率  			fincRate<br>
	 *  计息天数    			years	  360或365<br>
	 *  不足期计息类型     	intstCalcLessCycle	1-按月计息|2-按实际天数计息<br>
	 *  保留小数位数      		digits	2-两位|1-一位|0-不保留<br>
	 *  计息方式 			normCalcType	 1-按月计息  2-按实际天数计息	
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾帅龙
	 * @date 2017-05-17 下午17:33:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getQuarterlyIntst")
	public Map<String, Object> getQuarterlyIntst(@RequestBody Map<String, String> mapParm) throws Exception; 
	
	/**
	 * 等本等息还款 insertAvgPrcpAndAvgIntst 
	 *  @param Map<String, String> 
	 *  开始日期    			putoutDate <br>
	 *  结束日期     		endDate <br>
	 *  还款日方式    		repayDayType   1-固定还款日|2-随放款日<br>
	 *  固定还款日还款方式      fixedRepayDayType   固定还款日还款方式       	1-合并第一期|2-独立一期|3-放款时收取<br>
	 *  固定还款日       		fixedRepayDay    1-29或月末<br>
	 *  贷款金额       		loanAmt <br>
	 *  年利率  			fincRate<br>
	 *  计息天数    			years	  360或365<br>
	 *  不足期计息类型     	intstCalcLessCycle	1-按月计息|2-按实际天数计息<br>
	 *  保留小数位数      		digits	2-两位|1-一位|0-不保留<br>
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表		 
	 * @author WD
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getAvgPrcpAndAvgIntst")
	public Map<String, Object> getAvgPrcpAndAvgIntst(@RequestBody Map<String, String> mapParm) throws Exception;
	
	/**
	 * 等本等息还款 getSunFixedPrincipalInterest 
	 *  @param Map<String, String> 
	 *  开始日期    			putoutDate <br>
	 *  结束日期     		endDate <br>
	 *  还款日方式    		repayDayType   1-固定还款日|2-随放款日<br>
	 *  固定还款日还款方式      fixedRepayDayType   固定还款日还款方式       	1-合并第一期|2-独立一期|3-放款时收取<br>
	 *  固定还款日       		fixedRepayDay    1-29或月末<br>
	 *  贷款金额       		loanAmt <br>
	 *  年利率  			fincRate<br>
	 *  计息天数    			years	  360或365<br>
	 *  不足期计息类型     	intstCalcLessCycle	1-按月计息|2-按实际天数计息<br>
	 *  保留小数位数      		digits	2-两位|1-一位|0-不保留<br>
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表		 
	 * @author WD
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getSunFixedPrincipalInterest")
	public Map<String, Object> getSunFixedPrincipalInterest(@RequestBody Map<String, String> mapParm) throws Exception;
	
	/**
	 * 方法描述：按计划还款
	 *  @param Map<String, String> 
	 *  开始日期    			putoutDate <br>
	 *  结束日期     		endDate <br>
	 *  还款日方式    		repayDayType   1-固定还款日|2-随放款日<br>
	 *  固定还款日还款方式      fixedRepayDayType   固定还款日还款方式       	1-合并第一期|2-独立一期|3-放款时收取<br>
	 *  固定还款日       		fixedRepayDay    1-29或月末<br>
	 *  贷款金额       		loanAmt <br>
	 *  年利率  			fincRate<br>
	 *  计息天数    			years	  360或365<br>
	 *  不足期计息类型     	intstCalcLessCycle	1-按月计息|2-按实际天数计息<br>
	 *  保留小数位数      		digits	2-两位|1-一位|0-不保留<br>
	 *  *计息方式 			normCalcType	 1-按月计息  2-按实际天数计息	
	 *  利息计算基数类型          interestCalBaseType 		1-贷款本金   2-贷款余额<br>
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾帅龙
	 * @date 2017-05-18 上午09:27:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getOnSchedule")
	public Map<String, Object> getOnSchedule(@RequestBody Map<String, String> mapParm) throws Exception;
	/**
	 * 方法描述：利随本清  
	 *  @param Map<String, String> 
	 *  开始日期    			putoutDate <br>
	 *  结束日期     		endDate <br>
	 *  贷款金额       		loanAmt <br>
	 *  年利率  			fincRate<br>
	 *  计息天数    			years	  360或365<br>
	 *  保留小数位数      		digits	2-两位|1-一位|0-不保留<br>
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾帅龙
	 * @date 2017-05-18 上午 11:11:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getPrcpAndIntstOnce")
	public Map<String, Object> getPrcpAndIntstOnce(@RequestBody Map<String, String> mapParm) throws Exception;
	/**
	 * 等额本金还款 insertAvgPrcp 
	 *  @param Map<String, String> 
	 *  开始日期    			putoutDate <br>
	 *  结束日期     		endDate <br>
	 *  还款日方式    		repayDayType   1-固定还款日|2-随放款日<br>
	 *  固定还款日还款方式      fixedRepayDayType   固定还款日还款方式       	1-合并第一期|2-独立一期|3-放款时收取<br>
	 *  固定还款日       		fixedRepayDay    1-29或月末<br>
	 *  贷款金额       		loanAmt <br>
	 *  年利率  			fincRate<br>
	 *  计息天数    			years	  360或365<br>
	 *  不足期计息类型     	intstCalcLessCycle	1-按月计息|2-按实际天数计息<br>
	 *  保留小数位数      		digits	2-两位|1-一位|0-不保留<br>
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表		 
	 * @author WD
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getAvgPrcp")
	public Map<String, Object> getAvgPrcp(@RequestBody Map<String, String> mapParm) throws Exception;
	/**
	 * 灵活还款(按比例) insertFlexiblePaymentByPlnRate
	 * @param mfRepayPlanParameter 开始日期  贷款期限（月） 年利率  放款金额   每期还款比例 （注 如果是20% 则传入 0.2 ）  客户号 合同号 借据号 合同金额 操作员信息 部门信息
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表		 
	 * @author WD
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getFlexiblePaymentByPlnRate")
	public Map<String, Object> getFlexiblePaymentByPlnRate(@RequestBody MfRepayPlanParameter mfRepayPlanParameter) throws Exception;
	/**
	 * 灵活还款（每期比例不同） insertFlexiblePaymentByByPlnDiff
	 * @param  mfRepayPlanParameter 开始日期  贷款期限（月） 年利率  放款金额   计息天数 360/365  定义日期金额(定义日期金额的格式为：第一期本金比例，第二期本金比例，第三期本金比例   02,02,06) 客户号 合同号 借据号 合同金额 操作员信息 部门信息
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表		 
	 * @author WD
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getFlexiblePaymentByPlnDiff")
	public Map<String, Object> getFlexiblePaymentByPlnDiff(@RequestBody MfRepayPlanParameter mfRepayPlanParameter) throws Exception;
	/**
	 * 方法描述：按周还款 
	 * @param mfRepayPlanParameter	必传参数：利息计算基础（ALL-贷款金额|BAL-剩余本金） 开始日期	贷款金额 贷款期限（周）年利率  计息天数
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾帅龙
	 * @date 2017-05-19 上午 10:23:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getWeeklyIntst")
	public Map<String, Object> getWeeklyIntst(@RequestBody MfRepayPlanParameter mfRepayPlanParameter) throws Exception;
	/**
	 * 方法描述：灵活还款（合并第一期）
	 * @param mfRepayPlanParameter	必传参数：开始日期  贷款期限（月） 计息类型  年利率  贷款金额  还款日(每期的固定还款日)  计息天数 
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾帅龙
	 * @date 2017-05-19 上午 11:23:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getFlexiblePaymentByPln")
	public Map<String, Object> getFlexiblePaymentByPln(@RequestBody MfRepayPlanParameter mfRepayPlanParameter) throws Exception;
	/**
	 * 方法描述：灵活还款（按金额）
	 * @param mfRepayPlanParameter	必传参数：开始日期  贷款期限（月） 计息类型  年利率  贷款金额   每期本金    计息天数 
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾帅龙
	 * @date 2017-05-19 下午 14:49:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getFlexiblePaymentByPlnAmt")
	public Map<String, Object> getFlexiblePaymentByPlnAmt(@RequestBody MfRepayPlanParameter mfRepayPlanParameter) throws Exception;
	/**
	 * 方法描述：灵活还款（独立一期且延期）
	 * @param mfRepayPlanParameter	必传参数：开始日期  贷款期限（月） 计息类型  年利率  贷款金额   还款日    计息天数 
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾帅龙
	 * @date 2017-05-19 下午 15:33:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getFlexiblePaymentByPlnGrace")
	public Map<String, Object> getFlexiblePaymentByPlnGrace(@RequestBody MfRepayPlanParameter mfRepayPlanParameter) throws Exception;
	/**
	 * 方法描述：灵活还款（整期计息合并第一期）
	 * @param mfRepayPlanParameter	必传参数：开始日期  贷款期限（月） 计息类型  年利率  贷款金额   还款日   
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾帅龙
	 * @date 2017-05-19 下午 16:00:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getFlexiblePaymentByPlnHb")
	public Map<String, Object> getFlexiblePaymentByPlnHb(@RequestBody MfRepayPlanParameter mfRepayPlanParameter) throws Exception;
	/**
	 * 方法描述：灵活还款（独立一期按日计息）
	 * @param mfRepayPlanParameter	必传参数：开始日期  贷款期限（月） 计息类型  年利率  贷款金额  计息天数  还款日   
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾帅龙
	 * @date 2017-05-19 下午 16:40:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getFlexiblePaymentByPlnDl")
	public Map<String, Object> getFlexiblePaymentByPlnDl(@RequestBody MfRepayPlanParameter mfRepayPlanParameter) throws Exception;
	
	/**
	 * 方法描述：通用还款计划生成
	 *  @param Map<String, String> 
	 *  开始日期    			putoutDate <br>
	 *  结束日期     		endDate <br>
	 *  还款日方式    		repayDayType   1-固定还款日|2-随放款日<br>
	 *  固定还款日还款方式      fixedRepayDayType   固定还款日还款方式       	1-合并第一期|2-独立一期|3-放款时收取<br>
	 *  固定还款日       		fixedRepayDay    1-29或月末<br>
	 *  贷款金额       		loanAmt <br>
	 *  年利率  			fincRate<br>
	 *  计息天数    			years	  360或365<br>
	 *  不足期计息类型     	intstCalcLessCycle	1-按月计息|2-按实际天数计息<br>
	 *  保留小数位数      		digits	2-两位|1-一位|0-不保留<br>
	 *  *计息方式 			normCalcType	 1-按月计息  2-按实际天数计息	
	 *  利息计算基数类型          interestCalBaseType 		1-贷款本金   2-贷款余额<br>
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表
	 * @throws Exception
	 * Map<String,Object>
	 * @author 栾好威
	 * @date 2017-05-18 上午09:27:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getRulesPlanNorm")
	public Map<String, Object> getRulesPlanNorm(@RequestBody Map<String, String> mapParm) throws Exception;
	
	/**
	 * 保存还款计划
	 * @param mfRepayPlan
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-05-31 下午 14:40:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/insertMfRepayPlan")
	public List<MfRepayPlan> insertMfRepayPlan (@RequestBody List<MfRepayPlan> mfRepayPlan) throws Exception;
	/**
	 * 保存还款计划子表
	 * @param mfRepayPlanChild
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 * @date 2017-06-22 下午 14:40:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/insertMfRepayPlanChild")
	public List<MfRepayPlanChild> insertMfRepayPlanChild(@RequestBody List<MfRepayPlanChild> mfRepayPlanChild) throws Exception;
	/**
	 * 还款计划合并时更新主表还款计划
	 * @param mfRepayPlan
	 * @throws Exception
	 * @author 贾帅龙
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/updateMfRepayPlan")
	public void updateMfRepayPlan(@RequestBody List<MfRepayPlan> repayPlanList) throws Exception;
	/**
	 * 修改还款计划
	 * @param mfRepayPlan
	 * @throws Exception
	 * @author 任俊庆
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/updateMfRepayPlan")
	public void updateMfRepayPlan(@RequestBody MfRepayPlan mfRepayPlan) throws Exception;
	
	/**
	 * 通过借据号查询还款计划列表
	 * @param mfRepayPlan
	 * @return
	 * @throws Exception
	 * @author 贾帅龙
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getMfBusRepayPlanListById")
	public List<MfRepayPlan> getMfBusRepayPlanListById(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;
	/**
	 * 
	 * 方法描述：通过借据号和还款状态 获取还款计划条数
	 * @param mfRepayPlan
	 * @return int
	 * @throws Exception 
	 * @author WD
	 * @date 2017-6-30 上午11:20:15
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getMfRepayPlanCount")
	public int getMfRepayPlanCount(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;
	/**
	 * 
	* @Title: getMfRepayPlanByDateAndOutFlag  
	* @Description: 根据  借据号，当前日期，还款状态（过滤掉已换状态） 获取为结清的还款计划
	* @param @param mfRepayPlan
	* @param @return
	* @param @throws Exception    参数  
	* @return int    返回类型  
	* @throws
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getMfRepayPlanByDateAndOutFlag")
	public List<MfRepayPlan> getMfRepayPlanByDateAndOutFlag(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;
	/**
	 * 
	* @Title: getMfBusOverBaseRecordByBean  
	* @Description: 获得某一期逾期情况  
	* @param @param busOverBaseRecord
	* @param @return
	* @param @throws Exception    参数  
	* @return MfBusOverBaseRecord    返回类型  
	* @throws
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getMfBusOverBaseRecordByBean")
	public MfBusOverBaseRecord getMfBusOverBaseRecordByBean(@RequestBody MfBusOverBaseRecord busOverBaseRecord)throws Exception;
	/**
	 * 方法描述： 按自然季结息，到期还本
	 *  @param Map<String, String> 
	 *  开始日期    			putoutDate <br>
	 *  结束日期     		endDate <br>
	 *  还款日方式    		repayDayType   1-固定还款日|2-随放款日<br>
	 *  固定还款日还款方式      fixedRepayDayType   固定还款日还款方式       	1-合并第一期|2-独立一期|3-放款时收取<br>
	 *  固定还款日       		fixedRepayDay    1-29或月末<br>
	 *  贷款金额       		loanAmt <br>
	 *  年利率  			fincRate<br>
	 *  计息天数    			years	  360或365<br>
	 *  不足期计息类型     	intstCalcLessCycle	2-按月计息|3-按实际天数计息<br>
	 *  保留小数位数      		digits	2-两位|1-一位|0-不保留<br>
	 *  计息方式 			normCalcType	 2-按月计息  3-按实际天数计息	
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾帅龙
	 * @date 2017-08-07 下午20:32:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getSeasonInterestDuePrincipal")
	public Map<String, Object> getSeasonInterestDuePrincipal(@RequestBody Map<String, String> mapParm) throws Exception; 
	/**
	 * 方法描述： 按周期天数还款
	 *  @param Map<String, String> 
	 *  开始日期    			putoutDate <br>
	 *  结束日期     		endDate <br>
	 *  贷款金额       		loanAmt <br>
	 *  年利率  			fincRate<br>
	 *  计息天数    			years	  360或365<br>
	 *  保留小数位数      		digits	2-两位|1-一位|0-不保留<br>
	 *  利息计算基数类型          interestCalBaseType 		1-贷款本金   2-贷款余额<br>
	 * 	还款周期（天数）           cycleDays
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 贾帅龙
	 * @date 2017-08-08 下午20:32:10
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getCycleDaysRepayment")
	public Map<String, Object> getCycleDaysRepayment(@RequestBody Map<String, String> mapParm) throws Exception; 
	
	
	/**
	 * 等额本息还款计划规则引擎 只支持固定还款日到次月
	 * @param Map<String, String> 
	 *  开始日期    			putoutDate <br>
	 *  结束日期     		endDate <br>
	 *  固定还款日       		fixedRepayDay    1-29或月末<br>
	 *  贷款金额       		loanAmt <br>
	 *  年利率  			fincRate<br>
	 *  计息天数    			years	  360或365<br>
	 *  不足期计息类型     	intstCalcLessCycle	1-按月计息|2-按实际天数计息<br>
	 *  保留小数位数      		digits	2-两位|1-一位|0-不保留<br>
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表		 
	 * @author 栾好威
	 * @date 2017-8-31 下午4:44:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getFixedPaymentMortgage")
	public Map<String, Object> getFixedPaymentMortgage(@RequestBody Map<String, String> mapParm) throws Exception;
	
	/**
	 * 等额本金还款计划规则引擎 只支持固定还款日到次月
	 * @param Map<String, String> 
	 *  开始日期    			putoutDate <br>
	 *  结束日期     		endDate <br>
	 *  固定还款日       		fixedRepayDay    1-29或月末<br>
	 *  贷款金额       		loanAmt <br>
	 *  年利率  			fincRate<br>
	 *  计息天数    			years	  360或365<br>
	 *  不足期计息类型     	intstCalcLessCycle	1-按月计息|2-按实际天数计息<br>
	 *  保留小数位数      		digits	2-两位|1-一位|0-不保留<br>
	 * @return Map<String, Object> dataMap = new HashMap<String, Object>(); 
@RequestParam("dataMap.put("flag"") 		       dataMap.put("flag",@RequestParam(""1"")  "1"); // 0 成功  1 失败
@RequestParam("dataMap.put("msg"") 		       dataMap.put("msg",@RequestParam(""失败后的异常信息"")  "失败后的异常信息"); 
@RequestParam("dataMap.put("repayPlanList"")                dataMap.put("repayPlanList",@RequestParam("planBeans")  planBeans);//还款计划列表		 
	 * @author 栾好威
	 * @date 2017-8-31 下午4:44:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getFixedBasisMortgage")
	public Map<String, Object> getFixedBasisMortgage(@RequestBody Map<String, String> mapParm) throws Exception;

	/**
	 * 
	 * 方法描述：通过借据号 获取该笔借据的最后一期的还款计划
	 * @param lastMfRepayPlan
	 * @return
	 * @throws Exception 
	 * MfRepayPlan
	 * @author WD
	 * @date 2017-9-21 下午4:19:48
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getLastRepayPlanList")
	public MfRepayPlan getLastRepayPlanList(@RequestBody MfRepayPlan lastMfRepayPlan)throws Exception;
	/**
	 * 
	 * 方法描述：通过overId 更新逾期基数情况表
	 * @param baseRecordUpd
	 * @throws Exception 
	 * void
	 * @author WD
	 * @date 2017-9-21 下午8:26:57
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/updateMfBusOverBaseRecord")
	public void updateMfBusOverBaseRecord(@RequestBody MfBusOverBaseRecord baseRecordUpd)throws Exception;

	
	
	/**
	 * 
	 * 方法描述： 套打获取放款信息
	 * @param mapParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author lzshuai
	 * @date 2017-9-20 上午9:42:25
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getTaDaMessageForFK")
	public Map<String,Object> getTaDaMessageForFK(@RequestBody Map<String, String> mapParm) throws Exception;
	/**
	 * 
	 * 方法描述： 获取还款信息
	 * @param hkmap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author lzshuai
	 * @date 2017-9-20 下午2:43:46
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getTaDaMessageForHK")
	public Map<String, Object> getTaDaMessageForHK(@RequestBody Map<String, String> hkmap) throws Exception;
	/**
	 * 
	 * 方法描述：添加逾期基数情况记录表
	 * @param baseRecordInsert
	 * @throws Exception 
	 * void
	 * @author WD
	 * @date 2017-9-22 下午7:02:16
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/insertMfBusOverBaseRecord")
	public void insertMfBusOverBaseRecord(@RequestBody MfBusOverBaseRecord baseRecordInsert)throws Exception;
	/**
	 * 通过借据号查询还款计划历史列表
	 * @param mfRepayPlan
	 * @return
	 * @throws Exception
	 * @author ywh
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getMfRepayHistoryListById")
	public List<MfRepayHistory> getMfRepayHistoryListById(@RequestBody String fincId )throws Exception;
	/**
	 * 通过借据号查询逾期还款每期数据
	 * @param mfRepayPlan
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getMfBusRepayPlanListOnlyOne")
	public List<MfRepayPlan> getMfBusRepayPlanListOnlyOne(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;

	/**
	 * 
	 * 方法描述： 通过借据号 期号 或者 还款计划id 获取一期还款计划
	 * @param mfRepayPlan
	 * @return
	 * @throws Exception
	 * MfRepayPlan
	 * @author 沈浩兵
	 * @date 2017-11-4 下午4:23:17
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getRepayPlanByBean")
	public MfRepayPlan getRepayPlanByBean(@RequestBody MfRepayPlan mfRepayPlan) throws Exception ;
	/**
	 * @Description 通过客户号判断该客户是否存在逾期合同
	 * @Author zhaomingguang
	 * @DateTime 2019/9/20 14:46
	 * @Param 
	 * @return 
	 */
	@RequestMapping(value = "/calcRepayPlanInterface/getOverDueFlag")
	int getOverDueFlag(String cusNo);
}


