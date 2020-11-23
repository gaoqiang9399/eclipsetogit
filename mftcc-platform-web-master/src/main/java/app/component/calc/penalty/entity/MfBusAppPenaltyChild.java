package app.component.calc.penalty.entity;
import app.base.BaseDomain;
/**
* Title: MfBusAppPenaltyChild.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Jul 01 17:33:08 CST 2017
* @version：1.0
**/
public class MfBusAppPenaltyChild extends BaseDomain {
	private String id;//唯一序号
	private String appId;//申请id
	private String pactId;//合同id
	private String fincId;//借据id
	private String sysPenaltyChildId;//表mf_sys_penalty_child的id
	private String idMain;//主表mf_sys_penalty_main的id
	private Integer loanMouthMin;//贷款期限下限Min
	private Integer loanMouthMax;//贷款期限上限Max
	private Integer surplusLoanMouthMin;//剩余贷款期限下限Min
	private Integer surplusLoanMouthMax;//剩余贷款期限上限Max
	private Double multiStepRatio;//阶梯比例百分制

	/**
	 * @return 唯一序号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 唯一序号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 申请id
	 */
	public String getAppId() {
	 	return appId;
	}
	/**
	 * @设置 申请id
	 * @param appId
	 */
	public void setAppId(String appId) {
	 	this.appId = appId;
	}
	/**
	 * @return 合同id
	 */
	public String getPactId() {
	 	return pactId;
	}
	/**
	 * @设置 合同id
	 * @param pactId
	 */
	public void setPactId(String pactId) {
	 	this.pactId = pactId;
	}
	/**
	 * @return 借据id
	 */
	public String getFincId() {
	 	return fincId;
	}
	/**
	 * @设置 借据id
	 * @param fincId
	 */
	public void setFincId(String fincId) {
	 	this.fincId = fincId;
	}
	/**
	 * @return 表mf_sys_penalty_child的id
	 */
	public String getSysPenaltyChildId() {
	 	return sysPenaltyChildId;
	}
	/**
	 * @设置 表mf_sys_penalty_child的id
	 * @param sysPenaltyChildId
	 */
	public void setSysPenaltyChildId(String sysPenaltyChildId) {
	 	this.sysPenaltyChildId = sysPenaltyChildId;
	}
	/**
	 * @return 主表mf_sys_penalty_main的id
	 */
	public String getIdMain() {
	 	return idMain;
	}
	/**
	 * @设置 主表mf_sys_penalty_main的id
	 * @param idMain
	 */
	public void setIdMain(String idMain) {
	 	this.idMain = idMain;
	}
	/**
	 * @return 贷款期限下限Min
	 */
	public Integer getLoanMouthMin() {
	 	return loanMouthMin;
	}
	/**
	 * @设置 贷款期限下限Min
	 * @param loanMouthMin
	 */
	public void setLoanMouthMin(Integer loanMouthMin) {
	 	this.loanMouthMin = loanMouthMin;
	}
	/**
	 * @return 贷款期限上限Max
	 */
	public Integer getLoanMouthMax() {
	 	return loanMouthMax;
	}
	/**
	 * @设置 贷款期限上限Max
	 * @param loanMouthMax
	 */
	public void setLoanMouthMax(Integer loanMouthMax) {
	 	this.loanMouthMax = loanMouthMax;
	}
	/**
	 * @return 剩余贷款期限下限Min
	 */
	public Integer getSurplusLoanMouthMin() {
	 	return surplusLoanMouthMin;
	}
	/**
	 * @设置 剩余贷款期限下限Min
	 * @param surplusLoanMouthMin
	 */
	public void setSurplusLoanMouthMin(Integer surplusLoanMouthMin) {
	 	this.surplusLoanMouthMin = surplusLoanMouthMin;
	}
	/**
	 * @return 剩余贷款期限上限Max
	 */
	public Integer getSurplusLoanMouthMax() {
	 	return surplusLoanMouthMax;
	}
	/**
	 * @设置 剩余贷款期限上限Max
	 * @param surplusLoanMouthMax
	 */
	public void setSurplusLoanMouthMax(Integer surplusLoanMouthMax) {
	 	this.surplusLoanMouthMax = surplusLoanMouthMax;
	}
	/**
	 * @return 阶梯比例百分制
	 */
	public Double getMultiStepRatio() {
	 	return multiStepRatio;
	}
	/**
	 * @设置 阶梯比例百分制
	 * @param multiStepRatio
	 */
	public void setMultiStepRatio(Double multiStepRatio) {
	 	this.multiStepRatio = multiStepRatio;
	}
}