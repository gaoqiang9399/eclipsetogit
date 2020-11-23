package app.component.finance.finreport.entity;
import app.base.BaseDomain;
/**
 * Title: CwReportItem.java Description:
 * 
 * @author：kaifa@dhcc.com.cn
 * @Thu Jan 19 14:01:41 CST 2017
 * @version：1.0
 **/
public class CwReportItem extends BaseDomain {
	private String finBooks;// 帐套标识
	private String reportTypeId;// 报表标识
	private String reportItemId;// 报表项标识
	private String reportName;// 报表项名称
	private String showName;// 显示名称
	private String reoprtItemLev;// 上级项
	private String reportItemType;// 报表项类别0：资产/其他；1：负债
	private String operationType;// 运算类型0：空白项；1：查询项，2：运算项
	private String isInput;// 是否允许输入0：流入；1：流出
	private String isUse;// 是否使用0：未使用，1：已使用
	private String isAutoOperation;// 自动运算
	private String reportFormula;// 运算公式
	private String decimalPlaces;// 小数位数
	private String roundMode;// 舍入方式
	private String showOrder;// 显示顺序
	private String showStyle;// 显示样式0：正常；1：加粗
	private String remarks;// 备注
	private String exp;// 报表项等级
	private String subYn;// 是否存在子项Y：存在；N：不存在
	private String calcValue;// 运算公式值
	private String occDate;// 最后一次修改时间

	private String zcTr;//资产行次
	private String fzTr;//负债行次
	private String lrTr;//利润行次
	private String xjllTr;//现金流量行次
	
	// 展示使用
	private String reoprtLevName;// 上级项名称
	private String copyFinBooks;//辅助帐套
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
	 * @return 报表标识
	 */
	public String getReportTypeId() {
		return reportTypeId;
	}
	/**
	 * @设置 报表标识
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
	 * @return 报表项名称
	 */
	public String getReportName() {
		return reportName;
	}
	/**
	 * @设置 报表项名称
	 * @param reportName
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
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
	 * @return 报表项类别0：资产/其他；1：负债
	 */
	public String getReportItemType() {
		return reportItemType;
	}
	/**
	 * @设置 报表项类别0：资产/其他；1：负债
	 * @param reportItemType
	 */
	public void setReportItemType(String reportItemType) {
		this.reportItemType = reportItemType;
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
	 * @return 是否允许输入0：流入；1：流出
	 */
	public String getIsInput() {
		return isInput;
	}
	/**
	 * @设置 是否允许输入0：流入；1：流出
	 * @param isInput
	 */
	public void setIsInput(String isInput) {
		this.isInput = isInput;
	}
	/**
	 * @return 是否使用0：未使用，1：已使用
	 */
	public String getIsUse() {
		return isUse;
	}
	/**
	 * @设置 是否使用0：未使用，1：已使用
	 * @param isUse
	 */
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	/**
	 * @return 自动运算
	 */
	public String getIsAutoOperation() {
		return isAutoOperation;
	}
	/**
	 * @设置 自动运算
	 * @param isAutoOperation
	 */
	public void setIsAutoOperation(String isAutoOperation) {
		this.isAutoOperation = isAutoOperation;
	}
	/**
	 * @return 运算公式
	 */
	public String getReportFormula() {
		return reportFormula;
	}
	/**
	 * @设置 运算公式
	 * @param reportFormula
	 */
	public void setReportFormula(String reportFormula) {
		this.reportFormula = reportFormula;
	}
	/**
	 * @return 小数位数
	 */
	public String getDecimalPlaces() {
		return decimalPlaces;
	}
	/**
	 * @设置 小数位数
	 * @param decimalPlaces
	 */
	public void setDecimalPlaces(String decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}
	/**
	 * @return 舍入方式
	 */
	public String getRoundMode() {
		return roundMode;
	}
	/**
	 * @设置 舍入方式
	 * @param roundMode
	 */
	public void setRoundMode(String roundMode) {
		this.roundMode = roundMode;
	}
	/**
	 * @return 显示顺序
	 */
	public String getShowOrder() {
		return showOrder;
	}
	/**
	 * @设置 显示顺序
	 * @param showOrder
	 */
	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
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
	 * @return 运算公式值
	 */
	public String getCalcValue() {
		return calcValue;
	}
	/**
	 * @设置 运算公式值
	 * @param calcValue
	 */
	public void setCalcValue(String calcValue) {
		this.calcValue = calcValue;
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
	public String getReoprtLevName() {
		return reoprtLevName;
	}
	public void setReoprtLevName(String reoprtLevName) {
		this.reoprtLevName = reoprtLevName;
	}
	public String getCopyFinBooks() {
		return copyFinBooks;
	}
	public void setCopyFinBooks(String copyFinBooks) {
		this.copyFinBooks = copyFinBooks;
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