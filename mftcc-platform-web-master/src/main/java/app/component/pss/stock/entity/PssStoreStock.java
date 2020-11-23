package app.component.pss.stock.entity;
import app.base.BaseDomain;
/**
* Title: PssStoreStock.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Aug 11 16:54:32 CST 2017
* @version：1.0
**/
public class PssStoreStock extends BaseDomain {
	private String stockId;//库存ID
	private String storehouseId;//仓库编号
	private String storehouseName;//仓库名称
	private String goodsId;//商品编号
	private String goodsName;//商品名称
	private Double stockBaseQty;//库存基本数量
	private Double stockAvaQty;//可用库存
	private String regOpNo;//登记人编号
	private String regOpName;//登记人名称
	private String regBrNo;//登记机构编号
	private String regBrName;//登记机构名称
	private String regTime;//登记时间
	private String lstModOpNo;//最后修改人编号
	private String lstModOpName;//最后修改人名称
	private String lstModBrNo;//最后修改人机构编号
	private String lstModBrName;//最后修改人机构名称
	private String lstModTime;//最后修改时间
	
	//库存预警用
	private Integer maxAmt;//最大库存
	private Integer minAmt;//最小库存
	
	//中间变量 表中无对应字段
	private Double stockOutQty;//出库数量
	private Double stockInQty;//入库数量
	private String billDate;//单据日期
	private String flag;//商品启用标识

	/**
	 * @return 库存ID
	 */
	public String getStockId() {
	 	return stockId;
	}
	/**
	 * @设置 库存ID
	 * @param stockId
	 */
	public void setStockId(String stockId) {
	 	this.stockId = stockId;
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
	 * @return 库存基本数量
	 */
	public Double getStockBaseQty() {
	 	return stockBaseQty;
	}
	/**
	 * @设置 库存基本数量
	 * @param stockBaseQty
	 */
	public void setStockBaseQty(Double stockBaseQty) {
	 	this.stockBaseQty = stockBaseQty;
	}
	/**
	 * @return 可用库存
	 */
	public Double getStockAvaQty() {
	 	return stockAvaQty;
	}
	/**
	 * @设置 可用库存
	 * @param stockAvaQty
	 */
	public void setStockAvaQty(Double stockAvaQty) {
	 	this.stockAvaQty = stockAvaQty;
	}
	/**
	 * @return 登记人编号
	 */
	public String getRegOpNo() {
	 	return regOpNo;
	}
	/**
	 * @设置 登记人编号
	 * @param regOpNo
	 */
	public void setRegOpNo(String regOpNo) {
	 	this.regOpNo = regOpNo;
	}
	/**
	 * @return 登记人名称
	 */
	public String getRegOpName() {
	 	return regOpName;
	}
	/**
	 * @设置 登记人名称
	 * @param regOpName
	 */
	public void setRegOpName(String regOpName) {
	 	this.regOpName = regOpName;
	}
	/**
	 * @return 登记机构编号
	 */
	public String getRegBrNo() {
	 	return regBrNo;
	}
	/**
	 * @设置 登记机构编号
	 * @param regBrNo
	 */
	public void setRegBrNo(String regBrNo) {
	 	this.regBrNo = regBrNo;
	}
	/**
	 * @return 登记机构名称
	 */
	public String getRegBrName() {
	 	return regBrName;
	}
	/**
	 * @设置 登记机构名称
	 * @param regBrName
	 */
	public void setRegBrName(String regBrName) {
	 	this.regBrName = regBrName;
	}
	/**
	 * @return 登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改人编号
	 */
	public String getLstModOpNo() {
	 	return lstModOpNo;
	}
	/**
	 * @设置 最后修改人编号
	 * @param lstModOpNo
	 */
	public void setLstModOpNo(String lstModOpNo) {
	 	this.lstModOpNo = lstModOpNo;
	}
	/**
	 * @return 最后修改人名称
	 */
	public String getLstModOpName() {
	 	return lstModOpName;
	}
	/**
	 * @设置 最后修改人名称
	 * @param lstModOpName
	 */
	public void setLstModOpName(String lstModOpName) {
	 	this.lstModOpName = lstModOpName;
	}
	/**
	 * @return 最后修改人机构编号
	 */
	public String getLstModBrNo() {
	 	return lstModBrNo;
	}
	/**
	 * @设置 最后修改人机构编号
	 * @param lstModBrNo
	 */
	public void setLstModBrNo(String lstModBrNo) {
	 	this.lstModBrNo = lstModBrNo;
	}
	/**
	 * @return 最后修改人机构名称
	 */
	public String getLstModBrName() {
	 	return lstModBrName;
	}
	/**
	 * @设置 最后修改人机构名称
	 * @param lstModBrName
	 */
	public void setLstModBrName(String lstModBrName) {
	 	this.lstModBrName = lstModBrName;
	}
	/**
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	public Double getStockOutQty() {
		return stockOutQty;
	}
	public void setStockOutQty(Double stockOutQty) {
		this.stockOutQty = stockOutQty;
	}
	public Double getStockInQty() {
		return stockInQty;
	}
	public void setStockInQty(Double stockInQty) {
		this.stockInQty = stockInQty;
	}
	public Integer getMaxAmt() {
		return maxAmt;
	}
	public void setMaxAmt(Integer maxAmt) {
		this.maxAmt = maxAmt;
	}
	public Integer getMinAmt() {
		return minAmt;
	}
	public void setMinAmt(Integer minAmt) {
		this.minAmt = minAmt;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}