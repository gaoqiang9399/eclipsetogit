package app.component.pss.fund.entity;
import app.base.BaseDomain;
/**
* Title: PssBuySaleExpBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Nov 09 15:53:42 CST 2017
* @version：1.0
**/
public class PssBuySaleExpBill extends BaseDomain {
	private String buySaleExpId;//采购销售费用清单ID
	private String buySaleExpNo;//采购销售费用清单编号
	private Integer sequence;//序号
	private String supplierId;//供应商ID
	private String supplierName;//供应商名称
	private String saleType;//支出类别
	private Double expAmt;//金额
	private Double unpaidAmt;//未付费用
	private String sourceBillNo;//源单号
	private String sourceBillType;//源单业务类型
	private String isApportion;//是否分摊
	private String sourceContactId;//源单往来单位ID
	private String sourceContactName;//源单往来单位名称
	private String otherPayNo;//其他支出单编号
	private String sourceBillDate;//源单日期
	private String memo;//备注
	private String paidState;//付款状态
	private String auditSts;//审核状态
	private String regOpNo;//制单人编号
	private String regOpName;//制单人名称
	private String regBrNo;//制单人机构编号
	private String regBrName;//制单人机构名称
	private String regTime;//制单时间
	private String auditOpNo;//审核人编号
	private String auditOpName;//审核人名称
	private String auditBrNo;//审核人机构编号
	private String auditBrName;//审核人机构名称
	private String auditTime;//审核时间
	private String lstModOpNo;//最后修改人编号
	private String lstModOpName;//最后修改人名称
	private String lstModBrNo;//最后修改人机构编号
	private String lstModBrName;//最后修改人机构名称
	private String lstModTime;//最后修改时间

	/**
	 * @return 采购销售费用清单ID
	 */
	public String getBuySaleExpId() {
	 	return buySaleExpId;
	}
	/**
	 * @设置 采购销售费用清单ID
	 * @param buySaleExpId
	 */
	public void setBuySaleExpId(String buySaleExpId) {
	 	this.buySaleExpId = buySaleExpId;
	}
	/**
	 * @return 采购销售费用清单编号
	 */
	public String getBuySaleExpNo() {
	 	return buySaleExpNo;
	}
	/**
	 * @设置 采购销售费用清单编号
	 * @param buySaleExpNo
	 */
	public void setBuySaleExpNo(String buySaleExpNo) {
	 	this.buySaleExpNo = buySaleExpNo;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	/**
	 * @return 供应商ID
	 */
	public String getSupplierId() {
	 	return supplierId;
	}
	/**
	 * @设置 供应商ID
	 * @param supplierId
	 */
	public void setSupplierId(String supplierId) {
	 	this.supplierId = supplierId;
	}
	/**
	 * @return 供应商名称
	 */
	public String getSupplierName() {
	 	return supplierName;
	}
	/**
	 * @设置 供应商名称
	 * @param supplierName
	 */
	public void setSupplierName(String supplierName) {
	 	this.supplierName = supplierName;
	}
	/**
	 * @return 支出类别
	 */
	public String getSaleType() {
	 	return saleType;
	}
	/**
	 * @设置 支出类别
	 * @param saleType
	 */
	public void setSaleType(String saleType) {
	 	this.saleType = saleType;
	}
	/**
	 * @return 金额
	 */
	public Double getExpAmt() {
	 	return expAmt;
	}
	/**
	 * @设置 金额
	 * @param expAmt
	 */
	public void setExpAmt(Double expAmt) {
	 	this.expAmt = expAmt;
	}
	/**
	 * @return 未付费用
	 */
	public Double getUnpaidAmt() {
	 	return unpaidAmt;
	}
	/**
	 * @设置 未付费用
	 * @param unpaidAmt
	 */
	public void setUnpaidAmt(Double unpaidAmt) {
	 	this.unpaidAmt = unpaidAmt;
	}
	/**
	 * @return 源单号
	 */
	public String getSourceBillNo() {
	 	return sourceBillNo;
	}
	/**
	 * @设置 源单号
	 * @param sourceBillNo
	 */
	public void setSourceBillNo(String sourceBillNo) {
	 	this.sourceBillNo = sourceBillNo;
	}
	public String getSourceBillType() {
		return sourceBillType;
	}
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}
	/**
	 * @return 是否分摊
	 */
	public String getIsApportion() {
	 	return isApportion;
	}
	/**
	 * @设置 是否分摊
	 * @param isApportion
	 */
	public void setIsApportion(String isApportion) {
	 	this.isApportion = isApportion;
	}
	/**
	 * @return 源单往来单位ID
	 */
	public String getSourceContactId() {
	 	return sourceContactId;
	}
	/**
	 * @设置 源单往来单位ID
	 * @param sourceContactId
	 */
	public void setSourceContactId(String sourceContactId) {
	 	this.sourceContactId = sourceContactId;
	}
	/**
	 * @return 源单往来单位名称
	 */
	public String getSourceContactName() {
	 	return sourceContactName;
	}
	/**
	 * @设置 源单往来单位名称
	 * @param sourceContactName
	 */
	public void setSourceContactName(String sourceContactName) {
	 	this.sourceContactName = sourceContactName;
	}
	/**
	 * @return 其他支出单编号
	 */
	public String getOtherPayNo() {
	 	return otherPayNo;
	}
	/**
	 * @设置 其他支出单编号
	 * @param otherPayNo
	 */
	public void setOtherPayNo(String otherPayNo) {
	 	this.otherPayNo = otherPayNo;
	}
	/**
	 * @return 源单日期
	 */
	public String getSourceBillDate() {
	 	return sourceBillDate;
	}
	/**
	 * @设置 源单日期
	 * @param sourceBillDate
	 */
	public void setSourceBillDate(String sourceBillDate) {
	 	this.sourceBillDate = sourceBillDate;
	}
	/**
	 * @return 备注
	 */
	public String getMemo() {
	 	return memo;
	}
	/**
	 * @设置 备注
	 * @param memo
	 */
	public void setMemo(String memo) {
	 	this.memo = memo;
	}
	/**
	 * @return 付款状态
	 */
	public String getPaidState() {
	 	return paidState;
	}
	/**
	 * @设置 付款状态
	 * @param paidState
	 */
	public void setPaidState(String paidState) {
	 	this.paidState = paidState;
	}
	/**
	 * @return 审核状态
	 */
	public String getAuditSts() {
	 	return auditSts;
	}
	/**
	 * @设置 审核状态
	 * @param auditSts
	 */
	public void setAuditSts(String auditSts) {
	 	this.auditSts = auditSts;
	}
	/**
	 * @return 制单人编号
	 */
	public String getRegOpNo() {
	 	return regOpNo;
	}
	/**
	 * @设置 制单人编号
	 * @param regOpNo
	 */
	public void setRegOpNo(String regOpNo) {
	 	this.regOpNo = regOpNo;
	}
	/**
	 * @return 制单人名称
	 */
	public String getRegOpName() {
	 	return regOpName;
	}
	/**
	 * @设置 制单人名称
	 * @param regOpName
	 */
	public void setRegOpName(String regOpName) {
	 	this.regOpName = regOpName;
	}
	/**
	 * @return 制单人机构编号
	 */
	public String getRegBrNo() {
	 	return regBrNo;
	}
	/**
	 * @设置 制单人机构编号
	 * @param regBrNo
	 */
	public void setRegBrNo(String regBrNo) {
	 	this.regBrNo = regBrNo;
	}
	/**
	 * @return 制单人机构名称
	 */
	public String getRegBrName() {
	 	return regBrName;
	}
	/**
	 * @设置 制单人机构名称
	 * @param regBrName
	 */
	public void setRegBrName(String regBrName) {
	 	this.regBrName = regBrName;
	}
	/**
	 * @return 制单时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 制单时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 审核人编号
	 */
	public String getAuditOpNo() {
	 	return auditOpNo;
	}
	/**
	 * @设置 审核人编号
	 * @param auditOpNo
	 */
	public void setAuditOpNo(String auditOpNo) {
	 	this.auditOpNo = auditOpNo;
	}
	/**
	 * @return 审核人名称
	 */
	public String getAuditOpName() {
	 	return auditOpName;
	}
	/**
	 * @设置 审核人名称
	 * @param auditOpName
	 */
	public void setAuditOpName(String auditOpName) {
	 	this.auditOpName = auditOpName;
	}
	/**
	 * @return 审核人机构编号
	 */
	public String getAuditBrNo() {
	 	return auditBrNo;
	}
	/**
	 * @设置 审核人机构编号
	 * @param auditBrNo
	 */
	public void setAuditBrNo(String auditBrNo) {
	 	this.auditBrNo = auditBrNo;
	}
	/**
	 * @return 审核人机构名称
	 */
	public String getAuditBrName() {
	 	return auditBrName;
	}
	/**
	 * @设置 审核人机构名称
	 * @param auditBrName
	 */
	public void setAuditBrName(String auditBrName) {
	 	this.auditBrName = auditBrName;
	}
	/**
	 * @return 审核时间
	 */
	public String getAuditTime() {
	 	return auditTime;
	}
	/**
	 * @设置 审核时间
	 * @param auditTime
	 */
	public void setAuditTime(String auditTime) {
	 	this.auditTime = auditTime;
	}
	/**
	 * @return 最后修改人编号
	 */
	public String getLstModOpNo() {
	 	return lstModOpNo;
	}
	/**
	 * @设置 最后修改人编号
	 * @param lstModOpNo
	 */
	public void setLstModOpNo(String lstModOpNo) {
	 	this.lstModOpNo = lstModOpNo;
	}
	/**
	 * @return 最后修改人名称
	 */
	public String getLstModOpName() {
	 	return lstModOpName;
	}
	/**
	 * @设置 最后修改人名称
	 * @param lstModOpName
	 */
	public void setLstModOpName(String lstModOpName) {
	 	this.lstModOpName = lstModOpName;
	}
	/**
	 * @return 最后修改人机构编号
	 */
	public String getLstModBrNo() {
	 	return lstModBrNo;
	}
	/**
	 * @设置 最后修改人机构编号
	 * @param lstModBrNo
	 */
	public void setLstModBrNo(String lstModBrNo) {
	 	this.lstModBrNo = lstModBrNo;
	}
	/**
	 * @return 最后修改人机构名称
	 */
	public String getLstModBrName() {
	 	return lstModBrName;
	}
	/**
	 * @设置 最后修改人机构名称
	 * @param lstModBrName
	 */
	public void setLstModBrName(String lstModBrName) {
	 	this.lstModBrName = lstModBrName;
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