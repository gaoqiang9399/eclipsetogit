package app.component.pss.stock.entity;
import app.base.BaseDomain;
/**
* Title: PssOtherStockOutDetailBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Aug 28 09:44:54 CST 2017
* @version：1.0
**/
public class PssOtherStockOutDetailBill extends BaseDomain {
	private String otherStockOutDetailId;//其他出库单据明细编号
	private String otherStockOutNo;//其他出库单据编号
	private Integer sequence;//序号
	private String goodsId;//商品编号
	private String goodsName;//商品名称
	private String unitId;//单位id
	private String baseUnitId;//基本单位id
	private Double baseQuantity;//基本单位数量
	private String storehouseId;//仓库编号
	private String storehouseName;//仓库名称
	private Double otherOutQty;//数量
	private Double outUnitCost;//出库单位成本
	private Double outCost;//出库成本
	private String memo;//分录备注

	/**
	 * @return 其他出库单据明细编号
	 */
	public String getOtherStockOutDetailId() {
	 	return otherStockOutDetailId;
	}
	/**
	 * @设置 其他出库单据明细编号
	 * @param otherStockOutDetailId
	 */
	public void setOtherStockOutDetailId(String otherStockOutDetailId) {
	 	this.otherStockOutDetailId = otherStockOutDetailId;
	}
	/**
	 * @return 其他出库单据编号
	 */
	public String getOtherStockOutNo() {
	 	return otherStockOutNo;
	}
	/**
	 * @设置 其他出库单据编号
	 * @param otherStockOutNo
	 */
	public void setOtherStockOutNo(String otherStockOutNo) {
	 	this.otherStockOutNo = otherStockOutNo;
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
	 * @return 商品编号
	 */
	public String getGoodsId() {
	 	return goodsId;
	}
	/**
	 * @设置 商品编号
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
	 * @return 仓库编号
	 */
	public String getStorehouseId() {
	 	return storehouseId;
	}
	/**
	 * @设置 仓库编号
	 * @param storehouseId
	 */
	public void setStorehouseId(String storehouseId) {
	 	this.storehouseId = storehouseId;
	}
	public String getStorehouseName() {
		return storehouseName;
	}
	public void setStorehouseName(String storehouseName) {
		this.storehouseName = storehouseName;
	}
	/**
	 * @return 数量
	 */
	public Double getOtherOutQty() {
	 	return otherOutQty;
	}
	/**
	 * @设置 数量
	 * @param otherOutQty
	 */
	public void setOtherOutQty(Double otherOutQty) {
	 	this.otherOutQty = otherOutQty;
	}
	/**
	 * @return 出库单位成本
	 */
	public Double getOutUnitCost() {
	 	return outUnitCost;
	}
	/**
	 * @设置 出库单位成本
	 * @param outUnitCost
	 */
	public void setOutUnitCost(Double outUnitCost) {
	 	this.outUnitCost = outUnitCost;
	}
	/**
	 * @return 出库成本
	 */
	public Double getOutCost() {
	 	return outCost;
	}
	/**
	 * @设置 出库成本
	 * @param outCost
	 */
	public void setOutCost(Double outCost) {
	 	this.outCost = outCost;
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