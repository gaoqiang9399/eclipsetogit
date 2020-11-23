package app.component.cus.relation.entity;
import app.base.BaseDomain;
/**
* Title: MfCusRelationType.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Dec 03 17:34:38 CST 2016
* @version：1.0
**/
public class MfCusRelationType extends BaseDomain {
	private String id;//关系类型编号
	private String realationType;//关系类型
	private String relationTypeName;//关系类型名称
	private String relationTypeLevel;//关系等级
	private String relationTypeDesc;//关系类型描述
	private String relationTypeColor;//关系类型颜色
	private String useFlag;//启用状态
	private String isHandle;//是否手动录入 
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间

	/**
	 * @return 关系类型编号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 关系类型编号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 关系类型
	 */
	public String getRealationType() {
	 	return realationType;
	}
	/**
	 * @设置 关系类型
	 * @param realationType
	 */
	public void setRealationType(String realationType) {
	 	this.realationType = realationType;
	}
	/**
	 * @return 关系类型名称
	 */
	public String getRelationTypeName() {
	 	return relationTypeName;
	}
	/**
	 * @设置 关系类型名称
	 * @param relationTypeName
	 */
	public void setRelationTypeName(String relationTypeName) {
	 	this.relationTypeName = relationTypeName;
	}
	/**
	 * @return 关系等级
	 */
	public String getRelationTypeLevel() {
	 	return relationTypeLevel;
	}
	/**
	 * @设置 关系等级
	 * @param relationTypeLevel
	 */
	public void setRelationTypeLevel(String relationTypeLevel) {
	 	this.relationTypeLevel = relationTypeLevel;
	}
	/**
	 * @return 关系类型描述
	 */
	public String getRelationTypeDesc() {
	 	return relationTypeDesc;
	}
	/**
	 * @设置 关系类型描述
	 * @param relationTypeDesc
	 */
	public void setRelationTypeDesc(String relationTypeDesc) {
	 	this.relationTypeDesc = relationTypeDesc;
	}
	/**
	 * @return 关系类型颜色
	 */
	public String getRelationTypeColor() {
	 	return relationTypeColor;
	}
	/**
	 * @设置 关系类型颜色
	 * @param relationTypeColor
	 */
	public void setRelationTypeColor(String relationTypeColor) {
	 	this.relationTypeColor = relationTypeColor;
	}
	/**
	 * @return 启用状态
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 启用状态
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 是否手动录入 
	 */
	public String getIsHandle() {
	 	return isHandle;
	}
	/**
	 * @设置 是否手动录入 
	 * @param isHandle
	 */
	public void setIsHandle(String isHandle) {
	 	this.isHandle = isHandle;
	}
	/**
	 * @return 登记人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 登记人姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
}