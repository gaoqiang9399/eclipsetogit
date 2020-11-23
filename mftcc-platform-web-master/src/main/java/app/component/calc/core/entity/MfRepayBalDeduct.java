package app.component.calc.core.entity;
import app.base.BaseDomain;
/**
* Title: MfRepayBalDeduct.java
* Description: 结余冲抵实体
* @author：kaifa@dhcc.com.cn
* @Fri May 26 17:28:50 CST 2017
* @version：1.0
**/
public class MfRepayBalDeduct extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7587041106437658539L;
	private String balId;//
	private String pactId;//合同号
	private String fincId;//借据号
	private String balAgainstType;//结余冲抵类型:1-结余，2-冲抵 ,3-结余退款
	private Double balAgainstAmt;//结余冲抵金额
	private String balAgainstTime;//结余冲抵时间
	private String repayId;//还款历史总表mf_repay_history的repay_id
	private String regTime;//登记时间17为格式20170506 15:35:40
	private String lstModTime;//修改时间 17为格式20170506 15:35:40
	private String opNo;//登记人员编号
	private String opName;//登记人员名称
	private String brNo;//登记人员部门编号
	private String brName;//登记人员部门名称
	private String ext1;//
	private String ext2;//

	/**
	 * @return 
	 */
	public String getBalId() {
	 	return balId;
	}
	/**
	 * @设置 
	 * @param balId
	 */
	public void setBalId(String balId) {
	 	this.balId = balId;
	}
	/**
	 * @return 合同号
	 */
	public String getPactId() {
	 	return pactId;
	}
	/**
	 * @设置 合同号
	 * @param pactId
	 */
	public void setPactId(String pactId) {
	 	this.pactId = pactId;
	}
	/**
	 * @return 借据号
	 */
	public String getFincId() {
	 	return fincId;
	}
	/**
	 * @设置 借据号
	 * @param fincId
	 */
	public void setFincId(String fincId) {
	 	this.fincId = fincId;
	}
	/**
	 * @return 结余冲抵类型:1-结余，2-冲抵,3-结余退款
	 */
	public String getBalAgainstType() {
	 	return balAgainstType;
	}
	/**
	 * @设置 结余冲抵类型:1-结余，2-冲抵,3-结余退款
	 * @param balAgainstType
	 */
	public void setBalAgainstType(String balAgainstType) {
	 	this.balAgainstType = balAgainstType;
	}
	/**
	 * @return 结余冲抵金额
	 */
	public Double getBalAgainstAmt() {
	 	return balAgainstAmt;
	}
	/**
	 * @设置 结余冲抵金额
	 * @param balAgainstAmt
	 */
	public void setBalAgainstAmt(Double balAgainstAmt) {
	 	this.balAgainstAmt = balAgainstAmt;
	}
	/**
	 * @return 结余冲抵时间
	 */
	public String getBalAgainstTime() {
	 	return balAgainstTime;
	}
	/**
	 * @设置 结余冲抵时间
	 * @param balAgainstTime
	 */
	public void setBalAgainstTime(String balAgainstTime) {
	 	this.balAgainstTime = balAgainstTime;
	}
	/**
	 * @return 还款历史总表mf_repay_history的repay_id
	 */
	public String getRepayId() {
	 	return repayId;
	}
	/**
	 * @设置 还款历史总表mf_repay_history的repay_id
	 * @param repayId
	 */
	public void setRepayId(String repayId) {
	 	this.repayId = repayId;
	}
	/**
	 * @return 登记时间17为格式20170506 15:35:40
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间17为格式20170506 15:35:40
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 修改时间 17为格式20170506 15:35:40
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 修改时间 17为格式20170506 15:35:40
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 登记人员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 登记人员名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人员名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记人员部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记人员部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记人员部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记人员部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
}