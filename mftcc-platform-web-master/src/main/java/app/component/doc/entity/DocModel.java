package app.component.doc.entity;
import app.base.BaseDomain;
/**
* Title: DocModel.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Dec 21 15:43:44 CST 2016
* @version：1.0
**/
public class DocModel extends BaseDomain {
	private String docModelNo;//
	private String docModelName;//
	private String cusType;//
	private String kindNo;//
	private String cusTypeDes;//
	private String kindNoDes;//
	private String useFlag;//

	/**
	 * @return 
	 */
	public String getDocModelNo() {
	 	return docModelNo;
	}
	/**
	 * @设置 
	 * @param docModelNo
	 */
	public void setDocModelNo(String docModelNo) {
	 	this.docModelNo = docModelNo;
	}
	/**
	 * @return 
	 */
	public String getDocModelName() {
	 	return docModelName;
	}
	/**
	 * @设置 
	 * @param docModelName
	 */
	public void setDocModelName(String docModelName) {
	 	this.docModelName = docModelName;
	}
	/**
	 * @return 
	 */
	public String getCusType() {
	 	return cusType;
	}
	/**
	 * @设置 
	 * @param cusType
	 */
	public void setCusType(String cusType) {
	 	this.cusType = cusType;
	}
	/**
	 * @return 
	 */
	public String getKindNo() {
	 	return kindNo;
	}
	/**
	 * @设置 
	 * @param kindNo
	 */
	public void setKindNo(String kindNo) {
	 	this.kindNo = kindNo;
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
	public String getCusTypeDes() {
		return cusTypeDes;
	}
	public void setCusTypeDes(String cusTypeDes) {
		this.cusTypeDes = cusTypeDes;
	}
	public String getKindNoDes() {
		return kindNoDes;
	}
	public void setKindNoDes(String kindNoDes) {
		this.kindNoDes = kindNoDes;
	}
	
}