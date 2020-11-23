package app.component.model.entity;
import app.base.BaseDomain;
/**
* Title: LoanTemplateTagSet.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Sep 05 18:28:09 CST 2016
* @version：1.0
**/
public class LoanTemplateTagSet extends BaseDomain {
	private String seriaNo;//唯一号
	private String templateNo;//模板编号
	private String templateCnName;//模板中文名称
	private String templateEnName;//模板英文名称
	private String templatePath;//模板存储路径
	private String tagKeyNo;//标签key编号
	private String tagKeyName;//标签key名称
	private String templateEditFlag;//模板是否允许编辑

	/**
	 * @return 唯一号
	 */
	public String getSeriaNo() {
	 	return seriaNo;
	}
	/**
	 * @设置 唯一号
	 * @param seriaNo
	 */
	public void setSeriaNo(String seriaNo) {
	 	this.seriaNo = seriaNo;
	}
	/**
	 * @return 模板编号
	 */
	public String getTemplateNo() {
	 	return templateNo;
	}
	/**
	 * @设置 模板编号
	 * @param templateNo
	 */
	public void setTemplateNo(String templateNo) {
	 	this.templateNo = templateNo;
	}
	/**
	 * @return 模板中文名称
	 */
	public String getTemplateCnName() {
	 	return templateCnName;
	}
	/**
	 * @设置 模板中文名称
	 * @param templateCnName
	 */
	public void setTemplateCnName(String templateCnName) {
	 	this.templateCnName = templateCnName;
	}
	/**
	 * @return 模板英文名称
	 */
	public String getTemplateEnName() {
	 	return templateEnName;
	}
	/**
	 * @设置 模板英文名称
	 * @param templateEnName
	 */
	public void setTemplateEnName(String templateEnName) {
	 	this.templateEnName = templateEnName;
	}
	/**
	 * @return 模板存储路径
	 */
	public String getTemplatePath() {
	 	return templatePath;
	}
	/**
	 * @设置 模板存储路径
	 * @param templatePath
	 */
	public void setTemplatePath(String templatePath) {
	 	this.templatePath = templatePath;
	}
	/**
	 * @return 标签key编号
	 */
	public String getTagKeyNo() {
	 	return tagKeyNo;
	}
	/**
	 * @设置 标签key编号
	 * @param tagKeyNo
	 */
	public void setTagKeyNo(String tagKeyNo) {
	 	this.tagKeyNo = tagKeyNo;
	}
	/**
	 * @return 标签key名称
	 */
	public String getTagKeyName() {
	 	return tagKeyName;
	}
	/**
	 * @设置 标签key名称
	 * @param tagKeyName
	 */
	public void setTagKeyName(String tagKeyName) {
	 	this.tagKeyName = tagKeyName;
	}
	/**
	 * @return 模板是否允许编辑
	 */
	public String getTemplateEditFlag() {
	 	return templateEditFlag;
	}
	/**
	 * @设置 模板是否允许编辑
	 * @param templateEditFlag
	 */
	public void setTemplateEditFlag(String templateEditFlag) {
	 	this.templateEditFlag = templateEditFlag;
	}
}