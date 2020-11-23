package app.component.pms.entity;

import app.base.BaseDomain;

/**
 * 角色跟数据权限关联实体类
 * @ClassName: pmsDataRangRole
 */
public class PmsDataRangRole  extends BaseDomain{
	
	private String roleNo;//角色编号
	
	private String funNo;//业务功能编号

	private String funUrl;//功能对应URL

	private String funRoleType;//权限类型    1.本人。2.本机构。3.本机构及其向下
	
	private String pmsField;//指定字段
	private String pmsSts;//是否启用指定字段

	public String getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getFunNo() {
		return funNo;
	}

	public void setFunNo(String funNo) {
		this.funNo = funNo;
	}

	public String getFunUrl() {
		return funUrl;
	}

	public void setFunUrl(String funUrl) {
		this.funUrl = funUrl;
	}

	public String getFunRoleType() {
		return funRoleType;
	}

	public void setFunRoleType(String funRoleType) {
		this.funRoleType = funRoleType;
	}

	public String getPmsField() {
		return pmsField;
	}

	public void setPmsField(String pmsField) {
		this.pmsField = pmsField;
	}

	public String getPmsSts() {
		return pmsSts;
	}

	public void setPmsSts(String pmsSts) {
		this.pmsSts = pmsSts;
	}
}
