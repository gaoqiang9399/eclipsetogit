package app.component.finance.voucher.entity;
import app.base.BaseDomain;
/**
* Title: CwVoucherRemarks.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Jan 07 09:04:42 CST 2017
* @version：1.0
**/
public class CwVoucherRemarks extends BaseDomain {
	private String uid;//唯一编号
	private String finBooks;//帐套标识
	private String voucherRemark;//摘要信息
	private String createDate;//创建日期
	private String createTime;//创建时间
	private String opNo;//创建人编号
	private String opName;//创建人名称
	private String occTime;//时间戳格式：YYYYMMDD HH:mm:ss

	public CwVoucherRemarks() {
	}
	
	public CwVoucherRemarks(String finBooks) {
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
	 * @return 摘要信息
	 */
	public String getVoucherRemark() {
	 	return voucherRemark;
	}
	/**
	 * @设置 摘要信息
	 * @param voucherRemark
	 */
	public void setVoucherRemark(String voucherRemark) {
	 	this.voucherRemark = voucherRemark;
	}
	/**
	 * @return 创建日期
	 */
	public String getCreateDate() {
	 	return createDate;
	}
	/**
	 * @设置 创建日期
	 * @param createDate
	 */
	public void setCreateDate(String createDate) {
	 	this.createDate = createDate;
	}
	/**
	 * @return 创建时间
	 */
	public String getCreateTime() {
	 	return createTime;
	}
	/**
	 * @设置 创建时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
	 	this.createTime = createTime;
	}
	/**
	 * @return 创建人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 创建人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 创建人名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 创建人名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 时间戳格式：YYYYMMDD HH:mm:ss
	 */
	public String getOccTime() {
	 	return occTime;
	}
	/**
	 * @设置 时间戳格式：YYYYMMDD HH:mm:ss
	 * @param occTime
	 */
	public void setOccTime(String occTime) {
	 	this.occTime = occTime;
	}
}