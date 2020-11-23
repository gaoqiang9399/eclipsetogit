package app.component.sec.entity;
import app.base.BaseDomain;
/**
* Title: SecUserApptime.java
* Description:
* @author：@dhcc.com.cn
* @Tue Feb 23 02:28:25 GMT 2016
* @version：1.0
**/
public class SecUserApptime extends BaseDomain {
	private String id;//标识
	private String userId;//用户代码
	private String userName;//用户名称
	private String startTime;//开始时间
	private String endTime;//结束时间
	private Double timeConsuming;//运行时间
	private String actionName;//请求动作
	private String actionPara;//动作参数
	private String belongDate;//归属日

	/**
	 * @return 标识
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 标识
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 用户代码
	 */
	public String getUserId() {
	 	return userId;
	}
	/**
	 * @设置 用户代码
	 * @param userId
	 */
	public void setUserId(String userId) {
	 	this.userId = userId;
	}
	/**
	 * @return 用户名称
	 */
	public String getUserName() {
	 	return userName;
	}
	/**
	 * @设置 用户名称
	 * @param userName
	 */
	public void setUserName(String userName) {
	 	this.userName = userName;
	}
	/**
	 * @return 开始时间
	 */
	public String getStartTime() {
	 	return startTime;
	}
	/**
	 * @设置 开始时间
	 * @param startTime
	 */
	public void setStartTime(String startTime) {
	 	this.startTime = startTime;
	}
	/**
	 * @return 结束时间
	 */
	public String getEndTime() {
	 	return endTime;
	}
	/**
	 * @设置 结束时间
	 * @param endTime
	 */
	public void setEndTime(String endTime) {
	 	this.endTime = endTime;
	}
	/**
	 * @return 运行时间
	 */
	public Double getTimeConsuming() {
	 	return timeConsuming;
	}
	/**
	 * @设置 运行时间
	 * @param timeConsuming
	 */
	public void setTimeConsuming(Double timeConsuming) {
	 	this.timeConsuming = timeConsuming;
	}
	/**
	 * @return 请求动作
	 */
	public String getActionName() {
	 	return actionName;
	}
	/**
	 * @设置 请求动作
	 * @param actionName
	 */
	public void setActionName(String actionName) {
	 	this.actionName = actionName;
	}
	/**
	 * @return 动作参数
	 */
	public String getActionPara() {
	 	return actionPara;
	}
	/**
	 * @设置 动作参数
	 * @param actionPara
	 */
	public void setActionPara(String actionPara) {
	 	this.actionPara = actionPara;
	}
	/**
	 * @return 归属日
	 */
	public String getBelongDate() {
	 	return belongDate;
	}
	/**
	 * @设置 归属日
	 * @param belongDate
	 */
	public void setBelongDate(String belongDate) {
	 	this.belongDate = belongDate;
	}
}