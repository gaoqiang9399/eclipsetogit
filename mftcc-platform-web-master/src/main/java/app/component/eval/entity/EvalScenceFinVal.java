package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: EvalScenceFinVal.java
* Description:
* @author：@dhcc.com.cn
* @Thu Mar 31 06:35:36 GMT 2016
* @version：1.0
**/
public class EvalScenceFinVal extends BaseDomain {
	private String evalAppNo;//评级申请号
	private String evalScenceNo;//评级业务场景编号
	private Double score;//得分
	private String scoreList;//得分列表
	private String gradeCardId;//评分卡编号
	/**
	 * @return 评级申请号
	 */
	public String getEvalAppNo() {
	 	return evalAppNo;
	}
	/**
	 * @设置 评级申请号
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
	public String getGradeCardId() {
		return gradeCardId;
	}
	public void setGradeCardId(String gradeCardId) {
		this.gradeCardId = gradeCardId;
	}
}