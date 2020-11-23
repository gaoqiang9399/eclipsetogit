package app.component.cus.institutions.entity;
import app.base.BaseDomain;
/**
* Title: MfBusInstitutions.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu May 17 14:31:12 CST 2018
* @version：1.0
**/
public class MfBusInstitutions extends BaseDomain {
	private String agenciesId;//唯一编号
	private String agenciesUid;//资金机构编号系统自动生成
	private String agenciesName;//资金机构名称
	private String cusType;//类型与mf_cus_type对应
	private String agenciesButtName;//资金机构对接人名称
	private String agenciesButtPhone;//资金机构对接人联系方式
	private String agenciesOpNo;//资金机构关联操作员
	private String infIntegrity;//资料完整度
	private String remarks;//备注说明
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String brNo;//部门编号
	private String brName;//部门名称
	private String regTime;//登记时间格式：yyyyMMdd HH:mm:ss
	private String lstModTime;//最后修改时间格式：yyyyMMdd HH:mm:ss
	private Double agenciesDeposit;//保证金金额
	private Double agenciesDealBase;//返现点数
	private Double agenciesDealMoney;//返现金额
	private Double consultingFeeYear;//年化咨询费
	private Double securityFeeYear;//年化担保费
	private Double thirdPartyPaymentFee;//第三方支付费
	private Double platformServerFee;//平台服务费
	private Double investorYieldRate;//投资人收益率
	private Double payTotalRate;//客户承担总利率
	private String insititutionType;//
	private Double creditAmount;//
	private String lastDateCredit;//
	private String insititutionStatus;//
	private String approveNodeNo;//当前审批节点编号
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//审批角色号/用户号
	private String approvePartName;//当前审批角色/用户名称
	private String approveRemark;//审批说明
	private String approveResult;//审批意见1同意2否决3退回上一环节4退回初审5不同意
	private String applyProcessId;//保管审批流程编号

	private String delFlag;
	private String useFlag;

	
	
	public String getApplyProcessId() {
		return applyProcessId;
	}
	public void setApplyProcessId(String applyProcessId) {
		this.applyProcessId = applyProcessId;
	}
	/**
	 * @return 唯一编号
	 */
	public String getAgenciesId() {
	 	return agenciesId;
	}
	/**
	 * @设置 唯一编号
	 * @param agenciesId
	 */
	public void setAgenciesId(String agenciesId) {
	 	this.agenciesId = agenciesId;
	}
	/**
	 * @return 资金机构编号系统自动生成
	 */
	public String getAgenciesUid() {
	 	return agenciesUid;
	}
	/**
	 * @设置 资金机构编号系统自动生成
	 * @param agenciesUid
	 */
	public void setAgenciesUid(String agenciesUid) {
	 	this.agenciesUid = agenciesUid;
	}
	/**
	 * @return 资金机构名称
	 */
	public String getAgenciesName() {
	 	return agenciesName;
	}
	/**
	 * @设置 资金机构名称
	 * @param agenciesName
	 */
	public void setAgenciesName(String agenciesName) {
	 	this.agenciesName = agenciesName;
	}
	/**
	 * @return 类型与mf_cus_type对应
	 */
	public String getCusType() {
	 	return cusType;
	}
	/**
	 * @设置 类型与mf_cus_type对应
	 * @param cusType
	 */
	public void setCusType(String cusType) {
	 	this.cusType = cusType;
	}
	/**
	 * @return 资金机构对接人名称
	 */
	public String getAgenciesButtName() {
	 	return agenciesButtName;
	}
	/**
	 * @设置 资金机构对接人名称
	 * @param agenciesButtName
	 */
	public void setAgenciesButtName(String agenciesButtName) {
	 	this.agenciesButtName = agenciesButtName;
	}
	/**
	 * @return 资金机构对接人联系方式
	 */
	public String getAgenciesButtPhone() {
	 	return agenciesButtPhone;
	}
	/**
	 * @设置 资金机构对接人联系方式
	 * @param agenciesButtPhone
	 */
	public void setAgenciesButtPhone(String agenciesButtPhone) {
	 	this.agenciesButtPhone = agenciesButtPhone;
	}
	/**
	 * @return 资金机构关联操作员
	 */
	public String getAgenciesOpNo() {
	 	return agenciesOpNo;
	}
	/**
	 * @设置 资金机构关联操作员
	 * @param agenciesOpNo
	 */
	public void setAgenciesOpNo(String agenciesOpNo) {
	 	this.agenciesOpNo = agenciesOpNo;
	}
	/**
	 * @return 资料完整度
	 */
	public String getInfIntegrity() {
	 	return infIntegrity;
	}
	/**
	 * @设置 资料完整度
	 * @param infIntegrity
	 */
	public void setInfIntegrity(String infIntegrity) {
	 	this.infIntegrity = infIntegrity;
	}
	/**
	 * @return 备注说明
	 */
	public String getRemarks() {
	 	return remarks;
	}
	/**
	 * @设置 备注说明
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
	 	this.remarks = remarks;
	}
	/**
	 * @return 操作员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作员名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 操作员名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
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
	 * @return 部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 登记时间格式：yyyyMMdd HH:mm:ss
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间格式：yyyyMMdd HH:mm:ss
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改时间格式：yyyyMMdd HH:mm:ss
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间格式：yyyyMMdd HH:mm:ss
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 保证金金额
	 */
	public Double getAgenciesDeposit() {
	 	return agenciesDeposit;
	}
	/**
	 * @设置 保证金金额
	 * @param agenciesDeposit
	 */
	public void setAgenciesDeposit(Double agenciesDeposit) {
	 	this.agenciesDeposit = agenciesDeposit;
	}
	/**
	 * @return 返现点数
	 */
	public Double getAgenciesDealBase() {
	 	return agenciesDealBase;
	}
	/**
	 * @设置 返现点数
	 * @param agenciesDealBase
	 */
	public void setAgenciesDealBase(Double agenciesDealBase) {
	 	this.agenciesDealBase = agenciesDealBase;
	}
	/**
	 * @return 返现金额
	 */
	public Double getAgenciesDealMoney() {
	 	return agenciesDealMoney;
	}
	/**
	 * @设置 返现金额
	 * @param agenciesDealMoney
	 */
	public void setAgenciesDealMoney(Double agenciesDealMoney) {
	 	this.agenciesDealMoney = agenciesDealMoney;
	}
	/**
	 * @return 年化咨询费
	 */
	public Double getConsultingFeeYear() {
	 	return consultingFeeYear;
	}
	/**
	 * @设置 年化咨询费
	 * @param consultingFeeYear
	 */
	public void setConsultingFeeYear(Double consultingFeeYear) {
	 	this.consultingFeeYear = consultingFeeYear;
	}
	/**
	 * @return 年化担保费
	 */
	public Double getSecurityFeeYear() {
	 	return securityFeeYear;
	}
	/**
	 * @设置 年化担保费
	 * @param securityFeeYear
	 */
	public void setSecurityFeeYear(Double securityFeeYear) {
	 	this.securityFeeYear = securityFeeYear;
	}
	/**
	 * @return 第三方支付费
	 */
	public Double getThirdPartyPaymentFee() {
	 	return thirdPartyPaymentFee;
	}
	/**
	 * @设置 第三方支付费
	 * @param thirdPartyPaymentFee
	 */
	public void setThirdPartyPaymentFee(Double thirdPartyPaymentFee) {
	 	this.thirdPartyPaymentFee = thirdPartyPaymentFee;
	}
	/**
	 * @return 平台服务费
	 */
	public Double getPlatformServerFee() {
	 	return platformServerFee;
	}
	/**
	 * @设置 平台服务费
	 * @param platformServerFee
	 */
	public void setPlatformServerFee(Double platformServerFee) {
	 	this.platformServerFee = platformServerFee;
	}
	/**
	 * @return 投资人收益率
	 */
	public Double getInvestorYieldRate() {
	 	return investorYieldRate;
	}
	/**
	 * @设置 投资人收益率
	 * @param investorYieldRate
	 */
	public void setInvestorYieldRate(Double investorYieldRate) {
	 	this.investorYieldRate = investorYieldRate;
	}
	/**
	 * @return 客户承担总利率
	 */
	public Double getPayTotalRate() {
	 	return payTotalRate;
	}
	/**
	 * @设置 客户承担总利率
	 * @param payTotalRate
	 */
	public void setPayTotalRate(Double payTotalRate) {
	 	this.payTotalRate = payTotalRate;
	}
	/**
	 * @return 
	 */
	public String getInsititutionType() {
	 	return insititutionType;
	}
	/**
	 * @设置 
	 * @param insititutionType
	 */
	public void setInsititutionType(String insititutionType) {
	 	this.insititutionType = insititutionType;
	}
	/**
	 * @return 
	 */
	public Double getCreditAmount() {
	 	return creditAmount;
	}
	/**
	 * @设置 
	 * @param creditAmount
	 */
	public void setCreditAmount(Double creditAmount) {
	 	this.creditAmount = creditAmount;
	}
	/**
	 * @return 
	 */
	public String getLastDateCredit() {
	 	return lastDateCredit;
	}
	/**
	 * @设置 
	 * @param lastDateCredit
	 */
	public void setLastDateCredit(String lastDateCredit) {
	 	this.lastDateCredit = lastDateCredit;
	}
	/**
	 * @return 
	 */
	public String getInsititutionStatus() {
	 	return insititutionStatus;
	}
	/**
	 * @设置 
	 * @param insititutionStatus
	 */
	public void setInsititutionStatus(String insititutionStatus) {
	 	this.insititutionStatus = insititutionStatus;
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

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
}