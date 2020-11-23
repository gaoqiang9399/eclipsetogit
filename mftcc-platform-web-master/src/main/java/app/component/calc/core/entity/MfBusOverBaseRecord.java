package app.component.calc.core.entity;

import app.base.BaseDomain;

/**
 * Title: MfBusOverBaseRecord.java Description:
 * 
 * @author：kaifa@dhcc.com.cn
 * @Mon Jun 12 15:48:59 CST 2017
 * @version：1.0
 **/
public class MfBusOverBaseRecord extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = -236069331453984542L;
	private String overId;// 流水号
	private String repayId;// 还款历史总表mf_repay_history的repay_id
	private String fincId;// 借据号
	private String pactId;// 合同编号
	private Double pactAmt;// 合同金额
	private Double putoutAmt;// 借据金额
	private Double loanBal;// 借据余额
	private Double repayPrcp;// 本期计划应还剩余本金
	private Double repayIntst;// 本期计划应还剩余利息
	private Double feeSum;// 本期计划应还剩余费用合计
	private String feeSumInfo;//剩余未还费用项明细存放每个费用剩余金额 {"feeAmt1":"10.00","feeAmt2":"60.00","feeAmt3":"50.00"}
	private Double penaltySum;// 本期计划应还剩余违约金
	private Double overIntst;// 本期应还逾期利息剩余部分
	private Double overIntstFloatPart;//本期应还逾期浮动利率值 逾期利息剩余部分 （通过逾期利率浮动值计算 存0.5计算出的值）
	private Double cmpdIntst;// 本期应还复利利息剩余部分
	private Double cmpdIntstFloatPart;//本期应还复利浮动利率值 复利利息剩余部分 （通过复利利率浮动值计算 存0.5计算出的值）
	private Double feesumFaxi;//本期应还逾期费用罚息剩余部分
	private Double fincRate;// 利率：换算成年利率存储%
	private Double overRate;// 逾期利息利率%
	private Double cmpdRate;// 复利利息利率%
	private Integer termNum;// 期号
	private String planBeginDate;// 本期还款开始日期
	private String planEndDate;// 本期计划还款日期(本期结束日期)
	private String expiryIntstDate;// 本期结息日期
	/**
	 * 转逾期日期，该期数据转为逾期时的时间，部分还款后转为逾期时修改日期
	 */
	private String overDate;
	/**
	 * 逾期插入时与planEndDate相同，每次还款后更新与calcIntstDate相同，<br>
	 * 本期完结后就不在更新
	 */
	private String overDateCalc;
	/**
	 * 上次还款日期，与借据主表保持一致，逾期插入时=""，本期完结后就不在更新
	 */
	private String lastReturnDate;
	private String outFlag;// 还款计划状态（0 -未还款 1-已还完 2-部分还款 3-逾期）
	private String regTime;// 登记时间17为格式20170506 15:35:40
	private String lstModTime;// 修改时间 17为格式20170506 15:35:40
	private String opNo;// 登记人员编号
	private String opName;// 登记人员名称
	private String brNo;// 登记人员部门编号
	private String brName;// 登记人员部门名称
	private String ext1;// 备用字段1
	private String ext2;// 备用字段2
	private String ext3;// 备用字段3
	private String ext4;// 备用字段4
	private String ext5;// 备用字段5
	
	private String regDate;//登记日期
	private String lstModDate;//上次修改日期
	//本期应还罚息剩余部分
	private  Double shouldPenaltyRate;
	//本期应还已结罚息剩余部分
	private  Double hasPenaltyRate;
	//逾期时存放前N期逾期利息金额之和
	private Double overIntstBeforeTerm;
	//借据停息标志 0 未停息 1 已停息
	private String stopIntstFlag;
	//借据停息日期（这一天之后不计算相关利息）
	private String stopIntstDate;
	//应计本金（应该还而未还的所有本金之和） 如果 产品业务关联表中  cmpd_intst_calc_type  为 1 时 如果通过逾期批量 结息日计算相关金额
	private Double accruedPrcp;
	//应计利息（应该还而未还的所有正常利息+逾期利息(1的)）如果 产品业务关联表中  cmpd_intst_calc_type  为 1 时 如果通过逾期批量 结息日计算相关金额
	private Double accruedIntst;
	/**
	 * @return 流水号
	 */
	public String getOverId() {
		return overId;
	}

	/**
	 * @设置 流水号
	 * @param overId
	 */
	public void setOverId(String overId) {
		this.overId = overId;
	}

	/**
	 * @return 还款历史总表mf_repay_history的repay_id
	 */
	public String getRepayId() {
		return repayId;
	}

	/**
	 * @设置 还款历史总表mf_repay_history的repay_id
	 * @param repayId
	 */
	public void setRepayId(String repayId) {
		this.repayId = repayId;
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
	 * @return 借据金额
	 */
	public Double getPutoutAmt() {
		return putoutAmt;
	}

	/**
	 * @设置 借据金额
	 * @param putoutAmt
	 */
	public void setPutoutAmt(Double putoutAmt) {
		this.putoutAmt = putoutAmt;
	}

	/**
	 * @return 借据余额
	 */
	public Double getLoanBal() {
		return loanBal;
	}

	/**
	 * @设置 借据余额
	 * @param loanBal
	 */
	public void setLoanBal(Double loanBal) {
		this.loanBal = loanBal;
	}

	/**
	 * @return 本期计划应还剩余本金
	 */
	public Double getRepayPrcp() {
		return repayPrcp;
	}

	/**
	 * @设置 本期计划应还剩余本金
	 * @param repayPrcp
	 */
	public void setRepayPrcp(Double repayPrcp) {
		this.repayPrcp = repayPrcp;
	}

	/**
	 * @return 本期计划应还剩余利息
	 */
	public Double getRepayIntst() {
		return repayIntst;
	}

	/**
	 * @设置 本期计划应还剩余利息
	 * @param repayIntst
	 */
	public void setRepayIntst(Double repayIntst) {
		this.repayIntst = repayIntst;
	}

	/**
	 * @return 本期计划应还剩余费用合计
	 */
	public Double getFeeSum() {
		return feeSum;
	}

	/**
	 * @设置 本期计划应还剩余费用合计
	 * @param feeSum
	 */
	public void setFeeSum(Double feeSum) {
		this.feeSum = feeSum;
	}

	/**
	 * 本期计划应还剩余违约金
	 * 
	 * @return
	 */
	public Double getPenaltySum() {
		return penaltySum;
	}

	/**
	 * 本期计划应还剩余违约金
	 * 
	 * @param penaltySum
	 */
	public void setPenaltySum(Double penaltySum) {
		this.penaltySum = penaltySum;
	}

	/**
	 * 本期应还逾期利息剩余部分
	 * 
	 * @return
	 */
	public Double getOverIntst() {
		return overIntst;
	}

	/**
	 * 本期应还逾期利息剩余部分
	 * 
	 * @param overIntst
	 */
	public void setOverIntst(Double overIntst) {
		this.overIntst = overIntst;
	}
	/**
	 * 本期应还逾期浮动利率值 逾期利息剩余部分 （通过逾期利率浮动值计算 存0.5计算出的值）
	 */
	public Double getOverIntstFloatPart() {
		return overIntstFloatPart;
	}
	/**
	 * 本期应还逾期浮动利率值 逾期利息剩余部分 （通过逾期利率浮动值计算 存0.5计算出的值）
	 */
	public void setOverIntstFloatPart(Double overIntstFloatPart) {
		this.overIntstFloatPart = overIntstFloatPart;
	}

	/**
	 * 本期应还复利利息剩余部分
	 * 
	 * @return
	 */
	public Double getCmpdIntst() {
		return cmpdIntst;
	}

	/**
	 * 本期应还复利利息剩余部分
	 * 
	 * @param cmpdIntst
	 */
	public void setCmpdIntst(Double cmpdIntst) {
		this.cmpdIntst = cmpdIntst;
	}
	/**
	 * 本期应还复利浮动利率值 复利利息剩余部分 （通过复利利率浮动值计算 存0.5计算出的值）
	 */
	public Double getCmpdIntstFloatPart() {
		return cmpdIntstFloatPart;
	}
	/**
	 * 本期应还复利浮动利率值 复利利息剩余部分 （通过复利利率浮动值计算 存0.5计算出的值）
	 */
	public void setCmpdIntstFloatPart(Double cmpdIntstFloatPart) {
		this.cmpdIntstFloatPart = cmpdIntstFloatPart;
	}
	/**
	 * 本期应还逾期费用罚息剩余部分
	 */
	public Double getFeesumFaxi() {
		return feesumFaxi;
	}
	/**
	 * 本期应还逾期费用罚息剩余部分
	 */
	public void setFeesumFaxi(Double feesumFaxi) {
		this.feesumFaxi = feesumFaxi;
	}

	/**
	 * @return 利率：换算成年利率存储%
	 */
	public Double getFincRate() {
		return fincRate;
	}

	/**
	 * @设置 利率：换算成年利率存储%
	 * @param fincRate
	 */
	public void setFincRate(Double fincRate) {
		this.fincRate = fincRate;
	}

	/**
	 * @return 逾期利息利率%
	 */
	public Double getOverRate() {
		return overRate;
	}

	/**
	 * @设置 逾期利息利率%
	 * @param overRate
	 */
	public void setOverRate(Double overRate) {
		this.overRate = overRate;
	}

	/**
	 * @return 复利利息利率%
	 */
	public Double getCmpdRate() {
		return cmpdRate;
	}

	/**
	 * @设置 复利利息利率%
	 * @param cmpdRate
	 */
	public void setCmpdRate(Double cmpdRate) {
		this.cmpdRate = cmpdRate;
	}

	/**
	 * @return 期号
	 */
	public Integer getTermNum() {
		return termNum;
	}

	/**
	 * @设置 期号
	 * @param termNum
	 */
	public void setTermNum(Integer termNum) {
		this.termNum = termNum;
	}

	/**
	 * @return 本期还款开始日期
	 */
	public String getPlanBeginDate() {
		return planBeginDate;
	}

	/**
	 * @设置 本期还款开始日期
	 * @param planBeginDate
	 */
	public void setPlanBeginDate(String planBeginDate) {
		this.planBeginDate = planBeginDate;
	}

	/**
	 * @return 本期计划还款日期(本期结束日期)
	 */
	public String getPlanEndDate() {
		return planEndDate;
	}

	/**
	 * @设置 本期计划还款日期(本期结束日期)
	 * @param planEndDate
	 */
	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}

	/**
	 * @return 本期结息日期
	 */
	public String getExpiryIntstDate() {
		return expiryIntstDate;
	}

	/**
	 * @设置 本期结息日期
	 * @param expiryIntstDate
	 */
	public void setExpiryIntstDate(String expiryIntstDate) {
		this.expiryIntstDate = expiryIntstDate;
	}

	/**
	 * @return 转逾期日期，该期数据转为逾期时的时间，部分还款后转为逾期时修改日期
	 */
	public String getOverDate() {
		return overDate;
	}

	/**
	 * @设置 转逾期日期，该期数据转为逾期时的时间，部分还款后转为逾期时修改日期
	 * @param overDate
	 */
	public void setOverDate(String overDate) {
		this.overDate = overDate;
	}

	/**
	 * 逾期插入时与planEndDate相同，每次还款后更新与calcIntstDate相同，<br>
	 * 本期完结后就不在更新
	 */
	public String getOverDateCalc() {
		return overDateCalc;
	}

	/**
	 * 逾期插入时与planEndDate相同，每次还款后更新与calcIntstDate相同，<br>
	 * 本期完结后就不在更新
	 */
	public void setOverDateCalc(String overDateCalc) {
		this.overDateCalc = overDateCalc;
	}

	/**
	 * @return 上次还款日期，与借据主表保持一致，逾期插入时=""，本期完结后就不在更新
	 */
	public String getLastReturnDate() {
		return lastReturnDate;
	}

	/**
	 * @设置 上次还款日期，与借据主表保持一致，逾期插入时=""，本期完结后就不在更新
	 * @param lastReturnDate
	 */
	public void setLastReturnDate(String lastReturnDate) {
		this.lastReturnDate = lastReturnDate;
	}

	/**
	 * @return 还款计划状态（0 -未还款 1-已还完 2-部分还款 3-逾期）
	 */
	public String getOutFlag() {
		return outFlag;
	}

	/**
	 * @设置 还款计划状态（0 -未还款 1-已还完 2-部分还款 3-逾期）
	 * @param outFlag
	 */
	public void setOutFlag(String outFlag) {
		this.outFlag = outFlag;
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

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getLstModDate() {
		return lstModDate;
	}

	public void setLstModDate(String lstModDate) {
		this.lstModDate = lstModDate;
	}

	public String getFeeSumInfo() {
		return feeSumInfo;
	}

	public void setFeeSumInfo(String feeSumInfo) {
		this.feeSumInfo = feeSumInfo;
	}

	public Double getShouldPenaltyRate() {
		return shouldPenaltyRate;
	}

	public void setShouldPenaltyRate(Double shouldPenaltyRate) {
		this.shouldPenaltyRate = shouldPenaltyRate;
	}

	public Double getHasPenaltyRate() {
		return hasPenaltyRate;
	}

	public void setHasPenaltyRate(Double hasPenaltyRate) {
		this.hasPenaltyRate = hasPenaltyRate;
	}

	public Double getOverIntstBeforeTerm() {
		return overIntstBeforeTerm;
	}

	public void setOverIntstBeforeTerm(Double overIntstBeforeTerm) {
		this.overIntstBeforeTerm = overIntstBeforeTerm;
	}

	public String getStopIntstFlag() {
		return stopIntstFlag;
	}

	public void setStopIntstFlag(String stopIntstFlag) {
		this.stopIntstFlag = stopIntstFlag;
	}

	public String getStopIntstDate() {
		return stopIntstDate;
	}

	public void setStopIntstDate(String stopIntstDate) {
		this.stopIntstDate = stopIntstDate;
	}

	/**
	 *应计本金（应该还而未还的所有本金之和） 如果 产品业务关联表中  cmpd_intst_calc_type  为 1 时 如果通过逾期批量 结息日计算相关金额
	 */
	public Double getAccruedPrcp() {
		return accruedPrcp;
	}
	/**
	 *应计本金（应该还而未还的所有本金之和） 如果 产品业务关联表中  cmpd_intst_calc_type  为 1 时 如果通过逾期批量 结息日计算相关金额
	 */
	public void setAccruedPrcp(Double accruedPrcp) {
		this.accruedPrcp = accruedPrcp;
	}
	/**
	 *应计利息（应该还而未还的所有正常利息+逾期利息(1的)）如果 产品业务关联表中  cmpd_intst_calc_type  为 1 时 如果通过逾期批量 结息日计算相关金额
	 */
	public Double getAccruedIntst() {
		return accruedIntst;
	}
	/**
	 *应计利息（应该还而未还的所有正常利息+逾期利息(1的)）如果 产品业务关联表中  cmpd_intst_calc_type  为 1 时 如果通过逾期批量 结息日计算相关金额
	 */
	public void setAccruedIntst(Double accruedIntst) {
		this.accruedIntst = accruedIntst;
	}
}