package app.component.examine.entity;
import app.base.BaseDomain;
/**
* Title: MfExamRiskConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Feb 16 15:01:00 CST 2017
* @version：1.0
**/
public class MfExamRiskConfig extends BaseDomain {
	private String modelId;//模型编号
	private String modelName;//模型名称
	private String indexNo;//检查指标
	private String indexName;//检查指标名称
	private String useFlag;//启用标识
	private String rulesNo;//风险规则
	private String remark;//模型说明
	private String regTime;//登记时间

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
	 * @return 检查指标
	 */
	public String getIndexNo() {
	 	return indexNo;
	}
	/**
	 * @设置 检查指标
	 * @param indexNo
	 */
	public void setIndexNo(String indexNo) {
	 	this.indexNo = indexNo;
	}
	/**
	 * @return 检查指标名称
	 */
	public String getIndexName() {
	 	return indexName;
	}
	/**
	 * @设置 检查指标名称
	 * @param indexName
	 */
	public void setIndexName(String indexName) {
	 	this.indexName = indexName;
	}
	/**
	 * @return 启用标识
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 启用标识
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 风险规则
	 */
	public String getRulesNo() {
	 	return rulesNo;
	}
	/**
	 * @设置 风险规则
	 * @param rulesNo
	 */
	public void setRulesNo(String rulesNo) {
	 	this.rulesNo = rulesNo;
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
}