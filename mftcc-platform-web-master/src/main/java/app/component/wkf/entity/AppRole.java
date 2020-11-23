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

public class AppRole {
	private String roleId;
	private String roleName;

	public AppRole() {
	}

	public AppRole(String roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}
	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
