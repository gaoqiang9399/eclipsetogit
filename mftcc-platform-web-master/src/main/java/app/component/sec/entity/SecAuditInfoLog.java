package app.component.sec.entity;
import app.base.BaseDomain;
/**
* Title: SecAuditInfoLog.java
* Description:
* @author：@dhcc.com.cn
* @Tue Feb 23 02:46:27 GMT 2016
* @version：1.0
**/
public class SecAuditInfoLog extends BaseDomain {
	private Integer logId;//编号
	private String auditType;//修改类型
	private String userId;//被修改人编号
	private String userName;//被修改人名字
	private String changeUserId;//修改人编号
	private String changeUserName;//修改人名字
	private String auditTime;//修改时间
	private String remark1;//修改记录1
	private String remark2;//修改记录2

	/**
	 * @return 编号
	 */
	public Integer getLogId() {
	 	return logId;
	}
	/**
	 * @设置 编号
	 * @param logId
	 */
	public void setLogId(Integer logId) {
	 	this.logId = logId;
	}
	/**
	 * @return 修改类型
	 */
	public String getAuditType() {
	 	return auditType;
	}
	/**
	 * @设置 修改类型
	 * @param auditType
	 */
	public void setAuditType(String auditType) {
	 	this.auditType = auditType;
	}
	/**
	 * @return 被修改人编号
	 */
	public String getUserId() {
	 	return userId;
	}
	/**
	 * @设置 被修改人编号
	 * @param userId
	 */
	public void setUserId(String userId) {
	 	this.userId = userId;
	}
	/**
	 * @return 被修改人名字
	 */
	public String getUserName() {
	 	return userName;
	}
	/**
	 * @设置 被修改人名字
	 * @param userName
	 */
	public void setUserName(String userName) {
	 	this.userName = userName;
	}
	/**
	 * @return 修改人编号
	 */
	public String getChangeUserId() {
	 	return changeUserId;
	}
	/**
	 * @设置 修改人编号
	 * @param changeUserId
	 */
	public void setChangeUserId(String changeUserId) {
	 	this.changeUserId = changeUserId;
	}
	/**
	 * @return 修改人名字
	 */
	public String getChangeUserName() {
	 	return changeUserName;
	}
	/**
	 * @设置 修改人名字
	 * @param changeUserName
	 */
	public void setChangeUserName(String changeUserName) {
	 	this.changeUserName = changeUserName;
	}
	/**
	 * @return 修改时间
	 */
	public String getAuditTime() {
	 	return auditTime;
	}
	/**
	 * @设置 修改时间
	 * @param auditTime
	 */
	public void setAuditTime(String auditTime) {
	 	this.auditTime = auditTime;
	}
	/**
	 * @return 修改记录1
	 */
	public String getRemark1() {
	 	return remark1;
	}
	/**
	 * @设置 修改记录1
	 * @param remark1
	 */
	public void setRemark1(String remark1) {
	 	this.remark1 = remark1;
	}
	/**
	 * @return 修改记录2
	 */
	public String getRemark2() {
	 	return remark2;
	}
	/**
	 * @设置 修改记录2
	 * @param remark2
	 */
	public void setRemark2(String remark2) {
	 	this.remark2 = remark2;
	}
}