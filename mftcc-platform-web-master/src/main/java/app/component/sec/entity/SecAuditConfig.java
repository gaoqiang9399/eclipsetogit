package app.component.sec.entity;
import app.base.BaseDomain;
/**
* Title: SecAuditConfig.java
* Description:
* @author：jzh@dhcc.com.cn
* @Mon Feb 22 07:15:15 GMT 2016
* @version：1.0
**/
public class SecAuditConfig extends BaseDomain {
	private String itemNo;//编号
	private String codeType;//规则种类
	private String itemName;//规则描述
	private String itemValues;//规则值
	private String isUse;//启用标志
	private String isEdit;//是否可编辑

	/**
	 * @return 编号
	 */
	public String getItemNo() {
	 	return itemNo;
	}
	/**
	 * @设置 编号
	 * @param itemNo
	 */
	public void setItemNo(String itemNo) {
	 	this.itemNo = itemNo;
	}
	/**
	 * @return 规则种类
	 */
	public String getCodeType() {
	 	return codeType;
	}
	/**
	 * @设置 规则种类
	 * @param codeType
	 */
	public void setCodeType(String codeType) {
	 	this.codeType = codeType;
	}
	/**
	 * @return 规则描述
	 */
	public String getItemName() {
	 	return itemName;
	}
	/**
	 * @设置 规则描述
	 * @param itemName
	 */
	public void setItemName(String itemName) {
	 	this.itemName = itemName;
	}
	/**
	 * @return 规则值
	 */
	public String getItemValues() {
	 	return itemValues;
	}
	/**
	 * @设置 规则值
	 * @param itemValues
	 */
	public void setItemValues(String itemValues) {
	 	this.itemValues = itemValues;
	}
	/**
	 * @return 启用标志
	 */
	public String getIsUse() {
	 	return isUse;
	}
	/**
	 * @设置 启用标志
	 * @param isUse
	 */
	public void setIsUse(String isUse) {
	 	this.isUse = isUse;
	}
	/**
	 * @return 是否可编辑
	 */
	public String getIsEdit() {
	 	return isEdit;
	}
	/**
	 * @设置 是否可编辑
	 * @param isEdit
	 */
	public void setIsEdit(String isEdit) {
	 	this.isEdit = isEdit;
	}
}