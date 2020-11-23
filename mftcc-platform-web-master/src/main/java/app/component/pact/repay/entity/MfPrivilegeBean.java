/**
 * Copyright (C) DXHM 版权所有
 * 文件名： LoanPrivilegeBean.java
 * 包名： dxt.loan.dhgl.refund.bean
 * 说明：
 * @author 栾好威
 * @date 2015-12-11 下午2:51:45
 * @version V1.0
 */
package app.component.pact.repay.entity;

/**
 * 类名： LoanPrivilegeBean 描述：优惠单项金额合计
 * 
 * @author 栾好威
 * @date 2015-12-11 下午2:51:45
 */
public class MfPrivilegeBean {
	// 期号
	private String qiHao;
	// 借据号
	private String loanNo;
	// 合同号
	private String pactNo;
	// 优惠金额-罚息
	private String youHuiJineFaXi;
	// 优惠金额-逾期违约金
	private String youHuiJineYuQiWeiYueJin;
	// 优惠金额-利息
	private String youHuiJineLiXi;
	// 优惠金额-费用
	private String youHuiJineFeiYong;
	// 实收金额改变减-罚息
	private String shiShouChangeFaXi;
	// 实收金额改变减-逾期违约金
	private String shiShouChangeYuQiWeiYueJin;
	// 实收金额改变减-利息
	private String shiShouChangeLiXi;
	// 实收金额改变减-费用
	private String shiShouChangeFeiYong;
	// 实收金额改变减-本金
	private String shiShouChangeBenJin;

	public String getQiHao() {
		return qiHao;
	}

	public void setQiHao(String qiHao) {
		this.qiHao = qiHao;
	}

	public String getLoanNo() {
		return loanNo;
	}

	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	public String getPactNo() {
		return pactNo;
	}

	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}

	public String getYouHuiJineFaXi() {
		return youHuiJineFaXi;
	}

	public void setYouHuiJineFaXi(String youHuiJineFaXi) {
		this.youHuiJineFaXi = youHuiJineFaXi;
	}

	public String getYouHuiJineYuQiWeiYueJin() {
		return youHuiJineYuQiWeiYueJin;
	}

	public void setYouHuiJineYuQiWeiYueJin(String youHuiJineYuQiWeiYueJin) {
		this.youHuiJineYuQiWeiYueJin = youHuiJineYuQiWeiYueJin;
	}

	public String getYouHuiJineLiXi() {
		return youHuiJineLiXi;
	}

	public void setYouHuiJineLiXi(String youHuiJineLiXi) {
		this.youHuiJineLiXi = youHuiJineLiXi;
	}

	public String getYouHuiJineFeiYong() {
		return youHuiJineFeiYong;
	}

	public void setYouHuiJineFeiYong(String youHuiJineFeiYong) {
		this.youHuiJineFeiYong = youHuiJineFeiYong;
	}

	public String getShiShouChangeFaXi() {
		return shiShouChangeFaXi;
	}

	public void setShiShouChangeFaXi(String shiShouChangeFaXi) {
		this.shiShouChangeFaXi = shiShouChangeFaXi;
	}

	public String getShiShouChangeYuQiWeiYueJin() {
		return shiShouChangeYuQiWeiYueJin;
	}

	public void setShiShouChangeYuQiWeiYueJin(String shiShouChangeYuQiWeiYueJin) {
		this.shiShouChangeYuQiWeiYueJin = shiShouChangeYuQiWeiYueJin;
	}

	public String getShiShouChangeLiXi() {
		return shiShouChangeLiXi;
	}

	public void setShiShouChangeLiXi(String shiShouChangeLiXi) {
		this.shiShouChangeLiXi = shiShouChangeLiXi;
	}

	public String getShiShouChangeFeiYong() {
		return shiShouChangeFeiYong;
	}

	public void setShiShouChangeFeiYong(String shiShouChangeFeiYong) {
		this.shiShouChangeFeiYong = shiShouChangeFeiYong;
	}

	public String getShiShouChangeBenJin() {
		return shiShouChangeBenJin;
	}

	public void setShiShouChangeBenJin(String shiShouChangeBenJin) {
		this.shiShouChangeBenJin = shiShouChangeBenJin;
	}

}
