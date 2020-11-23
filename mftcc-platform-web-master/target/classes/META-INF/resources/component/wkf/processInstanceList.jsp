<%@page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.api.ProcessInstance"%>
<%@page import="com.dhcc.workflow.pvm.internal.model.ExecutionImpl"%>
<%@page import="com.dhcc.workflow.Format" %>
<%@page import="com.dhcc.workflow.WF" %>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib uri="/WEB-INF/tld/pagetag.tld" prefix="pageC"%>
<html>
	<head>
		<title>流程实例管理</title>
		
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<!--con -->
				<div class="from_bg">
					<form id="${webPath}/processInstance/findByPage" name="cms_form" action="${webPath}/wkf/ProcessInstance/findByPage" method="post">
						
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
										<th scope="col"  align="center">当前阶段</th>
										<th scope="col"  align="center">业务编号</th>
										<th scope="col"  align="center">业务数据</th>
										<th scope="col"  align="center">状态</th>
										<th scope="col"  align="center">操作员</th>
										<th scope="col" colspan="5" align="center" > <font class="button_color">操作</font></th>
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
									ProcessInstance pi = (ProcessInstance)list.get(i);
							%>
								<tr >
									<td align="center"><%=i+1 %></td>
									<td align="center"><%=pi.getId() %></td>
									<td align="center"><%=Format.formatString(((ExecutionImpl)pi).getProcessDefinitionDesc())%></td>
									<td align="center"><%=pi.findActiveActivityNames().toString() %></td>
									<td align="center"><%=Format.formatString(((ExecutionImpl)pi).getAppId()) %></td>
									<td align="left"><script language="javascript">document.write(formatAppValue('<%=Format.formatString(((ExecutionImpl)pi).getAppValue()) %>'));</script></td>
									<td align="center"><font color="red"><B><%=Format.formatState(pi.getState())%></B></font></td>
									<td align="center"><%=Format.formatString(((ExecutionImpl)pi).getOperator())%></td>
									<td width="5%" align="center">
										<button  onclick="javascript:buttonforward('${webPath}/wkf/svg/ProcessToSVG/viewProcessIns?processInstanceId=<%=pi.getId() %>')" class="ico_button4All" title="查看流程">查看流程</button>
									</td>
									<td width="5%" align="center">
										<button  onclick="javascript:lkconfirm('${webPath}/wkf/ProcessInstance/suspend?processInstanceId=<%=pi.getId() %>')" class="ico_button4All" title="暂停">暂停</button>
									</td>
									<td width="5%" align="center">
										<button  onclick="javascript:lkconfirm('${webPath}/wkf/ProcessInstance/resume?processInstanceId=<%=pi.getId() %>')" class="ico_button4All" title="恢复">恢复</button>
									</td>
									<td width="5%" align="center">
										<button  onclick="javascript:lkconfirm('${webPath}/wkf/ProcessInstance/cancel?processInstanceId=<%=pi.getId() %>')" class="ico_button4All" title="撤销">撤销</button>
									</td>
									<td width="5%" align="center">
										<button  onclick="javascript:lkconfirm('${webPath}/wkf/ProcessInstance/delete?processInstanceId=<%=pi.getId() %>')" class="ico_button4All" title="删除">删除</button>
									</td>
								</tr>
								<%} %>
							 </tbody> 
			</table>
 			<pageC:page urlPath="${webPath}/processInstance/findByPage" scope="request" page="ipage" />
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

