package app.component.oa.becomequalified.entity;
import app.base.BaseDomain;
/**
* Title: MfOaBecomeQualifiedHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Dec 15 14:11:58 CST 2017
* @version：1.0
**/
public class MfOaBecomeQualifiedHis extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String id;//ID
	private String becomeQualifiedId;//转正申请ID
	private String applyDate;//申请日期
	private String baseId;//关联基础信息ID
	private String lstModTime;//
	private String regTime;//创建时间
	private String opNo;//创建该申请操作员
	private String name;//姓名
	private String hireDate;//入职日期
	private String brNo;//部门编号
	private String brName;//部门名称
	private String position;//职级（经理，主任等）
	private String performance;//试用期间工作/业绩汇报
	private String qualifiedDate;//转正日期
	private String qualifiedReason;//转正原因：1-正常转正；2-提前转正；3-延迟转正；4-不予转正；
	private String qualifiedReasonState;//转正原因说明
	private Double qualifiedWage;//转正后工资标准
	private Double probationaryWage;//试用期工资标准
	private Double qualifiedAllowance;//转正后岗位津贴
	private Double probationaryAllowance;//试用期岗位津贴
	private Double qualifiedSum;//转正后工资合计
	private Double probationarySum;//试用期工资合计
	private String approveNodeNo;//当前审批节点编号
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//审批角色号/用户号
	private String approvePartName;//当前审批角色/用户名称
	private String approveRemark;//审批说明
	private String approveResult;//审批意见1同意2否决3退回上一环节4退回初审5不同意

	/**
	 * @return ID
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 ID
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 转正申请ID
	 */
	public String getBecomeQualifiedId() {
	 	return becomeQualifiedId;
	}
	/**
	 * @设置 转正申请ID
	 * @param becomeQualifiedId
	 */
	public void setBecomeQualifiedId(String becomeQualifiedId) {
	 	this.becomeQualifiedId = becomeQualifiedId;
	}
	/**
	 * @return 申请日期
	 */
	public String getApplyDate() {
	 	return applyDate;
	}
	/**
	 * @设置 申请日期
	 * @param applyDate
	 */
	public void setApplyDate(String applyDate) {
	 	this.applyDate = applyDate;
	}
	/**
	 * @return 关联基础信息ID
	 */
	public String getBaseId() {
	 	return baseId;
	}
	/**
	 * @设置 关联基础信息ID
	 * @param baseId
	 */
	public void setBaseId(String baseId) {
	 	this.baseId = baseId;
	}
	/**
	 * @return 
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 创建时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 创建时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 创建该申请操作员
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 创建该申请操作员
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 姓名
	 */
	public String getName() {
	 	return name;
	}
	/**
	 * @设置 姓名
	 * @param name
	 */
	public void setName(String name) {
	 	this.name = name;
	}
	/**
	 * @return 入职日期
	 */
	public String getHireDate() {
	 	return hireDate;
	}
	/**
	 * @设置 入职日期
	 * @param hireDate
	 */
	public void setHireDate(String hireDate) {
	 	this.hireDate = hireDate;
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
	 * @return 职级（经理，主任等）
	 */
	public String getPosition() {
	 	return position;
	}
	/**
	 * @设置 职级（经理，主任等）
	 * @param position
	 */
	public void setPosition(String position) {
	 	this.position = position;
	}
	/**
	 * @return 试用期间工作/业绩汇报
	 */
	public String getPerformance() {
	 	return performance;
	}
	/**
	 * @设置 试用期间工作/业绩汇报
	 * @param performance
	 */
	public void setPerformance(String performance) {
	 	this.performance = performance;
	}
	/**
	 * @return 转正日期
	 */
	public String getQualifiedDate() {
	 	return qualifiedDate;
	}
	/**
	 * @设置 转正日期
	 * @param qualifiedDate
	 */
	public void setQualifiedDate(String qualifiedDate) {
	 	this.qualifiedDate = qualifiedDate;
	}
	/**
	 * @return 转正原因：1-正常转正；2-提前转正；3-延迟转正；4-不予转正；
	 */
	public String getQualifiedReason() {
	 	return qualifiedReason;
	}
	/**
	 * @设置 转正原因：1-正常转正；2-提前转正；3-延迟转正；4-不予转正；
	 * @param qualifiedReason
	 */
	public void setQualifiedReason(String qualifiedReason) {
	 	this.qualifiedReason = qualifiedReason;
	}
	/**
	 * @return 转正原因说明
	 */
	public String getQualifiedReasonState() {
	 	return qualifiedReasonState;
	}
	/**
	 * @设置 转正原因说明
	 * @param qualifiedReasonState
	 */
	public void setQualifiedReasonState(String qualifiedReasonState) {
	 	this.qualifiedReasonState = qualifiedReasonState;
	}
	/**
	 * @return 转正后工资标准
	 */
	public Double getQualifiedWage() {
	 	return qualifiedWage;
	}
	/**
	 * @设置 转正后工资标准
	 * @param qualifiedWage
	 */
	public void setQualifiedWage(Double qualifiedWage) {
	 	this.qualifiedWage = qualifiedWage;
	}
	/**
	 * @return 试用期工资标准
	 */
	public Double getProbationaryWage() {
	 	return probationaryWage;
	}
	/**
	 * @设置 试用期工资标准
	 * @param probationaryWage
	 */
	public void setProbationaryWage(Double probationaryWage) {
	 	this.probationaryWage = probationaryWage;
	}
	/**
	 * @return 转正后岗位津贴
	 */
	public Double getQualifiedAllowance() {
	 	return qualifiedAllowance;
	}
	/**
	 * @设置 转正后岗位津贴
	 * @param qualifiedAllowance
	 */
	public void setQualifiedAllowance(Double qualifiedAllowance) {
	 	this.qualifiedAllowance = qualifiedAllowance;
	}
	/**
	 * @return 试用期岗位津贴
	 */
	public Double getProbationaryAllowance() {
	 	return probationaryAllowance;
	}
	/**
	 * @设置 试用期岗位津贴
	 * @param probationaryAllowance
	 */
	public void setProbationaryAllowance(Double probationaryAllowance) {
	 	this.probationaryAllowance = probationaryAllowance;
	}
	/**
	 * @return 转正后工资合计
	 */
	public Double getQualifiedSum() {
	 	return qualifiedSum;
	}
	/**
	 * @设置 转正后工资合计
	 * @param qualifiedSum
	 */
	public void setQualifiedSum(Double qualifiedSum) {
	 	this.qualifiedSum = qualifiedSum;
	}
	/**
	 * @return 试用期工资合计
	 */
	public Double getProbationarySum() {
	 	return probationarySum;
	}
	/**
	 * @设置 试用期工资合计
	 * @param probationarySum
	 */
	public void setProbationarySum(Double probationarySum) {
	 	this.probationarySum = probationarySum;
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
}