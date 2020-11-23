package app.component.examine.entity;
import app.base.BaseDomain;
/**
* Title: MfExamineDocTemplate.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Aug 03 10:56:54 CST 2017
* @version：1.0
**/
public class MfExamineDocTemplate extends BaseDomain {
	private String id;//编号
	private String examineModelId;//贷后检查模型编号
	private String templateNo;//模板编号
	private String templateNameEn;//模板文件名
	private String templateNameZh;//模板文件名称
	private String saveFlag;//保存标识

	/**
	 * @return 编号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 编号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 贷后检查模型编号
	 */
	public String getExamineModelId() {
	 	return examineModelId;
	}
	/**
	 * @设置 贷后检查模型编号
	 * @param examineModelId
	 */
	public void setExamineModelId(String examineModelId) {
	 	this.examineModelId = examineModelId;
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
	public String getTemplateNameEn() {
		return templateNameEn;
	}
	public void setTemplateNameEn(String templateNameEn) {
		this.templateNameEn = templateNameEn;
	}
	public String getTemplateNameZh() {
		return templateNameZh;
	}
	public void setTemplateNameZh(String templateNameZh) {
		this.templateNameZh = templateNameZh;
	}
	public String getSaveFlag() {
		return saveFlag;
	}
	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}
}