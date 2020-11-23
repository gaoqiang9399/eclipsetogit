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

import app.base.BaseDomain;

public class WorkflowActivity extends BaseDomain implements Serializable {

	 private static final long serialVersionUID = 1L;

	  protected long dbid;
	  protected int dbversion;

	  protected String executionId;
	  protected String type;
	  protected String activityName;
	  protected String activityDesc;

	  protected Timestamp startTime;
	  protected Timestamp endTime;
	  protected long duration;
	  
	  protected String transitionName;

	  protected int nextDetailIndex = 1;
	  
	  public WorkflowActivity(){
		  
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
	
		public String getType() {
			return type;
		}
	
		public void setType(String type) {
			this.type = type;
		}
	
		public String getActivityName() {
			return activityName;
		}
	
		public void setActivityName(String activityName) {
			this.activityName = activityName;
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

		public long getDuration() {
			return duration;
		}
	
		public void setDuration(long duration) {
			this.duration = duration;
		}
	
		public String getTransitionName() {
			return transitionName;
		}
	
		public void setTransitionName(String transitionName) {
			this.transitionName = transitionName;
		}
	
		public int getNextDetailIndex() {
			return nextDetailIndex;
		}
	
		public void setNextDetailIndex(int nextDetailIndex) {
			this.nextDetailIndex = nextDetailIndex;
		}

		public String getActivityDesc() {
			return activityDesc;
		}

		public void setActivityDesc(String activityDesc) {
			this.activityDesc = activityDesc;
		}
	  
}
