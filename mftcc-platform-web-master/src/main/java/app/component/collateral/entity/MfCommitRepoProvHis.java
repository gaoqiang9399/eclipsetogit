package app.component.collateral.entity;
import app.base.BaseDomain;
/**
* Title: MfCommitRepoProvHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri May 05 20:49:02 CST 2017
* @version：1.0
**/
public class MfCommitRepoProvHis extends BaseDomain {
	private String commRopeHistoryId;//质押/转让编号
	private String registerProveId;//登记证明编号
	private String registerProveTime;//登记时间
	private String registerProveType;//登记种类0初始登记
	private String dealType;//交易类型1质押2转让
	private String registerEndDate;//登记到期日
	private String pledgorNo;//出资/出让人编号
	private String pledgorName;//出质/出让人名称
	private String pledgorRegisterAddress;//出质/出让人注册地址
	private String pledgeeNo;//质权/出让人编号
	private String pledgeeName;//质权/出让人
	private String pledgeeRegisterAddress;//质权/出让人注册地址
	private String approveProcessId;//审批流程id
	private String approveNodeNo;//当前审批节点编号
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//审批角色号/用户号
	private String approvePartName;//当前审批角色/用户名称
	private String approveRemark;//审批说明
	private String approveResult;//审批意见1同意2否决3退回上一环节4退回初审5不同意
	private String appSts;//承购申请状态0新增申请1审批中2审批通过3否决
	private String busCollateralId;//押品业务关联编号
	private String opNo;//登记人
	private String opName;//登记人姓名
	private String brNo;//登记部门
	private String brName;//登记部门名称
	private String regTime;//信息登记时间
	private String lstModTime;//最后修改时间

	/**
	 * @return 质押/转让编号
	 */
	public String getCommRopeHistoryId() {
	 	return commRopeHistoryId;
	}
	/**
	 * @设置 质押/转让编号
	 * @param commRopeHistoryId
	 */
	public void setCommRopeHistoryId(String commRopeHistoryId) {
	 	this.commRopeHistoryId = commRopeHistoryId;
	}
	/**
	 * @return 登记证明编号
	 */
	public String getRegisterProveId() {
	 	return registerProveId;
	}
	/**
	 * @设置 登记证明编号
	 * @param registerProveId
	 */
	public void setRegisterProveId(String registerProveId) {
	 	this.registerProveId = registerProveId;
	}
	/**
	 * @return 登记时间
	 */
	public String getRegisterProveTime() {
	 	return registerProveTime;
	}
	/**
	 * @设置 登记时间
	 * @param registerProveTime
	 */
	public void setRegisterProveTime(String registerProveTime) {
	 	this.registerProveTime = registerProveTime;
	}
	/**
	 * @return 登记种类0初始登记
	 */
	public String getRegisterProveType() {
	 	return registerProveType;
	}
	/**
	 * @设置 登记种类0初始登记
	 * @param registerProveType
	 */
	public void setRegisterProveType(String registerProveType) {
	 	this.registerProveType = registerProveType;
	}
	/**
	 * @return 交易类型1质押2转让
	 */
	public String getDealType() {
	 	return dealType;
	}
	/**
	 * @设置 交易类型1质押2转让
	 * @param dealType
	 */
	public void setDealType(String dealType) {
	 	this.dealType = dealType;
	}
	/**
	 * @return 登记到期日
	 */
	public String getRegisterEndDate() {
	 	return registerEndDate;
	}
	/**
	 * @设置 登记到期日
	 * @param registerEndDate
	 */
	public void setRegisterEndDate(String registerEndDate) {
	 	this.registerEndDate = registerEndDate;
	}
	/**
	 * @return 出资/出让人编号
	 */
	public String getPledgorNo() {
	 	return pledgorNo;
	}
	/**
	 * @设置 出资/出让人编号
	 * @param pledgorNo
	 */
	public void setPledgorNo(String pledgorNo) {
	 	this.pledgorNo = pledgorNo;
	}
	/**
	 * @return 出质/出让人名称
	 */
	public String getPledgorName() {
	 	return pledgorName;
	}
	/**
	 * @设置 出质/出让人名称
	 * @param pledgorName
	 */
	public void setPledgorName(String pledgorName) {
	 	this.pledgorName = pledgorName;
	}
	/**
	 * @return 出质/出让人注册地址
	 */
	public String getPledgorRegisterAddress() {
	 	return pledgorRegisterAddress;
	}
	/**
	 * @设置 出质/出让人注册地址
	 * @param pledgorRegisterAddress
	 */
	public void setPledgorRegisterAddress(String pledgorRegisterAddress) {
	 	this.pledgorRegisterAddress = pledgorRegisterAddress;
	}
	/**
	 * @return 质权/出让人编号
	 */
	public String getPledgeeNo() {
	 	return pledgeeNo;
	}
	/**
	 * @设置 质权/出让人编号
	 * @param pledgeeNo
	 */
	public void setPledgeeNo(String pledgeeNo) {
	 	this.pledgeeNo = pledgeeNo;
	}
	/**
	 * @return 质权/出让人
	 */
	public String getPledgeeName() {
	 	return pledgeeName;
	}
	/**
	 * @设置 质权/出让人
	 * @param pledgeeName
	 */
	public void setPledgeeName(String pledgeeName) {
	 	this.pledgeeName = pledgeeName;
	}
	/**
	 * @return 质权/出让人注册地址
	 */
	public String getPledgeeRegisterAddress() {
	 	return pledgeeRegisterAddress;
	}
	/**
	 * @设置 质权/出让人注册地址
	 * @param pledgeeRegisterAddress
	 */
	public void setPledgeeRegisterAddress(String pledgeeRegisterAddress) {
	 	this.pledgeeRegisterAddress = pledgeeRegisterAddress;
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
	 * @return 承购申请状态0新增申请1审批中2审批通过3否决
	 */
	public String getAppSts() {
	 	return appSts;
	}
	/**
	 * @设置 承购申请状态0新增申请1审批中2审批通过3否决
	 * @param appSts
	 */
	public void setAppSts(String appSts) {
	 	this.appSts = appSts;
	}
	/**
	 * @return 押品业务关联编号
	 */
	public String getBusCollateralId() {
	 	return busCollateralId;
	}
	/**
	 * @设置 押品业务关联编号
	 * @param busCollateralId
	 */
	public void setBusCollateralId(String busCollateralId) {
	 	this.busCollateralId = busCollateralId;
	}
	/**
	 * @return 登记人
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人
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
	 * @return 登记部门
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门
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
	 * @return 信息登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 信息登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
}