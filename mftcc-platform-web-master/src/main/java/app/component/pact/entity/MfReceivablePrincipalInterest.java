/**
 * 
 */
package app.component.pact.entity;

import app.base.BaseDomain;

/**
 * @author QiuZhao
 *
 */
public class MfReceivablePrincipalInterest extends BaseDomain {

	private static final long serialVersionUID = 1L;
	private String appId;//申请号
	private String appName;//项目名称
	private String pactId;//合同id
	private String pactNo;//合同编号
	private String cusNo;//客户编号
	private String cusName;//客户名称
	private String cusType;//客户类型
	private String contactsTel;//联系电话
	private String fincSts;//借据状态
	private String fincId;//借据ID
	private String busEntrance;//业务入口
	private String planId;//流水号
	private String repayPrcp;//本期计划应还本金
	private String repayIntst;//本期计划应还利息
	private String feeSum;//应收费用
	private String planEndDate;//本期计划还款日期(本期结束日期)
	private String pactEndDate;//合同到期日期
	private String endDate;//到期日期
	private String nowDate;//当前日期
	private String overdueSts;//借据预期状态
	private String repaySum;//应收总额
	private String maxMonthDate;//月末最后一天
	private String scopeType;//筛选类型
	private String faXi;//罚息（逾期利息+复利利息）
	private String weiYueJin;//违约金
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
	public String getCusType() {
		return cusType;
	}
	public void setCusType(String cusType) {
		this.cusType = cusType;
	}
	public String getContactsTel() {
		return contactsTel;
	}
	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}
	public String getFincSts() {
		return fincSts;
	}
	public void setFincSts(String fincSts) {
		this.fincSts = fincSts;
	}
	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
	}
	public String getBusEntrance() {
		return busEntrance;
	}
	public void setBusEntrance(String busEntrance) {
		this.busEntrance = busEntrance;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getRepayPrcp() {
		return repayPrcp;
	}
	public void setRepayPrcp(String repayPrcp) {
		this.repayPrcp = repayPrcp;
	}
	public String getRepayIntst() {
		return repayIntst;
	}
	public void setRepayIntst(String repayIntst) {
		this.repayIntst = repayIntst;
	}
	public String getFeeSum() {
		return feeSum;
	}
	public void setFeeSum(String feeSum) {
		this.feeSum = feeSum;
	}
	public String getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}
	public String getPactEndDate() {
		return pactEndDate;
	}
	public void setPactEndDate(String pactEndDate) {
		this.pactEndDate = pactEndDate;
	}
	public String getNowDate() {
		return nowDate;
	}
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
	public String getOverdueSts() {
		return overdueSts;
	}
	public void setOverdueSts(String overdueSts) {
		this.overdueSts = overdueSts;
	}
	public String getRepaySum() {
		return repaySum;
	}
	public void setRepaySum(String repaySum) {
		this.repaySum = repaySum;
	}
	public String getMaxMonthDate() {
		return maxMonthDate;
	}
	public void setMaxMonthDate(String maxMonthDate) {
		this.maxMonthDate = maxMonthDate;
	}
	public String getScopeType() {
		return scopeType;
	}
	public void setScopeType(String scopeType) {
		this.scopeType = scopeType;
	}
	public String getWeiYueJin() {
		return weiYueJin;
	}
	public void setWeiYueJin(String weiYueJin) {
		this.weiYueJin = weiYueJin;
	}
	public String getFaXi() {
		return faXi;
	}
	public void setFaXi(String faXi) {
		this.faXi = faXi;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
