package app.component.examine.entity;
import app.base.BaseDomain;
/**
* Title: MfExamineTemplateConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Feb 09 15:35:54 CST 2017
* @version：1.0
**/
public class MfExamineTemplateConfig extends BaseDomain {
	private String templateId;//模板编号
	private String templateName;//检查模板名称
	private Integer timeLimit;//要求完成期限，单位天
	private String busModel;//业务模式
	private String busModelName;//业务模式名称
	private String kindNo;//产品种类
	private String kindName;//产品种类名称
	private String vouType;//担保方式
	private Double amt;//金额
	private Double amtMin;//金额下限
	private Double amtMax;//金额上限
	private Integer term;//期限
	private Integer termMin;//期限下限
	private Integer termMax;//期限上限
	private Double rate;//利率
	private Double rateMin;//利率下限
	private Double rateMax;//利率上限
	private String templateType;//模板类型
	private String docuTemplate;//文档模板名称
	private String formTemplate;//表单模板编号
	private String baseFormTemplate;//基础表单模板编号
	private String riskWarmModelNo;//风险预警模板
	private String riskWarnModel;//风险预警模板名称
	private String useFlag;//启用标识
	private String remark;//检查模板说明
	private String regTime;//登记时间
	private String cusType;//客户类型
	private String repayType;//还款方式
	private String tempSerialId;//还款方式
	private String rulesTemplateId;//调用评分规则的规则id

	/**
	 * @return 模板编号
	 */
	public String getTemplateId() {
	 	return templateId;
	}
	/**
	 * @设置 模板编号
	 * @param templateId
	 */
	public void setTemplateId(String templateId) {
	 	this.templateId = templateId;
	}
	/**
	 * @return 检查模板名称
	 */
	public String getTemplateName() {
	 	return templateName;
	}
	/**
	 * @设置 检查模板名称
	 * @param templateName
	 */
	public void setTemplateName(String templateName) {
	 	this.templateName = templateName;
	}
	/**
	 * @return 要求完成期限，单位天
	 */
	public Integer getTimeLimit() {
	 	return timeLimit;
	}
	/**
	 * @设置 要求完成期限，单位天
	 * @param timeLimit
	 */
	public void setTimeLimit(Integer timeLimit) {
	 	this.timeLimit = timeLimit;
	}
	/**
	 * @return 业务模式
	 */
	public String getBusModel() {
	 	return busModel;
	}
	/**
	 * @设置 业务模式
	 * @param busModel
	 */
	public void setBusModel(String busModel) {
	 	this.busModel = busModel;
	}
	/**
	 * @return 业务模式名称
	 */
	public String getBusModelName() {
	 	return busModelName;
	}
	/**
	 * @设置 业务模式名称
	 * @param busModelName
	 */
	public void setBusModelName(String busModelName) {
	 	this.busModelName = busModelName;
	}
	/**
	 * @return 产品种类
	 */
	public String getKindNo() {
	 	return kindNo;
	}
	/**
	 * @设置 产品种类
	 * @param kindNo
	 */
	public void setKindNo(String kindNo) {
	 	this.kindNo = kindNo;
	}
	/**
	 * @return 产品种类名称
	 */
	public String getKindName() {
	 	return kindName;
	}
	/**
	 * @设置 产品种类名称
	 * @param kindName
	 */
	public void setKindName(String kindName) {
	 	this.kindName = kindName;
	}
	/**
	 * @return 担保方式
	 */
	public String getVouType() {
	 	return vouType;
	}
	/**
	 * @设置 担保方式
	 * @param vouType
	 */
	public void setVouType(String vouType) {
	 	this.vouType = vouType;
	}
	/**
	 * @return 金额下限
	 */
	public Double getAmtMin() {
	 	return amtMin;
	}
	/**
	 * @设置 金额下限
	 * @param amtMin
	 */
	public void setAmtMin(Double amtMin) {
	 	this.amtMin = amtMin;
	}
	/**
	 * @return 金额上限
	 */
	public Double getAmtMax() {
	 	return amtMax;
	}
	/**
	 * @设置 金额上限
	 * @param amtMax
	 */
	public void setAmtMax(Double amtMax) {
	 	this.amtMax = amtMax;
	}
	/**
	 * @return 期限下限
	 */
	public Integer getTermMin() {
	 	return termMin;
	}
	/**
	 * @设置 期限下限
	 * @param termMin
	 */
	public void setTermMin(Integer termMin) {
	 	this.termMin = termMin;
	}
	/**
	 * @return 期限上限
	 */
	public Integer getTermMax() {
	 	return termMax;
	}
	/**
	 * @设置 期限上限
	 * @param termMax
	 */
	public void setTermMax(Integer termMax) {
	 	this.termMax = termMax;
	}
	/**
	 * @return 利率下限
	 */
	public Double getRateMin() {
	 	return rateMin;
	}
	/**
	 * @设置 利率下限
	 * @param rateMin
	 */
	public void setRateMin(Double rateMin) {
	 	this.rateMin = rateMin;
	}
	/**
	 * @return 利率上限
	 */
	public Double getRateMax() {
	 	return rateMax;
	}
	/**
	 * @设置 利率上限
	 * @param rateMax
	 */
	public void setRateMax(Double rateMax) {
	 	this.rateMax = rateMax;
	}
	/**
	 * @return 模板类型
	 */
	public String getTemplateType() {
	 	return templateType;
	}
	/**
	 * @设置 模板类型
	 * @param templateType
	 */
	public void setTemplateType(String templateType) {
	 	this.templateType = templateType;
	}
	/**
	 * @return 文档模板名称
	 */
	public String getDocuTemplate() {
	 	return docuTemplate;
	}
	/**
	 * @设置 文档模板名称
	 * @param docuTemplate
	 */
	public void setDocuTemplate(String docuTemplate) {
	 	this.docuTemplate = docuTemplate;
	}
	/**
	 * @return 表单模板编号
	 */
	public String getFormTemplate() {
	 	return formTemplate;
	}
	/**
	 * @设置 表单模板编号
	 * @param formTemplate
	 */
	public void setFormTemplate(String formTemplate) {
	 	this.formTemplate = formTemplate;
	}
	/**
	 * @return 风险预警模板
	 */
	public String getRiskWarmModelNo() {
	 	return riskWarmModelNo;
	}
	/**
	 * @设置 风险预警模板
	 * @param riskWarmModelNo
	 */
	public void setRiskWarmModelNo(String riskWarmModelNo) {
	 	this.riskWarmModelNo = riskWarmModelNo;
	}
	/**
	 * @return 启用标识
	 */
	public String getRiskWarnModel() {
	 	return riskWarnModel;
	}
	/**
	 * @设置 启用标识
	 * @param riskWarnModel
	 */
	public void setRiskWarnModel(String riskWarnModel) {
	 	this.riskWarnModel = riskWarnModel;
	}
	/**
	 * @return 检查模板说明
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 检查模板说明
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
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
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public Double getAmt() {
		return amt;
	}
	public void setAmt(Double amt) {
		this.amt = amt;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public String getCusType() {
		return cusType;
	}
	public void setCusType(String cusType) {
		this.cusType = cusType;
	}
	public String getRepayType() {
		return repayType;
	}
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	public String getBaseFormTemplate() {
		return baseFormTemplate;
	}
	public void setBaseFormTemplate(String baseFormTemplate) {
		this.baseFormTemplate = baseFormTemplate;
	}
	public String getTempSerialId() {
		return tempSerialId;
	}
	public void setTempSerialId(String tempSerialId) {
		this.tempSerialId = tempSerialId;
	}
	public String getRulesTemplateId() {
		return rulesTemplateId;
	}
	public void setRulesTemplateId(String rulesTemplateId) {
		this.rulesTemplateId = rulesTemplateId;
	}
	
	
}