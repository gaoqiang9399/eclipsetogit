package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: EvalSysAssess.java
* Description:
* @author：@dhcc.com.cn
* @Tue Jul 26 02:04:05 GMT 2016
* @version：1.0
**/
public class EvalSysAssess extends BaseDomain {
	private String evalLevel;//评级级别
	private String evalAssess;//综合评价信息

	/**
	 * @return 评级级别
	 */
	public String getEvalLevel() {
	 	return evalLevel;
	}
	/**
	 * @设置 评级级别
	 * @param evalLevel
	 */
	public void setEvalLevel(String evalLevel) {
	 	this.evalLevel = evalLevel;
	}
	/**
	 * @return 综合评价信息
	 */
	public String getEvalAssess() {
	 	return evalAssess;
	}
	/**
	 * @设置 综合评价信息
	 * @param evalAssess
	 */
	public void setEvalAssess(String evalAssess) {
	 	this.evalAssess = evalAssess;
	}
}