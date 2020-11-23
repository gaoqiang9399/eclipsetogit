package app.component.auth.entity;
import app.base.BaseDomain;
/**
* Title: MfCusCreditChildContract.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sun Apr 08 17:57:57 CST 2018
* @version：1.0
**/
public class MfCusCreditChildContract extends BaseDomain {
	private String childPactId;//子合同编号
	private String pactNo;//合同展示号
	private String agenciesUid;//资金机构编号
	private String agenciesName;//资金机构名称
	private Double creditSum;//合作额度
	private Double authBal;//合作授信余额
	private String isCeilingLoop;//额度是否可循环
	private Integer creditTerm;//授信期限
	private String beginDate;//开始日期
	private String endDate;//结束日期
	private String pactSignDate;//签约日期
	private String remark;//备注
	private String isPracticable;//评审意见是否落实
	private String creditAppId;//授信申请编号
	private String cusNo;//客户号
	private String cusName;//客户姓名
	private String appName;//项目名称
	private String creditUseType;//用信类型
	private String appId;//申请编号
	private String creditSts;//授信状态
	private String opNo;//登记人号
	private String opName;//登记人名称
	private String brNo;//登记机构号
	private String brName;//登记机构名称
	private String regDate;//登记时间
	private String regTime;//登记时间
	private String lstModDate;//最近修改日期
	private String lstModTime;//最近修改时间
	private String projectNo;//项目编号
	private String projectName;//项目名称
	private String creditType;//授信类型1新增2调整
	private Double addAmt;//追加额度

	/**
	 * @return 子合同编号
	 */
	public String getChildPactId() {
	 	return childPactId;
	}
	/**
	 * @设置 子合同编号
	 * @param childPactId
	 */
	public void setChildPactId(String childPactId) {
	 	this.childPactId = childPactId;
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
	 * @return 资金机构编号
	 */
	public String getAgenciesUid() {
	 	return agenciesUid;
	}
	/**
	 * @设置 资金机构编号
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
	 * @return 合作额度
	 */
	public Double getCreditSum() {
	 	return creditSum;
	}
	/**
	 * @设置 合作额度
	 * @param creditSum
	 */
	public void setCreditSum(Double creditSum) {
	 	this.creditSum = creditSum;
	}
	/**
	 * @return 合作授信余额
	 */
	public Double getAuthBal() {
	 	return authBal;
	}
	/**
	 * @设置 合作授信余额
	 * @param authBal
	 */
	public void setAuthBal(Double authBal) {
	 	this.authBal = authBal;
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
	 * @return 评审意见是否落实
	 */
	public String getIsPracticable() {
	 	return isPracticable;
	}
	/**
	 * @设置 评审意见是否落实
	 * @param isPracticable
	 */
	public void setIsPracticable(String isPracticable) {
	 	this.isPracticable = isPracticable;
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
	 * @return 项目名称
	 */
	public String getAppName() {
	 	return appName;
	}
	/**
	 * @设置 项目名称
	 * @param appName
	 */
	public void setAppName(String appName) {
	 	this.appName = appName;
	}
	/**
	 * @return 用信类型
	 */
	public String getCreditUseType() {
	 	return creditUseType;
	}
	/**
	 * @设置 用信类型
	 * @param creditUseType
	 */
	public void setCreditUseType(String creditUseType) {
	 	this.creditUseType = creditUseType;
	}
	/**
	 * @return 申请编号
	 */
	public String getAppId() {
	 	return appId;
	}
	/**
	 * @设置 申请编号
	 * @param appId
	 */
	public void setAppId(String appId) {
	 	this.appId = appId;
	}
	/**
	 * @return 授信状态
	 */
	public String getCreditSts() {
	 	return creditSts;
	}
	/**
	 * @设置 授信状态
	 * @param creditSts
	 */
	public void setCreditSts(String creditSts) {
	 	this.creditSts = creditSts;
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
	public String getRegDate() {
	 	return regDate;
	}
	/**
	 * @设置 登记时间
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
	 	this.regDate = regDate;
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
	 * @return 最近修改日期
	 */
	public String getLstModDate() {
	 	return lstModDate;
	}
	/**
	 * @设置 最近修改日期
	 * @param lstModDate
	 */
	public void setLstModDate(String lstModDate) {
	 	this.lstModDate = lstModDate;
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
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getCreditType() {
		return creditType;
	}
	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}
	public Double getAddAmt() {
		return addAmt;
	}
	public void setAddAmt(Double addAmt) {
		this.addAmt = addAmt;
	}
}