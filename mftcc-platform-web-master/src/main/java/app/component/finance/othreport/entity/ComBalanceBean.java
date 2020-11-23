package app.component.finance.othreport.entity;
/**
 * 科目余额表、试算平衡表通用
 * @author Yanght
 *
 */
public class ComBalanceBean {
	private String accNo;//科目号
	private String accName;//科目名称
	private String drBal1;//期初借方余额
	private String crBal1;//期初贷方余额
	private String mdrAmt;//本期借方发生额
	private String mcrAmt;//本期贷方发生额
	private String ydrAmt;//本年借方累计发生额
	private String ycrAmt;//本年贷方累计发生额
	private String drBal2;//期末借方余额
	private String crBal2;//期末贷方余额
	
	private String accLvl;//科目级别
	private String accType;//科目类别
	private String subAccYn;//是否有下级科目
	private String accHrt;//科目控制字
	private String upAccHrt;//上级科目控制字
	private String dcInd;
	
	
	public String getDcInd() {
		return dcInd;
	}
	public void setDcInd(String dcInd) {
		this.dcInd = dcInd;
	}
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
	public String getDrBal1() {
		return drBal1;
	}
	public void setDrBal1(String drBal1) {
		this.drBal1 = drBal1;
	}
	public String getCrBal1() {
		return crBal1;
	}
	public void setCrBal1(String crBal1) {
		this.crBal1 = crBal1;
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
	public String getDrBal2() {
		return drBal2;
	}
	public void setDrBal2(String drBal2) {
		this.drBal2 = drBal2;
	}
	public String getCrBal2() {
		return crBal2;
	}
	public void setCrBal2(String crBal2) {
		this.crBal2 = crBal2;
	}
	public String getAccLvl() {
		return accLvl;
	}
	public void setAccLvl(String accLvl) {
		this.accLvl = accLvl;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getSubAccYn() {
		return subAccYn;
	}
	public void setSubAccYn(String subAccYn) {
		this.subAccYn = subAccYn;
	}
	public String getAccHrt() {
		return accHrt;
	}
	public void setAccHrt(String accHrt) {
		this.accHrt = accHrt;
	}
	public String getUpAccHrt() {
		return upAccHrt;
	}
	public void setUpAccHrt(String upAccHrt) {
		this.upAccHrt = upAccHrt;
	}

	

	
}
