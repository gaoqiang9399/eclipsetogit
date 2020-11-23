package app.component.pss.fund.entity;
import app.base.BaseDomain;
/**
* Title: PssOtherRecDetailBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Nov 15 15:14:07 CST 2017
* @version：1.0
**/
public class PssOtherRecDetailBill extends BaseDomain {
	private String otherRecDetailId;//其他收入单明细ID
	private String otherRecNo;//其他收入单据编号
	private Integer sequence;//序号
	private String buyType;//收入类别
	private Double recAmt;//金额
	private String memo;//分录备注

	/**
	 * @return 其他收入单明细ID
	 */
	public String getOtherRecDetailId() {
	 	return otherRecDetailId;
	}
	/**
	 * @设置 其他收入单明细ID
	 * @param otherRecDetailId
	 */
	public void setOtherRecDetailId(String otherRecDetailId) {
	 	this.otherRecDetailId = otherRecDetailId;
	}
	/**
	 * @return 其他收入单据编号
	 */
	public String getOtherRecNo() {
	 	return otherRecNo;
	}
	/**
	 * @设置 其他收入单据编号
	 * @param otherRecNo
	 */
	public void setOtherRecNo(String otherRecNo) {
	 	this.otherRecNo = otherRecNo;
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
	 * @return 收入类别
	 */
	public String getBuyType() {
	 	return buyType;
	}
	/**
	 * @设置 收入类别
	 * @param buyType
	 */
	public void setBuyType(String buyType) {
	 	this.buyType = buyType;
	}
	/**
	 * @return 金额
	 */
	public Double getRecAmt() {
	 	return recAmt;
	}
	/**
	 * @设置 金额
	 * @param recAmt
	 */
	public void setRecAmt(Double recAmt) {
	 	this.recAmt = recAmt;
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