package app.component.tour.entity;
import app.base.BaseDomain;
/**
* Title: MfBusFincAppChild.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat May 26 13:09:22 CST 2018
* @version：1.0
**/
public class MfBusFincTour extends BaseDomain {
	private String fincChildId;//借据号
	private String fincId;//借据号
	private String wkfRepayId;//还款流程ID
	private String bankAccId;//mf_cus_bank_acc_manage表的ID
	private String busModel;//业务模式 1-动产质押 2-仓单 3-保单 4-保兑仓 5-应收账款融资 6-应收账款管理
	private String pactId;//合同编号
	private String pactNo;//展示合同编号,用于对外展示不做业务处理
	private String appId;//申请编号
	private String appName;//项目名称
	private String cusNo;//客户编号
	private String cusName;//客户名称
	private Double pactAmt;//合同金额
	private Double putoutAmt;//借据金额/放款金额，多次放款合并和借据是这里=合同金额
	private Double putoutAmtReal;//实际借据金额/实际放款金额不包括放款时收取的费用利息
	private Double loanBal;//未还金额/借据余额
	private String repayType;//还款方式
	private String kindNo;//贷款产品编号
	private String kindName;//贷款产品名称
	private String putoutAppDate;//放款申请日期：放款申请时的日期
	private String putoutDate;//放款确认日期：最后放款确认的日期
	private String intstBeginDate;//起息开始日期
	private String intstEndDate;//起息到期日期
	private String intstEndDateExp;//展期到期日期/每次展期都更新
	private String pactBeginDate;//合同开始日期
	private String pactEndDate;//合同到期日期
	private String pactSignDate;//合同签订日期
	private String lastReturnDate;//上次还款日期
	private String calcIntstDate;//本次还款时利息计算（算头不算尾时和last_return_date 一致 首尾都算时为 last_return_date+1）
	private String termType;//期限类型：1-月，2-日，3-期，4-月日
	private Integer termMonth;//期限月
	private Integer termDay;//期限日
	private String rateType;//利率类型：1-年利率，2-月利率，3-日利率
	private Double fincRate;//
	private Double fincRateFloat;//利率浮动百分比
	private String baseRateType;//基准利率类型 1-贷款基准利率 2-公积金贷款率 3-贴现基准利率
	private String baseRateDate;//基准利率类型开始日期
	private Double overFloat;//逾期利率浮动百分比
	private Double overRate;//
	private Double cmpdFloat;//复利利率浮动百分比
	private Double cmpdRate;//
	private String icType;//计息方式：1-固定利率，2-浮动利率，3-固定利息
	private String fincSts;//申请状态/借据状态1未提交2审批中3已否决4审批通过待放款5放款已复核6已还款未复核7完结
	private String expFlag;//展期状态：0-为展期，1-已展期
	private String feeFlag;//费用状态：费用单独收取时处理该状态
	private Integer putoutCount;//第几次放款
	private Integer chargeInterest;//收取前N期利息之和
	private Integer chargeFee;//收取前N期费用之和
	private String finishedTime;//本笔借据完结时间
	private String appTime;//提交申请时间
	private String regTime;//创建时间
	private String lstModTime;//修改时间
	private String remark;//备注
	private String opNo;//登记人员编号
	private String opName;//登记人员名称
	private String brNo;//登记人员部门编号
	private String brName;//登记人员部门名称
	private String cusNoSupplier;//供应商编号
	private String cusNameSupplier;//供应商名称
	private String cusNoSales;//经销商编号
	private String cusNameSales;//经销商名称
	private String cusNoCore;//核心企业编号
	private String cusNameCore;//核心企业名称
	private String cusNoWarehouse;//仓储方编号
	private String cusNameWarehouse;//仓储方名称
	private String cusNoLogistics;//物流厂商编号
	private String cusNameLogistics;//物流厂商名称
	private String cusNoInsurance;//信保机构编号
	private String cusNameInsurance;//信保机构名称
	private String cusNoFund;//资金机构编号
	private String cusNameFund;//资金机构名称
	private String autoClassify;//是否自动分类（1是 2否）
	private String classifyMethod;//是否自动分类（1系统初分 2手工分类）
	private String classifyDate;//分类日期
	private String approveProcessId;//审批流程id
	private String approveProcess;//审批流程
	private String approveNodeNo;//当前审批节点编号
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//审批角色号/用户号
	private String approvePartName;//当前审批角色/用户名称
	private String rateChangeFlag;//利率调整标识：0-未调整（默认），1-已调整
	private String ext1;//备用字段1
	private String ext2;//备用字段2
	private String ext3;//备用字段3
	private String ext4;//备用字段4
	private String ext5;//备用字段5
	private String regDate;//登记日期
	private String lstModDate;//上次修改日期
	private String payMethod;//支付方式 1-自主支付 2-受托支付
	private String collectAccId;//收款账户 关联mf_cus_bank_acc_manage表的ID
	private String repayAccId;//还款账户 关联mf_cus_bank_acc_manage表的ID
	private String ifFixRate;//是否固定利率0否1是
	private String ext6;//
	private String ext7;//
	private String ext8;//
	private String ext9;//
	private String ext10;//
	private String putoutRealDate;//借据自然日放款日期
	private Double guaranteeRate;//保证比例
	private Double depositOutMarginAmt;//风险(存出)保证金
	private String depositOutAccountId;//风险(存出)账号
	private String depositOutTime;//风险(存出)保证金时间

	/**
	 * @return 借据号
	 */
	public String getFincChildId() {
	 	return fincChildId;
	}
	/**
	 * @设置 借据号
	 * @param fincChildId
	 */
	public void setFincChildId(String fincChildId) {
	 	this.fincChildId = fincChildId;
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
	 * @return 还款流程ID
	 */
	public String getWkfRepayId() {
	 	return wkfRepayId;
	}
	/**
	 * @设置 还款流程ID
	 * @param wkfRepayId
	 */
	public void setWkfRepayId(String wkfRepayId) {
	 	this.wkfRepayId = wkfRepayId;
	}
	/**
	 * @return mf_cus_bank_acc_manage表的ID
	 */
	public String getBankAccId() {
	 	return bankAccId;
	}
	/**
	 * @设置 mf_cus_bank_acc_manage表的ID
	 * @param bankAccId
	 */
	public void setBankAccId(String bankAccId) {
	 	this.bankAccId = bankAccId;
	}
	/**
	 * @return 业务模式 1-动产质押 2-仓单 3-保单 4-保兑仓 5-应收账款融资 6-应收账款管理
	 */
	public String getBusModel() {
	 	return busModel;
	}
	/**
	 * @设置 业务模式 1-动产质押 2-仓单 3-保单 4-保兑仓 5-应收账款融资 6-应收账款管理
	 * @param busModel
	 */
	public void setBusModel(String busModel) {
	 	this.busModel = busModel;
	}
	/**
	 * @return 合同编号
	 */
	public String getPactId() {
	 	return pactId;
	}
	/**
	 * @设置 合同编号
	 * @param pactId
	 */
	public void setPactId(String pactId) {
	 	this.pactId = pactId;
	}
	/**
	 * @return 展示合同编号,用于对外展示不做业务处理
	 */
	public String getPactNo() {
	 	return pactNo;
	}
	/**
	 * @设置 展示合同编号,用于对外展示不做业务处理
	 * @param pactNo
	 */
	public void setPactNo(String pactNo) {
	 	this.pactNo = pactNo;
	}
	/**
	 * @return 申请编号
	 */
	public String getAppId() {
	 	return appId;
	}
	/**
	 * @设置 申请编号
	 * @param appId
	 */
	public void setAppId(String appId) {
	 	this.appId = appId;
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
	 * @return 合同金额
	 */
	public Double getPactAmt() {
	 	return pactAmt;
	}
	/**
	 * @设置 合同金额
	 * @param pactAmt
	 */
	public void setPactAmt(Double pactAmt) {
	 	this.pactAmt = pactAmt;
	}
	/**
	 * @return 借据金额/放款金额，多次放款合并和借据是这里=合同金额
	 */
	public Double getPutoutAmt() {
	 	return putoutAmt;
	}
	/**
	 * @设置 借据金额/放款金额，多次放款合并和借据是这里=合同金额
	 * @param putoutAmt
	 */
	public void setPutoutAmt(Double putoutAmt) {
	 	this.putoutAmt = putoutAmt;
	}
	/**
	 * @return 实际借据金额/实际放款金额不包括放款时收取的费用利息
	 */
	public Double getPutoutAmtReal() {
	 	return putoutAmtReal;
	}
	/**
	 * @设置 实际借据金额/实际放款金额不包括放款时收取的费用利息
	 * @param putoutAmtReal
	 */
	public void setPutoutAmtReal(Double putoutAmtReal) {
	 	this.putoutAmtReal = putoutAmtReal;
	}
	/**
	 * @return 未还金额/借据余额
	 */
	public Double getLoanBal() {
	 	return loanBal;
	}
	/**
	 * @设置 未还金额/借据余额
	 * @param loanBal
	 */
	public void setLoanBal(Double loanBal) {
	 	this.loanBal = loanBal;
	}
	/**
	 * @return 还款方式
	 */
	public String getRepayType() {
	 	return repayType;
	}
	/**
	 * @设置 还款方式
	 * @param repayType
	 */
	public void setRepayType(String repayType) {
	 	this.repayType = repayType;
	}
	/**
	 * @return 贷款产品编号
	 */
	public String getKindNo() {
	 	return kindNo;
	}
	/**
	 * @设置 贷款产品编号
	 * @param kindNo
	 */
	public void setKindNo(String kindNo) {
	 	this.kindNo = kindNo;
	}
	/**
	 * @return 贷款产品名称
	 */
	public String getKindName() {
	 	return kindName;
	}
	/**
	 * @设置 贷款产品名称
	 * @param kindName
	 */
	public void setKindName(String kindName) {
	 	this.kindName = kindName;
	}
	/**
	 * @return 放款申请日期：放款申请时的日期
	 */
	public String getPutoutAppDate() {
	 	return putoutAppDate;
	}
	/**
	 * @设置 放款申请日期：放款申请时的日期
	 * @param putoutAppDate
	 */
	public void setPutoutAppDate(String putoutAppDate) {
	 	this.putoutAppDate = putoutAppDate;
	}
	/**
	 * @return 放款确认日期：最后放款确认的日期
	 */
	public String getPutoutDate() {
	 	return putoutDate;
	}
	/**
	 * @设置 放款确认日期：最后放款确认的日期
	 * @param putoutDate
	 */
	public void setPutoutDate(String putoutDate) {
	 	this.putoutDate = putoutDate;
	}
	/**
	 * @return 起息开始日期
	 */
	public String getIntstBeginDate() {
	 	return intstBeginDate;
	}
	/**
	 * @设置 起息开始日期
	 * @param intstBeginDate
	 */
	public void setIntstBeginDate(String intstBeginDate) {
	 	this.intstBeginDate = intstBeginDate;
	}
	/**
	 * @return 起息到期日期
	 */
	public String getIntstEndDate() {
	 	return intstEndDate;
	}
	/**
	 * @设置 起息到期日期
	 * @param intstEndDate
	 */
	public void setIntstEndDate(String intstEndDate) {
	 	this.intstEndDate = intstEndDate;
	}
	/**
	 * @return 展期到期日期/每次展期都更新
	 */
	public String getIntstEndDateExp() {
	 	return intstEndDateExp;
	}
	/**
	 * @设置 展期到期日期/每次展期都更新
	 * @param intstEndDateExp
	 */
	public void setIntstEndDateExp(String intstEndDateExp) {
	 	this.intstEndDateExp = intstEndDateExp;
	}
	/**
	 * @return 合同开始日期
	 */
	public String getPactBeginDate() {
	 	return pactBeginDate;
	}
	/**
	 * @设置 合同开始日期
	 * @param pactBeginDate
	 */
	public void setPactBeginDate(String pactBeginDate) {
	 	this.pactBeginDate = pactBeginDate;
	}
	/**
	 * @return 合同到期日期
	 */
	public String getPactEndDate() {
	 	return pactEndDate;
	}
	/**
	 * @设置 合同到期日期
	 * @param pactEndDate
	 */
	public void setPactEndDate(String pactEndDate) {
	 	this.pactEndDate = pactEndDate;
	}
	/**
	 * @return 合同签订日期
	 */
	public String getPactSignDate() {
	 	return pactSignDate;
	}
	/**
	 * @设置 合同签订日期
	 * @param pactSignDate
	 */
	public void setPactSignDate(String pactSignDate) {
	 	this.pactSignDate = pactSignDate;
	}
	/**
	 * @return 上次还款日期
	 */
	public String getLastReturnDate() {
	 	return lastReturnDate;
	}
	/**
	 * @设置 上次还款日期
	 * @param lastReturnDate
	 */
	public void setLastReturnDate(String lastReturnDate) {
	 	this.lastReturnDate = lastReturnDate;
	}
	/**
	 * @return 本次还款时利息计算（算头不算尾时和last_return_date 一致 首尾都算时为 last_return_date+1）
	 */
	public String getCalcIntstDate() {
	 	return calcIntstDate;
	}
	/**
	 * @设置 本次还款时利息计算（算头不算尾时和last_return_date 一致 首尾都算时为 last_return_date+1）
	 * @param calcIntstDate
	 */
	public void setCalcIntstDate(String calcIntstDate) {
	 	this.calcIntstDate = calcIntstDate;
	}
	/**
	 * @return 期限类型：1-月，2-日，3-期，4-月日
	 */
	public String getTermType() {
	 	return termType;
	}
	/**
	 * @设置 期限类型：1-月，2-日，3-期，4-月日
	 * @param termType
	 */
	public void setTermType(String termType) {
	 	this.termType = termType;
	}
	/**
	 * @return 期限月
	 */
	public Integer getTermMonth() {
	 	return termMonth;
	}
	/**
	 * @设置 期限月
	 * @param termMonth
	 */
	public void setTermMonth(Integer termMonth) {
	 	this.termMonth = termMonth;
	}
	/**
	 * @return 期限日
	 */
	public Integer getTermDay() {
	 	return termDay;
	}
	/**
	 * @设置 期限日
	 * @param termDay
	 */
	public void setTermDay(Integer termDay) {
	 	this.termDay = termDay;
	}
	/**
	 * @return 利率类型：1-年利率，2-月利率，3-日利率
	 */
	public String getRateType() {
	 	return rateType;
	}
	/**
	 * @设置 利率类型：1-年利率，2-月利率，3-日利率
	 * @param rateType
	 */
	public void setRateType(String rateType) {
	 	this.rateType = rateType;
	}
	/**
	 * @return 
	 */
	public Double getFincRate() {
	 	return fincRate;
	}
	/**
	 * @设置 
	 * @param fincRate
	 */
	public void setFincRate(Double fincRate) {
	 	this.fincRate = fincRate;
	}
	/**
	 * @return 利率浮动百分比
	 */
	public Double getFincRateFloat() {
	 	return fincRateFloat;
	}
	/**
	 * @设置 利率浮动百分比
	 * @param fincRateFloat
	 */
	public void setFincRateFloat(Double fincRateFloat) {
	 	this.fincRateFloat = fincRateFloat;
	}
	/**
	 * @return 基准利率类型 1-贷款基准利率 2-公积金贷款率 3-贴现基准利率
	 */
	public String getBaseRateType() {
	 	return baseRateType;
	}
	/**
	 * @设置 基准利率类型 1-贷款基准利率 2-公积金贷款率 3-贴现基准利率
	 * @param baseRateType
	 */
	public void setBaseRateType(String baseRateType) {
	 	this.baseRateType = baseRateType;
	}
	/**
	 * @return 基准利率类型开始日期
	 */
	public String getBaseRateDate() {
	 	return baseRateDate;
	}
	/**
	 * @设置 基准利率类型开始日期
	 * @param baseRateDate
	 */
	public void setBaseRateDate(String baseRateDate) {
	 	this.baseRateDate = baseRateDate;
	}
	/**
	 * @return 逾期利率浮动百分比
	 */
	public Double getOverFloat() {
	 	return overFloat;
	}
	/**
	 * @设置 逾期利率浮动百分比
	 * @param overFloat
	 */
	public void setOverFloat(Double overFloat) {
	 	this.overFloat = overFloat;
	}
	/**
	 * @return 
	 */
	public Double getOverRate() {
	 	return overRate;
	}
	/**
	 * @设置 
	 * @param overRate
	 */
	public void setOverRate(Double overRate) {
	 	this.overRate = overRate;
	}
	/**
	 * @return 复利利率浮动百分比
	 */
	public Double getCmpdFloat() {
	 	return cmpdFloat;
	}
	/**
	 * @设置 复利利率浮动百分比
	 * @param cmpdFloat
	 */
	public void setCmpdFloat(Double cmpdFloat) {
	 	this.cmpdFloat = cmpdFloat;
	}
	/**
	 * @return 
	 */
	public Double getCmpdRate() {
	 	return cmpdRate;
	}
	/**
	 * @设置 
	 * @param cmpdRate
	 */
	public void setCmpdRate(Double cmpdRate) {
	 	this.cmpdRate = cmpdRate;
	}
	/**
	 * @return 计息方式：1-固定利率，2-浮动利率，3-固定利息
	 */
	public String getIcType() {
	 	return icType;
	}
	/**
	 * @设置 计息方式：1-固定利率，2-浮动利率，3-固定利息
	 * @param icType
	 */
	public void setIcType(String icType) {
	 	this.icType = icType;
	}
	/**
	 * @return 申请状态/借据状态1未提交2审批中3已否决4审批通过待放款5放款已复核6已还款未复核7完结
	 */
	public String getFincSts() {
	 	return fincSts;
	}
	/**
	 * @设置 申请状态/借据状态1未提交2审批中3已否决4审批通过待放款5放款已复核6已还款未复核7完结
	 * @param fincSts
	 */
	public void setFincSts(String fincSts) {
	 	this.fincSts = fincSts;
	}
	/**
	 * @return 展期状态：0-为展期，1-已展期
	 */
	public String getExpFlag() {
	 	return expFlag;
	}
	/**
	 * @设置 展期状态：0-为展期，1-已展期
	 * @param expFlag
	 */
	public void setExpFlag(String expFlag) {
	 	this.expFlag = expFlag;
	}
	/**
	 * @return 费用状态：费用单独收取时处理该状态
	 */
	public String getFeeFlag() {
	 	return feeFlag;
	}
	/**
	 * @设置 费用状态：费用单独收取时处理该状态
	 * @param feeFlag
	 */
	public void setFeeFlag(String feeFlag) {
	 	this.feeFlag = feeFlag;
	}
	/**
	 * @return 第几次放款
	 */
	public Integer getPutoutCount() {
	 	return putoutCount;
	}
	/**
	 * @设置 第几次放款
	 * @param putoutCount
	 */
	public void setPutoutCount(Integer putoutCount) {
	 	this.putoutCount = putoutCount;
	}
	/**
	 * @return 收取前N期利息之和
	 */
	public Integer getChargeInterest() {
	 	return chargeInterest;
	}
	/**
	 * @设置 收取前N期利息之和
	 * @param chargeInterest
	 */
	public void setChargeInterest(Integer chargeInterest) {
	 	this.chargeInterest = chargeInterest;
	}
	/**
	 * @return 收取前N期费用之和
	 */
	public Integer getChargeFee() {
	 	return chargeFee;
	}
	/**
	 * @设置 收取前N期费用之和
	 * @param chargeFee
	 */
	public void setChargeFee(Integer chargeFee) {
	 	this.chargeFee = chargeFee;
	}
	/**
	 * @return 本笔借据完结时间
	 */
	public String getFinishedTime() {
	 	return finishedTime;
	}
	/**
	 * @设置 本笔借据完结时间
	 * @param finishedTime
	 */
	public void setFinishedTime(String finishedTime) {
	 	this.finishedTime = finishedTime;
	}
	/**
	 * @return 提交申请时间
	 */
	public String getAppTime() {
	 	return appTime;
	}
	/**
	 * @设置 提交申请时间
	 * @param appTime
	 */
	public void setAppTime(String appTime) {
	 	this.appTime = appTime;
	}
	/**
	 * @return 创建时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 创建时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 备注
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
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
	 * @return 供应商编号
	 */
	public String getCusNoSupplier() {
	 	return cusNoSupplier;
	}
	/**
	 * @设置 供应商编号
	 * @param cusNoSupplier
	 */
	public void setCusNoSupplier(String cusNoSupplier) {
	 	this.cusNoSupplier = cusNoSupplier;
	}
	/**
	 * @return 供应商名称
	 */
	public String getCusNameSupplier() {
	 	return cusNameSupplier;
	}
	/**
	 * @设置 供应商名称
	 * @param cusNameSupplier
	 */
	public void setCusNameSupplier(String cusNameSupplier) {
	 	this.cusNameSupplier = cusNameSupplier;
	}
	/**
	 * @return 经销商编号
	 */
	public String getCusNoSales() {
	 	return cusNoSales;
	}
	/**
	 * @设置 经销商编号
	 * @param cusNoSales
	 */
	public void setCusNoSales(String cusNoSales) {
	 	this.cusNoSales = cusNoSales;
	}
	/**
	 * @return 经销商名称
	 */
	public String getCusNameSales() {
	 	return cusNameSales;
	}
	/**
	 * @设置 经销商名称
	 * @param cusNameSales
	 */
	public void setCusNameSales(String cusNameSales) {
	 	this.cusNameSales = cusNameSales;
	}
	/**
	 * @return 核心企业编号
	 */
	public String getCusNoCore() {
	 	return cusNoCore;
	}
	/**
	 * @设置 核心企业编号
	 * @param cusNoCore
	 */
	public void setCusNoCore(String cusNoCore) {
	 	this.cusNoCore = cusNoCore;
	}
	/**
	 * @return 核心企业名称
	 */
	public String getCusNameCore() {
	 	return cusNameCore;
	}
	/**
	 * @设置 核心企业名称
	 * @param cusNameCore
	 */
	public void setCusNameCore(String cusNameCore) {
	 	this.cusNameCore = cusNameCore;
	}
	/**
	 * @return 仓储方编号
	 */
	public String getCusNoWarehouse() {
	 	return cusNoWarehouse;
	}
	/**
	 * @设置 仓储方编号
	 * @param cusNoWarehouse
	 */
	public void setCusNoWarehouse(String cusNoWarehouse) {
	 	this.cusNoWarehouse = cusNoWarehouse;
	}
	/**
	 * @return 仓储方名称
	 */
	public String getCusNameWarehouse() {
	 	return cusNameWarehouse;
	}
	/**
	 * @设置 仓储方名称
	 * @param cusNameWarehouse
	 */
	public void setCusNameWarehouse(String cusNameWarehouse) {
	 	this.cusNameWarehouse = cusNameWarehouse;
	}
	/**
	 * @return 物流厂商编号
	 */
	public String getCusNoLogistics() {
	 	return cusNoLogistics;
	}
	/**
	 * @设置 物流厂商编号
	 * @param cusNoLogistics
	 */
	public void setCusNoLogistics(String cusNoLogistics) {
	 	this.cusNoLogistics = cusNoLogistics;
	}
	/**
	 * @return 物流厂商名称
	 */
	public String getCusNameLogistics() {
	 	return cusNameLogistics;
	}
	/**
	 * @设置 物流厂商名称
	 * @param cusNameLogistics
	 */
	public void setCusNameLogistics(String cusNameLogistics) {
	 	this.cusNameLogistics = cusNameLogistics;
	}
	/**
	 * @return 信保机构编号
	 */
	public String getCusNoInsurance() {
	 	return cusNoInsurance;
	}
	/**
	 * @设置 信保机构编号
	 * @param cusNoInsurance
	 */
	public void setCusNoInsurance(String cusNoInsurance) {
	 	this.cusNoInsurance = cusNoInsurance;
	}
	/**
	 * @return 信保机构名称
	 */
	public String getCusNameInsurance() {
	 	return cusNameInsurance;
	}
	/**
	 * @设置 信保机构名称
	 * @param cusNameInsurance
	 */
	public void setCusNameInsurance(String cusNameInsurance) {
	 	this.cusNameInsurance = cusNameInsurance;
	}
	/**
	 * @return 资金机构编号
	 */
	public String getCusNoFund() {
	 	return cusNoFund;
	}
	/**
	 * @设置 资金机构编号
	 * @param cusNoFund
	 */
	public void setCusNoFund(String cusNoFund) {
	 	this.cusNoFund = cusNoFund;
	}
	/**
	 * @return 资金机构名称
	 */
	public String getCusNameFund() {
	 	return cusNameFund;
	}
	/**
	 * @设置 资金机构名称
	 * @param cusNameFund
	 */
	public void setCusNameFund(String cusNameFund) {
	 	this.cusNameFund = cusNameFund;
	}
	/**
	 * @return 是否自动分类（1是 2否）
	 */
	public String getAutoClassify() {
	 	return autoClassify;
	}
	/**
	 * @设置 是否自动分类（1是 2否）
	 * @param autoClassify
	 */
	public void setAutoClassify(String autoClassify) {
	 	this.autoClassify = autoClassify;
	}
	/**
	 * @return 是否自动分类（1系统初分 2手工分类）
	 */
	public String getClassifyMethod() {
	 	return classifyMethod;
	}
	/**
	 * @设置 是否自动分类（1系统初分 2手工分类）
	 * @param classifyMethod
	 */
	public void setClassifyMethod(String classifyMethod) {
	 	this.classifyMethod = classifyMethod;
	}
	/**
	 * @return 分类日期
	 */
	public String getClassifyDate() {
	 	return classifyDate;
	}
	/**
	 * @设置 分类日期
	 * @param classifyDate
	 */
	public void setClassifyDate(String classifyDate) {
	 	this.classifyDate = classifyDate;
	}
	/**
	 * @return 审批流程id
	 */
	public String getApproveProcessId() {
	 	return approveProcessId;
	}
	/**
	 * @设置 审批流程id
	 * @param approveProcessId
	 */
	public void setApproveProcessId(String approveProcessId) {
	 	this.approveProcessId = approveProcessId;
	}
	/**
	 * @return 审批流程
	 */
	public String getApproveProcess() {
	 	return approveProcess;
	}
	/**
	 * @设置 审批流程
	 * @param approveProcess
	 */
	public void setApproveProcess(String approveProcess) {
	 	this.approveProcess = approveProcess;
	}
	/**
	 * @return 当前审批节点编号
	 */
	public String getApproveNodeNo() {
	 	return approveNodeNo;
	}
	/**
	 * @设置 当前审批节点编号
	 * @param approveNodeNo
	 */
	public void setApproveNodeNo(String approveNodeNo) {
	 	this.approveNodeNo = approveNodeNo;
	}
	/**
	 * @return 当前审批节点名称
	 */
	public String getApproveNodeName() {
	 	return approveNodeName;
	}
	/**
	 * @设置 当前审批节点名称
	 * @param approveNodeName
	 */
	public void setApproveNodeName(String approveNodeName) {
	 	this.approveNodeName = approveNodeName;
	}
	/**
	 * @return 审批角色号/用户号
	 */
	public String getApprovePartNo() {
	 	return approvePartNo;
	}
	/**
	 * @设置 审批角色号/用户号
	 * @param approvePartNo
	 */
	public void setApprovePartNo(String approvePartNo) {
	 	this.approvePartNo = approvePartNo;
	}
	/**
	 * @return 当前审批角色/用户名称
	 */
	public String getApprovePartName() {
	 	return approvePartName;
	}
	/**
	 * @设置 当前审批角色/用户名称
	 * @param approvePartName
	 */
	public void setApprovePartName(String approvePartName) {
	 	this.approvePartName = approvePartName;
	}
	/**
	 * @return 利率调整标识：0-未调整（默认），1-已调整
	 */
	public String getRateChangeFlag() {
	 	return rateChangeFlag;
	}
	/**
	 * @设置 利率调整标识：0-未调整（默认），1-已调整
	 * @param rateChangeFlag
	 */
	public void setRateChangeFlag(String rateChangeFlag) {
	 	this.rateChangeFlag = rateChangeFlag;
	}
	/**
	 * @return 备用字段1
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 备用字段1
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 备用字段2
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 备用字段2
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 备用字段3
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 备用字段3
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 备用字段4
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 备用字段4
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return 备用字段5
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 备用字段5
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
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
	 * @return 支付方式 1-自主支付 2-受托支付
	 */
	public String getPayMethod() {
	 	return payMethod;
	}
	/**
	 * @设置 支付方式 1-自主支付 2-受托支付
	 * @param payMethod
	 */
	public void setPayMethod(String payMethod) {
	 	this.payMethod = payMethod;
	}
	/**
	 * @return 收款账户 关联mf_cus_bank_acc_manage表的ID
	 */
	public String getCollectAccId() {
	 	return collectAccId;
	}
	/**
	 * @设置 收款账户 关联mf_cus_bank_acc_manage表的ID
	 * @param collectAccId
	 */
	public void setCollectAccId(String collectAccId) {
	 	this.collectAccId = collectAccId;
	}
	/**
	 * @return 还款账户 关联mf_cus_bank_acc_manage表的ID
	 */
	public String getRepayAccId() {
	 	return repayAccId;
	}
	/**
	 * @设置 还款账户 关联mf_cus_bank_acc_manage表的ID
	 * @param repayAccId
	 */
	public void setRepayAccId(String repayAccId) {
	 	this.repayAccId = repayAccId;
	}
	/**
	 * @return 是否固定利率0否1是
	 */
	public String getIfFixRate() {
	 	return ifFixRate;
	}
	/**
	 * @设置 是否固定利率0否1是
	 * @param ifFixRate
	 */
	public void setIfFixRate(String ifFixRate) {
	 	this.ifFixRate = ifFixRate;
	}
	/**
	 * @return 
	 */
	public String getExt6() {
	 	return ext6;
	}
	/**
	 * @设置 
	 * @param ext6
	 */
	public void setExt6(String ext6) {
	 	this.ext6 = ext6;
	}
	/**
	 * @return 
	 */
	public String getExt7() {
	 	return ext7;
	}
	/**
	 * @设置 
	 * @param ext7
	 */
	public void setExt7(String ext7) {
	 	this.ext7 = ext7;
	}
	/**
	 * @return 
	 */
	public String getExt8() {
	 	return ext8;
	}
	/**
	 * @设置 
	 * @param ext8
	 */
	public void setExt8(String ext8) {
	 	this.ext8 = ext8;
	}
	/**
	 * @return 
	 */
	public String getExt9() {
	 	return ext9;
	}
	/**
	 * @设置 
	 * @param ext9
	 */
	public void setExt9(String ext9) {
	 	this.ext9 = ext9;
	}
	/**
	 * @return 
	 */
	public String getExt10() {
	 	return ext10;
	}
	/**
	 * @设置 
	 * @param ext10
	 */
	public void setExt10(String ext10) {
	 	this.ext10 = ext10;
	}
	/**
	 * @return 借据自然日放款日期
	 */
	public String getPutoutRealDate() {
	 	return putoutRealDate;
	}
	/**
	 * @设置 借据自然日放款日期
	 * @param putoutRealDate
	 */
	public void setPutoutRealDate(String putoutRealDate) {
	 	this.putoutRealDate = putoutRealDate;
	}
	/**
	 * @return 保证比例
	 */
	public Double getGuaranteeRate() {
	 	return guaranteeRate;
	}
	/**
	 * @设置 保证比例
	 * @param guaranteeRate
	 */
	public void setGuaranteeRate(Double guaranteeRate) {
	 	this.guaranteeRate = guaranteeRate;
	}
	/**
	 * @return 风险(存出)保证金
	 */
	public Double getDepositOutMarginAmt() {
	 	return depositOutMarginAmt;
	}
	/**
	 * @设置 风险(存出)保证金
	 * @param depositOutMarginAmt
	 */
	public void setDepositOutMarginAmt(Double depositOutMarginAmt) {
	 	this.depositOutMarginAmt = depositOutMarginAmt;
	}
	/**
	 * @return 风险(存出)账号
	 */
	public String getDepositOutAccountId() {
	 	return depositOutAccountId;
	}
	/**
	 * @设置 风险(存出)账号
	 * @param depositOutAccountId
	 */
	public void setDepositOutAccountId(String depositOutAccountId) {
	 	this.depositOutAccountId = depositOutAccountId;
	}
	/**
	 * @return 风险(存出)保证金时间
	 */
	public String getDepositOutTime() {
	 	return depositOutTime;
	}
	/**
	 * @设置 风险(存出)保证金时间
	 * @param depositOutTime
	 */
	public void setDepositOutTime(String depositOutTime) {
	 	this.depositOutTime = depositOutTime;
	}
}