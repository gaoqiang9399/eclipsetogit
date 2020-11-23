package app.component.pss.sales.entity;
import app.base.BaseDomain;
/**
* Title: PssSaleOrder.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Aug 31 16:53:40 CST 2017
* @version：1.0
**/
public class PssSaleOrder extends BaseDomain {
	private String saleOrderId;//主键
	private String saleOrderNo;//销货订单号
	private String orderDate;//单据日期
	private String deliveryDate;//交货日期
	private String cusNo;//客户号
	private String salerNo;//销售人员编号
	private String businessType;//业务类别(01-销货 02-退货)
	private String auditStsed;//是否审核(1-已审核 0-未审核)
	private String billNos;//销货单号/销货退货单号(以|分隔)
	private Double amount;//销售金额
	private Double quantity;//数量
	private String orderState;//订单状态(01-未出库 02-部分出库 03-全部出库)
	private String enabledStatus;//启用状态(1-启用 0-关闭)
	private String lockStatus;//锁定状态(1-锁定 0-解锁)
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
	private String cusName;//客户名称
	private String salerName;//销售人员名称

	/**
	 * @return 主键
	 */
	public String getSaleOrderId() {
	 	return saleOrderId;
	}
	/**
	 * @设置 主键
	 * @param saleOrderId
	 */
	public void setSaleOrderId(String saleOrderId) {
	 	this.saleOrderId = saleOrderId;
	}
	/**
	 * @return 销货订单号
	 */
	public String getSaleOrderNo() {
	 	return saleOrderNo;
	}
	/**
	 * @设置 销货订单号
	 * @param saleOrderNo
	 */
	public void setSaleOrderNo(String saleOrderNo) {
	 	this.saleOrderNo = saleOrderNo;
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
	 * @return 客户号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户号
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 销售人员编号
	 */
	public String getSalerNo() {
	 	return salerNo;
	}
	/**
	 * @设置 销售人员编号
	 * @param salerNo
	 */
	public void setSalerNo(String salerNo) {
	 	this.salerNo = salerNo;
	}
	/**
	 * @return 业务类别(01-销货 02-退货)
	 */
	public String getBusinessType() {
	 	return businessType;
	}
	/**
	 * @设置 业务类别(01-销货 02-退货)
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
	 * @return 销货单号/销货退货单号(以|分隔)
	 */
	public String getBillNos() {
	 	return billNos;
	}
	/**
	 * @设置 销货单号/销货退货单号(以|分隔)
	 * @param billNos
	 */
	public void setBillNos(String billNos) {
	 	this.billNos = billNos;
	}
	/**
	 * @return 销售金额
	 */
	public Double getAmount() {
	 	return amount;
	}
	/**
	 * @设置 销售金额
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
	 * @return 订单状态(01-未出库 02-部分出库 03-全部出库)
	 */
	public String getOrderState() {
	 	return orderState;
	}
	/**
	 * @设置 订单状态(01-未出库 02-部分出库 03-全部出库)
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
	 * @return 锁定状态(1-锁定 0-解锁)
	 */
	public String getLockStatus() {
	 	return lockStatus;
	}
	/**
	 * @设置 锁定状态(1-锁定 0-解锁)
	 * @param lockStatus
	 */
	public void setLockStatus(String lockStatus) {
	 	this.lockStatus = lockStatus;
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
	/**
	 * @return 单据开始日期
	 */
	public String getOrderStartDate() {
		return orderStartDate;
	}
	/**
	 * @设置 单据开始日期
	 * @param orderStartDate
	 */
	public void setOrderStartDate(String orderStartDate) {
		this.orderStartDate = orderStartDate;
	}
	/**
	 * @return 单据结束日期
	 */
	public String getOrderEndDate() {
		return orderEndDate;
	}
	/**
	 * @设置 单据结束日期
	 * @param orderEndDate
	 */
	public void setOrderEndDate(String orderEndDate) {
		this.orderEndDate = orderEndDate;
	}
	/**
	 * @return 交货开始日期
	 */
	public String getDeliveryStartDate() {
		return deliveryStartDate;
	}
	/**
	 * @设置 交货开始日期
	 * @param deliveryStartDate
	 */
	public void setDeliveryStartDate(String deliveryStartDate) {
		this.deliveryStartDate = deliveryStartDate;
	}
	/**
	 * @return 交货结束日期
	 */
	public String getDeliveryEndDate() {
		return deliveryEndDate;
	}
	/**
	 * @设置 交货结束日期
	 * @param deliveryEndDate
	 */
	public void setDeliveryEndDate(String deliveryEndDate) {
		this.deliveryEndDate = deliveryEndDate;
	}
	/**
	 * @return 客户名称
	 */
	public String getCusName() {
		return cusName;
	}
	/**
	 * @设置 客户名称
	 * @param cusName
	 */
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	/**
	 * @return 销售人员名称
	 */
	public String getSalerName() {
		return salerName;
	}
	/**
	 * @设置 销售人员名称
	 * @param salerName
	 */
	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}
	
}