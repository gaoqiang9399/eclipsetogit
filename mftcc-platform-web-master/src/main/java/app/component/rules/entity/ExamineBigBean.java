/**
 * Copyright (C) DXHM 版权所有
 * 文件名： ExamineBigBean.java
 * 包名： app.component.rules.entity
 * 说明：
 * @author 沈浩兵
 * @date 2017-2-17 上午11:55:44
 * @version V1.0
 */ 
package app.component.rules.entity;

import java.util.Map;

/**
 * 类名： ExamineBigBean
 * 描述：贷后检查指标大实体
 * @author 沈浩兵
 * @date 2017-2-17 上午11:55:44
 *
 *
 */
public class ExamineBigBean {
	//经营情况
	private String manageStatus;
	//还款情况
	private String repayStatus;
	//贷款用途是否挪用
	private String fincUseeMbezzle;
	//抵质押物价值有无变化
	private String pleWorthChange;
	//贷款资金流向是否与合同一致
	private String amtFlowFitPact;
	//借款企业近期负债情况
	private String cusLiabiSta;
	//借款企业近期资产情况
	private String cusAssetSta;
	//借款企业资产负债率情况
	private String cusAssetAebtRatio;
	//借款企业银行账户往来情况
	private String cusBnakAccDeal;
	//借款企业所在行业是否发生重大变故
	private String bussAccident;
	//借款企业经营利润情况
	private String cusProfitSta;
	//借款企业贷后是否有其他投资
	private String otherInvest;
	//借款企业管理层是否变动
	private String managChange;
	//担保企业经营是否正常
	private String guaCusBussSta;
	//仓储企业经营是否正常
	private String storCusBussSta;
	//物流企业经营是否正常
	private String logCusBussSta;
	
	//获得结果
	private Map<String,Object> resultMap;
	public String getManageStatus() {
		return manageStatus;
	}
	public void setManageStatus(String manageStatus) {
		this.manageStatus = manageStatus;
	}
	public String getRepayStatus() {
		return repayStatus;
	}
	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}
	public String getFincUseeMbezzle() {
		return fincUseeMbezzle;
	}
	public void setFincUseeMbezzle(String fincUseeMbezzle) {
		this.fincUseeMbezzle = fincUseeMbezzle;
	}
	public String getPleWorthChange() {
		return pleWorthChange;
	}
	public void setPleWorthChange(String pleWorthChange) {
		this.pleWorthChange = pleWorthChange;
	}
	public Map<String, Object> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	public String getAmtFlowFitPact() {
		return amtFlowFitPact;
	}
	public void setAmtFlowFitPact(String amtFlowFitPact) {
		this.amtFlowFitPact = amtFlowFitPact;
	}
	public String getCusLiabiSta() {
		return cusLiabiSta;
	}
	public void setCusLiabiSta(String cusLiabiSta) {
		this.cusLiabiSta = cusLiabiSta;
	}
	public String getCusAssetSta() {
		return cusAssetSta;
	}
	public void setCusAssetSta(String cusAssetSta) {
		this.cusAssetSta = cusAssetSta;
	}
	public String getCusAssetAebtRatio() {
		return cusAssetAebtRatio;
	}
	public void setCusAssetAebtRatio(String cusAssetAebtRatio) {
		this.cusAssetAebtRatio = cusAssetAebtRatio;
	}
	public String getCusBnakAccDeal() {
		return cusBnakAccDeal;
	}
	public void setCusBnakAccDeal(String cusBnakAccDeal) {
		this.cusBnakAccDeal = cusBnakAccDeal;
	}
	public String getBussAccident() {
		return bussAccident;
	}
	public void setBussAccident(String bussAccident) {
		this.bussAccident = bussAccident;
	}
	public String getCusProfitSta() {
		return cusProfitSta;
	}
	public void setCusProfitSta(String cusProfitSta) {
		this.cusProfitSta = cusProfitSta;
	}
	public String getOtherInvest() {
		return otherInvest;
	}
	public void setOtherInvest(String otherInvest) {
		this.otherInvest = otherInvest;
	}
	public String getManagChange() {
		return managChange;
	}
	public void setManagChange(String managChange) {
		this.managChange = managChange;
	}
	public String getGuaCusBussSta() {
		return guaCusBussSta;
	}
	public void setGuaCusBussSta(String guaCusBussSta) {
		this.guaCusBussSta = guaCusBussSta;
	}
	public String getStorCusBussSta() {
		return storCusBussSta;
	}
	public void setStorCusBussSta(String storCusBussSta) {
		this.storCusBussSta = storCusBussSta;
	}
	public String getLogCusBussSta() {
		return logCusBussSta;
	}
	public void setLogCusBussSta(String logCusBussSta) {
		this.logCusBussSta = logCusBussSta;
	}
	
}
