package app.component.pss.information.entity;

import app.base.BaseDomain;

public class PssPrintReceiptBill extends BaseDomain{

	private static final long serialVersionUID = -8757197605782666480L;
	
	private String cusName;
	private String billDate;
	private String receiptNo;
	private String opName;
	private String regOpName;
	private Integer sequence;
	private String accountName;
	private Double recAmt;
	private String optName;
	private String clearanceAccountNum;
	private String memo;
	public String getCusName() {
		return cusName;
	}
	public String getBillDate() {
		return billDate;
	}
	public String getReceiptNo() {
		return receiptNo;
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
	public Double getRecAmt() {
		return recAmt;
	}
	public String getOptName() {
		return optName;
	}
	public String getMemo() {
		return memo;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
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
	public void setRecAmt(Double recAmt) {
		this.recAmt = recAmt;
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
