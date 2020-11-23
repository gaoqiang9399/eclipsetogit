package app.component.pss.stock.entity;
import app.base.BaseDomain;
/**
* Title: PssAlloTransDetailBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Aug 11 13:20:07 CST 2017
* @version：1.0
**/
public class PssAlloTransDetailBill extends BaseDomain {
	private String alloTransDetailId;//单据明细编号
	private String alloTransNo;//调拨单据编号
	private Integer sequence;//序号
	private String goodsId;//商品编号(主键)
	private String goodsName;//商品名称
	private String unitId;//单位id
	private Double goodsQty;//商品数量
	private String baseUnitId;//基本单位id
	private Double baseQuantity;//基本单位数量
	private String inStorehouseId;//调入仓库编号
	private String inStorehouseName;//调入仓库名称
	private String outStorehouseId;//调出仓库编号
	private String outStorehouseName;//调出仓库名称
	private String memo;//分录备注

	/**
	 * @return 单据明细编号
	 */
	public String getAlloTransDetailId() {
	 	return alloTransDetailId;
	}
	/**
	 * @设置 单据明细编号
	 * @param alloTransDetailId
	 */
	public void setAlloTransDetailId(String alloTransDetailId) {
	 	this.alloTransDetailId = alloTransDetailId;
	}
	/**
	 * @return 调拨单据编号
	 */
	public String getAlloTransNo() {
	 	return alloTransNo;
	}
	/**
	 * @设置 调拨单据编号
	 * @param alloTransNo
	 */
	public void setAlloTransNo(String alloTransNo) {
	 	this.alloTransNo = alloTransNo;
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
	/**
	 * @return 商品编号(主键)
	 */
	public String getGoodsId() {
	 	return goodsId;
	}
	/**
	 * @设置 商品编号(主键)
	 * @param goodsId
	 */
	public void setGoodsId(String goodsId) {
	 	this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	/**
	 * @return 商品数量
	 */
	public Double getGoodsQty() {
	 	return goodsQty;
	}
	/**
	 * @设置 商品数量
	 * @param goodsQty
	 */
	public void setGoodsQty(Double goodsQty) {
	 	this.goodsQty = goodsQty;
	}
	public String getBaseUnitId() {
		return baseUnitId;
	}
	public void setBaseUnitId(String baseUnitId) {
		this.baseUnitId = baseUnitId;
	}
	public Double getBaseQuantity() {
		return baseQuantity;
	}
	public void setBaseQuantity(Double baseQuantity) {
		this.baseQuantity = baseQuantity;
	}
	/**
	 * @return 调入仓库编号
	 */
	public String getInStorehouseId() {
	 	return inStorehouseId;
	}
	/**
	 * @设置 调入仓库编号
	 * @param inStorehouseId
	 */
	public void setInStorehouseId(String inStorehouseId) {
	 	this.inStorehouseId = inStorehouseId;
	}
	public String getInStorehouseName() {
		return inStorehouseName;
	}
	public void setInStorehouseName(String inStorehouseName) {
		this.inStorehouseName = inStorehouseName;
	}
	/**
	 * @return 调出仓库编号
	 */
	public String getOutStorehouseId() {
	 	return outStorehouseId;
	}
	/**
	 * @设置 调出仓库编号
	 * @param outStorehouseId
	 */
	public void setOutStorehouseId(String outStorehouseId) {
	 	this.outStorehouseId = outStorehouseId;
	}
	public String getOutStorehouseName() {
		return outStorehouseName;
	}
	public void setOutStorehouseName(String outStorehouseName) {
		this.outStorehouseName = outStorehouseName;
	}
	/**
	 * @return 分录备注
	 */
	public String getMemo() {
	 	return memo;
	}
	/**
	 * @设置 分录备注
	 * @param memo
	 */
	public void setMemo(String memo) {
	 	this.memo = memo;
	}
}