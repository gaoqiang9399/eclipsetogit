package app.component.pact.repay.entity;
import app.base.BaseDomain;
/**
* Title: MfPreRepayApply.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Aug 14 10:46:31 CST 2017
* @version：1.0
**/
public class MfPreRepayApply extends BaseDomain {
	private String preRepayAppId;//提前还款申请唯一主键
	private String appId;//申请编号
	private String pactId;//合同编号
	private String cusName;//客户名称
	private String appName;//项目名称
	private String fincId;//借据号
	private Double appAmt;//提前还款申请金额
	private String appAmtFormat;//提前还款申请金额格式化
	private Double intstAmt;//提前还款利息
	private Double penaltyAmt;//违约金
	private Double deductAmt;//优惠金额
	private String planRepayDate;//计划还款日期
	private String preRepayAppSts;//提前还款申请状态0-已申请未提交 1-流程中（已提交）2-申请成功（审批通过） 3-审批否决 
	private String appTime;//申请时间
	private String lstModTime;//最后修改时间
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String brNo;//部门编号
	private String brName;//部门名称
	private String remark;//备注
	private String flowFlag;//是否启用审批流程0-不启用 1-启用
	private String flowId;//流程编号
	private String borrowCode;
	private  String kindName;//产品名称

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getBorrowCode() {
		return borrowCode;
	}

	public void setBorrowCode(String borrowCode) {
		this.borrowCode = borrowCode;
	}

	/**
	 * @return 提前还款申请唯一主键
	 */
	public String getPreRepayAppId() {
	 	return preRepayAppId;
	}
	/**
	 * @设置 提前还款申请唯一主键
	 * @param preRepayAppId
	 */
	public void setPreRepayAppId(String preRepayAppId) {
	 	this.preRepayAppId = preRepayAppId;
	}
	/**
	 * @return 借据号
	 */
	public String getFincId() {
	 	return fincId;
	}
	/**
	 * @设置 借据号
	 * @param fincId
	 */
	public void setFincId(String fincId) {
	 	this.fincId = fincId;
	}
	/**
	 * @return 提前还款申请金额
	 */
	public Double getAppAmt() {
	 	return appAmt;
	}
	/**
	 * @设置 提前还款申请金额
	 * @param appAmt
	 */
	public void setAppAmt(Double appAmt) {
	 	this.appAmt = appAmt;
	}
	/**
	 * @return 违约金
	 */
	public Double getPenaltyAmt() {
	 	return penaltyAmt;
	}
	/**
	 * @设置 违约金
	 * @param penaltyAmt
	 */
	public void setPenaltyAmt(Double penaltyAmt) {
	 	this.penaltyAmt = penaltyAmt;
	}
	/**
	 * @return 优惠金额
	 */
	public Double getDeductAmt() {
	 	return deductAmt;
	}
	/**
	 * @设置 优惠金额
	 * @param deductAmt
	 */
	public void setDeductAmt(Double deductAmt) {
	 	this.deductAmt = deductAmt;
	}
	/**
	 * @return 计划还款日期
	 */
	public String getPlanRepayDate() {
	 	return planRepayDate;
	}
	/**
	 * @设置 计划还款日期
	 * @param planRepayDate
	 */
	public void setPlanRepayDate(String planRepayDate) {
	 	this.planRepayDate = planRepayDate;
	}
	/**
	 * @return 提前还款申请状态0-已申请未提交 1-流程中（已提交）2-申请成功（审批通过） 3-审批否决 
	 */
	public String getPreRepayAppSts() {
		return preRepayAppSts;
	}
	/**
	 * @设置 提前还款申请状态0-已申请未提交 1-流程中（已提交）2-申请成功（审批通过） 3-审批否决 
	 * @param appSts
	 */
	public void setPreRepayAppSts(String preRepayAppSts) {
		this.preRepayAppSts = preRepayAppSts;
	}
	/**
	 * @return 申请时间
	 */
	public String getAppTime() {
	 	return appTime;
	}
	/**
	 * @设置 申请时间
	 * @param appTime
	 */
	public void setAppTime(String appTime) {
	 	this.appTime = appTime;
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
	 * @return 操作员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作员名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 操作员名称
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
	 * @return 项目名称
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * @设置 项目名称
	 * @param appName
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getPactId() {
		return pactId;
	}
	public void setPactId(String pactId) {
		this.pactId = pactId;
	}
	public String getFlowFlag() {
		return flowFlag;
	}
	public void setFlowFlag(String flowFlag) {
		this.flowFlag = flowFlag;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Double getIntstAmt() {
		return intstAmt;
	}
	public void setIntstAmt(Double intstAmt) {
		this.intstAmt = intstAmt;
	}
	public String getAppAmtFormat() {
		return appAmtFormat;
	}
	public void setAppAmtFormat(String appAmtFormat) {
		this.appAmtFormat = appAmtFormat;
	}
}