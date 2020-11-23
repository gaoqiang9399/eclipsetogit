package app.component.finance.account.entity;
import app.base.BaseDomain;
/**
* Title: CwFicationData.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jan 23 10:00:27 CST 2017
* @version：1.0
**/
public class CwFicationData extends BaseDomain {
	private String uid;//唯一编号
	private String finBooks;//帐套标识
	private String txType;//辅助核算项编号
	private String txCode;//编号
	private String txName;//名称
	private String typeName;//辅助核算名称，数据库没有此字段
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
	 * @return 辅助核算项编号
	 */
	public String getTxType() {
	 	return txType;
	}
	/**
	 * @设置 辅助核算项编号
	 * @param txType
	 */
	public void setTxType(String txType) {
	 	this.txType = txType;
	}
	/**
	 * @return 编号
	 */
	public String getTxCode() {
	 	return txCode;
	}
	/**
	 * @设置 编号
	 * @param txCode
	 */
	public void setTxCode(String txCode) {
	 	this.txCode = txCode;
	}
	/**
	 * @return 名称
	 */
	public String getTxName() {
	 	return txName;
	}
	/**
	 * @设置 名称
	 * @param txName
	 */
	public void setTxName(String txName) {
	 	this.txName = txName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}