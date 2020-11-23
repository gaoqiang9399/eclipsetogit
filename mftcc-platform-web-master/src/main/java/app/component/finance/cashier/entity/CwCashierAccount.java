package app.component.finance.cashier.entity;
import app.base.BaseDomain;
/**
* Title: CwCashierAccount.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Mar 27 16:39:02 CST 2017
* @version：1.0
**/
public class CwCashierAccount extends BaseDomain {
	private String uid;//唯一编号
	private String finBooks;//帐套标识
	private String accountNo;//账户编码唯一
	private String accountName;//账户名称
	private String curNo;//币种
	private String bankNo;//银行账号银行类展示
	private String accHrt;//科目控制字
	private String accName;//科目名称
	private String accountType;//账户分类1：现金类；2：银行类；
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String occTime;//时间戳最后一次修改日期

	public CwCashierAccount() {
	}
	
	public CwCashierAccount(String finBooks) {
		this.finBooks = finBooks;
	}
	
	/**
	 * @return 唯一编号
	 */
	public String getUid() {
	 	return uid;
	}
	/**
	 * @设置 唯一编号
	 * @param uid
	 */
	public void setUid(String uid) {
	 	this.uid = uid;
	}
	/**
	 * @return 帐套标识
	 */
	public String getFinBooks() {
	 	return finBooks;
	}
	/**
	 * @设置 帐套标识
	 * @param finBooks
	 */
	public void setFinBooks(String finBooks) {
	 	this.finBooks = finBooks;
	}
	/**
	 * @return 账户编码唯一
	 */
	public String getAccountNo() {
	 	return accountNo;
	}
	/**
	 * @设置 账户编码唯一
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
	 * @return 币种
	 */
	public String getCurNo() {
	 	return curNo;
	}
	/**
	 * @设置 币种
	 * @param curNo
	 */
	public void setCurNo(String curNo) {
	 	this.curNo = curNo;
	}
	/**
	 * @return 银行账号银行类展示
	 */
	public String getBankNo() {
	 	return bankNo;
	}
	/**
	 * @设置 银行账号银行类展示
	 * @param bankNo
	 */
	public void setBankNo(String bankNo) {
	 	this.bankNo = bankNo;
	}
	/**
	 * @return 科目控制字
	 */
	public String getAccHrt() {
	 	return accHrt;
	}
	/**
	 * @设置 科目控制字
	 * @param accHrt
	 */
	public void setAccHrt(String accHrt) {
	 	this.accHrt = accHrt;
	}
	/**
	 * @return 科目名称
	 */
	public String getAccName() {
	 	return accName;
	}
	/**
	 * @设置 科目名称
	 * @param accName
	 */
	public void setAccName(String accName) {
	 	this.accName = accName;
	}
	/**
	 * @return 账户分类1：现金类；2：银行类；
	 */
	public String getAccountType() {
	 	return accountType;
	}
	/**
	 * @设置 账户分类1：现金类；2：银行类；
	 * @param accountType
	 */
	public void setAccountType(String accountType) {
	 	this.accountType = accountType;
	}
	/**
	 * @return 操作员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作员名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 操作员名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 时间戳最后一次修改日期
	 */
	public String getOccTime() {
	 	return occTime;
	}
	/**
	 * @设置 时间戳最后一次修改日期
	 * @param occTime
	 */
	public void setOccTime(String occTime) {
	 	this.occTime = occTime;
	}
}