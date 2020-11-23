package app.component.finance.othreport.entity;
import app.base.BaseDomain;
/**
 * Title: CwVoucherAssist.java Description:
 * 
 * @author：kaifa@dhcc.com.cn
 * @Thu Mar 02 14:09:11 CST 2017
 * @version：1.0
 **/
public class AccountLedger extends BaseDomain {
	
	private String voucherNo;// 凭证编号
	private String weeks;// 周期
	private String voucherDate;// 凭证日期
	private String pzPrefix;// 凭证前缀
	private String voucherNoteNo;// 凭证字号
	private String remark;// 摘要
	private String drAmt;// 借方发生额
	private String crAmt;// 贷方发生额
	private String dcInd;// 借贷标志1：借；2：贷；
	private String bal;// 余额

	/**
	 * @return 凭证编号
	 */
	public String getVoucherNo() {
		return voucherNo;
	}
	/**
	 * @设置 凭证编号
	 * @param voucherNo
	 */
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	public String getWeeks() {
		return weeks;
	}
	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
	/**
	 * @return 凭证日期
	 */
	public String getVoucherDate() {
		return voucherDate;
	}
	/**
	 * @设置 凭证日期
	 * @param voucherDate
	 */
	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}

	/**
	 * @return 借贷标志1：借；2：贷；
	 */
	public String getDcInd() {
		return dcInd;
	}
	/**
	 * @设置 借贷标志1：借；2：贷；
	 * @param dcInd
	 */
	public void setDcInd(String dcInd) {
		this.dcInd = dcInd;
	}
	public String getPzPrefix() {
		return pzPrefix;
	}
	public void setPzPrefix(String pzPrefix) {
		this.pzPrefix = pzPrefix;
	}
	public String getVoucherNoteNo() {
		return voucherNoteNo;
	}
	public void setVoucherNoteNo(String voucherNoteNo) {
		this.voucherNoteNo = voucherNoteNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDrAmt() {
		return drAmt;
	}
	public void setDrAmt(String drAmt) {
		this.drAmt = drAmt;
	}
	public String getCrAmt() {
		return crAmt;
	}
	public void setCrAmt(String crAmt) {
		this.crAmt = crAmt;
	}
	public String getBal() {
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}

}