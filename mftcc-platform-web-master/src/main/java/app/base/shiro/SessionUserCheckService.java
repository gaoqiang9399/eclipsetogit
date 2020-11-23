package app.base.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class SessionUserCheckService {
    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 踢出异地登陆用户
     *
     * @param session
     * @param nUserName
     */
    public void kickFirstOper(HttpSession session, String nUserName) {
        for (Session s : sessionDAO.getActiveSessions()) {
            PrincipalCollection principals = (SimplePrincipalCollection) s
                    .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            Object primary = null;
            if (!CollectionUtils.isEmpty(principals)) {
                primary = principals.getPrimaryPrincipal();
            }

            if (nUserName.equals((String) primary)) {// 有同名账号异地登陆,踢出用户
                if (s.getId().equals(session.getId())) {// 同浏览器登陆
                    return;
                }
                sessionDAO.delete(s);
                break;
            }
        }
    }
    /**
     * 退出登录使用删除session
     *
     * @param session
     * @param nUserName
     */
    public void deleteSession(HttpSession session, String nUserName) {
        Subject subject = SecurityUtils.getSubject(); 
        if (subject.isAuthenticated()) {  
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存  
        }  
    }
    /**
     * 获取登陆用户集合
     * @return
     */
    public List<String> getActiveUser() {
        List<String> list = new ArrayList<String>();
        for (Session s : sessionDAO.getActiveSessions()) {
            list.add((String) s.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
        }
        return list;
    }
}
