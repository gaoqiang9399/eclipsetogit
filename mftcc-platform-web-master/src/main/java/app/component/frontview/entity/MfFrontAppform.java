package app.component.frontview.entity;
import app.base.BaseDomain;
/**
* Title: MfFrontAppform.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Jun 22 10:18:20 CST 2017
* @version：1.0
**/
public class MfFrontAppform extends BaseDomain {
	private String id;//
	private String kindNo;//产品编号
	private String fieldName;//数据库中的字段名称
	private String fieldUnit;//字段单位
	private String mobleShow;//是否在移动端展示该数据
	private String pcShow;//是否在交易端展示该字段，0不展示，1展示
	private String defaultVal;//字段默认值
	private String mobileUse;//移动端是否使用该字段，0不使用，1使用
	private String pcUse;//pc交易端是否使用该字段，0不使用，1使用
	private String fieldLabel;//字段在交易端显示的中文名称

	/**
	 * @return 
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 产品编号
	 */
	public String getKindNo() {
	 	return kindNo;
	}
	/**
	 * @设置 产品编号
	 * @param kindNo
	 */
	public void setKindNo(String kindNo) {
	 	this.kindNo = kindNo;
	}
	/**
	 * @return 数据库中的字段名称
	 */
	public String getFieldName() {
	 	return fieldName;
	}
	/**
	 * @设置 数据库中的字段名称
	 * @param fieldName
	 */
	public void setFieldName(String fieldName) {
	 	this.fieldName = fieldName;
	}
	/**
	 * @return 字段单位
	 */
	public String getFieldUnit() {
	 	return fieldUnit;
	}
	/**
	 * @设置 字段单位
	 * @param fieldUnit
	 */
	public void setFieldUnit(String fieldUnit) {
	 	this.fieldUnit = fieldUnit;
	}
	/**
	 * @return 是否在移动端展示该数据
	 */
	public String getMobleShow() {
	 	return mobleShow;
	}
	/**
	 * @设置 是否在移动端展示该数据
	 * @param mobleShow
	 */
	public void setMobleShow(String mobleShow) {
	 	this.mobleShow = mobleShow;
	}
	/**
	 * @return 是否在交易端展示该字段，0不展示，1展示
	 */
	public String getPcShow() {
	 	return pcShow;
	}
	/**
	 * @设置 是否在交易端展示该字段，0不展示，1展示
	 * @param pcShow
	 */
	public void setPcShow(String pcShow) {
	 	this.pcShow = pcShow;
	}
	/**
	 * @return 字段默认值
	 */
	public String getDefaultVal() {
	 	return defaultVal;
	}
	/**
	 * @设置 字段默认值
	 * @param defaultVal
	 */
	public void setDefaultVal(String defaultVal) {
	 	this.defaultVal = defaultVal;
	}
	/**
	 * @return 移动端是否使用该字段，0不使用，1使用
	 */
	public String getMobileUse() {
	 	return mobileUse;
	}
	/**
	 * @设置 移动端是否使用该字段，0不使用，1使用
	 * @param mobileUse
	 */
	public void setMobileUse(String mobileUse) {
	 	this.mobileUse = mobileUse;
	}
	/**
	 * @return pc交易端是否使用该字段，0不使用，1使用
	 */
	public String getPcUse() {
	 	return pcUse;
	}
	/**
	 * @设置 pc交易端是否使用该字段，0不使用，1使用
	 * @param pcUse
	 */
	public void setPcUse(String pcUse) {
	 	this.pcUse = pcUse;
	}
	/**
	 * @return 字段在交易端显示的中文名称
	 */
	public String getFieldLabel() {
	 	return fieldLabel;
	}
	/**
	 * @设置 字段在交易端显示的中文名称
	 * @param fieldLabel
	 */
	public void setFieldLabel(String fieldLabel) {
	 	this.fieldLabel = fieldLabel;
	}
}