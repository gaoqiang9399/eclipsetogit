package app.component.rec.entity;
import app.base.BaseDomain;
/**
* Title: RecallBaseHis.java
* Description:
* @author：@dhcc.com.cn
* @Tue Mar 15 09:27:00 GMT 2016
* @version：1.0
**/
public class RecallBaseHis extends BaseDomain {
	private String hisNo;//历史记录号
	private String taskNo;//业务流水号
	private String conNo;//合同号
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String cusTel;//客户联系电话
	private String recallType;//催收类型
	private Double recallAmt;//催收金额
	private String recallDate;//催收日期
	private String recallWay;//催收方式
	private String recallDesc;//催收描述
	private String leaseModel;//租赁物名称/车牌号
	private String mgrNo;//经办人
	private String mgrName;//经办人名称
	private String returnDesc;//回执描述
	private String isRecall;//是否联系上承租人
	private String isCall;//是否打通电话
	private String isArrive;//是否到达实地催收
	private String isMeet;//是否遇见承租人
	private String isLaw;//是否发起诉讼
	private String lawDesc;//诉讼备注
	private Integer curOverDays;//逾期天数
	private Double delayIntCumu;//当前累计逾期金额
	private Double brcContAmt;//违约金
	private Integer termLimit;//剩余期数
	private Integer repayTerm;//已还期数
	private Double balSum;//剩余未还租金
	private String isClose;//是否收回租赁物
	private String recallSts;//状态
	private String riskLevel;//风险级别
	private String regDate;//登记日期

	/**
	 * @return 历史记录号
	 */
	public String getHisNo() {
	 	return hisNo;
	}
	/**
	 * @设置 历史记录号
	 * @param hisNo
	 */
	public void setHisNo(String hisNo) {
	 	this.hisNo = hisNo;
	}
	/**
	 * @return 业务流水号
	 */
	public String getTaskNo() {
	 	return taskNo;
	}
	/**
	 * @设置 业务流水号
	 * @param taskNo
	 */
	public void setTaskNo(String taskNo) {
	 	this.taskNo = taskNo;
	}
	/**
	 * @return 合同号
	 */
	public String getConNo() {
	 	return conNo;
	}
	/**
	 * @设置 合同号
	 * @param conNo
	 */
	public void setConNo(String conNo) {
	 	this.conNo = conNo;
	}
	/**
	 * @return 客户号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户号
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
	 * @return 客户联系电话
	 */
	public String getCusTel() {
	 	return cusTel;
	}
	/**
	 * @设置 客户联系电话
	 * @param cusTel
	 */
	public void setCusTel(String cusTel) {
	 	this.cusTel = cusTel;
	}
	/**
	 * @return 催收类型
	 */
	public String getRecallType() {
	 	return recallType;
	}
	/**
	 * @设置 催收类型
	 * @param recallType
	 */
	public void setRecallType(String recallType) {
	 	this.recallType = recallType;
	}
	/**
	 * @return 催收金额
	 */
	public Double getRecallAmt() {
	 	return recallAmt;
	}
	/**
	 * @设置 催收金额
	 * @param recallAmt
	 */
	public void setRecallAmt(Double recallAmt) {
	 	this.recallAmt = recallAmt;
	}
	/**
	 * @return 催收日期
	 */
	public String getRecallDate() {
	 	return recallDate;
	}
	/**
	 * @设置 催收日期
	 * @param recallDate
	 */
	public void setRecallDate(String recallDate) {
	 	this.recallDate = recallDate;
	}
	/**
	 * @return 催收方式
	 */
	public String getRecallWay() {
	 	return recallWay;
	}
	/**
	 * @设置 催收方式
	 * @param recallWay
	 */
	public void setRecallWay(String recallWay) {
	 	this.recallWay = recallWay;
	}
	/**
	 * @return 催收描述
	 */
	public String getRecallDesc() {
	 	return recallDesc;
	}
	/**
	 * @设置 催收描述
	 * @param recallDesc
	 */
	public void setRecallDesc(String recallDesc) {
	 	this.recallDesc = recallDesc;
	}
	/**
	 * @return 租赁物名称/车牌号
	 */
	public String getLeaseModel() {
	 	return leaseModel;
	}
	/**
	 * @设置 租赁物名称/车牌号
	 * @param leaseModel
	 */
	public void setLeaseModel(String leaseModel) {
	 	this.leaseModel = leaseModel;
	}
	/**
	 * @return 经办人
	 */
	public String getMgrNo() {
	 	return mgrNo;
	}
	/**
	 * @设置 经办人
	 * @param mgrNo
	 */
	public void setMgrNo(String mgrNo) {
	 	this.mgrNo = mgrNo;
	}
	/**
	 * @return 经办人名称
	 */
	public String getMgrName() {
	 	return mgrName;
	}
	/**
	 * @设置 经办人名称
	 * @param mgrName
	 */
	public void setMgrName(String mgrName) {
	 	this.mgrName = mgrName;
	}
	/**
	 * @return 回执描述
	 */
	public String getReturnDesc() {
	 	return returnDesc;
	}
	/**
	 * @设置 回执描述
	 * @param returnDesc
	 */
	public void setReturnDesc(String returnDesc) {
	 	this.returnDesc = returnDesc;
	}
	/**
	 * @return 是否联系上承租人
	 */
	public String getIsRecall() {
	 	return isRecall;
	}
	/**
	 * @设置 是否联系上承租人
	 * @param isRecall
	 */
	public void setIsRecall(String isRecall) {
	 	this.isRecall = isRecall;
	}
	/**
	 * @return 是否打通电话
	 */
	public String getIsCall() {
	 	return isCall;
	}
	/**
	 * @设置 是否打通电话
	 * @param isCall
	 */
	public void setIsCall(String isCall) {
	 	this.isCall = isCall;
	}
	/**
	 * @return 是否到达实地催收
	 */
	public String getIsArrive() {
	 	return isArrive;
	}
	/**
	 * @设置 是否到达实地催收
	 * @param isArrive
	 */
	public void setIsArrive(String isArrive) {
	 	this.isArrive = isArrive;
	}
	/**
	 * @return 是否遇见承租人
	 */
	public String getIsMeet() {
	 	return isMeet;
	}
	/**
	 * @设置 是否遇见承租人
	 * @param isMeet
	 */
	public void setIsMeet(String isMeet) {
	 	this.isMeet = isMeet;
	}
	/**
	 * @return 是否发起诉讼
	 */
	public String getIsLaw() {
	 	return isLaw;
	}
	/**
	 * @设置 是否发起诉讼
	 * @param isLaw
	 */
	public void setIsLaw(String isLaw) {
	 	this.isLaw = isLaw;
	}
	/**
	 * @return 诉讼备注
	 */
	public String getLawDesc() {
	 	return lawDesc;
	}
	/**
	 * @设置 诉讼备注
	 * @param lawDesc
	 */
	public void setLawDesc(String lawDesc) {
	 	this.lawDesc = lawDesc;
	}
	/**
	 * @return 逾期天数
	 */
	public Integer getCurOverDays() {
	 	return curOverDays;
	}
	/**
	 * @设置 逾期天数
	 * @param curOverDays
	 */
	public void setCurOverDays(Integer curOverDays) {
	 	this.curOverDays = curOverDays;
	}
	/**
	 * @return 当前累计逾期金额
	 */
	public Double getDelayIntCumu() {
	 	return delayIntCumu;
	}
	/**
	 * @设置 当前累计逾期金额
	 * @param delayIntCumu
	 */
	public void setDelayIntCumu(Double delayIntCumu) {
	 	this.delayIntCumu = delayIntCumu;
	}
	/**
	 * @return 违约金
	 */
	public Double getBrcContAmt() {
	 	return brcContAmt;
	}
	/**
	 * @设置 违约金
	 * @param brcContAmt
	 */
	public void setBrcContAmt(Double brcContAmt) {
	 	this.brcContAmt = brcContAmt;
	}
	/**
	 * @return 剩余期数
	 */
	public Integer getTermLimit() {
	 	return termLimit;
	}
	/**
	 * @设置 剩余期数
	 * @param termLimit
	 */
	public void setTermLimit(Integer termLimit) {
	 	this.termLimit = termLimit;
	}
	/**
	 * @return 已还期数
	 */
	public Integer getRepayTerm() {
	 	return repayTerm;
	}
	/**
	 * @设置 已还期数
	 * @param repayTerm
	 */
	public void setRepayTerm(Integer repayTerm) {
	 	this.repayTerm = repayTerm;
	}
	/**
	 * @return 剩余未还租金
	 */
	public Double getBalSum() {
	 	return balSum;
	}
	/**
	 * @设置 剩余未还租金
	 * @param balSum
	 */
	public void setBalSum(Double balSum) {
	 	this.balSum = balSum;
	}
	/**
	 * @return 是否收回租赁物
	 */
	public String getIsClose() {
	 	return isClose;
	}
	/**
	 * @设置 是否收回租赁物
	 * @param isClose
	 */
	public void setIsClose(String isClose) {
	 	this.isClose = isClose;
	}
	/**
	 * @return 状态
	 */
	public String getRecallSts() {
	 	return recallSts;
	}
	/**
	 * @设置 状态
	 * @param recallSts
	 */
	public void setRecallSts(String recallSts) {
	 	this.recallSts = recallSts;
	}
	/**
	 * @return 风险级别
	 */
	public String getRiskLevel() {
	 	return riskLevel;
	}
	/**
	 * @设置 风险级别
	 * @param riskLevel
	 */
	public void setRiskLevel(String riskLevel) {
	 	this.riskLevel = riskLevel;
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
}