package app.component.collateral.entity;
import app.base.BaseDomain;
/**
* Title: MfReceivablesTransferApproHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu May 11 11:24:37 CST 2017
* @version：1.0
**/
public class MfReceivablesTransferApproHis extends BaseDomain {
	private String tranApproHisId;//应收账款转让审批编号
	private String receTranAppId;//应收账款转让编号
	private Double receAmt;//应收账款总额
	private Double receBal;//应收账款余额
	private String tranReces;//转让押品
	private Double tranAmt;//转让金额
	private Double tranRate;//转让比例
	private Double feeAmt;//费用信息
	private String approveProcessId;//审批流程id
	private String approveNodeNo;//当前审批节点编号
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//审批角色号/用户号
	private String approvePartName;//当前审批角色/用户名称
	private String approveRemark;//审批说明
	private String approveResult;//审批意见1同意2否决3退回上一环节4退回初审5不同意
	private String appSts;//应收账款转让审批0新增申请1审批中2审批通过3否决
	private String busCollateralId;//押品业务关联编号
	private String opNo;//登记人
	private String opName;//登记人姓名
	private String brNo;//登记部门
	private String brName;//登记部门名称
	private String regTime;//信息登记时间
	private String lstModTime;//最后修改时间

	/**
	 * @return 应收账款转让审批编号
	 */
	public String getTranApproHisId() {
	 	return tranApproHisId;
	}
	/**
	 * @设置 应收账款转让审批编号
	 * @param tranApproHisId
	 */
	public void setTranApproHisId(String tranApproHisId) {
	 	this.tranApproHisId = tranApproHisId;
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
	 * @return 应收账款余额
	 */
	public Double getReceBal() {
	 	return receBal;
	}
	/**
	 * @设置 应收账款余额
	 * @param receBal
	 */
	public void setReceBal(Double receBal) {
	 	this.receBal = receBal;
	}
	/**
	 * @return 转让押品
	 */
	public String getTranReces() {
	 	return tranReces;
	}
	/**
	 * @设置 转让押品
	 * @param tranReces
	 */
	public void setTranReces(String tranReces) {
	 	this.tranReces = tranReces;
	}
	/**
	 * @return 转让金额
	 */
	public Double getTranAmt() {
	 	return tranAmt;
	}
	/**
	 * @设置 转让金额
	 * @param tranAmt
	 */
	public void setTranAmt(Double tranAmt) {
	 	this.tranAmt = tranAmt;
	}
	/**
	 * @return 转让比例
	 */
	public Double getTranRate() {
	 	return tranRate;
	}
	/**
	 * @设置 转让比例
	 * @param tranRate
	 */
	public void setTranRate(Double tranRate) {
	 	this.tranRate = tranRate;
	}
	/**
	 * @return 费用信息
	 */
	public Double getFeeAmt() {
	 	return feeAmt;
	}
	/**
	 * @设置 费用信息
	 * @param feeAmt
	 */
	public void setFeeAmt(Double feeAmt) {
	 	this.feeAmt = feeAmt;
	}
	/**
	 * @return 审批流程id
	 */
	public String getApproveProcessId() {
	 	return approveProcessId;
	}
	/**
	 * @设置 审批流程id
	 * @param approveProcessId
	 */
	public void setApproveProcessId(String approveProcessId) {
	 	this.approveProcessId = approveProcessId;
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
	 * @return 应收账款转让审批0新增申请1审批中2审批通过3否决
	 */
	public String getAppSts() {
	 	return appSts;
	}
	/**
	 * @设置 应收账款转让审批0新增申请1审批中2审批通过3否决
	 * @param appSts
	 */
	public void setAppSts(String appSts) {
	 	this.appSts = appSts;
	}
	/**
	 * @return 押品业务关联编号
	 */
	public String getBusCollateralId() {
	 	return busCollateralId;
	}
	/**
	 * @设置 押品业务关联编号
	 * @param busCollateralId
	 */
	public void setBusCollateralId(String busCollateralId) {
	 	this.busCollateralId = busCollateralId;
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
	public String getReceTranAppId() {
		return receTranAppId;
	}
	public void setReceTranAppId(String receTranAppId) {
		this.receTranAppId = receTranAppId;
	}
}