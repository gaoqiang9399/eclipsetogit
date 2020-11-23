package app.component.pss.stock.entity;
import app.base.BaseDomain;
/**
* Title: PssCheckStockBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Aug 11 17:11:31 CST 2017
* @version：1.0
**/
public class PssCheckStockBill extends BaseDomain {
	private String checkStockId;//盘点ID
	private String checkStockNo;//盘点编号
	private String checkStockDate;//盘点日期
	private String storehouseId;//盘点仓库
	private String resultId;//盘点结果
	private String otherStockInNo;//盘盈结果Id-对应其他入库单据编号
	private String otherStockOutNo;//盘亏结果Id-对应其他出库单据编号
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
	 * @return 盘点ID
	 */
	public String getCheckStockId() {
	 	return checkStockId;
	}
	/**
	 * @设置 盘点ID
	 * @param checkStockId
	 */
	public void setCheckStockId(String checkStockId) {
	 	this.checkStockId = checkStockId;
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
	 * @return 盘点日期
	 */
	public String getCheckStockDate() {
	 	return checkStockDate;
	}
	/**
	 * @设置 盘点日期
	 * @param checkStockDate
	 */
	public void setCheckStockDate(String checkStockDate) {
	 	this.checkStockDate = checkStockDate;
	}
	/**
	 * @return 盘点仓库
	 */
	public String getStorehouseId() {
	 	return storehouseId;
	}
	/**
	 * @设置 盘点仓库
	 * @param storehouseId
	 */
	public void setStorehouseId(String storehouseId) {
	 	this.storehouseId = storehouseId;
	}
	/**
	 * @return 盘点结果
	 */
	public String getResultId() {
	 	return resultId;
	}
	/**
	 * @设置 盘点结果
	 * @param resultId
	 */
	public void setResultId(String resultId) {
	 	this.resultId = resultId;
	}
	public String getOtherStockInNo() {
		return otherStockInNo;
	}
	public void setOtherStockInNo(String otherStockInNo) {
		this.otherStockInNo = otherStockInNo;
	}
	public String getOtherStockOutNo() {
		return otherStockOutNo;
	}
	public void setOtherStockOutNo(String otherStockOutNo) {
		this.otherStockOutNo = otherStockOutNo;
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