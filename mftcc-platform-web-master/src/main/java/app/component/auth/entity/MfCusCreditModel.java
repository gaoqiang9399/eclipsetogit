package app.component.auth.entity;
import app.base.BaseDomain;
/**
* Title: MfCusCreditModel.java
* Description:授信模型配置表实体
* @author：LJW
* @Thu Feb 23 16:13:12 CST 2017
* @version：1.0
**/
public class MfCusCreditModel extends BaseDomain {
	private String modelId;//模型编号
	private String modelName;//模型名称
	private String cusTypeNo;//客户类型编号
	private String cusType;//客户类型
	private String wkfCreditId;//授信业务流程编号
	private String creditFormId;//授信申请表单编号
	private String reportTemplateId;//尽调报告模板编号
	private String protocolTemplateId;//授信协议模板编号
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String remark;//模型说明

	private String wkfCreditName;   //授信业务流程名称
	private String creditFormName;  //授信申请表单名称
	private String reportTemplateName;  //尽调报告模板名称
	private String protocolTemplateName; //授信协议模板名称
	private String reportTemplateEn;  //尽调报告模板名称
	private String protocolTemplateEn; //授信协议模板名称
	private String creditAdjustFormId;//授信调整申请表单编号
	private String sts;//启用状态 1:启用 0:未启用
	private String applyTime;//时效时间 单位天
	private String typeCus;//授信类型:credit_1: 客户授信  credit_2: 项目授信
	private String del;//删除状态 1:未删除；2：软删除

	public String getTypeCus() {
		return typeCus;
	}

	public void setTypeCus(String typeCus) {
		this.typeCus = typeCus;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	/**
	 * @return 模型编号
	 */
	public String getModelId() {
	 	return modelId;
	}
	/**
	 * @设置 模型编号
	 * @param modelId
	 */
	public void setModelId(String modelId) {
	 	this.modelId = modelId;
	}
	/**
	 * @return 模型名称
	 */
	public String getModelName() {
	 	return modelName;
	}
	/**
	 * @设置 模型名称
	 * @param modelName
	 */
	public void setModelName(String modelName) {
	 	this.modelName = modelName;
	}
	/**
	 * @return 客户类型编号
	 */
	public String getCusTypeNo() {
	 	return cusTypeNo;
	}
	/**
	 * @设置 客户类型编号
	 * @param cusTypeNo
	 */
	public void setCusTypeNo(String cusTypeNo) {
	 	this.cusTypeNo = cusTypeNo;
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
	 * @return 授信业务流程编号
	 */
	public String getWkfCreditId() {
	 	return wkfCreditId;
	}
	/**
	 * @设置 授信业务流程编号
	 * @param wkfCreditId
	 */
	public void setWkfCreditId(String wkfCreditId) {
	 	this.wkfCreditId = wkfCreditId;
	}
	/**
	 * @return 授信申请表单编号
	 */
	public String getCreditFormId() {
	 	return creditFormId;
	}
	/**
	 * @设置 授信申请表单编号
	 * @param creditFormId
	 */
	public void setCreditFormId(String creditFormId) {
	 	this.creditFormId = creditFormId;
	}
	/**
	 * @return 尽调报告模板编号
	 */
	public String getReportTemplateId() {
	 	return reportTemplateId;
	}
	/**
	 * @设置 尽调报告模板编号
	 * @param reportTemplateId
	 */
	public void setReportTemplateId(String reportTemplateId) {
	 	this.reportTemplateId = reportTemplateId;
	}
	/**
	 * @return 授信协议模板编号
	 */
	public String getProtocolTemplateId() {
	 	return protocolTemplateId;
	}
	/**
	 * @设置 授信协议模板编号
	 * @param protocolTemplateId
	 */
	public void setProtocolTemplateId(String protocolTemplateId) {
	 	this.protocolTemplateId = protocolTemplateId;
	}
	/**
	 * @return 登记人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人编号
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
	 * @return 登记部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门编号
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
	 * @return 模型说明
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 模型说明
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	public String getWkfCreditName() {
		return wkfCreditName;
	}
	public void setWkfCreditName(String wkfCreditName) {
		this.wkfCreditName = wkfCreditName;
	}
	public String getCreditFormName() {
		return creditFormName;
	}
	public void setCreditFormName(String creditFormName) {
		this.creditFormName = creditFormName;
	}
	public String getReportTemplateName() {
		return reportTemplateName;
	}
	public void setReportTemplateName(String reportTemplateName) {
		this.reportTemplateName = reportTemplateName;
	}
	public String getProtocolTemplateName() {
		return protocolTemplateName;
	}
	public void setProtocolTemplateName(String protocolTemplateName) {
		this.protocolTemplateName = protocolTemplateName;
	}
	public String getCreditAdjustFormId() {
		return creditAdjustFormId;
	}
	public void setCreditAdjustFormId(String creditAdjustFormId) {
		this.creditAdjustFormId = creditAdjustFormId;
	}
	public String getReportTemplateEn() {
		return reportTemplateEn;
	}
	public void setReportTemplateEn(String reportTemplateEn) {
		this.reportTemplateEn = reportTemplateEn;
	}
	public String getProtocolTemplateEn() {
		return protocolTemplateEn;
	}
	public void setProtocolTemplateEn(String protocolTemplateEn) {
		this.protocolTemplateEn = protocolTemplateEn;
	}
}