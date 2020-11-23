package app.component.calccoreinterface;

import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.core.entity.MfBusFeePlanHistoryDetail;
import app.component.calc.core.entity.MfRepayBalDeduct;
import app.component.calc.core.entity.MfBusOverBaseRecord;
import app.component.calc.core.entity.MfRepayHistoryDetail;
import app.component.calc.core.entity.RepaymentParameter;
import app.component.calc.core.entity.MfRepayAmt;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.repay.entity.MfReceivableBean;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 还款接口
 * 
 * @author WD
 * 
 */
@FeignClient("mftcc-platform-factor")
public interface CalcRepaymentInterfaceFeign {
	/**
	 * 方法描述：通过借据号finc_id获取借据信息
	 * 
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getMfBusFincAppById")
	public MfBusFincApp getMfBusFincAppById(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	/**
	 * 获取还款页面 应还列表信息（从还款计划表中获取当期以及当期前未还款信息） 如果当前期已经还款 则显示下一期信息
	 * 
	 * @param mfBusFincApp
	 * @param repaymentParameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getMfReceivableBeanList")
	public List<MfReceivableBean> getMfReceivableBeanList(@RequestBody MfBusFincApp mfBusFincApp,@RequestParam("repaymentParameter")  RepaymentParameter repaymentParameter) throws Exception;

	/**
	 * 方法描述：查找还款的历史(目的是： 判断是否为第一次还款) 获取还款历史中还款次数
	 * 
	 * @param mfRepayObj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getHisContById")
	public int getHisContById(@RequestBody MfRepayHistory mfRepayObj) throws Exception;

	/**
	 * 还款操作总方法 ：正常还款 部分还款 逾期还款
	 * 
	 * @param parmMap
	 * @param planList
	 * @return
	 */
	@RequestMapping(value = "/calcRepaymentInterface/doRepaymentOperate")
	public Map<String, Object> doRepaymentOperate (@RequestBody Map<String, Object> parmMap, @RequestParam("planList") List<MfReceivableBean> planList) throws Exception;

	/**
	 * 获取还款相关信息 已还信息
	 * 
	 * @param mfRepayPlan
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getMfRepayPlanList")
	public List<MfRepayPlan> getMfRepayPlanList(@RequestBody MfRepayPlan mfRepayPlan) throws Exception;
    /**
     *
     * 功能描述:获取合并还款计划信息 借据表中状态
     * @param: [mfRepayPlan]
     * @return: java.util.List<app.component.calc.core.entity.MfRepayPlan>
     * @auther: wd
     * @date: 2018/12/5 15:20
     * 
     */
	@RequestMapping(value = "/calcRepaymentInterface/getMfRepayPlanMergeList")
	public List<MfRepayPlan> getMfRepayPlanMergeList(@RequestBody MfRepayPlan mfRepayPlan) throws Exception;

	/**
	 * 获取冲抵金额（当允许冲抵金额退款时 也是退款金额）
	 * 
	 * @param mfBusFincApp
	 * @return map 中 benCiChongDi 代表本次冲抵金额
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getBenCiChongDiByFincApp")
	public Map<String, String> getBenCiChongDiByFincApp(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	/**
	 * 获取还款页面数据展示接口
	 * 
	 * @param fincId 借据ID
	 * @param returnDate 还款日期
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepaymentInterface/doRepamentJsp")
	public Map<String, Object> doRepamentJsp(@RequestBody String fincId,@RequestParam("returnDate") String returnDate) throws Exception;

	/**
	 * 逾期批量 业务处理 1 更新还款计划表中 状态 变成逾期 2 添加一条逾期记录到逾期基数情况记录表中 3 更新逾期基数情况表中部分还款 逾期状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepaymentInterface/doOverBatch")
	public Map<String, String> doOverBatch() throws Exception;

	/**
	 * 还款日期修改时 重新计算相关还款数据
	 * 
	 * @param parmMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcRepaymentInterface/doRapayDateChange")
	public Map<String, Object> doRapayDateChange(@RequestBody Map<String, String> parmMap) throws Exception;

	/**
	 * 
	 * 方法描述： 逾期基数情况记录表,查询借据逾期情况
	 * 
	 * @param mfBusOverBaseRecord
	 * @return
	 * @throws Exception
	 *             List<MfBusOverBaseRecord>
	 * @author lwq
	 * @date 2017-6-28 上午10:56:53
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getMfBusOverBaseRecordByBean")
	public List<MfBusOverBaseRecord> getMfBusOverBaseRecordByBean(@RequestBody MfBusOverBaseRecord mfBusOverBaseRecord) throws Exception;

	/**
	 * 
	 * 方法描述： 查询还款历史信息
	 * 
	 * @param mfRepayHistoryDetail
	 * @return
	 * @throws Exception
	 *             MfRepayHistoryDetail
	 * @author lwq
	 * @date 2017-6-28 下午5:38:39
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getById")
	public MfRepayHistoryDetail getById(@RequestBody MfRepayHistoryDetail mfRepayHistoryDetail) throws Exception;
	/**
	 * 
	 * 方法描述：获取冲抵结余实体
	 * 
	 * @param mfRepayHistory
	 * @return MfRepayBalDeduct
	 * @author WD
	 * @date 2017-7-8 上午11:54:38
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getMfRepayBalDeductByMfRepayHistory")
	public MfRepayBalDeduct getMfRepayBalDeductByMfRepayHistory(@RequestBody MfRepayHistory mfRepayHistory) throws Exception;
	
	
	
	/**
	 * 
	 * 方法描述：通过借据号 获取 该 借据当前 应还信息（所有应还合计） 
	 * @param parmMap fincId 借据号 
	 * @return
	 * @throws Exception 
	 * Map<String,String>
	 * @author WD
	 * @date 2017-8-9 下午4:30:22
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getCurTermYingShouAmt")
	public MfRepayAmt getCurTermYingShouAmt(@RequestBody Map<String,String> parmMap) throws Exception;
	/**
	 * 获取客户下所有当期应还金额的总和
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-30 下午5:14:49
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getCurTermYingShouAmtByCusNo")
	public String getCurTermYingShouAmtByCusNo(@RequestBody String cusNo) throws Exception;
	
	/**
	 * 
	 * 方法描述：通过借据号 和 日期 date 获取 该 借据当前所有的 应还信息 
	 * @param parmMap fincId 借据号
	 * @return
	 * @throws Exception 
	 * Map<String,String>
	 * @author WD
	 * @date 2017-8-9 下午5:30:22
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getCurTermYingShouAmtList")
	public  List<MfRepayAmt> getCurTermYingShouAmtList(@RequestBody Map<String,String> parmMap) throws Exception;
	
	
	
	/**
	 * 
	 * 方法描述： 处理还款复核相关业务（原还款复核取消）
	 * @param mfBusFincAppUpd
	 * @param mfRepayHistory
	 * @throws Exception 
	 * void
	 * @author WD
	 * @date 2017-8-17 上午10:20:27
	 */
	@RequestMapping(value = "/calcRepaymentInterface/doRepayReview")
	public void doRepayReview(@RequestBody MfBusFincApp mfBusFincAppUpd,@RequestParam("mfRepayHistory") MfRepayHistory mfRepayHistory,@RequestParam("busFeePlanHistoryDetail") MfBusFeePlanHistoryDetail busFeePlanHistoryDetail)throws Exception;
	
	/**
	 * 
	 * 方法描述：通过借据号 和 还款计划状态 outFlag ('0','2','3') 获取还款计划列表
	 * @param mfRepayPlan
	 * @return
	 * @throws Exception 
	 * List<MfRepayPlan>
	 * @author WD
	 * @date 2017-8-19 下午2:48:59
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getMfBusRepayPlanList")
	public List<MfRepayPlan> getMfBusRepayPlanList(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;
	
	/**
	 * 
	 * 方法描述：查询所有的贷款余额
	 * @return
	 * @throws Exception 
	 * String
	 * @author WD
	 * @date 2017-8-21 上午10:49:09
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getAllLoanBal")
	public String getAllLoanBal()throws Exception; 
	
	/**
	 * 
	 * 方法描述：获取 正常贷款余额、关注贷款余额、次级贷款余额、可疑贷款余额、损失贷款余额
	 * @return bal_1 bal_2 bal_3 bal_4 bal_5
	 * @throws Exception 
	 * Map<String,String>
	 * @author WD
	 * @date 2017-8-21 下午2:27:26
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getFiveClassLoanBal")
	public List<Map<String, String>> getFiveClassLoanBal()throws Exception; 
	
	
    /**
     * 
     * 方法描述：通过借据号 期号 或者 还款计划id 获取一期还款计划
     * @param mfRepayPlan
     * @return
     * @throws Exception 
     * MfRepayPlan
     * @author WD
     * @date 2017-9-06 下午6:04:35
     */
	@RequestMapping(value = "/calcRepaymentInterface/getRepayPlanByBean")
	public MfRepayPlan getRepayPlanByBean(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;

	/**
	 * @Description:获取还款历史 
	 * @param mfRepayPlan
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-9-22 下午3:32:34
	 */
	@RequestMapping(value = "/calcRepaymentInterface/findByPage")
	public List<MfRepayPlan> findByPage(@RequestBody MfRepayPlan mfRepayPlan) throws Exception;
	/**
	 * 
	 * 方法描述： 记录引流方还款历史
	 * @param parmMap
	 * @param planList
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-30 下午3:48:42
	 */
	@RequestMapping(value = "/calcRepaymentInterface/insertRepayHistoryChannel")
	public Map<String,Object> insertRepayHistoryChannel (@RequestBody Map<String, Object> parmMap, @RequestParam("planList") List<MfReceivableBean> planList) throws Exception;
	/**
	 * @Description:获取今日还款历史 
	 * @param Ipage
	 * @return
	 * @throws Exception
	 * @author: 张冬磊
	 * @date: 2017-9-22 下午3:32:34
	 */
	@RequestMapping(value = "/calcRepaymentInterface/findByPageForCash")
	public Ipage findByPageForCash(@RequestBody Ipage ipg,@RequestParam("mfRepayHistory") MfRepayHistory mfRepayHistory) throws Exception;
	
	/**
	 * 
	 * 方法描述： 收取费用接口
	 * @param parmap
	 * void
	 * @author lwq
	 * @date 2018-1-6 下午3:15:26
	 */
	@RequestMapping(value = "/calcRepaymentInterface/doConnCwFincForFee")
	public void doConnCwFincForFee(@RequestBody Map<String, String> parmap);
	
	
	/**
	 * 
	 * 方法描述：计算违约金阶梯比例方法
	 * @param mfBusFincApp
	 * @param penaltyType
	 * @param overdueDays
	 * @return
	 * @throws Exception 
	 * String
	 * @author WD
	 * @date 2018-1-17 下午8:47:23
	 */
	@RequestMapping(value = "/calcRepaymentInterface/getPenaltyReceiveValueByFincInfo")
	public String getPenaltyReceiveValueByFincInfo(MfBusFincApp mfBusFincApp,
@RequestParam("penaltyType") 			String penaltyType,@RequestParam("overdueDays")  int overdueDays)throws Exception;
	
	 /**
     * 查看 逾期基数情况记录表 中信息 查看该期还款  再逾期基数情况表中 最近一次的数据记录 一条
     * @param busOverBaseRecord
     * @return
     */
	@RequestMapping(value = "/calcRepaymentInterface/getMfBusOverBaseRecord")
	public MfBusOverBaseRecord getMfBusOverBaseRecord(
			MfBusOverBaseRecord busOverBaseRecord)throws Exception;
	@RequestMapping(value = "/calcRepaymentInterface/doRepaymentByFincInfo")
	public Map<String, Object> doRepaymentByFincInfo(Map<String,String> parmMap)throws Exception;

}
