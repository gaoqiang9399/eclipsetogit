/**
 * Copyright (C) DXHM 版权所有
 * 文件名： InterestAccruedBean.java
 * 包名： app.component.pact.entity
 * 说明：
 * @author 沈浩兵
 * @date 2017-8-17 下午3:35:21
 * @version V1.0
 */ 
package app.component.pact.entity;

import app.base.BaseDomain;

/**
 * 类名： InterestAccruedBean
 * 描述：
 * @author 沈浩兵
 * @date 2017-8-17 下午3:35:21
 *
 *
 */
public class InterestAccruedBean extends BaseDomain{
	private static final long serialVersionUID = 7663776088291020982L;
	private String fincId;// 借据号
	private String fincShowId;// 借据展示号
	private String pactId;// 合同编号
	private String pactNo;// 展示合同编号,用于对外展示不做业务处理
	private String appId;// 申请编号
	private String appName;// 项目名称
	private String cusNo;// 客户编号
	private String cusName;// 客户名称
	private String fincSts;// 借据状态
	//private Double pactAmt;// 合同金额
	private Double putoutAmt;// 借据金额/放款金额，多次放款合并和借据是这里=合同金额
	//private Double putoutAmtReal;// 实际借据金额/实际放款金额不包括放款时收取的费用利息
	private String repayType;// 还款方式
	private String rateType;// 利率类型：1-年利率，2-月利率，3-日利率
	private Double fincRate;// 利率：换算成年利率存储%
	private Double principalInterest;//计息本金
	private Double realRate;//实际利率（月）
	private String intstBeginDate;// 起息开始日期
	private String intstEndDate;// 起息到期日期
	private String accruedMonth;//利息计提月份
	private Integer calcuInterestDays;//计息天数
	private Double accruedInterest;//计提利息
	private String queryMonth;//查询月份
	private String borrowCode;

	public String getBorrowCode() {
		return borrowCode;
	}

	public void setBorrowCode(String borrowCode) {
		this.borrowCode = borrowCode;
	}

	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
	}
	public String getFincShowId() {
		return fincShowId;
	}
	public void setFincShowId(String fincShowId) {
		this.fincShowId = fincShowId;
	}
	public String getPactId() {
		return pactId;
	}
	public void setPactId(String pactId) {
		this.pactId = pactId;
	}
	public String getPactNo() {
		return pactNo;
	}
	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
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
	/*public Double getPactAmt() {
		return pactAmt;
	}
	public void setPactAmt(Double pactAmt) {
		this.pactAmt = pactAmt;
	}*/
	public Double getPutoutAmt() {
		return putoutAmt;
	}
	public void setPutoutAmt(Double putoutAmt) {
		this.putoutAmt = putoutAmt;
	}
	/*public Double getPutoutAmtReal() {
		return putoutAmtReal;
	}
	public void setPutoutAmtReal(Double putoutAmtReal) {
		this.putoutAmtReal = putoutAmtReal;
	}*/
	public String getRepayType() {
		return repayType;
	}
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public Double getFincRate() {
		return fincRate;
	}
	public void setFincRate(Double fincRate) {
		this.fincRate = fincRate;
	}
	public Double getPrincipalInterest() {
		return principalInterest;
	}
	public void setPrincipalInterest(Double principalInterest) {
		this.principalInterest = principalInterest;
	}
	public Double getRealRate() {
		return realRate;
	}
	public void setRealRate(Double realRate) {
		this.realRate = realRate;
	}
	public String getIntstBeginDate() {
		return intstBeginDate;
	}
	public void setIntstBeginDate(String intstBeginDate) {
		this.intstBeginDate = intstBeginDate;
	}
	public String getIntstEndDate() {
		return intstEndDate;
	}
	public void setIntstEndDate(String intstEndDate) {
		this.intstEndDate = intstEndDate;
	}
	public String getAccruedMonth() {
		return accruedMonth;
	}
	public void setAccruedMonth(String accruedMonth) {
		this.accruedMonth = accruedMonth;
	}
	public Integer getCalcuInterestDays() {
		return calcuInterestDays;
	}
	public void setCalcuInterestDays(Integer calcuInterestDays) {
		this.calcuInterestDays = calcuInterestDays;
	}
	public Double getAccruedInterest() {
		return accruedInterest;
	}
	public void setAccruedInterest(Double accruedInterest) {
		this.accruedInterest = accruedInterest;
	}
	public String getQueryMonth() {
		return queryMonth;
	}
	public void setQueryMonth(String queryMonth) {
		this.queryMonth = queryMonth;
	}
	public String getFincSts() {
		return fincSts;
	}
	public void setFincSts(String fincSts) {
		this.fincSts = fincSts;
	}
	
}
