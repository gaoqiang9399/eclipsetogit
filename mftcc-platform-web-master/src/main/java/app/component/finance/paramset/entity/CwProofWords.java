package app.component.finance.paramset.entity;
import app.base.BaseDomain;
/**
* Title: CwProofWords.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Dec 26 16:14:31 CST 2016
* @version：1.0
**/
public class CwProofWords extends BaseDomain {
	private String uid;//唯一编号
	private String finBooks;//帐套标识
	private String pzProofNo;//凭证字编号使用4位数字
	private String pzTitle;//凭证打印标题
	private String pzPrefix;//凭证前缀
	private String isAuto;//是否默认Y：是 N：否
	private String txDate;//设置日期
	private String txTime;//设置时间
	private String opNo;//设置人编号
	private String opName;//设置人名称
	private String occTime;//时间戳格式：yyyyMMdd HH:mm:ss

	public CwProofWords() {
	}
	
	public CwProofWords(String finBooks) {
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
	 * @return 凭证字编号使用4位数字
	 */
	public String getPzProofNo() {
	 	return pzProofNo;
	}
	/**
	 * @设置 凭证字编号使用4位数字
	 * @param pzProofNo
	 */
	public void setPzProofNo(String pzProofNo) {
	 	this.pzProofNo = pzProofNo;
	}
	/**
	 * @return 凭证打印标题
	 */
	public String getPzTitle() {
	 	return pzTitle;
	}
	/**
	 * @设置 凭证打印标题
	 * @param pzTitle
	 */
	public void setPzTitle(String pzTitle) {
	 	this.pzTitle = pzTitle;
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
	 * @return 是否默认Y：是 N：否
	 */
	public String getIsAuto() {
	 	return isAuto;
	}
	/**
	 * @设置 是否默认Y：是 N：否
	 * @param isAuto
	 */
	public void setIsAuto(String isAuto) {
	 	this.isAuto = isAuto;
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
	 * @return 时间戳格式：yyyyMMdd HH:mm:ss
	 */
	public String getOccTime() {
	 	return occTime;
	}
	/**
	 * @设置 时间戳格式：yyyyMMdd HH:mm:ss
	 * @param occTime
	 */
	public void setOccTime(String occTime) {
	 	this.occTime = occTime;
	}
}