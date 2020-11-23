/**
 * Copyright(c)2012
 * DHC Software Co., Ltd.
 *
 * All Rights Reserved
 *
 * Revision History:
 *                       Modification        Tracking
 * Author (Email ID)     Date                Number              Description
 * Liping                2013-03-11           BugId
 *
 */
package app.component.wkf.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.dhcc.workflow.Format;

import app.base.BaseDomain;

public class WorkflowTask extends BaseDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected long dbid;
	protected int dbversion;

	protected String executionId;
	protected String parentExecutionId;
	protected String assignee;
	protected String state;
	protected String outcome;
	protected int priority;
	protected Timestamp createTime;
	protected Timestamp endTime;
	protected long duration;
	protected int nextDetailIndex = 1;

	protected String branchId;
	protected String lastOperator;
	protected String lastTaskId;
	protected String appId;
	protected String appValue;
	
	protected String activityName;
	protected String activityType;
	protected String description;
	protected String forms;
	protected String processDefinitionId;
	protected String processDefinitionKey;
	protected int isAssignNext;
	protected String result;
	protected String nextUser;
	protected String approveIdea;
	protected String signState;
	protected String proxyUser;

	public WorkflowTask(){
		
	}

	public String getId() {
		return Long.toString(dbid);
	}
	public long getDbid() {
		return dbid;
	}

	public void setDbid(long dbid) {
		this.dbid = dbid;
	}

	public int getDbversion() {
		return dbversion;
	}

	public void setDbversion(int dbversion) {
		this.dbversion = dbversion;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getParentExecutionId() {
		return parentExecutionId;
	}

	public void setParentExecutionId(String parentExecutionId) {
		this.parentExecutionId = parentExecutionId;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public int getNextDetailIndex() {
		return nextDetailIndex;
	}

	public void setNextDetailIndex(int nextDetailIndex) {
		this.nextDetailIndex = nextDetailIndex;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getLastOperator() {
		return lastOperator;
	}

	public void setLastOperator(String lastOperator) {
		this.lastOperator = lastOperator;
	}

	public String getLastTaskId() {
		return lastTaskId;
	}

	public void setLastTaskId(String lastTaskId) {
		this.lastTaskId = lastTaskId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppValue() {
		return appValue;
	}

	public void setAppValue(String appValue) {
		this.appValue = appValue;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getForms() {
		return forms;
	}

	public void setForms(String forms) {
		this.forms = forms;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public int getIsAssignNext() {
		return isAssignNext;
	}

	public void setIsAssignNext(int isAssignNext) {
		this.isAssignNext = isAssignNext;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getNextUser() {
		return nextUser;
	}

	public void setNextUser(String nextUser) {
		this.nextUser = nextUser;
	}

	public String getApproveIdea() {
		return approveIdea;
	}

	public void setApproveIdea(String approveIdea) {
		this.approveIdea = approveIdea;
	}

	public String getSignState() {
		return signState;
	}

	public void setSignState(String signState) {
		this.signState = signState;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}
	
	public String getAppValueScript() {
		return Format.formatAppValue(appValue);
	}
	
	public String getFormScript() {
		return Format.formatTaskForm(getId(), forms);
	}
}
