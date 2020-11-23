package app.component.pact.repay.entity;
import app.base.BaseDomain;
/**
* Title: MfDeductRefundApply.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Aug 22 11:24:01 CST 2017
* @version：1.0
**/
public class MfDeductRefundApply extends BaseDomain {
	private String id;//减免/退费唯一ID
	private String appType;//申请类型 1-减免 2-退费
	private String appId;//融资业务申请编号
	private String pactId;//合同号
	private String pactNo;//合同展示号
	private String fincId;//放款申请号
	private String appName;//项目名称
	private String cusNo;//客户编号
	private String cusName;//客户名称
	private String pactAmt;//合同金额
	private String fincRate;//合同利率
	private Double recvAmt;//本次实际收到贷款客户还款合计总额（不包括减免的）=prcp_sum+norm_intst_sum+over_intst_sum+cmpd_intst_sum+penalty_sum+fee_sum+bal_amt
	private Double prcpSum;//本次还款本金总额
	private Double normIntstSum;//本次还款正常利息（不包括减免的）
	private Double overIntstSum;//本次还款逾期利息合计（不包括减免的）
	private Double cmpdIntstSum;//本次还款复利利息合计（不包括减免的）
	private Double penaltySum;//本次划款违约金合计（不包括减免的）
	private Double feeSum;//本次划款费用合计（不包括减免的）（费用收取与还款合并是存储，单独收费时有单独的费用历史表）
	private Double applySum;//本次减免/退费合计
	private Double applyNormIntstSum;//本次减免/退费正常利息合计
	private Double applyOverIntstSum;//本次减免/退费逾期利息合计
	private Double applyCmpdIntstSum;//本次减免/退费复利利息合计
	private Double applyPenaltySum;//本次减免/退费违约金合计
	private Double applyFeeSum;//本次减免/退费费用合计
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String brNo;//部门编号
	private String brName;//部门名称
	private String appTime;//登记时间
	private String lstModTime;//最后修改时间
	private String regDate;//登记日期
	private String lstModDate;//上次修改日期
	private String ext1;//
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String ext5;//
	private String flowFlag;//审批流程启用标志
	private String flowId;//审批流程id
	private String appSts;//申请状态 0-已申请未提交 1-流程中（已提交）2-申请成功（审批通过） 3-审批否决
	private String overDays;//逾期天数用于流程参数
	private String kindName;//产品名称
	/**
	 * @return 减免/退费唯一ID
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 减免/退费唯一ID
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 申请类型 1-减免 2-退费
	 */
	public String getAppType() {
	 	return appType;
	}
	/**
	 * @设置 申请类型 1-减免 2-退费
	 * @param appType
	 */
	public void setAppType(String appType) {
	 	this.appType = appType;
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
	 * @return 合同展示号
	 */
	public String getPactNo() {
	 	return pactNo;
	}
	/**
	 * @设置 合同展示号
	 * @param pactNo
	 */
	public void setPactNo(String pactNo) {
	 	this.pactNo = pactNo;
	}
	/**
	 * @return 放款申请号
	 */
	public String getFincId() {
	 	return fincId;
	}
	/**
	 * @设置 放款申请号
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
	 * @return 客户编号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户编号
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
	 * @return 本次还款本金总额
	 */
	public Double getPrcpSum() {
	 	return prcpSum;
	}
	/**
	 * @设置 本次还款本金总额
	 * @param prcpSum
	 */
	public void setPrcpSum(Double prcpSum) {
	 	this.prcpSum = prcpSum;
	}
	/**
	 * @return 本次还款正常利息（不包括减免的）
	 */
	public Double getNormIntstSum() {
	 	return normIntstSum;
	}
	/**
	 * @设置 本次还款正常利息（不包括减免的）
	 * @param normIntstSum
	 */
	public void setNormIntstSum(Double normIntstSum) {
	 	this.normIntstSum = normIntstSum;
	}
	/**
	 * @return 本次还款逾期利息合计（不包括减免的）
	 */
	public Double getOverIntstSum() {
	 	return overIntstSum;
	}
	/**
	 * @设置 本次还款逾期利息合计（不包括减免的）
	 * @param overIntstSum
	 */
	public void setOverIntstSum(Double overIntstSum) {
	 	this.overIntstSum = overIntstSum;
	}
	/**
	 * @return 本次还款复利利息合计（不包括减免的）
	 */
	public Double getCmpdIntstSum() {
	 	return cmpdIntstSum;
	}
	/**
	 * @设置 本次还款复利利息合计（不包括减免的）
	 * @param cmpdIntstSum
	 */
	public void setCmpdIntstSum(Double cmpdIntstSum) {
	 	this.cmpdIntstSum = cmpdIntstSum;
	}
	/**
	 * @return 本次划款违约金合计（不包括减免的）
	 */
	public Double getPenaltySum() {
	 	return penaltySum;
	}
	/**
	 * @设置 本次划款违约金合计（不包括减免的）
	 * @param penaltySum
	 */
	public void setPenaltySum(Double penaltySum) {
	 	this.penaltySum = penaltySum;
	}
	/**
	 * @return 本次划款费用合计（不包括减免的）（费用收取与还款合并是存储，单独收费时有单独的费用历史表）
	 */
	public Double getFeeSum() {
	 	return feeSum;
	}
	/**
	 * @设置 本次划款费用合计（不包括减免的）（费用收取与还款合并是存储，单独收费时有单独的费用历史表）
	 * @param feeSum
	 */
	public void setFeeSum(Double feeSum) {
	 	this.feeSum = feeSum;
	}
	/**
	 * @return 本次减免/退费合计
	 */
	public Double getApplySum() {
	 	return applySum;
	}
	/**
	 * @设置 本次减免/退费合计
	 * @param applySum
	 */
	public void setApplySum(Double applySum) {
	 	this.applySum = applySum;
	}
	/**
	 * @return 本次减免/退费正常利息合计
	 */
	public Double getApplyNormIntstSum() {
	 	return applyNormIntstSum;
	}
	/**
	 * @设置 本次减免/退费正常利息合计
	 * @param applyNormIntstSum
	 */
	public void setApplyNormIntstSum(Double applyNormIntstSum) {
	 	this.applyNormIntstSum = applyNormIntstSum;
	}
	/**
	 * @return 本次减免/退费逾期利息合计
	 */
	public Double getApplyOverIntstSum() {
	 	return applyOverIntstSum;
	}
	/**
	 * @设置 本次减免/退费逾期利息合计
	 * @param applyOverIntstSum
	 */
	public void setApplyOverIntstSum(Double applyOverIntstSum) {
	 	this.applyOverIntstSum = applyOverIntstSum;
	}
	/**
	 * @return 本次减免/退费复利利息合计
	 */
	public Double getApplyCmpdIntstSum() {
	 	return applyCmpdIntstSum;
	}
	/**
	 * @设置 本次减免/退费复利利息合计
	 * @param applyCmpdIntstSum
	 */
	public void setApplyCmpdIntstSum(Double applyCmpdIntstSum) {
	 	this.applyCmpdIntstSum = applyCmpdIntstSum;
	}
	/**
	 * @return 本次减免/退费违约金合计
	 */
	public Double getApplyPenaltySum() {
	 	return applyPenaltySum;
	}
	/**
	 * @设置 本次减免/退费违约金合计
	 * @param applyPenaltySum
	 */
	public void setApplyPenaltySum(Double applyPenaltySum) {
	 	this.applyPenaltySum = applyPenaltySum;
	}
	/**
	 * @return 本次减免/退费费用合计
	 */
	public Double getApplyFeeSum() {
	 	return applyFeeSum;
	}
	/**
	 * @设置 本次减免/退费费用合计
	 * @param applyFeeSum
	 */
	public void setApplyFeeSum(Double applyFeeSum) {
	 	this.applyFeeSum = applyFeeSum;
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
	public String getAppTime() {
	 	return appTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setAppTime(String appTime) {
	 	this.appTime = appTime;
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
	public String getFlowFlag() {
		return flowFlag;
	}
	public void setFlowFlag(String flowFlag) {
		this.flowFlag = flowFlag;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getAppSts() {
		return appSts;
	}
	public void setAppSts(String appSts) {
		this.appSts = appSts;
	}
	public String getPactAmt() {
		return pactAmt;
	}
	public void setPactAmt(String pactAmt) {
		this.pactAmt = pactAmt;
	}
	public String getFincRate() {
		return fincRate;
	}
	public void setFincRate(String fincRate) {
		this.fincRate = fincRate;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOverDays() {
		return overDays;
	}

	public void setOverDays(String overDays) {
		this.overDays = overDays;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
}