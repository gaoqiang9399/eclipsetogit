package app.component.accnt.entity;
import app.base.BaseDomain;
/**
* Title: MfAccntRepayDetail.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed May 25 18:06:12 CST 2016
* @version：1.0
**/
public class MfAccntRepayDetail extends BaseDomain {
	private String repayDetailId;//冲销明细ID
	private String repayId;//历史记录ID
	private String pactId;//合同号
	private String transferId;//应收账款转让编号
	private String cusNo;//还款客户编号
	private String cusName;//客户名称
	private Double accntBal;//账款余额
	private Double returnAccntAmt;//冲销金额
	private String optType;//操作类型：1--回款 2--账款调整（不走回款入口）
	private String repayDate;//冲销日期
	private String repayType;//还款类型：1--正常 2--逾期 3--提前
	private String isAdvance;//垫付标志：0--否 1--是
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String brNo;//部门编号
	private String brName;//部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间

	/**
	 * @return 冲销明细ID
	 */
	public String getRepayDetailId() {
	 	return repayDetailId;
	}
	/**
	 * @设置 冲销明细ID
	 * @param repayDetailId
	 */
	public void setRepayDetailId(String repayDetailId) {
	 	this.repayDetailId = repayDetailId;
	}
	/**
	 * @return 历史记录ID
	 */
	public String getRepayId() {
	 	return repayId;
	}
	/**
	 * @设置 历史记录ID
	 * @param repayId
	 */
	public void setRepayId(String repayId) {
	 	this.repayId = repayId;
	}
	/**
	 * @return 合同号
	 */
	public String getPactId() {
	 	return pactId;
	}
	/**
	 * @设置 合同号
	 * @param pactId
	 */
	public void setPactId(String pactId) {
	 	this.pactId = pactId;
	}
	/**
	 * @return 应收账款转让编号
	 */
	public String getTransferId() {
	 	return transferId;
	}
	/**
	 * @设置 应收账款转让编号
	 * @param transferId
	 */
	public void setTransferId(String transferId) {
	 	this.transferId = transferId;
	}
	/**
	 * @return 还款客户编号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 还款客户编号
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 客户名称
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 客户名称
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 账款余额
	 */
	public Double getAccntBal() {
	 	return accntBal;
	}
	/**
	 * @设置 账款余额
	 * @param accntBal
	 */
	public void setAccntBal(Double accntBal) {
	 	this.accntBal = accntBal;
	}
	/**
	 * @return 冲销金额
	 */
	public Double getReturnAccntAmt() {
	 	return returnAccntAmt;
	}
	/**
	 * @设置 冲销金额
	 * @param returnAccntAmt
	 */
	public void setReturnAccntAmt(Double returnAccntAmt) {
	 	this.returnAccntAmt = returnAccntAmt;
	}
	/**
	 * @return 操作类型：1--回款 2--账款调整（不走回款入口）
	 */
	public String getOptType() {
	 	return optType;
	}
	/**
	 * @设置 操作类型：1--回款 2--账款调整（不走回款入口）
	 * @param optType
	 */
	public void setOptType(String optType) {
	 	this.optType = optType;
	}
	/**
	 * @return 冲销日期
	 */
	public String getRepayDate() {
	 	return repayDate;
	}
	/**
	 * @设置 冲销日期
	 * @param repayDate
	 */
	public void setRepayDate(String repayDate) {
	 	this.repayDate = repayDate;
	}
	/**
	 * @return 还款类型：1--正常 2--逾期 3--提前
	 */
	public String getRepayType() {
	 	return repayType;
	}
	/**
	 * @设置 还款类型：1--正常 2--逾期 3--提前
	 * @param repayType
	 */
	public void setRepayType(String repayType) {
	 	this.repayType = repayType;
	}
	/**
	 * @return 垫付标志：0--否 1--是
	 */
	public String getIsAdvance() {
	 	return isAdvance;
	}
	/**
	 * @设置 垫付标志：0--否 1--是
	 * @param isAdvance
	 */
	public void setIsAdvance(String isAdvance) {
	 	this.isAdvance = isAdvance;
	}
	/**
	 * @return 操作员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作员名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 操作员名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
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