package app.component.pms.entity;

import app.base.BaseDomain;

public class PmsViewpointRole extends BaseDomain{
	private String roleNo;//角色号
	private String viewpointNo;//视角编号(字典)
	private String viewpointMenuNo;//菜单编号（按钮编号),主键（手动输入）
	private String urlType;//类型 1-菜单；2-按钮
	public String getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}
	public String getViewpointNo() {
		return viewpointNo;
	}
	public void setViewpointNo(String viewpointNo) {
		this.viewpointNo = viewpointNo;
	}
	public String getViewpointMenuNo() {
		return viewpointMenuNo;
	}
	public void setViewpointMenuNo(String viewpointMenuNo) {
		this.viewpointMenuNo = viewpointMenuNo;
	}
	public String getUrlType() {
		return urlType;
	}
	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}
	
	
}
