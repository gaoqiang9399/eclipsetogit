package app.component.thirdservice.fuioupay.entity;

import java.util.List;

/**
 * *
 * 类描述：富友代收导出实体
 * @author 李伟
 * @date 2017-8-16 下午5:21:59
 */
public class FuiouPayExport {
	private int id;//序号
	private String bankName;//开户行
	private String bankArea;//开户市/县	
	private String bankFullName;//开户行全称
	private String accountNo;//收款人银行账号
	private String accountName;//户名
	private String amt;//金额(单位:元)
	private String companyId;//企业流水账号
	private String filter;//备注
	private String mobile;//手机号
	private String projectId;//项目ID
	private String businessDefiny;//业务定义
	private List<String> fincId;//导出的fincId
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankArea() {
		return bankArea;
	}
	public void setBankArea(String bankArea) {
		this.bankArea = bankArea;
	}
	public String getBankFullName() {
		return bankFullName;
	}
	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getBusinessDefiny() {
		return businessDefiny;
	}
	public void setBusinessDefiny(String businessDefiny) {
		this.businessDefiny = businessDefiny;
	}
	public List<String> getFincId() {
		return fincId;
	}
	public void setFincId(List<String> fincId) {
		this.fincId = fincId;
	}
}
