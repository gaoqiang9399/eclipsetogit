package app.component.collateral.movable.entity;
import app.base.BaseDomain;
/**
* Title: MfMoveableTransferApproHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Jun 09 16:34:57 CST 2017
* @version：1.0
**/
public class MfMoveableTransferApproHis extends BaseDomain {
	private String transferApproHisId;//移库审批历史编号
	private String transferId;//移库编号
	private String cusNoWarehouse;//仓储机构
	private String cusNameWarehouse;//仓储机构名称
	private String warehouseAddress;//仓库地点
	private String transferPledge;//移库押品
	private Integer pledgeNum;//押品数量
	private String pledgeDetail;//货物清单
	private String transferReason;//移库原因
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
	 * @return 移库审批历史编号
	 */
	public String getTransferApproHisId() {
	 	return transferApproHisId;
	}
	/**
	 * @设置 移库审批历史编号
	 * @param transferApproHisId
	 */
	public void setTransferApproHisId(String transferApproHisId) {
	 	this.transferApproHisId = transferApproHisId;
	}
	/**
	 * @return 移库编号
	 */
	public String getTransferId() {
	 	return transferId;
	}
	/**
	 * @设置 移库编号
	 * @param transferId
	 */
	public void setTransferId(String transferId) {
	 	this.transferId = transferId;
	}
	/**
	 * @return 仓储机构
	 */
	public String getCusNoWarehouse() {
	 	return cusNoWarehouse;
	}
	/**
	 * @设置 仓储机构
	 * @param cusNoWarehouse
	 */
	public void setCusNoWarehouse(String cusNoWarehouse) {
	 	this.cusNoWarehouse = cusNoWarehouse;
	}
	/**
	 * @return 仓储机构名称
	 */
	public String getCusNameWarehouse() {
	 	return cusNameWarehouse;
	}
	/**
	 * @设置 仓储机构名称
	 * @param cusNameWarehouse
	 */
	public void setCusNameWarehouse(String cusNameWarehouse) {
	 	this.cusNameWarehouse = cusNameWarehouse;
	}
	/**
	 * @return 仓库地点
	 */
	public String getWarehouseAddress() {
	 	return warehouseAddress;
	}
	/**
	 * @设置 仓库地点
	 * @param warehouseAddress
	 */
	public void setWarehouseAddress(String warehouseAddress) {
	 	this.warehouseAddress = warehouseAddress;
	}
	/**
	 * @return 押品数量
	 */
	public Integer getPledgeNum() {
	 	return pledgeNum;
	}
	/**
	 * @设置 押品数量
	 * @param pledgeNum
	 */
	public void setPledgeNum(Integer pledgeNum) {
	 	this.pledgeNum = pledgeNum;
	}
	/**
	 * @return 货物清单
	 */
	public String getPledgeDetail() {
	 	return pledgeDetail;
	}
	/**
	 * @设置 货物清单
	 * @param pledgeDetail
	 */
	public void setPledgeDetail(String pledgeDetail) {
	 	this.pledgeDetail = pledgeDetail;
	}
	/**
	 * @return 移库原因
	 */
	public String getTransferReason() {
	 	return transferReason;
	}
	/**
	 * @设置 移库原因
	 * @param transferReason
	 */
	public void setTransferReason(String transferReason) {
	 	this.transferReason = transferReason;
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
	public String getTransferPledge() {
		return transferPledge;
	}
	public void setTransferPledge(String transferPledge) {
		this.transferPledge = transferPledge;
	}
}