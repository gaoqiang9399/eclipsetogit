package app.component.risk.entity;
import java.util.List;

import app.base.BaseDomain;
/**
* Title: RiskPreventSceGen.java
* Description:
* @author：@dhcc.com.cn
* @Thu Mar 03 07:15:46 GMT 2016
* @version：1.0
**/
public class RiskPreventSceGen extends BaseDomain {
	private String genNo;//场景细分维度编号
	private String genName;
	private String scNo;//业务场景编号
	private String dime;//维度
	private String dimeVal;//
	private String dimeValDes;//维度选项描述
	private String isDefault;//是否为默认生成项
	private List<String> dimeVals;
	private String cusType;
	private String cusTypeDes;
	private String busModel;
	private String busModelDes;
	private String vouType;
	private String vouTypeDes;
	private String kindNo;
	private String kindName;
	private String rulesNo;
	private String pageStr;
	private String busType;
	
	
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getPageStr() {
		return pageStr;
	}
	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
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
	 * @return 业务场景编号
	 */
	public String getScNo() {
	 	return scNo;
	}
	/**
	 * @设置 业务场景编号
	 * @param scNo
	 */
	public void setScNo(String scNo) {
	 	this.scNo = scNo;
	}
	/**
	 * @return 维度
	 */
	public String getDime() {
	 	return dime;
	}
	/**
	 * @设置 维度
	 * @param dime
	 */
	public void setDime(String dime) {
	 	this.dime = dime;
	}
	/**
	 * @return 
	 */
	public String getDimeVal() {
	 	return dimeVal;
	}
	/**
	 * @设置 
	 * @param dimeVal
	 */
	public void setDimeVal(String dimeVal) {
	 	this.dimeVal = dimeVal;
	}
	/**
	 * @return 是否为默认生成项
	 */
	public String getIsDefault() {
	 	return isDefault;
	}
	/**
	 * @设置 是否为默认生成项
	 * @param isDefault
	 */
	public void setIsDefault(String isDefault) {
	 	this.isDefault = isDefault;
	}
	public List<String> getDimeVals() {
		return dimeVals;
	}
	public void setDimeVals(List<String> dimeVals) {
		this.dimeVals = dimeVals;
	}
	public String getGenName() {
		return genName;
	}
	public void setGenName(String genName) {
		this.genName = genName;
	}
	public String getCusType() {
		return cusType;
	}
	public void setCusType(String cusType) {
		this.cusType = cusType;
	}
	public String getBusModel() {
		return busModel;
	}
	public void setBusModel(String busModel) {
		this.busModel = busModel;
	}
	public String getVouType() {
		return vouType;
	}
	public void setVouType(String vouType) {
		this.vouType = vouType;
	}
	public String getKindNo() {
		return kindNo;
	}
	public void setKindNo(String kindNo) {
		this.kindNo = kindNo;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public String getRulesNo() {
		return rulesNo;
	}
	public void setRulesNo(String rulesNo) {
		this.rulesNo = rulesNo;
	}
	public String getDimeValDes() {
		return dimeValDes;
	}
	public void setDimeValDes(String dimeValDes) {
		this.dimeValDes = dimeValDes;
	}
	public String getCusTypeDes() {
		return cusTypeDes;
	}
	public void setCusTypeDes(String cusTypeDes) {
		this.cusTypeDes = cusTypeDes;
	}
	public String getBusModelDes() {
		return busModelDes;
	}
	public void setBusModelDes(String busModelDes) {
		this.busModelDes = busModelDes;
	}
	public String getVouTypeDes() {
		return vouTypeDes;
	}
	public void setVouTypeDes(String vouTypeDes) {
		this.vouTypeDes = vouTypeDes;
	}
	
	
}