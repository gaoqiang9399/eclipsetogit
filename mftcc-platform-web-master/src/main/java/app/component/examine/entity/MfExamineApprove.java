package app.component.examine.entity;
import app.base.BaseDomain;
/**
* Title: MfExamineApprove.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Jul 27 18:21:50 CST 2017
* @version：1.0
**/
public class MfExamineApprove extends BaseDomain {
	private String approveId;//检查审批编号
	private String examHisId;//检查历史编号
	private String appName;//项目名称
	private String pactId;//合同编号
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String templateId;//检查模板编号
	private String templateName;//检查模板
	private String docuTemplate;//文档模板
	private String formTemplate;//表单模板
	private String riskLevel;//风险级别
	private String examOpNo;//检查人
	private String examOpName;//检查人姓名
	private String beginDate;//检查开始日期
	private String endDate;//检查完成日期
	private String remark;//备注
	private String examProcessId;//检查审批流程编号
	private String opNo;//登记人
	private String opName;//登记人姓名
	private String brNo;//登记部门
	private String brName;//登记部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间
	private String fincId;//借据号
	private Double examineScore;//检查得分
	private String adjFiveClass;//调整五级分类
	private String adjRiskLevel;//调整风险级别
	private String fiveClass;//调整风险级别

	/**
	 * @return 检查审批编号
	 */
	public String getApproveId() {
	 	return approveId;
	}
	/**
	 * @设置 检查审批编号
	 * @param approveId
	 */
	public void setApproveId(String approveId) {
	 	this.approveId = approveId;
	}
	/**
	 * @return 检查历史编号
	 */
	public String getExamHisId() {
	 	return examHisId;
	}
	/**
	 * @设置 检查历史编号
	 * @param examHisId
	 */
	public void setExamHisId(String examHisId) {
	 	this.examHisId = examHisId;
	}
	/**
	 * @return 项目名称
	 */
	public String getAppName() {
	 	return appName;
	}
	/**
	 * @设置 项目名称
	 * @param appName
	 */
	public void setAppName(String appName) {
	 	this.appName = appName;
	}
	/**
	 * @return 合同编号
	 */
	public String getPactId() {
	 	return pactId;
	}
	/**
	 * @设置 合同编号
	 * @param pactId
	 */
	public void setPactId(String pactId) {
	 	this.pactId = pactId;
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
	 * @return 检查模板编号
	 */
	public String getTemplateId() {
	 	return templateId;
	}
	/**
	 * @设置 检查模板编号
	 * @param templateId
	 */
	public void setTemplateId(String templateId) {
	 	this.templateId = templateId;
	}
	/**
	 * @return 检查模板
	 */
	public String getTemplateName() {
	 	return templateName;
	}
	/**
	 * @设置 检查模板
	 * @param templateName
	 */
	public void setTemplateName(String templateName) {
	 	this.templateName = templateName;
	}
	/**
	 * @return 文档模板
	 */
	public String getDocuTemplate() {
	 	return docuTemplate;
	}
	/**
	 * @设置 文档模板
	 * @param docuTemplate
	 */
	public void setDocuTemplate(String docuTemplate) {
	 	this.docuTemplate = docuTemplate;
	}
	/**
	 * @return 表单模板
	 */
	public String getFormTemplate() {
	 	return formTemplate;
	}
	/**
	 * @设置 表单模板
	 * @param formTemplate
	 */
	public void setFormTemplate(String formTemplate) {
	 	this.formTemplate = formTemplate;
	}
	/**
	 * @return 风险级别
	 */
	public String getRiskLevel() {
	 	return riskLevel;
	}
	/**
	 * @设置 风险级别
	 * @param riskLevel
	 */
	public void setRiskLevel(String riskLevel) {
	 	this.riskLevel = riskLevel;
	}
	/**
	 * @return 检查人
	 */
	public String getExamOpNo() {
	 	return examOpNo;
	}
	/**
	 * @设置 检查人
	 * @param examOpNo
	 */
	public void setExamOpNo(String examOpNo) {
	 	this.examOpNo = examOpNo;
	}
	/**
	 * @return 检查人姓名
	 */
	public String getExamOpName() {
	 	return examOpName;
	}
	/**
	 * @设置 检查人姓名
	 * @param examOpName
	 */
	public void setExamOpName(String examOpName) {
	 	this.examOpName = examOpName;
	}
	/**
	 * @return 检查开始日期
	 */
	public String getBeginDate() {
	 	return beginDate;
	}
	/**
	 * @设置 检查开始日期
	 * @param beginDate
	 */
	public void setBeginDate(String beginDate) {
	 	this.beginDate = beginDate;
	}
	/**
	 * @return 检查完成日期
	 */
	public String getEndDate() {
	 	return endDate;
	}
	/**
	 * @设置 检查完成日期
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
	 	this.endDate = endDate;
	}
	/**
	 * @return 备注
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 检查审批流程编号
	 */
	public String getExamProcessId() {
	 	return examProcessId;
	}
	/**
	 * @设置 检查审批流程编号
	 * @param examProcessId
	 */
	public void setExamProcessId(String examProcessId) {
	 	this.examProcessId = examProcessId;
	}
	/**
	 * @return 登记人
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 登记人姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记部门
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 借据号
	 */
	public String getFincId() {
	 	return fincId;
	}
	/**
	 * @设置 借据号
	 * @param fincId
	 */
	public void setFincId(String fincId) {
	 	this.fincId = fincId;
	}
	/**
	 * @return 检查得分
	 */
	public Double getExamineScore() {
	 	return examineScore;
	}
	/**
	 * @设置 检查得分
	 * @param examineScore
	 */
	public void setExamineScore(Double examineScore) {
	 	this.examineScore = examineScore;
	}
	/**
	 * @return 调整五级分类
	 */
	public String getAdjFiveClass() {
	 	return adjFiveClass;
	}
	/**
	 * @设置 调整五级分类
	 * @param adjFiveClass
	 */
	public void setAdjFiveClass(String adjFiveClass) {
	 	this.adjFiveClass = adjFiveClass;
	}
	/**
	 * @return 调整风险级别
	 */
	public String getAdjRiskLevel() {
	 	return adjRiskLevel;
	}
	/**
	 * @设置 调整风险级别
	 * @param adjRiskLevel
	 */
	public void setAdjRiskLevel(String adjRiskLevel) {
	 	this.adjRiskLevel = adjRiskLevel;
	}
	public String getFiveClass() {
		return fiveClass;
	}
	public void setFiveClass(String fiveClass) {
		this.fiveClass = fiveClass;
	}
}