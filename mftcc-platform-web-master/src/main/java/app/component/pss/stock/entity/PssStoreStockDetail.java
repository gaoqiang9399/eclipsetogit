package app.component.pss.stock.entity;
import app.base.BaseDomain;
/**
* Title: PssStoreStockDetail.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Aug 16 15:45:36 CST 2017
* @version：1.0
**/
public class PssStoreStockDetail extends BaseDomain {
	private String inOutId;//出入库明细ID
	private String stockId;//库存ID
	private String storehouseId;//仓库编号
	private String goodsId;//商品编号
	private String billDate;//单据日期
	private String billId;//业务操作ID(单据主键)
	private String billNo;//业务操作NO(单据号)
	private String billType;//业务类别
	private String billDetailType;//业务明细类别
	private String billDetailId;//业务操作明细ID(单据明细号)
	private Integer sequence;//业务序号
	private String contactId;//往来单位编号
	private String contactName;//往来单位名称
	private String inOutType;//出入库标识
	private String unitId;//单位id
	private String memo;//备注
	private Double inQty;//入库数量
	private Double inBaseUnitQty;//入库基本单位数量
	private Double inUnitCost;//入库基本单位成本
	private Double inCost;//入库成本
	private Double outQty;//出库数量
	private Double outBaseUnitQty;//出库基本单位数量
	private Double outUnitCost;//出库基本单位成本
	private Double outCost;//出库成本
	private Double balanUnitQty;//结存单位数量
	private Double balanUnitCost;//结存单位成本
	private Double balanCost;//结存成本
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

	/**
	 * @return 出入库明细ID
	 */
	public String getInOutId() {
	 	return inOutId;
	}
	/**
	 * @设置 出入库明细ID
	 * @param inOutId
	 */
	public void setInOutId(String inOutId) {
	 	this.inOutId = inOutId;
	}
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
	/**
	 * @return 单据日期
	 */
	public String getBillDate() {
	 	return billDate;
	}
	/**
	 * @设置 单据日期
	 * @param billDate
	 */
	public void setBillDate(String billDate) {
	 	this.billDate = billDate;
	}
	/**
	 * @return 业务操作ID(单据主键)
	 */
	public String getBillId() {
	 	return billId;
	}
	/**
	 * @设置 业务操作ID(单据主键)
	 * @param billId
	 */
	public void setBillId(String billId) {
	 	this.billId = billId;
	}
	/**
	 * @return 业务操作NO(单据号)
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * @设置 业务操作NO(单据号)
	 * @param billNO
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	/**
	 * @return 业务类别
	 */
	public String getBillType() {
	 	return billType;
	}
	/**
	 * @设置 业务类别
	 * @param billType
	 */
	public void setBillType(String billType) {
	 	this.billType = billType;
	}
	public String getBillDetailType() {
		return billDetailType;
	}
	public void setBillDetailType(String billDetailType) {
		this.billDetailType = billDetailType;
	}
	/**
	 * @return 业务操作明细ID(单据明细号)
	 */
	public String getBillDetailId() {
	 	return billDetailId;
	}
	/**
	 * @设置 业务操作明细ID(单据明细号)
	 * @param billDetailId
	 */
	public void setBillDetailId(String billDetailId) {
	 	this.billDetailId = billDetailId;
	}
	/**
	 * @return 业务序号
	 */
	public Integer getSequence() {
	 	return sequence;
	}
	/**
	 * @设置 业务序号
	 * @param sequence
	 */
	public void setSequence(Integer sequence) {
	 	this.sequence = sequence;
	}
	/**
	 * @return 往来单位编号
	 */
	public String getContactId() {
	 	return contactId;
	}
	/**
	 * @设置 往来单位编号
	 * @param contactId
	 */
	public void setContactId(String contactId) {
	 	this.contactId = contactId;
	}
	/**
	 * @return 往来单位名称
	 */
	public String getContactName() {
	 	return contactName;
	}
	/**
	 * @设置 往来单位名称
	 * @param contactName
	 */
	public void setContactName(String contactName) {
	 	this.contactName = contactName;
	}
	/**
	 * @return 出入库标识
	 */
	public String getInOutType() {
	 	return inOutType;
	}
	/**
	 * @设置 出入库标识
	 * @param inOutType
	 */
	public void setInOutType(String inOutType) {
	 	this.inOutType = inOutType;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
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
	 * @return 入库数量
	 */
	public Double getInQty() {
	 	return inQty;
	}
	/**
	 * @设置 入库数量
	 * @param inQty
	 */
	public void setInQty(Double inQty) {
	 	this.inQty = inQty;
	}
	/**
	 * @return 入库基本单位数量
	 */
	public Double getInBaseUnitQty() {
	 	return inBaseUnitQty;
	}
	/**
	 * @设置 入库基本单位数量
	 * @param inBaseUnitQty
	 */
	public void setInBaseUnitQty(Double inBaseUnitQty) {
	 	this.inBaseUnitQty = inBaseUnitQty;
	}
	/**
	 * @return 入库基本单位成本
	 */
	public Double getInUnitCost() {
	 	return inUnitCost;
	}
	/**
	 * @设置 入库基本单位成本
	 * @param inUnitCost
	 */
	public void setInUnitCost(Double inUnitCost) {
	 	this.inUnitCost = inUnitCost;
	}
	/**
	 * @return 入库成本
	 */
	public Double getInCost() {
	 	return inCost;
	}
	/**
	 * @设置 入库成本
	 * @param inCost
	 */
	public void setInCost(Double inCost) {
	 	this.inCost = inCost;
	}
	/**
	 * @return 出库数量
	 */
	public Double getOutQty() {
	 	return outQty;
	}
	/**
	 * @设置 出库数量
	 * @param outQty
	 */
	public void setOutQty(Double outQty) {
	 	this.outQty = outQty;
	}
	/**
	 * @return 出库基本单位数量
	 */
	public Double getOutBaseUnitQty() {
	 	return outBaseUnitQty;
	}
	/**
	 * @设置 出库基本单位数量
	 * @param outBaseUnitQty
	 */
	public void setOutBaseUnitQty(Double outBaseUnitQty) {
	 	this.outBaseUnitQty = outBaseUnitQty;
	}
	/**
	 * @return 出库基本单位成本
	 */
	public Double getOutUnitCost() {
	 	return outUnitCost;
	}
	/**
	 * @设置 出库基本单位成本
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
	 * @return 结存单位数量
	 */
	public Double getBalanUnitQty() {
	 	return balanUnitQty;
	}
	/**
	 * @设置 结存单位数量
	 * @param balanUnitQty
	 */
	public void setBalanUnitQty(Double balanUnitQty) {
	 	this.balanUnitQty = balanUnitQty;
	}
	/**
	 * @return 结存单位成本
	 */
	public Double getBalanUnitCost() {
	 	return balanUnitCost;
	}
	/**
	 * @设置 结存单位成本
	 * @param balanUnitCost
	 */
	public void setBalanUnitCost(Double balanUnitCost) {
	 	this.balanUnitCost = balanUnitCost;
	}
	/**
	 * @return 结存成本
	 */
	public Double getBalanCost() {
	 	return balanCost;
	}
	/**
	 * @设置 结存成本
	 * @param balanCost
	 */
	public void setBalanCost(Double balanCost) {
	 	this.balanCost = balanCost;
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
}