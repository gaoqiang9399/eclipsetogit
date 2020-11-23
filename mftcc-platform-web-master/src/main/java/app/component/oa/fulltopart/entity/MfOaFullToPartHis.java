package app.component.oa.fulltopart.entity;
import app.base.BaseDomain;
/**
* Title: MfOaFullToPartHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Dec 19 20:34:57 CST 2017
* @version：1.0
**/
public class MfOaFullToPartHis extends BaseDomain {
	private String id;//ID
	private String fullToPartId;//全职转兼职申请申请ID
	private String applyDate;//申请日期
	private String baseId;//关联基础信息ID
	private String lstModTime;//更新时间
	private String regTime;//创建时间
	private String opNo;//创建该申请操作员
	private String name;//姓名
	private String hireDate;//入职日期
	private String sex;//性别：0-男；1-女；2-未知；
	private String sexShow;//性别展示
	private String brNo;//部门编号
	private String brName;//部门
	private String position;//职务
	private String positionShow;//职务展示
	private String fullToPartState;//全职转兼职说明
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