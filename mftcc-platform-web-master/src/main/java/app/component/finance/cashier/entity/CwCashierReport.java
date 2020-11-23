package app.component.finance.cashier.entity;
import app.base.BaseDomain;
/**
 * Title: CwCashierJournal.java Description:
 * 
 * @author：kaifa@dhcc.com.cn
 * @Mon Mar 27 16:43:21 CST 2017
 * @version：1.0
 **/
public class CwCashierReport extends BaseDomain {
	private String accountNo;// 账户编码关联cw_cashier_account.account_no
	private String accountName;// 账户名称
	private String beginBal;// 期初余额
	private String debit;// 收入
	private String credit;// 支出
	private String endBal;// 期末余额
	private String operate;// 操作

	/**
	 * @return 账户编码关联cw_cashier_account.account_no
	 */
	public String getAccountNo() {
		return accountNo;
	}
	/**
	 * @设置 账户编码关联cw_cashier_account.account_no
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	/**
	 * @return 账户名称
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @设置 账户名称
	 * @param accountName
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return 收入
	 */
	public String getDebit() {
		return debit;
	}
	/**
	 * @设置 收入
	 * @param debit
	 */
	public void setDebit(String debit) {
		this.debit = debit;
	}
	/**
	 * @return 支出
	 */
	public String getCredit() {
		return credit;
	}
	/**
	 * @设置 支出
	 * @param chrCredit
	 */
	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getBeginBal() {
		return beginBal;
	}
	public void setBeginBal(String beginBal) {
		this.beginBal = beginBal;
	}
	public String getEndBal() {
		return endBal;
	}
	public void setEndBal(String endBal) {
		this.endBal = endBal;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}

}