package app.component.pms.entity;

import app.base.BaseDomain;

public class PmsEntranceRole extends BaseDomain{
	private String roleNo;//角色号
	private String pmsNo;//pms_Entrance表主键
	private String EntranceNo;//入口编号
	private String EntranceUrlDesc;//定制页面描述
	public String getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}
	public String getPmsNo() {
		return pmsNo;
	}
	public void setPmsNo(String pmsNo) {
		this.pmsNo = pmsNo;
	}
	public String getEntranceNo() {
		return EntranceNo;
	}
	public void setEntranceNo(String entranceNo) {
		EntranceNo = entranceNo;
	}
	public String getEntranceUrlDesc() {
		return EntranceUrlDesc;
	}
	public void setEntranceUrlDesc(String entranceUrlDesc) {
		EntranceUrlDesc = entranceUrlDesc;
	}
	
	
}
