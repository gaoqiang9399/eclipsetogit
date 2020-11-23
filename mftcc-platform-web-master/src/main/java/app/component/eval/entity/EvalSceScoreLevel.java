package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: EvalSceScoreLevel.java
* Description:
* @author：@dhcc.com.cn
* @Thu Mar 31 12:01:52 GMT 2016
* @version：1.0
**/
public class EvalSceScoreLevel extends BaseDomain {
	private String scenceNo;//场景编号
	private String level;//级别
	private String upperScore;//分数上限
	private String lowerScore;//分数下限

	/**
	 * @return 场景编号
	 */
	public String getScenceNo() {
	 	return scenceNo;
	}
	/**
	 * @设置 场景编号
	 * @param scenceNo
	 */
	public void setScenceNo(String scenceNo) {
	 	this.scenceNo = scenceNo;
	}
	/**
	 * @return 级别
	 */
	public String getLevel() {
	 	return level;
	}
	/**
	 * @设置 级别
	 * @param level
	 */
	public void setLevel(String level) {
	 	this.level = level;
	}
	/**
	 * @return 分数上限
	 */
	public String getUpperScore() {
	 	return upperScore;
	}
	/**
	 * @设置 分数上限
	 * @param upperScore
	 */
	public void setUpperScore(String upperScore) {
	 	this.upperScore = upperScore;
	}
	/**
	 * @return 分数下限
	 */
	public String getLowerScore() {
	 	return lowerScore;
	}
	/**
	 * @设置 分数下限
	 * @param lowerScore
	 */
	public void setLowerScore(String lowerScore) {
	 	this.lowerScore = lowerScore;
	}
}