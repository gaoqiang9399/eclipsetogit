package app.component.finance.voucher.entity;
import app.base.BaseDomain;
/**
* Title: CwVoucherAssist.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Mar 02 14:09:11 CST 2017
* @version：1.0
**/
public class CwVoucherAssist extends BaseDomain {
	private String uid;//唯一编号
	private String finBooks;//帐套标识
	private String voucherNo;//凭证编号
	private String detialId;//分录uid
	private String voucherDate;//凭证日期
	private String weeks;//凭证周期
	private String accHrt;//科目控制字
	private String dcInd;//借贷标志1：借；2：贷；
	private String txAmt;//发生额
	private String txType;//辅助核算类别编号
	private String typeName;//辅助核算类别名称
	private String txCode;//辅助核算值编号
	private String txName;//辅助核算值名称
	private String txSource;//来源0：初始化；1：凭证
	private String occTime;//时间戳最后一次修改日期
	
	//数据库中没有这些字段
	private String employee;//员工
	private String employeeNo;
	private String department;//部门
	private String departmentNo;//部门
	private String customer;//客户
	private String customerNo;//客户
	private String defineself;//自定义项
	private String defineselfNo;//自定义项
	private String accName;//科目名称
	private String upAccHrt;//上级科目控制字
	
	/**
	 * @return 唯一编号
	 */
	public String getUid() {
	 	return uid;
	}
	/**
	 * @设置 唯一编号
	 * @param uid
	 */
	public void setUid(String uid) {
	 	this.uid = uid;
	}
	/**
	 * @return 帐套标识
	 */
	public String getFinBooks() {
	 	return finBooks;
	}
	/**
	 * @设置 帐套标识
	 * @param finBooks
	 */
	public void setFinBooks(String finBooks) {
	 	this.finBooks = finBooks;
	}
	/**
	 * @return 凭证编号
	 */
	public String getVoucherNo() {
	 	return voucherNo;
	}
	/**
	 * @设置 凭证编号
	 * @param voucherNo
	 */
	public void setVoucherNo(String voucherNo) {
	 	this.voucherNo = voucherNo;
	}
	/**
	 * @return 凭证日期
	 */
	public String getVoucherDate() {
	 	return voucherDate;
	}
	/**
	 * @设置 凭证日期
	 * @param voucherDate
	 */
	public void setVoucherDate(String voucherDate) {
	 	this.voucherDate = voucherDate;
	}
	/**
	 * @return 凭证周期
	 */
	public String getWeeks() {
	 	return weeks;
	}
	/**
	 * @设置 凭证周期
	 * @param weeks
	 */
	public void setWeeks(String weeks) {
	 	this.weeks = weeks;
	}
	/**
	 * @return 科目控制字
	 */
	public String getAccHrt() {
	 	return accHrt;
	}
	/**
	 * @设置 科目控制字
	 * @param accHrt
	 */
	public void setAccHrt(String accHrt) {
	 	this.accHrt = accHrt;
	}
	/**
	 * @return 借贷标志1：借；2：贷；
	 */
	public String getDcInd() {
	 	return dcInd;
	}
	/**
	 * @设置 借贷标志1：借；2：贷；
	 * @param dcInd
	 */
	public void setDcInd(String dcInd) {
	 	this.dcInd = dcInd;
	}
	/**
	 * @return 发生额
	 */
	public String getTxAmt() {
	 	return txAmt;
	}
	/**
	 * @设置 发生额
	 * @param txAmt
	 */
	public void setTxAmt(String txAmt) {
	 	this.txAmt = txAmt;
	}
	/**
	 * @return 辅助核算类别编号
	 */
	public String getTxType() {
	 	return txType;
	}
	/**
	 * @设置 辅助核算类别编号
	 * @param txType
	 */
	public void setTxType(String txType) {
	 	this.txType = txType;
	}
	/**
	 * @return 辅助核算类别名称
	 */
	public String getTypeName() {
	 	return typeName;
	}
	/**
	 * @设置 辅助核算类别名称
	 * @param typeName
	 */
	public void setTypeName(String typeName) {
	 	this.typeName = typeName;
	}
	/**
	 * @return 辅助核算值编号
	 */
	public String getTxCode() {
	 	return txCode;
	}
	/**
	 * @设置 辅助核算值编号
	 * @param txCode
	 */
	public void setTxCode(String txCode) {
	 	this.txCode = txCode;
	}
	/**
	 * @return 辅助核算值名称
	 */
	public String getTxName() {
	 	return txName;
	}
	/**
	 * @设置 辅助核算值名称
	 * @param txName
	 */
	public void setTxName(String txName) {
	 	this.txName = txName;
	}
	/**
	 * @return 来源0：初始化；1：凭证
	 */
	public String getTxSource() {
	 	return txSource;
	}
	/**
	 * @设置 来源0：初始化；1：凭证
	 * @param txSource
	 */
	public void setTxSource(String txSource) {
	 	this.txSource = txSource;
	}
	/**
	 * @return 时间戳最后一次修改日期
	 */
	public String getOccTime() {
	 	return occTime;
	}
	/**
	 * @设置 时间戳最后一次修改日期
	 * @param occTime
	 */
	public void setOccTime(String occTime) {
	 	this.occTime = occTime;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDepartmentNo() {
		return departmentNo;
	}
	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getDefineself() {
		return defineself;
	}
	public void setDefineself(String defineself) {
		this.defineself = defineself;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getDefineselfNo() {
		return defineselfNo;
	}
	public void setDefineselfNo(String defineselfNo) {
		this.defineselfNo = defineselfNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getDetialId() {
		return detialId;
	}
	public void setDetialId(String detialId) {
		this.detialId = detialId;
	}
	public String getUpAccHrt() {
		return upAccHrt;
	}
	public void setUpAccHrt(String upAccHrt) {
		this.upAccHrt = upAccHrt;
	}
	
}