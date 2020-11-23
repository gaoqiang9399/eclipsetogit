package app.component.pss.sales.entity;
import app.base.BaseDomain;
/**
* Title: PssSaleBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Sep 05 15:01:53 CST 2017
* @version：1.0
**/
public class PssSaleBill extends BaseDomain {
	private String saleBillId;//主键
	private String saleBillNo;//销货单号
	private String cusNo;//客户号
	private String salerNo;//销售人员编号
	private String billDate;//单据日期
	private String auditStsed;//是否审核(1-已审核 0-未审核)
	private String saleOrderNo;//销货订单号
	private Double amount;//销货金额
	private Double quantity;//数量
	private Double discountRate;//优惠率(%)
	private Double discountAmount;//收款优惠
	private Double discountAfterAmount;//优惠后金额
	private Double customerCost;//客户承担费用
	private Double thisReceipt;//本次收款
	private Double received;//已收款
	private String settlementAccount;//结算账户
	private Double thisDebt;//本次欠款
	private Double totalDebt;//总欠款
	private Double saleExpense;//销售费用
	private String receivedState;//收款状态(01-未收款 02-部分收款 03-全部收款)
	private String isAccount;//是否生成凭证(1-是 0-否)
	private String voucherNo;//凭证编号
	private Integer printTimes;//打印次数
	private Double invoicedAmount;//已开票金额
	private Double notInvoicedAmount;//未开票金额
	private Double invoiceState;//开票状态
	private String memo;//备注
	private String regOpNo;//制单人编号
	private String regOpName;//制单人名称
	private String regBrNo;//制单人机构编号
	private String regBrName;//制单人机构名称
	private String regTime;//制单时间
	private String auditOpNo;//审核人编号
	private String auditOpName;//审核人名称
	private String auditBrNo;//审核人机构编号
	private String auditBrName;//审核人机构名称
	private String auditTime;//审核时间
	private String lstModOpNo;//最后修改人编号
	private String lstModOpName;//最后修改人名称
	private String lstModBrNo;//最后修改人机构编号
	private String lstModBrName;//最后修改人机构名称
	private String lstModTime;//最后修改时间
	
	private String billStartDate;//单据开始日期
	private String billEndDate;//单据结束日期
	private String cusName;//客户名称
	private String salerName;//销售人员名称
	
	private String docBizNo;//上传文档关联编号
	
	/**
	 * @return 主键
	 */
	public String getSaleBillId() {
	 	return saleBillId;
	}
	/**
	 * @设置 主键
	 * @param saleBillId
	 */
	public void setSaleBillId(String saleBillId) {
	 	this.saleBillId = saleBillId;
	}
	/**
	 * @return 销货单号
	 */
	public String getSaleBillNo() {
	 	return saleBillNo;
	}
	/**
	 * @设置 销货单号
	 * @param saleBillNo
	 */
	public void setSaleBillNo(String saleBillNo) {
	 	this.saleBillNo = saleBillNo;
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
	 * @return 销售人员编号
	 */
	public String getSalerNo() {
	 	return salerNo;
	}
	/**
	 * @设置 销售人员编号
	 * @param salerNo
	 */
	public void setSalerNo(String salerNo) {
	 	this.salerNo = salerNo;
	}
	/**
	 * @return 单据日期
	 */
	public String getBillDate() {
	 	return billDate;
	}
	/**
	 * @设置 单据日期
	 * @param billDate
	 */
	public void setBillDate(String billDate) {
	 	this.billDate = billDate;
	}
	/**
	 * @return 是否审核(1-已审核 0-未审核)
	 */
	public String getAuditStsed() {
	 	return auditStsed;
	}
	/**
	 * @设置 是否审核(1-已审核 0-未审核)
	 * @param auditStsed
	 */
	public void setAuditStsed(String auditStsed) {
	 	this.auditStsed = auditStsed;
	}
	/**
	 * @return 销货订单号
	 */
	public String getSaleOrderNo() {
	 	return saleOrderNo;
	}
	/**
	 * @设置 销货订单号
	 * @param saleOrderNo
	 */
	public void setSaleOrderNo(String saleOrderNo) {
	 	this.saleOrderNo = saleOrderNo;
	}
	/**
	 * @return 销货金额
	 */
	public Double getAmount() {
	 	return amount;
	}
	/**
	 * @设置 销货金额
	 * @param amount
	 */
	public void setAmount(Double amount) {
	 	this.amount = amount;
	}
	/**
	 * @return 数量
	 */
	public Double getQuantity() {
	 	return quantity;
	}
	/**
	 * @设置 数量
	 * @param quantity
	 */
	public void setQuantity(Double quantity) {
	 	this.quantity = quantity;
	}
	/**
	 * @return 优惠率(%)
	 */
	public Double getDiscountRate() {
	 	return discountRate;
	}
	/**
	 * @设置 优惠率(%)
	 * @param discountRate
	 */
	public void setDiscountRate(Double discountRate) {
	 	this.discountRate = discountRate;
	}
	/**
	 * @return 收款优惠
	 */
	public Double getDiscountAmount() {
	 	return discountAmount;
	}
	/**
	 * @设置 收款优惠
	 * @param discountAmount
	 */
	public void setDiscountAmount(Double discountAmount) {
	 	this.discountAmount = discountAmount;
	}
	/**
	 * @return 优惠后金额
	 */
	public Double getDiscountAfterAmount() {
	 	return discountAfterAmount;
	}
	/**
	 * @设置 优惠后金额
	 * @param discountAfterAmount
	 */
	public void setDiscountAfterAmount(Double discountAfterAmount) {
	 	this.discountAfterAmount = discountAfterAmount;
	}
	/**
	 * @return 客户承担费用
	 */
	public Double getCustomerCost() {
	 	return customerCost;
	}
	/**
	 * @设置 客户承担费用
	 * @param customerCost
	 */
	public void setCustomerCost(Double customerCost) {
	 	this.customerCost = customerCost;
	}
	/**
	 * @return 本次收款
	 */
	public Double getThisReceipt() {
	 	return thisReceipt;
	}
	/**
	 * @设置 本次收款
	 * @param thisReceipt
	 */
	public void setThisReceipt(Double thisReceipt) {
	 	this.thisReceipt = thisReceipt;
	}
	/**
	 * @return 已收款
	 */
	public Double getReceived() {
	 	return received;
	}
	/**
	 * @设置 已收款
	 * @param received
	 */
	public void setReceived(Double received) {
	 	this.received = received;
	}
	/**
	 * @return 结算账户
	 */
	public String getSettlementAccount() {
	 	return settlementAccount;
	}
	/**
	 * @设置 结算账户
	 * @param settlementAccount
	 */
	public void setSettlementAccount(String settlementAccount) {
	 	this.settlementAccount = settlementAccount;
	}
	/**
	 * @return 本次欠款
	 */
	public Double getThisDebt() {
	 	return thisDebt;
	}
	/**
	 * @设置 本次欠款
	 * @param thisDebt
	 */
	public void setThisDebt(Double thisDebt) {
	 	this.thisDebt = thisDebt;
	}
	/**
	 * @return 总欠款
	 */
	public Double getTotalDebt() {
	 	return totalDebt;
	}
	/**
	 * @设置 总欠款
	 * @param totalDebt
	 */
	public void setTotalDebt(Double totalDebt) {
	 	this.totalDebt = totalDebt;
	}
	/**
	 * @return 销售费用
	 */
	public Double getSaleExpense() {
	 	return saleExpense;
	}
	/**
	 * @设置 销售费用
	 * @param saleExpense
	 */
	public void setSaleExpense(Double saleExpense) {
	 	this.saleExpense = saleExpense;
	}
	/**
	 * @return 收款状态(01-未收款 02-部分收款 03-全部收款)
	 */
	public String getReceivedState() {
	 	return receivedState;
	}
	/**
	 * @设置 收款状态(01-未收款 02-部分收款 03-全部收款)
	 * @param receivedState
	 */
	public void setReceivedState(String receivedState) {
	 	this.receivedState = receivedState;
	}
	/**
	 * @return 是否生成凭证(1-是 0-否)
	 */
	public String getIsAccount() {
	 	return isAccount;
	}
	/**
	 * @设置 是否生成凭证(1-是 0-否)
	 * @param isAccount
	 */
	public void setIsAccount(String isAccount) {
	 	this.isAccount = isAccount;
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
	 * @return 打印次数
	 */
	public Integer getPrintTimes() {
	 	return printTimes;
	}
	/**
	 * @设置 打印次数
	 * @param printTimes
	 */
	public void setPrintTimes(Integer printTimes) {
	 	this.printTimes = printTimes;
	}
	/**
	 * @return 已开票金额
	 */
	public Double getInvoicedAmount() {
	 	return invoicedAmount;
	}
	/**
	 * @设置 已开票金额
	 * @param invoicedAmount
	 */
	public void setInvoicedAmount(Double invoicedAmount) {
	 	this.invoicedAmount = invoicedAmount;
	}
	/**
	 * @return 未开票金额
	 */
	public Double getNotInvoicedAmount() {
	 	return notInvoicedAmount;
	}
	/**
	 * @设置 未开票金额
	 * @param notInvoicedAmount
	 */
	public void setNotInvoicedAmount(Double notInvoicedAmount) {
	 	this.notInvoicedAmount = notInvoicedAmount;
	}
	/**
	 * @return 开票状态
	 */
	public Double getInvoiceState() {
	 	return invoiceState;
	}
	/**
	 * @设置 开票状态
	 * @param invoiceState
	 */
	public void setInvoiceState(Double invoiceState) {
	 	this.invoiceState = invoiceState;
	}
	/**
	 * @return 备注
	 */
	public String getMemo() {
	 	return memo;
	}
	/**
	 * @设置 备注
	 * @param memo
	 */
	public void setMemo(String memo) {
	 	this.memo = memo;
	}
	/**
	 * @return 制单人编号
	 */
	public String getRegOpNo() {
	 	return regOpNo;
	}
	/**
	 * @设置 制单人编号
	 * @param regOpNo
	 */
	public void setRegOpNo(String regOpNo) {
	 	this.regOpNo = regOpNo;
	}
	/**
	 * @return 制单人名称
	 */
	public String getRegOpName() {
	 	return regOpName;
	}
	/**
	 * @设置 制单人名称
	 * @param regOpName
	 */
	public void setRegOpName(String regOpName) {
	 	this.regOpName = regOpName;
	}
	/**
	 * @return 制单人机构编号
	 */
	public String getRegBrNo() {
	 	return regBrNo;
	}
	/**
	 * @设置 制单人机构编号
	 * @param regBrNo
	 */
	public void setRegBrNo(String regBrNo) {
	 	this.regBrNo = regBrNo;
	}
	/**
	 * @return 制单人机构名称
	 */
	public String getRegBrName() {
	 	return regBrName;
	}
	/**
	 * @设置 制单人机构名称
	 * @param regBrName
	 */
	public void setRegBrName(String regBrName) {
	 	this.regBrName = regBrName;
	}
	/**
	 * @return 制单时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 制单时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 审核人编号
	 */
	public String getAuditOpNo() {
	 	return auditOpNo;
	}
	/**
	 * @设置 审核人编号
	 * @param auditOpNo
	 */
	public void setAuditOpNo(String auditOpNo) {
	 	this.auditOpNo = auditOpNo;
	}
	/**
	 * @return 审核人名称
	 */
	public String getAuditOpName() {
	 	return auditOpName;
	}
	/**
	 * @设置 审核人名称
	 * @param auditOpName
	 */
	public void setAuditOpName(String auditOpName) {
	 	this.auditOpName = auditOpName;
	}
	/**
	 * @return 审核人机构编号
	 */
	public String getAuditBrNo() {
	 	return auditBrNo;
	}
	/**
	 * @设置 审核人机构编号
	 * @param auditBrNo
	 */
	public void setAuditBrNo(String auditBrNo) {
	 	this.auditBrNo = auditBrNo;
	}
	/**
	 * @return 审核人机构名称
	 */
	public String getAuditBrName() {
	 	return auditBrName;
	}
	/**
	 * @设置 审核人机构名称
	 * @param auditBrName
	 */
	public void setAuditBrName(String auditBrName) {
	 	this.auditBrName = auditBrName;
	}
	/**
	 * @return 审核时间
	 */
	public String getAuditTime() {
	 	return auditTime;
	}
	/**
	 * @设置 审核时间
	 * @param auditTime
	 */
	public void setAuditTime(String auditTime) {
	 	this.auditTime = auditTime;
	}
	/**
	 * @return 最后修改人编号
	 */
	public String getLstModOpNo() {
	 	return lstModOpNo;
	}
	/**
	 * @设置 最后修改人编号
	 * @param lstModOpNo
	 */
	public void setLstModOpNo(String lstModOpNo) {
	 	this.lstModOpNo = lstModOpNo;
	}
	/**
	 * @return 最后修改人名称
	 */
	public String getLstModOpName() {
	 	return lstModOpName;
	}
	/**
	 * @设置 最后修改人名称
	 * @param lstModOpName
	 */
	public void setLstModOpName(String lstModOpName) {
	 	this.lstModOpName = lstModOpName;
	}
	/**
	 * @return 最后修改人机构编号
	 */
	public String getLstModBrNo() {
	 	return lstModBrNo;
	}
	/**
	 * @设置 最后修改人机构编号
	 * @param lstModBrNo
	 */
	public void setLstModBrNo(String lstModBrNo) {
	 	this.lstModBrNo = lstModBrNo;
	}
	/**
	 * @return 最后修改人机构名称
	 */
	public String getLstModBrName() {
	 	return lstModBrName;
	}
	/**
	 * @设置 最后修改人机构名称
	 * @param lstModBrName
	 */
	public void setLstModBrName(String lstModBrName) {
	 	this.lstModBrName = lstModBrName;
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
	/**
	 * @return 单据开始日期
	 */
	public String getBillStartDate() {
		return billStartDate;
	}
	/**
	 * @设置 单据开始日期
	 * @param billStartDate
	 */
	public void setBillStartDate(String billStartDate) {
		this.billStartDate = billStartDate;
	}
	/**
	 * @return 单据结束日期
	 */
	public String getBillEndDate() {
		return billEndDate;
	}
	/**
	 * @设置 单据结束日期
	 * @param billEndDate
	 */
	public void setBillEndDate(String billEndDate) {
		this.billEndDate = billEndDate;
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
	 * @return 销售人员名称
	 */
	public String getSalerName() {
		return salerName;
	}
	/**
	 * @设置 销售人员名称
	 * @param salerName
	 */
	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}
	public String getDocBizNo() {
		return docBizNo;
	}
	public void setDocBizNo(String docBizNo) {
		this.docBizNo = docBizNo;
	}
	
}