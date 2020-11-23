package app.component.finance.account.entity;
import app.base.BaseDomain;
/**
* Title: CwFication.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Jan 19 15:24:19 CST 2017
* @version：1.0
**/
public class CwFication extends BaseDomain {
	private String finBooks;//帐套标识
	private String txType;//类型编号
	private String typeName;//类型名称
	private String isUse;//是否启用Y：是；N：否；
	private String isSys;//是否是系统默认Y：是；N：否；
	private String flag;//辅助核算类型0：员工；1：部门；2：客户；3：自定义
	private String zTable;//对应表信息

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
	 * @return 类型编号
	 */
	public String getTxType() {
	 	return txType;
	}
	/**
	 * @设置 类型编号
	 * @param txType
	 */
	public void setTxType(String txType) {
	 	this.txType = txType;
	}
	/**
	 * @return 类型名称
	 */
	public String getTypeName() {
	 	return typeName;
	}
	/**
	 * @设置 类型名称
	 * @param typeName
	 */
	public void setTypeName(String typeName) {
	 	this.typeName = typeName;
	}
	/**
	 * @return 是否启用Y：是；N：否；
	 */
	public String getIsUse() {
	 	return isUse;
	}
	/**
	 * @设置 是否启用Y：是；N：否；
	 * @param isUse
	 */
	public void setIsUse(String isUse) {
	 	this.isUse = isUse;
	}
	/**
	 * @return 是否是系统默认Y：是；N：否；
	 */
	public String getIsSys() {
	 	return isSys;
	}
	/**
	 * @设置 是否是系统默认Y：是；N：否；
	 * @param isSys
	 */
	public void setIsSys(String isSys) {
	 	this.isSys = isSys;
	}
	/**
	 * @return 辅助核算类型0：员工；1：部门；2：客户；3：自定义
	 */
	public String getFlag() {
	 	return flag;
	}
	/**
	 * @设置 辅助核算类型0：员工；1：部门；2：客户；3：自定义
	 * @param flag
	 */
	public void setFlag(String flag) {
	 	this.flag = flag;
	}
	/**
	 * @return 对应表信息
	 */
	public String getZTable() {
	 	return zTable;
	}
	/**
	 * @设置 对应表信息
	 * @param zTable
	 */
	public void setZTable(String zTable) {
	 	this.zTable = zTable;
	}
}