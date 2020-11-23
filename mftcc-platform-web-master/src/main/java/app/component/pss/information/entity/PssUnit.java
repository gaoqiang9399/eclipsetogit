package app.component.pss.information.entity;
import app.base.BaseDomain;
/**
* Title: PssUnit.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Dec 04 10:18:34 CST 2017
* @version：1.0
**/
public class PssUnit extends BaseDomain {
	private String unitId;//单位ID
	private String unitName;//单位名称
	private String unitType;//单位类型
	private String isBase;//是否基本单位
	private String relId;//关联ID
	private String groupName;//多单位组名称
	private String relNum;//与基本单位关系
	private String invalidFlag;//启用标志

	/**
	 * @return 单位ID
	 */
	public String getUnitId() {
	 	return unitId;
	}
	/**
	 * @设置 单位ID
	 * @param unitId
	 */
	public void setUnitId(String unitId) {
	 	this.unitId = unitId;
	}
	/**
	 * @return 单位名称
	 */
	public String getUnitName() {
	 	return unitName;
	}
	/**
	 * @设置 单位名称
	 * @param unitName
	 */
	public void setUnitName(String unitName) {
	 	this.unitName = unitName;
	}
	/**
	 * @return 单位类型
	 */
	public String getUnitType() {
	 	return unitType;
	}
	/**
	 * @设置 单位类型
	 * @param unitType
	 */
	public void setUnitType(String unitType) {
	 	this.unitType = unitType;
	}
	/**
	 * @return 是否基本单位
	 */
	public String getIsBase() {
	 	return isBase;
	}
	/**
	 * @设置 是否基本单位
	 * @param isBase
	 */
	public void setIsBase(String isBase) {
	 	this.isBase = isBase;
	}
	/**
	 * @return 关联ID
	 */
	public String getRelId() {
	 	return relId;
	}
	/**
	 * @设置 关联ID
	 * @param relId
	 */
	public void setRelId(String relId) {
	 	this.relId = relId;
	}
	/**
	 * @return 多单位组名称
	 */
	public String getGroupName() {
	 	return groupName;
	}
	/**
	 * @设置 多单位组名称
	 * @param groupName
	 */
	public void setGroupName(String groupName) {
	 	this.groupName = groupName;
	}
	/**
	 * @return 与基本单位关系
	 */
	public String getRelNum() {
	 	return relNum;
	}
	/**
	 * @设置 与基本单位关系
	 * @param relNum
	 */
	public void setRelNum(String relNum) {
	 	this.relNum = relNum;
	}
	/**
	 * @return 启用标志
	 */
	public String getInvalidFlag() {
	 	return invalidFlag;
	}
	/**
	 * @设置 启用标志
	 * @param invalidFlag
	 */
	public void setInvalidFlag(String invalidFlag) {
	 	this.invalidFlag = invalidFlag;
	}
}