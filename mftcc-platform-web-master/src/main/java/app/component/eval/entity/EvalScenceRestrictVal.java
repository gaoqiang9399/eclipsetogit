package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: EvalScenceRestrictVal.java
* Description:
* @author：@dhcc.com.cn
* @Thu Mar 31 06:39:47 GMT 2016
* @version：1.0
**/
public class EvalScenceRestrictVal extends BaseDomain {
	private String evalAppNo;//评级申请号
	private String evalRestrictLevel;//评级约束级别
	private String evalRestrictList;//评级约束指标列表
	private String ifRestrict;//是否约束
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
	 * @return 评级约束级别
	 */
	public String getEvalRestrictLevel() {
	 	return evalRestrictLevel;
	}
	/**
	 * @设置 评级约束级别
	 * @param evalRestrictLevel
	 */
	public void setEvalRestrictLevel(String evalRestrictLevel) {
	 	this.evalRestrictLevel = evalRestrictLevel;
	}
	/**
	 * @return 评级约束指标列表
	 */
	public String getEvalRestrictList() {
	 	return evalRestrictList;
	}
	/**
	 * @设置 评级约束指标列表
	 * @param evalRestrictList
	 */
	public void setEvalRestrictList(String evalRestrictList) {
	 	this.evalRestrictList = evalRestrictList;
	}
	/**
	 * @return 是否约束
	 */
	public String getIfRestrict() {
	 	return ifRestrict;
	}
	/**
	 * @设置 是否约束
	 * @param ifRestrict
	 */
	public void setIfRestrict(String ifRestrict) {
	 	this.ifRestrict = ifRestrict;
	}
	public String getGradeCardId() {
		return gradeCardId;
	}
	public void setGradeCardId(String gradeCardId) {
		this.gradeCardId = gradeCardId;
	}
}