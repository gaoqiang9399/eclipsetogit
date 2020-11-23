package app.component.wkf.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.WF;
import com.dhcc.workflow.WFUtil;
import com.dhcc.workflow.api.TaskQuery;
import com.dhcc.workflow.api.history.HistoryTask;
import com.dhcc.workflow.api.history.HistoryTaskQuery;
import com.dhcc.workflow.api.model.Transition;
import com.dhcc.workflow.api.task.Task;
import com.dhcc.workflow.pvm.internal.model.ExecutionImpl;
import com.dhcc.workflow.pvm.internal.task.TaskConstants;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.dhcc.workflow.pvm.internal.util.StringUtil;

import app.base.User;
import app.component.sysTaskInfoInterface.SysTaskInfoInterfaceFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.ListUtil;
import app.component.wkf.WebUtil;
import app.component.wkf.feign.TaskFeign;
import app.component.wkf.feign.WkfBusinessOperateFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping(value = "/task")
public class TaskController extends BaseFormBean {
	
	private static final long serialVersionUID = 8659398196134479613L;
	private List<Task> taskList;
	private List historyTaskList;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private TaskFeign taskFeign;
	
	
	
	/*private String ajaxData;
	private Map<String,Object> dataMap;
	private FormService formService = new FormService();*/
	private WkfBusinessOperateFeign wkfBusinessOperateFeign;
	private SysTaskInfoInterfaceFeign sysTaskInfoInterface;
	

	
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model)  throws Exception {
		ActionContext.initialize(request,response);
		
		return "getListPage";
	}
	
	/***
	 * 标准例子
	 * 列表数据查询的异步方法
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String taskName,String ajaxData,FormData formwkf0015,String assignee,String appId,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		//FormData formwkf0015 = formService.getFormData("formwkf0015");
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			TaskQuery query = taskFeign.createQuery();
			if( !(StringUtil.isEmpty(taskName)) ) {
				query = query.activityName(taskName);
				this.changeFormProperty(formwkf0015,"taskName","initValue",taskName);
			}
			
			if( !(StringUtil.isEmpty(assignee)) ) { 
				query = query.assignee(assignee);
				this.changeFormProperty(formwkf0015,"assignee","initValue",assignee);
			}

			if( !(StringUtil.isEmpty(appId)) ) { 
				query = query.appId(appId);
				this.changeFormProperty(formwkf0015,"appId","initValue",appId);
			}
			JSONArray arr = JSONArray.fromObject(ajaxData);
			JSONObject obj = JSONObject.fromObject(arr.get(0));
			if(StringUtils.isNotBlank(obj.getString("customQuery"))){
				query.appId(obj.getString("customQuery"));
			}
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.initPageCounts(new Integer[] { (int) query.count()});
			int firstResult = (pageNo-1) * ipage.getPageSize();
			taskList= query.page(firstResult,ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
			ipage.setResult(taskList);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model,String taskName,String assignee,String appId) throws Exception {
		FormService formService = new FormService();
		FormData formwkf0015 = formService.getFormData("wkf0015");
		
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		
		TaskQuery query = taskFeign.createQuery();
		if( !(StringUtil.isEmpty(taskName)) ) {
			query = query.activityName(taskName);
			this.changeFormProperty(formwkf0015,"taskName","initValue",taskName);
		}
		
		if( !(StringUtil.isEmpty(assignee)) ) { 
			query = query.assignee(assignee);
			this.changeFormProperty(formwkf0015,"assignee","initValue",assignee);
		}

		if( !(StringUtil.isEmpty(appId)) ) { 
			query = query.appId(appId);
			this.changeFormProperty(formwkf0015,"appId","initValue",appId);
		}
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		taskList= query.page(firstResult,ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		ipage.setResult(taskList);
		model.addAttribute("formwkf0015", formwkf0015);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return WF.PARAM_TASK_LIST; 
	}
	@RequestMapping(value = "/findByPageForApp")
	public String findByPageForApp(Model model,String activityName,String assignee,String cifName,String name) throws Exception 
	{
		FormService formService = new FormService();
		FormData formwkf0021 = formService.getFormData("wkf0021");
		
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		
		TaskQuery query = taskFeign.createQuery();
		if( !(StringUtil.isEmpty(activityName)) ) { 
			query = query.activityNames(activityName);
			this.changeFormProperty(formwkf0021,"activityName","initValue",activityName);
		}
		if( !(StringUtil.isEmpty(assignee)) ) { 
			query = query.assignee(assignee);
			this.changeFormProperty(formwkf0021,"assignee","initValue",assignee);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0021,"cifName","initValue",cifName);
		}
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
//		List<PrdBase> list=wkfBusinessOperateBo.getPrdBaseListForWkf(null);
//		String key="";
//		for(int i=0;i<list.size();i++)
//		{
//			key+=list.get(i).getAppWorkflowId()+",";
//		}
//		taskList=query.processDefinitionKeys(key.substring(0,key.length()-1)).page(firstResult,ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();	
		taskList=query.page(firstResult,ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		model.addAttribute("formwkf0021", formwkf0021);
		model.addAttribute("firstResult",firstResult);
		model.addAttribute("query","");
		return "/component/wkf/Task_ListForApp";
	}
	
	@RequestMapping(value = "/rollback")
	public String rollback(Model model,String taskId) throws Exception {
		String rollbackTo = getHttpRequest().getParameter(WF.PARAM_TASK_TRANSITION);
		taskFeign.rollback(taskId,"退回",rollbackTo);
		
		return listMyTask(model);
	}
	@RequestMapping(value = "/recall")
	public String recall(Model model,String taskId) throws Exception {
		try {
			taskFeign.recall(taskId);
			
		} catch(Exception e) {
			String errorMsg = e.getMessage();
			if( errorMsg.length() < 30) {
			this.addActionError(model,errorMsg);
			}
		}
		
		return mySendedTask(model);
	}
	@RequestMapping(value = "/myProxyTask")
	public String myProxyTask(Model model) throws Exception  {
		String userId = User.getRegNo(getHttpRequest());
		String branchId = User.getOrgNo(getHttpRequest());
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		
		TaskQuery query = taskFeign.candidateTaskQuery(userId,branchId); 
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		taskList = query.page(firstResult,ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		ipage.setResult(taskList);
        model.addAttribute("firstResult", firstResult);
        model.addAttribute("query", "");
		return "/component/wkf/MyProxyTask_List";
	}
	@RequestMapping(value = "/reAssign")
	public String reAssign(Model model,String ajaxData,String taskId) throws Exception {
		String userId = getHttpRequest().getParameter(WF.PARAM_TASKQUERY_USERID);
		String appId = getHttpRequest().getParameter("appId");
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		if(userId==null||appId==null||"".equals(userId)||"".equals(appId)){
		  dataMap = getMapByJson(ajaxData);
			userId = String.valueOf(dataMap.get("userId"));
			appId = String.valueOf(dataMap.get("appId"));
			taskId = String.valueOf(dataMap.get("taskId"));
		}
		
		taskFeign.assign(taskId,userId);
		
		String[] next =new  String[3];
		Task task =(TaskImpl) taskFeign.getTask(taskId);
		if(task.getAssignee()!=null&&!"".equals(task.getAssignee())){
			next[0] = task.getAssignee();
		}
		if(StringUtils.isBlank(next[0])){
			next[1]=taskFeign.getTaskDefinition(task.getId()).getGroups((ExecutionImpl) WFUtil.getExecutionService().findExecutionByTaskId(task.getId()));
		}
		
		sysTaskInfoInterface.updateTaskChangeUser(appId, taskId, userId);
		model.addAttribute("query", "");
		return getListPage(model);
	}
	@ResponseBody
	@RequestMapping(value = "/reAssignAjax")
	public Map<String, Object> reAssignAjax(String ajaxData,String taskId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			String userId = getHttpRequest().getParameter(WF.PARAM_TASKQUERY_USERID);
			String appId = getHttpRequest().getParameter("appId");
			if(userId==null||appId==null||"".equals(userId)||"".equals(appId)){
				dataMap = getMapByJson(ajaxData);
				userId = String.valueOf(dataMap.get("userId"));
				appId = String.valueOf(dataMap.get("appId"));
				taskId = String.valueOf(dataMap.get("taskId"));
			}
			
			taskFeign.assign(taskId,userId);
			
			String[] next =new  String[3];
			Task task =(TaskImpl) taskFeign.getTask(taskId);
			if(task.getAssignee()!=null&&!"".equals(task.getAssignee())){
				next[0] = task.getAssignee();
			}
			if(StringUtils.isBlank(next[0])){
				next[1]=taskFeign.getTaskDefinition(task.getId()).getGroups((ExecutionImpl) WFUtil.getExecutionService().findExecutionByTaskId(task.getId()));
			}
			
			sysTaskInfoInterface.updateTaskChangeUser(appId, taskId, userId);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/reAssignForApp")
	public String reAssignForApp(Model model,String appId,String userId, String taskId) throws Exception 
	{
		
//		wkfBusinessOperateBo=(WkfBusinessOperateBo)SourceTemplate.getSpringContextInstance().getBean("wkfBusinessOperateBo");
		wkfBusinessOperateFeign.updateMangInfo(appId,userId,taskId);
		return findByPageForApp(model, appId, userId, taskId, taskId);
	}
	@RequestMapping(value = "/selectTransition")
	public String selectTransition(Model model,String taskId) throws Exception {
		List<Transition> list = taskFeign.getTransitions(taskId);
		getHttpRequest().setAttribute(WF.PARAM_LIST_NAME,list);
		
		return "/component/wkf/selectTransition";
	}
	@RequestMapping(value = "/selectBackTransition")
	public String selectBackTransition(Model model,String taskId) throws Exception {
		HttpServletRequest request = getHttpRequest();
		List<Transition> list = taskFeign.getBackTransitions(taskId);
		request.setAttribute(WF.PARAM_LIST_NAME,list);
		
		return "/component/wkf/selectBackTransition";
	}	
	@RequestMapping(value = "/refuseTask")
	public String refuseTask(Model model,String taskId) throws Exception {
		taskFeign.refuseString(taskId,"否决",WebUtil.getUserId(getHttpRequest()));
		return getListPage(model);
	}
	@ResponseBody
	@RequestMapping(value = "/refuseTaskAjax")
	public Map<String, Object> refuseTaskAjax(String taskId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			dataMap =  new HashMap<String, Object>(); 
			//taskId = String.valueOf(dataMap.get("taskId"));
			taskFeign.refuseString(taskId,"否决",WebUtil.getUserId(getHttpRequest()));
			sysTaskInfoInterface.deleteTaskByWkfTaskNo(taskId);
			dataMap.put("flag", "success");
			dataMap.put("msg", "否决成功");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "否决失败");
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/refuse")
	public String refuse(Model model,String taskId) throws Exception {
		taskFeign.refuseString(taskId,"否决",WebUtil.getUserId(getHttpRequest()));
		
		return listMyTask(model);
	}
	@RequestMapping(value = "/mySendedTask")
	public String mySendedTask(Model model) throws Exception  {
		String userId = User.getOrgNo(getHttpRequest());
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		
		TaskQuery query = taskFeign.sendedUser(userId);
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		taskList = query.page(firstResult,ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		ipage.setResult(taskList);
		/**page end**/

		return "/component/wkf/Task_MySendedList";
	}
	@RequestMapping(value = "/myCommitedTask")
	public String myCommitedTask() throws Exception {
		String userId = User.getOrgNo(getHttpRequest());
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		HistoryTaskQuery query = WFUtil.getHistoryService().createHistoryTaskQuery().assignee(userId).commited();
		Ipage ipage = this.getIpage();
		query.groupByAppId();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
//		historyTaskList = query.page(firstResult,ipage.getPageSize()).orderDesc(HistoryTaskQuery.PROPERTY_CREATETIME).list();
		//��������ֻ��һ��
		historyTaskList = query.page(firstResult,ipage.getPageSize()).orderDesc(HistoryTaskQuery.PROPERTY_DB_CREATETIME).list();
		ipage.setResult(historyTaskList);
		/**page end**/

		return "/component/wkf/Task_MyCommitedList";
	}
	@RequestMapping(value = "/myCommitedTaskForApp")
	public String myCommitedTaskForApp(Model model,String appNo,String cifName,String name,String startDate,String endDate) throws Exception {
		FormService formService = new FormService();
		FormData formwkf0026 = formService.getFormData("wkf0026");
		String userId = User.getRegNo(getHttpRequest());
		Ipage ipage = this.getIpage();
		/**page start**/
		HistoryTaskQuery query = WFUtil.getHistoryService().createHistoryTaskQuery();
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0026,"appNo","initValue",appNo);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0026,"cifName","initValue",cifName);
		}
		if (null != startDate && !"".equals(startDate))
		{
			this.changeFormProperty(formwkf0026,"startDate","initValue",startDate);
		}
		if (null != endDate && !"".equals(endDate))
		{
			this.changeFormProperty(formwkf0026,"endDate","initValue",endDate);
		}
		query.assignee(userId);
		query.commited();
		query.ended(startDate,endDate);
		query.groupByAppId();
		ipage.initPageCounts(new Integer[] { (int) query.count() });
		int firstResult = (ipage.getPageNo() - 1) * ipage.getPageSize();
//		historyTaskList = query.page(firstResult,ipage.getPageSize()).orderDesc(HistoryTaskQuery.PROPERTY_CREATETIME).list();
		//�������ֻ��һ��
		historyTaskList = query.page(firstResult,ipage.getPageSize()).orderDesc(HistoryTaskQuery.PROPERTY_DB_CREATETIME).list();
		historyTaskList = ListUtil.changeHisList(historyTaskList);
		ipage.setResult(historyTaskList);
		/**page end**/
		model.addAttribute("formwkf0026", formwkf0026);
		model.addAttribute("firstResult", firstResult);
		model.addAttribute("query", "");
		return "/component/wkf/Task_MyCommitedListForApp";
	}
	@RequestMapping(value = "/myCommitedTaskForAuth")
	public String myCommitedTaskForAuth(Model model,String appNo,String cifName,String name,String startDate,String endDate) throws Exception {
		FormService formService = new FormService();
		FormData formwkf0027 = formService.getFormData("wkf0027");
		String userId = User.getRegNo(getHttpRequest());
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		HistoryTaskQuery query = WFUtil.getHistoryService().createHistoryTaskQuery();
		query.processDefinitionKeys(AppConstant.COM_WKF_AUTH_KEY);
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0027,"appNo","initValue",appNo);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0027,"cifName","initValue",cifName);
		}
		if (null != startDate && !"".equals(startDate))
		{
			this.changeFormProperty(formwkf0027,"startDate","initValue",startDate);
		}
		if (null != endDate && !"".equals(endDate))
		{
			this.changeFormProperty(formwkf0027,"endDate","initValue",endDate);
		}
		query.assignee(userId);
		query.commited();
		query.ended(startDate,endDate);
		query.groupByAppId();
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
//		historyTaskList = query.page(firstResult,ipage.getPageSize()).orderDesc(HistoryTaskQuery.PROPERTY_CREATETIME).list();
		//�������ֻ��һ��
		historyTaskList = query.page(firstResult,ipage.getPageSize()).orderDesc(HistoryTaskQuery.PROPERTY_DB_CREATETIME).list();
		historyTaskList = ListUtil.changeHisList(historyTaskList);
		ipage.setResult(historyTaskList);
		/**page end**/
        model.addAttribute("formwkf0027", formwkf0027);
        model.addAttribute("firstResult", firstResult);
        model.addAttribute("query", "");
		return "/component/wkf/Task_MyCommitedListForAuth";
	}
	@RequestMapping(value = "/myCommitedTaskForEval")
	public String myCommitedTaskForEval(Model model,String appNo,String cifName,String name,String startDate,String endDate) throws Exception {
		FormService formService = new FormService();
		FormData formwkf0028 = formService.getFormData("wkf0028");
		String userId = User.getRegNo(getHttpRequest());
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		HistoryTaskQuery query = WFUtil.getHistoryService().createHistoryTaskQuery();
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0028,"appNo","initValue",appNo);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0028,"cifName","initValue",cifName);
		}
		if (null != startDate && !"".equals(startDate))
		{
			this.changeFormProperty(formwkf0028,"startDate","initValue",startDate);
		}
		if (null != endDate && !"".equals(endDate))
		{
			this.changeFormProperty(formwkf0028,"endDate","initValue",endDate);
		}
		query.assignee(userId);
		query.commited();
		query.ended(startDate,endDate);
		query.groupByAppId();
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
//		historyTaskList = query.page(firstResult,ipage.getPageSize()).orderDesc(HistoryTaskQuery.PROPERTY_CREATETIME).list();
		//�������ֻ��һ��
		historyTaskList = query.page(firstResult,ipage.getPageSize()).orderDesc(HistoryTaskQuery.PROPERTY_DB_CREATETIME).list();
		historyTaskList = ListUtil.changeHisList(historyTaskList);
		ipage.setResult(historyTaskList);
		/**page end**/
	    model.addAttribute("formwkf0028", formwkf0028);
	    model.addAttribute("firstResult", firstResult);
	    model.addAttribute("query", "");
		return "/component/wkf/Task_MyCommitedListForEval";
	}
	@RequestMapping(value = "/myCommitedTaskForExtend")
	public String myCommitedTaskForExtend(Model model,String appNo,String cifName,String name,String startDate,String endDate) throws Exception {
		FormService formService = new FormService();
		FormData formwkf0029 = formService.getFormData("wkf0029");
		String userId = User.getRegNo(getHttpRequest());
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		HistoryTaskQuery query = WFUtil.getHistoryService().createHistoryTaskQuery();
		query.processDefinitionKeys(AppConstant.COM_WKF_EXTEND_KEY);
		if (null != appNo && !"".equals(appNo))
		{
			query.appId(appNo);
			this.changeFormProperty(formwkf0029,"appNo","initValue",appNo);
		}
		if (null != cifName && !"".equals(cifName))
		{
			query.appValue(name+cifName);
			this.changeFormProperty(formwkf0029,"cifName","initValue",cifName);
		}
		if (null != startDate && !"".equals(startDate))
		{
			this.changeFormProperty(formwkf0029,"startDate","initValue",startDate);
		}
		if (null != endDate && !"".equals(endDate))
		{
			this.changeFormProperty(formwkf0029,"endDate","initValue",endDate);
		}
		query.assignee(userId);
		query.commited();
		query.ended(startDate,endDate);
		query.groupByAppId();
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
//		historyTaskList = query.page(firstResult,ipage.getPageSize()).orderDesc(HistoryTaskQuery.PROPERTY_CREATETIME).list();
		//�������ֻ��һ��
		historyTaskList = query.page(firstResult,ipage.getPageSize()).orderDesc(HistoryTaskQuery.PROPERTY_DB_CREATETIME).list();
		historyTaskList = ListUtil.changeHisList(historyTaskList);
		ipage.setResult(historyTaskList);
		/**page end**/
	    model.addAttribute("formwkf0029", formwkf0029);
		model.addAttribute("firstResult", firstResult);
        model.addAttribute("query", "");
		return "/component/wkf/Task_MyCommitedListForExtend";
	}
	@RequestMapping(value = "/openTaskForm")
	public String openTaskForm(Model model,String taskId,String formURL) throws Exception {
		String url = taskFeign.getTaskURL(taskId,formURL);
		try {
			getHttpResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/openApproveUrl")
	public String openApproveUrl(Model model,String taskId,String approved) throws Exception {
		TaskImpl task = (TaskImpl)taskFeign.getTask(taskId);
		String appUrl=task.getApproveUrl();
		String s=taskFeign.getTaskURLAppUrl(taskId,appUrl);
		StringBuilder url = new StringBuilder(s);
		try {
			if( url != null ) {
				if( url.indexOf("?") < 0 ) {
					url.append("?").append(WF.PARAM_TASK_ID).append("=").append(taskId);
				} else {
					url.append("&").append(WF.PARAM_TASK_ID).append("=").append(taskId);
				}
				
				if(null!=approved&&!"".equals(approved)){
					url.append("&approved=").append(approved);
				}
				if(url.indexOf("appNo") < 0){
					url.append("&appNo=").append(task.getAppId());
				}
				if(!StringUtil.isEmpty(task.getResult())&&!"null".equals(task.getApproveIdea())) {
                    url.append("&opinionType=").append(task.getResult());
                }
				if(!StringUtil.isEmpty(task.getApproveIdea())&&!"null".equals(task.getApproveIdea())) {
                    url.append("&approvalOpinion=").append(task.getApproveIdea());
                }
				url.append("&activityType=").append(task.getActivityType());
				url.append("&isAssignNext=").append(task.getIsAssignNext());
				url.append("&executionId=").append(task.getExecutionId());
				
			}
			getHttpResponse().sendRedirect(url.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@RequestMapping(value = "/resumeTask")
	public String resumeTask(Model model,String taskId) throws Exception {
		taskFeign.resume(taskId);
		return getListPage(model);
	}
	/**
	 * 暂停
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/suspendTask")
	public String suspendTask(Model model,String taskId) throws Exception {
		taskFeign.suspend(taskId);
		return getListPage(model);
	}
	@RequestMapping(value = "/complete")
	public String complete(Model model,String taskId) throws Exception {
		HttpServletRequest request = getHttpRequest();
		String transition = request.getParameter(WF.PARAM_TASK_TRANSITION);
		String userId = WebUtil.getUserId(request);
		taskFeign.completeZT(taskId,TaskConstants.PASS,"ͬ暂停",transition,userId);
		
		return listMyTask(model);
	}
	@RequestMapping(value = "/completeByNextUser")
	public String completeByNextUser(Model model,String taskId) throws Exception {
		HttpServletRequest request = getHttpRequest();
		String transition = request.getParameter(WF.PARAM_TASK_TRANSITION);
		String nextAssignee = request.getParameter(WF.PARAM_TASK_NEXTUSER);
		taskFeign.completeByNextUser(taskId,TaskConstants.PASS,"ͬ��",transition,nextAssignee);
		
		return listMyTask(model);
	}	
	@RequestMapping(value = "/take")
	public String take(Model model,String taskId) throws Exception {
		String userId = WebUtil.getUserId(getHttpRequest());
		taskFeign.take(taskId,userId);
		return "myList";
	}
	@RequestMapping(value = "/untake")
	public String untake(Model model,String taskId) throws Exception {
		taskFeign.untake(taskId);
		return "myList";
	}
	
	@RequestMapping(value = "/listMyTask")
	public String listMyTask(Model model) throws Exception {
		HttpServletRequest request = getHttpRequest();
		String userId = WebUtil.getUserId(request);
		String branchId = WebUtil.getBranchId(request);
		List<Task> list = null;
		
		if( !StringUtil.isEmpty(userId) ) {
			list = taskFeign.findPersonalTasksList(userId);
		} 
		
		if( list == null ) {
			list = taskFeign.findGroupTasksList(userId,branchId);
		} else {
			list.addAll(taskFeign.findGroupTasksList(userId,branchId));
		}
		request.setAttribute(WF.PARAM_LIST_NAME,list);

		return "mytaskList" ;
	}
	@RequestMapping(value = "/listMyTaskByUser")
	public String listMyTaskByUser(Model model) throws Exception {
		HttpServletRequest request = getHttpRequest();
		String userId = WebUtil.getUserId(request);
		if( StringUtil.isEmpty(userId) ) {
			return null;
		}
		
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		TaskQuery query =  taskFeign.assignee(userId);
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		List<Task> list = query.page(firstResult,ipage.getPageSize())
									.orderAsc(TaskQuery.PROPERTY_CREATEDATE)
									.list();
		ipage.setResult(list);
		request.setAttribute(WF.PARAM_LIST_NAME,list);
		
		String appMsg = request.getParameter(AppConstant.PARAM_MSG);
		if( !StringUtil.isEmpty(appMsg) ) {
			this.addActionMessage(model,appMsg);
		}
		return "/component/wkf/myTaskListByUser";
	}
	@RequestMapping(value = "/listMyTaskByGroup")
	public String listMyTaskByGroup(Model model) throws Exception {
		ActionContext.initialize(request,response);
		String userId =User.getRegNo(getHttpRequest());
		if( StringUtil.isEmpty(userId)) {
			return null;
		}
		
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		
		String branchId = User.getOrgNo(getHttpRequest());
		TaskQuery query = null;
		if (branchId == null || branchId.length() == 0 ) {
			query = taskFeign.candidateUserId(userId);
		} else {
			query = taskFeign.candidateUAB(userId,branchId);
		}
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		taskList = query.page(firstResult,ipage.getPageSize()).orderAsc(TaskQuery.PROPERTY_CREATEDATE).list();
		ipage.setResult(taskList);
		return "/component/wkf/Task_MyGroupList";
	}	
	@RequestMapping(value = "/delete")
	public String delete(Model model,String taskId) throws Exception {
		taskFeign.delete(taskId);
		return findByPage(model, taskId, taskId, taskId);
	}
	@RequestMapping(value = "/approveIdea")
	public String approveIdea(Model model,String executionId) throws Exception {
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		String processInstanceId = WF.getProcessInstanceId(executionId);
		HistoryTaskQuery query = WFUtil.getHistoryService().createHistoryTaskQuery().hasResult();
		query = query.executionId(processInstanceId);
		
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		historyTaskList = query.page(firstResult,ipage.getPageSize())
									.orderDesc(HistoryTaskQuery.PROPERTY_ENDTIME)
									.list();
		ipage.setResult(historyTaskList);
		/**page end**/
		return "/component/wkf/Task_ApproveIdeaList";		
	}
	@RequestMapping(value = "/approvePath")
	public String approvePath(Model model,String executionId) throws Exception {
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		String processInstanceId = WF.getProcessInstanceId(executionId);
		HistoryTaskQuery query = WFUtil.getHistoryService().createHistoryTaskQuery().hasResult();
		query = query.executionId(processInstanceId);
		
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		historyTaskList = query.page(firstResult,ipage.getPageSize())
									.orderDesc(HistoryTaskQuery.PROPERTY_ID)
									.list();
		ipage.setResult(historyTaskList);
		/**page end**/
		return "approvePath";		
	}
	@RequestMapping(value = "/approveIdea1")
	public String approveIdea1(Model model) throws Exception {
		HttpServletRequest request = getHttpRequest();
		/**page start**/
		String pageNo = this.getEadisPage();
		int currentPage = 1;
		if( !(StringUtil.isEmpty(pageNo)) ) {
			currentPage = Integer.parseInt(pageNo);
		}
		String executionId = request.getParameter(WF.PARAM_EXECUTION_ID);
		String processInstanceId = WF.getProcessInstanceId(executionId);
		HistoryTaskQuery query = WFUtil.getHistoryService().createHistoryTaskQuery().hasResult();
		query = query.executionId(processInstanceId);
		
		Ipage ipage = this.getIpage();
		ipage.initPageCounts(new Integer[] { (int) query.count()});
		int firstResult = (currentPage-1) * ipage.getPageSize();
		List<HistoryTask> list = query.page(firstResult,ipage.getPageSize())
									.orderDesc(HistoryTaskQuery.PROPERTY_ENDTIME)
									.list();
		ipage.setResult(list);
		request.setAttribute(WF.PARAM_LIST_NAME,list);
		/**page end**/
		request.setAttribute(WF.PARAM_EXECUTION_ID,executionId);
		
		return "/component/wkf/Task_ApproveIdeaList";
		
	}
	@RequestMapping(value = "/toChangeUser")
	public String toChangeUser(Model model,String taskId,String appId) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formwkf0051 = formService.getFormData("wkf0051");
		dataMap = new HashMap<String, Object>(); 
		dataMap.put("taskId", taskId);
		dataMap.put("appId", appId);
		getObjValue(formwkf0051, dataMap);
//		System.out.println(taskId+"-----"+userId+"-----"+appId+"-----");
		return "/component/wkf/Task_ChangeUser";
	}
	
}
