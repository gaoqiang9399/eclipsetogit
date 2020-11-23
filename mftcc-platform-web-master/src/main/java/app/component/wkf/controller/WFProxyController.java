package app.component.wkf.controller;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.dhcc.workflow.Format;
import com.dhcc.workflow.WF;
import com.dhcc.workflow.WFUtil;
import com.dhcc.workflow.api.ProcessDefinition;
import com.dhcc.workflow.api.ProcessDefinitionQuery;
import com.dhcc.workflow.api.ProxyQuery;
import com.dhcc.workflow.api.identity.User;
import com.dhcc.workflow.pvm.internal.task.IWFProxy;
import com.dhcc.workflow.pvm.internal.task.WFProxy;
import com.dhcc.workflow.pvm.internal.task.WFProxyProcess;
import com.dhcc.workflow.pvm.internal.util.StringUtil;

import app.util.toolkit.Ipage;
@Controller
@RequestMapping(value = "/wFProxy")
public class WFProxyController extends BaseFormBean 
{


/*	private PrdBaseBo prdBaseBo;
	private List<PrdBase> prdBaseList;*/
	private List<ProcessDefinition> pocessDefinitionList;
	
	private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd"); 
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model,String userId,String userName) 
	{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0016 = formService.getFormData("wkf0016");
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
            currentPage = Integer.parseInt(pageNo);
        }
		ProxyQuery query = WFUtil.getSystemService().createQuery().userId(userId).userName(userName);
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		List<IWFProxy> wFProxyList = query.page(firstResult, ipage.getPageSize()).orderAsc(ProxyQuery.PROPERTY_STARTTIME).list();
		ipage.setResult(wFProxyList);
		request.setAttribute("wFProxyList", wFProxyList);
		/**page end**/
        model.addAttribute("formwkf0016", formwkf0016);
        model.addAttribute("wFProxyList", wFProxyList);
        model.addAttribute("query", "");
		return "/component/wkf/WFProxy_List";
	}
	@RequestMapping(value = "/input")
	public String input(Model model) {
		FormService formService = new FormService();
		FormData formwkf0017 = formService.getFormData("wkf0017");
		model.addAttribute("formwkf0017", formwkf0017);
		model.addAttribute("query", "");
		return "/component/wkf/WFProxy_Insert";
	}
	@RequestMapping(value = "/insert")
	public String insert(Model model,String startTime,String endTime,String process) 
	{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		WFProxy proxy = new WFProxy();
		FormData formwkf0017 = formService.getFormData("wkf0017");
		getFormValue(formwkf0017);
		setObjValue(formwkf0017, proxy);
		proxy.setNew(true);
		proxy.setStartTime(Format.parseDate(startTime));
		proxy.setEndTime(Format.parseDate(endTime));
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
		String proxyId = WFUtil.getSystemService().saveProxy(proxy);
		proxy.setDbid(Long.parseLong(proxyId));
		getObjValue(formwkf0017, proxy);
		this.addActionMessage(model, "����ɹ�");
		model.addAttribute("formwkf0017", formwkf0017);
		model.addAttribute("proxyId", proxyId);
		model.addAttribute("query", "");
		return "/component/wkf/WFProxy_Detail";
	}
	@RequestMapping(value = "/update")
	public String update(Model model,String proxyId,String startTime,String endTime,String process)
	{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		WFProxy proxy = new WFProxy();
		proxy = (WFProxy) WFUtil.getSystemService().findProxy(proxyId);
		FormData formwkf0017 = formService.getFormData("wkf0017");
		getFormValue(formwkf0017);
		setObjValue(formwkf0017, proxy);
		proxy.setStartTime(Format.parseDate(startTime));
		proxy.setEndTime(Format.parseDate(endTime));
		Set<WFProxyProcess> set = new HashSet<WFProxyProcess>();
		if(process != null && process.length() > 0)
		{
			String[] processKeys = process.split(",");
			for(int i = 0; i < processKeys.length; i++)
			{
				String processKey = processKeys[i];
				if(processKey != null && !"".equals(processKey)) 
				{
					WFProxyProcess wfProxyProcess = new WFProxyProcess();
					wfProxyProcess.setProcessKey(processKey);
					set.add(wfProxyProcess);
				}
			}
		}
		proxy.setProcessKeys(set);
		proxyId= WFUtil.getSystemService().saveProxy(proxy);
		getObjValue(formwkf0017, proxy);
		this.addActionMessage(model, "����ɹ�");
		model.addAttribute("formwkf0017", formwkf0017);
		model.addAttribute("proxyId", proxyId);
		model.addAttribute("query", "");
		return "/component/wkf/WFProxy_Detail";
	}
	@RequestMapping(value = "/delete")
	public String delete(Model model,String proxyId) 
	{
		WFUtil.getSystemService().deleteProxy(proxyId);
		return findByPage(model, proxyId, proxyId);
	}
	@RequestMapping(value = "/getById")
	public String getById(Model model,String proxyId) {
		IWFProxy iproxy = WFUtil.getSystemService().findProxy(proxyId);
		FormService formService = new FormService();
		FormData formwkf0017 = formService.getFormData("wkf0017");
		WFProxy proxy = (WFProxy) iproxy;
		String process = "";
		getObjValue(formwkf0017, proxy);
	    this.changeFormProperty(formwkf0017, "startTime", "initValue",simpleDateFormat.format(proxy.getStartTime()));
	    this.changeFormProperty(formwkf0017, "endTime", "initValue",simpleDateFormat.format(proxy.getEndTime()));
	    this.changeFormProperty(formwkf0017, "proxyId", "initValue",proxyId);
	    Set<WFProxyProcess> set = proxy.getProcessKeys();
		if(set != null)
		{
			Iterator<WFProxyProcess> it = set.iterator();
			while(it.hasNext())
			{
				WFProxyProcess pp = it.next();
				process += "," + pp.getProcessKey();
			}
		}
		if(null!=process&&!"".equals(process)) {
            process = process.substring(1);
        }
	    this.changeFormProperty(formwkf0017, "process", "initValue",process);
	    model.addAttribute("formwkf0017", formwkf0017);
		model.addAttribute("process", process);
		model.addAttribute("query", "");
		return "/component/wkf/WFProxy_Detail";
	}
	@RequestMapping(value = "/selectAssign")
	public String selectAssign() {
		HttpServletRequest request = getHttpRequest();
		String userId = request.getParameter("UserId");
		String userName = request.getParameter("UserName");

		List<User> list = WFUtil.getIdentityService().findUsers(userId, userName);
		request.setAttribute(WF.PARAM_LIST_NAME, list);
		
		return "/component/wkf/selectAssignUser";
	}	
	public String findUsers() {
		HttpServletRequest request = getHttpRequest();
		String userId = request.getParameter("UserId");
		String userName = request.getParameter("UserName");
		
		List<User> list = WFUtil.getIdentityService().findUsers(userId, userName);
		request.setAttribute(WF.PARAM_LIST_NAME, list);
		
		return "/component/wkf/selectUserInfo";
	}	
	public String findUsersInfo() {
		HttpServletRequest request = getHttpRequest();
		String userId = request.getParameter("UserId");
		String userName = request.getParameter("UserName");
		
		List<User> list = WFUtil.getIdentityService().findUsers(userId, userName);
		request.setAttribute(WF.PARAM_LIST_NAME, list);
		
		return "/component/wkf/selectUserInfo";
	}
	@RequestMapping(value = "/selectBusiness")
	public String selectBusiness() 
	{
		ActionContext.initialize(request,response);
		ProcessDefinitionQuery query = WFUtil.getRepositoryService().createProcessDefinitionQuery();
		pocessDefinitionList = query.page(0, 50).orderAsc(ProcessDefinitionQuery.PROPERTY_KEY).orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).groupByKey().list();
		return "/component/wkf/WFProxy_SelectBusiness";
	}
	@RequestMapping(value = "/selectWorkflowId")
	public String selectWorkflowId() 
	{
		ActionContext.initialize(request, response);
		ProcessDefinitionQuery query = WFUtil.getRepositoryService().createProcessDefinitionQuery();
		pocessDefinitionList = query.page(0, 100).orderAsc(ProcessDefinitionQuery.PROPERTY_KEY).orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).groupByKey().list();
		return "/component/wkf/WFProxy_SelectWorkflowId";
	}
	

}
