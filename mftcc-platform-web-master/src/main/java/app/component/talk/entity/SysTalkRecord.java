package app.component.talk.entity;
import app.base.BaseDomain;
/**
* Title: SysTalkRecord.java
* Description:
* @author：@dhcc.com.cn
* @Mon Jan 14 07:02:01 GMT 2013
* @version：1.0
**/

public class SysTalkRecord extends BaseDomain {
	private String  soruceUserId;//发送者ID
	private String  targetUserId;//接受者ID
	private String  soruceUserName;//发送者名称
	private String  targetUserName;//接受者名称
	private String  recordContent;//消息内容
	private String  sendTime;//发送时间
	private String  recordStatus;//消息状态
	private String  recordType;//消息类型

	/**
	 * @return 发送者ID
	 */
	 public String getSoruceUserId() {
	 	return soruceUserId;
	 }
	 /**
	 * @设置 发送者ID
	 * @param soruceUserId
	 */
	 public void setSoruceUserId(String soruceUserId) {
	 	this.soruceUserId = soruceUserId;
	 }
	/**
	 * @return 接受者ID
	 */
	 public String getTargetUserId() {
	 	return targetUserId;
	 }
	 /**
	 * @设置 接受者ID
	 * @param targetUserId
	 */
	 public void setTargetUserId(String targetUserId) {
	 	this.targetUserId = targetUserId;
	 }
	/**
	 * @return 发送者名称
	 */
	 public String getSoruceUserName() {
	 	return soruceUserName;
	 }
	 /**
	 * @设置 发送者名称
	 * @param soruceUserName
	 */
	 public void setSoruceUserName(String soruceUserName) {
	 	this.soruceUserName = soruceUserName;
	 }
	/**
	 * @return 接受者名称
	 */
	 public String getTargetUserName() {
	 	return targetUserName;
	 }
	 /**
	 * @设置 接受者名称
	 * @param targetUserName
	 */
	 public void setTargetUserName(String targetUserName) {
	 	this.targetUserName = targetUserName;
	 }
	/**
	 * @return 消息内容
	 */
	 public String getRecordContent() {
	 	return recordContent;
	 }
	 /**
	 * @设置 消息内容
	 * @param recordContent
	 */
	 public void setRecordContent(String recordContent) {
	 	this.recordContent = recordContent;
	 }
	/**
	 * @return 发送时间
	 */
	 public String getSendTime() {
	 	return sendTime;
	 }
	 /**
	 * @设置 发送时间
	 * @param sendTime
	 */
	 public void setSendTime(String sendTime) {
	 	this.sendTime = sendTime;
	 }
	/**
	 * @return 消息状态
	 */
	 public String getRecordStatus() {
	 	return recordStatus;
	 }
	 /**
	 * @设置 消息状态
	 * @param recordStatus
	 */
	 public void setRecordStatus(String recordStatus) {
	 	this.recordStatus = recordStatus;
	 }
	/**
	 * @return 消息类型
	 */
	 public String getRecordType() {
	 	return recordType;
	 }
	 /**
	 * @设置 消息类型
	 * @param recordType
	 */
	 public void setRecordType(String recordType) {
	 	this.recordType = recordType;
	 }
}
