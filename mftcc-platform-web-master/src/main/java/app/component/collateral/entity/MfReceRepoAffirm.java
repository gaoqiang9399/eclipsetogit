package app.component.collateral.entity;
import app.base.BaseDomain;
/**
* Title: MfReceRepoAffirm.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri May 12 15:06:35 CST 2017
* @version：1.0
**/
public class MfReceRepoAffirm extends BaseDomain {
	private String repoAffirmId;//确认编号
	private String busPleId;//业务抵质押编号
	private Double fincPrepayBal;//融资预付款余额
	private Double accruedInterest;//应付未付利息
	private Double reduceAmt;//减免金额
	private Double receiptAmt;//实收金额
	private String receiptDate;//收款日期
	private String remark;//确认说明
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间

	/**
	 * @return 确认编号
	 */
	public String getRepoAffirmId() {
	 	return repoAffirmId;
	}
	/**
	 * @设置 确认编号
	 * @param repoAffirmId
	 */
	public void setRepoAffirmId(String repoAffirmId) {
	 	this.repoAffirmId = repoAffirmId;
	}
	/**
	 * @return 业务抵质押编号
	 */
	public String getBusPleId() {
	 	return busPleId;
	}
	/**
	 * @设置 业务抵质押编号
	 * @param busPleId
	 */
	public void setBusPleId(String busPleId) {
	 	this.busPleId = busPleId;
	}
	/**
	 * @return 融资预付款余额
	 */
	public Double getFincPrepayBal() {
	 	return fincPrepayBal;
	}
	/**
	 * @设置 融资预付款余额
	 * @param fincPrepayBal
	 */
	public void setFincPrepayBal(Double fincPrepayBal) {
	 	this.fincPrepayBal = fincPrepayBal;
	}
	/**
	 * @return 应付未付利息
	 */
	public Double getAccruedInterest() {
	 	return accruedInterest;
	}
	/**
	 * @设置 应付未付利息
	 * @param accruedInterest
	 */
	public void setAccruedInterest(Double accruedInterest) {
	 	this.accruedInterest = accruedInterest;
	}
	/**
	 * @return 减免金额
	 */
	public Double getReduceAmt() {
	 	return reduceAmt;
	}
	/**
	 * @设置 减免金额
	 * @param reduceAmt
	 */
	public void setReduceAmt(Double reduceAmt) {
	 	this.reduceAmt = reduceAmt;
	}
	/**
	 * @return 实收金额
	 */
	public Double getReceiptAmt() {
	 	return receiptAmt;
	}
	/**
	 * @设置 实收金额
	 * @param receiptAmt
	 */
	public void setReceiptAmt(Double receiptAmt) {
	 	this.receiptAmt = receiptAmt;
	}
	/**
	 * @return 收款日期
	 */
	public String getReceiptDate() {
	 	return receiptDate;
	}
	/**
	 * @设置 收款日期
	 * @param receiptDate
	 */
	public void setReceiptDate(String receiptDate) {
	 	this.receiptDate = receiptDate;
	}
	/**
	 * @return 确认说明
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 确认说明
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 登记人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 登记人姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记部门名称
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