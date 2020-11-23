package app.component.finance.othreport.entity;

public class CapJournalBean {
	private String voucherDate;//日期
	private String pwNo;//凭证字号
	private String remark;//摘要
	private String otherCom;//对方科目
	private String drAmt;//借方发生额
	private String crAmt;//贷方发生额
	private String dcInd;//借贷方向
	private String balance;//余额
	public String getVoucherDate() {
		return voucherDate;
	}
	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}
	public String getPwNo() {
		return pwNo;
	}
	public void setPwNo(String pwNo) {
		this.pwNo = pwNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOtherCom() {
		return otherCom;
	}
	public void setOtherCom(String otherCom) {
		this.otherCom = otherCom;
	}
	public String getDrAmt() {
		return drAmt;
	}
	public void setDrAmt(String drAmt) {
		this.drAmt = drAmt;
	}
	public String getCrAmt() {
		return crAmt;
	}
	public void setCrAmt(String crAmt) {
		this.crAmt = crAmt;
	}
	public String getDcInd() {
		return dcInd;
	}
	public void setDcInd(String dcInd) {
		this.dcInd = dcInd;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}
