package app.component.collateral.entity;
import app.base.BaseDomain;
/**
* Title: FairInfo.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Mar 15 13:16:46 CST 2017
* @version：1.0
**/
public class FairInfo extends BaseDomain {
	private String fairId;//公证ID
	private String collateralId;//押品编号
	private String fairNo;//公证书编号
	private String fairCus;//公证人
	private String fairOrg;//公证机构
	private String fairDate;//公正时间
	private String regCusId;//登记人编号
	private String regCusName;//登记人姓名
	private String regOrgId;//登记人机构
	private String regOrgName;//登记人机构名称
	private String regDate;//登记日期
	private String updateCusId;//更新人编号
	private String updateCusName;//更新人姓名
	private String updateOrgId;//更新人机构
	private String updateOrgName;//更新人机构名称
	private String updateDate;//更新日期
//	private String collateralType;
	private String classId;

	/**
	 * @return 公证ID
	 */
	public String getFairId() {
	 	return fairId;
	}
	/**
	 * @设置 公证ID
	 * @param fairId
	 */
	public void setFairId(String fairId) {
	 	this.fairId = fairId;
	}
	/**
	 * @return 押品编号
	 */
	public String getCollateralId() {
	 	return collateralId;
	}
	/**
	 * @设置 押品编号
	 * @param collateralId
	 */
	public void setCollateralId(String collateralId) {
	 	this.collateralId = collateralId;
	}
	/**
	 * @return 公证书编号
	 */
	public String getFairNo() {
	 	return fairNo;
	}
	/**
	 * @设置 公证书编号
	 * @param fairNo
	 */
	public void setFairNo(String fairNo) {
	 	this.fairNo = fairNo;
	}
	public String getFairCus() {
		return fairCus;
	}
	public void setFairCus(String fairCus) {
		this.fairCus = fairCus;
	}
	/**
	 * @return 公证机构
	 */
	public String getFairOrg() {
	 	return fairOrg;
	}
	/**
	 * @设置 公证机构
	 * @param fairOrg
	 */
	public void setFairOrg(String fairOrg) {
	 	this.fairOrg = fairOrg;
	}
	/**
	 * @return 公正时间
	 */
	public String getFairDate() {
	 	return fairDate;
	}
	/**
	 * @设置 公正时间
	 * @param fairDate
	 */
	public void setFairDate(String fairDate) {
	 	this.fairDate = fairDate;
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
	 * @return 登记人姓名
	 */
	public String getRegCusName() {
	 	return regCusName;
	}
	/**
	 * @设置 登记人姓名
	 * @param regCusName
	 */
	public void setRegCusName(String regCusName) {
	 	this.regCusName = regCusName;
	}
	/**
	 * @return 登记人机构
	 */
	public String getRegOrgId() {
	 	return regOrgId;
	}
	/**
	 * @设置 登记人机构
	 * @param regOrgId
	 */
	public void setRegOrgId(String regOrgId) {
	 	this.regOrgId = regOrgId;
	}
	/**
	 * @return 登记人机构名称
	 */
	public String getRegOrgName() {
	 	return regOrgName;
	}
	/**
	 * @设置 登记人机构名称
	 * @param regOrgName
	 */
	public void setRegOrgName(String regOrgName) {
	 	this.regOrgName = regOrgName;
	}
	/**
	 * @return 登记日期
	 */
	public String getRegDate() {
	 	return regDate;
	}
	/**
	 * @设置 登记日期
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
	 	this.regDate = regDate;
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
	 * @return 更新人机构
	 */
	public String getUpdateOrgId() {
	 	return updateOrgId;
	}
	/**
	 * @设置 更新人机构
	 * @param updateOrgId
	 */
	public void setUpdateOrgId(String updateOrgId) {
	 	this.updateOrgId = updateOrgId;
	}
	/**
	 * @return 更新人机构名称
	 */
	public String getUpdateOrgName() {
	 	return updateOrgName;
	}
	/**
	 * @设置 更新人机构名称
	 * @param updateOrgName
	 */
	public void setUpdateOrgName(String updateOrgName) {
	 	this.updateOrgName = updateOrgName;
	}
	/**
	 * @return 更新日期
	 */
	public String getUpdateDate() {
	 	return updateDate;
	}
	/**
	 * @设置 更新日期
	 * @param updateDate
	 */
	public void setUpdateDate(String updateDate) {
	 	this.updateDate = updateDate;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
}