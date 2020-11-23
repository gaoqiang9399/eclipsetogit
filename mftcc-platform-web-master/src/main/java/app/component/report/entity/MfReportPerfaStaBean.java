package app.component.report.entity;
/**
 * 
 * @author wte
 * 业绩统计表
 */
public class MfReportPerfaStaBean {
	

	private String nowDate; //日期
	private double yestdayStock; //昨日存量
	private double nowDateLoan; //当日放款
	private double nowDateRepay; //当日还本
	private double cumuStock; //累计存量
	private int cumuStockNum; //累计放款件数
	private int yestDayCustom;//昨日存量客户/个
	private int nowDateLoanNum;//当日放款/个
	private int settleNum;//结清/个
	private int yestDayCustomNum;//累计存量客户/个
	private int settle;//结清
	private int settlCreditAgain;//结清再贷
	private double derate;//降额
	private String cusNo;//客户号
	private String putoutDate;//放款时间
	private double loanBal;//放款余额
	private String findId;//流水号
	private String repayDate;//还款日期
	private double prcpSum;//还款总额
	private double initialAmt;//初始余额
	private double nextDayAmt;//第二日放款
	private double nextDayReim;//第二日还款
	private int putOutCount;//累计放款笔数
	private int cusOverNum; //当日结清的客户数
	private int fincIdOverNum; //当日结清的笔数
    private String finishDate;//结清日期

	
	public void setCumuStockNum(int cumuStockNum) {
		this.cumuStockNum = cumuStockNum;
	}
	public void setNextDayReim(double nextDayReim) {
		this.nextDayReim = nextDayReim;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public int getCusOverNum() {
		return cusOverNum;
	}
	public void setCusOverNum(int cusOverNum) {
		this.cusOverNum = cusOverNum;
	}
	public int getFincIdOverNum() {
		return fincIdOverNum;
	}
	public void setFincIdOverNum(int fincIdOverNum) {
		this.fincIdOverNum = fincIdOverNum;
	}
	public int getCumuStockNum() {
		return cumuStockNum;
	}
	
	public int getPutOutCount() {
		return putOutCount;
	}
	public void setPutOutCount(int putOutCount) {
		this.putOutCount = putOutCount;
	}
	public double getNextDayReim() {
		return nextDayReim;
	}
	
	public double getNextDayAmt() {
		return nextDayAmt;
	}
	public void setNextDayAmt(double nextDayAmt) {
		this.nextDayAmt = nextDayAmt;
	}
	public String getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
	public double getPrcpSum() {
		return prcpSum;
	}
	public void setPrcpSum(double prcpSum) {
		this.prcpSum = prcpSum;
	}
	public double getInitialAmt() {
		return initialAmt;
	}
	public void setInitialAmt(double initialAmt) {
		this.initialAmt = initialAmt;
	}
	public String getNowDate() {
		return nowDate;
	}
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
	public double getNowDateLoan() {
		return nowDateLoan;
	}
	public void setNowDateLoan(double nowDateLoan) {
		this.nowDateLoan = nowDateLoan;
	}
	public double getNowDateRepay() {
		return nowDateRepay;
	}
	public void setNowDateRepay(double nowDateRepay) {
		this.nowDateRepay = nowDateRepay;
	}
	public double getCumuStock() {
		return cumuStock;
	}
	public void setCumuStock(double cumuStock) {
		this.cumuStock = cumuStock;
	}
	
	public int getYestDayCustom() {
		return yestDayCustom;
	}
	public void setYestDayCustom(int yestDayCustom) {
		this.yestDayCustom = yestDayCustom;
	}
	public int getNowDateLoanNum() {
		return nowDateLoanNum;
	}
	public void setNowDateLoanNum(int nowDateLoanNum) {
		this.nowDateLoanNum = nowDateLoanNum;
	}
	public int getSettleNum() {
		return settleNum;
	}
	public void setSettleNum(int settleNum) {
		this.settleNum = settleNum;
	}
	public int getYestDayCustomNum() {
		return yestDayCustomNum;
	}
	public void setYestDayCustomNum(int yestDayCustomNum) {
		this.yestDayCustomNum = yestDayCustomNum;
	}
	
	public int getSettle() {
		return settle;
	}
	public void setSettle(int settle) {
		this.settle = settle;
	}
	public double getDerate() {
		return derate;
	}
	
	public int getSettlCreditAgain() {
		return settlCreditAgain;
	}
	public void setSettlCreditAgain(int settlCreditAgain) {
		this.settlCreditAgain = settlCreditAgain;
	}
	public void setDerate(double derate) {
		this.derate = derate;
	}
	public String getCusNo() {
		return cusNo;
	}
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	public String getPutoutDate() {
		return putoutDate;
	}
	public void setPutoutDate(String putoutDate) {
		this.putoutDate = putoutDate;
	}
	public double getLoanBal() {
		return loanBal;
	}
	public void setLoanBal(double loanBal) {
		this.loanBal = loanBal;
	}
	public String getFindId() {
		return findId;
	}
	public void setFindId(String findId) {
		this.findId = findId;
	}
	public double getYestdayStock() {
		return yestdayStock;
	}
	public void setYestdayStock(double yestdayStock) {
		this.yestdayStock = yestdayStock;
	}

	
	
}
