/**
 * Copyright (C) DXHM 版权所有
 * 文件名： RepayPlanBean.java
 * 包名： app.component.rules.entity
 * 说明：
 * @author wd
 * @date 2017-05-17
 * @version V1.0
 */ 
package app.component.rules.entity;

import java.util.Map;
/**
 * 类名： RepayPlanBean
 * 描述：还款计划规则使用实体
 * @author wd
 * @date 2017-05-17
 *
 *
 */
public class RepayPlanBean {	
	private String loanMon;//贷款期限月
	private String repayTerm;//还款周期指每期包含多少月
	private String loanTerm;//还款期数
	private String loanRate;//执行利率
	private String termRate;//周期利率
	private String loanAmt;//贷款金额
	private String startDate;//开始日期
	private String perdIntstAmt;//每期期供
	private String loanBal;//剩余本金
	private String normInt;//每期利息
	private String prcpAmt;//每期本金
	private String endDate;//到期日期
	private String perdEndDate;//每期还款日期
	private String perdNo;//当前期数
	private String intstType;//计息类型  M按月计息 D 按天计息
	private String intstDays;//计息天数 360/365
	private String prcpRatio;//每期还本比例 (0.2) 
	private String plnStr;//定义日期金额(定义日期金额的格式为：第一期本金比例，第二期本金比例，第三期本金比例   02,02,06) 
	private String loanWeek;//贷款期限（周）（按周还款）
	private String intstBase;//利息计算基础 (按周还款：ALL-贷款金额|BAL-剩余本金)
	private String endDay;//还款日(每期的固定还款日)
	private String repayDayType;//还款日方式
	private String fixedRepayDayType;//固定还款日还款方式
	private String fixedRepayDay;//固定还款日   等额本息
	private String notAdequacyCalType;//不足期计息类型
	private String interestReckonMode;//计息方式  1-按月计息  2-按实际天数计息
	private String decimalDigits;//保留小数位数（四舍五入）
	private String interestDays;//计息天数    等额本息
	private String yearRate;//年利率    等额本息
	private String periodSum;//期供期数
	private String monthRate;//月利率
	private String periodAmount;//期供金额
	private String capital1stDeductAmt;//本金首次扣除金额
	private String periodEndDate;//每期结束日期
	private String periodStartDate;//每期开始日期
	private String periodNum;//每期期数
	private String periodCapital;//每期应还本金
	private String periodInterest;//每期应还利息
	private String periodRepayAmount;//每期还款总额
	private String capitalBalance;//本金余额
	private String repayDay;//还款日
	private String isAdequacy;//是否足期
	private String dayRate;//日利率
	private String days;//天数
	private String loanDay;//放款日
	private String isLoanDayEnd;//放款日是否是月末
	private String isBothDayEnd;//固还和放款日是否都是月末
	private String periodType;//周期类型   1-按季   2-按月
	private String months;//月数
	private String interestSum;//利息合计
	private String seasonStartDate;//每期开始日期（按季）
	private String seasonNum;//每期期数（按季）
	
	//获得结果
    private Map<String,Object> resultMap;
	public String getLoanMon() {
		return loanMon;
	}
	public void setLoanMon(String loanMon) {
		this.loanMon = loanMon;
	}
	public String getRepayTerm() {
		return repayTerm;
	}
	public void setRepayTerm(String repayTerm) {
		this.repayTerm = repayTerm;
	}
	public String getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(String loanTerm) {
		this.loanTerm = loanTerm;
	}
	public String getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}
	public String getTermRate() {
		return termRate;
	}
	public void setTermRate(String termRate) {
		this.termRate = termRate;
	}
	public String getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(String loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getPerdIntstAmt() {
		return perdIntstAmt;
	}
	public void setPerdIntstAmt(String perdIntstAmt) {
		this.perdIntstAmt = perdIntstAmt;
	}
	public String getLoanBal() {
		return loanBal;
	}
	public void setLoanBal(String loanBal) {
		this.loanBal = loanBal;
	}
	public String getNormInt() {
		return normInt;
	}
	public void setNormInt(String normInt) {
		this.normInt = normInt;
	}
	public String getPrcpAmt() {
		return prcpAmt;
	}
	public void setPrcpAmt(String prcpAmt) {
		this.prcpAmt = prcpAmt;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPerdEndDate() {
		return perdEndDate;
	}
	public void setPerdEndDate(String perdEndDate) {
		this.perdEndDate = perdEndDate;
	}
	public String getPerdNo() {
		return perdNo;
	}
	public void setPerdNo(String perdNo) {
		this.perdNo = perdNo;
	}
	public Map<String, Object> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	public String getIntstType() {
		return intstType;
	}
	public void setIntstType(String intstType) {
		this.intstType = intstType;
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
	public String getRepayDayType() {
		return repayDayType;
	}
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
	public String getInterestDays() {
		return interestDays;
	}
	public void setInterestDays(String interestDays) {
		this.interestDays = interestDays;
	}
	public String getYearRate() {
		return yearRate;
	}
	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}
	public String getPeriodSum() {
		return periodSum;
	}
	public void setPeriodSum(String periodSum) {
		this.periodSum = periodSum;
	}
	public String getMonthRate() {
		return monthRate;
	}
	public void setMonthRate(String monthRate) {
		this.monthRate = monthRate;
	}
	public String getPeriodAmount() {
		return periodAmount;
	}
	public void setPeriodAmount(String periodAmount) {
		this.periodAmount = periodAmount;
	}
	public String getCapital1stDeductAmt() {
		return capital1stDeductAmt;
	}
	public void setCapital1stDeductAmt(String capital1stDeductAmt) {
		this.capital1stDeductAmt = capital1stDeductAmt;
	}
	public String getPeriodEndDate() {
		return periodEndDate;
	}
	public void setPeriodEndDate(String periodEndDate) {
		this.periodEndDate = periodEndDate;
	}
	public String getPeriodStartDate() {
		return periodStartDate;
	}
	public void setPeriodStartDate(String periodStartDate) {
		this.periodStartDate = periodStartDate;
	}
	public String getPeriodNum() {
		return periodNum;
	}
	public void setPeriodNum(String periodNum) {
		this.periodNum = periodNum;
	}
	public String getPeriodCapital() {
		return periodCapital;
	}
	public void setPeriodCapital(String periodCapital) {
		this.periodCapital = periodCapital;
	}
	public String getPeriodInterest() {
		return periodInterest;
	}
	public void setPeriodInterest(String periodInterest) {
		this.periodInterest = periodInterest;
	}
	public String getPeriodRepayAmount() {
		return periodRepayAmount;
	}
	public void setPeriodRepayAmount(String periodRepayAmount) {
		this.periodRepayAmount = periodRepayAmount;
	}
	public String getCapitalBalance() {
		return capitalBalance;
	}
	public void setCapitalBalance(String capitalBalance) {
		this.capitalBalance = capitalBalance;
	}
	public String getRepayDay() {
		return repayDay;
	}
	public void setRepayDay(String repayDay) {
		this.repayDay = repayDay;
	}
	public String getIsAdequacy() {
		return isAdequacy;
	}
	public void setIsAdequacy(String isAdequacy) {
		this.isAdequacy = isAdequacy;
	}
	public String getDayRate() {
		return dayRate;
	}
	public void setDayRate(String dayRate) {
		this.dayRate = dayRate;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getLoanDay() {
		return loanDay;
	}
	public void setLoanDay(String loanDay) {
		this.loanDay = loanDay;
	}
	public String getIsLoanDayEnd() {
		return isLoanDayEnd;
	}
	public void setIsLoanDayEnd(String isLoanDayEnd) {
		this.isLoanDayEnd = isLoanDayEnd;
	}
	public String getIsBothDayEnd() {
		return isBothDayEnd;
	}
	public void setIsBothDayEnd(String isBothDayEnd) {
		this.isBothDayEnd = isBothDayEnd;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	public String getInterestSum() {
		return interestSum;
	}
	public void setInterestSum(String interestSum) {
		this.interestSum = interestSum;
	}
	public String getSeasonStartDate() {
		return seasonStartDate;
	}
	public void setSeasonStartDate(String seasonStartDate) {
		this.seasonStartDate = seasonStartDate;
	}
	public String getSeasonNum() {
		return seasonNum;
	}
	public void setSeasonNum(String seasonNum) {
		this.seasonNum = seasonNum;
	}
	public String getInterestReckonMode() {
		return interestReckonMode;
	}
	public void setInterestReckonMode(String interestReckonMode) {
		this.interestReckonMode = interestReckonMode;
	}
	
}
