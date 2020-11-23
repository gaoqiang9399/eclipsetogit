package app.component.checkoff.entity;
import app.base.BaseDomain;
/**
* Title: MfBusCheckoffHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Aug 18 11:26:41 CST 2017
* @version：1.0
**/
public class MfBusCheckoffHis extends BaseDomain {
	private String hisId;//业务流水号
	private String checkoffId;//关联核销流水号
	private String fincId;//借据号
	private String fincShowId;//借据号
	private String pactId;//合同号
	private String pactNo;//合同展示号
	private String cusNo;//客户号
	private String cusName;//客户名称
	private Double checkoffPrcp;//核销本金
	private Double checkoffIntst;//核销利息(利息+罚息)
	private Double checkoffFee;//核销费用
	private Double checkoffSum;//核销金额合计
	private String checkoffDate;//核销时间
	private String checkoffType;//核销类型 1-本金核销 2-利息核销 3--全部核销
	private String checkoffMethod;//核销方式  1-收回现金 2-以物抵贷 3-贷款核销 4-其他方式
	private String checkoffReason;//核销原因
	private String regTime;//登记时间  yyyyMMdd HH:mm
	private String regDate;//登记日期  yyyyMMdd
	private String lstModDate;//最后修改日期 yyyyMMdd
	private String lstModTime;//最后修改时间   yyyyMMdd HH:mm
	private String checkoffStatus;//核销状态 1-未提交 2-审批中 3-待核销 4-已核销 5-已收回
	private String opNo;//登记人编号
	private String opName;//登记人名称
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String approveProcessId;//审批流程id
	private String approveProcess;//审批流程
	private String approveNodeNo;//当前审批节点编号
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//审批角色号/用户号
	private String approvePartName;//当前审批角色/用户名称
	private String approveRemark;//审批说明
	private String approveResult;//审批意见1同意2否决3退回上一环节4退回初审5不同意

	/**
	 * @return 业务流水号
	 */
	public String getHisId() {
	 	return hisId;
	}
	/**
	 * @设置 业务流水号
	 * @param hisId
	 */
	public void setHisId(String hisId) {
	 	this.hisId = hisId;
	}
	/**
	 * @return 关联核销流水号
	 */
	public String getCheckoffId() {
	 	return checkoffId;
	}
	/**
	 * @设置 关联核销流水号
	 * @param checkoffId
	 */
	public void setCheckoffId(String checkoffId) {
	 	this.checkoffId = checkoffId;
	}
	/**
	 * @return 借据号
	 */
	public String getFincId() {
	 	return fincId;
	}
	/**
	 * @设置 借据号
	 * @param fincId
	 */
	public void setFincId(String fincId) {
	 	this.fincId = fincId;
	}
	/**
	 * @return 合同号
	 */
	public String getPactId() {
	 	return pactId;
	}
	/**
	 * @设置 合同号
	 * @param pactId
	 */
	public void setPactId(String pactId) {
	 	this.pactId = pactId;
	}
	/**
	 * @return 合同展示号
	 */
	public String getPactNo() {
	 	return pactNo;
	}
	/**
	 * @设置 合同展示号
	 * @param pactNo
	 */
	public void setPactNo(String pactNo) {
	 	this.pactNo = pactNo;
	}
	/**
	 * @return 客户号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户号
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 客户名称
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 客户名称
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 核销本金
	 */
	public Double getCheckoffPrcp() {
	 	return checkoffPrcp;
	}
	/**
	 * @设置 核销本金
	 * @param checkoffPrcp
	 */
	public void setCheckoffPrcp(Double checkoffPrcp) {
	 	this.checkoffPrcp = checkoffPrcp;
	}
	/**
	 * @return 核销利息(利息+罚息)
	 */
	public Double getCheckoffIntst() {
	 	return checkoffIntst;
	}
	/**
	 * @设置 核销利息(利息+罚息)
	 * @param checkoffIntst
	 */
	public void setCheckoffIntst(Double checkoffIntst) {
	 	this.checkoffIntst = checkoffIntst;
	}
	/**
	 * @return 核销费用
	 */
	public Double getCheckoffFee() {
	 	return checkoffFee;
	}
	/**
	 * @设置 核销费用
	 * @param checkoffFee
	 */
	public void setCheckoffFee(Double checkoffFee) {
	 	this.checkoffFee = checkoffFee;
	}
	/**
	 * @return 核销金额合计
	 */
	public Double getCheckoffSum() {
	 	return checkoffSum;
	}
	/**
	 * @设置 核销金额合计
	 * @param checkoffSum
	 */
	public void setCheckoffSum(Double checkoffSum) {
	 	this.checkoffSum = checkoffSum;
	}
	/**
	 * @return 核销时间
	 */
	public String getCheckoffDate() {
	 	return checkoffDate;
	}
	/**
	 * @设置 核销时间
	 * @param checkoffDate
	 */
	public void setCheckoffDate(String checkoffDate) {
	 	this.checkoffDate = checkoffDate;
	}
	/**
	 * @return 核销类型 1-本金核销 2-利息核销 3--全部核销
	 */
	public String getCheckoffType() {
	 	return checkoffType;
	}
	/**
	 * @设置 核销类型 1-本金核销 2-利息核销 3--全部核销
	 * @param checkoffType
	 */
	public void setCheckoffType(String checkoffType) {
	 	this.checkoffType = checkoffType;
	}
	/**
	 * @return 核销方式  1-收回现金 2-以物抵贷 3-贷款核销 4-其他方式
	 */
	public String getCheckoffMethod() {
	 	return checkoffMethod;
	}
	/**
	 * @设置 核销方式  1-收回现金 2-以物抵贷 3-贷款核销 4-其他方式
	 * @param checkoffMethod
	 */
	public void setCheckoffMethod(String checkoffMethod) {
	 	this.checkoffMethod = checkoffMethod;
	}
	/**
	 * @return 核销原因
	 */
	public String getCheckoffReason() {
	 	return checkoffReason;
	}
	/**
	 * @设置 核销原因
	 * @param checkoffReason
	 */
	public void setCheckoffReason(String checkoffReason) {
	 	this.checkoffReason = checkoffReason;
	}
	/**
	 * @return 登记时间  yyyyMMdd HH:mm
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间  yyyyMMdd HH:mm
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 登记日期  yyyyMMdd
	 */
	public String getRegDate() {
	 	return regDate;
	}
	/**
	 * @设置 登记日期  yyyyMMdd
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
	 	this.regDate = regDate;
	}
	/**
	 * @return 最后修改日期 yyyyMMdd
	 */
	public String getLstModDate() {
	 	return lstModDate;
	}
	/**
	 * @设置 最后修改日期 yyyyMMdd
	 * @param lstModDate
	 */
	public void setLstModDate(String lstModDate) {
	 	this.lstModDate = lstModDate;
	}
	/**
	 * @return 最后修改时间   yyyyMMdd HH:mm
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间   yyyyMMdd HH:mm
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 核销状态 1-未提交 2-审批中 3-待核销 4-已核销 5-已收回
	 */
	public String getCheckoffStatus() {
	 	return checkoffStatus;
	}
	/**
	 * @设置 核销状态 1-未提交 2-审批中 3-待核销 4-已核销 5-已收回
	 * @param checkoffStatus
	 */
	public void setCheckoffStatus(String checkoffStatus) {
	 	this.checkoffStatus = checkoffStatus;
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
	 * @return 登记人名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 审批流程id
	 */
	public String getApproveProcessId() {
	 	return approveProcessId;
	}
	/**
	 * @设置 审批流程id
	 * @param approveProcessId
	 */
	public void setApproveProcessId(String approveProcessId) {
	 	this.approveProcessId = approveProcessId;
	}
	/**
	 * @return 审批流程
	 */
	public String getApproveProcess() {
	 	return approveProcess;
	}
	/**
	 * @设置 审批流程
	 * @param approveProcess
	 */
	public void setApproveProcess(String approveProcess) {
	 	this.approveProcess = approveProcess;
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
	public String getFincShowId() {
		return fincShowId;
	}
	public void setFincShowId(String fincShowId) {
		this.fincShowId = fincShowId;
	}
}