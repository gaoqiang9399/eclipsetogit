package app.component.report.entity;

public class MfAppEfficiencyBean {
	
	private String brNo;//部门编号
	private String brName;//部门名称
	private String appPersonNo;//审批人员编号
	private String appPersonName;//审批人员名称
	private String app_id;//申请号
	private String appStat;//审批状态
	private String ifOverTime;//是否超时
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getAppPersonNo() {
		return appPersonNo;
	}
	public void setAppPersonNo(String appPersonNo) {
		this.appPersonNo = appPersonNo;
	}
	public String getAppPersonName() {
		return appPersonName;
	}
	public void setAppPersonName(String appPersonName) {
		this.appPersonName = appPersonName;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getAppStat() {
		return appStat;
	}
	public void setAppStat(String appStat) {
		this.appStat = appStat;
	}
	public String getIfOverTime() {
		return ifOverTime;
	}
	public void setIfOverTime(String ifOverTime) {
		this.ifOverTime = ifOverTime;
	}
	
}
