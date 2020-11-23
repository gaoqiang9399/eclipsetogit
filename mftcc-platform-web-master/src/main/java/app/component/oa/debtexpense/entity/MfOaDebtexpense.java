package app.component.oa.debtexpense.entity;
import app.base.BaseDomain;
/**
* Title: MfOaDebtexpense.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sun Jan 22 10:12:02 CST 2017
* @version：1.0
**/
public class MfOaDebtexpense extends BaseDomain {
	private String debtexpenseId;//借款报销编号
	private String name;//名称
	private double amt;//金额
	private String regTime;//登记时间
	private String relId;//关联借款，报销，还款表中的id
	private String debtexpenseSts;//1未提交2审批中3复核中4审批未通过5已完成
	private String lstModTime;//最后修改时间
	private String feeType;//业务类型1报销2借款3还款
	private String busType;//事件类型
	private String opNo;//借款人编号
	private String opName;//借款人姓名
	private String brNo;//部门编号
	private String brName;//部门名称
	
	public String getDebtexpenseId() {
		return debtexpenseId;
	}
	public void setDebtexpenseId(String debtexpenseId) {
		this.debtexpenseId = debtexpenseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public double getAmt() {
		return amt;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getRelId() {
		return relId;
	}
	public void setRelId(String relId) {
		this.relId = relId;
	}
	public String getDebtexpenseSts() {
		return debtexpenseSts;
	}
	public void setDebtexpenseSts(String debtexpenseSts) {
		this.debtexpenseSts = debtexpenseSts;
	}
	public String getLstModTime() {
		return lstModTime;
	}
	public void setLstModTime(String lstModTime) {
		this.lstModTime = lstModTime;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
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
	
}