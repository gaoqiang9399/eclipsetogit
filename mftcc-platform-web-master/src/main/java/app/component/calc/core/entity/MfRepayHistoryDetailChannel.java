package app.component.calc.core.entity;
import app.base.BaseDomain;
/**
* Title: MfRepayHistoryDetailChannel.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Oct 30 15:40:53 CST 2017
* @version：1.0
**/
public class MfRepayHistoryDetailChannel extends BaseDomain {
	private String repayDetailId;//还款历史明细表id
	private String repayId;//还款历史ID
	private String orderId;//订单ID
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String pactId;//合同号
	private String fincId;//借据号
	private String appName;//项目名称
	private Double recvAmt;//本次实际收到贷款客户还款合计总额（不包括减免的）=prcp_sum+norm_intst_sum+over_intst_sum+cmpd_intst_sum+penalty_sum+fee_sum+bal_amt
	private Double recvAmtSys;//本次系统实际收到还款合计总额（不包括减免的，不包括本次冲抵）=prcp_sum+norm_intst_sum+over_intst_sum+cmpd_intst_sum+penalty_sum+fee_sum+bal_amt-against_amt
	private Integer termNum;//还款计划期号，提前还款时不指定具体期号
	private Double prcpSum;//本期还款本金总额
	private Double normIntstSum;//本期还款正常利息（不包括减免的）
	private Double overIntstSum;//本期还款逾期利息合计（不包括减免的）
	private Double cmpdIntstSum;//本期还款复利利息合计（不包括减免的）
	private Double penaltySum;//本期划款违约金合计（不包括减免的）
	private Double feeSum;//本期划款费用合计（不包括减免的）（费用收取与还款合并是存储，单独收费时有单独的费用历史表）
	private Double deductNormIntstSum;//本期减免正常利息合计
	private Double deductOverIntstSum;//本期减免逾期利息合计
	private Double deductCmpdIntstSum;//本期减免复利利息合计
	private Double deductPenaltySum;//本期减免违约金合计
	private Double deductFeeSum;//本期减免费用合计
	private Double loanBal;//余额
	private String repayType;//还款类型：1-正常还款，2-逾期还款，3-提前还款 ，4-上收息时收取,5-放款时收取（存在固定还款日且预先支付利息方式是 放款时收取时）
	private String repayDate;//还款日期：还款页面选择的还款日期
	private String accountFlag;//记账标识 0-未记账，1-记账
	private String paymentType;//支付方式：1--现金，2--银行转帐
	private String regTime;//登记时间17为格式20170506 15:35:40
	private String lstModTime;//修改时间 17为格式20170506 15:35:40
	private String opNo;//登记人员编号
	private String opName;//登记人员名称
	private String brNo;//登记人员部门编号
	private String brName;//登记人员部门名称
	private String regDate;//登记日期
	private String lstModDate;//上次修改日期
	private String ext1;//
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String ext5;//

	/**
	 * @return 还款历史明细表id
	 */
	public String getRepayDetailId() {
	 	return repayDetailId;
	}
	/**
	 * @设置 还款历史明细表id
	 * @param repayDetailId
	 */
	public void setRepayDetailId(String repayDetailId) {
	 	this.repayDetailId = repayDetailId;
	}
	/**
	 * @return 还款历史ID
	 */
	public String getRepayId() {
	 	return repayId;
	}
	/**
	 * @设置 还款历史ID
	 * @param repayId
	 */
	public void setRepayId(String repayId) {
	 	this.repayId = repayId;
	}
	/**
	 * @return 订单ID
	 */
	public String getOrderId() {
	 	return orderId;
	}
	/**
	 * @设置 订单ID
	 * @param orderId
	 */
	public void setOrderId(String orderId) {
	 	this.orderId = orderId;
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
	 * @return 借据号
	 */
	public String getFincId() {
	 	return fincId;
	}
	/**
	 * @设置 借据号
	 * @param fincId
	 */
	public void setFincId(String fincId) {
	 	this.fincId = fincId;
	}
	/**
	 * @return 项目名称
	 */
	public String getAppName() {
	 	return appName;
	}
	/**
	 * @设置 项目名称
	 * @param appName
	 */
	public void setAppName(String appName) {
	 	this.appName = appName;
	}
	/**
	 * @return 本次实际收到贷款客户还款合计总额（不包括减免的）=prcp_sum+norm_intst_sum+over_intst_sum+cmpd_intst_sum+penalty_sum+fee_sum+bal_amt
	 */
	public Double getRecvAmt() {
	 	return recvAmt;
	}
	/**
	 * @设置 本次实际收到贷款客户还款合计总额（不包括减免的）=prcp_sum+norm_intst_sum+over_intst_sum+cmpd_intst_sum+penalty_sum+fee_sum+bal_amt
	 * @param recvAmt
	 */
	public void setRecvAmt(Double recvAmt) {
	 	this.recvAmt = recvAmt;
	}
	/**
	 * @return 本次系统实际收到还款合计总额（不包括减免的，不包括本次冲抵）=prcp_sum+norm_intst_sum+over_intst_sum+cmpd_intst_sum+penalty_sum+fee_sum+bal_amt-against_amt
	 */
	public Double getRecvAmtSys() {
	 	return recvAmtSys;
	}
	/**
	 * @设置 本次系统实际收到还款合计总额（不包括减免的，不包括本次冲抵）=prcp_sum+norm_intst_sum+over_intst_sum+cmpd_intst_sum+penalty_sum+fee_sum+bal_amt-against_amt
	 * @param recvAmtSys
	 */
	public void setRecvAmtSys(Double recvAmtSys) {
	 	this.recvAmtSys = recvAmtSys;
	}
	/**
	 * @return 还款计划期号，提前还款时不指定具体期号
	 */
	public Integer getTermNum() {
	 	return termNum;
	}
	/**
	 * @设置 还款计划期号，提前还款时不指定具体期号
	 * @param termNum
	 */
	public void setTermNum(Integer termNum) {
	 	this.termNum = termNum;
	}
	/**
	 * @return 本期还款本金总额
	 */
	public Double getPrcpSum() {
	 	return prcpSum;
	}
	/**
	 * @设置 本期还款本金总额
	 * @param prcpSum
	 */
	public void setPrcpSum(Double prcpSum) {
	 	this.prcpSum = prcpSum;
	}
	/**
	 * @return 本期还款正常利息（不包括减免的）
	 */
	public Double getNormIntstSum() {
	 	return normIntstSum;
	}
	/**
	 * @设置 本期还款正常利息（不包括减免的）
	 * @param normIntstSum
	 */
	public void setNormIntstSum(Double normIntstSum) {
	 	this.normIntstSum = normIntstSum;
	}
	/**
	 * @return 本期还款逾期利息合计（不包括减免的）
	 */
	public Double getOverIntstSum() {
	 	return overIntstSum;
	}
	/**
	 * @设置 本期还款逾期利息合计（不包括减免的）
	 * @param overIntstSum
	 */
	public void setOverIntstSum(Double overIntstSum) {
	 	this.overIntstSum = overIntstSum;
	}
	/**
	 * @return 本期还款复利利息合计（不包括减免的）
	 */
	public Double getCmpdIntstSum() {
	 	return cmpdIntstSum;
	}
	/**
	 * @设置 本期还款复利利息合计（不包括减免的）
	 * @param cmpdIntstSum
	 */
	public void setCmpdIntstSum(Double cmpdIntstSum) {
	 	this.cmpdIntstSum = cmpdIntstSum;
	}
	/**
	 * @return 本期划款违约金合计（不包括减免的）
	 */
	public Double getPenaltySum() {
	 	return penaltySum;
	}
	/**
	 * @设置 本期划款违约金合计（不包括减免的）
	 * @param penaltySum
	 */
	public void setPenaltySum(Double penaltySum) {
	 	this.penaltySum = penaltySum;
	}
	/**
	 * @return 本期划款费用合计（不包括减免的）（费用收取与还款合并是存储，单独收费时有单独的费用历史表）
	 */
	public Double getFeeSum() {
	 	return feeSum;
	}
	/**
	 * @设置 本期划款费用合计（不包括减免的）（费用收取与还款合并是存储，单独收费时有单独的费用历史表）
	 * @param feeSum
	 */
	public void setFeeSum(Double feeSum) {
	 	this.feeSum = feeSum;
	}
	/**
	 * @return 本期减免正常利息合计
	 */
	public Double getDeductNormIntstSum() {
	 	return deductNormIntstSum;
	}
	/**
	 * @设置 本期减免正常利息合计
	 * @param deductNormIntstSum
	 */
	public void setDeductNormIntstSum(Double deductNormIntstSum) {
	 	this.deductNormIntstSum = deductNormIntstSum;
	}
	/**
	 * @return 本期减免逾期利息合计
	 */
	public Double getDeductOverIntstSum() {
	 	return deductOverIntstSum;
	}
	/**
	 * @设置 本期减免逾期利息合计
	 * @param deductOverIntstSum
	 */
	public void setDeductOverIntstSum(Double deductOverIntstSum) {
	 	this.deductOverIntstSum = deductOverIntstSum;
	}
	/**
	 * @return 本期减免复利利息合计
	 */
	public Double getDeductCmpdIntstSum() {
	 	return deductCmpdIntstSum;
	}
	/**
	 * @设置 本期减免复利利息合计
	 * @param deductCmpdIntstSum
	 */
	public void setDeductCmpdIntstSum(Double deductCmpdIntstSum) {
	 	this.deductCmpdIntstSum = deductCmpdIntstSum;
	}
	/**
	 * @return 本期减免违约金合计
	 */
	public Double getDeductPenaltySum() {
	 	return deductPenaltySum;
	}
	/**
	 * @设置 本期减免违约金合计
	 * @param deductPenaltySum
	 */
	public void setDeductPenaltySum(Double deductPenaltySum) {
	 	this.deductPenaltySum = deductPenaltySum;
	}
	/**
	 * @return 本期减免费用合计
	 */
	public Double getDeductFeeSum() {
	 	return deductFeeSum;
	}
	/**
	 * @设置 本期减免费用合计
	 * @param deductFeeSum
	 */
	public void setDeductFeeSum(Double deductFeeSum) {
	 	this.deductFeeSum = deductFeeSum;
	}
	/**
	 * @return 余额
	 */
	public Double getLoanBal() {
	 	return loanBal;
	}
	/**
	 * @设置 余额
	 * @param loanBal
	 */
	public void setLoanBal(Double loanBal) {
	 	this.loanBal = loanBal;
	}
	/**
	 * @return 还款类型：1-正常还款，2-逾期还款，3-提前还款 ，4-上收息时收取,5-放款时收取（存在固定还款日且预先支付利息方式是 放款时收取时）
	 */
	public String getRepayType() {
	 	return repayType;
	}
	/**
	 * @设置 还款类型：1-正常还款，2-逾期还款，3-提前还款 ，4-上收息时收取,5-放款时收取（存在固定还款日且预先支付利息方式是 放款时收取时）
	 * @param repayType
	 */
	public void setRepayType(String repayType) {
	 	this.repayType = repayType;
	}
	/**
	 * @return 还款日期：还款页面选择的还款日期
	 */
	public String getRepayDate() {
	 	return repayDate;
	}
	/**
	 * @设置 还款日期：还款页面选择的还款日期
	 * @param repayDate
	 */
	public void setRepayDate(String repayDate) {
	 	this.repayDate = repayDate;
	}
	/**
	 * @return 记账标识 0-未记账，1-记账
	 */
	public String getAccountFlag() {
	 	return accountFlag;
	}
	/**
	 * @设置 记账标识 0-未记账，1-记账
	 * @param accountFlag
	 */
	public void setAccountFlag(String accountFlag) {
	 	this.accountFlag = accountFlag;
	}
	/**
	 * @return 支付方式：1--现金，2--银行转帐
	 */
	public String getPaymentType() {
	 	return paymentType;
	}
	/**
	 * @设置 支付方式：1--现金，2--银行转帐
	 * @param paymentType
	 */
	public void setPaymentType(String paymentType) {
	 	this.paymentType = paymentType;
	}
	/**
	 * @return 登记时间17为格式20170506 15:35:40
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间17为格式20170506 15:35:40
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 修改时间 17为格式20170506 15:35:40
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 修改时间 17为格式20170506 15:35:40
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 登记人员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 登记人员名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人员名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记人员部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记人员部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记人员部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记人员部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 登记日期
	 */
	public String getRegDate() {
	 	return regDate;
	}
	/**
	 * @设置 登记日期
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
	 	this.regDate = regDate;
	}
	/**
	 * @return 上次修改日期
	 */
	public String getLstModDate() {
	 	return lstModDate;
	}
	/**
	 * @设置 上次修改日期
	 * @param lstModDate
	 */
	public void setLstModDate(String lstModDate) {
	 	this.lstModDate = lstModDate;
	}
	/**
	 * @return 
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return 
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
}