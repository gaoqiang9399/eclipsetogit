package app.component.wkf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.BaseFormBean;
import com.dhcc.workflow.WF;
import com.dhcc.workflow.WFUtil;
import com.dhcc.workflow.api.history.HistoryProcessInstance;
import com.dhcc.workflow.api.history.HistoryProcessInstanceQuery;
import com.dhcc.workflow.pvm.internal.util.StringUtil;

import app.base.User;
import app.util.toolkit.Ipage;
@Controller
@RequestMapping(value = "/historyProcessInstance")
public class HistoryProcessInstanceController extends BaseFormBean 
{

	
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	private List<HistoryProcessInstance> historyProcessInstanceList;
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model) throws Exception 
	{
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
            currentPage = Integer.parseInt(pageNo);
        }
		HistoryProcessInstanceQuery query = WFUtil.getHistoryService().createHistoryProcessInstanceQuery();
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		historyProcessInstanceList= query.page(firstResult, ipage.getPageSize()).orderAsc(HistoryProcessInstanceQuery.PROPERTY_STARTTIME).list();
		ipage.setResult(historyProcessInstanceList);
		/**page end**/
		
		return "/component/wkf/HistoryProcessInstance_List";
	}
	@RequestMapping(value = "/findByPage1")
	public String findByPage1() throws Exception {
		HttpServletRequest request = getHttpRequest();
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		HistoryProcessInstanceQuery query = WFUtil.getHistoryService().createHistoryProcessInstanceQuery();
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		List<HistoryProcessInstance> list = query.page(firstResult, ipage.getPageSize())
											.orderAsc(HistoryProcessInstanceQuery.PROPERTY_STARTTIME)
											.list();
		ipage.setResult(list);
		request.setAttribute(WF.PARAM_LIST_NAME, list);
		/**page end**/
		
		return "/component/wkf/processInstanceHistoryList";
	}
	@RequestMapping(value = "/myCreatedInstance")
	public String myCreatedInstance() 
	{
		String userId = User.getRegNo(getHttpRequest());
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
            currentPage = Integer.parseInt(pageNo);
        }
		HistoryProcessInstanceQuery query = WFUtil.getHistoryService().createHistoryProcessInstanceQuery().createdUser(userId);
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		historyProcessInstanceList = query.page(firstResult, ipage.getPageSize()).orderAsc(HistoryProcessInstanceQuery.PROPERTY_STARTTIME).list();
		ipage.setResult(historyProcessInstanceList);
		/**page end**/
		return "/component/wkf/myCreatedInstanceList";
	}
	public List<HistoryProcessInstance> getHistoryProcessInstanceList() {
		return historyProcessInstanceList;
	}

	public void setHistoryProcessInstanceList(List<HistoryProcessInstance> historyProcessInstanceList) {
		this.historyProcessInstanceList = historyProcessInstanceList;
	}
}
