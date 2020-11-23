package app.component.report.entity;

import app.base.BaseDomain;

public class MfReportBus extends BaseDomain{
	/* 合同表 */
	private String pactId; //合同号
	private String cusName;//客户名
	private String fincRate;//贷款利率
	private String pactAmt;//合同金额
	private String fincUse;//贷款用途
	private String beginDate;//合同开始时间
	private String endDate;//合同结束时间
	private String pactSts;//合同状态
	private String busModel;//业务模式
	private String opNo;
	private String opName;//登记人
	private String kindName;//产品名称
	private String vouType;//担保方式
	private String overRate;//逾期利率
	/*parm_dic  */
	private String keyName;
	private String optCode;
	private String optName;
	public String getPactId() {
		return pactId;
	}
	public void setPactId(String pactId) {
		this.pactId = pactId;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getFincRate() {
		return fincRate;
	}
	public void setFincRate(String fincRate) {
		this.fincRate = fincRate;
	}
	public String getPactAmt() {
		return pactAmt;
	}
	public void setPactAmt(String pactAmt) {
		this.pactAmt = pactAmt;
	}
	public String getFincUse() {
		return fincUse;
	}
	public void setFincUse(String fincUse) {
		this.fincUse = fincUse;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPactSts() {
		return pactSts;
	}
	public void setPactSts(String pactSts) {
		this.pactSts = pactSts;
	}

	public String getOpNo() {
		return opNo;
	}
	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getOptCode() {
		return optCode;
	}
	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}
	public String getOptName() {
		return optName;
	}
	public void setOptName(String optName) {
		this.optName = optName;
	}
	public String getBusModel() {
		return busModel;
	}
	public void setBusModel(String busModel) {
		this.busModel = busModel;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public String getVouType() {
		return vouType;
	}
	public void setVouType(String vouType) {
		this.vouType = vouType;
	}
	public String getOverRate() {
		return overRate;
	}
	public void setOverRate(String overRate) {
		this.overRate = overRate;
	}	
}
