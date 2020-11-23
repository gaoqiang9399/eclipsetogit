package app.component.report.entity;

public class MfAppEfficiencyRepBean {
	private String orderNum;//序号
	private String appPerson;//审批人
	private String appItemCnt;//审批项目数
	private String appItemOkCnt;//审批项目通过数
	private String appItemOkRate;//审批项目通过率
	private String appItemOvertimeCnt;//审批超时数
	private String appItemOvertimeRate;//审批超时占比
	private String brName;//所属部门
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getAppPerson() {
		return appPerson;
	}
	public void setAppPerson(String appPerson) {
		this.appPerson = appPerson;
	}
	public String getAppItemCnt() {
		return appItemCnt;
	}
	public void setAppItemCnt(String appItemCnt) {
		this.appItemCnt = appItemCnt;
	}
	public String getAppItemOkCnt() {
		return appItemOkCnt;
	}
	public void setAppItemOkCnt(String appItemOkCnt) {
		this.appItemOkCnt = appItemOkCnt;
	}
	public String getAppItemOkRate() {
		return appItemOkRate;
	}
	public void setAppItemOkRate(String appItemOkRate) {
		this.appItemOkRate = appItemOkRate;
	}
	public String getAppItemOvertimeCnt() {
		return appItemOvertimeCnt;
	}
	public void setAppItemOvertimeCnt(String appItemOvertimeCnt) {
		this.appItemOvertimeCnt = appItemOvertimeCnt;
	}
	public String getAppItemOvertimeRate() {
		return appItemOvertimeRate;
	}
	public void setAppItemOvertimeRate(String appItemOvertimeRate) {
		this.appItemOvertimeRate = appItemOvertimeRate;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	
	
	
}
