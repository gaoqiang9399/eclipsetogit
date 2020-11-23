package app.component.forewarning.entity;
import java.util.ArrayList;
import java.util.List;

import app.base.BaseDomain;
/**
* Title: MfSysMsgInfo.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Jul 05 09:14:26 CST 2017
* @version：1.0
**/
public class MfSysMsgInfo extends BaseDomain {
	private String id;//主键
	private String title;//消息标题
	private String content;//消息内容
	private String sendType;//消息通知类型1 短信 2站内消息,3邮件
	private String msgType;//消息类型
	private String receiverId;//接收者编号
	private String receiverType;//接收者类型1 操作员 2 客户 
	private String receiverNumber;//接收者号码短信此字段为手机号邮件此字段为邮箱地址
	private String sendDate;//发送日期
	private String sendTime;//发送时间
	private String createDate;//创建日期
	private String createTime;//创建时间
	private String regName;//消息发起人只记录名字不记录号
	private String readFlag;//是否已读0 未读, 1 已读
	private String sendSts;//发送状态0 未发 ,1 已发
	private String remark;//备注

	//作为controller接收param添加参数
	private String emailAddress;//邮件地址
	private String phoneNumber;//手机号
	
	/**
	 * 自定义查询
	 */
	private List<String> customQuery;
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
	 * @return 消息标题
	 */
	public String getTitle() {
	 	return title;
	}
	/**
	 * @设置 消息标题
	 * @param title
	 */
	public void setTitle(String title) {
	 	this.title = title;
	}
	/**
	 * @return 消息内容
	 */
	public String getContent() {
	 	return content;
	}
	/**
	 * @设置 消息内容
	 * @param content
	 */
	public void setContent(String content) {
	 	this.content = content;
	}
	/**
	 * @return 消息通知类型1 短信 2站内消息,3邮件
	 */
	public String getSendType() {
	 	return sendType;
	}
	/**
	 * @设置 消息通知类型1 短信 2站内消息,3邮件
	 * @param sendType
	 */
	public void setSendType(String sendType) {
	 	this.sendType = sendType;
	}
	/**
	 * @return 消息类型
	 */
	public String getMsgType() {
	 	return msgType;
	}
	/**
	 * @设置 消息类型
	 * @param msgType
	 */
	public void setMsgType(String msgType) {
	 	this.msgType = msgType;
	}
	/**
	 * @return 接收者编号
	 */
	public String getReceiverId() {
	 	return receiverId;
	}
	/**
	 * @设置 接收者编号
	 * @param receiverId
	 */
	public void setReceiverId(String receiverId) {
	 	this.receiverId = receiverId;
	}
	/**
	 * @return 接收者类型1 操作员 2 客户 
	 */
	public String getReceiverType() {
	 	return receiverType;
	}
	/**
	 * @设置 接收者类型1 操作员 2 客户 
	 * @param receiverType
	 */
	public void setReceiverType(String receiverType) {
	 	this.receiverType = receiverType;
	}
	/**
	 * @return 接收者号码短信此字段为手机号邮件此字段为邮箱地址
	 */
	public String getReceiverNumber() {
	 	return receiverNumber;
	}
	/**
	 * @设置 接收者号码短信此字段为手机号邮件此字段为邮箱地址
	 * @param receiverNumber
	 */
	public void setReceiverNumber(String receiverNumber) {
	 	this.receiverNumber = receiverNumber;
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
	 * @return 创建日期
	 */
	public String getCreateDate() {
	 	return createDate;
	}
	/**
	 * @设置 创建日期
	 * @param createDate
	 */
	public void setCreateDate(String createDate) {
	 	this.createDate = createDate;
	}
	/**
	 * @return 创建时间
	 */
	public String getCreateTime() {
	 	return createTime;
	}
	/**
	 * @设置 创建时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
	 	this.createTime = createTime;
	}
	/**
	 * @return 消息发起人只记录名字不记录号
	 */
	public String getRegName() {
	 	return regName;
	}
	/**
	 * @设置 消息发起人只记录名字不记录号
	 * @param regName
	 */
	public void setRegName(String regName) {
	 	this.regName = regName;
	}
	/**
	 * @return 是否已读0 未读, 1 已读
	 */
	public String getReadFlag() {
	 	return readFlag;
	}
	/**
	 * @设置 是否已读0 未读, 1 已读
	 * @param readFlag
	 */
	public void setReadFlag(String readFlag) {
	 	this.readFlag = readFlag;
	}
	/**
	 * @return 发送状态0 未发 ,1 已发
	 */
	public String getSendSts() {
	 	return sendSts;
	}
	/**
	 * @设置 发送状态0 未发 ,1 已发
	 * @param sendSts
	 */
	public void setSendSts(String sendSts) {
	 	this.sendSts = sendSts;
	}
	/**
	 * @return 备注
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	@Override
	public List<String> getCustomQuery() {
		return customQuery;
	}
	@Override
	public void setCustomQuery(Object customQuery) throws Exception{
		List<String> customQueryList = new ArrayList<String>();
			String value = (String) customQuery;
			for(String str:value.split(" ")){
				if(!"".equals(str)){
					customQueryList.add(str);
				}
			}
		this.customQuery = customQueryList;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}