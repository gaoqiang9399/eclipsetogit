package app.component.report.entity;

import app.base.BaseDomain;

public class MfReportFinc extends BaseDomain{
	private String cusNo; 
	private String cusName;//客户名称
	private String appName;//项目名称
	private String fincAppAmt;//申请支用金额
	private String putoutAmt;//放款金额
	private String putoutDate;//放款日期
	private String endDateFinc;//到期日期
	private String repayedFincAmt;//已还金额
	private String unrepayFincBal;//未还金额
	private String fincSts;//申请状态
	private String fincRate;//利率
	private String termShow;//期数
	private String opNo;
	private String opName;//登记人
	private String keyName;
	private String optCode;
	private String optName;
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
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getFincAppAmt() {
		return fincAppAmt;
	}
	public void setFincAppAmt(String fincAppAmt) {
		this.fincAppAmt = fincAppAmt;
	}
	public String getPutoutAmt() {
		return putoutAmt;
	}
	public void setPutoutAmt(String putoutAmt) {
		this.putoutAmt = putoutAmt;
	}
	public String getPutoutDate() {
		return putoutDate;
	}
	public void setPutoutDate(String putoutDate) {
		this.putoutDate = putoutDate;
	}
	public String getEndDateFinc() {
		return endDateFinc;
	}
	public void setEndDateFinc(String endDateFinc) {
		this.endDateFinc = endDateFinc;
	}
	public String getRepayedFincAmt() {
		return repayedFincAmt;
	}
	public void setRepayedFincAmt(String repayedFincAmt) {
		this.repayedFincAmt = repayedFincAmt;
	}
	public String getUnrepayFincBal() {
		return unrepayFincBal;
	}
	public void setUnrepayFincBal(String unrepayFincBal) {
		this.unrepayFincBal = unrepayFincBal;
	}
	public String getFincSts() {
		return fincSts;
	}
	public void setFincSts(String fincSts) {
		this.fincSts = fincSts;
	}
	public String getFincRate() {
		return fincRate;
	}
	public void setFincRate(String fincRate) {
		this.fincRate = fincRate;
	}
	public String getTermShow() {
		return termShow;
	}
	public void setTermShow(String termShow) {
		this.termShow = termShow;
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
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getOptCode() {
		return optCode;
	}
	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}
	public String getOptName() {
		return optName;
	}
	public void setOptName(String optName) {
		this.optName = optName;
	}
	
}
