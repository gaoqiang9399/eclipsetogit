package app.component.collateral.entity;
import app.base.BaseDomain;
/**
* Title: MfReceivablesRebateAppHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon May 15 11:31:02 CST 2017
* @version：1.0
**/
public class MfReceivablesRebateAppHis extends BaseDomain {
	private String rebateAppHisId;//折让申请审批历史编号
	private String rebateAppId;//折让申请编号
	private Double receAmt;//应收账款总额
	private Double tranAmt;//应收账款已受让金额
	private Double fincAppAmt;//本次支用余额，当前借据余额
	private Double rebateAmt;//申请折让金额
	private String rebateReces;//折让押品
	private Double rebateRepayAmt;//折让后需偿付本金
	private Double rebateRepayInt;//折让后需偿付利息
	private String remark;//备注
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
	 * @return 折让申请编号
	 */
	public String getRebateAppId() {
	 	return rebateAppId;
	}
	/**
	 * @设置 折让申请编号
	 * @param rebateAppId
	 */
	public void setRebateAppId(String rebateAppId) {
	 	this.rebateAppId = rebateAppId;
	}
	/**
	 * @return 应收账款总额
	 */
	public Double getReceAmt() {
	 	return receAmt;
	}
	/**
	 * @设置 应收账款总额
	 * @param receAmt
	 */
	public void setReceAmt(Double receAmt) {
	 	this.receAmt = receAmt;
	}
	/**
	 * @return 应收账款已受让金额
	 */
	public Double getTranAmt() {
	 	return tranAmt;
	}
	/**
	 * @设置 应收账款已受让金额
	 * @param tranAmt
	 */
	public void setTranAmt(Double tranAmt) {
	 	this.tranAmt = tranAmt;
	}
	/**
	 * @return 本次支用余额，当前借据余额
	 */
	public Double getFincAppAmt() {
	 	return fincAppAmt;
	}
	/**
	 * @设置 本次支用余额，当前借据余额
	 * @param fincAppAmt
	 */
	public void setFincAppAmt(Double fincAppAmt) {
	 	this.fincAppAmt = fincAppAmt;
	}
	/**
	 * @return 申请折让金额
	 */
	public Double getRebateAmt() {
	 	return rebateAmt;
	}
	/**
	 * @设置 申请折让金额
	 * @param rebateAmt
	 */
	public void setRebateAmt(Double rebateAmt) {
	 	this.rebateAmt = rebateAmt;
	}
	/**
	 * @return 折让押品
	 */
	public String getRebateReces() {
	 	return rebateReces;
	}
	/**
	 * @设置 折让押品
	 * @param rebateReces
	 */
	public void setRebateReces(String rebateReces) {
	 	this.rebateReces = rebateReces;
	}
	/**
	 * @return 折让后需偿付本金
	 */
	public Double getRebateRepayAmt() {
	 	return rebateRepayAmt;
	}
	/**
	 * @设置 折让后需偿付本金
	 * @param rebateRepayAmt
	 */
	public void setRebateRepayAmt(Double rebateRepayAmt) {
	 	this.rebateRepayAmt = rebateRepayAmt;
	}
	/**
	 * @return 折让后需偿付利息
	 */
	public Double getRebateRepayInt() {
	 	return rebateRepayInt;
	}
	/**
	 * @设置 折让后需偿付利息
	 * @param rebateRepayInt
	 */
	public void setRebateRepayInt(Double rebateRepayInt) {
	 	this.rebateRepayInt = rebateRepayInt;
	}
	/**
	 * @return 备注
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
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
	public String getRebateAppHisId() {
		return rebateAppHisId;
	}
	public void setRebateAppHisId(String rebateAppHisId) {
		this.rebateAppHisId = rebateAppHisId;
	}
}