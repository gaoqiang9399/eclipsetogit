package app.component.pss.information.entity;
import app.base.BaseDomain;
/**
* Title: MfQueryItem.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Jun 10 18:03:08 CST 2017
* @version：1.0
**/
public class PssQueryItem extends BaseDomain {
	private static final long serialVersionUID = -8757197605782666460L;
	private String id;//唯一主键
	private String itemId;//查询项id
	private String funcType;//功能类型
	private String isBase;//是否为基础数据0-否1-是
	private String itemType;//查询项类别
	private String itemName;//查询项名称
	private String itemIcon;//查询项图标
	private String opNo;//操作员编号
	private String attentionFlag;//专注状态
	private Integer sort;//排序顺序
	private String lstModTime;//最后一次更新时间
	private String pmsBizNo;//功能权限编号

	/**
	 * @return 唯一主键
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 唯一主键
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 查询项id
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * @设置 查询项id
	 * @param itemId
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * @return 是否为基础数据0-否1-是
	 */
	public String getIsBase() {
	 	return isBase;
	}
	/**
	 * @设置 是否为基础数据0-否1-是
	 * @param isBase
	 */
	public void setIsBase(String isBase) {
	 	this.isBase = isBase;
	}
	/**
	 * @return 查询项类别cus-客户 bus-业务  ple-押品 oa-办公 报表该字段代表未关注的样式
	 */
	public String getItemType() {
	 	return itemType;
	}
	/**
	 * @设置 查询项类别cus-客户 bus-业务  ple-押品 oa-办公 报表该字段代表未关注的样式
	 * @param itemType
	 */
	public void setItemType(String itemType) {
	 	this.itemType = itemType;
	}
	/**
	 * @return 查询项名称
	 */
	public String getItemName() {
	 	return itemName;
	}
	/**
	 * @设置 查询项名称
	 * @param itemName
	 */
	public void setItemName(String itemName) {
	 	this.itemName = itemName;
	}
	/**
	 * @return 查询项图标
	 */
	public String getItemIcon() {
	 	return itemIcon;
	}
	/**
	 * @设置 查询项图标
	 * @param itemIcon
	 */
	public void setItemIcon(String itemIcon) {
	 	this.itemIcon = itemIcon;
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
	 * @return 专注状态0-未关注 1-已关注
	 */
	public String getAttentionFlag() {
	 	return attentionFlag;
	}
	/**
	 * @设置 专注状态0-未关注 1-已关注
	 * @param attentionFlag
	 */
	public void setAttentionFlag(String attentionFlag) {
	 	this.attentionFlag = attentionFlag;
	}
	/**
	 * @return 排序顺序
	 */
	public Integer getSort() {
	 	return sort;
	}
	/**
	 * @设置 排序顺序
	 * @param sort
	 */
	public void setSort(Integer sort) {
	 	this.sort = sort;
	}
	/**
	 * @return 最后一次更新时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后一次更新时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	public String getFuncType() {
		return funcType;
	}
	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}
	public String getPmsBizNo() {
		return pmsBizNo;
	}
	public void setPmsBizNo(String pmsBizNo) {
		this.pmsBizNo = pmsBizNo;
	}
}