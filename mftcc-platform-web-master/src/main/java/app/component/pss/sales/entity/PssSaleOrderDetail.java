package app.component.pss.sales.entity;
import app.base.BaseDomain;
/**
* Title: PssSaleOrderDetail.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Aug 31 16:55:14 CST 2017
* @version：1.0
**/
public class PssSaleOrderDetail extends BaseDomain {
	private String saleOrderDetailId;//主键
	private String saleOrderNo;//销货订单号
	private Integer sequence;//序号
	private String goodsId;//商品ID
	private String unitId;//单位ID
	private String storehouseId;//仓库ID
	private Double quantity;//数量
	private Double unitPrice;//销货单价
	private Double taxUnitPrice;//含税单价
	private Double discountRate;//折扣率(%)
	private Double discountAmount;//折扣额
	private Double amount;//金额
	private Double taxRate;//税率(%)
	private Double taxAmount;//税额
	private Double totalPriceWithTax;//价税合计
	private String signWholePack;//签订整件散包
	private String deliveryWholePack;//出库整件散包
	private String undeliveryWholePack;//未出库整件散包
	private String memo;//备注
	private Double outStorageQuantity;//已出库数量
	private Double billDetailQuantity;//对应销货单明细数量(减去销货退货单明细数量)
	private String lastOutStorageDate;//最后出库日期
	private String baseUnitId;//基本单位ID
	private Double baseQuantity;//基本数量
	private String freightSpaceId;//仓位ID
	
	private String saleOrderId;//销货订单主键
	private String businessType;//业务类别
	private String orderDate;//单据日期
	private String deliveryDate;//交货日期
	private String goodsName;//商品名称
	private String deliveryStartDate;//交货开始日期
	private String deliveryEndDate;//交货结束日期
	private String saleOrderScope;//销售订单范围(01-未采购完 02-已采购完)
	private String auditStsed;//是否审核(1-已审核 0-未审核)
	private String enabledStatus;//启用状态(1-启用 0-关闭)
	
	private String buyOrderOrSaleBillDates;//购货/销货日期
	private String buyOrderNos;//购货订单号
	private String toBuyOrderQuantitys;//以销定购数量
	private String inStorageQuantitys;//实际入库数量
	private String saleBillNos;//销货单号
	private String outStorageQuantitys;//销货出库数量
	private String notOutStorageQuantity;//未出库数量
	private String willBuyQuantity;//待采购数量
	private Double thisBuyQuantity;//本次采购数量
	private Double thisBuyUnitPrice;//本次采购价格
	private String goodsModel;//商品型号
	private String unitName;//单位名称

	/**
	 * @return 主键
	 */
	public String getSaleOrderDetailId() {
	 	return saleOrderDetailId;
	}
	/**
	 * @设置 主键
	 * @param saleOrderDetailId
	 */
	public void setSaleOrderDetailId(String saleOrderDetailId) {
	 	this.saleOrderDetailId = saleOrderDetailId;
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
	 * @return 商品ID
	 */
	public String getGoodsId() {
	 	return goodsId;
	}
	/**
	 * @设置 商品ID
	 * @param goodsId
	 */
	public void setGoodsId(String goodsId) {
	 	this.goodsId = goodsId;
	}
	/**
	 * @return 单位ID
	 */
	public String getUnitId() {
	 	return unitId;
	}
	/**
	 * @设置 单位ID
	 * @param unitId
	 */
	public void setUnitId(String unitId) {
	 	this.unitId = unitId;
	}
	/**
	 * @return 仓库ID
	 */
	public String getStorehouseId() {
	 	return storehouseId;
	}
	/**
	 * @设置 仓库ID
	 * @param storehouseId
	 */
	public void setStorehouseId(String storehouseId) {
	 	this.storehouseId = storehouseId;
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
	 * @return 销货单价
	 */
	public Double getUnitPrice() {
	 	return unitPrice;
	}
	/**
	 * @设置 销货单价
	 * @param unitPrice
	 */
	public void setUnitPrice(Double unitPrice) {
	 	this.unitPrice = unitPrice;
	}
	/**
	 * @return 含税单价
	 */
	public Double getTaxUnitPrice() {
	 	return taxUnitPrice;
	}
	/**
	 * @设置 含税单价
	 * @param taxUnitPrice
	 */
	public void setTaxUnitPrice(Double taxUnitPrice) {
	 	this.taxUnitPrice = taxUnitPrice;
	}
	/**
	 * @return 折扣率(%)
	 */
	public Double getDiscountRate() {
	 	return discountRate;
	}
	/**
	 * @设置 折扣率(%)
	 * @param discountRate
	 */
	public void setDiscountRate(Double discountRate) {
	 	this.discountRate = discountRate;
	}
	/**
	 * @return 折扣额
	 */
	public Double getDiscountAmount() {
	 	return discountAmount;
	}
	/**
	 * @设置 折扣额
	 * @param discountAmount
	 */
	public void setDiscountAmount(Double discountAmount) {
	 	this.discountAmount = discountAmount;
	}
	/**
	 * @return 金额
	 */
	public Double getAmount() {
	 	return amount;
	}
	/**
	 * @设置 金额
	 * @param amount
	 */
	public void setAmount(Double amount) {
	 	this.amount = amount;
	}
	/**
	 * @return 税率(%)
	 */
	public Double getTaxRate() {
	 	return taxRate;
	}
	/**
	 * @设置 税率(%)
	 * @param taxRate
	 */
	public void setTaxRate(Double taxRate) {
	 	this.taxRate = taxRate;
	}
	/**
	 * @return 税额
	 */
	public Double getTaxAmount() {
	 	return taxAmount;
	}
	/**
	 * @设置 税额
	 * @param taxAmount
	 */
	public void setTaxAmount(Double taxAmount) {
	 	this.taxAmount = taxAmount;
	}
	/**
	 * @return 价税合计
	 */
	public Double getTotalPriceWithTax() {
	 	return totalPriceWithTax;
	}
	/**
	 * @设置 价税合计
	 * @param totalPriceWithTax
	 */
	public void setTotalPriceWithTax(Double totalPriceWithTax) {
	 	this.totalPriceWithTax = totalPriceWithTax;
	}
	/**
	 * @return 签订整件散包
	 */
	public String getSignWholePack() {
	 	return signWholePack;
	}
	/**
	 * @设置 签订整件散包
	 * @param signWholePack
	 */
	public void setSignWholePack(String signWholePack) {
	 	this.signWholePack = signWholePack;
	}
	/**
	 * @return 出库整件散包
	 */
	public String getDeliveryWholePack() {
	 	return deliveryWholePack;
	}
	/**
	 * @设置 出库整件散包
	 * @param deliveryWholePack
	 */
	public void setDeliveryWholePack(String deliveryWholePack) {
	 	this.deliveryWholePack = deliveryWholePack;
	}
	/**
	 * @return 未出库整件散包
	 */
	public String getUndeliveryWholePack() {
	 	return undeliveryWholePack;
	}
	/**
	 * @设置 未出库整件散包
	 * @param undeliveryWholePack
	 */
	public void setUndeliveryWholePack(String undeliveryWholePack) {
	 	this.undeliveryWholePack = undeliveryWholePack;
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
	 * @return 已出库数量
	 */
	public Double getOutStorageQuantity() {
		return outStorageQuantity;
	}
	/**
	 * @设置 已出库数量
	 * @param outStorageQuantity
	 */
	public void setOutStorageQuantity(Double outStorageQuantity) {
		this.outStorageQuantity = outStorageQuantity;
	}
	/**
	 * @return 对应销货单明细数量(减去销货退货单明细数量)
	 */
	public Double getBillDetailQuantity() {
		return billDetailQuantity;
	}
	/**
	 * @设置 对应销货单明细数量(减去销货退货单明细数量)
	 * @param billDetailQuantity
	 */
	public void setBillDetailQuantity(Double billDetailQuantity) {
		this.billDetailQuantity = billDetailQuantity;
	}
	/**
	 * @return 最后出库日期
	 */
	public String getLastOutStorageDate() {
		return lastOutStorageDate;
	}
	/**
	 * @设置 最后出库日期
	 * @param lastOutStorageDate
	 */
	public void setLastOutStorageDate(String lastOutStorageDate) {
		this.lastOutStorageDate = lastOutStorageDate;
	}
	/**
	 * @return 基本单位ID
	 */
	public String getBaseUnitId() {
	 	return baseUnitId;
	}
	/**
	 * @设置 基本单位ID
	 * @param baseUnitId
	 */
	public void setBaseUnitId(String baseUnitId) {
	 	this.baseUnitId = baseUnitId;
	}
	/**
	 * @return 基本数量
	 */
	public Double getBaseQuantity() {
        return baseQuantity;
	}
	/**
	 * @设置 基本数量
	 * @param baseQuantity
	 */
	public void setBaseQuantity(Double baseQuantity) {
	 	this.baseQuantity = baseQuantity;
	}
	/**
	 * @return 仓位ID
	 */
	public String getFreightSpaceId() {
		return freightSpaceId;
	}
	/**
	 * @设置 仓位ID
	 * @param freightSpaceId
	 */
	public void setFreightSpaceId(String freightSpaceId) {
		this.freightSpaceId = freightSpaceId;
	}
	/**
	 * @return 销货订单主键
	 */
	public String getSaleOrderId() {
		return saleOrderId;
	}
	/**
	 * @设置 销货订单主键
	 * @param saleOrderId
	 */
	public void setSaleOrderId(String saleOrderId) {
		this.saleOrderId = saleOrderId;
	}
	/**
	 * @return 业务类别
	 */
	public String getBusinessType() {
		return businessType;
	}
	/**
	 * @设置 业务类别
	 * @param businessType
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
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
	 * @return 商品名称
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * @设置 商品名称
	 * @param goodsName
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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
	 * @return 销售订单范围(01-未采购完 02-已采购完)
	 */
	public String getSaleOrderScope() {
		return saleOrderScope;
	}
	/**
	 * @设置 销售订单范围(01-未采购完 02-已采购完)
	 * @param saleOrderScope
	 */
	public void setSaleOrderScope(String saleOrderScope) {
		this.saleOrderScope = saleOrderScope;
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
	 * @return 购货/销货日期
	 */
	public String getBuyOrderOrSaleBillDates() {
		return buyOrderOrSaleBillDates;
	}
	/**
	 * @设置购货/销货日期
	 * @param buyOrderOrSaleBillDates
	 */
	public void setBuyOrderOrSaleBillDates(String buyOrderOrSaleBillDates) {
		this.buyOrderOrSaleBillDates = buyOrderOrSaleBillDates;
	}
	/**
	 * @return 购货订单号
	 */
	public String getBuyOrderNos() {
		return buyOrderNos;
	}
	/**
	 * @购货订单号
	 * @param buyOrderNos
	 */
	public void setBuyOrderNos(String buyOrderNos) {
		this.buyOrderNos = buyOrderNos;
	}
	/**
	 * @return 以销定购数量
	 */
	public String getToBuyOrderQuantitys() {
		return toBuyOrderQuantitys;
	}
	/**
	 * @以销定购数量
	 * @param toBuyOrderQuantitys
	 */
	public void setToBuyOrderQuantitys(String toBuyOrderQuantitys) {
		this.toBuyOrderQuantitys = toBuyOrderQuantitys;
	}
	/**
	 * @return 实际入库数量
	 */
	public String getInStorageQuantitys() {
		return inStorageQuantitys;
	}
	/**
	 * @实际入库数量
	 * @param inStorageQuantitys
	 */
	public void setInStorageQuantitys(String inStorageQuantitys) {
		this.inStorageQuantitys = inStorageQuantitys;
	}
	/**
	 * @return 销货单号
	 */
	public String getSaleBillNos() {
		return saleBillNos;
	}
	/**
	 * @销货单号
	 * @param saleBillNos
	 */
	public void setSaleBillNos(String saleBillNos) {
		this.saleBillNos = saleBillNos;
	}
	/**
	 * @return 销货出库数量
	 */
	public String getOutStorageQuantitys() {
		return outStorageQuantitys;
	}
	/**
	 * @销货出库数量
	 * @param outStorageQuantitys
	 */
	public void setOutStorageQuantitys(String outStorageQuantitys) {
		this.outStorageQuantitys = outStorageQuantitys;
	}
	/**
	 * @return 未出库数量
	 */
	public String getNotOutStorageQuantity() {
		return notOutStorageQuantity;
	}
	/**
	 * @未出库数量
	 * @param notOutStorageQuantity
	 */
	public void setNotOutStorageQuantity(String notOutStorageQuantity) {
		this.notOutStorageQuantity = notOutStorageQuantity;
	}
	/**
	 * @return 待采购数量
	 */
	public String getWillBuyQuantity() {
		return willBuyQuantity;
	}
	/**
	 * @待采购数量
	 * @param willBuyQuantity
	 */
	public void setWillBuyQuantity(String willBuyQuantity) {
		this.willBuyQuantity = willBuyQuantity;
	}
	/**
	 * @return 本次采购数量
	 */
	public Double getThisBuyQuantity() {
		return thisBuyQuantity;
	}
	/**
	 * @本次采购数量
	 * @param thisBuyQuantity
	 */
	public void setThisBuyQuantity(Double thisBuyQuantity) {
		this.thisBuyQuantity = thisBuyQuantity;
	}
	/**
	 * @return 本次采购价格
	 */
	public Double getThisBuyUnitPrice() {
		return thisBuyUnitPrice;
	}
	/**
	 * @本次采购价格
	 * @param thisBuyUnitPrice
	 */
	public void setThisBuyUnitPrice(Double thisBuyUnitPrice) {
		this.thisBuyUnitPrice = thisBuyUnitPrice;
	}
	/**
	 * @return 商品型号
	 */
	public String getGoodsModel() {
		return goodsModel;
	}
	/**
	 * @设置 商品型号
	 * @param auditStsed
	 */
	public void setGoodsModel(String goodsModel) {
		this.goodsModel = goodsModel;
	}
	/**
	 * @return 单位名称
	 */
	public String getUnitName() {
		return unitName;
	}
	/**
	 * @设置 单位名称
	 * @param unitName
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
}