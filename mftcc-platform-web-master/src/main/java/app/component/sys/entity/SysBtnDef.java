package app.component.sys.entity;
import app.base.BaseDomain;
/**
* Title: SysBtnDef.java
* Description:
* @author：@dhcc.com.cn
* @Mon Aug 22 07:31:44 GMT 2016
* @version：1.0
**/
public class SysBtnDef extends BaseDomain {
	private String id;//编号
	private String btnNo;//按钮编号
	private String btnName;//按钮名称
	private String btnDesc;//按钮描述
	private String componentName;//组件名称
	private String componentDesc;//组件描述
	private String funNo;//功能编号
	private String funName;//功能名称
	private String lv;//级别
	private String upId;//上级编号
	private String roleNo;//角色号
	private String checked;//选中

	/**
	 * @return 编号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 编号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 按钮编号
	 */
	public String getBtnNo() {
	 	return btnNo;
	}
	/**
	 * @设置 按钮编号
	 * @param btnNo
	 */
	public void setBtnNo(String btnNo) {
	 	this.btnNo = btnNo;
	}
	/**
	 * @return 按钮名称
	 */
	public String getBtnName() {
	 	return btnName;
	}
	/**
	 * @设置 按钮名称
	 * @param btnName
	 */
	public void setBtnName(String btnName) {
	 	this.btnName = btnName;
	}
	/**
	 * @return 按钮描述
	 */
	public String getBtnDesc() {
	 	return btnDesc;
	}
	/**
	 * @设置 按钮描述
	 * @param btnDesc
	 */
	public void setBtnDesc(String btnDesc) {
	 	this.btnDesc = btnDesc;
	}
	/**
	 * @return 组件名称
	 */
	public String getComponentName() {
	 	return componentName;
	}
	/**
	 * @设置 组件名称
	 * @param componentName
	 */
	public void setComponentName(String componentName) {
	 	this.componentName = componentName;
	}
	/**
	 * @return 组件描述
	 */
	public String getComponentDesc() {
	 	return componentDesc;
	}
	/**
	 * @设置 组件描述
	 * @param componentDesc
	 */
	public void setComponentDesc(String componentDesc) {
	 	this.componentDesc = componentDesc;
	}
	/**
	 * @return 功能编号
	 */
	public String getFunNo() {
	 	return funNo;
	}
	/**
	 * @设置 功能编号
	 * @param funNo
	 */
	public void setFunNo(String funNo) {
	 	this.funNo = funNo;
	}
	/**
	 * @return 功能名称
	 */
	public String getFunName() {
	 	return funName;
	}
	/**
	 * @设置 功能名称
	 * @param funName
	 */
	public void setFunName(String funName) {
	 	this.funName = funName;
	}
	/**
	 * @return 级别
	 */
	public String getLv() {
	 	return lv;
	}
	/**
	 * @设置 级别
	 * @param lv
	 */
	public void setLv(String lv) {
	 	this.lv = lv;
	}
	/**
	 * @return 上级编号
	 */
	public String getUpId() {
	 	return upId;
	}
	/**
	 * @设置 上级编号
	 * @param upId
	 */
	public void setUpId(String upId) {
	 	this.upId = upId;
	}
	public String getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
}