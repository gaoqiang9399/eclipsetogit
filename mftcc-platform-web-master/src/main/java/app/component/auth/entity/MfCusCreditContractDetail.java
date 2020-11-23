package app.component.auth.entity;
import app.base.BaseDomain;
/**
* Title: MfCusCreditContractDetail.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Apr 18 19:42:08 CST 2018
* @version：1.0
**/
public class MfCusCreditContractDetail extends BaseDomain {
	private String id;//唯一主键
	private String cusNo;//客户号
	private String cusName;//客户姓名
	private String pactNo;//合同展示号
	private String pactName;//合同名称
	private String pactType;//合同类型
	private String firstParty;//甲方
	private String secondParty;//乙方
	private String thirdParty;//丙方
	private String fourParty;//丁方
	private String pactSignDate;//签约日期
	private Double creditSum;//授信总额(合同金额)
	private Double involveCreditSum;//涉及授信金额
	private Double authBal;//授信余额
	private Integer creditTerm;//授信期限
	private String beginDate;//开始日期
	private String endDate;//结束日期
	private String isCeilingLoop;//额度是否可循环
	private String remark;//备注
	private String cooperationPlatform;//合作平台
	private String opNo;//登记人号
	private String opName;//登记人名称
	private String brNo;//登记机构号
	private String brName;//登记机构名称
	private String regTime;//登记
	private String lstModTime;//最近修改时间
	private String creditAppId;//授信申请编号
	private String creditModel;//授信模式。数据字典CREDIT_MODEL1客户授信2项目授信
	private String projectNo;//项目展示编号
	private String projectName;//项目名称
	private String projectPactNo;//项目合同号
	private String creditPactId;//授信协议编号
	private String creditPactNo;//授信协议展示号
	private String appId;//业务申请号
	private String loanPactNo;//融资合同展示号
	private String ext1;//
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String ext5;//

	/**
	 * @return 唯一主键
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 唯一主键
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
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
	 * @return 客户姓名
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 客户姓名
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
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
	 * @return 合同名称
	 */
	public String getPactName() {
	 	return pactName;
	}
	/**
	 * @设置 合同名称
	 * @param pactName
	 */
	public void setPactName(String pactName) {
	 	this.pactName = pactName;
	}
	/**
	 * @return 合同类型
	 */
	public String getPactType() {
	 	return pactType;
	}
	/**
	 * @设置 合同类型
	 * @param pactType
	 */
	public void setPactType(String pactType) {
	 	this.pactType = pactType;
	}
	/**
	 * @return 甲方
	 */
	public String getFirstParty() {
	 	return firstParty;
	}
	/**
	 * @设置 甲方
	 * @param firstParty
	 */
	public void setFirstParty(String firstParty) {
	 	this.firstParty = firstParty;
	}
	/**
	 * @return 乙方
	 */
	public String getSecondParty() {
	 	return secondParty;
	}
	/**
	 * @设置 乙方
	 * @param secondParty
	 */
	public void setSecondParty(String secondParty) {
	 	this.secondParty = secondParty;
	}
	/**
	 * @return 丙方
	 */
	public String getThirdParty() {
	 	return thirdParty;
	}
	/**
	 * @设置 丙方
	 * @param thirdParty
	 */
	public void setThirdParty(String thirdParty) {
	 	this.thirdParty = thirdParty;
	}
	/**
	 * @return 丁方
	 */
	public String getFourParty() {
	 	return fourParty;
	}
	/**
	 * @设置 丁方
	 * @param fourParty
	 */
	public void setFourParty(String fourParty) {
	 	this.fourParty = fourParty;
	}
	/**
	 * @return 签约日期
	 */
	public String getPactSignDate() {
	 	return pactSignDate;
	}
	/**
	 * @设置 签约日期
	 * @param pactSignDate
	 */
	public void setPactSignDate(String pactSignDate) {
	 	this.pactSignDate = pactSignDate;
	}
	/**
	 * @return 授信总额(合同金额)
	 */
	public Double getCreditSum() {
	 	return creditSum;
	}
	/**
	 * @设置 授信总额(合同金额)
	 * @param creditSum
	 */
	public void setCreditSum(Double creditSum) {
	 	this.creditSum = creditSum;
	}
	/**
	 * @return 涉及授信金额
	 */
	public Double getInvolveCreditSum() {
	 	return involveCreditSum;
	}
	/**
	 * @设置 涉及授信金额
	 * @param involveCreditSum
	 */
	public void setInvolveCreditSum(Double involveCreditSum) {
	 	this.involveCreditSum = involveCreditSum;
	}
	/**
	 * @return 授信余额
	 */
	public Double getAuthBal() {
	 	return authBal;
	}
	/**
	 * @设置 授信余额
	 * @param authBal
	 */
	public void setAuthBal(Double authBal) {
	 	this.authBal = authBal;
	}
	/**
	 * @return 授信期限
	 */
	public Integer getCreditTerm() {
	 	return creditTerm;
	}
	/**
	 * @设置 授信期限
	 * @param creditTerm
	 */
	public void setCreditTerm(Integer creditTerm) {
	 	this.creditTerm = creditTerm;
	}
	/**
	 * @return 开始日期
	 */
	public String getBeginDate() {
	 	return beginDate;
	}
	/**
	 * @设置 开始日期
	 * @param beginDate
	 */
	public void setBeginDate(String beginDate) {
	 	this.beginDate = beginDate;
	}
	/**
	 * @return 结束日期
	 */
	public String getEndDate() {
	 	return endDate;
	}
	/**
	 * @设置 结束日期
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
	 	this.endDate = endDate;
	}
	/**
	 * @return 额度是否可循环
	 */
	public String getIsCeilingLoop() {
	 	return isCeilingLoop;
	}
	/**
	 * @设置 额度是否可循环
	 * @param isCeilingLoop
	 */
	public void setIsCeilingLoop(String isCeilingLoop) {
	 	this.isCeilingLoop = isCeilingLoop;
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
	 * @return 合作平台
	 */
	public String getCooperationPlatform() {
	 	return cooperationPlatform;
	}
	/**
	 * @设置 合作平台
	 * @param cooperationPlatform
	 */
	public void setCooperationPlatform(String cooperationPlatform) {
	 	this.cooperationPlatform = cooperationPlatform;
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
	 * @return 登记
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记
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
	 * @return 授信申请编号
	 */
	public String getCreditAppId() {
	 	return creditAppId;
	}
	/**
	 * @设置 授信申请编号
	 * @param creditAppId
	 */
	public void setCreditAppId(String creditAppId) {
	 	this.creditAppId = creditAppId;
	}
	/**
	 * @return 授信模式。数据字典CREDIT_MODEL1客户授信2项目授信
	 */
	public String getCreditModel() {
	 	return creditModel;
	}
	/**
	 * @设置 授信模式。数据字典CREDIT_MODEL1客户授信2项目授信
	 * @param creditModel
	 */
	public void setCreditModel(String creditModel) {
	 	this.creditModel = creditModel;
	}
	/**
	 * @return 项目展示编号
	 */
	public String getProjectNo() {
	 	return projectNo;
	}
	/**
	 * @设置 项目展示编号
	 * @param projectNo
	 */
	public void setProjectNo(String projectNo) {
	 	this.projectNo = projectNo;
	}
	/**
	 * @return 项目名称
	 */
	public String getProjectName() {
	 	return projectName;
	}
	/**
	 * @设置 项目名称
	 * @param projectName
	 */
	public void setProjectName(String projectName) {
	 	this.projectName = projectName;
	}
	/**
	 * @return 项目合同号
	 */
	public String getProjectPactNo() {
	 	return projectPactNo;
	}
	/**
	 * @设置 项目合同号
	 * @param projectPactNo
	 */
	public void setProjectPactNo(String projectPactNo) {
	 	this.projectPactNo = projectPactNo;
	}
	/**
	 * @return 授信协议编号
	 */
	public String getCreditPactId() {
	 	return creditPactId;
	}
	/**
	 * @设置 授信协议编号
	 * @param creditPactId
	 */
	public void setCreditPactId(String creditPactId) {
	 	this.creditPactId = creditPactId;
	}
	/**
	 * @return 授信协议展示号
	 */
	public String getCreditPactNo() {
	 	return creditPactNo;
	}
	/**
	 * @设置 授信协议展示号
	 * @param creditPactNo
	 */
	public void setCreditPactNo(String creditPactNo) {
	 	this.creditPactNo = creditPactNo;
	}
	/**
	 * @return 业务申请号
	 */
	public String getAppId() {
	 	return appId;
	}
	/**
	 * @设置 业务申请号
	 * @param appId
	 */
	public void setAppId(String appId) {
	 	this.appId = appId;
	}
	/**
	 * @return 融资合同展示号
	 */
	public String getLoanPactNo() {
	 	return loanPactNo;
	}
	/**
	 * @设置 融资合同展示号
	 * @param loanPactNo
	 */
	public void setLoanPactNo(String loanPactNo) {
	 	this.loanPactNo = loanPactNo;
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
}