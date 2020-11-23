package app.component.risk.entity;
import java.util.List;

import app.base.BaseDomain;
import app.component.nmd.entity.ParmDic;
/**
* Title: RiskItemThreashode.java
* Description:
* @author：@dhcc.com.cn
* @Tue Feb 23 06:49:07 GMT 2016
* @version：1.0
**/
public class RiskItemThreashode extends BaseDomain {
	private String itemNo;//方案编号
	private String itemName;//方案名称
	private String threashodeName;//阀值变量名
	private String threashodeType;//阀值类型
	private String threashodeValueType;//数值类型
	private String threashodeValue;//数值
	private String dicKeyName;//字典项名称
	private String dicKeyValue;//字典项数值
	private String threashodeChnName;//阀值变量中文显示
	private List<ParmDic> parmDic;
	
	private String threashodeIndex;
	private String maxValue;
	private String minValue;
	private String symbol;
	private String riskLevel;
	
	
	
	
	public String getThreashodeIndex() {
		return threashodeIndex;
	}
	public void setThreashodeIndex(String threashodeIndex) {
		this.threashodeIndex = threashodeIndex;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	/**
	 * @return 方案编号
	 */
	public String getItemNo() {
	 	return itemNo;
	}
	/**
	 * @设置 方案编号
	 * @param itemNo
	 */
	public void setItemNo(String itemNo) {
	 	this.itemNo = itemNo;
	}
	/**
	 * @return 方案名称
	 */
	public String getItemName() {
	 	return itemName;
	}
	/**
	 * @设置 方案名称
	 * @param itemName
	 */
	public void setItemName(String itemName) {
	 	this.itemName = itemName;
	}
	/**
	 * @return 阀值变量名
	 */
	public String getThreashodeName() {
	 	return threashodeName;
	}
	/**
	 * @设置 阀值变量名
	 * @param threashodeName
	 */
	public void setThreashodeName(String threashodeName) {
	 	this.threashodeName = threashodeName;
	}
	/**
	 * @return 阀值类型
	 */
	public String getThreashodeType() {
	 	return threashodeType;
	}
	/**
	 * @设置 阀值类型
	 * @param threashodeType
	 */
	public void setThreashodeType(String threashodeType) {
	 	this.threashodeType = threashodeType;
	}
	/**
	 * @return 数值类型
	 */
	public String getThreashodeValueType() {
	 	return threashodeValueType;
	}
	/**
	 * @设置 数值类型
	 * @param threashodeValueType
	 */
	public void setThreashodeValueType(String threashodeValueType) {
	 	this.threashodeValueType = threashodeValueType;
	}
	/**
	 * @return 数值
	 */
	public String getThreashodeValue() {
	 	return threashodeValue;
	}
	/**
	 * @设置 数值
	 * @param threashodeValue
	 */
	public void setThreashodeValue(String threashodeValue) {
	 	this.threashodeValue = threashodeValue;
	}
	public String getDicKeyName() {
		return dicKeyName;
	}
	public void setDicKeyName(String dicKeyName) {
		this.dicKeyName = dicKeyName;
	}
	public String getDicKeyValue() {
		return dicKeyValue;
	}
	public void setDicKeyValue(String dicKeyValue) {
		this.dicKeyValue = dicKeyValue;
	}
	public String getThreashodeChnName() {
		return threashodeChnName;
	}
	public void setThreashodeChnName(String threashodeChnName) {
		this.threashodeChnName = threashodeChnName;
	}
	public List<ParmDic> getParmDic() {
		return parmDic;
	}
	public void setParmDic(List<ParmDic> parmDic) {
		this.parmDic = parmDic;
	}
	
	
	
	
	
}