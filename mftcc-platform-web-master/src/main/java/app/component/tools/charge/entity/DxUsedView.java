package app.component.tools.charge.entity;

import app.base.BaseDomain;

public class DxUsedView extends BaseDomain {
	private String itemName;//服务项名称
	private String receivePhone;//接收人
	private String receiveTxt;//短信内容
	private String consAmt;//总价
	private String sendSts;//发送状态
	private String sendDesc;//描述
	private String occDate;//发送时间
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	public String getReceiveTxt() {
		return receiveTxt;
	}
	public void setReceiveTxt(String receiveTxt) {
		this.receiveTxt = receiveTxt;
	}
	public String getConsAmt() {
		return consAmt;
	}
	public void setConsAmt(String consAmt) {
		this.consAmt = consAmt;
	}
	public String getSendSts() {
		return sendSts;
	}
	public void setSendSts(String sendSts) {
		this.sendSts = sendSts;
	}
	public String getSendDesc() {
		return sendDesc;
	}
	public void setSendDesc(String sendDesc) {
		this.sendDesc = sendDesc;
	}
	public String getOccDate() {
		return occDate;
	}
	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}
	
}
