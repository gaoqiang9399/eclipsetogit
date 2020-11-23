package app.component.finance.othreport.entity;

import app.base.BaseDomain;

/**
 * 类描述：总分类账(查询)
 * @author liwei
 * @date 2016-12-30 下午3:19:49
 */
public class GeneralLedger  extends BaseDomain {
	//借方-本期合计
	private String debitTermSum;
	//借方-本年累计
	private String debitYearSum;
	//贷方-本期合计
	private String lenderTermSum;
	//贷方-本年累计
	private String lenderYearSum;
	//当前借方余额
	private String drBal;
	//当前贷方余额
	private String crBal;
	
	public String getDebitTermSum() {
		return debitTermSum;
	}
	public void setDebitTermSum(String debitTermSum) {
		this.debitTermSum = debitTermSum;
	}
	public String getDebitYearSum() {
		return debitYearSum;
	}
	public void setDebitYearSum(String debitYearSum) {
		this.debitYearSum = debitYearSum;
	}
	public String getLenderTermSum() {
		return lenderTermSum;
	}
	public void setLenderTermSum(String lenderTermSum) {
		this.lenderTermSum = lenderTermSum;
	}
	public String getLenderYearSum() {
		return lenderYearSum;
	}
	public void setLenderYearSum(String lenderYearSum) {
		this.lenderYearSum = lenderYearSum;
	}
	
	/**
	 * 当前借方余额
	 * @return
	 */
	public String getDrBal() {
		return drBal;
	}
	public void setDrBal(String drBal) {
		this.drBal = drBal;
	}
	/**
	 * 当前贷方余额
	 * @return
	 */
	public String getCrBal() {
		return crBal;
	}
	public void setCrBal(String crBal) {
		this.crBal = crBal;
	}
	
}
