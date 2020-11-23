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

public class WorkflowInstance extends BaseDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected long dbid;
	protected int dbversion;

	protected String processDefinitionId;
	protected String processDefinitionDesc;
	protected String processInstanceId;
	protected String key;
	protected String state;
	protected String endActivityName;
	protected Timestamp startTime;
	protected Timestamp endTime;
	protected Long duration;
	protected int nextDetailIndex = 1;

	protected String operator;
	protected String appId;
	protected String appValue;
	
	public WorkflowInstance(){
		
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

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionDesc() {
		return processDefinitionDesc;
	}

	public void setProcessDefinitionDesc(String processDefinitionDesc) {
		this.processDefinitionDesc = processDefinitionDesc;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEndActivityName() {
		return endActivityName;
	}

	public void setEndActivityName(String endActivityName) {
		this.endActivityName = endActivityName;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public int getNextDetailIndex() {
		return nextDetailIndex;
	}

	public void setNextDetailIndex(int nextDetailIndex) {
		this.nextDetailIndex = nextDetailIndex;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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
	
	public String getAppValueScript() {
		return Format.formatAppValue(appValue);
	}
}
