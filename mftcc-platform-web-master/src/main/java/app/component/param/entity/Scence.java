package app.component.param.entity;
import app.base.BaseDomain;
/**
* Title: Scence.java
* Description:
* @author：@dhcc.com.cn
* @Wed Jan 20 03:11:13 GMT 2016
* @version：1.0
**/
public class Scence extends BaseDomain {
	private String scNo;//
	private String scName;//
	private String scenceType;//
	private String useFlag;//
	private String scTypeChnName;//
	/**
	 * @return 
	 */
	public String getScNo() {
	 	return scNo;
	}
	/**
	 * @设置 
	 * @param scNo
	 */
	public void setScNo(String scNo) {
	 	this.scNo = scNo;
	}
	/**
	 * @return 
	 */
	public String getScName() {
	 	return scName;
	}
	/**
	 * @设置 
	 * @param scName
	 */
	public void setScName(String scName) {
	 	this.scName = scName;
	}
	/**
	 * @return 
	 */
	public String getScenceType() {
	 	return scenceType;
	}
	/**
	 * @设置 
	 * @param scenceType
	 */
	public void setScenceType(String scenceType) {
	 	this.scenceType = scenceType;
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
	public String getScTypeChnName() {
		return scTypeChnName;
	}
	public void setScTypeChnName(String scTypeChnName) {
		this.scTypeChnName = scTypeChnName;
	}
	
}