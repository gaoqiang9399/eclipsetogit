package app.component.finance.paramset.entity;
import app.base.BaseDomain;
/**
* Title: CwVchRuleDetail.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Mar 10 15:48:51 CST 2017
* @version：1.0
**/
public class CwVchRuleDetail extends BaseDomain {
	private String traceNo;//流水号对应cw_vch_rule_mst.trace_no
	private String uuid;//表的唯一id
	private String curNo;//币种默认RMB
	private String accHrt;//科目控制字
	private String dcInd;//借贷标识1：借；2：贷；
	private String amtNo;//分录编码
	private String txDesc;//描述
	private String txRemark;//摘要
	private String employValue;//员工项目
	private String deptValue;//部门项目
	private String customerValue;//客户项目
	private String itemsNo;//自定义辅助类别
	private String itemsValue;//自定义辅助项目
	private String cashType;//报表项编号
	private String cashTypeName;//报表项名称
	private String kmSts;//现金银行分类01：其他；02：现金银行
	private String payItems;//取值
	private String finBooks;//帐套标识
	
	private String priceTaxType;//价税类型
	private String priceTaxFlag;//是否计税
	private String priceTaxNo;//关联的科目信息
	private String trId;//列表的trId
	
	
	private String accNo;//科目号
	private String accName;//科目名称
	private String dcIndName;//借贷名称

	public CwVchRuleDetail() {
	}
	
	public CwVchRuleDetail(String finBooks) {
		this.finBooks = finBooks;
	}
	/**
	 * @return 流水号对应cw_vch_rule_mst.trace_no
	 */
	public String getTraceNo() {
	 	return traceNo;
	}
	/**
	 * @设置 流水号对应cw_vch_rule_mst.trace_no
	 * @param traceNo
	 */
	public void setTraceNo(String traceNo) {
	 	this.traceNo = traceNo;
	}
	/**
	 * @return 流水笔次
	 */
	
	/**
	 * @return 币种默认RMB
	 */
	public String getCurNo() {
	 	return curNo;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @设置 币种默认RMB
	 * @param curNo
	 */
	public void setCurNo(String curNo) {
	 	this.curNo = curNo;
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
	 * @return 借贷标识1：借；2：贷；
	 */
	public String getDcInd() {
	 	return dcInd;
	}
	/**
	 * @设置 借贷标识1：借；2：贷；
	 * @param dcInd
	 */
	public void setDcInd(String dcInd) {
	 	this.dcInd = dcInd;
	}
	/**
	 * @return 分录编码
	 */
	public String getAmtNo() {
	 	return amtNo;
	}
	/**
	 * @设置 分录编码
	 * @param amtNo
	 */
	public void setAmtNo(String amtNo) {
	 	this.amtNo = amtNo;
	}
	/**
	 * @return 描述
	 */
	public String getTxDesc() {
	 	return txDesc;
	}
	/**
	 * @设置 描述
	 * @param txDesc
	 */
	public void setTxDesc(String txDesc) {
	 	this.txDesc = txDesc;
	}
	/**
	 * @return 摘要
	 */
	public String getTxRemark() {
	 	return txRemark;
	}
	/**
	 * @设置 摘要
	 * @param txRemark
	 */
	public void setTxRemark(String txRemark) {
	 	this.txRemark = txRemark;
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
	 * @return 报表项编号
	 */
	public String getCashType() {
	 	return cashType;
	}
	/**
	 * @设置 报表项编号
	 * @param cashType
	 */
	public void setCashType(String cashType) {
	 	this.cashType = cashType;
	}
	/**
	 * @return 报表项名称
	 */
	public String getCashTypeName() {
	 	return cashTypeName;
	}
	/**
	 * @设置 报表项名称
	 * @param cashTypeName
	 */
	public void setCashTypeName(String cashTypeName) {
	 	this.cashTypeName = cashTypeName;
	}
	/**
	 * @return 现金银行分类01：其他；02：现金银行
	 */
	public String getKmSts() {
	 	return kmSts;
	}
	/**
	 * @设置 现金银行分类01：其他；02：现金银行
	 * @param kmSts
	 */
	public void setKmSts(String kmSts) {
	 	this.kmSts = kmSts;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getDcIndName() {
		return dcIndName;
	}
	public void setDcIndName(String dcIndName) {
		this.dcIndName = dcIndName;
	}
	public String getPayItems() {
		return payItems;
	}
	public void setPayItems(String payItems) {
		this.payItems = payItems;
	}
	public String getFinBooks() {
		return finBooks;
	}
	public void setFinBooks(String finBooks) {
		this.finBooks = finBooks;
	}

	public String getPriceTaxType() {
		return priceTaxType;
	}

	public void setPriceTaxType(String priceTaxType) {
		this.priceTaxType = priceTaxType;
	}

	public String getPriceTaxFlag() {
		return priceTaxFlag;
	}

	public void setPriceTaxFlag(String priceTaxFlag) {
		this.priceTaxFlag = priceTaxFlag;
	}

	public String getPriceTaxNo() {
		return priceTaxNo;
	}

	public void setPriceTaxNo(String priceTaxNo) {
		this.priceTaxNo = priceTaxNo;
	}

	public String getTrId() {
		return trId;
	}

	public void setTrId(String trId) {
		this.trId = trId;
	}
	
	
}