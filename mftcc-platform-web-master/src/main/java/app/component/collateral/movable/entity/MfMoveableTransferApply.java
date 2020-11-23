package app.component.collateral.movable.entity;
import app.base.BaseDomain;
/**
* Title: MfMoveableTransferApply.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Jun 09 16:32:52 CST 2017
* @version：1.0
**/
public class MfMoveableTransferApply extends BaseDomain {
	private String transferId;//移库编号
	private String cusNoWarehouse;//仓储机构
	private String cusNameWarehouse;//仓储机构名称
	private String warehouseAddress;//仓库地点
	private String transferPledge;//移库押品
	private String transferPledgeDes;//移库押品描述
	private Integer pledgeNum;//押品数量
	private String pledgeDetail;//货物清单
	private String transferReason;//移库原因
	private String appSts;//申请状态0新增申请1审批中2审批通过3否决
	private String busPleId;//押品业务关联编号
	private String opNo;//登记人
	private String opName;//登记人姓名
	private String brNo;//登记部门
	private String brName;//登记部门名称
	private String regTime;//信息登记时间
	private String lstModTime;//最后修改时间
	private String approveNodeName;//当前审批节点名称
	private String approvePartName;//当前审批角色/用户名称

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
	public String getApproveNodeName() {
		return approveNodeName;
	}
	public void setApproveNodeName(String approveNodeName) {
		this.approveNodeName = approveNodeName;
	}
	public String getApprovePartName() {
		return approvePartName;
	}
	public void setApprovePartName(String approvePartName) {
		this.approvePartName = approvePartName;
	}
	public String getTransferPledge() {
		return transferPledge;
	}
	public void setTransferPledge(String transferPledge) {
		this.transferPledge = transferPledge;
	}
	public String getTransferPledgeDes() {
		return transferPledgeDes;
	}
	public void setTransferPledgeDes(String transferPledgeDes) {
		this.transferPledgeDes = transferPledgeDes;
	}
}