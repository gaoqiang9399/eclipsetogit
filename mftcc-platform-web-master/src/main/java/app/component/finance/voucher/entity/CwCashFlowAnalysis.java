package app.component.finance.voucher.entity;
/**
 * 现金流量分析
 * @author Yanght
 *
 */
public class CwCashFlowAnalysis {
	private String uid;//凭证分录的唯一编号
	private String otherCom;//对方科目 accNo/accName
	private String cashType;//主表项目
	private String brfsAmt;//现金流量分析金额
	private String isInput;//0：流入  1：流出
	
	
	public String getIsInput() {
		return isInput;
	}
	public void setIsInput(String isInput) {
		this.isInput = isInput;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getOtherCom() {
		return otherCom;
	}
	public void setOtherCom(String otherCom) {
		this.otherCom = otherCom;
	}
	public String getCashType() {
		return cashType;
	}
	public void setCashType(String cashType) {
		this.cashType = cashType;
	}
	public String getBrfsAmt() {
		return brfsAmt;
	}
	public void setBrfsAmt(String brfsAmt) {
		this.brfsAmt = brfsAmt;
	}
	
	
}
