/**
 * Copyright (C) DXHM 版权所有
 * 文件名： KemuHuiZong.java
 * 包名： app.component.finance.othreport.entity
 * 说明：
 * @author 刘争帅
 * @date 2017-1-10 上午10:40:24
 * @version V1.0
 */ 
package app.component.finance.othreport.entity;
/**
 * 类名： KemuHuiZong
 * 描述：科目汇总表使用
 * @author 刘争帅
 * @date 2017-1-10 上午10:40:24
 *
 *
 */
public class CwKemuHuiZong {
	
	private String finBooks;//帐套标识
	private String accNo;//科目编号
	private String accName;//科目名称
	private String daiAmt;//贷方金额
	private String jieAmt;//借方金额
	private String accType;//科目类型
	private String beginDate;//开始时间
	private String endDate;//结束时间
	private String noAccountpz;//未记账凭证
	private String wrongpz;//错误凭证
	private String showTabKemu;//显示表外类科目
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getDaiAmt() {
		return daiAmt;
	}
	public void setDaiAmt(String daiAmt) {
		this.daiAmt = daiAmt;
	}
	public String getJieAmt() {
		return jieAmt;
	}
	public void setJieAmt(String jieAmt) {
		this.jieAmt = jieAmt;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
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
	public String getShowTabKemu() {
		return showTabKemu;
	}
	public void setShowTabKemu(String showTabKemu) {
		this.showTabKemu = showTabKemu;
	}
	public String getFinBooks() {
		return finBooks;
	}
	public void setFinBooks(String finBooks) {
		this.finBooks = finBooks;
	}
	
	
	
	
}
