package app.component.pact.entity;
import java.util.List;

import app.base.BaseDomain;
/**
* Title: ThirdPartyPayForAnother.java
* Description:第三方代付
* @author：kaifa@dhcc.com.cn
* @Mon Aug 14 15:16:07 CST 2017
* @version：1.0
**/
public class ThirdPartyPayForAnother extends BaseDomain {
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String appName;//项目名称
	private String kindName;//产品名称
	private String pactNo;//合同编号
	private Double pactAmt;//合同金额
	private Double fincRate;//融资款利率
	private String termType;//期限类型 1-月 2-天
	private Integer term;//申请期限数值
	private String termShow;//申请期限显示
	private String appId;//申请Id
	private String fincId;//借据Id
	private List<String> exportFincId;//导出的fincId
	

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
	 * @return 产品名称
	 */
	public String getKindName() {
	 	return kindName;
	}
	/**
	 * @设置 产品名称
	 * @param kindName
	 */
	public void setKindName(String kindName) {
	 	this.kindName = kindName;
	}
	/**
	 * @return 合同编号
	 */
	public String getPactNo() {
	 	return pactNo;
	}
	/**
	 * @设置 合同编号
	 * @param pactNo
	 */
	public void setPactNo(String pactNo) {
	 	this.pactNo = pactNo;
	}
	/**
	 * @return 合同金额
	 */
	public Double getPactAmt() {
	 	return pactAmt;
	}
	/**
	 * @设置 合同金额
	 * @param pactAmt
	 */
	public void setPactAmt(Double pactAmt) {
	 	this.pactAmt = pactAmt;
	}
	/**
	 * @return 融资款利率
	 */
	public Double getFincRate() {
	 	return fincRate;
	}
	/**
	 * @设置 融资款利率
	 * @param fincRate
	 */
	public void setFincRate(Double fincRate) {
	 	this.fincRate = fincRate;
	}
	/**
	 * @return 期限类型 1-月 2-天
	 */
	public String getTermType() {
	 	return termType;
	}
	/**
	 * @设置 期限类型 1-月 2-天
	 * @param termType
	 */
	public void setTermType(String termType) {
	 	this.termType = termType;
	}
	/**
	 * @return 申请期限数值
	 */
	public Integer getTerm() {
	 	return term;
	}
	/**
	 * @设置 申请期限数值
	 * @param term
	 */
	public void setTerm(Integer term) {
	 	this.term = term;
	}
	/**
	 * @return 申请期限显示
	 */
	public String getTermShow() {
	 	return termShow;
	}
	/**
	 * @设置 申请期限显示
	 * @param termShow
	 */
	public void setTermShow(String termShow) {
	 	this.termShow = termShow;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
	}
	public List<String> getExportFincId() {
		return exportFincId;
	}
	public void setExportFincId(List<String> exportFincId) {
		this.exportFincId = exportFincId;
	}
}