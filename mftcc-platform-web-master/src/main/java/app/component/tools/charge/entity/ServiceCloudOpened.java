package app.component.tools.charge.entity;

import app.base.BaseDomain;

public class ServiceCloudOpened extends BaseDomain {
	private String itemName;//服务名称
	private String itemNo;//服务编号
	private String priceAmt;//价格
	private String sts;//开通状况
	private String remarks;//备注
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getPriceAmt() {
		return priceAmt;
	}
	public void setPriceAmt(String priceAmt) {
		this.priceAmt = priceAmt;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
