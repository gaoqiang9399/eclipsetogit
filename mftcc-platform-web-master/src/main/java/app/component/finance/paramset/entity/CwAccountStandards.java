package app.component.finance.paramset.entity;
import app.base.BaseDomain;
/**
* Title: CwAccountStandards.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Feb 07 15:05:15 CST 2017
* @version：1.0
**/
public class CwAccountStandards extends BaseDomain {
	private String standNo;//准则编号
	private String standName;//准则名称
	private String monBnlrKm;//月结本年利润科目填写科目控制字
	private String monSytzKm;//月结以前年度损益调整科目填写科目控制字
	private String monSytzjzKm;//月结以前年度损益调整的结转科目填写科目控制字
	private String yearWfplrKm;//年结未分配利润科目填写科目控制字
	private String itemStartNo;//财务报表项自增编号
	private String accnoLenVal;//科目编码长度参数编码长度参数范围0~3
	private String isEditLen;//编码长度是否可修改Y：可修改；N：不可修改
	private String createDate;//创建日期
	private String createTime;//创建时间
	private String occTime;//时间戳格式：YYYYMMDD HH:mm:ss

	/**
	 * @return 准则编号
	 */
	public String getStandNo() {
	 	return standNo;
	}
	/**
	 * @设置 准则编号
	 * @param standNo
	 */
	public void setStandNo(String standNo) {
	 	this.standNo = standNo;
	}
	/**
	 * @return 准则名称
	 */
	public String getStandName() {
	 	return standName;
	}
	/**
	 * @设置 准则名称
	 * @param standName
	 */
	public void setStandName(String standName) {
	 	this.standName = standName;
	}
	/**
	 * @return 月结本年利润科目填写科目控制字
	 */
	public String getMonBnlrKm() {
	 	return monBnlrKm;
	}
	/**
	 * @设置 月结本年利润科目填写科目控制字
	 * @param monBnlrKm
	 */
	public void setMonBnlrKm(String monBnlrKm) {
	 	this.monBnlrKm = monBnlrKm;
	}
	/**
	 * @return 月结以前年度损益调整科目填写科目控制字
	 */
	public String getMonSytzKm() {
	 	return monSytzKm;
	}
	/**
	 * @设置 月结以前年度损益调整科目填写科目控制字
	 * @param monSytzKm
	 */
	public void setMonSytzKm(String monSytzKm) {
	 	this.monSytzKm = monSytzKm;
	}
	/**
	 * @return 月结以前年度损益调整的结转科目填写科目控制字
	 */
	public String getMonSytzjzKm() {
	 	return monSytzjzKm;
	}
	/**
	 * @设置 月结以前年度损益调整的结转科目填写科目控制字
	 * @param monSytzjzKm
	 */
	public void setMonSytzjzKm(String monSytzjzKm) {
	 	this.monSytzjzKm = monSytzjzKm;
	}
	/**
	 * @return 年结未分配利润科目填写科目控制字
	 */
	public String getYearWfplrKm() {
	 	return yearWfplrKm;
	}
	/**
	 * @设置 年结未分配利润科目填写科目控制字
	 * @param yearWfplrKm
	 */
	public void setYearWfplrKm(String yearWfplrKm) {
	 	this.yearWfplrKm = yearWfplrKm;
	}
	/**
	 * @return 财务报表项自增编号
	 */
	public String getItemStartNo() {
	 	return itemStartNo;
	}
	/**
	 * @设置 财务报表项自增编号
	 * @param itemStartNo
	 */
	public void setItemStartNo(String itemStartNo) {
	 	this.itemStartNo = itemStartNo;
	}
	/**
	 * @return 科目编码长度参数编码长度参数范围0~3
	 */
	public String getAccnoLenVal() {
	 	return accnoLenVal;
	}
	/**
	 * @设置 科目编码长度参数编码长度参数范围0~3
	 * @param accnoLenVal
	 */
	public void setAccnoLenVal(String accnoLenVal) {
	 	this.accnoLenVal = accnoLenVal;
	}
	/**
	 * @return 编码长度是否可修改Y：可修改；N：不可修改
	 */
	public String getIsEditLen() {
	 	return isEditLen;
	}
	/**
	 * @设置 编码长度是否可修改Y：可修改；N：不可修改
	 * @param isEditLen
	 */
	public void setIsEditLen(String isEditLen) {
	 	this.isEditLen = isEditLen;
	}
	/**
	 * @return 创建日期
	 */
	public String getCreateDate() {
	 	return createDate;
	}
	/**
	 * @设置 创建日期
	 * @param createDate
	 */
	public void setCreateDate(String createDate) {
	 	this.createDate = createDate;
	}
	/**
	 * @return 创建时间
	 */
	public String getCreateTime() {
	 	return createTime;
	}
	/**
	 * @设置 创建时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
	 	this.createTime = createTime;
	}
	/**
	 * @return 时间戳格式：YYYYMMDD HH:mm:ss
	 */
	public String getOccTime() {
	 	return occTime;
	}
	/**
	 * @设置 时间戳格式：YYYYMMDD HH:mm:ss
	 * @param occTime
	 */
	public void setOccTime(String occTime) {
	 	this.occTime = occTime;
	}
}