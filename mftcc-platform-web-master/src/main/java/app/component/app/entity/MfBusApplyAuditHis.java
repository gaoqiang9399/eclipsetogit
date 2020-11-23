package app.component.app.entity;
import app.base.BaseDomain;
/**
* Title: MfBusApplyAuditHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jun 19 09:13:10 CST 2017
* @version：1.0
**/
public class MfBusApplyAuditHis extends BaseDomain {
	private String id;//唯一编号
	private String hisId;//审批历史编号
	private String appId;//业务编号
	private String reviewType;//评审结果（1：同意；2：不同意；）
	private String reviewNo;//评审人号
	private String reviewName;//评审人名称
	private String reviewDesc;//评审意见
	private String opNo;//登记人号
	private String opName;//登记人名称
	private String brNo;//登记机构号
	private String brName;//登记机构名称
	private String regTime;//登记时间
	private String lstModTime;//最近修改时间
	private String ext1;//ext1
	private String ext2;//ext2
	private String ext3;//ext3
	private String ext4;//ext4
	private String ext5;//ext5
	private String ext6;//ext6
	private String ext7;//ext7
	private String ext8;//ext8
	private String ext9;//ext9
	private String ext10;//ext10

	/**
	 * @return 唯一编号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 唯一编号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 审批历史编号
	 */
	public String getHisId() {
	 	return hisId;
	}
	/**
	 * @设置 审批历史编号
	 * @param hisId
	 */
	public void setHisId(String hisId) {
	 	this.hisId = hisId;
	}
	/**
	 * @return 业务编号
	 */
	public String getAppId() {
	 	return appId;
	}
	/**
	 * @设置 业务编号
	 * @param appId
	 */
	public void setAppId(String appId) {
	 	this.appId = appId;
	}
	/**
	 * @return 评审结果（1：同意；2：不同意；）
	 */
	public String getReviewType() {
	 	return reviewType;
	}
	/**
	 * @设置 评审结果（1：同意；2：不同意；）
	 * @param reviewType
	 */
	public void setReviewType(String reviewType) {
	 	this.reviewType = reviewType;
	}
	/**
	 * @return 评审人号
	 */
	public String getReviewNo() {
	 	return reviewNo;
	}
	/**
	 * @设置 评审人号
	 * @param reviewNo
	 */
	public void setReviewNo(String reviewNo) {
	 	this.reviewNo = reviewNo;
	}
	/**
	 * @return 评审人名称
	 */
	public String getReviewName() {
	 	return reviewName;
	}
	/**
	 * @设置 评审人名称
	 * @param reviewName
	 */
	public void setReviewName(String reviewName) {
	 	this.reviewName = reviewName;
	}
	/**
	 * @return 评审意见
	 */
	public String getReviewDesc() {
	 	return reviewDesc;
	}
	/**
	 * @设置 评审意见
	 * @param reviewDesc
	 */
	public void setReviewDesc(String reviewDesc) {
	 	this.reviewDesc = reviewDesc;
	}
	/**
	 * @return 登记人号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人号
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
	 * @return 登记机构号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记机构号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记机构名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记机构名称
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
	 * @return 最近修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最近修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return ext1
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 ext1
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return ext2
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 ext2
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return ext3
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 ext3
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return ext4
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 ext4
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return ext5
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 ext5
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	/**
	 * @return ext6
	 */
	public String getExt6() {
	 	return ext6;
	}
	/**
	 * @设置 ext6
	 * @param ext6
	 */
	public void setExt6(String ext6) {
	 	this.ext6 = ext6;
	}
	/**
	 * @return ext7
	 */
	public String getExt7() {
	 	return ext7;
	}
	/**
	 * @设置 ext7
	 * @param ext7
	 */
	public void setExt7(String ext7) {
	 	this.ext7 = ext7;
	}
	/**
	 * @return ext8
	 */
	public String getExt8() {
	 	return ext8;
	}
	/**
	 * @设置 ext8
	 * @param ext8
	 */
	public void setExt8(String ext8) {
	 	this.ext8 = ext8;
	}
	/**
	 * @return ext9
	 */
	public String getExt9() {
	 	return ext9;
	}
	/**
	 * @设置 ext9
	 * @param ext9
	 */
	public void setExt9(String ext9) {
	 	this.ext9 = ext9;
	}
	/**
	 * @return ext10
	 */
	public String getExt10() {
	 	return ext10;
	}
	/**
	 * @设置 ext10
	 * @param ext10
	 */
	public void setExt10(String ext10) {
	 	this.ext10 = ext10;
	}
}