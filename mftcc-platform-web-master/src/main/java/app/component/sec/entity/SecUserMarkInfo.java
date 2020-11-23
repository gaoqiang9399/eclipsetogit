package app.component.sec.entity;
import app.base.BaseDomain;
/**
* Title: SecUserMarkInfo.java
* Description:
* @author：@dhcc.com.cn
* @Tue Feb 23 02:25:30 GMT 2016
* @version：1.0
**/
public class SecUserMarkInfo extends BaseDomain {
	private String userId;//
	private String passwordUpdateTime;//
	private Integer visitTimes;//
	private Integer loginErrorTimes;//
	private String lastSignInTime;//
	private String lastSignOutTime;//
	private String currentSignInTime;//
	private String passwordState;//
	private String passwordMessege;//

	/**
	 * @return 
	 */
	public String getUserId() {
	 	return userId;
	}
	/**
	 * @设置 
	 * @param userId
	 */
	public void setUserId(String userId) {
	 	this.userId = userId;
	}
	/**
	 * @return 
	 */
	public String getPasswordUpdateTime() {
	 	return passwordUpdateTime;
	}
	/**
	 * @设置 
	 * @param passwordUpdateTime
	 */
	public void setPasswordUpdateTime(String passwordUpdateTime) {
	 	this.passwordUpdateTime = passwordUpdateTime;
	}
	/**
	 * @return 
	 */
	public Integer getVisitTimes() {
	 	return visitTimes;
	}
	/**
	 * @设置 
	 * @param visitTimes
	 */
	public void setVisitTimes(Integer visitTimes) {
	 	this.visitTimes = visitTimes;
	}
	/**
	 * @return 
	 */
	public Integer getLoginErrorTimes() {
	 	return loginErrorTimes;
	}
	/**
	 * @设置 
	 * @param loginErrorTimes
	 */
	public void setLoginErrorTimes(Integer loginErrorTimes) {
	 	this.loginErrorTimes = loginErrorTimes;
	}
	/**
	 * @return 
	 */
	public String getLastSignInTime() {
	 	return lastSignInTime;
	}
	/**
	 * @设置 
	 * @param lastSignInTime
	 */
	public void setLastSignInTime(String lastSignInTime) {
	 	this.lastSignInTime = lastSignInTime;
	}
	/**
	 * @return 
	 */
	public String getLastSignOutTime() {
	 	return lastSignOutTime;
	}
	/**
	 * @设置 
	 * @param lastSignOutTime
	 */
	public void setLastSignOutTime(String lastSignOutTime) {
	 	this.lastSignOutTime = lastSignOutTime;
	}
	/**
	 * @return 
	 */
	public String getCurrentSignInTime() {
	 	return currentSignInTime;
	}
	/**
	 * @设置 
	 * @param currentSignInTime
	 */
	public void setCurrentSignInTime(String currentSignInTime) {
	 	this.currentSignInTime = currentSignInTime;
	}
	/**
	 * @return 
	 */
	public String getPasswordState() {
	 	return passwordState;
	}
	/**
	 * @设置 
	 * @param passwordState
	 */
	public void setPasswordState(String passwordState) {
	 	this.passwordState = passwordState;
	}
	/**
	 * @return 
	 */
	public String getPasswordMessege() {
	 	return passwordMessege;
	}
	/**
	 * @设置 
	 * @param passwordMessege
	 */
	public void setPasswordMessege(String passwordMessege) {
	 	this.passwordMessege = passwordMessege;
	}
}