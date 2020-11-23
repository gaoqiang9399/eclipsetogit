package app.component.collateral.movable.entity;
import app.base.BaseDomain;
/**
* Title: MfMoveableCheckInventoryInfo.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Jun 08 15:08:07 CST 2017
* @version：1.0
**/
public class MfMoveableCheckInventoryInfo extends BaseDomain {
	private String checkInventoryId;//核库编号
	private String kindIsStandard;//押品种类是否在规定范围内
	private String ownershipIsChange;//押品权属是否变更
	private String worthIsUnderMin;//押品价值是否低于监管下限
	private String isDevalue;//押品是否贬值
	private String isDamage;//评级模型名称
	private String checkOpNo;//核库人
	private String checkOpName;//核库人名称
	private String busPleId;//押品业务关联编号
	private String checkDate;//核库时间
	private String opNo;//登记人
	private String opName;//登记人姓名
	private String brNo;//登记部门
	private String brName;//登记部门名称
	private String regTime;//信息登记时间
	private String lstModTime;//最后修改时间
	private String pledgeNo;//押品编号
	private String classId;//押品编号

	/**
	 * @return 核库编号
	 */
	public String getCheckInventoryId() {
	 	return checkInventoryId;
	}
	/**
	 * @设置 核库编号
	 * @param checkInventoryId
	 */
	public void setCheckInventoryId(String checkInventoryId) {
	 	this.checkInventoryId = checkInventoryId;
	}
	/**
	 * @return 押品种类是否在规定范围内
	 */
	public String getKindIsStandard() {
	 	return kindIsStandard;
	}
	/**
	 * @设置 押品种类是否在规定范围内
	 * @param kindIsStandard
	 */
	public void setKindIsStandard(String kindIsStandard) {
	 	this.kindIsStandard = kindIsStandard;
	}
	/**
	 * @return 押品权属是否变更
	 */
	public String getOwnershipIsChange() {
	 	return ownershipIsChange;
	}
	/**
	 * @设置 押品权属是否变更
	 * @param ownershipIsChange
	 */
	public void setOwnershipIsChange(String ownershipIsChange) {
	 	this.ownershipIsChange = ownershipIsChange;
	}
	/**
	 * @return 押品价值是否低于监管下限
	 */
	public String getWorthIsUnderMin() {
	 	return worthIsUnderMin;
	}
	/**
	 * @设置 押品价值是否低于监管下限
	 * @param worthIsUnderMin
	 */
	public void setWorthIsUnderMin(String worthIsUnderMin) {
	 	this.worthIsUnderMin = worthIsUnderMin;
	}
	/**
	 * @return 押品是否贬值
	 */
	public String getIsDevalue() {
	 	return isDevalue;
	}
	/**
	 * @设置 押品是否贬值
	 * @param isDevalue
	 */
	public void setIsDevalue(String isDevalue) {
	 	this.isDevalue = isDevalue;
	}
	/**
	 * @return 评级模型名称
	 */
	public String getIsDamage() {
	 	return isDamage;
	}
	/**
	 * @设置 评级模型名称
	 * @param isDamage
	 */
	public void setIsDamage(String isDamage) {
	 	this.isDamage = isDamage;
	}
	/**
	 * @return 核库人
	 */
	public String getCheckOpNo() {
	 	return checkOpNo;
	}
	/**
	 * @设置 核库人
	 * @param checkOpNo
	 */
	public void setCheckOpNo(String checkOpNo) {
	 	this.checkOpNo = checkOpNo;
	}
	/**
	 * @return 核库人名称
	 */
	public String getCheckOpName() {
	 	return checkOpName;
	}
	/**
	 * @设置 核库人名称
	 * @param checkOpName
	 */
	public void setCheckOpName(String checkOpName) {
	 	this.checkOpName = checkOpName;
	}
	/**
	 * @return 押品业务关联编号
	 */
	public String getBusPleId() {
	 	return busPleId;
	}
	/**
	 * @设置 押品业务关联编号
	 * @param busPleId
	 */
	public void setBusPleId(String busPleId) {
	 	this.busPleId = busPleId;
	}
	/**
	 * @return 核库时间
	 */
	public String getCheckDate() {
	 	return checkDate;
	}
	/**
	 * @设置 核库时间
	 * @param checkDate
	 */
	public void setCheckDate(String checkDate) {
	 	this.checkDate = checkDate;
	}
	/**
	 * @return 登记人
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人
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
	 * @return 登记部门
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门
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
	 * @return 信息登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 信息登记时间
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
	public String getPledgeNo() {
		return pledgeNo;
	}
	public void setPledgeNo(String pledgeNo) {
		this.pledgeNo = pledgeNo;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
}