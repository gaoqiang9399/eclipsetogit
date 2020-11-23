package app.component.wkf.entity;

import app.base.BaseDomain;

public class WkfApprovalOpinion extends BaseDomain
{
	private String description;//任务描述
	private String assignee;//审批人
	private String result;//审批结果
	private String approveIdea;//审批意见
	private String create;//创建时间
	private String end;//结束时间
	private String duration;//耗时
	private String activityName;//任务名称
	private String execution;//流程实例编号
	private String state;//流程实例编号
	private String dbId;//
	private String appId;//业务主键
	private String activityType;//业务主键
	
	public String getDescription() {
		return description;
	}
	public String getAssignee() {
		return assignee;
	}
	public String getResult() {
		return result;
	}
	public String getApproveIdea() {
		return approveIdea;
	}
	public String getCreate() {
		return create;
	}
	public String getEnd() {
		return end;
	}
	public String getDuration() {
		return duration;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setApproveIdea(String approveIdea) {
		this.approveIdea = approveIdea;
	}
	public void setCreate(String create) {
		this.create = create;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setExecution(String execution) {
		this.execution = execution;
	}
	public String getExecution() {
		return execution;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getState() {
		return state;
	}
	public void setDbId(String dbId) {
		this.dbId = dbId;
	}
	public String getDbId() {
		return dbId;
	}
	/**
	 * @return 业务主键
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * @设置 业务主键
	 * @param appId
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
}
