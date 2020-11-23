/**
 * Copyright(c)2013
 * DHC Software Co., Ltd.
 *
 * All Rights Reserved
 *
 * Revision History:
 *                       Modification        Tracking
 * Author (Email ID)     Date                Number              Description
 * xiongxm               2013-02-22          BugId				 Workflow Engine initialize servlet
 *
 */

package app.component.wkf.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.dhcc.workflow.WFUtil;
import com.dhcc.workflow.api.ProcessEngine;
import com.dhcc.workflow.internal.log.Log;

import app.base.SpringUtil;

public class WorkflowServlet extends HttpServlet {
	private static final long serialVersionUID = -5042882867967445161L;
	private static final Log log = Log.getLog(WorkflowServlet.class.getName());
	
	/**
	 * Constructor of the object.
	 */
	public WorkflowServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	@Override
	public void init() throws ServletException {
//		ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
//		SourceTemplate.setContext(applicationContext);
		
		log.info("Workflow Engine initialize...");
		
		try {
			ProcessEngine engine = (ProcessEngine) SpringUtil.getBean("processEngine");
			if(engine != null) {
				if( !(WFUtil.initialize(engine)) ) {
					log.error("Workflow Engine initialize failure,could not get proccssEngine!");
				}
				log.info("Workflow Engine initialize: [SUCCESS].");
			}
			
		} catch(Exception e) {
			log.error("Workflow initialize failure, please check log file and analyse reasons.");
			log.error(e.getMessage());
		}
	}

}
