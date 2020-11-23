package app.component.report.entity;
import app.base.BaseDomain;
/**
* Title: MfReportQueryConditionConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Aug 24 17:03:14 CST 2017
* @version：1.0
**/
public class MfReportQueryConditionConfig extends BaseDomain {
	private String id;//流水号
	private String reportId;//报表编号
	private String reportName;//报表名称
	private String conditonContent;//查询条件值
	private String useFlag;//启用标志
	private String sqlJavaFlag;//sql实现方式 1-sql 2-javaBean
	private String jsonFlag;//返回类型 1-json类型 0-字符串
	private String conditonShow;//查询条件内容

	/**
	 * @return 流水号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 流水号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 报表编号
	 */
	public String getReportId() {
	 	return reportId;
	}
	/**
	 * @设置 报表编号
	 * @param reportId
	 */
	public void setReportId(String reportId) {
	 	this.reportId = reportId;
	}
	/**
	 * @return 报表名称
	 */
	public String getReportName() {
	 	return reportName;
	}
	/**
	 * @设置 报表名称
	 * @param reportName
	 */
	public void setReportName(String reportName) {
	 	this.reportName = reportName;
	}
	/**
	 * @return 查询条件
	 */
	public String getConditonContent() {
	 	return conditonContent;
	}
	/**
	 * @设置 查询条件
	 * @param conditonContent
	 */
	public void setConditonContent(String conditonContent) {
	 	this.conditonContent = conditonContent;
	}
	/**
	 * @return 启用标志
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 启用标志
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return sql实现方式 1-sql 2-javaBean
	 */
	public String getSqlJavaFlag() {
	 	return sqlJavaFlag;
	}
	/**
	 * @设置 sql实现方式 1-sql 2-javaBean
	 * @param sqlJavaFlag
	 */
	public void setSqlJavaFlag(String sqlJavaFlag) {
	 	this.sqlJavaFlag = sqlJavaFlag;
	}
	public String getJsonFlag() {
		return jsonFlag;
	}
	public void setJsonFlag(String jsonFlag) {
		this.jsonFlag = jsonFlag;
	}
	public String getConditonShow() {
		return conditonShow;
	}
	public void setConditonShow(String conditonShow) {
		this.conditonShow = conditonShow;
	}
	
}