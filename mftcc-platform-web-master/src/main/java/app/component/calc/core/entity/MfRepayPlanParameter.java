package app.component.calc.core.entity;

/**
 * Title: MfRepayPlanParameter.java Description: 还款计划参数 调用还款计划接口时传入的参数实体，全部都传
 * 
 * @author：wd
 * @Tue May 16 10:42:10 CST 2017
 * @version：1.0
 **/
public class MfRepayPlanParameter {
	private String cusNo;// 客户编号
	private String cusName;// 客户名称
	private String pactId;// 合同编号
	private String fincId;// 借据号，支用申请id
	private Double pactAmt;// 合同金额
	private String repayType;// 还款方式
	private String putoutDate;// 开始日期（放款日期）
	private String endDate;// 到期日期（合同到期日期）
	private String termMonth;// 贷款期限（月）
	private String loanWeek;// 贷款期限（周）（按周还款）
	private Double loanAmt;// 借据金额
	private String fincRate;// 年利率
	private String intstType;// 计息类型 （M:按月 D：按天）
	private String intstDays;// 计息天数 （360/365）
	private String prcpRatio;// 每期还本比例 (灵活还款)
	private String plnStr;// 定义日期金额(定义日期金额的格式为：第一期本金比例，第二期本金比例，第三期本金比例 02,02,06)
							// （灵活还款）
	private String intstBase;// 利息计算基础 (按周还款：ALL-贷款金额|BAL-剩余本金)
	private String endDay;// 还款日(每期的固定还款日)
	private String prcpAmt;// 每期本金 （灵活还款）
	/**
	 * 还款日方式  1-固定还款日|2-随放款日
	 */
	private String repayDayType;
	private String fixedRepayDayType;// 固定还款日还款方式
	private String fixedRepayDay;// 固定还款日
	private String notAdequacyCalType;// 不足期计息类型
	private String decimalDigits;// 保留小数位数（四舍五入）
	private String periodType;// 周期类型 1-按季 2-按月
	private String interestReckonMode;// 计息方式 1-按月计息 2-按实际天数计息
	private String interestCollectType;// 利息收息方式：1-上收息 2-下收息
	private String feeCollectWay;// 费用收取方式：1-上收费 2-下收费  parm_dic表key_name=FEE_COLLECT_WAY
	private String multipleLoanPlanMerge;// 多次放款还款计划合并：1-启用、0-禁用
	private String rounding;// 四舍五入 2
	private String interestCalBaseType;// 利息计算基数类型 1-贷款本金 2-贷款余额 (按周期天数还款使用)
	private String calcIntstFlag;//利息计算区间标志  1-算头不算尾 2-首尾都计算
	private String cycleDays;// 还款周期（天数）
	private String opNo;// 登记人员编号
	private String opName;// 登记人员名称
	private String brNo;// 登记人员部门编号
	private String brName;// 登记人员部门名称
	/**
	 * 规则唯一编号
	 */
	private String rulesNo;
	
	/**
	 * 还款日设置：1-贷款发放日 2-月末 3-固定还款日 parm_dic表key_name=REPAY_DATE_SET_TYPE
	 */
	private String repayDateSet;

	private String periodAdjust;//调整期数（按计划还款方式使用）

	public String getPactId() {
		return pactId;
	}

	public void setPactId(String pactId) {
		this.pactId = pactId;
	}

	public String getFincId() {
		return fincId;
	}

	public void setFincId(String fincId) {
		this.fincId = fincId;
	}

	public Double getPactAmt() {
		return pactAmt;
	}

	public void setPactAmt(Double pactAmt) {
		this.pactAmt = pactAmt;
	}

	public String getPutoutDate() {
		return putoutDate;
	}

	public void setPutoutDate(String putoutDate) {
		this.putoutDate = putoutDate;
	}

	public String getTermMonth() {
		return termMonth;
	}

	public void setTermMonth(String termMonth) {
		this.termMonth = termMonth;
	}

	public Double getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(Double loanAmt) {
		this.loanAmt = loanAmt;
	}

	public String getFincRate() {
		return fincRate;
	}

	public void setFincRate(String fincRate) {
		this.fincRate = fincRate;
	}

	public String getOpNo() {
		return opNo;
	}

	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getBrName() {
		return brName;
	}

	public void setBrName(String brName) {
		this.brName = brName;
	}

	public String getCusNo() {
		return cusNo;
	}

	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getIntstDays() {
		return intstDays;
	}

	public void setIntstDays(String intstDays) {
		this.intstDays = intstDays;
	}

	public String getPrcpRatio() {
		return prcpRatio;
	}

	public void setPrcpRatio(String prcpRatio) {
		this.prcpRatio = prcpRatio;
	}

	public String getPlnStr() {
		return plnStr;
	}

	public void setPlnStr(String plnStr) {
		this.plnStr = plnStr;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getIntstType() {
		return intstType;
	}

	public void setIntstType(String intstType) {
		this.intstType = intstType;
	}

	public String getLoanWeek() {
		return loanWeek;
	}

	public void setLoanWeek(String loanWeek) {
		this.loanWeek = loanWeek;
	}

	public String getIntstBase() {
		return intstBase;
	}

	public void setIntstBase(String intstBase) {
		this.intstBase = intstBase;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public String getPrcpAmt() {
		return prcpAmt;
	}

	public void setPrcpAmt(String prcpAmt) {
		this.prcpAmt = prcpAmt;
	}
    /**
     * 1-固定还款日|2-随放款日
     */
	public String getRepayDayType() {
		return repayDayType;
	}
    /**
     * 1-固定还款日|2-随放款日
     */
	public void setRepayDayType(String repayDayType) {
		this.repayDayType = repayDayType;
	}

	public String getFixedRepayDayType() {
		return fixedRepayDayType;
	}

	public void setFixedRepayDayType(String fixedRepayDayType) {
		this.fixedRepayDayType = fixedRepayDayType;
	}

	public String getFixedRepayDay() {
		return fixedRepayDay;
	}

	public void setFixedRepayDay(String fixedRepayDay) {
		this.fixedRepayDay = fixedRepayDay;
	}

	public String getNotAdequacyCalType() {
		return notAdequacyCalType;
	}

	public void setNotAdequacyCalType(String notAdequacyCalType) {
		this.notAdequacyCalType = notAdequacyCalType;
	}

	public String getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(String decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public String getInterestReckonMode() {
		return interestReckonMode;
	}

	public void setInterestReckonMode(String interestReckonMode) {
		this.interestReckonMode = interestReckonMode;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
    /**
     * 利息收息方式：1-上收息 2-下收息
     */
	public String getInterestCollectType() {
		return interestCollectType;
	}
    /**
     * 利息收息方式：1-上收息 2-下收息
     */
	public void setInterestCollectType(String interestCollectType) {
		this.interestCollectType = interestCollectType;
	}

	public String getMultipleLoanPlanMerge() {
		return multipleLoanPlanMerge;
	}

	public void setMultipleLoanPlanMerge(String multipleLoanPlanMerge) {
		this.multipleLoanPlanMerge = multipleLoanPlanMerge;
	}

	public String getRounding() {
		return rounding;
	}

	public void setRounding(String rounding) {
		this.rounding = rounding;
	}

	public String getInterestCalBaseType() {
		return interestCalBaseType;
	}

	public void setInterestCalBaseType(String interestCalBaseType) {
		this.interestCalBaseType = interestCalBaseType;
	}

	public String getCycleDays() {
		return cycleDays;
	}

	public void setCycleDays(String cycleDays) {
		this.cycleDays = cycleDays;
	}

	/**
	 * 规则唯一编号
	 */
	public String getRulesNo() {
		return rulesNo;
	}

	/**
	 * 规则唯一编号
	 */
	public void setRulesNo(String rulesNo) {
		this.rulesNo = rulesNo;
	}

    /**
     * 利息计算区间标志  1-算头不算尾 2-首尾都计算               
     */
	public String getCalcIntstFlag() {
		return calcIntstFlag;
	}
    /**
     * 利息计算区间标志  1-算头不算尾 2-首尾都计算               
     */
	public void setCalcIntstFlag(String calcIntstFlag) {
		this.calcIntstFlag = calcIntstFlag;
	}

	/**
	 * 还款日设置：1-贷款发放日 2-月末 3-固定还款日 parm_dic表key_name=REPAY_DATE_SET_TYPE
	 */
	public String getRepayDateSet() {
		return repayDateSet;
	}

	/**
	 * 还款日设置：1-贷款发放日 2-月末 3-固定还款日 parm_dic表key_name=REPAY_DATE_SET_TYPE
	 */
	public void setRepayDateSet(String repayDateSet) {
		this.repayDateSet = repayDateSet;
	}
    /**
     * 费用收取方式：1-上收费 2-下收费  parm_dic表key_name=FEE_COLLECT_WAY
     */
	public String getFeeCollectWay() {
		return feeCollectWay;
	}
    /**
     * 费用收取方式：1-上收费 2-下收费  parm_dic表key_name=FEE_COLLECT_WAY
     */
	public void setFeeCollectWay(String feeCollectWay) {
		this.feeCollectWay = feeCollectWay;
	}
	/**
	 *调整期数（按计划还款方式使用）
	 */
	public String getPeriodAdjust() {
		return periodAdjust;
	}
	/**
	 *调整期数（按计划还款方式使用）
	 */

	public void setPeriodAdjust(String periodAdjust) {
		this.periodAdjust = periodAdjust;
	}
}