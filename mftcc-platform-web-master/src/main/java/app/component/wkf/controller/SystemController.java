package app.component.wkf.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dhcc.workflow.Format;
import com.dhcc.workflow.WF;
import com.dhcc.workflow.WFUtil;
import com.dhcc.workflow.api.ProxyQuery;
import com.dhcc.workflow.api.identity.User;
import com.dhcc.workflow.pvm.internal.task.IWFProxy;
import com.dhcc.workflow.pvm.internal.task.WFProxy;
import com.dhcc.workflow.pvm.internal.task.WFProxyProcess;
import com.dhcc.workflow.pvm.internal.util.StringUtil;

import app.common.BaseController;
import app.util.toolkit.Ipage;

@Controller
@RequestMapping(value = "/system")
public class SystemController extends BaseController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	
	@RequestMapping(value = "/updateProxy")
	public String updateProxy() {
		HttpServletRequest request = getHttpRequest();
		String proxyId = request.getParameter(WF.PARAM_PROXY_ID);
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String proxyUser = request.getParameter("proxyUser");
		String proxyUserName = request.getParameter("proxyUserName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String state = request.getParameter("state");
		
		WFProxy proxy = (WFProxy) WFUtil.getSystemService().findProxy(proxyId);
		proxy.setNew(false);
		proxy.setUser(userId);
		proxy.setUserName(userName);
		proxy.setProxyUser(proxyUser);
		proxy.setProxyUserName(proxyUserName);
		proxy.setStartTime(Format.parseDate(startTime));
		proxy.setEndTime(Format.parseDate(endTime));
		proxy.setState(state);
		String process = request.getParameter("process");
		
		Set<WFProxyProcess> set = new HashSet<WFProxyProcess>();
		if(process != null && process.length() > 0){
			String[] processKeys = process.split(",");
			for(int i = 0; i < processKeys.length; i++){
				String processKey = processKeys[i];
				if(processKey != null && !"".equals(processKey)) {
					WFProxyProcess wfProxyProcess = new WFProxyProcess();
					wfProxyProcess.setProcessKey(processKey);
					set.add(wfProxyProcess);
				}
			}
		}
		proxy.setProcessKeys(set);
		
		WFUtil.getSystemService().saveProxy(proxy);
		
		return listProxy();
	}

	public String deleteProxy() {
		HttpServletRequest request = getHttpRequest();
		String proxyId = request.getParameter(WF.PARAM_PROXY_ID);
		WFUtil.getSystemService().deleteProxy(proxyId);
		
		return listProxy();
	}

	public String findProxy() {
		HttpServletRequest request = getHttpRequest();
		String proxyId = request.getParameter(WF.PARAM_PROXY_ID);
		IWFProxy proxy = WFUtil.getSystemService().findProxy(proxyId);
		request.setAttribute(WF.PARAM_OBJECT_NAME, proxy);
		
		return WF.PARAM_PROXY_FIND;
	}

	public String addProxy() {
		HttpServletRequest request = getHttpRequest();
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String proxyUser = request.getParameter("proxyUser");
		String proxyUserName = request.getParameter("proxyUserName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String process = request.getParameter("process");
		
		WFProxy proxy = new WFProxy();
		proxy.setNew(true);
		proxy.setUser(userId);
		proxy.setUserName(userName);
		proxy.setProxyUser(proxyUser);
		proxy.setProxyUserName(proxyUserName);
		proxy.setStartTime(Format.parseDate(startTime));
		proxy.setEndTime(Format.parseDate(endTime));
		proxy.setState(IWFProxy.STATE_ENABLED);
		
		Set<WFProxyProcess> set = new HashSet<WFProxyProcess>();
		if(process != null && process.length() > 0){
			String[] processKeys = process.split(",");
			for(int i = 0; i < processKeys.length; i++){
				String processKey = processKeys[i];
				if(processKey != null && !"".equals(processKey)) {
					WFProxyProcess wfProxyProcess = new WFProxyProcess();
					wfProxyProcess.setProcessKey(processKey);
					set.add(wfProxyProcess);
				}
			}
		}
		proxy.setProcessKeys(set);
		
		WFUtil.getSystemService().saveProxy(proxy);
		
		return listProxy();
	}

	public String listProxy() {
		HttpServletRequest request = getHttpRequest();
		String userId = request.getParameter(WF.PARAM_PROXYQUERY_USERID);
		String userName = request.getParameter(WF.PARAM_PROXYQUERY_USERNAME);
		
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		ProxyQuery query = WFUtil.getSystemService()
										.createQuery()
										.userId(userId)
										.userName(userName);
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		List<IWFProxy> list = query.page(firstResult, ipage.getPageSize())
								.orderAsc(ProxyQuery.PROPERTY_STARTTIME)
								.list();
		ipage.setResult(list);
		request.setAttribute(WF.PARAM_LIST_NAME, list);
		/**page end**/

		return WF.PARAM_PROXY_LIST;
	}
	
	public String selectAssign() {
		HttpServletRequest request = getHttpRequest();
		String userId = request.getParameter("UserId");
		String userName = request.getParameter("UserName");

		List<User> list = WFUtil.getIdentityService().findUsers(userId, userName);
		request.setAttribute(WF.PARAM_LIST_NAME, list);
		
		return WF.PARAM_SELECT_ASSIGN;
	}	
	public String findUsers() {
		HttpServletRequest request = getHttpRequest();
		String userId = request.getParameter("UserId");
		String userName = request.getParameter("UserName");
		
		List<User> list = WFUtil.getIdentityService().findUsers(userId, userName);
		request.setAttribute(WF.PARAM_LIST_NAME, list);
		
		return WF.PARAM_USERSINFO_FIND;
	}	
	public String findUsersInfo() {
		HttpServletRequest request = getHttpRequest();
		String userId = request.getParameter("UserId");
		String userName = request.getParameter("UserName");
		
		List<User> list = WFUtil.getIdentityService().findUsers(userId, userName);
		request.setAttribute(WF.PARAM_LIST_NAME, list);
		
		return WF.PARAM_USERSINFO_FIND;
	}
	
	public String addUserProxy() {
		return "adduserproxy";
	}
	
}
