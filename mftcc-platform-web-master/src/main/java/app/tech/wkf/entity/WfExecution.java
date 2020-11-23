package app.tech.wkf.entity;
import app.base.BaseDomain;
/**
* Title: WfExecution.java
* Description:
* @author：@dhcc.com.cn
* @Tue Apr 12 03:47:44 GMT 2016
* @version：1.0
**/
public class WfExecution extends BaseDomain {
	private String dbid;//
	private String execution;//
	private String parentExecution;//
	private String activityname;//
	private String procdefid;//
	private String appId;//
	private String state;//
	private String assignee;
	private String activityType;
	private String create;
	private String end;
	private String result;
	private String approveIdea;
	private String appValue;
	private String description;
	
	public String getActivityname() {
		return activityname;
	}
	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}
	public String getProcdefid() {
		return procdefid;
	}
	public void setProcdefid(String procdefid) {
		this.procdefid = procdefid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getExecution() {
		return execution;
	}
	public void setExecution(String execution) {
		this.execution = execution;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getCreate() {
		return create;
	}
	public void setCreate(String create) {
		this.create = create;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getApproveIdea() {
		return approveIdea;
	}
	public void setApproveIdea(String approveIdea) {
		this.approveIdea = approveIdea;
	}
	public String getAppValue() {
		return appValue;
	}
	public void setAppValue(String appValue) {
		this.appValue = appValue;
	}
	public String getDbid() {
		return dbid;
	}
	public void setDbid(String dbid) {
		this.dbid = dbid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	public String getParentExecution() {
		return parentExecution;
	}

	public void setParentExecution(String parentExecution) {
		this.parentExecution = parentExecution;
	}
}