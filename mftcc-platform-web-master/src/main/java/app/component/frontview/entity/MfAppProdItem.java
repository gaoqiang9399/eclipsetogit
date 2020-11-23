package app.component.frontview.entity;
import app.base.BaseDomain;
/**
* Title: MfAppProdItem.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Oct 24 20:40:37 CST 2017
* @version：1.0
**/
public class MfAppProdItem extends BaseDomain {
	private String kindNo;//产品号
	private String kindName;//产品号
	private String itemId;//采集指标项
	private String itemName;//采集项名称
	private String itemType;//2-基本信息 3-第三方认证信息
	private String itemRequire;//是否必填 0非必填 1必填
	private Integer itemOrder;//显示顺序

	/**
	 * @return 产品号
	 */
	public String getKindNo() {
	 	return kindNo;
	}
	/**
	 * @设置 产品号
	 * @param kindNo
	 */
	public void setKindNo(String kindNo) {
	 	this.kindNo = kindNo;
	}
	/**
	 * @return 采集指标项
	 */
	public String getItemId() {
	 	return itemId;
	}
	/**
	 * @设置 采集指标项
	 * @param itemId
	 */
	public void setItemId(String itemId) {
	 	this.itemId = itemId;
	}
	/**
	 * @return 2-基本信息 3-第三方认证信息
	 */
	public String getItemType() {
	 	return itemType;
	}
	/**
	 * @设置 2-基本信息 3-第三方认证信息
	 * @param itemType
	 */
	public void setItemType(String itemType) {
	 	this.itemType = itemType;
	}
	/**
	 * @return 是否必填 0非必填 1必填
	 */
	public String getItemRequire() {
	 	return itemRequire;
	}
	/**
	 * @设置 是否必填 0非必填 1必填
	 * @param itemRequire
	 */
	public void setItemRequire(String itemRequire) {
	 	this.itemRequire = itemRequire;
	}
	/**
	 * @return 显示顺序
	 */
	public Integer getItemOrder() {
	 	return itemOrder;
	}
	/**
	 * @设置 显示顺序
	 * @param itemOrder
	 */
	public void setItemOrder(Integer itemOrder) {
	 	this.itemOrder = itemOrder;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	
}