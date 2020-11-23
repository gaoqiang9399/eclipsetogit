package app.component.pss.information.entity;

import app.base.BaseDomain;

public class PssPrintOtherRecBill extends BaseDomain{

	private static final long serialVersionUID = -8757197605782666482L;;
	
	private String cusName;
	private String billDate;
	private String otherRecNo;
	private String optName;
	private Double recAmt;
	private String regOpName;
	private Integer sequence;
	private String optNameDetail;
	private Double recAmtDetail;
	private String memo;
	public String getCusName() {
		return cusName;
	}
	public String getBillDate() {
		return billDate;
	}
	public String getOtherRecNo() {
		return otherRecNo;
	}
	public String getOptName() {
		return optName;
	}
	public Double getRecAmt() {
		return recAmt;
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
	public Double getRecAmtDetail() {
		return recAmtDetail;
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
	public void setOtherRecNo(String otherRecNo) {
		this.otherRecNo = otherRecNo;
	}
	public void setOptName(String optName) {
		this.optName = optName;
	}
	public void setRecAmt(Double recAmt) {
		this.recAmt = recAmt;
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
	public void setRecAmtDetail(Double recAmtDetail) {
		this.recAmtDetail = recAmtDetail;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
