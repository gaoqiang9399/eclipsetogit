package app.component.examine.entity;
import app.base.BaseDomain;
/**
* Title: MfExamineCard.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jul 24 14:26:01 CST 2017
* @version：1.0
**/
public class MfExamineCard extends BaseDomain {
	private String examineCardId;//检查卡编号
	private String examineCardName;//检查卡名称
	private String examineCardType;//检查卡类型
	private String examineCardTypeName;//检查卡类型名称
	private String examTemplateId;//贷后检查模型编号
	private String examTemplateName;//贷后检查模型名称
	private String remark;//检查卡说明

	/**
	 * @return 检查卡编号
	 */
	public String getExamineCardId() {
	 	return examineCardId;
	}
	/**
	 * @设置 检查卡编号
	 * @param examineCardId
	 */
	public void setExamineCardId(String examineCardId) {
	 	this.examineCardId = examineCardId;
	}
	/**
	 * @return 检查卡名称
	 */
	public String getExamineCardName() {
	 	return examineCardName;
	}
	/**
	 * @设置 检查卡名称
	 * @param examineCardName
	 */
	public void setExamineCardName(String examineCardName) {
	 	this.examineCardName = examineCardName;
	}
	/**
	 * @return 检查卡类型
	 */
	public String getExamineCardType() {
	 	return examineCardType;
	}
	/**
	 * @设置 检查卡类型
	 * @param examineCardType
	 */
	public void setExamineCardType(String examineCardType) {
	 	this.examineCardType = examineCardType;
	}
	/**
	 * @return 检查卡类型名称
	 */
	public String getExamineCardTypeName() {
	 	return examineCardTypeName;
	}
	/**
	 * @设置 检查卡类型名称
	 * @param examineCardTypeName
	 */
	public void setExamineCardTypeName(String examineCardTypeName) {
	 	this.examineCardTypeName = examineCardTypeName;
	}
	/**
	 * @return 贷后检查模型编号
	 */
	public String getExamTemplateId() {
	 	return examTemplateId;
	}
	/**
	 * @设置 贷后检查模型编号
	 * @param examTemplateId
	 */
	public void setExamTemplateId(String examTemplateId) {
	 	this.examTemplateId = examTemplateId;
	}
	/**
	 * @return 贷后检查模型名称
	 */
	public String getExamTemplateName() {
	 	return examTemplateName;
	}
	/**
	 * @设置 贷后检查模型名称
	 * @param examTemplateName
	 */
	public void setExamTemplateName(String examTemplateName) {
	 	this.examTemplateName = examTemplateName;
	}
	/**
	 * @return 检查卡说明
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 检查卡说明
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
}