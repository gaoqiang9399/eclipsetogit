package app.component.collateral.entity;
import app.base.BaseDomain;
/**
* Title: MfReceivablesDisputedAppHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon May 15 18:31:05 CST 2017
* @version：1.0
**/
public class MfReceivablesDisputedAppHis extends BaseDomain {
	private String disputedAppHisId;//争议审批历史编号
	private String disputedAppId;//争议申请编号
	private String disputedInitiatorNo;//争议发起方
	private String disputedInitiatorName;//争议发起方姓名
	private String disputedType;//争议类型1拒绝接受货物;2拒绝接收发票;3提供抗辩、反索或抵销
	private String relPledgeNo;//关联押品编号
	private String relPledgeName;//关联押品名称
	private String relInvoiceNum;//关联发票号
	private String remark;//争议原因
	private String disputedDetail;//争议货物明细
	private String disputedResult;//争议结果
	private String disputedDate;//争议解除时间
	private String appSts;//争议申请状态0新增申请1审批中2审批通过3否决
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
	 * @return 争议审批历史编号
	 */
	public String getDisputedAppHisId() {
	 	return disputedAppHisId;
	}
	/**
	 * @设置 争议审批历史编号
	 * @param disputedAppHisId
	 */
	public void setDisputedAppHisId(String disputedAppHisId) {
	 	this.disputedAppHisId = disputedAppHisId;
	}
	/**
	 * @return 争议申请编号
	 */
	public String getDisputedAppId() {
	 	return disputedAppId;
	}
	/**
	 * @设置 争议申请编号
	 * @param disputedAppId
	 */
	public void setDisputedAppId(String disputedAppId) {
	 	this.disputedAppId = disputedAppId;
	}
	/**
	 * @return 争议发起方
	 */
	public String getDisputedInitiatorNo() {
	 	return disputedInitiatorNo;
	}
	/**
	 * @设置 争议发起方
	 * @param disputedInitiatorNo
	 */
	public void setDisputedInitiatorNo(String disputedInitiatorNo) {
	 	this.disputedInitiatorNo = disputedInitiatorNo;
	}
	/**
	 * @return 争议发起方姓名
	 */
	public String getDisputedInitiatorName() {
	 	return disputedInitiatorName;
	}
	/**
	 * @设置 争议发起方姓名
	 * @param disputedInitiatorName
	 */
	public void setDisputedInitiatorName(String disputedInitiatorName) {
	 	this.disputedInitiatorName = disputedInitiatorName;
	}
	/**
	 * @return 争议类型1拒绝接受货物;2拒绝接收发票;3提供抗辩、反索或抵销
	 */
	public String getDisputedType() {
	 	return disputedType;
	}
	/**
	 * @设置 争议类型1拒绝接受货物;2拒绝接收发票;3提供抗辩、反索或抵销
	 * @param disputedType
	 */
	public void setDisputedType(String disputedType) {
	 	this.disputedType = disputedType;
	}
	/**
	 * @return 关联发票号
	 */
	public String getRelInvoiceNum() {
	 	return relInvoiceNum;
	}
	/**
	 * @设置 关联发票号
	 * @param relInvoiceNum
	 */
	public void setRelInvoiceNum(String relInvoiceNum) {
	 	this.relInvoiceNum = relInvoiceNum;
	}
	/**
	 * @return 争议原因
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 争议原因
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 争议货物明细
	 */
	public String getDisputedDetail() {
	 	return disputedDetail;
	}
	/**
	 * @设置 争议货物明细
	 * @param disputedDetail
	 */
	public void setDisputedDetail(String disputedDetail) {
	 	this.disputedDetail = disputedDetail;
	}
	/**
	 * @return 争议结果
	 */
	public String getDisputedResult() {
	 	return disputedResult;
	}
	/**
	 * @设置 争议结果
	 * @param disputedResult
	 */
	public void setDisputedResult(String disputedResult) {
	 	this.disputedResult = disputedResult;
	}
	/**
	 * @return 争议解除时间
	 */
	public String getDisputedDate() {
	 	return disputedDate;
	}
	/**
	 * @设置 争议解除时间
	 * @param disputedDate
	 */
	public void setDisputedDate(String disputedDate) {
	 	this.disputedDate = disputedDate;
	}
	/**
	 * @return 争议申请状态0新增申请1审批中2审批通过3否决
	 */
	public String getAppSts() {
	 	return appSts;
	}
	/**
	 * @设置 争议申请状态0新增申请1审批中2审批通过3否决
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
	public String getRelPledgeNo() {
		return relPledgeNo;
	}
	public void setRelPledgeNo(String relPledgeNo) {
		this.relPledgeNo = relPledgeNo;
	}
	public String getRelPledgeName() {
		return relPledgeName;
	}
	public void setRelPledgeName(String relPledgeName) {
		this.relPledgeName = relPledgeName;
	}
}