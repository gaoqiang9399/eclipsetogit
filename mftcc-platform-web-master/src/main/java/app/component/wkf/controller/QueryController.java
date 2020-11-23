package app.component.wkf.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import app.base.User;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.WorkflowActivity;
import app.component.wkf.entity.WorkflowInstance;
import app.component.wkf.entity.WorkflowTask;
import app.component.wkf.feign.QueryFeign;
import app.util.toolkit.Ipage;
@Controller
@RequestMapping(value = "/query")
public class QueryController extends BaseFormBean {
	
	private static final long serialVersionUID = 8659398196134479613L;
	@Autowired
	private QueryFeign queryFeign ;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	private List<WorkflowActivity> workflowActivityList;
	private List<WorkflowInstance> workflowInstanceList;
	private List<Map<String,Object>> workflowTaskList;
	private WorkflowActivity workflowActivity;
	private WorkflowInstance workflowInstance;
	private WorkflowTask workflowTask;
	
	@RequestMapping(value = "/listActivity")
	public String listActivity(Model model,String startTime,String endTime) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		if(startTime != null && !"".equals(startTime)) {
            startTime = startTime + " 00:00:00";
        }
//		if(endTime != null && !"".equals(endTime))
//			endTime = endTime + " 23:59:59";
		FormData formwkf0018 = formService.getFormData("wkf0018");
		workflowActivity = new WorkflowActivity();
		getFormValue(formwkf0018);
		setObjValue(formwkf0018, workflowActivity);
		workflowActivity.setStartTime(Format.parseTimeStamp(startTime));
		workflowActivity.setEndTime(Format.parseTimeStamp(endTime));
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("workflowActivity", workflowActivity));
		workflowActivityList = (List<WorkflowActivity>) queryFeign.listActivity(ipage).getResult();
		model.addAttribute("formwkf0018", formwkf0018);
		model.addAttribute("ipage", ipage);
		model.addAttribute("query", "");
		return "/component/wkf/queryActivity";
	}
	@RequestMapping(value = "/listInstance")
	public String listInstance(Model model,String startTime,String endTime) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		if(startTime != null && !"".equals(startTime)) {
            startTime = startTime + " 00:00:00";
        }
//		if(endTime != null && !"".equals(endTime))
//			endTime = endTime + " 23:59:59";
		FormData formwkf0019 = formService.getFormData("wkf0019");
		workflowInstance = new WorkflowInstance();
		getFormValue(formwkf0019);
		setObjValue(formwkf0019, workflowInstance);
		workflowInstance.setOperator(User.getRegNo(getHttpRequest()));
		workflowInstance.setStartTime(Format.parseTimeStamp(startTime));
		workflowInstance.setEndTime(Format.parseTimeStamp(endTime));
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("workflowInstance", workflowInstance));
		workflowInstanceList = (List<WorkflowInstance>) queryFeign.listInstance(ipage).getResult();
		model.addAttribute("formwkf0019", formwkf0019);
		model.addAttribute("ipage", ipage);
		model.addAttribute("query", "");
		return "/component/wkf/queryInstance";
	}
	
//	public String listTask() throws Exception {
//		ActionContext.initialize(request,response);
//		formwkf0020 = formService.getFormData("wkf0020");
//		workflowTask = new WorkflowTask();
//		getFormValue(formwkf0020);
//		setObjValue(formwkf0020, workflowTask);
//		workflowTask.setAssignee(User.getRegNo(getHttpRequest()));
//		workflowTask.setCreateTime(Format.parseTimeStamp(createTime));
//		workflowTask.setEndTime(Format.parseTimeStamp(endTime));
//		workflowTask.setAppValue(cifName);
//		workflowTask.setProcessDefinitionKey("'"+AppConstant.PAS_WKF_KEY+"','"+AppConstant.COM_SUP_WKF_KEY+"','"+AppConstant.COM_WKF_KEY+"','"+AppConstant.NORMAL_WKF_KEY+"'");
//		Ipage ipage = this.getIpage();
//		changList(((List<WorkflowTask>) queryBo.listTask(ipage, workflowTask).getResult()));
//		return "listTask";
//	}
	@RequestMapping(value = "/listTaskForExtend")
	public String listTaskForExtend(Model model,String createTime,String endTime,String cifName) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0030 = formService.getFormData("wkf0030");
		workflowTask = new WorkflowTask();
		getFormValue(formwkf0030);
		setObjValue(formwkf0030, workflowTask);
		workflowTask.setAssignee(User.getRegNo(getHttpRequest()));
		workflowTask.setCreateTime(Format.parseTimeStamp(createTime));
		workflowTask.setEndTime(Format.parseTimeStamp(endTime));
		workflowTask.setAppValue(cifName);
		workflowTask.setProcessDefinitionKey("'"+AppConstant.COM_WKF_EXTEND_KEY+"'");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("workflowTask", workflowTask));
		changList(((List<WorkflowTask>) queryFeign.listTask(ipage).getResult()));
		model.addAttribute("formwkf0030", formwkf0030);
		model.addAttribute("ipage", ipage);
		model.addAttribute("query", "");
		return "/component/wkf/queryTaskForExtend";
	}
	
//	public String listTaskForEval() throws Exception {
//		ActionContext.initialize(request,response);
//		formwkf0031 = formService.getFormData("wkf0031");
//		workflowTask = new WorkflowTask();
//		getFormValue(formwkf0031);
//		setObjValue(formwkf0031, workflowTask);
//		workflowTask.setAssignee(User.getRegNo(getHttpRequest()));
//		workflowTask.setCreateTime(Format.parseTimeStamp(createTime));
//		workflowTask.setEndTime(Format.parseTimeStamp(endTime));
//		workflowTask.setAppValue(cifName);
//		workflowTask.setProcessDefinitionKey("'"+AppConstant.COM_WKF_EVAL_KEY+"'");
//		Ipage ipage = this.getIpage();
//		changList(((List<WorkflowTask>) queryBo.listTask(ipage, workflowTask).getResult()));
//		return "listTaskForEval";
//	}
	@RequestMapping(value = "/listTaskForAuth")
	public String listTaskForAuth(Model model,String createTime,String endTime,String cifName) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0032 = formService.getFormData("wkf0032");
		workflowTask = new WorkflowTask();
		getFormValue(formwkf0032);
		setObjValue(formwkf0032, workflowTask);
		workflowTask.setAssignee(User.getRegNo(getHttpRequest()));
		workflowTask.setCreateTime(Format.parseTimeStamp(createTime));
		workflowTask.setEndTime(Format.parseTimeStamp(endTime));
		workflowTask.setAppValue(cifName);
		workflowTask.setProcessDefinitionKey("'"+AppConstant.COM_WKF_AUTH_KEY+"'");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("workflowTask", workflowTask));
		changList(((List<WorkflowTask>) queryFeign.listTask(ipage).getResult()));
		model.addAttribute("formwkf0032", formwkf0032);
		model.addAttribute("query", "");
		return "/component/wkf/queryTaskForAuth";
	}
	@RequestMapping(value = "/changList")
	private void changList(List<WorkflowTask> list)
	{
		if(null==list||list.size()==0) {
            return;
        }
		workflowTaskList=new ArrayList<Map<String,Object>>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0;i<list.size();i++)
		{
			Map<String,Object> map=new HashMap<String,Object>();
			String appValue=list.get(i).getAppValue();
			if(null!=appValue&&!"".equals(appValue))
			{
				String[] appArray=appValue.split(",");
				if(null!=appArray&&appArray.length>0)
				{
					for(int j=0;j<appArray.length;j++)
					{
						String[] tempArray=appArray[j].split(":");
						if(null!=tempArray&&tempArray.length>1) {
                            map.put(tempArray[0],tempArray[1]);
                        }
					}
				}
			}
			map.put("id",list.get(i).getId());
			map.put("description",list.get(i).getDescription());
			map.put("appId",list.get(i).getAppId());
			map.put("assignee",list.get(i).getAssignee());
			map.put("createTimeValue",dateFormat.format(list.get(i).getCreateTime()));
			map.put("endTimeValue",dateFormat.format(list.get(i).getEndTime()));
			map.put("state",list.get(i).getState());
			map.put("executionId",list.get(i).getExecutionId());
			workflowTaskList.add(map);
		}
	}

}
