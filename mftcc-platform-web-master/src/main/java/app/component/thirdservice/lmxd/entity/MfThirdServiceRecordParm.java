package app.component.thirdservice.lmxd.entity;
import app.base.BaseDomain;
/**
* Title: MfThirdServiceRecord.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Dec 19 15:46:55 CST 2017
* @version：1.0
**/
public class MfThirdServiceRecordParm extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String appId;//申请号
	private String appName;//项目名称
	private String kindName;//产品名称
	private String contactsTel;//联系电话
	private String idNum;//证件号码或者社会信用代码
	private String thirdQueryType;//查询类型
	private String accountNo;//银行卡号
	private String caseId;//案件ID
	private String dataType;//数据类型
	private String operator;//运营商
	private String emergencyContact;//紧急联系人
	private String personalPictureCode;//查询的字段编号(编号规则见数据字典)，查询所有字段值可传“all”。查询多个指标可用逗号进行分割。
	private String number;
	private String objectId;//GPS设备ID
	private String idType;//客户查询方式 name 客户名  idType 证件号
	private String chkCertNoTypeList;//房产证书类型0房地产权证书 1不动产权证书
	private String radioSearchType;//房产查询方式 0分户 1分栋
	private String txtNewCertNoYear; //不动产权证书登记时间
	private String number1;//房产产权状态查询中 客户不动产权证书
	private String lookTime;//查看时间段
	private String platformType;//平台类型
	private String loseCreditBlacklistType;//失信黑名单查询类型
	private String beginTime;//起始时间
	private String endTime;//截止时间
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
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getContactsTel() {
		return contactsTel;
	}
	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getThirdQueryType() {
		return thirdQueryType;
	}
	public void setThirdQueryType(String thirdQueryType) {
		this.thirdQueryType = thirdQueryType;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getPersonalPictureCode() {
		return personalPictureCode;
	}
	public void setPersonalPictureCode(String personalPictureCode) {
		this.personalPictureCode = personalPictureCode;
	}
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getIdType() { return idType; }
	public void setIdType(String idType) { this.idType = idType; }
	public String getChkCertNoTypeList() {
		return chkCertNoTypeList;
	}
	public void setChkCertNoTypeList(String chkCertNoTypeList) {
		this.chkCertNoTypeList = chkCertNoTypeList;
	}
	public String getRadioSearchType() {
		return radioSearchType;
	}
	public void setRadioSearchType(String radioSearchType) {
		this.radioSearchType = radioSearchType;
	}
	public String getTxtNewCertNoYear() {
		return txtNewCertNoYear;
	}
	public void setTxtNewCertNoYear(String txtNewCertNoYear) {
		this.txtNewCertNoYear = txtNewCertNoYear;
	}
	public String getNumber1() {
		return number1;
	}
	public void setNumber1(String number1) {
		this.number1 = number1;
	}
	public String getPlatformType() { return platformType; }
	public void setPlatformType(String platformType) { this.platformType = platformType; }
	public String getLookTime() { return lookTime; }
	public void setLookTime(String lookTime) { this.lookTime = lookTime; }
	public String getLoseCreditBlacklistType() { return loseCreditBlacklistType; }
	public void setLoseCreditBlacklistType(String loseCreditBlacklistType) { this.loseCreditBlacklistType = loseCreditBlacklistType; }
	public String getBeginTime() { return beginTime; }
	public void setBeginTime(String beginTime) { this.beginTime = beginTime; }
	public String getEndTime() { return endTime; }
	public void setEndTime(String endTime) { this.endTime = endTime; }
	
}