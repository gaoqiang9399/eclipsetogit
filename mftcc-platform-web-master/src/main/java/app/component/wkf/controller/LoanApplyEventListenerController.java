package app.component.wkf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dhcc.workflow.Constant;
import com.dhcc.workflow.api.listener.EventListener;
import com.dhcc.workflow.api.listener.EventListenerExecution;
import com.dhcc.workflow.pvm.internal.model.ExecutionImpl;
@Controller
@RequestMapping(value = "/loanApplyEventListener")
public class LoanApplyEventListenerController implements EventListener {
	private static final long serialVersionUID = -2909009538067382803L;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Override
	@RequestMapping(value = "/notify")
	public void notify(EventListenerExecution ele) throws Exception 
	{
		ExecutionImpl execution = (ExecutionImpl)ele;
		execution.getHistoryActivityInstanceDbid();
		String appId = execution.getVariable(Constant.DEFAULT_DATASOURCE).toString();
		//ҵ�����
	}
}
