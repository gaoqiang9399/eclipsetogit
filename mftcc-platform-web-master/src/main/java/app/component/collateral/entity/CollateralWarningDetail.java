package app.component.collateral.entity;
import app.base.BaseDomain;
/**
* Title: CollateralWarningDetail.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Mar 31 16:49:24 CST 2017
* @version：1.0
**/
public class CollateralWarningDetail extends BaseDomain {
	private String warnNo;//方案编号
	private String warnDetailNo;//方案明细编号
	private String warnType;//预警类型
	private String warnRemark;//预警描述
	private Integer warnValue;//预警阈值
	private String warnUnit;//单位
	private String isStart;//是否启用

	/**
	 * @return 方案编号
	 */
	public String getWarnNo() {
	 	return warnNo;
	}
	/**
	 * @设置 方案编号
	 * @param warnNo
	 */
	public void setWarnNo(String warnNo) {
	 	this.warnNo = warnNo;
	}
	/**
	 * @return 方案明细编号
	 */
	public String getWarnDetailNo() {
	 	return warnDetailNo;
	}
	/**
	 * @设置 方案明细编号
	 * @param warnDetailNo
	 */
	public void setWarnDetailNo(String warnDetailNo) {
	 	this.warnDetailNo = warnDetailNo;
	}
	/**
	 * @return 预警类型
	 */
	public String getWarnType() {
	 	return warnType;
	}
	/**
	 * @设置 预警类型
	 * @param warnType
	 */
	public void setWarnType(String warnType) {
	 	this.warnType = warnType;
	}
	/**
	 * @return 预警描述
	 */
	public String getWarnRemark() {
	 	return warnRemark;
	}
	/**
	 * @设置 预警描述
	 * @param warnRemark
	 */
	public void setWarnRemark(String warnRemark) {
	 	this.warnRemark = warnRemark;
	}
	/**
	 * @return 预警阈值
	 */
	public Integer getWarnValue() {
	 	return warnValue;
	}
	/**
	 * @设置 预警阈值
	 * @param warnValue
	 */
	public void setWarnValue(Integer warnValue) {
	 	this.warnValue = warnValue;
	}
	/**
	 * @return 单位
	 */
	public String getWarnUnit() {
	 	return warnUnit;
	}
	/**
	 * @设置 单位
	 * @param warnUnit
	 */
	public void setWarnUnit(String warnUnit) {
	 	this.warnUnit = warnUnit;
	}
	/**
	 * @return 是否启用
	 */
	public String getIsStart() {
	 	return isStart;
	}
	/**
	 * @设置 是否启用
	 * @param isStart
	 */
	public void setIsStart(String isStart) {
	 	this.isStart = isStart;
	}
}