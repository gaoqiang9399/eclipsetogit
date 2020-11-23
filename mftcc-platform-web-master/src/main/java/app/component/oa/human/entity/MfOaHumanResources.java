package app.component.oa.human.entity;
import app.base.BaseDomain;
/**
* Title: MfOaHumanResources.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Dec 14 17:16:09 CST 2017
* @version：1.0
**/
public class MfOaHumanResources extends BaseDomain {
	private static final long serialVersionUID = -6649252876971759813L;
	private String appId;//申请编号
	private String appDepartment;//申请部门
	private String appDate;//申请日期
	private String recruitmentPosition;//招聘职位
	private Integer numOfPeople;//招聘人数
	private String arrivalDate;//希望到岗日期
	private String genderRequirements;//性别要求(显示男人数，女人数，或男女皆可)
	private Integer numGender;//男性人数，女性人数
	private String typeOfDemand;//需求的类别(1.缺员补员;2.离职补员;3.储备人力;4.编制外增加人员;5.临时性任务;)
	private String addReson;//编制外增加人员的理由
	private Integer existNumDepartmet;//部门现有人数
	private String jobDescriptions;//招聘条件
	private String jobSpecification;//任职要求
	private String jobResponsibilities;//工作职责
	private String applySts;//申请状态0未提交1流程中2审批完成3发回重审4补充资料5否决
	private String applyProcessId;//保管审批流程编号
	private String approvePartNo;//当前审批人员编号
	private String approvePartName;//当前审批人员名称
	private String approveNodeNo;//当前审批节点
	private String approveNodeName;//当前审批节点名称
	private String ext1;//预留字段
	private String ext2;//预留字段
	private String ext3;//预留字段
	private String ext4;//预留字段
	private String ext5;//预留字段
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private Integer womanNum;//女性人数
	private String regTime;//登记时间
	
	/**
	 * @return 登记人编号
	 */
	public String getOpNo() {
		return opNo;
	}
	/**
	 * @设置 登记人编号
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
	 * @param op_name
	 */
	public void setOpName(String opName) {
		this.opName = opName;
	}
	/**
	 * @return 申请编号
	 */
	public String getAppId() {
	 	return appId;
	}
	/**
	 * @设置 申请编号
	 * @param appId
	 */
	public void setAppId(String appId) {
	 	this.appId = appId;
	}
	/**
	 * @return 申请部门
	 */
	public String getAppDepartment() {
	 	return appDepartment;
	}
	/**
	 * @设置 申请部门
	 * @param appDepartment
	 */
	public void setAppDepartment(String appDepartment) {
	 	this.appDepartment = appDepartment;
	}
	/**
	 * @return 申请日期
	 */
	public String getAppDate() {
	 	return appDate;
	}
	/**
	 * @设置 申请日期
	 * @param appDate
	 */
	public void setAppDate(String appDate) {
	 	this.appDate = appDate;
	}
	/**
	 * @return 招聘职位
	 */
	public String getRecruitmentPosition() {
	 	return recruitmentPosition;
	}
	/**
	 * @设置 招聘职位
	 * @param recruitmentPosition
	 */
	public void setRecruitmentPosition(String recruitmentPosition) {
	 	this.recruitmentPosition = recruitmentPosition;
	}
	/**
	 * @return 招聘人数
	 */
	public Integer getNumOfPeople() {
	 	return numOfPeople;
	}
	/**
	 * @设置 招聘人数
	 * @param numOfPeople
	 */
	public void setNumOfPeople(Integer numOfPeople) {
	 	this.numOfPeople = numOfPeople;
	}
	/**
	 * @return 希望到岗日期
	 */
	public String getArrivalDate() {
	 	return arrivalDate;
	}
	/**
	 * @设置 希望到岗日期
	 * @param arrivalDate
	 */
	public void setArrivalDate(String arrivalDate) {
	 	this.arrivalDate = arrivalDate;
	}
	/**
	 * @return 性别要求(显示男人数，女人数，或男女皆可)
	 */
	public String getGenderRequirements() {
	 	return genderRequirements;
	}
	/**
	 * @设置 性别要求(显示男人数，女人数，或男女皆可)
	 * @param genderRequirements
	 */
	public void setGenderRequirements(String genderRequirements) {
	 	this.genderRequirements = genderRequirements;
	}
	/**
	 * @return 人数(显示男人数，女人数)
	 */
	public Integer getNumGender() {
		return numGender;
	}
	/**
	 * @设置 人数(男人数，女人数)
	 * @param numGender
	 */
	public void setNumGender(Integer numGender) {
		this.numGender = numGender;
	}
	/**
	 * @return 需求的类别(1.缺员补员;2.离职补员;3.储备人力;4.编制外增加人员;5.临时性任务;)
	 */
	public String getTypeOfDemand() {
	 	return typeOfDemand;
	}
	/**
	 * @设置 需求的类别(1.缺员补员;2.离职补员;3.储备人力;4.编制外增加人员;5.临时性任务;)
	 * @param typeOfDemand
	 */
	public void setTypeOfDemand(String typeOfDemand) {
	 	this.typeOfDemand = typeOfDemand;
	}
	/**
	 * @return 编制外增加人员的理由
	 */
	public String getAddReson() {
	 	return addReson;
	}
	/**
	 * @设置 编制外增加人员的理由
	 * @param addReson
	 */
	public void setAddReson(String addReson) {
	 	this.addReson = addReson;
	}
	/**
	 * @return 部门现有人数
	 */
	public Integer getExistNumDepartmet() {
	 	return existNumDepartmet;
	}
	/**
	 * @设置 部门现有人数
	 * @param existNumDepartmet
	 */
	public void setExistNumDepartmet(Integer existNumDepartmet) {
	 	this.existNumDepartmet = existNumDepartmet;
	}
	/**
	 * @return 招聘条件
	 */
	public String getJobDescriptions() {
	 	return jobDescriptions;
	}
	/**
	 * @设置 招聘条件
	 * @param jobDescriptions
	 */
	public void setJobDescriptions(String jobDescriptions) {
	 	this.jobDescriptions = jobDescriptions;
	}
	/**
	 * @return 任职要求
	 */
	public String getJobSpecification() {
	 	return jobSpecification;
	}
	/**
	 * @设置 任职要求
	 * @param jobSpecification
	 */
	public void setJobSpecification(String jobSpecification) {
	 	this.jobSpecification = jobSpecification;
	}
	/**
	 * @return 工作职责
	 */
	public String getJobResponsibilities() {
	 	return jobResponsibilities;
	}
	/**
	 * @设置 工作职责
	 * @param jobResponsibilities
	 */
	public void setJobResponsibilities(String jobResponsibilities) {
	 	this.jobResponsibilities = jobResponsibilities;
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
	 * @return 预留字段
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 预留字段
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 预留字段
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 预留字段
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 预留字段
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 预留字段
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 预留字段
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 预留字段
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return 预留字段
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 预留字段
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	/**
	 * @return 女性人数
	 */
	public Integer getWomanNum() {
		return womanNum;
	}
	/**
	 * @设置 女性人数
	 * @param womanNum
	 */
	public void setWomanNum(Integer womanNum) {
		this.womanNum = womanNum;
	}
	/**
	 * @return regTime 
	 */
	public String getRegTime() {
		return regTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	
	
}