package app.component.finance.paramset.entity;
import app.base.BaseDomain;
/**
* Title: CwInitBal.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Jan 03 10:25:01 CST 2017
* @version：1.0
**/
public class CwInitBal extends BaseDomain {
	private String finBooks;//帐套标识
	private String txDate;//日期
	private String accHrt;//科目控制字
	private String itemsNo;//辅助核算编号
	private String itemsValueNo;//辅助核算值编号
	private String upAccHrt;//上级科目控制字
	private String isAcc;//是否是科目余额Y：是科目余额；N：辅助核算余额
	private String dcInd;//借贷标志1：借；2：贷；
	private String curNo;//币别
	private String drBal;//借方余额
	private String crBal;//贷方余额
	private String opNo;//操作员
	private String opName;//操作员名称
	private String occDate;//时间戳
	
	private String accNo;//科目编码
	private String accName;//科目名称
	private String bal;//余额
	private String accType;

	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getBal() {
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
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
	 * @return 日期
	 */
	public String getTxDate() {
	 	return txDate;
	}
	/**
	 * @设置 日期
	 * @param txDate
	 */
	public void setTxDate(String txDate) {
	 	this.txDate = txDate;
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
	 * @return 辅助核算编号
	 */
	public String getItemsNo() {
	 	return itemsNo;
	}
	/**
	 * @设置 辅助核算编号
	 * @param itemsNo
	 */
	public void setItemsNo(String itemsNo) {
	 	this.itemsNo = itemsNo;
	}
	/**
	 * @return 辅助核算值编号
	 */
	public String getItemsValueNo() {
	 	return itemsValueNo;
	}
	/**
	 * @设置 辅助核算值编号
	 * @param itemsValueNo
	 */
	public void setItemsValueNo(String itemsValueNo) {
	 	this.itemsValueNo = itemsValueNo;
	}
	/**
	 * @return 上级科目控制字
	 */
	public String getUpAccHrt() {
	 	return upAccHrt;
	}
	/**
	 * @设置 上级科目控制字
	 * @param upAccHrt
	 */
	public void setUpAccHrt(String upAccHrt) {
	 	this.upAccHrt = upAccHrt;
	}
	/**
	 * @return 是否是科目余额Y：是科目余额；N：辅助核算余额
	 */
	public String getIsAcc() {
	 	return isAcc;
	}
	/**
	 * @设置 是否是科目余额Y：是科目余额；N：辅助核算余额
	 * @param isAcc
	 */
	public void setIsAcc(String isAcc) {
	 	this.isAcc = isAcc;
	}
	/**
	 * @return 借贷标志1：借；2：贷；
	 */
	public String getDcInd() {
	 	return dcInd;
	}
	/**
	 * @设置 借贷标志1：借；2：贷；
	 * @param dcInd
	 */
	public void setDcInd(String dcInd) {
	 	this.dcInd = dcInd;
	}
	/**
	 * @return 币别
	 */
	public String getCurNo() {
	 	return curNo;
	}
	/**
	 * @设置 币别
	 * @param curNo
	 */
	public void setCurNo(String curNo) {
	 	this.curNo = curNo;
	}
	/**
	 * @return 借方余额
	 */
	public String getDrBal() {
	 	return drBal;
	}
	/**
	 * @设置 借方余额
	 * @param drBal
	 */
	public void setDrBal(String drBal) {
	 	this.drBal = drBal;
	}
	/**
	 * @return 贷方余额
	 */
	public String getCrBal() {
	 	return crBal;
	}
	/**
	 * @设置 贷方余额
	 * @param crBal
	 */
	public void setCrBal(String crBal) {
	 	this.crBal = crBal;
	}
	/**
	 * @return 操作员
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员
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
	 * @return 时间戳
	 */
	public String getOccDate() {
	 	return occDate;
	}
	/**
	 * @设置 时间戳
	 * @param occDate
	 */
	public void setOccDate(String occDate) {
	 	this.occDate = occDate;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	
}