package app.component.model.entity;
import app.base.BaseDomain;
/**
* Title: MfTemplateTagBase.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Jun 24 17:21:13 CST 2017
* @version：1.0
**/
public class MfTemplateTagBase extends BaseDomain {
	private String serialNo;//流水号
	private String keyNo;//标签key编号
	private String tagKeyName;//标签key名称 $借款合同号$
	private String dataSourceType;//标签取值来源类型0-来自数据库字段，1-计算字段，2-规范的表格类，9-其他
	private String tableName;//取值数据库表，该标签直接取值的数据库表
	private String columnName;//标签取值来源类型=0-来自数据库字段，该字段存储的是直接取值的数据库表那个字段。
	private String tagUnit;//标签计量单位元、%百分号
	private String queryCondition;//查询条件，该标签取值表的查询唯一字段，如合同表的pactno字段，有两个条件用|| 或者&& 分开
	private String groupFlag;//分组标识：目前按照01-客户、02-合同、03-押品等分类
	private String useFlag;//启用标识0-未启用，1-已启用
	private String formatType;//格式化类型 01 金额小写，格式化千分位 02 金额大写 03 日期格式化年月日 05 金额拆分06保留两位小数
	private String remark;//备注
	private String sysDictionryVal;//字典项编号
	private String paramType;//参数类型
	private String poName;//表格类替换字段的key值
	private String titleName;//表格类对象的表头
	private String bookGroup;//标签编号
	private Integer sort;//标签顺序
	private String tableHeaderFlag;//是否使用表头配置定义表头0否1是
	private Integer lineNum;//行号
	private Integer columnNum;//列号
	private String tagReplaceType;//标签替换值类型1横向2纵向
	private String isChecked;//0未配置1已配置
	private String formatParam;//格式化参数
	/**
	 * @return 流水号
	 */
	public String getSerialNo() {
	 	return serialNo;
	}
	/**
	 * @设置 流水号
	 * @param serialNo
	 */
	public void setSerialNo(String serialNo) {
	 	this.serialNo = serialNo;
	}
	/**
	 * @return 标签key编号
	 */
	public String getKeyNo() {
	 	return keyNo;
	}
	/**
	 * @设置 标签key编号
	 * @param keyNo
	 */
	public void setKeyNo(String keyNo) {
	 	this.keyNo = keyNo;
	}
	/**
	 * @return 标签key名称 $借款合同号$
	 */
	public String getTagKeyName() {
	 	return tagKeyName;
	}
	/**
	 * @设置 标签key名称 $借款合同号$
	 * @param tagKeyName
	 */
	public void setTagKeyName(String tagKeyName) {
	 	this.tagKeyName = tagKeyName;
	}
	/**
	 * @return 标签取值来源类型0-来自数据库字段，1-计算字段，2-规范的表格类，9-其他
	 */
	public String getDataSourceType() {
	 	return dataSourceType;
	}
	/**
	 * @设置 标签取值来源类型0-来自数据库字段，1-计算字段，2-规范的表格类，9-其他
	 * @param dataSourceType
	 */
	public void setDataSourceType(String dataSourceType) {
	 	this.dataSourceType = dataSourceType;
	}
	/**
	 * @return 取值数据库表，该标签直接取值的数据库表
	 */
	public String getTableName() {
	 	return tableName;
	}
	/**
	 * @设置 取值数据库表，该标签直接取值的数据库表
	 * @param tableName
	 */
	public void setTableName(String tableName) {
	 	this.tableName = tableName;
	}
	/**
	 * @return 标签取值来源类型=0-来自数据库字段，该字段存储的是直接取值的数据库表那个字段。
 标签取值来源类型=1-计算字段，该字段存储的是计算的方法名称，标签取值来源类型=2-规范的表格类，该字段存储的是直接可查询复杂的SQL语句，如select pactno,pactamt from cr_pactmanager where pactno=?
	 */
	public String getColumnName() {
	 	return columnName;
	}
	/**
	 * @设置 标签取值来源类型=0-来自数据库字段，该字段存储的是直接取值的数据库表那个字段。
 标签取值来源类型=1-计算字段，该字段存储的是计算的方法名称，标签取值来源类型=2-规范的表格类，该字段存储的是直接可查询复杂的SQL语句，如select pactno,pactamt from cr_pactmanager where pactno=?
	 * @param columnName
	 */
	public void setColumnName(String columnName) {
	 	this.columnName = columnName;
	}
	/**
	 * @return 标签计量单位元、%百分号
	 */
	public String getTagUnit() {
	 	return tagUnit;
	}
	/**
	 * @设置 标签计量单位元、%百分号
	 * @param tagUnit
	 */
	public void setTagUnit(String tagUnit) {
	 	this.tagUnit = tagUnit;
	}
	/**
	 * @return 查询条件，该标签取值表的查询唯一字段，如合同表的pactno字段，有两个条件用|| 或者&& 分开
	 */
	public String getQueryCondition() {
	 	return queryCondition;
	}
	/**
	 * @设置 查询条件，该标签取值表的查询唯一字段，如合同表的pactno字段，有两个条件用|| 或者&& 分开
	 * @param queryCondition
	 */
	public void setQueryCondition(String queryCondition) {
	 	this.queryCondition = queryCondition;
	}
	/**
	 * @return 分组标识：目前按照01-客户、02-合同、03-押品等分类
	 */
	public String getGroupFlag() {
	 	return groupFlag;
	}
	/**
	 * @设置 分组标识：目前按照01-客户、02-合同、03-押品等分类
	 * @param groupFlag
	 */
	public void setGroupFlag(String groupFlag) {
	 	this.groupFlag = groupFlag;
	}
	/**
	 * @return 启用标识0-未启用，1-已启用
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 启用标识0-未启用，1-已启用
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 格式化类型
	 */
	public String getFormatType() {
	 	return formatType;
	}
	/**
	 * @设置 格式化类型
	 * @param formatType
	 */
	public void setFormatType(String formatType) {
	 	this.formatType = formatType;
	}
	/**
	 * @return 备注
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
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
	/**
	 * @return 参数类型
	 */
	public String getParamType() {
	 	return paramType;
	}
	/**
	 * @设置 参数类型
	 * @param paramType
	 */
	public void setParamType(String paramType) {
	 	this.paramType = paramType;
	}
	/**
	 * @return 表格类替换字段的key值
	 */
	public String getPoName() {
	 	return poName;
	}
	/**
	 * @设置 表格类替换字段的key值
	 * @param poName
	 */
	public void setPoName(String poName) {
	 	this.poName = poName;
	}
	/**
	 * @return 表格类对象的表头
	 */
	public String getTitleName() {
	 	return titleName;
	}
	/**
	 * @设置 表格类对象的表头
	 * @param titleName
	 */
	public void setTitleName(String titleName) {
	 	this.titleName = titleName;
	}
	/**
	 * @return 标签编号
	 */
	public String getBookGroup() {
	 	return bookGroup;
	}
	/**
	 * @设置 标签编号
	 * @param bookGroup
	 */
	public void setBookGroup(String bookGroup) {
	 	this.bookGroup = bookGroup;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getTableHeaderFlag() {
		return tableHeaderFlag;
	}
	public void setTableHeaderFlag(String tableHeaderFlag) {
		this.tableHeaderFlag = tableHeaderFlag;
	}
	public Integer getLineNum() {
		return lineNum;
	}
	public void setLineNum(Integer lineNum) {
		this.lineNum = lineNum;
	}
	public Integer getColumnNum() {
		return columnNum;
	}
	public void setColumnNum(Integer columnNum) {
		this.columnNum = columnNum;
	}
	public String getTagReplaceType() {
		return tagReplaceType;
	}
	public void setTagReplaceType(String tagReplaceType) {
		this.tagReplaceType = tagReplaceType;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public String getFormatParam() {
		return formatParam;
	}
	public void setFormatParam(String formatParam) {
		this.formatParam = formatParam;
	}
}