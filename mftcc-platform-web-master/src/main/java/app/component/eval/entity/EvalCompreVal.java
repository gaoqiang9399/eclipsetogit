package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: EvalCompreVal.java
* Description:
* @author：@dhcc.com.cn
* @Thu Mar 31 06:43:39 GMT 2016
* @version：1.0
**/
public class EvalCompreVal extends BaseDomain {
	private String evalAppNo;//评级申请编号
	private String evalScenceNo;//评级场景编号
	private Double dlScore;//定量得分
	private Double dlScorePercent;//定量所占权重
	private Double dxScore;//定性得分
	private Double dxScorePercent;//定性所占权重
	private Double finScore;//财务得分
	private Double finScorePercent;//财务所占权重
	private Double manAdjustScore;//客户经理调整分数
	private String manAdjustCause;//客户经理调整得分理由
	private Double adjustScore;//调整指标得分
	private String adjustList;//调整指标列表
	private String restrictLevel;//约束级别

	/**
	 * @return 评级申请编号
	 */
	public String getEvalAppNo() {
	 	return evalAppNo;
	}
	/**
	 * @设置 评级申请编号
	 * @param evalAppNo
	 */
	public void setEvalAppNo(String evalAppNo) {
	 	this.evalAppNo = evalAppNo;
	}
	/**
	 * @return 评级场景编号
	 */
	public String getEvalScenceNo() {
	 	return evalScenceNo;
	}
	/**
	 * @设置 评级场景编号
	 * @param evalScenceNo
	 */
	public void setEvalScenceNo(String evalScenceNo) {
	 	this.evalScenceNo = evalScenceNo;
	}
	/**
	 * @return 定量得分
	 */
	public Double getDlScore() {
	 	return dlScore;
	}
	/**
	 * @设置 定量得分
	 * @param dlScore
	 */
	public void setDlScore(Double dlScore) {
	 	this.dlScore = dlScore;
	}
	/**
	 * @return 定量所占权重
	 */
	public Double getDlScorePercent() {
	 	return dlScorePercent;
	}
	/**
	 * @设置 定量所占权重
	 * @param dlScorePercent
	 */
	public void setDlScorePercent(Double dlScorePercent) {
	 	this.dlScorePercent = dlScorePercent;
	}
	/**
	 * @return 定性得分
	 */
	public Double getDxScore() {
	 	return dxScore;
	}
	/**
	 * @设置 定性得分
	 * @param dxScore
	 */
	public void setDxScore(Double dxScore) {
	 	this.dxScore = dxScore;
	}
	/**
	 * @return 定性所占权重
	 */
	public Double getDxScorePercent() {
	 	return dxScorePercent;
	}
	/**
	 * @设置 定性所占权重
	 * @param dxScorePercent
	 */
	public void setDxScorePercent(Double dxScorePercent) {
	 	this.dxScorePercent = dxScorePercent;
	}
	/**
	 * @return 财务得分
	 */
	public Double getFinScore() {
	 	return finScore;
	}
	/**
	 * @设置 财务得分
	 * @param finScore
	 */
	public void setFinScore(Double finScore) {
	 	this.finScore = finScore;
	}
	/**
	 * @return 财务所占权重
	 */
	public Double getFinScorePercent() {
	 	return finScorePercent;
	}
	/**
	 * @设置 财务所占权重
	 * @param finScorePercent
	 */
	public void setFinScorePercent(Double finScorePercent) {
	 	this.finScorePercent = finScorePercent;
	}
	/**
	 * @return 客户经理调整分数
	 */
	public Double getManAdjustScore() {
	 	return manAdjustScore;
	}
	/**
	 * @设置 客户经理调整分数
	 * @param manAdjustScore
	 */
	public void setManAdjustScore(Double manAdjustScore) {
	 	this.manAdjustScore = manAdjustScore;
	}
	/**
	 * @return 客户经理调整得分理由
	 */
	public String getManAdjustCause() {
	 	return manAdjustCause;
	}
	/**
	 * @设置 客户经理调整得分理由
	 * @param manAdjustCause
	 */
	public void setManAdjustCause(String manAdjustCause) {
	 	this.manAdjustCause = manAdjustCause;
	}
	/**
	 * @return 调整指标得分
	 */
	public Double getAdjustScore() {
	 	return adjustScore;
	}
	/**
	 * @设置 调整指标得分
	 * @param adjustScore
	 */
	public void setAdjustScore(Double adjustScore) {
	 	this.adjustScore = adjustScore;
	}
	/**
	 * @return 调整指标列表
	 */
	public String getAdjustList() {
	 	return adjustList;
	}
	/**
	 * @设置 调整指标列表
	 * @param adjustList
	 */
	public void setAdjustList(String adjustList) {
	 	this.adjustList = adjustList;
	}
	/**
	 * @return 约束级别
	 */
	public String getRestrictLevel() {
	 	return restrictLevel;
	}
	/**
	 * @设置 约束级别
	 * @param restrictLevel
	 */
	public void setRestrictLevel(String restrictLevel) {
	 	this.restrictLevel = restrictLevel;
	}
}