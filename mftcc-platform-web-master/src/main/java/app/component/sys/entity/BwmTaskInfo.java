package app.component.sys.entity;
import app.base.BaseDomain;
/**
* Title: BwmTaskInfo.java
* Description:
* @author：@dhcc.com.cn
* @Tue Mar 22 07:19:50 GMT 2016
* @version：1.0
**/
public class BwmTaskInfo extends BaseDomain {
	private String taskNo;
	private String taskName;
	private String taskType;
	private String layoutType;
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getLayoutType() {
		return layoutType;
	}
	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}
	
}