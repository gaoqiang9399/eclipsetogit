package app.component.pss.purchases.entity;
import app.base.BaseDomain;
/**
* Title: PssBuyOrderDetail.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Aug 10 17:41:38 CST 2017
* @version：1.0
**/
public class PssBuyOrderDetail extends BaseDomain {
	private String buyOrderDetailId;//主键
	private String buyOrderNo;//购货订单号
	private Integer sequence;//序号
	private String goodsId;//商品ID
	private String unitId;//单位ID
	private String storehouseId;//仓库ID
	private Double quantity;//数量
	private Double unitPrice;//购货单价
	private Double taxUnitPrice;//含税单价
	private Double discountRate;//折扣率(%)
	private Double discountAmount;//折扣额
	private Double amount;//金额
	private Double taxRate;//税率(%)
	private Double taxAmount;//税额
	private Double totalPriceWithTax;//价税合计
	private String memo;//备注
	private String saleOrderNo;//销货订单号
	private Double inStorageQuantity;//已入库数量
	private Double billDetailQuantity;//对应购货单明细数量(减去购货退货单明细数量)
	private String lastInStorageDate;//最后入库日期
	private String baseUnitId;//基本单位ID
	private Double baseQuantity;//基本数量
	private String freightSpaceId;//仓位ID
	
	private String businessType;//业务类别
	private String saleOrderDetailId;//销货订单明细主键
	private String orderDate;//单据日期
	private String auditStsed;//是否审核(1-已审核 0-未审核)
	private String goodsModel;//商品型号
	private String regTime;//制单时间
	private String goodsName;//商品名称

	/**
	 * @return 主键
	 */
	public String getBuyOrderDetailId() {
	 	return buyOrderDetailId;
	}
	/**
	 * @设置 主键
	 * @param buyOrderDetailId
	 */
	public void setBuyOrderDetailId(String buyOrderDetailId) {
	 	this.buyOrderDetailId = buyOrderDetailId;
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
	 * @return 购货单价
	 */
	public Double getUnitPrice() {
        return unitPrice;
	}
	/**
	 * @设置 购货单价
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
	 * @return 已入库数量
	 */
	public Double getInStorageQuantity() {
		return inStorageQuantity;
	}
	/**
	 * @设置 已入库数量
	 * @param inStorageQuantity
	 */
	public void setInStorageQuantity(Double inStorageQuantity) {
		this.inStorageQuantity = inStorageQuantity;
	}
	/**
	 * @return 对应购货单明细数量(减去购货退货单明细数量)
	 */
	public Double getBillDetailQuantity() {
		return billDetailQuantity;
	}
	/**
	 * @设置 对应购货单明细数量(减去购货退货单明细数量)
	 * @param billDetailQuantity
	 */
	public void setBillDetailQuantity(Double billDetailQuantity) {
		this.billDetailQuantity = billDetailQuantity;
	}
	/**
	 * @return 最后入库日期
	 */
	public String getLastInStorageDate() {
		return lastInStorageDate;
	}
	/**
	 * @设置 最后入库日期
	 * @param lastInStorageDate
	 */
	public void setLastInStorageDate(String lastInStorageDate) {
		this.lastInStorageDate = lastInStorageDate;
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
	 * @return 销货订单明细主键
	 */
	public String getSaleOrderDetailId() {
		return saleOrderDetailId;
	}
	/**
	 * @设置 销货订单明细主键
	 * @param saleOrderDetailId
	 */
	public void setSaleOrderDetailId(String saleOrderDetailId) {
		this.saleOrderDetailId = saleOrderDetailId;
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
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
}