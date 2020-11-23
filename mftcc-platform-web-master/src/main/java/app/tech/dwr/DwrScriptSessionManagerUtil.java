package app.tech.dwr;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.Container;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;

public class DwrScriptSessionManagerUtil {
	
    public void init()throws ServletException {  
           Container container = ServerContextFactory.get().getContainer();  
           ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);  
           ScriptSessionListener listener = new ScriptSessionListener() {  
                  @Override
                  public void sessionCreated(ScriptSessionEvent ev) {
                         HttpSession session = WebContextFactory.get().getSession();  
                         String userId =session.getAttribute("regNo")+"";  
                         ev.getSession().setAttribute("userId", userId);  
                  }  
                  @Override
                  public void sessionDestroyed(ScriptSessionEvent ev) {
                  }  
           };  
           manager.addScriptSessionListener(listener);  
    }  
}
