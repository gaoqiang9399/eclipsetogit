package app.component.pact.entity;
import java.util.List;

import app.base.BaseDomain;
/**
* Title: ThirdPartyGetForAnother.java
* Description:第三方代收
* @author：kaifa@dhcc.com.cn
* @Mon Aug 14 16:34:21 CST 2017
* @version：1.0
**/
public class ThirdPartyGetForAnother extends BaseDomain {
	private String fincId;//借据号
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String appName;//项目名称
	private String kindName;//产品名称
	private String pactNo;//合同编号
	private Double principalSum;//应收本金(元)
	private Double incomeSum;//应收利息(元)
	private Double feeSum;//应收费用(元)
	private Double overIncomeSum;//应收罚息(元)
	private Double penaltySum;//应收违约金(元)
	private Double totalSum;//应收总额(元)
	private String returnDate;//到期日期
	private String appId;//申请id
	private List<String> exportFincId;//导出的fincId
	
	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
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
	 * @return 应收本金(元)
	 */
	public Double getPrincipalSum() {
	 	return principalSum;
	}
	/**
	 * @设置 应收本金(元)
	 * @param principalSum
	 */
	public void setPrincipalSum(Double principalSum) {
	 	this.principalSum = principalSum;
	}
	/**
	 * @return 应收利息(元)
	 */
	public Double getIncomeSum() {
	 	return incomeSum;
	}
	/**
	 * @设置 应收利息(元)
	 * @param incomeSum
	 */
	public void setIncomeSum(Double incomeSum) {
	 	this.incomeSum = incomeSum;
	}
	/**
	 * @return 应收费用(元)
	 */
	public Double getFeeSum() {
	 	return feeSum;
	}
	/**
	 * @设置 应收费用(元)
	 * @param feeSum
	 */
	public void setFeeSum(Double feeSum) {
	 	this.feeSum = feeSum;
	}
	/**
	 * @return 应收罚息(元)
	 */
	public Double getOverIncomeSum() {
	 	return overIncomeSum;
	}
	/**
	 * @设置 应收罚息(元)
	 * @param overIncomeSum
	 */
	public void setOverIncomeSum(Double overIncomeSum) {
	 	this.overIncomeSum = overIncomeSum;
	}
	/**
	 * @return 应收违约金(元)
	 */
	public Double getPenaltySum() {
	 	return penaltySum;
	}
	/**
	 * @设置 应收违约金(元)
	 * @param penaltyIncome
	 */
	public void setPenaltySum(Double penaltySum) {
	 	this.penaltySum = penaltySum;
	}
	/**
	 * @return 应收总额(元)
	 */
	public Double getTotalSum() {
	 	return totalSum;
	}
	/**
	 * @设置 应收总额(元)
	 * @param totalSum
	 */
	public void setTotalSum(Double totalSum) {
	 	this.totalSum = totalSum;
	}
	/**
	 * @return 到期日期
	 */
	public String getReturnDate() {
	 	return returnDate;
	}
	/**
	 * @设置 到期日期
	 * @param returnDate
	 */
	public void setReturnDate(String returnDate) {
	 	this.returnDate = returnDate;
	}
	public List<String> getExportFincId() {
		return exportFincId;
	}
	public void setExportFincId(List<String> exportFincId) {
		this.exportFincId = exportFincId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}