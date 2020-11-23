package app.component.oa.debt.entity;
import app.base.BaseDomain;
/**
* Title: MfOaDebt.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Dec 17 14:00:23 CST 2016
* @version：1.0
**/
public class MfOaDebt extends BaseDomain {
	private String debtId;//借款编号
	private String opNo;//借款人编号
	private String opName;//借款人姓名
	private String brNo;//部门编号
	private String brName;//部门名称
	private String applyType;//申请类别  个人借款、因公借款、预付款和其他
	private String applyName;//申请名称
	private Double applyAmt;//借款金额
	private Double returnAmt;//销账金额
	private Double debtAmt;//欠款金额
	private String reason;//申请事由
	private String applyTime;//申请时间
	private String lstReturnTime;//最后一次还款时间
	private String debtSts;//状态1未提交 2审批中 3借款复核中 4审批未通过 5已借出 6还款复核中 7已完成
	private String approveNodeName;//审批环节
	private String br;//部门：brName+brNo
	private String op;//申请人：opName+opNo
	private String approvePartNo;//当前审批人员编号
	private String approvePartName;//当前审批人员名称
	/**
	 * @return 借款编号
	 */
	public String getDebtId() {
	 	return debtId;
	}
	/**
	 * @设置 借款编号
	 * @param debtId
	 */
	public void setDebtId(String debtId) {
	 	this.debtId = debtId;
	}
	/**
	 * @return 借款人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 借款人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 借款人姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 借款人姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 申请类别  个人借款、因公借款、预付款和其他
	 */
	public String getApplyType() {
	 	return applyType;
	}
	/**
	 * @设置 申请类别  个人借款、因公借款、预付款和其他
	 * @param applyType
	 */
	public void setApplyType(String applyType) {
	 	this.applyType = applyType;
	}

	/**
	 * @return 申请名称
	 */
	public String getApplyName() {
	 	return applyName;
	}
	/**
	 * @设置 申请名称
	 * @param applyName
	 */
	public void setApplyName(String applyName) {
	 	this.applyName = applyName;
	}
	/**
	 * @return 借款金额
	 */
	
	/**
	 * @return 申请事由
	 */
	public String getReason() {
	 	return reason;
	}
	/**
	 * @设置 申请事由
	 * @param reason
	 */
	public void setReason(String reason) {
	 	this.reason = reason;
	}
	/**
	 * @return 申请时间
	 */
	public String getApplyTime() {
	 	return applyTime;
	}
	/**
	 * @设置 申请时间
	 * @param applyTime
	 */
	public void setApplyTime(String applyTime) {
	 	this.applyTime = applyTime;
	}
	/**
	 * @return 最后一次还款时间
	 */
	public String getLstReturnTime() {
	 	return lstReturnTime;
	}
	/**
	 * @设置 最后一次还款时间
	 * @param lstReturnTime
	 */
	public void setLstReturnTime(String lstReturnTime) {
	 	this.lstReturnTime = lstReturnTime;
	}
	/**
	 * @return 状态1未提交 2审批中 3借款复核中 4审批未通过 5已借出 6还款复核中 7已完成
	 */
	public String getDebtSts() {
	 	return debtSts;
	}
	/**
	 * @设置 状态1未提交 2审批中 3借款复核中 4审批未通过 5已借出 6还款复核中 7已完成
	 * @param debtSts
	 */
	public void setDebtSts(String debtSts) {
	 	this.debtSts = debtSts;
	}

	/**
	 * @return 审批环节
	 */
	public String getApproveNodeName() {
	 	return approveNodeName;
	}
	/**
	 * @设置 审批环节
	 * @param approveNodeName
	 */
	public void setApproveNodeName(String approveNodeName) {
	 	this.approveNodeName = approveNodeName;
	}
	public String getBr() {
		return br;
	}
	public void setBr(String br) {
		this.br = br;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	
	public Double getApplyAmt() {
		return applyAmt;
	}
	public void setApplyAmt(Double applyAmt) {
		this.applyAmt = applyAmt;
	}
	public Double getReturnAmt() {
		return returnAmt;
	}
	public void setReturnAmt(Double returnAmt) {
		this.returnAmt = returnAmt;
	}
	public Double getDebtAmt() {
		return debtAmt;
	}
	public void setDebtAmt(Double debtAmt) {
		this.debtAmt = debtAmt;
	}
	public String getApprovePartNo() {
		return approvePartNo;
	}
	public void setApprovePartNo(String approvePartNo) {
		this.approvePartNo = approvePartNo;
	}
	public String getApprovePartName() {
		return approvePartName;
	}
	public void setApprovePartName(String approvePartName) {
		this.approvePartName = approvePartName;
	}
	
	
}