package app.component.finance.paramset.entity;
import app.base.BaseDomain;
/**
* Title: CwVchRuleMst.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Mar 08 14:53:10 CST 2017
* @version：1.0
**/
public class CwVchRuleMst extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String traceNo;//流水号
	private String txCode;//交易代码
	private String txName;//交易名称
	private String prdtNo;//产品编码已@符合隔开
	private String txType;//交易类型01：财务；02：信贷
	private String isUse;//启用标识Y：启用；N：禁用
	private String isHide;//是否隐藏Y：隐藏；N：显示
	private String pzProofNo;//凭证字
	private String remarks;//备注
	private String finBooks;//帐套标识
	

	
	private String txTypeName;//数据库没有此字段
	
	public CwVchRuleMst() {
	}
	
	public CwVchRuleMst(String finBooks) {
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
	 * @return 交易代码
	 */
	public String getTxCode() {
	 	return txCode;
	}
	/**
	 * @设置 交易代码
	 * @param txCode
	 */
	public void setTxCode(String txCode) {
	 	this.txCode = txCode;
	}
	/**
	 * @return 交易名称
	 */
	public String getTxName() {
	 	return txName;
	}
	/**
	 * @设置 交易名称
	 * @param txName
	 */
	public void setTxName(String txName) {
	 	this.txName = txName;
	}
	/**
	 * @return 产品编码已@符合隔开
	 */
	public String getPrdtNo() {
	 	return prdtNo;
	}
	/**
	 * @设置 产品编码已@符合隔开
	 * @param prdtNo
	 */
	public void setPrdtNo(String prdtNo) {
	 	this.prdtNo = prdtNo;
	}
	/**
	 * @return 交易类型01：财务；02：信贷
	 */
	public String getTxType() {
	 	return txType;
	}
	/**
	 * @设置 交易类型01：财务；02：信贷
	 * @param txType
	 */
	public void setTxType(String txType) {
	 	this.txType = txType;
	}
	/**
	 * @return 启用标识Y：启用；N：禁用
	 */
	public String getIsUse() {
	 	return isUse;
	}
	/**
	 * @设置 启用标识Y：启用；N：禁用
	 * @param isUse
	 */
	public void setIsUse(String isUse) {
	 	this.isUse = isUse;
	}
	/**
	 * @return 是否隐藏Y：隐藏；N：显示
	 */
	public String getIsHide() {
	 	return isHide;
	}
	/**
	 * @设置 是否隐藏Y：隐藏；N：显示
	 * @param isHide
	 */
	public void setIsHide(String isHide) {
	 	this.isHide = isHide;
	}
	/**
	 * @return 凭证字
	 */
	public String getPzProofNo() {
	 	return pzProofNo;
	}
	/**
	 * @设置 凭证字
	 * @param pzProofNo
	 */
	public void setPzProofNo(String pzProofNo) {
	 	this.pzProofNo = pzProofNo;
	}
	/**
	 * @return 备注
	 */
	public String getRemarks() {
	 	return remarks;
	}
	/**
	 * @设置 备注
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
	 	this.remarks = remarks;
	}
	public String getTxTypeName() {
		return txTypeName;
	}
	public void setTxTypeName(String txTypeName) {
		this.txTypeName = txTypeName;
	}
	public String getFinBooks() {
		return finBooks;
	}
	public void setFinBooks(String finBooks) {
		this.finBooks = finBooks;
	}

	
}