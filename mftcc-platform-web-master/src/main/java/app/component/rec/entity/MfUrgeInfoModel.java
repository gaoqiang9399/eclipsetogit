package app.component.rec.entity;
import app.base.BaseDomain;
/**
* Title: MfUrgeInfoModel.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue May 31 10:44:59 CST 2016
* @version：1.0
**/
public class MfUrgeInfoModel extends BaseDomain {
	private String urgeModelId;//催收模板ID
	private String modelType;//应收账款/融资款
	private String urgeType;//短信/电话/催收函（email）
	private String modelContent;//模板内容（编辑器）
	private String lstModTime;//最后修改时间
	private String useFlag;//启用标志
	private String modelName;
	
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	/**
	 * @return 催收模板ID
	 */
	public String getUrgeModelId() {
	 	return urgeModelId;
	}
	/**
	 * @设置 催收模板ID
	 * @param urgeModelId
	 */
	public void setUrgeModelId(String urgeModelId) {
	 	this.urgeModelId = urgeModelId;
	}
	/**
	 * @return 应收账款/融资款
	 */
	public String getModelType() {
	 	return modelType;
	}
	/**
	 * @设置 应收账款/融资款
	 * @param modelType
	 */
	public void setModelType(String modelType) {
	 	this.modelType = modelType;
	}
	/**
	 * @return 短信/电话/催收函（email）
	 */
	public String getUrgeType() {
	 	return urgeType;
	}
	/**
	 * @设置 短信/电话/催收函（email）
	 * @param urgeType
	 */
	public void setUrgeType(String urgeType) {
	 	this.urgeType = urgeType;
	}
	/**
	 * @return 模板内容（编辑器）
	 */
	public String getModelContent() {
	 	return modelContent;
	}
	/**
	 * @设置 模板内容（编辑器）
	 * @param modelContent
	 */
	public void setModelContent(String modelContent) {
	 	this.modelContent = modelContent;
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
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
}