package app.component.oa.mattersreported.entity;
import app.base.BaseDomain;
/**
* Title: MfOaMattersReported.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Dec 20 12:05:06 CST 2017
* @version：1.0
**/
public class MfOaMattersReported extends BaseDomain {
	private String mattersReportedId;//呈报编号
	private String itemCategory;//事项类别(客户手动新增类别信息)
	private String itemTitle;//事项标题
	private String itemContent;//事项内容
	private String submitJobs;//呈报岗位/人员
	private String reportingTime;//呈报时间
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String regTime;//登记时间
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

	/**
	 * @return 呈报编号
	 */
	public String getMattersReportedId() {
	 	return mattersReportedId;
	}
	/**
	 * @设置 呈报编号
	 * @param mattersReportedId
	 */
	public void setMattersReportedId(String mattersReportedId) {
	 	this.mattersReportedId = mattersReportedId;
	}
	/**
	 * @return 事项类别(客户手动新增类别信息)
	 */
	public String getItemCategory() {
	 	return itemCategory;
	}
	/**
	 * @设置 事项类别(客户手动新增类别信息)
	 * @param itemCategory
	 */
	public void setItemCategory(String itemCategory) {
	 	this.itemCategory = itemCategory;
	}
	/**
	 * @return 事项标题
	 */
	public String getItemTitle() {
	 	return itemTitle;
	}
	/**
	 * @设置 事项标题
	 * @param itemTitle
	 */
	public void setItemTitle(String itemTitle) {
	 	this.itemTitle = itemTitle;
	}
	/**
	 * @return 事项内容
	 */
	public String getItemContent() {
	 	return itemContent;
	}
	/**
	 * @设置 事项内容
	 * @param itemContent
	 */
	public void setItemContent(String itemContent) {
	 	this.itemContent = itemContent;
	}
	/**
	 * @return 呈报岗位/人员
	 */
	public String getSubmitJobs() {
	 	return submitJobs;
	}
	/**
	 * @设置 呈报岗位/人员
	 * @param submitJobs
	 */
	public void setSubmitJobs(String submitJobs) {
	 	this.submitJobs = submitJobs;
	}
	/**
	 * @return 呈报时间
	 */
	public String getReportingTime() {
	 	return reportingTime;
	}
	/**
	 * @设置 呈报时间
	 * @param reportingTime
	 */
	public void setReportingTime(String reportingTime) {
	 	this.reportingTime = reportingTime;
	}
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
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记时间
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
}