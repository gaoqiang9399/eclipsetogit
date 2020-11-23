package app.component.oa.communication.entity;
import app.base.BaseDomain;
/**
* Title: MfOaInternalCommunication.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Sep 29 10:25:41 CST 2017
* @version：1.0
**/
public class MfOaInternalCommunication extends BaseDomain {
	private String messageId;//消息编号
	private String messageTitle;//消息主题
	private String messageContent;//消息内容
	private String messageSendOpNo;//消息发送人编号
	private String messageSendOpName;//消息发送人名称
	private String messageSendBrNo;//消息发送人部门编号
	private String messageSendBrName;//消息发送人部门名称
	private String messageAcceptOpNo;//消息接受人编号
	private String messageAcceptOpName;//消息接受人名称
	private String messageAcceptBrNo;//消息接受人部门编号
	private String messageAcceptBrName;//消息接受人部门名称
	private String sendTime;//发送时间
	private String sendDate;//发送日期
	private String readSts;//消息读取状态0未读1已读
	private String readDate;//消息读取时间
	private String readTime;//消息读取日期
	private String recoverySts;//消息回复状态0未回复1已回复
	private String recoveryDate;//消息回复日期
	private String recoveryTime;//消息回复时间
	private String messageType;//消息类型1发送2接受
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间
	private String regDate;//登记日期
	private String lstModDate;//最后修改日期
	private String recoveryMessageId;//回复消息编号
	private String messageShow;//消息展示
	private String messageKind;//消息种类

	public String getMessageKind() {
		return messageKind;
	}

	public void setMessageKind(String messageKind) {
		this.messageKind = messageKind;
	}

	public String getMessageShow() {
		return messageShow;
	}

	public void setMessageShow(String messageShow) {
		this.messageShow = messageShow;
	}

	/**
	 * @return 消息编号
	 */
	public String getMessageId() {
	 	return messageId;
	}
	/**
	 * @设置 消息编号
	 * @param messageId
	 */
	public void setMessageId(String messageId) {
	 	this.messageId = messageId;
	}
	/**
	 * @return 消息主题
	 */
	public String getMessageTitle() {
	 	return messageTitle;
	}
	/**
	 * @设置 消息主题
	 * @param messageTitle
	 */
	public void setMessageTitle(String messageTitle) {
	 	this.messageTitle = messageTitle;
	}
	/**
	 * @return 消息内容
	 */
	public String getMessageContent() {
	 	return messageContent;
	}
	/**
	 * @设置 消息内容
	 * @param messageContent
	 */
	public void setMessageContent(String messageContent) {
	 	this.messageContent = messageContent;
	}
	/**
	 * @return 消息发送人编号
	 */
	public String getMessageSendOpNo() {
	 	return messageSendOpNo;
	}
	/**
	 * @设置 消息发送人编号
	 * @param messageSendOpNo
	 */
	public void setMessageSendOpNo(String messageSendOpNo) {
	 	this.messageSendOpNo = messageSendOpNo;
	}
	/**
	 * @return 消息发送人名称
	 */
	public String getMessageSendOpName() {
	 	return messageSendOpName;
	}
	/**
	 * @设置 消息发送人名称
	 * @param messageSendOpName
	 */
	public void setMessageSendOpName(String messageSendOpName) {
	 	this.messageSendOpName = messageSendOpName;
	}
	/**
	 * @return 消息接受人编号
	 */
	public String getMessageAcceptOpNo() {
	 	return messageAcceptOpNo;
	}
	/**
	 * @设置 消息接受人编号
	 * @param messageAcceptOpNo
	 */
	public void setMessageAcceptOpNo(String messageAcceptOpNo) {
	 	this.messageAcceptOpNo = messageAcceptOpNo;
	}
	/**
	 * @return 消息接受人名称
	 */
	public String getMessageAcceptOpName() {
	 	return messageAcceptOpName;
	}
	/**
	 * @设置 消息接受人名称
	 * @param messageAcceptOpName
	 */
	public void setMessageAcceptOpName(String messageAcceptOpName) {
	 	this.messageAcceptOpName = messageAcceptOpName;
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
	 * @return 发送日期
	 */
	public String getSendDate() {
	 	return sendDate;
	}
	/**
	 * @设置 发送日期
	 * @param sendDate
	 */
	public void setSendDate(String sendDate) {
	 	this.sendDate = sendDate;
	}
	/**
	 * @return 消息读取状态0未读1已读
	 */
	public String getReadSts() {
	 	return readSts;
	}
	/**
	 * @设置 消息读取状态0未读1已读
	 * @param readSts
	 */
	public void setReadSts(String readSts) {
	 	this.readSts = readSts;
	}
	/**
	 * @return 消息读取时间
	 */
	public String getReadDate() {
	 	return readDate;
	}
	/**
	 * @设置 消息读取时间
	 * @param readDate
	 */
	public void setReadDate(String readDate) {
	 	this.readDate = readDate;
	}
	/**
	 * @return 消息读取日期
	 */
	public String getReadTime() {
	 	return readTime;
	}
	/**
	 * @设置 消息读取日期
	 * @param readTime
	 */
	public void setReadTime(String readTime) {
	 	this.readTime = readTime;
	}
	/**
	 * @return 消息回复状态0未回复1已回复
	 */
	public String getRecoverySts() {
	 	return recoverySts;
	}
	/**
	 * @设置 消息回复状态0未回复1已回复
	 * @param recoverySts
	 */
	public void setRecoverySts(String recoverySts) {
	 	this.recoverySts = recoverySts;
	}
	/**
	 * @return 消息回复日期
	 */
	public String getRecoveryDate() {
	 	return recoveryDate;
	}
	/**
	 * @设置 消息回复日期
	 * @param recoveryDate
	 */
	public void setRecoveryDate(String recoveryDate) {
	 	this.recoveryDate = recoveryDate;
	}
	/**
	 * @return 消息回复时间
	 */
	public String getRecoveryTime() {
	 	return recoveryTime;
	}
	/**
	 * @设置 消息回复时间
	 * @param recoveryTime
	 */
	public void setRecoveryTime(String recoveryTime) {
	 	this.recoveryTime = recoveryTime;
	}
	/**
	 * @return 消息类型1发送2接受
	 */
	public String getMessageType() {
	 	return messageType;
	}
	/**
	 * @设置 消息类型1发送2接受
	 * @param messageType
	 */
	public void setMessageType(String messageType) {
	 	this.messageType = messageType;
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
	 * @return 最后修改日期
	 */
	public String getLstModDate() {
	 	return lstModDate;
	}
	/**
	 * @设置 最后修改日期
	 * @param lstModDate
	 */
	public void setLstModDate(String lstModDate) {
	 	this.lstModDate = lstModDate;
	}
	public String getRecoveryMessageId() {
		return recoveryMessageId;
	}
	public void setRecoveryMessageId(String recoveryMessageId) {
		this.recoveryMessageId = recoveryMessageId;
	}
	public String getMessageSendBrNo() {
		return messageSendBrNo;
	}
	public void setMessageSendBrNo(String messageSendBrNo) {
		this.messageSendBrNo = messageSendBrNo;
	}
	public String getMessageSendBrName() {
		return messageSendBrName;
	}
	public void setMessageSendBrName(String messageSendBrName) {
		this.messageSendBrName = messageSendBrName;
	}
	public String getMessageAcceptBrNo() {
		return messageAcceptBrNo;
	}
	public void setMessageAcceptBrNo(String messageAcceptBrNo) {
		this.messageAcceptBrNo = messageAcceptBrNo;
	}
	public String getMessageAcceptBrName() {
		return messageAcceptBrName;
	}
	public void setMessageAcceptBrName(String messageAcceptBrName) {
		this.messageAcceptBrName = messageAcceptBrName;
	}
}