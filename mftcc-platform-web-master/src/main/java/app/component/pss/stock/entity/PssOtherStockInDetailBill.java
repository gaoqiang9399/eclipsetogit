package app.component.pss.stock.entity;
import app.base.BaseDomain;
/**
* Title: PssOtherStockInDetailBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Aug 22 10:41:18 CST 2017
* @version：1.0
**/
public class PssOtherStockInDetailBill extends BaseDomain {
	private String otherStockInDetailId;//其他入库单据明细编号
	private String otherStockInNo;//其他入库单据编号
	private Integer sequence;//序号
	private String goodsId;//商品编号
	private String goodsName;//商品名称
	private String unitId;//单位id
	private String baseUnitId;//基本单位id
	private Double baseQuantity;//基本单位数量
	private String storehouseId;//仓库编号
	private String storehouseName;//仓库名称
	private Double otherInQty;//数量
	private Double inUnitCost;//入库单位成本
	private Double inCost;//入库金额
	private String memo;//分录备注

	/**
	 * @return 其他入库单据明细编号
	 */
	public String getOtherStockInDetailId() {
	 	return otherStockInDetailId;
	}
	/**
	 * @设置 其他入库单据明细编号
	 * @param otherStockInDetailId
	 */
	public void setOtherStockInDetailId(String otherStockInDetailId) {
	 	this.otherStockInDetailId = otherStockInDetailId;
	}
	/**
	 * @return 其他入库单据编号
	 */
	public String getOtherStockInNo() {
	 	return otherStockInNo;
	}
	/**
	 * @设置 其他入库单据编号
	 * @param otherStockInNo
	 */
	public void setOtherStockInNo(String otherStockInNo) {
	 	this.otherStockInNo = otherStockInNo;
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
	public Double getOtherInQty() {
	 	return otherInQty;
	}
	/**
	 * @设置 数量
	 * @param otherInQty
	 */
	public void setOtherInQty(Double otherInQty) {
	 	this.otherInQty = otherInQty;
	}
	/**
	 * @return 入库单位成本
	 */
	public Double getInUnitCost() {
	 	return inUnitCost;
	}
	/**
	 * @设置 入库单位成本
	 * @param inUnitCost
	 */
	public void setInUnitCost(Double inUnitCost) {
	 	this.inUnitCost = inUnitCost;
	}
	/**
	 * @return 入库金额
	 */
	public Double getInCost() {
	 	return inCost;
	}
	/**
	 * @设置 入库金额
	 * @param inCost
	 */
	public void setInCost(Double inCost) {
	 	this.inCost = inCost;
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