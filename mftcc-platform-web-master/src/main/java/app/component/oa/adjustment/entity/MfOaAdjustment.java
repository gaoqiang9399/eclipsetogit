package app.component.oa.adjustment.entity;
import app.base.BaseDomain;
/**
* Title: MfOaAdjustment.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Dec 18 14:06:51 CST 2017
* @version：1.0
**/
public class MfOaAdjustment extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String adjustmentId;//调薪调岗申请ID
	private String applyDate;//申请日期
	private String baseId;//关联基础信息ID
	private String lstModTime;//
	private String regTime;//创建时间
	private String opNo;//创建该申请操作员
	private String opName;//创建该申请操作员
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
	private String ifThroughNewBr;//是否通过新部门审批：1-通过；0-不通过；
	private String adjustmentWageReason;//调薪原因
	private String applySts;//申请状态0未提交1流程中2审批完成3发回重审4补充资料5否决
	private String applyProcessId;//保管审批流程编号
	private String approveNodeNo;//当前审批节点
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//当前审批人员编号
	private String approvePartName;//当前审批人员名称

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
	 * @return 申请状态0未提交1流程中2审批完成3发回重审4补充资料5否决
	 */
	public String getApplySts() {
	 	return applySts;
	}
	/**
	 * @设置 申请状态0未提交1流程中2审批完成3发回重审4补充资料5否决
	 * @param applySts
	 */
	public void setApplySts(String applySts) {
	 	this.applySts = applySts;
	}
	/**
	 * @return 保管审批流程编号
	 */
	public String getApplyProcessId() {
	 	return applyProcessId;
	}
	/**
	 * @设置 保管审批流程编号
	 * @param applyProcessId
	 */
	public void setApplyProcessId(String applyProcessId) {
	 	this.applyProcessId = applyProcessId;
	}
	/**
	 * @return 当前审批节点
	 */
	public String getApproveNodeNo() {
	 	return approveNodeNo;
	}
	/**
	 * @设置 当前审批节点
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
	 * @return 当前审批人员编号
	 */
	public String getApprovePartNo() {
	 	return approvePartNo;
	}
	/**
	 * @设置 当前审批人员编号
	 * @param approvePartNo
	 */
	public void setApprovePartNo(String approvePartNo) {
	 	this.approvePartNo = approvePartNo;
	}
	/**
	 * @return 当前审批人员名称
	 */
	public String getApprovePartName() {
	 	return approvePartName;
	}
	/**
	 * @设置 当前审批人员名称
	 * @param approvePartName
	 */
	public void setApprovePartName(String approvePartName) {
	 	this.approvePartName = approvePartName;
	}
	public String getIfThroughNewBr() {
		return ifThroughNewBr;
	}
	public void setIfThroughNewBr(String ifThroughNewBr) {
		this.ifThroughNewBr = ifThroughNewBr;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
}