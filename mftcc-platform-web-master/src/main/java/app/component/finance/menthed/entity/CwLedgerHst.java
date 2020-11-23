package app.component.finance.menthed.entity;
import app.base.BaseDomain;
/**
* Title: CwLedgerHst.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jan 09 09:55:07 CST 2017
* @version：1.0
**/
public class CwLedgerHst extends BaseDomain {
	private String finBooks;//帐套标识
	private String curNo;//币种
	private String accHrt;//科目控制字
	private String weeks;//周期
	private String txDate;//交易日期
	private String dcInd;//科目余额方向
	private String upAccHrt;//汇总科目控制字
	private String drBal;//当前借方余额
	private String crBal;//当前贷方余额
	private String lddBal;//昨日借方余额
	private String lcdBal;//昨日贷方余额
	private String rddCnt;//本日借笔数
	private String rcdCnt;//本日待笔数
	private String rddAmt;//本日借发生额
	private String rcdAmt;//本日贷发生额
	private String cddCnt;//本日现金借笔数
	private String ccdCnt;//本日现金贷笔数
	private String cddAmt;//本日现金借发生额
	private String ccdAmt;//本日现金贷发生额
	private String tddrBal;//旬初借方余额
	private String tdcrBal;//旬初贷方余额
	private String tddrCnt;//本旬借方笔数
	private String tdcrCnt;//本旬贷方笔数
	private String tddrAmt;//本旬借方发生额
	private String tdcrAmt;//本旬贷方发生额
	private String mdrBal;//月初借余额
	private String mcrBal;//月初贷余额
	private String mdrCnt;//本月借笔数
	private String mcrCnt;//本月贷笔数
	private String mdrAmt;//本月借发生额
	private String mcrAmt;//本月贷发生额
	private String qdrBal;//季初借余额
	private String qcrBal;//季初贷余额
	private String qdrCnt;//本季借笔数
	private String qcrCnt;//本季贷笔数
	private String qdrAmt;//本季借发生额
	private String qcrAmt;//本季贷发生额
	private String ydrBal;//年初借余额
	private String ycrBal;//年初贷余额
	private String ydrAnt;//本年借笔数
	private String ycrCnt;//本年贷笔数
	private String ydrAmt;//本年借发生额
	private String ycrAmt;//本年贷发生额
	private String ledgerStatus;//状态信息
	private String occDate;//时间戳

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
	 * @return 周期
	 */
	public String getWeeks() {
	 	return weeks;
	}
	/**
	 * @设置 周期
	 * @param weeks
	 */
	public void setWeeks(String weeks) {
	 	this.weeks = weeks;
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
	 * @return 科目余额方向
	 */
	public String getDcInd() {
	 	return dcInd;
	}
	/**
	 * @设置 科目余额方向
	 * @param dcInd
	 */
	public void setDcInd(String dcInd) {
	 	this.dcInd = dcInd;
	}
	/**
	 * @return 汇总科目控制字
	 */
	public String getUpAccHrt() {
	 	return upAccHrt;
	}
	/**
	 * @设置 汇总科目控制字
	 * @param upAccHrt
	 */
	public void setUpAccHrt(String upAccHrt) {
	 	this.upAccHrt = upAccHrt;
	}
	/**
	 * @return 当前借方余额
	 */
	public String getDrBal() {
	 	return drBal;
	}
	/**
	 * @设置 当前借方余额
	 * @param drBal
	 */
	public void setDrBal(String drBal) {
	 	this.drBal = drBal;
	}
	/**
	 * @return 当前贷方余额
	 */
	public String getCrBal() {
	 	return crBal;
	}
	/**
	 * @设置 当前贷方余额
	 * @param crBal
	 */
	public void setCrBal(String crBal) {
	 	this.crBal = crBal;
	}
	/**
	 * @return 昨日借方余额
	 */
	public String getLddBal() {
	 	return lddBal;
	}
	/**
	 * @设置 昨日借方余额
	 * @param lddBal
	 */
	public void setLddBal(String lddBal) {
	 	this.lddBal = lddBal;
	}
	/**
	 * @return 昨日贷方余额
	 */
	public String getLcdBal() {
	 	return lcdBal;
	}
	/**
	 * @设置 昨日贷方余额
	 * @param lcdBal
	 */
	public void setLcdBal(String lcdBal) {
	 	this.lcdBal = lcdBal;
	}
	/**
	 * @return 本日借笔数
	 */
	public String getRddCnt() {
	 	return rddCnt;
	}
	/**
	 * @设置 本日借笔数
	 * @param rddCnt
	 */
	public void setRddCnt(String rddCnt) {
	 	this.rddCnt = rddCnt;
	}
	/**
	 * @return 本日待笔数
	 */
	public String getRcdCnt() {
	 	return rcdCnt;
	}
	/**
	 * @设置 本日待笔数
	 * @param rcdCnt
	 */
	public void setRcdCnt(String rcdCnt) {
	 	this.rcdCnt = rcdCnt;
	}
	/**
	 * @return 本日借发生额
	 */
	public String getRddAmt() {
	 	return rddAmt;
	}
	/**
	 * @设置 本日借发生额
	 * @param rddAmt
	 */
	public void setRddAmt(String rddAmt) {
	 	this.rddAmt = rddAmt;
	}
	/**
	 * @return 本日贷发生额
	 */
	public String getRcdAmt() {
	 	return rcdAmt;
	}
	/**
	 * @设置 本日贷发生额
	 * @param rcdAmt
	 */
	public void setRcdAmt(String rcdAmt) {
	 	this.rcdAmt = rcdAmt;
	}
	/**
	 * @return 本日现金借笔数
	 */
	public String getCddCnt() {
	 	return cddCnt;
	}
	/**
	 * @设置 本日现金借笔数
	 * @param cddCnt
	 */
	public void setCddCnt(String cddCnt) {
	 	this.cddCnt = cddCnt;
	}
	/**
	 * @return 本日现金贷笔数
	 */
	public String getCcdCnt() {
	 	return ccdCnt;
	}
	/**
	 * @设置 本日现金贷笔数
	 * @param ccdCnt
	 */
	public void setCcdCnt(String ccdCnt) {
	 	this.ccdCnt = ccdCnt;
	}
	/**
	 * @return 本日现金借发生额
	 */
	public String getCddAmt() {
	 	return cddAmt;
	}
	/**
	 * @设置 本日现金借发生额
	 * @param cddAmt
	 */
	public void setCddAmt(String cddAmt) {
	 	this.cddAmt = cddAmt;
	}
	/**
	 * @return 本日现金贷发生额
	 */
	public String getCcdAmt() {
	 	return ccdAmt;
	}
	/**
	 * @设置 本日现金贷发生额
	 * @param ccdAmt
	 */
	public void setCcdAmt(String ccdAmt) {
	 	this.ccdAmt = ccdAmt;
	}
	/**
	 * @return 旬初借方余额
	 */
	public String getTddrBal() {
	 	return tddrBal;
	}
	/**
	 * @设置 旬初借方余额
	 * @param tddrBal
	 */
	public void setTddrBal(String tddrBal) {
	 	this.tddrBal = tddrBal;
	}
	/**
	 * @return 旬初贷方余额
	 */
	public String getTdcrBal() {
	 	return tdcrBal;
	}
	/**
	 * @设置 旬初贷方余额
	 * @param tdcrBal
	 */
	public void setTdcrBal(String tdcrBal) {
	 	this.tdcrBal = tdcrBal;
	}
	/**
	 * @return 本旬借方笔数
	 */
	public String getTddrCnt() {
	 	return tddrCnt;
	}
	/**
	 * @设置 本旬借方笔数
	 * @param tddrCnt
	 */
	public void setTddrCnt(String tddrCnt) {
	 	this.tddrCnt = tddrCnt;
	}
	/**
	 * @return 本旬贷方笔数
	 */
	public String getTdcrCnt() {
	 	return tdcrCnt;
	}
	/**
	 * @设置 本旬贷方笔数
	 * @param tdcrCnt
	 */
	public void setTdcrCnt(String tdcrCnt) {
	 	this.tdcrCnt = tdcrCnt;
	}
	/**
	 * @return 本旬借方发生额
	 */
	public String getTddrAmt() {
	 	return tddrAmt;
	}
	/**
	 * @设置 本旬借方发生额
	 * @param tddrAmt
	 */
	public void setTddrAmt(String tddrAmt) {
	 	this.tddrAmt = tddrAmt;
	}
	/**
	 * @return 本旬贷方发生额
	 */
	public String getTdcrAmt() {
	 	return tdcrAmt;
	}
	/**
	 * @设置 本旬贷方发生额
	 * @param tdcrAmt
	 */
	public void setTdcrAmt(String tdcrAmt) {
	 	this.tdcrAmt = tdcrAmt;
	}
	/**
	 * @return 月初借余额
	 */
	public String getMdrBal() {
	 	return mdrBal;
	}
	/**
	 * @设置 月初借余额
	 * @param mdrBal
	 */
	public void setMdrBal(String mdrBal) {
	 	this.mdrBal = mdrBal;
	}
	/**
	 * @return 月初贷余额
	 */
	public String getMcrBal() {
	 	return mcrBal;
	}
	/**
	 * @设置 月初贷余额
	 * @param mcrBal
	 */
	public void setMcrBal(String mcrBal) {
	 	this.mcrBal = mcrBal;
	}
	/**
	 * @return 本月借笔数
	 */
	public String getMdrCnt() {
	 	return mdrCnt;
	}
	/**
	 * @设置 本月借笔数
	 * @param mdrCnt
	 */
	public void setMdrCnt(String mdrCnt) {
	 	this.mdrCnt = mdrCnt;
	}
	/**
	 * @return 本月贷笔数
	 */
	public String getMcrCnt() {
	 	return mcrCnt;
	}
	/**
	 * @设置 本月贷笔数
	 * @param mcrCnt
	 */
	public void setMcrCnt(String mcrCnt) {
	 	this.mcrCnt = mcrCnt;
	}
	/**
	 * @return 本月借发生额
	 */
	public String getMdrAmt() {
	 	return mdrAmt;
	}
	/**
	 * @设置 本月借发生额
	 * @param mdrAmt
	 */
	public void setMdrAmt(String mdrAmt) {
	 	this.mdrAmt = mdrAmt;
	}
	/**
	 * @return 本月贷发生额
	 */
	public String getMcrAmt() {
	 	return mcrAmt;
	}
	/**
	 * @设置 本月贷发生额
	 * @param mcrAmt
	 */
	public void setMcrAmt(String mcrAmt) {
	 	this.mcrAmt = mcrAmt;
	}
	/**
	 * @return 季初借余额
	 */
	public String getQdrBal() {
	 	return qdrBal;
	}
	/**
	 * @设置 季初借余额
	 * @param qdrBal
	 */
	public void setQdrBal(String qdrBal) {
	 	this.qdrBal = qdrBal;
	}
	/**
	 * @return 季初贷余额
	 */
	public String getQcrBal() {
	 	return qcrBal;
	}
	/**
	 * @设置 季初贷余额
	 * @param qcrBal
	 */
	public void setQcrBal(String qcrBal) {
	 	this.qcrBal = qcrBal;
	}
	/**
	 * @return 本季借笔数
	 */
	public String getQdrCnt() {
	 	return qdrCnt;
	}
	/**
	 * @设置 本季借笔数
	 * @param qdrCnt
	 */
	public void setQdrCnt(String qdrCnt) {
	 	this.qdrCnt = qdrCnt;
	}
	/**
	 * @return 本季贷笔数
	 */
	public String getQcrCnt() {
	 	return qcrCnt;
	}
	/**
	 * @设置 本季贷笔数
	 * @param qcrCnt
	 */
	public void setQcrCnt(String qcrCnt) {
	 	this.qcrCnt = qcrCnt;
	}
	/**
	 * @return 本季借发生额
	 */
	public String getQdrAmt() {
	 	return qdrAmt;
	}
	/**
	 * @设置 本季借发生额
	 * @param qdrAmt
	 */
	public void setQdrAmt(String qdrAmt) {
	 	this.qdrAmt = qdrAmt;
	}
	/**
	 * @return 本季贷发生额
	 */
	public String getQcrAmt() {
	 	return qcrAmt;
	}
	/**
	 * @设置 本季贷发生额
	 * @param qcrAmt
	 */
	public void setQcrAmt(String qcrAmt) {
	 	this.qcrAmt = qcrAmt;
	}
	/**
	 * @return 年初借余额
	 */
	public String getYdrBal() {
	 	return ydrBal;
	}
	/**
	 * @设置 年初借余额
	 * @param ydrBal
	 */
	public void setYdrBal(String ydrBal) {
	 	this.ydrBal = ydrBal;
	}
	/**
	 * @return 年初贷余额
	 */
	public String getYcrBal() {
	 	return ycrBal;
	}
	/**
	 * @设置 年初贷余额
	 * @param ycrBal
	 */
	public void setYcrBal(String ycrBal) {
	 	this.ycrBal = ycrBal;
	}
	/**
	 * @return 本年借笔数
	 */
	public String getYdrAnt() {
	 	return ydrAnt;
	}
	/**
	 * @设置 本年借笔数
	 * @param ydrAnt
	 */
	public void setYdrAnt(String ydrAnt) {
	 	this.ydrAnt = ydrAnt;
	}
	/**
	 * @return 本年贷笔数
	 */
	public String getYcrCnt() {
	 	return ycrCnt;
	}
	/**
	 * @设置 本年贷笔数
	 * @param ycrCnt
	 */
	public void setYcrCnt(String ycrCnt) {
	 	this.ycrCnt = ycrCnt;
	}
	/**
	 * @return 本年借发生额
	 */
	public String getYdrAmt() {
	 	return ydrAmt;
	}
	/**
	 * @设置 本年借发生额
	 * @param ydrAmt
	 */
	public void setYdrAmt(String ydrAmt) {
	 	this.ydrAmt = ydrAmt;
	}
	/**
	 * @return 本年贷发生额
	 */
	public String getYcrAmt() {
	 	return ycrAmt;
	}
	/**
	 * @设置 本年贷发生额
	 * @param ycrAmt
	 */
	public void setYcrAmt(String ycrAmt) {
	 	this.ycrAmt = ycrAmt;
	}
	/**
	 * @return 状态信息
	 */
	public String getLedgerStatus() {
	 	return ledgerStatus;
	}
	/**
	 * @设置 状态信息
	 * @param ledgerStatus
	 */
	public void setLedgerStatus(String ledgerStatus) {
	 	this.ledgerStatus = ledgerStatus;
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
}