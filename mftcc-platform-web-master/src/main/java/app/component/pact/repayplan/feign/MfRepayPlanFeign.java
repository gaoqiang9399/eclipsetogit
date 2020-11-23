package app.component.pact.repayplan.feign;

import app.base.ServiceException;
import app.component.app.entity.MfBusAppKind;
import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.core.entity.MfRepayPlanChild;
import app.component.calc.core.entity.MfRepayPlanParameter;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusFincAppChild;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfRepayPlanFeign {

	@RequestMapping(value = "/mfRepayPlan4Pact/updateMfBusFincAppAndChild")
	public void updateMfBusFincAppAndChild(@RequestBody MfBusFincApp mfBusFincApp,
			@RequestParam("mfBusFincAppChild") MfBusFincAppChild mfBusFincAppChild) throws ServiceException;

	/** app.component.pact.repayplan.controller.MfRepayPlanController */
	@RequestMapping(value = "/mfRepayPlan4Pact/doApprovalProcess",method = RequestMethod.POST)
	public Map<String, Object> doApprovalProcess(@RequestParam("beginDate") String beginDate,
			@RequestParam("endDate") String endDate, @RequestParam("planListData") String planListData, @RequestParam("planListSize") String planListSize,
			@RequestParam("repayFeeSum") String repayFeeSum, @RequestParam("repayIntstSum") String repayIntstSum,
			@RequestParam("multipleLoanPlanMerge") String multipleLoanPlanMerge, @RequestParam("interestCollectType") String interestCollectType,
			@RequestParam("putOutChargeIntstFlag") String putOutChargeIntstFlag, @RequestParam("putOutChargeIntst") String putOutChargeIntst,
			@RequestBody Map<String, Object> parmMap);

	@RequestMapping(value = "/mfRepayPlan4Pact/doApprovalProcessForBatch",method = RequestMethod.POST)
	public Map<String, Object> doApprovalProcessForBatch(@RequestParam("beginDate") String beginDate,
			@RequestParam("endDate") String endDate, @RequestParam("planListData") String planListData, @RequestParam("planListSize") String planListSize,
			@RequestParam("repayFeeSum") String repayFeeSum, @RequestParam("repayIntstSum") String repayIntstSum,
			@RequestParam("multipleLoanPlanMerge") String multipleLoanPlanMerge, @RequestParam("interestCollectType") String interestCollectType,
			@RequestParam("putOutChargeIntstFlag") String putOutChargeIntstFlag, @RequestParam("putOutChargeIntst") String putOutChargeIntst,
			@RequestBody Map<String, Object> parmMap);

	@RequestMapping(value = "/mfRepayPlan4Pact/doApprovalProcessForAccount",method = RequestMethod.POST)
	public Map<String, Object> doApprovalProcessForAccount(@RequestBody Map<String, Object> parmMap);
	@RequestMapping(value = "/mfRepayPlan4Pact/getDateAndMoneyFormat")
	public List<MfRepayPlan> getDateAndMoneyFormat(@RequestBody List<MfRepayPlan> repayPlanList, @RequestParam("digits") String digits) throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/getDateAndMoneyFormatStr")
	public List<MfRepayPlan> getDateAndMoneyFormatStr(@RequestBody List<MfRepayPlan> repayPlanList,
			@RequestParam("digits") String digits) throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/changePlanByPrcp")
	public Map<String, Object> changePlanByPrcp(@RequestBody List<MfRepayPlan> repayPlanList,
			@RequestParam("mfRepayPlan4PactParameter") MfRepayPlanParameter mfRepayPlan4PactParameter, @RequestParam("mapParm") Map<String, String> mapParm)
			throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/changePlanByEndDate")
	public Map<String, Object> changePlanByEndDate(@RequestBody List<MfRepayPlan> repayPlanList,
			@RequestParam("mfRepayPlan4PactParameter") MfRepayPlanParameter mfRepayPlan4PactParameter, @RequestParam("mapParm") Map<String, String> mapParm)
			throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/changePlanByIntst")
	public Map<String, Object> changePlanByIntst(@RequestBody List<MfRepayPlan> repayPlanList,
			@RequestParam("mapParm") Map<String, String> mapParm) throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/getRepayIntstByRepayPrcpParam")
	public Double getRepayIntstByRepayPrcpParam(@RequestParam("repayPrcpBal") double repayPrcpBal, @RequestParam("loanAmt") double loanAmt,
			@RequestParam("normCalcBase") int normCalcBase, @RequestParam("repayPrcpBal") int normCalcType, @RequestParam("repayPrcpBal") int intstCalcLessCycle,
			@RequestParam("mouthRate") double mouthRate, @RequestParam("dayRate") double dayRate, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate);

	@RequestMapping(value = "/mfRepayPlan4Pact/getRepayIntstByEndDateParam")
	public Double getRepayIntstByEndDateParam(@RequestParam("repayPrcpBal") double repayPrcpBal, @RequestParam ("loanAmt")double loanAmt,
			@RequestParam("normCalcBase") int normCalcBase, @RequestParam("normCalcType") int normCalcType, @RequestParam("intstCalcLessCycle") int intstCalcLessCycle,
			@RequestParam("mouthRate") double mouthRate, @RequestParam("dayRate") double dayRate, @RequestParam ("startDate")String startDate,
			@RequestParam("endDate") String endDate);

	@RequestMapping(value = "/mfRepayPlan4Pact/getOnChargeInterest")
	public Map<String, Object> getOnChargeInterest(@RequestBody List<MfRepayPlan> repayPlanList,
			@RequestParam("mfBusFincAppChild") MfBusFincAppChild mfBusFincAppChild, @RequestParam("digits") String digits);

	@RequestMapping(value = "/mfRepayPlan4Pact/insertMfRepayHistory")
	public void insertMfRepayHistory(@RequestBody MfRepayHistory mfRepayHistory,
			@RequestParam("mfBusFincAppChild") MfBusFincAppChild mfBusFincAppChild) throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/insertMfRepayHistoryDetail")
	public void insertMfRepayHistoryDetail(@RequestBody MfRepayHistory mfRepayHistory,
			@RequestParam("mfBusFincAppChild") MfBusFincAppChild mfBusFincAppChild, @RequestParam("repayPlanList") List<MfRepayPlan> repayPlanList)
			throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/getRepayPlanChildList")
	public List<MfRepayPlanChild> getRepayPlanChildList(@RequestBody Map<String, String> formMap,
														@RequestParam("planListSize") int planListSize, @RequestParam("mfBusFincAppChild") MfBusFincAppChild mfBusFincAppChild);

	@RequestMapping(value = "/mfRepayPlan4Pact/changeBeginDateAndEndDateByParam")
	public List<MfRepayPlan> changeBeginDateAndEndDateByParam(@RequestBody List<MfRepayPlan> repayPlanList,
			@RequestParam("mfRepayPlan4PactParameter") MfRepayPlanParameter mfRepayPlan4PactParameter, @RequestParam("beginDateHidden") String beginDateHidden);

	@RequestMapping(value = "/mfRepayPlan4Pact/getRepayPlanList")
	public Map<String, Object> getRepayPlanList(@RequestParam("appId") String appId, @RequestBody Map<String, String> mapParm)
			throws Exception;

    @RequestMapping(value = "/mfRepayPlan4Pact/getPreRepayPlanList")
    public Map<String, Object> getPreRepayPlanList(@RequestParam("appId") String appId, @RequestParam("fincId") String fincId, @RequestBody Map<String, String> mapParm)
            throws Exception;

    @RequestMapping(value = "/mfRepayPlan4Pact/doEditorPrePlan", method = RequestMethod.POST)
    public Map<String, Object> doEditorPrePlan(@RequestParam("planListSize") String planListSize,
                                               @RequestBody Map<String, Object> parmMap);

	@RequestMapping(value = "/mfRepayPlan4Pact/getPlanListByBeginDate")
	public Map<String, Object> getPlanListByBeginDate(@RequestBody MfRepayPlanParameter mfRepayPlan4PactParameter,
			@RequestParam("mapParm") Map<String, String> mapParm) throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/getPlanListByEndDate")
	public Map<String, Object> getPlanListByEndDate(@RequestBody MfRepayPlanParameter mfRepayPlan4PactParameter,
			@RequestParam("mapParm") Map<String, String> mapParm) throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/getChangePlanByPrcp")
	public Map<String, Object> getChangePlanByPrcp(@RequestBody  Map<String,Object> parmMap)
			throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/getChangePlanByEndDate")
	public Map<String, Object> getChangePlanByEndDate(@RequestBody Map<String, Object> parmMap)
			throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/getChangePlanByIntst")
	public Map<String, Object> getChangePlanByIntst(@RequestBody Map<String,Object> parmMap)
			throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/getMfBusFincAppChildByInterestCollectType")
	public Map<String, Object> getMfBusFincAppChildByInterestCollectType(@RequestBody List<MfRepayPlan> planList,
			@RequestParam("mfBusFincAppChild") MfBusFincAppChild mfBusFincAppChild, @RequestParam("mfRepayPlan4PactParameter") MfRepayPlanParameter mfRepayPlan4PactParameter)
			throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/getfixEndDate")
	public String getfixEndDate(@RequestParam("repayDateSet") String repayDateSet, @RequestParam("repayDateDef") String repayDateDef,
			@RequestParam("intstEndDate") String intstEndDate, @RequestParam("calcIntstFlag") String calcIntstFlag);

	@RequestMapping(value = "/mfRepayPlan4Pact/getRepayPlanFeeSumList")
	public List<MfRepayPlan> getRepayPlanFeeSumList(@RequestBody List<MfRepayPlan> repayPlanList,
			@RequestParam("mfBusFincApp") MfBusFincApp mfBusFincApp, @RequestParam("mfBusAppKind") MfBusAppKind mfBusAppKind,
			@RequestParam("feePlanType") String feePlanType) throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/getRealTimePay")
	public Map<String, Object> getRealTimePay(@RequestBody MfBusFincAppChild mfBusFincAppChild,
			@RequestParam("isAutoValidateVerification") String isAutoValidateVerification) throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/doRepayPlanInsert")
	public Map<String, Object> doRepayPlanInsert(@RequestBody MfBusFincAppChild mfBusFincAppChild,
			@RequestParam("mfBusAppKind") MfBusAppKind mfBusAppKind, @RequestParam("parmMap") Map<String, String> parmMap) throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/doAutoLoan")
	public Map<String, Object> doAutoLoan(@RequestBody MfBusFincApp mfBusFincApp,
			@RequestParam("mfBusFincAppChild") MfBusFincAppChild mfBusFincAppChild, @RequestParam("mfBusAppKind") MfBusAppKind mfBusAppKind,
			@RequestParam("parmMap") Map<String, String> parmMap) throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/doLianLianRealTimePay")
	public Map<String, Object> doLianLianRealTimePay(@RequestBody MfBusFincApp mfBusFincApp,
			@RequestParam("beginDate")String beginDate, @RequestParam("endDate")String endDate, @RequestParam("planListData")String planListData,
			@RequestParam("planListSize")String planListSize,  @RequestParam("mfBusAppKind")String repayFeeSum, @RequestParam(" repayIntstSum")String repayIntstSum,
			@RequestParam("multipleLoanPlanMerge")String multipleLoanPlanMerge,  @RequestParam("interestCollectType")String interestCollectType,
			@RequestParam("putOutChargeIntstFlag")String putOutChargeIntstFlag,  @RequestParam("putOutChargeIntst")String putOutChargeIntst,
			@RequestParam("putOutFeeMap") Map<String, Object> putOutFeeMap) throws Exception;
	/**
	 * 
	 * 方法描述：三方放款发送网关请求
	 * @param paramMap
	 * @return
	 * @throws Exception 
	 * Map<String,Object>
	 * @author wd
	 * @date 2018年5月28日 下午8:38:59
	 */
	@RequestMapping(value = "/thirdPayGateWayInterfaceConT/doRepayPlan")
	public Map<String, Object> doRepayPlan(@RequestBody Map<String, Object> paramMap)throws Exception;

	/**
	 *
	 * 方法描述：判断该笔借据是否向网关发起过放款申请  如果发起过 不允许再次发起申请
	 * @param fincId 借据号
	 * @return  code 0000 可以放款  9999 已经放款成功 不能再次回调
	 * Map<String,String>
	 */
	@RequestMapping(value = "/thirdPayGateWayInterfaceConT/checkPutoutApplyInfoByFincId")
	public Map<String, String> checkPutoutApplyInfoByFincId(@RequestBody String fincId)throws Exception;

	@RequestMapping(value = "/mfRepayPlan4Pact/getPlanListByPeriod")
	public Map<String, Object> getPlanListByPeriod(@RequestBody MfRepayPlanParameter mfRepayPlan4PactParameter,
													@RequestParam("mapParm") Map<String, String> mapParm) throws Exception;
	/**
	 * 根据当前期号和借据号查询剩余还款计划（不包括当前期号）
	 */
	@RequestMapping(value = "/mfRepayPlan4Pact/getPlanListByQihaoAndFincId")
	public List<MfRepayPlan> getPlanListByQihaoAndFincId(@RequestBody @RequestParam("mapParm")Map<String, String> mapParm);


    @RequestMapping(value = "/mfRepayPlan4Pact/changePreInstCollectType")
    public Map<String, Object> changePreInstCollectType(@RequestParam("appId") String appId,
                                                        @RequestBody Map<String, String> mapParm) throws Exception;


	@RequestMapping(value = "/mfRepayPlan4Pact/findByPage")
	public Ipage findByPage(@RequestParam("appId") String appId) throws Exception;

}
