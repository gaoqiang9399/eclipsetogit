package app.component.report.entity;

import app.base.BaseDomain;

public class MfReportPledge extends BaseDomain{
	private String pledgeNoShow;//押品编号
	private String pledgeName;//押品名称
	private String classNo;//所属类别编号
	private String className;//所属类别名称	
	private String pledgeMethod;//担保类型
	private String pledgeSts;//押品状态
	private String pledgeAmount;//质押总笔数
	private String amountUnit;//数量单位
	private String pledgeRate;//担保时实际价值
	private String envalue;//押品首次评估价值
	private String envalueDate;//押品首次评估日期
	private String importDate;//入库日期
	private String exportDate;//出库日期
	private String cusNo;//押品所有者客户编号
	private String cusName;//押品所有者客户名称
	private String opNo;//
	private String opName;//登记人
	private String keyName;
	private String optCode;
	private String optName;
	public String getPledgeNoShow() {
		return pledgeNoShow;
	}
	public void setPledgeNoShow(String pledgeNoShow) {
		this.pledgeNoShow = pledgeNoShow;
	}
	public String getPledgeName() {
		return pledgeName;
	}
	public void setPledgeName(String pledgeName) {
		this.pledgeName = pledgeName;
	}
	public String getPledgeMethod() {
		return pledgeMethod;
	}
	public void setPledgeMethod(String pledgeMethod) {
		this.pledgeMethod = pledgeMethod;
	}
	public String getPledgeSts() {
		return pledgeSts;
	}
	public void setPledgeSts(String pledgeSts) {
		this.pledgeSts = pledgeSts;
	}
	public String getPledgeAmount() {
		return pledgeAmount;
	}
	public void setPledgeAmount(String pledgeAmount) {
		this.pledgeAmount = pledgeAmount;
	}
	public String getAmountUnit() {
		return amountUnit;
	}
	public void setAmountUnit(String amountUnit) {
		this.amountUnit = amountUnit;
	}
	public String getPledgeRate() {
		return pledgeRate;
	}
	public void setPledgeRate(String pledgeRate) {
		this.pledgeRate = pledgeRate;
	}
	public String getEnvalue() {
		return envalue;
	}
	public void setEnvalue(String envalue) {
		this.envalue = envalue;
	}
	public String getImportDate() {
		return importDate;
	}
	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}
	public String getExportDate() {
		return exportDate;
	}
	public void setExportDate(String exportDate) {
		this.exportDate = exportDate;
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
	public String getEnvalueDate() {
		return envalueDate;
	}
	public void setEnvalueDate(String envalueDate) {
		this.envalueDate = envalueDate;
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
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
