package app.component.msgconf.entity;
import app.base.BaseDomain;
/**
* Title: MfExamineTemplateConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Feb 09 15:35:54 CST 2017
* @version：1.0
**/
public class MfExamineTemplateConfig extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
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
	private String riskWarmModelNo;//风险预警模板
	private String riskWarnModel;//风险预警模板名称
	private String useFlag;//启用标识
	private String remark;//检查模板说明
	private String regTime;//登记时间
	
	private String cusType;
	private String repayType;
	
	private String pliWarnNo;//预警模型编号
	private String pliWarnName;//预警模型名称
	private String pliWarnContent; //预警信息
	private String pliContentArgs; //预警信息参数
	private String pliRelModels; //关联贷后检查模型
	private String pliWarnType;//预警类型
	private Integer pliDays ;//指定天数
	private String sendType; //发送类型
	private Integer pliFreq ;//频率
	private String pliFreqUnit;//频率单位
	private Integer pliWarnLimit; //预警最大次数
	private String recCusType ; //接收人
	
	public String getPliWarnNo() {
		return pliWarnNo;
	}
	public void setPliWarnNo(String pliWarnNo) {
		this.pliWarnNo = pliWarnNo;
	}
	public String getPliWarnName() {
		return pliWarnName;
	}
	public void setPliWarnName(String pliWarnName) {
		this.pliWarnName = pliWarnName;
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
	public String getRecCusType() {
		return recCusType;
	}
	public void setRecCusType(String recCusType) {
		this.recCusType = recCusType;
	}
	public String getPliWarnType() {
		return pliWarnType;
	}
	public void setPliWarnType(String pliWarnType) {
		this.pliWarnType = pliWarnType;
	}
	public Integer getPliDays() {
		return pliDays;
	}
	public void setPliDays(Integer pliDays) {
		this.pliDays = pliDays;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public Integer getPliFreq() {
		return pliFreq;
	}
	public void setPliFreq(Integer pliFreq) {
		this.pliFreq = pliFreq;
	}
	public String getPliFreqUnit() {
		return pliFreqUnit;
	}
	public void setPliFreqUnit(String pliFreqUnit) {
		this.pliFreqUnit = pliFreqUnit;
	}
	public Integer getPliWarnLimit() {
		return pliWarnLimit;
	}
	public void setPliWarnLimit(Integer pliWarnLimit) {
		this.pliWarnLimit = pliWarnLimit;
	}
	public String getPliWarnContent() {
		return pliWarnContent;
	}
	public void setPliWarnContent(String pliWarnContent) {
		this.pliWarnContent = pliWarnContent;
	}
	public String getPliContentArgs() {
		return pliContentArgs;
	}
	public void setPliContentArgs(String pliContentArgs) {
		this.pliContentArgs = pliContentArgs;
	}
	public String getPliRelModels() {
		return pliRelModels;
	}
	public void setPliRelModels(String pliRelModels) {
		this.pliRelModels = pliRelModels;
	}
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
	
}