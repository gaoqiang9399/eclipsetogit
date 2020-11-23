package app.component.pss.information.entity;
import app.base.BaseDomain;
/**
* Title: PssCustomerInfo.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Dec 01 16:13:22 SGT 2017
* @version：1.0
**/
public class PssCustomerInfo1 extends BaseDomain {
	private String cusNo;//主键
	private String cusCode;//客户号
	private String cusName;//客户名称
	private String cusCategory;//客户类别
	private String cusGrade;//客户等级
	private String balanceDate;//余额日期
	private Double accountsReceived;//期初应收账款
	private Double depositReceived;//期初预收账款
	private String cusTaxNo;//纳税人识别号
	private String bankName;//开户行名称
	private String bankNo;//开户行账号
	private String salerNo;//销售人员编号
	private Double accountsReceivedBalance;//应收款余额
	private String sendGoodsAddress;//送货地址
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
	
	public String getCusCode() {
		return cusCode;
	}
	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}
	/**
	 * @return 客户号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户号
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 客户名称
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 客户名称
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 客户类别
	 */
	public String getCusCategory() {
	 	return cusCategory;
	}
	/**
	 * @设置 客户类别
	 * @param cusCategory
	 */
	public void setCusCategory(String cusCategory) {
	 	this.cusCategory = cusCategory;
	}
	/**
	 * @return 客户等级
	 */
	public String getCusGrade() {
	 	return cusGrade;
	}
	/**
	 * @设置 客户等级
	 * @param cusGrade
	 */
	public void setCusGrade(String cusGrade) {
	 	this.cusGrade = cusGrade;
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
	 * @return 期初应收账款
	 */
	public Double getAccountsReceived() {
	 	return accountsReceived;
	}
	/**
	 * @设置 期初应收账款
	 * @param accountsReceived
	 */
	public void setAccountsReceived(Double accountsReceived) {
	 	this.accountsReceived = accountsReceived;
	}
	/**
	 * @return 期初预收账款
	 */
	public Double getDepositReceived() {
	 	return depositReceived;
	}
	/**
	 * @设置 期初预收账款
	 * @param depositReceived
	 */
	public void setDepositReceived(Double depositReceived) {
	 	this.depositReceived = depositReceived;
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
	 * @return 销售人员编号
	 */
	public String getSalerNo() {
	 	return salerNo;
	}
	/**
	 * @设置 销售人员编号
	 * @param salerNo
	 */
	public void setSalerNo(String salerNo) {
	 	this.salerNo = salerNo;
	}
	/**
	 * @return 应收款余额
	 */
	public Double getAccountsReceivedBalance() {
	 	return accountsReceivedBalance;
	}
	/**
	 * @设置 应收款余额
	 * @param accountsReceivedBalance
	 */
	public void setAccountsReceivedBalance(Double accountsReceivedBalance) {
	 	this.accountsReceivedBalance = accountsReceivedBalance;
	}
	/**
	 * @return 送货地址
	 */
	public String getSendGoodsAddress() {
	 	return sendGoodsAddress;
	}
	/**
	 * @设置 送货地址
	 * @param sendGoodsAddress
	 */
	public void setSendGoodsAddress(String sendGoodsAddress) {
	 	this.sendGoodsAddress = sendGoodsAddress;
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