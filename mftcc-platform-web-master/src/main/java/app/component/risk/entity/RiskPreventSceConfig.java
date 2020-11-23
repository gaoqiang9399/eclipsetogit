package app.component.risk.entity;
import app.base.BaseDomain;
/**
* Title: RiskPreventSceConfig.java
* Description:
* @author：@dhcc.com.cn
* @Thu Mar 03 07:11:49 GMT 2016
* @version：1.0
**/
public class RiskPreventSceConfig extends BaseDomain {
	private String scNo;//业务场景编号
	private String dime;//维度
	private String dimeJavaFieldName;//
	private String useFlag;//是否启用
	private String oldDime;

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
	public String getDimeJavaFieldName() {
	 	return dimeJavaFieldName;
	}
	/**
	 * @设置 
	 * @param dimeJavaFieldName
	 */
	public void setDimeJavaFieldName(String dimeJavaFieldName) {
	 	this.dimeJavaFieldName = dimeJavaFieldName;
	}
	/**
	 * @return 是否启用
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 是否启用
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	public String getOldDime() {
		return oldDime;
	}
	public void setOldDime(String oldDime) {
		this.oldDime = oldDime;
	}
	
}