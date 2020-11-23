package app.component.oa.changemoney.entity;
import app.base.BaseDomain;
/**
* Title: MfOaCounttrans.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Jun 13 15:21:27 CST 2017
* @version：1.0
**/
public class MfOaCounttrans extends BaseDomain {
	private String badgeNo;//主键
	private String cust;//申请人
	private String state;//状态 0 未提交 1 审批中 2 审批通过 3审批不通过 4 完成转款
	private String applyTime;//申请时间
	private String approveNodeName;//审批环节
	private String transferOutAccountNumber;//转出账号
	private String transferInAccountNumber;//转入账号
	private String transferOutAccountName;//转出账户名称
	private String transferInAccountName;//转入账户名称
	private String transferOutBandName;//转出开户行名称
	private String transferInBandName;//转入开户行名称
	private String transferInBandBignumber;//转入行大行行号
	private Double moneyAmt;//转账金额
	private String transferRemark;//转账事由
	private String proofAddress;//转款凭单文件地址
	
	
	private String approvePartName;//初审的操作员(不存入数据库)
	private String approvalNodeName;//审批环节
	
	private String opNo;//
	private String opName;//
	private String brNo;//
	private String brName;//
	private String regTime;//
	private String lstModTime;//
	private String remark;//
	private String ext1;//
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String ext5;//
	private String ext6;//
	private String ext7;//
	private String ext8;//
	private String ext9;//
	private String ext10;//
	private String opinionType;
	private String approvalOpinion;
	private String taskId;
	private String isChairman;
	private String cusNoFund;//确定资金机构
	
	
	/**
	 * @return 主键
	 */
	public String getBadgeNo() {
	 	return badgeNo;
	}
	/**
	 * @设置 主键
	 * @param badgeNo
	 */
	public void setBadgeNo(String badgeNo) {
	 	this.badgeNo = badgeNo;
	}
	/**
	 * @return 申请人
	 */
	public String getCust() {
	 	return cust;
	}
	/**
	 * @设置 申请人
	 * @param cust
	 */
	public void setCust(String cust) {
	 	this.cust = cust;
	}
	/**
	 * @return 状态 0 未提交 1 审批中 2 审批通过 3审批不通过 4 完成转款
	 */
	public String getState() {
	 	return state;
	}
	/**
	 * @设置 状态 0 未提交 1 审批中 2 审批通过 3审批不通过 4 完成转款
	 * @param state
	 */
	public void setState(String state) {
	 	this.state = state;
	}
	/**
	 * @return 申请时间
	 */
	public String getApplyTime() {
	 	return applyTime;
	}
	/**
	 * @设置 申请时间
	 * @param applyTime
	 */
	public void setApplyTime(String applyTime) {
	 	this.applyTime = applyTime;
	}
	/**
	 * @return 审批环节
	 */
	public String getApproveNodeName() {
	 	return approveNodeName;
	}
	/**
	 * @设置 审批环节
	 * @param approveNodeName
	 */
	public void setApproveNodeName(String approveNodeName) {
	 	this.approveNodeName = approveNodeName;
	}
	/**
	 * @return 转出账号
	 */
	public String getTransferOutAccountNumber() {
	 	return transferOutAccountNumber;
	}
	/**
	 * @设置 转出账号
	 * @param transferOutAccountNumber
	 */
	public void setTransferOutAccountNumber(String transferOutAccountNumber) {
	 	this.transferOutAccountNumber = transferOutAccountNumber;
	}
	/**
	 * @return 转入账号
	 */
	public String getTransferInAccountNumber() {
	 	return transferInAccountNumber;
	}
	/**
	 * @设置 转入账号
	 * @param transferInAccountNumber
	 */
	public void setTransferInAccountNumber(String transferInAccountNumber) {
	 	this.transferInAccountNumber = transferInAccountNumber;
	}
	/**
	 * @return 转出账户名称
	 */
	public String getTransferOutAccountName() {
	 	return transferOutAccountName;
	}
	/**
	 * @设置 转出账户名称
	 * @param transferOutAccountName
	 */
	public void setTransferOutAccountName(String transferOutAccountName) {
	 	this.transferOutAccountName = transferOutAccountName;
	}
	/**
	 * @return 转入账户名称
	 */
	public String getTransferInAccountName() {
	 	return transferInAccountName;
	}
	/**
	 * @设置 转入账户名称
	 * @param transferInAccountName
	 */
	public void setTransferInAccountName(String transferInAccountName) {
	 	this.transferInAccountName = transferInAccountName;
	}
	/**
	 * @return 转出开户行名称
	 */
	public String getTransferOutBandName() {
	 	return transferOutBandName;
	}
	/**
	 * @设置 转出开户行名称
	 * @param transferOutBandName
	 */
	public void setTransferOutBandName(String transferOutBandName) {
	 	this.transferOutBandName = transferOutBandName;
	}
	/**
	 * @return 转入开户行名称
	 */
	public String getTransferInBandName() {
	 	return transferInBandName;
	}
	/**
	 * @设置 转入开户行名称
	 * @param transferInBandName
	 */
	public void setTransferInBandName(String transferInBandName) {
	 	this.transferInBandName = transferInBandName;
	}
	/**
	 * @return 转入行大行行号
	 */
	public String getTransferInBandBignumber() {
	 	return transferInBandBignumber;
	}
	/**
	 * @设置 转入行大行行号
	 * @param transferInBandBignumber
	 */
	public void setTransferInBandBignumber(String transferInBandBignumber) {
	 	this.transferInBandBignumber = transferInBandBignumber;
	}
	/**
	 * @return 转账金额
	 */
	public Double getMoneyAmt() {
	 	return moneyAmt;
	}
	/**
	 * @设置 转账金额
	 * @param moneyAmt
	 */
	public void setMoneyAmt(Double moneyAmt) {
	 	this.moneyAmt = moneyAmt;
	}
	/**
	 * @return 转账事由
	 */
	public String getTransferRemark() {
	 	return transferRemark;
	}
	/**
	 * @设置 转账事由
	 * @param transferRemark
	 */
	public void setTransferRemark(String transferRemark) {
	 	this.transferRemark = transferRemark;
	}
	/**
	 * @return 转款凭单文件地址
	 */
	public String getProofAddress() {
	 	return proofAddress;
	}
	/**
	 * @设置 转款凭单文件地址
	 * @param proofAddress
	 */
	public void setProofAddress(String proofAddress) {
	 	this.proofAddress = proofAddress;
	}
	/**
	 * @return 
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
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
	 * @return 
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return 
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	/**
	 * @return 
	 */
	public String getExt6() {
	 	return ext6;
	}
	/**
	 * @设置 
	 * @param ext6
	 */
	public void setExt6(String ext6) {
	 	this.ext6 = ext6;
	}
	/**
	 * @return 
	 */
	public String getExt7() {
	 	return ext7;
	}
	/**
	 * @设置 
	 * @param ext7
	 */
	public void setExt7(String ext7) {
	 	this.ext7 = ext7;
	}
	/**
	 * @return 
	 */
	public String getExt8() {
	 	return ext8;
	}
	/**
	 * @设置 
	 * @param ext8
	 */
	public void setExt8(String ext8) {
	 	this.ext8 = ext8;
	}
	/**
	 * @return 
	 */
	public String getExt9() {
	 	return ext9;
	}
	/**
	 * @设置 
	 * @param ext9
	 */
	public void setExt9(String ext9) {
	 	this.ext9 = ext9;
	}
	/**
	 * @return 
	 */
	public String getExt10() {
	 	return ext10;
	}
	/**
	 * @设置 
	 * @param ext10
	 */
	public void setExt10(String ext10) {
	 	this.ext10 = ext10;
	}
	public String getOpinionType() {
		return opinionType;
	}
	public void setOpinionType(String opinionType) {
		this.opinionType = opinionType;
	}
	public String getApprovalOpinion() {
		return approvalOpinion;
	}
	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getIsChairman() {
		return isChairman;
	}
	public void setIsChairman(String isChairman) {
		this.isChairman = isChairman;
	}
	public String getApprovePartName() {
		return approvePartName;
	}
	public void setApprovePartName(String approvePartName) {
		this.approvePartName = approvePartName;
	}
	public String getApprovalNodeName() {
		return approvalNodeName;
	}
	public void setApprovalNodeName(String approvalNodeName) {
		this.approvalNodeName = approvalNodeName;
	}
	public String getCusNoFund() {
		return cusNoFund;
	}
	public void setCusNoFund(String cusNoFund) {
		this.cusNoFund = cusNoFund;
	}
	
	
}