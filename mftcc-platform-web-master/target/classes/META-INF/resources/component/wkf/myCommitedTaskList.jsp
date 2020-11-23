<%@page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.api.history.HistoryTask"%>
<%@page import="com.dhcc.workflow.pvm.internal.history.model.HistoryTaskImpl"%>
<%@page import="com.dhcc.workflow.pvm.internal.task.IForm"%>
<%@page import="com.dhcc.workflow.Format" %>
<%@page import="com.dhcc.workflow.WF" %>
<script language="javascript">
<!--
var isGoBack = true;
-->
</script>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib uri="/WEB-INF/tld/pagetag.tld" prefix="pageC"%>
<html>
	<head>
		<title>我已提交的任务</title>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<!--con -->
				<div class="from_bg">
					<form id="${webPath}/task/listMyTask" name="cms_form" action="<%=webPath %>/wkf/Task/listMyTask" method="post">
						<!--table -->
						<div class="table_w">
							<!--list -->
							<table id="tablist" width="100%" border="0"   align="center" cellspacing="1" class="ls_list" title="待处理任务列表">
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<thead> 
									<tr> 
										<th scope="col"  align="center">序号</th>
										<th scope="col"  align="center">任务实例编号</th>
										<th scope="col"  align="center">任务名称</th>
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
									HistoryTaskImpl task = (HistoryTaskImpl)list.get(i);
							%>
								<tr >
									<td align="center"><%=i+1 %></td>
									<td align="center"><%=task.getId() %></td>
									<td align="center"><%=task.getActivityName()%></td>
									<td align="center"><%=Format.formatString(task.getDescription()) %></td>
									<td align="center"><%=Format.formatString(task.getAppId()) %></td>
									<td align="left"><script language="javascript">document.write(formatAppValue('<%=Format.formatString(task.getAppValue()) %>'));</script></td>
									<td align="center"><%=task.getAssignee() %></td>
									<td align="center"><%=task.getCreateTime() %></td>
									<td align="center"><%=Format.formatDate(task.getEndTime()) %></td>
									<td align="center"><script language="javascript">document.write(formatTaskForm("${webPath}", "<%=task.getId()%>", "<%=Format.formatString(task.getForms()) %>"));</script></td>
									<td width="5%" align="center">
										<button onclick="javascript:buttonforward('${webPath}/wkf/svg/ProcessToSVG/viewProcessIns?processInstanceId=<%=task.getExecutionId() %>')" class="ico_button4All" title="查看流程">查看流程</button>
									</td>
								</tr>
								<%} %>
							 </tbody> 
			</table>
			<pageC:page urlPath="${webPath}/task/myCommitedTask" scope="request" page="ipage" />
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
