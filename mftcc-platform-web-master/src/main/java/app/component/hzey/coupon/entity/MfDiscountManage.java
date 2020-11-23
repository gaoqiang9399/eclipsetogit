package app.component.hzey.coupon.entity;
import app.base.BaseDomain;
/**
* Title: MfDiscountManage.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sun Jul 23 10:28:46 CST 2017
* @version：1.0
**/
public class MfDiscountManage extends BaseDomain {
	private String discountId;//优惠券id
	private String cusNo;//客户号
	private Double discountAmt;//优惠金额
	private String startDate;//优惠开始日期
	private String endDate;//优惠结束日期
	private String useFlag;//是否使用0-未用 1-已使用
	private String lstModTime;//修改时间
	private Double discountAmtSum; //客户优惠总额    仅展示用
	private String discountCount;//优惠券张数  仅展示用
	private String cusName;//客户姓名  仅展示用
	private String useFlagShow;//优惠券使用状态
	/**
	 * @return 优惠券id
	 */
	public String getDiscountId() {
	 	return discountId;
	}
	/**
	 * @设置 优惠券id
	 * @param discountId
	 */
	public void setDiscountId(String discountId) {
	 	this.discountId = discountId;
	}
	/**
	 * @return 客户号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户号
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 优惠金额
	 */
	public Double getDiscountAmt() {
	 	return discountAmt;
	}
	/**
	 * @设置 优惠金额
	 * @param discountAmt
	 */
	public void setDiscountAmt(Double discountAmt) {
	 	this.discountAmt = discountAmt;
	}
	/**
	 * @return 优惠开始日期
	 */
	public String getStartDate() {
	 	return startDate;
	}
	/**
	 * @设置 优惠开始日期
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
	 	this.startDate = startDate;
	}
	/**
	 * @return 优惠结束日期
	 */
	public String getEndDate() {
	 	return endDate;
	}
	/**
	 * @设置 优惠结束日期
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
	 	this.endDate = endDate;
	}
	/**
	 * @return 是否使用0-未用 1-已使用
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 是否使用0-未用 1-已使用
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	public Double getDiscountAmtSum() {
		return discountAmtSum;
	}
	public void setDiscountAmtSum(Double discountAmtSum) {
		this.discountAmtSum = discountAmtSum;
	}
	public String getDiscountCount() {
		return discountCount;
	}
	public void setDiscountCount(String discountCount) {
		this.discountCount = discountCount;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getUseFlagShow() {
		return useFlagShow;
	}
	public void setUseFlagShow(String useFlagShow) {
		this.useFlagShow = useFlagShow;
	}
	
}