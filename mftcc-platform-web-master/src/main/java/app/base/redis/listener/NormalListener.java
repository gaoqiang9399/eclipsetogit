package app.base.redis.listener;

import app.base.redis.RedisQueueListener;
import app.component.message.entity.MfInnerMessage;
import app.component.message.eum.MessagePushMethod;
import app.component.sys.entity.SysTaskInfo;
import app.tech.dwr.MessagePush;
import app.util.JsonStrHandling;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NormalListener<T> implements RedisQueueListener<T> {


	private Logger logger = LoggerFactory.getLogger(NormalListener.class);

	private MessagePush mp = new MessagePush();
	/**
	 * 推送消息
	 */
	private static final String SEND_MESSAGE_AUTO = "sendMessageAuto";

	/**
	 * 推送信息 通知
	 */
	private static final String SEND_MESSAGE_AUTO_NOTICE = "sendMessageAutoNotice";

	/**
	 * 关闭消息
	 */
	private static final String CLOSE_POP = "closePop";


	@Override
	public void onMessage(T value) {
		logger.info("Listener: " + value);
		if (value != null && !"".equals(value)) {
			String msg = (String) value;
			if(msg.startsWith("\"")&&msg.endsWith("\"")){
				msg = msg.substring(1,msg.length()-1);
			}
			JSONObject jsonObj = JSONObject.fromObject(msg);
			webAppHandler(jsonObj);
		}
	}

	/**
	 * 业务处理
	 *
	 */
	public void webAppHandler(JSONObject jsonObj) {
		try {
			String methodName = jsonObj.getString("methodName");
			if (methodName != null && !"".equals(methodName)) {
				if(SEND_MESSAGE_AUTO.equals(methodName)) {
					String userId = (String)jsonObj.get("userid");
					String messageType = (String)jsonObj.get("messageType");
					Integer massageCount = (Integer)jsonObj.get("massageCount");
					String message = (String)jsonObj.get("message");
					SysTaskInfo sysTaskInfo = (SysTaskInfo)JsonStrHandling.handlingStrToBean(jsonObj.get("sysTaskInfo"), SysTaskInfo.class);
					String username = (String)jsonObj.get("username");
					logger.debug("接受到(SEND_MESSAGE_AUTO)消息  并推送消息");
					mp.sendMessageAuto(userId, messageType, massageCount, message, sysTaskInfo, username);
				}else if(SEND_MESSAGE_AUTO_NOTICE.equals(methodName)) {
					String targetUser = (String)jsonObj.get("targetUser");
					String sourceUser = (String)jsonObj.get("sourceUser");
					String message = (String)jsonObj.get("message");
					logger.debug("接受到(SEND_MESSAGE_AUTO_NOTICE)消息  并推送消息");
					mp.sendMessageAuto(targetUser, sourceUser, message);
				}else if("insertMessage".equals(methodName)){
					String userId = (String)jsonObj.get("nextUser");
					String messageType = (String)jsonObj.get("pasMinNo");
					Integer massageCount = 1;
					String message = (String)jsonObj.get("pasContent");
					SysTaskInfo sysTaskInfo = JsonStrHandling.handlingStrToBean(jsonObj.get("sysTaskInfo"), SysTaskInfo.class);
					String username = (String)jsonObj.get("regName");
					logger.debug("接受到(SEND_MESSAGE_AUTO)消息  并推送消息");
					mp.sendMessageAuto(userId, messageType, massageCount, message, sysTaskInfo, username);
				}else if(CLOSE_POP.equals(methodName)){
					mp.closePop();
				}else if(MessagePushMethod.SEND_WEB_PAGE_MESSAGE.equals(methodName)){
					String userId = (String)jsonObj.get("receiverNo");
					String messageType = (String)jsonObj.get("scdKind");
					Integer massageCount = 1;
					String message =methodName;
					MfInnerMessage mfInnerMessage = JsonStrHandling.handlingStrToBean(jsonObj.get("mfInnerMessage"), MfInnerMessage.class);
					String username = (String)jsonObj.get("username");
					logger.debug("接受到SEND_WEB_PAGE_MESSAGE消息  并推送消息");
					mp.sendWebMessage(userId, messageType, massageCount, message, mfInnerMessage, username);

				}else {
				}
			}
		}catch (Exception e) {
			logger.error("消息发送格式错误无法解析！！！",e);
		}
	}

}
