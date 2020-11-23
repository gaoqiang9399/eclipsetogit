package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: EvalScenceRestrictRel.java
* Description:
* @author：@dhcc.com.cn
* @Tue Mar 22 01:41:22 GMT 2016
* @version：1.0
**/
public class EvalScenceRestrictRel extends BaseDomain {
	private String scenceNo;//评级场景编号
	private String indexNo;//指标编号
	private String indexName;//指标名称
	private String indexDesc;//指标描述
	private String javaItem;//
	private String opSymbol1;//区间符号1
	private String opSymbol2;//区间符号2
	private String opSymbol3;//区间符号3
	private Double opVal1;//区间值1
	private Double opVal2;//区间值2
	private String evalLevel;//约束级别
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
	 * @return 
	 */
	public String getJavaItem() {
	 	return javaItem;
	}
	/**
	 * @设置 
	 * @param javaItem
	 */
	public void setJavaItem(String javaItem) {
	 	this.javaItem = javaItem;
	}
	/**
	 * @return 区间符号1
	 */
	public String getOpSymbol1() {
	 	return opSymbol1;
	}
	/**
	 * @设置 区间符号1
	 * @param opSymbol1
	 */
	public void setOpSymbol1(String opSymbol1) {
	 	this.opSymbol1 = opSymbol1;
	}
	/**
	 * @return 区间符号2
	 */
	public String getOpSymbol2() {
	 	return opSymbol2;
	}
	/**
	 * @设置 区间符号2
	 * @param opSymbol2
	 */
	public void setOpSymbol2(String opSymbol2) {
	 	this.opSymbol2 = opSymbol2;
	}
	/**
	 * @return 区间符号3
	 */
	public String getOpSymbol3() {
	 	return opSymbol3;
	}
	/**
	 * @设置 区间符号3
	 * @param opSymbol3
	 */
	public void setOpSymbol3(String opSymbol3) {
	 	this.opSymbol3 = opSymbol3;
	}
	/**
	 * @return 区间值1
	 */
	public Double getOpVal1() {
	 	return opVal1;
	}
	/**
	 * @设置 区间值1
	 * @param opVal1
	 */
	public void setOpVal1(Double opVal1) {
	 	this.opVal1 = opVal1;
	}
	/**
	 * @return 区间值2
	 */
	public Double getOpVal2() {
	 	return opVal2;
	}
	/**
	 * @设置 区间值2
	 * @param opVal2
	 */
	public void setOpVal2(Double opVal2) {
	 	this.opVal2 = opVal2;
	}
	/**
	 * @return 约束级别
	 */
	public String getEvalLevel() {
	 	return evalLevel;
	}
	/**
	 * @设置 约束级别
	 * @param evalLevel
	 */
	public void setEvalLevel(String evalLevel) {
	 	this.evalLevel = evalLevel;
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