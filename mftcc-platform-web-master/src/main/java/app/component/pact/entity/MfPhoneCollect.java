package app.component.pact.entity;

import app.base.BaseDomain;

/**
 * Title: MfBusFincApp.java Description: 电话催收
 * 
 * @author：kaifa@dhcc.com.cn
 * @Tue May 31 18:10:07 CST 2016
 * @version：1.0
 **/
public class MfPhoneCollect extends BaseDomain {
	private static final long serialVersionUID = 7663776088291020982L;
	private String fincId;// 借据号
	private String putoutAmt;// 借据金额
	private String cusNo;// 客户编号
	private String cusName;// 客户名称
	private String appName;//项目名称
	private String appId;//项目ID
	private Integer overDays;//逾期天数
	private String contactsTel;//联系人手机号
	private String contactsName;//联系人姓名
	private String endDate;//到期日期
	private String optName;//跟进类型名
	private String trackType;//跟进类型
	private String fine;//罚金
	private Double repayPrcp;//本期计划应还本金
	private Double repayIntst;//本期计划应还利息
	private Double repayPrcpIntstSum;//本期计划应还本息合计
	private String nowDate;//当前日期
	private String finishedTime;//借据完结时间
	private String fincSts;//借据状态 1-已完结，0-未完结
	private String kindNo;//产品编号
	
	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
	}
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
	public Integer getOverDays() {
		return overDays;
	}
	public void setOverDays(Integer overDays) {
		this.overDays = overDays;
	}
	public String getContactsTel() {
		return contactsTel;
	}
	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}
	public String getContactsName() {
		return contactsName;
	}
	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getOptName() {
		return optName;
	}
	public void setOptName(String optName) {
		this.optName = optName;
	}
	public String getTrackType() {
		return trackType;
	}
	public void setTrackType(String trackType) {
		this.trackType = trackType;
	}
	public String getPutoutAmt() {
		return putoutAmt;
	}
	public void setPutoutAmt(String putoutAmt) {
		this.putoutAmt = putoutAmt;
	}
	public String getFine() {
		return fine;
	}
	public void setFine(String fine) {
		this.fine = fine;
	}
	public Double getRepayPrcp() {
		return repayPrcp;
	}
	public void setRepayPrcp(Double repayPrcp) {
		this.repayPrcp = repayPrcp;
	}
	public Double getRepayIntst() {
		return repayIntst;
	}
	public void setRepayIntst(Double repayIntst) {
		this.repayIntst = repayIntst;
	}
	public Double getRepayPrcpIntstSum() {
		return repayPrcpIntstSum;
	}
	public void setRepayPrcpIntstSum(Double repayPrcpIntstSum) {
		this.repayPrcpIntstSum = repayPrcpIntstSum;
	}
	public String getNowDate() {
		return nowDate;
	}
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getFinishedTime() {
		return finishedTime;
	}
	public void setFinishedTime(String finishedTime) {
		this.finishedTime = finishedTime;
	}
	public String getFincSts() {
		return fincSts;
	}
	public void setFincSts(String fincSts) {
		this.fincSts = fincSts;
	}
	public String getKindNo() {
		return kindNo;
	}
	public void setKindNo(String kindNo) {
		this.kindNo = kindNo;
	}
}