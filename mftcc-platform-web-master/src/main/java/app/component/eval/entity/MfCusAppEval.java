/**
 * 
 */
package app.component.eval.entity;

import app.base.BaseDomain;

/**
 * 客户评级查询
 * @author QiuZhao
 *
 */
public class MfCusAppEval extends BaseDomain {

	private static final long serialVersionUID = 1L;
	private String idNum;//证件号码
	private String evalAppNo;//评级申请号
	private String evalScenceNo;//评级场景编号
	private String evalScenceName;//评级场景编号
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String cusType;//客户类型
	private double finScore;//财务指标得分
	private double dlScore;//定量指标得分
	private double dxScore;//定性指标得分
	private double adjScore;//调整指标得分
	private double manAdjScore;//客户经理调整指标得分
	private double totalScore;//总分
	private double totalScoreTmp;//总分
	private String grade;//机评等级
	private String mangGrade;//客户经理建议等级
	private String approvalGrade;//审批认定等级
	private String rptType;//报表类型，月报年报季报
	private String rptDate;//报表日期
	private String evalStartDate;//评级起始日期
	private String evalEndDate;//评级到期日期
	private String filler;//备注
	private String evalSts;//评级申请状态
	private String orgNo;//登记机构号
	private String orgName;//登记机构名称
	private String regNo;//登记人
	private String regName;//登记人名称
	private String regDate;//登记日期
	private String lstDate;//更新日期
	private String lstModTime;//更新日期
	private String lstOrgNo;//更新登记机构号
	private String lstOrgName;//更新机构名称
	private String lstRegNo;//更新登记人
	private String lstRegName;//更新登记人名称
	private String appWorkFlowNo;//流程编号
	private String approveNodeName;//当前审批节点名称
	private String approvePartName;//当前审批角色/用户名称
	private String restrictLevel;//约束级别
	
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getEvalAppNo() {
		return evalAppNo;
	}
	public void setEvalAppNo(String evalAppNo) {
		this.evalAppNo = evalAppNo;
	}
	public String getEvalScenceNo() {
		return evalScenceNo;
	}
	public void setEvalScenceNo(String evalScenceNo) {
		this.evalScenceNo = evalScenceNo;
	}
	public String getEvalScenceName() {
		return evalScenceName;
	}
	public void setEvalScenceName(String evalScenceName) {
		this.evalScenceName = evalScenceName;
	}
	public String getCusNo() {
		return cusNo;
	}
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getCusType() {
		return cusType;
	}
	public void setCusType(String cusType) {
		this.cusType = cusType;
	}
	public double getFinScore() {
		return finScore;
	}
	public void setFinScore(double finScore) {
		this.finScore = finScore;
	}
	public double getDlScore() {
		return dlScore;
	}
	public void setDlScore(double dlScore) {
		this.dlScore = dlScore;
	}
	public double getDxScore() {
		return dxScore;
	}
	public void setDxScore(double dxScore) {
		this.dxScore = dxScore;
	}
	public double getAdjScore() {
		return adjScore;
	}
	public void setAdjScore(double adjScore) {
		this.adjScore = adjScore;
	}
	public double getManAdjScore() {
		return manAdjScore;
	}
	public void setManAdjScore(double manAdjScore) {
		this.manAdjScore = manAdjScore;
	}
	public double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}
	public double getTotalScoreTmp() {
		return totalScoreTmp;
	}
	public void setTotalScoreTmp(double totalScoreTmp) {
		this.totalScoreTmp = totalScoreTmp;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getMangGrade() {
		return mangGrade;
	}
	public void setMangGrade(String mangGrade) {
		this.mangGrade = mangGrade;
	}
	public String getApprovalGrade() {
		return approvalGrade;
	}
	public void setApprovalGrade(String approvalGrade) {
		this.approvalGrade = approvalGrade;
	}
	public String getRptType() {
		return rptType;
	}
	public void setRptType(String rptType) {
		this.rptType = rptType;
	}
	public String getRptDate() {
		return rptDate;
	}
	public void setRptDate(String rptDate) {
		this.rptDate = rptDate;
	}
	public String getEvalStartDate() {
		return evalStartDate;
	}
	public void setEvalStartDate(String evalStartDate) {
		this.evalStartDate = evalStartDate;
	}
	public String getEvalEndDate() {
		return evalEndDate;
	}
	public void setEvalEndDate(String evalEndDate) {
		this.evalEndDate = evalEndDate;
	}
	public String getFiller() {
		return filler;
	}
	public void setFiller(String filler) {
		this.filler = filler;
	}
	public String getEvalSts() {
		return evalSts;
	}
	public void setEvalSts(String evalSts) {
		this.evalSts = evalSts;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getLstDate() {
		return lstDate;
	}
	public void setLstDate(String lstDate) {
		this.lstDate = lstDate;
	}
	public String getLstOrgNo() {
		return lstOrgNo;
	}
	public void setLstOrgNo(String lstOrgNo) {
		this.lstOrgNo = lstOrgNo;
	}
	public String getLstOrgName() {
		return lstOrgName;
	}
	public void setLstOrgName(String lstOrgName) {
		this.lstOrgName = lstOrgName;
	}
	public String getLstRegNo() {
		return lstRegNo;
	}
	public void setLstRegNo(String lstRegNo) {
		this.lstRegNo = lstRegNo;
	}
	public String getLstRegName() {
		return lstRegName;
	}
	public void setLstRegName(String lstRegName) {
		this.lstRegName = lstRegName;
	}
	public String getAppWorkFlowNo() {
		return appWorkFlowNo;
	}
	public void setAppWorkFlowNo(String appWorkFlowNo) {
		this.appWorkFlowNo = appWorkFlowNo;
	}
	public String getApproveNodeName() {
		return approveNodeName;
	}
	public void setApproveNodeName(String approveNodeName) {
		this.approveNodeName = approveNodeName;
	}
	public String getApprovePartName() {
		return approvePartName;
	}
	public void setApprovePartName(String approvePartName) {
		this.approvePartName = approvePartName;
	}
	public String getRestrictLevel() {
		return restrictLevel;
	}
	public void setRestrictLevel(String restrictLevel) {
		this.restrictLevel = restrictLevel;
	}
	public String getLstModTime() {
		return lstModTime;
	}
	public void setLstModTime(String lstModTime) {
		this.lstModTime = lstModTime;
	}
	
}
