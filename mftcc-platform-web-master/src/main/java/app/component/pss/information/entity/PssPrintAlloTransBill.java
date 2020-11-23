package app.component.pss.information.entity;

import app.base.BaseDomain;

public class PssPrintAlloTransBill extends BaseDomain{

	private static final long serialVersionUID = -8757197605782666477L;
	
	private String billDate;
	private String alloTransNo;
	private String regOpName;
	private Integer sequence;
	private String goodsName;
	private String unitName;
	private Double goodsQty;
	private String outStorehouseName;
	private String inStorehouseName; 
	private String memo;
	
	public String getBillDate() {
		return billDate;
	}
	public String getAlloTransNo() {
		return alloTransNo;
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
	public Double getGoodsQty() {
		return goodsQty;
	}
	public String getOutStorehouseName() {
		return outStorehouseName;
	}
	public String getInStorehouseName() {
		return inStorehouseName;
	}
	public String getMemo() {
		return memo;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public void setAlloTransNo(String alloTransNo) {
		this.alloTransNo = alloTransNo;
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
	public void setGoodsQty(Double goodsQty) {
		this.goodsQty = goodsQty;
	}
	public void setOutStorehouseName(String outStorehouseName) {
		this.outStorehouseName = outStorehouseName;
	}
	public void setInStorehouseName(String inStorehouseName) {
		this.inStorehouseName = inStorehouseName;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
