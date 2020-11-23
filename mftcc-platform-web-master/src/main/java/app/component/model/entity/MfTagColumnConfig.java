package app.component.model.entity;
import app.base.BaseDomain;
/**
* Title: MfTagColumnConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Jan 05 15:14:28 CST 2018
* @version：1.0
**/
public class MfTagColumnConfig extends BaseDomain {
	private String configId;//编号
	private String keyNo;//标签key编号
	private String tableColumnId;//表格列编号
	private String columnId;//对应字段
	private String columnName;//对应字段名称
	private String mergeFlag;//合并单元格标识。0不合并1合并
	private Integer mergeStartLineNum;//合并开始行号
	private Integer mergeStartColumnNum;//合并开始列号
	private Integer mergeEndLineNum;//合并结束行号
	private Integer mergeEndColumnNum;//合并结束列号
	private String highlightFlag;//是否突出显示
	private String highlightColor;//突出显示背景颜色
	private String formatType;//格式化类型01金额小写02金额大写03日期格式化年月日05金额拆分06保留小数
	private String formatParam;//格式化参数
	private String columnUnit;//单位。元、万元、%等
	private String sysDictionryVal;//字典项编号
	private String groupId;//字段组编号,适用于两个字段拼接，后者列编号column_id(end_date)。例如起止日期
	private String splitStr;//字段组分割符号
	private String sort;//顺序

	/**
	 * @return 编号
	 */
	public String getConfigId() {
	 	return configId;
	}
	/**
	 * @设置 编号
	 * @param configId
	 */
	public void setConfigId(String configId) {
	 	this.configId = configId;
	}
	/**
	 * @return 表格列编号
	 */
	public String getTableColumnId() {
	 	return tableColumnId;
	}
	/**
	 * @设置 表格列编号
	 * @param tableColumnId
	 */
	public void setTableColumnId(String tableColumnId) {
	 	this.tableColumnId = tableColumnId;
	}
	/**
	 * @return 对应字段
	 */
	public String getColumnId() {
	 	return columnId;
	}
	/**
	 * @设置 对应字段
	 * @param columnId
	 */
	public void setColumnId(String columnId) {
	 	this.columnId = columnId;
	}
	/**
	 * @return 对应字段名称
	 */
	public String getColumnName() {
	 	return columnName;
	}
	/**
	 * @设置 对应字段名称
	 * @param columnName
	 */
	public void setColumnName(String columnName) {
	 	this.columnName = columnName;
	}
	/**
	 * @return 合并单元格标识。0不合并1合并
	 */
	public String getMergeFlag() {
	 	return mergeFlag;
	}
	/**
	 * @设置 合并单元格标识。0不合并1合并
	 * @param mergeFlag
	 */
	public void setMergeFlag(String mergeFlag) {
	 	this.mergeFlag = mergeFlag;
	}
	/**
	 * @return 合并开始行号
	 */
	public Integer getMergeStartLineNum() {
	 	return mergeStartLineNum;
	}
	/**
	 * @设置 合并开始行号
	 * @param mergeStartLineNum
	 */
	public void setMergeStartLineNum(Integer mergeStartLineNum) {
	 	this.mergeStartLineNum = mergeStartLineNum;
	}
	/**
	 * @return 合并开始列号
	 */
	public Integer getMergeStartColumnNum() {
	 	return mergeStartColumnNum;
	}
	/**
	 * @设置 合并开始列号
	 * @param mergeStartColumnNum
	 */
	public void setMergeStartColumnNum(Integer mergeStartColumnNum) {
	 	this.mergeStartColumnNum = mergeStartColumnNum;
	}
	/**
	 * @return 合并结束行号
	 */
	public Integer getMergeEndLineNum() {
	 	return mergeEndLineNum;
	}
	/**
	 * @设置 合并结束行号
	 * @param mergeEndLineNum
	 */
	public void setMergeEndLineNum(Integer mergeEndLineNum) {
	 	this.mergeEndLineNum = mergeEndLineNum;
	}
	/**
	 * @return 合并结束列号
	 */
	public Integer getMergeEndColumnNum() {
	 	return mergeEndColumnNum;
	}
	/**
	 * @设置 合并结束列号
	 * @param mergeEndColumnNum
	 */
	public void setMergeEndColumnNum(Integer mergeEndColumnNum) {
	 	this.mergeEndColumnNum = mergeEndColumnNum;
	}
	/**
	 * @return 是否突出显示
	 */
	public String getHighlightFlag() {
	 	return highlightFlag;
	}
	/**
	 * @设置 是否突出显示
	 * @param highlightFlag
	 */
	public void setHighlightFlag(String highlightFlag) {
	 	this.highlightFlag = highlightFlag;
	}
	/**
	 * @return 突出显示背景颜色
	 */
	public String getHighlightColor() {
	 	return highlightColor;
	}
	/**
	 * @设置 突出显示背景颜色
	 * @param highlightColor
	 */
	public void setHighlightColor(String highlightColor) {
	 	this.highlightColor = highlightColor;
	}
	/**
	 * @return 格式化类型01金额小写02金额大写03日期格式化年月日05金额拆分06保留小数
	 */
	public String getFormatType() {
	 	return formatType;
	}
	/**
	 * @设置 格式化类型01金额小写02金额大写03日期格式化年月日05金额拆分06保留小数
	 * @param formatType
	 */
	public void setFormatType(String formatType) {
	 	this.formatType = formatType;
	}
	/**
	 * @return 单位。元、万元、%等
	 */
	public String getColumnUnit() {
	 	return columnUnit;
	}
	/**
	 * @设置 单位。元、万元、%等
	 * @param columnUnit
	 */
	public void setColumnUnit(String columnUnit) {
	 	this.columnUnit = columnUnit;
	}
	/**
	 * @return 字典项编号
	 */
	public String getSysDictionryVal() {
	 	return sysDictionryVal;
	}
	/**
	 * @设置 字典项编号
	 * @param sysDictionryVal
	 */
	public void setSysDictionryVal(String sysDictionryVal) {
	 	this.sysDictionryVal = sysDictionryVal;
	}
	public String getKeyNo() {
		return keyNo;
	}
	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}
	public String getFormatParam() {
		return formatParam;
	}
	public void setFormatParam(String formatParam) {
		this.formatParam = formatParam;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getSplitStr() {
		return splitStr;
	}
	public void setSplitStr(String splitStr) {
		this.splitStr = splitStr;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
}