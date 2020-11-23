package app.component.sys.entity;
import app.base.BaseDomain;
/**
* Title: SysMsgConfig.java
* Description:
* @author：@dhcc.com.cn
* @Sat May 07 02:29:40 GMT 2016
* @version：1.0
**/
public class SysMsgConfig extends BaseDomain {
	private String msgNo;//模版编号
	private String msgContent;//消息内容
	private String msgTemp;//预留字段
	private String msgType;//消息类型

	/**
	 * @return 模版编号
	 */
	public String getMsgNo() {
	 	return msgNo;
	}
	/**
	 * @设置 模版编号
	 * @param msgNo
	 */
	public void setMsgNo(String msgNo) {
	 	this.msgNo = msgNo;
	}
	/**
	 * @return 消息内容
	 */
	public String getMsgContent() {
	 	return msgContent;
	}
	/**
	 * @设置 消息内容
	 * @param msgContent
	 */
	public void setMsgContent(String msgContent) {
	 	this.msgContent = msgContent;
	}
	/**
	 * @return 预留字段
	 */
	public String getMsgTemp() {
	 	return msgTemp;
	}
	/**
	 * @设置 预留字段
	 * @param msgTemp
	 */
	public void setMsgTemp(String msgTemp) {
	 	this.msgTemp = msgTemp;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
}