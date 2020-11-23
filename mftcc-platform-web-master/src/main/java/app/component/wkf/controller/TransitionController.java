package app.component.wkf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.BaseFormBean;
import com.dhcc.workflow.api.model.Transition;

import app.component.wkf.feign.TaskFeign;

@Controller
@RequestMapping(value = "/transition")
public class TransitionController extends BaseFormBean 
{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private TaskFeign taskFeign;
	public String findNextTransition(Model model,String taskId) throws Exception 
	{
		List<Transition> transitionList=transitionList=taskFeign.getTransitions(taskId);	
		model.addAttribute("transitionList", transitionList);
		return "/component/wkf/Transition_NextTransitionList";
	}
	public String findBackTransition(Model model,String taskId) throws Exception 
	{
		List<Transition> transitionList=taskFeign.getBackTransitions(taskId);	
		return "/component/wkf/Transition_BackTransitionList";
	}
}
