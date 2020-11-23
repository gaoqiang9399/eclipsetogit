package app.component.sys.entity;
import app.base.BaseDomain;
/**
* Title: SysRoleButton.java
* Description:
* @author：@dhcc.com.cn
* @Tue Aug 23 08:28:20 GMT 2016
* @version：1.0
**/
public class SysRoleButton extends BaseDomain {
	private String id;//
	private String roleNo;//
	private String funNo;//
	private String btnNo;//

	/**
	 * @return 
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 
	 */
	public String getRoleNo() {
	 	return roleNo;
	}
	/**
	 * @设置 
	 * @param roleNo
	 */
	public void setRoleNo(String roleNo) {
	 	this.roleNo = roleNo;
	}
	public String getFunNo() {
		return funNo;
	}
	public void setFunNo(String funNo) {
		this.funNo = funNo;
	}
	public String getBtnNo() {
		return btnNo;
	}
	public void setBtnNo(String btnNo) {
		this.btnNo = btnNo;
	}
}