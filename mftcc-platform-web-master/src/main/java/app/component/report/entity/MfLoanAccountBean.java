package app.component.report.entity;

public class MfLoanAccountBean {
	private String program ;//统计项目
	private String yearBal ;//年初余额
	private String curBal ;//本期余额
	private String moreMoney ;//较上期增减金额
	private String recTotal ;//本期累收笔数
	private String recSum ;//本期累收金额
	private String loanTotal ;//本期累放笔数
	private String loanSum ;//本期累放金额
	
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getYearBal() {
		return yearBal;
	}
	public void setYearBal(String yearBal) {
		this.yearBal = yearBal;
	}
	public String getCurBal() {
		return curBal;
	}
	public void setCurBal(String curBal) {
		this.curBal = curBal;
	}
	public String getMoreMoney() {
		return moreMoney;
	}
	public void setMoreMoney(String moreMoney) {
		this.moreMoney = moreMoney;
	}
	public String getRecTotal() {
		return recTotal;
	}
	public void setRecTotal(String recTotal) {
		this.recTotal = recTotal;
	}
	public String getRecSum() {
		return recSum;
	}
	public void setRecSum(String recSum) {
		this.recSum = recSum;
	}
	public String getLoanTotal() {
		return loanTotal;
	}
	public void setLoanTotal(String loanTotal) {
		this.loanTotal = loanTotal;
	}
	public String getLoanSum() {
		return loanSum;
	}
	public void setLoanSum(String loanSum) {
		this.loanSum = loanSum;
	}
	
}
