package app.component.frontview.entity;
import app.base.BaseDomain;
/**
* Title: MfAppCollectItem.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Oct 24 12:01:38 CST 2017
* @version：1.0
**/
public class MfAppCollectItem extends BaseDomain {
	private String id;//流水号
	private String modelType;//信息编号
	private String itemName;//采集信息名称
	private String itemUseFlag;//是否启用 0禁用 1启用
	private String itemUrl;//链接
	private String itemType;//1-我信息 2-基本信息 3-第三方认证信息
	private String itemModelUrl;//表单模板路径

	/**
	 * @return 流水号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 流水号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 信息编号
	 */
	public String getModelType() {
	 	return modelType;
	}
	/**
	 * @设置 信息编号
	 * @param modelType
	 */
	public void setModelType(String modelType) {
	 	this.modelType = modelType;
	}
	/**
	 * @return 采集信息名称
	 */
	public String getItemName() {
	 	return itemName;
	}
	/**
	 * @设置 采集信息名称
	 * @param itemName
	 */
	public void setItemName(String itemName) {
	 	this.itemName = itemName;
	}
	/**
	 * @return 是否启用 0禁用 1启用
	 */
	public String getItemUseFlag() {
	 	return itemUseFlag;
	}
	/**
	 * @设置 是否启用 0禁用 1启用
	 * @param itemUseFlag
	 */
	public void setItemUseFlag(String itemUseFlag) {
	 	this.itemUseFlag = itemUseFlag;
	}
	/**
	 * @return 链接
	 */
	public String getItemUrl() {
	 	return itemUrl;
	}
	/**
	 * @设置 链接
	 * @param itemUrl
	 */
	public void setItemUrl(String itemUrl) {
	 	this.itemUrl = itemUrl;
	}
	/**
	 * @return 1-我信息 2-基本信息 3-第三方认证信息
	 */
	public String getItemType() {
	 	return itemType;
	}
	/**
	 * @设置 1-我信息 2-基本信息 3-第三方认证信息
	 * @param itemType
	 */
	public void setItemType(String itemType) {
	 	this.itemType = itemType;
	}
	/**
	 * @return 表单模板路径
	 */
	public String getItemModelUrl() {
	 	return itemModelUrl;
	}
	/**
	 * @设置 表单模板路径
	 * @param itemModelUrl
	 */
	public void setItemModelUrl(String itemModelUrl) {
	 	this.itemModelUrl = itemModelUrl;
	}
}