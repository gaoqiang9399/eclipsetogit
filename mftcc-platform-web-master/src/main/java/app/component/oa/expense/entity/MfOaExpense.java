package app.component.oa.expense.entity;
import app.base.BaseDomain;
/**
* Title: MfOaExpense.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Dec 19 09:18:12 CST 2016
* @version：1.0
**/
public class MfOaExpense extends BaseDomain {
	private String expenseId;//
	private String type;//费用类别
	private String isPub;//1公共费用0不是公共费用
	private String name;//费用名称
	private Double amt;//费用金额
	private String applyReason;//申请理由
	private Integer billCount;//发票个数
	private String billNo;//最上一张发票单号
	private String expenseSts;//1未提交2审批中3复核中4审批未通过5已完成
	private String opNo;//申请人员编号
	private String opName;//申请人员姓名
	private String brNo;//申请人员部门编号
	private String brName;//申请人员部门名称
	private String regTime;//登记时间
	private String approveNodeName;//审批节点名称
	private String lstModTime;//申请人最后修改时间
	private String nowSub;
	private String approvePartNo;//当前审批人员编号
	private String approvePartName;//当前审批人员名称
	/**
	 * @return 
	 */
	public String getExpenseId() {
	 	return expenseId;
	}
	/**
	 * @设置 
	 * @param expenseId
	 */
	public void setExpenseId(String expenseId) {
	 	this.expenseId = expenseId;
	}
	/**
	 * @return 费用类别
	 */
	public String getType() {
	 	return type;
	}
	/**
	 * @设置 费用类别
	 * @param type
	 */
	public void setType(String type) {
	 	this.type = type;
	}
	/**
	 * @return 1公共费用0不是公共费用
	 */
	public String getIsPub() {
	 	return isPub;
	}
	/**
	 * @设置 1公共费用0不是公共费用
	 * @param isPub
	 */
	public void setIsPub(String isPub) {
	 	this.isPub = isPub;
	}
	/**
	 * @return 费用名称
	 */
	public String getName() {
	 	return name;
	}
	/**
	 * @设置 费用名称
	 * @param name
	 */
	public void setName(String name) {
	 	this.name = name;
	}
	/**
	 * @return 费用金额
	 */
	public Double getAmt() {
	 	return amt;
	}
	/**
	 * @设置 费用金额
	 * @param amt
	 */
	public void setAmt(Double amt) {
	 	this.amt = amt;
	}
	/**
	 * @return 申请理由
	 */
	public String getApplyReason() {
	 	return applyReason;
	}
	/**
	 * @设置 申请理由
	 * @param applyReason
	 */
	public void setApplyReason(String applyReason) {
	 	this.applyReason = applyReason;
	}
	/**
	 * @return 发票个数
	 */
	public Integer getBillCount() {
	 	return billCount;
	}
	/**
	 * @设置 发票个数
	 * @param billCount
	 */
	public void setBillCount(Integer billCount) {
	 	this.billCount = billCount;
	}
	/**
	 * @return 最上一张发票单号
	 */
	public String getBillNo() {
	 	return billNo;
	}
	/**
	 * @设置 最上一张发票单号
	 * @param billNo
	 */
	public void setBillNo(String billNo) {
	 	this.billNo = billNo;
	}
	/**
	 * @return 1未提交2审批中3复核中4审批未通过5已完成
	 */
	public String getExpenseSts() {
	 	return expenseSts;
	}
	/**
	 * @设置 1未提交2审批中3复核中4审批未通过5已完成
	 * @param expenseSts
	 */
	public void setExpenseSts(String expenseSts) {
	 	this.expenseSts = expenseSts;
	}
	/**
	 * @return 申请人员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 申请人员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 申请人员姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 申请人员姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 申请人员部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 申请人员部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 申请人员部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 申请人员部门名称
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
	 * @return 审批节点名称
	 */
	public String getApproveNodeName() {
	 	return approveNodeName;
	}
	/**
	 * @设置 审批节点名称
	 * @param approveNodeName
	 */
	public void setApproveNodeName(String approveNodeName) {
	 	this.approveNodeName = approveNodeName;
	}
	/**
	 * @return 申请人最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 申请人最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	public String getNowSub() {
		return nowSub;
	}
	public void setNowSub(String nowSub) {
		this.nowSub = nowSub;
	}
	public String getApprovePartNo() {
		return approvePartNo;
	}
	public void setApprovePartNo(String approvePartNo) {
		this.approvePartNo = approvePartNo;
	}
	public String getApprovePartName() {
		return approvePartName;
	}
	public void setApprovePartName(String approvePartName) {
		this.approvePartName = approvePartName;
	}
	
}