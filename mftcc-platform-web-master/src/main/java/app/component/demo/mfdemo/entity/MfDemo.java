package app.component.demo.mfdemo.entity;
import app.base.BaseDomain;

/**
* Title: MfDemo.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Apr 27 19:09:27 CST 2017
* @version：1.0
**/
public class MfDemo extends BaseDomain {
	private String demoId;//主键流水号
	private String demoName;//名称
	private String registerDate;//注册日期
	private String lastModifyTime;//最近修改时间（含日期）
	private Double appAmt;//申请金额
	private Integer term;//期数
	private Double rate;//利率
	private String sex;//性别-数据字典
	private String cusType;//客户类型-数据字典
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String brNo;//部门编号
	private String brName;//部门名称
	private String ext1;//

	/**
	 * @return 主键流水号
	 */
	public String getDemoId() {
	 	return demoId;
	}
	/**
	 * @设置 主键流水号
	 * @param demoId
	 */
	public void setDemoId(String demoId) {
	 	this.demoId = demoId;
	}
	/**
	 * @return 名称
	 */
	public String getDemoName() {
	 	return demoName;
	}
	/**
	 * @设置 名称
	 * @param demoName
	 */
	public void setDemoName(String demoName) {
	 	this.demoName = demoName;
	}
	/**
	 * @return 注册日期
	 */
	public String getRegisterDate() {
	 	return registerDate;
	}
	/**
	 * @设置 注册日期
	 * @param registerDate
	 */
	public void setRegisterDate(String registerDate) {
	 	this.registerDate = registerDate;
	}
	/**
	 * @return 最近修改时间（含日期）
	 */
	public String getLastModifyTime() {
	 	return lastModifyTime;
	}
	/**
	 * @设置 最近修改时间（含日期）
	 * @param lastModifyTime
	 */
	public void setLastModifyTime(String lastModifyTime) {
	 	this.lastModifyTime = lastModifyTime;
	}
	/**
	 * @return 申请金额
	 */
	public Double getAppAmt() {
	 	return appAmt;
	}
	/**
	 * @设置 申请金额
	 * @param appAmt
	 */
	public void setAppAmt(Double appAmt) {
	 	this.appAmt = appAmt;
	}
	/**
	 * @return 期数
	 */
	public Integer getTerm() {
	 	return term;
	}
	/**
	 * @设置 期数
	 * @param term
	 */
	public void setTerm(Integer term) {
	 	this.term = term;
	}
	/**
	 * @return 利率
	 */
	public Double getRate() {
	 	return rate;
	}
	/**
	 * @设置 利率
	 * @param rate
	 */
	public void setRate(Double rate) {
	 	this.rate = rate;
	}
	/**
	 * @return 性别-数据字典
	 */
	public String getSex() {
	 	return sex;
	}
	/**
	 * @设置 性别-数据字典
	 * @param sex
	 */
	public void setSex(String sex) {
	 	this.sex = sex;
	}
	/**
	 * @return 客户类型-数据字典
	 */
	public String getCusType() {
	 	return cusType;
	}
	/**
	 * @设置 客户类型-数据字典
	 * @param cusType
	 */
	public void setCusType(String cusType) {
	 	this.cusType = cusType;
	}
	/**
	 * @return 操作员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作员名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 操作员名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 部门名称
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
}