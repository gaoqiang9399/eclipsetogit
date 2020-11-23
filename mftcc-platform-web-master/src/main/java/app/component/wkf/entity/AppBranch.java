/**
 * Copyright(c)2012
 * DHC Software Co., Ltd.
 *
 * All Rights Reserved
 *
 * Revision History:
 *                       Modification        Tracking
 * Author (Email ID)     Date                Number              Description
 * Liping                2012-12-28             BugId
 *
 */
package app.component.wkf.entity;

public class AppBranch {
	private String branchId;
	private String branchName;
	private String parentId;
	private String branchType;

	public AppBranch() {
	}

	public AppBranch(String branchId, String branchName) {
		this.branchId = branchId;
		this.branchName = branchName;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}
	
	

}
