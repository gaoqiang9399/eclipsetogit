package app.component.examine.entity;
import app.base.BaseDomain;
/**
* Title: MfExamineIndexVal.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Jul 26 23:52:34 CST 2017
* @version：1.0
**/
public class MfExamineIndexVal extends BaseDomain {
	private String indexValId;//得分编号
	private String examHisId;//评级申请号
	private String examTemplateId;//贷后检查模型编号
	private Double score;//得分
	private String scoreList;//得分列表
	private String examCardId;//检查卡编号

	/**
	 * @return 得分编号
	 */
	public String getIndexValId() {
	 	return indexValId;
	}
	/**
	 * @设置 得分编号
	 * @param indexValId
	 */
	public void setIndexValId(String indexValId) {
	 	this.indexValId = indexValId;
	}
	/**
	 * @return 评级申请号
	 */
	public String getExamHisId() {
	 	return examHisId;
	}
	/**
	 * @设置 评级申请号
	 * @param examHisId
	 */
	public void setExamHisId(String examHisId) {
	 	this.examHisId = examHisId;
	}
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
	 * @return 得分
	 */
	public Double getScore() {
	 	return score;
	}
	/**
	 * @设置 得分
	 * @param score
	 */
	public void setScore(Double score) {
	 	this.score = score;
	}
	/**
	 * @return 得分列表
	 */
	public String getScoreList() {
	 	return scoreList;
	}
	/**
	 * @设置 得分列表
	 * @param scoreList
	 */
	public void setScoreList(String scoreList) {
	 	this.scoreList = scoreList;
	}
	public String getExamCardId() {
		return examCardId;
	}
	public void setExamCardId(String examCardId) {
		this.examCardId = examCardId;
	}
}