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

public class AppUser {

	private String userId;
	private String userName;
	private String userPwd;
	private String branchId;
	private String branchName;
	
	private String roleIds;
	private String roleNames;

	public AppUser() {
	}
	
	public AppUser(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}
	
	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getUserId() {
		return userId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getRoleIds() {
		return roleIds;
	}

}
