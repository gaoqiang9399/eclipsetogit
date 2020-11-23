package app.component.pss.stock.entity;
import app.base.BaseDomain;
/**
* Title: PssCheckStockDetailBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Aug 11 17:16:56 CST 2017
* @version：1.0
**/
public class PssCheckStockDetailBill extends BaseDomain {
	private String checkStockDetailId;//盘点明细编号
	private String checkStockNo;//盘点编号
	private Integer sequence;//序号
	private String storehouseId;//仓库编号
	private String storehouseName;//仓库名称
	private String goodsId;//商品编号
	private String goodsName;//商品名称
	private Double storeStockQty;//系统库存
	private Double checkStockQty;//盘点库存
	private Double profitLossQty;//盘盈盘亏

	/**
	 * @return 盘点明细编号
	 */
	public String getCheckStockDetailId() {
	 	return checkStockDetailId;
	}
	/**
	 * @设置 盘点明细编号
	 * @param checkStockDetailId
	 */
	public void setCheckStockDetailId(String checkStockDetailId) {
	 	this.checkStockDetailId = checkStockDetailId;
	}
	/**
	 * @return 盘点编号
	 */
	public String getCheckStockNo() {
	 	return checkStockNo;
	}
	/**
	 * @设置 盘点编号
	 * @param checkStockNo
	 */
	public void setCheckStockNo(String checkStockNo) {
	 	this.checkStockNo = checkStockNo;
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
	/**
	 * @return 系统库存
	 */
	public Double getStoreStockQty() {
	 	return storeStockQty;
	}
	/**
	 * @设置 系统库存
	 * @param storeStockQty
	 */
	public void setStoreStockQty(Double storeStockQty) {
	 	this.storeStockQty = storeStockQty;
	}
	/**
	 * @return 盘点库存
	 */
	public Double getCheckStockQty() {
	 	return checkStockQty;
	}
	/**
	 * @设置 盘点库存
	 * @param checkStockQty
	 */
	public void setCheckStockQty(Double checkStockQty) {
	 	this.checkStockQty = checkStockQty;
	}
	/**
	 * @return 盘盈盘亏
	 */
	public Double getProfitLossQty() {
	 	return profitLossQty;
	}
	/**
	 * @设置 盘盈盘亏
	 * @param profitLossQty
	 */
	public void setProfitLossQty(Double profitLossQty) {
	 	this.profitLossQty = profitLossQty;
	}
}