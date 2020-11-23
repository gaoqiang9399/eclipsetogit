package app.component.rec.entity;
import app.base.BaseDomain;
/**
* Title: CollectionPlan.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sun Mar 19 14:05:01 CST 2017
* @version：1.0
**/
public class CollectionPlan extends BaseDomain {
	private String collectionPlanNo;//催收方案编号
	private String collectionPlanName;//催收方案名称
	private String regDate;//方案建立时间
	private String updateDate;//方案更新时间
	private String isStart;//是否启用
	private String collectionPlanExplain;//方案说明
	private String regCusId;//登记人编号
	private String regCusName;//登记人姓名
	private String regOrgId;//登记人机构
	private String regOrgName;//登记人机构名称
	private String updateCusId;//更新人编号
	private String updateCusName;//更新人姓名
	private String updateOrgId;//更新人机构
	private String updateOrgName;//更新人机构名称

	/**
	 * @return 催收方案编号
	 */
	public String getCollectionPlanNo() {
	 	return collectionPlanNo;
	}
	/**
	 * @设置 催收方案编号
	 * @param collectionPlanNo
	 */
	public void setCollectionPlanNo(String collectionPlanNo) {
	 	this.collectionPlanNo = collectionPlanNo;
	}
	/**
	 * @return 催收方案名称
	 */
	public String getCollectionPlanName() {
	 	return collectionPlanName;
	}
	/**
	 * @设置 催收方案名称
	 * @param collectionPlanName
	 */
	public void setCollectionPlanName(String collectionPlanName) {
	 	this.collectionPlanName = collectionPlanName;
	}
	/**
	 * @return 方案建立时间
	 */
	public String getRegDate() {
	 	return regDate;
	}
	/**
	 * @设置 方案建立时间
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
	 	this.regDate = regDate;
	}
	/**
	 * @return 方案更新时间
	 */
	public String getUpdateDate() {
	 	return updateDate;
	}
	/**
	 * @设置 方案更新时间
	 * @param updateDate
	 */
	public void setUpdateDate(String updateDate) {
	 	this.updateDate = updateDate;
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
	 * @return 方案说明
	 */
	public String getCollectionPlanExplain() {
	 	return collectionPlanExplain;
	}
	/**
	 * @设置 方案说明
	 * @param collectionPlanExplain
	 */
	public void setCollectionPlanExplain(String collectionPlanExplain) {
	 	this.collectionPlanExplain = collectionPlanExplain;
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
}