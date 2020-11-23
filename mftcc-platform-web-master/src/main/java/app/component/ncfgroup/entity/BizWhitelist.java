package app.component.ncfgroup.entity;
import app.base.BaseDomain;
/**
* Title: BizWhitelist.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Dec 14 12:28:45 CST 2017
* @version：1.0
**/
public class BizWhitelist extends BaseDomain {
	private Integer id;//
	private String customerName;//客户名称
	private Integer customerType;//客户类型，0：个人，2：企业，
	private Integer identifiedType;//证件类型，1：身份证、2，军官士兵证，3， 护照，     9.企业营业执照
	private String identifiedNo;//证件号码
	private String customerMobile;//客户手机号码
	private String grantOfficeName;//授信机构代码
	private Integer grantQuote;//授信额度
	private String grantValidition;//授信有效期
	private String grantProId;//授信产品编号， 1. 东风贷，2，车抵贷，3，车商贷，4. 汇达贷
	private Integer grantType;//授信类型：1：单次，2：单额度循环；3：总额固定
	private String workTime;//入职时间
	private String placeBelong;//所属营业所
	private String agentName;//业代姓名
	private Integer monthSaleCount;//平均月销售额
	private Integer monthReturnCount;//平均月回款

	/**
	 * @return 
	 */
	public Integer getId() {
	 	return id;
	}
	/**
	 * @设置 
	 * @param id
	 */
	public void setId(Integer id) {
	 	this.id = id;
	}
	/**
	 * @return 客户名称
	 */
	public String getCustomerName() {
	 	return customerName;
	}
	/**
	 * @设置 客户名称
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
	 	this.customerName = customerName;
	}
	/**
	 * @return 客户类型，0：个人，2：企业，
	 */
	public Integer getCustomerType() {
	 	return customerType;
	}
	/**
	 * @设置 客户类型，0：个人，2：企业，
	 * @param customerType
	 */
	public void setCustomerType(Integer customerType) {
	 	this.customerType = customerType;
	}
	/**
	 * @return 证件类型，1：身份证、2，军官士兵证，3， 护照，     9.企业营业执照
	 */
	public Integer getIdentifiedType() {
	 	return identifiedType;
	}
	/**
	 * @设置 证件类型，1：身份证、2，军官士兵证，3， 护照，     9.企业营业执照
	 * @param identifiedType
	 */
	public void setIdentifiedType(Integer identifiedType) {
	 	this.identifiedType = identifiedType;
	}
	/**
	 * @return 证件号码
	 */
	public String getIdentifiedNo() {
	 	return identifiedNo;
	}
	/**
	 * @设置 证件号码
	 * @param identifiedNo
	 */
	public void setIdentifiedNo(String identifiedNo) {
	 	this.identifiedNo = identifiedNo;
	}
	/**
	 * @return 客户手机号码
	 */
	public String getCustomerMobile() {
	 	return customerMobile;
	}
	/**
	 * @设置 客户手机号码
	 * @param customerMobile
	 */
	public void setCustomerMobile(String customerMobile) {
	 	this.customerMobile = customerMobile;
	}
	/**
	 * @return 授信机构代码
	 */
	public String getGrantOfficeName() {
	 	return grantOfficeName;
	}
	/**
	 * @设置 授信机构代码
	 * @param grantOfficeName
	 */
	public void setGrantOfficeName(String grantOfficeName) {
	 	this.grantOfficeName = grantOfficeName;
	}
	/**
	 * @return 授信额度
	 */
	public Integer getGrantQuote() {
	 	return grantQuote;
	}
	/**
	 * @设置 授信额度
	 * @param grantQuote
	 */
	public void setGrantQuote(Integer grantQuote) {
	 	this.grantQuote = grantQuote;
	}
	/**
	 * @return 授信有效期
	 */
	public String getGrantValidition() {
	 	return grantValidition;
	}
	/**
	 * @设置 授信有效期
	 * @param grantValidition
	 */
	public void setGrantValidition(String grantValidition) {
	 	this.grantValidition = grantValidition;
	}
	/**
	 * @return 授信产品编号， 1. 东风贷，2，车抵贷，3，车商贷，4. 汇达贷
	 */
	public String getGrantProId() {
	 	return grantProId;
	}
	/**
	 * @设置 授信产品编号， 1. 东风贷，2，车抵贷，3，车商贷，4. 汇达贷
	 * @param grantProId
	 */
	public void setGrantProId(String grantProId) {
	 	this.grantProId = grantProId;
	}
	/**
	 * @return 授信类型：1：单次，2：单额度循环；3：总额固定
	 */
	public Integer getGrantType() {
	 	return grantType;
	}
	/**
	 * @设置 授信类型：1：单次，2：单额度循环；3：总额固定
	 * @param grantType
	 */
	public void setGrantType(Integer grantType) {
	 	this.grantType = grantType;
	}
	/**
	 * @return 入职时间
	 */
	public String getWorkTime() {
	 	return workTime;
	}
	/**
	 * @设置 入职时间
	 * @param workTime
	 */
	public void setWorkTime(String workTime) {
	 	this.workTime = workTime;
	}
	/**
	 * @return 所属营业所
	 */
	public String getPlaceBelong() {
	 	return placeBelong;
	}
	/**
	 * @设置 所属营业所
	 * @param placeBelong
	 */
	public void setPlaceBelong(String placeBelong) {
	 	this.placeBelong = placeBelong;
	}
	/**
	 * @return 业代姓名
	 */
	public String getAgentName() {
	 	return agentName;
	}
	/**
	 * @设置 业代姓名
	 * @param agentName
	 */
	public void setAgentName(String agentName) {
	 	this.agentName = agentName;
	}
	/**
	 * @return 平均月销售额
	 */
	public Integer getMonthSaleCount() {
	 	return monthSaleCount;
	}
	/**
	 * @设置 平均月销售额
	 * @param monthSaleCount
	 */
	public void setMonthSaleCount(Integer monthSaleCount) {
	 	this.monthSaleCount = monthSaleCount;
	}
	/**
	 * @return 平均月回款
	 */
	public Integer getMonthReturnCount() {
	 	return monthReturnCount;
	}
	/**
	 * @设置 平均月回款
	 * @param monthReturnCount
	 */
	public void setMonthReturnCount(Integer monthReturnCount) {
	 	this.monthReturnCount = monthReturnCount;
	}
}