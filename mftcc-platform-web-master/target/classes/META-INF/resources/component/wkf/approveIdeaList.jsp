<%@page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.pvm.internal.history.model.HistoryTaskImpl"%>
<%@page import="com.dhcc.workflow.Format" %>
<%@page import="com.dhcc.workflow.WF" %>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib uri="/WEB-INF/tld/pagetag.tld" prefix="pageC"%>
<html>
	<head>
		<title>审批意见查询</title>
		<script type="text/javascript">
			window.name="ideawindow";
		</script>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<!--con -->
				<div class="from_bg">
					<form id="${webPath}/task/approveIdea" name="cms_form" action="<%=webPath %>/task/approveIdea" method="post" target="ideawindow">
						<!--table -->
						<div class="table_w">
							<!--list -->
							<table id="tablist" width="100%" border="0"   align="center" cellspacing="1" class="ls_list" title="流程任务列表">
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
										<th scope="col"  align="center">任务描述</th>
										<th scope="col"  align="center">审批人</th>
										<th scope="col"  align="center">审批结果</th>
										<th scope="col"  align="center">审批意见</th>
										<th scope="col"  align="center">创建时间</th>
										<th scope="col"  align="center">结束时间</th>
										<th scope="col"  align="center">耗时</th>
									</tr>
								</thead>
							 <tbody id="tab"> 
							<%
								Object obj =  request.getAttribute(WF.PARAM_LIST_NAME);
								
								int total = 0;
								List list = null;
								if( obj != null ) {
									list = (List) obj;
									total = list.size();
								}
								String assignee = "";
								String proxyUser = "";
								for(int i = 0; i < total ;i++) {
									HistoryTaskImpl task = (HistoryTaskImpl)list.get(i);
									proxyUser = task.getProxyUser();
									assignee = (proxyUser == null || "".equals(proxyUser)) ? task.getAssignee() :  proxyUser + "(代理审批)";
							%>
								<tr >
									<td align="center"><%=i+1 %></td>
									<td align="center"><%=Format.formatString(task.getDescription()) %></td>
									<td align="center"><%=Format.formatString(assignee) %></td>
									<td align="center"><%=Format.getResultType(task.getResult()) %></td>
									<td align="left"><%=Format.formatString(task.getApproveIdea() ) %></td>
									<td align="center"><%=Format.formatDate(task.getCreateTime()) %></td>
									<td align="center"><%=Format.formatDate(task.getEndTime()) %></td>
									<td align="center"><%=Format.formatTimeDiff(task.getDuration()) %></td>
								</tr>
								<%} %>
							 </tbody> 
			</table>
			<% String executionId = request.getParameter("executionId"); %>
			<input type="hidden" name="executionId" value="<%=executionId %>">
 			<pageC:page urlPath="${webPath}/task/approveIdea" scope="request" page="ipage" />
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
