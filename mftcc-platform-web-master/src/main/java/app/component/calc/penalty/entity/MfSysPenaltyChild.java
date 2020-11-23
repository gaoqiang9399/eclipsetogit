package app.component.calc.penalty.entity;
import app.base.BaseDomain;
/**
* Title: MfSysPenaltyChild.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Jul 01 17:27:43 CST 2017
* @version：1.0
**/
public class MfSysPenaltyChild extends BaseDomain {
	private String id;//唯一序号
	private String idMain;//主表mf_sys_penalty_main的id
	private Integer loanMouthMin;//贷款期限下限Min
	private Integer loanMouthMax;//贷款期限上限Max
	private Integer surplusLoanMouthMin;//剩余贷款期限下限Min
	private Integer surplusLoanMouthMax;//剩余贷款期限上限Max
	private Double multiStepRatio;//阶梯比例百分制
	private String operate;

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
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
}