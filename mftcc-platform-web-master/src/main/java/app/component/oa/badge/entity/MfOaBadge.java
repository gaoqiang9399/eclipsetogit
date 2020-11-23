package app.component.oa.badge.entity;
import app.base.BaseDomain;
/**
* Title: MfOaBadge.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Jun 01 09:00:01 CST 2017
* @version：1.0
**/
public class MfOaBadge extends BaseDomain {
	private String badgeNo;//主键
	private String badageName;//用章名称
	private String busiType;//业务类型 0 带章 1 带证
	private String remarks;//备注
	private String cust;//申请人
	private String badgeSts;//状态 0 未提交 1 审批中 2待归还 3已归还 4否决 5待领章'
	private String applyTime;//申请时间
	private String approveNodeName;//审批环节
	private String opNo;//操作人编号
	private String opName;//操作人名称
	private String lstModTime;//修改时间
	private String approvePartName;//初审的操作员(不存入数据库)
	private String approvalNodeName;//审批环节
	/*
	 *   `revert_time` varchar(17) DEFAULT NULL COMMENT '归还时间',
  		`out_time` varchar(17) DEFAULT NULL COMMENT '出章时间',
  		`out_remark` varchar(100) DEFAULT NULL COMMENT '出章说明',
	 */
	private String revertTime;//归还时间
	private String outTime;//出章时间
	private String outRemark;//出章说明
	
	private String isChairman;
	
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
	
	
	
	
	public String getRevertTime() {
		return revertTime;
	}
	public void setRevertTime(String revertTime) {
		this.revertTime = revertTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getOutRemark() {
		return outRemark;
	}
	public void setOutRemark(String outRemark) {
		this.outRemark = outRemark;
	}
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
	 * @return 章的名称
	 */
	public String getBadageName() {
	 	return badageName;
	}
	/**
	 * @设置 章的名称
	 * @param badageName
	 */
	public void setBadageName(String badageName) {
	 	this.badageName = badageName;
	}
	/**
	 * @return 业务类型 0 带章 1 带证
	 */
	public String getBusiType() {
	 	return busiType;
	}
	/**
	 * @设置 业务类型 0 带章 1 带证
	 * @param busiType
	 */
	public void setBusiType(String busiType) {
	 	this.busiType = busiType;
	}
	/**
	 * @return 备注
	 */
	public String getRemarks() {
	 	return remarks;
	}
	/**
	 * @设置 备注
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
	 	this.remarks = remarks;
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
	/*
	 * 
	 */
	public String getBadgeSts() {
		return badgeSts;
	}
	public void setBadgeSts(String badgeSts) {
		this.badgeSts = badgeSts;
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
	 * @return 操作人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作人名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 操作人名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
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
	
}