package app.component.pss.information.entity;

import app.base.BaseDomain;

public class PssPrintOtherPayBill extends BaseDomain{
	private static final long serialVersionUID = -8757197605782666483L;;
	
	private String supplierName;
	private String billDate;
	private String otherPayNo;
	private String optName;
	private Double payAmt;
	private String regOpName;
	private Integer sequence;
	private String optNameDetail;
	private Double payAmtDetail;
	private String memo;
	public String getSupplierName() {
		return supplierName;
	}
	public String getBillDate() {
		return billDate;
	}
	public String getOtherPayNo() {
		return otherPayNo;
	}
	public String getOptName() {
		return optName;
	}
	public Double getPayAmt() {
		return payAmt;
	}
	public String getRegOpName() {
		return regOpName;
	}
	public Integer getSequence() {
		return sequence;
	}
	public String getOptNameDetail() {
		return optNameDetail;
	}
	public Double getPayAmtDetail() {
		return payAmtDetail;
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
	public void setOtherPayNo(String otherPayNo) {
		this.otherPayNo = otherPayNo;
	}
	public void setOptName(String optName) {
		this.optName = optName;
	}
	public void setPayAmt(Double payAmt) {
		this.payAmt = payAmt;
	}
	public void setRegOpName(String regOpName) {
		this.regOpName = regOpName;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public void setOptNameDetail(String optNameDetail) {
		this.optNameDetail = optNameDetail;
	}
	public void setPayAmtDetail(Double payAmtDetail) {
		this.payAmtDetail = payAmtDetail;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
