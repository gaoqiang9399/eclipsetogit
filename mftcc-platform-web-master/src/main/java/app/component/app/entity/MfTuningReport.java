package app.component.app.entity;
import app.base.BaseDomain;
/**
* Title: MfTuningReport.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Jun 13 19:48:40 CST 2017
* @version：1.0
**/
public class MfTuningReport extends BaseDomain {
	private String reportId;//唯一编号
	private String appId;//申请号
	private String pactId;//合同号
	private String cusNo;//客户编号
	private String cusName;//客户名称
	private String humanLaw;//人法查询
	private String dishonest;//失信查询
	private String threeParty;//三方征信查询
	private String houseConditions;//下户房屋情况
	private String paymentFrom;//还款来源
	private String suppData;//补充资料
	private String reminder;//提醒
	private String summary;//总结
	private String opinion;//意见
	private String guarantee;//担保信息
	private String opNo;//登记人号
	private String opName;//登记人名称
	private String brNo;//登记机构号
	private String brName;//登记机构名称
	private String regTime;//登记时间
	private String lstModTime;//最近修改时间
	private String ext1;//
	private String ext2;//
	private String ext3;//
	
	private String manageOpNo;//业务客户经理编号
	private String manageOpName;//业务客户经理名称
	private String viewOpNo;//面访人员编号
	private String viewOpName;//面访人员姓名

	/**
	 * @return 唯一编号
	 */
	public String getReportId() {
	 	return reportId;
	}
	/**
	 * @设置 唯一编号
	 * @param reportId
	 */
	public void setReportId(String reportId) {
	 	this.reportId = reportId;
	}
	/**
	 * @return 申请号
	 */
	public String getAppId() {
	 	return appId;
	}
	/**
	 * @设置 申请号
	 * @param appId
	 */
	public void setAppId(String appId) {
	 	this.appId = appId;
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
	 * @return 客户编号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户编号
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
	 * @return 人法查询
	 */
	public String getHumanLaw() {
	 	return humanLaw;
	}
	/**
	 * @设置 人法查询
	 * @param humanLaw
	 */
	public void setHumanLaw(String humanLaw) {
	 	this.humanLaw = humanLaw;
	}
	/**
	 * @return 失信查询
	 */
	public String getDishonest() {
	 	return dishonest;
	}
	/**
	 * @设置 失信查询
	 * @param dishonest
	 */
	public void setDishonest(String dishonest) {
	 	this.dishonest = dishonest;
	}
	/**
	 * @return 三方征信查询
	 */
	public String getThreeParty() {
	 	return threeParty;
	}
	/**
	 * @设置 三方征信查询
	 * @param threeParty
	 */
	public void setThreeParty(String threeParty) {
	 	this.threeParty = threeParty;
	}
	/**
	 * @return 下户房屋情况
	 */
	public String getHouseConditions() {
	 	return houseConditions;
	}
	/**
	 * @设置 下户房屋情况
	 * @param houseConditions
	 */
	public void setHouseConditions(String houseConditions) {
	 	this.houseConditions = houseConditions;
	}
	/**
	 * @return 还款来源
	 */
	public String getPaymentFrom() {
	 	return paymentFrom;
	}
	/**
	 * @设置 还款来源
	 * @param paymentFrom
	 */
	public void setPaymentFrom(String paymentFrom) {
	 	this.paymentFrom = paymentFrom;
	}
	/**
	 * @return 补充资料
	 */
	public String getSuppData() {
	 	return suppData;
	}
	/**
	 * @设置 补充资料
	 * @param suppData
	 */
	public void setSuppData(String suppData) {
	 	this.suppData = suppData;
	}
	/**
	 * @return 提醒
	 */
	public String getReminder() {
	 	return reminder;
	}
	/**
	 * @设置 提醒
	 * @param reminder
	 */
	public void setReminder(String reminder) {
	 	this.reminder = reminder;
	}
	/**
	 * @return 总结
	 */
	public String getSummary() {
	 	return summary;
	}
	/**
	 * @设置 总结
	 * @param summary
	 */
	public void setSummary(String summary) {
	 	this.summary = summary;
	}
	/**
	 * @return 意见
	 */
	public String getOpinion() {
	 	return opinion;
	}
	/**
	 * @设置 意见
	 * @param opinion
	 */
	public void setOpinion(String opinion) {
	 	this.opinion = opinion;
	}
	/**
	 * @return 担保信息
	 */
	public String getGuarantee() {
	 	return guarantee;
	}
	/**
	 * @设置 担保信息
	 * @param guarantee
	 */
	public void setGuarantee(String guarantee) {
	 	this.guarantee = guarantee;
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
	public String getManageOpNo() {
		return manageOpNo;
	}
	public void setManageOpNo(String manageOpNo) {
		this.manageOpNo = manageOpNo;
	}
	public String getManageOpName() {
		return manageOpName;
	}
	public void setManageOpName(String manageOpName) {
		this.manageOpName = manageOpName;
	}
	public String getViewOpNo() {
		return viewOpNo;
	}
	public void setViewOpNo(String viewOpNo) {
		this.viewOpNo = viewOpNo;
	}
	public String getViewOpName() {
		return viewOpName;
	}
	public void setViewOpName(String viewOpName) {
		this.viewOpName = viewOpName;
	}
	
}