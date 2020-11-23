package app.component.pact.entity;
import app.base.BaseDomain;
/**
* Title: MfRecoverPactAmtHistory.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sun Jul 23 15:21:36 CST 2017
* @version：1.0
**/
public class MfRecoverPactAmtHistory extends BaseDomain {
	private String recoverHisId;//恢复历史编号
	private String pactId;//合同号
	private String appName;//项目名
	private String fincId;//借据编号
	private Double recoverAmt;//恢复额度
	private String recoverType;//恢复类型1还款恢复
	private String regDate;//登记日期
	private String lstModDate;//上次修改日期
	private String regTime;//登记时间
	private String lstModTime;//最近修改时间

	/**
	 * @return 恢复历史编号
	 */
	public String getRecoverHisId() {
	 	return recoverHisId;
	}
	/**
	 * @设置 恢复历史编号
	 * @param recoverHisId
	 */
	public void setRecoverHisId(String recoverHisId) {
	 	this.recoverHisId = recoverHisId;
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
	 * @return 项目名
	 */
	public String getAppName() {
	 	return appName;
	}
	/**
	 * @设置 项目名
	 * @param appName
	 */
	public void setAppName(String appName) {
	 	this.appName = appName;
	}
	/**
	 * @return 借据编号
	 */
	public String getFincId() {
	 	return fincId;
	}
	/**
	 * @设置 借据编号
	 * @param fincId
	 */
	public void setFincId(String fincId) {
	 	this.fincId = fincId;
	}
	/**
	 * @return 恢复额度
	 */
	public Double getRecoverAmt() {
	 	return recoverAmt;
	}
	/**
	 * @设置 恢复额度
	 * @param recoverAmt
	 */
	public void setRecoverAmt(Double recoverAmt) {
	 	this.recoverAmt = recoverAmt;
	}
	/**
	 * @return 恢复类型1还款恢复
	 */
	public String getRecoverType() {
	 	return recoverType;
	}
	/**
	 * @设置 恢复类型1还款恢复
	 * @param recoverType
	 */
	public void setRecoverType(String recoverType) {
	 	this.recoverType = recoverType;
	}
	/**
	 * @return 登记日期
	 */
	public String getRegDate() {
	 	return regDate;
	}
	/**
	 * @设置 登记日期
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
	 	this.regDate = regDate;
	}
	/**
	 * @return 上次修改日期
	 */
	public String getLstModDate() {
	 	return lstModDate;
	}
	/**
	 * @设置 上次修改日期
	 * @param lstModDate
	 */
	public void setLstModDate(String lstModDate) {
	 	this.lstModDate = lstModDate;
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
}