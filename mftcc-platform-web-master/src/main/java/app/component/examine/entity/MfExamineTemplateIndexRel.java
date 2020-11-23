package app.component.examine.entity;
import app.base.BaseDomain;
/**
* Title: MfExamineTemplateIndexRel.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jul 24 14:29:55 CST 2017
* @version：1.0
**/
public class MfExamineTemplateIndexRel extends BaseDomain {
	private String examTemplateId;//贷后检查模型编号
	private String indexId;//指标编号
	private String indexName;//指标名称
	private String indexDesc;//指标描述
	private Double stdCore;//指标得分
	private String level;//指标级别
	private String upIndexId;//上级指标编号
	private String examineCardId;//检查卡编号
	private String indexRiskLevel;//指标风险级别

	/**
	 * @return 贷后检查模型编号
	 */
	public String getExamTemplateId() {
	 	return examTemplateId;
	}
	/**
	 * @设置 贷后检查模型编号
	 * @param examTemplateId
	 */
	public void setExamTemplateId(String examTemplateId) {
	 	this.examTemplateId = examTemplateId;
	}
	/**
	 * @return 指标编号
	 */
	public String getIndexId() {
	 	return indexId;
	}
	/**
	 * @设置 指标编号
	 * @param indexId
	 */
	public void setIndexId(String indexId) {
	 	this.indexId = indexId;
	}
	/**
	 * @return 指标名称
	 */
	public String getIndexName() {
	 	return indexName;
	}
	/**
	 * @设置 指标名称
	 * @param indexName
	 */
	public void setIndexName(String indexName) {
	 	this.indexName = indexName;
	}
	/**
	 * @return 指标描述
	 */
	public String getIndexDesc() {
	 	return indexDesc;
	}
	/**
	 * @设置 指标描述
	 * @param indexDesc
	 */
	public void setIndexDesc(String indexDesc) {
	 	this.indexDesc = indexDesc;
	}
	/**
	 * @return 指标得分
	 */
	public Double getStdCore() {
	 	return stdCore;
	}
	/**
	 * @设置 指标得分
	 * @param stdCore
	 */
	public void setStdCore(Double stdCore) {
	 	this.stdCore = stdCore;
	}
	/**
	 * @return 指标级别
	 */
	public String getLevel() {
	 	return level;
	}
	/**
	 * @设置 指标级别
	 * @param leval
	 */
	public void setLevel(String level) {
	 	this.level = level;
	}
	/**
	 * @return 上级指标编号
	 */
	public String getUpIndexId() {
	 	return upIndexId;
	}
	/**
	 * @设置 上级指标编号
	 * @param upIndexId
	 */
	public void setUpIndexId(String upIndexId) {
	 	this.upIndexId = upIndexId;
	}
	public String getExamineCardId() {
		return examineCardId;
	}
	public void setExamineCardId(String examineCardId) {
		this.examineCardId = examineCardId;
	}
	public String getIndexRiskLevel() {
		return indexRiskLevel;
	}
	public void setIndexRiskLevel(String indexRiskLevel) {
		this.indexRiskLevel = indexRiskLevel;
	}
	
}