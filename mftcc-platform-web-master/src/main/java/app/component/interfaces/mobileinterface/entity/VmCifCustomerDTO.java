package app.component.interfaces.mobileinterface.entity;
import app.base.BaseDomain;
/**
* Title: VmCifCustomerDTO.java
* Description:完善个人信息数据
* @author：kaifa@dhcc.com.cn
* @Fri May 06 10:14:21 CST 2017
* @version：1.0
**/
public class VmCifCustomerDTO extends BaseDomain {
	private String cusNo;//客户ID
	private String cusName;//客户名称
	private String idType;//证件类型
	private String idNum;//证件号码
	private String nationality;//民族
	private String careaProvice;//客户所属地区 三级地名 如 河南省郑州市二七区
	private String regHomeAddre;//户籍地址的详细地址
	private String cusMngNo;//客户经理编号
	private String cusMngName;//客户经理姓名
	private String cusTel;
	private String openid; //三方登录标识
	private String type; //登录类型 
	private String cusType; //客户类型 
	private String creditNum; //信用卡id
	private String phone; //手机运营商预留电话
	//先锋支付校验次数
	private Integer importIdCardTime;//输入身份证号所用时间毫秒单位
	private Integer importBankCardTime;//输入银行卡所用时间毫秒单位
	private Integer errorIdCardCheckCount;//输入借款人身份证号码校验错误次数
	private Integer errorCreditCardCheckCount;//输入借款人信用卡号校验错误次数
	//渠道来源 1 凡易贷 2 得亨快贷
	private String channelSourceNo;
	
	//信用卡保存标识 
	private String creditFlag;
	
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
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getCareaProvice() {
		return careaProvice;
	}
	public void setCareaProvice(String careaProvice) {
		this.careaProvice = careaProvice;
	}
	public String getRegHomeAddre() {
		return regHomeAddre;
	}
	public void setRegHomeAddre(String regHomeAddre) {
		this.regHomeAddre = regHomeAddre;
	}
	public String getCusMngNo() {
		return cusMngNo;
	}
	public void setCusMngNo(String cusMngNo) {
		this.cusMngNo = cusMngNo;
	}
	public String getCusMngName() {
		return cusMngName;
	}
	public void setCusMngName(String cusMngName) {
		this.cusMngName = cusMngName;
	}
	public String getCusTel() {
		return cusTel;
	}
	public void setCusTel(String cusTel) {
		this.cusTel = cusTel;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getCusType() {
		return cusType;
	}
	public void setCusType(String cusType) {
		this.cusType = cusType;
	}
	public String getCreditNum() {
		return creditNum;
	}
	public void setCreditNum(String creditNum) {
		this.creditNum = creditNum;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getImportIdCardTime() {
		return importIdCardTime;
	}
	public void setImportIdCardTime(Integer importIdCardTime) {
		this.importIdCardTime = importIdCardTime;
	}
	public Integer getImportBankCardTime() {
		return importBankCardTime;
	}
	public void setImportBankCardTime(Integer importBankCardTime) {
		this.importBankCardTime = importBankCardTime;
	}
	public Integer getErrorIdCardCheckCount() {
		return errorIdCardCheckCount;
	}
	public void setErrorIdCardCheckCount(Integer errorIdCardCheckCount) {
		this.errorIdCardCheckCount = errorIdCardCheckCount;
	}
	public Integer getErrorCreditCardCheckCount() {
		return errorCreditCardCheckCount;
	}
	public void setErrorCreditCardCheckCount(Integer errorCreditCardCheckCount) {
		this.errorCreditCardCheckCount = errorCreditCardCheckCount;
	}
	public String getChannelSourceNo() {
		return channelSourceNo;
	}
	public void setChannelSourceNo(String channelSourceNo) {
		this.channelSourceNo = channelSourceNo;
	}
	public String getCreditFlag() {
		return creditFlag;
	}
	public void setCreditFlag(String creditFlag) {
		this.creditFlag = creditFlag;
	}
	
	
}