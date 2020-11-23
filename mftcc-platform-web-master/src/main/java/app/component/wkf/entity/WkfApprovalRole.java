package app.component.wkf.entity;
import app.base.BaseDomain;
/**
* Title: WkfApprovalRole.java
* Description:
* @authorzhanglei@dhcc.com.cn
* @Thu Feb 21 14:01:33 CST 2013
* @version1.0
**/
public class WkfApprovalRole extends BaseDomain 
{
	private String wkfRoleNo;//审批角色编号
	private String wkfRoleName;//审批角色名称
	private String wkfRoleBrNo;//审批角色配置的审批部门编号
	private String wkfRoleBrName;//审批角色配置的审批部门名称

	/**
	 * @return 审批角色编号
	 */
	 public String getWkfRoleNo() {
	 	return wkfRoleNo;
	 }
	 /**
	 * @ 审批角色编号
	 * @param wkfRoleNo
	 */
	 public void setWkfRoleNo(String wkfRoleNo) {
	 	this.wkfRoleNo = wkfRoleNo;
	 }
	/**
	 * @return 审批角色名称
	 */
	 public String getWkfRoleName() {
	 	return wkfRoleName;
	 }
	 /**
	 * @审批角色名称
	 * @param wkfRoleName
	 */
	 public void setWkfRoleName(String wkfRoleName) {
	 	this.wkfRoleName = wkfRoleName;
	 }

	public String getWkfRoleBrNo() {
		return wkfRoleBrNo;
	}


	public void setWkfRoleBrNo(String wkfRoleBrNo) {
		this.wkfRoleBrNo = wkfRoleBrNo;
	}

	public String getWkfRoleBrName() {
		return wkfRoleBrName;
	}

	public void setWkfRoleBrName(String wkfRoleBrName) {
		this.wkfRoleBrName = wkfRoleBrName;
	}
}