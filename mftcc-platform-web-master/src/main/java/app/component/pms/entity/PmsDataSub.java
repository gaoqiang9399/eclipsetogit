package app.component.pms.entity;
import app.base.BaseDomain;
/**
* Title: PmsDataSub.java
* Description:
* @author：@dhcc.com.cn
* @Wed Sep 21 02:27:39 GMT 2016
* @version：1.0
**/
public class PmsDataSub extends BaseDomain {
	private String funNo;//业务功能编号
	private String pmsLv;//权限类型编号
	private String pmsName;//权限类型名称
	private String pmsField;//指定字段
	private String pmsSts;//是否启用指定字段

	/**
	 * @return 业务功能编号
	 */
	public String getFunNo() {
	 	return funNo;
	}
	/**
	 * @设置 业务功能编号
	 * @param funNo
	 */
	public void setFunNo(String funNo) {
	 	this.funNo = funNo;
	}
	/**
	 * @return 权限类型编号
	 */
	public String getPmsLv() {
	 	return pmsLv;
	}
	/**
	 * @设置 权限类型编号
	 * @param pmsLv
	 */
	public void setPmsLv(String pmsLv) {
	 	this.pmsLv = pmsLv;
	}
	/**
	 * @return 权限类型名称
	 */
	public String getPmsName() {
	 	return pmsName;
	}
	/**
	 * @设置 权限类型名称
	 * @param pmsName
	 */
	public void setPmsName(String pmsName) {
	 	this.pmsName = pmsName;
	}
	/**
	 * @return 指定字段
	 */
	public String getPmsField() {
	 	return pmsField;
	}
	/**
	 * @设置 指定字段
	 * @param pmsField
	 */
	public void setPmsField(String pmsField) {
	 	this.pmsField = pmsField;
	}
	/**
	 * @return 是否启用指定字段
	 */
	public String getPmsSts() {
	 	return pmsSts;
	}
	/**
	 * @设置 是否启用指定字段
	 * @param pmsSts
	 */
	public void setPmsSts(String pmsSts) {
	 	this.pmsSts = pmsSts;
	}
}