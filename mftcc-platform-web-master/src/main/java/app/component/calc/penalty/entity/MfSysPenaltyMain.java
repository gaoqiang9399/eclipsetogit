package app.component.calc.penalty.entity;
import app.base.BaseDomain;
/**
* Title: MfSysPenaltyMain.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Jun 27 09:04:25 CST 2017
* @version：1.0
**/
public class MfSysPenaltyMain extends BaseDomain {
	private String id;//唯一序号
	private String kindNo;//产品种类编号
	private String repayTypeNo;//还款方式编号
	private String penaltyReceiveType;//违约金计算类型:1-百分比%,2-固定金额,3-阶梯比例  逾期违约金Parm_dic表OVER_PENALTY_ACCOUNT_TYPE,提前还款违约金Parm_dic表PRE_REPAY_PENALTY_ACCOUNT_TYPE
	private Double penaltyReceiveValue;//违约金计算类型对应值：违约金计算类型选择固定额度时存储的是金额，选择固定百分比时存储的是百分比如:5%，存储的是5，选择阶梯比例时查询mf_sys_penalty_child
	private String penaltyCalcBaseType;//违约金计算基数：逾期违约金:1-合同金额,2-借据金额,3-逾期本金,4-逾期金额 Parm_dic表OVER_PENALTY_ACCOUNT_BASE 提前还款违约金:1-合同金额,2-借据金额,3-提前还款本金   Parm_dic表PRE_REPAY_PENALTY_ACCOUNT_BASE
	private String penaltyReceiveCycle;//违约金计算周期：1-一次性（即每期逾期后，只收取一次违约金）2-按逾期天数
	private Integer repaymentLimitMouth;//从放款时间开始几个月内不得提前还款
	private String useFlag;//启用标识0-禁用,1-启用parm_dic表key_name=USE_FLAG
	private String penaltyType;//违约金分类 ：1-逾期违约金，提前还款违约金

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
	 * @return 产品种类编号
	 */
	public String getKindNo() {
	 	return kindNo;
	}
	/**
	 * @设置 产品种类编号
	 * @param kindNo
	 */
	public void setKindNo(String kindNo) {
	 	this.kindNo = kindNo;
	}
	/**
	 * @return 还款方式编号
	 */
	public String getRepayTypeNo() {
	 	return repayTypeNo;
	}
	/**
	 * @设置 还款方式编号
	 * @param repayTypeNo
	 */
	public void setRepayTypeNo(String repayTypeNo) {
	 	this.repayTypeNo = repayTypeNo;
	}
	/**
	 * @return 违约金计算类型:1-百分比%,2-固定金额,3-阶梯比例  逾期违约金Parm_dic表OVER_PENALTY_ACCOUNT_TYPE,提前还款违约金Parm_dic表PRE_REPAY_PENALTY_ACCOUNT_TYPE
	 */
	public String getPenaltyReceiveType() {
	 	return penaltyReceiveType;
	}
	/**
	 * @设置 违约金计算类型:1-百分比%,2-固定金额,3-阶梯比例  逾期违约金Parm_dic表OVER_PENALTY_ACCOUNT_TYPE,提前还款违约金Parm_dic表PRE_REPAY_PENALTY_ACCOUNT_TYPE
	 * @param penaltyReceiveType
	 */
	public void setPenaltyReceiveType(String penaltyReceiveType) {
	 	this.penaltyReceiveType = penaltyReceiveType;
	}
	/**
	 * @return 违约金计算类型对应值：违约金计算类型选择固定额度时存储的是金额，选择固定百分比时存储的是百分比如:5%，存储的是5，选择阶梯比例时查询mf_sys_penalty_child
	 */
	public Double getPenaltyReceiveValue() {
	 	return penaltyReceiveValue;
	}
	/**
	 * @设置 违约金计算类型对应值：违约金计算类型选择固定额度时存储的是金额，选择固定百分比时存储的是百分比如:5%，存储的是5，选择阶梯比例时查询mf_sys_penalty_child
	 * @param penaltyReceiveValue
	 */
	public void setPenaltyReceiveValue(Double penaltyReceiveValue) {
	 	this.penaltyReceiveValue = penaltyReceiveValue;
	}
	/**
	 * @return 违约金计算基数：逾期违约金:1-合同金额,2-借据金额,3-逾期本金,4-逾期金额 Parm_dic表OVER_PENALTY_ACCOUNT_BASE 提前还款违约金:1-合同金额,2-借据金额,3-提前还款本金   Parm_dic表PRE_REPAY_PENALTY_ACCOUNT_BASE
	 */
	public String getPenaltyCalcBaseType() {
	 	return penaltyCalcBaseType;
	}
	/**
	 * @设置 违约金计算基数：逾期违约金:1-合同金额,2-借据金额,3-逾期本金,4-逾期金额 Parm_dic表OVER_PENALTY_ACCOUNT_BASE 提前还款违约金:1-合同金额,2-借据金额,3-提前还款本金   Parm_dic表PRE_REPAY_PENALTY_ACCOUNT_BASE
	 * @param penaltyCalcBaseType
	 */
	public void setPenaltyCalcBaseType(String penaltyCalcBaseType) {
	 	this.penaltyCalcBaseType = penaltyCalcBaseType;
	}
	/**
	 * @return 违约金计算周期：1-一次性（即每期逾期后，只收取一次违约金）2-按逾期天数
	 */
	public String getPenaltyReceiveCycle() {
	 	return penaltyReceiveCycle;
	}
	/**
	 * @设置 违约金计算周期：1-一次性（即每期逾期后，只收取一次违约金）2-按逾期天数
	 * @param penaltyReceiveCycle
	 */
	public void setPenaltyReceiveCycle(String penaltyReceiveCycle) {
	 	this.penaltyReceiveCycle = penaltyReceiveCycle;
	}
	/**
	 * @return 从放款时间开始几个月内不得提前还款
	 */
	public Integer getRepaymentLimitMouth() {
	 	return repaymentLimitMouth;
	}
	/**
	 * @设置 从放款时间开始几个月内不得提前还款
	 * @param repaymentLimitMouth
	 */
	public void setRepaymentLimitMouth(Integer repaymentLimitMouth) {
	 	this.repaymentLimitMouth = repaymentLimitMouth;
	}
	/**
	 * @return 启用标识0-禁用,1-启用parm_dic表key_name=USE_FLAG
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 启用标识0-禁用,1-启用parm_dic表key_name=USE_FLAG
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 违约金分类 ：1-逾期违约金，提前还款违约金
	 */
	public String getPenaltyType() {
	 	return penaltyType;
	}
	/**
	 * @设置 违约金分类 ：1-逾期违约金，提前还款违约金
	 * @param penaltyType
	 */
	public void setPenaltyType(String penaltyType) {
	 	this.penaltyType = penaltyType;
	}
}