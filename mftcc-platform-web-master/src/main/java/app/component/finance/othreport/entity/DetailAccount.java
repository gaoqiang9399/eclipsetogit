package app.component.finance.othreport.entity;

import app.base.BaseDomain;

public class DetailAccount extends BaseDomain{
	//科目编码
	//private String accNo;
	//科目名称 
	//private String accName;
	//日期
	private String date;
	//期数
	private String term;
	//凭证号 
	private String voucherNo;
	//凭证前缀
	private String pzPrefix;
	//凭证字号
	private String voucherNoteNo;
	//摘要
	private String summary;
	//发生额
	private String txAmt;
	//借方
	private String debit;
	//贷方
	private String lender;
	//方向
	private String direction;
	//余额
	private String balance;
	/**
	 * 日期
	 * @return
	 */
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDebit() {
		return debit;
	}
	public void setDebit(String debit) {
		this.debit = debit;
	}
	public String getLender() {
		return lender;
	}
	public void setLender(String lender) {
		this.lender = lender;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	/*public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}*/
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getTxAmt() {
		return txAmt;
	}
	public void setTxAmt(String txAmt) {
		this.txAmt = txAmt;
	}
	public String getPzPrefix() {
		return pzPrefix;
	}
	public void setPzPrefix(String pzPrefix) {
		this.pzPrefix = pzPrefix;
	}
	public String getVoucherNoteNo() {
		return voucherNoteNo;
	}
	public void setVoucherNoteNo(String voucherNoteNo) {
		this.voucherNoteNo = voucherNoteNo;
	}
}
