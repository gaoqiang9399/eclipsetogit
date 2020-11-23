package app.component.accnt.entity;
import app.base.BaseDomain;
/**
* Title: MfAdjustRecord.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Jun 04 17:14:31 CST 2016
* @version：1.0
**/
public class MfAdjustRecord extends BaseDomain {
	private String adjustId;//账款调整ID
	private String pactId;//合同号
	private String transferId;//应收帐款转让ID
	private Double beforeAdjustAmt;//调整前金额
	private Double afterAdjustAmt;//调整后金额
	private String adjustDate;//调整日期
	private String adjustReason;//调整原因
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String brNo;//部门编号
	private String brName;//部门名称
	private String lstModTime;//最后修改时间

	/**
	 * @return 账款调整ID
	 */
	public String getAdjustId() {
	 	return adjustId;
	}
	/**
	 * @设置 账款调整ID
	 * @param adjustId
	 */
	public void setAdjustId(String adjustId) {
	 	this.adjustId = adjustId;
	}
	/**
	 * @return 合同号
	 */
	public String getPactId() {
	 	return pactId;
	}
	/**
	 * @设置 合同号
	 * @param pactId
	 */
	public void setPactId(String pactId) {
	 	this.pactId = pactId;
	}
	/**
	 * @return 应收帐款转让ID
	 */
	public String getTransferId() {
	 	return transferId;
	}
	/**
	 * @设置 应收帐款转让ID
	 * @param transferId
	 */
	public void setTransferId(String transferId) {
	 	this.transferId = transferId;
	}
	/**
	 * @return 调整前金额
	 */
	public Double getBeforeAdjustAmt() {
	 	return beforeAdjustAmt;
	}
	/**
	 * @设置 调整前金额
	 * @param beforeAdjustAmt
	 */
	public void setBeforeAdjustAmt(Double beforeAdjustAmt) {
	 	this.beforeAdjustAmt = beforeAdjustAmt;
	}
	/**
	 * @return 调整后金额
	 */
	public Double getAfterAdjustAmt() {
	 	return afterAdjustAmt;
	}
	/**
	 * @设置 调整后金额
	 * @param afterAdjustAmt
	 */
	public void setAfterAdjustAmt(Double afterAdjustAmt) {
	 	this.afterAdjustAmt = afterAdjustAmt;
	}
	/**
	 * @return 调整日期
	 */
	public String getAdjustDate() {
	 	return adjustDate;
	}
	/**
	 * @设置 调整日期
	 * @param adjustDate
	 */
	public void setAdjustDate(String adjustDate) {
	 	this.adjustDate = adjustDate;
	}
	/**
	 * @return 调整原因
	 */
	public String getAdjustReason() {
	 	return adjustReason;
	}
	/**
	 * @设置 调整原因
	 * @param adjustReason
	 */
	public void setAdjustReason(String adjustReason) {
	 	this.adjustReason = adjustReason;
	}
	/**
	 * @return 操作员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作员名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 操作员名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
}