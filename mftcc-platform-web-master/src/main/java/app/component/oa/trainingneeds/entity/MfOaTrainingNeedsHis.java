package app.component.oa.trainingneeds.entity;
import app.base.BaseDomain;
/**
* Title: MfOaTrainingNeedsHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Dec 20 14:21:54 CST 2017
* @version：1.0
**/
public class MfOaTrainingNeedsHis extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String id;//ID
	private String trainingNeedsId;//培训需求申请申请ID
	private String applyDate;//申请日期
	private String lstModTime;//更新时间
	private String regTime;//创建时间
	private String opNo;//创建该申请操作员编号
	private String opName;//创建该申请操作员
	private String trainingNeedsType;//培训类型
	private String title;//培训标题
	private String content;//培训内容
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
	public String getTrainingNeedsType() {
		return trainingNeedsType;
	}
	public void setTrainingNeedsType(String trainingNeedsType) {
		this.trainingNeedsType = trainingNeedsType;
	}
}