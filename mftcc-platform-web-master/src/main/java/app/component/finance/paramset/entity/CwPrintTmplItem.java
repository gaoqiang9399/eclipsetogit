package app.component.finance.paramset.entity;
import app.base.BaseDomain;
/**
* Title: CwPrintTmplItem.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Sep 15 14:30:33 CST 2017
* @version：1.0
**/
public class CwPrintTmplItem extends BaseDomain {
	private String finBooks;//账套标识
	private String id;//唯一标识
	private String name;//名称
	private String tmplValue;//预览值
	private String isMoneyStyle;//是否金额类型
	private String busType;//隶属业务类型
	private String orderSeq;//序列
	private String remark;//备注
	private String showStyle;//显示类型
	private String ext1;//ext1扩展字段
	private String ext2;//ext2扩展字段
	private String ext3;//ext3扩展字段
	private String ext4;//ext4扩展字段
	private String ext5;//ext5扩展字段

	/**
	 * @return 账套标识
	 */
	public String getFinBooks() {
	 	return finBooks;
	}
	/**
	 * @设置 账套标识
	 * @param finBooks
	 */
	public void setFinBooks(String finBooks) {
	 	this.finBooks = finBooks;
	}
	/**
	 * @return 唯一标识
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 唯一标识
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 名称
	 */
	public String getName() {
	 	return name;
	}
	/**
	 * @设置 名称
	 * @param name
	 */
	public void setName(String name) {
	 	this.name = name;
	}
	/**
	 * @return 预览值
	 */
	public String getTmplValue() {
	 	return tmplValue;
	}
	/**
	 * @设置 预览值
	 * @param tmplValue
	 */
	public void setTmplValue(String tmplValue) {
	 	this.tmplValue = tmplValue;
	}
	/**
	 * @return 是否金额类型
	 */
	public String getIsMoneyStyle() {
	 	return isMoneyStyle;
	}
	/**
	 * @设置 是否金额类型
	 * @param isMoneyStyle
	 */
	public void setIsMoneyStyle(String isMoneyStyle) {
	 	this.isMoneyStyle = isMoneyStyle;
	}
	/**
	 * @return 隶属业务类型
	 */
	public String getBusType() {
	 	return busType;
	}
	/**
	 * @设置 隶属业务类型
	 * @param busType
	 */
	public void setBusType(String busType) {
	 	this.busType = busType;
	}
	/**
	 * @return 序列
	 */
	public String getOrderSeq() {
	 	return orderSeq;
	}
	/**
	 * @设置 序列
	 * @param orderSeq
	 */
	public void setOrderSeq(String orderSeq) {
	 	this.orderSeq = orderSeq;
	}
	/**
	 * @return 备注
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 显示类型
	 */
	public String getShowStyle() {
	 	return showStyle;
	}
	/**
	 * @设置 显示类型
	 * @param showStyle
	 */
	public void setShowStyle(String showStyle) {
	 	this.showStyle = showStyle;
	}
	/**
	 * @return ext1扩展字段
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 ext1扩展字段
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return ext2扩展字段
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 ext2扩展字段
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return ext3扩展字段
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 ext3扩展字段
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return ext4扩展字段
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 ext4扩展字段
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return ext5扩展字段
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 ext5扩展字段
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
}