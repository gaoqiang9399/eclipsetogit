package app.component.calc.core.entity;
/**
 * 描述：提前还款相关参数
 * @author WD
 *
 */
/**
 * @author WD
 *
 */
public class PrepaymentParameter {
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String fincId;//借据号
	private String pactId;//合同编号
	private String planId;//还款计划中id
	private String repayId;//还款历史总表中id 
	private Double loanBal;//本次还款时剩余的金额（还款之前 剩余未还本金）
	private String repayType;//还款类型：1-正常还款，2-逾期还款，3-提前还款	
	private String huanKuanRiQi;// 还款日期
	//当期本金  提前还款使用
	private Double dangQiBenJin;
	private String dangQiBenJinFormat;
	private Double shiShouLiXi;//实收本金
	private Double tiQianHuanKuanWeiYueJin;
	private String tiQianHuanKuanWeiYueJinFormat;//提前还款违约金
	private Double tiQianHuanBen;//提前还本
	private Double YouHuiZongEr;//实收优惠总计	
	private Double benCiJieYu;// 本次结余
	private Double benCiChongDi;// 本次冲抵	
	//实收总计 展示页面时与应收总计相等。操作还款动作时不一定相等。提前还款所有金额合计	 
	private Double shiShouZongJi;
	//实收总计 展示页面时与应收总计相等。操作还款动作时不一定相等。提前还款所有金额合计
	private Double yingShouZongJi;
	private String preRepayType;//提前还款：1-提前结清 2-提前归还部分本金
	private String preRepayInstAccoutBase;//提前还款利息计算基数：1-按借据余额、2-按提前还款本金 
	private String termInstMustBack;//当期本息是否必须归还：1-是、0-否
    private String balanceDealType;//结余处理方式：1-冲抵贷款、2-退款   
	//还款计划调整参数0-还款计划总期数不变,1-还款计划每期金额不变
	private String jiHuaTiaoZhengCanShu;
	private String calcIntstFlag;//获取利息计算区间标志  1-算头不算尾 2-首尾都计算
	private String opNo;//登记人员编号
	private String opName;//登记人员名称
	private String brNo;//登记人员部门编号
	private String brName;//登记人员部门名称
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
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getRepayId() {
		return repayId;
	}
	public void setRepayId(String repayId) {
		this.repayId = repayId;
	}
	public Double getLoanBal() {
		return loanBal;
	}
	public void setLoanBal(Double loanBal) {
		this.loanBal = loanBal;
	}
	public String getRepayType() {
		return repayType;
	}
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	public String getHuanKuanRiQi() {
		return huanKuanRiQi;
	}
	public void setHuanKuanRiQi(String huanKuanRiQi) {
		this.huanKuanRiQi = huanKuanRiQi;
	}
	public Double getDangQiBenJin() {
		return dangQiBenJin;
	}
	public void setDangQiBenJin(Double dangQiBenJin) {
		this.dangQiBenJin = dangQiBenJin;
	}
	public String getDangQiBenJinFormat() {
		return dangQiBenJinFormat;
	}
	public void setDangQiBenJinFormat(String dangQiBenJinFormat) {
		this.dangQiBenJinFormat = dangQiBenJinFormat;
	}
	public Double getShiShouLiXi() {
		return shiShouLiXi;
	}
	public void setShiShouLiXi(Double shiShouLiXi) {
		this.shiShouLiXi = shiShouLiXi;
	}
	public String getPreRepayType() {
		return preRepayType;
	}
	public void setPreRepayType(String preRepayType) {
		this.preRepayType = preRepayType;
	}
	public String getPreRepayInstAccoutBase() {
		return preRepayInstAccoutBase;
	}
	public void setPreRepayInstAccoutBase(String preRepayInstAccoutBase) {
		this.preRepayInstAccoutBase = preRepayInstAccoutBase;
	}
	public Double getTiQianHuanKuanWeiYueJin() {
		return tiQianHuanKuanWeiYueJin;
	}
	public void setTiQianHuanKuanWeiYueJin(Double tiQianHuanKuanWeiYueJin) {
		this.tiQianHuanKuanWeiYueJin = tiQianHuanKuanWeiYueJin;
	}
	public String getTiQianHuanKuanWeiYueJinFormat() {
		return tiQianHuanKuanWeiYueJinFormat;
	}
	public void setTiQianHuanKuanWeiYueJinFormat(
			String tiQianHuanKuanWeiYueJinFormat) {
		this.tiQianHuanKuanWeiYueJinFormat = tiQianHuanKuanWeiYueJinFormat;
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
	public Double getShiShouZongJi() {
		return shiShouZongJi;
	}
	public void setShiShouZongJi(Double shiShouZongJi) {
		this.shiShouZongJi = shiShouZongJi;
	}
	public Double getYingShouZongJi() {
		return yingShouZongJi;
	}
	public void setYingShouZongJi(Double yingShouZongJi) {
		this.yingShouZongJi = yingShouZongJi;
	}
	public String getTermInstMustBack() {
		return termInstMustBack;
	}
	public void setTermInstMustBack(String termInstMustBack) {
		this.termInstMustBack = termInstMustBack;
	}
	public String getBalanceDealType() {
		return balanceDealType;
	}
	public void setBalanceDealType(String balanceDealType) {
		this.balanceDealType = balanceDealType;
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
	public Double getTiQianHuanBen() {
		return tiQianHuanBen;
	}
	public void setTiQianHuanBen(Double tiQianHuanBen) {
		this.tiQianHuanBen = tiQianHuanBen;
	}
	public Double getYouHuiZongEr() {
		return YouHuiZongEr;
	}
	public void setYouHuiZongEr(Double youHuiZongEr) {
		YouHuiZongEr = youHuiZongEr;
	}
	public String getJiHuaTiaoZhengCanShu() {
		return jiHuaTiaoZhengCanShu;
	}
	public void setJiHuaTiaoZhengCanShu(String jiHuaTiaoZhengCanShu) {
		this.jiHuaTiaoZhengCanShu = jiHuaTiaoZhengCanShu;
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
}
