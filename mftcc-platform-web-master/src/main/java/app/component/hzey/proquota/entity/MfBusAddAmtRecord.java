package app.component.hzey.proquota.entity;
import app.base.BaseDomain;
/**
* Title: MfBusAddAmtRecord.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Nov 30 15:14:39 CST 2017
* @version：1.0
**/
public class MfBusAddAmtRecord extends BaseDomain {
	private String id;//主键
	private String cusNo;//赠送客户号
	private String cusName;//客户名称
	private String pactNo;//合同展示号
	private String appId;//申请号
	private String appName;//项目名称
	private Double addAmt;//提升金额（元）
	private String createTime;//添加时间
	private String lstModTime;//最近修改时间
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称

	/**
	 * @return 主键
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 主键
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 赠送客户号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 赠送客户号
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 客户名称
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 客户名称
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 合同展示号
	 */
	public String getPactNo() {
	 	return pactNo;
	}
	/**
	 * @设置 合同展示号
	 * @param pactNo
	 */
	public void setPactNo(String pactNo) {
	 	this.pactNo = pactNo;
	}
	/**
	 * @return 申请号
	 */
	public String getAppId() {
	 	return appId;
	}
	/**
	 * @设置 申请号
	 * @param appId
	 */
	public void setAppId(String appId) {
	 	this.appId = appId;
	}
	/**
	 * @return 项目名称
	 */
	public String getAppName() {
	 	return appName;
	}
	/**
	 * @设置 项目名称
	 * @param appName
	 */
	public void setAppName(String appName) {
	 	this.appName = appName;
	}
	/**
	 * @return 提升金额（元）
	 */
	public Double getAddAmt() {
	 	return addAmt;
	}
	/**
	 * @设置 提升金额（元）
	 * @param addAmt
	 */
	public void setAddAmt(Double addAmt) {
	 	this.addAmt = addAmt;
	}
	/**
	 * @return 添加时间
	 */
	public String getCreateTime() {
	 	return createTime;
	}
	/**
	 * @设置 添加时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
	 	this.createTime = createTime;
	}
	/**
	 * @return 最近修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最近修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 登记人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 登记人姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
}