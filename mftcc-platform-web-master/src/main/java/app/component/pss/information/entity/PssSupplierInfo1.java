package app.component.pss.information.entity;
import app.base.BaseDomain;
/**
* Title: PssSupplierInfo.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Dec 01 16:25:37 SGT 2017
* @version：1.0
**/
public class PssSupplierInfo1 extends BaseDomain {
	private String supplierId;//主键
	private String supplierNo;//供应商号
	private String supplierName;//供应商名称
	private String supplierCategory;//供应商类别
	private String balanceDate;//余额日期
	private Double accountsPayed;//期初应付账款
	private Double depositPayed;//期初预付账款
	private Double taxRate;//税率(%)
	private String cusTaxNo;//纳税人识别号
	private String bankName;//开户行名称
	private String bankNo;//开户行账号
	private Double accountsPayedBalance;//应付款余额
	private String contactsAddress;//联系地址
	private String enabledStatus;//启用状态(1-启用 0-关闭)
	private String memo;//备注
	private String regOpNo;//登记人编号
	private String regOpName;//登记人名称
	private String regBrNo;//登记人机构编号
	private String regBrName;//登记人机构名称
	private String regTime;//登记时间
	private String lstModOpNo;//最后修改人编号
	private String lstModOpName;//最后修改人名称
	private String lstModBrNo;//最后修改人机构编号
	private String lstModBrName;//最后修改人机构名称
	private String lstModTime;//最后修改时间
	
	private String contactsName;
	private String mobilePhone;
	
	public String getContactsName() {
		return contactsName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	/**
	 * @return 主键
	 */
	public String getSupplierId() {
	 	return supplierId;
	}
	/**
	 * @设置 主键
	 * @param supplierId
	 */
	public void setSupplierId(String supplierId) {
	 	this.supplierId = supplierId;
	}
	/**
	 * @return 供应商号
	 */
	public String getSupplierNo() {
	 	return supplierNo;
	}
	/**
	 * @设置 供应商号
	 * @param supplierNo
	 */
	public void setSupplierNo(String supplierNo) {
	 	this.supplierNo = supplierNo;
	}
	/**
	 * @return 供应商名称
	 */
	public String getSupplierName() {
	 	return supplierName;
	}
	/**
	 * @设置 供应商名称
	 * @param supplierName
	 */
	public void setSupplierName(String supplierName) {
	 	this.supplierName = supplierName;
	}
	/**
	 * @return 供应商类别
	 */
	public String getSupplierCategory() {
	 	return supplierCategory;
	}
	/**
	 * @设置 供应商类别
	 * @param supplierCategory
	 */
	public void setSupplierCategory(String supplierCategory) {
	 	this.supplierCategory = supplierCategory;
	}
	/**
	 * @return 余额日期
	 */
	public String getBalanceDate() {
	 	return balanceDate;
	}
	/**
	 * @设置 余额日期
	 * @param balanceDate
	 */
	public void setBalanceDate(String balanceDate) {
	 	this.balanceDate = balanceDate;
	}
	/**
	 * @return 期初应付账款
	 */
	public Double getAccountsPayed() {
	 	return accountsPayed;
	}
	/**
	 * @设置 期初应付账款
	 * @param accountsPayed
	 */
	public void setAccountsPayed(Double accountsPayed) {
	 	this.accountsPayed = accountsPayed;
	}
	/**
	 * @return 期初预付账款
	 */
	public Double getDepositPayed() {
	 	return depositPayed;
	}
	/**
	 * @设置 期初预付账款
	 * @param depositPayed
	 */
	public void setDepositPayed(Double depositPayed) {
	 	this.depositPayed = depositPayed;
	}
	/**
	 * @return 税率(%)
	 */
	public Double getTaxRate() {
	 	return taxRate;
	}
	/**
	 * @设置 税率(%)
	 * @param taxRate
	 */
	public void setTaxRate(Double taxRate) {
	 	this.taxRate = taxRate;
	}
	/**
	 * @return 纳税人识别号
	 */
	public String getCusTaxNo() {
	 	return cusTaxNo;
	}
	/**
	 * @设置 纳税人识别号
	 * @param cusTaxNo
	 */
	public void setCusTaxNo(String cusTaxNo) {
	 	this.cusTaxNo = cusTaxNo;
	}
	/**
	 * @return 开户行名称
	 */
	public String getBankName() {
	 	return bankName;
	}
	/**
	 * @设置 开户行名称
	 * @param bankName
	 */
	public void setBankName(String bankName) {
	 	this.bankName = bankName;
	}
	/**
	 * @return 开户行账号
	 */
	public String getBankNo() {
	 	return bankNo;
	}
	/**
	 * @设置 开户行账号
	 * @param bankNo
	 */
	public void setBankNo(String bankNo) {
	 	this.bankNo = bankNo;
	}
	/**
	 * @return 应付款余额
	 */
	public Double getAccountsPayedBalance() {
	 	return accountsPayedBalance;
	}
	/**
	 * @设置 应付款余额
	 * @param accountsPayedBalance
	 */
	public void setAccountsPayedBalance(Double accountsPayedBalance) {
	 	this.accountsPayedBalance = accountsPayedBalance;
	}
	/**
	 * @return 联系地址
	 */
	public String getContactsAddress() {
	 	return contactsAddress;
	}
	/**
	 * @设置 联系地址
	 * @param contactsAddress
	 */
	public void setContactsAddress(String contactsAddress) {
	 	this.contactsAddress = contactsAddress;
	}
	/**
	 * @return 启用状态(1-启用 0-关闭)
	 */
	public String getEnabledStatus() {
	 	return enabledStatus;
	}
	/**
	 * @设置 启用状态(1-启用 0-关闭)
	 * @param enabledStatus
	 */
	public void setEnabledStatus(String enabledStatus) {
	 	this.enabledStatus = enabledStatus;
	}
	/**
	 * @return 备注
	 */
	public String getMemo() {
	 	return memo;
	}
	/**
	 * @设置 备注
	 * @param memo
	 */
	public void setMemo(String memo) {
	 	this.memo = memo;
	}
	/**
	 * @return 登记人编号
	 */
	public String getRegOpNo() {
	 	return regOpNo;
	}
	/**
	 * @设置 登记人编号
	 * @param regOpNo
	 */
	public void setRegOpNo(String regOpNo) {
	 	this.regOpNo = regOpNo;
	}
	/**
	 * @return 登记人名称
	 */
	public String getRegOpName() {
	 	return regOpName;
	}
	/**
	 * @设置 登记人名称
	 * @param regOpName
	 */
	public void setRegOpName(String regOpName) {
	 	this.regOpName = regOpName;
	}
	/**
	 * @return 登记人机构编号
	 */
	public String getRegBrNo() {
	 	return regBrNo;
	}
	/**
	 * @设置 登记人机构编号
	 * @param regBrNo
	 */
	public void setRegBrNo(String regBrNo) {
	 	this.regBrNo = regBrNo;
	}
	/**
	 * @return 登记人机构名称
	 */
	public String getRegBrName() {
	 	return regBrName;
	}
	/**
	 * @设置 登记人机构名称
	 * @param regBrName
	 */
	public void setRegBrName(String regBrName) {
	 	this.regBrName = regBrName;
	}
	/**
	 * @return 登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改人编号
	 */
	public String getLstModOpNo() {
	 	return lstModOpNo;
	}
	/**
	 * @设置 最后修改人编号
	 * @param lstModOpNo
	 */
	public void setLstModOpNo(String lstModOpNo) {
	 	this.lstModOpNo = lstModOpNo;
	}
	/**
	 * @return 最后修改人名称
	 */
	public String getLstModOpName() {
	 	return lstModOpName;
	}
	/**
	 * @设置 最后修改人名称
	 * @param lstModOpName
	 */
	public void setLstModOpName(String lstModOpName) {
	 	this.lstModOpName = lstModOpName;
	}
	/**
	 * @return 最后修改人机构编号
	 */
	public String getLstModBrNo() {
	 	return lstModBrNo;
	}
	/**
	 * @设置 最后修改人机构编号
	 * @param lstModBrNo
	 */
	public void setLstModBrNo(String lstModBrNo) {
	 	this.lstModBrNo = lstModBrNo;
	}
	/**
	 * @return 最后修改人机构名称
	 */
	public String getLstModBrName() {
	 	return lstModBrName;
	}
	/**
	 * @设置 最后修改人机构名称
	 * @param lstModBrName
	 */
	public void setLstModBrName(String lstModBrName) {
	 	this.lstModBrName = lstModBrName;
	}
	/**
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
}