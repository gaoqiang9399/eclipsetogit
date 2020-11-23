package app.component.model.entity;
import app.base.BaseDomain;
/**
* Title: MfTemplateModel.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Nov 19 11:18:13 CST 2016
* @version：1.0
**/
public class MfTemplateModel extends BaseDomain {
	private String modelNo;//模版模型id
	private String modelName;//模型名称
	private String modelRemark;//模型描述
	private String useFlag;//启用/禁用
	private String createTime;//创建时间
	private String lstModTime;//最后一次修改时间
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String brNo;//部门编号
	private String brName;//部门名称

	/**
	 * @return 模版模型id
	 */
	public String getModelNo() {
	 	return modelNo;
	}
	/**
	 * @设置 模版模型id
	 * @param modelNo
	 */
	public void setModelNo(String modelNo) {
	 	this.modelNo = modelNo;
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
	 * @return 模型描述
	 */
	public String getModelRemark() {
	 	return modelRemark;
	}
	/**
	 * @设置 模型描述
	 * @param modelRemark
	 */
	public void setModelRemark(String modelRemark) {
	 	this.modelRemark = modelRemark;
	}
	/**
	 * @return 启用/禁用
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 启用/禁用
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 创建时间
	 */
	public String getCreateTime() {
	 	return createTime;
	}
	/**
	 * @设置 创建时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
	 	this.createTime = createTime;
	}
	/**
	 * @return 最后一次修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后一次修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 操作员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作员名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 操作员名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
}