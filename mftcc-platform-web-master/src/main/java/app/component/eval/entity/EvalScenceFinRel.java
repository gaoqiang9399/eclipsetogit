package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: EvalScenceFinRel.java
* Description:
* @author：@dhcc.com.cn
* @Thu Mar 17 06:44:16 GMT 2016
* @version：1.0
**/
public class EvalScenceFinRel extends BaseDomain {
	private String scenceNo;//评级场景编号
	private String indexNo;//指标编号
	private String finCode;//对应财务指标编号
	private String indexName;//指标名称
	private String indexDesc;//指标描述
	private String level;//指标级别
	private String upIndexNo;//上级指标级别
	private Double stdVal;//标准值
	private Double minVal;//最低值
	private Double maxVal;//最高值
	private Double stdScore;//标准得分
	private Double minScore;//最低得分
	private Double maxScore;//最高得分
	private String useFlag;//是否可用
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
	 * @return 对应财务指标编号
	 */
	public String getFinCode() {
	 	return finCode;
	}
	/**
	 * @设置 对应财务指标编号
	 * @param finCode
	 */
	public void setFinCode(String finCode) {
	 	this.finCode = finCode;
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
	 * @return 上级指标级别
	 */
	public String getUpIndexNo() {
	 	return upIndexNo;
	}
	/**
	 * @设置 上级指标级别
	 * @param upIndexNo
	 */
	public void setUpIndexNo(String upIndexNo) {
	 	this.upIndexNo = upIndexNo;
	}
	/**
	 * @return 标准值
	 */
	public Double getStdVal() {
	 	return stdVal;
	}
	/**
	 * @设置 标准值
	 * @param stdVal
	 */
	public void setStdVal(Double stdVal) {
	 	this.stdVal = stdVal;
	}
	/**
	 * @return 最低值
	 */
	public Double getMinVal() {
	 	return minVal;
	}
	/**
	 * @设置 最低值
	 * @param minVal
	 */
	public void setMinVal(Double minVal) {
	 	this.minVal = minVal;
	}
	/**
	 * @return 最高值
	 */
	public Double getMaxVal() {
	 	return maxVal;
	}
	/**
	 * @设置 最高值
	 * @param maxVal
	 */
	public void setMaxVal(Double maxVal) {
	 	this.maxVal = maxVal;
	}
	/**
	 * @return 标准得分
	 */
	public Double getStdScore() {
	 	return stdScore;
	}
	/**
	 * @设置 标准得分
	 * @param stdScore
	 */
	public void setStdScore(Double stdScore) {
	 	this.stdScore = stdScore;
	}
	/**
	 * @return 最低得分
	 */
	public Double getMinScore() {
	 	return minScore;
	}
	/**
	 * @设置 最低得分
	 * @param minScore
	 */
	public void setMinScore(Double minScore) {
	 	this.minScore = minScore;
	}
	/**
	 * @return 最高得分
	 */
	public Double getMaxScore() {
	 	return maxScore;
	}
	/**
	 * @设置 最高得分
	 * @param maxScore
	 */
	public void setMaxScore(Double maxScore) {
	 	this.maxScore = maxScore;
	}
	/**
	 * @return 是否可用
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 是否可用
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	public String getGradeCardId() {
		return gradeCardId;
	}
	public void setGradeCardId(String gradeCardId) {
		this.gradeCardId = gradeCardId;
	}
}