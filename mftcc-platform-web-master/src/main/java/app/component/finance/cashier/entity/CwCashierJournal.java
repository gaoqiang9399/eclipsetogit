package app.component.finance.cashier.entity;
import app.base.BaseDomain;
/**
* Title: CwCashierJournal.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Mar 27 16:43:21 CST 2017
* @version：1.0
**/
public class CwCashierJournal extends BaseDomain {
	private String uid;//唯一编号
	private String finBooks;//帐套标识
	private String accountNo;//账户编码关联cw_cashier_account.account_no
	private String accountName;//账户名称
	private String tranceNo;//流水号
	private String voucherDate;//凭证日期
	private String weeks;//凭证周期
	private String remark;//摘要
	private String chrDebit;//收入
	private String chrCredit;//支出
	private String pzPrefix;//凭证前缀
	private String voucherNoteNo;//凭证字号
	private String voucherNo;//凭证编号
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String occTime;//时间戳最后一次修改日期

	public CwCashierJournal() {
	}
	
	public CwCashierJournal(String finBooks) {
		this.finBooks = finBooks;
	}
	//列表使用
	private String chrBal;//余额
	private String operate;//操作
	
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
	 * @return 账户编码关联cw_cashier_account.account_no
	 */
	public String getAccountNo() {
	 	return accountNo;
	}
	/**
	 * @设置 账户编码关联cw_cashier_account.account_no
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
	 * @return 流水号
	 */
	public String getTranceNo() {
	 	return tranceNo;
	}
	/**
	 * @设置 流水号
	 * @param tranceNo
	 */
	public void setTranceNo(String tranceNo) {
	 	this.tranceNo = tranceNo;
	}
	/**
	 * @return 凭证日期
	 */
	public String getVoucherDate() {
	 	return voucherDate;
	}
	/**
	 * @设置 凭证日期
	 * @param voucherDate
	 */
	public void setVoucherDate(String voucherDate) {
	 	this.voucherDate = voucherDate;
	}
	/**
	 * @return 凭证周期
	 */
	public String getWeeks() {
	 	return weeks;
	}
	/**
	 * @设置 凭证周期
	 * @param weeks
	 */
	public void setWeeks(String weeks) {
	 	this.weeks = weeks;
	}
	/**
	 * @return 摘要
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 摘要
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 收入
	 */
	public String getChrDebit() {
	 	return chrDebit;
	}
	/**
	 * @设置 收入
	 * @param chrDebit
	 */
	public void setChrDebit(String chrDebit) {
	 	this.chrDebit = chrDebit;
	}
	/**
	 * @return 支出
	 */
	public String getChrCredit() {
	 	return chrCredit;
	}
	/**
	 * @设置 支出
	 * @param chrCredit
	 */
	public void setChrCredit(String chrCredit) {
	 	this.chrCredit = chrCredit;
	}
	/**
	 * @return 凭证前缀
	 */
	public String getPzPrefix() {
	 	return pzPrefix;
	}
	/**
	 * @设置 凭证前缀
	 * @param pzPrefix
	 */
	public void setPzPrefix(String pzPrefix) {
	 	this.pzPrefix = pzPrefix;
	}
	/**
	 * @return 凭证字号
	 */
	public String getVoucherNoteNo() {
	 	return voucherNoteNo;
	}
	/**
	 * @设置 凭证字号
	 * @param voucherNoteNo
	 */
	public void setVoucherNoteNo(String voucherNoteNo) {
	 	this.voucherNoteNo = voucherNoteNo;
	}
	/**
	 * @return 凭证编号
	 */
	public String getVoucherNo() {
	 	return voucherNo;
	}
	/**
	 * @设置 凭证编号
	 * @param voucherNo
	 */
	public void setVoucherNo(String voucherNo) {
	 	this.voucherNo = voucherNo;
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
	public String getChrBal() {
		return chrBal;
	}
	public void setChrBal(String chrBal) {
		this.chrBal = chrBal;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	
}