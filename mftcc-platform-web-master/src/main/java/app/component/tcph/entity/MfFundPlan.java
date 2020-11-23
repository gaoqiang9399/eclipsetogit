package app.component.tcph.entity;
import app.base.BaseDomain;
/**
* Title: MfFundPlan.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Nov 29 18:13:15 CST 2017
* @version：1.0
**/
public class MfFundPlan extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String id;//
	private String date;//日期
	private String fundPlanType;//类型：1-回款；2-出款；3-还款；
	private String unit;//单位
	private String project;//项目
	private Double fundPlanAmt;//金额
	private String fundPlanSts;//状态：1-正常；2-坏账；3-未补息；
	private String state;//说明
	private String regTime;//登记时间
	private String opNo;//登记人编号
	private String opName;//登记人
	private String mainNo;//公司编号
	private String mainName;//公司名称
	private Double ownFunds;//自有资金
	private Double otherFunds;//其他来源
	private Double dailyExpenses;//日常费用
	private Double receivableLoanFund;//应收贷出资金
	private String year;//年份
	private String month;//月份
	private String yearWeek;//周
	private Double earlyAmt;//期初金额


	/**
	 * @return 
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 日期
	 */
	public String getDate() {
	 	return date;
	}
	/**
	 * @设置 日期
	 * @param date
	 */
	public void setDate(String date) {
	 	this.date = date;
	}
	/**
	 * @return 类型：1-回款；2-出款；3-还款；
	 */
	public String getFundPlanType() {
	 	return fundPlanType;
	}
	/**
	 * @设置 类型：1-回款；2-出款；3-还款；
	 * @param fundPlanType
	 */
	public void setFundPlanType(String fundPlanType) {
	 	this.fundPlanType = fundPlanType;
	}
	/**
	 * @return 单位
	 */
	public String getUnit() {
	 	return unit;
	}
	/**
	 * @设置 单位
	 * @param unit
	 */
	public void setUnit(String unit) {
	 	this.unit = unit;
	}
	/**
	 * @return 项目
	 */
	public String getProject() {
	 	return project;
	}
	/**
	 * @设置 项目
	 * @param project
	 */
	public void setProject(String project) {
	 	this.project = project;
	}
	/**
	 * @return 金额
	 */
	public Double getFundPlanAmt() {
	 	return fundPlanAmt;
	}
	/**
	 * @设置 金额
	 * @param fundPlanAmt
	 */
	public void setFundPlanAmt(Double fundPlanAmt) {
	 	this.fundPlanAmt = fundPlanAmt;
	}
	/**
	 * @return 状态：1-正常；2-坏账；3-未补息；
	 */
	public String getFundPlanSts() {
	 	return fundPlanSts;
	}
	/**
	 * @设置 状态：1-正常；2-坏账；3-未补息；
	 * @param fundPlanSts
	 */
	public void setFundPlanSts(String fundPlanSts) {
	 	this.fundPlanSts = fundPlanSts;
	}
	/**
	 * @return 说明
	 */
	public String getState() {
	 	return state;
	}
	/**
	 * @设置 说明
	 * @param state
	 */
	public void setState(String state) {
	 	this.state = state;
	}
	/**
	 * @return 登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 登记人
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return the opName
	 */
	public String getOpName() {
		return opName;
	}
	/**
	 * @param opName the opName to set
	 */
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getMainNo() {
		return mainNo;
	}
	public void setMainNo(String mainNo) {
		this.mainNo = mainNo;
	}
	public String getMainName() {
		return mainName;
	}
	public void setMainName(String mainName) {
		this.mainName = mainName;
	}
	public Double getOwnFunds() {
		return ownFunds;
	}
	public void setOwnFunds(Double ownFunds) {
		this.ownFunds = ownFunds;
	}
	public Double getOtherFunds() {
		return otherFunds;
	}
	public void setOtherFunds(Double otherFunds) {
		this.otherFunds = otherFunds;
	}
	public Double getDailyExpenses() {
		return dailyExpenses;
	}
	public void setDailyExpenses(Double dailyExpenses) {
		this.dailyExpenses = dailyExpenses;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Double getEarlyAmt() {
		return earlyAmt;
	}
	public void setEarlyAmt(Double earlyAmt) {
		this.earlyAmt = earlyAmt;
	}
	public Double getReceivableLoanFund() {
		return receivableLoanFund;
	}
	public void setReceivableLoanFund(Double receivableLoanFund) {
		this.receivableLoanFund = receivableLoanFund;
	}
	public String getYearWeek() {
		return yearWeek;
	}
	public void setYearWeek(String yearWeek) {
		this.yearWeek = yearWeek;
	}
	
}
