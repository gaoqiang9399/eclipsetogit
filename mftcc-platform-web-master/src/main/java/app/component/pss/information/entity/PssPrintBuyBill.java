package app.component.pss.information.entity;

import app.base.BaseDomain;

public class PssPrintBuyBill extends BaseDomain{

	private static final long serialVersionUID = -8757197605782666471L;
	
	private String supplierId;
	private String supplierName;
	private String billDate;
	private String buyBillId;
	private String buyBillNo;
	private Integer sequence;
	private String goodsName;
	private String unitName;
	private Double quantity;
	private Double unitPrice;
	private Double discountRate;
	private Double discountAmount;
	private Double amount;
	private Double taxRate;
	private Double taxAmount;
	private Double totalPriceWithTax;
	private String storeHouseName;
	
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getBillDate() {
		return billDate;
	}
	public String getBuyBillId() {
		return buyBillId;
	}
	public String getBuyBillNo() {
		return buyBillNo;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public String getUnitName() {
		return unitName;
	}
	public Double getQuantity() {
		return quantity;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public Double getDiscountRate() {
		return discountRate;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public Double getAmount() {
		return amount;
	}
	public Double getTaxRate() {
		return taxRate;
	}
	public Double getTaxAmount() {
		return taxAmount;
	}
	public Double getTotalPriceWithTax() {
		return totalPriceWithTax;
	}
	public String getStoreHouseName() {
		return storeHouseName;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public void setBuyBillId(String buyBillId) {
		this.buyBillId = buyBillId;
	}
	public void setBuyBillNo(String buyBillNo) {
		this.buyBillNo = buyBillNo;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public void setTotalPriceWithTax(Double totalPriceWithTax) {
		this.totalPriceWithTax = totalPriceWithTax;
	}
	public void setStoreHouseName(String storeHouseName) {
		this.storeHouseName = storeHouseName;
	}
	
	
}
