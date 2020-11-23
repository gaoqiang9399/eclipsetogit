package app.component.pss.information.entity;

import app.base.BaseDomain;

public class PssPrintOtherStockInBill extends BaseDomain{
	
	private static final long serialVersionUID = -8757197605782666478L;
	
	private String supplierName;
	private String billDate;
	private String otherStockInNo;
	private String otherInType;
	private String regOpName;
	private Integer sequence;
	private String goodsName;
	private String unitName;
	private String storehouseName;
	private Double otherInQty;
	private Double inUnitCost;
	private Double inCost;
	private String memo;
	
	public String getSupplierName() {
		return supplierName;
	}
	public String getBillDate() {
		return billDate;
	}
	public String getOtherStockInNo() {
		return otherStockInNo;
	}
	public String getOtherInType() {
		return otherInType;
	}
	public String getRegOpName() {
		return regOpName;
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
	public String getStorehouseName() {
		return storehouseName;
	}
	public Double getOtherInQty() {
		return otherInQty;
	}
	public Double getInUnitCost() {
		return inUnitCost;
	}
	public Double getInCost() {
		return inCost;
	}
	public String getMemo() {
		return memo;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public void setOtherStockInNo(String otherStockInNo) {
		this.otherStockInNo = otherStockInNo;
	}
	public void setOtherInType(String otherInType) {
		this.otherInType = otherInType;
	}
	public void setRegOpName(String regOpName) {
		this.regOpName = regOpName;
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
	public void setStorehouseName(String storehouseName) {
		this.storehouseName = storehouseName;
	}
	public void setOtherInQty(Double otherInQty) {
		this.otherInQty = otherInQty;
	}
	public void setInUnitCost(Double inUnitCost) {
		this.inUnitCost = inUnitCost;
	}
	public void setInCost(Double inCost) {
		this.inCost = inCost;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
