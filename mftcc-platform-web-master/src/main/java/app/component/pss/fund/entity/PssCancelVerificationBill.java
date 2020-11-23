package app.component.pss.fund.entity;
import app.base.BaseDomain;
/**
* Title: PssCancelVerificationBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Sep 27 14:24:51 CST 2017
* @version：1.0
**/
public class PssCancelVerificationBill extends BaseDomain {
	private String cancelId;//核销单据ID
	private String cancelNo;//核销单据编号
	private String billDate;//单据日期
	private String cancelType;//业务类型
	private String cusNo;//客户ID（转出）
	private String cusName;//客户名称（转出）
	private String transCusNo;//转入客户ID
	private String transCusName;//转入客户名称
	private String supplierId;//供应商ID（转出）
	private String supplierName;//供应商名称（转出）
	private String transSupplierId;//转入供应商ID
	private String transSupplierName;//转入供应商名称
	private Double cancelAmt;//核销金额
	private String memo;//备注
	private String regOpNo;//制单人编号
	private String regOpName;//制单人名称
	private String regBrNo;//制单人机构编号
	private String regBrName;//制单人机构名称
	private String regTime;//制单时间
	private String lstModOpNo;//最后修改人编号
	private String lstModOpName;//最后修改人名称
	private String lstModBrNo;//最后修改人机构编号
	private String lstModBrName;//最后修改人机构名称
	private String lstModTime;//最后修改时间

	/**
	 * @return 核销单据ID
	 */
	public String getCancelId() {
	 	return cancelId;
	}
	/**
	 * @设置 核销单据ID
	 * @param cancelId
	 */
	public void setCancelId(String cancelId) {
	 	this.cancelId = cancelId;
	}
	/**
	 * @return 核销单据编号
	 */
	public String getCancelNo() {
	 	return cancelNo;
	}
	/**
	 * @设置 核销单据编号
	 * @param cancelNo
	 */
	public void setCancelNo(String cancelNo) {
	 	this.cancelNo = cancelNo;
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
	 * @return 业务类型
	 */
	public String getCancelType() {
	 	return cancelType;
	}
	/**
	 * @设置 业务类型
	 * @param cancelType
	 */
	public void setCancelType(String cancelType) {
	 	this.cancelType = cancelType;
	}
	/**
	 * @return 客户ID（转出）
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户ID（转出）
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 客户名称（转出）
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 客户名称（转出）
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 转入客户ID
	 */
	public String getTransCusNo() {
	 	return transCusNo;
	}
	/**
	 * @设置 转入客户ID
	 * @param transCusNo
	 */
	public void setTransCusNo(String transCusNo) {
	 	this.transCusNo = transCusNo;
	}
	/**
	 * @return 转入客户名称
	 */
	public String getTransCusName() {
	 	return transCusName;
	}
	/**
	 * @设置 转入客户名称
	 * @param transCusName
	 */
	public void setTransCusName(String transCusName) {
	 	this.transCusName = transCusName;
	}
	/**
	 * @return 供应商ID（转出）
	 */
	public String getSupplierId() {
	 	return supplierId;
	}
	/**
	 * @设置 供应商ID（转出）
	 * @param supplierId
	 */
	public void setSupplierId(String supplierId) {
	 	this.supplierId = supplierId;
	}
	/**
	 * @return 供应商名称（转出）
	 */
	public String getSupplierName() {
	 	return supplierName;
	}
	/**
	 * @设置 供应商名称（转出）
	 * @param supplierName
	 */
	public void setSupplierName(String supplierName) {
	 	this.supplierName = supplierName;
	}
	/**
	 * @return 转入供应商ID
	 */
	public String getTransSupplierId() {
	 	return transSupplierId;
	}
	/**
	 * @设置 转入供应商ID
	 * @param transSupplierId
	 */
	public void setTransSupplierId(String transSupplierId) {
	 	this.transSupplierId = transSupplierId;
	}
	/**
	 * @return 转入供应商名称
	 */
	public String getTransSupplierName() {
	 	return transSupplierName;
	}
	/**
	 * @设置 转入供应商名称
	 * @param transSupplierName
	 */
	public void setTransSupplierName(String transSupplierName) {
	 	this.transSupplierName = transSupplierName;
	}
	/**
	 * @return 核销金额
	 */
	public Double getCancelAmt() {
	 	return cancelAmt;
	}
	/**
	 * @设置 核销金额
	 * @param cancelAmt
	 */
	public void setCancelAmt(Double cancelAmt) {
	 	this.cancelAmt = cancelAmt;
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