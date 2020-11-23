package app.component.risk.entity;
import java.util.List;

import app.base.BaseDomain;
/**
* Title: RiskPreventItemRel.java
* Description:
* @author：@dhcc.com.cn
* @Thu Mar 03 07:18:05 GMT 2016
* @version：1.0
**/
public class RiskPreventItemRel extends BaseDomain {
	private String relNo;
	private String genNo;//场景细分维度编号
	private String itemNo;//预警项目编号
	private String baseItemNo;//基础项目编号
	private String itemDesc;//拦截提示信息
	private String riskLevel;//风险拦截级别
	private String useInd;//是否启用
	private List<String> selectedItmeNos;
	private List<String> riskPreventClasses;
	private String itemType;

	
	
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	/**
	 * @return 场景细分维度编号
	 */
	public String getGenNo() {
	 	return genNo;
	}
	/**
	 * @设置 场景细分维度编号
	 * @param genNo
	 */
	public void setGenNo(String genNo) {
	 	this.genNo = genNo;
	}
	/**
	 * @return 预警项目编号
	 */
	public String getItemNo() {
	 	return itemNo;
	}
	/**
	 * @设置 预警项目编号
	 * @param itemNo
	 */
	public void setItemNo(String itemNo) {
	 	this.itemNo = itemNo;
	}
	/**
	 * @return 拦截提示信息
	 */
	public String getItemDesc() {
	 	return itemDesc;
	}
	/**
	 * @设置 拦截提示信息
	 * @param itemDesc
	 */
	public void setItemDesc(String itemDesc) {
	 	this.itemDesc = itemDesc;
	}
	/**
	 * @return 风险拦截级别
	 */
	public String getRiskLevel() {
	 	return riskLevel;
	}
	/**
	 * @设置 风险拦截级别
	 * @param riskLevel
	 */
	public void setRiskLevel(String riskLevel) {
	 	this.riskLevel = riskLevel;
	}
	/**
	 * @return 是否启用
	 */
	public String getUseInd() {
	 	return useInd;
	}
	/**
	 * @设置 是否启用
	 * @param useInd
	 */
	public void setUseInd(String useInd) {
	 	this.useInd = useInd;
	}
	public List<String> getSelectedItmeNos() {
		return selectedItmeNos;
	}
	public void setSelectedItmeNos(List<String> selectedItmeNos) {
		this.selectedItmeNos = selectedItmeNos;
	}
	public String getRelNo() {
		return relNo;
	}
	public void setRelNo(String relNo) {
		this.relNo = relNo;
	}
	public List<String> getRiskPreventClasses() {
		return riskPreventClasses;
	}
	public void setRiskPreventClasses(List<String> riskPreventClasses) {
		this.riskPreventClasses = riskPreventClasses;
	}
	public String getBaseItemNo() {
		return baseItemNo;
	}
	public void setBaseItemNo(String baseItemNo) {
		this.baseItemNo = baseItemNo;
	}
}