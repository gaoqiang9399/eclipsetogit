package app.component.calc.core.entity;
/**
 * 描述：还款相关参数
 * @author WD
 *
 */
/**
 * @author WD
 *
 */
public class RepaymentParameter {
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String appId;//申请号
	private String fincId;//借据号
	private String pactId;//合同编号
	private String planId;//还款计划中id
	private String repayId;//还款历史总表中id 
	private Double loanBal;//本次还款时剩余的金额（还款之前 剩余未还本金）
	private String appName;//项目名称
	private String repayType;//还款类型：1-正常还款，2-逾期还款，3-提前还款	
	private String accountFlag;//记账标识 0-未记账，1-记账
	private String paymentType;//支付方式：1--现金，2--银行转帐
	private Double shiShouBenJin;// 实收本金
	private Double shiShouLiXi;// 实收本金的利息
	private Double shiShouZongE;//本期实收总额（对应还款页面上输入的实收总额）
	private String huanKuanRiQi;// 还款日期
	private Integer qiHao;//期号
	private Double  shiShouYuQiLx;//实收逾期利息
	private Double  shiShouFuLiLx;//实收复利利息
	private Double shiShouFaXi;// 实收罚息
	private Double shiShouWeiYueJin;// 实收收取的违约金
	private Double shiShouFeiYong;// 实收费用
	private Double benCiJieYu;// 本次结余
	private Double benCiChongDi;// 本次冲抵
	private Double shangCiJieYu;// 上次结余
	private Double jianMianZhengChangLiXi;//减免正常利息
	private Double jianMianYuQiLiXi;//减免逾期利息
	private Double jianMianFuliLiXi;//减免复利利息
	private Double jianMianWeiYueJin;//减免违约金
	private Double jianMianLiFaXi;//减免罚息
	private Double jianMianFeiYongHeJi;//减免费用合计
	private Double jianMianHeJi;//减免总和
	private Double shiHuanZongEr;//实还总额=shiShouZongE+benCiChongDi
	private Double yingShouZongJiAll;//应收总额（正常情况下应该还的金额）
	private String ShiJiOutFlag;// 实际还款后还款计划状态0-未还款，1-已还款，2-部分还款   3-欠款，
	private String opNo;//登记人员编号
	private String opName;//登记人员名称
	private String brNo;//登记人员部门编号
	private String brName;//登记人员部门名称
	private String repaymentOrder;//还款顺序
	private String yearDays;//一年多少天
	private int baoliuXiaoShuWei;//还款总额保留小数位数 0-不保留1-保留一位2-保留两位(默认2位小数)
	private String xiaoShuoSheRu;//还款总额保留小数舍入 0-取底1-取顶2-四舍五入(默认四舍五入)
	private String jieYuTuiKuan;//结余处理方式：1-冲抵贷款、2-退款
	private String shiFouShouQuFuLi;//是否收取复利利息   1 不收取 2 收取
	private String calcIntstFlag;//获取利息计算区间标志  1-算头不算尾 2-首尾都计算
	/**
	 * 利随本清利息收取方式 1-分次部分收取， 2-一次性全部收取
	 */
	private String lsbqChargeIntst;	
	//利息是否支持减免 1-是 0-否
	private String shiFouJianMianLiXi;
	// 利息收息方式：1-上收息 2-下收息
	private String interestCollectType;
	private String ext1;//还款历史总表 存放：网信还款方式 还款渠道   0:-支付宝 1-微信  2-网银   3-柜面还款  4-手机银行还款  5-系统手动还款  6-划扣还款
	//还款日与法定节假日或周末重合时逾期后逾期利息、复利收取方式 0 不收取  1 收取
	private String festivalType;//0 不收取  1 收取
	//到期还本按月结息支持按日计息（正常还款时是收取到当天的利息还是收取整月的利息）1 收取到当天利息 2 收取整月利息
	private String repayChargeIntstFlag;
	//还款方式
	private String repayMethod;
	
	public String getCusNo() {
		return cusNo;
	}
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
	}
	public String getPactId() {
		return pactId;
	}
	public void setPactId(String pactId) {
		this.pactId = pactId;
	}
	public Double getLoanBal() {
		return loanBal;
	}
	public void setLoanBal(Double loanBal) {
		this.loanBal = loanBal;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Double getShiShouBenJin() {
		return shiShouBenJin;
	}
	public void setShiShouBenJin(Double shiShouBenJin) {
		this.shiShouBenJin = shiShouBenJin;
	}
	public Double getShiShouLiXi() {
		return shiShouLiXi;
	}
	public void setShiShouLiXi(Double shiShouLiXi) {
		this.shiShouLiXi = shiShouLiXi;
	}
	public Double getShiShouZongE() {
		return shiShouZongE;
	}
	public void setShiShouZongE(Double shiShouZongE) {
		this.shiShouZongE = shiShouZongE;
	}
	public String getHuanKuanRiQi() {
		return huanKuanRiQi;
	}
	public void setHuanKuanRiQi(String huanKuanRiQi) {
		this.huanKuanRiQi = huanKuanRiQi;
	}
	public Integer getQiHao() {
		return qiHao;
	}
	public void setQiHao(Integer qiHao) {
		this.qiHao = qiHao;
	}

	public Double getShiShouYuQiLx() {
		return shiShouYuQiLx;
	}
	public void setShiShouYuQiLx(Double shiShouYuQiLx) {
		this.shiShouYuQiLx = shiShouYuQiLx;
	}
	public Double getShiShouFuLiLx() {
		return shiShouFuLiLx;
	}
	public void setShiShouFuLiLx(Double shiShouFuLiLx) {
		this.shiShouFuLiLx = shiShouFuLiLx;
	}
	public Double getShiShouFaXi() {
		return shiShouFaXi;
	}
	public void setShiShouFaXi(Double shiShouFaXi) {
		this.shiShouFaXi = shiShouFaXi;
	}

	public Double getShiShouWeiYueJin() {
		return shiShouWeiYueJin;
	}
	public void setShiShouWeiYueJin(Double shiShouWeiYueJin) {
		this.shiShouWeiYueJin = shiShouWeiYueJin;
	}
	public Double getShiShouFeiYong() {
		return shiShouFeiYong;
	}
	public void setShiShouFeiYong(Double shiShouFeiYong) {
		this.shiShouFeiYong = shiShouFeiYong;
	}
	public Double getBenCiJieYu() {
		return benCiJieYu;
	}
	public void setBenCiJieYu(Double benCiJieYu) {
		this.benCiJieYu = benCiJieYu;
	}
	public Double getBenCiChongDi() {
		return benCiChongDi;
	}
	public void setBenCiChongDi(Double benCiChongDi) {
		this.benCiChongDi = benCiChongDi;
	}
	public Double getShangCiJieYu() {
		return shangCiJieYu;
	}
	public void setShangCiJieYu(Double shangCiJieYu) {
		this.shangCiJieYu = shangCiJieYu;
	}
	public Double getJianMianLiFaXi() {
		return jianMianLiFaXi;
	}
	public void setJianMianLiFaXi(Double jianMianLiFaXi) {
		this.jianMianLiFaXi = jianMianLiFaXi;
	}
	public Double getJianMianWeiYueJin() {
		return jianMianWeiYueJin;
	}
	public void setJianMianWeiYueJin(Double jianMianWeiYueJin) {
		this.jianMianWeiYueJin = jianMianWeiYueJin;
	}
    
	public Double getJianMianZhengChangLiXi() {
		return jianMianZhengChangLiXi;
	}
	public void setJianMianZhengChangLiXi(Double jianMianZhengChangLiXi) {
		this.jianMianZhengChangLiXi = jianMianZhengChangLiXi;
	}
	public Double getJianMianYuQiLiXi() {
		return jianMianYuQiLiXi;
	}
	public void setJianMianYuQiLiXi(Double jianMianYuQiLiXi) {
		this.jianMianYuQiLiXi = jianMianYuQiLiXi;
	}
	public Double getJianMianFuliLiXi() {
		return jianMianFuliLiXi;
	}
	public void setJianMianFuliLiXi(Double jianMianFuliLiXi) {
		this.jianMianFuliLiXi = jianMianFuliLiXi;
	}
	public Double getJianMianFeiYongHeJi() {
		return jianMianFeiYongHeJi;
	}
	public void setJianMianFeiYongHeJi(Double jianMianFeiYongHeJi) {
		this.jianMianFeiYongHeJi = jianMianFeiYongHeJi;
	}
	public String getOpNo() {
		return opNo;
	}
	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getRepayType() {
		return repayType;
	}
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	public String getAccountFlag() {
		return accountFlag;
	}
	public void setAccountFlag(String accountFlag) {
		this.accountFlag = accountFlag;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public Double getJianMianHeJi() {
		return jianMianHeJi;
	}
	public void setJianMianHeJi(Double jianMianHeJi) {
		this.jianMianHeJi = jianMianHeJi;
	}
	public Double getShiHuanZongEr() {
		return shiHuanZongEr;
	}
	public void setShiHuanZongEr(Double shiHuanZongEr) {
		this.shiHuanZongEr = shiHuanZongEr;
	}
	public Double getYingShouZongJiAll() {
		return yingShouZongJiAll;
	}
	public void setYingShouZongJiAll(Double yingShouZongJiAll) {
		this.yingShouZongJiAll = yingShouZongJiAll;
	}
	public String getShiJiOutFlag() {
		return ShiJiOutFlag;
	}
	public void setShiJiOutFlag(String shiJiOutFlag) {
		ShiJiOutFlag = shiJiOutFlag;
	}
	public String getRepayId() {
		return repayId;
	}
	public void setRepayId(String repayId) {
		this.repayId = repayId;
	}
	public String getRepaymentOrder() {
		return repaymentOrder;
	}
	public void setRepaymentOrder(String repaymentOrder) {
		this.repaymentOrder = repaymentOrder;
	}
	public String getYearDays() {
		return yearDays;
	}
	public void setYearDays(String yearDays) {
		this.yearDays = yearDays;
	}
	public int getBaoliuXiaoShuWei() {
		return baoliuXiaoShuWei;
	}
	public void setBaoliuXiaoShuWei(int baoliuXiaoShuWei) {
		this.baoliuXiaoShuWei = baoliuXiaoShuWei;
	}
	public String getXiaoShuoSheRu() {
		return xiaoShuoSheRu;
	}
	public void setXiaoShuoSheRu(String xiaoShuoSheRu) {
		this.xiaoShuoSheRu = xiaoShuoSheRu;
	}
	public String getJieYuTuiKuan() {
		return jieYuTuiKuan;
	}
	public void setJieYuTuiKuan(String jieYuTuiKuan) {
		this.jieYuTuiKuan = jieYuTuiKuan;
	}
	public String getShiFouJianMianLiXi() {
		return shiFouJianMianLiXi;
	}
	public void setShiFouJianMianLiXi(String shiFouJianMianLiXi) {
		this.shiFouJianMianLiXi = shiFouJianMianLiXi;
	}
	public String getShiFouShouQuFuLi() {
		return shiFouShouQuFuLi;
	}
	public void setShiFouShouQuFuLi(String shiFouShouQuFuLi) {
		this.shiFouShouQuFuLi = shiFouShouQuFuLi;
	}
	/**
	 * 利随本清利息收取方式 1-分次部分收取， 2-一次性全部收取
	 */
	public String getLsbqChargeIntst() {
		return lsbqChargeIntst;
	}
	/**
	 * 利随本清利息收取方式 1-分次部分收取， 2-一次性全部收取
	 */
	public void setLsbqChargeIntst(String lsbqChargeIntst) {
		this.lsbqChargeIntst = lsbqChargeIntst;
	}
	/**
	 * 获取利息计算区间标志  1-算头不算尾 2-首尾都计算
	 */
	public String getCalcIntstFlag() {
		return calcIntstFlag;
	}
	/**
	 * 获取利息计算区间标志  1-算头不算尾 2-首尾都计算
	 */
	public void setCalcIntstFlag(String calcIntstFlag) {
		this.calcIntstFlag = calcIntstFlag;
	}
	/**
	 * 利息收息方式：1-上收息 2-下收息
	 */
	public String getInterestCollectType() {
		return interestCollectType;
	}
	/**
	 * 利息收息方式：1-上收息 2-下收息
	 */
	public void setInterestCollectType(String interestCollectType) {
		this.interestCollectType = interestCollectType;
	}
	/**
	 * 网信还款方式：还款渠道   0:-支付宝 1-微信  2-网银   3-柜面还款  4-手机银行还款  5-系统手动还款  6-划扣还款
	 */
	public String getExt1() {
		return ext1;
	}
	/**
	 * 网信还款方式：还款渠道   0:-支付宝 1-微信  2-网银   3-柜面还款  4-手机银行还款  5-系统手动还款  6-划扣还款
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	/**
	 * 还款日与法定节假日或周末重合时逾期后逾期利息、复利收取方式 0 不收取  1 收取
	 */
	public String getFestivalType() {
		return festivalType;
	}
	/**
	 * 还款日与法定节假日或周末重合时逾期后逾期利息、复利收取方式 0 不收取  1 收取
	 */
	public void setFestivalType(String festivalType) {
		this.festivalType = festivalType;
	}
	/**
	 * 到期还本按月结息支持按日计息（正常还款时是收取到当天的利息还是收取整月的利息）1 收取到当天利息 2 收取整月利息
	 */
	public String getRepayChargeIntstFlag() {
		return repayChargeIntstFlag;
	}
	/**
	 * 到期还本按月结息支持按日计息（正常还款时是收取到当天的利息还是收取整月的利息）1 收取到当天利息 2 收取整月利息
	 */
	public void setRepayChargeIntstFlag(String repayChargeIntstFlag) {
		this.repayChargeIntstFlag = repayChargeIntstFlag;
	}
	/**
	 * 还款方式  3 利随本清  4 到期还本按月结息 5 到期还本按季结息 9 到期还本按自然季结息
	 */
	public String getRepayMethod() {
		return repayMethod;
	}
	/**
	 * 还款方式  3 利随本清  4 到期还本按月结息 5 到期还本按季结息 9 到期还本按自然季结息
	 */
	public void setRepayMethod(String repayMethod) {
		this.repayMethod = repayMethod;
	}
	/**
	 * 业务申请号
	 */
	public String getAppId() {
		return appId;
	}
	
	/**
	 * 业务申请号
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	
	
	
}
