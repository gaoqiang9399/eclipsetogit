package app.component.app.entity;
import app.base.BaseDomain;
/**
* Title: VouAppRel.java
* Description:
* @author：@dhcc.com.cn
* @Mon Jan 18 03:47:45 GMT 2016
* @version：1.0
**/
public class VouAppRel extends BaseDomain {
	private String appNo;//业务申请号
	private String vouNo;//担保编号
	private String vouType;//担保方式
	private String guaType;//抵质押物类型
	private Double vouAmt;//担保金额

	/**
	 * @return 业务申请号
	 */
	public String getAppNo() {
	 	return appNo;
	}
	/**
	 * @设置 业务申请号
	 * @param appNo
	 */
	public void setAppNo(String appNo) {
	 	this.appNo = appNo;
	}
	/**
	 * @return 担保编号
	 */
	public String getVouNo() {
	 	return vouNo;
	}
	/**
	 * @设置 担保编号
	 * @param vouNo
	 */
	public void setVouNo(String vouNo) {
	 	this.vouNo = vouNo;
	}
	/**
	 * @return 担保方式
	 */
	public String getVouType() {
	 	return vouType;
	}
	/**
	 * @设置 担保方式
	 * @param vouType
	 */
	public void setVouType(String vouType) {
	 	this.vouType = vouType;
	}
	/**
	 * @return 抵质押物类型
	 */
	public String getGuaType() {
	 	return guaType;
	}
	/**
	 * @设置 抵质押物类型
	 * @param guaType
	 */
	public void setGuaType(String guaType) {
	 	this.guaType = guaType;
	}
	public Double getVouAmt() {
		return vouAmt;
	}
	public void setVouAmt(Double vouAmt) {
		this.vouAmt = vouAmt;
	}
}