package app.component.oa.filesign.entity;
import app.base.BaseDomain;
/**
* Title: MfOaFileCountersign.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jun 12 18:19:50 CST 2017
* @version：1.0
**/
public class MfOaFileCountersign extends BaseDomain {
	private String fileNo;//文件编号
	private String fileType;//文件类型1：内部合同；2：外部合同；3：委托授权书；4：制度；5：其他
	private String counterName;//会签主题
	private String appSts;//审批状态0： 申请中；1：审批中；2：审批通过；3：已否决
	private String remark;//备注
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间
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
	
	
	private String nextOpNo;//下一操作员
	private String isCountersign;//是否会签
	private String isChairman;//是否走董事长
	private String opinionType;//审批类型
	private String approvalOpinion;//审批意见


	private String cusNo;
	private String cusName;
	private String idNum;
	private String contactsTel;


	/**
	 * @return 文件编号
	 */
	public String getFileNo() {
	 	return fileNo;
	}
	/**
	 * @设置 文件编号
	 * @param fileNo
	 */
	public void setFileNo(String fileNo) {
	 	this.fileNo = fileNo;
	}
	/**
	 * @return 文件类型1：内部合同；2：外部合同；3：委托授权书；4：制度；5：其他
	 */
	public String getFileType() {
	 	return fileType;
	}
	/**
	 * @设置 文件类型1：内部合同；2：外部合同；3：委托授权书；4：制度；5：其他
	 * @param fileType
	 */
	public void setFileType(String fileType) {
	 	this.fileType = fileType;
	}
	/**
	 * @return 会签主题
	 */
	public String getCounterName() {
	 	return counterName;
	}
	/**
	 * @设置 会签主题
	 * @param counterName
	 */
	public void setCounterName(String counterName) {
	 	this.counterName = counterName;
	}
	/**
	 * @return 审批状态0： 申请中；1：审批中；2：审批通过；3：已否决
	 */
	public String getAppSts() {
	 	return appSts;
	}
	/**
	 * @设置 审批状态0： 申请中；1：审批中；2：审批通过；3：已否决
	 * @param appSts
	 */
	public void setAppSts(String appSts) {
	 	this.appSts = appSts;
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
	public String getNextOpNo() {
		return nextOpNo;
	}
	public void setNextOpNo(String nextOpNo) {
		this.nextOpNo = nextOpNo;
	}
	public String getIsCountersign() {
		return isCountersign;
	}
	public void setIsCountersign(String isCountersign) {
		this.isCountersign = isCountersign;
	}
	public String getIsChairman() {
		return isChairman;
	}
	public void setIsChairman(String isChairman) {
		this.isChairman = isChairman;
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

	public String getCusNo() {
		return cusNo;
	}

	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}
}