package app.component.pss.fund.entity;
import app.base.BaseDomain;
/**
* Title: PssOtherPayDetailBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Nov 15 15:02:32 CST 2017
* @version：1.0
**/
public class PssOtherPayDetailBill extends BaseDomain {
	private String otherPayDetailId;//其他支出单明细ID
	private String otherPayNo;//其他支出单据编号
	private String buySaleExpNo;//采购销售费用清单编号
	private Integer sequence;//序号
	private String saleType;//支出类别
	private Double payAmt;//金额
	private String memo;//分录备注

	/**
	 * @return 其他支出单明细ID
	 */
	public String getOtherPayDetailId() {
	 	return otherPayDetailId;
	}
	/**
	 * @设置 其他支出单明细ID
	 * @param otherPayDetailId
	 */
	public void setOtherPayDetailId(String otherPayDetailId) {
	 	this.otherPayDetailId = otherPayDetailId;
	}
	/**
	 * @return 其他支出单据编号
	 */
	public String getOtherPayNo() {
	 	return otherPayNo;
	}
	/**
	 * @设置 其他支出单据编号
	 * @param otherPayNo
	 */
	public void setOtherPayNo(String otherPayNo) {
	 	this.otherPayNo = otherPayNo;
	}
	/**
	 * @return 采购销售费用清单编号
	 */
	public String getBuySaleExpNo() {
	 	return buySaleExpNo;
	}
	/**
	 * @设置 采购销售费用清单编号
	 * @param buySaleExpNo
	 */
	public void setBuySaleExpNo(String buySaleExpNo) {
	 	this.buySaleExpNo = buySaleExpNo;
	}
	/**
	 * @return 序号
	 */
	public Integer getSequence() {
	 	return sequence;
	}
	/**
	 * @设置 序号
	 * @param sequence
	 */
	public void setSequence(Integer sequence) {
	 	this.sequence = sequence;
	}
	/**
	 * @return 支出类别
	 */
	public String getSaleType() {
	 	return saleType;
	}
	/**
	 * @设置 支出类别
	 * @param saleType
	 */
	public void setSaleType(String saleType) {
	 	this.saleType = saleType;
	}
	/**
	 * @return 金额
	 */
	public Double getPayAmt() {
	 	return payAmt;
	}
	/**
	 * @设置 金额
	 * @param payAmt
	 */
	public void setPayAmt(Double payAmt) {
	 	this.payAmt = payAmt;
	}
	/**
	 * @return 分录备注
	 */
	public String getMemo() {
	 	return memo;
	}
	/**
	 * @设置 分录备注
	 * @param memo
	 */
	public void setMemo(String memo) {
	 	this.memo = memo;
	}
}