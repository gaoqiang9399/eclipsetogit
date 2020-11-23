package app.component.finance.othreport.entity;
import app.base.BaseDomain;
/**
 * Title: CwVoucherAssist.java Description:
 * 
 * @author：kaifa@dhcc.com.cn
 * @Thu Mar 02 14:09:11 CST 2017
 * @version：1.0
 **/
public class AccountBalance extends BaseDomain {

	private String itemVNo;// 辅助项目编号
	private String itemVName;// 辅助项目名称
	private String sdrBal;// 期初借方余额
	private String scrBal;// 期初贷方余额
	private String mdrAmt;// 本期借方发生额
	private String mcrAmt;// 本期贷方发生额
	private String ydrAmt;// 本年借方累计发生额
	private String ycrAmt;// 本年贷方累计发生额
	private String edrBal;// 期末借方余额
	private String ecrBal;// 期末贷方余额
	public String getItemVNo() {
		return itemVNo;
	}
	public void setItemVNo(String itemVNo) {
		this.itemVNo = itemVNo;
	}
	public String getItemVName() {
		return itemVName;
	}
	public void setItemVName(String itemVName) {
		this.itemVName = itemVName;
	}
	public String getSdrBal() {
		return sdrBal;
	}
	public void setSdrBal(String sdrBal) {
		this.sdrBal = sdrBal;
	}
	public String getScrBal() {
		return scrBal;
	}
	public void setScrBal(String scrBal) {
		this.scrBal = scrBal;
	}
	public String getMdrAmt() {
		return mdrAmt;
	}
	public void setMdrAmt(String mdrAmt) {
		this.mdrAmt = mdrAmt;
	}
	public String getMcrAmt() {
		return mcrAmt;
	}
	public void setMcrAmt(String mcrAmt) {
		this.mcrAmt = mcrAmt;
	}
	public String getYdrAmt() {
		return ydrAmt;
	}
	public void setYdrAmt(String ydrAmt) {
		this.ydrAmt = ydrAmt;
	}
	public String getYcrAmt() {
		return ycrAmt;
	}
	public void setYcrAmt(String ycrAmt) {
		this.ycrAmt = ycrAmt;
	}
	public String getEdrBal() {
		return edrBal;
	}
	public void setEdrBal(String edrBal) {
		this.edrBal = edrBal;
	}
	public String getEcrBal() {
		return ecrBal;
	}
	public void setEcrBal(String ecrBal) {
		this.ecrBal = ecrBal;
	}

}