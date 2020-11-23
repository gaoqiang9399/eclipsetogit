package app.component.common;

import com.dhcc.workflow.WF;
import com.dhcc.workflow.WFUtil;
import com.dhcc.workflow.api.TaskService;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.dhcc.workflow.pvm.internal.util.StringUtil;

public class SysTaskInfoUtil {
	/**
	 * 转换成超链接
	 * @param str 转换的字符串
	 * @param url 转换的链接地址
	 * @return
	 */
	public static String getHyperlink(String ... params){
		StringBuffer link = new StringBuffer();
		String title = "";
		try {
			title = params[2];
		} catch (Exception e) {
		}
		link.append("<a onClick=openBigForm(\\\""+params[1]+"\\\",\\\""+title+"\\\");>").append(params[0]).append("</a>");
		return link.toString();
	}
	
	public static String openApproveUrl(String taskId) throws Exception{
		TaskService service = WFUtil.getTaskService();
		TaskImpl task = (TaskImpl)service.getTask(taskId);
		String appUrl=task.getApproveUrl();
		String s=service.getTaskURL(taskId,appUrl);
		StringBuilder url = new StringBuilder(s);
		if( url != null ) {
			if( url.indexOf("?") < 0 ) {
				url.append("?").append(WF.PARAM_TASK_ID).append("=").append(taskId);
			} else {
				url.append("&").append(WF.PARAM_TASK_ID).append("=").append(taskId);
			}
//				
//				if(null!=approved&&!"".equals(approved)){
//					url.append("&approved=").append(approved);
//				}
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
		return url.toString();
	}
}
