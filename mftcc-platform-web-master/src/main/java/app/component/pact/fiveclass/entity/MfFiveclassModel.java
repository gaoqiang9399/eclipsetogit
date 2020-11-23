package app.component.pact.fiveclass.entity;
import app.base.BaseDomain;
/**
* Title: MfFiveclassModel.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Mar 10 12:26:58 CST 2017
* @version：1.0
**/
public class MfFiveclassModel extends BaseDomain {
	private String modelId;//主键id
	private String approveFlag;//是否启用审批
	private String allowManualFlag;//是否允许任意调整参数设置，五级分类人工认定调整规则。否-仅允许业务形态向下调整，如关注类贷款不能调整为正常类，只能调整为次级、可疑、或损失类贷款；是-允许业务形态任意调整，如关注类贷款既可以调整为正常类，也可以调整为次级、可疑、或损失类贷款。
	private String processId;//审批流程编号
	private String processName;//审批流程名称
	private String remark;//备注
	private String opNo;//操作员编号，最近一次操作
	private String lstModTime;//最近一次修改时间

	/**
	 * @return 主键id
	 */
	public String getModelId() {
	 	return modelId;
	}
	/**
	 * @设置 主键id
	 * @param modelId
	 */
	public void setModelId(String modelId) {
	 	this.modelId = modelId;
	}
	/**
	 * @return 是否启用审批
	 */
	public String getApproveFlag() {
	 	return approveFlag;
	}
	/**
	 * @设置 是否启用审批
	 * @param approveFlag
	 */
	public void setApproveFlag(String approveFlag) {
	 	this.approveFlag = approveFlag;
	}
	/**
	 * @return 是否允许任意调整参数设置，五级分类人工认定调整规则。否-仅允许业务形态向下调整，如关注类贷款不能调整为正常类，只能调整为次级、可疑、或损失类贷款；是-允许业务形态任意调整，如关注类贷款既可以调整为正常类，也可以调整为次级、可疑、或损失类贷款。
	 */
	public String getAllowManualFlag() {
	 	return allowManualFlag;
	}
	/**
	 * @设置 是否允许任意调整参数设置，五级分类人工认定调整规则。否-仅允许业务形态向下调整，如关注类贷款不能调整为正常类，只能调整为次级、可疑、或损失类贷款；是-允许业务形态任意调整，如关注类贷款既可以调整为正常类，也可以调整为次级、可疑、或损失类贷款。
	 * @param allowManualFlag
	 */
	public void setAllowManualFlag(String allowManualFlag) {
	 	this.allowManualFlag = allowManualFlag;
	}
	/**
	 * @return 审批流程编号
	 */
	public String getProcessId() {
	 	return processId;
	}
	/**
	 * @设置 审批流程编号
	 * @param processId
	 */
	public void setProcessId(String processId) {
	 	this.processId = processId;
	}
	/**
	 * @return 审批流程名称
	 */
	public String getProcessName() {
	 	return processName;
	}
	/**
	 * @设置 审批流程名称
	 * @param processName
	 */
	public void setProcessName(String processName) {
	 	this.processName = processName;
	}
	/**
	 * @return 备注
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 操作员编号，最近一次操作
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员编号，最近一次操作
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 最近一次修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最近一次修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
}