/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfReportRecall.java
 * 包名： app.component.report.entity
 * 说明：
 * @author 谢静霞
 * @date 2017-6-20 下午11:10:28
 * @version V1.0
 */ 
package app.component.report.entity;

public class MfReportRecall{
	
	private String recallWay;//催收方式
	private String recallSts;//催收状态
	private String brNo;//部门编号
	private String brName;//部门名称
	private double recallAmt;//催收金额
	private double recallBackAmt;//收回金额
	private int recallCount;//催收项目数
	
	private int messageCount;//短信催收数
	private double messageRate;//短信催收占比
	private double messageAmt;//短信催收金额
	private int telCount;//电话催收数
	private double telRate;//电话催收占比
	private double telAmt;//电话催收金额
	private int emileCount;//函件催收数
	private double emileRate;//函件催收占比
	private double emileAmt;//函件催收占比
	private int lawCount;//法务催收数
	private double lawRate;//法务催收占比
	private double lawAmt;//法务催收金额
	private int visitCount;//外访催收数
	private double visitRate;//外访催收占比
	private double visitAmt;//外访催收金额
	private int outCount;//委外催收数
	private double outRate;//委外催收占比
	private double outAmt;//委外催收金额
	public String getRecallWay() {
		return recallWay;
	}
	public void setRecallWay(String recallWay) {
		this.recallWay = recallWay;
	}
	public String getRecallSts() {
		return recallSts;
	}
	public void setRecallSts(String recallSts) {
		this.recallSts = recallSts;
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
	public double getRecallAmt() {
		return recallAmt;
	}
	public void setRecallAmt(double recallAmt) {
		this.recallAmt = recallAmt;
	}
	public double getRecallBackAmt() {
		return recallBackAmt;
	}
	public void setRecallBackAmt(double recallBackAmt) {
		this.recallBackAmt = recallBackAmt;
	}
	public int getRecallCount() {
		return recallCount;
	}
	public void setRecallCount(int recallCount) {
		this.recallCount = recallCount;
	}
	public int getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}
	public double getMessageRate() {
		return messageRate;
	}
	public void setMessageRate(double messageRate) {
		this.messageRate = messageRate;
	}
	public double getMessageAmt() {
		return messageAmt;
	}
	public void setMessageAmt(double messageAmt) {
		this.messageAmt = messageAmt;
	}
	public int getTelCount() {
		return telCount;
	}
	public void setTelCount(int telCount) {
		this.telCount = telCount;
	}
	public double getTelRate() {
		return telRate;
	}
	public void setTelRate(double telRate) {
		this.telRate = telRate;
	}
	public double getTelAmt() {
		return telAmt;
	}
	public void setTelAmt(double telAmt) {
		this.telAmt = telAmt;
	}
	public int getEmileCount() {
		return emileCount;
	}
	public void setEmileCount(int emileCount) {
		this.emileCount = emileCount;
	}
	public double getEmileRate() {
		return emileRate;
	}
	public void setEmileRate(double emileRate) {
		this.emileRate = emileRate;
	}
	public double getEmileAmt() {
		return emileAmt;
	}
	public void setEmileAmt(double emileAmt) {
		this.emileAmt = emileAmt;
	}
	public int getLawCount() {
		return lawCount;
	}
	public void setLawCount(int lawCount) {
		this.lawCount = lawCount;
	}
	public double getLawRate() {
		return lawRate;
	}
	public void setLawRate(double lawRate) {
		this.lawRate = lawRate;
	}
	public double getLawAmt() {
		return lawAmt;
	}
	public void setLawAmt(double lawAmt) {
		this.lawAmt = lawAmt;
	}
	public int getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	public double getVisitRate() {
		return visitRate;
	}
	public void setVisitRate(double visitRate) {
		this.visitRate = visitRate;
	}
	public double getVisitAmt() {
		return visitAmt;
	}
	public void setVisitAmt(double visitAmt) {
		this.visitAmt = visitAmt;
	}
	public int getOutCount() {
		return outCount;
	}
	public void setOutCount(int outCount) {
		this.outCount = outCount;
	}
	public double getOutRate() {
		return outRate;
	}
	public void setOutRate(double outRate) {
		this.outRate = outRate;
	}
	public double getOutAmt() {
		return outAmt;
	}
	public void setOutAmt(double outAmt) {
		this.outAmt = outAmt;
	}
	
}
