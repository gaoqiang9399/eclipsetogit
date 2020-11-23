package app.component.finance.voucher.entity;
import app.base.BaseDomain;
/**
* Title: CwVoucherDetial.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Dec 30 10:02:05 CST 2016
* @version：1.0
**/
public class CwVoucherDetial extends BaseDomain{
	private String uid;//唯一编号
	private String finBooks;//帐套标识
	private String voucherNo;//凭证号关联字段cw_voucher_mst.voucher_no
	private String curNo;//币种
	private String txDate;//交易日期
	private String tranceCnt;//流水笔次
	private String accHrt;//科目控制字
	private String dcInd;//借贷标志1：借；2：贷；
	private String ctInd;//现转标志该字段不使用
	private String txAmt;//发生额
	private String txDesc;//摘要
	private String itemsNo;//辅助核算类编号
	private String itemsName;//辅助核算类名称
	private String itemsValueNo;//辅助核算项值编号
	private String itemsValueName;//辅助核算项值名称
	private String cashType;//报表项编号
	private String cashTypeName;//报表项名称
	private String cashType2;//报表项编号2
	private String cashType2Name;//报表项名称2
	private String brfsAmt;//现金流量分析金额
	private String amtNo;//交易代码编号用于批量交易代码配置批量分析
	private String txRemark;//备注
	private String occTime;//时间戳

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
	 * @return 凭证号关联字段cw_voucher_mst.voucher_no
	 */
	public String getVoucherNo() {
	 	return voucherNo;
	}
	/**
	 * @设置 凭证号关联字段cw_voucher_mst.voucher_no
	 * @param voucherNo
	 */
	public void setVoucherNo(String voucherNo) {
	 	this.voucherNo = voucherNo;
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
	 * @return 交易日期
	 */
	public String getTxDate() {
	 	return txDate;
	}
	/**
	 * @设置 交易日期
	 * @param txDate
	 */
	public void setTxDate(String txDate) {
	 	this.txDate = txDate;
	}
	/**
	 * @return 流水笔次
	 */
	public String getTranceCnt() {
	 	return tranceCnt;
	}
	/**
	 * @设置 流水笔次
	 * @param tranceCnt
	 */
	public void setTranceCnt(String tranceCnt) {
	 	this.tranceCnt = tranceCnt;
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
	 * @return 现转标志该字段不使用
	 */
	public String getCtInd() {
	 	return ctInd;
	}
	/**
	 * @设置 现转标志该字段不使用
	 * @param ctInd
	 */
	public void setCtInd(String ctInd) {
	 	this.ctInd = ctInd;
	}
	/**
	 * @return 发生额
	 */
	public String getTxAmt() {
	 	return txAmt;
	}
	/**
	 * @设置 发生额
	 * @param txAmt
	 */
	public void setTxAmt(String txAmt) {
	 	this.txAmt = txAmt;
	}
	/**
	 * @return 摘要
	 */
	public String getTxDesc() {
	 	return txDesc;
	}
	/**
	 * @设置 摘要
	 * @param txDesc
	 */
	public void setTxDesc(String txDesc) {
	 	this.txDesc = txDesc;
	}
	/**
	 * @return 辅助核算类编号
	 */
	public String getItemsNo() {
	 	return itemsNo;
	}
	/**
	 * @设置 辅助核算类编号
	 * @param itemsNo
	 */
	public void setItemsNo(String itemsNo) {
	 	this.itemsNo = itemsNo;
	}
	/**
	 * @return 辅助核算类名称
	 */
	public String getItemsName() {
	 	return itemsName;
	}
	/**
	 * @设置 辅助核算类名称
	 * @param itemsName
	 */
	public void setItemsName(String itemsName) {
	 	this.itemsName = itemsName;
	}
	/**
	 * @return 辅助核算项值编号
	 */
	public String getItemsValueNo() {
	 	return itemsValueNo;
	}
	/**
	 * @设置 辅助核算项值编号
	 * @param itemsValueNo
	 */
	public void setItemsValueNo(String itemsValueNo) {
	 	this.itemsValueNo = itemsValueNo;
	}
	/**
	 * @return 辅助核算项值名称
	 */
	public String getItemsValueName() {
	 	return itemsValueName;
	}
	/**
	 * @设置 辅助核算项值名称
	 * @param itemsValueName
	 */
	public void setItemsValueName(String itemsValueName) {
	 	this.itemsValueName = itemsValueName;
	}
	/**
	 * @return 报表项编号
	 */
	public String getCashType() {
	 	return cashType;
	}
	/**
	 * @设置 报表项编号
	 * @param cashType
	 */
	public void setCashType(String cashType) {
	 	this.cashType = cashType;
	}
	/**
	 * @return 报表项名称
	 */
	public String getCashTypeName() {
	 	return cashTypeName;
	}
	/**
	 * @设置 报表项名称
	 * @param cashTypeName
	 */
	public void setCashTypeName(String cashTypeName) {
	 	this.cashTypeName = cashTypeName;
	}
	/**
	 * @return 报表项编号2
	 */
	public String getCashType2() {
	 	return cashType2;
	}
	/**
	 * @设置 报表项编号2
	 * @param cashType2
	 */
	public void setCashType2(String cashType2) {
	 	this.cashType2 = cashType2;
	}
	/**
	 * @return 报表项名称2
	 */
	public String getCashType2Name() {
	 	return cashType2Name;
	}
	/**
	 * @设置 报表项名称2
	 * @param cashType2Name
	 */
	public void setCashType2Name(String cashType2Name) {
	 	this.cashType2Name = cashType2Name;
	}
	/**
	 * @return 现金流量分析金额
	 */
	public String getBrfsAmt() {
	 	return brfsAmt;
	}
	/**
	 * @设置 现金流量分析金额
	 * @param brfsAmt
	 */
	public void setBrfsAmt(String brfsAmt) {
	 	this.brfsAmt = brfsAmt;
	}
	/**
	 * @return 交易代码编号用于批量交易代码配置批量分析
	 */
	public String getAmtNo() {
	 	return amtNo;
	}
	/**
	 * @设置 交易代码编号用于批量交易代码配置批量分析
	 * @param amtNo
	 */
	public void setAmtNo(String amtNo) {
	 	this.amtNo = amtNo;
	}
	/**
	 * @return 备注
	 */
	public String getTxRemark() {
	 	return txRemark;
	}
	/**
	 * @设置 备注
	 * @param txRemark
	 */
	public void setTxRemark(String txRemark) {
	 	this.txRemark = txRemark;
	}
	/**
	 * @return 时间戳
	 */
	public String getOccTime() {
	 	return occTime;
	}
	/**
	 * @设置 时间戳
	 * @param occTime
	 */
	public void setOccTime(String occTime) {
	 	this.occTime = occTime;
	}
}