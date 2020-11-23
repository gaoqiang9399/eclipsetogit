package app.component.collateral.movable.entity;
import app.base.BaseDomain;
/**
* Title: MfMoveableModifyApproHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jun 12 20:27:07 CST 2017
* @version：1.0
**/
public class MfMoveableModifyApproHis extends BaseDomain {
	private String modifyApproHisId;//调价申请审批编号
	private String modifyId;//调价编号
	private String pledgeNo;//押品名称
	private String pledgeName;//押品编号
	private String pledgeShowNo;//押品展示编号
	private Double pledgeWorth;//押品价值
	private String unitPrice;//单价
	private Double adjustedWorth;//调整后价值
	private String adjustedUnitPrice;//调整后单价
	private String adjustedBasis;//调价依据
	private String adjustedReason;//调价原因
	private String pledgeBillNo;//调价原因
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
	 * @return 调价申请审批编号
	 */
	public String getModifyApproHisId() {
	 	return modifyApproHisId;
	}
	/**
	 * @设置 调价申请审批编号
	 * @param modifyApproHisId
	 */
	public void setModifyApproHisId(String modifyApproHisId) {
	 	this.modifyApproHisId = modifyApproHisId;
	}
	/**
	 * @return 调价编号
	 */
	public String getModifyId() {
	 	return modifyId;
	}
	/**
	 * @设置 调价编号
	 * @param modifyId
	 */
	public void setModifyId(String modifyId) {
	 	this.modifyId = modifyId;
	}
	/**
	 * @return 押品名称
	 */
	public String getPledgeNo() {
	 	return pledgeNo;
	}
	/**
	 * @设置 押品名称
	 * @param pledgeNo
	 */
	public void setPledgeNo(String pledgeNo) {
	 	this.pledgeNo = pledgeNo;
	}
	/**
	 * @return 押品编号
	 */
	public String getPledgeName() {
	 	return pledgeName;
	}
	/**
	 * @设置 押品编号
	 * @param pledgeName
	 */
	public void setPledgeName(String pledgeName) {
	 	this.pledgeName = pledgeName;
	}
	/**
	 * @return 押品价值
	 */
	public Double getPledgeWorth() {
	 	return pledgeWorth;
	}
	/**
	 * @设置 押品价值
	 * @param pledgeWorth
	 */
	public void setPledgeWorth(Double pledgeWorth) {
	 	this.pledgeWorth = pledgeWorth;
	}
	/**
	 * @return 单价
	 */
	public String getUnitPrice() {
	 	return unitPrice;
	}
	/**
	 * @设置 单价
	 * @param unitPrice
	 */
	public void setUnitPrice(String unitPrice) {
	 	this.unitPrice = unitPrice;
	}
	/**
	 * @return 调整后价值
	 */
	public Double getAdjustedWorth() {
	 	return adjustedWorth;
	}
	/**
	 * @设置 调整后价值
	 * @param adjustedWorth
	 */
	public void setAdjustedWorth(Double adjustedWorth) {
	 	this.adjustedWorth = adjustedWorth;
	}
	/**
	 * @return 调整后单价
	 */
	public String getAdjustedUnitPrice() {
	 	return adjustedUnitPrice;
	}
	/**
	 * @设置 调整后单价
	 * @param adjustedUnitPrice
	 */
	public void setAdjustedUnitPrice(String adjustedUnitPrice) {
	 	this.adjustedUnitPrice = adjustedUnitPrice;
	}
	/**
	 * @return 调价依据
	 */
	public String getAdjustedBasis() {
	 	return adjustedBasis;
	}
	/**
	 * @设置 调价依据
	 * @param adjustedBasis
	 */
	public void setAdjustedBasis(String adjustedBasis) {
	 	this.adjustedBasis = adjustedBasis;
	}
	/**
	 * @return 调价原因
	 */
	public String getAdjustedReason() {
	 	return adjustedReason;
	}
	/**
	 * @设置 调价原因
	 * @param adjustedReason
	 */
	public void setAdjustedReason(String adjustedReason) {
	 	this.adjustedReason = adjustedReason;
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
	/**
	 * 
	 * @return 押品展示编号
	 */
	public String getPledgeShowNo() {
		return pledgeShowNo;
	}
	/**
	 * @设置 押品展示编号
	 * @param pledgeShowNo
	 */
	public void setPledgeShowNo(String pledgeShowNo) {
		this.pledgeShowNo = pledgeShowNo;
	}
	
}