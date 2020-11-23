package app.component.oa.debt.entity;
import java.math.BigDecimal;

import app.base.BaseDomain;
/**
* Title: MfOaDebtReturnHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Dec 22 10:32:09 CST 2016
* @version：1.0
**/
public class MfOaDebtReturnHis extends BaseDomain {
	private String hisId;//历史编号
	private String debtId;//借款编号
	private double returnAmt;//本次还款
	private BigDecimal debtAmt;//欠款金额
	private String returnTime;//归还时间
	private String returnType;//还款方式 现金/转账
	private String remark;//备注
	private String regTime;//登记时间
	private double sumApplyAmt;//借款总额
	private String opName;//借款人姓名
	private String brName;//部门名称
	/**
	 * @return 历史编号
	 */
	public String getHisId() {
	 	return hisId;
	}
	/**
	 * @设置 历史编号
	 * @param hisId
	 */
	public void setHisId(String hisId) {
	 	this.hisId = hisId;
	}
	/**
	 * @return 借款编号
	 */
	public String getDebtId() {
	 	return debtId;
	}
	/**
	 * @设置 借款编号
	 * @param debtId
	 */
	public void setDebtId(String debtId) {
	 	this.debtId = debtId;
	}
	/**
	 * @return 本次还款
	 */
	public BigDecimal getReturnAmt() {
	 	return BigDecimal.valueOf(returnAmt);
	}
	/**
	 * @设置 本次还款
	 * @param returnAmt
	 */
	public void setReturnAmt(double returnAmt) {
		System.out.println(returnAmt);
		//returnAmt;
	 	this.returnAmt =returnAmt;
	}
	/**
	 * @return 欠款金额
	 */
	public BigDecimal getDebtAmt() {
	 	return debtAmt;
	}
	/**
	 * @设置 欠款金额
	 * @param debtAmt
	 */
	public void setDebtAmt(BigDecimal debtAmt) {
	 	this.debtAmt = debtAmt;
	}
	/**
	 * @return 归还时间
	 */
	public String getReturnTime() {
	 	return returnTime;
	}
	/**
	 * @设置 归还时间
	 * @param returnTime
	 */
	public void setReturnTime(String returnTime) {
	 	this.returnTime = returnTime;
	}
	/**
	 * @return 还款方式 预留
	 */
	public String getReturnType() {
	 	return returnType;
	}
	/**
	 * @设置 还款方式 预留
	 * @param returnType
	 */
	public void setReturnType(String returnType) {
	 	this.returnType = returnType;
	}
	/**
	 * @return 备注
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
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
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public BigDecimal getSumApplyAmt() {
		return BigDecimal.valueOf(sumApplyAmt);
	}
	public void setSumApplyAmt(double sumApplyAmt) {
		this.sumApplyAmt = sumApplyAmt;
	}
	@Override
	public String toString() {
		return "MfOaDebtReturnHis [hisId=" + hisId + ", debtId=" + debtId
				+ ", returnAmt=" + returnAmt + ", debtAmt=" + debtAmt
				+ ", returnTime=" + returnTime + ", returnType=" + returnType
				+ ", remark=" + remark + ", regTime=" + regTime + "]";
	}
	
}