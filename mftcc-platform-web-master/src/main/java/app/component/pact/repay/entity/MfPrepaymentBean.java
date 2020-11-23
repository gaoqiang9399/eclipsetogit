package app.component.pact.repay.entity;
/**
 * 
 * 类名： MfPrepaymentBean 描述：提前还款相关参数 描述：
 * 
 * @author wd
 * @date 2017-07-19 上午11:34:02
 */
public class MfPrepaymentBean {
	// 借据号
	private String fincId;
	// 合同号
	private String pactId;
	// 实收本金的利息
	private Double shiShouLiXi;
	private String shiShouLiXiFormat;
	// 本次结余
	private Double benCiJieYu;
	private String benCiJieYuFormat;
	// 上次结余
	private Double shangCiJieYu;
	// 本次冲抵
	private Double benCiChongDi;
	private String benCiChongDiFormat;
	// 还款日期
	private String huanKuanRiQi;
	// 上次还款日期
	private String shangCiHuanKuanRiQi;
	// 当前系统时间段格式如：20151216
	private String systemDateShort;
	// 当前系统时间长格式如：2015-12-16
	private String systemDateLong;
	
	// 还款历史表的还款类型:1-正常还款-全部还款，2-部分还款，3-逾期还款
	private String refundType;
	// 还款方式
	private String returnMethod;
	// 优惠额
	private Double youHuiEr;
	// 剩余本金 提前还款使用
	private Double shengYuBenJin;
	private String shengYuBenJinFormat;
	// 当期本金 提前还款使用
	private Double dangQiBenJin;
	private String dangQiBenJinFormat;
	private String preRepayType;// 提前还款：1-提前结清 2-提前归还部分本金
	private String preRepayInstAccoutBase;// 提前还款利息计算基数：1-按借据余额、2-按提前还款本金
	private Double tiQianHuanKuanWeiYueJin;
	private String tiQianHuanKuanWeiYueJinFormat;// 提前还款违约金
	private Double tiQianHuanBen;//提前还本
	private String tiQianHuanBenFormat;//提前还本
	private Double shiShouZongJi;//实收总计
	private String termInstMustBack;// 当期本息是否必须归还：1-是、0-否
	private String returnPlanPoint;//还款计划保留小数位0-不保留1-保留一位2-保留两位
	private String returnPlanRound;//还款计划舍入方式2四舍五入
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
	public Double getShiShouLiXi() {
		return shiShouLiXi;
	}
	public void setShiShouLiXi(Double shiShouLiXi) {
		this.shiShouLiXi = shiShouLiXi;
	}
	public String getShiShouLiXiFormat() {
		return shiShouLiXiFormat;
	}
	public void setShiShouLiXiFormat(String shiShouLiXiFormat) {
		this.shiShouLiXiFormat = shiShouLiXiFormat;
	}
	public Double getBenCiJieYu() {
		return benCiJieYu;
	}
	public void setBenCiJieYu(Double benCiJieYu) {
		this.benCiJieYu = benCiJieYu;
	}
	public String getBenCiJieYuFormat() {
		return benCiJieYuFormat;
	}
	public void setBenCiJieYuFormat(String benCiJieYuFormat) {
		this.benCiJieYuFormat = benCiJieYuFormat;
	}
	public Double getShangCiJieYu() {
		return shangCiJieYu;
	}
	public void setShangCiJieYu(Double shangCiJieYu) {
		this.shangCiJieYu = shangCiJieYu;
	}
	public Double getBenCiChongDi() {
		return benCiChongDi;
	}
	public void setBenCiChongDi(Double benCiChongDi) {
		this.benCiChongDi = benCiChongDi;
	}
	public String getBenCiChongDiFormat() {
		return benCiChongDiFormat;
	}
	public void setBenCiChongDiFormat(String benCiChongDiFormat) {
		this.benCiChongDiFormat = benCiChongDiFormat;
	}
	public String getHuanKuanRiQi() {
		return huanKuanRiQi;
	}
	public void setHuanKuanRiQi(String huanKuanRiQi) {
		this.huanKuanRiQi = huanKuanRiQi;
	}
	public String getShangCiHuanKuanRiQi() {
		return shangCiHuanKuanRiQi;
	}
	public void setShangCiHuanKuanRiQi(String shangCiHuanKuanRiQi) {
		this.shangCiHuanKuanRiQi = shangCiHuanKuanRiQi;
	}
	public String getSystemDateShort() {
		return systemDateShort;
	}
	public void setSystemDateShort(String systemDateShort) {
		this.systemDateShort = systemDateShort;
	}
	public String getSystemDateLong() {
		return systemDateLong;
	}
	public void setSystemDateLong(String systemDateLong) {
		this.systemDateLong = systemDateLong;
	}
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	public String getReturnMethod() {
		return returnMethod;
	}
	public void setReturnMethod(String returnMethod) {
		this.returnMethod = returnMethod;
	}
	public Double getYouHuiEr() {
		return youHuiEr;
	}
	public void setYouHuiEr(Double youHuiEr) {
		this.youHuiEr = youHuiEr;
	}
	public Double getShengYuBenJin() {
		return shengYuBenJin;
	}
	public void setShengYuBenJin(Double shengYuBenJin) {
		this.shengYuBenJin = shengYuBenJin;
	}
	public String getShengYuBenJinFormat() {
		return shengYuBenJinFormat;
	}
	public void setShengYuBenJinFormat(String shengYuBenJinFormat) {
		this.shengYuBenJinFormat = shengYuBenJinFormat;
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
	public String getTermInstMustBack() {
		return termInstMustBack;
	}
	public void setTermInstMustBack(String termInstMustBack) {
		this.termInstMustBack = termInstMustBack;
	}
	public Double getShiShouZongJi() {
		return shiShouZongJi;
	}
	public void setShiShouZongJi(Double shiShouZongJi) {
		this.shiShouZongJi = shiShouZongJi;
	}
	public String getReturnPlanPoint() {
		return returnPlanPoint;
	}
	public void setReturnPlanPoint(String returnPlanPoint) {
		this.returnPlanPoint = returnPlanPoint;
	}
	public String getReturnPlanRound() {
		return returnPlanRound;
	}
	public void setReturnPlanRound(String returnPlanRound) {
		this.returnPlanRound = returnPlanRound;
	}
	/**
	 *提前还本
	 */
	public Double getTiQianHuanBen() {
		return tiQianHuanBen;
	}
	/**
	 *提前还本
	 */
	public void setTiQianHuanBen(Double tiQianHuanBen) {
		this.tiQianHuanBen = tiQianHuanBen;
	}
	/**
	 *提前还本
	 */
	public String getTiQianHuanBenFormat() {
		return tiQianHuanBenFormat;
	}
	/**
	 *提前还本
	 */
	public void setTiQianHuanBenFormat(String tiQianHuanBenFormat) {
		this.tiQianHuanBenFormat = tiQianHuanBenFormat;
	}
}
