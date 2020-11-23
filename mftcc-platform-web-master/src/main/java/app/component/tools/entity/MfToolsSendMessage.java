package app.component.tools.entity;
import app.base.BaseDomain;
/**
* Title: MfToolsSendMessage.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Oct 08 14:57:44 CST 2016
* @version：1.0
**/
public class MfToolsSendMessage extends BaseDomain {
	private String id;//
	private String sendMsg;//短信内容
	private String cusTel;//客户手机号码
	private String sendSts;//0 发送失败  1 发送成功
	private String sendResultMsg;//发送结果的返回消息。
	private String sendTime;//短信发送时间
	private String opNo;//操作员登录号
	private String opName;//操作员名称
	private String brNo;//机构号
	private String brName;//机构名称
	private String sendMsgType;
	private String msgTel;
	private String receiverName;
	private String groupNo;//群发标志

	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getMsgTel() {
		return msgTel;
	}
	public void setMsgTel(String msgTel) {
		this.msgTel = msgTel;
	}
	public String getSendMsgType() {
		return sendMsgType;
	}
	public void setSendMsgType(String sendMsgType) {
		this.sendMsgType = sendMsgType;
	}
	/**
	 * @return 
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 短信内容
	 */
	public String getSendMsg() {
	 	return sendMsg;
	}
	/**
	 * @设置 短信内容
	 * @param sendMsg
	 */
	public void setSendMsg(String sendMsg) {
	 	this.sendMsg = sendMsg;
	}
	/**
	 * @return 客户手机号码
	 */
	public String getCusTel() {
	 	return cusTel;
	}
	/**
	 * @设置 客户手机号码
	 * @param cusTel
	 */
	public void setCusTel(String cusTel) {
	 	this.cusTel = cusTel;
	}
	/**
	 * @return 0 发送失败  1 发送成功
	 */
	public String getSendSts() {
	 	return sendSts;
	}
	/**
	 * @设置 0 发送失败  1 发送成功
	 * @param sendSts
	 */
	public void setSendSts(String sendSts) {
	 	this.sendSts = sendSts;
	}
	/**
	 * @return 短信发送时间
	 */
	public String getSendTime() {
	 	return sendTime;
	}
	/**
	 * @设置 短信发送时间
	 * @param sendTime
	 */
	public void setSendTime(String sendTime) {
	 	this.sendTime = sendTime;
	}
	/**
	 * @return 操作员登录号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员登录号
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
	 * @return 机构号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 机构号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 机构名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 机构名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	public String getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	public String getSendResultMsg() {
		return sendResultMsg;
	}
	public void setSendResultMsg(String sendResultMsg) {
		this.sendResultMsg = sendResultMsg;
	}
	
	
}