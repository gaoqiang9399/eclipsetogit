package app.component.pss.purchases.entity;
import app.base.BaseDomain;
/**
* Title: PssBuyOrder.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Aug 10 13:26:18 CST 2017
* @version：1.0
**/
public class PssBuyOrder extends BaseDomain {
	private String buyOrderId;//主键
	private String buyOrderNo;//购货订单号
	private String supplierId;//供应商ID
	private String orderDate;//单据日期
	private String deliveryDate;//交货日期
	private String businessType;//业务类别(01-购货 02-退货)
	private String auditStsed;//是否审核(1-已审核 0-未审核)
	private String billNos;//购货单号/购货退货单号(以|分隔)
	private Double amount;//购货金额
	private Double quantity;//数量
	private String orderState;//订单状态(01-未入库 02-部分入库 03-全部入库)
	private String enabledStatus;//启用状态(1-启用 0-关闭)
	private Double discountRate;//优惠率(%)
	private Double discountAmount;//优惠金额
	private Double discountAfterAmount;//优惠后金额
	private Integer printTimes;//打印次数
	private String memo;//备注
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
	
	private String orderStartDate;//单据开始日期
	private String orderEndDate;//单据结束日期
	private String deliveryStartDate;//交货开始日期
	private String deliveryEndDate;//交货结束日期
	private String supplierName;//供应商名称

	/**
	 * @return 主键
	 */
	public String getBuyOrderId() {
	 	return buyOrderId;
	}
	/**
	 * @设置 主键
	 * @param buyOrderId
	 */
	public void setBuyOrderId(String buyOrderId) {
	 	this.buyOrderId = buyOrderId;
	}
	/**
	 * @return 购货订单号
	 */
	public String getBuyOrderNo() {
	 	return buyOrderNo;
	}
	/**
	 * @设置 购货订单号
	 * @param buyOrderNo
	 */
	public void setBuyOrderNo(String buyOrderNo) {
	 	this.buyOrderNo = buyOrderNo;
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
	 * @return 单据日期
	 */
	public String getOrderDate() {
	 	return orderDate;
	}
	/**
	 * @设置 单据日期
	 * @param orderDate
	 */
	public void setOrderDate(String orderDate) {
	 	this.orderDate = orderDate;
	}
	/**
	 * @return 交货日期
	 */
	public String getDeliveryDate() {
	 	return deliveryDate;
	}
	/**
	 * @设置 交货日期
	 * @param deliveryDate
	 */
	public void setDeliveryDate(String deliveryDate) {
	 	this.deliveryDate = deliveryDate;
	}
	/**
	 * @return 业务类别(01-购货 02-退货)
	 */
	public String getBusinessType() {
	 	return businessType;
	}
	/**
	 * @设置 业务类别(01-购货 02-退货)
	 * @param businessType
	 */
	public void setBusinessType(String businessType) {
	 	this.businessType = businessType;
	}
	/**
	 * @return 是否审核(1-已审核 0-未审核)
	 */
	public String getAuditStsed() {
	 	return auditStsed;
	}
	/**
	 * @设置 是否审核(1-已审核 0-未审核)
	 * @param auditStsed
	 */
	public void setAuditStsed(String auditStsed) {
	 	this.auditStsed = auditStsed;
	}
	/**
	 * @return 购货单号/购货退货单号(以|分隔)
	 */
	public String getBillNos() {
	 	return billNos;
	}
	/**
	 * @设置 购货单号/购货退货单号(以|分隔)
	 * @param billNos
	 */
	public void setBillNos(String billNos) {
	 	this.billNos = billNos;
	}
	/**
	 * @return 购货金额
	 */
	public Double getAmount() {
	 	return amount;
	}
	/**
	 * @设置 购货金额
	 * @param amount
	 */
	public void setAmount(Double amount) {
	 	this.amount = amount;
	}
	/**
	 * @return 数量
	 */
	public Double getQuantity() {
	 	return quantity;
	}
	/**
	 * @设置 数量
	 * @param quantity
	 */
	public void setQuantity(Double quantity) {
	 	this.quantity = quantity;
	}
	/**
	 * @return 订单状态(01-未入库 02-部分入库 03-全部入库)
	 */
	public String getOrderState() {
	 	return orderState;
	}
	/**
	 * @设置 订单状态(01-未入库 02-部分入库 03-全部入库)
	 * @param orderState
	 */
	public void setOrderState(String orderState) {
	 	this.orderState = orderState;
	}
	/**
	 * @return 启用状态(1-启用 0-关闭)
	 */
	public String getEnabledStatus() {
	 	return enabledStatus;
	}
	/**
	 * @设置 启用状态(1-启用 0-关闭)
	 * @param enabledStatus
	 */
	public void setEnabledStatus(String enabledStatus) {
	 	this.enabledStatus = enabledStatus;
	}
	/**
	 * @return 优惠率(%)
	 */
	public Double getDiscountRate() {
	 	return discountRate;
	}
	/**
	 * @设置 优惠率(%)
	 * @param discountRate
	 */
	public void setDiscountRate(Double discountRate) {
	 	this.discountRate = discountRate;
	}
	/**
	 * @return 优惠金额
	 */
	public Double getDiscountAmount() {
	 	return discountAmount;
	}
	/**
	 * @设置 优惠金额
	 * @param discountAmount
	 */
	public void setDiscountAmount(Double discountAmount) {
	 	this.discountAmount = discountAmount;
	}
	/**
	 * @return 优惠后金额
	 */
	public Double getDiscountAfterAmount() {
	 	return discountAfterAmount;
	}
	/**
	 * @设置 优惠后金额
	 * @param discountAfterAmount
	 */
	public void setDiscountAfterAmount(Double discountAfterAmount) {
	 	this.discountAfterAmount = discountAfterAmount;
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
	
	public String getOrderStartDate() {
		return orderStartDate;
	}
	public void setOrderStartDate(String orderStartDate) {
		this.orderStartDate = orderStartDate;
	}
	public String getOrderEndDate() {
		return orderEndDate;
	}
	public void setOrderEndDate(String orderEndDate) {
		this.orderEndDate = orderEndDate;
	}
	public String getDeliveryStartDate() {
		return deliveryStartDate;
	}
	public void setDeliveryStartDate(String deliveryStartDate) {
		this.deliveryStartDate = deliveryStartDate;
	}
	public String getDeliveryEndDate() {
		return deliveryEndDate;
	}
	public void setDeliveryEndDate(String deliveryEndDate) {
		this.deliveryEndDate = deliveryEndDate;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
}