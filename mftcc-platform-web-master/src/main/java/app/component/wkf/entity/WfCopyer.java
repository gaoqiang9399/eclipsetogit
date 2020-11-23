package app.component.wkf.entity;

public class WfCopyer {
	private Integer dbid;
	private String dbversion;
	private String groupid;//角色
	private String userid;//用户
	private String branch;//机构
	private String taskId;//
	private String execution;//流程
	
	
	public Integer getDbid() {
		return dbid;
	}
	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}
	public String getDbversion() {
		return dbversion;
	}
	public void setDbversion(String dbversion) {
		this.dbversion = dbversion;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getExecution() {
		return execution;
	}
	public void setExecution(String execution) {
		this.execution = execution;
	}
}
