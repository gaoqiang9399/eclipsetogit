package app.component.tools.entity;

import app.base.BaseDomain;
/**
 * 贷款计算器
 * @author Tangxj
 * 
 */
public class MfLoanCalculator extends BaseDomain {
	
	private String startDate;//开始日期 隐藏
	private Integer loanMon;//贷款期限（月）
	private String intstType;//计息类型
	private double loanRate;//年利率
	private double loanAmt;//贷款金额
	private Integer repayTerm;//还款周期 隐藏 1
	private String repayType;//还款方式 
	private double prcpAmt;//每期本金
	private double normInt;//每期利息
	private double perdIntstAmt;//每期期供
	private double loanBal;//剩余本金
	private double monRate;//月利率
	private double dayRate;// 日利率
	private Integer loanTerm;//贷款期数
	private Integer perdNo;//当前期数
	private String perdEndDate;//每期到期日期
	private String endDate;// 贷款到期日
	private String lstPerdEndDate;// 上期到期日
	private Integer betweenDays;// 间隔天数
	private Integer betweenMons;// 间隔月数
	private String ruleName;//规则名称
	
	private String repayDateState;//还款日状态1是固定还款日，2是贷款发放日
	private String regularRepayDate;//固定还款日
	private String provideDate;//发放日期
	//提前还款（暂没有接口）
	private String advRepayState;//提前还款状态，0-否，1-是
	private String advRepayDate;//提前还款时间
	private String advRepayType;//提前还款方式，0-一次性还清，1-部分还款
	private String advRepayMoney;//提前还款额
	private String opType;//提前部分还款处理方式，0-减少月还款额，1-缩短还款期限
	//期限类型
	private String termType;
	//合同编号
	private String appId;
	private String pactId;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Integer getLoanMon() {
		return loanMon;
	}
	public void setLoanMon(Integer loanMon) {
		this.loanMon = loanMon;
	}
	public String getIntstType() {
		return intstType;
	}
	public void setIntstType(String intstType) {
		this.intstType = intstType;
	}
	public double getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(double loanRate) {
		this.loanRate = loanRate;
	}
	public double getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}
	public Integer getRepayTerm() {
		return repayTerm;
	}
	public void setRepayTerm(Integer repayTerm) {
		this.repayTerm = repayTerm;
	}
	public String getRepayType() {
		return repayType;
	}
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	public double getPrcpAmt() {
		return prcpAmt;
	}
	public void setPrcpAmt(double prcpAmt) {
		this.prcpAmt = prcpAmt;
	}
	public double getNormInt() {
		return normInt;
	}
	public void setNormInt(double normInt) {
		this.normInt = normInt;
	}
	public double getPerdIntstAmt() {
		return perdIntstAmt;
	}
	public void setPerdIntstAmt(double perdIntstAmt) {
		this.perdIntstAmt = perdIntstAmt;
	}
	public double getLoanBal() {
		return loanBal;
	}
	public void setLoanBal(double loanBal) {
		this.loanBal = loanBal;
	}
	
	public double getMonRate() {
		return monRate;
	}
	public void setMonRate(double monRate) {
		this.monRate = monRate;
	}
	public double getDayRate() {
		return dayRate;
	}
	public void setDayRate(double dayRate) {
		this.dayRate = dayRate;
	}
	public Integer getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}
	public Integer getPerdNo() {
		return perdNo;
	}
	public void setPerdNo(Integer perdNo) {
		this.perdNo = perdNo;
	}
	public String getPerdEndDate() {
		return perdEndDate;
	}
	public void setPerdEndDate(String perdEndDate) {
		this.perdEndDate = perdEndDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getLstPerdEndDate() {
		return lstPerdEndDate;
	}
	public void setLstPerdEndDate(String lstPerdEndDate) {
		this.lstPerdEndDate = lstPerdEndDate;
	}
	public Integer getBetweenDays() {
		return betweenDays;
	}
	public void setBetweenDays(Integer betweenDays) {
		this.betweenDays = betweenDays;
	}
	public Integer getBetweenMons() {
		return betweenMons;
	}
	public void setBetweenMons(Integer betweenMons) {
		this.betweenMons = betweenMons;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRepayDateState() {
		return this.repayDateState;
	}
	public void setRepayDateState(String repayDateState) {
		this.repayDateState = repayDateState;
	}
	public String getRegularRepayDate() {
		return this.regularRepayDate;
	}
	public void setRegularRepayDate(String regularRepayDate) {
		this.regularRepayDate = regularRepayDate;
	}
	public String getProvideDate() {
		return this.provideDate;
	}
	public void setProvideDate(String provideDate) {
		this.provideDate = provideDate;
	}
	public String getAdvRepayState() {
		return this.advRepayState;
	}
	public void setAdvRepayState(String advRepayState) {
		this.advRepayState = advRepayState;
	}
	public String getAdvRepayDate() {
		return this.advRepayDate;
	}
	public void setAdvRepayDate(String advRepayDate) {
		this.advRepayDate = advRepayDate;
	}
	public String getAdvRepayType() {
		return this.advRepayType;
	}
	public void setAdvRepayType(String advRepayType) {
		this.advRepayType = advRepayType;
	}
	public String getAdvRepayMoney() {
		return this.advRepayMoney;
	}
	public void setAdvRepayMoney(String advRepayMoney) {
		this.advRepayMoney = advRepayMoney;
	}
	public String getOpType() {
		return this.opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public String getPactId() {
		return pactId;
	}
	public void setPactId(String pactId) {
		this.pactId = pactId;
	}
	@Override
	public String toString() {
		return "MfLoanCalculator [startDate=" + startDate + ", loanMon="
				+ loanMon + ", intstType=" + intstType + ", loanRate="
				+ loanRate + ", loanAmt=" + loanAmt + ", repayTerm="
				+ repayTerm + ", repayType=" + repayType + ", prcpAmt="
				+ prcpAmt + ", normInt=" + normInt + ", perdIntstAmt="
				+ perdIntstAmt + ", loanBal=" + loanBal + "]";
	}
	
}
