package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: AppProjectEval.java
* Description:
* @author：@dhcc.com.cn
* @Thu Jul 14 01:17:56 GMT 2016
* @version：1.0
**/
public class AppProjectEval extends BaseDomain {
	private String evalAppNo;//评级申请号
	private String appNo;//业务申请号
	private String evalScenceNo;//评级场景编号
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String cusType;//客户类型
	private Double finScore;//定量线性指标得分
	private Double dlScore;//定量阶段性指标得分
	private Double dxScore;//定性指标得分
	private Double totalScore;//总分
	private String grade;//机评等级
	private String rptDate;//报表日期
	private String filler;//备注
	private String orgNo;//登记机构号
	private String orgName;//登记机构名称
	private String regNo;//登记人
	private String regName;//登记人名称
	private String regDate;//登记日期

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
	 * @return 业务申请号
	 */
	public String getAppNo() {
	 	return appNo;
	}
	/**
	 * @设置 业务申请号
	 * @param appNo
	 */
	public void setAppNo(String appNo) {
	 	this.appNo = appNo;
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
	 * @return 客户号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户号
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 客户名称
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 客户名称
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 客户类型
	 */
	public String getCusType() {
	 	return cusType;
	}
	/**
	 * @设置 客户类型
	 * @param cusType
	 */
	public void setCusType(String cusType) {
	 	this.cusType = cusType;
	}
	/**
	 * @return 财务指标得分
	 */
	public Double getFinScore() {
	 	return finScore;
	}
	/**
	 * @设置 财务指标得分
	 * @param finScore
	 */
	public void setFinScore(Double finScore) {
	 	this.finScore = finScore;
	}
	/**
	 * @return 定量指标得分
	 */
	public Double getDlScore() {
	 	return dlScore;
	}
	/**
	 * @设置 定量指标得分
	 * @param dlScore
	 */
	public void setDlScore(Double dlScore) {
	 	this.dlScore = dlScore;
	}
	/**
	 * @return 定性指标得分
	 */
	public Double getDxScore() {
		return dxScore;
	}
	/**
	 * @设置 定性指标得分
	 * @param dxScore
	 */
	public void setDxScore(Double dxScore) {
		this.dxScore = dxScore;
	}
	/**
	 * @return 总分
	 */
	public Double getTotalScore() {
	 	return totalScore;
	}
	/**
	 * @设置 总分
	 * @param totalScore
	 */
	public void setTotalScore(Double totalScore) {
	 	this.totalScore = totalScore;
	}
	/**
	 * @return 机评等级
	 */
	public String getGrade() {
	 	return grade;
	}
	/**
	 * @设置 机评等级
	 * @param grade
	 */
	public void setGrade(String grade) {
	 	this.grade = grade;
	}
	/**
	 * @return 报表日期
	 */
	public String getRptDate() {
	 	return rptDate;
	}
	/**
	 * @设置 报表日期
	 * @param rptDate
	 */
	public void setRptDate(String rptDate) {
	 	this.rptDate = rptDate;
	}
	/**
	 * @return 备注
	 */
	public String getFiller() {
	 	return filler;
	}
	/**
	 * @设置 备注
	 * @param filler
	 */
	public void setFiller(String filler) {
	 	this.filler = filler;
	}
	/**
	 * @return 登记机构号
	 */
	public String getOrgNo() {
	 	return orgNo;
	}
	/**
	 * @设置 登记机构号
	 * @param orgNo
	 */
	public void setOrgNo(String orgNo) {
	 	this.orgNo = orgNo;
	}
	/**
	 * @return 登记机构名称
	 */
	public String getOrgName() {
	 	return orgName;
	}
	/**
	 * @设置 登记机构名称
	 * @param orgName
	 */
	public void setOrgName(String orgName) {
	 	this.orgName = orgName;
	}
	/**
	 * @return 登记人
	 */
	public String getRegNo() {
	 	return regNo;
	}
	/**
	 * @设置 登记人
	 * @param regNo
	 */
	public void setRegNo(String regNo) {
	 	this.regNo = regNo;
	}
	/**
	 * @return 登记人名称
	 */
	public String getRegName() {
	 	return regName;
	}
	/**
	 * @设置 登记人名称
	 * @param regName
	 */
	public void setRegName(String regName) {
	 	this.regName = regName;
	}
	/**
	 * @return 登记日期
	 */
	public String getRegDate() {
	 	return regDate;
	}
	/**
	 * @设置 登记日期
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
	 	this.regDate = regDate;
	}
}