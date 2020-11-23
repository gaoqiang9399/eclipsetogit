package app.component.finance.cwtools.entity;
import app.base.BaseDomain;
/**
* Title: CwMonthKnot.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Jan 05 17:01:51 CST 2017
* @version：1.0
**/
public class CwMonthKnot extends BaseDomain {
	private String finBooks;//帐套标志
	private String txYear;//年份格式：yyyy
	private String txWeek;//会计周期格式：yyyyMM
	private String txFlag;//结账标识Y：已结账；N：未结账；
	private String opNo;//结账人
	private String opName;//结账人名称
	private String occTime;//时间戳

	/**
	 * @return 年份格式：yyyy
	 */
	public String getTxYear() {
	 	return txYear;
	}
	/**
	 * @设置 年份格式：yyyy
	 * @param txYear
	 */
	public void setTxYear(String txYear) {
	 	this.txYear = txYear;
	}
	/**
	 * @return 会计周期格式：yyyyMM
	 */
	public String getTxWeek() {
	 	return txWeek;
	}
	/**
	 * @设置 会计周期格式：yyyyMM
	 * @param txWeek
	 */
	public void setTxWeek(String txWeek) {
	 	this.txWeek = txWeek;
	}
	/**
	 * @return 结账标识Y：已结账；N：未结账；
	 */
	public String getTxFlag() {
	 	return txFlag;
	}
	/**
	 * @设置 结账标识Y：已结账；N：未结账；
	 * @param txFlag
	 */
	public void setTxFlag(String txFlag) {
	 	this.txFlag = txFlag;
	}
	/**
	 * @return 结账人
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 结账人
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 结账人名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 结账人名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
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
	public String getFinBooks() {
		return finBooks;
	}
	public void setFinBooks(String finBooks) {
		this.finBooks = finBooks;
	}
	
}