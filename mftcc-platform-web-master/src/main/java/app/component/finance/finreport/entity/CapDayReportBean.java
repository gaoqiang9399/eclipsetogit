package app.component.finance.finreport.entity;

import app.base.BaseDomain;

public class CapDayReportBean extends BaseDomain {
	private String busType;
	private String otherCom;
	private String dAmt;
	private String mAmt;
	private String yAmt;
	private String remark;
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getOtherCom() {
		return otherCom;
	}
	public void setOtherCom(String otherCom) {
		this.otherCom = otherCom;
	}
	public String getdAmt() {
		return dAmt;
	}
	public void setdAmt(String dAmt) {
		this.dAmt = dAmt;
	}
	public String getmAmt() {
		return mAmt;
	}
	public void setmAmt(String mAmt) {
		this.mAmt = mAmt;
	}
	public String getyAmt() {
		return yAmt;
	}
	public void setyAmt(String yAmt) {
		this.yAmt = yAmt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
