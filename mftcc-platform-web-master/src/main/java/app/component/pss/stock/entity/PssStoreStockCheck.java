package app.component.pss.stock.entity;
import app.base.BaseDomain;
/**
* Title: PssStoreStockCheck.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed OTC 13 09:45:36 CST 2017
* @version：1.0
**/
public class PssStoreStockCheck extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private boolean isCheck;//是否审核
	private String flag;//检查标识 0：不足；1：充足
	private String storehouseId;//仓库编号
	private String storehouseName;//仓库名称
	private String goodsId;//商品编号
	private String goodsName;//商品名称
	private Double stockBaseQty;//库存基本（实际）数量-出入库后
	private Double stockAvaQty;//可用库存-出入库后
	private Double stockNotEnoughQty;//不足库存数量
	
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getStorehouseId() {
		return storehouseId;
	}
	public void setStorehouseId(String storehouseId) {
		this.storehouseId = storehouseId;
	}
	public String getStorehouseName() {
		return storehouseName;
	}
	public void setStorehouseName(String storehouseName) {
		this.storehouseName = storehouseName;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Double getStockBaseQty() {
		return stockBaseQty;
	}
	public void setStockBaseQty(Double stockBaseQty) {
		this.stockBaseQty = stockBaseQty;
	}
	public Double getStockAvaQty() {
		return stockAvaQty;
	}
	public void setStockAvaQty(Double stockAvaQty) {
		this.stockAvaQty = stockAvaQty;
	}
	public Double getStockNotEnoughQty() {
		return stockNotEnoughQty;
	}
	public void setStockNotEnoughQty(Double stockNotEnoughQty) {
		this.stockNotEnoughQty = stockNotEnoughQty;
	}
	
}