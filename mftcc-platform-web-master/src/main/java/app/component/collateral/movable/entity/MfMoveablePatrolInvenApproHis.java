package app.component.collateral.movable.entity;
import app.base.BaseDomain;
/**
* Title: MfMoveablePatrolInvenApproHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Jun 13 20:29:52 CST 2017
* @version：1.0
**/
public class MfMoveablePatrolInvenApproHis extends BaseDomain {
	private String patrolApproHis;//巡库审批历史编号
	private String patrolId;//巡库编号
	private String patrolOpNo;//巡库人
	private String patrolOpName;//巡库人名称
	private String patrolDate;//巡库时间
	private String quantityIsTrue;//品名、数量是否正确
	private String isDamage;//是否有包装破损、锈蚀
	private String isStackTrue;//是否有按要求堆放
	private String isStackChange;//是否变更堆位
	private String isAbnormalGoodsLabel;//有无其他异常货物标签
	private String isRisk;//是否存在风险
	private String riskType;//存在风险情况
	private String riskRemark;//风险情况说明
	private String storageCapacityRemark;//仓库库容情况
	private String inventoryConditionRemark;//仓库存货条件
	private String mechEquipmentRemark;//仓库机械设备
	private String staffTurnoverRemark;//仓库人员变化
	private String stockRemark;//收货情况
	private String shipmentRemark;//出货情况
	private String remark;//总结或补充
	private String manageRemark;//仓库管理动态
	private String appSts;//申请状态0新增申请1审批中2审批通过3否决
	private String busPleId;//押品业务关联编号
	private String approveNodeNo;//当前审批节点编号
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//审批角色号/用户号
	private String approvePartName;//当前审批角色/用户名称
	private String approveRemark;//审批说明
	private String approveResult;//审批意见1同意2否决3退回上一环节4退回初审5不同意
	private String opNo;//登记人
	private String opName;//登记人姓名
	private String brNo;//登记部门
	private String brName;//登记部门名称
	private String regTime;//信息登记时间
	private String lstModTime;//最后修改时间

	/**
	 * @return 巡库审批历史编号
	 */
	public String getPatrolApproHis() {
	 	return patrolApproHis;
	}
	/**
	 * @设置 巡库审批历史编号
	 * @param patrolApproHis
	 */
	public void setPatrolApproHis(String patrolApproHis) {
	 	this.patrolApproHis = patrolApproHis;
	}
	/**
	 * @return 巡库编号
	 */
	public String getPatrolId() {
	 	return patrolId;
	}
	/**
	 * @设置 巡库编号
	 * @param patrolId
	 */
	public void setPatrolId(String patrolId) {
	 	this.patrolId = patrolId;
	}
	/**
	 * @return 巡库人
	 */
	public String getPatrolOpNo() {
	 	return patrolOpNo;
	}
	/**
	 * @设置 巡库人
	 * @param patrolOpNo
	 */
	public void setPatrolOpNo(String patrolOpNo) {
	 	this.patrolOpNo = patrolOpNo;
	}
	/**
	 * @return 巡库人名称
	 */
	public String getPatrolOpName() {
	 	return patrolOpName;
	}
	/**
	 * @设置 巡库人名称
	 * @param patrolOpName
	 */
	public void setPatrolOpName(String patrolOpName) {
	 	this.patrolOpName = patrolOpName;
	}
	/**
	 * @return 巡库时间
	 */
	public String getPatrolDate() {
	 	return patrolDate;
	}
	/**
	 * @设置 巡库时间
	 * @param patrolDate
	 */
	public void setPatrolDate(String patrolDate) {
	 	this.patrolDate = patrolDate;
	}
	/**
	 * @return 品名、数量是否正确
	 */
	public String getQuantityIsTrue() {
	 	return quantityIsTrue;
	}
	/**
	 * @设置 品名、数量是否正确
	 * @param quantityIsTrue
	 */
	public void setQuantityIsTrue(String quantityIsTrue) {
	 	this.quantityIsTrue = quantityIsTrue;
	}
	/**
	 * @return 是否有包装破损、锈蚀
	 */
	public String getIsDamage() {
	 	return isDamage;
	}
	/**
	 * @设置 是否有包装破损、锈蚀
	 * @param isDamage
	 */
	public void setIsDamage(String isDamage) {
	 	this.isDamage = isDamage;
	}
	/**
	 * @return 是否有按要求堆放
	 */
	public String getIsStackTrue() {
	 	return isStackTrue;
	}
	/**
	 * @设置 是否有按要求堆放
	 * @param isStackTrue
	 */
	public void setIsStackTrue(String isStackTrue) {
	 	this.isStackTrue = isStackTrue;
	}
	/**
	 * @return 是否变更堆位
	 */
	public String getIsStackChange() {
	 	return isStackChange;
	}
	/**
	 * @设置 是否变更堆位
	 * @param isStackChange
	 */
	public void setIsStackChange(String isStackChange) {
	 	this.isStackChange = isStackChange;
	}
	/**
	 * @return 有无其他异常货物标签
	 */
	public String getIsAbnormalGoodsLabel() {
	 	return isAbnormalGoodsLabel;
	}
	/**
	 * @设置 有无其他异常货物标签
	 * @param isAbnormalGoodsLabel
	 */
	public void setIsAbnormalGoodsLabel(String isAbnormalGoodsLabel) {
	 	this.isAbnormalGoodsLabel = isAbnormalGoodsLabel;
	}
	/**
	 * @return 是否存在风险
	 */
	public String getIsRisk() {
	 	return isRisk;
	}
	/**
	 * @设置 是否存在风险
	 * @param isRisk
	 */
	public void setIsRisk(String isRisk) {
	 	this.isRisk = isRisk;
	}
	/**
	 * @return 存在风险情况
	 */
	public String getRiskType() {
	 	return riskType;
	}
	/**
	 * @设置 存在风险情况
	 * @param riskType
	 */
	public void setRiskType(String riskType) {
	 	this.riskType = riskType;
	}
	/**
	 * @return 风险情况说明
	 */
	public String getRiskRemark() {
	 	return riskRemark;
	}
	/**
	 * @设置 风险情况说明
	 * @param riskRemark
	 */
	public void setRiskRemark(String riskRemark) {
	 	this.riskRemark = riskRemark;
	}
	/**
	 * @return 仓库库容情况
	 */
	public String getStorageCapacityRemark() {
	 	return storageCapacityRemark;
	}
	/**
	 * @设置 仓库库容情况
	 * @param storageCapacityRemark
	 */
	public void setStorageCapacityRemark(String storageCapacityRemark) {
	 	this.storageCapacityRemark = storageCapacityRemark;
	}
	/**
	 * @return 仓库存货条件
	 */
	public String getInventoryConditionRemark() {
	 	return inventoryConditionRemark;
	}
	/**
	 * @设置 仓库存货条件
	 * @param inventoryConditionRemark
	 */
	public void setInventoryConditionRemark(String inventoryConditionRemark) {
	 	this.inventoryConditionRemark = inventoryConditionRemark;
	}
	/**
	 * @return 仓库机械设备
	 */
	public String getMechEquipmentRemark() {
	 	return mechEquipmentRemark;
	}
	/**
	 * @设置 仓库机械设备
	 * @param mechEquipmentRemark
	 */
	public void setMechEquipmentRemark(String mechEquipmentRemark) {
	 	this.mechEquipmentRemark = mechEquipmentRemark;
	}
	/**
	 * @return 仓库人员变化
	 */
	public String getStaffTurnoverRemark() {
	 	return staffTurnoverRemark;
	}
	/**
	 * @设置 仓库人员变化
	 * @param staffTurnoverRemark
	 */
	public void setStaffTurnoverRemark(String staffTurnoverRemark) {
	 	this.staffTurnoverRemark = staffTurnoverRemark;
	}
	/**
	 * @return 收货情况
	 */
	public String getStockRemark() {
	 	return stockRemark;
	}
	/**
	 * @设置 收货情况
	 * @param stockRemark
	 */
	public void setStockRemark(String stockRemark) {
	 	this.stockRemark = stockRemark;
	}
	/**
	 * @return 出货情况
	 */
	public String getShipmentRemark() {
	 	return shipmentRemark;
	}
	/**
	 * @设置 出货情况
	 * @param shipmentRemark
	 */
	public void setShipmentRemark(String shipmentRemark) {
	 	this.shipmentRemark = shipmentRemark;
	}
	/**
	 * @return 总结或补充
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 总结或补充
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 仓库管理动态
	 */
	public String getManageRemark() {
	 	return manageRemark;
	}
	/**
	 * @设置 仓库管理动态
	 * @param manageRemark
	 */
	public void setManageRemark(String manageRemark) {
	 	this.manageRemark = manageRemark;
	}
	/**
	 * @return 申请状态0新增申请1审批中2审批通过3否决
	 */
	public String getAppSts() {
	 	return appSts;
	}
	/**
	 * @设置 申请状态0新增申请1审批中2审批通过3否决
	 * @param appSts
	 */
	public void setAppSts(String appSts) {
	 	this.appSts = appSts;
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
	 * @return 当前审批节点编号
	 */
	public String getApproveNodeNo() {
	 	return approveNodeNo;
	}
	/**
	 * @设置 当前审批节点编号
	 * @param approveNodeNo
	 */
	public void setApproveNodeNo(String approveNodeNo) {
	 	this.approveNodeNo = approveNodeNo;
	}
	/**
	 * @return 当前审批节点名称
	 */
	public String getApproveNodeName() {
	 	return approveNodeName;
	}
	/**
	 * @设置 当前审批节点名称
	 * @param approveNodeName
	 */
	public void setApproveNodeName(String approveNodeName) {
	 	this.approveNodeName = approveNodeName;
	}
	/**
	 * @return 审批角色号/用户号
	 */
	public String getApprovePartNo() {
	 	return approvePartNo;
	}
	/**
	 * @设置 审批角色号/用户号
	 * @param approvePartNo
	 */
	public void setApprovePartNo(String approvePartNo) {
	 	this.approvePartNo = approvePartNo;
	}
	/**
	 * @return 当前审批角色/用户名称
	 */
	public String getApprovePartName() {
	 	return approvePartName;
	}
	/**
	 * @设置 当前审批角色/用户名称
	 * @param approvePartName
	 */
	public void setApprovePartName(String approvePartName) {
	 	this.approvePartName = approvePartName;
	}
	/**
	 * @return 审批说明
	 */
	public String getApproveRemark() {
	 	return approveRemark;
	}
	/**
	 * @设置 审批说明
	 * @param approveRemark
	 */
	public void setApproveRemark(String approveRemark) {
	 	this.approveRemark = approveRemark;
	}
	/**
	 * @return 审批意见1同意2否决3退回上一环节4退回初审5不同意
	 */
	public String getApproveResult() {
	 	return approveResult;
	}
	/**
	 * @设置 审批意见1同意2否决3退回上一环节4退回初审5不同意
	 * @param approveResult
	 */
	public void setApproveResult(String approveResult) {
	 	this.approveResult = approveResult;
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
}