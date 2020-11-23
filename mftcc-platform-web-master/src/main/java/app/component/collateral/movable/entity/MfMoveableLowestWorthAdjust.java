package app.component.collateral.movable.entity;
import app.base.BaseDomain;
/**
* Title: MfMoveableLowestWorthAdjust.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Jun 16 16:03:57 CST 2017
* @version：1.0
**/
public class MfMoveableLowestWorthAdjust extends BaseDomain {
	private String worthAdjustId;//价值调整编号
	private Double lowestSuperWorth;//现最低监管价值
	private Double newLowestSuperWorth;//新最低监管价值
	private String adjustRemark;//调整依据
	private String pledgeNo;//调整押品编号
	private String busPleId;//押品业务关联编号
	private String opNo;//登记人
	private String opName;//登记人姓名
	private String brNo;//登记部门
	private String brName;//登记部门名称
	private String regTime;//信息登记时间
	private String lstModTime;//最后修改时间
	private String pledgeName;//押品名称
	private String classSecondName;//押品类别

	/**
	 * @return 价值调整编号
	 */
	public String getWorthAdjustId() {
	 	return worthAdjustId;
	}
	/**
	 * @设置 价值调整编号
	 * @param worthAdjustId
	 */
	public void setWorthAdjustId(String worthAdjustId) {
	 	this.worthAdjustId = worthAdjustId;
	}
	/**
	 * @return 现最低监管价值
	 */
	public Double getLowestSuperWorth() {
	 	return lowestSuperWorth;
	}
	/**
	 * @设置 现最低监管价值
	 * @param lowestSuperWorth
	 */
	public void setLowestSuperWorth(Double lowestSuperWorth) {
	 	this.lowestSuperWorth = lowestSuperWorth;
	}
	/**
	 * @return 新最低监管价值
	 */
	public Double getNewLowestSuperWorth() {
	 	return newLowestSuperWorth;
	}
	/**
	 * @设置 新最低监管价值
	 * @param newLowestSuperWorth
	 */
	public void setNewLowestSuperWorth(Double newLowestSuperWorth) {
	 	this.newLowestSuperWorth = newLowestSuperWorth;
	}
	/**
	 * @return 调整依据
	 */
	public String getAdjustRemark() {
	 	return adjustRemark;
	}
	/**
	 * @设置 调整依据
	 * @param adjustRemark
	 */
	public void setAdjustRemark(String adjustRemark) {
	 	this.adjustRemark = adjustRemark;
	}
	/**
	 * @return 调整押品编号
	 */
	public String getPledgeNo() {
	 	return pledgeNo;
	}
	/**
	 * @设置 调整押品编号
	 * @param pledgeNo
	 */
	public void setPledgeNo(String pledgeNo) {
	 	this.pledgeNo = pledgeNo;
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
	public String getPledgeName() {
		return pledgeName;
	}
	public void setPledgeName(String pledgeName) {
		this.pledgeName = pledgeName;
	}
	public String getClassSecondName() {
		return classSecondName;
	}
	public void setClassSecondName(String classSecondName) {
		this.classSecondName = classSecondName;
	}
}