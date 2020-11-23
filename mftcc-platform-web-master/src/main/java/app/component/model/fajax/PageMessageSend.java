package app.component.model.fajax;

import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;

import app.tech.dwr.DwrScriptSessionManagerUtil;

/**
 * pageOffice向页面推送信息
 * 
 * @author LiWQ
 * 
 */
public class PageMessageSend {
	
	/**
	 * 
	 * 方法描述： 
	 * @param userId 登录操作员
	 * @param unique 业务唯一编号
	 * void
	 * @author lwq
	 * @date 2017-8-27 上午9:50:29
	 */
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
	
	public void pageMessageSendAuto(String user_id) {
		final String userId = user_id;

		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
            @Override
			public boolean match(ScriptSession session){
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

				script.appendCall("pageCallback");

				Collection<ScriptSession> sessions = Browser.getTargetSessions();

				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
}
