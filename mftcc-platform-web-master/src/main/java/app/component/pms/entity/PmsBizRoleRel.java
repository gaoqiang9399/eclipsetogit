package app.component.pms.entity;

import app.base.BaseDomain;

public class PmsBizRoleRel extends BaseDomain{
	private String pmsSerno;//功能定义流水号（PK）
	private String roleNo;//角色号
	private String pmsBizNo;
	
	public String getPmsSerno() {
		return pmsSerno;
	}
	public void setPmsSerno(String pmsSerno) {
		this.pmsSerno = pmsSerno;
	}
	public String getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}
	public String getPmsBizNo() {
		return pmsBizNo;
	}
	public void setPmsBizNo(String pmsBizNo) {
		this.pmsBizNo = pmsBizNo;
	}
	
	
}
