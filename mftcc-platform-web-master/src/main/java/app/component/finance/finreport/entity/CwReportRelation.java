package app.component.finance.finreport.entity;
import app.base.BaseDomain;
/**
* Title: CwReportRelation.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jan 23 14:29:26 CST 2017
* @version：1.0
**/
public class CwReportRelation extends BaseDomain {
	private String finBooks;//帐套标识
	private String uuid;//流水号 系统唯一标识
	private String bbType;//报表类型 001：资产负债表；002：现金流量表； 003：利润表；
	private String reportItemId;//报表项编号
	private String calcSign;//运算符号1：加 ；2：减
	private String gsType;//格式类型1：科目 ；2：报表项
	private String calcItem;//公式数据
	private String calcRule;//取值规则
	private String altFlag;//变更标志1：变更,0：未变更
	private String occDate;//最后一次修改时间

	//
	private String copyFinBooks;//复制帐套
	
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
	 * @return 流水号 系统唯一标识
	 */
	public String getUuid() {
	 	return uuid;
	}
	/**
	 * @设置 流水号 系统唯一标识
	 * @param uuid
	 */
	public void setUuid(String uuid) {
	 	this.uuid = uuid;
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
	 * @return 报表项编号
	 */
	public String getReportItemId() {
	 	return reportItemId;
	}
	/**
	 * @设置 报表项编号
	 * @param reportItemId
	 */
	public void setReportItemId(String reportItemId) {
	 	this.reportItemId = reportItemId;
	}
	/**
	 * @return 运算符号1：加 ；2：减
	 */
	public String getCalcSign() {
	 	return calcSign;
	}
	/**
	 * @设置 运算符号1：加 ；2：减
	 * @param calcSign
	 */
	public void setCalcSign(String calcSign) {
	 	this.calcSign = calcSign;
	}
	/**
	 * @return 格式类型1：科目 ；2：报表项
	 */
	public String getGsType() {
	 	return gsType;
	}
	/**
	 * @设置 格式类型1：科目 ；2：报表项
	 * @param gsType
	 */
	public void setGsType(String gsType) {
	 	this.gsType = gsType;
	}
	/**
	 * @return 公式数据
	 */
	public String getCalcItem() {
	 	return calcItem;
	}
	/**
	 * @设置 公式数据
	 * @param calcItem
	 */
	public void setCalcItem(String calcItem) {
	 	this.calcItem = calcItem;
	}
	/**
	 * @return 取值规则
	 */
	public String getCalcRule() {
	 	return calcRule;
	}
	/**
	 * @设置 取值规则
	 * @param calcRule
	 */
	public void setCalcRule(String calcRule) {
	 	this.calcRule = calcRule;
	}
	/**
	 * @return 变更标志1：变更,0：未变更
	 */
	public String getAltFlag() {
	 	return altFlag;
	}
	/**
	 * @设置 变更标志1：变更,0：未变更
	 * @param altFlag
	 */
	public void setAltFlag(String altFlag) {
	 	this.altFlag = altFlag;
	}
	/**
	 * @return 最后一次修改时间
	 */
	public String getOccDate() {
	 	return occDate;
	}
	/**
	 * @设置 最后一次修改时间
	 * @param occDate
	 */
	public void setOccDate(String occDate) {
	 	this.occDate = occDate;
	}
	public String getCopyFinBooks() {
		return copyFinBooks;
	}
	public void setCopyFinBooks(String copyFinBooks) {
		this.copyFinBooks = copyFinBooks;
	}
	
}