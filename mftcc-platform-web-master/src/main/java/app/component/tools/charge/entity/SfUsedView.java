package app.component.tools.charge.entity;

import app.base.BaseDomain;

public class SfUsedView extends BaseDomain{
	private String itemName;//服务项名称
	private String cifName;//客户名称
	private String idNum;//身份证号
	private String amt;//金额
	private String status;//状态
	private String remarks;//备注
	private String occTime;//时间
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getCifName() {
		return cifName;
	}
	public void setCifName(String cifName) {
		this.cifName = cifName;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOccTime() {
		return occTime;
	}
	public void setOccTime(String occTime) {
		this.occTime = occTime;
	}
	
}
