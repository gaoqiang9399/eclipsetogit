package app.component.pss.information.entity;

import app.base.BaseDomain;

public class PssPrintPaymentBill extends BaseDomain{

	private static final long serialVersionUID = -8757197605782666481L;
	
	private String supplierName;
	private String billDate;
	private String paymentNo;
	private String opName;
	private String regOpName;
	private Integer sequence;
	private String accountName;
	private Double payAmt;
	private String optName;
	private String clearanceAccountNum;
	private String memo;
	public String getSupplierName() {
		return supplierName;
	}
	public String getBillDate() {
		return billDate;
	}
	public String getPaymentNo() {
		return paymentNo;
	}
	public String getOpName() {
		return opName;
	}
	public String getRegOpName() {
		return regOpName;
	}
	public String getAccountName() {
		return accountName;
	}
	public Double getPayAmt() {
		return payAmt;
	}
	public String getOptName() {
		return optName;
	}
	public String getMemo() {
		return memo;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public void setRegOpName(String regOpName) {
		this.regOpName = regOpName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public void setPayAmt(Double payAmt) {
		this.payAmt = payAmt;
	}
	public void setOptName(String optName) {
		this.optName = optName;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getClearanceAccountNum() {
		return clearanceAccountNum;
	}
	public void setClearanceAccountNum(String clearanceAccountNum) {
		this.clearanceAccountNum = clearanceAccountNum;
	}
	
}
