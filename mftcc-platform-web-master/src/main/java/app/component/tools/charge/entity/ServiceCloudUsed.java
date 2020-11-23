package app.component.tools.charge.entity;

import app.base.BaseDomain;

public class ServiceCloudUsed extends BaseDomain {
	private String itemName;//服务名称
	private String itemNo;//服务编号
	private String price;//单价
	private String useSum;//使用数量
	private String type;//服务类型
	private String totalAmt;//使用总额
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUseSum() {
		return useSum;
	}
	public void setUseSum(String useSum) {
		this.useSum = useSum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

}
