package app.component.finance.finreport.entity;
import app.base.BaseDomain;
/**
* Title: CwReportAcount.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sun Jan 22 10:12:05 CST 2017
* @version：1.0
**/
public class CwReportAcount extends BaseDomain {
	private String finBooks;//帐套标识
	private String bbId;//标识
	private String bbType;//报表类型 001：资产负债表；002：现金流量表； 003：利润表；
	private String bbWeek;//报表周期
	private String bbDate;//报表生成日期
	private String bbLb;//报表类别01：年报表；02：季报表；03：期报表
	private String bbShow;//报表显示列
	private String bbTel;//创建人
	private String bbTelName;//创建人名称
	private String bbRemark;//备注
	private String occDate;//最后一次修改日期

	/**
	 * @return 帐套标识
	 */
	public String getFinBooks() {
	 	return finBooks;
	}
	/**
	 * @设置 帐套标识
	 * @param finBooks
	 */
	public void setFinBooks(String finBooks) {
	 	this.finBooks = finBooks;
	}
	/**
	 * @return 标识
	 */
	public String getBbId() {
	 	return bbId;
	}
	/**
	 * @设置 标识
	 * @param bbId
	 */
	public void setBbId(String bbId) {
	 	this.bbId = bbId;
	}
	/**
	 * @return 报表类型 001：资产负债表；002：现金流量表； 003：利润表；
	 */
	public String getBbType() {
	 	return bbType;
	}
	/**
	 * @设置 报表类型 001：资产负债表；002：现金流量表； 003：利润表；
	 * @param bbType
	 */
	public void setBbType(String bbType) {
	 	this.bbType = bbType;
	}
	/**
	 * @return 报表周期
	 */
	public String getBbWeek() {
	 	return bbWeek;
	}
	/**
	 * @设置 报表周期
	 * @param bbWeek
	 */
	public void setBbWeek(String bbWeek) {
	 	this.bbWeek = bbWeek;
	}
	/**
	 * @return 报表生成日期
	 */
	public String getBbDate() {
	 	return bbDate;
	}
	/**
	 * @设置 报表生成日期
	 * @param bbDate
	 */
	public void setBbDate(String bbDate) {
	 	this.bbDate = bbDate;
	}
	/**
	 * @return 报表类别01：年报表；02：季报表；03：期报表
	 */
	public String getBbLb() {
	 	return bbLb;
	}
	/**
	 * @设置 报表类别01：年报表；02：季报表；03：期报表
	 * @param bbLb
	 */
	public void setBbLb(String bbLb) {
	 	this.bbLb = bbLb;
	}
	/**
	 * @return 报表显示列
	 */
	public String getBbShow() {
	 	return bbShow;
	}
	/**
	 * @设置 报表显示列
	 * @param bbShow
	 */
	public void setBbShow(String bbShow) {
	 	this.bbShow = bbShow;
	}
	/**
	 * @return 创建人
	 */
	public String getBbTel() {
	 	return bbTel;
	}
	/**
	 * @设置 创建人
	 * @param bbTel
	 */
	public void setBbTel(String bbTel) {
	 	this.bbTel = bbTel;
	}
	/**
	 * @return 创建人名称
	 */
	public String getBbTelName() {
	 	return bbTelName;
	}
	/**
	 * @设置 创建人名称
	 * @param bbTelName
	 */
	public void setBbTelName(String bbTelName) {
	 	this.bbTelName = bbTelName;
	}
	/**
	 * @return 备注
	 */
	public String getBbRemark() {
	 	return bbRemark;
	}
	/**
	 * @设置 备注
	 * @param bbRemark
	 */
	public void setBbRemark(String bbRemark) {
	 	this.bbRemark = bbRemark;
	}
	/**
	 * @return 最后一次修改日期
	 */
	public String getOccDate() {
	 	return occDate;
	}
	/**
	 * @设置 最后一次修改日期
	 * @param occDate
	 */
	public void setOccDate(String occDate) {
	 	this.occDate = occDate;
	}
}