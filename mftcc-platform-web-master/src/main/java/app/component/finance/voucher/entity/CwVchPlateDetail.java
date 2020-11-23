package app.component.finance.voucher.entity;
import app.base.BaseDomain;
/**
* Title: CwVchPlateDetail.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Mar 07 11:06:15 CST 2017
* @version：1.0
**/
public class CwVchPlateDetail extends BaseDomain {
	private String finBooks;
	private String plateNo;//模版编号对应cw_vch_plate_mst.plate_no
	private String sort;//排序
	private String txDesc;//摘要
	private String accHrt;//科目控制字
	private String employValue;//员工项目
	private String deptValue;//部门项目
	private String customerValue;//客户项目
	private String itemsNo;//自定义辅助类别
	private String itemsValue;//自定义辅助项目
	private String drAmt;//借方金额
	private String crAmt;//贷方金额

	/**
	 * @return 模版编号对应cw_vch_plate_mst.plate_no
	 */
	public String getPlateNo() {
	 	return plateNo;
	}
	/**
	 * @设置 模版编号对应cw_vch_plate_mst.plate_no
	 * @param plateNo
	 */
	public void setPlateNo(String plateNo) {
	 	this.plateNo = plateNo;
	}
	/**
	 * @return 排序
	 */
	public String getSort() {
	 	return sort;
	}
	/**
	 * @设置 排序
	 * @param sort
	 */
	public void setSort(String sort) {
	 	this.sort = sort;
	}
	/**
	 * @return 摘要
	 */
	public String getTxDesc() {
	 	return txDesc;
	}
	/**
	 * @设置 摘要
	 * @param txDesc
	 */
	public void setTxDesc(String txDesc) {
	 	this.txDesc = txDesc;
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
	 * @return 员工项目
	 */
	public String getEmployValue() {
	 	return employValue;
	}
	/**
	 * @设置 员工项目
	 * @param employValue
	 */
	public void setEmployValue(String employValue) {
	 	this.employValue = employValue;
	}
	/**
	 * @return 部门项目
	 */
	public String getDeptValue() {
	 	return deptValue;
	}
	/**
	 * @设置 部门项目
	 * @param deptValue
	 */
	public void setDeptValue(String deptValue) {
	 	this.deptValue = deptValue;
	}
	/**
	 * @return 客户项目
	 */
	public String getCustomerValue() {
	 	return customerValue;
	}
	/**
	 * @设置 客户项目
	 * @param customerValue
	 */
	public void setCustomerValue(String customerValue) {
	 	this.customerValue = customerValue;
	}
	/**
	 * @return 自定义辅助类别
	 */
	public String getItemsNo() {
	 	return itemsNo;
	}
	/**
	 * @设置 自定义辅助类别
	 * @param itemsNo
	 */
	public void setItemsNo(String itemsNo) {
	 	this.itemsNo = itemsNo;
	}
	/**
	 * @return 自定义辅助项目
	 */
	public String getItemsValue() {
	 	return itemsValue;
	}
	/**
	 * @设置 自定义辅助项目
	 * @param itemsValue
	 */
	public void setItemsValue(String itemsValue) {
	 	this.itemsValue = itemsValue;
	}
	/**
	 * @return 借方金额
	 */
	public String getDrAmt() {
	 	return drAmt;
	}
	/**
	 * @设置 借方金额
	 * @param drAmt
	 */
	public void setDrAmt(String drAmt) {
	 	this.drAmt = drAmt;
	}
	/**
	 * @return 贷方金额
	 */
	public String getCrAmt() {
	 	return crAmt;
	}
	/**
	 * @设置 贷方金额
	 * @param crAmt
	 */
	public void setCrAmt(String crAmt) {
	 	this.crAmt = crAmt;
	}
	public String getFinBooks() {
		return finBooks;
	}
	public void setFinBooks(String finBooks) {
		this.finBooks = finBooks;
	}
	
}