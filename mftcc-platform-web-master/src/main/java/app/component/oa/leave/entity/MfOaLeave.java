package app.component.oa.leave.entity;
import app.base.BaseDomain;
/**
* Title: MfOaLeave.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Dec 17 09:27:31 CST 2016
* @version：1.0
**/
public class MfOaLeave extends BaseDomain {
	private String leaveNo;//请假单编号
	private String brNo;//部门编号
	private String brName;//部门名称
	private String opNo;//请假人编号
	private String opName;//请假人名称
	private String leaveType;//请假类型 0 丧假, 1 病假,2 倒休假, 3 产假, 4 婚假 ,5 探亲假 ,6 带薪年假,7 事假'
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String timeSum;//请假时长
	private String leaveReason;//请假事由
	private String createTime;//创建时间
	private String leaveSts;//状态 0未提交，1 审批中，2 审批通过，3不通过
	private String approvePartName;//初审的操作员(不存入数据库)
	private String approvalNodeName;//审批环节
	private String lstModTime;//最后一次修改时间

	/**
	 * @return 请假单编号
	 */
	public String getLeaveNo() {
	 	return leaveNo;
	}
	/**
	 * @设置 请假单编号
	 * @param leaveNo
	 */
	public void setLeaveNo(String leaveNo) {
	 	this.leaveNo = leaveNo;
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
	 * @return 请假人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 请假人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 请假人名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 请假人名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 请假类型 0 丧假, 1 病假,2 倒休假, 3 产假, 4 婚假 ,5 探亲假 ,6 带薪年假,7 事假'
	 */
	public String getLeaveType() {
	 	return leaveType;
	}
	/**
	 * @设置 请假类型 0 丧假, 1 病假,2 倒休假, 3 产假, 4 婚假 ,5 探亲假 ,6 带薪年假,7 事假'
	 * @param leaveType
	 */
	public void setLeaveType(String leaveType) {
	 	this.leaveType = leaveType;
	}
	/**
	 * @return 开始时间
	 */
	public String getStartTime() {
	 	return startTime;
	}
	/**
	 * @设置 开始时间
	 * @param startTime
	 */
	public void setStartTime(String startTime) {
	 	this.startTime = startTime;
	}
	/**
	 * @return 结束时间
	 */
	public String getEndTime() {
	 	return endTime;
	}
	/**
	 * @设置 结束时间
	 * @param endTime
	 */
	public void setEndTime(String endTime) {
	 	this.endTime = endTime;
	}
	/**
	 * @return 请假时长
	 */
	public String getTimeSum() {
	 	return timeSum;
	}
	/**
	 * @设置 请假时长
	 * @param timeSum
	 */
	public void setTimeSum(String timeSum) {
	 	this.timeSum = timeSum;
	}
	/**
	 * @return 请假事由
	 */
	public String getLeaveReason() {
	 	return leaveReason;
	}
	/**
	 * @设置 请假事由
	 * @param leaveReason
	 */
	public void setLeaveReason(String leaveReason) {
	 	this.leaveReason = leaveReason;
	}
	/**
	 * @return 创建时间
	 */
	public String getCreateTime() {
	 	return createTime;
	}
	/**
	 * @设置 创建时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
	 	this.createTime = createTime;
	}
	/**
	 * @return 状态 0未提交，1 审批中，2 审批通过，3不通过
	 */
	public String getLeaveSts() {
	 	return leaveSts;
	}
	/**
	 * @设置 状态 0未提交，1 审批中，2 审批通过，3不通过
	 * @param leaveSts
	 */
	public void setLeaveSts(String leaveSts) {
	 	this.leaveSts = leaveSts;
	}	
	/**
	 * @return 审批环节
	 */
	public String getApprovalNodeName() {
	 	return approvalNodeName;
	}
	/**
	 * @设置 审批环节
	 * @param approvalNodeName
	 */
	public void setApprovalNodeName(String approvalNodeName) {
	 	this.approvalNodeName = approvalNodeName;
	}
	/**
	 * @return 最后一次修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后一次修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	public String getApprovePartName() {
		return approvePartName;
	}
	public void setApprovePartName(String approvePartName) {
		this.approvePartName = approvePartName;
	}
	
	
}