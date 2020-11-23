package app.component.finance.account.entity;
import app.base.BaseDomain;
/**
* Title: CwFicationData.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jan 23 10:00:27 CST 2017
* @version：1.0
**/
public class CwAssistData extends BaseDomain {
	private String txType;//辅助核算项编号
	private String txCode;//编号
	private String txName;//名称
	
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
}