package app.component.model.entity;
import app.base.BaseDomain;
/**
* Title: MfTagTableConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Jan 05 14:50:42 CST 2018
* @version：1.0
**/
public class MfTagTableConfig extends BaseDomain {
	private String configId;//表格配置编号
	private String keyNo;//标签key编号
	private String tableColumnId;//表格列编号
	private String tableColumnName;//表格列名称
	private Integer sort;//列顺序
	private Integer lineNum;//行号
	private Integer columnNum;//列号
	private String headerDirection;//表头方向。1横向2纵向
	private String columnType;//单元列类型1常规2合并
	private String mergeType;//合并单元格类型1合并列2合并行3复合
	private Integer mergeStartLineNum;//合并开始行号
	private Integer mergeStartColumnNum;//合并开始列号
	private Integer mergeEndLineNum;//合并结束行号
	private Integer mergeEndColumnNum;//合并结束列号
	private String subFlag;//复合单元格子列标识。0否1是
	private String parentColumnId;//父列
	private String dynamicType;//动态类型1数据字典2以天为单位统计当前月3以月为单位统计当前年
	private String dynamicParam ;//动态列参数
	private String useFlag;//启用标识

	/**
	 * @return 表格配置编号
	 */
	public String getConfigId() {
	 	return configId;
	}
	/**
	 * @设置 表格配置编号
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
	 * @return 表格列名称
	 */
	public String getTableColumnName() {
	 	return tableColumnName;
	}
	/**
	 * @设置 表格列名称
	 * @param tableColumnName
	 */
	public void setTableColumnName(String tableColumnName) {
	 	this.tableColumnName = tableColumnName;
	}
	/**
	 * @return 列顺序
	 */
	public Integer getSort() {
	 	return sort;
	}
	/**
	 * @设置 列顺序
	 * @param sort
	 */
	public void setSort(Integer sort) {
	 	this.sort = sort;
	}
	/**
	 * @return 行号
	 */
	public Integer getLineNum() {
	 	return lineNum;
	}
	/**
	 * @设置 行号
	 * @param lineNum
	 */
	public void setLineNum(Integer lineNum) {
	 	this.lineNum = lineNum;
	}
	/**
	 * @return 列号
	 */
	public Integer getColumnNum() {
	 	return columnNum;
	}
	/**
	 * @设置 列号
	 * @param columnNum
	 */
	public void setColumnNum(Integer columnNum) {
	 	this.columnNum = columnNum;
	}
	/**
	 * @return 表头方向。1横向2纵向
	 */
	public String getHeaderDirection() {
	 	return headerDirection;
	}
	/**
	 * @设置 表头方向。1横向2纵向
	 * @param headerDirection
	 */
	public void setHeaderDirection(String headerDirection) {
	 	this.headerDirection = headerDirection;
	}
	/**
	 * @return 单元列类型1常规2合并
	 */
	public String getColumnType() {
	 	return columnType;
	}
	/**
	 * @设置 单元列类型1常规2合并
	 * @param columnType
	 */
	public void setColumnType(String columnType) {
	 	this.columnType = columnType;
	}
	/**
	 * @return 合并单元格类型1合并列2合并行3复合
	 */
	public String getMergeType() {
	 	return mergeType;
	}
	/**
	 * @设置 合并单元格类型1合并列2合并行3复合
	 * @param mergeType
	 */
	public void setMergeType(String mergeType) {
	 	this.mergeType = mergeType;
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
	 * @return 复合单元格子列标识。0否1是
	 */
	public String getSubFlag() {
	 	return subFlag;
	}
	/**
	 * @设置 复合单元格子列标识。0否1是
	 * @param subFlag
	 */
	public void setSubFlag(String subFlag) {
	 	this.subFlag = subFlag;
	}
	/**
	 * @return 父列
	 */
	public String getParentColumnId() {
	 	return parentColumnId;
	}
	/**
	 * @设置 父列
	 * @param parentColumnId
	 */
	public void setParentColumnId(String parentColumnId) {
	 	this.parentColumnId = parentColumnId;
	}
	/**
	 * @return 动态类型1数据字典2以天为单位统计当前月3以月为单位统计当前年
	 */
	public String getDynamicType() {
	 	return dynamicType;
	}
	/**
	 * @设置 动态类型1数据字典2以天为单位统计当前月3以月为单位统计当前年
	 * @param dynamicType
	 */
	public void setDynamicType(String dynamicType) {
	 	this.dynamicType = dynamicType;
	}
	public String getKeyNo() {
		return keyNo;
	}
	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public String getDynamicParam() {
		return dynamicParam;
	}
	public void setDynamicParam(String dynamicParam) {
		this.dynamicParam = dynamicParam;
	}
}