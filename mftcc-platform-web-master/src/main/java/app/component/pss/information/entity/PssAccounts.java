package app.component.pss.information.entity;
import app.base.BaseDomain;
/**
* Title: PssAccounts.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Dec 20 19:34:19 SGT 2017
* @version：1.0
**/
public class PssAccounts extends BaseDomain {
	private String accountId;//主键
	private String accountNo;//账户编号
	private String accountName;//账户名称
	private Double currBalance;//当前余额
	private Double periodBalance;//期初余额
	private String setAccountDate;//余额日期
	private String accountType;//账户类型
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
	private String isDefaultAccount;
	
	public String getIsDefaultAccount() {
		return isDefaultAccount;
	}
	public void setIsDefaultAccount(String isDefaultAccount) {
		this.isDefaultAccount = isDefaultAccount;
	}
	/**
	 * @return 主键
	 */
	public String getAccountId() {
	 	return accountId;
	}
	/**
	 * @设置 主键
	 * @param accountId
	 */
	public void setAccountId(String accountId) {
	 	this.accountId = accountId;
	}
	/**
	 * @return 账户编号
	 */
	public String getAccountNo() {
	 	return accountNo;
	}
	/**
	 * @设置 账户编号
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
	 	this.accountNo = accountNo;
	}
	/**
	 * @return 账户名称
	 */
	public String getAccountName() {
	 	return accountName;
	}
	/**
	 * @设置 账户名称
	 * @param accountName
	 */
	public void setAccountName(String accountName) {
	 	this.accountName = accountName;
	}
	/**
	 * @return 当前余额
	 */
	public Double getCurrBalance() {
	 	return currBalance;
	}
	/**
	 * @设置 当前余额
	 * @param currBalance
	 */
	public void setCurrBalance(Double currBalance) {
	 	this.currBalance = currBalance;
	}
	/**
	 * @return 期初余额
	 */
	public Double getPeriodBalance() {
	 	return periodBalance;
	}
	/**
	 * @设置 期初余额
	 * @param periodBalance
	 */
	public void setPeriodBalance(Double periodBalance) {
	 	this.periodBalance = periodBalance;
	}
	/**
	 * @return 余额日期
	 */
	public String getSetAccountDate() {
	 	return setAccountDate;
	}
	/**
	 * @设置 余额日期
	 * @param setAccountDate
	 */
	public void setSetAccountDate(String setAccountDate) {
	 	this.setAccountDate = setAccountDate;
	}
	/**
	 * @return 账户类型
	 */
	public String getAccountType() {
	 	return accountType;
	}
	/**
	 * @设置 账户类型
	 * @param accountType
	 */
	public void setAccountType(String accountType) {
	 	this.accountType = accountType;
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