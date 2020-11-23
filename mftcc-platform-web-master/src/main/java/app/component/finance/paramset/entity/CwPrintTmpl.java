package app.component.finance.paramset.entity;
import app.base.BaseDomain;
/**
* Title: CwPrintTmpl.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Sep 15 14:34:46 CST 2017
* @version：1.0
**/
public class CwPrintTmpl extends BaseDomain {
	private String finBooks;//账套标识
	private String id;//模板编号
	private String code;//模板代码
	private String busType;//业务类型
	private String isDefault;//默认标志
	private String height;//长度
	private String width;//宽度
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
	 * @return 模板编号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 模板编号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 模板代码
	 */
	public String getCode() {
	 	return code;
	}
	/**
	 * @设置 模板代码
	 * @param code
	 */
	public void setCode(String code) {
	 	this.code = code;
	}
	/**
	 * @return 业务类型
	 */
	public String getBusType() {
	 	return busType;
	}
	/**
	 * @设置 业务类型
	 * @param busType
	 */
	public void setBusType(String busType) {
	 	this.busType = busType;
	}
	/**
	 * @return 默认标志
	 */
	public String getIsDefault() {
	 	return isDefault;
	}
	/**
	 * @设置 默认标志
	 * @param isDefault
	 */
	public void setIsDefault(String isDefault) {
	 	this.isDefault = isDefault;
	}
	/**
	 * @return 长度
	 */
	public String getHeight() {
	 	return height;
	}
	/**
	 * @设置 长度
	 * @param height
	 */
	public void setHeight(String height) {
	 	this.height = height;
	}
	/**
	 * @return 宽度
	 */
	public String getWidth() {
	 	return width;
	}
	/**
	 * @设置 宽度
	 * @param width
	 */
	public void setWidth(String width) {
	 	this.width = width;
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