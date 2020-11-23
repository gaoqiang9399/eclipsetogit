package app.component.pss.sales.entity;
import app.base.BaseDomain;
/**
* Title: PssSaleReturnBillDetail.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Sep 14 15:32:40 CST 2017
* @version：1.0
**/
public class PssSaleReturnBillDetail extends BaseDomain {
	private String saleReturnBillDetailId;//主键
	private String saleReturnBillNo;//销货退货单号
	private Integer sequence;//序号
	private String goodsId;//商品ID
	private String unitId;//单位ID
	private String storehouseId;//仓库ID
	private Double quantity;//数量
	private Double unitPrice;//销售单价
	private Double taxUnitPrice;//含税单价
	private Double discountRate;//折扣率(%)
	private Double discountAmount;//折扣额
	private Double amount;//金额
	private Double taxRate;//税率(%)
	private Double taxAmount;//税额
	private Double totalPriceWithTax;//价税合计
	private String memo;//备注
	private String saleOrderNo;//销货订单号
	private String saleBillNo;//原销货单号
	private String saleBillDetailId;//销货单明细主键
	private String saleOrderDetailId;//销货订单明细主键
	private String baseUnitId;//基本单位ID
	private Double baseQuantity;//基本数量
	private String freightSpaceId;//仓位ID
	
	private Double returnableQuantity;//可退数量
	private String goodsModel;//商品型号
	private String goodsName;//商品名称
	private String unitName;//单位名称
	private String storehouseName;//仓库名称

	/**
	 * @return 主键
	 */
	public String getSaleReturnBillDetailId() {
	 	return saleReturnBillDetailId;
	}
	/**
	 * @设置 主键
	 * @param saleReturnBillDetailId
	 */
	public void setSaleReturnBillDetailId(String saleReturnBillDetailId) {
	 	this.saleReturnBillDetailId = saleReturnBillDetailId;
	}
	/**
	 * @return 销货退货单号
	 */
	public String getSaleReturnBillNo() {
	 	return saleReturnBillNo;
	}
	/**
	 * @设置 销货退货单号
	 * @param saleReturnBillNo
	 */
	public void setSaleReturnBillNo(String saleReturnBillNo) {
	 	this.saleReturnBillNo = saleReturnBillNo;
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
	 * @return 销售单价
	 */
	public Double getUnitPrice() {
	 	return unitPrice;
	}
	/**
	 * @设置 销售单价
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
	 * @return 原销货单号
	 */
	public String getSaleBillNo() {
		return saleBillNo;
	}
	/**
	 * @设置 原销货单号
	 * @param saleBillNo
	 */
	public void setSaleBillNo(String saleBillNo) {
		this.saleBillNo = saleBillNo;
	}
	/**
	 * @return 销货单明细主键
	 */
	public String getSaleBillDetailId() {
		return saleBillDetailId;
	}
	/**
	 * @设置 销货单明细主键
	 * @param saleBillDetailId
	 */
	public void setSaleBillDetailId(String saleBillDetailId) {
		this.saleBillDetailId = saleBillDetailId;
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
	 * @return 可退数量
	 */
	public Double getReturnableQuantity() {
	 	return returnableQuantity;
	}
	/**
	 * @设置 可退数量
	 * @param returnableQuantity
	 */
	public void setReturnableQuantity(Double returnableQuantity) {
	 	this.returnableQuantity = returnableQuantity;
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
	/**
	 * @return 仓库名称
	 */
	public String getStorehouseName() {
		return storehouseName;
	}
	/**
	 * @设置 仓库名称
	 * @param storehouseNameString
	 */
	public void setStorehouseName(String storehouseName) {
		this.storehouseName = storehouseName;
	}
}