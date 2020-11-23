package app.component.calc.core.entity;
import app.base.BaseDomain;
import app.component.app.entity.MfBusApply;
import app.component.pact.entity.MfBusFincAppChild;
import app.component.pact.entity.MfBusPact;
/**
* Title: MfRepayPlan.java
* Description: 生成费用计划所需参数类
* @author：kaifa@dhcc.com.cn
* @Tue May 16 21:02:41 CST 2017
* @version：1.0
**/
public class MfFeePlanParameter extends BaseDomain {
	
	private static final long serialVersionUID = 1L;
	private String itemNos;// 费用项编号 支持多编号需要"|1|2|3"
	private String amount;// 基准金额（目前需要传金额数值）
	private String standard;//基准项
	private String appId;//申请Id
	private String pactId;//合同Id
	private String fincId;//借据Id
	private String planBeginDate;//开始时间
	private String planEndDate;//结束时间
	private String takenMode;// 收取方式    1-一次收取;3-按年收取;4-按季收取;5-按月收取  6-按天收取  7-一次性按年收取
	private String computeMode;//计算方式    1-固额;2-按比例
	private String fixedQuota;//固定额度
	private String rate;//比例/费率
	private String rateType;//费率类型     1-年费率;2-月费率
	private String decimalDigits;//保留小数位数（四舍五入）    2-两位;1-一位;0-不保留
	private String repayPeriods;//还款计划期数
	private String pactAmt;//合同金额
	private String putOutAmt;//放款金额
	
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String opNo;//登记人员编号
	private String opName;//登记人员名称
	private String brNo;//登记人员部门编号
	private String brName;//登记人员部门名称
	private MfBusApply mfBusApply;//申请信息
	private MfBusPact mfBusPact;//合同信息
	private MfBusFincAppChild mfBusFincAppChild;//借据信息子表
	private Double feeSum;//收取费用 总和
	private String calcIntstFlag;
	private String feeCollectTime;//费用收取时间：1-放款时收取，2-还款时收取，3-单独收取
	private int sort;//排序
	private String pactIntstAmt;//合同本息
	private String putOutIntstAmt;//放款本息
	private String standards;//基准项集合
	public String getItemNos() {
		return itemNos;
	}
	public void setItemNos(String itemNos) {
		this.itemNos = itemNos;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPactId() {
		return pactId;
	}
	public void setPactId(String pactId) {
		this.pactId = pactId;
	}
	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
	}
	
	public String getPlanBeginDate() {
		return planBeginDate;
	}
	public void setPlanBeginDate(String planBeginDate) {
		this.planBeginDate = planBeginDate;
	}
	public String getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}
	public String getCusNo() {
		return cusNo;
	}
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getTakenMode() {
		return takenMode;
	}
	public void setTakenMode(String takenMode) {
		this.takenMode = takenMode;
	}
	public String getComputeMode() {
		return computeMode;
	}
	public void setComputeMode(String computeMode) {
		this.computeMode = computeMode;
	}
	public String getFixedQuota() {
		return fixedQuota;
	}
	public void setFixedQuota(String fixedQuota) {
		this.fixedQuota = fixedQuota;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getDecimalDigits() {
		return decimalDigits;
	}
	public void setDecimalDigits(String decimalDigits) {
		this.decimalDigits = decimalDigits;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public MfBusApply getMfBusApply() {
		return mfBusApply;
	}
	public void setMfBusApply(MfBusApply mfBusApply) {
		this.mfBusApply = mfBusApply;
	}
	public MfBusPact getMfBusPact() {
		return mfBusPact;
	}
	public void setMfBusPact(MfBusPact mfBusPact) {
		this.mfBusPact = mfBusPact;
	}
	public MfBusFincAppChild getMfBusFincAppChild() {
		return mfBusFincAppChild;
	}
	public void setMfBusFincAppChild(MfBusFincAppChild mfBusFincAppChild) {
		this.mfBusFincAppChild = mfBusFincAppChild;
	}
	public String getOpNo() {
		return opNo;
	}
	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public Double getFeeSum() {
		return feeSum;
	}
	public void setFeeSum(Double feeSum) {
		this.feeSum = feeSum;
	}
	public String getRepayPeriods() {
		return repayPeriods;
	}
	public void setRepayPeriods(String repayPeriods) {
		this.repayPeriods = repayPeriods;
	}
	public String getPactAmt() {
		return pactAmt;
	}
	public void setPactAmt(String pactAmt) {
		this.pactAmt = pactAmt;
	}
	public String getPutOutAmt() {
		return putOutAmt;
	}
	public void setPutOutAmt(String putOutAmt) {
		this.putOutAmt = putOutAmt;
	}
	public String getCalcIntstFlag() {
		return calcIntstFlag;
	}
	public void setCalcIntstFlag(String calcIntstFlag) {
		this.calcIntstFlag = calcIntstFlag;
	}
	
	/**
	 * 费用收取时间：1-放款时收取，2-还款时收取，3-单独收取
	 */
	public String getFeeCollectTime() {
		return feeCollectTime;
	}
	/**
	 * 费用收取时间：1-放款时收取，2-还款时收取，3-单独收取
	 */
	public void setFeeCollectTime(String feeCollectTime) {
		this.feeCollectTime = feeCollectTime;
	}
	/**
	 * 费用收取顺序
	 */
	public int getSort() {
		return sort;
	}
	/**
	 * 费用收取顺序
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getPactIntstAmt() {
		return pactIntstAmt;
	}
	public void setPactIntstAmt(String pactIntstAmt) {
		this.pactIntstAmt = pactIntstAmt;
	}
	public String getPutOutIntstAmt() {
		return putOutIntstAmt;
	}
	public void setPutOutIntstAmt(String putOutIntstAmt) {
		this.putOutIntstAmt = putOutIntstAmt;
	}
	public String getStandards() {
		return standards;
	}
	public void setStandards(String standards) {
		this.standards = standards;
	}
}