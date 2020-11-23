package app.tech.dwr;

import app.component.message.entity.MfInnerMessage;
import config.YmlConfig;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import org.directwebremoting.Browser;
import org.directwebremoting.Container;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.ScriptSessionManager;

import app.component.common.BizPubParm;
import app.component.sys.entity.SysOrg;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysOrgFeign;
import app.component.sys.feign.SysUserFeign;
import app.component.talk.entity.SysTalkRecord;
import app.component.talk.feign.SysTalkRecordFeign;
import app.util.ServiceLocator;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;
	
public class MessagePush {
	private SysTalkRecordFeign sysTalkRecordFeign;
	private SysUserFeign sysUserFeign;
	private SysOrgFeign sysOrgFeign;
	
	public void onPageLoad(String userId) {
		ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
		scriptSession.setAttribute(userId, userId);
		DwrScriptSessionManagerUtil dwrScriptSessionManagerUtil = new DwrScriptSessionManagerUtil();
		try {
			dwrScriptSessionManagerUtil.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessageAuto(String userid, String messageType, int massageCount, String message, SysTaskInfo sysTaskInfo, String username) {

		final String userId = userid;
		final String userName = username;
		final String autoMessage = message;
		final String msgType = messageType;
		final int msgCount = massageCount;
		final SysTaskInfo taskInfo = sysTaskInfo;
		pushDwr(userId, msgType, msgCount, autoMessage, JSONObject.fromObject(taskInfo), userName, userName, autoMessage, msgCount);
	}

	private void pushDwr(String userId, String msgType, int msgCount2, String autoMessage2, JSONObject jsonObject, String userName2, String userName, String autoMessage, int msgCount) {
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			@Override
			public boolean match(ScriptSession session) {
				if (session.getAttribute("userId") == null) {
                    return false;
                } else {
                    return (session.getAttribute("userId")).equals(userId);
                }
			}
		}, new Runnable() {

			private ScriptBuffer script = new ScriptBuffer();

			@Override
			public void run() {

				script.appendCall("showMessage", msgType, msgCount2, autoMessage2, jsonObject.toString(), userName2);

				Collection<ScriptSession> sessions = Browser.getTargetSessions();

				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}

	public void sendWebMessage(String userid, String messageType, int massageCount, String message, MfInnerMessage mfInnerMessage, String username) {

		final String userId = userid;
		final String userName = username;
		final String autoMessage = message;
		final String msgType = messageType;
		final int msgCount = massageCount;
		final MfInnerMessage mfInnerMessage1 = mfInnerMessage;
		pushDwr(userId, msgType, msgCount, autoMessage, JSONObject.fromObject(mfInnerMessage1), userName, userName, autoMessage, msgCount);
	}

	
	public void closePop() {
		
		YmlConfig ymlConfig = ServiceLocator.getBean(YmlConfig.class);
		String webPath = ymlConfig.getWebservice().get("webPath");
		if(webPath.startsWith("/")){
		}else{
			webPath="/"+webPath;
		}

		if(webPath.endsWith("/")){
			webPath=webPath.substring(0,webPath.length()-1);
		}
		
		Browser.withPage(webPath + "/sysLogin/userLogin", new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();
			@Override
			public void run() {
				script.appendCall("closePop");
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}

	/**
	 * 即时通讯页面初始化
	 * 
	 * @param userId
	 * @param userName
	 */
	
	public void onPageLoad(String userId, String userName) {

		// ScriptSession scriptSession = WebContextFactory.get().getScriptSession();

		// scriptSession.setAttribute(userId, userId);

		DwrScriptSessionManagerUtil dwrScriptSessionManagerUtil = new DwrScriptSessionManagerUtil();
		try {
			dwrScriptSessionManagerUtil.init();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 聊天消息推送
	 * 
	 * @param targetUser
	 * @param sourceUser
	 * @param message
	 * @return
	 * @throws Exception
	 */
	
	public String sendMessageAuto(String targetUser, String sourceUser, String message) throws Exception {

		if (targetUser == null || targetUser.indexOf(":") < 0) {
			return "发送失败！请刷新新页面重试";
		}
		int index = targetUser.indexOf(":");
		final String targetUserId = targetUser.substring(0, index);
		final String targetUserName = targetUser.substring(index + 1);
		if (sourceUser == null || sourceUser.indexOf(":") < 0) {
			return "发送失败！请刷新新页面重试";
		}
		String sendDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		int sourceIndex = sourceUser.indexOf(":");
		final String sourceUserId = sourceUser.substring(0, sourceIndex);
		final String sourceUserName = sourceUser.substring(sourceIndex + 1);
		final String retDate = sendDate;
		final String autoMessage = message;

		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			@Override
			public boolean match(ScriptSession session) {
				if (session.getAttribute("userId") == null) {
					return false;
				} else {
					return session.getAttribute("userId").equals(targetUserId);
				}
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();

			@Override
			public void run() {
				script.appendCall("showMessageForTalk", sourceUserId, sourceUserName, targetUserId, targetUserName, retDate, autoMessage);
				script.appendCall("showMessage", "7", "1", autoMessage, sourceUserName + "(" + sourceUserId + ")");
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}

		});
		insertMsg(targetUserId, targetUserName, sourceUserId, sourceUserName, sendDate, autoMessage);
		updateStatus(targetUserId, sourceUserId, "1");

		return sendDate;
	}

	
	public List<String> getUnReadSource(String targetUserId) throws Exception {
		SysTalkRecord sysTalkRecord = new SysTalkRecord();
		sysTalkRecord.setTargetUserId(targetUserId);
		sysTalkRecord.setRecordStatus("0");
		sysTalkRecordFeign = ServiceLocator.getBean(SysTalkRecordFeign.class);
		List<SysTalkRecord> list = sysTalkRecordFeign.getUnReadSource(sysTalkRecord);
		if (list != null && list.size() > 0) {
			List<String> newList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				SysTalkRecord st = list.get(i);
				newList.add(st.getSoruceUserId() + ":" + st.getSoruceUserName());
			}
			return newList;
		}
		return null;
	}

	/**
	 * 获取未读信息
	 * 
	 * @param targetUserId
	 * @return
	 */
	
	public List<SysTalkRecord> getUnReadRecords(String targetUserId) {
		try {
			SysTalkRecord sysTalkRecord = new SysTalkRecord();
			sysTalkRecord.setTargetUserId(targetUserId);
			sysTalkRecord.setRecordStatus("0");
			sysTalkRecordFeign = ServiceLocator.getBean(SysTalkRecordFeign.class);
			List<SysTalkRecord> list = sysTalkRecordFeign.getUnReadRecords(sysTalkRecord);

			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ArrayList<SysTalkRecord>();
		}
	}

	/**
	 * 获取历史记录
	 * 
	 * @param sourceUserId
	 * @param targetUserId
	 * @param sendTime
	 * @return
	 */
	
	public List<SysTalkRecord> getHisRecords(String sourceUserId, String targetUserId, String sendTime) {
		List<SysTalkRecord> list = new ArrayList<SysTalkRecord>();

		try {
			SysTalkRecord sysTalkRecord = new SysTalkRecord();
			sysTalkRecord.setSoruceUserId(sourceUserId);
			sysTalkRecord.setTargetUserId(targetUserId);
			sysTalkRecord.setSendTime(sendTime);
			Ipage ipage = new Ipage();
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("sysTalkRecord", sysTalkRecord);
			ipage.setParams(paramsMap);
			sysTalkRecordFeign = ServiceLocator.getBean(SysTalkRecordFeign.class);
			list = (List<SysTalkRecord>) sysTalkRecordFeign.findMyMsg(ipage).getResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return list;
	}

	/**
	 * 即时通讯-获取在线用户
	 * 
	 * @return
	 */
	
	public List<String> findOnlineUser() {
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		Container container = ServerContextFactory.get().getContainer();
		ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
		Collection<ScriptSession> sessions = manager.getAllScriptSessions();
		for (ScriptSession scriptSession : sessions) {
			String tlrno = (String) scriptSession.getAttribute("regNo");
			String displayname = (String) scriptSession.getAttribute("regName");
			if (tlrno != null && !"".equals(tlrno)) {
				if (set.add(tlrno)) {
					list.add(tlrno + ":" + displayname);
				}
			}
		}

		return list;
	}

	/**
	 * 即时通讯-发送消息
	 * 
	 * @param targetUserId
	 * @param targetUserName
	 * @param sourceUserId
	 * @param sourceUserName
	 * @param time
	 * @param autoMessage
	 * @throws Exception
	 */
	
	public void insertMsg(String targetUserId, String targetUserName, String sourceUserId, String sourceUserName, String time, String autoMessage)
			throws Exception {
		SysTalkRecord sysTalkRecord = new SysTalkRecord();
		sysTalkRecord.setTargetUserId(targetUserId);
		sysTalkRecord.setTargetUserName(targetUserName);
		sysTalkRecord.setSoruceUserId(sourceUserId);
		sysTalkRecord.setSoruceUserName(sourceUserName);
		sysTalkRecord.setSendTime(time);
		sysTalkRecord.setRecordContent(autoMessage);
		String recordStatus = "0";
		sysTalkRecord.setRecordStatus(recordStatus);
		sysTalkRecordFeign = ServiceLocator.getBean(SysTalkRecordFeign.class);
		sysTalkRecordFeign.insert(sysTalkRecord);
	}

	/**
	 * 更新消息状态
	 * 
	 * @param sourceUserId
	 * @param targetUserId
	 * @param status
	 * @throws Exception
	 */
	
	public void updateStatus(String sourceUserId, String targetUserId, String status) throws Exception {
		SysTalkRecord sysTalkRecord = new SysTalkRecord();
		sysTalkRecord.setTargetUserId(targetUserId);
		sysTalkRecord.setSoruceUserId(sourceUserId);
		sysTalkRecord.setRecordStatus(status);
		sysTalkRecordFeign = ServiceLocator.getBean(SysTalkRecordFeign.class);
		sysTalkRecordFeign.updateStatus(sysTalkRecord);
	}

	/**
	 * 即时通讯-获取所有用户
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public List<String> getAllUser() throws Exception {
		try {
			sysUserFeign = ServiceLocator.getBean(SysUserFeign.class);
			SysUser sysUser = new SysUser();
			sysUser.setOpNoType(BizPubParm.OP_NO_TYPE1);
			List<SysUser> list = sysUserFeign.getAllUserList(sysUser);
			Object[] arrayList = list.toArray();
			Arrays.sort(arrayList, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return ((SysUser) o1).getBrNo().compareTo(((SysUser) o2).getBrNo());
				}
			});
			sysOrgFeign = ServiceLocator.getBean(SysOrgFeign.class);
			SysOrg sysOrg1 = new SysOrg();
			sysOrg1.setOpNoType(BizPubParm.OP_NO_TYPE1);
			List<SysOrg> listOrg = sysOrgFeign.getAllOrg(sysOrg1);
			Map<String, String> mapOrg = new HashMap<String, String>();
			for (SysOrg sysOrg : listOrg) {
				mapOrg.put(sysOrg.getBrNo(), sysOrg.getBrName());
			}
			List<String> listUser = new ArrayList<String>();
			ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
			String regNo = scriptSession.getAttribute("userId") == null ? "" : scriptSession.getAttribute("userId").toString();
			for (int i = 0; i < arrayList.length; i++) {
				SysUser user = (SysUser) arrayList[i];
				if (!user.getOpNo().equals(regNo)) {
					String brName = mapOrg.get(user.getBrNo());
					listUser.add("{'opNo':'" + user.getOpNo() + "','opName':'" + user.getOpName() + "','brNo':'" + user.getBrNo() + "','brName':'"
							+ (brName == null ? "" : brName) + "'}");
				}
			}
			return listUser;
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ArrayList<String>();
		}
	}
}
