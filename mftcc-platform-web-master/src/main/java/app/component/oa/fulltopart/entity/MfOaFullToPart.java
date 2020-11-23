package app.component.oa.fulltopart.entity;
import app.base.BaseDomain;
/**
* Title: MfOaFullToPart.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Dec 19 19:46:17 CST 2017
* @version：1.0
**/
public class MfOaFullToPart extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String fullToPartId;//全职转兼职申请申请ID
	private String applyDate;//申请日期
	private String baseId;//关联基础信息ID
	private String lstModTime;//更新时间
	private String regTime;//创建时间
	private String opNo;//创建该申请操作员
	private String opName;//创建该申请操作员
	private String name;//姓名
	private String hireDate;//入职日期
	private String sex;//性别：0-男；1-女；2-未知；
	private String sexShow;//性别展示
	private String brNo;//部门编号
	private String brName;//部门
	private String position;//职务
	private String positionShow;//职务展示
	private String fullToPartState;//全职转兼职说明
	private String applySts;//申请状态0未提交1流程中2审批完成3发回重审4补充资料5否决
	private String applyProcessId;//保管审批流程编号
	private String approveNodeNo;//当前审批节点
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//当前审批人员编号
	private String approvePartName;//当前审批人员名称

	/**
	 * @return 全职转兼职申请申请ID
	 */
	public String getFullToPartId() {
	 	return fullToPartId;
	}
	/**
	 * @设置 全职转兼职申请申请ID
	 * @param fullToPartId
	 */
	public void setFullToPartId(String fullToPartId) {
	 	this.fullToPartId = fullToPartId;
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
	 * @return 更新时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 更新时间
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
	 * @return 性别：0-男；1-女；2-未知；
	 */
	public String getSex() {
	 	return sex;
	}
	/**
	 * @设置 性别：0-男；1-女；2-未知；
	 * @param sex
	 */
	public void setSex(String sex) {
	 	this.sex = sex;
	}
	/**
	 * @return 性别展示
	 */
	public String getSexShow() {
	 	return sexShow;
	}
	/**
	 * @设置 性别展示
	 * @param sexShow
	 */
	public void setSexShow(String sexShow) {
	 	this.sexShow = sexShow;
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
	 * @return 部门
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 部门
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 职务
	 */
	public String getPosition() {
	 	return position;
	}
	/**
	 * @设置 职务
	 * @param position
	 */
	public void setPosition(String position) {
	 	this.position = position;
	}
	/**
	 * @return 职务展示
	 */
	public String getPositionShow() {
	 	return positionShow;
	}
	/**
	 * @设置 职务展示
	 * @param positionShow
	 */
	public void setPositionShow(String positionShow) {
	 	this.positionShow = positionShow;
	}
	/**
	 * @return 全职转兼职说明
	 */
	public String getFullToPartState() {
	 	return fullToPartState;
	}
	/**
	 * @设置 全职转兼职说明
	 * @param fullToPartState
	 */
	public void setFullToPartState(String fullToPartState) {
	 	this.fullToPartState = fullToPartState;
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
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
}