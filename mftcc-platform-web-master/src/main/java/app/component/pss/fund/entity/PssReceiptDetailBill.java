package app.component.pss.fund.entity;
import app.base.BaseDomain;
/**
* Title: PssReceiptDetailBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Sep 20 17:57:27 CST 2017
* @version：1.0
**/
public class PssReceiptDetailBill extends BaseDomain {
	private String receiptDetailId;//收款单据明细ID
	private String receiptNo;//收款单据编号
	private Integer sequence;//序号
	private String clearanceAccountId;//结算账户ID
	private String clearanceAccountName;//结算账户名称
	private Double recAmt;//收款金额
	private String clearanceAccountType;//结算方式
	private String clearanceAccountNum;//结算号
	private String memo;//分录备注

	/**
	 * @return 收款单据明细ID
	 */
	public String getReceiptDetailId() {
	 	return receiptDetailId;
	}
	/**
	 * @设置 收款单据明细ID
	 * @param receiptDetailId
	 */
	public void setReceiptDetailId(String receiptDetailId) {
	 	this.receiptDetailId = receiptDetailId;
	}
	/**
	 * @return 收款单据编号
	 */
	public String getReceiptNo() {
	 	return receiptNo;
	}
	/**
	 * @设置 收款单据编号
	 * @param receiptNo
	 */
	public void setReceiptNo(String receiptNo) {
	 	this.receiptNo = receiptNo;
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
	 * @return 结算账户ID
	 */
	public String getClearanceAccountId() {
	 	return clearanceAccountId;
	}
	/**
	 * @设置 结算账户ID
	 * @param clearanceAccountId
	 */
	public void setClearanceAccountId(String clearanceAccountId) {
	 	this.clearanceAccountId = clearanceAccountId;
	}
	/**
	 * @return 结算账户名称
	 */
	public String getClearanceAccountName() {
	 	return clearanceAccountName;
	}
	/**
	 * @设置 结算账户名称
	 * @param clearanceAccountName
	 */
	public void setClearanceAccountName(String clearanceAccountName) {
	 	this.clearanceAccountName = clearanceAccountName;
	}
	/**
	 * @return 收款金额
	 */
	public Double getRecAmt() {
	 	return recAmt;
	}
	/**
	 * @设置 收款金额
	 * @param recAmt
	 */
	public void setRecAmt(Double recAmt) {
	 	this.recAmt = recAmt;
	}
	/**
	 * @return 结算方式
	 */
	public String getClearanceAccountType() {
	 	return clearanceAccountType;
	}
	/**
	 * @设置 结算方式
	 * @param clearanceAccountType
	 */
	public void setClearanceAccountType(String clearanceAccountType) {
	 	this.clearanceAccountType = clearanceAccountType;
	}
	/**
	 * @return 结算号
	 */
	public String getClearanceAccountNum() {
	 	return clearanceAccountNum;
	}
	/**
	 * @设置 结算号
	 * @param clearanceAccountNum
	 */
	public void setClearanceAccountNum(String clearanceAccountNum) {
	 	this.clearanceAccountNum = clearanceAccountNum;
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