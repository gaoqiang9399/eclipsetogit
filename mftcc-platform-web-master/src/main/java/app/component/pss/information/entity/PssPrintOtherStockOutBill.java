package app.component.pss.information.entity;

import app.base.BaseDomain;

public class PssPrintOtherStockOutBill extends BaseDomain{
	
	private static final long serialVersionUID = -8757197605782666479L;
	
	private String cusName;
	private String billDate;
	private String otherStockOutNo;
	private String otherOutType;
	private String regOpName;
	private Integer sequence;
	private String goodsName;
	private String unitName;
	private String storehouseName;
	private Double otherOutQty;
	private Double outUnitCost;
	private Double outCost;
	private String memo;
	
	public String getCusName() {
		return cusName;
	}
	public String getBillDate() {
		return billDate;
	}
	public String getOtherStockOutNo() {
		return otherStockOutNo;
	}
	public String getOtherOutType() {
		return otherOutType;
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
	public Double getOtherOutQty() {
		return otherOutQty;
	}
	public Double getOutUnitCost() {
		return outUnitCost;
	}
	public Double getOutCost() {
		return outCost;
	}
	public String getMemo() {
		return memo;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public void setOtherStockOutNo(String otherStockOutNo) {
		this.otherStockOutNo = otherStockOutNo;
	}
	public void setOtherOutType(String otherOutType) {
		this.otherOutType = otherOutType;
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
	public void setOtherOutQty(Double otherOutQty) {
		this.otherOutQty = otherOutQty;
	}
	public void setOutUnitCost(Double outUnitCost) {
		this.outUnitCost = outUnitCost;
	}
	public void setOutCost(Double outCost) {
		this.outCost = outCost;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
