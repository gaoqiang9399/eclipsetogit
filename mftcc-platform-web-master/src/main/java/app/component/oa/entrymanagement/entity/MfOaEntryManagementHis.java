package app.component.oa.entrymanagement.entity;
import app.base.BaseDomain;
/**
* Title: MfOaEntryManagementHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Dec 14 17:13:47 CST 2017
* @version：1.0
**/
public class MfOaEntryManagementHis extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String id;//
	private String entryManagementId;//申请编号
	private String approveNodeNo;//当前审批节点编号
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//审批角色号/用户号
	private String approvePartName;//当前审批角色/用户名称
	private String approveRemark;//审批说明
	private String approveResult;//审批意见1同意2否决3退回上一环节4退回初审5不同意
	private String regTime;//登记时间

	/**
	 * @return 
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 申请编号
	 */
	public String getEntryManagementId() {
	 	return entryManagementId;
	}
	/**
	 * @设置 申请编号
	 * @param entryManagementId
	 */
	public void setEntryManagementId(String entryManagementId) {
	 	this.entryManagementId = entryManagementId;
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
}