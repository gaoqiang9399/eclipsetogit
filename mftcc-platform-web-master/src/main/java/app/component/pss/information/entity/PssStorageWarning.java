package app.component.pss.information.entity;
import app.base.BaseDomain;
/**
* Title: PssStorageWarning.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Aug 30 10:07:13 CST 2017
* @version：1.0
**/
public class PssStorageWarning extends BaseDomain {
	private String warningId;//方案编号
	private String goodsId;//货物编号
	private Integer maxAmt;//最大库存
	private Integer minAmt;//最小库存
	private String storehouseId;//仓库编号
	private String useHelperprop;//是否使用辅助属性
	private String detailIds;//辅助属性
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
	private Integer sequence;

	/**
	 * @return 方案编号
	 */
	public String getWarningId() {
	 	return warningId;
	}
	/**
	 * @设置 方案编号
	 * @param warningId
	 */
	public void setWarningId(String warningId) {
	 	this.warningId = warningId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	/**
	 * @return 最大库存
	 */
	public Integer getMaxAmt() {
	 	return maxAmt;
	}
	/**
	 * @设置 最大库存
	 * @param maxAmt
	 */
	public void setMaxAmt(Integer maxAmt) {
	 	this.maxAmt = maxAmt;
	}
	/**
	 * @return 最小库存
	 */
	public Integer getMinAmt() {
	 	return minAmt;
	}
	/**
	 * @设置 最小库存
	 * @param minAmt
	 */
	public void setMinAmt(Integer minAmt) {
	 	this.minAmt = minAmt;
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
	 * @return 是否使用辅助属性
	 */
	public String getUseHelperprop() {
	 	return useHelperprop;
	}
	/**
	 * @设置 是否使用辅助属性
	 * @param useHelperprop
	 */
	public void setUseHelperprop(String useHelperprop) {
	 	this.useHelperprop = useHelperprop;
	}
	/**
	 * @return 辅助属性
	 */
	public String getDetailIds() {
	 	return detailIds;
	}
	/**
	 * @设置 辅助属性
	 * @param detailIds
	 */
	public void setDetailIds(String detailIds) {
	 	this.detailIds = detailIds;
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
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
}