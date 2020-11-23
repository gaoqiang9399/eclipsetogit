package app.component.pss.fund.entity;
import app.base.BaseDomain;
/**
* Title: PssOtherPayBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Nov 15 14:57:11 CST 2017
* @version：1.0
**/
public class PssOtherPayBill extends BaseDomain {
	private String otherPayId;//其他支出单据ID
	private String otherPayNo;//其他支出单据编号
	private String billDate;//单据日期
	private String supplierId;//供应商ID
	private String supplierName;//供应商名称
	private Double payAmt;//付款金额
	private String clearanceAccountId;//结算账户ID
	private String clearanceAccountName;//结算账户名称
	private Integer printTimes;//打印次数
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
	 * @return 其他支出单据ID
	 */
	public String getOtherPayId() {
	 	return otherPayId;
	}
	/**
	 * @设置 其他支出单据ID
	 * @param otherPayId
	 */
	public void setOtherPayId(String otherPayId) {
	 	this.otherPayId = otherPayId;
	}
	/**
	 * @return 其他支出单据编号
	 */
	public String getOtherPayNo() {
	 	return otherPayNo;
	}
	/**
	 * @设置 其他支出单据编号
	 * @param otherPayNo
	 */
	public void setOtherPayNo(String otherPayNo) {
	 	this.otherPayNo = otherPayNo;
	}
	/**
	 * @return 单据日期
	 */
	public String getBillDate() {
	 	return billDate;
	}
	/**
	 * @设置 单据日期
	 * @param billDate
	 */
	public void setBillDate(String billDate) {
	 	this.billDate = billDate;
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
	 * @return 付款金额
	 */
	public Double getPayAmt() {
	 	return payAmt;
	}
	/**
	 * @设置 付款金额
	 * @param payAmt
	 */
	public void setPayAmt(Double payAmt) {
	 	this.payAmt = payAmt;
	}
	/**
	 * @return 结算账户ID
	 */
	public String getClearanceAccountId() {
	 	return clearanceAccountId;
	}
	/**
	 * @设置 结算账户ID
	 * @param clearanceAccountId
	 */
	public void setClearanceAccountId(String clearanceAccountId) {
	 	this.clearanceAccountId = clearanceAccountId;
	}
	/**
	 * @return 结算账户名称
	 */
	public String getClearanceAccountName() {
	 	return clearanceAccountName;
	}
	/**
	 * @设置 结算账户名称
	 * @param clearanceAccountName
	 */
	public void setClearanceAccountName(String clearanceAccountName) {
	 	this.clearanceAccountName = clearanceAccountName;
	}
	/**
	 * @return 打印次数
	 */
	public Integer getPrintTimes() {
	 	return printTimes;
	}
	/**
	 * @设置 打印次数
	 * @param printTimes
	 */
	public void setPrintTimes(Integer printTimes) {
	 	this.printTimes = printTimes;
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