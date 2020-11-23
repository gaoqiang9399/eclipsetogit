package app.component.collateral.entity;
import app.base.BaseDomain;
/**
* Title: MfPleRepoApply.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri May 05 09:49:33 CST 2017
* @version：1.0
**/
public class MfPleRepoApply extends BaseDomain {
	private String repoAppId;//申请编号
	private String busPleId;//业务抵质押编号
	private String applyDate;//申请日期
	private String transferReason;//反转让原因
	private Double fincPrepayBal;//融资预付款余额
	private Double accruedInterest;//应付未付利息
	private Double reduceAmt;//减免金额
	private Double receiptAmt;//实收金额
	private String receiptDate;//收款日期
	private String affirmRemark;//确认说明
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间
	private String appSts;//申请状态1:申请中2:审批中3:审批通过4:已否决
	private String ext1;//
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String ext5;//
	private String wkfAppId;  //审批流程id
	
	private String approveNodeNo;//当前审批节点编号
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//审批角色号/用户号
	private String approvePartName;//当前审批角色/用户名称
	
	/**
	 * @return 申请编号
	 */
	public String getRepoAppId() {
	 	return repoAppId;
	}
	/**
	 * @设置 申请编号
	 * @param repoAppId
	 */
	public void setRepoAppId(String repoAppId) {
	 	this.repoAppId = repoAppId;
	}
	/**
	 * @return 业务抵质押编号
	 */
	public String getBusPleId() {
	 	return busPleId;
	}
	/**
	 * @设置 业务抵质押编号
	 * @param busPleId
	 */
	public void setBusPleId(String busPleId) {
	 	this.busPleId = busPleId;
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
	 * @return 反转让原因
	 */
	public String getTransferReason() {
	 	return transferReason;
	}
	/**
	 * @设置 反转让原因
	 * @param transferReason
	 */
	public void setTransferReason(String transferReason) {
	 	this.transferReason = transferReason;
	}
	/**
	 * @return 融资预付款余额
	 */
	public Double getFincPrepayBal() {
	 	return fincPrepayBal;
	}
	/**
	 * @设置 融资预付款余额
	 * @param fincPrepayBal
	 */
	public void setFincPrepayBal(Double fincPrepayBal) {
	 	this.fincPrepayBal = fincPrepayBal;
	}
	/**
	 * @return 应付未付利息
	 */
	public Double getAccruedInterest() {
	 	return accruedInterest;
	}
	/**
	 * @设置 应付未付利息
	 * @param accruedInterest
	 */
	public void setAccruedInterest(Double accruedInterest) {
	 	this.accruedInterest = accruedInterest;
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
	/**
	 * @return 申请状态1:申请中2:审批中3:审批通过4:已否决
	 */
	public String getAppSts() {
	 	return appSts;
	}
	/**
	 * @设置 申请状态1:申请中2:审批中3:审批通过4:已否决
	 * @param appSts
	 */
	public void setAppSts(String appSts) {
	 	this.appSts = appSts;
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
	public String getApproveNodeNo() {
		return approveNodeNo;
	}
	public void setApproveNodeNo(String approveNodeNo) {
		this.approveNodeNo = approveNodeNo;
	}
	public String getApproveNodeName() {
		return approveNodeName;
	}
	public void setApproveNodeName(String approveNodeName) {
		this.approveNodeName = approveNodeName;
	}
	public String getApprovePartNo() {
		return approvePartNo;
	}
	public void setApprovePartNo(String approvePartNo) {
		this.approvePartNo = approvePartNo;
	}
	public String getApprovePartName() {
		return approvePartName;
	}
	public void setApprovePartName(String approvePartName) {
		this.approvePartName = approvePartName;
	}
	public String getWkfAppId() {
		return wkfAppId;
	}
	public void setWkfAppId(String wkfAppId) {
		this.wkfAppId = wkfAppId;
	}
	public Double getReduceAmt() {
		return reduceAmt;
	}
	public void setReduceAmt(Double reduceAmt) {
		this.reduceAmt = reduceAmt;
	}
	public Double getReceiptAmt() {
		return receiptAmt;
	}
	public void setReceiptAmt(Double receiptAmt) {
		this.receiptAmt = receiptAmt;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getAffirmRemark() {
		return affirmRemark;
	}
	public void setAffirmRemark(String affirmRemark) {
		this.affirmRemark = affirmRemark;
	}
}