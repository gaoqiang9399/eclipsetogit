package app.component.collateral.entity;
import app.base.BaseDomain;
/**
* Title: CollateralWarningPlan.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Mar 31 16:47:32 CST 2017
* @version：1.0
**/
public class CollateralWarningPlan extends BaseDomain {
	private String warnNo;//方案编号
	private String warnName;//方案名称
	private String warnDesc;//方案描述
	private String isStart;//是否启用
	private String startDate;//建立日期
	private String updDate;//更新日期
	private String regCusId;//登记人编号
	private String regCusName;//登记人
	private String regOrgId;//登记人机构编号
	private String regOrgName;//登记人机构
	private String updateCusId;//更新人编号
	private String updateCusName;//更新人姓名
	private String updateOrgId;//更新人机构编号
	private String updateOrgName;//更新人机构

	/**
	 * @return 方案编号
	 */
	public String getWarnNo() {
	 	return warnNo;
	}
	/**
	 * @设置 方案编号
	 * @param warnNo
	 */
	public void setWarnNo(String warnNo) {
	 	this.warnNo = warnNo;
	}
	/**
	 * @return 方案名称
	 */
	public String getWarnName() {
	 	return warnName;
	}
	/**
	 * @设置 方案名称
	 * @param warnName
	 */
	public void setWarnName(String warnName) {
	 	this.warnName = warnName;
	}
	/**
	 * @return 方案描述
	 */
	public String getWarnDesc() {
	 	return warnDesc;
	}
	/**
	 * @设置 方案描述
	 * @param warnDesc
	 */
	public void setWarnDesc(String warnDesc) {
	 	this.warnDesc = warnDesc;
	}
	/**
	 * @return 是否启用
	 */
	public String getIsStart() {
	 	return isStart;
	}
	/**
	 * @设置 是否启用
	 * @param isStart
	 */
	public void setIsStart(String isStart) {
	 	this.isStart = isStart;
	}
	/**
	 * @return 建立日期
	 */
	public String getStartDate() {
	 	return startDate;
	}
	/**
	 * @设置 建立日期
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
	 	this.startDate = startDate;
	}
	/**
	 * @return 更新日期
	 */
	public String getUpdDate() {
	 	return updDate;
	}
	/**
	 * @设置 更新日期
	 * @param updDate
	 */
	public void setUpdDate(String updDate) {
	 	this.updDate = updDate;
	}
	/**
	 * @return 登记人编号
	 */
	public String getRegCusId() {
	 	return regCusId;
	}
	/**
	 * @设置 登记人编号
	 * @param regCusId
	 */
	public void setRegCusId(String regCusId) {
	 	this.regCusId = regCusId;
	}
	/**
	 * @return 登记人
	 */
	public String getRegCusName() {
	 	return regCusName;
	}
	/**
	 * @设置 登记人
	 * @param regCusName
	 */
	public void setRegCusName(String regCusName) {
	 	this.regCusName = regCusName;
	}
	/**
	 * @return 登记人机构编号
	 */
	public String getRegOrgId() {
	 	return regOrgId;
	}
	/**
	 * @设置 登记人机构编号
	 * @param regOrgId
	 */
	public void setRegOrgId(String regOrgId) {
	 	this.regOrgId = regOrgId;
	}
	/**
	 * @return 登记人机构
	 */
	public String getRegOrgName() {
	 	return regOrgName;
	}
	/**
	 * @设置 登记人机构
	 * @param regOrgName
	 */
	public void setRegOrgName(String regOrgName) {
	 	this.regOrgName = regOrgName;
	}
	/**
	 * @return 更新人编号
	 */
	public String getUpdateCusId() {
	 	return updateCusId;
	}
	/**
	 * @设置 更新人编号
	 * @param updateCusId
	 */
	public void setUpdateCusId(String updateCusId) {
	 	this.updateCusId = updateCusId;
	}
	/**
	 * @return 更新人姓名
	 */
	public String getUpdateCusName() {
	 	return updateCusName;
	}
	/**
	 * @设置 更新人姓名
	 * @param updateCusName
	 */
	public void setUpdateCusName(String updateCusName) {
	 	this.updateCusName = updateCusName;
	}
	/**
	 * @return 更新人机构编号
	 */
	public String getUpdateOrgId() {
	 	return updateOrgId;
	}
	/**
	 * @设置 更新人机构编号
	 * @param updateOrgId
	 */
	public void setUpdateOrgId(String updateOrgId) {
	 	this.updateOrgId = updateOrgId;
	}
	/**
	 * @return 更新人机构
	 */
	public String getUpdateOrgName() {
	 	return updateOrgName;
	}
	/**
	 * @设置 更新人机构
	 * @param updateOrgName
	 */
	public void setUpdateOrgName(String updateOrgName) {
	 	this.updateOrgName = updateOrgName;
	}
}