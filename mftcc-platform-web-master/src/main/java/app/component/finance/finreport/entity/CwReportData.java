package app.component.finance.finreport.entity;
import app.base.BaseDomain;
/**
* Title: CwReportData.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Feb 03 13:42:00 CST 2017
* @version：1.0
**/
public class CwReportData extends BaseDomain {
	private String finBooks;//帐套标识
	private String bbId;//报表标识对应cw_report_acount.bb_id
	private String reportTypeId;//报表类标识
	private String reportItemId;//报表项标识
	private String showName;//显示名称
	private String reoprtItemLev;//上级项
	private String bbItemnoA;//报表项A值
	private String bbItemnoB;//报表项B值
	private String operationType;//运算类型0：空白项；1：查询项，2：运算项
	private String showStyle;//显示样式0：正常；1：加粗
	private String exp;//报表项等级
	private String subYn;//是否存在子项Y：存在；N：不存在
	private String bbOrder;//报表项顺序
	private String bbType;//报表项编号类别0：资产/其他；1：负债
	private String remarks;//备注
	
	private String zcTr;//资产行次
	private String fzTr;//负债行次
	private String lrTr;//利润行次
	private String xjllTr;//现金流量行次

	public CwReportData() { }
	public CwReportData(String bbId) {
		this.bbId = bbId;
	}
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
	 * @return 报表标识对应cw_report_acount.bb_id
	 */
	public String getBbId() {
	 	return bbId;
	}
	/**
	 * @设置 报表标识对应cw_report_acount.bb_id
	 * @param bbId
	 */
	public void setBbId(String bbId) {
	 	this.bbId = bbId;
	}
	/**
	 * @return 报表类标识
	 */
	public String getReportTypeId() {
	 	return reportTypeId;
	}
	/**
	 * @设置 报表类标识
	 * @param reportTypeId
	 */
	public void setReportTypeId(String reportTypeId) {
	 	this.reportTypeId = reportTypeId;
	}
	/**
	 * @return 报表项标识
	 */
	public String getReportItemId() {
	 	return reportItemId;
	}
	/**
	 * @设置 报表项标识
	 * @param reportItemId
	 */
	public void setReportItemId(String reportItemId) {
	 	this.reportItemId = reportItemId;
	}
	/**
	 * @return 显示名称
	 */
	public String getShowName() {
	 	return showName;
	}
	/**
	 * @设置 显示名称
	 * @param showName
	 */
	public void setShowName(String showName) {
	 	this.showName = showName;
	}
	/**
	 * @return 上级项
	 */
	public String getReoprtItemLev() {
	 	return reoprtItemLev;
	}
	/**
	 * @设置 上级项
	 * @param reoprtItemLev
	 */
	public void setReoprtItemLev(String reoprtItemLev) {
	 	this.reoprtItemLev = reoprtItemLev;
	}
	/**
	 * @return 报表项A值
	 */
	public String getBbItemnoA() {
	 	return bbItemnoA;
	}
	/**
	 * @设置 报表项A值
	 * @param bbItemnoA
	 */
	public void setBbItemnoA(String bbItemnoA) {
	 	this.bbItemnoA = bbItemnoA;
	}
	/**
	 * @return 报表项B值
	 */
	public String getBbItemnoB() {
	 	return bbItemnoB;
	}
	/**
	 * @设置 报表项B值
	 * @param bbItemnoB
	 */
	public void setBbItemnoB(String bbItemnoB) {
	 	this.bbItemnoB = bbItemnoB;
	}
	/**
	 * @return 运算类型0：空白项；1：查询项，2：运算项
	 */
	public String getOperationType() {
	 	return operationType;
	}
	/**
	 * @设置 运算类型0：空白项；1：查询项，2：运算项
	 * @param operationType
	 */
	public void setOperationType(String operationType) {
	 	this.operationType = operationType;
	}
	/**
	 * @return 显示样式0：正常；1：加粗
	 */
	public String getShowStyle() {
	 	return showStyle;
	}
	/**
	 * @设置 显示样式0：正常；1：加粗
	 * @param showStyle
	 */
	public void setShowStyle(String showStyle) {
	 	this.showStyle = showStyle;
	}
	/**
	 * @return 报表项等级
	 */
	public String getExp() {
	 	return exp;
	}
	/**
	 * @设置 报表项等级
	 * @param exp
	 */
	public void setExp(String exp) {
	 	this.exp = exp;
	}
	/**
	 * @return 是否存在子项Y：存在；N：不存在
	 */
	public String getSubYn() {
	 	return subYn;
	}
	/**
	 * @设置 是否存在子项Y：存在；N：不存在
	 * @param subYn
	 */
	public void setSubYn(String subYn) {
	 	this.subYn = subYn;
	}
	/**
	 * @return 报表项顺序
	 */
	public String getBbOrder() {
	 	return bbOrder;
	}
	/**
	 * @设置 报表项顺序
	 * @param bbOrder
	 */
	public void setBbOrder(String bbOrder) {
	 	this.bbOrder = bbOrder;
	}
	/**
	 * @return 报表项编号类别0：资产/其他；1：负债
	 */
	public String getBbType() {
	 	return bbType;
	}
	/**
	 * @设置 报表项编号类别0：资产/其他；1：负债
	 * @param bbType
	 */
	public void setBbType(String bbType) {
	 	this.bbType = bbType;
	}
	/**
	 * @return 备注
	 */
	public String getRemarks() {
	 	return remarks;
	}
	/**
	 * @设置 备注
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
	 	this.remarks = remarks;
	}
	public String getZcTr() {
		return zcTr;
	}
	public void setZcTr(String zcTr) {
		this.zcTr = zcTr;
	}
	public String getFzTr() {
		return fzTr;
	}
	public void setFzTr(String fzTr) {
		this.fzTr = fzTr;
	}
	public String getLrTr() {
		return lrTr;
	}
	public void setLrTr(String lrTr) {
		this.lrTr = lrTr;
	}
	public String getXjllTr() {
		return xjllTr;
	}
	public void setXjllTr(String xjllTr) {
		this.xjllTr = xjllTr;
	}
	
	
}