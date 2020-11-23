package app.component.pss.information.entity;

import app.base.BaseDomain;

public class PssPrintSaleReturnBill extends BaseDomain{
	private static final long serialVersionUID = -8757197605782666476L;
	
	private String cusNo;
	private String cusName;
	private String billDate;
	private String saleBillId;
	private String saleBillNo;
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
	public String getCusNo() {
		return cusNo;
	}
	public String getCusName() {
		return cusName;
	}
	public String getBillDate() {
		return billDate;
	}
	public String getSaleBillId() {
		return saleBillId;
	}
	public String getSaleBillNo() {
		return saleBillNo;
	}
	public Integer getSequence() {
		return sequence;
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
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public void setSaleBillId(String saleBillId) {
		this.saleBillId = saleBillId;
	}
	public void setSaleBillNo(String saleBillNo) {
		this.saleBillNo = saleBillNo;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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
