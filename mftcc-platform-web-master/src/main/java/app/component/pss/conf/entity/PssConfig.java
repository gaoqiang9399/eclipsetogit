package app.component.pss.conf.entity;
import app.base.BaseDomain;
/**
* Title: PssConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Aug 11 16:00:07 CST 2017
* @version：1.0
**/
/**
 * @author LiuYF
 *
 */
public class PssConfig extends BaseDomain {
	private String confId;//数据主键
	private String curType;//币种
	private String numDecimalDigit;//数量小数位
	private String amtDecimalDigit;//单价小数位
	private String invPricingMethod;//存活计价方法
	private String startNegativeInv;//启用负库存检查
	private String startApproval;//启用审核
	private String startDuty;//启用税金
	private Double dutyRate;//增值税税率
	private String startPlusDuty;//商品价格是否含税
	private String startPropHelper;//启用辅助属性
	private String startSn;//启用序列号
	private String startExpDate;//启用批次保质期管理
	private String startAutoAmt;//启用自动填充结算金额
	private String startAps;//启用分仓核算
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

	/**
	 * @return 数据主键
	 */
	public String getConfId() {
	 	return confId;
	}
	/**
	 * @设置 数据主键
	 * @param confId
	 */
	public void setConfId(String confId) {
	 	this.confId = confId;
	}
	/**
	 * @return 币种
	 */
	public String getCurType() {
	 	return curType;
	}
	/**
	 * @设置 币种
	 * @param curType
	 */
	public void setCurType(String curType) {
	 	this.curType = curType;
	}
	public String getNumDecimalDigit() {
		return numDecimalDigit;
	}
	public void setNumDecimalDigit(String numDecimalDigit) {
		this.numDecimalDigit = numDecimalDigit;
	}
	public String getAmtDecimalDigit() {
		return amtDecimalDigit;
	}
	public void setAmtDecimalDigit(String amtDecimalDigit) {
		this.amtDecimalDigit = amtDecimalDigit;
	}
	/**
	 * @return 单价小数位
	 */
	public String getInvPricingMethod() {
	 	return invPricingMethod;
	}
	/**
	 * @设置 单价小数位
	 * @param invPricingMethod
	 */
	public void setInvPricingMethod(String invPricingMethod) {
	 	this.invPricingMethod = invPricingMethod;
	}
	/**
	 * @return 启用负库存检查
	 */
	public String getStartNegativeInv() {
	 	return startNegativeInv;
	}
	/**
	 * @设置 启用负库存检查
	 * @param startNegativeInv
	 */
	public void setStartNegativeInv(String startNegativeInv) {
	 	this.startNegativeInv = startNegativeInv;
	}
	/**
	 * @return 启用审核
	 */
	public String getStartApproval() {
	 	return startApproval;
	}
	/**
	 * @设置 启用审核
	 * @param startApproval
	 */
	public void setStartApproval(String startApproval) {
	 	this.startApproval = startApproval;
	}
	/**
	 * @return 启用税金
	 */
	public String getStartDuty() {
	 	return startDuty;
	}
	/**
	 * @设置 启用税金
	 * @param startDuty
	 */
	public void setStartDuty(String startDuty) {
	 	this.startDuty = startDuty;
	}
	/**
	 * @return 增值税税率
	 */
	public Double getDutyRate() {
	 	return dutyRate;
	}
	/**
	 * @设置 增值税税率
	 * @param dutyRate
	 */
	public void setDutyRate(Double dutyRate) {
	 	this.dutyRate = dutyRate;
	}
	/**
	 * @return 商品价格是否含税
	 */
	public String getStartPlusDuty() {
	 	return startPlusDuty;
	}
	/**
	 * @设置 商品价格是否含税
	 * @param startPlusDuty
	 */
	public void setStartPlusDuty(String startPlusDuty) {
	 	this.startPlusDuty = startPlusDuty;
	}
	/**
	 * @return 启用辅助属性
	 */
	public String getStartPropHelper() {
	 	return startPropHelper;
	}
	/**
	 * @设置 启用辅助属性
	 * @param startPropHelper
	 */
	public void setStartPropHelper(String startPropHelper) {
	 	this.startPropHelper = startPropHelper;
	}
	/**
	 * @return 启用序列号
	 */
	public String getStartSn() {
	 	return startSn;
	}
	/**
	 * @设置 启用序列号
	 * @param startSn
	 */
	public void setStartSn(String startSn) {
	 	this.startSn = startSn;
	}
	/**
	 * @return 启用批次保质期管理
	 */
	public String getStartExpDate() {
	 	return startExpDate;
	}
	/**
	 * @设置 启用批次保质期管理
	 * @param startExpDate
	 */
	public void setStartExpDate(String startExpDate) {
	 	this.startExpDate = startExpDate;
	}
	/**
	 * @return 启用自动填充结算金额
	 */
	public String getStartAutoAmt() {
	 	return startAutoAmt;
	}
	/**
	 * @设置 启用自动填充结算金额
	 * @param startAutoAmt
	 */
	public void setStartAutoAmt(String startAutoAmt) {
	 	this.startAutoAmt = startAutoAmt;
	}
	/**
	 * @return 启用分仓核算
	 */
	public String getStartAps() {
	 	return startAps;
	}
	/**
	 * @设置 启用分仓核算
	 * @param startAps
	 */
	public void setStartAps(String startAps) {
	 	this.startAps = startAps;
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
}