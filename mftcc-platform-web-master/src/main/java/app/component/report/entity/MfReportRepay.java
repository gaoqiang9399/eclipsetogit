/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfReportFiveClass.java
 * 包名： app.component.report.entity
 * 说明：
 * @author 谢静霞
 * @date 2017-6-16 下午4:25:28
 * @version V1.0
 */ 
package app.component.report.entity;

public class MfReportRepay{
	
	private String brNo;//部门编号
	private String brName;//部门名称
	private double prcpSumYear;//年初余额
	private double prcpSumMonth;//月初余额
	private double prcpSum;//本期余额
	private String fiveclass;//五级分类
	private double yearRise;//较年初增减
	private double monthRise;//较月初增减
	
	private double normPrcpSumYear;//正常贷款年初余额
	private double normPrcpSumMonth;//正常贷款月初余额
	private double normPrcpSum;//正常贷款本期余额
	private double normYearRise;//正常贷款较年初增减
	private double normMonthRise;//正常贷款较月初增减
	
	private double unormPrcpSumYear;//不良贷款年初余额
	private double unormPrcpSumMonth;//不良贷款月初余额
	private double unormPrcpSum;//不良贷款本期余额
	private double unormYearRise;//不良贷款较年初增减
	private double unormMonthRise;//不良贷款较月初增减
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
	public double getPrcpSumYear() {
		return prcpSumYear;
	}
	public void setPrcpSumYear(double prcpSumYear) {
		this.prcpSumYear = prcpSumYear;
	}
	public double getPrcpSumMonth() {
		return prcpSumMonth;
	}
	public void setPrcpSumMonth(double prcpSumMonth) {
		this.prcpSumMonth = prcpSumMonth;
	}
	public double getPrcpSum() {
		return prcpSum;
	}
	public void setPrcpSum(double prcpSum) {
		this.prcpSum = prcpSum;
	}
	public String getFiveclass() {
		return fiveclass;
	}
	public void setFiveclass(String fiveclass) {
		this.fiveclass = fiveclass;
	}
	public double getYearRise() {
		return yearRise;
	}
	public void setYearRise(double yearRise) {
		this.yearRise = yearRise;
	}
	public double getMonthRise() {
		return monthRise;
	}
	public void setMonthRise(double monthRise) {
		this.monthRise = monthRise;
	}
	public double getNormPrcpSumYear() {
		return normPrcpSumYear;
	}
	public void setNormPrcpSumYear(double normPrcpSumYear) {
		this.normPrcpSumYear = normPrcpSumYear;
	}
	public double getNormPrcpSumMonth() {
		return normPrcpSumMonth;
	}
	public void setNormPrcpSumMonth(double normPrcpSumMonth) {
		this.normPrcpSumMonth = normPrcpSumMonth;
	}
	public double getNormPrcpSum() {
		return normPrcpSum;
	}
	public void setNormPrcpSum(double normPrcpSum) {
		this.normPrcpSum = normPrcpSum;
	}
	public double getNormYearRise() {
		return normYearRise;
	}
	public void setNormYearRise(double normYearRise) {
		this.normYearRise = normYearRise;
	}
	public double getNormMonthRise() {
		return normMonthRise;
	}
	public void setNormMonthRise(double normMonthRise) {
		this.normMonthRise = normMonthRise;
	}
	public double getUnormPrcpSumYear() {
		return unormPrcpSumYear;
	}
	public void setUnormPrcpSumYear(double unormPrcpSumYear) {
		this.unormPrcpSumYear = unormPrcpSumYear;
	}
	public double getUnormPrcpSumMonth() {
		return unormPrcpSumMonth;
	}
	public void setUnormPrcpSumMonth(double unormPrcpSumMonth) {
		this.unormPrcpSumMonth = unormPrcpSumMonth;
	}
	public double getUnormPrcpSum() {
		return unormPrcpSum;
	}
	public void setUnormPrcpSum(double unormPrcpSum) {
		this.unormPrcpSum = unormPrcpSum;
	}
	public double getUnormYearRise() {
		return unormYearRise;
	}
	public void setUnormYearRise(double unormYearRise) {
		this.unormYearRise = unormYearRise;
	}
	public double getUnormMonthRise() {
		return unormMonthRise;
	}
	public void setUnormMonthRise(double unormMonthRise) {
		this.unormMonthRise = unormMonthRise;
	}
	
}
