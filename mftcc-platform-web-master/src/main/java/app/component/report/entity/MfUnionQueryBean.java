package app.component.report.entity;

import app.base.BaseDomain;

public class MfUnionQueryBean extends BaseDomain{
	private static final long serialVersionUID = -5719242072837931231L;
	private String cifNo;
	private String loanNo;
	private String loanBal;
	private String loanObj;//对象
	private String loanOjbName;
	private String putoutAmt;//发放金额   算额度
	private String mainVouNo;//担保方式
	private String mainVouName;
	private String termMon;
	private String termDay;
	private String maxOverDay;
	private String repayAmt;
	private String dueState;
	private String dueBegDate;
	private String dueEndDate;
	private String overDate;
	private String returnmethodno;
	private String appRate;
	private String overAmt;
	private String overdueIntsts;
	private String baseRateno;
	private String boverDays;
	private String loverDays;
	private String crKind;
	
	
	public String getCrKind() {
		return crKind;
	}
	public void setCrKind(String crKind) {
		this.crKind = crKind;
	}
	public String getBoverDays() {
		return boverDays;
	}
	public void setBoverDays(String boverDays) {
		this.boverDays = boverDays;
	}
	public String getLoverDays() {
		return loverDays;
	}
	public void setLoverDays(String loverDays) {
		this.loverDays = loverDays;
	}
	public String getBaseRateno() {
		return baseRateno;
	}
	public void setBaseRateno(String baseRateno) {
		this.baseRateno = baseRateno;
	}
	public String getOverAmt() {
		return overAmt;
	}
	public void setOverAmt(String overAmt) {
		this.overAmt = overAmt;
	}
	public String getOverdueIntsts() {
		return overdueIntsts;
	}
	public void setOverdueIntsts(String overdueIntsts) {
		this.overdueIntsts = overdueIntsts;
	}
	public String getReturnmethodno() {
		return returnmethodno;
	}
	public void setReturnmethodno(String returnmethodno) {
		this.returnmethodno = returnmethodno;
	}
	public String getAppRate() {
		return appRate;
	}
	public void setAppRate(String appRate) {
		this.appRate = appRate;
	}
	public String getCifNo() {
		return cifNo;
	}
	public void setCifNo(String cifNo) {
		this.cifNo = cifNo;
	}
	public String getOverDate() {
		return overDate;
	}
	public void setOverDate(String overDate) {
		this.overDate = overDate;
	}
	public String getDueBegDate() {
		return dueBegDate;
	}
	public void setDueBegDate(String dueBegDate) {
		this.dueBegDate = dueBegDate;
	}
	public String getDueEndDate() {
		return dueEndDate;
	}
	public void setDueEndDate(String dueEndDate) {
		this.dueEndDate = dueEndDate;
	}
	public String getDueState() {
		return dueState;
	}
	public void setDueState(String dueState) {
		this.dueState = dueState;
	}
	public String getRepayAmt() {
		return repayAmt;
	}
	public void setRepayAmt(String repayAmt) {
		this.repayAmt = repayAmt;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getLoanBal() {
		return loanBal;
	}
	public void setLoanBal(String loanBal) {
		this.loanBal = loanBal;
	}
	public String getLoanObj() {
		return loanObj;
	}
	public void setLoanObj(String loanObj) {
		this.loanObj = loanObj;
	}
	public String getLoanOjbName() {
		return loanOjbName;
	}
	public void setLoanOjbName(String loanOjbName) {
		this.loanOjbName = loanOjbName;
	}
	public String getPutoutAmt() {
		return putoutAmt;
	}
	public void setPutoutAmt(String putoutAmt) {
		this.putoutAmt = putoutAmt;
	}
	public String getMainVouNo() {
		return mainVouNo;
	}
	public void setMainVouNo(String mainVouNo) {
		this.mainVouNo = mainVouNo;
	}
	public String getMainVouName() {
		return mainVouName;
	}
	public void setMainVouName(String mainVouName) {
		this.mainVouName = mainVouName;
	}
	public String getTermMon() {
		return termMon;
	}
	public void setTermMon(String termMon) {
		this.termMon = termMon;
	}
	public String getTermDay() {
		return termDay;
	}
	public void setTermDay(String termDay) {
		this.termDay = termDay;
	}
	public String getMaxOverDay() {
		return maxOverDay;
	}
	public void setMaxOverDay(String maxOverDay) {
		this.maxOverDay = maxOverDay;
	}
	
}
