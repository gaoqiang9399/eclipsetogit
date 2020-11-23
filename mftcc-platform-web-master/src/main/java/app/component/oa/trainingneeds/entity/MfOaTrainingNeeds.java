package app.component.oa.trainingneeds.entity;
import app.base.BaseDomain;
/**
* Title: MfOaTrainingNeeds.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Dec 20 14:21:16 CST 2017
* @version：1.0
**/
public class MfOaTrainingNeeds extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String trainingNeedsId;//培训需求申请申请ID
	private String applyDate;//申请日期
	private String lstModTime;//更新时间
	private String regTime;//创建时间
	private String opNo;//创建该申请操作员编号
	private String opName;//创建该申请操作员
	private String trainingNeedsType;//培训类型
	private String title;//培训标题
	private String content;//培训内容
	private String applySts;//申请状态0未提交1流程中2审批完成3发回重审4补充资料5否决
	private String applyProcessId;//保管审批流程编号
	private String approveNodeNo;//当前审批节点
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//当前审批人员编号
	private String approvePartName;//当前审批人员名称

	/**
	 * @return 培训需求申请申请ID
	 */
	public String getTrainingNeedsId() {
	 	return trainingNeedsId;
	}
	/**
	 * @设置 培训需求申请申请ID
	 * @param trainingNeedsId
	 */
	public void setTrainingNeedsId(String trainingNeedsId) {
	 	this.trainingNeedsId = trainingNeedsId;
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
	 * @return 创建该申请操作员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 创建该申请操作员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 创建该申请操作员
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 创建该申请操作员
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 培训标题
	 */
	public String getTitle() {
	 	return title;
	}
	/**
	 * @设置 培训标题
	 * @param title
	 */
	public void setTitle(String title) {
	 	this.title = title;
	}
	/**
	 * @return 培训内容
	 */
	public String getContent() {
	 	return content;
	}
	/**
	 * @设置 培训内容
	 * @param content
	 */
	public void setContent(String content) {
	 	this.content = content;
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
	public String getTrainingNeedsType() {
		return trainingNeedsType;
	}
	public void setTrainingNeedsType(String trainingNeedsType) {
		this.trainingNeedsType = trainingNeedsType;
	}
}