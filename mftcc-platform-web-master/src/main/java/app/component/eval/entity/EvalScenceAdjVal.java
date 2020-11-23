package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: EvalScenceAdjVal.java
* Description:
* @author：@dhcc.com.cn
* @Tue Apr 05 06:46:34 GMT 2016
* @version：1.0
**/
public class EvalScenceAdjVal extends BaseDomain {
	private String evalAppNo;//得分列表
	private String evalScenceNo;//评级业务场景编号
	private Double score;//SCORE
	private String scoreList;//SCORE_LIST
	private String gradeCardId;//评分卡编号
	/**
	 * @return 得分列表
	 */
	public String getEvalAppNo() {
	 	return evalAppNo;
	}
	/**
	 * @设置 得分列表
	 * @param evalAppNo
	 */
	public void setEvalAppNo(String evalAppNo) {
	 	this.evalAppNo = evalAppNo;
	}
	/**
	 * @return 评级业务场景编号
	 */
	public String getEvalScenceNo() {
	 	return evalScenceNo;
	}
	/**
	 * @设置 评级业务场景编号
	 * @param evalScenceNo
	 */
	public void setEvalScenceNo(String evalScenceNo) {
	 	this.evalScenceNo = evalScenceNo;
	}
	/**
	 * @return SCORE
	 */
	public Double getScore() {
	 	return score;
	}
	/**
	 * @设置 SCORE
	 * @param score
	 */
	public void setScore(Double score) {
	 	this.score = score;
	}
	/**
	 * @return SCORE_LIST
	 */
	public String getScoreList() {
	 	return scoreList;
	}
	/**
	 * @设置 SCORE_LIST
	 * @param scoreList
	 */
	public void setScoreList(String scoreList) {
	 	this.scoreList = scoreList;
	}
	public String getGradeCardId() {
		return gradeCardId;
	}
	public void setGradeCardId(String gradeCardId) {
		this.gradeCardId = gradeCardId;
	}
}