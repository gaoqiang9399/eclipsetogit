/**
 * Copyright (C) DXHM 版权所有
 * 文件名： AssistAccountDetailBean.java
 * 包名： app.component.finance.othreport.entity
 * 说明：
 * @author 刘争帅
 * @date 2017-2-6 下午4:26:05
 * @version V1.0
 */ 
package app.component.finance.othreport.entity;


/**
 * 类名： AssistAccountDetailBean
 * 描述：
 * @author 刘争帅
 * @date 2017-2-6 下午4:26:05
 *
 *
 */
public class AssistAccountDetailBean {
	
	private String finBooks;//帐套号
	private String month;//月
	private String day;//日
	private String accName;//科目名称
	private String assistName;//辅助核算名称
	private String pznum;//凭证编号
	private String summary;//摘要
	private String sideItem;//对方科目
	private String jie;//借
	private String dai;//贷
	private String direction;//方向
	private String blance;//余额
	private String itemNo;//辅助核算编号
	private String itemValueNo;//辅助核算值编号
	private String otherDcInd;//对方科目
	private String dcInd;//借贷标识
	
	//{accNo=1002, endWeek=201705, beginWeek=201701, wrongpz=1, noAccountpz=1}
	private String beginDate;//开始日期
	private String endDate;//结束日期
	private String wrongpz;//错误凭证
	private String noAccountpz;//未记账凭证
	private String accNo;//科目号
	
	private String txDate;//日期
	
	private String qichuBalance;//期初余额
	private String currentJie;//本期借方
	private String currentDai;//本期贷方
	private String daiTotal;//贷方累计
	private String jieTotal;//借方累计
	private String qimoBalance;//期末余额
	private String accHrt;//科目控制字
	private String orientation;//方向
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAssistName() {
		return assistName;
	}
	public void setAssistName(String assistName) {
		this.assistName = assistName;
	}
	public String getPznum() {
		return pznum;
	}
	public void setPznum(String pznum) {
		this.pznum = pznum;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getSideItem() {
		return sideItem;
	}
	public void setSideItem(String sideItem) {
		this.sideItem = sideItem;
	}
	public String getJie() {
		return jie;
	}
	public void setJie(String jie) {
		this.jie = jie;
	}
	public String getDai() {
		return dai;
	}
	public void setDai(String dai) {
		this.dai = dai;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getBlance() {
		return blance;
	}
	public void setBlance(String blance) {
		this.blance = blance;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getWrongpz() {
		return wrongpz;
	}
	public void setWrongpz(String wrongpz) {
		this.wrongpz = wrongpz;
	}
	public String getNoAccountpz() {
		return noAccountpz;
	}
	public void setNoAccountpz(String noAccountpz) {
		this.noAccountpz = noAccountpz;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getTxDate() {
		return txDate;
	}
	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemValueNo() {
		return itemValueNo;
	}
	public void setItemValueNo(String itemValueNo) {
		this.itemValueNo = itemValueNo;
	}
	public String getOtherDcInd() {
		return otherDcInd;
	}
	public void setOtherDcInd(String otherDcInd) {
		this.otherDcInd = otherDcInd;
	}
	public String getDcInd() {
		return dcInd;
	}
	public void setDcInd(String dcInd) {
		this.dcInd = dcInd;
	}
	public String getQichuBalance() {
		return qichuBalance;
	}
	public void setQichuBalance(String qichuBalance) {
		this.qichuBalance = qichuBalance;
	}
	public String getCurrentJie() {
		return currentJie;
	}
	public void setCurrentJie(String currentJie) {
		this.currentJie = currentJie;
	}
	public String getCurrentDai() {
		return currentDai;
	}
	public void setCurrentDai(String currentDai) {
		this.currentDai = currentDai;
	}
	public String getDaiTotal() {
		return daiTotal;
	}
	public void setDaiTotal(String daiTotal) {
		this.daiTotal = daiTotal;
	}
	public String getQimoBalance() {
		return qimoBalance;
	}
	public void setQimoBalance(String qimoBalance) {
		this.qimoBalance = qimoBalance;
	}
	public String getAccHrt() {
		return accHrt;
	}
	public void setAccHrt(String accHrt) {
		this.accHrt = accHrt;
	}
	public String getJieTotal() {
		return jieTotal;
	}
	public void setJieTotal(String jieTotal) {
		this.jieTotal = jieTotal;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public String getFinBooks() {
		return finBooks;
	}
	public void setFinBooks(String finBooks) {
		this.finBooks = finBooks;
	}
	
	
}
