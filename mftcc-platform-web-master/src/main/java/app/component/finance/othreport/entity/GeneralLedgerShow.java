package app.component.finance.othreport.entity;

import app.base.BaseDomain;

/**
 * 类描述：总分类账(展示)
 * @author liwei
 * @date 2017-1-10 下午3:08:06
 */
public class GeneralLedgerShow extends BaseDomain{
	//科目编码
	private String accNo;
	//科目名称 
	private String accName;
	//期间
	private String duration;
	//摘要
	private String summary;
	//借方
	private String debit;
	//贷方
	private String lender;
	//方向
	private String direction;
	//余额
	private String balance;
	//开始期数
	private String beginWeek;
	//结束期数
	private String endWeek;
	
	public String getAccNo() {
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
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
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
	/**
	 * 描述:方向
	 * @param direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getBeginWeek() {
		return beginWeek;
	}
	public void setBeginWeek(String beginWeek) {
		this.beginWeek = beginWeek;
	}
	public String getEndWeek() {
		return endWeek;
	}
	public void setEndWeek(String endWeek) {
		this.endWeek = endWeek;
	}
}
