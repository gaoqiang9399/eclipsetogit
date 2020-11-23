/**
 * Copyright (C) DXHM 版权所有
 * 文件名： LoanRepaymentBean.java
 * 包名： dxt.loan.dhgl.refund.bean
 * 说明：
 * @author 栾好威
 * @date 2015-12-7 下午4:32:51
 * @version V1.0
 */
package app.component.pact.repay.entity;

import java.util.List;

/**
 * 
 * 类名： MfRepaymentBean 描述：还款相关参数 描述：
 * 
 * @author zhs
 * @date 2016-9-2 上午11:10:02
 */
public class MfRepaymentBean {
	// 借据号
	private String fincId;
	// 合同号
	private String pactId;
	// 实收本金
	private Double shiShouBenJin;
	// 实收本金
	private String shiShouBenJinFormat;
	// 实收本金的利息
	private Double shiShouLiXi;
	private String shiShouLiXiFormat;
	// 实收罚息
	private Double shiShouFaXi;
	private String shiShouFaXiFormat;
	// 实收逾期利息
	private Double shiShouYuQiLiXi;
	private String shiShouYuQiLiXiFormat;
	// 实收逾期利息 0.5 逾期利率浮动
	private Double shiShouYuQiLiXiPart;
	private String shiShouYuQiLiXiPartFormat;
	
	// 实收复利利息
	private Double shiShouFuLiLiXi;
	private String shiShouFuLiLiXiFormat;
	
	//实收复利利息 0.5 复利利率浮动
	private Double shiShouFuLiLiXiPart;
	private String shiShouFuLiLiXiPartFormat;
	
	// 实收逾期时收取的违约金
	private Double shiShouYuQiWeiYueJin;
	private String shiShouYuQiWeiYueJinFormat;
	// 实收费用
	private Double shiShouFeiYong;
	private String shiShouFeiYongFormat;
	//实收费用罚息
	private Double shiShouFeiYongFaXi;
	private String shiShouFeiYongFaXiFormat;
	// 本次结余
	private Double benCiJieYu;
	private String benCiJieYuFormat;
	// 上次结余
	private Double shangCiJieYu;
	// 本次冲抵
	private Double benCiChongDi;
	private String benCiChongDiFormat;
	// 实收总计 展示页面时与应收总计相等。操作还款动作时不一定相等。某一期的应收总计
	private Double shiShouZongJi;
	private String shiShouZongJiFormat;
	// 实收总计 展示页面时与应收总计相等。操作还款动作时不一定相等。本次还款所有期的应收总计，可以理解为改变默认选项后的应收总计
	private Double yingShouZongJiAll;
	private String yingShouZongJiAllFormat;
	// 实收总计 展示页面时与应收总计相等。操作还款动作时不一定相等。本次还款所有期的应收总计，该应收总计金额不会改变
	private Double yingShouZongJiAllNoChange;
	private String yingShouZongJiAllNoChangeFormat;
	// 实收优惠总计
	private Double shiShouYouHuiZongJi;
	private String shiShouYouHuiZongJiFormat;
	// 还款日期
	private String huanKuanRiQi;
	// 上次还款日期
	private String shangCiHuanKuanRiQi;
	// 当前系统时间段格式如：20151216
	private String systemDateShort;
	// 当前系统时间长格式如：2015-12-16
	private String systemDateLong;
	private List<MfReceivableBean> mfReceivableBeans;
	// 还款历史表的还款类型:1-正常还款-全部还款，2-部分还款，3-逾期还款
	private String refundType;
	// 实际还款后还款计划状态0-未还款，1-已还款，2-欠款，3-部分还款
	private String shiJiOutFlag;
	// 本次还款后的本金余额
	private Double loanBalAfter;
	// 还款方式
	private String returnMethod;
	// 利随本清欠款标示2-欠款，0-非欠款
	private String liSuiBenQingDebtFlag;
	// 0合并第一期, 1放款时收取, 2独立一期, 3合并最后一期
	private String isprepay;
	// 优惠单项金额合计
	private MfPrivilegeBean mfPrivilegeBean;
	//还款页面存放逾期违约金是否展示标志  0 不展示  1 展示
    private String yuQiWeiYueJinShowFlag;
    //还款页面存放费用是否展示标志   0 不展示  1 展示
    private String feeYongShowFlag;
    //费用罚息是否展示 0 不展示 1 展示
    private String feiYongFaXiFlag;

	/**
	 * 本次还款时Max可以还款的金额
	 */
	private Double totalPay;

	/**
	 * 错误标志<br>
	 * Err101-本次结余大于本次还款后的余额<br>
	 * ……其他一次类推,在利用是必须标记清楚 切记切记！！！！
	 */
	private String errMsg;
	// 实收本金提醒
	private String shiShouBenJinTip;
	// 实收利息提醒
	private String shiShouLiXiTip;
	// 应收总额提醒
	private String yingShouZongJiAllTip;
	// 实收金额提醒
	private String shiShouZongJiTip;
	// 本次冲抵提醒
	private String benCiChongDiTip;
	// 实收罚息提醒
	private String shiShouFaXiTip;
	// 实收费用
	private String shiShouFeiYongTip;
	// 实收违约金提醒
	private String shiShouWeiYueTip;
	// 本次结余的提示
	private String benCiJieYuTip;
	// 实收总计余额
	private Double shiShouZongJiYuEr;
	// 实收总金额
	private Double shiShouZongJinEr;
	// 优惠额
	private Double youHuiEr;
	public String getBenCiJieYuTip() {
		return benCiJieYuTip;
	}

	public void setBenCiJieYuTip(String benCiJieYuTip) {
		this.benCiJieYuTip = benCiJieYuTip;
	}

	public String getShiShouFeiYongTip() {
		return shiShouFeiYongTip;
	}

	public void setShiShouFeiYongTip(String shiShouFeiYongTip) {
		this.shiShouFeiYongTip = shiShouFeiYongTip;
	}

	public String getShiShouWeiYueTip() {
		return shiShouWeiYueTip;
	}

	public void setShiShouWeiYueTip(String shiShouWeiYueTip) {
		this.shiShouWeiYueTip = shiShouWeiYueTip;
	}

	public String getShiShouFaXiTip() {
		return shiShouFaXiTip;
	}

	public void setShiShouFaXiTip(String shiShouFaXiTip) {
		this.shiShouFaXiTip = shiShouFaXiTip;
	}

	public String getBenCiChongDiTip() {
		return benCiChongDiTip;
	}

	public void setBenCiChongDiTip(String benCiChongDiTip) {
		this.benCiChongDiTip = benCiChongDiTip;
	}

	public String getShiShouZongJiTip() {
		return shiShouZongJiTip;
	}

	public void setShiShouZongJiTip(String shiShouZongJiTip) {
		this.shiShouZongJiTip = shiShouZongJiTip;
	}

	public String getYingShouZongJiAllTip() {
		return yingShouZongJiAllTip;
	}

	public void setYingShouZongJiAllTip(String yingShouZongJiAllTip) {
		this.yingShouZongJiAllTip = yingShouZongJiAllTip;
	}

	public String getShiShouLiXiTip() {
		return shiShouLiXiTip;
	}

	public void setShiShouLiXiTip(String shiShouLiXiTip) {
		this.shiShouLiXiTip = shiShouLiXiTip;
	}

	public String getShiShouBenJinTip() {
		return shiShouBenJinTip;
	}

	public void setShiShouBenJinTip(String shiShouBenJinTip) {
		this.shiShouBenJinTip = shiShouBenJinTip;
	}

	public Double getShiShouBenJin() {
		if (shiShouBenJin == null) {
			return 0.00;
		}
		return shiShouBenJin;
	}

	public void setShiShouBenJin(Double shiShouBenJin) {
		this.shiShouBenJin = shiShouBenJin;
	}

	public Double getShiShouLiXi() {
		if (shiShouLiXi == null) {
			return 0.00;
		}
		return shiShouLiXi;
	}

	public void setShiShouLiXi(Double shiShouLiXi) {
		this.shiShouLiXi = shiShouLiXi;
	}

	public Double getShiShouFaXi() {
		if (shiShouFaXi == null) {
			return 0.00;
		}
		return shiShouFaXi;
	}

	public void setShiShouFaXi(Double shiShouFaXi) {
		this.shiShouFaXi = shiShouFaXi;
	}

	public Double getShiShouYuQiWeiYueJin() {
		if (shiShouYuQiWeiYueJin == null) {
			return 0.00;
		}
		return shiShouYuQiWeiYueJin;
	}

	public void setShiShouYuQiWeiYueJin(Double shiShouYuQiWeiYueJin) {
		this.shiShouYuQiWeiYueJin = shiShouYuQiWeiYueJin;
	}

	public Double getShiShouFeiYong() {
		if (shiShouFeiYong == null) {
			return 0.00;
		}
		return shiShouFeiYong;
	}

	public void setShiShouFeiYong(Double shiShouFeiYong) {
		this.shiShouFeiYong = shiShouFeiYong;
	}

	public Double getBenCiJieYu() {
		if (benCiJieYu == null) {
			return 0.00;
		}
		return benCiJieYu;
	}

	public void setBenCiJieYu(Double benCiJieYu) {
		this.benCiJieYu = benCiJieYu;
	}

	public Double getShangCiJieYu() {
		if (shangCiJieYu == null) {
			return 0.00;
		}
		return shangCiJieYu;
	}

	public void setShangCiJieYu(Double shangCiJieYu) {
		this.shangCiJieYu = shangCiJieYu;
	}

	public Double getBenCiChongDi() {
		if (benCiChongDi == null) {
			return 0.00;
		}
		return benCiChongDi;
	}

	public void setBenCiChongDi(Double benCiChongDi) {
		this.benCiChongDi = benCiChongDi;
	}

	public Double getShiShouZongJi() {
		if (shiShouZongJi == null) {
			return 0.00;
		}
		return shiShouZongJi;
	}

	public void setShiShouZongJi(Double shiShouZongJi) {
		this.shiShouZongJi = shiShouZongJi;
	}

	public Double getYingShouZongJiAll() {
		if (yingShouZongJiAll == null) {
			return 0.00;
		}
		return yingShouZongJiAll;
	}

	public void setYingShouZongJiAll(Double yingShouZongJiAll) {
		this.yingShouZongJiAll = yingShouZongJiAll;
	}

	public Double getYingShouZongJiAllNoChange() {
		if (yingShouZongJiAllNoChange == null) {
			return 0.00;
		}
		return yingShouZongJiAllNoChange;
	}

	public void setYingShouZongJiAllNoChange(Double yingShouZongJiAllNoChange) {
		this.yingShouZongJiAllNoChange = yingShouZongJiAllNoChange;
	}

	public Double getShiShouYouHuiZongJi() {
		if (shiShouYouHuiZongJi == null) {
			return 0.00;
		}
		return shiShouYouHuiZongJi;
	}

	public void setShiShouYouHuiZongJi(Double shiShouYouHuiZongJi) {
		this.shiShouYouHuiZongJi = shiShouYouHuiZongJi;
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

	/**
	 * 实际还款后还款计划状态0-未还款，1-已还款，2-欠款，3-部分还款
	 */
	public String getShiJiOutFlag() {
		return shiJiOutFlag;
	}

	/**
	 * 实际还款后还款计划状态0-未还款，1-已还款，2-欠款，3-部分还款
	 */
	public void setShiJiOutFlag(String shiJiOutFlag) {
		this.shiJiOutFlag = shiJiOutFlag;
	}

	/**
	 * 错误标志<br>
	 * Err101-本次结余大于本次还款后的余额<br>
	 * ……其他一次类推,在利用是必须标记清楚 切记切记！！！！！！
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * 错误标志<br>
	 * Err101-本次结余大于本次还款后的余额<br>
	 * ……其他一次类推,在利用是必须标记清楚 切记切记！！！！！！
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getReturnMethod() {
		return returnMethod;
	}

	public void setReturnMethod(String returnMethod) {
		this.returnMethod = returnMethod;
	}

	public String getLiSuiBenQingDebtFlag() {
		return liSuiBenQingDebtFlag;
	}

	public void setLiSuiBenQingDebtFlag(String liSuiBenQingDebtFlag) {
		this.liSuiBenQingDebtFlag = liSuiBenQingDebtFlag;
	}

	public String getIsprepay() {
		return isprepay;
	}

	public void setIsprepay(String isprepay) {
		this.isprepay = isprepay;
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

	public List<MfReceivableBean> getMfReceivableBeans() {
		return mfReceivableBeans;
	}

	public void setMfReceivableBeans(List<MfReceivableBean> mfReceivableBeans) {
		this.mfReceivableBeans = mfReceivableBeans;
	}

	public MfPrivilegeBean getMfPrivilegeBean() {
		return mfPrivilegeBean;
	}

	public void setMfPrivilegeBean(MfPrivilegeBean mfPrivilegeBean) {
		this.mfPrivilegeBean = mfPrivilegeBean;
	}

	public Double getShiShouZongJiYuEr() {
		return shiShouZongJiYuEr;
	}

	public void setShiShouZongJiYuEr(Double shiShouZongJiYuEr) {
		this.shiShouZongJiYuEr = shiShouZongJiYuEr;
	}

	public Double getShiShouZongJinEr() {
		return shiShouZongJinEr;
	}

	public void setShiShouZongJinEr(Double shiShouZongJinEr) {
		this.shiShouZongJinEr = shiShouZongJinEr;
	}

	public Double getYouHuiEr() {
		return youHuiEr;
	}

	public void setYouHuiEr(Double youHuiEr) {
		this.youHuiEr = youHuiEr;
	}

	/**
	 * 本次还款时Max可以还款的金额
	 */
	public Double getTotalPay() {
		return totalPay;
	}

	/**
	 * 本次还款时Max可以还款的金额
	 */
	public void setTotalPay(Double totalPay) {
		this.totalPay = totalPay;
	}

	public String getShiShouLiXiFormat() {
		return shiShouLiXiFormat;
	}

	public void setShiShouLiXiFormat(String shiShouLiXiFormat) {
		this.shiShouLiXiFormat = shiShouLiXiFormat;
	}

	public String getBenCiJieYuFormat() {
		return benCiJieYuFormat;
	}

	public void setBenCiJieYuFormat(String benCiJieYuFormat) {
		this.benCiJieYuFormat = benCiJieYuFormat;
	}

	public String getBenCiChongDiFormat() {
		return benCiChongDiFormat;
	}

	public void setBenCiChongDiFormat(String benCiChongDiFormat) {
		this.benCiChongDiFormat = benCiChongDiFormat;
	}

	public String getShiShouBenJinFormat() {
		return shiShouBenJinFormat;
	}

	public void setShiShouBenJinFormat(String shiShouBenJinFormat) {
		this.shiShouBenJinFormat = shiShouBenJinFormat;
	}

	public String getShiShouFaXiFormat() {
		return shiShouFaXiFormat;
	}

	public void setShiShouFaXiFormat(String shiShouFaXiFormat) {
		this.shiShouFaXiFormat = shiShouFaXiFormat;
	}

	public String getShiShouYuQiWeiYueJinFormat() {
		return shiShouYuQiWeiYueJinFormat;
	}

	public void setShiShouYuQiWeiYueJinFormat(String shiShouYuQiWeiYueJinFormat) {
		this.shiShouYuQiWeiYueJinFormat = shiShouYuQiWeiYueJinFormat;
	}

	public String getShiShouFeiYongFormat() {
		return shiShouFeiYongFormat;
	}

	public void setShiShouFeiYongFormat(String shiShouFeiYongFormat) {
		this.shiShouFeiYongFormat = shiShouFeiYongFormat;
	}

	public String getShiShouZongJiFormat() {
		return shiShouZongJiFormat;
	}

	public void setShiShouZongJiFormat(String shiShouZongJiFormat) {
		this.shiShouZongJiFormat = shiShouZongJiFormat;
	}

	public String getYingShouZongJiAllFormat() {
		return yingShouZongJiAllFormat;
	}

	public void setYingShouZongJiAllFormat(String yingShouZongJiAllFormat) {
		this.yingShouZongJiAllFormat = yingShouZongJiAllFormat;
	}

	public String getYingShouZongJiAllNoChangeFormat() {
		return yingShouZongJiAllNoChangeFormat;
	}

	public void setYingShouZongJiAllNoChangeFormat(String yingShouZongJiAllNoChangeFormat) {
		this.yingShouZongJiAllNoChangeFormat = yingShouZongJiAllNoChangeFormat;
	}

	public String getShiShouYouHuiZongJiFormat() {
		return shiShouYouHuiZongJiFormat;
	}

	public void setShiShouYouHuiZongJiFormat(String shiShouYouHuiZongJiFormat) {
		this.shiShouYouHuiZongJiFormat = shiShouYouHuiZongJiFormat;
	}

	public Double getLoanBalAfter() {
		return loanBalAfter;
	}

	public void setLoanBalAfter(Double loanBalAfter) {
		this.loanBalAfter = loanBalAfter;
	}

	public Double getShiShouYuQiLiXi() {
		return shiShouYuQiLiXi;
	}

	public void setShiShouYuQiLiXi(Double shiShouYuQiLiXi) {
		this.shiShouYuQiLiXi = shiShouYuQiLiXi;
	}

	public String getShiShouYuQiLiXiFormat() {
		return shiShouYuQiLiXiFormat;
	}

	public void setShiShouYuQiLiXiFormat(String shiShouYuQiLiXiFormat) {
		this.shiShouYuQiLiXiFormat = shiShouYuQiLiXiFormat;
	}

	public Double getShiShouFuLiLiXi() {
		return shiShouFuLiLiXi;
	}

	public void setShiShouFuLiLiXi(Double shiShouFuLiLiXi) {
		this.shiShouFuLiLiXi = shiShouFuLiLiXi;
	}

	public String getShiShouFuLiLiXiFormat() {
		return shiShouFuLiLiXiFormat;
	}

	public void setShiShouFuLiLiXiFormat(String shiShouFuLiLiXiFormat) {
		this.shiShouFuLiLiXiFormat = shiShouFuLiLiXiFormat;
	}

	public String getYuQiWeiYueJinShowFlag() {
		return yuQiWeiYueJinShowFlag;
	}

	public void setYuQiWeiYueJinShowFlag(String yuQiWeiYueJinShowFlag) {
		this.yuQiWeiYueJinShowFlag = yuQiWeiYueJinShowFlag;
	}
	public String getFeeYongShowFlag() {
		return feeYongShowFlag;
	}
	public void setFeeYongShowFlag(String feeYongShowFlag) {
		this.feeYongShowFlag = feeYongShowFlag;
	}

	public Double getShiShouYuQiLiXiPart() {
		return shiShouYuQiLiXiPart;
	}

	public void setShiShouYuQiLiXiPart(Double shiShouYuQiLiXiPart) {
		this.shiShouYuQiLiXiPart = shiShouYuQiLiXiPart;
	}

	public String getShiShouYuQiLiXiPartFormat() {
		return shiShouYuQiLiXiPartFormat;
	}

	public void setShiShouYuQiLiXiPartFormat(String shiShouYuQiLiXiPartFormat) {
		this.shiShouYuQiLiXiPartFormat = shiShouYuQiLiXiPartFormat;
	}

	public Double getShiShouFuLiLiXiPart() {
		return shiShouFuLiLiXiPart;
	}

	public void setShiShouFuLiLiXiPart(Double shiShouFuLiLiXiPart) {
		this.shiShouFuLiLiXiPart = shiShouFuLiLiXiPart;
	}

	public String getShiShouFuLiLiXiPartFormat() {
		return shiShouFuLiLiXiPartFormat;
	}

	public void setShiShouFuLiLiXiPartFormat(String shiShouFuLiLiXiPartFormat) {
		this.shiShouFuLiLiXiPartFormat = shiShouFuLiLiXiPartFormat;
	}
	public Double getShiShouFeiYongFaXi() {
		return shiShouFeiYongFaXi;
	}

	public void setShiShouFeiYongFaXi(Double shiShouFeiYongFaXi) {
		this.shiShouFeiYongFaXi = shiShouFeiYongFaXi;
	}

	public String getShiShouFeiYongFaXiFormat() {
		return shiShouFeiYongFaXiFormat;
	}

	public void setShiShouFeiYongFaXiFormat(String shiShouFeiYongFaXiFormat) {
		this.shiShouFeiYongFaXiFormat = shiShouFeiYongFaXiFormat;
	}

	public String getFeiYongFaXiFlag() {
		return feiYongFaXiFlag;
	}

	public void setFeiYongFaXiFlag(String feiYongFaXiFlag) {
		this.feiYongFaXiFlag = feiYongFaXiFlag;
	}
}
