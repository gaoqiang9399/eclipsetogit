package app.component.pss.information.entity;
import app.base.BaseDomain;
/**
* Title: PssUnitGoodsRel.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Dec 12 17:08:28 CST 2017
* @version：1.0
**/
public class PssUnitGoodsRel extends BaseDomain {
	private String ugrId;//数据ID
	private String unitId;//单位ID
	private String unitName;//单位名称
	private String goodsId;//商品ID
	private Double retailPrice;//零售价
	private Double wholesalePrice;//批发价
	private Double vipPrice;//VIP会员价
	private Double discountRate1;//折扣率一(%)
	private Double discountRate2;//折扣率二(%)
	private Double estimatedPurchasePrice;//预计采购价
	private String invalidFlag;//预计采购价
	private String relNum;//预计采购价
	private Integer sequence;//序号
	private String isBase;//是否为基本单位

	/**
	 * @return 数据ID
	 */
	public String getUgrId() {
	 	return ugrId;
	}
	/**
	 * @设置 数据ID
	 * @param ugrId
	 */
	public void setUgrId(String ugrId) {
	 	this.ugrId = ugrId;
	}
	/**
	 * @return 单位ID
	 */
	public String getUnitId() {
	 	return unitId;
	}
	/**
	 * @设置 单位ID
	 * @param unitId
	 */
	public void setUnitId(String unitId) {
	 	this.unitId = unitId;
	}
	/**
	 * @return 单位名称
	 */
	public String getUnitName() {
	 	return unitName;
	}
	/**
	 * @设置 单位名称
	 * @param unitName
	 */
	public void setUnitName(String unitName) {
	 	this.unitName = unitName;
	}
	/**
	 * @return 商品ID
	 */
	public String getGoodsId() {
	 	return goodsId;
	}
	/**
	 * @设置 商品ID
	 * @param goodsId
	 */
	public void setGoodsId(String goodsId) {
	 	this.goodsId = goodsId;
	}
	/**
	 * @return 零售价
	 */
	public Double getRetailPrice() {
	 	return retailPrice;
	}
	/**
	 * @设置 零售价
	 * @param retailPrice
	 */
	public void setRetailPrice(Double retailPrice) {
	 	this.retailPrice = retailPrice;
	}
	/**
	 * @return 批发价
	 */
	public Double getWholesalePrice() {
	 	return wholesalePrice;
	}
	/**
	 * @设置 批发价
	 * @param wholesalePrice
	 */
	public void setWholesalePrice(Double wholesalePrice) {
	 	this.wholesalePrice = wholesalePrice;
	}
	/**
	 * @return VIP会员价
	 */
	public Double getVipPrice() {
	 	return vipPrice;
	}
	/**
	 * @设置 VIP会员价
	 * @param vipPrice
	 */
	public void setVipPrice(Double vipPrice) {
	 	this.vipPrice = vipPrice;
	}
	/**
	 * @return 折扣率一(%)
	 */
	public Double getDiscountRate1() {
	 	return discountRate1;
	}
	/**
	 * @设置 折扣率一(%)
	 * @param discountRate1
	 */
	public void setDiscountRate1(Double discountRate1) {
	 	this.discountRate1 = discountRate1;
	}
	/**
	 * @return 折扣率二(%)
	 */
	public Double getDiscountRate2() {
	 	return discountRate2;
	}
	/**
	 * @设置 折扣率二(%)
	 * @param discountRate2
	 */
	public void setDiscountRate2(Double discountRate2) {
	 	this.discountRate2 = discountRate2;
	}
	/**
	 * @return 预计采购价
	 */
	public Double getEstimatedPurchasePrice() {
	 	return estimatedPurchasePrice;
	}
	/**
	 * @设置 预计采购价
	 * @param estimatedPurchasePrice
	 */
	public void setEstimatedPurchasePrice(Double estimatedPurchasePrice) {
	 	this.estimatedPurchasePrice = estimatedPurchasePrice;
	}
	/**
	 * @return 序号
	 */
	public Integer getSequence() {
	 	return sequence;
	}
	/**
	 * @设置 序号
	 * @param sequence
	 */
	public void setSequence(Integer sequence) {
	 	this.sequence = sequence;
	}
	public String getInvalidFlag() {
		return invalidFlag;
	}
	public void setInvalidFlag(String invalidFlag) {
		this.invalidFlag = invalidFlag;
	}
	public String getRelNum() {
		return relNum;
	}
	public void setRelNum(String relNum) {
		this.relNum = relNum;
	}
	public String getIsBase() {
		return isBase;
	}
	public void setIsBase(String isBase) {
		this.isBase = isBase;
	}

}