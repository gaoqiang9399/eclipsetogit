package app.component.finance.vchout.entity;
import app.base.BaseDomain;
/**
* Title: CwVchOutCorrbind.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Dec 18 17:40:34 CST 2017
* @version：1.0
**/
public class CwVchOutCorrbind extends BaseDomain {
	private String corrId;//唯一编号
	private String corrType;//关系类型（0 辅助核算；1：凭证字；2 现金流量分析；9：临时科目）
	private String typeNo;//类别编号
	private String typeName;//类别名称
	private String itemNo;//辅助项编号
	private String itemName;//辅助项名称
	private String corrNo;//关系编号
	private String corrName;//关系名称
	private String ext1;//备用1
	private String ext2;//备用2
	private String ext3;//备用3
	private String ext4;//备用4
	private String ext5;//备用5
	private String ext6;//备用6
	private String ext7;//备用7
	private String ext8;//备用8
	private String ext9;//备用9
	private String ext10;//备用10

	/**
	 * @return 唯一编号
	 */
	public String getCorrId() {
	 	return corrId;
	}
	/**
	 * @设置 唯一编号
	 * @param corrId
	 */
	public void setCorrId(String corrId) {
	 	this.corrId = corrId;
	}
	/**
	 * @return 关系类型（0 辅助核算；1：凭证字；2 现金流量分析；9：临时科目）
	 */
	public String getCorrType() {
	 	return corrType;
	}
	/**
	 * @设置 关系类型（0 辅助核算；1：凭证字；2 现金流量分析；9：临时科目）
	 * @param corrType
	 */
	public void setCorrType(String corrType) {
	 	this.corrType = corrType;
	}
	/**
	 * @return 类别编号
	 */
	public String getTypeNo() {
	 	return typeNo;
	}
	/**
	 * @设置 类别编号
	 * @param typeNo
	 */
	public void setTypeNo(String typeNo) {
	 	this.typeNo = typeNo;
	}
	/**
	 * @return 类别名称
	 */
	public String getTypeName() {
	 	return typeName;
	}
	/**
	 * @设置 类别名称
	 * @param typeName
	 */
	public void setTypeName(String typeName) {
	 	this.typeName = typeName;
	}
	/**
	 * @return 辅助项编号
	 */
	public String getItemNo() {
	 	return itemNo;
	}
	/**
	 * @设置 辅助项编号
	 * @param itemNo
	 */
	public void setItemNo(String itemNo) {
	 	this.itemNo = itemNo;
	}
	/**
	 * @return 辅助项名称
	 */
	public String getItemName() {
	 	return itemName;
	}
	/**
	 * @设置 辅助项名称
	 * @param itemName
	 */
	public void setItemName(String itemName) {
	 	this.itemName = itemName;
	}
	/**
	 * @return 关系编号
	 */
	public String getCorrNo() {
	 	return corrNo;
	}
	/**
	 * @设置 关系编号
	 * @param corrNo
	 */
	public void setCorrNo(String corrNo) {
	 	this.corrNo = corrNo;
	}
	/**
	 * @return 关系名称
	 */
	public String getCorrName() {
	 	return corrName;
	}
	/**
	 * @设置 关系名称
	 * @param corrName
	 */
	public void setCorrName(String corrName) {
	 	this.corrName = corrName;
	}
	/**
	 * @return 备用1
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 备用1
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 备用2
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 备用2
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 备用3
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 备用3
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 备用4
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 备用4
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return 备用5
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 备用5
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	/**
	 * @return 备用6
	 */
	public String getExt6() {
	 	return ext6;
	}
	/**
	 * @设置 备用6
	 * @param ext6
	 */
	public void setExt6(String ext6) {
	 	this.ext6 = ext6;
	}
	/**
	 * @return 备用7
	 */
	public String getExt7() {
	 	return ext7;
	}
	/**
	 * @设置 备用7
	 * @param ext7
	 */
	public void setExt7(String ext7) {
	 	this.ext7 = ext7;
	}
	/**
	 * @return 备用8
	 */
	public String getExt8() {
	 	return ext8;
	}
	/**
	 * @设置 备用8
	 * @param ext8
	 */
	public void setExt8(String ext8) {
	 	this.ext8 = ext8;
	}
	/**
	 * @return 备用9
	 */
	public String getExt9() {
	 	return ext9;
	}
	/**
	 * @设置 备用9
	 * @param ext9
	 */
	public void setExt9(String ext9) {
	 	this.ext9 = ext9;
	}
	/**
	 * @return 备用10
	 */
	public String getExt10() {
	 	return ext10;
	}
	/**
	 * @设置 备用10
	 * @param ext10
	 */
	public void setExt10(String ext10) {
	 	this.ext10 = ext10;
	}
}