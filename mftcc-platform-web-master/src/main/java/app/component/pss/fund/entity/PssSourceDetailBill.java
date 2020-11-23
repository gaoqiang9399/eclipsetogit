package app.component.pss.fund.entity;
import app.base.BaseDomain;
/**
* Title: PssSourceDetailBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Sep 20 18:03:13 CST 2017
* @version：1.0
**/
public class PssSourceDetailBill extends BaseDomain {
	private String sourceDetailId;//收付核源单明细ID
	private String billNo;//收付核单据编号
	private Integer sequence;//序号
	private String recPayCancelType;//收付核类型
	private String cancelDetailType;//核销单据类型
	private String sourceBillNo;//源单编号
	private String sourceBillType;//源单业务类别
	private String sourceBillDate;//源单单据日期
	private Double sourceBillAmt;//源单单据金额
	private Double comCancelAmt;//已核销金额
	private Double notCancelAmt;//未核销金额
	private Double currCancelAmt;//本次核销金额
	private String auditSts;//审核状态
	
	private String cusNo;//客户id
	private String supplierId;//供应商id
	
	private String sourceBillTypeName;//源单业务类别名称

	/**
	 * @return 收付核源单明细ID
	 */
	public String getSourceDetailId() {
	 	return sourceDetailId;
	}
	/**
	 * @设置 收付核源单明细ID
	 * @param sourceDetailId
	 */
	public void setSourceDetailId(String sourceDetailId) {
	 	this.sourceDetailId = sourceDetailId;
	}
	/**
	 * @return 收付核单据编号
	 */
	public String getBillNo() {
	 	return billNo;
	}
	/**
	 * @设置 收付核单据编号
	 * @param billNo
	 */
	public void setBillNo(String billNo) {
	 	this.billNo = billNo;
	}
	/**
	 * @return 序号
	 */
	public Integer getSequence() {
	 	return sequence;
	}
	/**
	 * @设置 序号
	 * @param sequence
	 */
	public void setSequence(Integer sequence) {
	 	this.sequence = sequence;
	}
	/**
	 * @return 收付核类型
	 */
	public String getRecPayCancelType() {
	 	return recPayCancelType;
	}
	/**
	 * @设置 收付核类型
	 * @param recPayCancelType
	 */
	public void setRecPayCancelType(String recPayCancelType) {
	 	this.recPayCancelType = recPayCancelType;
	}
	/**
	 * @return 核销单据类型
	 */
	public String getCancelDetailType() {
	 	return cancelDetailType;
	}
	/**
	 * @设置 核销单据类型
	 * @param cancelDetailType
	 */
	public void setCancelDetailType(String cancelDetailType) {
	 	this.cancelDetailType = cancelDetailType;
	}
	/**
	 * @return 源单编号
	 */
	public String getSourceBillNo() {
	 	return sourceBillNo;
	}
	/**
	 * @设置 源单编号
	 * @param sourceBillNo
	 */
	public void setSourceBillNo(String sourceBillNo) {
	 	this.sourceBillNo = sourceBillNo;
	}
	/**
	 * @return 源单业务类别
	 */
	public String getSourceBillType() {
	 	return sourceBillType;
	}
	/**
	 * @设置 源单业务类别
	 * @param sourceBillType
	 */
	public void setSourceBillType(String sourceBillType) {
	 	this.sourceBillType = sourceBillType;
	}
	/**
	 * @return 源单单据日期
	 */
	public String getSourceBillDate() {
	 	return sourceBillDate;
	}
	/**
	 * @设置 源单单据日期
	 * @param sourceBillDate
	 */
	public void setSourceBillDate(String sourceBillDate) {
	 	this.sourceBillDate = sourceBillDate;
	}
	/**
	 * @return 源单单据金额
	 */
	public Double getSourceBillAmt() {
	 	return sourceBillAmt;
	}
	/**
	 * @设置 源单单据金额
	 * @param sourceBillAmt
	 */
	public void setSourceBillAmt(Double sourceBillAmt) {
	 	this.sourceBillAmt = sourceBillAmt;
	}
	/**
	 * @return 已核销金额
	 */
	public Double getComCancelAmt() {
	 	return comCancelAmt;
	}
	/**
	 * @设置 已核销金额
	 * @param comCancelAmt
	 */
	public void setComCancelAmt(Double comCancelAmt) {
	 	this.comCancelAmt = comCancelAmt;
	}
	/**
	 * @return 未核销金额
	 */
	public Double getNotCancelAmt() {
	 	return notCancelAmt;
	}
	/**
	 * @设置 未核销金额
	 * @param notCancelAmt
	 */
	public void setNotCancelAmt(Double notCancelAmt) {
	 	this.notCancelAmt = notCancelAmt;
	}
	/**
	 * @return 本次核销金额
	 */
	public Double getCurrCancelAmt() {
	 	return currCancelAmt;
	}
	/**
	 * @设置 本次核销金额
	 * @param currCancelAmt
	 */
	public void setCurrCancelAmt(Double currCancelAmt) {
	 	this.currCancelAmt = currCancelAmt;
	}
	public String getAuditSts() {
		return auditSts;
	}
	public void setAuditSts(String auditSts) {
		this.auditSts = auditSts;
	}
	public String getCusNo() {
		return cusNo;
	}
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSourceBillTypeName() {
		return sourceBillTypeName;
	}
	public void setSourceBillTypeName(String sourceBillTypeName) {
		this.sourceBillTypeName = sourceBillTypeName;
	}
}