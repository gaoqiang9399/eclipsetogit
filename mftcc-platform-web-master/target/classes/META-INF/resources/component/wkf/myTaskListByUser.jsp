<%@page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.api.task.Task"%>
<%@page import="com.dhcc.workflow.pvm.internal.task.TaskImpl"%>
<%@page import="com.dhcc.workflow.pvm.internal.task.IForm"%>
<%@page import="com.dhcc.workflow.api.task.SignState"%>
<%@page import="com.dhcc.workflow.Format" %>
<%@page import="com.dhcc.workflow.WF" %>
<%@page import="com.dhcc.workflow.NodeType"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="wf_base_approve.jsp"%>
<%@ taglib uri="/WEB-INF/tld/pagetag.tld" prefix="pageC"%>
<html>
	<head>
		<title>待处理任务列表</title>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<!--con -->
				<div class="from_bg">
					<form id="${webPath}/task/listMyTask" name="cms_form" action="${webPath}/wkf/Task/listMyTask" method="post">
						<!--table -->
						<div class="table_w">
							<!--list -->
							<table id="tablist" width="100%" border="0"   align="center" cellspacing="1" class="ls_list" title="待处理任务列表">
								
								<thead> 
									<tr> 
										<th scope="col"  align="center">序号</th>
										<th scope="col"  align="center">任务描述</th>
										<th scope="col"  align="center">业务编号</th>
										<th scope="col"  align="center">业务数据</th>
										<th scope="col"  align="center">任务执行人</th>
										<th scope="col"  align="center">创建时间</th>
										<th scope="col"  align="center">截止时间</th>
										<th scope="col"  align="center">业务表单</th>
										<th scope="col" colspan="1" align="center" > <font class="button_color">操作</font></th>
									</tr>
								</thead>
							 <tbody id="tab"> 
							<%
								Object obj = request.getAttribute(WF.PARAM_LIST_NAME);
								
								int total= 0;
								List list = null;
								if( obj != null ) {
									list = (List) obj;
									total = list.size();
								} 
								for(int i = 0; i < total ;i++) {
									Task task = (Task)list.get(i);
							%>
								<tr >
									<td align="center"><%=i+1 %></td>
									<td align="center"><%=Format.formatString(task.getDescription()) %></td>
									<td align="center"><%=Format.formatString(task.getAppId()) %></td>
									<td align="left"><script language="javascript">document.write(formatAppValue('<%=Format.formatString(task.getAppValue()) %>'));</script></td>
									<td align="center"><%=Format.formatString(task.getAssignee()) %></td>
									<td align="center"><%=Format.formatDate(task.getCreateTime()) %></td>
									<td align="center"><%=Format.formatDate(task.getDuedate()) %></td>
									<td align="center"><script language="javascript">document.write(formatTaskForm("${webPath}", "<%=task.getId()%>", "<%=Format.formatString(task.getForms()) %>"));</script></td>
									<td width="5%" align="center">
									<%if(SignState.STATE_SIGNED.equals(task.getSignState())){ %>
										<button onclick="javascript:lkconfirm('${webPath}/wkf/Task/untake?taskId=<%=task.getId() %>')" class="ico_button4All" title="取消签收">取消签收</a>
									<%}%>
										<button onclick="javascript:openApproveUrl('<%=task.getId() %>','<%=task.getApproveUrl()%>')" class="ico_button4All" title="审批">审批</button>
										<button onclick="javascript:buttonforward('${webPath}/wkf/svg/ProcessToSVG/viewProcessIns?processInstanceId=<%=task.getExecutionId() %>')" class="ico_button4All" title="查看流程">查看流程</button>
									</td>
								</tr>
								<%} %>
							 </tbody> 
			</table>
 			<pageC:page urlPath="${webPath}/task/listMyTaskByUser" scope="request" page="ipage" />
							<!--list end-->
						</div>
						<!--table end -->
					</form>
				</div>
				<!--con end-->
			</div>
 
		</div>
	</body>
</html>

<script language="javascript" >
	function openApproveUrl(taskId, approveUrl) {
		var url = '${webPath}/wkf/Task/<%=WF.PARAM_TASK_OPEN_APPROVEURL%>?taskId=' + taskId;
		buttonforward(url);
	}
</script>
