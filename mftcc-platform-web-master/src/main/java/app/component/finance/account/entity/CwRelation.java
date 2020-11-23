package app.component.finance.account.entity;
import app.base.BaseDomain;
/**
* Title: CwRelation.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Jan 24 09:42:23 CST 2017
* @version：1.0
**/
public class CwRelation extends BaseDomain {
	
	private String finBooks;//帐套标识
	private String uid;//唯一编号
	private String accHrt;//科目控制字
	private String txType;//辅助核算编号
	private String opNo;//设置人编号
	private String opName;//设置人名称
	private String occTime;//时间戳
	
	private String accNo;//科目编号,数据库没有此字段
	private String txName;//辅助核算名称,数据库没有此字段
	private String accName;//科目名称,数据库没有此字段
	private String txflag;//辅助核算类型
	private String copyFinBooks;
	

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
	public String getTxType() {
	 	return txType;
	}
	/**
	 * @设置 辅助核算编号
	 * @param txType
	 */
	public void setTxType(String txType) {
	 	this.txType = txType;
	}
	/**
	 * @return 设置人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 设置人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 设置人名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 设置人名称
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
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getTxName() {
		return txName;
	}
	public void setTxName(String txName) {
		this.txName = txName;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getTxflag() {
		return txflag;
	}
	public void setTxflag(String txflag) {
		this.txflag = txflag;
	}
	public String getFinBooks() {
		return finBooks;
	}
	public void setFinBooks(String finBooks) {
		this.finBooks = finBooks;
	}
	public String getCopyFinBooks() {
		return copyFinBooks;
	}
	public void setCopyFinBooks(String copyFinBooks) {
		this.copyFinBooks = copyFinBooks;
	}
	
}