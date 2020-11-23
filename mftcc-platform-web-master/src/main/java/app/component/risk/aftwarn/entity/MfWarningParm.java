package app.component.risk.aftwarn.entity;
import app.base.BaseDomain;
/**
* Title: MfWarningParm.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Jun 01 17:50:53 CST 2016
* @version：1.0
**/
public class MfWarningParm extends BaseDomain {
	private String parmId;//唯一id
	private String parmType;//
	private String parmValue;//
	private String parmFilter;//
	private String parmRemark;//
	private String useFlag;//
	private String isEdit;

	/**
	 * @return 唯一id
	 */
	public String getParmId() {
	 	return parmId;
	}
	/**
	 * @设置 唯一id
	 * @param parmId
	 */
	public void setParmId(String parmId) {
	 	this.parmId = parmId;
	}
	/**
	 * @return 
	 */
	public String getParmType() {
	 	return parmType;
	}
	/**
	 * @设置 
	 * @param parmType
	 */
	public void setParmType(String parmType) {
	 	this.parmType = parmType;
	}
	/**
	 * @return 
	 */
	public String getParmValue() {
	 	return parmValue;
	}
	/**
	 * @设置 
	 * @param parmValue
	 */
	public void setParmValue(String parmValue) {
	 	this.parmValue = parmValue;
	}
	/**
	 * @return 
	 */
	public String getParmFilter() {
	 	return parmFilter;
	}
	/**
	 * @设置 
	 * @param parmFilter
	 */
	public void setParmFilter(String parmFilter) {
	 	this.parmFilter = parmFilter;
	}
	/**
	 * @return 
	 */
	public String getParmRemark() {
	 	return parmRemark;
	}
	/**
	 * @设置 
	 * @param parmRemark
	 */
	public void setParmRemark(String parmRemark) {
	 	this.parmRemark = parmRemark;
	}
	/**
	 * @return 
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	public String getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}
	
	
}