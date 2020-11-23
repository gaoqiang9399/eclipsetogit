package app.component.sys.entity;
import app.base.BaseDomain;
/**
* Title: SysButton.java
* Description:
* @author：lifeng@dhcc.com.cn
* @Thu Mar 14 12:47:13 GMT 2013
* @version：1.0
**/
public class SysButton extends BaseDomain {
	private String menuNo;//菜单号
	private String buttonNo;//按钮编号
	private String buttonDesc;//按钮描述

	/**
	 * @return 菜单号
	 */
	 public String getMenuNo() {
	 	return menuNo;
	 }
	 /**
	 * @设置 菜单号
	 * @param menuNo
	 */
	 public void setMenuNo(String menuNo) {
	 	this.menuNo = menuNo;
	 }
	/**
	 * @return 按钮编号
	 */
	 public String getButtonNo() {
	 	return buttonNo;
	 }
	 /**
	 * @设置 按钮编号
	 * @param buttonNo
	 */
	 public void setButtonNo(String buttonNo) {
	 	this.buttonNo = buttonNo;
	 }
	/**
	 * @return 按钮描述
	 */
	 public String getButtonDesc() {
	 	return buttonDesc;
	 }
	 /**
	 * @设置 按钮描述
	 * @param buttonDesc
	 */
	 public void setButtonDesc(String buttonDesc) {
	 	this.buttonDesc = buttonDesc;
	 }
}