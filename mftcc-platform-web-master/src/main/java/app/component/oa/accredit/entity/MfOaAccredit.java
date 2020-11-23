package app.component.oa.accredit.entity;
import app.base.BaseDomain;
/**
* Title: MfOaAccredit.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Apr 25 11:16:07 CST 2017
* @version：1.0
**/
public class MfOaAccredit extends BaseDomain {
	private String accreditId;//托管id
	private String accreditFunc;//托管功能
	private String authorizerNo;//委托人编号
	private String authorizerName;//委托人名称
	private String agentNo;//受托人编号
	private String agentName;//受托人名称
	private String accreditSts;//托管状态 0托管失效 1正在托管2待托管
	private String accreditStart;//开始时间
	private String accreditEnd;//结束时间
	private Double accreditSum;//托管时长
	private String authorizerBrNo;//委托人部门编号
	private String authorizerBrName;//委托人部门名称
	private String agentBrNo;//受托人部门编号
	private String agentBrName;//受托人部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间

	/**
	 * @return 托管id
	 */
	public String getAccreditId() {
	 	return accreditId;
	}
	/**
	 * @设置 托管id
	 * @param accreditId
	 */
	public void setAccreditId(String accreditId) {
	 	this.accreditId = accreditId;
	}
	/**
	 * @return 托管功能
	 */
	public String getAccreditFunc() {
	 	return accreditFunc;
	}
	/**
	 * @设置 托管功能
	 * @param accreditFunc
	 */
	public void setAccreditFunc(String accreditFunc) {
	 	this.accreditFunc = accreditFunc;
	}
	/**
	 * @return 委托人编号
	 */
	public String getAuthorizerNo() {
	 	return authorizerNo;
	}
	/**
	 * @设置 委托人编号
	 * @param authorizerNo
	 */
	public void setAuthorizerNo(String authorizerNo) {
	 	this.authorizerNo = authorizerNo;
	}
	/**
	 * @return 委托人名称
	 */
	public String getAuthorizerName() {
	 	return authorizerName;
	}
	/**
	 * @设置 委托人名称
	 * @param authorizerName
	 */
	public void setAuthorizerName(String authorizerName) {
	 	this.authorizerName = authorizerName;
	}
	/**
	 * @return 受托人编号
	 */
	public String getAgentNo() {
	 	return agentNo;
	}
	/**
	 * @设置 受托人编号
	 * @param agentNo
	 */
	public void setAgentNo(String agentNo) {
	 	this.agentNo = agentNo;
	}
	/**
	 * @return 受托人名称
	 */
	public String getAgentName() {
	 	return agentName;
	}
	/**
	 * @设置 受托人名称
	 * @param agentName
	 */
	public void setAgentName(String agentName) {
	 	this.agentName = agentName;
	}
	/**
	 * @return 托管状态 0托管失效 1正在托管
	 */
	public String getAccreditSts() {
	 	return accreditSts;
	}
	/**
	 * @设置 托管状态 0托管失效 1正在托管
	 * @param accreditSts
	 */
	public void setAccreditSts(String accreditSts) {
	 	this.accreditSts = accreditSts;
	}
	/**
	 * @return 开始时间
	 */
	public String getAccreditStart() {
	 	return accreditStart;
	}
	/**
	 * @设置 开始时间
	 * @param accreditStart
	 */
	public void setAccreditStart(String accreditStart) {
	 	this.accreditStart = accreditStart;
	}
	/**
	 * @return 结束时间
	 */
	public String getAccreditEnd() {
	 	return accreditEnd;
	}
	/**
	 * @设置 结束时间
	 * @param accreditEnd
	 */
	public void setAccreditEnd(String accreditEnd) {
	 	this.accreditEnd = accreditEnd;
	}
	/**
	 * @return 托管时长
	 */
	public Double getAccreditSum() {
	 	return accreditSum;
	}
	/**
	 * @设置 托管时长
	 * @param accreditSum
	 */
	public void setAccreditSum(Double accreditSum) {
	 	this.accreditSum = accreditSum;
	}
	/**
	 * @return 委托人部门编号
	 */
	public String getAuthorizerBrNo() {
	 	return authorizerBrNo;
	}
	/**
	 * @设置 委托人部门编号
	 * @param authorizerBrNo
	 */
	public void setAuthorizerBrNo(String authorizerBrNo) {
	 	this.authorizerBrNo = authorizerBrNo;
	}
	/**
	 * @return 委托人部门名称
	 */
	public String getAuthorizerBrName() {
	 	return authorizerBrName;
	}
	/**
	 * @设置 委托人部门名称
	 * @param authorizerBrName
	 */
	public void setAuthorizerBrName(String authorizerBrName) {
	 	this.authorizerBrName = authorizerBrName;
	}
	/**
	 * @return 受托人部门编号
	 */
	public String getAgentBrNo() {
	 	return agentBrNo;
	}
	/**
	 * @设置 受托人部门编号
	 * @param agentBrNo
	 */
	public void setAgentBrNo(String agentBrNo) {
	 	this.agentBrNo = agentBrNo;
	}
	/**
	 * @return 受托人部门名称
	 */
	public String getAgentBrName() {
	 	return agentBrName;
	}
	/**
	 * @设置 受托人部门名称
	 * @param agentBrName
	 */
	public void setAgentBrName(String agentBrName) {
	 	this.agentBrName = agentBrName;
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
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
}