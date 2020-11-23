package app.component.doc.entity;
import app.base.BaseDomain;
/**
* Title: DocFormConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon May 15 15:37:13 CST 2017
* @version：1.0
**/
public class DocFormConfig extends BaseDomain {
	private String id;//唯一id
	private String docType;//要件类型（文档类型）
	private String docTypeName;//类型名称
	private String baseFormNo;//母版编号
	private String itemNo;//关联编号--与基础元素表关联的id
	private String itemName;//关联编号名称

	/**
	 * @return 唯一id
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 唯一id
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 要件类型（文档类型）
	 */
	public String getDocType() {
	 	return docType;
	}
	/**
	 * @设置 要件类型（文档类型）
	 * @param docType
	 */
	public void setDocType(String docType) {
	 	this.docType = docType;
	}
	/**
	 * @return 类型名称
	 */
	public String getDocTypeName() {
	 	return docTypeName;
	}
	/**
	 * @设置 类型名称
	 * @param docTypeName
	 */
	public void setDocTypeName(String docTypeName) {
	 	this.docTypeName = docTypeName;
	}
	/**
	 * @return 母版编号
	 */
	public String getBaseFormNo() {
	 	return baseFormNo;
	}
	/**
	 * @设置 母版编号
	 * @param baseFormNo
	 */
	public void setBaseFormNo(String baseFormNo) {
	 	this.baseFormNo = baseFormNo;
	}
	/**
	 * @return 关联编号--与基础元素表关联的id
	 */
	public String getItemNo() {
	 	return itemNo;
	}
	/**
	 * @设置 关联编号--与基础元素表关联的id
	 * @param itemNo
	 */
	public void setItemNo(String itemNo) {
	 	this.itemNo = itemNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}