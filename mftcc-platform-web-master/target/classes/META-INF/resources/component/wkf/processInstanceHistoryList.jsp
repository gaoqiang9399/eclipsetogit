<%@page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.api.history.*"%>
<%@page import="com.dhcc.workflow.pvm.internal.history.model.*"%>
<%@page import="com.dhcc.workflow.Format" %>
<%@page import="com.dhcc.workflow.WF" %>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib uri="/WEB-INF/tld/pagetag.tld" prefix="pageC"%>
<html>
	<head>
		<title>流程实例查询</title>
		
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<!--con -->
				<div class="from_bg">
					<form id="${webPath}/processInstanceHistory/findByPage" name="cms_form" action="${webPath}/wkf/ProcessInstanceHistory/findByPage" method="post">
						
						<!--table -->
						<div class="table_w">
							<!--list -->
							<table id="tablist" width="100%" border="0"   align="center" cellspacing="1" class="ls_list" title="流程实例列表">
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
										<th scope="col"  align="center">流程实例标识</th>
										<th scope="col"  align="center">流程描述</th>
										<th scope="col"  align="center">业务编号</th>
										<th scope="col"  align="center">业务数据</th>
										<th scope="col"  align="center">开始时间</th>
										<th scope="col"  align="center">结束时间</th>
										<th scope="col"  align="center">结束节点</th>
										<th scope="col"  align="center">耗时</th>
										<th scope="col"  align="center">流程状态</th>
										<th scope="col" colspan="1" align="center" > <font class="button_color">操作</font></th>
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
								for(int i = 0; i < total ;i++) {
									HistoryProcessInstance hpi = (HistoryProcessInstance)list.get(i);
							%>
								<tr >
									<td align="center"><%=i+1 %></td>
									<td align="center"><%=hpi.getProcessInstanceId()  %></td>
									<td align="center"><%=Format.formatString(((HistoryProcessInstanceImpl)hpi).getProcessDefinitionDesc()) %></td>
									<td align="center"><%=Format.formatString(((HistoryProcessInstanceImpl)hpi).getAppId()) %></td>
									<td align="left"><script language="javascript">document.write(formatAppValue('<%=Format.formatString(((HistoryProcessInstanceImpl)hpi).getAppValue()) %>'));</script></td>
									<td align="center"><%=Format.formatDate(hpi.getStartTime()) %></td>
									<td align="center"><%=Format.formatDate(hpi.getEndTime()) %></td>
									<td align="center"><%=Format.formatString(hpi.getEndActivityName()) %></td>
									<td align="center"><%=Format.formatTimeDiff(hpi.getDuration()) %></td>
									<td align="center"><font color="red"><B><%=Format.formatState(hpi.getState()) %></B></font></td>
									<td width="5%" align="center">
										<button  onclick="javascript:buttonforward('${webPath}/wkf/svg/ProcessToSVG/viewProcessIns?processInstanceId=<%=hpi.getProcessInstanceId() %>')" class="ico_button4All" title="查看流程">查看流程</button>
									</td>
								</tr>
								<%} %>
							 </tbody> 
			</table>
 			<pageC:page urlPath="${webPath}/processInstanceHistory/findByPage" scope="request" page="ipage" />
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

