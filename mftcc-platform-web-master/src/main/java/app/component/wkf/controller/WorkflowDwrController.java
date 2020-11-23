package app.component.wkf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.BaseFormBean;

import app.base.User;
import app.component.wkf.feign.WorkflowDwrFeign;
import app.util.ServiceLocator;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/workflowDwr")
public class WorkflowDwrController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private WorkflowDwrFeign workflowDwrFeign;

	@RequestMapping(value = "/findNextTransition")
	@ResponseBody
	public String findNextTransition(String taskId) throws Exception {
		workflowDwrFeign = ServiceLocator.getBean(WorkflowDwrFeign.class);
		return workflowDwrFeign.findNextTransition(taskId);
	}

	@RequestMapping(value = "/getTaskInfo")
	@ResponseBody
	public String[] getTaskInfo(String appNo, String taskId, HttpServletRequest request, String ajaxData) throws Exception {
		workflowDwrFeign = ServiceLocator.getBean(WorkflowDwrFeign.class);
		//封装成map，解决传参数长度限制
		Map<String,Object> parmMap = new HashMap<String, Object>();
		parmMap.put("appNo",appNo);
		parmMap.put("taskId",taskId);
		parmMap.put("ajaxData",ajaxData);
		parmMap.put("regNo",User.getRegNo(request));
		return workflowDwrFeign.getTaskInfo(parmMap);
	}
	@RequestMapping(value = "/getTask")
	@ResponseBody
	public String getTask(String taskId) throws Exception {
		workflowDwrFeign = ServiceLocator.getBean(WorkflowDwrFeign.class);
		return workflowDwrFeign.getTask(taskId);
	}

	@RequestMapping(value = "/findBackTransition")
	@ResponseBody
	public String findBackTransition(String taskId) throws Exception {
		workflowDwrFeign = ServiceLocator.getBean(WorkflowDwrFeign.class);
		return workflowDwrFeign.findBackTransition(taskId);
	}

	@RequestMapping(value = "/findExecutorByTransition")
	@ResponseBody
	public String[] findExecutorByTransition(String taskId, String transitionName, String designateType, String branchId, String appNo)
			throws Exception {
		workflowDwrFeign = ServiceLocator.getBean(WorkflowDwrFeign.class);
		return workflowDwrFeign.findExecutorByTransition(taskId, transitionName, designateType, branchId, appNo);
	}

	@RequestMapping(value = "/findWkfApprovalRole")
	@ResponseBody
	public String findWkfApprovalRole(String wkfRoleNo) throws Exception {
		workflowDwrFeign = ServiceLocator.getBean(WorkflowDwrFeign.class);
		return workflowDwrFeign.findWkfApprovalRole(wkfRoleNo);
	}

	@RequestMapping(value = "/findWkfApprovalUser")
	@ResponseBody
	public String findWkfApprovalUser(String wkfRoleNo) throws Exception {
		workflowDwrFeign = ServiceLocator.getBean(WorkflowDwrFeign.class);
		return workflowDwrFeign.findWkfApprovalUser(wkfRoleNo);
	}

}
