package app.component.tools.charge.entity;

import app.base.BaseDomain;

public class AllCloudService extends BaseDomain {
	private String itemNo;//服务编号
	private String itemName;//服务名称
	private String itemType;//服务类型
	private String cifSts;//开通禁用(1已开通，0禁用，null未开通过)
	private String showAmt;//单价
	private String remarks;//备注
	private String merchantName;//服务提供商
	private String merchantNo;//服务提供商id
	private String operate;//操作
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getCifSts() {
		return cifSts;
	}
	public void setCifSts(String cifSts) {
		this.cifSts = cifSts;
	}
	public String getShowAmt() {
		return showAmt;
	}
	public void setShowAmt(String showAmt) {
		this.showAmt = showAmt;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	
}
