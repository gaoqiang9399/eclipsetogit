package app.component.oa.adjustment.entity;
import app.base.BaseDomain;
/**
* Title: MfOaAdjustmentHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Dec 18 21:08:48 CST 2017
* @version：1.0
**/
public class MfOaAdjustmentHis extends BaseDomain {
	private String id;//ID
	private String adjustmentId;//调薪调岗申请ID
	private String applyDate;//申请日期
	private String baseId;//关联基础信息ID
	private String lstModTime;//
	private String regTime;//创建时间
	private String opNo;//创建该申请操作员
	private String name;//姓名
	private String hireDate;//入职日期
	private String takeEffectDate;//生效日期
	private String brNo;//原任部门编号
	private String brName;//原任部门
	private String position;//原任职务
	private String positionShow;//原任职务展示
	private String newBrNo;//拟任部门编号
	private String newBrName;//拟任部门
	private String newPosition;//新职务
	private String newPositionShow;//新职务展示
	private String adjustmentReason;//异动原因
	private String workHandover;//工作交接情况
	private Double afterAdjustmentWage;//调整后工资标准
	private Double beforeAdjustmentWage;//调整前工资标准
	private Double afterAdjustmentAllowance;//调整后岗位津贴
	private Double beforeAdjustmentAllowance;//调整前岗位津贴
	private Double afterAdjustmentSum;//调整后工资合计
	private Double beforeAdjustmentSum;//调整前工资合计
	private String adjustmentType;//职务/职级调整：1-调资；2-调岗；3-晋职；4-降职；5-晋级；6-降级；
	private String ifThroughNewBr;//是否通过新部门审批：1-是；0-否；
	private String adjustmentWageReason;//调薪原因
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
	 * @return 调薪调岗申请ID
	 */
	public String getAdjustmentId() {
	 	return adjustmentId;
	}
	/**
	 * @设置 调薪调岗申请ID
	 * @param adjustmentId
	 */
	public void setAdjustmentId(String adjustmentId) {
	 	this.adjustmentId = adjustmentId;
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
	 * @return 生效日期
	 */
	public String getTakeEffectDate() {
	 	return takeEffectDate;
	}
	/**
	 * @设置 生效日期
	 * @param takeEffectDate
	 */
	public void setTakeEffectDate(String takeEffectDate) {
	 	this.takeEffectDate = takeEffectDate;
	}
	/**
	 * @return 原任部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 原任部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 原任部门
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 原任部门
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 原任职务
	 */
	public String getPosition() {
	 	return position;
	}
	/**
	 * @设置 原任职务
	 * @param position
	 */
	public void setPosition(String position) {
	 	this.position = position;
	}
	/**
	 * @return 原任职务展示
	 */
	public String getPositionShow() {
	 	return positionShow;
	}
	/**
	 * @设置 原任职务展示
	 * @param positionShow
	 */
	public void setPositionShow(String positionShow) {
	 	this.positionShow = positionShow;
	}
	/**
	 * @return 拟任部门编号
	 */
	public String getNewBrNo() {
	 	return newBrNo;
	}
	/**
	 * @设置 拟任部门编号
	 * @param newBrNo
	 */
	public void setNewBrNo(String newBrNo) {
	 	this.newBrNo = newBrNo;
	}
	/**
	 * @return 拟任部门
	 */
	public String getNewBrName() {
	 	return newBrName;
	}
	/**
	 * @设置 拟任部门
	 * @param newBrName
	 */
	public void setNewBrName(String newBrName) {
	 	this.newBrName = newBrName;
	}
	/**
	 * @return 新职务
	 */
	public String getNewPosition() {
	 	return newPosition;
	}
	/**
	 * @设置 新职务
	 * @param newPosition
	 */
	public void setNewPosition(String newPosition) {
	 	this.newPosition = newPosition;
	}
	/**
	 * @return 新职务展示
	 */
	public String getNewPositionShow() {
	 	return newPositionShow;
	}
	/**
	 * @设置 新职务展示
	 * @param newPositionShow
	 */
	public void setNewPositionShow(String newPositionShow) {
	 	this.newPositionShow = newPositionShow;
	}
	/**
	 * @return 异动原因
	 */
	public String getAdjustmentReason() {
	 	return adjustmentReason;
	}
	/**
	 * @设置 异动原因
	 * @param adjustmentReason
	 */
	public void setAdjustmentReason(String adjustmentReason) {
	 	this.adjustmentReason = adjustmentReason;
	}
	/**
	 * @return 工作交接情况
	 */
	public String getWorkHandover() {
	 	return workHandover;
	}
	/**
	 * @设置 工作交接情况
	 * @param workHandover
	 */
	public void setWorkHandover(String workHandover) {
	 	this.workHandover = workHandover;
	}
	/**
	 * @return 调整后工资标准
	 */
	public Double getAfterAdjustmentWage() {
	 	return afterAdjustmentWage;
	}
	/**
	 * @设置 调整后工资标准
	 * @param afterAdjustmentWage
	 */
	public void setAfterAdjustmentWage(Double afterAdjustmentWage) {
	 	this.afterAdjustmentWage = afterAdjustmentWage;
	}
	/**
	 * @return 调整前工资标准
	 */
	public Double getBeforeAdjustmentWage() {
	 	return beforeAdjustmentWage;
	}
	/**
	 * @设置 调整前工资标准
	 * @param beforeAdjustmentWage
	 */
	public void setBeforeAdjustmentWage(Double beforeAdjustmentWage) {
	 	this.beforeAdjustmentWage = beforeAdjustmentWage;
	}
	/**
	 * @return 调整后岗位津贴
	 */
	public Double getAfterAdjustmentAllowance() {
	 	return afterAdjustmentAllowance;
	}
	/**
	 * @设置 调整后岗位津贴
	 * @param afterAdjustmentAllowance
	 */
	public void setAfterAdjustmentAllowance(Double afterAdjustmentAllowance) {
	 	this.afterAdjustmentAllowance = afterAdjustmentAllowance;
	}
	/**
	 * @return 调整前岗位津贴
	 */
	public Double getBeforeAdjustmentAllowance() {
	 	return beforeAdjustmentAllowance;
	}
	/**
	 * @设置 调整前岗位津贴
	 * @param beforeAdjustmentAllowance
	 */
	public void setBeforeAdjustmentAllowance(Double beforeAdjustmentAllowance) {
	 	this.beforeAdjustmentAllowance = beforeAdjustmentAllowance;
	}
	/**
	 * @return 调整后工资合计
	 */
	public Double getAfterAdjustmentSum() {
	 	return afterAdjustmentSum;
	}
	/**
	 * @设置 调整后工资合计
	 * @param afterAdjustmentSum
	 */
	public void setAfterAdjustmentSum(Double afterAdjustmentSum) {
	 	this.afterAdjustmentSum = afterAdjustmentSum;
	}
	/**
	 * @return 调整前工资合计
	 */
	public Double getBeforeAdjustmentSum() {
	 	return beforeAdjustmentSum;
	}
	/**
	 * @设置 调整前工资合计
	 * @param beforeAdjustmentSum
	 */
	public void setBeforeAdjustmentSum(Double beforeAdjustmentSum) {
	 	this.beforeAdjustmentSum = beforeAdjustmentSum;
	}
	/**
	 * @return 职务/职级调整：1-调资；2-调岗；3-晋职；4-降职；5-晋级；6-降级；
	 */
	public String getAdjustmentType() {
	 	return adjustmentType;
	}
	/**
	 * @设置 职务/职级调整：1-调资；2-调岗；3-晋职；4-降职；5-晋级；6-降级；
	 * @param adjustmentType
	 */
	public void setAdjustmentType(String adjustmentType) {
	 	this.adjustmentType = adjustmentType;
	}
	/**
	 * @return 是否通过新部门审批：1-是；0-否；
	 */
	public String getIfThroughNewBr() {
	 	return ifThroughNewBr;
	}
	/**
	 * @设置 是否通过新部门审批：1-是；0-否；
	 * @param ifThroughNewBr
	 */
	public void setIfThroughNewBr(String ifThroughNewBr) {
	 	this.ifThroughNewBr = ifThroughNewBr;
	}
	/**
	 * @return 调薪原因
	 */
	public String getAdjustmentWageReason() {
	 	return adjustmentWageReason;
	}
	/**
	 * @设置 调薪原因
	 * @param adjustmentWageReason
	 */
	public void setAdjustmentWageReason(String adjustmentWageReason) {
	 	this.adjustmentWageReason = adjustmentWageReason;
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