package app.component.pss.information.entity;
import app.base.BaseDomain;
/**
* Title: PssGoods.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Aug 15 17:06:56 CST 2017
* @version：1.0
**/
public class PssGoods extends BaseDomain {
	private String goodsId;//商品ID
	private String goodsNo;//商品编号
	private String goodsName;//商品名称
	private String goodsSn;//商品序列号
	private String goodsModel;//商品型号
	private String goodsType;//商品种类
	private String storehouseId;//首选仓库
	private String goodsBrand;//商品品牌
	private String goodsUnit;//商品单位
	private Double retailPrice;//零售价
	private Double wholesalePrice;//批发价
	private Double vipPrice;//VIP会员价
	private Double discountRate1;//折扣率一(%)
	private Double discountRate2;//折扣率二(%)
	private Double estimatedPurchasePrice;//预计采购价
	private String memo;//备注
	private String goodsPic;//图片
	private String stockWarningFlag;//启用库存预警
	private String warningPlanId;//预警方案id
	private String detailIds;//辅助属性编号
	private String batchWarningFlag;//启用批次质量管理
	private Integer batchQgp;//保质期天数
	private Integer batchBeforQgp;//启用预警天数
	private String snFlag;//启用序列号管理
	private String flag;//是否启用
	private String regOpNo;//创建人编号
	private String regOpName;//创建人名称
	private String regBrNo;//创建人机构编号
	private String regBrName;//创建人机构名称
	private String regTime;//创建时间
	private String lstModOpNo;//最后修改人编号
	private String lstModOpName;//最后修改人名称
	private String lstModBrNo;//最后修改人机构编号
	private String lstModBrName;//最后修改人机构名称
	private String lstModTime;//最后修改时间
	private Integer maxStock;
	private Integer minStock;
	private Integer sepStockFlag;
	private String mutiUnitFlag;
	private String unitId;
	private String docNo;
	private String docBizNo;
	private String displayFlag;

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
	 * @return 商品编号
	 */
	public String getGoodsNo() {
	 	return goodsNo;
	}
	/**
	 * @设置 商品编号
	 * @param goodsNo
	 */
	public void setGoodsNo(String goodsNo) {
	 	this.goodsNo = goodsNo;
	}
	/**
	 * @return 商品名称
	 */
	public String getGoodsName() {
	 	return goodsName;
	}
	/**
	 * @设置 商品名称
	 * @param goodsName
	 */
	public void setGoodsName(String goodsName) {
	 	this.goodsName = goodsName;
	}
	/**
	 * @return 商品序列号
	 */
	public String getGoodsSn() {
	 	return goodsSn;
	}
	/**
	 * @设置 商品序列号
	 * @param goodsSn
	 */
	public void setGoodsSn(String goodsSn) {
	 	this.goodsSn = goodsSn;
	}
	/**
	 * @return 商品型号
	 */
	public String getGoodsModel() {
	 	return goodsModel;
	}
	/**
	 * @设置 商品型号
	 * @param goodsModel
	 */
	public void setGoodsModel(String goodsModel) {
	 	this.goodsModel = goodsModel;
	}
	/**
	 * @return 商品种类
	 */
	public String getGoodsType() {
	 	return goodsType;
	}
	/**
	 * @设置 商品种类
	 * @param goodsType
	 */
	public void setGoodsType(String goodsType) {
	 	this.goodsType = goodsType;
	}
	/**
	 * @return 首选仓库
	 */
	public String getStorehouseId() {
	 	return storehouseId;
	}
	/**
	 * @设置 首选仓库
	 * @param storehouseId
	 */
	public void setStorehouseId(String storehouseId) {
	 	this.storehouseId = storehouseId;
	}
	/**
	 * @return 商品品牌
	 */
	public String getGoodsBrand() {
	 	return goodsBrand;
	}
	/**
	 * @设置 商品品牌
	 * @param goodsBrand
	 */
	public void setGoodsBrand(String goodsBrand) {
	 	this.goodsBrand = goodsBrand;
	}
	/**
	 * @return 商品单位
	 */
	public String getGoodsUnit() {
	 	return goodsUnit;
	}
	/**
	 * @设置 商品单位
	 * @param goodsUnit
	 */
	public void setGoodsUnit(String goodsUnit) {
	 	this.goodsUnit = goodsUnit;
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
	 * @return 备注
	 */
	public String getMemo() {
	 	return memo;
	}
	/**
	 * @设置 备注
	 * @param memo
	 */
	public void setMemo(String memo) {
	 	this.memo = memo;
	}
	/**
	 * @return 图片
	 */
	public String getGoodsPic() {
	 	return goodsPic;
	}
	/**
	 * @设置 图片
	 * @param goodsPic
	 */
	public void setGoodsPic(String goodsPic) {
	 	this.goodsPic = goodsPic;
	}
	/**
	 * @return 启用库存预警
	 */
	public String getStockWarningFlag() {
	 	return stockWarningFlag;
	}
	/**
	 * @设置 启用库存预警
	 * @param stockWarningFlag
	 */
	public void setStockWarningFlag(String stockWarningFlag) {
	 	this.stockWarningFlag = stockWarningFlag;
	}
	/**
	 * @return 预警方案id
	 */
	public String getWarningPlanId() {
	 	return warningPlanId;
	}
	/**
	 * @设置 预警方案id
	 * @param warningPlanId
	 */
	public void setWarningPlanId(String warningPlanId) {
	 	this.warningPlanId = warningPlanId;
	}
	/**
	 * @return 辅助属性编号
	 */
	public String getDetailIds() {
	 	return detailIds;
	}
	/**
	 * @设置 辅助属性编号
	 * @param detailIds
	 */
	public void setDetailIds(String detailIds) {
	 	this.detailIds = detailIds;
	}
	/**
	 * @return 启用批次质量管理
	 */
	public String getBatchWarningFlag() {
	 	return batchWarningFlag;
	}
	/**
	 * @设置 启用批次质量管理
	 * @param batchWarningFlag
	 */
	public void setBatchWarningFlag(String batchWarningFlag) {
	 	this.batchWarningFlag = batchWarningFlag;
	}
	/**
	 * @return 保质期天数
	 */
	public Integer getBatchQgp() {
	 	return batchQgp;
	}
	/**
	 * @设置 保质期天数
	 * @param batchQgp
	 */
	public void setBatchQgp(Integer batchQgp) {
	 	this.batchQgp = batchQgp;
	}
	/**
	 * @return 启用预警天数
	 */
	public Integer getBatchBeforQgp() {
	 	return batchBeforQgp;
	}
	/**
	 * @设置 启用预警天数
	 * @param batchBeforQgp
	 */
	public void setBatchBeforQgp(Integer batchBeforQgp) {
	 	this.batchBeforQgp = batchBeforQgp;
	}
	/**
	 * @return 启用序列号管理
	 */
	public String getSnFlag() {
	 	return snFlag;
	}
	/**
	 * @设置 启用序列号管理
	 * @param snFlag
	 */
	public void setSnFlag(String snFlag) {
	 	this.snFlag = snFlag;
	}
	/**
	 * @return 创建人编号
	 */
	public String getRegOpNo() {
	 	return regOpNo;
	}
	/**
	 * @设置 创建人编号
	 * @param regOpNo
	 */
	public void setRegOpNo(String regOpNo) {
	 	this.regOpNo = regOpNo;
	}
	/**
	 * @return 创建人名称
	 */
	public String getRegOpName() {
	 	return regOpName;
	}
	/**
	 * @设置 创建人名称
	 * @param regOpName
	 */
	public void setRegOpName(String regOpName) {
	 	this.regOpName = regOpName;
	}
	/**
	 * @return 创建人机构编号
	 */
	public String getRegBrNo() {
	 	return regBrNo;
	}
	/**
	 * @设置 创建人机构编号
	 * @param regBrNo
	 */
	public void setRegBrNo(String regBrNo) {
	 	this.regBrNo = regBrNo;
	}
	/**
	 * @return 创建人机构名称
	 */
	public String getRegBrName() {
	 	return regBrName;
	}
	/**
	 * @设置 创建人机构名称
	 * @param regBrName
	 */
	public void setRegBrName(String regBrName) {
	 	this.regBrName = regBrName;
	}
	/**
	 * @return 创建时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 创建时间
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Integer getMaxStock() {
		return maxStock;
	}
	public void setMaxStock(Integer maxStock) {
		this.maxStock = maxStock;
	}
	public Integer getMinStock() {
		return minStock;
	}
	public void setMinStock(Integer minStock) {
		this.minStock = minStock;
	}
	public Integer getSepStockFlag() {
		return sepStockFlag;
	}
	public void setSepStockFlag(Integer sepStockFlag) {
		this.sepStockFlag = sepStockFlag;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getDocBizNo() {
		return docBizNo;
	}
	public void setDocBizNo(String docBizNo) {
		this.docBizNo = docBizNo;
	}
	public String getDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}
	public String getMutiUnitFlag() {
		return mutiUnitFlag;
	}
	public void setMutiUnitFlag(String mutiUnitFlag) {
		this.mutiUnitFlag = mutiUnitFlag;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
}