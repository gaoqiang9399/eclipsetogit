package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: EvalScenceAdjRel.java
* Description:
* @author：@dhcc.com.cn
* @Thu Mar 17 06:46:26 GMT 2016
* @version：1.0
**/
public class EvalScenceAdjRel extends BaseDomain {
	private String scenceNo;//评级场景编号
	private String indexNo;//指标编号
	private String indexName;//指标名称
	private String indexDesc;//指标描述
	private Double stdCore;//指标得分
	private String level;//指标级别
	private String upIndexNo;//上级指标编号
	private String gradeCardId;//评分卡编号
	/**
	 * @return 评级场景编号
	 */
	public String getScenceNo() {
	 	return scenceNo;
	}
	/**
	 * @设置 评级场景编号
	 * @param scenceNo
	 */
	public void setScenceNo(String scenceNo) {
	 	this.scenceNo = scenceNo;
	}
	/**
	 * @return 指标编号
	 */
	public String getIndexNo() {
	 	return indexNo;
	}
	/**
	 * @设置 指标编号
	 * @param indexNo
	 */
	public void setIndexNo(String indexNo) {
	 	this.indexNo = indexNo;
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
	 * @param level
	 */
	public void setLevel(String level) {
	 	this.level = level;
	}
	/**
	 * @return 上级指标编号
	 */
	public String getUpIndexNo() {
	 	return upIndexNo;
	}
	/**
	 * @设置 上级指标编号
	 * @param upIndexNo
	 */
	public void setUpIndexNo(String upIndexNo) {
	 	this.upIndexNo = upIndexNo;
	}
	public String getGradeCardId() {
		return gradeCardId;
	}
	public void setGradeCardId(String gradeCardId) {
		this.gradeCardId = gradeCardId;
	}
}