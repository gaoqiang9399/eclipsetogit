package app.component.report.entity;


public class MfReportStatic {
	
	/*********基本情况**************/
	
	//运营资金
	private String operateAmt;
	//注册资金
	private String registerAmt;
	//从银行融入资金
	private String bankAmt;
	//其他资金
	private String otherAmt;
	//批复时间
	private String confirmDate;
	//工商注册时间
	private String registerDate;
	//开业时间
	private String openDate;
	//从业人数
	private String bussinessCnt;
	
	/***********业务情况***********/
	private String typename;

	//对应列项目名
	private String keyoption;
	
	/***本期发生**/
	//户数
	private String termOccCuscnt;
	//笔数
	private String termOccLoanCnt;
	//贷款额
	private String termOccAmt;

	/***期末余额**/
	//户数
	private String termEndCuscnt;
	//笔数
	private String termEndLoanCnt;
	//贷款余额
	private String termEndLoanBal;
	
	/***本年累计***/
	//户数
	private String yearCuscnt;
	//笔数
	private String yearLoancnt;
	//贷款额
	private String yearLoanAmt;
	
	/***开业以来累计**/
	//户数
	private String sinceOpenCuscnt;
	//笔数
	private String sinceOpenLoancnt;
	//贷款额
	private String sinceOpenLoanAmt;
	//平均贷款利率（%）
	private String waverageraterate;
	//逾期贷款
	private String wtimelimitloan;
	//贷款损失
	private String wloanlosses;
	//本期
	//资产总额（余额）
	private String aaLoanbal;
	//其中：固定资产（余额）
	private String baFixedbal;
	// 经营收入合计
	private String caTotalamt;
	//①利息收入
	private String daIncome;
	//各项贷款利息收入
	private String ealoanInit;
	//金融机构往来利息收入
	private String faFinaInit;
	// ②其他收入
	private String gaFinaInit;
	private String defaultVal = "0.00";
	private String totalCusCnt = "0.00";
	private String totalLoanCnt = "0.00";
	private String totalAmt = "0.00";
	private String totalTermEndCusCnt = "0.00";
	private String totalTermEndLoanCnt = "0.00";
	private String totalTermEndAmt = "0.00";
	private String totalYearCusCnt = "0.00";
	private String totalYearLoanCnt = "0.00";
	private String totalYearLoanAmt = "0.00";
	private String totalOpenCuscnt = "0.00";
	private String totalOpenLoancnt = "0.00";
	private String totalOpenLoanAmt = "0.00";
	
	public String getOperateAmt() {
		return operateAmt;
	}
	public void setOperateAmt(String operateAmt) {
		this.operateAmt = operateAmt;
	}
	public String getRegisterAmt() {
		return registerAmt;
	}
	public void setRegisterAmt(String registerAmt) {
		this.registerAmt = registerAmt;
	}
	public String getBankAmt() {
		return bankAmt;
	}
	public void setBankAmt(String bankAmt) {
		this.bankAmt = bankAmt;
	}
	public String getOtherAmt() {
		return otherAmt;
	}
	public void setOtherAmt(String otherAmt) {
		this.otherAmt = otherAmt;
	}
	public String getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getBussinessCnt() {
		return bussinessCnt;
	}
	public void setBussinessCnt(String bussinessCnt) {
		this.bussinessCnt = bussinessCnt;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getKeyoption() {
		return keyoption;
	}
	public void setKeyoption(String keyoption) {
		this.keyoption = keyoption;
	}
	public String getTermOccCuscnt() {
		return termOccCuscnt;
	}
	public void setTermOccCuscnt(String termOccCuscnt) {
		this.termOccCuscnt = termOccCuscnt;
	}
	public String getTermOccLoanCnt() {
		return termOccLoanCnt;
	}
	public void setTermOccLoanCnt(String termOccLoanCnt) {
		this.termOccLoanCnt = termOccLoanCnt;
	}
	public String getTermOccAmt() {
		return termOccAmt;
	}
	public void setTermOccAmt(String termOccAmt) {
		this.termOccAmt = termOccAmt;
	}
	public String getTermEndCuscnt() {
		return termEndCuscnt;
	}
	public void setTermEndCuscnt(String termEndCuscnt) {
		this.termEndCuscnt = termEndCuscnt;
	}
	public String getTermEndLoanCnt() {
		return termEndLoanCnt;
	}
	public void setTermEndLoanCnt(String termEndLoanCnt) {
		this.termEndLoanCnt = termEndLoanCnt;
	}
	public String getTermEndLoanBal() {
		return termEndLoanBal;
	}
	public void setTermEndLoanBal(String termEndLoanBal) {
		this.termEndLoanBal = termEndLoanBal;
	}
	public String getYearCuscnt() {
		return yearCuscnt;
	}
	public void setYearCuscnt(String yearCuscnt) {
		this.yearCuscnt = yearCuscnt;
	}
	public String getYearLoancnt() {
		return yearLoancnt;
	}
	public void setYearLoancnt(String yearLoancnt) {
		this.yearLoancnt = yearLoancnt;
	}
	public String getYearLoanAmt() {
		return yearLoanAmt;
	}
	public void setYearLoanAmt(String yearLoanAmt) {
		this.yearLoanAmt = yearLoanAmt;
	}
	public String getSinceOpenCuscnt() {
		return sinceOpenCuscnt;
	}
	public void setSinceOpenCuscnt(String sinceOpenCuscnt) {
		this.sinceOpenCuscnt = sinceOpenCuscnt;
	}
	public String getSinceOpenLoancnt() {
		return sinceOpenLoancnt;
	}
	public void setSinceOpenLoancnt(String sinceOpenLoancnt) {
		this.sinceOpenLoancnt = sinceOpenLoancnt;
	}
	public String getSinceOpenLoanAmt() {
		return sinceOpenLoanAmt;
	}
	public void setSinceOpenLoanAmt(String sinceOpenLoanAmt) {
		this.sinceOpenLoanAmt = sinceOpenLoanAmt;
	}
	public String getWaverageraterate() {
		return waverageraterate;
	}
	public void setWaverageraterate(String waverageraterate) {
		this.waverageraterate = waverageraterate;
	}
	public String getWtimelimitloan() {
		return wtimelimitloan;
	}
	public void setWtimelimitloan(String wtimelimitloan) {
		this.wtimelimitloan = wtimelimitloan;
	}
	public String getWloanlosses() {
		return wloanlosses;
	}
	public void setWloanlosses(String wloanlosses) {
		this.wloanlosses = wloanlosses;
	}
	public String getAaLoanbal() {
		return aaLoanbal;
	}
	public void setAaLoanbal(String aaLoanbal) {
		this.aaLoanbal = aaLoanbal;
	}
	public String getBaFixedbal() {
		return baFixedbal;
	}
	public void setBaFixedbal(String baFixedbal) {
		this.baFixedbal = baFixedbal;
	}
	public String getCaTotalamt() {
		return caTotalamt;
	}
	public void setCaTotalamt(String caTotalamt) {
		this.caTotalamt = caTotalamt;
	}
	public String getDaIncome() {
		return daIncome;
	}
	public void setDaIncome(String daIncome) {
		this.daIncome = daIncome;
	}
	public String getEaloanInit() {
		return ealoanInit;
	}
	public void setEaloanInit(String ealoanInit) {
		this.ealoanInit = ealoanInit;
	}
	public String getFaFinaInit() {
		return faFinaInit;
	}
	public void setFaFinaInit(String faFinaInit) {
		this.faFinaInit = faFinaInit;
	}
	public String getGaFinaInit() {
		return gaFinaInit;
	}
	public void setGaFinaInit(String gaFinaInit) {
		this.gaFinaInit = gaFinaInit;
	}
	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	public String getTotalCusCnt() {
		return totalCusCnt;
	}
	public void setTotalCusCnt(String totalCusCnt) {
		this.totalCusCnt = totalCusCnt;
	}
	public String getTotalLoanCnt() {
		return totalLoanCnt;
	}
	public void setTotalLoanCnt(String totalLoanCnt) {
		this.totalLoanCnt = totalLoanCnt;
	}
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getTotalTermEndCusCnt() {
		return totalTermEndCusCnt;
	}
	public void setTotalTermEndCusCnt(String totalTermEndCusCnt) {
		this.totalTermEndCusCnt = totalTermEndCusCnt;
	}
	public String getTotalTermEndLoanCnt() {
		return totalTermEndLoanCnt;
	}
	public void setTotalTermEndLoanCnt(String totalTermEndLoanCnt) {
		this.totalTermEndLoanCnt = totalTermEndLoanCnt;
	}
	public String getTotalTermEndAmt() {
		return totalTermEndAmt;
	}
	public void setTotalTermEndAmt(String totalTermEndAmt) {
		this.totalTermEndAmt = totalTermEndAmt;
	}
	public String getTotalYearCusCnt() {
		return totalYearCusCnt;
	}
	public void setTotalYearCusCnt(String totalYearCusCnt) {
		this.totalYearCusCnt = totalYearCusCnt;
	}
	public String getTotalYearLoanCnt() {
		return totalYearLoanCnt;
	}
	public void setTotalYearLoanCnt(String totalYearLoanCnt) {
		this.totalYearLoanCnt = totalYearLoanCnt;
	}
	public String getTotalYearLoanAmt() {
		return totalYearLoanAmt;
	}
	public void setTotalYearLoanAmt(String totalYearLoanAmt) {
		this.totalYearLoanAmt = totalYearLoanAmt;
	}
	public String getTotalOpenCuscnt() {
		return totalOpenCuscnt;
	}
	public void setTotalOpenCuscnt(String totalOpenCuscnt) {
		this.totalOpenCuscnt = totalOpenCuscnt;
	}
	public String getTotalOpenLoancnt() {
		return totalOpenLoancnt;
	}
	public void setTotalOpenLoancnt(String totalOpenLoancnt) {
		this.totalOpenLoancnt = totalOpenLoancnt;
	}
	public String getTotalOpenLoanAmt() {
		return totalOpenLoanAmt;
	}
	public void setTotalOpenLoanAmt(String totalOpenLoanAmt) {
		this.totalOpenLoanAmt = totalOpenLoanAmt;
	}
	
}
