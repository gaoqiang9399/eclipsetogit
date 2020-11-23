package app.component.finance.voucher.entity;
import app.base.BaseDomain;
/**
 * Title: CwReviewBusiness.java Description:
 * 
 * @author：kaifa@dhcc.com.cn
 * @Fri Apr 21 11:46:05 CST 2017
 * @version：1.0
 **/
public class CwReviewBusiness extends BaseDomain {
	private String finBooks;//帐套标识
	private String traceNo;// 流水号
	private String txCode;// 交易代码结合产品类型匹配cw_vch_rule_mst CODE20001：放款；CODE20002：还款；CODE20003收费
	private String prdtNo;// 产品类型编码
	private String businessType;// 业务类型02 信贷
	private String businessNo;// 业务编号  存fincshowNo
	private String busShowNo;//展示编号
	private String employ;// 员工
	private String dept;// 部门
	private String customerNo;// 客户编号
	private String customerName;// 客户名称
	private String phoneNo;// 客户手机号
	private String remarks;// 摘要
	private String totalAmt;// 总金额
	private String cashBankAmt;// 现金银行金额
	private String principalAmt;// 本金
	private String intstAmt;// 利息
	private String overInistAmt;// 罚息
	private String overDueAmt;// 本次还款逾期利息合计
	private String cmpdIntstAmt;// 本次还款复利利息合计
	private String againstAmt;// 本次冲抵
	private String breachAmt;// 违约金   //本次划款违约金合计
	private String cancelAmt;// 核销
	private String marginAmt;// 保证金
	private String balanceAmt;// 结余
	private String depositAmt;// 定金
	private String preferAmt;// 优惠表外类科目
	// 2017年8月28日 添加
	private String refundAmt;//退款金额
	private String verificationAmt;//重复定义了核销，这个修改成备用字段 2017年8月29日
	
	//由于有15个费用，以下四个地段不再使用
	private String feeGuarantee;// 担保费
	private String feePoundage;// 手续费   // 本次划款费用合计（不包括减免的）（费用收取与还款合并是存储，单独收费时有单独的费用历史表）
	private String feeService;// 服务费
	private String feeReview;// 评审费
	//以上四个字段不再使用 2017年8月28日
	//
	
	private String overduePrincipalAmt;//逾期本金 2017年8月27日 添加
	
	//添加字段 2017年8月28日 
	private String feeItem1;//担保费
	private String feeItem2;//保证金
	private String feeItem3;//存出保证金
	private String feeItem4;//手续费
	private String feeItem5;//服务费
	private String feeItem6;//账户管理费
	private String feeItem7;//车辆管理费
	private String feeItem8;//评审费
	private String feeItem9;//GPS安装费
	private String feeItem10;//保管费
	private String feeItem11;//发票处理费
	private String feeItem12;//保理资信调查费
	private String feeItem13;//延期管理费
	private String feeItem14;//返费
	private String feeItem15;//费用15

	private String feeCost1;// 费用1备用  
	private String feeCost2;// 费用2备用 
	private String feeCost3;// 费用3备用  
	private String feeCost4;// 费用4备用
	private String feeCost5;// 费用5备用
	private String feeCost6;// 费用6备用
	private String feeCost7;// 费用7备用
	private String feeCost8;// 费用8备用
	private String feeCost9;// 费用9备用
	private String feeCost10;// 费用10备用
	private String callBack;// 回调地址
	private String backState;// 回调状态 0：未回调；1：回调成功；2：回调失败'
	private String pzPrefix;// 凭证前缀
	private String voucherNoteNo;// 凭证字号
	private String voucherNo;// 凭证编号
	private String reviewState;// 复核状态0：未复核；1：已复核
	private String reviewDate;// 复核日期
	private String createDate;// 创建日期
	private String opNo;// 操作员编号
	private String opName;// 操作员名称
	private String occTime;// 时间戳最后一次修改日期
	
	private String cusTel;//客户联系电话//数据库没有此字段
	

	public String getFinBooks() {
		return finBooks;
	}
	public void setFinBooks(String finBooks) {
		this.finBooks = finBooks;
	}
	/**
	 * @return 流水号
	 */
	public String getTraceNo() {
		return traceNo;
	}
	/**
	 * @设置 流水号
	 * @param traceNo
	 */
	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}
	/**
	 * @return 交易代码结合产品类型匹配cw_vch_rule_mst
	 */
	public String getTxCode() {
		return txCode;
	}
	/**
	 * @设置 交易代码结合产品类型匹配cw_vch_rule_mst
	 * @param txCode
	 */
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	/**
	 * @return 产品类型编码
	 */
	public String getPrdtNo() {
		return prdtNo;
	}
	/**
	 * @设置 产品类型编码
	 * @param prdtNo
	 */
	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}
	/**
	 * @return 业务类型1：放款；2：还款
	 */
	public String getBusinessType() {
		return businessType;
	}
	/**
	 * @设置 业务类型1：放款；2：还款
	 * @param businessType
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	/**
	 * @return 业务编号
	 */
	public String getBusinessNo() {
		return businessNo;
	}
	/**
	 * @设置 业务编号
	 * @param businessNo
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	/**
	 * @return 员工
	 */
	public String getEmploy() {
		return employ;
	}
	/**
	 * @设置 员工
	 * @param employ
	 */
	public void setEmploy(String employ) {
		this.employ = employ;
	}
	/**
	 * @return 部门
	 */
	public String getDept() {
		return dept;
	}
	/**
	 * @设置 部门
	 * @param dept
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}
	/**
	 * @return 客户编号
	 */
	public String getCustomerNo() {
		return customerNo;
	}
	/**
	 * @设置 客户编号
	 * @param customerNo
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	/**
	 * @return 客户名称
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @设置 客户名称
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return 客户手机号
	 */
	public String getPhoneNo() {
		return phoneNo;
	}
	/**
	 * @设置 客户手机号
	 * @param phoneNo
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	/**
	 * @return 摘要
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @设置 摘要
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return 总金额
	 */
	public String getTotalAmt() {
		return totalAmt;
	}
	/**
	 * @设置 总金额
	 * @param totalAmt
	 */
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	/**
	 * @return 现金银行金额
	 */
	public String getCashBankAmt() {
		return cashBankAmt;
	}
	/**
	 * @设置 现金银行金额
	 * @param cashBankAmt
	 */
	public void setCashBankAmt(String cashBankAmt) {
		this.cashBankAmt = cashBankAmt;
	}
	/**
	 * @return 本金
	 */
	public String getPrincipalAmt() {
		return principalAmt;
	}
	/**
	 * @设置 本金
	 * @param principalAmt
	 */
	public void setPrincipalAmt(String principalAmt) {
		this.principalAmt = principalAmt;
	}
	/**
	 * @return 利息
	 */
	public String getIntstAmt() {
		return intstAmt;
	}
	/**
	 * @设置 利息
	 * @param intstAmt
	 */
	public void setIntstAmt(String intstAmt) {
		this.intstAmt = intstAmt;
	}
	/**
	 * @return 罚息
	 */
	public String getOverInistAmt() {
		return overInistAmt;
	}
	/**
	 * @设置 罚息
	 * @param overInistAmt
	 */
	public void setOverInistAmt(String overInistAmt) {
		this.overInistAmt = overInistAmt;
	}
	/**
	 * @return 违约金
	 */
	public String getBreachAmt() {
		return breachAmt;
	}
	/**
	 * @设置 违约金
	 * @param breachAmt
	 */
	public void setBreachAmt(String breachAmt) {
		this.breachAmt = breachAmt;
	}
	/**
	 * @return 核销
	 */
	public String getCancelAmt() {
		return cancelAmt;
	}
	/**
	 * @设置 核销
	 * @param cancelAmt
	 */
	public void setCancelAmt(String cancelAmt) {
		this.cancelAmt = cancelAmt;
	}
	/**
	 * @return 保证金
	 */
	public String getMarginAmt() {
		return marginAmt;
	}
	/**
	 * @设置 保证金
	 * @param marginAmt
	 */
	public void setMarginAmt(String marginAmt) {
		this.marginAmt = marginAmt;
	}
	/**
	 * @return 结余
	 */
	public String getBalanceAmt() {
		return balanceAmt;
	}
	/**
	 * @设置 结余
	 * @param balanceAmt
	 */
	public void setBalanceAmt(String balanceAmt) {
		this.balanceAmt = balanceAmt;
	}
	/**
	 * @return 定金
	 */
	public String getDepositAmt() {
		return depositAmt;
	}
	/**
	 * @设置 定金
	 * @param depositAmt
	 */
	public void setDepositAmt(String depositAmt) {
		this.depositAmt = depositAmt;
	}
	/**
	 * @return 优惠表外类科目
	 */
	public String getPreferAmt() {
		return preferAmt;
	}
	/**
	 * @设置 优惠表外类科目
	 * @param preferAmt
	 */
	public void setPreferAmt(String preferAmt) {
		this.preferAmt = preferAmt;
	}
	/**
	 * @return 担保费
	 */
	public String getFeeGuarantee() {
		return feeGuarantee;
	}
	/**
	 * @设置 担保费
	 * @param feeGuarantee
	 */
	public void setFeeGuarantee(String feeGuarantee) {
		this.feeGuarantee = feeGuarantee;
	}
	/**
	 * @return 手续费
	 */
	public String getFeePoundage() {
		return feePoundage;
	}
	/**
	 * @设置 手续费
	 * @param feePoundage
	 */
	public void setFeePoundage(String feePoundage) {
		this.feePoundage = feePoundage;
	}
	/**
	 * @return 服务费
	 */
	public String getFeeService() {
		return feeService;
	}
	/**
	 * @设置 服务费
	 * @param feeService
	 */
	public void setFeeService(String feeService) {
		this.feeService = feeService;
	}
	/**
	 * @return 评审费
	 */
	public String getFeeReview() {
		return feeReview;
	}
	/**
	 * @设置 评审费
	 * @param feeReview
	 */
	public void setFeeReview(String feeReview) {
		this.feeReview = feeReview;
	}
	/**
	 * @return 费用1备用
	 */
	public String getFeeCost1() {
		return feeCost1;
	}
	/**
	 * @设置 费用1备用
	 * @param feeCost1
	 */
	public void setFeeCost1(String feeCost1) {
		this.feeCost1 = feeCost1;
	}
	/**
	 * @return 费用2备用
	 */
	public String getFeeCost2() {
		return feeCost2;
	}
	/**
	 * @设置 费用2备用
	 * @param feeCost2
	 */
	public void setFeeCost2(String feeCost2) {
		this.feeCost2 = feeCost2;
	}
	/**
	 * @return 费用3备用
	 */
	public String getFeeCost3() {
		return feeCost3;
	}
	/**
	 * @设置 费用3备用
	 * @param feeCost3
	 */
	public void setFeeCost3(String feeCost3) {
		this.feeCost3 = feeCost3;
	}
	/**
	 * @return 费用4备用
	 */
	public String getFeeCost4() {
		return feeCost4;
	}
	/**
	 * @设置 费用4备用
	 * @param feeCost4
	 */
	public void setFeeCost4(String feeCost4) {
		this.feeCost4 = feeCost4;
	}
	/**
	 * @return 费用5备用
	 */
	public String getFeeCost5() {
		return feeCost5;
	}
	/**
	 * @设置 费用5备用
	 * @param feeCost5
	 */
	public void setFeeCost5(String feeCost5) {
		this.feeCost5 = feeCost5;
	}
	/**
	 * @return 费用6备用
	 */
	public String getFeeCost6() {
		return feeCost6;
	}
	/**
	 * @设置 费用6备用
	 * @param feeCost6
	 */
	public void setFeeCost6(String feeCost6) {
		this.feeCost6 = feeCost6;
	}
	/**
	 * @return 费用7备用
	 */
	public String getFeeCost7() {
		return feeCost7;
	}
	/**
	 * @设置 费用7备用
	 * @param feeCost7
	 */
	public void setFeeCost7(String feeCost7) {
		this.feeCost7 = feeCost7;
	}
	/**
	 * @return 费用8备用
	 */
	public String getFeeCost8() {
		return feeCost8;
	}
	/**
	 * @设置 费用8备用
	 * @param feeCost8
	 */
	public void setFeeCost8(String feeCost8) {
		this.feeCost8 = feeCost8;
	}
	/**
	 * @return 费用9备用
	 */
	public String getFeeCost9() {
		return feeCost9;
	}
	/**
	 * @设置 费用9备用
	 * @param feeCost9
	 */
	public void setFeeCost9(String feeCost9) {
		this.feeCost9 = feeCost9;
	}
	/**
	 * @return 费用10备用
	 */
	public String getFeeCost10() {
		return feeCost10;
	}
	/**
	 * @设置 费用10备用
	 * @param feeCost10
	 */
	public void setFeeCost10(String feeCost10) {
		this.feeCost10 = feeCost10;
	}
	/**
	 * @return 回调地址
	 */
	public String getCallBack() {
		return callBack;
	}
	/**
	 * @设置 回调地址
	 * @param callBack
	 */
	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}
	/**
	 * @return 凭证前缀
	 */
	public String getPzPrefix() {
		return pzPrefix;
	}
	/**
	 * @设置 凭证前缀
	 * @param pzPrefix
	 */
	public void setPzPrefix(String pzPrefix) {
		this.pzPrefix = pzPrefix;
	}
	/**
	 * @return 凭证字号
	 */
	public String getVoucherNoteNo() {
		return voucherNoteNo;
	}
	/**
	 * @设置 凭证字号
	 * @param voucherNoteNo
	 */
	public void setVoucherNoteNo(String voucherNoteNo) {
		this.voucherNoteNo = voucherNoteNo;
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
	 * @return 复核状态0：未复核；1：已复核
	 */
	public String getReviewState() {
		return reviewState;
	}
	/**
	 * @设置 复核状态0：未复核；1：已复核
	 * @param reviewState
	 */
	public void setReviewState(String reviewState) {
		this.reviewState = reviewState;
	}
	/**
	 * @return 复核日期
	 */
	public String getReviewDate() {
		return reviewDate;
	}
	/**
	 * @设置 复核日期
	 * @param reviewDate
	 */
	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	/**
	 * @return 创建日期
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * @设置 创建日期
	 * @param createDate
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
	/**
	 * 方法描述： 回调状态 0：未回调；1：回调成功；2：回调失败
	 * @return
	 * String
	 * @author Javelin
	 * @date 2017-4-24 下午3:59:17
	 */
	public String getBackState() {
		return backState;
	}
	/**
	 * 方法描述： 回调状态 0：未回调；1：回调成功；2：回调失败
	 * @param backState
	 * void
	 * @author Javelin
	 * @date 2017-4-24 下午3:59:10
	 */
	public void setBackState(String backState) {
		this.backState = backState;
	}
	public String getOverDueAmt() {
		return overDueAmt;
	}
	public void setOverDueAmt(String overDueAmt) {
		this.overDueAmt = overDueAmt;
	}
	public String getCmpdIntstAmt() {
		return cmpdIntstAmt;
	}
	public void setCmpdIntstAmt(String cmpdIntstAmt) {
		this.cmpdIntstAmt = cmpdIntstAmt;
	}
	public String getAgainstAmt() {
		return againstAmt;
	}
	public void setAgainstAmt(String againstAmt) {
		this.againstAmt = againstAmt;
	}
	public String getBusShowNo() {
		return busShowNo;
	}
	public void setBusShowNo(String busShowNo) {
		this.busShowNo = busShowNo;
	}
	public String getOverduePrincipalAmt() {
		return overduePrincipalAmt;
	}
	public void setOverduePrincipalAmt(String overduePrincipalAmt) {
		this.overduePrincipalAmt = overduePrincipalAmt;
	}
	public String getFeeItem1() {
		return feeItem1;
	}
	public void setFeeItem1(String feeItem1) {
		this.feeItem1 = feeItem1;
	}
	public String getFeeItem2() {
		return feeItem2;
	}
	public void setFeeItem2(String feeItem2) {
		this.feeItem2 = feeItem2;
	}
	public String getFeeItem3() {
		return feeItem3;
	}
	public void setFeeItem3(String feeItem3) {
		this.feeItem3 = feeItem3;
	}
	public String getFeeItem4() {
		return feeItem4;
	}
	public void setFeeItem4(String feeItem4) {
		this.feeItem4 = feeItem4;
	}
	public String getFeeItem5() {
		return feeItem5;
	}
	public void setFeeItem5(String feeItem5) {
		this.feeItem5 = feeItem5;
	}
	public String getFeeItem6() {
		return feeItem6;
	}
	public void setFeeItem6(String feeItem6) {
		this.feeItem6 = feeItem6;
	}
	public String getFeeItem7() {
		return feeItem7;
	}
	public void setFeeItem7(String feeItem7) {
		this.feeItem7 = feeItem7;
	}
	public String getFeeItem8() {
		return feeItem8;
	}
	public void setFeeItem8(String feeItem8) {
		this.feeItem8 = feeItem8;
	}
	public String getFeeItem9() {
		return feeItem9;
	}
	public void setFeeItem9(String feeItem9) {
		this.feeItem9 = feeItem9;
	}
	public String getFeeItem10() {
		return feeItem10;
	}
	public void setFeeItem10(String feeItem10) {
		this.feeItem10 = feeItem10;
	}
	public String getFeeItem11() {
		return feeItem11;
	}
	public void setFeeItem11(String feeItem11) {
		this.feeItem11 = feeItem11;
	}
	public String getFeeItem12() {
		return feeItem12;
	}
	public void setFeeItem12(String feeItem12) {
		this.feeItem12 = feeItem12;
	}
	public String getFeeItem13() {
		return feeItem13;
	}
	public void setFeeItem13(String feeItem13) {
		this.feeItem13 = feeItem13;
	}
	public String getFeeItem14() {
		return feeItem14;
	}
	public void setFeeItem14(String feeItem14) {
		this.feeItem14 = feeItem14;
	}
	public String getFeeItem15() {
		return feeItem15;
	}
	public void setFeeItem15(String feeItem15) {
		this.feeItem15 = feeItem15;
	}
	public String getRefundAmt() {
		return refundAmt;
	}
	public void setRefundAmt(String refundAmt) {
		this.refundAmt = refundAmt;
	}
	public String getVerificationAmt() {
		return verificationAmt;
	}
	public void setVerificationAmt(String verificationAmt) {
		this.verificationAmt = verificationAmt;
	}
	public String getCusTel() {
		return cusTel;
	}
	public void setCusTel(String cusTel) {
		this.cusTel = cusTel;
	}
	
}