package app.component.oa.entrymanagement.entity;
import app.base.BaseDomain;
/**
* Title: MfOaEntryManagement.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Dec 12 11:59:03 CST 2017
* @version：1.0
**/
public class MfOaEntryManagement extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String entryManagementId;//入职申请ID
	private String applyDate;//申请日期
	private String state;//说明
	private String baseId;//关联基础信息ID
	private String lstModTime;//
	private String regTime;//创建时间
	private String opNo;//创建该申请操作员
	private String opNoName;//操作员姓名
	private String opSts;//申请在职状态
	private String applySts;//申请状态0未提交1流程中2审批完成3发回重审4补充资料5否决
	private String applyProcessId;//保管审批流程编号
	private String approveNodeNo;//当前审批节点
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//当前审批人员编号
	private String approvePartName;//当前审批人员名称
	private String opName;//姓名
	private String sex;//性别
	private String idNum;//身份证号
	private String birthday;//出生日期
	private String tel;//手机
	private String email;//出生日期
	private String brNo;//部门编号
	private String brName;//部门名称
	private String position;//职级（经理，主任等）
	private Double wage;//薪水
	private Double probationaryWage;//试用期待遇

	/**
	 * @return 入职申请ID
	 */
	public String getEntryManagementId() {
	 	return entryManagementId;
	}
	/**
	 * @设置 入职申请ID
	 * @param entryManagementId
	 */
	public void setEntryManagementId(String entryManagementId) {
	 	this.entryManagementId = entryManagementId;
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
	 * @return 说明
	 */
	public String getState() {
	 	return state;
	}
	/**
	 * @设置 说明
	 * @param state
	 */
	public void setState(String state) {
	 	this.state = state;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getOpSts() {
		return opSts;
	}
	public void setOpSts(String opSts) {
		this.opSts = opSts;
	}
	public Double getWage() {
		return wage;
	}
	public void setWage(Double wage) {
		this.wage = wage;
	}
	public Double getProbationaryWage() {
		return probationaryWage;
	}
	public void setProbationaryWage(Double probationaryWage) {
		this.probationaryWage = probationaryWage;
	}
	public String getOpNoName() {
		return opNoName;
	}
	public void setOpNoName(String opNoName) {
		this.opNoName = opNoName;
	}
}