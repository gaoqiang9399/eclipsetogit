package app.component.sys.entity;

import app.base.BaseDomain;

/**
 * 此类为sql组装作用类 没有实现表
 */
public class SysGlobalSearch  extends BaseDomain{
	private String configNo;//配置编号
	private String tableName;//表名
	private String tableColumns;//字段数组
	private String selectColumns;//查询的字段
	private String tableColState;//字段说明
	private String vals; //查询值数组
	private String url;//请求链接
	private String belong;//归属类别
	private String fixedQuery;//固定查询
	private String funNoSql;//权限查询sql
	private String sts;//启用状态
	private String params;//数据字典项
	private String prmKey;//表主键
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableColumns() {
		return tableColumns;
	}
	public void setTableColumns(String tableColumns) {
		this.tableColumns = tableColumns;
	}
	public String getVals() {
		return vals;
	}
	public void setVals(String vals) {
		this.vals = vals;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public String getTableColState() {
		return tableColState;
	}
	public void setTableColState(String tableColState) {
		this.tableColState = tableColState;
	}
	public String getConfigNo() {
		return configNo;
	}
	public void setConfigNo(String configNo) {
		this.configNo = configNo;
	}
	public String getSelectColumns() {
		return selectColumns;
	}
	public void setSelectColumns(String selectColumns) {
		this.selectColumns = selectColumns;
	}
	public String getFixedQuery() {
		return fixedQuery;
	}
	public void setFixedQuery(String fixedQuery) {
		this.fixedQuery = fixedQuery;
	}
	public String getFunNoSql() {
		return funNoSql;
	}
	public void setFunNoSql(String funNoSql) {
		this.funNoSql = funNoSql;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getPrmKey() {
		return prmKey;
	}
	public void setPrmKey(String prmKey) {
		this.prmKey = prmKey;
	}
}
