package app.component.finance.paramset.entity;

import app.base.BaseDomain;

/**
 * Title: CwCycle.java Description:
 * 
 * @author：kaifa@dhcc.com.cn
 * @Fri Jan 06 20:11:05 CST 2017
 * @version：1.0
 **/
public class CwCycle extends BaseDomain {
	private String uid;// 唯一编号
	private String finBooks;// 帐套标识
	private String traceNo;// 流水号
	private String type;// 是否是自然年度期间Y：自然年度会计期间；N：会计期间期数；
	private String txDate;// 设置日期
	private String txTime;// 设置时间
	private String txYear;// 年份
	private String remark;// 备注

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
	 * @return 流水号
	 */
	public String getTraceNo() {
		return traceNo;
	}

	/**
	 * @设置 流水号
	 * @param traceNo
	 */
	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	/**
	 * @return 是否是自然年度期间Y：自然年度会计期间；N：会计期间期数；
	 */
	public String getType() {
		return type;
	}

	/**
	 * @设置 是否是自然年度期间Y：自然年度会计期间；N：会计期间期数；
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return 设置日期
	 */
	public String getTxDate() {
		return txDate;
	}

	/**
	 * @设置 设置日期
	 * @param txDate
	 */
	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	/**
	 * @return 设置时间
	 */
	public String getTxTime() {
		return txTime;
	}

	/**
	 * @设置 设置时间
	 * @param txTime
	 */
	public void setTxTime(String txTime) {
		this.txTime = txTime;
	}

	/**
	 * @return 年份
	 */
	public String getTxYear() {
		return txYear;
	}

	/**
	 * @设置 年份
	 * @param txYear
	 */
	public void setTxYear(String txYear) {
		this.txYear = txYear;
	}

	/**
	 * @return 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @设置 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}