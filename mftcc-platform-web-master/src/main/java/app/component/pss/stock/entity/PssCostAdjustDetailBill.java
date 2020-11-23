package app.component.pss.stock.entity;
import app.base.BaseDomain;
/**
* Title: PssCostAdjustDetailBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Aug 31 14:46:40 CST 2017
* @version：1.0
**/
public class PssCostAdjustDetailBill extends BaseDomain {
	private String costAdjustDetailId;//成本调整单据明细编号
	private String costAdjustNo;//成本调整单据编号
	private Integer sequence;//序号
	private String goodsId;//商品编号
	private String goodsName;//商品名称
	private String storehouseId;//仓库编号
	private String storehouseName;//仓库名称
	private Double adjustDetailAmt;//调整金额
	private String memo;//分录备注

	/**
	 * @return 成本调整单据明细编号
	 */
	public String getCostAdjustDetailId() {
	 	return costAdjustDetailId;
	}
	/**
	 * @设置 成本调整单据明细编号
	 * @param costAdjustDetailId
	 */
	public void setCostAdjustDetailId(String costAdjustDetailId) {
	 	this.costAdjustDetailId = costAdjustDetailId;
	}
	/**
	 * @return 成本调整单据编号
	 */
	public String getCostAdjustNo() {
	 	return costAdjustNo;
	}
	/**
	 * @设置 成本调整单据编号
	 * @param costAdjustNo
	 */
	public void setCostAdjustNo(String costAdjustNo) {
	 	this.costAdjustNo = costAdjustNo;
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
	 * @return 调整金额
	 */
	public Double getAdjustDetailAmt() {
	 	return adjustDetailAmt;
	}
	/**
	 * @设置 调整金额
	 * @param adjustDetailAmt
	 */
	public void setAdjustDetailAmt(Double adjustDetailAmt) {
	 	this.adjustDetailAmt = adjustDetailAmt;
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