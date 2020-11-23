package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: EvalScoreGradeConfig.java
* Description:
* @author：@dhcc.com.cn
* @Wed Apr 27 06:10:07 GMT 2016
* @version：1.0
**/
public class EvalScoreGradeConfig extends BaseDomain {
	private String evalScenceNo;//评级场景编号
	private String configNo;//配置编号
	private String opSymbol1;//操作符1
	private String opSymbol2;//操作符2
	private Double opScore1;//区间分数1
	private Double opScore2;//区间分数2
	private String evalLevel;//级别
	private String evalLevelEn;//级别,例如A，AA
	private String evalAssess;//综合评价信息
	private Double creditAmt;//评级对应参考授信额度
	private String cusType;//客户类型 1为企业客户 2为个人客户
	private String evalLevelNew;//新级别

	private String evalClass;//评级类型;1-信用评级 2-债项评级
	private String evalGradeClass;//分类等级
	private String evalGradeLevel;//明细等级
	private String evalGradeClassDesc;//等级描述

	public String getEvalClass() {
		return evalClass;
	}

	public void setEvalClass(String evalClass) {
		this.evalClass = evalClass;
	}

	public String getEvalGradeClass() {
		return evalGradeClass;
	}

	public void setEvalGradeClass(String evalGradeClass) {
		this.evalGradeClass = evalGradeClass;
	}

	public String getEvalGradeLevel() {
		return evalGradeLevel;
	}

	public void setEvalGradeLevel(String evalGradeLevel) {
		this.evalGradeLevel = evalGradeLevel;
	}

	public String getEvalGradeClassDesc() {
		return evalGradeClassDesc;
	}

	public void setEvalGradeClassDesc(String evalGradeClassDesc) {
		this.evalGradeClassDesc = evalGradeClassDesc;
	}

	public String getEvalLevelNew() {
		return evalLevelNew;
	}

	public void setEvalLevelNew(String evalLevelNew) {
		this.evalLevelNew = evalLevelNew;
	}

	public String getCusType() {
		return cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
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
	 * @return 配置编号
	 */
	public String getConfigNo() {
	 	return configNo;
	}
	/**
	 * @设置 配置编号
	 * @param configNo
	 */
	public void setConfigNo(String configNo) {
	 	this.configNo = configNo;
	}
	/**
	 * @return 操作符1
	 */
	public String getOpSymbol1() {
	 	return opSymbol1;
	}
	/**
	 * @设置 操作符1
	 * @param opSymbol1
	 */
	public void setOpSymbol1(String opSymbol1) {
	 	this.opSymbol1 = opSymbol1;
	}
	/**
	 * @return 操作符2
	 */
	public String getOpSymbol2() {
	 	return opSymbol2;
	}
	/**
	 * @设置 操作符2
	 * @param opSymbol2
	 */
	public void setOpSymbol2(String opSymbol2) {
	 	this.opSymbol2 = opSymbol2;
	}
	/**
	 * @return 区间分数1
	 */
	public Double getOpScore1() {
	 	return opScore1;
	}
	/**
	 * @设置 区间分数1
	 * @param opScore1
	 */
	public void setOpScore1(Double opScore1) {
	 	this.opScore1 = opScore1;
	}
	/**
	 * @return 区间分数2
	 */
	public Double getOpScore2() {
	 	return opScore2;
	}
	/**
	 * @设置 区间分数2
	 * @param opScore2
	 */
	public void setOpScore2(Double opScore2) {
	 	this.opScore2 = opScore2;
	}
	/**
	 * @return 级别
	 */
	public String getEvalLevel() {
	 	return evalLevel;
	}
	/**
	 * @设置 级别
	 * @param evalLevel
	 */
	public void setEvalLevel(String evalLevel) {
	 	this.evalLevel = evalLevel;
	}
	public String getEvalAssess() {
		return evalAssess;
	}
	public void setEvalAssess(String evalAssess) {
		this.evalAssess = evalAssess;
	}
	public String getEvalLevelEn() {
		return evalLevelEn;
	}
	public void setEvalLevelEn(String evalLevelEn) {
		this.evalLevelEn = evalLevelEn;
	}
	public Double getCreditAmt() {
		return creditAmt;
	}
	public void setCreditAmt(Double creditAmt) {
		this.creditAmt = creditAmt;
	}
	
	
	
}